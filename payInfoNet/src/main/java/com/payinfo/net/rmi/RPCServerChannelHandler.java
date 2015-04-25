/**
 * Juice
 * com.juice.orange.game.rmi
 * RPCServerChannelHandler.java
 */
package com.payinfo.net.rmi;

import java.util.concurrent.Executor;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

import com.payinfo.net.rmi.control.RPCServerControl;
import com.payinfo.net.util.threadpool.JuicePoolManager;

/**
 * @author shaojieque
 * 2013-4-15
 */
public class RPCServerChannelHandler extends SimpleChannelUpstreamHandler {
	//
	private final Executor executor;
	//
	public RPCServerChannelHandler(Executor executor){
		this.executor = executor;
	}
	
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		if (e.getMessage() instanceof Transport) {
			Transport t = (Transport) e.getMessage();
			RPCServerControl control = new RPCServerControl(executor, ctx, t);
			JuicePoolManager.addRequest(control);
			return;
		}
		super.messageReceived(ctx, e);
	}
}
