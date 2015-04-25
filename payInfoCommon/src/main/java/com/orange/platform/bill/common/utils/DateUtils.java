/**
 * ChinaMobileMarket
 * com.orange.game.china.mobile.market.utils
 * DateUtils.java
 */
package com.orange.platform.bill.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author author
 * 2012-10-23
 */
public class DateUtils {
	private static SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
	private static SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	public static Date convertString(String value) {
		Date now = new Date();
		try {
			now = format.parse(value);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return now;
	}
	
	public static String convertDate(Date date) {
		return format2.format(date);
	}
	
	public static void main(String[] args) throws Exception{
		/*String value = "s";//"OR$00101@122321312";
		int position = value.trim().indexOf('@');
		if (position == 8) { 
			System.out.println(true);
		}
		char flag = value.trim().charAt(8);
		if (flag == '@') {
			System.out.println(value.trim().substring(3, 6));
			System.out.println(value.trim().substring(6, 8));
		}*/
		
		//System.out.println(DateFormat.getInstance().parse("20120327143153"));
		System.out.println(convertDate(convertString("20120327143153")));
	}
}
