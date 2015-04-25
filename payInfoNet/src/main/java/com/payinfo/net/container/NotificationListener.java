/**
 * Juice
 * com.juice.orange.game.container
 * NotificationListener.java
 */
package com.payinfo.net.container;

import com.payinfo.net.exception.JuiceException;

/**
 * @author shaojieque
 * 2013-3-30
 */
public interface NotificationListener {
	void handler(Object obj) throws JuiceException;
}
