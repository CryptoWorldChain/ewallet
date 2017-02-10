package com.fr.chain.utils;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class JoinSQLUtils {
	public static String joinSQLSelect(String tablename, Map<String, String> paraMap,
			List<String> layout, Map<String, String> relation,int page,int rows) {
		String sql = "select ";
		
		String regex = "\\d{4}-\\d{2}-\\d{2}";
		for (String string : layout) {
			sql+=string+",";
		}
		sql = sql.substring(0,sql.length()-1)+" from "+ tablename+" ";
		if(paraMap.size()>0){
			sql+="where ";
		}
		String str="";
		for (Map.Entry<String, String> entry : paraMap.entrySet()) {
			str=relation.get(entry.getKey());
			if(str==null)continue;
			if("-10".equals(entry.getValue().toString())){
				continue;
			}
			if(Pattern.matches(regex, entry.getValue())){
				if(str.split(",")[1].contains(">")){
					paraMap.put(entry.getKey(), String.valueOf(DateUtil.getDate("yyyy-MM-dd hh:mm:ss", entry.getValue()+" 00:00:00").getTime()));
				}else if(str.split(",")[1].contains("<")){
					paraMap.put(entry.getKey(), String.valueOf(DateUtil.getDate("yyyy-MM-dd hh:mm:ss", entry.getValue()+" 23:59:59").getTime()));
				}
				
			}
			sql += str.split(",")[0]+" "+str.split(",")[1]+" '"+entry.getValue()+"' and ";
		}
		if(paraMap.size()>0&&sql.endsWith("and ")){
			sql=sql.substring(0,sql.length()-4);
		}
		if(sql.endsWith("where ")){
			sql=sql.substring(0,sql.length()-6);
		}
		if(layout!=null && layout.size()>0){
			sql+=" ORDER BY ";
			for(String order : layout){
				
				if("T_PARA_LIMITEDNUM".equals(tablename)||"T_PARA_LIMITEDFEATURE".equals(tablename)){
					if("ltype".equals(order)){
						sql+=order+",";
					}
				}else{
					if("statdate".equals(order)){
						sql+=order+",";
					}else if("period".equals(order)){
						sql+=order+",";
					}
				}
			}
		}
		if(sql.endsWith(",")){
			sql=sql.substring(0, sql.length()-1);
		}else{
			sql=sql.substring(0, sql.lastIndexOf(" ORDER BY "));
		}
		if("T_PARA_LIMITEDNUM".equals(tablename) || "T_PARA_LIMITEDFEATURE".equals(tablename)){
			return sql;
		}
		if(page!=0 && rows!=0){
			return sql+" limit "+(page-1)*rows+","+rows;
		}
		return sql;
	}
	
	public static String joinSQLSelectWithOrderBy(String tablename, Map<String, String> paraMap,
			List<String> layout, Map<String, String> relation, List<String> orderList,int page,int rows){
	
		String sql = "select ";
		
		String regex = "\\d{4}-\\d{2}-\\d{2}";
		for (String string : layout) {
			sql+=string+",";
		}
		sql = sql.substring(0,sql.length()-1)+" from "+ tablename+" ";
		if(paraMap.size()>0){
			sql+="where ";
		}
		String str="";
		for (Map.Entry<String, String> entry : paraMap.entrySet()) {
			str=relation.get(entry.getKey());
			if(str==null)continue;
			if("-10".equals(entry.getValue().toString())){
				continue;
			}
			if(Pattern.matches(regex, entry.getValue())){
				if(str.split(",")[1].contains(">")){
					paraMap.put(entry.getKey(), String.valueOf(DateUtil.getDate("yyyy-MM-dd hh:mm:ss", entry.getValue()+" 00:00:00").getTime()));
				}else if(str.split(",")[1].contains("<")){
					paraMap.put(entry.getKey(), String.valueOf(DateUtil.getDate("yyyy-MM-dd hh:mm:ss", entry.getValue()+" 23:59:59").getTime()));
				}
				
			}
			sql += str.split(",")[0]+" "+str.split(",")[1]+" '"+entry.getValue()+"' and ";
		}
		if(paraMap.size()>0&&sql.endsWith("and ")){
			sql=sql.substring(0,sql.length()-4);
		}
		if(sql.endsWith("where ")){
			sql=sql.substring(0,sql.length()-6);
		}
		if(orderList!=null && orderList.size()>0){
			sql+=" ORDER BY ";
			for(String order : orderList){
				sql+=order+",";
			}
		}
		if(sql.endsWith(",")){
			sql=sql.substring(0,sql.length()-1);
		}
		if(page!=0 && rows!=0){
			return sql+" limit "+(page-1)*rows+","+rows;
		}
		return sql;
		
	}
	
	
	public static String joinSQLCount(String tablename, Map<String, String> paraMap,
			Map<String, String> relation) {
		String sql = "select count(*) ";
		String regex = "\\d{4}-\\d{2}-\\d{2}";
		sql += " from "+ tablename+" ";
		if(paraMap.size()>0){
			sql+="where ";
		}
		String str="";
		for (Map.Entry<String, String> entry : paraMap.entrySet()) {
			str=relation.get(entry.getKey());
			if(str==null)continue;
			if(("".equals(str))&&("-1".equals(str))){
				continue;
			}
			if("".equals(entry.getValue())||"-1".equals(entry.getValue())){
				continue;
			}
			if(Pattern.matches(regex, entry.getValue())){
				if(str.split(",")[1].contains(">")){
					paraMap.put(entry.getKey(), String.valueOf(DateUtil.getDate("yyyy-MM-dd hh:mm:ss", entry.getValue()+" 00:00:00").getTime()));
				}else if(str.split(",")[1].contains("<")){
					paraMap.put(entry.getKey(), String.valueOf(DateUtil.getDate("yyyy-MM-dd hh:mm:ss", entry.getValue()+" 23:59:59").getTime()));
				}
				
			}
			sql += str.split(",")[0]+" "+str.split(",")[1]+" '"+entry.getValue()+"' and ";
		}
		if(paraMap.size()>0){
			sql=sql.substring(0,sql.length()-4);
		}
		return sql;
	}

}
