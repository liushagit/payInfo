/**
 * weimiBillCommon
 */
package com.orange.platform.bill.common.action;

/**
 * @author weimiplayer.com
 *
 * 2012年11月2日
 */
public interface SMSHelperAction {
	String querySMSVersion(String appId, String channel, String province);
	
	String querySMSContent(String appId, String channel, String province);
	
	int queryInterval(String appId, String channel, String province);
}
