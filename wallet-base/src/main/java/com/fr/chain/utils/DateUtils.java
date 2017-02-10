package com.fr.chain.utils;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期操作的工具类
 */
public class DateUtils {

	/**
	 * 获取当前系统时间
	 * @return yyyy-MM-dd%20HH:mm:ss
	 */
	public static String getNowDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(new Date());
	}
	
	/**
	 * 获取当前系统时间  minute分钟后 的时间
	 * @return yyyy-MM-dd%20HH:mm:ss
	 */
	public static String getNowDate(int minute) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MINUTE, c.get(Calendar.MINUTE)+minute);
		return formatter.format(c.getTime());
	}
	
	/**
	 * 将字符串转化为日期类型
	 * 
	 * @param pattern
	 *            转化格式
	 * @param dateStr
	 *            要转化的字符串
	 * @return
	 */
	public static Date getDate(String pattern, String dateStr) {
		Date date = null;

		try {
			SimpleDateFormat formatter = new SimpleDateFormat(pattern);
			date = formatter.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date;
	}

	/**
	 * 获取时间字符串
	 * 
	 * @param pattern
	 * @return
	 */
	public static String formatDate(String pattern, Date date) {
		String str = null;
		try {
			str = new SimpleDateFormat(pattern).format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	
	public static void main(String[] args) {
		Date date = getDate("yyyy-MM-dd","2014-05-07");
		
		System.out.println(date.getTime());
//		String formatDate = formatDate("yyyy-MM-dd", new Date());
//		System.out.println(formatDate);
	}

}
