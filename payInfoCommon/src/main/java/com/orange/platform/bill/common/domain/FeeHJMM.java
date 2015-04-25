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
public class FeeHJMM implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String seqId;
	private String srcAddr;
	private String destAddr;
	private String msg;
	private String linkId;
	private int fee;
	private String cpRemark;
	private String appId;
	private String channel;
	private String propId;

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public String getSrcAddr() {
		return srcAddr;
	}

	public void setSrcAddr(String srcAddr) {
		this.srcAddr = srcAddr;
	}

	public String getDestAddr() {
		return destAddr;
	}

	public void setDestAddr(String destAddr) {
		this.destAddr = destAddr;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getLinkId() {
		return linkId;
	}

	public void setLinkId(String linkId) {
		this.linkId = linkId;
	}

	public int getFee() {
		return fee;
	}

	public void setFee(int fee) {
		this.fee = fee;
	}

	public String getCpRemark() {
		return cpRemark;
	}

	public void setCpRemark(String cpRemark) {
		this.cpRemark = cpRemark;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getPropId() {
		return propId;
	}

	public void setPropId(String propId) {
		this.propId = propId;
	}
}
