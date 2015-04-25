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
public class OtherSessionFrameDecoder extends FrameDecoder {
	private static Logger logger = LoggerFactory
			.getLogger(OtherSessionFrameDecoder.class);
	private static final int DEFAULT_HEAD_LENGTH = 2;
	public static final String OTHERSESSIONID = "otherSessionId";
	private int headLength;

	public OtherSessionFrameDecoder() {
		this.headLength = DEFAULT_HEAD_LENGTH;
	}

	public OtherSessionFrameDecoder(int length) {
		this.headLength = length;
	}

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel,
			ChannelBuffer buffer) throws Exception {
		try {
			if (!buffer.readable()) {
				return null;
			}
			int index = buffer.readerIndex();
			
			short head = buffer.getShort(index);
			short len = buffer.getShort(index + 2);
			/*if(buffer.capacity() - index < len){
				return null;
			}*/
			if (buffer.readableBytes() < len) {
				return null;
			}
			short protocolId = buffer.getShort(index + 4);
			if (head != 1000) {
				throw new JuiceException("message head value is error!" + channel.getRemoteAddress());
			}
			
//			}
			short otherLength = buffer.getShort(index + 6);
			byte[] otherSessionString = new byte[otherLength];
			buffer.getBytes(index + 8, otherSessionString);
			String otherSessionId = new String(otherSessionString);
			//
			byte[] content = new byte[len - 10 - otherLength];
			
			buffer.getBytes(index + 8 + otherLength, content);
			InputMessage msg = new InputMessage(content);
			short last = buffer.getShort(index + len - 2);
			if (last != 2000) {
				throw new JuiceException("message last value is error!");
			}
			//
			buffer.skipBytes(len);
			DefaultJuiceMessage message = new DefaultJuiceMessage(protocolId);
			message.setMsg(msg);
			message.setOtherSessionId(otherSessionId);
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
