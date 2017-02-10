package com.fr.chain.utils;

import org.apache.commons.lang3.ObjectUtils;

/**
 * 对象处理工具类
 * @author fcpays
 * 这里面的方法引用apache.commons.lang-2.3里的ObjectUtils类
 */
public class ObjectUtil {
	
	/**
	 * 构造函数
	 *
	 */
	public ObjectUtil(){
		
	}
	
	
	/**
	 * 如果对象的值为null返回默认值，否则返回对象的值，引用apache.commons.lang包里ObjectUtils.defaultIfNull(Object object, Object defaultValue)方法。<br>
	 * @param object 检验的对象，可以为null。
	 * @param defaultValue 返回的默认值，可能为null。
	 * @return 对象的值或默认值
	 */
	public static Object defaultIfNull(Object object, Object defaultValue) {
		return ObjectUtils.defaultIfNull(object,defaultValue);
	}
	
	/**
	 * 比较两个对象是否相等，两个对象都可能为空，引用apache.commons.lang包里ObjectUtils.equals(Object object1, Object object2)方法。<br>
	 * @param object1 对象1，可以为null。
	 * @param object2 对象2，可以为null。
	 * @return 两个对象相同返回true,否则返回false。
	 */
	public static boolean equals(Object object1, Object object2) {
		return ObjectUtils.equals(object1,object2);
	}
	
	/**
	 * 获得对象的编码，如果对象不为null返回对象编码，否则返回0，引用apache.commons.lang包里ObjectUtils.hashCode(Object obj)方法。<br>
	 * @param obj 需要获得编码的对象，可以为null。
	 * @return 如果对象不为null返回对象编码，否则返回0。
	 */
	public static int hashCode(Object obj)  {
		return ObjectUtils.hashCode(obj);
	}
	
	/**
	 * 把对象的转换成字符串或对象为null时转换成""，引用apache.commons.lang包里ObjectUtils.toString(Object obj)方法。<br>
	 * 例： <br>
	 * 		ObjectUtils.toString(null)  = ""	<br>
	 *		ObjectUtils.toString("")    = ""	<br>
	 *		ObjectUtils.toString("bat") = "bat"	<br>
	 * @param obj 转换的对象，可以为null。
	 * @return 如果对象为null返回""，否则返回obj.toString()。
	 */
	public static String toString(Object obj)  {
		return ObjectUtils.toString(obj);
	}
	
	/**
	 * 对象按给定的参数nullStr替换，引用apache.commons.lang包里ObjectUtils.toString(Object obj, String nullStr)方法。<br>
	 * 例：	<br>
	 * 		ObjectUtils.toString(null, null)     = null		<br>
	 *		ObjectUtils.toString(null, "null")   = "null"	<br>
	 *		ObjectUtils.toString("", "null")     = ""		<br>
	 *		ObjectUtils.toString("bat", "null")  = "bat"	<br>
	 * @param obj 转换的对象，可以为null。
	 * @param nullStr 返回的字符串，可以为null。
	 * @return 如果参数obj为null返回null,否则返回obj.toString()。
	 */
	public static String toString(Object obj, String nullStr)  {
		return ObjectUtils.toString(obj,nullStr);
	}
}
