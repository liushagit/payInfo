/**
 * Juice
 * com.juice.orange.game.container
 * GameSessionImpl.java
 */
package com.payinfo.net.container;

import java.io.ByteArrayOutputStream;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelHandlerContext;

import com.payinfo.net.exception.JuiceException;
import com.payinfo.net.log.LoggerFactory;
import com.payinfo.net.util.DateUtils;
import com.payinfo.net.util.OutputMessage;

/**
 * @author shaojieque 2013-3-22
 */
public class GameSessionImpl implements GameSession {
	private static Logger logger = LoggerFactory.getLogger(GameSessionImpl.class);
	private Map<String, Object> objectMap;
	private final ChannelHandlerContext ctx;

	private String sessionId;
	private int status;

	public GameSessionImpl(ChannelHandlerContext ctx) {
		autoGenetateId();
		this.ctx = ctx;
		this.objectMap = new HashMap<String, Object>();
	}

	@Override
	public String getSessionId() {
		return sessionId;
	}
	
	@Override
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	@Override
	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public int getStatus() {
		return status;
	}
	
	@Override
	public void sendMessage(short protocolId, OutputMessage msg) {
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		try {
			int length = 10 + msg.length();

			short head = 1000;
			//
			short last = 2000;
			/**** other id*****/
			short sessionLength = 0;
			Object object = getObject("otherSessionId");
			
			String otherSessionId = "";
			if (object != null) {
				otherSessionId = String.valueOf(object);
				sessionLength = (short)otherSessionId.getBytes().length;
				length += sessionLength;
			}else {
				length -= 2;
			}
			///**** other id*****/
			byte[] length_byte = toBytes((short) length);
			//
			buf.write(toBytes(head));
			buf.write(length_byte);
			
			/**** other id*****/
			if (object != null) {
				buf.write(toBytes(sessionLength));
				buf.write(otherSessionId.getBytes());
			}
			/**** other id*****/
			
			buf.write(toBytes(protocolId));
			buf.write(msg.getBytes());
			buf.write(toBytes(last));
			ChannelBuffer buffer = ChannelBuffers.copiedBuffer(buf
					.toByteArray());
			sendMessage(buffer);
//			byte b[] = buf.toByteArray();
//			for (int i = 0; i < b.length; i++) {
//				System.out.print(b[i] + " ");
//			}
			buf.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	private void sendMessage(ChannelBuffer buffer) {
		try {
			isExistChannel();
			//
			ctx.getChannel().write(buffer);
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	private byte[] toBytes(short value) {
		byte[] value_byte = new byte[2];
		value_byte[0] = (byte) ((value >>> 8) & 0xFF);
		value_byte[1] = (byte) (value & 0xFF);
		return value_byte;
	}

	private void isExistChannel() throws JuiceException {
		if (ctx.getChannel() == null) {
			throw new JuiceException("channel is null, can't send message!");
		}

		if (!ctx.getChannel().isConnected()) {
			SocketAddress remoteAddress = ctx.getChannel().getRemoteAddress();
			boolean result = ctx.getChannel().connect(remoteAddress).isSuccess();
			if (!result) {
				throw new JuiceException(
						"can't not connect by channel remote address!");
			}
		}
	}

	private void autoGenetateId() {
		sessionId = DateUtils.generateId();
	}

	@Override
	public void put(String key, Object value) {
		objectMap.put(key, value);
	}

	@Override
	public Object getObject(String key) {
		return objectMap.get(key);
	}

	@Override
	public void remove(String key) {
		objectMap.remove(key);
	}
}
