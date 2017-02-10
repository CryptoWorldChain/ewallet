package com.fr.chain.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 数值、金额格式化工具类
 * @author fcpays
 *
 */
public class NumberFormatUtil {
	
	private static final DecimalFormat df1 = new DecimalFormat("###,###,###,##0");
	private static final DecimalFormat df2 = new DecimalFormat("###0");
	private static final DecimalFormat df3 = new DecimalFormat("###0.00");
	private static final DecimalFormat df4 = new DecimalFormat("###,###,###,##0.00");
	public static final DecimalFormat df5 = new DecimalFormat("################.##");
	
	/**
	 * 构造函数
	 */
    public NumberFormatUtil(){
    	
    }
    
    public static String format(BigDecimal val, DecimalFormat df) {
		if (val == null) {
			return "";
		}
		return df.format(val);
	}
    
    
	/**
	 * BigDecimal值转换为String（BigDecimal数值四舍五入为整数"###,###,###,##0"格式的字符串）。
	 * @param val BigDecimal值
	 * @return String 返回格式为"###,###,###,##0"的整数字符串
	 */
	public static String formatToIntLong(BigDecimal val) {
		if (val == null) {
			return "";
		}
		return df1.format(val);
	}
		
	/**
	 * Integer值转换为String（Integer数值四舍五入为整数"###,###,###,##0"格式的字符串）。
	 * @param val Integer值
	 * @return String 返回格式为"###,###,###,##0"的整数字符串
	 */
	public static String formatToIntLong(Integer val) {
		if (val == null) {
			return "";
		}
		return df1.format(val);
	}
	
	/**
	 * long值转换为String（long数值四舍五入为整数"###,###,###,##0"格式的字符串）。
	 * @param longVal long值
	 * @return String 返回格式为"###,###,###,##0"的整数字符串
	 */
	public static String formatToIntLong(long longVal) {
		return df1.format(longVal);
	}
	
	/**
	 * String类型的数值转换为四舍五入后整数("###,###,###,##0"格式)的字符串。
	 * @param strVal String值
	 * @return String 返回格式为"###,###,###,##0"的整数字符串
	 */
	public static String formatToIntLong(String strVal) {
		if (strVal == null || strVal.equals("")) {
			return "";
		}
		return formatToIntLong(new BigDecimal(strVal));
	}	
	
	
	
	/**
	 * BigDecimal值转换为String（BigDecimal数值四舍五入为"###0"格式的字符串）。
	 * @param val BigDecimal值
	 * @return String 返回格式为"###0"的整数字符串
	 */
	public static String formatToInt(BigDecimal val) {
		if (val == null) {
			return "";
		}
		return df2.format(val);
	}
	
	/**
	 * Integer值转换为String（Integer数值四舍五入为"###0"格式整数的字符串）。
	 * @param val Integer值
	 * @return String 返回格式为"###0"的整数字符串
	 */
	public static String formatToInt(Integer val) {
		if (val == null) {
			return "";
		}
		return df2.format(val);
	}
	
	/**
	 * long值转换为String（long数值四舍五入为整数"###0"格式的字符串）。
	 * @param longVal long值
	 * @return String 返回格式为"###0"的整数字符串
	 */
	public static String formatToInt(long longVal) {
		return df2.format(longVal);
	}

	/**
	 * String类型的数值转换为四舍五入后整数("###0"格式)的字符串。
	 * @param strVal String值
	 * @return String 返回格式为"##0"的整数字符串
	 */
	public static String formatToInt(String strVal) {
		if (strVal == null || strVal.equals("")) {
			return "";
		}
		return formatToInt(new BigDecimal(strVal));
	}
	
	
	
	/**
	 * BigDecimal值转换为String（BigDecimal数值四舍五入为"###,###,###,##0.00"格式的字符串）。
	 * @param val BigDecimal值
	 * @return String 返回格式为"###,###,###,##0.00"的字符串
	 */
	public static String formatToPercent(BigDecimal val) {
		if (val == null) {
			return "";
		}
		return df4.format(val);
	}
	/**
	 * double值转换为String类型值（double值四舍五入为"###,###,###,##0.00"格式字符串）。
	 * @param doubleVal double值
	 * @return String 返回格式为"###,###,###,##0.00"的字符串
	 */
	public static String formatToPercent(double doubleVal) {
		return df4.format(doubleVal);
	}	
	
	
			
	/**
	 * BigDecimal金额转换为String（BigDecimal金额四舍五入为"###0.00"格式的金额字符串）。
	 * @param val BigDecimal金额
	 * @return String 返回格式为"###0.00"的字符串
	 */
	public static String formatMoneyPercent(BigDecimal val) {
		if (val == null) {
			return "";
		}
		return df3.format(val);
	}
	
	/**
	 * long金额转换为String（long金额四舍五入为"###0.00"格式的金额字符串）。
	 * @param longVal long金额
	 * @return String 返回格式为"###0.00"的字符串
	 */
	public static String formatMoneyPercent(long longVal) {
		return df3.format(longVal);
	}
	
	/**
	 * String金额转换为String（String金额四舍五入为"###0.00"格式的金额字符串）。
	 * @param strVal String金额
	 * @return String 返回格式为"###0.00"的字符串
	 */
	public static String formatMoneyPercent(String strVal) {
		if (strVal == null || strVal.trim().equals("")) {
			return "0.00";
		}
		return formatMoneyPercent(new BigDecimal(strVal));
	}
	
		
	/**
	 * 根据给定长度截取数值，如果number为100010，length长度为4，那么返回0010(number值后4位)。如果number的长度小于length，那么返回number本身。
	 * @param number 数值
	 * @param length 要截取的长度
	 * @return String 截取后的字符串
	 */
	public static String formatByZero(int number, int length) {
//		String strFormat = String.valueOf(number);
//		String newStr="";
//		if(strFormat !=null){
//			if (strFormat.length() < length) {
//				int leng =length - strFormat.length();
//				for(int i=0; i<leng; i++){
//					newStr =newStr+"0";
//				}
//				strFormat =newStr + strFormat;
//			}
//		}
//		return strFormat;
		
		
		String strFormat = String.valueOf(number);
		if(strFormat != null){
			if(strFormat.length() > length){
				strFormat =strFormat.substring(strFormat.length() - length);
			}
		}
		return strFormat;
	}
	
	
	/**
	 * 格式化val为两位小数的BigDecimal（四舍五入）
	 * @param val BigDecimal参数值
	 * @return BigDecima
	 */
	public static BigDecimal formatBigDecimal(BigDecimal val){
		if(val != null){
			BigDecimal newVal = new BigDecimal(val.toString()).setScale(2, BigDecimal.ROUND_HALF_UP); 
			return newVal;
		}else{
			return new BigDecimal(0);
		}
	}
}
