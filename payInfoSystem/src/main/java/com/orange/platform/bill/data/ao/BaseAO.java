/**
 * weimiBillSystem
 */
package com.orange.platform.bill.data.ao;

import com.orange.platform.bill.data.dao.BillBasicDAO;
import com.orange.platform.bill.data.dao.CommonHelperDAO;
import com.orange.platform.bill.data.dao.FeeHelperDAO;
import com.orange.platform.bill.data.dao.SMSHelperDAO;

/**
 * @author weimiplayer.com
 *
 * 2012年11月2日
 */
public class BaseAO {
	protected static BillBasicDAO billDAO = new BillBasicDAO();
	protected static SMSHelperDAO smsDAO = new SMSHelperDAO();
	protected static FeeHelperDAO feeHelperDAO = new FeeHelperDAO();
	protected static CommonHelperDAO commonHelperDAO = new CommonHelperDAO();
}
