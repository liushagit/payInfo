/**
 * weimiBillServer
 */
package com.orange.platform.bill.view.server;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.orange.platform.bill.view.ActionAware;
import com.orange.platform.bill.view.utils.HttpTool;
import com.orange.platform.bill.view.utils.ip.IPtest;
import com.orange.platform.bill.common.domain.InitInfo;
import com.orange.platform.bill.common.domain.PayInfo;
import com.orange.platform.bill.common.domain.SDKKey;
import com.orange.platform.bill.common.domain.mm.MMInitInfo;
import com.orange.platform.bill.common.utils.CommonTools;
import com.payinfo.net.handler.HttpRequest;
import com.payinfo.net.handler.HttpResponse;
import com.payinfo.net.log.LoggerFactory;

/**
 * @author weimiplayer.com
 *
 * 2012年11月2日
 */
public class MiServer extends ActionAware {
	private static Logger logger = LoggerFactory.getLogger(MiServer.class);
	
	/**
	 * 激活
	 * appId
	 * channel
	 * imei
	 * imsi
	 * asn
	 * scv
	 */
	public void init(HttpRequest request, HttpResponse response) throws Exception {
		try {
			String ip = getRealIP(request);
			logger.info("用户访问IP=" + ip);
			String province = IPtest.getInstance().queryProvince(ip);
			logger.info("IP对应的省份=" + province);
			
			String appId = request.getParam("appId");
			String channel = request.getParam("channel");
			String imei = request.getParam("imei");
			String imsi = request.getParam("imsi");
			if (imsi == null) imsi = "si" + CommonTools.createOrderNO();

			String appVersion = request.getParam("asn");
			String sdkVersion = request.getParam("scv");
			appVersion = appVersion == null ? "0" : appVersion;
			sdkVersion = sdkVersion == null ? "0" : sdkVersion;
			
//			boolean imsiCheck = CommonTools.IMSICheck(imsi);
//			if (!imsiCheck) {
//				logger.warn("imsi=" + imsi + "不符合规则！");
//				imsi = "si" + CommonTools.createOrderNO();
//			}
//			
//			boolean imeiCheck = CommonTools.IMEICheck(imei);
//			if (!imeiCheck) {
//				logger.warn("imei=" + imei + "不符合规则！");
//				imei = "ei" + CommonTools.createOrderNO();
//			}
			
			
			InitInfo info = new InitInfo();
			info.setImsi(imsi);
			info.setImei(imei);
			info.setAppId(appId);
			info.setChannel(channel);
			info.setMobileType(CommonTools.locateOperator(imsi));
			info.setIp(ip);
			info.setProvince(province);
			info.setAppVersion(appVersion);
			info.setSdkVersion(sdkVersion);
			billAction.addInitInfo(info);
			
//			String partUrl = "http://127.0.0.1/mmcom/startInit";
//			logger.info("init用户启动数设置：" + partUrl);
//			send(partUrl ,"", info.getAppId(), "", imsi, imei, "10", info.getChannel());
		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
		} finally {
			response.content("").end();
		}
	}
	
	/**
	 * 查询MM初始化key
	 * 127.0.0.1/weimi/reqMmInitNetCode
	 */
	public void reqMmInitNetCode(HttpRequest request, HttpResponse response) throws Exception {
		String pHttpContent = request.body();
		JSONObject jsonObject = JSONObject.parseObject(pHttpContent);
		String appId = jsonObject.getString("appId");
		MMInitInfo info = billAction.queryMmInitNetCode(appId);
		String result = "{\"appId\":\""+info.getMmAppId()+"\",\"appKey\":\""+info.getMmAppKey()+"\"}";
		response.content(result).end();
	}
	
	/**
	 * 付费统计
	 * orderNo
	 * imei
	 * imsi
	 * mobile
	 * ext
	 * fee
	 * asn
	 * scv
	 */
	public void pay(HttpRequest request, HttpResponse response) throws Exception {
		try {
			String ip = getRealIP(request);
			logger.info("用户访问IP=" + ip);
			String province = IPtest.getInstance().queryProvince(ip);
			logger.info("IP对应的省份=" + province);
			
			String orderId = request.getParam("orderNo");
			String imei = request.getParam("imei");
			String imsi = request.getParam("imsi");
			String mobile = request.getParam("mobile");
			String ext = request.getParam("ext");
			String fee = request.getParam("fee");
			int feeInt = Integer.parseInt(fee);
			String payType = request.getParam("payType");
			
			String appVersion = request.getParam("asn");
			String sdkVersion = request.getParam("scv");
			appVersion = appVersion == null ? "0" : appVersion;
			sdkVersion = sdkVersion == null ? "0" : sdkVersion;
			
			PayInfo info = new PayInfo();
			info.setOrderId(orderId);
			info.setImei(imei);
			info.setImsi(imsi);
			info.setMobile(mobile);
			info.setExt(ext);
			String[] params = ext.split("\\@");
			if (params.length == 3) {
				String appId = params[0];
				String channel = params[1];
				String propId = params[2];

				info.setAppId(appId);
				info.setChannel(channel);
				info.setPropId(propId);
			}
			info.setFee(feeInt);
			info.setMobileType(CommonTools.locateOperator(imsi));
			info.setIp(ip);
			info.setProvince(province);
			info.setPayType(payType);
			info.setAppVersion(appVersion);
			info.setSdkVersion(sdkVersion);
			billAction.addPayInfo(info);
			
//			if(orderId.startsWith("mm")) {
//			String partUrl = "http://127.0.0.1/mmcom/startPay";
//			logger.info("pay用户启动数设置：" + partUrl);
//			send(partUrl ,orderId, info.getAppId(), payType, imsi, imei, fee, info.getChannel());
//			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
		} finally {
			response.content("").end();
		}
	}
	
	
	/**
	 * 付费统计
	 * orderNo
	 * imei
	 * imsi
	 * mobile
	 * ext
	 * fee
	 * asn
	 * scv
	 */
	public void fee(HttpRequest request, HttpResponse response) throws Exception {
		try {
			String ip = getRealIP(request);
			logger.info("用户访问IP=" + ip);
			String province = IPtest.getInstance().queryProvince(ip);
			logger.info("IP对应的省份=" + province);
			
			String orderId = request.getParam("orderNo");
			String imei = request.getParam("imei");
			String imsi = request.getParam("imsi");
			String mobile = request.getParam("mobile");
			String ext = request.getParam("ext");
			String fee = request.getParam("fee");
			int feeInt = Integer.parseInt(fee);
			String payType = request.getParam("payType");
			
			String appVersion = request.getParam("asn");
			String sdkVersion = request.getParam("scv");
			appVersion = appVersion == null ? "0" : appVersion;
			sdkVersion = sdkVersion == null ? "0" : sdkVersion;
			
			PayInfo info = new PayInfo();
			info.setOrderId(orderId);
			info.setImei(imei);
			info.setImsi(imsi);
			info.setMobile(mobile);
			info.setExt(ext);
			String[] params = ext.split("\\@");
			if (params.length == 3) {
				String appId = params[0];
				String channel = params[1];
				String propId = params[2];

				info.setAppId(appId);
				info.setChannel(channel);
				info.setPropId(propId);
			}
			info.setFee(feeInt);
			info.setMobileType(CommonTools.locateOperator(imsi));
			info.setIp(ip);
			info.setProvince(province);
			info.setPayType(payType);
			info.setAppVersion(appVersion);
			info.setSdkVersion(sdkVersion);
			billAction.addPayFee(info);
			
//			if(orderId.startsWith("mm")) {
			String partUrl = "http://127.0.0.1:9981/mmcom/startPay";
			logger.info("fee用户启动数设置" + partUrl);
			send(partUrl ,orderId, info.getAppId(), payType + "_10", imsi, imei, fee, info.getChannel());
//			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
		} finally {
			response.content("").end();
		}
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * 获取短代版本
	 * appId
	 * channel
	 */
	public void getSMSVersion(HttpRequest request, HttpResponse response) throws Exception {
		String version = "0";
		try {
			String appId = request.getParam("appId");
			String channel = request.getParam("channel");
			String province = IPtest.getInstance().queryProvince(getRealIP(request));
			if (province.equals("广东")) {
				String country = IPtest.getInstance().queryCountry(getRealIP(request));
				logger.info(country);
				if (country.contains("广州")) {
					province="广州";
				} else if (country.contains("深圳")) {
					province="深圳";
				}
			}
//			String province = IPtest.getInstance().queryProvince("60.189.211.67");
			version = smsAction.querySMSVersion(appId, channel, province);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} 
		response.content(version).end();
	}
	
	/**
	 * 获取最新的短代
	 * appId
	 * channel
	 */
	public void getSMSContent(HttpRequest request, HttpResponse response) throws Exception {
		String code = "error";
		try {
			String province = IPtest.getInstance().queryProvince(getRealIP(request));
			if (province.equals("广东")) {
				String country = IPtest.getInstance().queryCountry(getRealIP(request));
				logger.info(country);
				if (country.contains("广州")) {
					province="广州";
				} else if (country.contains("深圳")) {
					province="深圳";
				}
			}
			logger.info("IP对应的省份=" + province);
			String appId = request.getParam("appId");
			String channel = request.getParam("channel");

			code = smsAction.querySMSContent(appId, channel, province);
		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
		} 
		response.content(code).end();
	}
	
	
	/**
	 * 付费间隔
	 * 127.0.0.1/weimi/getInterval
	 */
	public void getInterval(HttpRequest request, HttpResponse response) throws Exception {
		String time = "1000";
		try {
			String ip = getRealIP(request);
			logger.info("用户访问IP=" + ip);
			String province = IPtest.getInstance().queryProvince(ip);
			logger.info("IP对应的省份=" + province);
			
			String appId = request.getParam("appId");
			String channel = request.getParam("channel");

			int interval = smsAction.queryInterval(appId, channel, province);
			time = interval + "";
		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
		} 
		response.content(time).end();
	}
	
	
	/**
	 * 获取SDK关键信息
	 * 127.0.0.1/weimi/querySDKValue
	 */
	public void querySDKValue(HttpRequest request, HttpResponse response) throws Exception {
		String result = "error";
		try {
			String key = request.getParam("key");

			SDKKey sk = billAction.querySDKKey(key);
			result = sk.getValue();
		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
		} 
		response.content(result).end();
	}
	
	public void reqNetCode(HttpRequest request, HttpResponse response) throws Exception {
		String result = "error";
		String appId = request.getParam("appId");
		String channel = request.getParam("channel");
		
		String imei = request.getParam("imei");
		String imsi = request.getParam("imsi");
		String mobile = request.getParam("mobile");
		String fee = request.getParam("fee");
		
//				
		
//		（一种是jsonArray）还有一种是其他格式			
//		port短信端口	String		
//		content短信内容	String		
//		provider运营商	String		
//		orderNo订单编号	String		
//		type	String		
//		mustInitNetCode	String		
//		key	String		

		response.content(result).end();
	}
	
	
	/**
	 * 清缓存
	 */
	public void clearCache(HttpRequest request, HttpResponse response) throws Exception {
		String result = "ok";
		try {
			billAction.clearCache();;
		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
		} 
		response.content(result).end();
	}
	
	
	
	
	
	
	
	
	/**
	 * 从HTTP请求中提取IP地址
	 */
	private String getRealIP(HttpRequest request) {
		String ip = request.header("X-Real-IP");
		if (ip == null || "unknown".equalsIgnoreCase(ip)) {
			ip = request.header("X-Forwarded-For");
			if (ip != null && !"unknown".equalsIgnoreCase(ip)) {
				int index = ip.indexOf(',');
				if (index != -1) {
					ip = ip.substring(0, index);
				}
			}
		}
		if (ip == null) {
			ip = request.remoteAddress().toString();
		}

		int port = ip.indexOf(":");
		if (port > 1) {
			ip = ip.substring(1, port);
		}
		return ip;
	}
	
	private void send(String partUrl ,String orderId, String appId, String payType, String imsi, String imei, String fee, String channel) {
		StringBuilder url = new StringBuilder();
		url.append(partUrl).append("?orderId=").append(orderId)
			.append("&appId=").append(appId)
			.append("&payType=").append(payType)
			.append("&imsi=").append(imsi)
			.append("&imei=").append(imei)
			.append("&fee=").append(fee)
			.append("&channel=").append(channel);
		String reqResult = HttpTool.sendHttp(url.toString(), "");
		logger.info("指令结果=" + reqResult);
	}
	
}
