/**
 * weimiBillSystem
 */
package com.orange.platform.bill.data.ao;

import org.apache.log4j.Logger;

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
import com.payinfo.net.cached.MemcachedResource;
import com.payinfo.net.log.LoggerFactory;

/**
 * @author weimiplayer.com
 *
 * 2012年11月3日
 */
public class FeeHelperAO extends BaseAO {
	private static Logger logger = LoggerFactory.getLogger(FeeHelperAO.class);
	
	public static final String PREFIX_FEEZW = "feezw_";
	public static final String PREFIX_FEEYS = "feeys_";
	public static final String PREFIX_FEELTDX = "feeltdx_";
	public static final String PREFIX_FEESKYJ = "feesfyj_";
	public static final String PREFIX_FEELYF = "feelyf_";
	public static final String PREFIX_FEEMRKJ = "feemrkj_";
	public static final String PREFIX_FEEWSY = "feewsy_";
	public static final String PREFIX_FEERZD = "feerzd_";
	public static final String PREFIX_FEETAX = "feetax_";
	public static final String PREFIX_FEEWB = "feewb_";
	public static final String PREFIX_FEEYXWX = "feeyxwx_";
	public static final String PREFIX_FEEDY = "feedy_";
	public static final String PREFIX_FEEZSYYZ = "feezsyyz_";
	public static final String PREFIX_FEEJQF = "feejqf_";
	public static final String PREFIX_FEESZSDT = "feeszsdt_";
	public static final String PREFIX_FEEHZZW = "feehzzw_";
	public static final String PREFIX_FEEBJZY = "feebjzy_";
	public static final String PREFIX_FEELGHD = "feelghd_";
	public static final String PREFIX_FEEXSZSDT = "feexszsdt_";
	public static final String PREFIX_FEEGZYF = "feegzyf_";
	public static final String PREFIX_FEEYFMP = "feeyfmp_";
	public static final String PREFIX_FEEWBYD = "feewbyd_";
	public static final String PREFIX_FEEDTYD = "feedtyd_";
	public static final String PREFIX_FEEZYHD = "feezyhd_";
	public static final String PREFIX_FEEZYHDMM = "feezyhdmm_";
	
	
	public void addFeeMRKJ(FeeMRKJ info) {
		FeeMRKJ exist = queryFeeMRKJ(info.getLinkId());
		if (exist == null) {
			feeHelperDAO.addFeeMRKJ(info);
		} else {
			logger.error("明日空间订单号:" + info.getLinkId() + " 存在重复!");
		}
	}
	
	public FeeMRKJ queryFeeMRKJ(String linkId) {
		String key = createKey(PREFIX_FEEMRKJ, linkId);
		FeeMRKJ info = (FeeMRKJ) MemcachedResource.get(key);
		if (info == null) info = feeHelperDAO.queryFeeMRKJ(linkId);
		
		if (info != null) MemcachedResource.save(key, info);
		return info;
	}
	
	// =============================智玩支付信息===============
	public void addFeeZW(FeeZW info) {
		FeeZW exist = queryFeeZW(info.getOrderNo());
		if (exist == null) {
			feeHelperDAO.addFeeZW(info);
		} else {
			logger.error("智玩订单号:" + info.getOrderNo() + " 存在重复!");
		}
	}
	
	public FeeZW queryFeeZW(String orderNO) {
		String key = createKey(PREFIX_FEEZW, orderNO);
		FeeZW info = (FeeZW) MemcachedResource.get(key);
		if (info == null) info = feeHelperDAO.queryFeeZW(orderNO);
		
		if (info != null) MemcachedResource.save(key, info);
		return info;
	}
	
	// =============================宜搜支付信息===============
	public void addFeeYS(FeeYS info) {
		FeeYS exist = queryFeeYS(info.getOrderId());
		if (exist == null) {
			feeHelperDAO.addFeeYS(info);
		} else {
			logger.error("宜搜订单号:" + info.getOrderId() + " 存在重复!");
		}
	}
	
	public FeeYS queryFeeYS(String orderNO) {
		String key = createKey(PREFIX_FEEYS, orderNO);
		FeeYS info = (FeeYS) MemcachedResource.get(key);
		if (info == null) info = feeHelperDAO.queryFeeYS(orderNO);
		
		if (info != null) MemcachedResource.save(key, info);
		return info;
	}
	
	// =============================朗天支付信息===============
	public void addFeeLTDX(FeeLTDX info) {
		FeeLTDX exist = queryFeeLTDX(info.getLinkId());
		if (exist == null) {
			feeHelperDAO.addFeeLTDX(info);
		} else {
			logger.error("朗天订单号:" + info.getLinkId() + " 存在重复!");
		}
	}
	
	public FeeLTDX queryFeeLTDX(String orderNO) {
		String key = createKey(PREFIX_FEELTDX, orderNO);
		FeeLTDX info = (FeeLTDX) MemcachedResource.get(key);
		if (info == null) info = feeHelperDAO.queryFeeLTDX(orderNO);
		
		if (info != null) MemcachedResource.save(key, info);
		return info;
	}
	
	
	// =============================恒巨支付信息===============
	public void addFeeHJMM(FeeHJMM info) {
		feeHelperDAO.addFeeHJMM(info);
	}
	
	
	// =============================沃勤支付信息===============
	public void addFeeWQ(FeeWQ info) {
		feeHelperDAO.addFeeWQ(info);
	}
	
	
	// =============================胜峰远景支付信息===============
	public void addFeeSKYJ(FeeSKYJ info) {
		FeeSKYJ exist = queryFeeSKYJ(info.getMchNo());
		if (exist == null) {
			feeHelperDAO.addFeeSKYJ(info);
		} else {
			logger.error("胜峰订单号:" + info.getMchNo() + " 存在重复!");
		}
	}
	
	public FeeSKYJ queryFeeSKYJ(String orderNO) {
		String key = createKey(PREFIX_FEESKYJ, orderNO);
		FeeSKYJ info = (FeeSKYJ) MemcachedResource.get(key);
		if (info == null) info = feeHelperDAO.queryFeeSKYJ(orderNO);
		
		if (info != null) MemcachedResource.save(key, info);
		return info;
	}
	
	// =============================乐易付支付信息===============
	public void addFeeLYF(FeeLYF info) {
		FeeSKYJ exist = queryFeeSKYJ(info.getOrderNO());
		if (exist == null) {
			feeHelperDAO.addFeeLYF(info);
		} else {
			logger.error("胜峰订单号:" + info.getOrderNO() + " 存在重复!");
		}
	}
	
	public FeeLYF queryFeeLYF(String orderNO, int step) {
		String key = createKey(PREFIX_FEELYF, orderNO+""+step);
		FeeLYF info = (FeeLYF) MemcachedResource.get(key);
		if (info == null) info = feeHelperDAO.queryFeeLYF(orderNO, step);
		
		if (info != null) MemcachedResource.save(key, info);
		return info;
	}
	
	// =============================威搜游支付信息===============
	public void addFeeWSY(FeeWSY info) {
		FeeWSY exist = queryFeeWSY(info.getLinkId());
		if (exist == null) {
			feeHelperDAO.addFeeWSY(info);
		} else {
			logger.error("威搜游订单号:" + info.getLinkId() + " 存在重复!");
		}
	}
	
	public FeeWSY queryFeeWSY(String linkId) {
		String key = createKey(PREFIX_FEEWSY, linkId);
		FeeWSY info = (FeeWSY) MemcachedResource.get(key);
		if (info == null) info = feeHelperDAO.queryFeeWSY(linkId);
		
		if (info != null) MemcachedResource.save(key, info);
		return info;
	}
	
	// =============================杭州掌维支付信息===============
	public void addFeeHZZW(FeeHZZW info) {
		FeeRZD exist = queryFeeRZD(info.getOrderNo());
		if (exist == null) {
			feeHelperDAO.addFeeHZZW(info);
		} else {
			logger.error("杭州掌维订单号:" + info.getOrderNo() + " 存在重复!");
		}
		
	}
	
	public FeeHZZW queryFeeHZZW(String orderNo) {
		String key = createKey(PREFIX_FEEHZZW, orderNo);
		FeeHZZW info = (FeeHZZW) MemcachedResource.get(key);
		if (info == null) info = feeHelperDAO.queryFeeHZZW(orderNo);
		
		if (info != null) MemcachedResource.save(key, info);
		return info;
	}
	
	// =============================北京锐之道支付信息===============
	public void addFeeRZD(FeeRZD info) {
		FeeRZD exist = queryFeeRZD(info.getOrderNO());
		if (exist == null) {
			feeHelperDAO.addFeeRZD(info);
		} else {
			logger.error("锐之道订单号:" + info.getOrderNO() + " 存在重复!");
		}
	}
	
	public FeeRZD queryFeeRZD(String orderNO) {
		String key = createKey(PREFIX_FEERZD, orderNO);
		FeeRZD info = (FeeRZD) MemcachedResource.get(key);
		if (info == null) info = feeHelperDAO.queryFeeRZD(orderNO);
		
		if (info != null) MemcachedResource.save(key, info);
		return info;
	}
	
	
	// =============================特安讯支付信息===============
	public void addFeeTAX(FeeTAX info) {
		FeeTAX exist = queryFeeTAX(info.getLinkId());
		if (exist == null) {
			feeHelperDAO.addFeeTAX(info);
		} else {
			logger.error("特安讯订单号:" + info.getLinkId() + " 存在重复!");
		}
	}
	
	public FeeTAX queryFeeTAX(String linkId) {
		String key = createKey(PREFIX_FEETAX, linkId);
		FeeTAX info = (FeeTAX) MemcachedResource.get(key);
		if (info == null) info = feeHelperDAO.queryFeeTAX(linkId);
		
		if (info != null) MemcachedResource.save(key, info);
		return info;
	}
	
	// =============================特安讯(万贝)电信支付信息===============
	public void addFeeWB(FeeWB info) {
		FeeWB exist = queryFeeWB(info.getLinkId());
		if (exist == null) {
			feeHelperDAO.addFeeWB(info);
		} else {
			logger.error("特安讯(万贝)电信订单号:" + info.getLinkId() + " 存在重复!");
		}
	}
	
	public FeeWB queryFeeWB(String linkId) {
		String key = createKey(PREFIX_FEEWB, linkId);
		FeeWB info = (FeeWB) MemcachedResource.get(key);
		if (info == null) info = feeHelperDAO.queryFeeWB(linkId);
		
		if (info != null) MemcachedResource.save(key, info);
		return info;
	}
	
	
	// =============================易讯无限支付信息===============
	public void addFeeYXWX(FeeYXWX info) {
		FeeYXWX exist = queryFeeYXWX(info.getOrderId());
		if (exist == null) {
			feeHelperDAO.addFeeYXWX(info);
		} else {
			logger.error("易讯无限订单号:" + info.getOrderId() + " 存在重复!");
		}
	}

	public FeeYXWX queryFeeYXWX(String orderId) {
		String key = createKey(PREFIX_FEEYXWX, orderId);
		FeeYXWX info = (FeeYXWX) MemcachedResource.get(key);
		if (info == null) info = feeHelperDAO.queryFeeYXWX(orderId);
		
		if (info != null) MemcachedResource.save(key, info);
		return info;
	}
	
	// =============================电盈支付信息===============
	public void addFeeDY(FeeDY info) {
		FeeDY exist = queryFeeDY(info.getLinkId());
		if (exist == null) {
			feeHelperDAO.addFeeDY(info);
		} else {
			logger.error("电盈订单号:" + info.getLinkId() + " 存在重复!");
		}
	}

	public FeeDY queryFeeDY(String linkId) {
		String key = createKey(PREFIX_FEEDY, linkId);
		FeeDY info = (FeeDY) MemcachedResource.get(key);
		if (info == null) info = feeHelperDAO.queryFeeDY(linkId);
		
		if (info != null) MemcachedResource.save(key, info);
		return info;
	}
	
	
	// =============================中国手游广州盈正支付信息===============
	public void addFeeZSYYZ(FeeZSYYZ info) {
		FeeZSYYZ exist = queryFeeZSYYZ(info.getOrderId());
		if (exist == null) {
			feeHelperDAO.addFeeZSYYZ(info);
		} else {
			logger.error("广州盈正订单号:" + info.getOrderId() + " 存在重复!");
		}
	}

	public FeeZSYYZ queryFeeZSYYZ(String orderId) {
		String key = createKey(PREFIX_FEEZSYYZ, orderId);
		FeeZSYYZ info = (FeeZSYYZ) MemcachedResource.get(key);
		if (info == null) info = feeHelperDAO.queryFeeZSYYZ(orderId);
		
		if (info != null) MemcachedResource.save(key, info);
		return info;
	}
	
	// =============================JQF支付信息===============
	public void addFeeJQF(FeeJQF info) {
		FeeJQF exist = queryFeeJQF(info.getLinkId());
		if (exist == null) {
			feeHelperDAO.addFeeJQF(info);
		} else {
			logger.error("JQF订单号:" + info.getLinkId() + " 存在重复!");
		}
	}
	
	public FeeJQF queryFeeJQF(String linkId) {
		String key = createKey(PREFIX_FEEJQF, linkId);
		FeeJQF info = (FeeJQF) MemcachedResource.get(key);
		if (info == null) info = feeHelperDAO.queryFeeJQF(linkId);
		
		if (info != null) MemcachedResource.save(key, info);
		return info;
	
	}
	
	// =============================深圳斯达通支付信息===============
	public void addFeeSZSDT(FeeCommonInfo info) {
		FeeCommonInfo exist = queryFeeSZSDT(info.getOrderId());
		if (exist == null) {
			feeHelperDAO.addFeeSZSDT(info);
		} else {
			logger.error("深圳斯达通订单号:" + info.getOrderId() + " 存在重复!");
		}
	}
	
	public FeeCommonInfo queryFeeSZSDT(String orderId){
		String key = createKey(PREFIX_FEESZSDT, orderId);
		FeeCommonInfo info = (FeeCommonInfo) MemcachedResource.get(key);
		if (info == null) info = feeHelperDAO.queryFeeSZSDT(orderId);
		
		if (info != null) MemcachedResource.save(key, info);
		return info;
	}
	
	// =============================北京指游支付信息===============
	public void addFeeBJZY(FeeCommonInfo info) {
		FeeCommonInfo exist = queryFeeBJZY(info.getOrderId());
		if (exist == null) {
			feeHelperDAO.addFeeBJZY(info);
		} else {
			logger.error("北京指游订单号:" + info.getOrderId() + " 存在重复!");
		}
	}
	
	public FeeCommonInfo queryFeeBJZY(String orderId){
		String key = createKey(PREFIX_FEEBJZY, orderId);
		FeeCommonInfo info = (FeeCommonInfo) MemcachedResource.get(key);
		if (info == null) info = feeHelperDAO.queryFeeBJZY(orderId);
		
		if (info != null) MemcachedResource.save(key, info);
		return info;
	}
	
	// =============================灵光互动支付信息===============
	public void addFeeLGHD(FeeCommonInfo info) {
		FeeCommonInfo exist = queryFeeLGHD(info.getOrderId());
		if (exist == null) {
			feeHelperDAO.addFeeLGHD(info);
		} else {
			logger.error("灵光互动订单号:" + info.getOrderId() + " 存在重复!");
		}
	}
	
	public FeeCommonInfo queryFeeLGHD(String orderId){
		String key = createKey(PREFIX_FEELGHD, orderId);
		FeeCommonInfo info = (FeeCommonInfo) MemcachedResource.get(key);
		if (info == null) info = feeHelperDAO.queryFeeLGHD(orderId);
		
		if (info != null) MemcachedResource.save(key, info);
		return info;
	}
	
	// =============================新深圳斯达通支付信息===============
	public void addFeeXSZSDT(FeeXSZSDT info) {
		FeeCommonInfo exist = queryFeeXSZSDT(info.getOrderId());
		if (exist == null) {
			feeHelperDAO.addFeeXSZSDT(info);
		} else {
			logger.error("新深圳斯达通订单号:" + info.getOrderId() + " 存在重复!");
		}
	}
	
	public FeeCommonInfo queryFeeXSZSDT(String orderId){
		String key = createKey(PREFIX_FEEXSZSDT, orderId);
		FeeCommonInfo info = (FeeCommonInfo) MemcachedResource.get(key);
		if (info == null) info = feeHelperDAO.queryFeeXSZSDT(orderId);
		
		if (info != null) MemcachedResource.save(key, info);
		return info;
	}
	
	// =============================广州游发支付信息===============
	public void addFeeGZYF(FeeCommonInfo info) {
		FeeCommonInfo exist = queryFeeGZYF(info.getOrderId());
		if (exist == null) {
			feeHelperDAO.addFeeGZYF(info);
		} else {
			logger.error("广州游发订单号:" + info.getOrderId() + " 存在重复!");
		}
	}
	
	public FeeCommonInfo queryFeeGZYF(String orderId){
		String key = createKey(PREFIX_FEEGZYF, orderId);
		FeeCommonInfo info = (FeeCommonInfo) MemcachedResource.get(key);
		if (info == null) info = feeHelperDAO.queryFeeGZYF(orderId);
		
		if (info != null) MemcachedResource.save(key, info);
		return info;
	}
	
	// =============================YFMP支付信息===============
	public void addFeeYFMP(FeeMM info) {
		FeeCommonInfo exist = queryFeeYFMP(info.getOrderId());
		if (exist == null) {
			feeHelperDAO.addFeeYFMP(info);
		} else {
			logger.error("YFMP订单号:" + info.getOrderId() + " 存在重复!");
		}
	}
	
	public FeeCommonInfo queryFeeYFMP(String orderId){
		String key = createKey(PREFIX_FEEYFMP, orderId);
		FeeCommonInfo info = (FeeCommonInfo) MemcachedResource.get(key);
		if (info == null) info = feeHelperDAO.queryFeeYFMP(orderId);
		
		if (info != null) MemcachedResource.save(key, info);
		return info;
	}
	
	// =============================万贝移动支付信息===============
	public void addFeeWBYD(FeeWB info) {
		FeeWB exist = queryFeeWBYD(info.getLinkId());
		if (exist == null) {
			feeHelperDAO.addFeeWBYD(info);
		} else {
			logger.error("万贝移动订单号:" + info.getLinkId() + " 存在重复!");
		}
	}
	
	public FeeWB queryFeeWBYD(String linkId) {
		String key = createKey(PREFIX_FEEWBYD, linkId);
		FeeWB info = (FeeWB) MemcachedResource.get(key);
		if (info == null) info = feeHelperDAO.queryFeeWBYD(linkId);
		
		if (info != null) MemcachedResource.save(key, info);
		return info;
	}
	
	// =============================DTYD支付信息===============
	public void addFeeDTYD(FeeCommonInfo info) {
		FeeCommonInfo exist = queryFeeDTYD(info.getOrderId());
		if (exist == null) {
			feeHelperDAO.addFeeDTYD(info);
		} else {
			logger.error("DTYD订单号:" + info.getOrderId() + " 存在重复!");
		}
	}
	
	public FeeCommonInfo queryFeeDTYD(String orderId){
		String key = createKey(PREFIX_FEEDTYD, orderId);
		FeeCommonInfo info = (FeeCommonInfo) MemcachedResource.get(key);
		if (info == null) info = feeHelperDAO.queryFeeDTYD(orderId);
		
		if (info != null) MemcachedResource.save(key, info);
		return info;
	}
	
	
	
	// =============================掌游互动支付信息===============
		public void addFeeZYHD(FeeZYHD info) {
			FeeZYHD exist = queryFeeZYHD(info.getLinkid());
			if (exist == null) {
				feeHelperDAO.addFeeZYHD(info);
			} else {
				logger.error("DTYD订单号:" + info.getLinkid() + " 存在重复!");
			}
		}
		
		public FeeZYHD queryFeeZYHD(String orderId){
			String key = createKey(PREFIX_FEEZYHD, orderId);
			FeeZYHD info = (FeeZYHD) MemcachedResource.get(key);
			if (info == null) info = feeHelperDAO.queryFeeZYHD(orderId);
			
			if (info != null) MemcachedResource.save(key, info);
			return info;
		}
		
		public ZYHDInfo queryFeeZYHDMM(int id){
			String key = createKey(PREFIX_FEEZYHDMM, id+"");
			ZYHDInfo info = (ZYHDInfo) MemcachedResource.get(key);
			if (info == null) info = feeHelperDAO.queryFeeZYHDMM(id);
			
			if (info != null) MemcachedResource.save(key, info);
			return info;
		}
		
		
	
	private String createKey(String prefix, String orderNO) {
		StringBuilder key = new StringBuilder();
		key.append(prefix).append(orderNO);
		return key.toString();
	}
	
	
	
}
