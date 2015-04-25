/**
 * Juice
 * com.juice.orange.game.rmi
 * DefaultRemoteFrameDecoder.java
 */
package com.payinfo.net.rmi;

import java.util.Iterator;
import java.util.List;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.UpstreamMessageEvent;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
import org.jboss.netty.handler.codec.serialization.ClassResolver;
import org.jboss.netty.handler.codec.serialization.ObjectDecoder;

/**
 * @author shaojieque
 * 2013-5-7
 */
public class DefaultRemoteFrameDecoder extends ObjectDecoder {
	private String name;
	
	public DefaultRemoteFrameDecoder(String name, ClassResolver classResolver) {
		super(classResolver);
		this.name = name;
    }
	
	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel,
			ChannelBuffer buffer) throws Exception {
		ChannelBuffer bufferClone = ChannelBuffers.copiedBuffer(buffer);
		Object obj = super.decode(ctx, channel, bufferClone);
        if (obj == null) {
        	// 无法解析出来
        	ChannelHandler _decoder = getNextHandler(channel);
        	if (_decoder instanceof FrameDecoder) {
        		FrameDecoder decoder = (FrameDecoder) _decoder;
        		decoder.messageReceived(ctx, new UpstreamMessageEvent(channel, 
        				buffer, ctx.getChannel().getRemoteAddress()));
        	}
            return null;
        }
        bufferClone.clear();
		return super.decode(ctx, channel, buffer);
	}
	
	private ChannelHandler getNextHandler(Channel channel) {
		List<String> handlerNames = channel.getPipeline().getNames();
		Iterator<String> iter = handlerNames.iterator();
		while (iter.hasNext()) {
			String currName = iter.next();
			if (currName.equals(name)) {
				break;
			}
		}
		String nextName = iter.next();
		return channel.getPipeline().get(nextName);
	}
}
