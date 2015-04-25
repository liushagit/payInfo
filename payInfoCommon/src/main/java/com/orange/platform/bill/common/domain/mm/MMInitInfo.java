package com.orange.platform.bill.common.domain.mm;

import java.io.Serializable;

public class MMInitInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String mmAppId;
	private String mmAppKey;
	
	public String getMmAppId() {
		return mmAppId;
	}
	public void setMmAppId(String mmAppId) {
		this.mmAppId = mmAppId;
	}
	public String getMmAppKey() {
		return mmAppKey;
	}
	public void setMmAppKey(String mmAppKey) {
		this.mmAppKey = mmAppKey;
	}

}
