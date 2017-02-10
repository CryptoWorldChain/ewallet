package com.fr.chain.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 序列化接口 扩展的序列化，需要实现此接口
 * 
 */
public class SerializerUtil {

	/*** 默认序列化类型 */
	private static final ISerializer json = JsonSerializer.getInstance(); 

	public static <T> Object serialize(T data) {
		return json.serialize(data);
	}

	public static <T> T deserialize(Object dataArray, Class<T> clazz) {
		if(dataArray == null){
			try {
				return clazz.newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return json.deserialize(dataArray, clazz);
	}

	public static <T> Object serializeArray(List<T> list) {
		if(list == null || list.size() == 0){
			return new ArrayList<>();
		}
		return json.serializeArray(list);
	}

	public static <T> List<T> deserializeArray(Object bytes, Class<T> clazz) {
		if(bytes == null){
			return new ArrayList<>();
		}
		return json.deserializeArray(bytes, clazz);
	}

}
