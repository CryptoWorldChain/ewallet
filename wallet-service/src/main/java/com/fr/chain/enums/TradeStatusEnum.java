package com.fr.chain.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 订单状态枚举类
 * @author dylan
 *
 */
public enum TradeStatusEnum {	
	失败(0, "失败"),
	处理中(1, "处理中"),
	成功(2, "成功");
	

	public static String getNameByValue(int value) {
		TradeStatusEnum status = valueMap.get(value);
		if (status != null) {
			return status.getName();
		}
		return "未知状态";
	}

	public static Integer getValueByName(String name) {
		TradeStatusEnum status = nameMap.get(name);
		if (status != null) {
			return status.getValue();
		}
		return null;
	}

	private static Map<Integer, TradeStatusEnum> valueMap;

	private static Map<String, TradeStatusEnum> nameMap;
	static {
		valueMap = new HashMap<Integer, TradeStatusEnum>();
		nameMap = new HashMap<String, TradeStatusEnum>();
		for (TradeStatusEnum status : TradeStatusEnum.values()) {
			valueMap.put(status.getValue(), status);
			nameMap.put(status.getName(), status);
		}
	}

	private int value;
	private String name;

	private TradeStatusEnum(int value, String name) {
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
