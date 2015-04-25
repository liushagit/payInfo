/**
 * Juice
 * com.juice.orange.game.rmi
 * RPCServerControl.java
 */
package com.payinfo.net.rmi.control;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Executor;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.ChannelHandlerContext;

import com.payinfo.net.handler.IJuiceControl;
import com.payinfo.net.log.LoggerFactory;
import com.payinfo.net.rmi.Result;
import com.payinfo.net.rmi.Transport;
import com.payinfo.net.util.ClassUtils;

/**
 * @author shaojieque
 * 2013-7-29
 */
public class RPCServerControl implements IJuiceControl {
	private static Logger logger = LoggerFactory.getLogger(RPCServerControl.class);
	//
	private final Executor executor;
	private Transport transport;
	private ChannelHandlerContext ctx;
	
	public RPCServerControl(final Executor executor, 
			ChannelHandlerContext ctx, Transport transport){
		this.executor = executor;
		this.transport = transport;
		this.ctx = ctx;
	}
	
	
	@Override
	public void execute(Runnable command) {
		handlerExecutor().execute(command);
	}

	@Override
	public void nextHandler() {
		try {
			String clazz = transport.getClazz();
			Object obj = ClassUtils.getObject(clazz);
			//
			Class<?>[] parameterTypes = transport.getParamTypes();
			Object[] params = transport.getArgs();
			Method method = obj.getClass().getMethod(transport.getMethod(), parameterTypes);
			Object result = null;
			try {
				result = method.invoke(obj, params);
			}catch (InvocationTargetException ie) {
				result = ie.getTargetException();
			}
			// 将结果发送给客户端  send the result to client
			Result rs = new Result();
			rs.setId(transport.getId());
			rs.setResult(result);
			ctx.getChannel().write(rs);
		}catch(Exception e) {
			logger.error(e);
		}
	}

	@Override
	public Executor handlerExecutor() {
		return executor;
	}
}
