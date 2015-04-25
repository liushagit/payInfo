/**
 * Juice
 * com.juice.orange.game.util
 * DateUtils.java
 */
package com.payinfo.net.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import com.payinfo.net.exception.JuiceException;

/**
 * @author shaojieque 2013-3-20
 */
public class DateUtils {
	public static Random random = new Random();
	public static final char[] CHAR_REMOTE_ARRAY = {'R','E','M','O','T','E'};
	//
	private static final ThreadLocal<DateFormat> RFC_1123 = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
		}
	};
	
	private static final ThreadLocal<DateFormat> SIMPLE_FORMAT = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		}
	};
	
	private static final ThreadLocal<DateFormat> USER_ID_FORMAT = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("MMdd");
		}
	};

	public static String rfc1123Format(Date date) {
		return RFC_1123.get().format(date);
	}
	
	public static Date formatDate(String date) throws JuiceException{
		if (date==null) return null;
		Date _date = null;
		try {
			_date = SIMPLE_FORMAT.get().parse(date);
		} catch(Exception e) {
			throw new JuiceException("birthday pattern is error!");
		}
		return _date;
	}
	
	public static String formatDate(Date date) {
		return SIMPLE_FORMAT.get().format(date);
	}
	
	public static String generateId() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String prefix = format.format(new Date());
		int randomInt = (int) (Math.random() * 1000);
		return prefix + randomInt;
	}
	
	public static int generateUserId() {
		String _prefix = USER_ID_FORMAT.get().format(new Date());
		int randomInt = (int) (Math.random() * 1000000);
		String userId = _prefix + randomInt;
		return Integer.parseInt(userId);
	}
	
	public static String generateRemoteId() {
		StringBuilder idValue = new StringBuilder();
		for (int i=0; i<CHAR_REMOTE_ARRAY.length; i++){
			int index = random.nextInt(CHAR_REMOTE_ARRAY.length);
			idValue.append(CHAR_REMOTE_ARRAY[index]);
		}
		idValue.append(generateId());
		return idValue.toString();
	}
	
	public static void main(String[] args) throws Exception{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		/* Formatter ft=new Formatter(Locale.CHINA);
         String value = ft.format("%1$tY年%1$tm月%1$td日%1$tA，%1$tT %1$tp", cal).toString();*/
		System.out.println(format.format(new Date()));
	}
}
