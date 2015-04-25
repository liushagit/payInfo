/**
 * Juice
 * com.juice.orange.game.handler
 * IJuiceHandler.java
 */
package com.payinfo.net.handler;

/**
 * @author shaojieque 
 * 2013-3-19
 */
public interface IJuiceHandler {
	/**
	 * handler request
	 */
	void handlerRequest(IJuiceRequest request, IJuiceResponse response,
			IJuiceControl control) throws Exception;
}
