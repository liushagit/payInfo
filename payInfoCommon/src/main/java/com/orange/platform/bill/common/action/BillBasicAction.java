/**
 * weimiBillCommon
 */
package com.orange.platform.bill.common.action;

import com.orange.platform.bill.common.domain.GameMatchInfo;
import com.orange.platform.bill.common.domain.InitInfo;
import com.orange.platform.bill.common.domain.PayInfo;
import com.orange.platform.bill.common.domain.SDKKey;
import com.orange.platform.bill.common.domain.mm.MMInitInfo;

/**
 * @author weimiplayer.com
 *
 * 2012年11月2日
 */
public interface BillBasicAction {
	void addInitInfo(InitInfo info);
	
	void addPayInfo(PayInfo info);
	
	void addPayFee(PayInfo info);
	
	SDKKey querySDKKey(String key);

	void clearCache();
	
	MMInitInfo queryMmInitNetCode(String appId);
	
	GameMatchInfo queryGameConf(String appId, String propId);
}
