/**
 * 
 */
package com.payinfo.net.util.threadpool;

import com.payinfo.net.handler.IJuiceControl;

/**
 * @author queshaojie
 * 
 *         orangegame
 */
public class JuiceNotify {
	private IJuiceControl control;
	private long recvTime;

	public IJuiceControl getControl() {
		return control;
	}

	public void setControl(IJuiceControl control) {
		this.control = control;
	}

	public long getRecvTime() {
		return recvTime;
	}

	public void setRecvTime(long recvTime) {
		this.recvTime = recvTime;
	}
}
