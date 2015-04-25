/**
 * weimiBillSystem
 */
package com.orange.platform.bill.data.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.orange.platform.bill.common.domain.CodeInfo;
import com.orange.platform.bill.common.domain.GameMatchInfo;
import com.orange.platform.bill.common.domain.InitInfo;
import com.orange.platform.bill.common.domain.PayInfo;
import com.orange.platform.bill.common.domain.SDKKey;
import com.orange.platform.bill.common.domain.mm.MMInitInfo;
import com.payinfo.net.database.ConnectionResource;
import com.payinfo.net.database.IJuiceDBHandler;

/**
 * @author weimiplayer.com
 *
 * 2012年11月2日
 */
public class BillBasicDAO extends ConnectionResource {
	//==============================激活================================
	private static IJuiceDBHandler<InitInfo> HANDLER_INIT = new IJuiceDBHandler<InitInfo>() {
		@Override
		public InitInfo handler(ResultSet rs) throws SQLException {
			InitInfo info = new InitInfo();
			info.setImsi(rs.getString("imsi"));
			info.setImei(rs.getString("imei"));
			return info;
		}
	};
	
	public void addInitInfo(InitInfo info) {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into t_init_info(imsi, imei, app_id, channel,")
			.append("mobile_type, ip, province, app_version, sdk_version, create_time, update_time) ")
			.append(" values(?,?,?,?,?,?,?,?,?,now(),now())");
		saveOrUpdate(sql.toString(), info.getImsi(), info.getImei(), info.getAppId(), info.getChannel(),
				info.getMobileType(), info.getIp(), info.getProvince(), info.getAppVersion(), info.getSdkVersion());
	}
	
	public InitInfo queryInitInfo(String imei, String appId, String channel) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from t_init_info where app_id=? and channel=? and imei=?");
		return queryForObject(sql.toString(), HANDLER_INIT, appId, channel, imei);
	}
	
	public InitInfo queryInitInfo1(String imei, String appId, String channel) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from t_init_info_1 where app_id=? and channel=? and imei=?");
		return queryForObject(sql.toString(), HANDLER_INIT, appId, channel, imei);
	}
	
	//==============================付费================================
	private static IJuiceDBHandler<PayInfo> HANDLER_PAY = new IJuiceDBHandler<PayInfo>() {
		@Override
		public PayInfo handler(ResultSet rs) throws SQLException {
			PayInfo info = new PayInfo();
			info.setImsi(rs.getString("imsi"));
			info.setImei(rs.getString("imei"));
			return info;
		}
	};
	
	public void addPayInfo(PayInfo info) {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into t_pay_info(order_id, imei, imsi, mobile, ext, app_id, channel, prop_id, ")
			.append("fee, mobile_type, ip, province, pay_type, app_version, sdk_version, create_time, update_time) ")
			.append(" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),now())");
		saveOrUpdate(sql.toString(), info.getOrderId(), info.getImei(), info.getImsi(), info.getMobile(),
				info.getExt(), info.getAppId(), info.getChannel(), info.getPropId(), info.getFee(),
				info.getMobileType(), info.getIp(), info.getProvince(), info.getPayType(), 
				info.getAppVersion(), info.getSdkVersion());
	}
	
	public void addPayFee(PayInfo info) {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into t_pay_fee(order_id, imei, imsi, mobile, ext, app_id, channel, prop_id, ")
			.append("fee, mobile_type, ip, province, pay_type, app_version, sdk_version, create_time, update_time) ")
			.append(" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),now())");
		saveOrUpdate(sql.toString(), info.getOrderId(), info.getImei(), info.getImsi(), info.getMobile(),
				info.getExt(), info.getAppId(), info.getChannel(), info.getPropId(), info.getFee(),
				info.getMobileType(), info.getIp(), info.getProvince(), info.getPayType(), 
				info.getAppVersion(), info.getSdkVersion());
	}
	
	
	//==============================SDK关键配置信息================================
	private static IJuiceDBHandler<SDKKey> HANDLER_SDK_KEY = new IJuiceDBHandler<SDKKey>() {
		@Override
		public SDKKey handler(ResultSet rs) throws SQLException {
			SDKKey info = new SDKKey();
			info.setKey(rs.getString("id"));
			info.setValue(rs.getString("value"));
			return info;
		}
	};
	
	public SDKKey querySDKKey(String key) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from t_sdk_key where id=?");
		return queryForObject(sql.toString(), HANDLER_SDK_KEY, key);
	}
	
	//==============================获取MMapp_key================================
	private static IJuiceDBHandler<MMInitInfo> HANDLER_MMINITKEY_KEY = new IJuiceDBHandler<MMInitInfo>() {
		@Override
		public MMInitInfo handler(ResultSet rs) throws SQLException {
			MMInitInfo info = new MMInitInfo();
			info.setMmAppId(rs.getString("mm_app_id"));
			info.setMmAppKey(rs.getString("mm_app_key"));
			return info;
		}
	};
	
	public MMInitInfo queryMmInitNetCode(String appId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from t_mminit_conf where app_id=?");
		return queryForObject(sql.toString(), HANDLER_MMINITKEY_KEY, appId);
	}
	
	//==============================获取游戏配置信息================================
	private static IJuiceDBHandler<GameMatchInfo> HANDLER_GAMECONFKEY_KEY = new IJuiceDBHandler<GameMatchInfo>() {
		@Override
		public GameMatchInfo handler(ResultSet rs) throws SQLException {
			GameMatchInfo info = new GameMatchInfo();
			info.setAppName(rs.getString("app_name"));
			info.setPropName(rs.getString("prop_name"));
			return info;
		}
	};
	
	public GameMatchInfo queryGameConf(String appId, String propId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from t_game_conf where app_id=? and prop_id=?");
		return queryForObject(sql.toString(), HANDLER_GAMECONFKEY_KEY, appId,propId);
	}
	
	//============================获取计费代码==================================
	private static IJuiceDBHandler<CodeInfo> HANDLER_CODEINFO_KEY = new IJuiceDBHandler<CodeInfo>() {
		@Override
		public CodeInfo handler(ResultSet rs) throws SQLException {
			CodeInfo info = new CodeInfo();
			info.setPort(rs.getString("port"));
			info.setContent(rs.getString("content"));
			info.setProvider(rs.getString("provider"));
			info.setOrderNo(rs.getString("order_id"));
			info.setType(rs.getString("type"));
			info.setMustInitNetCode(rs.getString("must_init_net_code"));
			info.setKey(rs.getString("key"));
			return info;
		}
	};
	
	
	public CodeInfo queryCodeInfo(String appId,String channelId, String propId){
		StringBuilder sql = new StringBuilder();
		sql.append("select * from t_code_info where app_id=? and channel_id=? and prop_id=? and status=1");
		return queryForObject(sql.toString(), HANDLER_CODEINFO_KEY, appId,channelId,propId);
	}
}
