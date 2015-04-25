/**
 * 
 */
package com.orange.platform.bill.data.job;

import org.apache.log4j.Logger;
import org.quartz.Job;

import com.payinfo.net.log.LoggerFactory;

/**
 * @author DTYD
 * 
 *         DTYD
 */
public abstract class BaseJob implements Job {
	protected static Logger logger = LoggerFactory.getLogger(BaseJob.class);
	//
	public static final String JOB_DAY_MONITOR_AO = "monitorJob";
}
