/**
 * weimiBillSystem
 */
package com.orange.platform.bill.data.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.orange.platform.bill.common.domain.FeeCommonInfo;
import com.orange.platform.bill.common.domain.FeeDY;
import com.orange.platform.bill.common.domain.FeeHJMM;
import com.orange.platform.bill.common.domain.FeeHZZW;
import com.orange.platform.bill.common.domain.FeeJQF;
import com.orange.platform.bill.common.domain.FeeLTDX;
import com.orange.platform.bill.common.domain.FeeLYF;
import com.orange.platform.bill.common.domain.FeeMRKJ;
import com.orange.platform.bill.common.domain.FeeRZD;
import com.orange.platform.bill.common.domain.FeeSKYJ;
import com.orange.platform.bill.common.domain.FeeTAX;
import com.orange.platform.bill.common.domain.FeeWB;
import com.orange.platform.bill.common.domain.FeeWQ;
import com.orange.platform.bill.common.domain.FeeWSY;
import com.orange.platform.bill.common.domain.FeeXSZSDT;
import com.orange.platform.bill.common.domain.FeeYS;
import com.orange.platform.bill.common.domain.FeeYXWX;
import com.orange.platform.bill.common.domain.FeeZSYYZ;
import com.orange.platform.bill.common.domain.FeeZW;
import com.orange.platform.bill.common.domain.FeeZYHD;
import com.orange.platform.bill.common.domain.ZYHDInfo;
import com.orange.platform.bill.common.domain.mm.FeeMM;
import com.payinfo.net.database.ConnectionResource;
import com.payinfo.net.database.IJuiceDBHandler;

/**
 * @author weimiplayer.com
 *
 * 2012年11月3日
 */
public class FeeHelperDAO extends ConnectionResource {
	private static final IJuiceDBHandler<FeeCommonInfo> HANDLER_FeeCOM = new IJuiceDBHandler<FeeCommonInfo>() {
		@Override
		public FeeCommonInfo handler(ResultSet rs) throws SQLException {
			FeeCommonInfo info = new FeeCommonInfo();
			info.setId(rs.getString("id"));
			return info;
		}
	};
	
	// =============================明日空间支付信息===============
	private static final IJuiceDBHandler<FeeMRKJ> HANDLER_FeeMRKJ = new IJuiceDBHandler<FeeMRKJ>() {
		@Override
		public FeeMRKJ handler(ResultSet rs) throws SQLException {
			FeeMRKJ info = new FeeMRKJ();
			info.setLinkId(rs.getString("link_id"));
			return info;
		}
	};
	
	public void addFeeMRKJ(FeeMRKJ info) {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into t_fee_mrkj(mobile, link_id, result, srvcode, feetype, fee, send_date, msg,")
			.append("create_time, update_time) values(?,?,?,?,?,?,?,?,now(),now())");
		saveOrUpdate(sql.toString(), info.getMobile(), info.getLinkId(), info.getResult(), info.getSrvcode(),
				info.getFeeType(), info.getFee(), info.getSendDate(), info.getMsg());
	}
	
	
	public FeeMRKJ queryFeeMRKJ(String linkId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from t_fee_mrkj where link_id=?");
		return queryForObject(sql.toString(), HANDLER_FeeMRKJ, linkId);
	}
	
	// =============================智玩支付信息===============
	private static final IJuiceDBHandler<FeeZW> HANDLER_FeeZW = new IJuiceDBHandler<FeeZW>() {
		@Override
		public FeeZW handler(ResultSet rs) throws SQLException {
			FeeZW info = new FeeZW();
			info.setCpId(rs.getString("cp_id"));
			info.setProductId(rs.getString("product_id"));
			info.setOrderNo(rs.getString("order_no"));
			info.setCpRemark(rs.getString("cp_remark"));
			return info;
		}
	};
	
	public void addFeeZW(FeeZW info) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"insert into t_fee_zw(cp_id, product_id, order_no, fee_code, fee, cp_remark, ")
				.append("app_id, channel, prop_id, result_code, create_time, update_time) values(")
				.append("?,?,?,?,?,?,?,?,?,?,now(),now())");
		saveOrUpdate(sql.toString(), info.getCpId(), info.getProductId(),
				info.getOrderNo(), info.getFeeCode(), info.getFee(),
				info.getCpRemark(), info.getAppId(), info.getChannel(),
				info.getPropId(), info.getResultCode());
	}

	public FeeZW queryFeeZW(String orderNo) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from t_fee_zw where order_no=?");
		return queryForObject(sql.toString(), HANDLER_FeeZW, orderNo);
	}
	
	// =============================宜搜支付信息===============
	private static final IJuiceDBHandler<FeeYS> HANDLER_FeeYS = new IJuiceDBHandler<FeeYS>() {
		@Override
		public FeeYS handler(ResultSet rs) throws SQLException {
			FeeYS info = new FeeYS();
			info.setOrderId(rs.getString("order_id"));
			return info;
		}
	};
	
	// =============================掌游互动支付信息===============
	private static final IJuiceDBHandler<FeeZYHD> HANDLER_FeeZYHD = new IJuiceDBHandler<FeeZYHD>() {
		@Override
		public FeeZYHD handler(ResultSet rs) throws SQLException {
			FeeZYHD info = new FeeZYHD();
			info.setLinkid(rs.getString("link_id"));
			return info;
		}
	};
	
	private static final IJuiceDBHandler<ZYHDInfo> HANDLER_FeeZYHD_INFO = new IJuiceDBHandler<ZYHDInfo>() {
		@Override
		public ZYHDInfo handler(ResultSet rs) throws SQLException {
			ZYHDInfo info = new ZYHDInfo();
			info.setId(rs.getInt("id"));
			info.setExtData(rs.getString("ext_data"));
			return info;
		}
	};
	
	public void addFeeYS(FeeYS info) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"insert into t_fee_ys(order_id, transe_id, ext_data, fee_mode, fee_id, fee,")
				.append("paidfee, app_id, channel, prop_id, create_time, update_time) values(")
				.append("?,?,?,?,?,?,?,?,?,?,now(),now())");
		saveOrUpdate(sql.toString(), info.getOrderId(), info.getTranseId(),
				info.getExtData(), info.getFeeMode(), info.getFeeId(),
				info.getFee(), info.getPaidFee(), info.getAppId(),
				info.getChannel(), info.getPropId());
	}

	public FeeYS queryFeeYS(String orderId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from t_fee_ys where order_id=?");
		return queryForObject(sql.toString(), HANDLER_FeeYS, orderId);
	}
	
	// =============================朗天支付信息===============
	private static final IJuiceDBHandler<FeeLTDX> HANDLER_FeeLTDX = new IJuiceDBHandler<FeeLTDX>() {
		@Override
		public FeeLTDX handler(ResultSet rs) throws SQLException {
			FeeLTDX info = new FeeLTDX();
			info.setLinkId(rs.getString("link_id"));
			return info;
		}
	};
	
	public void addFeeLTDX(FeeLTDX info) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"insert into t_fee_ltdx(link_id, msg_id, mobile, content, dest_addr,")
				.append("result, fee, app_id, channel, prop_id, create_time, update_time) ")
				.append("values(?,?,?,?,?,?,?,?,?,?,now(),now())");
		saveOrUpdate(sql.toString(), info.getLinkId(), info.getMsgId(),
				info.getMobile(), info.getContent(), info.getDestAddr(),
				info.getResult(), info.getFee(), info.getAppId(),
				info.getChannel(), info.getPropId());
	}
	
	public FeeLTDX queryFeeLTDX(String linkId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from t_fee_ltdx where link_id=?");
		return queryForObject(sql.toString(), HANDLER_FeeLTDX, linkId);
	}
	
	// =============================恒巨支付信息===============
	public void addFeeHJMM(FeeHJMM info) {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into t_fee_hjmm(seq_id, src_addr, dest_addr, msg, link_id,")
				.append("fee, cp_remark, app_id, channel, prop_id, create_time, update_time) ")
				.append("values(?,?,?,?,?,?,?,?,?,?,now(),now())");
		saveOrUpdate(sql.toString(), info.getSeqId(), info.getSrcAddr(), info.getDestAddr(),
				info.getMsg(), info.getLinkId(), info.getFee(), info.getCpRemark(),
				info.getAppId(), info.getChannel(), info.getPropId());
	}
	
	// =============================沃勤支付信息===============
	public void addFeeWQ(FeeWQ info) {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into t_fee_wq(mch_no, imsi, ext_data, app_id, channel, prop_id, fee, create_time, update_time) ");
		sql.append(" values(?, ?, ?, ?, ?, ?, ?, now(), now())");
		saveOrUpdate(sql.toString(), info.getMchNo(), info.getImsi(),
				info.getExtData(), info.getAppId(), info.getChannel(),
				info.getPropId(), info.getFee());
	}
	
	
	// =============================胜峰远景支付信息===============
	public static IJuiceDBHandler<FeeSKYJ> HANDLER_FeeSKYJ = new IJuiceDBHandler<FeeSKYJ>() {
		@Override
		public FeeSKYJ handler(ResultSet rs) throws SQLException {
			FeeSKYJ info = new FeeSKYJ();
			info.setMchNo(rs.getString("mch_no"));
			return info;
		}
	};
	
	public void addFeeSKYJ(FeeSKYJ info) {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into t_fee_sfyj(mch_no, phone, order_id, app_id, channel, prop_id, fee, mobile_type, create_time, update_time) ");
		sql.append(" values(?, ?, ?, ?, ?, ?, ?, ?, now(), now())");
		saveOrUpdate(sql.toString(), info.getMchNo(), info.getPhone(),
				info.getOrderId(), info.getAppId(), info.getChannel(),
				info.getPropId(), info.getFee(), info.getMobileType());
	}
	
	public FeeSKYJ queryFeeSKYJ(String mchNO) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from t_fee_sfyj where mch_no=?");
		return queryForObject(sql.toString(), HANDLER_FeeSKYJ, mchNO);
	}
	
	
	// =============================乐易付支付信息===============
	private static final IJuiceDBHandler<FeeLYF> HANDLER_FeeLYF = new IJuiceDBHandler<FeeLYF>() {
		@Override
		public FeeLYF handler(ResultSet rs) throws SQLException {
			FeeLYF info = new FeeLYF();
			info.setOrderNO(rs.getString("order_no"));
			return info;
		}
	};
	
	public void addFeeLYF(FeeLYF info) {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into t_fee_lyf(order_no, fee, pay_order_step, price, order_status,")
				.append("error, cp_remark, app_id, channel, prop_id, create_time, update_time) ")
				.append("values(?,?,?,?,?,?,?,?,?,?,now(),now())");
		saveOrUpdate(sql.toString(), info.getOrderNO(), info.getFee(), info.getStep(),
				info.getPrice(), info.getOrderStatus(), info.getError(), info.getCpRemark(),
				info.getAppId(), info.getChannel(), info.getPropId());
	}
		
	public FeeLYF queryFeeLYF(String orderNO, int step) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from t_fee_lyf where order_no=? and pay_order_step=?");
		return queryForObject(sql.toString(), HANDLER_FeeLYF, orderNO, step);
	}
	
	// =============================威搜游===============
	private static final IJuiceDBHandler<FeeWSY> HANDLER_FeeWSY = new IJuiceDBHandler<FeeWSY>() {
		@Override
		public FeeWSY handler(ResultSet rs) throws SQLException {
			FeeWSY info = new FeeWSY();
			info.setLinkId(rs.getString("link_id"));
			return info;
		}
	};
	
	public void addFeeWSY(FeeWSY info) {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into t_fee_wsy(app_id, channel_id, link_id, mno, price, flag, rtime, create_time, update_time) values")
			.append("(?,?,?,?,?,?,?,now(),now())");
		saveOrUpdate(sql.toString(), info.getAppId(), info.getChannelId(), info.getLinkId(),
				info.getMno(), info.getPrice(), info.getFlag(), info.getRtime());
	}
	
	public FeeWSY queryFeeWSY(String linkId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from t_fee_wsy where link_id=?");
		return queryForObject(sql.toString(), HANDLER_FeeWSY, linkId);
	}
	
	// =============================杭州掌维支付信息===============
	private static final IJuiceDBHandler<FeeHZZW> HANDLER_FeeHZZW = new IJuiceDBHandler<FeeHZZW>() {
		@Override
		public FeeHZZW handler(ResultSet rs) throws SQLException {
			FeeHZZW info = new FeeHZZW();
			info.setOrderNo(rs.getString("order_id"));
			return info;
		}
	};
	
	public void addFeeHZZW(FeeHZZW info) {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into t_fee_hzzw(cp_id, order_no, fee_code, app_id, channel, prop_id, fee, cm, result_code, ");
		sql.append("create_time, update_time, notify_id, trade_no, timestamp, uid, account, ext_data) ");
		sql.append(" values(?, ?, ?, ?, ?, ?, ?, ?, ?, now(), now(), ?, ?, ?, ?, ?, ? )");
		saveOrUpdate(sql.toString(), info.getCpId(), info.getOrderNo(), info.getFeeCode(),info.getAppId(), info.getChannel(), 
				info.getPropId(), info.getFee(),info.getCm(), info.getResultCode(), info.getNotifyId(), info.getTradeNo(),
				info.getTimestamp(), info.getUid(), info.getAccount(), info.getExtdata());
	}
	
	public FeeHZZW queryFeeHZZW(String orderNo) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from t_fee_hzzw where order_no=?");
		return queryForObject(sql.toString(), HANDLER_FeeHZZW, orderNo);
	}
	// =============================锐之道支付信息===============
	private static final IJuiceDBHandler<FeeRZD> HANDLER_FeeRZD = new IJuiceDBHandler<FeeRZD>() {
		@Override
		public FeeRZD handler(ResultSet rs) throws SQLException {
			FeeRZD info = new FeeRZD();
			info.setOrderNO(rs.getString("order_no"));
			info.setAmount(rs.getInt("amount"));
			info.setImsi(rs.getString("imsi"));
			info.setStatus(rs.getInt("status"));
			info.setExt(rs.getString("ext"));
			info.setType(rs.getString("type"));
			info.setAppId(rs.getString("app_id"));
			info.setChannel(rs.getString("channel"));
			info.setPropId(rs.getString("prop_id"));
			return info;
		}
	};
	
	public void addFeeRZD(FeeRZD info) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"insert into t_fee_rzd(order_no, amount, imsi, status, ext, type, ")
				.append("app_id, channel, prop_id, create_time, update_time) values(")
				.append("?,?,?,?,?,?,?,?,?,now(),now())");
		saveOrUpdate(sql.toString(), info.getOrderNO(), info.getAmount(), info.getImsi(),
				info.getStatus(), info.getExt(), info.getType(),
				info.getAppId(), info.getChannel(), info.getPropId());
	}

	public FeeRZD queryFeeRZD(String orderNo) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from t_fee_rzd where order_no=?");
		return queryForObject(sql.toString(), HANDLER_FeeRZD, orderNo);
	}
	
	
	
	// =============================特安訊支付信息===============
	private static final IJuiceDBHandler<FeeTAX> HANDLER_FeeTAX = new IJuiceDBHandler<FeeTAX>() {
		@Override
		public FeeTAX handler(ResultSet rs) throws SQLException {
			FeeTAX info = new FeeTAX();
			info.setLinkId(rs.getString("link_id"));
			return info;
		}
	};
	
	public void addFeeTAX(FeeTAX info) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"insert into t_fee_tax(link_id, momsg, fee, province, ext, ")
				.append("app_id, channel, prop_id, create_time, update_time) values(")
				.append("?,?,?,?,?,?,?,?,now(),now())");
		saveOrUpdate(sql.toString(), info.getLinkId(), info.getMomsg(), info.getFee(),
				info.getProvince(), info.getExt(),
				info.getAppId(), info.getChannel(), info.getPropId());
	}
	
	public FeeTAX queryFeeTAX(String linkId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from t_fee_tax where link_id=?");
		return queryForObject(sql.toString(), HANDLER_FeeTAX, linkId);
	}
	
	// =============================特安訊(万贝)电信支付信息===============
	private static final IJuiceDBHandler<FeeWB> HANDLER_FeeWB = new IJuiceDBHandler<FeeWB>() {
		@Override
		public FeeWB handler(ResultSet rs) throws SQLException {
			FeeWB info = new FeeWB();
			info.setLinkId(rs.getString("link_id"));
			return info;
		}
	};
	
	public void addFeeWB(FeeWB info) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"insert into t_fee_wb(mobile,spnumber,link_id, momsg, fee, province, ext, ")
				.append("app_id, channel, prop_id, create_time, update_time) values(")
				.append("?,?,?,?,?,?,?,?,?,?,now(),now())");
		saveOrUpdate(sql.toString(), info.getMobile(), info.getSpnumber(),info.getLinkId(), 
				info.getMomsg(), info.getFee(),info.getProvince(), info.getExt(),
				info.getAppId(), info.getChannel(), info.getPropId());
	}

	public FeeWB queryFeeWB(String linkId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from t_fee_wb where link_id=?");
		return queryForObject(sql.toString(), HANDLER_FeeWB, linkId);
	}
	
	
	
	// =============================易讯无限支付信息===============
	private static final IJuiceDBHandler<FeeYXWX> HANDLER_FeeYXWX = new IJuiceDBHandler<FeeYXWX>() {
		@Override
		public FeeYXWX handler(ResultSet rs) throws SQLException {
			FeeYXWX info = new FeeYXWX();
			info.setOrderId(rs.getString("order_id"));
			return info;
		}
	};
	
	public void addFeeYXWX(FeeYXWX info) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"insert into t_fee_yxwx(order_id, order_date, order_status, pay_type, pay_type_name, imsi, ")
				.append("ext_data, fee, app_id, channel, prop_id, create_time, update_time) values(")
				.append("?,?,?,?,?,?,?,?,?,?,?,now(),now())");
		saveOrUpdate(sql.toString(), info.getOrderId(), info.getOrderDate(), info.getOrderStatus(),
				info.getPayType(), info.getPayTypeName(), info.getImsi(),
				info.getExtData(), info.getFee(), info.getAppId(), info.getChannel(),
				info.getPropId());
	}

	public FeeYXWX queryFeeYXWX(String orderId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from t_fee_yxwx where order_id=?");
		return queryForObject(sql.toString(), HANDLER_FeeYXWX, orderId);
	}
	
	// =============================电盈支付信息===============
	private static final IJuiceDBHandler<FeeDY> HANDLER_FeeDY = new IJuiceDBHandler<FeeDY>() {
		@Override
		public FeeDY handler(ResultSet rs) throws SQLException {
			FeeDY info = new FeeDY();
			info.setLinkId(rs.getString("link_id"));
			return info;
		}
	};
	
	public void addFeeDY(FeeDY info) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"insert into t_fee_dy(link_id, result, fee, ext, ")
				.append("app_id, channel, prop_id, create_time, update_time) values(")
				.append("?,?,?,?,?,?,?,now(),now())");
		saveOrUpdate(sql.toString(), info.getLinkId(), info.getResult(), info.getFee(), 
				info.getExtData(), info.getAppId(), info.getChannel(),
				info.getPropId());
	}

	public FeeDY queryFeeDY(String linkId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from t_fee_dy where link_id=?");
		return queryForObject(sql.toString(), HANDLER_FeeDY, linkId);
	}
	
	
	// =============================中国手游广州盈正支付信息===============
	private static final IJuiceDBHandler<FeeZSYYZ> HANDLER_FeeZSYYZ = new IJuiceDBHandler<FeeZSYYZ>() {
		@Override
		public FeeZSYYZ handler(ResultSet rs) throws SQLException {
			FeeZSYYZ info = new FeeZSYYZ();
			info.setOrderId(rs.getString("order_id"));
			return info;
		}
	};
	
	public void addFeeZSYYZ(FeeZSYYZ info) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"insert into t_fee_zsyyz(order_id, service_id, province, fee, ext, result,")
				.append("app_id, channel, prop_id, create_time, update_time) values(")
				.append("?,?,?,?,?,?,?,?,?,now(),now())");
		saveOrUpdate(sql.toString(), info.getOrderId(), info.getServiceId(), info.getProvince(),
				info.getPrice(), info.getExt(), info.getResult(),
				info.getAppId(), info.getChannel(), info.getPropId());
	}

	public FeeZSYYZ queryFeeZSYYZ(String orderId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from t_fee_zsyyz where order_id=?");
		return queryForObject(sql.toString(), HANDLER_FeeZSYYZ, orderId);
	}
	
	
	// =============================JQF支付信息===============
	private static final IJuiceDBHandler<FeeJQF> HANDLER_FeeJQF = new IJuiceDBHandler<FeeJQF>() {
		@Override
		public FeeJQF handler(ResultSet rs) throws SQLException {
			FeeJQF info = new FeeJQF();
			info.setLinkId(rs.getString("link_id"));
			return info;
		}
	};
	
	public void addFeeJQF(FeeJQF info) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"insert into t_fee_jqf(link_id, spnumber, ext, result,")
				.append("app_id, channel, prop_id, fee, create_time, update_time) values(")
				.append("?,?,?,?,?,?,?,?,now(),now())");
		saveOrUpdate(sql.toString(), info.getLinkId(), info.getSpnumber(), info.getExt(),
				info.getResult(), info.getAppId(), info.getChannel(), info.getPropId(),
				info.getFee());
	}

	public FeeJQF queryFeeJQF(String linkId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from t_fee_jqf where link_id=?");
		return queryForObject(sql.toString(), HANDLER_FeeJQF, linkId);
	}
	
	
	// =============================深圳斯达通支付信息===============
	private static final IJuiceDBHandler<FeeCommonInfo> HANDLER_FeeSZSDT = new IJuiceDBHandler<FeeCommonInfo>() {
		@Override
		public FeeCommonInfo handler(ResultSet rs) throws SQLException {
			FeeCommonInfo info = new FeeCommonInfo();
			info.setOrderId(rs.getString("order_id"));
			return info;
		}
	};
	
	public void addFeeSZSDT(FeeCommonInfo info) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"insert into t_fee_szsdt(fee_code, trade_id, port, mobile, fee, app_id, prop_id, channel_id,")
				.append("order_id, channel, action_time, ext_data, weimi_order_id, create_time, update_time) values(")
				.append("?,?,?,?,?,?,?,?,?,?,?,?,?,now(),now())");
		saveOrUpdate(sql.toString(), info.getFeeCode(), info.getTradeId(), info.getPort(), info.getMobile(),info.getFee(),
				info.getAppId(), info.getPropId(), info.getChannelId(),info.getOrderId(), info.getChannel(), info.getActiontime(), 
				info.getExtdata(), info.getweimiOrderId());
	}
	
	public FeeCommonInfo queryFeeSZSDT(String orderId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from t_fee_szsdt where order_id=?");
		return queryForObject(sql.toString(), HANDLER_FeeSZSDT, orderId);
	}
	
	
	// =============================北京指游支付信息===============
	public void addFeeBJZY(FeeCommonInfo info) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"insert into t_fee_bjzy(order_id, province_id, province_name, payment_type, partner_type, app_name, actiontime,")
				.append("result, link_id, fee, ext_data, app_id, channel, prop_id, create_time, update_time) values(")
				.append("?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),now())");
		saveOrUpdate(sql.toString(), info.getOrderId(), info.getProvinceId(), info.getProvinceName(), info.getPaymentType(),
				info.getPartnerType(), info.getAppName(), info.getActiontime(), info.getResultCode(), info.getweimiOrderId(),
				info.getFee(), info.getExtdata(), info.getAppId(), info.getChannel(), info.getPropId());
	}
	
	public FeeCommonInfo queryFeeBJZY(String orderId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from t_fee_bjzy where order_id=?");
		return queryForObject(sql.toString(), HANDLER_FeeCOM, orderId);
	}
	
	
	// =============================灵光互动支付信息===============
	public void addFeeLGHD(FeeCommonInfo info) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"insert into t_fee_lghd(order_id, mobile, content, actiontime,")
				.append("result, link_id, fee, ext_data, app_id, channel, prop_id, create_time, update_time) values(")
				.append("?,?,?,?,?,?,?,?,?,?,?,now(),now())");
		saveOrUpdate(sql.toString(), info.getOrderId(), info.getMobile(), info.getContent(), info.getActiontime(), 
				info.getResultCode(), info.getweimiOrderId(),info.getFee(), info.getExtdata(), info.getAppId(), 
				info.getChannel(), info.getPropId());
	}
	
	public FeeCommonInfo queryFeeLGHD(String orderId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from t_fee_lghd where order_id=?");
		return queryForObject(sql.toString(), HANDLER_FeeCOM, orderId);
	}
	
	// =============================新深圳斯达通支付信息===============
	public void addFeeXSZSDT(FeeXSZSDT info) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"insert into t_fee_xszsdt(order_id, action_time, trade_id, ")
				.append("result, link_id, fee, ext_data, app_id, channel, prop_id, create_time, update_time) ")
				.append("values(?,?,?,?,?,?,?,?,?,?,now(),now())");
		saveOrUpdate(sql.toString(), info.getOrderId(), info.getActionTime(), info.getTradeId(),
				info.getResult(), info.getLinkId(), info.getPrice(), info.getExt(), info.getAppId(), 
				info.getChannel(), info.getPropId());
	}
	
	public FeeCommonInfo queryFeeXSZSDT(String orderId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from t_fee_xszsdt where order_id=?");
		return queryForObject(sql.toString(), HANDLER_FeeCOM, orderId);
	}
	
	// =============================广州游发支付信息===============
	public void addFeeGZYF(FeeCommonInfo info) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"insert into t_fee_gzyf(fee_code, trade_id, port, mobile, fee, app_id, prop_id, channel_id,")
				.append("order_id, channel, action_time, ext_data, weimi_order_id, create_time, update_time) values(")
				.append("?,?,?,?,?,?,?,?,?,?,?,?,?,now(),now())");
		saveOrUpdate(sql.toString(), info.getFeeCode(), info.getTradeId(), info.getPort(), info.getMobile(),info.getFee(),
				info.getAppId(), info.getPropId(), info.getChannelId(),info.getOrderId(), info.getChannel(), info.getActiontime(), 
				info.getExtdata(), info.getweimiOrderId());
	}
	
	public FeeCommonInfo queryFeeGZYF(String orderId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from t_fee_gzyf where order_id=?");
		return queryForObject(sql.toString(), HANDLER_FeeCOM, orderId);
	}
	
	// =============================YFMP支付信息===============
	public void addFeeYFMP(FeeMM info) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"insert into t_fee_yfmp(order_id, action_time, trade_id, ")
				.append("result, link_id, fee, ext_data, app_id, channel, prop_id, create_time, update_time) ")
				.append("values(?,?,?,?,?,?,?,?,?,?,now(),now())");
		saveOrUpdate(sql.toString(), info.getOrderId(), info.getActionTime(), info.getTradeId(),
				info.getResult(), info.getLinkId(), info.getPrice(), info.getExt(), info.getAppId(), 
				info.getChannel(), info.getPropId());
	}
	
	public FeeCommonInfo queryFeeYFMP(String orderId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from t_fee_yfmp where order_id=?");
		return queryForObject(sql.toString(), HANDLER_FeeCOM, orderId);
	}
	
	// =============================万贝移动支付信息===============
	public void addFeeWBYD(FeeWB info) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"insert into t_fee_wbyd(mobile,spnumber,link_id, momsg, fee, ext, ")
				.append("app_id, channel, prop_id, create_time, update_time) values(")
				.append("?,?,?,?,?,?,?,?,?,now(),now())");
		saveOrUpdate(sql.toString(), info.getMobile(), info.getSpnumber(),info.getLinkId(), 
				info.getMomsg(), info.getFee(), info.getExt(),
				info.getAppId(), info.getChannel(), info.getPropId());
	}

	public FeeWB queryFeeWBYD(String linkId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from t_fee_wbyd where link_id=?");
		return queryForObject(sql.toString(), HANDLER_FeeWB, linkId);
	}
	
	// =============================DTYD支付信息===============
	public void addFeeDTYD(FeeCommonInfo info) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"insert into t_fee_dtyd(order_id,mobile,description, result, fee, ext, ")
				.append("app_id, channel, prop_id, create_time, update_time) values(")
				.append("?,?,?,?,?,?,?,?,?,now(),now())");
		saveOrUpdate(sql.toString(), info.getOrderId(), info.getMobile(),info.getDescription(), 
				info.getResultCode(), info.getFee(), info.getExtdata(),
				info.getAppId(), info.getChannel(), info.getPropId());
	}
	
	public FeeCommonInfo queryFeeDTYD(String orderId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from t_fee_dtyd where order_id=?");
		return queryForObject(sql.toString(), HANDLER_FeeCOM, orderId);
	}
	
	// =============================掌游互动支付信息===============
	
	public void addFeeZYHD(FeeZYHD info) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"insert into t_fee_zyhd(link_id,mobile,longnum, consume_code, fee, hret, ")
				.append("status, cpparam, app_id, channel_id, prop_id,create_time,update_time) values(")
				.append("?,?,?,?,?,?,?,?,?,?,?,now(),now())");
		saveOrUpdate(sql.toString(), info.getLinkid(),info.getMobile(),info.getLongnum(),
				info.getConsumeCode(),info.getFee(),info.getHret(),info.getStatus(),
				info.getCpparam(),info.getAppId(),info.getChannelId(),info.getPropId());
	}
	public FeeZYHD queryFeeZYHD(String orderId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from t_fee_zyhd where link_id=?");
		return queryForObject(sql.toString(), HANDLER_FeeZYHD, orderId);
	}
	
	public ZYHDInfo queryFeeZYHDMM(int id) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from t_zyhd_info where id=?");
		ZYHDInfo info = queryForObject(sql.toString(), HANDLER_FeeZYHD_INFO, id);
		if(info != null){
			sql = new StringBuilder();
			sql.append("update t_zyhd_info set update_time=now() where id=?");
			saveOrUpdate(sql.toString(), id);
		}
		return info;
	}
	
	
}
