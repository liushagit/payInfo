/**
 * weimiBillSystem
 */
package com.orange.platform.bill.data.ao;

import com.orange.platform.bill.common.domain.LinkInfo;

/**
 * @author weimiplayer.com
 *
 * 2012年12月20日
 */
public class CommonHelperAO extends BaseAO {
	public void addLinkInfo(LinkInfo info) {
		commonHelperDAO.addLinkInfo(info);
	}
	
	public LinkInfo queryLinkInfo(String linkId) {
		return commonHelperDAO.queryLinkInfo(linkId);
	}
}
