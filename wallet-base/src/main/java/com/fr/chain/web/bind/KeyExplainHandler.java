package com.fr.chain.web.bind;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;

public class KeyExplainHandler {

	private static ObjectMapper mapper = new ObjectMapper();
	public final static String ID_KEY = "_id";
	
	// field-value_field-value_
	@SuppressWarnings("rawtypes")
	public static String genKey(HashMap map, Class<?> clazz) {
		StringBuffer key = new StringBuffer();
		for (Field field : FieldUtils.allDeclaredField(clazz)) {
			key.append(field.getName()).append("-")
					.append(map.get(field.getName())).append("_");
		}
		return key.toString();
	}
	
	public static <T> String genPojoKey(T pojo,Class<?> keyClazz) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		StringBuffer key = new StringBuffer();
		for(Field field : FieldUtils.allDeclaredField(keyClazz)){
			key.append(field.getName()).append("-")
			.append(ObjectUtils.toString(FieldUtils.getObjectValue(pojo, field.getName()))).append("_");
		}
		return key.toString();
	}
	
	
	/**
	 * 
	 * @param key
	 * @param pojo     example::ParaDbproperties
	 * @param keyClass example::ParaDbpropertiesKey
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T explainKey(String key, T pojo) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Map<String, Object> fvMap = new HashMap<>();
		String[] strs = StringUtils.split(key, "_");
		for (String s : strs) {
			String[] fvs = StringUtils.split(s, "-");
			if(fvs.length > 1 && !"null".equals(fvs[1]) && !"".equals(fvs[1])){
				fvMap.put(fvs[0], fvs[1]);
			}
//			else{
//				System.out.println("#######"+s);
//			}
		}
		//get Key
//		for (Field field : FieldUtils.allDeclaredField(pojo.getClass())) {
////			if(fvMap.get(field.getName())!=null){
////				FieldUtils.setObjectValue(pojo, field,fvMap.get(field.getName()));
////			}
//			Object value = FieldUtils.getObjectValue(pojo, field.getName());
//			if(value!=null){
//				fvMap.put(field.getName(), value);
//			}
//		}
		T source = (T) converType(fvMap, pojo.getClass());
		for (Field field : FieldUtils.allDeclaredField(source.getClass())) {
			Object value = FieldUtils.getObjectValue(source, field.getName());
			if(value!=null){
				FieldUtils.setObjectValue(pojo, field, value);
			}
		}
		fvMap.clear();
		return pojo;
	}
	
	private static <T> T converType(Object source,Class<T> clazz){
		return mapper.convertValue(source, clazz);
	}
	
}
