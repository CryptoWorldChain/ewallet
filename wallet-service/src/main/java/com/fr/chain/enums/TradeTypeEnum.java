package com.fr.chain.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 交易类型枚举类
 * @author dylan
 *
 */
public enum TradeTypeEnum {	
	创建资产(1, "创建资产"),
	发送资产(2, "发送资产"),
	领取资产(3, "领取资产"),
	退回资产(4, "退回资产"),
	丢弃资产(5, "丢弃资产"),
	消费资产(6, "消费资产");
	

	public static String getNameByValue(int value) {
		TradeTypeEnum status = valueMap.get(value);
		if (status != null) {
			return status.getName();
		}
		return "未知状态";
	}

	public static Integer getValueByName(String name) {
		TradeTypeEnum status = nameMap.get(name);
		if (status != null) {
			return status.getValue();
		}
		return null;
	}

	private static Map<Integer, TradeTypeEnum> valueMap;

	private static Map<String, TradeTypeEnum> nameMap;
	static {
		valueMap = new HashMap<Integer, TradeTypeEnum>();
		nameMap = new HashMap<String, TradeTypeEnum>();
		for (TradeTypeEnum status : TradeTypeEnum.values()) {
			valueMap.put(status.getValue(), status);
			nameMap.put(status.getName(), status);
		}
	}

	private int value;
	private String name;

	private TradeTypeEnum(int value, String name) {
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
