package com.fr.chain.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 资产类型枚举类
 * @author dylan
 *
 */
public enum PropertyTypeEnum {	
	自营资产(1, "自营资产"),
	数字资产(2, "数字资产"),
	个性资产(3, "个性资产");
	

	public static String getNameByValue(int value) {
		PropertyTypeEnum status = valueMap.get(value);
		if (status != null) {
			return status.getName();
		}
		return "未知状态";
	}

	public static Integer getValueByName(String name) {
		PropertyTypeEnum status = nameMap.get(name);
		if (status != null) {
			return status.getValue();
		}
		return null;
	}

	private static Map<Integer, PropertyTypeEnum> valueMap;

	private static Map<String, PropertyTypeEnum> nameMap;
	static {
		valueMap = new HashMap<Integer, PropertyTypeEnum>();
		nameMap = new HashMap<String, PropertyTypeEnum>();
		for (PropertyTypeEnum status : PropertyTypeEnum.values()) {
			valueMap.put(status.getValue(), status);
			nameMap.put(status.getName(), status);
		}
	}

	private int value;
	private String name;

	private PropertyTypeEnum(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	public String toString() {
		return this.getValue() + "-" + this.getName();
	}
}
