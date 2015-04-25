/**
 * 
 */
package com.payinfo.net.util.threadpool;

import java.util.concurrent.ArrayBlockingQueue;

import org.apache.log4j.Logger;

import com.payinfo.net.handler.DefaultJuiceControl;
import com.payinfo.net.handler.IJuiceControl;
import com.payinfo.net.log.LoggerFactory;
import com.payinfo.net.log.LoggerName;

/**
 * @author queshaojie
 * 
 *         orangegame
 */
public class JuiceThreadPool {
	//@off
	private static Logger logger = LoggerFactory.getLogger(LoggerName.STAT);
	//@on
	// 队列最大容量
	public static final int Q_SIZE = 1024000;
	public static final int REQUEST_TIME_OUT = 10000;
	public static final int POOL_WARN_SIZE = 200;
	public static final int THREAD_SIZE = 10;

	private ArrayBlockingQueue<JuiceNotify> requestQueue = new ArrayBlockingQueue<JuiceNotify>(
			Q_SIZE);

	public void addJuiceNotify(IJuiceControl control) {
		JuiceNotify jn = new JuiceNotify();
		jn.setControl(control);
		jn.setRecvTime(System.currentTimeMillis());
		
		requestQueue.add(jn);
	}
	
	public void initThreadPool() {
		for (int i = 0; i < THREAD_SIZE; i++) {
			JuiceWorkThread jwt = new JuiceWorkThread();
			jwt.start();
		}
	}

	public class JuiceWorkThread extends Thread {
		@Override
		public void run() {
			IJuiceControl control = null;
			while (true) {
				try {
					JuiceNotify jn = requestQueue.take(); //.poll(100,TimeUnit.MILLISECONDS);
					int warntime = 1000;
					long now = 0;
					long timeout = 0;
					int poolSize = 0;
					if (jn != null) {
						now = System.currentTimeMillis();
						timeout = now - jn.getRecvTime();
						if (timeout > warntime) {
							logger.warn("request queue wait too long, wait time:"
									+ timeout);
						}
						//
						control = jn.getControl();
						try {
							control.nextHandler();
							if (control instanceof DefaultJuiceControl) {
								DefaultJuiceControl _control = (DefaultJuiceControl) control;
								logger.warn("method start time :" + (System.currentTimeMillis() - now) + ", protocolId:" + _control.getMessage().getProtocolId());
							}
						} catch (Exception e) {
							logger.error("control exception ", e);
						}
						//
						poolSize = requestQueue.size();
						if (poolSize >= POOL_WARN_SIZE) {
							logger.warn("Thread pool request queue size:"
									+ poolSize + " is not ok");
						}
					}
				} catch (InterruptedException e) {
					logger.error("WorkThread exception", e);
				}
			}
		}
	}
}
