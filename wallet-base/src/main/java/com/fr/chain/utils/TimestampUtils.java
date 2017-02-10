package com.fr.chain.utils;


import java.sql.Timestamp;
import java.util.Date;


/**
 * Timestamp格式化工具类
 * @author fcpays
 *
 */
public class TimestampUtils {
	
	/**
	 * 把Date日期类型转换成Timestamp日期类型
	 * @param date 要转换的日期
	 * @return Timestamp 转换后的Timestamp日期
	 */
	public static Timestamp parseForDate(Date date) {
		long longTime = date.getTime();
		return new Timestamp(longTime);
	} 
	
	/**
	 * 把String日期类型转换成Timestamp类型，参数格式为10位的字符串，如："yyyy-MM-dd"
	 * @param strDate 原日期字符串
	 * @return Timestamp 转换后的Timestamp类型日期
	 * @throws IllegalArgumentException 如果给定的参数不是"yyyy-mm-dd hh:mm:ss"或长于"yyyy-mm-dd hh:mm:ss"的长度则抛出异常
	 */
	public static Timestamp parseTimestamp(String strDate) {
		if (strDate != null && !"".equals(strDate) && strDate.length() == 10) {
			strDate = strDate + " 00:00:00.0";
		} 
		return Timestamp.valueOf(strDate);
	}
	
	/**
	 * 将timestamp日期<br>
	 * 转换为用于sql文的字符串(to_timestamp('2007-04-18 00:00:00.0' , 'yyyy-mm-dd hh24:mi:ssxff'))
	 * @param timestamp 待转换的日期
	 * @return String 返回如：to_timestamp('2007-04-18 00:00:00.0','yyyy-mm-dd hh24:mi:ssxff')的字符串
	 */
	public static String toTimeStamp(Timestamp timestamp) {
		String sql = "to_timestamp('" + timestamp.toString() + "' , 'yyyy-mm-dd hh24:mi:ssxff')";
		return sql;
	}

	/**
	 * 将格式如2007-04-18 00:00:00.0的日期字符串，<br>
	 * 转换为用于sql的字符串(to_timestamp('2007-04-18 00:00:00.0' , 'yyyy-mm-dd hh24:mi:ssxff'))
	 * @param str 待转换的字符串
	 * @return String 返回如：to_timestamp('2007-04-18 00:00:00.0' , 'yyyy-mm-dd hh24:mi:ssxff')的字符串
	 */
	public static String toTimeStamp(String str) {
		String sql = "to_timestamp('" + str + "' , 'yyyy-mm-dd hh24:mi:ssxff')";
		return sql;
	}
}
