/**
 * Juice
 * com.juice.orange.game.work
 * RemoteRequestPool.java
 */
package com.payinfo.net.rmi.thread;

import java.util.concurrent.ArrayBlockingQueue;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;

import com.payinfo.net.exception.JuiceException;
import com.payinfo.net.log.LoggerFactory;
import com.payinfo.net.rmi.RemoteChannelFactory;
import com.payinfo.net.rmi.RemoteConfig;
import com.payinfo.net.rmi.Transport;
import com.payinfo.net.rmi.TransportNotify;

/**
 * @author shaojieque 2013-8-1
 */
public class RemoteRequestPool {
	//队列最大容量  
    public static final int Q_SIZE = 1024000; 
	static {
		for (int i=0; i<5; i++) {
			RemoteWorkThread rwt = new RemoteWorkThread();
			rwt.start();
		}
	}

	private static ArrayBlockingQueue<TransportNotify> requestQueue = new ArrayBlockingQueue<TransportNotify>(Q_SIZE);

	public static ArrayBlockingQueue<TransportNotify> getRequestQueue() {
		return requestQueue;
	}
	
	public static void addTransport(TransportNotify tn) {
		requestQueue.add(tn);
	}
	
	public static class RemoteWorkThread extends Thread {
		public static Logger logger = LoggerFactory.getLogger(RemoteWorkThread.class);
		//
		public final static int REMOTE_TIME_OUT = 10000;
		
		@Override
		public void run() {
			while (true) {
				try {
					TransportNotify tn = requestQueue.take(); // .poll(100, TimeUnit.MILLISECONDS);
					if (tn!=null) {
						Transport t = tn.getTransport();
						RemoteConfig rc = tn.getRemoteConfig();
						sendRemoteRequest(t, rc);
					}
				} catch (Exception e) {
					logger.error("SEND", e);
				}
			}
		}
		
		private void sendRemoteRequest(Transport t, RemoteConfig rc) throws Exception{
			RemoteChannelFactory rcf = new RemoteChannelFactory();
			Channel channel = rcf.get(rc, REMOTE_TIME_OUT);
			if (!channel.isConnected()){
				rcf.removeClient(rcf.getKey(rc));
				channel = rcf.get(rc, REMOTE_TIME_OUT);
			}
			if (!channel.isConnected()){
				throw new JuiceException("Remote channle is not connected, check channel:"
						+ channel.getRemoteAddress());
			}
			channel.write(t);
		}
	}
}
