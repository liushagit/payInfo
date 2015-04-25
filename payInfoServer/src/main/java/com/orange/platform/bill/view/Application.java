package com.orange.platform.bill.view;

import org.apache.log4j.Logger;

import com.orange.platform.bill.view.server.MiCommonServer;
import com.orange.platform.bill.view.server.MiFeedBack;
import com.orange.platform.bill.view.server.MiServer;
import com.payinfo.net.container.Container;
import com.payinfo.net.log.LoggerFactory;

/**
 * @author author 2012-5-3
 */
public class Application {
	private static Logger logger = LoggerFactory.getLogger(Application.class);

	//
	public void init() {
		initServer();
		//
	}

	public void initServer() {
		logger.info("Init servers......");
		// 用户服务
		Container.registerServer("weimi", new MiServer());
		Container.registerServer("dypay", new MiFeedBack());
		Container.registerServer("common", new MiCommonServer());
		Container.registerServer("mmcom", new MiCommonServer());
	}
}
