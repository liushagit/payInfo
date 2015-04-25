/**
 * Juice
 * com.juice.orange.game.rmi.control
 * RPCClientControl.java
 */
package com.payinfo.net.rmi.control;

import java.util.concurrent.Executor;

import com.payinfo.net.handler.IJuiceControl;
import com.payinfo.net.rmi.JuiceRemoteManager;
import com.payinfo.net.rmi.Result;
import com.payinfo.net.rmi.Transport;

/**
 * @author shaojieque
 * 2013-8-6
 */
public class RPCClientControl implements IJuiceControl {
	//
	private final Executor executor;
	private Result result;
	
	public RPCClientControl(final Executor executor, Result result){
		this.executor = executor;
		this.result = result;
	}

	@Override
	public void nextHandler() {
		System.out.println("RPCClientControl:" + System.currentTimeMillis() + ";result:"+result.getId());
		Transport t = JuiceRemoteManager.getTransport(result.getId());
		if (t!=null) {
			t.setResult(result);
			t.getLatch().countDown();
		}
	}
	
	@Override
	public void execute(Runnable command) {
		this.executor.execute(command);
	}

	@Override
	public Executor handlerExecutor() {
		return executor;
	}
}
