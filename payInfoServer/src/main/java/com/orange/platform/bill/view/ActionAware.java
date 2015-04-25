/**
 * SuperStarServer
 * com.orange.superstar.server
 * ActionAware.java
 */
package com.orange.platform.bill.view;

import com.orange.platform.bill.common.action.BillBasicAction;
import com.orange.platform.bill.common.action.CommonHelperAction;
import com.orange.platform.bill.common.action.FeeHelperAction;
import com.orange.platform.bill.common.action.SMSHelperAction;
import com.payinfo.net.container.Container;

/**
 * @author author
 * 2012-5-6
 */
public class ActionAware extends Application {
	public static final String REMOTE_PREFIX_SYSTEM = "PayInfoSystem";
	
	protected static BillBasicAction billAction = (BillBasicAction) Container.
			createRemoteService(BillBasicAction.class, REMOTE_PREFIX_SYSTEM);
	
	protected static SMSHelperAction smsAction = (SMSHelperAction) Container.
			createRemoteService(SMSHelperAction.class, REMOTE_PREFIX_SYSTEM);
	
	protected static FeeHelperAction feeHelperAction = (FeeHelperAction) Container.
			createRemoteService(FeeHelperAction.class, REMOTE_PREFIX_SYSTEM);
	
	protected static CommonHelperAction commonHelperAction = (CommonHelperAction) Container.
			createRemoteService(CommonHelperAction.class, REMOTE_PREFIX_SYSTEM);
}
