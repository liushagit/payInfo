/**
 * weimiPayCommon
 */
package com.orange.platform.bill.common.utils;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.concurrent.atomic.AtomicInteger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.orange.platform.bill.common.domain.FeeWQ;
import com.payinfo.net.exception.JuiceException;
import com.payinfo.net.log.LoggerFactory;

/**
 * @author weimiplayer.com
 *
 * 2012年10月29日
 */
public class CommonTools {
	private static org.apache.log4j.Logger logger = LoggerFactory.getLogger(CommonTools.class);
	
	public static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static AtomicInteger atid = new AtomicInteger(1);
	public static Random random = new Random();
	public static char[] params = {'0','1','2','3','4','5','6','7','8','9', 'q', 'w','e',
		'r','t','y','u','i','o','p','a','s','d','f','g','h','j','k','l','z','x','c','v','b','n','m'};
	
	/**
	 * 获取电话制式
	 * 定位运营商类型
	 * 
	 * 5,6两位就可分辨了
	 * 00,02为移动
	 * 01为联通
	 * 03为电信
	 */
	public static final int locateOperator(String imsi) {
		try {
			if (imsi == null || imsi.length()<15) {
				throw new JuiceException("IMSI:" + imsi + " 格式错误！");
			}
			
			String mcc = imsi.substring(0, 3);
			if (!mcc.equals("460")) {
				throw new JuiceException("IMSI mcc:" + mcc + " 不属于中国!");
			}
			
			// 划定区分网址：http://en.wikipedia.org/wiki/Mobile_country_code
			String mnc = imsi.substring(3, 5);
			if (mnc.equals("00") || mnc.equals("02") || mnc.equals("07")) {
				return ConstantDefine.CODE_TYPE_MOBILE;
			}
			
			if (mnc.equals("01") || mnc.equals("06") || mnc.equals("20")) {
				return ConstantDefine.CODE_TYPE_UNICOM;
			}
			
			if (mnc.equals("03") || mnc.equals("05")) {
				return ConstantDefine.CODE_TYPE_TELNET;
			}
			
			throw new JuiceException("IMSI mnc:" + mnc + " 运营商不能区分！"); 
		} catch (IndexOutOfBoundsException e) {
			logger.error("根据IMSI获取手机号码类型错误");
		} catch (Exception e) {
			logger.error("根据IMSI获取手机号码类型错误");
		}
		return ConstantDefine.CODE_TYPE_MOBILE;
	}

	/**
	 * 获取运营商名称
	 */
	public static final String locateOperatorName(String imsi) {
		switch (locateOperator(imsi)) {
		case ConstantDefine.CODE_TYPE_UNICOM:
			return "联通";
		case ConstantDefine.CODE_TYPE_TELNET:
			return "电信";
		default:
			return "移动";
		}
	}
	
	public static final int locateOperatorCode(String name) {
		if (name == null) return ConstantDefine.CODE_TYPE_MOBILE;
		if (name.contains("联通")) {
			return ConstantDefine.CODE_TYPE_UNICOM;
		} else if (name.contains("电信")) {
			return ConstantDefine.CODE_TYPE_TELNET;
		}
		return ConstantDefine.CODE_TYPE_MOBILE;
	}
	
	
	/**订单号生产规则*/
	public static final String createOrderNO(){
		try {
			Date date = format.parse("1970-01-01 00:00:00");
			int timeSpace = (int)(Calendar.getInstance().getTimeInMillis() - date.getTime())/1000;
			
			StringBuilder uid = new StringBuilder();
			int randomValue = (int)(random.nextFloat() * 100000);
			uid.append(timeSpace).append(getStrValue(getSeqId(), 4)).append(getStrValue(randomValue, 5));
			return uid.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
		}
		return null;
	}
	
	/** 获取序列号*/
	private static int getSeqId() {
		if (atid.get()==9999) {
			atid.set(1);
		}
		return atid.getAndIncrement();
	}
	
	/** 数字填零格式化*/
	private static String getStrValue(int param, int length) {
		String value = String.valueOf(param);
		
		StringBuilder msg = new StringBuilder();
		for (int i=0; i<(length-value.length());i++){
			msg.append("0");
		}
		msg.append(param);
		return msg.toString();
	}
	
	
	public static final int getFee(String feeCode) {
		int fee = 0;
		if (feeCode.equals("1010001")) {
			fee = 1;
		} else if (feeCode.equals("1010002")) {
			fee = 2;
		} else if (feeCode.equals("02000003")) {
			fee = 3;
		} else if (feeCode.equals("02000004")) {
			fee = 4;
		} else if (feeCode.equals("1010003")) {
			fee = 5;
		} else if (feeCode.equals("02000006")) {
			fee = 6;
		} else if (feeCode.equals("1010004")) {
			fee = 8;
		} else if (feeCode.equals("1010005")) {
			fee = 10;
		} else if (feeCode.equals("1010006")) {
			fee = 15;
		}

		return fee;
	}
	
	
	public static final int getFee1(String feeCode) {
		int fee = 0;
		if (feeCode.equals("23000002")) {
			fee = 2;
		} else if (feeCode.equals("23000004")) {
			fee = 4;
		} else if (feeCode.equals("23000006")) {
			fee = 6;
		} else if (feeCode.equals("23000008")) {
			fee = 8;
		} else if (feeCode.equals("23000010")) {
			fee = 10;
		}

		return fee;
	}
	
	public static final int getFeeLTDX(String destAddr) {
		int fee = 0;
		if (destAddr.equals("10661025")) {
			fee = 4;
		} else if (destAddr.equals("106598725")) {
			fee = 6;
		} else if (destAddr.equals("1065987216")) {
			fee = 10;
		}
		return fee;
	}
	
	
	public static Map<String, String> parseLTLT(String content) {
		Map<String, String> paramMap = new HashMap<String, String>();
		try {
			StringTokenizer st = new StringTokenizer(content, "&");
			while (st.hasMoreTokens()) {
				String[] pair = st.nextToken().split("=");
				String key = pair[0];
				String value = pair.length == 1 ? null : pair[1];
				if (key != null && value != null) {
					paramMap.put(key, value);
				}
			}
		} catch (Exception e) {
			logger.error("Couldn't parse param string: " + content, e);
		}
		return paramMap;
	}
	
	public static String parseHJMM(Document doc, String key) throws Exception {
		NodeList keyNodes = doc.getElementsByTagName(key);
		Node keyNode = keyNodes.item(0);
		String keyValue = keyNode.getTextContent();
		return keyValue;
	}
	
	public static FeeWQ parseWQ(InputStream in) throws Exception {
		FeeWQ info = new FeeWQ();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
        DocumentBuilder builder = factory.newDocumentBuilder();  
        Document doc = builder.parse(in);  
        NodeList nodeList = doc.getElementsByTagName("sync_data");  
        int totalFee = 0;
        for (int i = 0; i < nodeList.getLength(); i++) {  
            Element  ele = (Element)nodeList.item(i);
            
            NodeList status = ele.getElementsByTagName("ChargeStatus");
            Node statusNode = status.item(0);
            String state = statusNode.getTextContent();
            if (!state.equals("0"))continue;
            
            NodeList prices = ele.getElementsByTagName("Price");
            Node priceNode = prices.item(0);
            int fee = convertInt(priceNode.getTextContent());
            
            NodeList mchnos = ele.getElementsByTagName("ServiceID");
            Node mchnoNode = mchnos.item(0);
            String mchNo = mchnoNode.getTextContent();
            
            NodeList imsis = ele.getElementsByTagName("imsi");
            Node imsiNode = imsis.item(0);
            String imsi = imsiNode.getTextContent();
            
            NodeList extDatas = ele.getElementsByTagName("ExtData");
            Node extDataNode = extDatas.item(0);
            String extData = extDataNode.getTextContent();
            
            totalFee += fee;
            info.setImsi(imsi);
            info.setMchNo(mchNo);
            info.setExtData(extData);
        }
		
        if (totalFee == 0) {
        	return null;
        }
        
        info.setFee(totalFee);
        return info;
	}
	
	public static int convertInt(String value) {
		int result = 0;
		try {
			result = Integer.parseInt(value);
		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
		}
		return result;
	}
	
	/**
	 * 校验IMSI
	 */
	public static boolean IMSICheck(String imsi) {
		boolean result = true;
		if (imsi == null || imsi.length()<15) {
			result = false;
		}
		return result;
	}
	
	/**
	 * 校验IMEI
	 */
	public static boolean IMEICheck(String imei) {
		boolean result = true;
		if (imei == null || imei.length()<12) {
			result = false;
		}
		return result;
	}
	
	
	public static void main(String[] args) {
		for (int i=0;i<100;i++) {
			System.out.println(createOrderNO());
		}
	}
}
