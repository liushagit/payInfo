package com.orange.platform.bill.view.utils.ip;

import java.io.File;

public class IPtest {
	public static final String data_path = System.getProperty("user.dir") + File.separator + "data";
	
	public static void main(String[] args) {
		IPSeeker ip = new IPSeeker("QQWry.dat", "data");
	}
	
	//
	private IPSeeker seeker;
	private static IPtest test;
	private IPtest(){}
	
	public static IPtest getInstance(){
		if (test==null) {
			test = new IPtest();
			test.init();
		}
		return test;
	}
	
	public void init() {
		seeker = new IPSeeker("QQWry.dat", "data");
	}
	
	public String queryProvince(String ip) {
		String province = "";
		try {
			String country = seeker.getIPLocation(ip).getCountry();
			province = country.substring(0, 2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return province;
	}
	
	public String queryCountry(String ip) {
		return seeker.getIPLocation(ip).getCountry();
	}
	
	public String queryArea(String ip) {
		return seeker.getIPLocation(ip).getArea();
	}
}