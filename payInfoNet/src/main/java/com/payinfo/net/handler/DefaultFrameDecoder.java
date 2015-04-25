/**
 * Juice
 * com.juice.orange.game.handler
 * DefaultStringHandler.java
 */
package com.payinfo.net.handler;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

import com.payinfo.net.exception.JuiceException;
import com.payinfo.net.log.LoggerFactory;
import com.payinfo.net.util.InputMessage;

/**
 * @author shaojieque 
 * 2013-3-22
 */
public class DefaultFrameDecoder extends FrameDecoder {
	private static Logger logger = LoggerFactory
			.getLogger(DefaultFrameDecoder.class);
	private static final int DEFAULT_HEAD_LENGTH = 2;
	private int headLength;

	public DefaultFrameDecoder() {
		this.headLength = DEFAULT_HEAD_LENGTH;
	}

	public DefaultFrameDecoder(int length) {
		this.headLength = length;
	}

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel,
			ChannelBuffer buffer) throws Exception {
		try {
			if (!buffer.readable()) {
				return null;
			}
			//int length = buffer.readableBytes();
			int index = buffer.readerIndex();
			short head = buffer.getShort(index);
			if (head != 1000) {
				throw new JuiceException("message head value is error!" + channel.getRemoteAddress());
			}
			short len = buffer.getShort(index + 2);
			if (buffer.readableBytes() < len) {
				return null;
			}
			
			short protocolId = buffer.getShort(index + 4);
			//
			byte[] content = new byte[len - 4 - headLength - 2];
			buffer.getBytes(index + 4 + headLength, content);
			InputMessage msg = new InputMessage(content);
			
			short last = buffer.getShort(index + len - 2);
			if (last != 2000) {
				throw new JuiceException("message last value is error!");
			}
			//
			buffer.skipBytes(len);
			//ChannelBuffer frame = extractFrame(buffer, index + len, buffer.readableBytes());
			//buffer.
			//buffer.discardReadBytes();
			
			DefaultJuiceMessage message = new DefaultJuiceMessage(protocolId);
			message.setMsg(msg);
			//Channels.fireMessageReceived(channel, message);
			return message;
		} catch (Exception e) {
			logger.error("Exception message : ",e);
		} 

		return null;
	}
	
	protected ChannelBuffer extractFrame(ChannelBuffer buffer, int index, int length) {
        ChannelBuffer frame = buffer.factory().getBuffer(length);
        frame.writeBytes(buffer, index, length);
        return frame;
    }
}
