package com.fr.chain.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 资产状态枚举类
 * @author dylan
 *
 */
public enum PropertyStatusEnum {	
	不可用(0, "不可用"),
	可用(1, "可用"),
	锁定(2, "锁定");
	

	public static String getNameByValue(int value) {
		PropertyStatusEnum status = valueMap.get(value);
		if (status != null) {
			return status.getName();
		}
		return "未知状态";
	}

	public static Integer getValueByName(String name) {
		PropertyStatusEnum status = nameMap.get(name);
		if (status != null) {
			return status.getValue();
		}
		return null;
	}

	private static Map<Integer, PropertyStatusEnum> valueMap;

	private static Map<String, PropertyStatusEnum> nameMap;
	static {
		valueMap = new HashMap<Integer, PropertyStatusEnum>();
		nameMap = new HashMap<String, PropertyStatusEnum>();
		for (PropertyStatusEnum status : PropertyStatusEnum.values()) {
			valueMap.put(status.getValue(), status);
			nameMap.put(status.getName(), status);
		}
	}

	private int value;
	private String name;

	private PropertyStatusEnum(int value, String name) {
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
