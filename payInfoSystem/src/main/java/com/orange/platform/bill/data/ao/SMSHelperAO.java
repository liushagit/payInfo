/**
 * weimiBillSystem
 */
package com.orange.platform.bill.data.ao;

import java.util.ArrayList;
import java.util.List;

import com.orange.platform.bill.common.domain.SMSContent;
import com.orange.platform.bill.common.domain.SMSIndex;
import com.orange.platform.bill.common.utils.ConstantDefine;
import com.payinfo.net.cached.MemcachedResource;

/**
 * @author weimiplayer.com
 *
 * 2012年11月2日
 */
public class SMSHelperAO extends BaseAO {
	public static final String SMS_PREFIX = "sms_";
	/**
	 * 获取短信索引信息
	 */
	public SMSIndex getSMSIndex(String appId, String channel, String province) {
		String key = createIndex0(appId, channel, province);
		SMSIndex sms = (SMSIndex) MemcachedResource.get(key);
		if (sms != null) return sms;
		
		List<SMSIndex> indexs = smsDAO.querySMSIndex(appId);
		sms = adaptSMSIndex(indexs, channel, province);
		
		if (sms != null) MemcachedResource.save(key, sms);
		return sms;
	}
	
	private SMSIndex adaptSMSIndex(List<SMSIndex> siList, String channel, String province) {
		List<SMSIndex> temp0 = new ArrayList<SMSIndex>();
		List<SMSIndex> temp1 = new ArrayList<SMSIndex>();
		// 判断渠道号是否包含
		for(SMSIndex si : siList) {
			boolean isContain0 = si.getChannel().contains(channel);
			if (isContain0) temp0.add(si);
			
			
			boolean isContain1 = si.getChannel().contains(ConstantDefine.CHANNEL_DEFAULT);
			if (isContain1) temp1.add(si);
		}

		// 判断省份是否包含
		if (!temp0.isEmpty()) {
			for (SMSIndex si : temp0) {
				boolean isContain = si.getProvince().contains(province);
				if (isContain) return si;
			}
			
			for (SMSIndex si : temp0) {
				boolean isContain = si.getProvince().contains(ConstantDefine.PROVINCE_DEFAULT);
				if (isContain) return si;
			}
		}
		
		if (!temp1.isEmpty()) {
			for (SMSIndex si : temp1) {
				boolean isContain = si.getProvince().contains(province);
				if (isContain) return si;
			}
			
			for (SMSIndex si : temp1) {
				boolean isContain = si.getProvince().contains(ConstantDefine.PROVINCE_DEFAULT);
				if (isContain) return si;
			}
		}
		
		return null;
	}
	
	/**
	 * 获取短信内容信息
	 */
	public SMSContent getSMSContent(int smsId) {
		String key = createIndex1(smsId);
		SMSContent info = (SMSContent) MemcachedResource.get(key);
		if (info != null) return info;
		
		info = smsDAO.querySMSContent(smsId);
		if (info != null) MemcachedResource.save(key, info);
		return info;
	}
	
	
	public String createIndex0(String appId, String channel, String province) {
		StringBuilder params = new StringBuilder();
		params.append(SMS_PREFIX).append(appId).append("_")
			.append(channel).append("_").append(province);
		return params.toString();
	}
	
	public String createIndex1(int smsId) {
		StringBuilder params = new StringBuilder();
		params.append(SMS_PREFIX).append("id_")
			.append(smsId);
		return params.toString();
	}
}
