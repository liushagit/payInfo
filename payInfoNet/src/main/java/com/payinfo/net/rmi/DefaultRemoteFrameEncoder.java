/**
 * Juice
 * com.juice.orange.game.rmi
 * DefaultRemoteFrameEncoder.java
 */
package com.payinfo.net.rmi;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;

/**
 * @author shaojieque
 * 2013-5-8
 */
public class DefaultRemoteFrameEncoder extends ObjectEncoder {
	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel channel,
			Object msg) throws Exception {
		if (msg instanceof Result) {
			return super.encode(ctx, channel, msg);
		} else  {
			return msg;
		}
	}
}
