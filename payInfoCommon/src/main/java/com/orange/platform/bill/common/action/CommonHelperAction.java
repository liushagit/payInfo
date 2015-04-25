/**
 * weimiBillCommon
 */
package com.orange.platform.bill.common.action;

import com.orange.platform.bill.common.domain.LinkInfo;

/**
 * @author weimiplayer.com
 *
 * 2012年12月20日
 */
public interface CommonHelperAction {
	/** 添加订单绑定信息*/
	void addLinkInfo(LinkInfo info);
}
