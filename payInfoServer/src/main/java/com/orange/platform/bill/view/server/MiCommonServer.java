/**
 * weimiBillServer
 */
package com.orange.platform.bill.view.server;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.orange.platform.bill.view.ActionAware;
import com.orange.platform.bill.common.domain.LinkInfo;
import com.orange.platform.bill.common.utils.CommonTools;
import com.payinfo.net.handler.HttpRequest;
import com.payinfo.net.handler.HttpResponse;
import com.payinfo.net.log.LoggerFactory;

/**
 * @author weimiplayer.com
 *
 * 2012年12月20日
 */
public class MiCommonServer extends ActionAware {
	private static Logger logger = LoggerFactory.getLogger(MiCommonServer.class);
	
	/**
	 * 订单号数据绑定
	 * http://127.0.0.1/common/bindOrder
	 */
	public void bindOrder(HttpRequest request, HttpResponse response) throws Exception {
		try {
			String linkId = request.getParam("linkId");
			String ext = request.getParam("ext");
			String sp = request.getParam("sp");
			String imsi = request.getParam("imsi");
			String ip = request.getParam("ip");
			String feeValue = request.getParam("fee");
			int fee = CommonTools.convertInt(feeValue);
			
			LinkInfo info = new LinkInfo();
			info.setLinkId(linkId);
			info.setExt(ext);
			info.setSp(sp);
			info.setImsi(imsi);
			info.setIp(ip);
			info.setFee(fee);
			commonHelperAction.addLinkInfo(info);
		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
		}
		response.content("").end();
	}
	
	public void addMobileInfo(HttpRequest request, HttpResponse response) throws Exception {
//		JSONObject result = new JSONObject();
//		result.put("Version", 1);
//		
//		JSONArray prop = new JSONArray();
//		
//		JSONObject propId = new JSONObject();
//		propId.put("propId", "1");
//		prop.add(propId);
//		
//		JSONObject sms = new JSONObject();
//		
//		JSONArray smsInfo = new JSONArray();
//		
//		JSONObject server = new JSONObject();
//		server.put("server", 1);
//		smsInfo.add(server);
//		
//		JSONObject address = new JSONObject();
//		server.put("address", "10086");
//		smsInfo.add(address);
//		
//		JSONObject content = new JSONObject();
//		server.put("content", "test");
//		smsInfo.add(content);
//		
//		JSONObject point = new JSONObject();
//		server.put("point", "1.0");
//		smsInfo.add(point);
//		
//		
//		JSONObject servicesphone = new JSONObject();
//		server.put("servicesphone", "15245478714");
//		smsInfo.add(servicesphone);
//		
//		JSONObject smstag = new JSONObject();
//		server.put("smstag", "1");
//		smsInfo.add(smstag);
//		
//		
//		JSONObject dialogtag = new JSONObject();
//		server.put("dialogtag", "1");
//		smsInfo.add(dialogtag);
//		
//		
//		JSONObject smsprovider = new JSONObject();
//		server.put("smsprovider", "1");
//		smsInfo.add(smsprovider);
//		
//		JSONObject maxfailtotal = new JSONObject();
//		server.put("maxfailtotal", "1");
//		smsInfo.add(maxfailtotal);
//		
//		sms.put("sms", smsInfo);
//		
//		prop.add(sms);
//		
//		result.put("prop", prop);
//		result.put("filterKeyword", "");
//		result.put("forcedUpdating", "false");
//		System.out.println(result.toJSONString());
//		Version	int				
//		prop	JSONArray	propId	string		
//							
//				sms	JSONArray	server	int
//						address	string
//						content	string
//						point	Double
//						servicesphone	string
//						smstag	int
//						dialogtag	int
//						smsprovider	string
//						maxfailtotal	int
//							
//				propName	string		
//							
//		filterKeyword	string				
//		forcedUpdating	Boolean				

		response.content("").end();
	}
	
	
}
