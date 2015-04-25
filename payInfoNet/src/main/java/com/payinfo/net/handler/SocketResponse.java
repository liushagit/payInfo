/**
 * Juice
 * com.juice.orange.game.handler
 * SocketResponse.java
 */
package com.payinfo.net.handler;

import com.payinfo.net.util.OutputMessage;

/**
 * @author shaojieque
 * 2013-4-10
 */
public interface SocketResponse extends IJuiceResponse{
	void sendMessage(short protocolId, OutputMessage msg);
}
