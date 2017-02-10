package com.fr.chain.web.bean;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Select;
import org.codehaus.jackson.JsonNode;

import com.fr.chain.web.bean.FieldsMapperBean.SearchField;
import com.fr.chain.web.bind.FieldUtils;
import com.fr.chain.web.bind.QueryMapperResolver;

@Slf4j
public class SqlMaker {
	
	public final static String TABLE_SUFFIX_KEY="SUFFIX";
	
	public final static String TABLE_NAME = "TABLE_NAME";

	public static <T> String getCountSql(DbCondi dc)
			throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		return selectInfoSql(" COUNT(*) COUNT ", dc);
	}

	public static <T> String getReferenceCountSql(DbCondi dc)
			throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		String selectStr = "";
		try {
			Class<?> mapperClazz = dc.getMapperClass();
			
			Select select  =mapperClazz.getMethod("selectReferenceTabCount").getAnnotation(Select.class);
			//String selectStr = select.value()[0];
			StringBuffer selectBuffer = new StringBuffer();
			for (String selStr: select.value()) {
				selectBuffer.append(selStr);
			}
			selectStr = selectBuffer.toString();
		} catch (NoSuchMethodException e) { 
			return selectInfoSql(" COUNT(*) COUNT ", dc); 
		}
		return selectReferenceInfoSql(selectStr, dc);
	}
	
	
	public static String getData(DbCondi dc) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> clazz = dc.getEntityClass();
		FieldsMapperBean fmb = dc.getFmb();
		return selectInfoSql(getDbFieldNames(clazz,fmb), dc);
	}

	public static String getReferenceData(DbCondi dc) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String selectStr = "";
		try {
			Class<?> mapperClazz = dc.getMapperClass();	
			Select select  =mapperClazz.getMethod("selectReferenceTab").getAnnotation(Select.class);
			//String selectStr = select.value()[0];
			StringBuffer selectBuffer = new StringBuffer();
			for (String selStr: select.value()) {
				selectBuffer.append(selStr);
			}
			selectStr = selectBuffer.toString();
		} catch (NoSuchMethodException e) { 
			
			Class<?> clazz = dc.getEntityClass();
			FieldsMapperBean fmb = dc.getFmb();
			return selectInfoSql(getDbFieldNames(clazz,fmb), dc);
		}
		return selectReferenceInfoSql(selectStr, dc);
	}
	
	
	public static String getDbFieldNames(Class<?> clazz,FieldsMapperBean fmb){
		Map<String,Field> fieldMap = new HashMap<String, Field>();
		for (Field field : FieldUtils.allDeclaredField(clazz)) {
			//从注解里判断列是否自动加入sql
			Col fieldColAnno = field.getAnnotation(Col.class);
			if (!((fieldColAnno != null) && (!fieldColAnno.autoField()))) {
				//
				fieldMap.put(field.getName(), field);
			}
		}
		StringBuffer fields = new StringBuffer();
		if(fmb!=null&&fmb.getFields().size()>0){
			for(SearchField sf : fmb.getFields()){
				if(sf.getShow()==1){
					//添加验证 是否包含字段
					if(checkQueryField(sf.getFieldName(), fieldMap)){
						fields.append(FieldUtils.field2SqlColomn(sf.getFieldName())).append(" ").append(sf.getFieldName()).append(",");
					}else{
						log.debug("The query fields[{}] are not among Class [{}]..",sf.getFieldName(),clazz.getSimpleName());
					}
				}
			}
		}else{
			for(Entry<String,Field> entry : fieldMap.entrySet()){
				
				String f = FieldUtils.field2SqlColomn(entry.getKey());
				String n = entry.getKey();
				
				fields.append(FieldUtils.field2SqlColomn(entry.getKey())).append(" ").append(entry.getKey()).append(",");
			}
		}
		
		
		String s = fields.substring(0, fields.lastIndexOf(","));
		
		return fields.substring(0, fields.lastIndexOf(","));
	}
	
	private static boolean checkQueryField(String fieldName,Map<String,Field> fieldMap){
		if(fieldMap.get(fieldName)==null){
			return false;
		}
		return true;
	}
	
	private static String getTableName(Class<?> clazz,DbCondi dc){
		return ObjectUtils.toString(dc.getOther().get(TABLE_NAME));
	}
	
	public static String selectInfoSql(String selectInfo ,DbCondi dc) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Class<?> clazz = dc.getEntityClass();
		QueryMapperBean queryCondi = dc.getQmb();
		PageInfo para = dc.getPageinfo();
		String  sort = para.getSort();

		Map<String,String> fieldsMap = new HashMap<String, String>();

		for (Field field : FieldUtils.allDeclaredField(clazz)) {
			//从注解里获取列名
			Col fieldColAnno = field.getAnnotation(Col.class);
			if (fieldColAnno != null) {
				fieldsMap.put(field.getName(), FieldUtils.field2SqlColomn(fieldColAnno.tableAlias() + "." + fieldColAnno.name()));
			} else {
				fieldsMap.put(field.getName(), FieldUtils.field2SqlColomn(field.getName()));
			}
		}		
		
		if(!para.isSortModed()&&(sort != null)&&sort.length()>0){
			String sortColm="";
			for(String col:sort.split(",")){
				if(sortColm.length()>0)sortColm+=",";
				if(col.startsWith("-")){
					sortColm+=fieldsMap.get(col.substring(1))+" DESC";
				}else{
					sortColm+=fieldsMap.get(col);
				}
			}
			 para.setSort(sortColm);
			 para.setSortModed(true);
		}
				
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ").append(selectInfo).append(" FROM ");
		sql.append(getTableName(clazz, dc));
		if(queryCondi==null){
			addPageLimit(para, sql);
			return sql.toString();
		}
		JsonNode node=dc.getQmb().getNode();
		
		String whereClause=QueryMapperResolver.genQueyDirectory("", node, "and",fieldsMap);

		
		if(StringUtils.isBlank(whereClause)){
			addPageLimit(para, sql);
			return sql.toString();
		}
		
		sql.append(" WHERE "+whereClause);
		addPageLimit(para, sql);
		return sql.toString();
	}

	public static String selectReferenceInfoSql(String selectInfo ,DbCondi dc) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Class<?> clazz = dc.getEntityClass();
		QueryMapperBean queryCondi = dc.getQmb();
		PageInfo para = dc.getPageinfo();
		StringBuffer sql = new StringBuffer();
		sql.append(selectInfo);
		if(queryCondi==null){
			addPageLimit(para, sql);
			return sql.toString();
		}
		JsonNode node=dc.getQmb().getNode();
		Map<String,String> fieldsMap = new HashMap<String, String>();

		for (Field field : FieldUtils.allDeclaredField(clazz)) {
			//从注解里获取列名
			Col fieldColAnno = field.getAnnotation(Col.class);
			if (fieldColAnno != null) {
				fieldsMap.put(field.getName(), FieldUtils.field2SqlColomn(fieldColAnno.tableAlias() + "." + fieldColAnno.name()));
			} else {
				fieldsMap.put(field.getName(), FieldUtils.field2SqlColomn(field.getName()));
			}
		}
		
		String whereClause=QueryMapperResolver.genQueyDirectory("", node, "and",fieldsMap);
		
//		String whereClause=dc.getQmb().getWhereClause();
		if(StringUtils.isBlank(whereClause)){
			addPageLimit(para, sql);
			return sql.toString();
		}
		
		
		
		
		sql.append(" WHERE "+whereClause);
		addPageLimit(para, sql);
		return sql.toString();
	}
	
	
	public static void addPageLimit(PageInfo para,StringBuffer sql){
		if(para!=null){
			if(StringUtils.isNotBlank(para.getSort())){
				sql.append(" ORDER BY "+para.getSort());
			}
			if(Integer.MAX_VALUE != para.getLimit()){
				sql.append(" limit ").append(para.getSkip()).append(",").append(para.getLimit());
			}
		}
	}
	
}
