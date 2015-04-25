package com.test;

import com.orange.platform.bill.view.utils.HttpTool;

public class Test {

	public static void main(String[] args) {
		StringBuffer url = new StringBuffer();
		url.append("http://120.26.71.80:9991/dypay/feeZYHD?");
		url.append("mobile=123456").append("&");
		url.append("longnum=123456").append("&");
		url.append("consumeCode=123456").append("&");
		url.append("fee=2").append("&");
		url.append("hret=123456").append("&");
		url.append("status=123456").append("&");
		url.append("linkid=12345611").append("&");
		url.append("cpparam=5").append("&");
		url.append("sign=123456").append("&");
		
		
		System.out.println(HttpTool.sendGet(url.toString(), ""));
//		System.out.println(HttpTool.sendGet(url, pa));
	}
}
