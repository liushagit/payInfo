/**
 * Juice
 * com.juice.orange.game.bootstrap
 * BootstrapProperties.java
 */
package com.payinfo.net.bootstrap;

/**
 * @author shaojieque 
 * 2013-5-2
 */
public class BootstrapProperties {
	/** 线程池个数 */
	private int poolNum;
	/** 通信协议 */
	private String protocol;
	/** 监听端口 */
	private int port;

	public int getPoolNum() {
		return poolNum;
	}

	public void setPoolNum(int poolNum) {
		this.poolNum = poolNum;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}
