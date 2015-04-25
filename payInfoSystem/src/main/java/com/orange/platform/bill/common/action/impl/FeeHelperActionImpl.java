/**
 * weimiBillSystem
 */
package com.orange.platform.bill.common.action.impl;

import com.orange.platform.bill.common.action.FeeHelperAction;
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
import com.orange.platform.bill.common.domain.LinkInfo;
import com.orange.platform.bill.common.domain.ZYHDInfo;
import com.orange.platform.bill.common.domain.mm.FeeMM;
import com.orange.platform.bill.data.Application;

/**
 * @author weimiplayer.com
 *
 * 2012年11月3日
 */
public class FeeHelperActionImpl extends Application implements FeeHelperAction {
	@Override
	public void addFeeMRKJ(FeeMRKJ info) {
		feeHelperAO().addFeeMRKJ(info);
	}

	@Override
	public void addFeeZW(FeeZW info) {
		feeHelperAO().addFeeZW(info);
	}

	@Override
	public void addFeeZW1(FeeZW info) {
		LinkInfo temp = commonHelperAO().queryLinkInfo("ZW"+info.getOrderNo());
		if (temp != null) {
			String cpparam = temp.getExt();
			info.setCpRemark(cpparam);
			String[] params = cpparam.split("\\@");
			if (params.length == 3) {
				String appId = params[0];
				String channel = params[1];
				String propId = params[2];

				info.setAppId(appId);
				info.setChannel(channel);
				info.setPropId(propId);
			}
		}
		feeHelperAO().addFeeZW(info);
	}

	@Override
	public void addFeeYS(FeeYS info) {
		feeHelperAO().addFeeYS(info);
	}

	@Override
	public void addFeeLTDX(FeeLTDX info) {
		feeHelperAO().addFeeLTDX(info);
	}

	@Override
	public void addFeeHJMM(FeeHJMM info) {
		feeHelperAO().addFeeHJMM(info);
	}

	@Override
	public void addFeeWQ(FeeWQ info) {
		feeHelperAO().addFeeWQ(info);
	}

	@Override
	public void addFeeSKYJ(FeeSKYJ info) {
		feeHelperAO().addFeeSKYJ(info);
	}

	@Override
	public void addFeeLYF(FeeLYF info) {
		feeHelperAO().addFeeLYF(info);
	}
	
	@Override
	public void addFeeWSY(FeeWSY info) {
		feeHelperAO().addFeeWSY(info);
	}

	@Override
	public void addFeeHZZW(FeeHZZW info) {
		LinkInfo temp = commonHelperAO().queryLinkInfo(info.getOrderNo());
		if (temp != null) {
			String cpparam = temp.getExt();
			info.setExtdata(cpparam);
			String[] params = cpparam.split("\\@");
			if (params.length == 3) {
				String appId = params[0];
				String channel = params[1];
				String propId = params[2];

				info.setAppId(appId);
				info.setChannel(channel);
				info.setPropId(propId);
			}
		}
		feeHelperAO().addFeeHZZW(info);
	}

	@Override
	public void addFeeRZD(FeeRZD info) {
		feeHelperAO().addFeeRZD(info);
	}

	@Override
	public void addFeeTAX(FeeTAX info) {
		feeHelperAO().addFeeTAX(info);
	}

	@Override
	public void addFeeYXWX(FeeYXWX info) {
		feeHelperAO().addFeeYXWX(info);
	}

	@Override
	public void addFeeDY(FeeDY info, String orderId, boolean isUnicom) {
		if (!isUnicom) {
			LinkInfo temp = commonHelperAO().queryLinkInfo(orderId);
			if (temp != null) {
				String cpparam = temp.getExt();
				info.setExtData(cpparam);
				String[] params = cpparam.split("\\@");
				if (params.length == 3) {
					String appId = params[0];
					String channel = params[1];
					String propId = params[2];

					info.setAppId(appId);
					info.setChannel(channel);
					info.setPropId(propId);
				}
			}
		}
		feeHelperAO().addFeeDY(info);
	}

	@Override
	public void addFeeZSYYZ(FeeZSYYZ info) {
		String orderId = info.getExt();
		LinkInfo temp = commonHelperAO().queryLinkInfo(orderId);
		if (temp != null) {
			String cpparam = temp.getExt();
			String[] params = cpparam.split("\\@");
			if (params.length == 3) {
				String appId = params[0];
				String channel = params[1];
				String propId = params[2];

				info.setAppId(appId);
				info.setChannel(channel);
				info.setPropId(propId);
			}
		}
		feeHelperAO().addFeeZSYYZ(info);
	}

	@Override
	public void addFeeJQF(FeeJQF info) {
		feeHelperAO().addFeeJQF(info);
	}

	@Override
	public void addFeeSZSDT(FeeCommonInfo info) {
		feeHelperAO().addFeeSZSDT(info);
	}

	@Override
	public void addFeeWB(FeeWB info) {
		feeHelperAO().addFeeWB(info);
	}

	@Override
	public void addFeeBJZY(FeeCommonInfo info) {
		LinkInfo temp = commonHelperAO().queryLinkInfo(info.getweimiOrderId());
		if (temp != null) {
			String cpparam = temp.getExt();
			info.setExtdata(cpparam);
			String[] params = cpparam.split("\\@");
			if (params.length == 3) {
				String appId = params[0];
				String channel = params[1];
				String propId = params[2];

				info.setAppId(appId);
				info.setChannel(channel);
				info.setPropId(propId);
			}
		}
		feeHelperAO().addFeeBJZY(info);
	}

	@Override
	public void addFeeLGHD(FeeCommonInfo info) {
		String content = info.getContent();
		String linkId = "lghd"+content.substring(content.length()-9);
		info.setweimiOrderId(linkId);
		LinkInfo temp = commonHelperAO().queryLinkInfo(linkId);
		if (temp != null) {
			String cpparam = temp.getExt();
			String[] params = cpparam.split("\\@");
			if (params.length == 3) {
				String appId = params[0];
				String channel = params[1];
				String propId = params[2];

				info.setAppId(appId);
				info.setChannel(channel);
				info.setPropId(propId);
			}
		}
		feeHelperAO().addFeeLGHD(info);
	}

	@Override
	public void addFeeXSZSDT(FeeXSZSDT info) {
		String linkId = "xszsdt" + info.getExt();
		LinkInfo temp = commonHelperAO().queryLinkInfo(linkId);
		if (temp != null) {
			String cpparam = temp.getExt();
			String[] params = cpparam.split("\\@");
			if (params.length == 3) {
				String appId = params[0];
				String channel = params[1];
				String propId = params[2];

				info.setAppId(appId);
				info.setChannel(channel);
				info.setPropId(propId);
			}
		}
		feeHelperAO().addFeeXSZSDT(info);
	}
	
	@Override
	public void addFeeGZYF(FeeCommonInfo info) {
		feeHelperAO().addFeeGZYF(info);
	}

	@Override
	public void addFeeYFMP(FeeMM info) {
		feeHelperAO().addFeeYFMP(info);
	}
	
	@Override
	public void addFeeWBYD(FeeWB info) {
		feeHelperAO().addFeeWBYD(info);
	}

	@Override
	public void addFeeDTYD(FeeCommonInfo info) {
		feeHelperAO().addFeeDTYD(info);
	}

	@Override
	public void addFeeZYHD(FeeZYHD info) {
		feeHelperAO().addFeeZYHD(info);
	}

	@Override
	public ZYHDInfo quaryZYHDMM(int id) {
		return feeHelperAO().queryFeeZYHDMM(id);
	}
	
	
}
