/**
 * Juice
 * com.juice.orange.game.handler
 * JuiceChannelHandler.java
 */
package com.payinfo.net.handler;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.handler.codec.http.HttpVersion;

import com.payinfo.net.container.Container;
import com.payinfo.net.container.GameSession;
import com.payinfo.net.container.GameSessionImpl;
import com.payinfo.net.exception.JuiceException;
import com.payinfo.net.server.JuiceServer;
import com.payinfo.net.util.ConnectionHelper;
import com.payinfo.net.util.threadpool.JuicePoolManager;

/**
 * @author shaojieque 2013-3-20
 */
public class JuiceChannelHandler extends SimpleChannelHandler {
	private static final Object IGNORE_REQUEST = new Object();

	private final Executor executor;
	private final List<IJuiceHandler> handlers;
	private final Object id;
	private final long timestamp;
	private final Thread.UncaughtExceptionHandler exceptionHandler;
	private final Thread.UncaughtExceptionHandler ioExceptionHandler;
	private final ConnectionHelper connectionHelper;
	private String transport;
	
	public JuiceChannelHandler(Executor executor, List<IJuiceHandler> handlers,
			Object id, long timestamp,
			Thread.UncaughtExceptionHandler exceptionHandler,
			Thread.UncaughtExceptionHandler ioExceptionHandler,
			String transport) {
		this.executor = executor;
		this.handlers = handlers;
		this.id = id;
		this.timestamp = timestamp;
		this.exceptionHandler = exceptionHandler;
		this.ioExceptionHandler = ioExceptionHandler;
		this.transport = transport;
		//
		connectionHelper = new ConnectionHelper(executor, exceptionHandler,
				ioExceptionHandler) {
			@Override
			protected void fireOnClose() throws Exception {
				throw new UnsupportedOperationException();
			}
		};
	}

	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		GameSession session = null;
		String sessionId = Container.getChannelSession(ctx.getChannel().getId());
		if (sessionId == null) {
			session = new GameSessionImpl(ctx);
			Container.addSession(session);
			Container.addChannelSession(ctx.getChannel().getId(), session.getSessionId());
		} else {
			session = Container.getSessionById(sessionId);
		}
		//
		if (session != null) {
			session.setStatus(GameSession.STATUS_CONN);
		}
	}

	@Override
	public void channelDisconnected(ChannelHandlerContext ctx,
			ChannelStateEvent e) throws Exception {
		GameSession session = null;
		String sessionId = Container.getChannelSession(ctx.getChannel().getId());
		if (sessionId != null) {
			session = Container.getSessionById(sessionId);
			session.setStatus(GameSession.STATUS_UNCONN);
			Container.notifyListener(session);
			Container.removeSession(session.getSessionId());
			
		}
		//
		Container.removeChannelSession(ctx.getChannel().getId());
		StaleConnectionTrackingHandler.stopTracking(ctx.getChannel());
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		GameSession session = null;
		String sessionId = Container.getChannelSession(ctx.getChannel().getId());
		if (sessionId != null) {
			session = Container.getSessionById(sessionId);
		}
		//
		if (e.getMessage() instanceof DefaultJuiceMessage) {
			DefaultJuiceMessage messgae = (DefaultJuiceMessage) e.getMessage();
			handleDefaultRequest(ctx, e, session, messgae);
		} else if (e.getMessage() instanceof HttpRequest
				&& ctx.getAttachment() != IGNORE_REQUEST) {
			handleHttpRequest(ctx, e, session, (HttpRequest) e.getMessage());
		}
	}

	private void handleDefaultRequest(final ChannelHandlerContext ctx,
			MessageEvent messageEvent, GameSession session,
			DefaultJuiceMessage defaultRequest) {
		if (defaultRequest.getOtherSessionId() != null && defaultRequest.getOtherSessionId() != "") {
			session = Container.getSessionById(defaultRequest.getOtherSessionId());
			if (session == null) {
				session = new GameSessionImpl(ctx);
				session.setSessionId(defaultRequest.getOtherSessionId());
				Container.addSession(session);
				session.setStatus(GameSession.STATUS_CONN);
				session.put(OtherSessionFrameDecoder.OTHERSESSIONID, defaultRequest.getOtherSessionId());
			}
		}
		final DefaultJuiceRequest request = new DefaultJuiceRequest(ctx.getChannel().getRemoteAddress(),
				session, String.valueOf(defaultRequest.getProtocolId()), defaultRequest.getMsg(), id, timestamp);
		final DefaultJuiceResponse response = new DefaultJuiceResponse(session);
		Iterator<IJuiceHandler> handlerIterator = this.handlers.iterator();
		final DefaultJuiceControl control = new DefaultJuiceControl(
				handlerIterator, executor, request, response, defaultRequest);
		
		JuicePoolManager.addRequest(control);
	}

	private void handleHttpRequest(final ChannelHandlerContext ctx,
			MessageEvent messageEvent, GameSession session,
			HttpRequest httpRequest) {
		final JuiceHttpRequest request = new JuiceHttpRequest(messageEvent,
				httpRequest, session, id, timestamp);
		DefaultHttpResponse ok_200 = new DefaultHttpResponse(
				HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
		final JuiceHttpResponse response = new JuiceHttpResponse(ctx, ok_200,
				HttpHeaders.isKeepAlive(httpRequest), exceptionHandler);
		Iterator<IJuiceHandler> handlerIterator = this.handlers.iterator();
		final HttpControl control = new JuiceHttpControl(handlerIterator,
				executor, ctx, request, response, httpRequest, ok_200,
				exceptionHandler, ioExceptionHandler);
		JuicePoolManager.addRequest(control);
	}

	@Override
	public void exceptionCaught(final ChannelHandlerContext ctx,
			final ExceptionEvent e) throws Exception {
		connectionHelper.fireConnectionException(e);
		exceptionHandler.uncaughtException(Thread.currentThread(),
				JuiceException.fromException(e.getCause(), ctx.getChannel()));
	}
}
