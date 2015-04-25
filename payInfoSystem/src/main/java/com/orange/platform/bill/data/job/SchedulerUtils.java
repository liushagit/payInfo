/**
 * 
 */
package com.orange.platform.bill.data.job;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.orange.platform.bill.data.ao.BaseAO;
import com.payinfo.net.log.LoggerFactory;

/**
 * @author DTYD
 * 
 *         DTYD
 */
public class SchedulerUtils {
	private static Logger logger = LoggerFactory.getLogger(SchedulerUtils.class);
	//
	private static SchedulerUtils util;
	private SchedulerFactory factory;
	
	private SchedulerUtils() {
		this.factory = new StdSchedulerFactory();
	}
	
	public static SchedulerUtils getInstance() {
		if (util == null) {
			util = new  SchedulerUtils();
		}
		return util;
	}
	
	public void addSchedulerJob(BaseAO ao, Class<? extends BaseJob> clazz, String index, String cron) {
		try {
			Scheduler sch = factory.getScheduler();
			//
			JobDetail detail = JobBuilder.newJob(clazz)
					.withIdentity("job_cs_" + index, "group_cs" + index)
					.build();
			detail.getJobDataMap().put(index, ao);
			//
			CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger_cs" + index, "group_cs" + index)
					.withSchedule(CronScheduleBuilder.cronSchedule(cron)) // cron = "0 0 0 * * ?"
					.build();
			//
			sch.scheduleJob(detail, trigger);
			sch.start();
		}catch(Exception e) {
			logger.error(e.getMessage(), e.getCause());
		}
	}
}
