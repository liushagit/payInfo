package com.orange.platform.bill.view.utils.bjsf;

import java.net.URLDecoder;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;

public class AES1 {

 private static final String KEY = "1E10B0670331DE6F";//从服务器要的密钥

    public static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";
   
    private static IvParameterSpec iv;
 public static String AES_VI = "L+\\~f4,Ir)b$=pkf";
//	public static String AES_VI = "1E10B0670331DE6F";
    /**
     * 解密
     * @param content
     *            待解密内容
     * @return
     */
    public static byte[] decrypt(byte[] data) throws Exception {

        Key k = toKey(KEY.getBytes());

        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        iv = new IvParameterSpec(AES_VI.getBytes("UTF-8"));
        cipher.init(Cipher.DECRYPT_MODE, k,iv);

        return cipher.doFinal(data);
    }

    private static Key toKey(byte[] key) throws Exception {

        SecretKey secretKey = new SecretKeySpec(key, "AES");

        return secretKey;
    }
   
    public static void main(String[] args) {
     try {
      String str = "JJwQsnnB0tduAR9mBHV93h0dl8x9J9mFHqCRCfZhTTR2K0ZFEFdnafc6Tt81wvpcp9Wi7gyJq0%2BvbWo2fuRH0ZbUX7V8j%2F11YVTALOepYJ0%3D";
     
      str = URLDecoder.decode(str,"utf-8");
      byte data[] = new BASE64Decoder().decodeBuffer(str);
      byte res[] = decrypt(data);
      System.out.println(new String(res,"utf-8"));

     
      String str1 = "WFkACRdhYHFxXjob2asdEOZuh%2F%2BprE%2FZI2Ggjs%2BxwxJNTk3j0STrogkln0GNhl%2BNkHefDWERW6kX0XeYR4kFTU%2BgvkKpfTif0OjcynVgggY%3D";
      str1 = URLDecoder.decode(str1,"utf-8");
      byte data1[] = new BASE64Decoder().decodeBuffer(str1);
      byte res1[] = decrypt(data1);
      System.out.println(new String(res1,"utf-8"));
  } catch (Exception e) {
   e.printStackTrace();
  }
 }

}
