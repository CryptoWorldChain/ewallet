package com.fr.chain.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基础状态枚举类
 * 命名规则: 实体类名 + 属性名 + Enum
 * @date	2014-10-13
 */
public enum BaseStatusEnum {
	
	有效(1, "有效", 1),
	无效(0, "无效", 1),
	成功(1, "成功", 2),
	失败(0, "失败", 2),
	不预期错误(-1, "不预期错误", 2),
	是(1, "是", 3),
	否(0, "否", 3),
	启用(1, "启用", 4),
	停用(0, "停用", 4),
	未處理(0, "未處理", 5),
	已處理(1, "已處理", 5),	
	已失效(2, "已失效", 5),
	json原始数据入库(0, "json原始数据入库", 6),	
	json数据已经被处理(1, "json数据已经被处理", 6),
	同步失败(0, "同步失败", 7),	
	未同步(1, "未同步", 7),
	已同步(2, "已同步", 7),	
	待提现(1, "待提现", 8),
	已提现(2, "已提现", 8),
	初始(0, "初始", 9),
	RSA(1, "RSA", 10),
	MD5(2, "MD5", 10),	
	SHA(3, "SHA", 10),
	IPS_MD5(17, "IPS_MD5", 10);
	
		
	/** 有效无效组变量 **/
	public final static int BSE_GROUP_V = 1;
	/** 成功失败组变量 **/
	public final static int BSE_GROUP_S = 2;
	/** 是否组变量 **/
	public final static int BSE_GROUP_Y = 3;
	/** 启用停用组变量 **/
	public final static int BSE_GROUP_U = 4;
	
	/**
	 * @param type type
	 * @param code code
	 * @return BaseStatusEnum
	 */
	public static String getNameByTypeAndCode(Integer type, Integer code) {
		BaseStatusEnum names = codeMap.get(type+""+code);
		if (names != null) {
			return names.getName();
		}
		return "未知状态";
	}
	
	public static Integer getCodeByName(String name) {
		BaseStatusEnum codes = nameMap.get(name);
		if (codes != null) {
			return codes.getCode();
		}
		return null;
	}

	private static Map<String, BaseStatusEnum> codeMap;
	private static Map<String, BaseStatusEnum> nameMap;

	private BaseStatusEnum(Integer code, String name, Integer type) {
		this.code = code;
		this.name = name;
		this.type = type;
	}

	static {
		codeMap = new HashMap<String, BaseStatusEnum>();
		nameMap = new HashMap<String, BaseStatusEnum>();
		for (BaseStatusEnum type : BaseStatusEnum.values()) {
			codeMap.put(type.getType()+""+type.getCode(), type);
			nameMap.put(type.getName(), type);
		}
	}
	private Integer code;
	private String name;
	private Integer type;

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
	
	public Integer getType() {
		return type;
	}

	public String toString() {
		return this.getCode() + "-" + this.getName() + "-" + this.getType();
	}
	
	static List<BaseStatusEnum> list ;
	
	public static BaseStatusEnum[] getValuesByType(Integer type){
		if(null==type) return null;
		list = new ArrayList<BaseStatusEnum>();
		for(BaseStatusEnum _enum : BaseStatusEnum.values()) {
			if(type==_enum.getType()) {
				list.add(_enum);
			}
		}
		return list.toArray(new BaseStatusEnum[0]);
	}
	
}