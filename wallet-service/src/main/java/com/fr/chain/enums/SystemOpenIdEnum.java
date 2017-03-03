package com.fr.chain.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 交易类型枚举类
 * @author dylan
 *
 */
public enum SystemOpenIdEnum {	
	系统默认账户(10000000, "OpenId_Sys");

	public static String getNameByValue(int value) {
		SystemOpenIdEnum status = valueMap.get(value);
		if (status != null) {
			return status.getName();
		}
		return "未知状态";
	}

	public static Integer getValueByName(String name) {
		SystemOpenIdEnum status = nameMap.get(name);
		if (status != null) {
			return status.getValue();
		}
		return null;
	}

	private static Map<Integer, SystemOpenIdEnum> valueMap;

	private static Map<String, SystemOpenIdEnum> nameMap;
	static {
		valueMap = new HashMap<Integer, SystemOpenIdEnum>();
		nameMap = new HashMap<String, SystemOpenIdEnum>();
		for (SystemOpenIdEnum status : SystemOpenIdEnum.values()) {
			valueMap.put(status.getValue(), status);
			nameMap.put(status.getName(), status);
		}
	}

	private int value;
	private String name;

	private SystemOpenIdEnum(int value, String name) {
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
