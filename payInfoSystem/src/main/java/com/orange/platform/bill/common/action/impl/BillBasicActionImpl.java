/**
 * weimiBillSystem
 */
package com.orange.platform.bill.common.action.impl;

import com.orange.platform.bill.common.action.BillBasicAction;
import com.orange.platform.bill.common.domain.GameMatchInfo;
import com.orange.platform.bill.common.domain.InitInfo;
import com.orange.platform.bill.common.domain.PayInfo;
import com.orange.platform.bill.common.domain.PingOrder;
import com.orange.platform.bill.common.domain.SDKKey;
import com.orange.platform.bill.common.domain.mm.MMInitInfo;
import com.orange.platform.bill.data.Application;
import com.payinfo.net.cached.MemcachedResource;

/**
 * @author weimiplayer.com
 *
 * 2012年11月2日
 */
public class BillBasicActionImpl extends Application implements BillBasicAction {
	@Override
	public void addInitInfo(InitInfo info) {
		InitInfo exist = billAO().queryInit(info.getImei(), info.getAppId(), info.getChannel());
		if (exist == null) billAO().addInitInfo(info);
	}

	@Override
	public void addPayInfo(PayInfo info) {
		billAO().addPayInfo(info);
	}

	@Override
	public void addPayFee(PayInfo info) {
		billAO().addPayFee(info);
	}
	
	@Override
	public SDKKey querySDKKey(String key) {
		return billAO().querySDKKey(key);
	}

	@Override
	public void clearCache() {
//		MemcachedResource.;
	}

	@Override
	public MMInitInfo queryMmInitNetCode(String appId) {
		return billAO().queryMmInitNetCode(appId);
	}

	@Override
	public GameMatchInfo queryGameConf(String appId, String propId) {
		return billAO().queryGameConf(appId,propId);
	}

	@Override
	public long queryMaxPingOrderId() {
		return billAO().queryMaxPingOrderId();
	}

	@Override
	public void addPingOrder(PingOrder order) {
		billAO().addPingOrder(order);
	}

	@Override
	public void updatePingOrder(long orderId, String orderStatus,int liveModel) {
		billAO().updatePingOrder(orderId, orderStatus,liveModel);
	}
}
