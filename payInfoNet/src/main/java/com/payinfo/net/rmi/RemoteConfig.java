/**
 * Juice
 * com.juice.orange.game.rmi
 * RemoteConfig.java
 */
package com.payinfo.net.rmi;

/**
 * @author shaojieque 
 * 2013-4-18
 */
public class RemoteConfig {
	/** 远程调用服务名称：索引字段*/
	private String name;
	/** 远程服务器ip地址*/
	private String address;
	/** 远程服务器端口号*/
	private int port;

	public RemoteConfig(){
		
	}
	
	public RemoteConfig(String name, String address, int port) {
		this.name = name;
		this.address = address;
		this.port = port;
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}
