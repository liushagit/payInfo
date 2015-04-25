/**
 * weimiBillCommon
 */
package com.orange.platform.bill.common.domain;

import java.io.Serializable;

/**
 * @author weimiplayer.com
 *
 *         2012年12月21日
 */
public class FeeJQF implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String linkId;
	private String spnumber;
	private String ext;
	private String mobile;
	private String result;
	private String appId;
	private String channel;
	private String propId;
	private int fee;

	public String getLinkId() {
		return linkId;
	}

	public void setLinkId(String linkId) {
		this.linkId = linkId;
	}

	public String getSpnumber() {
		return spnumber;
	}

	public void setSpnumber(String spnumber) {
		this.spnumber = spnumber;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
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

	public int getFee() {
		return fee;
	}

	public void setFee(int fee) {
		this.fee = fee;
	}
}
