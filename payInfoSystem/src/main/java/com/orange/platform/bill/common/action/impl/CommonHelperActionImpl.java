/**
 * weimiBillSystem
 */
package com.orange.platform.bill.common.action.impl;

import com.orange.platform.bill.common.action.CommonHelperAction;
import com.orange.platform.bill.common.domain.LinkInfo;
import com.orange.platform.bill.data.Application;

/**
 * @author weimiplayer.com
 *
 * 2012年12月20日
 */
public class CommonHelperActionImpl extends Application implements
		CommonHelperAction {
	@Override
	public void addLinkInfo(LinkInfo info) {
		commonHelperAO().addLinkInfo(info);
	}
}
