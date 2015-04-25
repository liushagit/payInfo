/**
 * weimiBillSystem
 */
package com.orange.platform.bill.common.action.impl;

import com.orange.platform.bill.common.action.SMSHelperAction;
import com.orange.platform.bill.common.domain.SMSContent;
import com.orange.platform.bill.common.domain.SMSIndex;
import com.orange.platform.bill.data.Application;

/**
 * @author weimiplayer.com
 *
 * 2012年11月2日
 */
public class SMSHelperActionImpl extends Application implements SMSHelperAction {

	/* (non-Javadoc)
	 * @see com.orange.platform.bill.common.action.SMSHelperAction#querySMSVersion(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String querySMSVersion(String appId, String channel, String province) {
		SMSIndex sms = smsAO().getSMSIndex(appId, channel, province);
		
		return sms == null ? "0" : sms.getVersion();
	}

	/* (non-Javadoc)
	 * @see com.orange.platform.bill.common.action.SMSHelperAction#querySMSContent(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String querySMSContent(String appId, String channel, String province) {
		SMSIndex sms = smsAO().getSMSIndex(appId, channel, province);
		
		if (sms != null)  { 
			SMSContent info = smsAO().getSMSContent(sms.getSmsId());
			return info == null ? "error" : info.getSmsContent();
		}
		
		return "error";
	}

	@Override
	public int queryInterval(String appId, String channel, String province) {
		SMSIndex sms = smsAO().getSMSIndex(appId, channel, province);
		
		if (sms != null)  { 
			return sms.getInterval();
		}
		return 1000;
	}
}
