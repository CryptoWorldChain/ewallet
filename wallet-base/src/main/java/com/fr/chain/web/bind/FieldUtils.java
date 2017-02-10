package com.fr.chain.web.bind;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

public class FieldUtils {

	public static List<Field> allDeclaredField(Class<?> clazz) {
		List<Field> fieldList = new ArrayList<>();
		Class<?> targetClass = clazz;
		do {
			Field[] fields = targetClass.getDeclaredFields();
			for (Field field : fields) {
				fieldList.add(field);
			}
			targetClass = targetClass.getSuperclass();
		} while (targetClass != null && targetClass != Object.class);
		return fieldList;
	}

	public static Object getObjectValue(Object pojo,String fieldName) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		String methodName = "get" + StringUtils.capitalize(fieldName);
		Method method = ReflectionUtils.findMethod(pojo.getClass(), methodName);
		Object obj = method.invoke(pojo);
		return obj;
	}
	
	public static void setObjectValue(Object pojo,Field field,Object value) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		String methodName = "set" + StringUtils.capitalize(field.getName());
		Method method = ReflectionUtils.findMethod(pojo.getClass(), methodName,field.getType());
		method.invoke(pojo,value);
	}
	
	public static String field2SqlColomn(String field) {
		StringBuffer buff = new StringBuffer();
		for (int i = 0; i < field.length(); i++) {
			char ch = field.charAt(i);
			if (ch >= 'A' && ch <= 'Z') {
				buff.append("_").append(ch);
			} else {
				buff.append(ch);
			}
		}
		return buff.toString().toUpperCase();
	}
}
