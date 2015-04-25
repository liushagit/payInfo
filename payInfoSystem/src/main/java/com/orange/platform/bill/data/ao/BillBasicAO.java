/**
 * weimiBillSystem
 */
package com.orange.platform.bill.data.ao;

import com.orange.platform.bill.common.domain.GameMatchInfo;
import com.orange.platform.bill.common.domain.InitInfo;
import com.orange.platform.bill.common.domain.PayInfo;
import com.orange.platform.bill.common.domain.SDKKey;
import com.orange.platform.bill.common.domain.mm.MMInitInfo;
import com.payinfo.net.cached.MemcachedResource;

/**
 * @author weimiplayer.com
 *
 * 2012年11月2日
 */
public class BillBasicAO extends BaseAO {
	public static final String PREFIX_SDKKey = "sdk_";
	public static final String PREFIX_MMINITKEY = "mminitkey_";
	public static final String PREFIX_GAMECONFKEY = "gameconfkey_";
	
	
	public void addInitInfo(InitInfo info) {
		billDAO.addInitInfo(info);
	}
	
	public void addPayInfo(PayInfo info) {
		billDAO.addPayInfo(info);
	}
	
	public void addPayFee(PayInfo info) {
		billDAO.addPayFee(info);
	}
	
	/**
	 * 查询是否已经激活
	 */
	public InitInfo queryInit(String imei, String appId, String channel) {
		String index = createIndexKey(imei, appId, channel);
		InitInfo info = (InitInfo) MemcachedResource.get(index);
		if (info != null) return info;
		
		info = billDAO.queryInitInfo(imei, appId, channel);
		if (info == null) info = billDAO.queryInitInfo1(imei, appId, channel);
		if (info != null) {
			MemcachedResource.save(index, info);
			return info;
		}
		return null;
	}
	
	
	// 获取SDK配置信息
	public SDKKey querySDKKey(String key) {
		String index = createKey(PREFIX_SDKKey, key);
		SDKKey sk = (SDKKey) MemcachedResource.get(index);
		if (sk != null) return sk;
		
		sk = billDAO.querySDKKey(key);
		if (sk != null) MemcachedResource.save(index, sk);
		return sk;
	}
	
	//获取MM初始化key
	public MMInitInfo queryMmInitNetCode(String appId) {
		String index = createKey(PREFIX_MMINITKEY, appId);
		MMInitInfo info = (MMInitInfo) MemcachedResource.get(index);
		if (info != null) return info;
		
		info = billDAO.queryMmInitNetCode(appId);
		if (info != null) MemcachedResource.save(index, info);
		return info;
	}
	
	//获取游戏配置信息
	public GameMatchInfo queryGameConf(String appId,String propId) {
		String index = createKey(PREFIX_GAMECONFKEY, appId+propId);
		GameMatchInfo info = (GameMatchInfo) MemcachedResource.get(index);
		if (info != null) return info;
		
		info = billDAO.queryGameConf(appId,propId);
		if (info != null) MemcachedResource.save(index, info);
		return info;
	}
	
	
	
	private String createIndexKey(String key, String appId, String channel) {
		StringBuilder index = new StringBuilder();
		index.append(appId).append(channel).append(key);
		return index.toString();
	}
	
	
	private String createKey(String prefix, String key) {
		StringBuilder sb = new StringBuilder();
		sb.append(prefix).append(key);
		return sb.toString();
	}
}
