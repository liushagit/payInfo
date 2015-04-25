package com.orange.platform.bill.view.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 
 * @author guojiang
 *
 * 2014年7月1日
 */
public class URLUtil {

	private static final String enc = "UTF-8";
	
	/**
	 * 对字符串进行UTF-8解码
	 * @param msg
	 * @return
	 */
	public static String decode(String msg){
		try {
			return URLDecoder.decode(msg, enc);
		} catch (UnsupportedEncodingException e) {
		}
		return "";
	}
	
	/**
	 * 对字符串进行UTF-8编码
	 * @param msg
	 * @return
	 */
	public static String encode(String msg){
		try {
			return URLEncoder.encode(msg, enc);
		} catch (UnsupportedEncodingException e) {
		}
		return "";
	}
	
	public static void main(String[] args) {
		String ss = "ssss_////%%%sssssssss";
		String en = encode(ss);
		System.out.println(en);
		System.out.println(decode(en));
		System.out.println(ss);
	}
}
