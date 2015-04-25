/**
 * weimiBillCommon
 */
package com.orange.platform.bill.common.domain;

import java.io.Serializable;

/**
 * @author weimiplayer.com
 *
 *         2012年11月3日
 */
public class FeeZYHD implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String mobile;
	private String longnum;
	private String consumeCode;
	private int fee;
	private String hret;
	private String status;
	private String linkid;
	private String cpparam;
	private String appId;
	private String channelId;
	private String propId;
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getLongnum() {
		return longnum;
	}
	public void setLongnum(String longnum) {
		this.longnum = longnum;
	}
	public String getConsumeCode() {
		return consumeCode;
	}
	public void setConsumeCode(String consumeCode) {
		this.consumeCode = consumeCode;
	}
	public int getFee() {
		return fee;
	}
	public void setFee(int fee) {
		this.fee = fee;
	}
	public String getHret() {
		return hret;
	}
	public void setHret(String hret) {
		this.hret = hret;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLinkid() {
		return linkid;
	}
	public void setLinkid(String linkid) {
		this.linkid = linkid;
	}
	public String getCpparam() {
		return cpparam;
	}
	public void setCpparam(String cpparam) {
		this.cpparam = cpparam;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getPropId() {
		return propId;
	}
	public void setPropId(String propId) {
		this.propId = propId;
	}
	
	
}
