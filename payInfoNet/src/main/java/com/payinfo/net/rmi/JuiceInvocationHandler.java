/**
 * Juice
 * com.juice.orange.game.rmi
 * JuiceInvocationHandler.java
 */
package com.payinfo.net.rmi;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.payinfo.net.container.Container;
import com.payinfo.net.log.LoggerFactory;
import com.payinfo.net.rmi.thread.RemoteRequestPool;

/**
 * @author shaojieque 
 * 2013-4-11
 */
public class JuiceInvocationHandler implements InvocationHandler, Serializable {
	private static Logger logger = LoggerFactory.getLogger(JuiceInvocationHandler.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//
	public static final int REMOTE_TIME_OUT = 10000;
	//
	//private Channel channel;
	private String remoteName;
	private String clazzName;

	public JuiceInvocationHandler(String remoteName, String clazzName) {
		//this.channel = channel;
		this.remoteName = remoteName;
		this.clazzName = clazzName;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Transport t = new Transport();
		//t.setClazz(proxy.getClass().getName());
		t.setClazz(clazzName);
		t.setMethod(method.getName());
		t.setParamTypes(method.getParameterTypes());
		t.setArgs(args);
		CountDownLatch latch = new CountDownLatch(1);
		t.setLatch(latch);
		// send a request to server
		JuiceRemoteManager.addTransport(t);
		//
		RemoteConfig rc = Container.getConfigs().get(remoteName);
		TransportNotify tn = new TransportNotify(t, rc);
		RemoteRequestPool.addTransport(tn);
		try {
			boolean timeOut = tn.getTransport().getLatch().await(REMOTE_TIME_OUT, TimeUnit.MILLISECONDS);
			if(!timeOut){
				logger.error("Latch request time out..");
			}
		}finally {
			JuiceRemoteManager.removeTransport(t.getId());
		}
		//
		if (t.getResult().getResult() instanceof Throwable) {
			throw (Throwable) t.getResult().getResult();
		}
		return t.getResult().getResult();
	}

	/*private void sendRemoteRequest(final Transport t) {
		Channel channel = JuiceRemoteManager.getChannel(remoteName);
		if (channel==null || !channel.isConnected()) {
			RemoteConfig config = Container.getConfigs().get(remoteName);
			RemoteServer server = new RemoteServer();
			channel = server.setupRemoteServer(config);
		}
		if (!channel.isConnected()){
			throw new JuiceException("Remote channle is not connected, check channel:"+channel.getRemoteAddress());
		}
		channel.write(t);
	}*/
}
