package com.orange.platform.bill.data;

import org.apache.log4j.Logger;

import com.orange.platform.bill.data.ao.BillBasicAO;
import com.orange.platform.bill.data.ao.CommonHelperAO;
import com.orange.platform.bill.data.ao.FeeHelperAO;
import com.orange.platform.bill.data.ao.SMSHelperAO;
import com.orange.platform.bill.data.job.BaseJob;
import com.orange.platform.bill.data.job.SchedulerUtils;
import com.payinfo.net.log.LoggerFactory;

/**
 * @author author 2012-5-3
 */
public class Application {
	private static Logger logger = LoggerFactory.getLogger(Application.class);
	//
	private static BillBasicAO billAO;
	private static SMSHelperAO smsAO;
	private static FeeHelperAO feeHelperAO;
	private static CommonHelperAO commonHelperAO;
//	private static MonitFeeAo monitFeeAO;
	//
	public void init() {
		billAO = new BillBasicAO();
		smsAO = new SMSHelperAO();
		feeHelperAO = new FeeHelperAO();
		commonHelperAO = new CommonHelperAO();
//		monitFeeAO = new MonitFeeAo();
		
		
		initSchedulerJob();//监控计费数据
	}
	
	/**
	 * 监控计费数据
	 */
	public void initSchedulerJob() {
		logger.info("Init Scheduler Job......");
		//
		
//		SchedulerUtils.getInstance().addSchedulerJob(monitFeeAO, MonitorJob.class, BaseJob.JOB_DAY_MONITOR_AO,
//				"0 0/60 * * * ?");//   0 0/3 14 * * ?     每天下午的 2点到2点59分(整点开始，每隔3分触发)
		
	}

	public BillBasicAO billAO() {
		return billAO;
	}
	
	public SMSHelperAO smsAO() {
		return smsAO;
	}
	
	public FeeHelperAO feeHelperAO() {
		return feeHelperAO;
	}
	
	public CommonHelperAO commonHelperAO() {
		return commonHelperAO;
	}
	
}
