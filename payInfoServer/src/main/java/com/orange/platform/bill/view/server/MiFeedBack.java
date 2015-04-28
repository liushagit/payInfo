/**
 * weimiBillServer
 */
package com.orange.platform.bill.view.server;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.omg.IOP.Encoding;
import org.w3c.dom.Document;

import com.alibaba.fastjson.JSONObject;
import com.orange.platform.bill.common.domain.FeeCommonInfo;
import com.orange.platform.bill.common.domain.FeeDY;
import com.orange.platform.bill.common.domain.FeeHJMM;
import com.orange.platform.bill.common.domain.FeeHZZW;
import com.orange.platform.bill.common.domain.FeeLTDX;
import com.orange.platform.bill.common.domain.FeeLYF;
import com.orange.platform.bill.common.domain.FeeMRKJ;
import com.orange.platform.bill.common.domain.FeeRZD;
import com.orange.platform.bill.common.domain.FeeSKYJ;
import com.orange.platform.bill.common.domain.FeeTAX;
import com.orange.platform.bill.common.domain.FeeWB;
import com.orange.platform.bill.common.domain.FeeWQ;
import com.orange.platform.bill.common.domain.FeeWSY;
import com.orange.platform.bill.common.domain.FeeXSZSDT;
import com.orange.platform.bill.common.domain.FeeYS;
import com.orange.platform.bill.common.domain.FeeYXWX;
import com.orange.platform.bill.common.domain.FeeZSYYZ;
import com.orange.platform.bill.common.domain.FeeZW;
import com.orange.platform.bill.common.domain.FeeZYHD;
import com.orange.platform.bill.common.domain.GameMatchInfo;
import com.orange.platform.bill.common.domain.ZYHDInfo;
import com.orange.platform.bill.common.domain.mm.FeeMM;
import com.orange.platform.bill.common.domain.mm.SyncAppOrderReq;
import com.orange.platform.bill.common.utils.CommonTools;
import com.orange.platform.bill.common.utils.DateUtils;
import com.orange.platform.bill.common.utils.MD5Utils;
import com.orange.platform.bill.common.utils.XMLUtils;
import com.orange.platform.bill.view.ActionAware;
import com.orange.platform.bill.view.service.PingService;
import com.orange.platform.bill.view.utils.HttpTool;
import com.orange.platform.bill.view.utils.URLUtil;
import com.orange.platform.bill.view.utils.bjsf.AES;
import com.payinfo.net.exception.JuiceException;
import com.payinfo.net.handler.HttpRequest;
import com.payinfo.net.handler.HttpResponse;
import com.payinfo.net.log.LoggerFactory;
import com.pingplusplus.model.Charge;
import com.pingplusplus.model.Notify;
import com.pingplusplus.model.Refund;

/**
 * 订单回调通知
 * 
 * @author weimiplayer.com
 *
 *         2012年11月2日
 */
public class MiFeedBack extends ActionAware {
	private static Logger logger = LoggerFactory.getLogger(MiFeedBack.class);

	
	/***
	 * 朗天电信支付 http://127.0.0.1/lwpay/feeLTDX
	 */
	public void feeLTDX(HttpRequest request, HttpResponse response)
			throws JuiceException {
		String content = request.body();
		logger.info("朗天电信下行短信："+content);
		Map<String, String> myObj = parseYZ(content);
		
		String mobile = myObj.get("mobile");
		String linkId = myObj.get("linkId");
		String longCode = myObj.get("longCode");
		String msg = myObj.get("msg");
		String status = myObj.get("status");
		String fee = myObj.get("fee");
		String mac = myObj.get("mac");
		
		StringBuilder value = new StringBuilder();
		value.append(mobile).append(linkId).append(longCode).append(msg)
			.append(status).append(fee).append(LTDX_KEY);
		String sign = MD5Utils.toMD5Value(value.toString());
		if(!sign.equals(mac.toLowerCase())) {
			logger.error("朗天电信支付回调信息验证失败:"+value.toString());
			response.content("faile").end();
			return;
		}

		StringBuilder desc = new StringBuilder();
		desc.append("朗天电信支付:{");
		desc.append("mobile:").append(mobile).append("|");
		desc.append("linkId:").append(linkId).append("|");
		desc.append("longCode:").append(longCode).append("|");
		desc.append("msg:").append(msg).append("|");
		desc.append("status:").append(status).append("|");
		desc.append("fee:").append(fee).append("|");
		desc.append("mac:").append(mac).append("}");
		
		String result = "";
		if ("00".equals(status)) {
			logger.info(desc.toString());
			result = "DELIVRD";
		} else {
			logger.error(desc.toString());
			result = status;
		}

		try {
			FeeLTDX info = new FeeLTDX();
			info.setLinkId(linkId);
			info.setMsgId(mac);
			info.setMobile(mobile);
			info.setContent(msg);
			info.setDestAddr(longCode);
			info.setResult(result);
			info.setFee(convertInt(fee));

			String[] extDatas = msg.split("\\#");
			if (extDatas.length > 2) {
				String extData = extDatas[2];
				String[] params = extData.split("\\@");
				if (params.length == 3) {
					String appId = params[0];
					String channel = params[1];
					String propId = params[2];

					info.setAppId(appId);
					info.setChannel(channel);
					info.setPropId(propId);
				}
			}
			feeHelperAction.addFeeLTDX(info);
		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
		}

		response.content("success").end();
	}

	/***
	 * 朗天联通支付 http://127.0.0.1/lwpay/feeLTLT
	 */
	public void feeLTLT(HttpRequest request, HttpResponse response)
			throws JuiceException {
		String body = request.body();
		logger.info("朗天联通支付回调内容：" + body);
		Map<String, String> paramMap = CommonTools.parseLTLT(body);

		String result = paramMap.get("hRet"); // 计费状态标识， 0：成功 其他：失败
		String cpParam = paramMap.get("cpParam"); // 自定义参数
		String transIDO = paramMap.get("transIDO");
		String linkId = paramMap.get("linkid");

		StringBuilder desc = new StringBuilder();
		desc.append("朗天联通支付\n");
		desc.append("result:").append(result).append("\n");
		desc.append("cpParam:").append(cpParam).append("\n");
		desc.append("transIDO:").append(transIDO).append("\n");
		desc.append("linkId:").append(linkId);
		logger.info(desc.toString());

		response.content("OK").end();
	}
	

	/***
	 * 朗天电信下行短信配置 http://127.0.0.1/lwpay/matchLTDX
	 */
	public void matchLTDX(HttpRequest request, HttpResponse response)
			throws JuiceException {
		String content = request.body();
		logger.info("朗天电信下行短信："+content);
		Map<String, String> myObj = parseYZ(content);
		
		String mobile = myObj.get("mobile");
		String msg = myObj.get("msg");
		String longCode = myObj.get("longCode");
		String linkId = myObj.get("linkId");
		String fee = myObj.get("fee");
		String status = myObj.get("status");
		String mac = myObj.get("mac");
		
		StringBuilder value = new StringBuilder();
		value.append(mobile).append(msg).append(linkId).append(longCode)
			.append(fee).append(status).append(LTDX_KEY);
		String sign = MD5Utils.toMD5Value(value.toString());
		if(!sign.equals(mac.toLowerCase())) {
			logger.error("朗天电信信息验证失败:"+value.toString());
			response.content("faile").end();
			return;
		}

		try {
			GameMatchInfo info = new GameMatchInfo();
			info.setMobile(mobile);
			info.setMsg(msg);
			info.setLongCode(longCode);
			info.setLinkId(linkId);
			info.setFee(fee);
			info.setStatus(status);
			info.setMac(mac);

			String[] extDatas = msg.split("\\#");
			if (extDatas.length > 2) {
				String extData = extDatas[2];
				String[] params = extData.split("\\@");
				if (params.length == 3) {
					String appId = params[0];
					String channel = params[1];
					String propId = params[2];

					info.setAppId(appId);
					info.setChannel(channel);
					info.setPropId(propId);
					
					GameMatchInfo resultInfo = billAction.queryGameConf(appId,propId);
					if (resultInfo == null) resultInfo = billAction.queryGameConf("all","all");
					
					StringBuilder returnMsg = new StringBuilder();
					returnMsg.append(resultInfo.getAppName()).append(";").append(resultInfo.getPropName());
					String retMsg = URLEncoder.encode(returnMsg.toString(), "utf-8");
					StringBuilder retValue = new StringBuilder();
					retValue.append(mobile).append(LTDX_CHANNELID).append(linkId)
						.append(longCode).append(retMsg).append(LTDX_KEY);
					String newMac = MD5Utils.toMD5Value(retValue.toString());
					
//					String retUrl = "http://121.41.58.237:8981/center/Submit";
					StringBuilder httpReq = new StringBuilder();
					httpReq.append("mobile=").append(mobile)
						.append("&channelId=").append(LTDX_CHANNELID).append("&linkId=").append(linkId)
						.append("&longCode=").append(longCode)
						.append("&msg=").append(retMsg)
						.append("&mac=").append(newMac.toUpperCase());
//					String httpResult = HttpTool.sendHttp(retUrl+httpReq.toString(), httpReq.toString());
					String httpResult = HttpTool.sendGet(LTDX_FEE_URL, httpReq.toString());
					logger.info("朗天电信请求url:"+LTDX_FEE_URL);
					logger.info("朗天电信请求url参数:"+httpReq.toString());
					logger.info("朗天电信请求url请求结果:"+httpResult);
				}
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
		}

		response.content("success").end();
	}
	
	
	/***
	 * 掌游互动 http://127.0.0.1/lwpay/feeZYHD 支付成功通知
	 */
	public void feeZYHD(HttpRequest request, HttpResponse response)
			throws JuiceException {
		
		String res = "ok";
		
		String mobile = request.getParam("mobile");
		String longnum = request.getParam("longnum");
		String consumeCode = request.getParam("consumeCode");
		String fee = request.getParam("fee");
		String hret = request.getParam("hret");
		String status = request.getParam("status");
		String linkid = request.getParam("linkid");
		String cpparam = request.getParam("cpparam");
		String sign = request.getParam("sign");
		String con = null;
		if (fee == null) {
			con = request.body();
			Map<String, String> myObj = parseYZ(con);
			mobile = myObj.get("mobile");
			longnum = myObj.get("longnum");
			consumeCode = myObj.get("consumeCode");
			fee = myObj.get("fee");
			hret = myObj.get("hret");
			status = myObj.get("status");
			linkid = myObj.get("linkid");
			cpparam = myObj.get("cpparam");
			sign = myObj.get("sign");
		}

		StringBuilder msg = new StringBuilder();
		msg.append("掌游互动支付成功通知\n");
		msg.append("mobile:").append(mobile).append("\n");
		msg.append("longnum:").append(longnum).append("\n");
		msg.append("consumeCode:").append(consumeCode).append("\n");
		msg.append("fee:").append(fee).append("\n");
		msg.append("hret:").append(hret).append("\n");
		msg.append("status:").append(status).append("\n");
		msg.append("linkid:").append(linkid).append("\n");
		msg.append("cpparam:").append(cpparam).append("\n");
		msg.append("sign:").append(sign).append("\n");
		msg.append("con:").append(con).append("\n");
		
		StringBuffer value = getZYHDData(consumeCode,fee,hret,cpparam,linkid);
		value.append(KEY_ZYHD);
		String autoSign = MD5Utils.toMD5Value(value.toString());
		msg.append("autoSign:").append(autoSign).append("\n");
		
		
		logger.info(msg.toString());
		if (sign != null) {
			sign = sign.toLowerCase();
		}
		boolean isOk = autoSign.equals(sign);
		if (isOk) {
			try {
				FeeZYHD info = new FeeZYHD();
				info.setMobile(mobile);
				info.setLongnum(longnum);
				info.setConsumeCode(consumeCode);
				info.setFee(Integer.valueOf(fee));
				info.setHret(hret);
				info.setStatus(status);
				info.setLinkid(linkid);
				info.setCpparam(cpparam);
				int id = -1;
				try {
					id = Integer.valueOf(cpparam);
				} catch (Exception e) {
					try {
						String tmpId = cpparam.replace("FKWN", "");
						id = Integer.valueOf(tmpId);
					} catch (Exception e2) {
					}
				}
				ZYHDInfo zyInfo = feeHelperAction.quaryZYHDMM(id);
				if (zyInfo != null) {
					String appInfs[] = zyInfo.getExtData().split("\\@");
					if (appInfs.length == 4) {
						info.setAppId(appInfs[1]);
						info.setChannelId(appInfs[2]);
						info.setPropId(appInfs[3]);
					}
				}
				
				feeHelperAction.addFeeZYHD(info);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}else{
			res = "fail sign";
		}
		logger.info("zyhd result:" + res);
		response.content(res).end();
	}

	private static final String KEY_ZYHD = "zyxd678*&^";
	private StringBuffer getZYHDData(String consumeCode,String fee,String hret,String cpparam,String linkid){
		StringBuffer sb = new StringBuffer();
		sb.append(consumeCode).append(fee).append(hret).append(cpparam).append(linkid);
		return sb;
	}
	
	/***
	 * 逐梦 http://127.0.0.1/lwpay/feeZMSYN 短息同步转发接口
	 */
	public void feeZMSYN(HttpRequest request, HttpResponse response)
			throws JuiceException {
		String res = "ok";
		
		String linkid = request.getParam("linkid");
		String message = request.getParam("message");
		String phone = request.getParam("phone");
		String spnumber = request.getParam("spnumber");
		String bodyData = request.body();
		StringBuffer sb = new StringBuffer();
		sb.append("逐梦短息同步转发接口\n")
		.append("linkid:").append(linkid).append("\n")
		.append("message:").append(message).append("\n")
		.append("phone:").append(phone).append("\n")
		.append("spnumber:").append(spnumber).append("\n")
		.append("bodyData:").append(bodyData).append("\n");
		
		logger.info(sb.toString());
		response.content(res).end();
	}
	/***
	 * 逐梦 http://127.0.0.1/lwpay/feeZM ivr同步转发接口支付成功通知
	 */
	public void feeZM(HttpRequest request, HttpResponse response)
			throws JuiceException {
		String res = "ok";

		String linkid = request.getParam("linkid");
		String message = request.getParam("message");
		String phone = request.getParam("phone");
		String spnumber = request.getParam("spnumber");
		String bodyData = request.body();
		StringBuffer sb = new StringBuffer();
		sb.append("逐梦同步转发接口支付成功通知\n")
		.append("linkid:").append(linkid).append("\n")
		.append("message:").append(message).append("\n")
		.append("phone:").append(phone).append("\n")
		.append("spnumber:").append(spnumber).append("\n")
		.append("bodyData:").append(bodyData).append("\n");
		
		logger.info(sb.toString());
		
		response.content(res).end();
	}
	
	
	/***
	 * ping++支付通知 http://127.0.0.1/lwpay/feeZM ping++异步通知支付成功
	 */
	public void feePing(HttpRequest request, HttpResponse response)
			throws JuiceException {
		String res = "fail";
		// 读取异步通知数据
		String notifyJson = request.body();

		// 解析异步通知数据
		Object o = Notify.parseNotify(notifyJson);

		// 对异步通知做处理
		if (o instanceof Charge) {
			Charge charge = (Charge) o;
			if (charge.getOrderNo() == null) {
				res = "fail";
			} else {
				long orderId = Long.parseLong(charge.getOrderNo());
				int liveMode = charge.getLivemode() ? 1 : 0;
				billAction.updatePingOrder(orderId, PingService.FINISHED,liveMode);
				// 处理成功返回success
				res = "success";

			}
		}

		if (o instanceof Refund) {
			Refund refund = (Refund) o;

			if (refund.getOrderNo() == null) {
				res = "fail";
			} else {
				long orderId = Long.parseLong(refund.getOrderNo());
				billAction.updatePingOrder(orderId, PingService.REFUND,1);
				// 处理成功返回success
				res = "success";

			}
		}

		response.content(res).end();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/***
	 * 明日空间 http://127.0.0.1/lwpay/feeMRKJ 支付成功通知
	 */
	public void feeMRKJ(HttpRequest request, HttpResponse response)
			throws JuiceException {
		String mobile = request.getParam("usernumber");
		String linkId = request.getParam("linkid");
		String report = request.getParam("report");
		String srvcode = request.getParam("srvcode");
		String feeType = request.getParam("feetype");
		String feeCode = request.getParam("feecode");
		String sendDate = request.getParam("date");
		String content = request.getParam("msg");

		StringBuilder msg = new StringBuilder();
		msg.append("明日空间支付成功通知\n");
		msg.append("mobile:").append(mobile).append("\n");
		msg.append("linkId:").append(linkId).append("\n");
		msg.append("report:").append(report).append("\n");
		msg.append("srvcode:").append(srvcode).append("\n");
		msg.append("feeType:").append(feeType).append("\n");
		msg.append("feeCode:").append(feeCode).append("\n");
		msg.append("sendDate:").append(sendDate).append("\n");
		msg.append("content:").append(content).append("\n");
		logger.info(msg.toString());
		try {
			FeeMRKJ info = new FeeMRKJ();
			info.setMobile(mobile);
			info.setLinkId(linkId);
			info.setResult(report);
			info.setSrvcode(srvcode);
			info.setFeeType(feeType);
			info.setFee(convertInt(feeCode));
			info.setSendDate(sendDate);
			info.setMsg(content);

			feeHelperAction.addFeeMRKJ(info);
			;
		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
		}
		response.content("ok").end();
	}

	/***
	 * 智玩 http://127.0.0.1/lwpay/feeZW
	 */
	public void feeZW(HttpRequest request, HttpResponse response)
			throws JuiceException {
		// String cpId = request.getParam("cpId"); // CP编号
		// String productId = request.getParam("productId");
		// String orderNo = request.getParam("orderNo");
		// String feeCode = request.getParam("feeCode");
		// String cpRemark = request.getParam("cpRemark");
		// String sign = request.getParam("sign");
		// String reqTime = request.getParam("reqTime");
		// String resultCode = request.getParam("resultCode");
		// StringBuilder desc = new StringBuilder();
		// desc.append("义讯支付信息\n");
		// desc.append("cpId:").append(cpId).append("\n");
		// desc.append("productId:").append(productId).append("\n");
		// desc.append("orderNo:").append(orderNo).append("\n");
		// desc.append("feeCode:").append(feeCode).append("\n");
		// desc.append("cpRemark:").append(cpRemark).append("\n");
		// desc.append("reqTime:").append(reqTime).append("\n");
		// desc.append("resultCode:").append(resultCode).append("\n");
		// desc.append("sign:").append(sign);
		// if (resultCode.equals("1")) {
		// logger.info(desc.toString());
		// } else {
		// logger.error(desc.toString());
		// }
		//
		// try {
		// FeeZW info = new FeeZW();
		// info.setCpId(cpId);
		// info.setProductId(productId);
		// info.setOrderNo(orderNo);
		// info.setFeeCode(feeCode);
		// info.setFee(CommonTools.getFee(feeCode));
		// info.setCpRemark(cpRemark);
		// info.setResultCode(resultCode);
		//
		// String[] params = cpRemark.split("\\@");
		// if (params.length == 3) {
		// String appId = params[0];
		// String channel = params[1];
		// String propId = params[2];
		//
		// info.setAppId(appId);
		// info.setChannel(channel);
		// info.setPropId(propId);
		// }
		//
		// feeHelperAction.addFeeZW(info);
		// } catch (Exception e) {
		// logger.error(e);
		// }

		String cpId = request.getParam("mcpid");
		String oid = request.getParam("oid");
		String orderNo = request.getParam("orderNo");
		String feeCode = request.getParam("feeCode");
		String resultCode = request.getParam("resultCode");
		String payTime = request.getParam("payTime");
		String reqTime = request.getParam("reqTime");

		StringBuilder desc = new StringBuilder();
		desc.append("义讯支付信息\n");
		desc.append("cpId:").append(cpId).append("\n");
		desc.append("oid:").append(oid).append("\n");
		desc.append("orderNo:").append(orderNo).append("\n");
		desc.append("feeCode:").append(feeCode).append("\n");
		desc.append("resultCode:").append(resultCode).append("\n");
		desc.append("reqTime:").append(reqTime).append("\n");
		desc.append("payTime:").append(payTime);
		if (resultCode.equals("1")) {
			logger.info(desc.toString());
		} else {
			logger.error(desc.toString());
		}

		try {
			FeeZW info = new FeeZW();
			info.setCpId(cpId);
			info.setProductId(oid);
			info.setOrderNo(orderNo);
			info.setFeeCode(feeCode);
			info.setFee(CommonTools.getFee1(feeCode));
			info.setResultCode(resultCode);

			feeHelperAction.addFeeZW1(info);
		} catch (Exception e) {
			logger.error(e);
		}
		response.content("ok").end();
	}

	/***
	 * 宜搜支付 http://127.0.0.1/lwpay/feeYS
	 */
	public void feeYS(HttpRequest request, HttpResponse response)
			throws JuiceException {
		String orderId = request.getParam("orderid");
		String transeId = request.getParam("transeid");
		String extData = request.getParam("cporderid");
		String feeMode = request.getParam("feemode");
		String appFeeId = request.getParam("appfeeid");
		String appFee = request.getParam("appfee");
		String paidFee = request.getParam("paidfee");

		String bodyData = request.body();
		if (orderId == null) {
			Map<String, String> map = parseYZ(bodyData);
			orderId = map.get("orderid");
			transeId = map.get("transeid");
			extData = map.get("cporderid");
			feeMode = map.get("feemode");
			appFeeId = map.get("appfeeid");
			appFee = map.get("appfee");
			paidFee = map.get("paidfee");
		}
		
		StringBuilder desc = new StringBuilder();
		desc.append("宜搜支付信息\n");
		desc.append("orderId:").append(orderId).append("\n");
		desc.append("transeId:").append(transeId).append("\n");
		desc.append("extData:").append(extData).append("\n");
		desc.append("feeMode:").append(feeMode).append("\n");
		desc.append("appFeeId:").append(appFeeId).append("\n");
		desc.append("appFee:").append(appFee).append("\n");
		desc.append("paidFee:").append(paidFee).append("\n");
		desc.append("bodyData:").append(bodyData);
		logger.info(desc.toString());

		
		int appFeeIdInt = convertInt(appFeeId);
		int appFeeInt = convertInt(appFee);
		int paidFeeInt = convertInt(paidFee);
		try {
			FeeYS info = new FeeYS();
			info.setOrderId(orderId);
			info.setTranseId(transeId);
			info.setExtData(extData);
			info.setFeeMode(feeMode);
			info.setFeeId(appFeeIdInt);
			info.setFee(appFeeInt);
			info.setPaidFee(paidFeeInt);

			String[] params = extData.split("\\@");
			if (params.length == 3) {
				String appId = params[0];
				String channel = params[1];
				String propId = params[2];

				info.setAppId(appId);
				info.setChannel(channel);
				info.setPropId(propId);
			}

			feeHelperAction.addFeeYS(info);
		} catch (Exception e) {
			logger.error("feeYS exception",e);
		}

		response.content("0").end();
	}
	
	
//	/***
//	 * 朗天电信支付 http://127.0.0.1/lwpay/feeLTDX
//	 */
//	public void feeLTDX(HttpRequest request, HttpResponse response)
//			throws JuiceException {
//		String mobile = request.getParam("mobile");
//		String content = request.getParam("passwd");
//		String result = request.getParam("msg");
//		String linkId = request.getParam("LinkID");
//		String msgId = request.getParam("MsgID");
//		String destAddr = request.getParam("DestAddr");
//
//		StringBuilder desc = new StringBuilder();
//		desc.append("朗天电信支付\n");
//		desc.append("mobile:").append(mobile).append("\n");
//		desc.append("content:").append(content).append("\n");
//		desc.append("result:").append(result).append("\n");
//		desc.append("linkId:").append(linkId).append("\n");
//		desc.append("msgId:").append(msgId).append("\n");
//		desc.append("destAddr:").append(destAddr).append("\n");
//		if (result.equals("DELIVRD")) {
//			logger.info(desc.toString());
//		} else {
//			logger.error(desc.toString());
//		}
//
//		try {
//			FeeLTDX info = new FeeLTDX();
//			info.setLinkId(linkId);
//			info.setMsgId(msgId);
//			info.setMobile(mobile);
//			info.setContent(content);
//			info.setDestAddr(destAddr);
//			info.setResult(result);
//
//			int fee = CommonTools.getFeeLTDX(destAddr);
//			info.setFee(fee);
//
//			String[] extDatas = content.split("\\#");
//			if (extDatas.length > 1) {
//				String extData = extDatas[1];
//				String[] params = extData.split("\\@");
//				if (params.length == 3) {
//					String appId = params[0];
//					String channel = params[1];
//					String propId = params[2];
//
//					info.setAppId(appId);
//					info.setChannel(channel);
//					info.setPropId(propId);
//				}
//			}
//			feeHelperAction.addFeeLTDX(info);
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e.getCause());
//		}
//
//		response.content("OK").end();
//	}

	

	/***
	 * 恒巨支付 http://127.0.0.1/lwpay/feeHJMM
	 */
	public void feeHJMM(HttpRequest request, HttpResponse response)
			throws JuiceException {
		String body = request.body();
		logger.info("恒巨MM支付回调内容：" + body);

		String seqid = "1";
		try {
			InputStream in = new ByteArrayInputStream(body.getBytes());
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(in);
			seqid = CommonTools.parseHJMM(doc, "SEQID");

			String srcAddr = CommonTools.parseHJMM(doc, "SRCADDR");
			String destAddr = CommonTools.parseHJMM(doc, "DESTADDR");
			String msg = CommonTools.parseHJMM(doc, "MSG");
			String linkId = CommonTools.parseHJMM(doc, "LINKID");
			String cpRemark = CommonTools.parseHJMM(doc, "CORID");
			String feeValue = CommonTools.parseHJMM(doc, "SRCADDRTYPE");
			int fee = Integer.parseInt(feeValue);
			in.close();

			FeeHJMM info = new FeeHJMM();
			info.setSeqId(seqid);
			info.setSrcAddr(srcAddr);
			info.setDestAddr(destAddr);
			info.setMsg(msg);
			info.setLinkId(linkId);
			info.setFee(fee);
			info.setCpRemark(cpRemark);
			String[] params = cpRemark.split("\\@");
			if (params.length == 3) {
				String appId = params[0];
				String channel = params[1];
				String propId = params[2];

				info.setAppId(appId);
				info.setChannel(channel);
				info.setPropId(propId);
			}
			feeHelperAction.addFeeHJMM(info);
		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
		}
		StringBuilder respMsg = new StringBuilder();
		respMsg.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>")
				.append("<ROOT>").append("<MSGTYP>ReportResp</MSGTYP>")
				.append("<SEQID>" + seqid + "</SEQID>").append("<RET>0</RET>")
				.append("</ROOT>");
		response.content(respMsg.toString()).end();
	}

	/***
	 * 沃勤支付 http://127.0.0.1/lwpay/feeWQ
	 */
	public void feeWQ(HttpRequest request, HttpResponse response)
			throws JuiceException {
		String content = request.body();
		logger.info(content);
		try {
			FeeWQ info = CommonTools.parseWQ(new ByteArrayInputStream(content
					.getBytes()));
			if (info != null) {
				String[] params = info.getExtData().split("\\@");
				if (params.length == 3) {
					String appId = params[0];
					String channel = params[1];
					String propId = params[2];

					info.setAppId(appId);
					info.setChannel(channel);
					info.setPropId(propId);
				}

				feeHelperAction.addFeeWQ(info);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
		}

		StringBuilder memo = new StringBuilder();
		memo.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		memo.append("<response>\n");
		memo.append("<resp_code>1</resp_code>\n");
		memo.append("<resp_putChannelID>2083</resp_putChannelID>\n");
		memo.append("</response>\n");
		response.content(memo.toString()).end();
	}

	public static final String KEY_HZZW = "FknLjpFRgwh1xYXu8tchMhunJF7x0RM0";
	
	//朗天
	private static final String LTDX_KEY = "00U65JXM";
	private static final String LTDX_CHANNELID = "1066";
	private static final String LTDX_FEE_URL = "http://121.41.58.237:8981/center/Submit";

	

	
	/**
	 * 北京盛峰 http://127.0.0.1/lwpay/feeSFYJ
	 */
	public void feeSFYJ(HttpRequest request, HttpResponse response)
			throws JuiceException {
		String resultCode = "000~success~";
//		String Prim = request.getParam("Prim");
		String Prim = null;
		String MchId = null;

		if(Prim == null){
			String body = request.body();
			String s[] = body.split("&");
			for (String tmp : s) {
				String s1[] = tmp.split("=");
				if("Prim".equals(s1[0])){
					Prim = s1[1];
				}
				if("MchId".equals(s1[0])){
					MchId = s1[1];
				}
			}
		}
		logger.info("feeSFYJ body:"+Prim);
		boolean isOk = true;
		String res = "";
		try {
	//		Prim = URLUtil.decode(Prim);
			Prim = URLUtil.decode(Prim);
			Prim = Prim.replaceAll(" ", "+");
			res = AES.decrypt(Prim, null);
		} catch (Exception e1) {
			logger.error("feeSFYJ decrypt exception", e1);
			isOk = false;
			resultCode = "401~OK~";
		}
		logger.info("res:" + res);
		try {
			String prim[] = res.split("~");
			
			/** 订单号流水 */
			String mchNo = null;
			/** 用户电话 */
			String phone = null;
			/** 信息费 */
			String fee = null;
			/** 订单号 */
			String orderId = null;
			/** 类型 */
			String mobileType = null;
			try {
				mchNo = prim[0];
				phone = prim[1];
				fee = prim[2];
				orderId = prim[3];
				mobileType = prim[4];
				
			} catch (Exception e) {
				logger.error("par exception", e);
			}
			int typeValue = 0;
			if (mobileType != null) {
				if (mobileType.equals("DX")) {
					typeValue = 3;
				} else if (mobileType.equals("LT")) {
					typeValue = 2;
				} else {
					typeValue = 1;
				}
			}

			

			StringBuilder memo = new StringBuilder();
			memo.append("北京盛峰\n");
			memo.append("mchNo:\t").append(mchNo).append("\n");
			memo.append("phone:\t").append(phone).append("\n");
			memo.append("orderId:\t").append(orderId).append("\n");
			memo.append("fee:\t").append(fee).append("\n");
			memo.append("MobileType:\t").append(mobileType).append("\n");
			memo.append("isOk:\t").append(isOk).append("\n");
			logger.info(memo.toString());
			// 添加支付信息
			if (isOk) {
				FeeSKYJ info = new FeeSKYJ();
				info.setMchNo(mchNo);
				info.setPhone(phone);
				info.setFee(Integer.parseInt(fee));
				info.setOrderId(orderId);
				info.setMobileType(typeValue);

				String[] params = orderId.split("\\@");
				if (params.length == 4) {
					String appId = params[1];
					String channel = params[2];
					String propId = params[3];

					info.setAppId(appId);
					info.setChannel(channel);
					info.setPropId(propId);
				}

				feeHelperAction.addFeeSKYJ(info);
			}
		} catch (Exception e) {
			resultCode = "401~OK~";
			logger.error("exception:" ,e);
		}
		//
		logger.info("result:" + resultCode);
		response.content(resultCode).end();
	}

	/***
	 * 乐易付 订单通知 http://127.0.0.1/lwpay/orderLYF
	 */
	public void orderLYF(HttpRequest request, HttpResponse response)
			throws JuiceException {
		StringBuilder msg = new StringBuilder();
		msg.append("乐易付订单通知");
		logger.info(msg.toString());
		response.content("success").end();
	}

	/***
	 * 乐易付 支付成功通知 http://127.0.0.1/lwpay/feeLYF
	 */
	public void feeLYF(HttpRequest request, HttpResponse response)
			throws JuiceException {
		String userOrderId = request.getParam("userOrderId");
		String orderno = request.getParam("orderno");
		String fee = request.getParam("amount");
		String payOrderStep = request.getParam("payOrderStep");
		String price = request.getParam("price");
		String payStatus = request.getParam("payStatus");
		String error = request.getParam("error");

		StringBuilder msg = new StringBuilder();
		msg.append("乐易付支付成功通知\n");
		msg.append("userOrderId:").append(userOrderId).append("\n");
		msg.append("orderno:").append(orderno).append("\n");
		msg.append("fee:").append(fee).append("\n");
		msg.append("payOrderStep:").append(payOrderStep).append("\n");
		msg.append("price:").append(price).append("\n");
		msg.append("payStatus:").append(payStatus).append("\n");
		msg.append("error:").append(error).append("\n");
		logger.info(msg.toString());

		try {
			FeeLYF info = new FeeLYF();
			info.setOrderNO(orderno);
			info.setFee(convertInt(fee));
			info.setStep(convertInt(payOrderStep));
			info.setPrice(convertInt(price));
			info.setOrderStatus(convertInt(payStatus));
			info.setError(error);
			info.setCpRemark(userOrderId);

			String[] params = userOrderId.split("\\@");
			if (params.length == 3) {
				String appId = params[0];
				String channel = params[1];
				String propId = params[2];

				info.setAppId(appId);
				info.setChannel(channel);
				info.setPropId(propId);
			}

			feeHelperAction.addFeeLYF(info);
		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
		}
		response.content("success").end();
	}

	/***
	 * 威搜游 支付成功通知 http://127.0.0.1/lwpay/feeWSY
	 */
	public void feeWSY(HttpRequest request, HttpResponse response)
			throws JuiceException {
		String appId = request.getParam("appId");
		String channelId = request.getParam("channelId");
		String linkId = request.getParam("linkId");
		String mno = request.getParam("mno");
		String price = request.getParam("price");
		String flag = request.getParam("status");
		String rtime = request.getParam("rTime");

		StringBuilder msg = new StringBuilder();
		msg.append("威搜游支付成功通知\n");
		msg.append("appId:").append(appId).append("\n");
		msg.append("channelId:").append(channelId).append("\n");
		msg.append("linkId:").append(linkId).append("\n");
		msg.append("mno:").append(mno).append("\n");
		msg.append("price:").append(price).append("\n");
		msg.append("flag:").append(flag).append("\n");
		msg.append("rtime:").append(rtime).append("\n");
		logger.info(msg.toString());

		try {
			FeeWSY info = new FeeWSY();
			info.setAppId(appId);
			info.setChannelId(channelId);
			info.setLinkId(linkId);
			info.setMno(convertInt(mno));
			info.setPrice(convertInt(price));
			info.setFlag(convertInt(flag));
			info.setRtime(rtime);

			feeHelperAction.addFeeWSY(info);
		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
		}
		response.content("ok").end();
	}

	/***
	 * 杭州掌唯 支付成功通知 http://127.0.0.1/lwpay/feeHZZW
	 */
	public void feeHZZW(HttpRequest request, HttpResponse response)
			throws JuiceException {
		String body = request.body();
		logger.info("杭州掌维支付回调内容：" + body);
		Map<String, String> paramMap = CommonTools.parseLTLT(body);
		/*
		 * String cpId = request.getParam("app_id");//掌维提供的商户编号 String notifyId
		 * = request.getParam("notify_id");//通知校验 ID String orderNo =
		 * request.getParam("orderNo");//商户订单号 String tradeNo =
		 * request.getParam("trade_no");//掌维交易订单号 String tradeStatus =
		 * request.getParam("trade_status");//本次交易状态 String tradeTotal =
		 * request.getParam("trade_total");//本次订单充值金额 String timestamp =
		 * request.getParam("timestamp");//通知时间戳 String uid =
		 * request.getParam("uid");//商户用户 ID(发起支付请求时传递的 uid 参数，此处原样返回) String
		 * account = request.getParam("account");//商户用户账号(发起支付请求时传递的 account
		 * 参数，此处原样返回) String ext = request.getParam("ext");//扩展内容(发起支付请求时传递的
		 * body 参数，此处原样返回) String sign = request.getParam("sign");//sign
		 */
		String cpId = paramMap.get("app_id");// 掌维提供的商户编号
		String notifyId = paramMap.get("notify_id");// 通知校验 ID
		String orderNo = paramMap.get("order_no");// 商户订单号
		String tradeNo = paramMap.get("trade_no");// 掌维交易订单号
		String tradeStatus = paramMap.get("trade_status");// 本次交易状态
		String tradeTotal = paramMap.get("trade_total");// 本次订单充值金额
		String timestamp = paramMap.get("timestamp");// 通知时间戳
		String uid = paramMap.get("uid");// 商户用户 ID(发起支付请求时传递的 uid 参数，此处原样返回)
		String account = paramMap.get("account");// 商户用户账号(发起支付请求时传递的 account
													// 参数，此处原样返回)
		String feeCode = paramMap.get("fee_code");// 计费代码
		String sign = paramMap.get("sign");// sign

		try {
			StringBuilder value = new StringBuilder();
			value = getHZZWData(value, account, cpId, feeCode, notifyId,
					orderNo, timestamp, tradeNo, tradeStatus, tradeTotal, uid);

			value.append("&key=").append(KEY_HZZW);
			String autoSign = MD5Utils.toMD5Value(value.toString());
			boolean isOk = autoSign.equals(sign);

			StringBuilder msg = new StringBuilder();
			msg.append("杭州掌维\n");
			msg.append("account:").append(account).append("\n");
			msg.append("app_id:").append(cpId).append("\n");
			// msg.append("ext:").append(ext).append("\n");
			msg.append("notify_id:").append(notifyId).append("\n");
			msg.append("order_no:").append(orderNo).append("\n");
			msg.append("timestamp:").append(timestamp).append("\n");
			msg.append("trade_no:").append(tradeNo).append("\n");
			msg.append("trade_status:").append(tradeStatus).append("\n");
			msg.append("trade_total:").append(tradeTotal).append("\n");
			msg.append("uid:").append(uid).append("\n");
			msg.append("sign:").append(sign).append("\n");
			msg.append("isOk:").append(isOk).append("\n");
			logger.info(msg.toString());

			if (true) {
				FeeHZZW info = new FeeHZZW();
				info.setCpId(cpId);
				info.setNotifyId(notifyId);
				info.setOrderNo(orderNo);
				info.setTradeNo(tradeNo);
				if ("TRADE_SUCCESS".equals(tradeStatus)) {
					info.setResultCode("1");
				} else {
					info.setResultCode("0");
				}
				info.setTimestamp(timestamp);
				info.setUid(uid);
				info.setAccount(account);
				info.setFeeCode(feeCode);
				int fee = 0;
				if (tradeTotal != null && tradeTotal.contains(".")) {
					fee = convertInt(tradeTotal.split("\\.")[0]);
				}
				info.setFee(fee);
				info.setCm(account);
				feeHelperAction.addFeeHZZW(info);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
		}
		response.content("success").end();
	}

	private StringBuilder getHZZWData(StringBuilder value, String account,
			String cpId, String feeCode, String notifyId, String orderNo,
			String timestamp, String tradeNo, String tradeStatus,
			String tradeTotal, String uid) {
		if (account != null && "".equals(account)) {
			value.append("account=").append(account);
		}
		if (cpId != null && "".equals(cpId)) {
			value.append("&app_id=").append(cpId);
		}
		if (feeCode != null && "".equals(feeCode)) {
			value.append("&fee_code=").append(feeCode);
		}
		if (notifyId != null && "".equals(notifyId)) {
			value.append("&notify_id=").append(notifyId);
		}
		if (orderNo != null && "".equals(orderNo)) {
			value.append("&order_no=").append(orderNo);
		}
		if (timestamp != null && "".equals(timestamp)) {
			value.append("&timestamp=").append(timestamp);
		}
		if (tradeNo != null && "".equals(tradeNo)) {
			value.append("&trade_no=").append(tradeNo);
		}
		if (tradeStatus != null && "".equals(tradeStatus)) {
			value.append("&trade_status=").append(tradeStatus);
		}
		if (tradeTotal != null && "".equals(tradeTotal)) {
			value.append("&trade_total=").append(tradeTotal);
		}
		if (uid != null && "".equals(uid)) {
			value.append("&uid=").append(uid);
		}

		return value;
	}

	/***
	 * 北京锐之道 支付成功通知 http://127.0.0.1/lwpay/feeRZD
	 */
	public void feeRZD(HttpRequest request, HttpResponse response)
			throws JuiceException {
		try {
			String orderNO = request.getParam("orderno");
			String amount = request.getParam("amount");
			String imsi = request.getParam("imsi");
			String status = request.getParam("status");
			String ext = request.getParam("cpparam");
			String type = request.getParam("sid");

			FeeRZD info = new FeeRZD();
			info.setOrderNO(orderNO);
			info.setAmount(convertInt(amount));
			info.setImsi(imsi);
			info.setStatus(convertInt(status));
			info.setExt(ext);
			info.setType(type);
			String[] params = ext.split("\\@");
			if (params.length == 3) {
				String appId = params[0];
				String channel = params[1];
				String propId = params[2];

				info.setAppId(appId);
				info.setChannel(channel);
				info.setPropId(propId);
			}
			feeHelperAction.addFeeRZD(info);
		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
		}
		response.content("ok").end();
	}

	/***
	 * 特安讯 支付成功通知 http://127.0.0.1/lwpay/feeTAX
	 */
	public void feeTAX(HttpRequest request, HttpResponse response)
			throws JuiceException {
		try {
			String linkId = request.getParam("linkid");
			String momsg = request.getParam("momsg");
			String province = request.getParam("province");
			String cpparam = request.getParam("cpparam");
			String price = request.getParam("price");

			FeeTAX info = new FeeTAX();
			info.setLinkId(linkId);
			info.setMomsg(momsg);
			info.setProvince(province);
			info.setFee(CommonTools.convertInt(price));
			info.setExt(cpparam);
			String[] params = cpparam.split("\\@");
			if (params.length == 3) {
				String appId = params[0];
				String channel = params[1];
				String propId = params[2];

				info.setAppId(appId);
				info.setChannel(channel);
				info.setPropId(propId);
			}
			feeHelperAction.addFeeTAX(info);
		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
		}
		response.content("ok").end();
	}

	/***
	 * 特安讯(万贝)电信 支付成功通知 http://127.0.0.1/lwpay/feeWB
	 */
	public void feeWB(HttpRequest request, HttpResponse response)
			throws JuiceException {
		try {
			String mobile = request.getParam("mobile");
			String spnumber = request.getParam("spnumber");
			String linkId = request.getParam("linkid");
			String momsg = request.getParam("momsg");
			String province = request.getParam("province");
			String cpparam = request.getParam("cpparam");
			String price = request.getParam("price");

			FeeWB info = new FeeWB();
			info.setMobile(mobile);
			info.setSpnumber(spnumber);
			info.setLinkId(linkId);
			info.setMomsg(momsg);
			info.setProvince(province);
			info.setFee(CommonTools.convertInt(price));
			info.setExt(cpparam);
			String[] params = cpparam.split("\\@");
			if (params.length == 3) {
				String appId = params[0];
				String channel = params[1];
				String propId = params[2];

				info.setAppId(appId);
				info.setChannel(channel);
				info.setPropId(propId);
			}
			feeHelperAction.addFeeWB(info);
		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
		}
		response.content("ok").end();
	}

	/***
	 * 深圳梦方程 支付成功通知 http://127.0.0.1/lwpay/feeMFC
	 */
	public void feeMFC(HttpRequest request, HttpResponse response)
			throws JuiceException {
		try {

		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
		}
		response.content("ok").end();
	}

	/***
	 * 中手游 支付成功通知 http://127.0.0.1/lwpay/feeZSY
	 */
	public void feeZSY(HttpRequest request, HttpResponse response)
			throws JuiceException {
		try {

		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
		}
		response.content("ok").end();
	}

	/***
	 * 中手游盈正 支付成功通知 http://127.0.0.1/lwpay/feeZSYYZ
	 */
	public void feeZSYYZ(HttpRequest request, HttpResponse response)
			throws JuiceException {
		try {
			String content = request.body();
			logger.info(content);
			Map<String, String> params = parseYZ(content);
			String orderId = params.get("ord");
			String serviceId = params.get("serviceId");
			String province = params.get("province");
			String price = params.get("price");
			String ext = params.get("cpParam");
			String result = params.get("checkCode");

			FeeZSYYZ info = new FeeZSYYZ();
			info.setOrderId(orderId);
			info.setServiceId(serviceId);
			info.setProvince(province);
			info.setPrice(convertInt(price));
			info.setExt(ext);
			info.setResult(result);
			feeHelperAction.addFeeZSYYZ(info);
		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
		}
		response.content("200").end();
	}

	/***
	 * 永兴隆投资 http://127.0.0.1/lwpay/feeYXL
	 */
	public void feeYXL(HttpRequest request, HttpResponse response)
			throws JuiceException {
		try {

		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
		}
		response.content("ok").end();
	}

	/***
	 * 易讯无限 支付成功通知 http://127.0.0.1/lwpay/feeYXWX
	 */
	public void feeYXWX(HttpRequest request, HttpResponse response)
			throws JuiceException {
		try {
			String content = request.body();
			logger.info(content);

			FeeYXWX info = parseJson(content);
			if (info != null)
				feeHelperAction.addFeeYXWX(info);
		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
		}
		response.content("{\"resultCode\":\"200\"}").end();
	}

	/***
	 * 电盈 支付成功通知 http://127.0.0.1/lwpay/feeDY
	 */
	@SuppressWarnings("deprecation")
	public void feeDY(HttpRequest request, HttpResponse response)
			throws JuiceException {
		try {
			String linkId = request.getParam("LinkID");
			String result = request.getParam("RptStat");
			String orderId = request.getParam("ExtData");
			String feeCode = request.getParam("FeeCode");
			String gameID = request.getParam("GameID");
			boolean isUnicom = gameID != null;
			int fee = convertInt(feeCode);

			FeeDY info = new FeeDY();
			info.setLinkId(linkId);
			info.setResult(result);
			info.setFee(fee);
			if (isUnicom) {
				String extData = URLDecoder.decode(orderId);
				String[] params = extData.split("\\@");
				if (params.length == 3) {
					String appId = params[0];
					String channel = params[1];
					String propId = params[2];

					info.setAppId(appId);
					info.setChannel(channel);
					info.setPropId(propId);
				}

			}
			feeHelperAction.addFeeDY(info, orderId, isUnicom);
		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
		}
		response.content("ok").end();
	}

	/***
	 *  JQF支付成功通知 http://127.0.0.1/lwpay/feeJQF
	 */
	public void feeJQF(HttpRequest request, HttpResponse response)
			throws JuiceException {
		try {

		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
		}
		response.content("ok").end();
	}

	/***
	 * 优酷 支付成功通知 http://127.0.0.1/lwpay/feeYK
	 */
	public void feeYK(HttpRequest request, HttpResponse response)
			throws JuiceException {
		try {

		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
		}
		response.content("ok").end();
	}

	public FeeYXWX parseJson(String value) {
		FeeYXWX info = new FeeYXWX();
		JSONObject myObj = JSONObject.parseObject(value);

		String orderId = myObj.getString("orderid");
		String orderDate = myObj.getString("orderdate");
		String status = myObj.getString("ordersatus");
		String payType = myObj.getString("paytype");
		String payTypeName = myObj.getString("paytypename");
		String feeValue = myObj.getString("amt");
		int fee = convertFloat(feeValue);
		String extData = myObj.getString("reqOrderId");
		String imsi = myObj.getString("imsi");

		info.setOrderId(orderId);
		info.setOrderDate(orderDate);
		info.setOrderStatus(status);
		info.setPayType(payType);
		info.setPayTypeName(payTypeName);
		info.setFee(fee);
		info.setExtData(extData);
		info.setImsi(imsi);
		String[] params = extData.split("P");
		if (params.length >= 3) {
			String appId = params[0];
			String channel = params[1];
			String propId = params[2];

			info.setAppId(appId);
			info.setChannel(channel);
			info.setPropId(propId);
		}

		return info;
	}

	/***
	 * 深圳斯达通 支付成功通知 http://127.0.0.1/lwpay/feeSZSDT
	 */
	public void feeSZSDT(HttpRequest request, HttpResponse response)
			throws JuiceException {
		try {

			String message = request.getParam("message");// 计费代码(用户上行的信息)
			String orderid = request.getParam("orderid");// 交易流水号(唯一标识)
			String spnumber = request.getParam("spnumber");// 端口号
			String mobile = request.getParam("mobile");
			String tradeid = request.getParam("tradeid");// 订单交易id(交易流水，渠道方订单交易id)
			String channelid = request.getParam("channelid");// 渠道id
			String actiontime = request.getParam("actiontime");// 订单时间(2015-01-05T13:19:09)
			String reqsid = request.getParam("reqsid");// 计费请求sid(透传上行请求计费时所提交的sid参数)
			String extData = reqsid.substring(reqsid.indexOf("_") + 1);

			StringBuilder msg = new StringBuilder();
			msg.append("深圳斯达通\n");
			msg.append("message:").append(message).append("\n");
			msg.append("orderid:").append(orderid).append("\n");
			msg.append("spnumber:").append(spnumber).append("\n");
			msg.append("mobile:").append(mobile).append("\n");
			msg.append("tradeid:").append(tradeid).append("\n");
			msg.append("channelid:").append(channelid).append("\n");
			msg.append("actiontime:").append(actiontime).append("\n");
			msg.append("reqsid:").append(reqsid).append("\n");
			logger.info(msg.toString());

			FeeCommonInfo info = new FeeCommonInfo();
			info.setFeeCode(message);
			int fee = 0;
			if ("30000834957501".equals(message)) {
				fee = 1;
			} else if ("30000834957502".equals(message)) {
				fee = 2;
			} else if ("30000834957503".equals(message)) {
				fee = 4;
			} else if ("30000834957504".equals(message)) {
				fee = 5;
			} else if ("30000834957505".equals(message)) {
				fee = 6;
			} else if ("30000834957506".equals(message)) {
				fee = 8;
			} else {
				fee = 10;
			}
			info.setFee(fee);
			info.setOrderId(orderid);
			info.setPort(spnumber);
			info.setMobile(mobile);
			info.setTradeId(tradeid);
			info.setChannelId(channelid);
			info.setActiontime(actiontime);
			info.setExtdata(extData);
			if (reqsid.contains("_")) {
				info.setweimiOrderId(reqsid.substring(0, reqsid.indexOf("_")));
			}
			String[] params = extData.split("@");
			if (params.length >= 3) {
				String appId = params[0];
				String channel = params[1];
				String propId = params[2];

				info.setAppId(appId);
				info.setChannel(channel);
				info.setPropId(propId);
			}
			feeHelperAction.addFeeSZSDT(info);

		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
		}
		response.content("ok").end();
	}

	/***
	 * 北京指游 支付成功通知 http://127.0.0.1/lwpay/feeBJZY
	 */
	public void feeBJZY(HttpRequest request, HttpResponse response)
			throws JuiceException {
		try {
			String content = request.body();
			logger.info("北京指游回调信息：" + content);
			JSONObject myObj = JSONObject.parseObject(content);

			String provinceId = myObj.getString("provinceId");// 省份id
			String paymentType = myObj.getString("paymentType");// 支付类型 话费：1
																// 支付宝：2 银联：3
																// 充值卡游戏点卡：4
			String partnerType = myObj.getString("partnerType");// 合作方类型 1:cp
																// 2:渠道
			String serialNumber = myObj.getString("serialNumber");// 客户端发起计费时的流水号
			String billFee = myObj.getString("billFee");// 计费金额（单位：分）
			String result = myObj.getString("result");// 计费结果 0：成功 1：失败
			String appName = myObj.getString("appName");// 游戏名称
			String indexId = myObj.getString("indexId");// 此ID主要是标识每条同步的记录流水的惟一性
			String chargeTime = myObj.getString("chargeTime");// 平台显示的计费时间
			BJZYProvince[] pro = BJZYProvince.values();// 获取对应省份名字
			String provinceName = pro[convertInt(provinceId)].value;

			FeeCommonInfo info = new FeeCommonInfo();
			info.setProvinceId(provinceId);
			info.setPaymentType(paymentType);
			info.setPartnerType(partnerType);
			info.setweimiOrderId(serialNumber);
			info.setFee(convertInt(billFee));
			info.setResultCode(result);
			info.setAppName(appName);
			info.setOrderId(indexId);
			info.setActiontime(chargeTime);
			info.setProvinceName(provinceName);
			feeHelperAction.addFeeBJZY(info);

		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
		}
		response.content("{\"resultCode\":\"0\",\"resultMsg\":\"success\"}")
				.end();
	}

	/***
	 * (电盈)灵光互动 支付成功通知 http://127.0.0.1/lwpay/feeLGHD
	 */
	public void feeLGHD(HttpRequest request, HttpResponse response)
			throws JuiceException {
		try {
			String mobile = request.getParam("Mobile");// 上行信息的手机号
			// String spNumber = request.getParam("SpNumber");
			String moContent = request.getParam("MoContent");// 短信内容
			String linkId = request.getParam("LinkID");// 移动通讯会话标识
			String moTime = request.getParam("MoTime");// 上行时间
			String feeCode = request.getParam("FeeCode");// 代码费率
			String rptStat = request.getParam("RptStat");// 状态报告

			StringBuilder msg = new StringBuilder();
			msg.append("灵光互动{");
			msg.append("Mobile:").append(mobile).append("|");
			msg.append("MoContent:").append(moContent).append("|");
			msg.append("LinkID:").append(linkId).append("|");
			msg.append("MoTime:").append(moTime).append("|");
			msg.append("FeeCode:").append(feeCode).append("|");
			msg.append("RptStat:").append(rptStat).append("|");
			logger.info(msg.toString());

			FeeCommonInfo info = new FeeCommonInfo();
			info.setMobile(mobile);
			info.setContent(moContent);
			info.setOrderId(linkId);
			info.setActiontime(moTime);
			info.setFee(convertInt(feeCode));
			if ("DELIVRD".equals(rptStat)) {
				info.setResultCode("0");
			} else {
				info.setResultCode("1");
			}
			feeHelperAction.addFeeLGHD(info);
		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
		}
		response.content("ok").end();
	}

	/***
	 * 深圳斯达通 支付成功通知 http://127.0.0.1/lwpay/feeXSZSDT
	 */
	public void feeXSZSDT(HttpRequest request, HttpResponse response)
			throws JuiceException {
		try {

			String content = request.body();
			String ip = getRealIP(request);
			logger.info("read xmlStr: " + content + "; ip:" + ip);
			// SyncXMLUtils utils = new SyncXMLUtils();
			SyncAppOrderReq sao = XMLUtils.getInstance().parseXML2AppOrderReq(
					content);
			logger.info("syncAppOrderReq:" + sao.getAppID());
			//
			FeeXSZSDT info = new FeeXSZSDT();
			info.setOrderId(sao.getOrderID());
			info.setTradeId(sao.getTradeID());
			info.setAppId(sao.getAppID());
			info.setChannelId(sao.getChannelID());
			info.setActionId(sao.getActionID());
			info.setActionTime(DateUtils.convertString(sao.getActionTime()));
			info.setMsisdn(sao.getMSISDN());
			info.setMsisdnFee(sao.getFeeMSISDN());
			info.setPayCode(sao.getPayCode());
			info.setPrice(sao.getPrice());
			info.setNum(sao.getSubsNumb());
			info.setTotalPrice(sao.getTotalPrice());
			info.setExt(sao.getExData());
			// 返回消息转为vo
			/*
			 * SyncAppOrderResp syncAppOrderResp = new SyncAppOrderResp();
			 * syncAppOrderResp.setMsgType("SyncAppOrderResp");
			 * syncAppOrderResp.setVersion("1.0.0");
			 * syncAppOrderResp.sethRet(0); String result =
			 * utils.vo2Xml(syncAppOrderResp, "SyncAppOrderResp");
			 */
			StringBuilder msg = new StringBuilder();
			msg.append("新深圳斯达通：{");
			msg.append("OrderId:").append(sao.getOrderID()).append("|");
			msg.append("TradeId:").append(sao.getTradeID()).append("|");
			msg.append("AppId:").append(sao.getAppID()).append("|");
			msg.append("ChannelId:").append(sao.getChannelID()).append("|");
			msg.append("ActionId:").append(sao.getActionID()).append("|");
			msg.append("ActionTime:")
					.append(DateUtils.convertString(sao.getActionTime()))
					.append("|");
			msg.append("Msisdn:").append(sao.getMSISDN()).append("|");
			msg.append("MsisdnFee:").append(sao.getFeeMSISDN()).append("|");
			msg.append("PayCode:").append(sao.getPayCode()).append("|");
			msg.append("Price:").append(sao.getPrice()).append("|");
			msg.append("Num:").append(sao.getSubsNumb()).append("|");
			msg.append("TotalPrice:").append(sao.getTotalPrice()).append("|");
			msg.append("Ext:").append(sao.getExData()).append("}");
			logger.info(msg);

			feeHelperAction.addFeeXSZSDT(info);
		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
		}

		response.content("success").end();
	}

	/***
	 * 广州游发 支付成功通知 http://127.0.0.1/lwpay/feeGZYF
	 */
	public void feeGZYF(HttpRequest request, HttpResponse response)
			throws JuiceException {
		try {

			String message = request.getParam("message");// 计费代码(用户上行的信息)
			String orderid = request.getParam("orderid");// 交易流水号(唯一标识)
			String spnumber = request.getParam("spnumber");// 端口号
			String mobile = request.getParam("mobile");
			String tradeid = request.getParam("tradeid");// 订单交易id(交易流水，渠道方订单交易id)
			String channelid = request.getParam("channelid");// 渠道id
			String actiontime = request.getParam("actiontime");// 订单时间(2015-01-05T13:19:09)
			String reqsid = request.getParam("reqsid");// 计费请求sid(透传上行请求计费时所提交的sid参数)
			String extData = reqsid.substring(reqsid.indexOf("_") + 1);

			StringBuilder msg = new StringBuilder();
			msg.append("广州游发\n");
			msg.append("message:").append(message).append("\n");
			msg.append("orderid:").append(orderid).append("\n");
			msg.append("spnumber:").append(spnumber).append("\n");
			msg.append("mobile:").append(mobile).append("\n");
			msg.append("tradeid:").append(tradeid).append("\n");
			msg.append("channelid:").append(channelid).append("\n");
			msg.append("actiontime:").append(actiontime).append("\n");
			msg.append("reqsid:").append(reqsid).append("\n");
			logger.info(msg.toString());

			FeeCommonInfo info = new FeeCommonInfo();
			info.setFeeCode(message);
			int fee = 0;
			if ("30000882402405".equals(message)) {
				fee = 2;
			} else if ("30000882402401".equals(message)) {
				fee = 4;
			} else if ("30000882402406".equals(message)) {
				fee = 6;
			} else if ("30000882402403".equals(message)) {
				fee = 10;
			}
			info.setFee(fee);
			info.setOrderId(orderid);
			info.setPort(spnumber);
			info.setMobile(mobile);
			info.setTradeId(tradeid);
			info.setChannelId(channelid);
			info.setActiontime(actiontime);
			info.setExtdata(extData);
			if (reqsid.contains("_")) {
				info.setweimiOrderId(reqsid.substring(0, reqsid.indexOf("_")));
			}
			String[] params = extData.split("@");
			if (params.length >= 3) {
				String appId = params[0];
				String channel = params[1];
				String propId = params[2];

				info.setAppId(appId);
				info.setChannel(channel);
				info.setPropId(propId);
			}
			feeHelperAction.addFeeGZYF(info);

		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
		}
		response.content("ok").end();
	}

	/***
	 * YFMP 支付成功通知 http://127.0.0.1/lwpay/feeYFMP
	 */
	public void feeYFMP(HttpRequest request, HttpResponse response)
			throws JuiceException {
		try {

			String content = request.body();
			String ip = getRealIP(request);
			logger.info("read xmlStr: " + content + "; ip:" + ip);
			// SyncXMLUtils utils = new SyncXMLUtils();
			SyncAppOrderReq sao = XMLUtils.getInstance().parseXML2AppOrderReq(
					content);
			logger.info("syncAppOrderReq:" + sao.getAppID());
			//
			FeeMM info = new FeeMM();
			info.setOrderId(sao.getOrderID());
			info.setTradeId(sao.getTradeID());
			info.setAppId(sao.getAppID());
			info.setChannelId(sao.getChannelID());
			info.setActionId(sao.getActionID());
			info.setActionTime(DateUtils.convertString(sao.getActionTime()));
			info.setMsisdn(sao.getMSISDN());
			info.setMsisdnFee(sao.getFeeMSISDN());
			info.setPayCode(sao.getPayCode());
			info.setPrice(sao.getPrice());
			info.setNum(sao.getSubsNumb());
			info.setTotalPrice(sao.getTotalPrice());
			String ext = sao.getExData();
			info.setExt(ext);
			// 返回消息转为vo
			/*
			 * SyncAppOrderResp syncAppOrderResp = new SyncAppOrderResp();
			 * syncAppOrderResp.setMsgType("SyncAppOrderResp");
			 * syncAppOrderResp.setVersion("1.0.0");
			 * syncAppOrderResp.sethRet(0); String result =
			 * utils.vo2Xml(syncAppOrderResp, "SyncAppOrderResp");
			 */
			StringBuilder msg = new StringBuilder();
			msg.append("YFMP：{");
			msg.append("OrderId:").append(sao.getOrderID()).append("|");
			msg.append("TradeId:").append(sao.getTradeID()).append("|");
			msg.append("AppId:").append(sao.getAppID()).append("|");
			msg.append("ChannelId:").append(sao.getChannelID()).append("|");
			msg.append("ActionId:").append(sao.getActionID()).append("|");
			msg.append("ActionTime:")
					.append(DateUtils.convertDate(DateUtils.convertString(sao
							.getActionTime()))).append("|");
			msg.append("Msisdn:").append(sao.getMSISDN()).append("|");
			msg.append("MsisdnFee:").append(sao.getFeeMSISDN()).append("|");
			msg.append("PayCode:").append(sao.getPayCode()).append("|");
			msg.append("Price:").append(sao.getPrice()).append("|");
			msg.append("Num:").append(sao.getSubsNumb()).append("|");
			msg.append("TotalPrice:").append(sao.getTotalPrice()).append("|");
			msg.append("Ext:").append(ext).append("}");
			logger.info(msg);

			if (ext.contains("_")) {
				info.setweimiOrderId(ext.substring(0, ext.indexOf("_")));
			}
			String extData = ext.substring(ext.indexOf("_") + 1);
			String[] params = extData.split("@");
			if (params.length >= 3) {
				String appId = params[0];
				String channel = params[1];
				String propId = params[2];

				info.setAppId(appId);
				info.setChannel(channel);
				info.setPropId(propId.replace("\"559", ""));
			}
			feeHelperAction.addFeeYFMP(info);

		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
		}
		response.content("ok").end();
	}

	/***
	 * 万贝移动 支付成功通知 http://127.0.0.1/lwpay/feeWBYD
	 */
	public void feeWBYD(HttpRequest request, HttpResponse response)
			throws JuiceException {
		try {
			String mobile = request.getParam("mobile");
			String spnumber = request.getParam("spnumber");
			String linkId = request.getParam("linkid");
			String momsg = request.getParam("momsg");
			String cpparam = request.getParam("cpparam").replace("KUAIL", "");
			String price = request.getParam("price");

			FeeWB info = new FeeWB();
			info.setMobile(mobile);
			info.setSpnumber(spnumber);
			info.setLinkId(linkId);
			info.setMomsg(momsg);
			info.setFee(CommonTools.convertInt(price));
			info.setExt(cpparam);
			String[] params = cpparam.split("\\@");
			if (params.length == 3) {
				String appId = params[0];
				String channel = params[1];
				String propId = params[2];

				info.setAppId(appId);
				info.setChannel(channel);
				info.setPropId(propId);
			}
			feeHelperAction.addFeeWBYD(info);
		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
		}
		response.content("ok").end();
	}
	
	/***
	 * DTYD 支付成功通知 http://127.0.0.1/lwpay/feeDTYD
	 */
	public void feeDTYD(HttpRequest request, HttpResponse response)
			throws JuiceException {
		try {
			String content = request.body();
			logger.info("DTYD回调信息："+content);
			JSONObject myObj = JSONObject.parseObject(content);
			
			String result = myObj.getString("result");
			String description = myObj.getString("description");
			String tradeNo = myObj.getString("tradeNo");
			String mobile = myObj.getString("mobilePrefix");
//			String spnumber = myObj.getString("spnumber");
//			String linkId = myObj.getString("linkid");
//			String momsg = myObj.getString("momsg");
			String extData = myObj.getString("callbackData");
			String fee = myObj.getString("fee");
			
			FeeCommonInfo info = new FeeCommonInfo();
			info.setResultCode(result);
			info.setDescription(description);
			info.setMobile(mobile);
			info.setOrderId(tradeNo);
			info.setFee(CommonTools.convertInt(fee));
			info.setExtdata(extData);
			String[] params = extData.split("\\@");
			if (params.length == 3) {
				String appId = params[0];
				String channel = params[1];
				String propId = params[2];

				info.setAppId(appId);
				info.setChannel(channel);
				info.setPropId(propId);
			}
			feeHelperAction.addFeeDTYD(info);
		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
		}
		response.content("200").end();
	}
	
	
	

	
	
	private int convertInt(String value) {
		int result = 0;
		try {
			result = Integer.parseInt(value);
		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
		}
		return result;
	}

	private int convertFloat(String value) {
		int result = 0;
		try {
			Float feeValue = Float.parseFloat(value);
			result = feeValue.intValue();
		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
		}
		return result;
	}

	private Map<String, String> parseYZ(String content) {
		Map<String, String> params = new HashMap<String, String>();
		try {
			StringTokenizer st = new StringTokenizer(content, "&");
			while (st.hasMoreTokens()) {
				String[] pair = st.nextToken().split("=");
				String key = URLDecoder.decode(pair[0], "UTF-8");
				String value = pair.length == 1 ? null : URLDecoder.decode(
						pair[1], "UTF-8");
				params.put(key, value);
			}
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Couldn't parse query string: "
					+ content, e);
		}
		return params;
	}

	private enum BJZYProvince {
		PRO_0("省份为空"), PRO_1("省份为空"), PRO_2("省份为空"), PRO_3("北京"), PRO_4("安徽"), PRO_5(
				"四川"), PRO_6("重庆"), PRO_7("福建"), PRO_8("甘肃"), PRO_9("广东"), PRO_10(
				"广西"), PRO_11("贵州"), PRO_12("海南"), PRO_13("河北"), PRO_14("河南"), PRO_15(
				"黑龙江"), PRO_16("湖北"), PRO_17("湖南"), PRO_18("吉林"), PRO_19("江苏"), PRO_20(
				"江西"), PRO_21("辽宁"), PRO_22("内蒙古"), PRO_23("宁夏"), PRO_24("青海"), PRO_25(
				"山东"), PRO_26("山西"), PRO_27("陕西"), PRO_28("上海"), PRO_29("天津"), PRO_30(
				"西藏"), PRO_31("新疆"), PRO_32("云南"), PRO_33("浙江");
		String value;

		BJZYProvince(String value) {
			this.value = value;
		}
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
	
	/***
	 * 测试端口 http://127.0.0.1/lwpay/test
	 */
//	public void test(HttpRequest request, HttpResponse response) {
//		String retUrl = "http://127.0.0.1:9991/code/test";
//		StringBuilder httpReq = new StringBuilder();
//		httpReq.append("mobile=13373498245&channelId=57&linkId=03031707190000024641&longCode=10660973&msg=fx%2357%23xmxx20121017%40lwxx8001%40003&mac=9704317C24A260549095D393A745B2BB");
//		String result = HttpTool.sendHttp(retUrl, httpReq.toString());
//		System.out.println(result);
//		response.content(result).end();
//	}
}
