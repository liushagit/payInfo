/**
 * weimiBillCommon
 */
package com.orange.platform.bill.common.domain;

import java.io.Serializable;

/**
 * @author weimiplayer.com
 *
 *         2012年11月2日
 */
public class SMSContent implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	private String smsContent;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}
}
