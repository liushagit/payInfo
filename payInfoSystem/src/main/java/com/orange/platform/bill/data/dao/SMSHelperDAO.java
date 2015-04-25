/**
 * weimiBillSystem
 */
package com.orange.platform.bill.data.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.orange.platform.bill.common.domain.SMSContent;
import com.orange.platform.bill.common.domain.SMSIndex;
import com.payinfo.net.database.ConnectionResource;
import com.payinfo.net.database.IJuiceDBHandler;

/**
 * @author weimiplayer.com
 *
 * 2012年11月2日
 */
public class SMSHelperDAO extends ConnectionResource {
	public static  IJuiceDBHandler<SMSIndex> HANDLER_SMS_INDEX = new IJuiceDBHandler<SMSIndex>() {
		@Override
		public SMSIndex handler(ResultSet rs) throws SQLException {
			SMSIndex info = new SMSIndex();
			info.setAppId(rs.getString("app_id"));
			info.setChannel(rs.getString("channel"));
			info.setProvince(rs.getString("province"));
			info.setSmsId(rs.getInt("sms_id"));
			info.setVersion(rs.getString("version"));
			info.setAppVersion(rs.getString("app_version"));
			info.setInterval(rs.getInt("interval"));
			return info;
		}
	};
	
	public List<SMSIndex> querySMSIndex(String appId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from t_sms_index where app_id=?");
		return queryForList(sql.toString(), HANDLER_SMS_INDEX, appId);
	}
	
	
	public SMSIndex getDefaultSMS(String appId, String channel) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from t_sms_index where app_id=? and channel=? and province='all'");
		return queryForObject(sql.toString(), HANDLER_SMS_INDEX, appId, channel);
	}
	
	
	//==============================================================
	public static  IJuiceDBHandler<SMSContent> HANDLER_SMS_INFO = new IJuiceDBHandler<SMSContent>() {
		@Override
		public SMSContent handler(ResultSet rs) throws SQLException {
			SMSContent info = new SMSContent();
			info.setId(rs.getInt("id"));
			info.setSmsContent(rs.getString("sms_content"));
			return info;
		}
	};
	
	public SMSContent querySMSContent(int smsId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from t_sms_content where id=?");
		return queryForObject(sql.toString(), HANDLER_SMS_INFO, smsId);
	}
}
