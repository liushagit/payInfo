/**
 * Juice
 * com.juice.orange.game.work
 * TransportNotify.java
 */
package com.payinfo.net.rmi;


/**
 * @author shaojieque 
 * 2013-8-6
 */
public class TransportNotify {
	private Transport transport;
	private RemoteConfig remoteConfig;

	public TransportNotify(Transport transport, RemoteConfig remoteConfig) {
		this.transport = transport;
		this.remoteConfig = remoteConfig;
	}

	public Transport getTransport() {
		return transport;
	}

	public void setTransport(Transport transport) {
		this.transport = transport;
	}

	public RemoteConfig getRemoteConfig() {
		return remoteConfig;
	}

	public void setRemoteConfig(RemoteConfig remoteConfig) {
		this.remoteConfig = remoteConfig;
	}
}
