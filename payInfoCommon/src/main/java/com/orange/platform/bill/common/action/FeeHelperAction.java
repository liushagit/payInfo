/**
 * weimiBillCommon
 */
package com.orange.platform.bill.common.action;

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

/**
 * @author weimiplayer.com
 *
 * 2012年11月3日
 */
public interface FeeHelperAction {
	/** 明日空间*/
	void addFeeMRKJ(FeeMRKJ info);
	
	/** 智玩*/
	void addFeeZW(FeeZW info);
	
	/** 智玩*/
	void addFeeZW1(FeeZW info);
	
	/** 宜搜*/
	void addFeeYS(FeeYS info);
	
	/** 朗天电信*/
	void addFeeLTDX(FeeLTDX info);
	
	/** 恒巨信息*/
	void addFeeHJMM(FeeHJMM info);
	
	/** 沃勤*/
	void addFeeWQ(FeeWQ info);
	
	/** 北京盛峰*/
	void addFeeSKYJ(FeeSKYJ info);
	
	/** 乐易付*/
	void addFeeLYF(FeeLYF info);
	
	/** 威搜游*/
	void addFeeWSY(FeeWSY info);
	
	/** 掌维*/
	void addFeeHZZW(FeeHZZW info);
	
	/** 锐之道*/
	void addFeeRZD(FeeRZD info);
	
	/** 特安讯*/
	void addFeeTAX(FeeTAX info);
	
	/** 特安讯(万贝)电信*/
	void addFeeWB(FeeWB info);
	
	/** 易讯无限*/
	void addFeeYXWX(FeeYXWX info);
	
	/** 电盈*/
	void addFeeDY(FeeDY info, String orderId, boolean isUnicom);
	
	/** 广州盈*/	
	void addFeeZSYYZ(FeeZSYYZ info);
	
	/** JQF*/	
	void addFeeJQF(FeeJQF info);
	
	/** 深圳斯达通*/	
	void addFeeSZSDT(FeeCommonInfo info);
	
	/** 北京指游*/	
	void addFeeBJZY(FeeCommonInfo info);
	
	/** 灵光互动*/	
	void addFeeLGHD(FeeCommonInfo info);
	
	/** 新深圳斯达通*/	
	void addFeeXSZSDT(FeeXSZSDT info);
	
	/** 广州游发*/	
	void addFeeGZYF(FeeCommonInfo info);
	
	/** YFMP*/	
	void addFeeYFMP(FeeMM info);
	
	/** 万贝移动*/
	void addFeeWBYD(FeeWB info);
	
	/** DTYD*/
	void addFeeDTYD(FeeCommonInfo info);
	
	/** 掌游互动 */
	void addFeeZYHD(FeeZYHD info);
	/** 掌游互动MM */
	ZYHDInfo quaryZYHDMM(int id);
	
}
