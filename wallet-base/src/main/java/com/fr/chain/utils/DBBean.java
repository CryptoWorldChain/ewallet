package com.fr.chain.utils;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

import org.apache.commons.lang3.StringUtils;

import com.fr.chain.web.bean.Tab;



public enum DBBean {
	adminoperator("T_ADMIN_OPERATOR",true),
	adminoperatorrole("T_ADMIN_OPERATOR_ROLE",true),
	adminpermission("T_ADMIN_PERMISSION",true),
	adminrole("T_ADMIN_ROLE",true),
	phdmedicalprocess("T_P_HD_MEDICAL_PROCESS",true),
	phdphysicalexam("T_P_HD_PHYSICAL_EXAM",true),
	phdmedicalhistory("T_P_HD_MEDICAL_HISTORY",true),
	adminrolepermission("T_ADMIN_ROLE_PERMISSION",true),	
	patientoperation("T_P_PATIENT_OPERATION",true),
	
	tmpmedicineadvice("T_P_ADVICE_MEDICINE_TMP",true),
	oralmedicineadvice("T_P_ADVICE_MEDICINE_ORAL",true),
	examinemedicineadvice("T_P_ADVICE_EXAMINE",true),
	
	dialysisadvice("T_P_ADVICE_DIALYSIS",true),
	spritzemedicineadvice("T_P_ADVICE_MEDICINE_SPRITZE",true);


	@Getter
	private String table;
	@Getter
	private boolean staticTable;
	
	private DBBean(String table,boolean staticTable) {
		this.table = table;
		this.staticTable = staticTable;
	}
	private static Map<String,DBBean> classMap = new HashMap<>();
	
	static{
		for(DBBean info :DBBean.values()){
			classMap.put(info.name(), info);
		}
	}
	
	public static String getTableName2Class(Class<?> clazz){
		//从注解里读取表名
		Tab classTabAnno = clazz.getAnnotation(Tab.class);
		if (classTabAnno != null) {
			return classTabAnno.name();
		} else {
			return classMap.get(clazz.getSimpleName().toLowerCase()).getTable();
		}
	}
	
	public static String getDomain2Class(Class<?> clazz){
		return clazz.getSimpleName().toLowerCase();
	}
	
	public static String getTable2Name(String name){
		name = name.toLowerCase();
		if(StringUtils.contains(name, separator)){
			String[] str = StringUtils.split(name, separator);
			String tableName = classMap.get(str[0]).getTable();
			for(int i = 1 ;i<str.length;i++){
				tableName+=str[i];
				if(i<(str.length-1)){
					tableName+="_";
				}
			}
			return tableName;
		}
		return classMap.get(name).getTable();
	}
	
	public static boolean isStaticTable(String name){
		name = name.toLowerCase();
		return classMap.get(name)==null?false:classMap.get(name).isStaticTable();
	}
	
	private final static String separator = "_";
}
