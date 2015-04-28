package com.pingplusplus;

public enum SmallChannel {

	ALIPAY(4,"alipay"),UPMP(5,"upmp"),WEIXIN(6,"wx"),BAIDU(7,"bfb");
	
	private String value;
	private int id;
	private SmallChannel(int id,String value){
		this.id = id;
		this.value = value;
	}
	
	public static String getValue(int id){
		for (SmallChannel c : SmallChannel.values()) {
			if (id == c.id) {
				return c.value;
			}
		}
		return "";
	}
}
