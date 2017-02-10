package com.fr.chain.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;

import org.apache.commons.lang3.math.NumberUtils;

/**
 * 数值、金额处理和转换工具类
 * @author fcpays
 * 这里面的有些方法引用apache.commons.lang-2.3.math里的NumberUtils类
 */
public class NumberUtil {
	   
	/**
	 * 构造函数
	 */
	public NumberUtil(){
		
	}
	
	/**
	 * 比较两个指定的 double 值，引用org.apache.commons.lang.NumberUtils.compare(double lhs, double rhs)方法。<br>
	 * -1 - lhs小于rhs	<br>
	 * 0  - lhs等于rhs	<br>
	 * +1 - lhs大于rhs	<br>
	 * @param lhs 要比较的第一个double值
	 * @param rhs 要比较的第二个double值
	 * @return int -1（lhs小于rhs），0（lhs等于rhs），+1（lhs大于rhs）
	 */
	public static int compare(BigDecimal lhs, BigDecimal rhs){
		if(lhs == null){
			lhs = new BigDecimal("0.00");
		}
		if(rhs == null){
			rhs = new BigDecimal("0.00");
		}
		return lhs.compareTo(rhs);
		
	}
	
	/**
	 * 把String转换为BigDecimal值，引用org.apache.commons.lang.NumberUtils.createBigDecimal(String str)方法。<br>
	 * 假如String值为null将返回null。<br>
	 * @param str 转换的字符串 - 可以为null。
	 * @return BigDecimal 转换后的BigDecimal值。
	 * @throws 如果String值不能被转换或null，将抛出NumberFormatException异常
	 */
	public static BigDecimal createBigDecimal(String str) {
		return NumberUtils.createBigDecimal(str);
	}
	
	/**
	 * 把String转换为BigInteger值，引用org.apache.commons.lang.NumberUtils.createBigInteger(String str)方法。<br>
	 * 假如String值为null将返回null。<br>
	 * @param str 转换的字符串 - 可以为null。
	 * @return BigInteger 转换后的BigInteger值。
	 * @throws 如果String值不能被转换或null，将抛出NumberFormatException异常
	 */
	public static BigInteger createBigInteger(String str)  {
		return NumberUtils.createBigInteger(str);
	}
	
	/**
	 * 把String转换为Double值，引用org.apache.commons.lang.NumberUtils.createDouble(String str)方法。<br>
	 * 假如String值为null将返回null。<br>
	 * @param str 转换的字符串 - 可以为null。
	 * @return Double 转换后的Double值。
	 * @throws 如果String值不能被转换或null，将抛出NumberFormatException异常
	 */
	public static Double createDouble(String str) {
		return NumberUtils.createDouble(str);
	}
	
	/**
	 * 把String转换为Float值，引用org.apache.commons.lang.NumberUtils.createFloat(String str)方法。<br>
	 * 假如String值为null将返回null。<br>
	 * @param str 转换的字符串 - 可以为null。
	 * @return Float 转换后的Float值。
	 * @throws 如果String值不能被转换或null，将抛出NumberFormatException异常
	 */
	public static Float createFloat(String str) {
		return NumberUtils.createFloat(str);
	}
	
	/**
	 * 把String转换为Integer值，引用org.apache.commons.lang.NumberUtils.createInteger(String str)方法。<br>
	 * 假如String值为null将返回null。<br>
	 * @param str 转换的字符串 - 可以为null。
	 * @return Integer 转换后的Integer值。
	 * @throws 如果String值不能被转换或null，将抛出NumberFormatException异常
	 */
	public static Integer createInteger(String str) {
		return NumberUtils.createInteger(str);
	}
	
	/**
	 * 把String转换为Long值，引用org.apache.commons.lang.NumberUtils.createLong(String str)方法。<br>
	 * 假如String值为null将返回null。<br>
	 * @param str 转换的字符串 - 可以为null。
	 * @return Long 转换后的Long值。
	 * @throws 如果String值不能被转换或null，将抛出NumberFormatException异常
	 */
	public static Long createLong(String str)  {
		return NumberUtils.createLong(str);
	}
	
	/**
	 * 把String转换为java.lang.Number值，引用org.apache.commons.lang.NumberUtils.createNumber(String str)方法。<br>
	 * 如果String是以0x或-0x开头的,它将会被解释为十六进制整数。<br>
	 * String中的空格不能被trim,如果有空格将抛出NumberFormatException异。<br>
	 * 假如String值为null将返回null。<br>
	 * @param str 转换的字符串 - 可以为null。
	 * @return Number 转换后的Number值。
	 * @throws 如果String值不能被转换或null，将抛出NumberFormatException异常
	 */
	public static Number createNumber(String str)    {
		return NumberUtils.createNumber(str);
	}
	
	
	
	/**
	 * 检查字符串中的字符是否为数字，引用org.apache.commons.lang.NumberUtils.isDigits(String str)方法。<br>
	 * null或""将返回false。<br>
	 * @param str 要检查的字符串
	 * @return boolean 如果只包含数字将返回true,否则返回false。
	 */
	public static boolean isDigits(String str)  {
		return NumberUtils.isDigits(str);
	}
	
	/**
	 * 检查字符串中的字符是否为有校的Java number，引用org.apache.commons.lang.NumberUtils.isNumber(String str)方法。<br>
	 * 有效数字中包括十六进制，用0x标记，科学符号，数字标明的类型（如123L）。<br>
	 * @param str 要检查的字符串
	 * @return boolean 如果字符串是正确格式的数字将返回true,否则返回false。
	 */
	public static boolean isNumber(String str)   {
		return NumberUtils.isNumber(str);
	}
	
	
	
		
	/**
	 * 返回数组中的最大的byte值，引用org.apache.commons.lang.NumberUtils.max(byte[] array)方法。<br>
	 * @param array 查找的数组 - 不能为null或""。
	 * @return byte 返回数组中最大的byte值
	 * @throws 如果array为null或""将抛出IllegalArgumentException异常。
	 */
	public static byte max(byte[] array) {
		return NumberUtils.max(array);
	}

	/**
	 * 返回a,b,c三个byte参数中的最大的值，引用org.apache.commons.lang.NumberUtils.max(byte a, byte b, byte c)方法。<br>
	 * @param a value 1
	 * @param b value 2
	 * @param c value 3
	 * @return byte 返回最大的值。
	 */
	public static byte max(byte a, byte b, byte c) {
		return NumberUtils.max(a,b,c);
	}

	/**
	 * 返回数组中的最大的double值，引用org.apache.commons.lang.NumberUtils.max(double[] array)方法。<br>
	 * @param array 查找的数组 - 不能为null或""。
	 * @return double 返回数组中最大的double值
	 * @throws 如果array为null或""将抛出IllegalArgumentException异常。
	 */
	public static double max(double[] array) {
		return NumberUtils.max(array);
	}
	
	/**
	 * 返回a,b,c三个double参数中的最大的值，引用org.apache.commons.lang.NumberUtils.max(double a, double b, double c)方法。<br>
	 * 如果所有的参数值是NaN，将返回NaN。<br>
	 * @param a value 1
	 * @param b value 2
	 * @param c value 3
	 * @return double 返回最大的值。
	 */
	public static double max(double a, double b, double c) {
		return NumberUtils.max(a,b,c);
	}
	
	/**
	 * 返回数组中的最大的float值，引用org.apache.commons.lang.NumberUtils.max(float[] array)方法。<br>
	 * @param array 查找的数组 - 不能为null或""。
	 * @return float 返回数组中最大的float值
	 * @throws 如果array为null或""将抛出IllegalArgumentException异常。
	 */
	public static float max(float[] array) {
		return NumberUtils.max(array);
	}
	
	/**
	 * 返回a,b,c三个float参数中的最大的值，引用org.apache.commons.lang.NumberUtils.max(float a, float b, float c)方法。<br>
	 * 如果所有的参数值是NaN，将返回NaN。<br>
	 * @param a value 1
	 * @param b value 2
	 * @param c value 3
	 * @return float 返回最大的值。
	 */
	public static float max(float a, float b, float c) {
		return NumberUtils.max(a,b,c);
	}
	
	/**
	 * 返回数组中的最大的int值，引用org.apache.commons.lang.NumberUtils.max(int[] array)方法。<br>
	 * @param array 查找的数组 - 不能为null或""。
	 * @return int 返回数组中最大的int值
	 * @throws 如果array为null或""将抛出IllegalArgumentException异常。
	 */
	public static int max(int[] array) {
		return NumberUtils.max(array);
	}
	
	/**
	 * 返回a,b,c三个int参数中的最大的值，引用org.apache.commons.lang.NumberUtils.max(int a, int b, int c)方法。<br>
	 * @param a value 1
	 * @param b value 2
	 * @param c value 3
	 * @return int 返回最大的值。
	 */
	public static int max(int a, int b, int c) {
		return NumberUtils.max(a,b,c);
	}
	
	/**
	 * 返回数组中的最大的long值，引用org.apache.commons.lang.NumberUtils.max(long[] array)方法。<br>
	 * @param array 查找的数组 - 不能为null或""。
	 * @return long 返回数组中最大的long值
	 * @throws 如果array为null或""将抛出IllegalArgumentException异常。
	 */
	public static long max(long[] array) {
		return NumberUtils.max(array);
	}
 
	/**
	 * 返回a,b,c三个long参数中的最大的值，引用org.apache.commons.lang.NumberUtils.max(long a, long b, long c)方法。<br>
	 * @param a value 1
	 * @param b value 2
	 * @param c value 3
	 * @return long 返回最大的值。
	 */
	public static long max(long a, long b, long c) {
		return NumberUtils.max(a,b,c);
	}
	
	/**
	 * 返回数组中的最大的short值，引用org.apache.commons.lang.NumberUtils.max(short[] array)方法。<br>
	 * @param array 查找的数组 - 不能为null或""。
	 * @return short 返回数组中最大的short值
	 * @throws 如果array为null或""将抛出IllegalArgumentException异常。
	 */
	public static short max(short[] array) {
		return NumberUtils.max(array);
	}

	/**
	 * 返回a,b,c三个short参数中的最大的值，引用org.apache.commons.lang.NumberUtils.max(short a, short b, short c)方法。<br>
	 * @param a value 1
	 * @param b value 2
	 * @param c value 3
	 * @return short 返回最大的值。
	 */
	public static short max(short a, short b, short c) {
		return NumberUtils.max(a,b,c);
	}
	
	
	
	
	/**
	 * 返回数组中的最小的byte值，引用org.apache.commons.lang.NumberUtils.min(byte[] array)方法。<br>
	 * @param array 查找的数组 - 不能为null或""。
	 * @return byte 返回数组中最小的byte值
	 * @throws 如果array为null或""将抛出IllegalArgumentException异常。
	 */
	public static byte min(byte[] array) {
		return NumberUtils.min(array);
	}

	/**
	 * 返回a,b,c三个byte参数中的最小的值，引用org.apache.commons.lang.NumberUtils.min(byte a, byte b, byte c)方法。<br>
	 * @param a value 1
	 * @param b value 2
	 * @param c value 3
	 * @return byte 返回最小的值。
	 */
	public static byte min(byte a, byte b, byte c) {
		return NumberUtils.min(a,b,c);
	}
	
	/**
	 * 返回数组中的最小的double值，引用org.apache.commons.lang.NumberUtils.min(double[] array)方法。<br>
	 * @param array 查找的数组 - 不能为null或""。
	 * @return double 返回数组中最小的double值
	 * @throws 如果array为null或""将抛出IllegalArgumentException异常。
	 */
	public static double min(double[] array) {
		return NumberUtils.min(array);
	}

	/**
	 * 返回a,b,c三个double参数中的最小的值，引用org.apache.commons.lang.NumberUtils.min(double a, double b, double c)方法。<br>
	 * 如果所有的参数值是NaN，将返回NaN。<br>
	 * @param a value 1
	 * @param b value 2
	 * @param c value 3
	 * @return double 返回最小的值。
	 */
	public static double min(double a, double b, double c) {
		return NumberUtils.min(a,b,c);
	}
	
	/**
	 * 返回数组中的最小的float值，引用org.apache.commons.lang.NumberUtils.min(float[] array)方法。<br>
	 * @param array 查找的数组 - 不能为null或""。
	 * @return float 返回数组中最小的float值
	 * @throws 如果array为null或""将抛出IllegalArgumentException异常。
	 */
	public static float min(float[] array) {
		return NumberUtils.min(array);
	}

	/**
	 * 返回a,b,c三个float参数中的最小的值，引用org.apache.commons.lang.NumberUtils.min(float a, float b, float c)方法。<br>
	 * 如果所有的参数值是NaN，将返回NaN。<br>
	 * @param a value 1
	 * @param b value 2
	 * @param c value 3
	 * @return float 返回最小的值。
	 */
	public static float min(float a, float b, float c) {
		return NumberUtils.min(a,b,c);
	}
	
	/**
	 * 返回数组中的最小的int值，引用org.apache.commons.lang.NumberUtils.min(int[] array)方法。<br>
	 * @param array 查找的数组 - 不能为null或""。
	 * @return int 返回数组中最小的int值
	 * @throws 如果array为null或""将抛出IllegalArgumentException异常。
	 */
	public static int min(int[] array) {
		return NumberUtils.min(array);
	}
 
	/**
	 * 返回a,b,c三个int参数中的最小的值，引用org.apache.commons.lang.NumberUtils.min(int a, int b, int c)方法。<br>
	 * @param a value 1
	 * @param b value 2
	 * @param c value 3
	 * @return int 返回最小的值。
	 */
	public static int min(int a, int b, int c) {
		return NumberUtils.min(a,b,c);
	}
	
	/**
	 * 返回数组中的最小的long值，引用org.apache.commons.lang.NumberUtils.min(long[] array)方法。<br>
	 * @param array 查找的数组 - 不能为null或""。
	 * @return long 返回数组中最小的long值
	 * @throws 如果array为null或""将抛出IllegalArgumentException异常。
	 */
	public static long min(long[] array) {
		return NumberUtils.min(array);
	}

	/**
	 * 返回a,b,c三个long参数中的最小的值，引用org.apache.commons.lang.NumberUtils.min(long a, long b, long c)方法。<br>
	 * @param a value 1
	 * @param b value 2
	 * @param c value 3
	 * @return long 返回最小的值。
	 */
	public static long min(long a, long b, long c) {
		return NumberUtils.min(a,b,c);
	}
	
	/**
	 * 返回数组中的最小的short值，引用org.apache.commons.lang.NumberUtils.min(short[] array)方法。<br>
	 * @param array 查找的数组 - 不能为null或""。
	 * @return short 返回数组中最小的short值
	 * @throws 如果array为null或""将抛出IllegalArgumentException异常。
	 */
	public static short min(short[] array) {
		return NumberUtils.min(array);
	}
 
	/**
	 * 返回a,b,c三个short参数中的最小的值，引用org.apache.commons.lang.NumberUtils.min(short a, short b, short c)方法。<br>
	 * @param a value 1
	 * @param b value 2
	 * @param c value 3
	 * @return short 返回最小的值。
	 */
	public static short min(short a, short b, short c) {
		return NumberUtils.min(a,b,c);
	}
 
 
	
	
	
	/**
	 * 返回一个新的用指定String表示的double值，如果转换失败返回0.0d，引用org.apache.commons.lang.NumberUtils.toDouble(String str)方法。<br>
	 * 如果字符串为null，返回0.0d。<br>
	 * 例：<br>
	 *		NumberUtil.toDouble(null)   = 0.0d	<br>
	 *    	NumberUtil.toDouble("")     = 0.0d	<br>
	 *  	NumberUtil.toDouble("1.5")  = 1.5d	<br>
	 * @param str 要解析的字符串 - 可能为null。
	 * @return double 返回由字符串参数表示的double值，如果转换失败返回0.0d。
	 */
	public static double toDouble(String str) {
		return NumberUtils.toDouble(str);
	}
 
	/**
	 * 返回一个新的用指定String表示的double值，如果转换失败就返回默认的double值，引用org.apache.commons.lang.NumberUtils.toDouble(String str, double defaultValue)方法。<br>
	 * 如果字符串为null，返回默认的double值。<br>
	 * 例：<br>
	 *		NumberUtil.toDouble(null, 1.1d) = 1.0d	<br>
	 *    	NumberUtil.toDouble("", 1.1d)   = 1.1d	<br>
	 *  	NumberUtil.toDouble("1.5", 0.0d)= 1.5d	<br>
	 * @param str 要解析的字符串 - 可能为null。
	 * @param defaultValue 默认值
	 * @return double 返回由字符串参数表示的double值，如果转换失败就返回默认的double值。
	 */
	public static double toDouble(String str, double defaultValue) {
		return NumberUtils.toDouble(str,defaultValue);
	}
 
	/**
	 * 返回一个新的用指定String表示的float值，如果转换失败返回0.0f，引用org.apache.commons.lang.NumberUtils.toFloat(String str)方法。<br>
	 * 如果字符串为null，返回0.0f。<br>
	 * 例：<br>
	 *		NumberUtil.toFloat(null)   = 0.0f	<br>
	 *    	NumberUtil.toFloat("")     = 0.0f	<br>
	 *  	NumberUtil.toFloat("1.5")  = 1.5f	<br>
	 * @param str 要解析的字符串 - 可能为null。
	 * @return float 返回由字符串参数表示的float值，如果转换失败返回0.0f。
	 */
	public static float toFloat(String str) {
		return NumberUtils.toFloat(str);
	}

	/**
	 * 返回一个新的用指定String表示的float值，如果转换失败就返回默认的float值，引用org.apache.commons.lang.NumberUtils.toFloat(String str, float defaultValue)方法。<br>
	 * 如果字符串为null，返回默认的float值。<br>
	 * 例：<br>
	 *		NumberUtil.toFloat(null, 1.1f) = 1.0f	<br>
	 *    	NumberUtil.toFloat("", 1.1f)   = 1.1f	<br>
	 *  	NumberUtil.toFloat("1.5", 0.0f)= 1.5f	<br>
	 * @param str 要解析的字符串 - 可能为null。
	 * @param defaultValue 默认值
	 * @return float 返回由字符串参数表示的float值，如果转换失败就返回默认的float值。
	 */
	public static float toFloat(String str, float defaultValue) {
		return NumberUtils.toFloat(str,defaultValue);
	}
 
	/**
	 * 返回一个新的用指定String表示的int值，如果转换失败返回0，引用org.apache.commons.lang.NumberUtils.toInt(String str)方法。<br>
	 * 如果字符串为null，返回0。<br>
	 * 例：<br>
	 *		NumberUtil.toInt(null)   = 0	<br>
	 *    	NumberUtil.toInt("")     = 0	<br>
	 *  	NumberUtil.toInt("1")  	 = 1	<br>
	 * @param str 要解析的字符串 - 可能为null。
	 * @return int 返回由字符串参数表示的int值，如果转换失败返回0。
	 */
	public static int toInt(String str) {
		return NumberUtils.toInt(str);
	}

	/**
	 * 返回一个新的用指定String表示的int值，如果转换失败就返回默认的int值，引用org.apache.commons.lang.NumberUtils.toInt(String str, int defaultValue)方法。<br>
	 * 如果字符串为null，返回默认的int值。<br>
	 * 例：<br>
	 *		NumberUtil.toInt(null, 1) = 1	<br>
	 *    	NumberUtil.toInt("", 1)   = 1	<br>
	 *  	NumberUtil.toInt("1", 0)  = 1	<br>
	 * @param str 要解析的字符串 - 可能为null。
	 * @param defaultValue 默认值
	 * @return int 返回由字符串参数表示的int值，如果转换失败就返回默认的int值。
	 */
	public static int toInt(String str, int defaultValue) {
		return NumberUtils.toInt(str,defaultValue);
	}

	/**
	 * 返回一个新的用指定String表示的long值，如果转换失败返回0，引用org.apache.commons.lang.NumberUtils.toLong(String str)方法。<br>
	 * 如果字符串为null，返回0。<br>
	 * 例：<br>
	 *		NumberUtil.toLong(null)   = 0L	<br>
	 *    	NumberUtil.toLong("")     = 0L	<br>
	 *  	NumberUtil.toLong("1")    = 1L	<br>
	 * @param str 要解析的字符串 - 可能为null。
	 * @return long 返回由字符串参数表示的long值，如果转换失败返回0。
	 */
	public static long toLong(String str) {
		return NumberUtils.toLong(str);
	}
 
	/**
	 * 返回一个新的用指定String表示的long值，如果转换失败就返回默认的long值，引用org.apache.commons.lang.NumberUtils.toLong(String str, long defaultValue)方法。<br>
	 * 如果字符串为null，返回默认的long值。<br>
	 * 例：<br>
	 *		NumberUtil.toLong(null, 1L) = 1L	<br>
	 *    	NumberUtil.toLong("", 1L)   = 1L	<br>
	 *  	NumberUtil.toLong("1", 0L)  = 1L	<br>
	 * @param str 要解析的字符串 - 可能为null。
	 * @param defaultValue 默认值
	 * @return long 返回由字符串参数表示的long值，如果转换失败就返回默认的long值。
	 */
	public static long toLong(String str, long defaultValue) {
		return NumberUtils.toLong(str,defaultValue);
	}
	
	/**
	 * 给数字类型的数据添加千分位，不是数字类型返回原值
	 * @param strPrice      输入参数为数字类型
	 * @return String  		输出参数格式千分位后的字符串
	 */
	public static String formatQfw(String strPrice) {
		return formatQfwOrRmbOrigin(strPrice, "0");
	}
	
	/**
	 * 给数字类型的数据添加千分位和RMB符号，不是数字类型返回原值
	 * @param strPrice      输入参数为数字类型
	 * @return String  		输出参数格式千分位后的字符串
	 */
	public static String formatQfwAndRmb(String strPrice) {
		strPrice = StringUtil.nvl(strPrice);
		if(strPrice.equals("")){
			strPrice = "0.00";
		}
		BigDecimal	price = new BigDecimal(strPrice);
		DecimalFormat df3 = new DecimalFormat("###0.00");
		strPrice = df3.format(price);
		return formatQfwOrRmbOrigin(strPrice, "1");
	}
	
	/**
	 * 给数字类型的数据添加千分位和RMB符号，不是数字类型返回原值
	 * @param price      输入参数为数字类型
	 * @return String  		输出参数格式千分位后的字符串
	 */
	public static String formatQfwAndRmb(BigDecimal price) {
		if(price == null ){
			price = new BigDecimal("0.00");
		}
		DecimalFormat df3 = new DecimalFormat("###0.00");
		String strPrice = df3.format(price);
		strPrice = formatQfwOrRmbOrigin(strPrice, "1");
		return strPrice;
	}
	
	/**
	 * 给数字类型的数据添加千分位和RMB符号，不是数字类型返回原值
	 * @param strPrice      输入参数为数字类型
	 * @param isAddRmb      输入参数为是否添加RMB符号(0:不加 1:加)
	 * @return String  		输出参数格式千分位后的字符串
	 */
	private static String formatQfwOrRmbOrigin(String strPrice, String isAddRmb) {
		if (isNumber(strPrice)) {

			strPrice = strPrice.trim();
			// 负数标志
			String flag = "no";
			if (!"".equals(strPrice)) {
				if ("-".equals(strPrice.substring(0, 1))) {
					strPrice = strPrice.substring(1);
					flag = "yes";
				}
				int pointIndex = strPrice.indexOf(".");
				String strPriceZ = "";
				String strPriceX = "";
				if (pointIndex > 0) {
					strPriceZ = strPrice.substring(0, pointIndex);
					strPriceX = strPrice.substring(pointIndex);
				} else {
					strPriceZ = strPrice;
					strPriceX = "";
				}

				if (strPriceZ.length() >= 4) {
					int qfpNum = (strPriceZ.length() - (strPriceZ.length() % 3)) / 3;
					String tempPrice = "";
					for (int i = 0; i < qfpNum; i++) {
						tempPrice = "," + strPriceZ.substring(strPriceZ.length() - (i + 1) * 3, strPriceZ.length() - i * 3) + tempPrice;
					}
					if (strPriceZ.length() % 3 == 0) {
						tempPrice = tempPrice.trim().substring(1);
					} else {
						tempPrice = strPriceZ.substring(0, strPriceZ.length() % 3) + tempPrice;
					}
					if ("yes".equals(flag)) {
						strPrice = "-" + tempPrice + strPriceX;
					} else {
						strPrice = tempPrice + strPriceX;
					}
					if ("1".equals(isAddRmb)) {
						strPrice = "￥" + strPrice;
					}
				} else {
					if (!"".equals(strPrice) && !".".equals(strPrice)) {
						if ("yes".equals(flag)) {
							strPrice = "-" + strPrice;
						}
						if ("1".equals(isAddRmb)) {
							strPrice = "￥" + strPrice;
						}
					}
				}
			}
		}
		return strPrice;
	}
}
