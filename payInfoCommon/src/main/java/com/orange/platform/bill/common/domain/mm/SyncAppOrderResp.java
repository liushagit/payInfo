/**
 * ChinaMobileMarket
 * com.orange.game.china.mobile.market.domain
 * SyncAppOrderResp.java
 */
package com.orange.platform.bill.common.domain.mm;

import java.io.Serializable;

/**
 * @author author
 * 2012-10-23
 */
public class SyncAppOrderResp implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//
	private String MsgType;//消息类型

	private String Version;//版本号

	private Integer hRet;//返回值

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

	public String getVersion() {
		return Version;
	}

	public void setVersion(String version) {
		Version = version;
	}

	public Integer gethRet() {
		return hRet;
	}

	public void sethRet(Integer hRet) {
		this.hRet = hRet;
	}
}
