
package com.fr.chain.utils;

import java.io.IOException;
import java.io.Writer;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 * 
 * @author fcpays
 * 这里面的有些方法引用apache.commons.lang-2.3包里的StringEscapeUtils类
 */
public class StringEscapeUtil {

	/**
	 * 构造函数
	 */
	public StringEscapeUtil(){
		
	}
	
	
	/**
	 * 用HTML4.0的格式转换String里的字符，引用apache.commons.lang包里的StringEscapeUtils.escapeHtml(String str)方法。<br>
	 * 例：<br>
	 * "bread" & "butter" 变成 &quot;bread&quot; &amp; &quot;butter&quot;	<br>
	 * @param str 转换的字符串 - 可以为null
	 * @return String 返回转换后新的字符串，如果字符串为null就返回null
	 */
	public static String escapeHtml(String str) {
		return StringEscapeUtils.escapeHtml4(str);
	}

	/**
	 * 用java字符串标准格式转换String里的字符，引用apache.commons.lang包里的StringEscapeUtils.escapeJava(String str)方法。<br>
	 * 处理正确的引用和控制字符 (tab, backslash, cr, ff, etc.)<br>
	 * 所以TAB键变成字符'\\' and 't'. 	<br>
	 * 例：<br>
	 *  input string: He didn't say, "Stop!"		<br>
 	 *	output string: He didn't say, \"Stop!\" 	<br>
	 * @param str 转换的字符串 - 可以为null
	 * @return String 返回转换后新的字符串，如果字符串为null就返回null
	 */
	public static String escapeJava(String str) {
		return StringEscapeUtils.escapeJava(str);
	}

	/**
	 * -------------------------------------------------
	 * 把字符串转换成合适的SQL查询语言,适用于  like  
	 * @param str 转换的字符串 - 可以为null
	 * @return String 返回转换后新的SQL文字符串，如果字符串为null就返回null
	 * 将用户传入的检索条件中的特殊字符进行转义
	 * <br/>
	 * 将 ' 转换 \'
	 * <br/>
	 * 将 " 转换 \"
	 * <br/>
	 * 将 % 转换 \%
	 * <br/>
	 * 将 _ 转换 \_
	 * <br/>
	 * 用法 " like '"+name+"'" 改写为   " like '"+StringEscapeUtil.escapeSql(name)+"'"
	 */
	public static String escapeSql(String str) {
//		return StringEscapeUtils.escapeSql(str);
		if(str==null||str.equals("")){
			return str;
		}else{
			StringBuffer buf = new StringBuffer();
			for(int i=0;i<str.length();i++){
				char c = str.charAt(i);
					switch(c){   
					//将 ' 转换为 ''
					case '\'':
						buf.append("''");
						break;
					//将 " 转换为 \"
					case '\"':
					    buf.append("\"");
					    break;
					  //将 % 转换为 \%    
					case '%':
						buf.append("\\%");
						break;
					//将 _ 转换为 \_	
					case '_':
						buf.append("\\_");
						break;
					default:
						buf.append(c);
						break;
				}
			}
			return buf.toString();
		}
	} 
	/**
	 * -------------------------------------------------
	 * 把字符串转换成合适的SQL查询语言,适用于  = 
	 * @param str 转换的字符串 - 可以为null
	 * @return String 返回转换后新的SQL文字符串，如果字符串为null就返回null
	 * 将用户传入的检索条件中的特殊字符进行转义
	 * <br/>
	 * 将 ' 转换 \'
	 * <br/>
	 * 将 " 转换 \"
	 * <br/>
	 * 用法 " like '"+name+"'" 改写为   " like '"+StringEscapeUtil.escapeSql(name)+"'"
	 */
	public static String escapeSql_eq(String str){
		if(str==null||str.equals("")){
			return str;
		}else{
			StringBuffer buf = new StringBuffer();
			for(int i=0;i<str.length();i++){
				char c = str.charAt(i);
					switch(c){   
					//将 ' 转换为 ''
					case '\'':
						buf.append("''");
						break;
					//将 " 转换为 \"
					case '\"':
					    buf.append("\"");
					    break; 					
					default:
						buf.append(c);
						break;
				}
			}
			return buf.toString();
		}
	}
	/**
	 * 用Xml标准格式转换String里的字符，引用apache.commons.lang包里的StringEscapeUtils.escapeXml(String str)方法。<br>
	 * Xml唯一支持的5个基础格式(gt, lt, quot, amp, apos)，不支持DTDs或外部格式 <br>
	 * 例：<br>
	 *  "bread" & "butter" => &quot;bread&quot; &amp; &quot;butter&quot;	<br>
	 * @param str 转换的字符串 - 可以为null
	 * @return String 返回转换后新的字符串，如果字符串为null就返回null
	 */
	public static String escapeXml(String str) {
		return StringEscapeUtils.escapeXml(str);
	}
	
	/**
	 * 和上面的escapeJava(String str)用法相反	<br>
	 * 转换字符串所有中的java标记，引用apache.commons.lang包里的StringEscapeUtils.unescapeJava(String str)方法。<br>
	 * @param str 转换的字符串 - 可以为null
	 * @return String 返回转换后新的字符串，如果字符串为null就返回null
	 */
	public static String unescapeJava(String str) {
		return StringEscapeUtils.unescapeJava(str);
	} 
	
	/**
	 * 和上面的unescapeXml(String str)用法相反	<br>
	 * 把字符串内容中实际的Unicode转换成相应的XMl标准格式，引用apache.commons.lang包里的StringEscapeUtils.unescapeXml(String str)方法。<br>
	 * Xml唯一支持的5个基础格式(gt, lt, quot, amp, apos)，不支持DTDs或外部格式 <br>
	 * @param str 转换的字符串 - 可以为null
	 * @return String 返回转换后新的字符串，如果字符串为null就返回null
	 */
	public static String unescapeXml(String str) {
		return StringEscapeUtils.unescapeXml(str);
	} 
	
}
