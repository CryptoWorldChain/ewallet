package com.fr.chain.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.engines.RC4Engine;
import org.bouncycastle.crypto.params.KeyParameter;

/**
 * 字符串处理工具类
 * @author fcpays
 * 这里面的有些方法引用apache.commons.lang-2.3里的StringUtils类
 */
public class StringUtil {
	
	/**
	 * 构造函数
	 */
	public StringUtil(){
		
	}	
	
	/**
	 * null对象转换，参数对象为null时，返回空字符串;否则返回对象的值(如果对象为null时进行trim()是可用这方法)。<br>
	 * @param obj	需要转换的对象
	 * @return String 返回字符串或空字符串。
	 */
	public static String nvl(Object obj) {
		if(obj == null) {
			return "";
		}
		return obj.toString().trim();
	}
	
	/**
	 * 判断字符串是否为null或""，或者trim后长度小于1；true为null或者trim后长度小于1，引用org.apache.commons.lang.StringUtils.isEmpty(String str)方法。<br>
	 * 例：	 <br>
	 * 		 StringUtil.isEmpty(null)      = true		<br>
	 *		 StringUtil.isEmpty("")        = true		<br>
	 *		 StringUtil.isEmpty(" ")       = false		<br>
	 *		 StringUtil.isEmpty("bob")     = false		<br>
	 *		 StringUtil.isEmpty("  bob  ") = false		<br>
	 * @param str 要被判断的字符串
	 * @return boolean 为空或长度小于1返回true；否则返回false。
	 */
	public static boolean isEmpty(String str) {
		if (StringUtils.isEmpty(str) || str.trim().length() < 1) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 将传入的英文名字取第一个字母并大写。<br>
	 * @param str 城市英文名字
	 * @return String 第一个大写字母。
	 */
	public static String eNameOneToUpperCase(String str) {
		String strEnameOne = str.trim();
		if (strEnameOne.length() > 0) {
			strEnameOne = strEnameOne.substring(0, 1).toUpperCase();
		}
		return strEnameOne;
	}
	
	

	/**
	 * 按给定字符拆分成字符串数组。<br>
	 * @param inputStr 	原始字符串
	 * @param splitChar 拆分字符
	 * @return String[]	字符串数组。
	 */
	public static String[] splitLong(String inputStr, char splitChar) {
		int iStrLen = inputStr.length();
		int iTokCount = 0;

		if (0 == iStrLen)
			return null;

		for (int p = 0; p < iStrLen; p++)
			if (inputStr.charAt(p) == splitChar)
				iTokCount++;

		String Tokens[] = new String[iTokCount + 1];

		int iToken = 0;
		int iLast = 0;
		for (int iNext = 0; iNext < iStrLen; iNext++) {
			if (inputStr.charAt(iNext) == splitChar) {
				if (iLast == iNext)
					Tokens[iToken] = "";
				else
					Tokens[iToken] = inputStr.substring(iLast, iNext);
				iLast = iNext + 1;
				iToken++;
			} 
		} 

		if (iLast >= iStrLen)
			Tokens[iToken] = "";
		else
			Tokens[iToken] = inputStr.substring(iLast, iStrLen);

		return Tokens;
	} 

	/**
	* 将字符串（包括汉字,汉字算两个字符）分割成固定长度的字符串数组。<br>
	* @param strParamTarget 分割的字符串
	* @param length 按多长分割数组
	* @return String[] 字符串数组。
	*/
	public static String[] split(String strParamTarget, int length) {
		Vector<Object> vctWork = new Vector<Object>();
		int nCharLen;
		int nLen = 0;
		int i;
		StringBuffer sbWork = new StringBuffer("");
		char cWork;
		if (strParamTarget == null) {
	
		} else {
			for (i = 0; i < strParamTarget.length(); i++) {
				cWork = strParamTarget.charAt(i);
				if (String.valueOf(cWork).getBytes().length > 1) {
					nCharLen = 2;
				} else {
					nCharLen = 1;
				}
				if ((nLen + nCharLen) > length) {
					vctWork.addElement(sbWork.toString());
					sbWork = new StringBuffer("");
					nLen = 0;
				}
				nLen += nCharLen;
				sbWork.append(cWork);
			}
			vctWork.addElement(sbWork.toString());
		}
		return (String[]) vctWork.toArray(new String[0]);
	}

	/**
	* 将字符串（包括汉字，汉字算一个字符）分割成固定长度的字符串数组。<br>
	* @param strParamTarget 分割的字符串
	* @param length 固定长度
	* @return String[] 字符串数组。
	*/
	public static String[] splitByStringLength(String strParamTarget, int length) {
		Vector<Object> vctWork = new Vector<Object>();
		int nCharLen;
		int nLen = 0;
		int i;
		StringBuffer sbWork = new StringBuffer("");
		char cWork;
		if (strParamTarget == null) {
	
		} else {
			for (i = 0; i < strParamTarget.length(); i++) {
				cWork = strParamTarget.charAt(i);
				if (String.valueOf(cWork).getBytes().length > 1) {
					nCharLen = 1;
				} else {
					nCharLen = 1;
				}
				if ((nLen + nCharLen) > length) {
					vctWork.addElement(sbWork.toString());
					sbWork = new StringBuffer("");
					nLen = 0;
				}
				nLen += nCharLen;
				sbWork.append(cWork);
			}
			vctWork.addElement(sbWork.toString());
		}
		return (String[]) vctWork.toArray(new String[0]);
	}
	
    /**
	 * 检查输入的字符串是否全为半角（半角返回true; 全角返回false）。<br>
	 * @param str 被检查的字符串
	 * @return boolean 半角返回true; 全角返回false。
	 */
    public static boolean isAllHalfWidth(String str) {
        boolean result = true;
        for (int i = 0; (str != null) && (i < str.length()); i++) {
            char c = str.charAt(i);
            if (CharUtil.isHalfWidth(c)) {
                if ((c == '&') || (c == '<') || (c == '>') || (c == '"') || (c == '\'')) {
                    result = false;
                    break;
                } else {
                    continue;
                }
            } else {
                result = false;
                break;
            }
        }
        return result;
    }

	/**
	 * 检查是否为整数。可以为负整数，但是不能只有负号而没有其他数字（整数返回true, 非整数返回false）。<br>
	 * @param str 被检查的字符串
	 * @return boolean 整数返回true, 非整数返回false。
	 */
	public static boolean isInteger(String str) {
		int i = 0;
		if (str == null || str.trim().length() < 1) {
			return false;
		}
		boolean negative = false;
		if (str.charAt(0) == '-') {
			i++;
			negative = true;
		}
		while (i < str.length()) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
			i++;
		}
		if (negative && (i == 1)) {
			return false;
		}
		return true;
	}
    
    
    
	/**
	 * 去掉字符串两端的控制符，如果变为null或""，则返回""，引用org.apache.commons.lang.StringUtils.trimToEmpty(String str)方法。<br>
	 * 例：	<br>
	 * 		StringUtil.trimToEmpty(null)    = ""		<br>
 	 *		StringUtil.trimToEmpty("")      = ""		<br>
 	 *		StringUtil.trimToEmpty("    ")  = ""		<br>
 	 * 		StringUtil.trimToEmpty("abc")  = "abc"		<br>
 	 *		StringUtil.trimToEmpty(" abc ")= "abc"		<br>
	 * @param str 需处理的字符串 - 可能为null
	 * @return String 返回trim或转换后的字符串。
	 */
	public static String trimToEmpty(String str){
		return StringUtils.trimToEmpty(str);
	}
	
	/**
	 * 比较两个字符串是否相等，相等返回true，引用org.apache.commons.lang.StringUtils.equals(String str1, String str2)方法。<br>
	 * @param str1 字符串1 - 可能为null
	 * @param str2 字符串2 - 可能为null
	 * @return boolean 如果两个字符串相等返回true,否则返回false。
	 */
	public static boolean equals(String str1,String str2){
		return StringUtils.equals(str1, str2);
	}
	
	/**
	 * 比较两个字符串是否相等（不考虑大小写），引用org.apache.commons.lang.StringUtils.equalsIgnoreCase(String str1,String str2)方法。<br>
	 * 如果两个字符串的长度相同，并且其中的相应字符都相等（忽略大小写），则认为这两个字符串是相等的。<br>
	 * @param str1 字符串1 - 可能为null 
	 * @param str2 字符串2 - 可能为null
	 * @return boolean 如果参数不为null，且这两个String相等（忽略大小写），则返回 true；否则返回 false。
	 */
	public static boolean equalsIgnoreCase(String str1,String str2){
		return StringUtils.equalsIgnoreCase(str1, str2);
	}
	
	/**
	 * 判断某字符串是否为空或长度为0或由空白符(whitespace)构成，引用org.apache.commons.lang.StringUtils.isBlank(String str)方法。<br>
	 * 例：	<br>
	 * 		StringUtil.isBlank(null)      = true		<br>
	 *		StringUtil.isBlank("")        = true		<br>
 	 *		StringUtil.isBlank(" ")       = true		<br>
 	 *		StringUtil.isBlank("bob")     = false		<br>
 	 *		StringUtil.isBlank("  bob  ") = false		<br>
	 * @param str 要检查的字符串 - 可能为null
	 * @return boolean 如果为null或""或者有空格返回true,否则返回false。
	 */
	public static boolean isBlank(String str){
		return StringUtils.isBlank(str);
	}
	
	/**
	 * 判断某字符串是否不为空且长度不为0且不由空白符(whitespace)构成，引用org.apache.commons.lang.StringUtils.isNotBlank(String str)方法。<br>
	 * 和isBlank()方法相反。<br>
	 * 例：	<br>
	 * 		StringUtil.isNotBlank(null)      = false	<br>
 	 *		StringUtil.isNotBlank("")        = false	<br>
 	 *		StringUtil.isNotBlank(" ")       = false	<br>
 	 *		StringUtil.isNotBlank("bob")     = true		<br>
 	 *		StringUtil.isNotBlank("  bob  ") = true		<br>
	 * @param str 要检查的字符串 - 可能为null
	 * @return boolean 如果为null或""或者有空格返回false,否则返回true。
	 */
	public static boolean isNotBlank(String str){
		return StringUtils.isNotBlank(str);
	}
	
	/**
	 * 检查字符串是否不""空或者不为null，引用org.apache.commons.lang.StringUtils.isNotEmpty(String str)方法。<br>
	 * 例：	<br>
	 * 		StringUtil.isNotEmpty(null)      = false	<br>
	 *	 	StringUtil.isNotEmpty("")        = false	<br>
	 *	 	StringUtil.isNotEmpty(" ")       = true		<br>
	 *	 	StringUtil.isNotEmpty("bob")     = true		<br>
	 *	 	StringUtil.isNotEmpty("  bob  ") = true		<br>
	 * @param str 要检查的字符串 - 可能为null
	 * @return boolean 如果不为""或null返回true，否则返回false。
	 */
	public static boolean isNotEmpty(String str){
		return StringUtils.isNotEmpty(str);
	}
	
	/**
	 * 检查字符串的内容是否只包含数字，字符串为null将返回false，""将返回true，引用org.apache.commons.lang.StringUtils.isNumeric(String str)方法。<br>
	 * 例：	 <br>
	 * 		 StringUtil.isNumeric(null)   = false		<br>
	 *		 StringUtil.isNumeric("")     = true		<br>
	 *		 StringUtil.isNumeric("  ")   = false		<br>
	 *		 StringUtil.isNumeric("123")  = true		<br>
	 *		 StringUtil.isNumeric("12 3") = false		<br>
	 *		 StringUtil.isNumeric("12.3") = false		<br>
	 * @param str 要检查的字符串 - 可能为null
	 * @return boolean 如果字符串不为null且值包含数字返回true,否则返回false。
	 */
	public static boolean isNumeric(String str){
		return StringUtils.isNumeric(str);
	}
	
	/**
	 * 检查字符串的内容是否只包含数字或空格，字符串为null将返回false，""将返回true，引用org.apache.commons.lang.StringUtils.isNumericSpace(String str)方法。<br>
	 * 例：	 <br>
	 * 		 StringUtil.isNumeric(null)   = false		<br>
	 *	 	 StringUtil.isNumeric("")     = true		<br>
	 *	 	 StringUtil.isNumeric("  ")   = true		<br>
	 *	 	 StringUtil.isNumeric("123")  = true		<br>
	 *	 	 StringUtil.isNumeric("12 3") = true		<br>
	 *	 	 StringUtil.isNumeric("12.3") = false		<br>
	 * @param str 要检查的字符串 - 可能为null
	 * @return boolean 如果字符串不为null且只包含数字或空格返回true,否则返回false。
	 */
	public static boolean isNumericSpace(String str){
		return StringUtils.isNumericSpace(str);
	}
	
	/**
	 * 检查字符串的内容是否只包含空格，字符串为null将返回false，""将返回true，引用org.apache.commons.lang.StringUtils.isWhitespace(String str)方法。	<br>
	 * 例：	 <br>
	 * 		 StringUtil.isWhitespace(null)   = false	<br>
	 *		 StringUtil.isWhitespace("")     = true		<br>
	 *		 StringUtil.isWhitespace("  ")   = true		<br>
	 *		 StringUtil.isWhitespace("abc")  = false	<br>
	 * @param str 要检查的字符串 - 可能为null
	 * @return boolean 如果字符串不为null且只包含空格返回true,否则返回false。
	 */
	public static boolean isWhitespace(String str){
		return StringUtils.isWhitespace(str);
	}
	
	/**
	 * 检查字符串中的内容是否只包含字母，字符串为null将返回false，""将返回true，引用org.apache.commons.lang.StringUtils.isAlpha(String str)方法。<br>
	 * 例：	<br>
	 * 		StringUtil.isAlpha(null)   = false		<br>
	 *	 	StringUtil.isAlpha("")     = true		<br>
	 *	 	StringUtil.isAlpha("  ")   = false		<br>
	 *	 	StringUtil.isAlpha("abc")  = true		<br>
	 * @param str 要检查的字符串 - 可能为null
	 * @return boolean 如果字符串不为null且只包含字母返回true,否则返回false。
	 */
	public static boolean isAlpha(String str){
		return StringUtils.isAlpha(str);
	}
	
	/**
	 * 检查字符串中的内容是否只包含字母和空格，字符串为null将返回false，""将返回true，引用org.apache.commons.lang.StringUtils.isAlphaSpace(String str)方法。<br>
	 * 例：	 <br>
	 * 		 StringUtil.isAlphaSpace(null)   = false	<br>
	 *		 StringUtil.isAlphaSpace("")     = true		<br>
	 *		 StringUtil.isAlphaSpace("  ")   = true		<br>
	 *		 StringUtil.isAlphaSpace("abc")  = true		<br>
	 *		 StringUtil.isAlphaSpace("ab c") = true		<br>
	 *		 StringUtil.isAlphaSpace("ab-c") = false	<br>
	 * @param str 要检查的字符串 - 可能为null
	 * @return boolean 如果字符串不为null且只包含字母和空格返回true,否则返回false。
	 */
	public static boolean isAlphaSpace(String str){
		return StringUtils.isAlphaSpace(str);
	}
	
	/**
	 * 检查字符串中的内容是否只包含数字或字母，字符串为null将返回false，""将返回true，引用org.apache.commons.lang.StringUtils.isAlphanumeric(String str)方法。<br>
	 * 例：	 <br>
	 * 		 StringUtil.isAlphanumeric(null)   = false		<br>
	 *		 StringUtil.isAlphanumeric("")     = true		<br>
	 *		 StringUtil.isAlphanumeric("  ")   = false		<br>
	 *		 StringUtil.isAlphanumeric("abc")  = true		<br>
	 *		 StringUtil.isAlphanumeric("ab c") = false		<br>
	 *		 StringUtil.isAlphanumeric("ab2c") = true		<br>
	 * @param str 要检查的字符串 - 可能为null
	 * @return boolean 如果字符串不为null且只包含字母或数字返回true,否则返回false。
	 */
	public static boolean isAlphanumeric(String str){
		return StringUtils.isAlphanumeric(str);
	}
	
	/**
	 * 检查字符串中的内容是否只包含数字、字母或空格，字符串为null将返回false，""将返回true，引用org.apache.commons.lang.StringUtils.isAlphanumericSpace(String str)方法。<br>
	 * 例：	 <br>
	 * 		 StringUtil.isAlphanumeric(null)   = false		<br>
	 *		 StringUtil.isAlphanumeric("")     = true		<br>
	 *		 StringUtil.isAlphanumeric("  ")   = true		<br>
	 *		 StringUtil.isAlphanumeric("abc")  = true		<br>
	 *		 StringUtil.isAlphanumeric("ab c") = true		<br>
	 *		 StringUtil.isAlphanumeric("ab2c") = true		<br>
	 *		 StringUtil.isAlphanumeric("ab-c") = false		<br>
	 * @param str 要检查的字符串 - 可能为null
	 * @return boolean 如果字符串不为null且只包含字母、数字或空格返回true,否则返回false。
	 */
	public static boolean isAlphanumericSpace(String str){
		return StringUtils.isAlphanumericSpace(str);
	}
	
	/**
	 * 检查字符串是否只含有ASCII的可打印字符，字符串为null将返回false，""将返回true，引用org.apache.commons.lang.StringUtils.isAsciiPrintable(String str)方法。<br>
	 * 例：	 <br>
	 * 		 StringUtil.isAsciiPrintable(null)     = false		<br>
	 *		 StringUtil.isAsciiPrintable("")       = true		<br>
	 *		 StringUtil.isAsciiPrintable(" ")      = true		<br>
	 *		 StringUtil.isAsciiPrintable("Ceki")   = true		<br>
	 *		 StringUtil.isAsciiPrintable("ab2c")   = true		<br>
	 *		 StringUtil.isAsciiPrintable("!ab-c~") = true		<br>
	 *		 StringUtil.isAsciiPrintable("!")		 = true		<br>
	 *	 	 StringUtil.isAsciiPrintable("Ceki Gülcü") = false	<br>
	 * @param str 要检查的字符串 - 可能为null
	 * @return boolean 如果字符串每个字符都在ASCII范围之内返回true,否则返回false。
	 */
	public static boolean isAsciiPrintable(String str){
		return StringUtils.isAsciiPrintable(str);
	}
	
	
	
		
	/**
	 * 返回str的一个子字符串，该子字符串从指定索引处start的字符开始，直到此字符串末尾，引用org.apache.commons.lang.StringUtils.subString(String str,int start)方法。<br>
	 * 假如str为null将返回null,""将返回""。<br>
	 * 例：	 <br>
	 * 		 StringUtil.substring(null, *)   = null			<br>
	 *		 StringUtil.substring("", *)     = ""			<br>
	 *		 StringUtil.substring("abc", 0)  = "abc"		<br>
	 *		 StringUtil.substring("abc", 2)  = "c"			<br>
	 *		 StringUtil.substring("abc", 4)  = ""			<br>
	 *		 StringUtil.substring("abc", -2) = "bc"			<br>
	 *		 StringUtil.substring("abc", -4) = "abc"		<br>
	 * @param str 返回子串的字符串  - 可能为null
	 * @param start 起始索引 - 可以为负数
	 * @return String 如果字符串不为null返回从start开始的子字符串,否则返回null。
	 */
	public static String subString(String str,int start){
		return StringUtils.substring(str, start);
	}
	
	
	
	
	
	/**
	 * 返回str的一个子字符串，该子字符串从指定的start处开始，直到索引end-1处的字符，引用org.apache.commons.lang.StringUtils.subString(String str,int start,int end)方法。<br>
	 * 例：	 <br>
	 * 		 StringUtil.substring(null, *, *)    = null		<br>
	 *		 StringUtil.substring("", * ,  *)    = "";		<br>
	 *		 StringUtil.substring("abc", 0, 2)   = "ab"		<br>
	 *		 StringUtil.substring("abc", 2, 0)   = ""		<br>
	 *		 StringUtil.substring("abc", 2, 4)   = "c"		<br>
	 *		 StringUtil.substring("abc", 4, 6)   = ""		<br>
	 *		 StringUtil.substring("abc", 2, 2)   = ""		<br>
	 *		 StringUtil.substring("abc", -2, -1) = "b"		<br>
	 *		 StringUtil.substring("abc", -4, 2)  = "ab"		<br>
	 * @param str 返回子串的字符串  - 可能为null
	 * @param start 起始索引  - 可以为负数
	 * @param end 结束索引  - 可以为负数
	 * @return String 如果字符串不为null返回从start开始到end结束的子字符串,否则返回null。
	 */
	public static String substring(String str,int start,int end){
		return StringUtils.substring(str, start, end);
	}
	
	
	
	
	
	/**
	 * 返回str的一个从separator开始后的一个子字符串（从最左面出现的separator开始，不包括separator），引用org.apache.commons.lang.StringUtils.substringAfter(String str,String separator)方法。<br>
	 * 字符串str为null将返回null，""将返回""。字符串str不为null时，如果separator为null将返回""。<br>
	 * 例：	 <br>
	 * 		 StringUtil.substringAfter(null, *)      = null		<br>
	 *		 StringUtil.substringAfter(*, null)      = ""		<br>
	 *		 StringUtil.substringAfter("abcba", "b") = "cba"	<br>
	 *		 StringUtil.substringAfter("abc", "c")   = ""		<br>
	 *		 StringUtil.substringAfter("abc", "d")   = ""		<br>
	 *		 StringUtil.substringAfter("abc", "")    = "abc"	<br>
	 * @param str 返回子串的字符串  - 可能为null
	 * @param separator 开始的字符串位置  - 可能为null
	 * @return String 如果字符串不为null返回从separator后的一个子字符串，否则返回null。
	 */
	public static String substringAfter(String str,String separator){
		return StringUtils.substringAfter(str, separator);
	}
	
	/**
	 * 返回str的一个从separator开始后的一个子字符串（从最右面出现的separator开始，不包括separator），引用org.apache.commons.lang.StringUtils.substringAfterLast(String str,String separator)方法。<br>
	 * 字符串str为null将返回null，""将返回""。字符串str不为null时，如果separator为null将返回""。<br>
	 * 例：<br>
	 *	 StringUtil.substringAfterLast(null, *)      = null	<br>
	 *	 StringUtil.substringAfterLast("", *)        = ""	<br>
	 *	 StringUtil.substringAfterLast(*, "")        = ""	<br>
	 *	 StringUtil.substringAfterLast(*, null)      = ""	<br>
	 *	 StringUtil.substringAfterLast("abc", "a")   = "bc"	<br>
	 *	 StringUtil.substringAfterLast("abcba", "b") = "a"	<br>
	 *	 StringUtil.substringAfterLast("abc", "c")   = ""	<br>
	 *	 StringUtil.substringAfterLast("a", "a")     = ""	<br>
	 *	 StringUtil.substringAfterLast("a", "z")     = ""	<br>
	 * @param str 返回子串的字符串  - 可能为null
	 * @param separator 开始的字符串位置  - 可能为null
	 * @return String 如果字符串不为null返回从separator后的一个子字符串，否则返回null。
	 */
	public static String substringAfterLast(String str,String separator){
		return StringUtils.substringAfterLast(str, separator);
	}
	
	/**
	 * 返回str的一个从separator结束前的一个子字符串（从最左面出现的separator开始，不包括separator），引用org.apache.commons.lang.StringUtils.substringBefore(String str,String separator)方法。<br>
	 * 字符串str为null将返回null，""将返回""。假如字符串str不为null时，如果separator为null或""将返回""。<br>
	 * 例：<br>
	 *	 StringUtil.substringBefore(null, *)      = null	<br>
	 *	 StringUtil.substringBefore("abc", "a")   = ""		<br>
	 *	 StringUtil.substringBefore("abcba", "b") = "a"		<br>
	 *	 StringUtil.substringBefore("abc", "c")   = "ab"	<br>
	 *	 StringUtil.substringBefore("abc", "d")   = "abc"	<br>
	 *	 StringUtil.substringBefore("abc", "")    = ""		<br>
	 *	 StringUtil.substringBefore("abc", null)  = "abc"	<br>
	 * @param str 返回子串的字符串  - 可能为null
	 * @param separator 结束的字符串位置  - 可能为null
	 * @return String 如果字符串不为null返回从separator之前的一个子字符串，否则返回null。
	 */
	public static String substringBefore(String str,String separator){
		return StringUtils.substringBefore(str, separator);
	}
	
	/**
	 * 返回str的一个从separator结束前的一个子字符串（从最右面出现的separator开始，不包括separator），引用org.apache.commons.lang.StringUtils.substringBeforeLast(String str,String separator)方法。<br>
	 * 字符串str为null将返回null，""将返回""。假如字符串str不为null时，如果separator为null或""将返回该字符串str。<br>
	 * 例：<br>
	 *  StringUtil.substringBeforeLast(null, *)      = null		<br>
	 *	StringUtil.substringBeforeLast("", *)        = ""		<br>
	 *	StringUtil.substringBeforeLast("abcba", "b") = "abc"	<br>
	 *	StringUtil.substringBeforeLast("abc", "c")   = "ab"		<br>
	 *	StringUtil.substringBeforeLast("a", "a")     = ""		<br>
	 *	StringUtil.substringBeforeLast("a", "z")     = "a"		<br>
	 *	StringUtil.substringBeforeLast("a", null)    = "a"		<br>
	 *	StringUtil.substringBeforeLast("a", "")      = "a"		<br>
	 * @param str 返回子串的字符串  - 可能为null
	 * @param separator 结束的字符串位置  - 可能为null
	 * @return String 如果字符串不为null返回从separator之前的一个子字符串，否则返回null。
	 */
	public static String substringBeforeLast(String str,String separator){
		return StringUtils.substringBeforeLast(str, separator);
	}
	
	/**
	 * 在字符串str中返回一个在字符串tag之间的一个子字符串，引用org.apache.commons.lang.StringUtils.substringBetween(String str,String tag)方法。<br>
	 * 如果str为null将返回null,如果tag为null将返回null。<br>
	 * 例：<br>
	 *	 StringUtil.substringBetween(null, *)            = null		<br>
	 *	 StringUtil.substringBetween("", "")             = ""		<br>
	 *	 StringUtil.substringBetween("", "tag")          = null		<br>
	 *	 StringUtil.substringBetween("tagabctag", null)  = null		<br>
	 *	 StringUtil.substringBetween("tagabctag", "")    = ""		<br>
	 *	 StringUtil.substringBetween("tagabctag", "tag") = "abc"	<br>
	 * @param str 包含子串的字符串 - 可能为null
	 * @param tag 字符串str的开始和结束位置 - 可能为null
	 * @return String 返回子字符串，如果没有匹配的将返回null 。
	 */
	public static String substringBetween(String str,String tag){
		return StringUtils.substringBetween(str, tag);
	}
	
	/**
	 * 获得在str中从open开始到close结束之间的子字符串(返回最左面匹配的子字符串)，引用org.apache.commons.lang.StringUtils.substringBetween(String str,String open,String close)方法。<br>
	 * 如果str为null将返回null，如果open或close为null将返回null(没有匹配的)，如果open和close为""将返回""字符串。<br>
	 * 例：<br>
	 *	 StringUtil.substringBetween("wx[b]yz", "[", "]") = "b"		<br>
	 *	 StringUtil.substringBetween(null, *, *)          = null	<br>
	 *	 StringUtil.substringBetween(*, *, null)          = null	<br>
	 *	 StringUtil.substringBetween("", "", "")          = ""		<br>
	 *	 StringUtil.substringBetween("", "[", "]")        = null	<br>
	 *	 StringUtil.substringBetween("yabcz", "", "")     = ""		<br>
	 *	 StringUtil.substringBetween("yabczyabz", "y", "z")   = "abc"	<br>
	 * @param str 包含子串的字符串 - 可能为null
	 * @param open 子字符串的开始位置 - 可能为null
	 * @param close 子字符串的结束位置 - 可能为null
	 * @return String 返回匹配的子字符串，没有匹配的返回null。
	 */
	public static String substringBetween(String str,String open,String close){
		return StringUtils.substringBetween(str, open, close);
	}
	
	/**
	 * 返回所有在字符串str中从open开始到close结束的子字符串数组，引用org.apache.commons.lang.StringUtils.substringsBetween(String str,String open,String close)方法。<br>
	 * 如果str为null将返回null，如果open或close为null将返回null(没有匹配的)，如果open和close为""将返回null(没有匹配的)。<br>
	 * 例：<br>
	 *	 StringUtil.substringsBetween("[a][b][c]", "[", "]") = ["a","b","c"]		<br>
	 *	 StringUtil.substringsBetween(null, *, *)            = null		<br>
	 *	 StringUtil.substringsBetween(*, null, *)            = null		<br>
	 *	 StringUtil.substringsBetween(*, *, null)            = null		<br>
	 *	 StringUtil.substringsBetween("", "[", "]")          = []		<br>
	 * @param str 包含子串的字符串（null将返回null,""将返回""）
	 * @param open 查找子字符串的开始位置（""将返回null）
	 * @param close 查找子字符串的结束位置（""将返回null）
	 * @return String 返回子字符串数组，如果没有匹配的返回null。
	 */
	public static String[] substringsBetween(String str,String open,String close){
		return StringUtils.substringsBetween(str, open, close);
	}
	
	
	
	
	
	/**
	 * 字符串str为null或""替换，假如str为null或""将返回默认的字符串defaultStr，引用org.apache.commons.lang.StringUtils.defaultIfEmpty(String str,String defaultStr)方法。<br>
	 * 例：<br>
	 *	 StringUtil.defaultIfEmpty(null, "NULL")  = "NULL"	<br>
	 *	 StringUtil.defaultIfEmpty("", "NULL")    = "NULL"	<br>
	 *	 StringUtil.defaultIfEmpty("bat", "NULL") = "bat"	<br>
	 * @param str 被检查的字符串 - 可以为null
	 * @param defaultStr 将被返回的默认字符串 - 可以为null
	 * @return String 返回字符串本身或默认的字符串。
	 */
	public static String defaultIfEmpty(String str,String defaultStr){
		return StringUtils.defaultIfEmpty(str, defaultStr);
	}
	
	/**
	 * 字符串str为null或""处理，假如str为null或""将返回""，引用org.apache.commons.lang.StringUtils.defaultString(String str)方法。<br>
	 * 和原国旅工具类里的nvl(str)方法相同。<br>
	 * 例：<br>
	 * 	 StringUtils.defaultString(null)  = ""		<br>
	 *	 StringUtils.defaultString("")    = ""		<br>
	 *	 StringUtils.defaultString("bat") = "bat"	<br>
	 * @param str 被检查的字符串 - 可以为null
	 * @return String 返回字符串本身或""。
	 */
	public static String defaultString(String str){
		return StringUtils.defaultString(str);
	}
	
	/**
	 * 字符串str为null或""替换,假如str为null将返回默认的字符串defaultStr，为""将返回"",引用org.apache.commons.lang.StringUtils.defaultString(String str,String defaultStr)方法。<br>
	 * 例：<br>
	 *	 StringUtils.defaultString(null, "NULL")  = "NULL"	<br>
	 *	 StringUtils.defaultString("", "NULL")    = ""		<br>
	 *	 StringUtils.defaultString("bat", "NULL") = "bat"	<br>
	 * @param str 被检查的字符串 - 可以为null
	 * @param defaultStr 将被返回的默认字符串 - 可以为null
	 * @return String 返回字符串本身或默认的字符串或""。
	 */
	public static String defaultString(String str,String defaultStr){
		return StringUtils.defaultString(str, defaultStr);
	}
	
	
	
	
	
	/**
	 * 用空格作为分割符，分割str为数组，分隔符不能作为数组中的元素返回(空格不可以当作数组中的一个元素返回)，引用org.apache.commons.lang.StringUtils.split(String str)方法。<br>
	 * 如果str为null将返回null。<br>
	 * 例：<br>
	 *	 StringUtil.split(null)       = null				<br>
	 *	 StringUtil.split("")         = []					<br>
	 *	 StringUtil.split("abc def")  = ["abc", "def"]		<br>
	 *	 StringUtil.split("abc  def") = ["abc", "def"]		<br>
	 *	 StringUtil.split(" abc ")    = ["abc"]				<br>
	 * @param str 分割的字符串 - 可能为null
	 * @return String[] 返回分割后的字符串数组，如果str为null将返回null。
	 */
	public static String[] split(String str) {
		return StringUtils.split(str);
	}
	
	/**
	 * 参数separatorChar字符作为分隔符，把字符串str分割为数组，分隔符不能作为数组中的元素返回(空格不可以当作数组中的一个元素返回)，引用org.apache.commons.lang.StringUtils.split方法(String str,char separatorChar)。<br>
	 * 如果str为null将返回null。<br>
	 * 例：<br>
	 *	 StringUtil.split(null, *)         = null						<br>
	 *	 StringUtil.split("", *)           = []							<br>
	 *	 StringUtil.split("a.b.c", '.')    = ["a", "b", "c"]			<br>
	 *	 StringUtil.split("a..b.c", '.')   = ["a", "b", "c"]			<br>
	 *	 StringUtil.split("a:b:c", '.')    = ["a:b:c"]					<br>
	 *	 StringUtil.split("a\tb\nc", null) = ["a", "b", "c"]			<br>
	 *	 StringUtil.split("a b c", ' ')    = ["a", "b", "c"]			<br>
	 * @param str 分割的字符串 - 可能为null
	 * @param separatorChar 分隔符 - null当作空格分隔符
	 * @return String[] 分隔后的数组，如果str为null将返回null。
	 */
	public static String[] split(String str,char separatorChar) {
		return StringUtils.split(str, separatorChar);
	}
	
	/**
	 * 参数separatorChars字符串作为分隔符，把字符串str分割为数组，分隔符不能作为数组中的元素返回(空格不可以当作数组中的一个元素返回)，引用org.apache.commons.lang.StringUtils.split(String str,String separatorChars)方法。<br>
	 * 如果str为null将返回null，null当作空格分隔符。<br>
	 * 例：<br>
	 *	 StringUtil.split(null, *)         = null					<br>
	 *	 StringUtil.split("", *)           = []						<br>
	 *	 StringUtil.split("abc def", null) = ["abc", "def"]			<br>
	 *	 StringUtil.split("abc def", " ")  = ["abc", "def"]			<br>
	 *	 StringUtil.split("abc  def", " ") = ["abc", "def"]			<br>
	 *	 StringUtil.split("ab:cd:ef", ":") = ["ab", "cd", "ef"]		<br>
	 * @param str 分割的字符串 - 可能为null
	 * @param separatorChars 分隔符 - null当作空格分隔符
	 * @return String[] 分隔后的数组，如果str为null将返回null。
	 */
	public static String[] split(String str,String separatorChars) {
		return StringUtils.split(str, separatorChars);
	}
	
	/**
	 * 根据参数separatorChars字符串作为分隔符按照最大长度max，把字符串str分割为数组，分隔符不能作为数组中的元素返回(空格不可以当作数组中的一个元素返回)，引用org.apache.commons.lang.StringUtils.split(String str,String separatorChars,int max)方法。<br>
	 * 如果按分隔符分割出来的数组大于max，就把分隔符最左面的作为数组中的一个元素，右面作为另一个元素。<br>
	 * 如果str为null将返回null，null当作空格分隔符。<br>
	 * 例：<br>
	 *	 StringUtil.split(null, *, *)            = null					<br>
	 *	 StringUtil.split("", *, *)              = []					<br>
	 *	 StringUtil.split("ab de fg", null, 0)   = ["ab", "cd", "ef"]	<br>
	 *	 StringUtil.split("ab   de fg", null, 0) = ["ab", "cd", "ef"]	<br>
	 *	 StringUtil.split("ab:cd:ef", ":", 0)    = ["ab", "cd", "ef"]	<br>
	 *	 StringUtil.split("ab:cd:ef", ":", 2)    = ["ab", "cd:ef"]		<br>
	 * @param str 分割的字符串 - 可能为null
	 * @param separatorChars  分隔符 - null当作空格分隔符
	 * @param max 在此长度基础上分隔 - 0或负数表示长度没有限制
	 * @return String[] 分隔后的数组，如果str为null将返回null。
	 */
	public static String[] split(String str,String separatorChars,int max) {
		return StringUtils.split(str, separatorChars, max);
	}
	
	/**
	 * 完全按照给定的参数separatorChars作为分隔符把字符串str分割为数组，分隔符不能作为数组中的元素返回(空格不可以当作数组中的一个元素返回)，引用org.apache.commons.lang.StringUtils.splitByWholeSeparator(String str,String separator)方法。<br>
	 * 如果str为null将返回null，null当作空格分隔符。<br>
	 * 例：	<br>
	 *	 StringUtil.splitByWholeSeparator(null, *)               = null						<br>
	 *	 StringUtil.splitByWholeSeparator("", *)                 = []						<br>
	 *	 StringUtil.splitByWholeSeparator("ab de fg", null)      = ["ab", "de", "fg"]		<br>
	 *	 StringUtil.splitByWholeSeparator("ab   de fg", null)    = ["ab", "de", "fg"]		<br>
	 *	 StringUtil.splitByWholeSeparator("ab:cd:ef", ":")       = ["ab", "cd", "ef"]		<br>
	 *	 StringUtil.splitByWholeSeparator("ab-!-cd-!-ef", "-!-") = ["ab", "cd", "ef"]		<br>
	 * @param str 分割的字符串 - 可能为null
	 * @param separator 分隔符 - null当作空格分隔符
	 * @return String[] 分隔后的数组，如果str为null将返回null。
	 */
	public static String[] splitByWholeSeparator(String str,String separator) {
		return StringUtils.splitByWholeSeparator(str, separator);
	}
	
	/**
	 * 根据参数separatorChars字符串作为分隔符按照最大长度max，把字符串str分割为数组，分隔符不能作为数组中的元素返回(空格不可以当作数组中的一个元素返回)，引用org.apache.commons.lang.StringUtils.split(String str,String separator,int max)方法。<br>
	 * 如果按分隔符分割出来的数组大于max，就把分隔符最左面的作为数组中的一个元素，右面作为另一个元素。<br>
	 * 如果str为null将返回null，null当作空格分隔符。<br>
	 * 例：<br>
	 *	 StringUtil.splitByWholeSeparator(null, *, *)               = null					<br>
	 *	 StringUtil.splitByWholeSeparator("", *, *)                 = []					<br>
	 *	 StringUtil.splitByWholeSeparator("ab de fg", null, 0)      = ["ab", "de", "fg"]	<br>
	 *	 StringUtil.splitByWholeSeparator("ab   de fg", null, 0)    = ["ab", "de", "fg"]	<br>
	 *	 StringUtil.splitByWholeSeparator("ab:cd:ef", ":", 2)       = ["ab", "cd:ef"]		<br>
	 *	 StringUtil.splitByWholeSeparator("ab-!-cd-!-ef", "-!-", 5) = ["ab", "cd", "ef"]	<br>
	 *	 StringUtil.splitByWholeSeparator("ab-!-cd-!-ef", "-!-", 2) = ["ab", "cd-!-ef"]		<br>
	 * @param str 分割的字符串 - 可能为null
	 * @param separator  分隔符 - null当作空格分隔符
	 * @param max 在此长度基础上分隔 - 0或负数表示长度没有限制
	 * @return String[] 分隔后的数组，如果str为null将返回null。
	 */
	public static String[] splitByWholeSeparator(String str,String separator,int max) {
		return StringUtils.splitByWholeSeparator(str, separator, max);
	}
	
	/**
	 * 用空格作为分割符，分割str为数组，分隔符不能作为数组中的元素返回(空格可以当作数组中的一个元素返回)，引用org.apache.commons.lang.StringUtils.splitPreserveAllTokens(String str)方法。<br>
	 * 如果str为null将返回null，null当作空格分隔符。<br>
	 * 例：<br>
	 *	 StringUtil.splitPreserveAllTokens(null)       = null					<br>
	 *	 StringUtil.splitPreserveAllTokens("")         = []						<br>
	 *	 StringUtil.splitPreserveAllTokens("abc  def") = ["abc", "", "def"]		<br>
	 *	 StringUtil.splitPreserveAllTokens(" abc ")    = ["", "abc", ""]		<br>
	 * @param str 分割的字符串 - 可能为null
	 * @return String[] 分隔后的数组，如果str为null将返回null。
	 */
	public static String[] splitPreserveAllTokens(String str) {
		return StringUtils.splitPreserveAllTokens(str);
	}
	
	/**
	 * 参数separatorChar字符作为分隔符，把字符串str分割为数组，分隔符不能作为数组中的元素返回(空格可以当作数组中的一个元素返回)，引用org.apache.commons.lang.StringUtils.splitPreserveAllTokens(String str,char separatorChar)方法。<br>
	 * 如果str为null将返回null。<br>
	 * 例：<br>
	 *	 StringUtil.splitPreserveAllTokens(null, *)         = null						<br>
	 *	 StringUtil.splitPreserveAllTokens("", *)           = []						<br>
	 *	 StringUtil.splitPreserveAllTokens("a..b.c", '.')   = ["a", "", "b", "c"]		<br>
	 *	 StringUtil.splitPreserveAllTokens("a\tb\nc", null) = ["a", "b", "c"]			<br>
	 *	 StringUtil.splitPreserveAllTokens("a b c", ' ')    = ["a", "b", "c"]			<br>
	 *	 StringUtil.splitPreserveAllTokens("a b c ", ' ')   = ["a", "b", "c", ""]		<br>
	 *	 StringUtil.splitPreserveAllTokens("a b c  ", ' ')   = ["a", "b", "c", "", ""]	<br>
	 *	 StringUtil.splitPreserveAllTokens(" a b c", ' ')   = ["", a", "b", "c"]		<br>
	 *	 StringUtil.splitPreserveAllTokens("  a b c", ' ')  = ["", "", a", "b", "c"]	<br>
	 *	 StringUtil.splitPreserveAllTokens(" a b c ", ' ')  = ["", a", "b", "c", ""]	<br>
	 * @param str 分割的字符串 - 可能为null
	 * @param separatorChar 分隔符 - null当作空格分隔符
	 * @return String[] 分隔后的数组，如果str为null将返回null。
	 */
	public static String[] splitPreserveAllTokens(String str,char separatorChar) {
		return StringUtils.splitPreserveAllTokens(str, separatorChar);
	}
	
	/**
	 * 参数separatorChars字符串作为分隔符，把字符串str分割为数组，分隔符不能作为数组中的元素返回(空格可以当作数组中的一个元素返回)，引用org.apache.commons.lang.StringUtils.splitPreserveAllTokens(String str,String separatorChars)方法。<br>
	 * 如果str为null将返回null，null当作空格分隔符。<br>
	 * 例：<br>
	 *	 StringUtil.splitPreserveAllTokens(null, *)           = null					<br>
	 *	 StringUtil.splitPreserveAllTokens("", *)             = []						<br>
	 *	 StringUtil.splitPreserveAllTokens("abc def", null)   = ["abc", "def"]			<br>
	 *	 StringUtil.splitPreserveAllTokens("abc  def", " ")   = ["abc", "", def"]		<br>
	 *	 StringUtil.splitPreserveAllTokens("ab:cd:ef", ":")   = ["ab", "cd", "ef"]		<br>
	 *	 StringUtil.splitPreserveAllTokens("ab:cd:ef:", ":")  = ["ab", "cd", "ef", ""]	<br>
	 *	 StringUtil.splitPreserveAllTokens("ab:cd:ef::", ":") = ["ab", "cd", "ef", "", ""]	<br>
	 *	 StringUtil.splitPreserveAllTokens("ab::cd:ef", ":")  = ["ab", "", cd", "ef"]	<br>
	 *	 StringUtil.splitPreserveAllTokens(":cd:ef", ":")     = ["", cd", "ef"]			<br>
	 *	 StringUtil.splitPreserveAllTokens("::cd:ef", ":")    = ["", "", cd", "ef"]		<br>
	 *	 StringUtil.splitPreserveAllTokens(":cd:ef:", ":")    = ["", cd", "ef", ""]		<br>
	 * @param str 分割的字符串 - 可能为null
	 * @param separatorChars 分隔符 - null当作空格分隔符
	 * @return String[] 分隔后的数组，如果str为null将返回null。
	 */
	public static String[] splitPreserveAllTokens(String str,String separatorChars) {
		return StringUtils.splitPreserveAllTokens(str, separatorChars);
	}
	
	/**
	 * 根据参数separatorChars字符串作为分隔符按照最大长度max，把字符串str分割为数组，分隔符不能作为数组中的元素返回(空格可以当作数组中的一个元素返回)，引用org.apache.commons.lang.StringUtils.splitPreserveAllTokens(String str,String separatorChars,int max)方法。<br>
	 * 如果按分隔符分割出来的数组大于max，就把分隔符最左面的作为数组中的一个元素，右面作为另一个元素。<br>
	 * 如果str为null将返回null，null当作空格分隔符<br>
	 * 例：<br>
	 *	 StringUtil.splitPreserveAllTokens(null, *, *)            = null]					<br>
	 *	 StringUtil.splitPreserveAllTokens("", *, *)              = []]						<br>
	 *	 StringUtil.splitPreserveAllTokens("ab   de fg", null, 0) = ["ab", "cd", "ef"]]		<br>
	 *	 StringUtil.splitPreserveAllTokens("ab:cd:ef", ":", 2)    = ["ab", "cd:ef"]]		<br>
	 *	 StringUtil.splitPreserveAllTokens("ab   de fg", null, 2) = ["ab", "  de fg"]]		<br>
	 *	 StringUtil.splitPreserveAllTokens("ab   de fg", null, 3) = ["ab", "", " de fg"]]	<br>
	 *	 StringUtil.splitPreserveAllTokens("ab   de fg", null, 4) = ["ab", "", "", "de fg"]]<br>
	 * @param str 分割的字符串 - 可能为null
	 * @param separatorChars  分隔符 - null当作空格分隔符
	 * @param max 在此长度基础上分隔 - 0或负数表示长度没有限制
	 * @return String[] 分隔后的数组，如果str为null将返回null。
	 */
	public static String[] splitPreserveAllTokens(String str,String separatorChars,int max) {
		return StringUtils.splitPreserveAllTokens(str, separatorChars, max);
	}

	
	
	
	
	/**
	 * 获得从字符串str从最左面开始len长度字符串，引用org.apache.commons.lang.StringUtils.left(String str,int len)方法。<br>
	 * 例：<br>
	 *	 StringUtil.left(null, *)    = null	<br>
	 *	 StringUtil.left(*, -ve)     = ""	<br>
	 *	 StringUtil.left("", *)      = ""	<br>
	 *	 StringUtil.left("abc", 0)   = ""	<br>
	 *	 StringUtil.left("abc", 2)   = "ab"	<br>
	 *	 StringUtil.left("abc", 4)   = "abc"	<br>
	 * @param str 包含子串的字符串 - 可能为null
	 * @param len 长度 - 必须为0或正数
	 * @return String 返回最左面开始len长度的字符串，如果str为null将返回null。
	 */
	public static String left(String str,int len){
		return StringUtils.left(str, len);
	}
	
	/**
	 * 用空格填补在字符串的最左面，填补的长度等于参数size-str的长度，引用org.apache.commons.lang.StringUtils.leftPad(String str,int size)方法。<br>
	 * 例：	<br>
	 *   StringUtil.leftPad(null, *)   = null	<br>
	 *	 StringUtil.leftPad("", 3)     = "   "	<br>
	 *	 StringUtil.leftPad("bat", 3)  = "bat"	<br>
	 *	 StringUtil.leftPad("bat", 5)  = "  bat"<br>
	 *	 StringUtil.leftPad("bat", 1)  = "bat"	<br>
	 *	 StringUtil.leftPad("bat", -1) = "bat"	<br>
	 * @param str 填补前的字符串 - 可以为null
	 * @param size 可能是填补后的字符串的长度
	 * @return String 左面被填补后的字符串或原始的字符串，如果参数size-str的长度=>1就不用填补，如果字符串为null就返回null。
	 */
	public static String leftPad(String str,int size){
		return StringUtils.leftPad(str, size);
	}
	
	/**
	 * 用字符padChar填补在字符串的最左面，填补的长度等于参数size-str的长度，引用org.apache.commons.lang.StringUtils.leftPad(String str,int size,char padChar)方法。<br>
	 * 例：	<br>
	 *	 StringUtil.leftPad(null, *, *)     = null		<br>
	 *	 StringUtil.leftPad("", 3, 'z')     = "zzz"		<br>
	 *	 StringUtil.leftPad("bat", 3, 'z')  = "bat"		<br>
	 *	 StringUtil.leftPad("bat", 5, 'z')  = "zzbat"	<br>
	 *	 StringUtil.leftPad("bat", 1, 'z')  = "bat"		<br>
	 *	 StringUtil.leftPad("bat", -1, 'z') = "bat"		<br>
	 * @param str 填补前的字符串 - 可以为null
	 * @param size 可能是填补后的字符串的长度
	 * @param padChar 要填补的字符
	 * @return String 左面被填补后的字符串或原始的字符串，如果参数size-str的长度=>1就不用填补，如果字符串为null就返回null。
	 */
	public static String leftPad(String str,int size,char padChar){
		return StringUtils.leftPad(str, size, padChar);
	}
	
	/**
	 * 用指定的字符串padStr填补在字符串的最左面，填补的长度等于参数size-str的长度，引用org.apache.commons.lang.StringUtils.leftPad(String str,int size,String padStr)方法。<br>
	 * 如果填补时剩下的长度 < 要填补的字符串padStr的长度，就从padStr最左面的字符开始直到填补后的长度 = 参数size为止。<br>
	 * 例：	<br>
	 *	 StringUtil.leftPad(null, *, *)      = null			<br>
	 *	 StringUtil.leftPad("", 3, "z")      = "zzz"		<br>
	 *	 StringUtil.leftPad("bat", 3, "yz")  = "bat"		<br>
	 *	 StringUtil.leftPad("bat", 5, "yz")  = "yzbat"		<br>
	 *	 StringUtil.leftPad("bat", 8, "yz")  = "yzyzybat"	<br>
	 *	 StringUtil.leftPad("bat", 1, "yz")  = "bat"		<br>
	 *	 StringUtil.leftPad("bat", -1, "yz") = "bat"		<br>
	 *	 StringUtil.leftPad("bat", 5, null)  = "  bat"		<br>
	 *	 StringUtil.leftPad("bat", 5, "")    = "  bat"		<br>
	 * @param str 填补前的字符串 - 可以为null
	 * @param size 可能是填补后的字符串的长度
	 * @param padStr 要填补的字符串，如果为null或""当作一个空格
	 * @return String 左面被填补后的字符串或原始的字符串，如果参数size-str的长度=>1就不用填补，如果字符串为null就返回null。
	 */
	public static String leftPad(String str,int size,String padStr){
		return StringUtils.leftPad(str, size, padStr);
	}
	
	
	
	
	
	/**
	 * 获得从字符串str最右面开始len长度字符串，引用org.apache.commons.lang.StringUtils.right(String str,int len)方法。<br>
	 * 例：<br>
	 *	 StringUtil.right(null, *)    = null	<br>
	 *	 StringUtil.right(*, -ve)     = ""		<br>
	 *	 StringUtil.right("", *)      = ""		<br>
	 *	 StringUtil.right("abc", 0)   = ""		<br>
	 *	 StringUtil.right("abc", 2)   = "bc"	<br>
	 *	 StringUtil.right("abc", 4)   = "abc"	<br>
	 * @param str 包含子串的字符串 - 可能为null
	 * @param len 长度 - 必须为0或正数
	 * @return String 返回最右面开始len长度的字符串，如果str为null将返回null。
	 */
	public static String right(String str,int len){
		return StringUtils.right(str, len);
	}
	
	/**
	 * 用空格填补在字符串的最右面，填补的长度等于参数size-str的长度，引用org.apache.commons.lang.StringUtils.rightPad(String str,int size)方法。<br>
	 * 例：	<br>
	 *   StringUtil.rightPad(null, *)   = null	<br>
	 *	 StringUtil.rightPad("", 3)     = "   "	<br>
	 *	 StringUtil.rightPad("bat", 3)  = "bat"	<br>
	 *	 StringUtil.rightPad("bat", 5)  = "bat  "<br>
	 *	 StringUtil.rightPad("bat", 1)  = "bat"	<br>
	 *	 StringUtil.rightPad("bat", -1) = "bat"	<br>
	 * @param str 填补前的字符串 - 可以为null
	 * @param size 可能是填补后的字符串的长度
	 * @return String 右面被填补后的字符串或原始的字符串，如果参数size-str的长度=>1就不用填补，如果字符串为null就返回null。
	 */
	public static String rightPad(String str,int size){
		return StringUtils.rightPad(str, size);
	}
	
	/**
	 * 用字符padChar填补在字符串的最右面，填补的长度等于参数size-str的长度，引用org.apache.commons.lang.StringUtils.rightPad(String str,int size,char padChar)方法。<br>
	 * 例：	<br>
	 *	 StringUtil.rightPad(null, *, *)     = null		<br>
	 *	 StringUtil.rightPad("", 3, 'z')     = "zzz"	<br>
	 *	 StringUtil.rightPad("bat", 3, 'z')  = "bat"	<br>
	 *	 StringUtil.rightPad("bat", 5, 'z')  = "batzz"	<br>
	 *	 StringUtil.rightPad("bat", 1, 'z')  = "bat"	<br>
	 *	 StringUtil.rightPad("bat", -1, 'z') = "bat"	<br>
	 * @param str 填补前的字符串 - 可以为null
	 * @param size 可能是填补后的字符串的长度
	 * @param padChar 要填补的字符
	 * @return String 右面被填补后的字符串或原始的字符串，如果参数size-str的长度=>1就不用填补，如果字符串为null就返回null。
	 */
	public static String rightPad(String str,int size,char padChar){
		return StringUtils.rightPad(str, size, padChar);
	}
	
	/**
	 * 用指定的字符串padStr填补在字符串的最右面，填补的长度等于参数size-str的长度，引用org.apache.commons.lang.StringUtils.rightPad(String str,int size,String padStr)方法。<br>
	 * 如果填补时剩下的长度 < 要填补的字符串padStr的长度，就从padStr最左面的字符开始直到填补后的长度 = 参数size为止 。<br>
	 * 例：	<br>
	 *	 StringUtil.rightPad(null, *, *)      = null		<br>
	 *	 StringUtil.rightPad("", 3, "z")      = "zzz"		<br>
	 *	 StringUtil.rightPad("bat", 3, "yz")  = "bat"		<br>
	 *	 StringUtil.rightPad("bat", 5, "yz")  = "batyz"		<br>
	 *	 StringUtil.rightPad("bat", 8, "yz")  = "batyzyzy"	<br>
	 *	 StringUtil.rightPad("bat", 1, "yz")  = "bat"		<br>
	 *	 StringUtil.rightPad("bat", -1, "yz") = "bat"		<br>
	 *	 StringUtil.rightPad("bat", 5, null)  = "bat  "		<br>
	 *	 StringUtil.rightPad("bat", 5, "")    = "bat  "		<br>
	 * @param str 填补前的字符串 - 可以为null
	 * @param size 可能是填补后的字符串的长度
	 * @param padStr 要填补的字符串，如果为null或""当作一个空格
	 * @return String 右面被填补后的字符串或原始的字符串，如果参数size-str的长度=>1就不用填补，如果字符串为null就返回null。
	 */
	public static String rightPad(String str,int size,String padStr){
		return StringUtils.rightPad(str, size, padStr);
	}
	
	
	
	
	
	
	/**
	 * 把对象数组中单独的字符串元素连接成一个字符串，引用org.apache.commons.lang.StringUtils.join(Object[] array)方法。<br>
	 * null对象或在数组之内的空字符串由空字符串("")代替。<br>
	 * 例：<br>
	 *	 StringUtil.join(null)            = null	<br>
	 *	 StringUtil.join([])              = ""		<br>
	 *	 StringUtil.join([null])          = ""		<br>
	 *	 StringUtil.join(["a", "b", "c"]) = "abc"	<br>
	 *	 StringUtil.join([null, "", "a"]) = "a"		<br>
	 * @param array 被连接的对象数组 - 可能为null
	 * @return String 连接后的字符串，如果数组为null将返回null。
	 */
	public static String join(Object[] array){
		return StringUtils.join(array);
	}
	
	/**
	 * 在原对象数组的基础上用指定的字符(separator)当作分隔符把数组中的元素连接成一个字符串(包括分隔符在内)，引用org.apache.commons.lang.StringUtils.join(Object[] array,char separator)方法。<br>
	 * 分隔符不能在数组的前后增加 ，null对象或在数组之内的空字符串("")由空字符串("")代替。<br>
	 * 例：<br>
	 *	 StringUtil.join(null, *)               = null		<br>
	 *	 StringUtil.join([], *)                 = ""		<br>
	 *	 StringUtil.join([null], *)             = ""		<br>
	 *	 StringUtil.join(["a", "b", "c"], ';')  = "a;b;c"	<br>
	 *	 StringUtil.join(["a", "b", "c"], null) = "abc"		<br>
	 *	 StringUtil.join([null, "", "a"], ';')  = ";;a"		<br>
	 * @param array 被连接的对象数组 - 可能为null
	 * @param separator 使用的分隔符
	 * @return String 连接后的字符串，如果数组为null将返回null。
	 */
	public static String join(Object[] array,char separator){
		return StringUtils.join(array, separator);
	}
	
	/**
	 * 在原对象数组的基础上用指定的字符串(separator)当作分隔符把数组中的元素连接成一个字符串(包括分隔符在内)，引用org.apache.commons.lang.StringUtils.join(Object[] array,String separator)方法。<br>
	 * 分隔符不能在数组的前后增加，null分隔符当作""，null对象或在数组之内的空字符串("")由空字符串("")代替。<br>
	 * 例：<br>
	 *	 StringUtil.join(null, *)                = null		<br>
	 *	 StringUtil.join([], *)                  = ""		<br>
	 *	 StringUtil.join([null], *)              = ""		<br>
	 *	 StringUtil.join(["a", "b", "c"], "--")  = "a--b--c"<br>
	 *	 StringUtil.join(["a", "b", "c"], null)  = "abc"	<br>
	 *	 StringUtil.join(["a", "b", "c"], "")    = "abc"	<br>
	 *	 StringUtil.join([null, "", "a"], ',')   = ",,a"	<br>
	 * @param array 被连接的对象数组 - 可能为null
	 * @param separator 使用的分隔符或是处理当作的""
	 * @return String 连接后的字符串，如果数组为null将返回null。
	 */
	public static String join(Object[] array,String separator){
		return StringUtils.join(array, separator);
	}
	
	/**
	 * 把数组中的元素连接成一个字符串返回,把分隔符separator也加上,连接的开始位置为startIndex,结束位置为endIndex，引用org.apache.commons.lang.StringUtils.join(Object[] array,String separator,int startIndex,int endIndex)方法。<br>
	 * @param array 被连接的对象数组 - 可能为null
	 * @param separator 使用的分隔符或是处理当作的""
	 * @param startIndex 连接的开始位置
	 * @param endIndex 连接的结束位置
	 * @return String 连接后的字符串，如果数组为null将返回null。
	 */
	public static String join(Object[] array,String separator,int startIndex,int endIndex){
		return StringUtils.join(array, separator, startIndex, endIndex);
	}
	
	/**
	 * 把数组中的元素连接成一个字符串返回,把分隔符separator也加上,连接的开始位置为startIndex,结束位置为endIndex，引用org.apache.commons.lang.StringUtils.join(Object[] array,char separator,int startIndex,int endIndex)方法。<br>
	 * @param array 被连接的对象数组 - 可能为null
	 * @param separator 使用的分隔符或是处理当作的""
	 * @param startIndex 连接的开始位置
	 * @param endIndex 连接的结束位置
	 * @return String 连接后的字符串，如果数组为null将返回null。
	 */
	public static String join(Object[] array,char separator,int startIndex,int endIndex){
		return StringUtils.join(array, separator, startIndex, endIndex);
	}
	
    
	
	/**
	 * 把Collection中的元素用指定的字符(separator)当作分隔符把数组中的元素连接成一个字符串(包括分隔符在内)，引用org.apache.commons.lang.StringUtils.join(Collection<Object> collection,char separator)方法。<br>
	 * 分隔符不能在Collection的前后增加 ，null对象或在数组之内的空字符串("")由空字符串("")代替。<br>
	 * @param collection 被连接的集合 - 可能为null
	 * @param separator 使用的分隔符
	 * @return String 连接后的字符串，如果数组为null将返回null。
	 */
	public static String join(Collection<Object> collection,char separator){
		return StringUtils.join(collection, separator);
	}
	
	/**
	 * 把Collection中的元素用指定的字符串(separator)当作分隔符把数组中的元素连接成一个字符串(包括分隔符在内)，引用org.apache.commons.lang.StringUtils.join(Collection<Object> collection,String separator)方法。<br>
	 * 分隔符不能在Collection的前后增加，null分隔符当作""代替。<br>
	 * @param collection 被连接的集合 - 可能为null
	 * @param separator 使用的分隔符或是处理当作的""
	 * @return String 连接后的字符串，如果数组为null将返回null。
	 */
	public static String join(Collection<Object> collection,String separator){
		return StringUtils.join(collection, separator);
	}
	
	/**
	 * 把Iterator中的元素用指定的字符(separator)当作分隔符把数组中的元素连接成一个字符串(包括分隔符在内)，引用org.apache.commons.lang.StringUtils.join(Iterator<Object> iterator,char separator)方法。<br>
	 * 分隔符不能在Iterator的前后增加，null对象或在数组之内的空字符串("")由空字符串("")代替。<br>
	 * @param iterator 被连接的迭代器 - 可能为null
	 * @param separator 使用的分隔符
	 * @return String 连接后的字符串，如果数组为null将返回null。
	 */
	public static String join(Iterator<Object> iterator,char separator){
		return StringUtils.join(iterator, separator);
	}
	
	/**
	 * 把Iterator中的元素用指定的字符串(separator)当作分隔符把数组中的元素连接成一个字符串(包括分隔符在内)，引用org.apache.commons.lang.StringUtils.join(Iterator<Object> iterator,String separator)方法。<br>
	 * 分隔符不能在Iterator的前后增加，null分隔符当作""代替。<br>
	 * @param iterator 被连接的迭代器 - 可能为null
	 * @param separator 使用的分隔符
	 * @return String 连接后的字符串，如果数组为null将返回null。
	 */
	public static String join(Iterator<Object> iterator,String separator){
		return StringUtils.join(iterator, separator);
	}
	
	
	
	/**
	 * 对字符串作MD5加密处理
	 * @param inStr 需要被处理的字符串
	 * @param charset gbk gb2312 utf8 默认gbk
	 * @return 被处理后的字符串，被转换为16进制表示的字符串
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException 
	 */
	public static String md5(String inStr, String charset) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if(StringUtil.isEmpty( charset )) {
			charset = "gbk";
		}
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(inStr.getBytes(charset));
		byte[] r = md.digest();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < r.length; i++) {
			byte b = r[i];
			sb.append(Character.forDigit((b >> 4 & 0x0F), 16));
			sb.append(Character.forDigit((b & 0x0F), 16));
		}
		return sb.toString();
	}
	
	public static String md5(String inStr)
	{
		try {
			return md5(inStr,"GBK");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return inStr;
		
	}
	/**
	 *  先rc4，后base64加密字符串，采用gbk编码
	 * @param value 需要加密的字符串
	 * @param key 加密私钥
	 * @return	string 加密后返回的字符串
	 * @throws UnsupportedEncodingException
	 */
	public static String rc4(String value, String key) throws UnsupportedEncodingException {
		RC4Engine engine = new RC4Engine();
		CipherParameters parameters = new KeyParameter(key.getBytes("gbk"));
		engine.init(false, parameters);
		int len = value.getBytes("gbk").length;
		byte[]  bytes = new byte[len];
		for(int i=0;i< len;i++) {
			bytes[i] = engine.returnByte(value.getBytes("gbk")[i]);
		}
		
		String base64Encrypt = new String(Base64.encodeBase64(bytes),"gbk");

		return base64Encrypt;
	}
	
	/**
	 * 取得给定长度的随机数
	 * @param leng 长度
	 * @return String 一定长度的随机数
	 */
	public static String getRandomNumber(int leng) {
		String s = "0,1,2,3,4,5,6,7,8,9,";
		s = s + "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,";
		s = s + "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z";
		//s = s + "~,!,@,#,$,%,^,&,*";
		
		String[] sz = s.split(",");
		List<Object> al = new ArrayList<Object>();
		for (int i = 0; i < leng; i++) {
			if (al.size()== 0) {
				Random rd = new Random();
				int a = rd.nextInt(sz.length);
				al.add(String.valueOf(a));
			} else {
				int c = 0;
				boolean isonly = true;

				while (isonly) {
					Random rd = new Random();
					int b = rd.nextInt(sz.length);
					if (isone(al, b)) {
						isonly = false;
						c = b;

					}
				}
				al.add(String.valueOf(c));
			}
		}
		String rand = "";
		for (int j = 0; j < al.size(); j++) {
			rand += sz[Integer.parseInt((String)al.get(j))];
		}
		return rand;

	}   
	
	@SuppressWarnings("unused")
	private static boolean isone(List<Object> a, int b) {
		boolean one = true;
		for (int i = 0; i < a.size(); i++) {
			if (a.get(i).toString() == String.valueOf(b)) {
				one = false;
				i = a.size();
			}
		}
		return one;
	}   
	public static String createPostscript( int num ) throws Exception {

        StringBuffer buffer = new StringBuffer();
        for ( int i = 0; i < num; i++ ) {

            int hightPos, lowPos; // 定义高低位
            Random random = new Random();

            hightPos = ( 176 + Math.abs( random.nextInt( 39 ) ) );// 获取高位值
            lowPos = ( 161 + Math.abs( random.nextInt( 93 ) ) );// 获取低位值

            byte[] b = new byte[2];
            b[0] = ( new Integer( hightPos ).byteValue() );
            b[1] = ( new Integer( lowPos ).byteValue() );
            buffer.append( new String( b, "GBK" ) );// 转成中文
        }

        return buffer.toString();

    }

//	/**
//	 * 取得字符串的实际宽度（根据指定名称、样式和磅值大小取字符串的宽度）。
//	 * @param str 字符串
//	 * @return int	字符串实际宽度
//	 */
//	public static int getStringLength(String str) {
//		Frame frame = new Frame();
//		Font font = new Font("Dialog", Font.PLAIN, 12);
//		FontMetrics fontMetrics = frame.getFontMetrics(font);
//		int iWidth = SwingUtilities.computeStringWidth(fontMetrics, str);
//		return iWidth;
//	}
//
//	/**
//	 * 按字体取得字符串的实际宽度。
//	 * @param str 字符串
//	 * @param font 字体
//	 * @return int 返回字符串实际宽度
//	 */
//	public static int getStringLength(String str, Font font) {
//		Frame frame = new Frame();
//		FontMetrics fontMetrics = frame.getFontMetrics(font);
//		int iWidth = SwingUtilities.computeStringWidth(fontMetrics, str);
//		return iWidth;
//	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println(rc4("food4872","CHONGTIXITONG"));
	}

}
