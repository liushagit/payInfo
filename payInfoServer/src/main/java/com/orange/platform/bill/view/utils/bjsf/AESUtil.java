package com.orange.platform.bill.view.utils.bjsf;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.jboss.netty.handler.codec.base64.Base64Decoder;

import sun.misc.BASE64Decoder;

import sun.misc.BASE64Encoder;

import com.orange.platform.bill.view.utils.Base64;
//import com.alibaba.fastjson.util.Base64;
import com.orange.platform.bill.view.utils.URLUtil;

public class AESUtil {

	/**
	 * 加密
	 * 
	 * @param content
	 * @param password
	 * @return
	 */
	public static byte[] encrypt(String content, String password) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(password.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(byteContent);
			return result; // 加密
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密
	 * 
	 * @param content
	 * @param password
	 * @return
	 */
	public static byte[] decrypt(byte[] content, String password) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			random.setSeed(password.getBytes());
			kgen.init(128,random);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(content);
			return result; // 加密
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		try {
			
			String t = "测试aaa";
			
			String key = "key";
			byte aes[] = encrypt(t, key);
			String s = new BASE64Encoder().encode(aes);
			System.out.println(s); 
			byte b[] = new BASE64Decoder().decodeBuffer(s);
			String res = new String(decrypt(b, key));
			System.out.println(res);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		// // String msg = "测试使用aaa";
		// // String key = "key";
		// // byte aes[] = encrypt(msg, key);
		//
		// // System.out.println(new String(aes));
		// String msg =
		// "WFkACRdhYHFxXjob2asdEOZuh%2F%2BprE%2FZI2Ggjs%2BxwxJNTk3j0STrogkln0GNhl%2BNkHefDWERW6kX0XeYR4kFTU%2BgvkKpfTif0OjcynVgggY%3D";
		// try {
		// // msg = URLUtil.decode(msg);
		// // System.out.println(msg);
		// // byte b[] = Base64.decodeFast(msg);
		// // System.out.println(new String(b));
		// // byte re[] = decrypt(b, "1E10B0670331DE6F");
		// System.out.println(aesDecrypt(msg, "1E10B0670331DE6F"));
		// // System.out.println(new String(re));
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		test();

	}

	public static String aesDecrypt(String str, String key) throws Exception {

		// if (str == null || key == null) return null;
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE,
				new SecretKeySpec(key.getBytes("utf-8"), "AES"));
		// str = URLUtil.decode(str);
		// str = str.replaceAll(" ", "");

		byte[] bytes = null;
		// String s = new String(bytes,"utf-8");
		bytes = new BASE64Decoder().decodeBuffer(str);
		int re = bytes.length % 16;
		if (re != 0) {
			int length = bytes.length + 16 - re;
			byte b[] = new byte[length];
			System.arraycopy(bytes, 0, b, 0, bytes.length);
			bytes = b;
		}
		bytes = cipher.doFinal(bytes);
		return new String(bytes, "utf-8");
	}

	private static String test() {
		// String stringB =
		// "80, 114, 105, 109, 61, 74, 74, 119, 81, 115, 110, 110, 66, 48, 116, 100, 117, 65, 82, 57, 109, 66, 72, 86, 57, 51, 104, 48, 100, 108, 56, 120, 57, 74, 57, 109, 70, 72, 113, 67, 82, 67, 102, 90, 104, 84, 84, 82, 50, 75, 48, 90, 70, 69, 70, 100, 110, 97, 102, 99, 54, 84, 116, 56, 49, 119, 118, 112, 99, 112, 57, 87, 105, 55, 103, 121, 74, 113, 48, 37, 50, 66, 118, 98, 87, 111, 50, 102, 117, 82, 72, 48, 90, 98, 85, 88, 55, 86, 56, 106, 37, 50, 70, 49, 49, 89, 86, 84, 65, 76, 79, 101, 112, 89, 74, 48, 37, 51, 68, 38, 77, 99, 104, 73, 100, 61, 77, 68, 89, 75, 74";
		// String p =
		// "Prim=JJwQsnnB0tduAR9mBHV93h0dl8x9J9mFHqCRCfZhTTR2K0ZFEFdnafc6Tt81wvpcp9Wi7gyJq0%2BvbWo2fuRH0ZbUX7V8j%2F11YVTALOepYJ0%3D&MchId=MDYKJ";
		// String s[] = stringB.split(", ");
		// byte b[] = new byte[s.length];
		// for (int i = 0; i < s.length; i++) {
		// b[i] = Byte.valueOf(s[i]);
		// }
		// try {
		// System.out.println(new String(b,"utf-8"));
		// } catch (UnsupportedEncodingException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// System.out.println(p);
		// return "";
		try {
			String str = "JJwQsnnB0tduAR9mBHV93h0dl8x9J9mFHqCRCfZhTTR2K0ZFEFdnafc6Tt81wvpcp9Wi7gyJq0%2BvbWo2fuRH0ZbUX7V8j%2F11YVTALOepYJ0%3D";
			str = URLUtil.decode(str);
			System.out.println(str);
//			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
//			cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(
//					"1E10B0670331DE6F".getBytes("utf-8"), "AES"));
			byte[] bytes = new BASE64Decoder().decodeBuffer(str);
//			str = new BASE64Encoder().encode(bytes);
//			System.out.println(str);
//			bytes = new BASE64Decoder().decodeBuffer(str);
//			String s = parseByte2HexStr(bytes);
//			bytes = parseHexStr2Byte(s);
//			bytes = cipher.doFinal(bytes);
//			System.out.println(new String(bytes, "utf-8"));
//			byte b[] = DecryptToBytes(bytes, "1E10B0670331DE6F".getBytes("utf-8"));
//			encrypt(str, "1E10B0670331DE6F");
			byte b1[] = decrypt(bytes, "1E10B0670331DE6F");
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		// String res = ebotongDecrypto(str);
		// System.out.println(res);
		return "";
	}
	
	public static byte[] parseHexStr2Byte(String hexStr) {  
        if (hexStr.length() < 1)  
                return null;  
        byte[] result = new byte[hexStr.length()/2];  
        for (int i = 0;i< hexStr.length()/2; i++) {  
                int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);  
                int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);  
                result[i] = (byte) (high * 16 + low);  
        }  
        return result;  
}  
	
	public static String parseByte2HexStr(byte buf[]) {  
        StringBuffer sb = new StringBuffer();  
        for (int i = 0; i < buf.length; i++) {  
                String hex = Integer.toHexString(buf[i] & 0xFF);  
                if (hex.length() == 1) {  
                        hex = '0' + hex;  
                }  
                sb.append(hex.toUpperCase());  
        }  
        return sb.toString();  
}  

	public static String ebotongDecrypto(String str) {
		String result = str;
		if (str != null && str.length() > 0) {
			try {
				byte[] encodeByte = base64decoder.decodeBuffer(str);
				byte[] decoder = symmetricDecrypto(encodeByte);
				result = new String(decoder, "utf-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static byte[] symmetricDecrypto(byte[] byteSource) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			int mode = Cipher.DECRYPT_MODE;
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("AES");
			byte[] keyData = { 1, 9, 8, 2, 0, 8, 2, 1 };
			DESKeySpec keySpec = new DESKeySpec(keyData);
			Key key = keyFactory.generateSecret(keySpec);
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(mode, key);
			byte[] result = cipher.doFinal(byteSource);
			// int blockSize = cipher.getBlockSize();
			// int position = 0;
			// int length = byteSource.length;
			// boolean more = true;
			// while (more) {
			// if (position + blockSize <= length) {
			// baos.write(cipher.update(byteSource, position, blockSize));
			// position += blockSize;
			// } else {
			// more = false;
			// }
			// }
			// if (position < length) {
			// baos.write(cipher.doFinal(byteSource, position, length
			// - position));
			// } else {
			// baos.write(cipher.doFinal());
			// }
			// return baos.toByteArray();
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			baos.close();
		}
	}

	private static final BASE64Encoder base64encoder = new BASE64Encoder();
	private static final BASE64Decoder base64decoder = new BASE64Decoder();

	public final class EbotongSecurity {
		// The length of Encryptionstring should be 8 bytes and not be
		// a weak key

		private final static String encoding = "UTF-8";

		/**
		 * 加密字符串
		 */
		public String ebotongEncrypto(String str) {
			String result = str;
			if (str != null && str.length() > 0) {
				try {
					byte[] encodeByte = symmetricEncrypto(str
							.getBytes(encoding));
					result = base64encoder.encode(encodeByte);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return result;
		}
	}

	public static byte[] symmetricEncrypto(byte[] byteSource) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			int mode = Cipher.ENCRYPT_MODE;
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			byte[] keyData = { 1, 9, 8, 2, 0, 8, 2, 1 };
			DESKeySpec keySpec = new DESKeySpec(keyData);
			Key key = keyFactory.generateSecret(keySpec);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(mode, key);
			byte[] result = cipher.doFinal(byteSource);
			// int blockSize = cipher.getBlockSize();
			// int position = 0;
			// int length = byteSource.length;
			// boolean more = true;
			// while (more) {
			// if (position + blockSize <= length) {
			// baos.write(cipher.update(byteSource, position, blockSize));
			// position += blockSize;
			// } else {
			// more = false;
			// }
			// }
			// if (position < length) {
			// baos.write(cipher.doFinal(byteSource, position, length
			// - position));
			// } else {
			// baos.write(cipher.doFinal());
			// }
			// return baos.toByteArray();
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			baos.close();
		}
	}

	static boolean isInited = false;
	static final String algorithmStr = "AES/ECB/PKCS5Padding";

	static private KeyGenerator keyGen;

	static private Cipher cipher;

	// 初始化
	static private void init() {

		// 初始化keyGen
		try {
			keyGen = KeyGenerator.getInstance("AES");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		keyGen.init(128);

		// 初始化cipher
		try {
			cipher = Cipher.getInstance(algorithmStr);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		isInited = true;
	}

	// 解密为byte[]
	public static byte[] DecryptToBytes(byte[] content, byte[] keyBytes) {
		byte[] originBytes = null;
		if (!isInited) {
			init();
		}

		Key key = new SecretKeySpec(keyBytes, "AES");

		try {
			cipher.init(Cipher.DECRYPT_MODE, key);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 解密
		try {
			originBytes = cipher.doFinal(content);
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return originBytes;
	}

}
