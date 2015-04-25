/**
 * Juice
 * com.juice.orange.game.handler
 * DefaultJuiceResponse.java
 */
package com.payinfo.net.handler;

import com.payinfo.net.container.GameSession;
import com.payinfo.net.util.OutputMessage;

/**
 * @author shaojieque 2013-3-22
 */
public class DefaultJuiceResponse implements SocketResponse {
	private GameSession session;

	public DefaultJuiceResponse(GameSession session) {
		this.session = session;
	}

	@Override
	public void sendMessage(short protocolId, OutputMessage msg) {
		session.sendMessage(protocolId, msg);
	}
}
