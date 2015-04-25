package com.orange.platform.bill.view.utils.bjsf;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

import com.orange.platform.bill.view.server.MiFeedBack;
import com.orange.platform.bill.view.utils.URLUtil;
import com.payinfo.net.log.LoggerFactory;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 通用 AES加密解密类库
 * (无需修改)
 * @author onnes
 *
 */
public class AES {
	
	private static Logger logger = LoggerFactory.getLogger(MiFeedBack.class);
	private AES() {
	}

	private static String oldKey = "1E10B0670331DE6F";

	public static String getOldKey() {
		return oldKey;
	}

	/**
	 * @param args
	 */
	// 加密
	public static String encrypt(String sSrc, String sKey) throws Exception {
		try {
			// ||sKey.equals("")
			if (sKey == null) {
				// System.out.print("Key为空null");
				sKey = getOldKey();
				// return null;
			}
			// 判断Key是否为16位
			if (sKey.length() != 16) {
				// System.out.print("Key长度不是16位");
				return null;
			}
			byte[] raw = sKey.getBytes();
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// "算法/模式/补码方式"
			IvParameterSpec iv = new IvParameterSpec("L+\\~f4,Ir)b$=pkf"
					.getBytes());
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			byte[] encrypted = cipher.doFinal(sSrc.getBytes());

			return new BASE64Encoder().encode(encrypted);// 此处使用BASE64做转码功能
		} catch (Exception ex) {

			return null;
		}
	}

	// 解密
	public static String decrypt(String sSrc, String sKey) throws Exception {
		try {
			// 判断Key是否正确
			// ||sKey.equals("")
			if (sKey == null) {
				//System.out.print("Key为空null");
				sKey = getOldKey();
				// return null;
			}
			// 判断Key是否为16位
			if (sKey.length() != 16) {
				//System.out.print("Key长度不是16位");
				return null;
			}
			byte[] raw = sKey.getBytes("ASCII");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec("L+\\~f4,Ir)b$=pkf"
					.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);// 先用base64解密
			try {
				byte[] original = cipher.doFinal(encrypted1);
				String originalString = new String(original);

				return originalString;
			} catch (Exception e) {
				logger.error("aes exception", e);

				return null;
			}
		} catch (Exception ex) {
			System.out.println(ex.toString());

			return null;
		}
	}
	
	public static void main(String[] args) {
		try {
			String s = URLUtil.decode("WtA6AyLt xOMeLCV0DfHm3UG3gcYL1ibD5rl3bVWr 0LObnpPyf25ns4Mek6RW/Q2Xp9bj n/iXocRtC3tsB iU7iaoiRso7l6kLS9hsPnfx9KKJehh KwV72DQQuQAr");
			System.out.println(s);
			s = URLUtil.decode(s);
			s = s.replaceAll(" ", "+");
			System.out.println(s);
			String res = decrypt(s, null);
			String prim[] = res.split("~");
			/** 订单号流水 */
			String mchNo = prim[0];
			/** 用户电话 */
			String phone = prim[1];
			/** 信息费 */
			String fee = prim[2];
			/** 订单号 */
			String orderId = prim[3];
			/** 类型 */
			String mobileType = prim[4];
			System.out.println(mchNo + ":" + phone + ":" + fee + ":" + orderId + ":" +mobileType);
			System.err.println(res);
		} catch (Exception e) {
			// TODO Auto-    generated catch block
			e.printStackTrace();
		}
	}

}