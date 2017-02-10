
package com.fr.chain.utils;

import org.apache.commons.lang3.CharUtils;

/**
 * 字符处理工具类
 * @author fcpays
 * 这里面的有些方法引用apache.commons.lang-2.3里的StringUtils类
 */
public class CharUtil {
	
	/**
	 * 构造函数
	 */
	public CharUtil(){
		
	}
	
	
	/**
	 * 检查字符ch是否为ASCII码，引用org.apache.commons.lang.CharUtils.isAscii(char ch)方法。<br>
	 * 例：<br>
	 *	   CharUtil.isAscii('a')  = true	<br>
	 *	   CharUtil.isAscii('A')  = true	<br>
	 *	   CharUtil.isAscii('3')  = true	<br>
	 *	   CharUtil.isAscii('-')  = true	<br>
	 *	   CharUtil.isAscii('\n') = true	<br>
	 *	   CharUtil.isAscii('©') = false 	<br>
	 * @param ch 检查的字符
	 * @return boolean 如果小于ASCII码128返回true，否则返回false。
	 */
	public static boolean isAscii(char ch){
		return CharUtils.isAscii(ch);
	}
	
	
	/**
	 * 检查字符ch是否为可以打印的ASCII码，引用org.apache.commons.lang.CharUtils.isAsciiPrintable(char ch)方法。<br>
	 * 例：<br>
	 *	   CharUtil.isAsciiPrintable('a')  = true 	<br>
	 *	   CharUtil.isAsciiPrintable('A')  = true 	<br>
	 *	   CharUtil.isAsciiPrintable('3')  = true 	<br>
	 *	   CharUtil.isAsciiPrintable('-')  = true 	<br>
	 *	   CharUtil.isAsciiPrintable('\n') = false 	<br>
	 *	   CharUtil.isAsciiPrintable('©') = false 	<br>
	 * @param ch 检查的字符
	 * @return boolean 如果包含在ASCII码32-126之间返回true，否则返回false。
	 */
	public static boolean isAsciiPrintable(char ch){
		return CharUtils.isAsciiPrintable(ch);
	}
	
	/**
	 * 检查字符ch是否为的ASCII码的字母，引用org.apache.commons.lang.CharUtils.isAsciiAlpha(char ch)方法。<br>
	 * 例：<br>
	 *	   CharUtil.isAsciiAlpha('a')  = true 	<br>
	 *	   CharUtil.isAsciiAlpha('A')  = true 	<br>
	 *	   CharUtil.isAsciiAlpha('3')  = false 	<br>
	 *	   CharUtil.isAsciiAlpha('-')  = false 	<br>
	 *	   CharUtil.isAsciiAlpha('\n') = false 	<br>
	 *	   CharUtil.isAsciiAlpha('©') = false 	<br>
	 * @param ch 检查的字符
	 * @return boolean 如果包含在ASCII码65-90或者97-122之间返回true，否则返回false。
	 */
	public static boolean isAsciiAlpha(char ch){
		return CharUtils.isAsciiAlpha(ch);
	}
	
	/**
	 * 检查字符ch是否为的ASCII码的小写字母，引用org.apache.commons.lang.CharUtils.isAsciiAlphaLower(char ch)方法。<br>
	 * 例：<br>
	 *	   CharUtil.isAsciiAlphaLower('a')  = true 		<br>
	 *	   CharUtil.isAsciiAlphaLower('A')  = false 	<br>
	 *	   CharUtil.isAsciiAlphaLower('3')  = false 	<br>
	 *	   CharUtil.isAsciiAlphaLower('-')  = false 	<br>
	 *	   CharUtil.isAsciiAlphaLower('\n') = false 	<br>
	 *	   CharUtil.isAsciiAlphaLower('©') = false 		<br>
	 * @param ch 检查的字符
	 * @return boolean 如果包含在ASCII码97-122之间返回true，否则返回false。
	 */
	public static boolean isAsciiAlphaLower(char ch){
		return CharUtils.isAsciiAlphaLower(ch);
	}
	
	/**
	 * 检查字符ch是否为的ASCII码的大写字母，引用org.apache.commons.lang.CharUtils.isAsciiAlphaUpper(char ch)方法。<br>
	 * 例：<br>
	 *	   CharUtil.isAsciiAlphaUpper('a')  = false 	<br>
	 *	   CharUtil.isAsciiAlphaUpper('A')  = true 		<br>
	 *	   CharUtil.isAsciiAlphaUpper('3')  = false 	<br>
	 *	   CharUtil.isAsciiAlphaUpper('-')  = false 	<br>
	 *	   CharUtil.isAsciiAlphaUpper('\n') = false 	<br>
	 *	   CharUtil.isAsciiAlphaUpper('©') = false 		<br>
	 * @param ch 检查的字符
	 * @return boolean 如果包含在ASCII码65-90之间返回true，否则返回false。
	 */
	public static boolean isAsciiAlphaUpper(char ch){
		return CharUtils.isAsciiAlphaUpper(ch);
	}
		
	/**
	 * 检查字符ch是否为的ASCII码的数字和字符，引用org.apache.commons.lang.CharUtils.isAsciiAlphanumeric(char ch)方法。<br>
	 * 例：<br>
	 *	   CharUtil.isAsciiAlphanumeric('a')  = true 	<br>
	 *	   CharUtil.isAsciiAlphanumeric('A')  = true 	<br>
	 *	   CharUtil.isAsciiAlphanumeric('3')  = true 	<br>
	 *	   CharUtil.isAsciiAlphanumeric('-')  = false 	<br>
	 *	   CharUtil.isAsciiAlphanumeric('\n') = false 	<br>
	 *	   CharUtil.isAsciiAlphanumeric('©') = false 	<br>
	 * @param ch 检查的字符
	 * @return boolean 如果包含在ASCII码48-57或65-90或97-122之间返回true，否则返回false。
	 */
	public static boolean isAsciiAlphanumeric(char ch){
		return CharUtils.isAsciiAlphanumeric(ch);
	}
	
	/**
	 * 检查字符ch是否在ASCII码的范围之内，引用org.apache.commons.lang.CharUtils.isAsciiControl(char ch)方法。<br>
	 * 例：<br>
	 *	   CharUtil.isAsciiControl('a')  = false 	<br>
	 *	   CharUtil.isAsciiControl('A')  = false 	<br>
	 *	   CharUtil.isAsciiControl('3')  = false 	<br>
	 *	   CharUtil.isAsciiControl('-')  = false 	<br>
	 *	   CharUtil.isAsciiControl('\n') = true 	<br>
	 *	   CharUtil.isAsciiControl('©') = false 	<br>
	 * @param ch 检查的字符
	 * @return boolean 如果包含在ASCII码小与32或者等于127返回true，否则返回false。
	 */
	public static boolean isAsciiControl(char ch){
		return CharUtils.isAsciiControl(ch);
	}
	
	/**
	 * 检查字符ch是否为的ASCII码的数字，引用org.apache.commons.lang.CharUtils.isAsciiNumeric(char ch)方法。<br>
	 * 例：<br>
	 *	   CharUtil.isAsciiNumeric('a')  = false 	<br>
	 *	   CharUtil.isAsciiNumeric('A')  = false 	<br>
	 *	   CharUtil.isAsciiNumeric('3')  = true 	<br>
	 *	   CharUtil.isAsciiNumeric('-')  = false 	<br>
	 *	   CharUtil.isAsciiNumeric('\n') = false 	<br>
	 *	   CharUtil.isAsciiNumeric('©') = false 	<br>
	 * @param ch 检查的字符
	 * @return boolean 如果包含在ASCII码48-57之间返回true，否则返回false。
	 */
	public static boolean isAsciiNumeric(char ch){
		return CharUtils.isAsciiNumeric(ch);
	}
	
	
	
	
	/**
	 * 返回此Character对象的char值，如果Character对象为null抛出异常，引用org.apache.commons.lang.CharUtils.toChar(Character ch)方法。<br>
	 * 例：<br>
	 *	   CharUtil.toChar(null) = IllegalArgumentException	<br>
	 *	   CharUtil.toChar(' ')  = ' '	<br>
	 *	   CharUtil.toChar('A')  = 'A'	<br>
	 * @param ch 转换值的对象
	 * @return char 返回Character的char值
	 * @throws 如果Character对象为null，抛出IllegalArgumentException异常。
	 */
	public static char toChar(Character ch){
		return CharUtils.toChar(ch);
	}
	
	/**
	 * 返回此Character对象的char值，如果Character为null时用默认的字符替换，引用org.apache.commons.lang.CharUtils.toChar(Character ch, char defaultValue)方法。<br>
	 * 例：<br>
	 *	   CharUtil.toChar(null, 'X') = 'X'	<br>
	 *	   CharUtil.toChar(' ', 'X')  = ' '	<br>
	 *	   CharUtil.toChar('A', 'X')  = 'A'	<br>
	 * @param ch 转换值的对象
	 * @param defaultValue 默认的替换值
	 * @return char 返回Character对象的char值，如果对象为null就返回默认的值。
	 */
	public static char toChar(Character ch, char defaultValue){
		return CharUtils.toChar(ch,defaultValue);
	}
	
	/**
	 * 返回此String对象第一个字符的char值，如果String对象为""抛出异常，引用org.apache.commons.lang.CharUtils.toChar(String str)方法。<br>
	 * 例：<br>
	 *	   CharUtil.toChar(null) = IllegalArgumentException	<br>
	 *	   CharUtil.toChar("")   = IllegalArgumentException	<br>
	 *	   CharUtil.toChar("A")  = 'A'	<br>
	 *	   CharUtil.toChar("BA") = 'B'	<br>
	 * @param str 转换值的对象
	 * @return char 返回String对象第一个字符的char值
	 * @throws 如果String对象为""，抛出IllegalArgumentException异常。
	 */
	public static char toChar(String str) {
		return CharUtils.toChar(str);
	}
	
	/**
	 * 返回此String对象第一个字符的char值，如果String对象为""时用默认的字符替换，引用org.apache.commons.lang.CharUtils.toChar(String str, char defaultValue)方法。<br>
	 * 例：<br>
	 *	   CharUtil.toChar(null, 'X') = 'X'	<br>
	 *	   CharUtil.toChar("", 'X')   = 'X'	<br>
	 *	   CharUtil.toChar("A", 'X')  = 'A'	<br>
	 *	   CharUtil.toChar("BA", 'X') = 'B'	<br>
	 * @param str 转换值的对象
	 * @param defaultValue 默认的替换值
	 * @return char 返回Character对象的char值，如果对象为null就返回默认的值。
	 */
	public static char toChar(String str, char defaultValue) {
		return CharUtils.toChar(str,defaultValue);
	}
	
	/**
	 * 把char转换成Character对象，引用org.apache.commons.lang.CharUtils.toCharacterObject(char ch)方法。<br>
	 * 例：<br>
	 *	   CharUtil.toCharacterObject(' ')  = ' '<br>
	 *	   CharUtil.toCharacterObject('A')  = 'A'<br>
	 * @param ch 转换的char字符
	 * @return Character 返回char转换成Character后的对象
	 */
	public static Character toCharacterObject(char ch) {
		return CharUtils.toCharacterObject(ch);
	}
	
	/**
	 * 返回String对象第一个字符的Character对象，如果String对象为""将返回null，引用org.apache.commons.lang.CharUtils.toCharacterObject(String str)方法。<br>
	 * 例：<br>
	 *	   CharUtil.toCharacterObject(null) = null	<br>
	 *	   CharUtil.toCharacterObject("")   = null	<br>
	 *	   CharUtil.toCharacterObject("A")  = 'A'	<br>
	 *	   CharUtil.toCharacterObject("BA") = 'B'	<br>
	 * @param str 转换的String对象
	 * @return Character 返回String对象第一个字符的Character对象
	 */
	public static Character toCharacterObject(String str)  {
		return CharUtils.toCharacterObject(str);
	}
	

	
	/**
	 * 把char字符转换为int值，如果char不是数字将抛出异常，引用org.apache.commons.lang.CharUtils.toIntValue(char ch)方法。<br>
	 * 例：<br>
	 *		CharUtil.toIntValue('3')  = 3 <br>
   	 *		CharUtil.toIntValue('A')  = IllegalArgumentException <br>
	 * @param ch 转换的字符
	 * @return int 返回char转换后的int值。
	 * @throws 如果char不是ASCII码的数字，就抛出IllegalArgumentException异常。
	 */
	public static int toIntValue(char ch) {
		return CharUtils.toIntValue(ch);
	}
	
	/**
	 * 把Character字符转换为int值，如果Character不是数字将抛出异常，引用org.apache.commons.lang.CharUtils.toIntValue(Character ch)方法。<br>
	 * 例：<br>
	 *		CharUtil.toIntValue('3')  = 3 <br>
   	 *		CharUtil.toIntValue('A')  = IllegalArgumentException  <br>
   	 *		CharUtil.toIntValue(null)  = IllegalArgumentException <br>
	 * @param ch 转换的字符 - 不能为null
	 * @return int 返回Character转换后的int值。
	 * @throws 如果char不是ASCII码的数字或者为null，就抛出IllegalArgumentException异常。
	 */
	public static int toIntValue(Character ch)  {
		return CharUtils.toIntValue(ch);
	}
	
	/**
	 * 把Character对象转换为int值，如果Character不是ASCII码的数字时用默认的int值符替换，如果Character不是数字将抛出异常，引用org.apache.commons.lang.CharUtils.toIntValue(Character ch, int defaultValue)方法。<br>
	 * 例：<br>
	 *	   CharUtil.toIntValue(null, -1) = -1	<br>
	 *	   CharUtil.toIntValue('3', -1)  = 3	<br>
	 *	   CharUtil.toIntValue('A', -1)  = -1	<br>
	 * @param ch 转换int值的对象
	 * @param defaultValue 默认的int值
	 * @return int 返回Character对象转换后的int值。
	 */
	public static int toIntValue(Character ch, int defaultValue)  {
		return CharUtils.toIntValue(ch,defaultValue);
	}
	
	/**
	 * 把char字符转换为int值，如果char不是ASCII码的数字时用默认的int值符替换，如果char不是数字将抛出异常，引用org.apache.commons.lang.CharUtils.toIntValue(char ch, int defaultValue)方法。<br>
	 * 例：<br>
	 *		CharUtil.toIntValue('3', -1)  = 3  <br>
   	 *		CharUtil.toIntValue('A', -1)  = -1 <br>
	 * @param ch 转换int值的字符
	 * @param defaultValue 默认的int值
	 * @return int 返回char转换后的int值。
	 */
	public static int toIntValue(char ch, int defaultValue)  {
		return CharUtils.toIntValue(ch,defaultValue);
	}
	
	/**
	 * 把char转换成包含这个字符的Stirng对象，引用org.apache.commons.lang.CharUtils.toString(char ch)方法。<br>
	 * 为ASCII的7位的字符，每次将返回相同的字符串对象<br>
	 * 例：<br>
	 *	   CharUtil.toString(' ')  = " "	<br>
	 *	   CharUtil.toString('A')  = "A"	<br>
	 * @param ch 转换的字符
	 * @return String 返回包含这个字符的字符串对象
	 */
	public static String toString(char ch)  {
		return CharUtils.toString(ch);
	}
	
	/**
	 * 把Character转换成包含这个Character的Stirng对象，引用org.apache.commons.lang.CharUtils.toString(Character ch)方法。<br>
	 * 为ASCII的7位的字符，每次将返回相同的字符串对象<br>
	 * 如果Character为null将返回null <br>
	 * 例：<br>
	 * 	   CharUtil.toString(null) = null	<br>
	 *	   CharUtil.toString(' ')  = " "	<br>
	 *	   CharUtil.toString('A')  = "A"	<br>
	 * @param ch 转换的Character对象
	 * @return String 返回包含这个Character的字符串对象
	 */
	public static String toString(Character ch) {
		return CharUtils.toString(ch);
	}
	
	
	
	/**
	 * 把char格式转换成unicode的字符串格式，引用org.apache.commons.lang.CharUtils.unicodeEscaped(char ch)方法。<br>
	 * 对Java源代码格式化。<br>
	 * 例：<br>
	 *	   CharUtil.unicodeEscaped(' ') = " "	<br>
	 *	   CharUtil.unicodeEscaped('A') = "A"	<br>
	 * @param ch 转换的字符
	 * @return String 转换后的unicode字符串
	 */
	public static String unicodeEscaped(char ch) {
		return CharUtils.unicodeEscaped(ch);
	}
	
	/**
	 * 把Character格式转换成unicode的字符串格式，如果Character为null将返回null，引用org.apache.commons.lang.CharUtils.unicodeEscaped(Character ch)方法。<br>
	 * 对Java源代码格式化。<br>
	 * 例：<br>
	 * 	   CharUtil.unicodeEscaped(null) = null <br>
	 *	   CharUtil.unicodeEscaped(' ') = " "	<br>
	 *	   CharUtil.unicodeEscaped('A') = "A"	<br>
	 * @param ch 转换的Character对象 - 可能为null
	 * @return String 转换后的unicode字符串,如果Character为null将返回null。
	 */
	public static String unicodeEscaped(Character ch)  {
		return CharUtils.unicodeEscaped(ch);
	}
	
	
    
    /**
     * 检查输入的字符是否为半角（半角返回true; 全角返回false）
     * @param ch 被检查的字符
     * @return boolean 半角返回true; 全角返回false
     */
    public static boolean isHalfWidth(char ch) {
        boolean result = true;
        Character.UnicodeBlock cub = Character.UnicodeBlock.of(ch);
        if (cub.equals(Character.UnicodeBlock.BASIC_LATIN)) {
            result = true;
        } else if (cub.equals(Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS)) {
            int type = Character.getType(ch);
            if ((type == Character.MODIFIER_LETTER) || (type == Character.OTHER_LETTER)) {
                result = true;
            } else {
                result = false;
            }
        } else {
            result = false;
        }
        return result;
    }
}
