package com.fr.chain.utils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;

/**
 * Json转换时间有字符串转换为长整形
 */
public class TransformString {
	
	private static List<String> date_list = new ArrayList<String>();
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	static{
		
		
		date_list.add("closesharedate");
		date_list.add("prestartdate");
		date_list.add("startdate");
		date_list.add("realenddate");
		date_list.add("enddate");
		date_list.add("realstartdate");
		date_list.add("createdate");
		date_list.add("updatedate");
		date_list.add("handletime");
		date_list.add("lastdate");
		date_list.add("statdate");
		date_list.add("wagerdate");
		date_list.add("validationdate");
		date_list.add("requesttimes");
		date_list.add("printtime");
		date_list.add("lotterytime");
		
	}
	
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	public static String transform(String json){		
		String jsonDate = null;
		String jsonDate2="";
		try {
			JsonNode node = mapper.readTree(json);
			if(node.isArray()){
				for(JsonNode jsonNode : node){
					if(jsonDate==null){
						jsonDate = jsonNode.toString();
					}
					for(String path : date_list){
						jsonDate = TransformString.transformJsonString(jsonDate,path);
					}
					jsonDate2+=jsonDate+",";
					jsonDate=null;
				}
			}
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "["+jsonDate2.substring(0,jsonDate2.length()-1)+"]";
		
	}

	private static String transformJsonString(String jsonDate, String path) {
		JsonNode node = null;
		long  formatDate = 0;
		try {
			node = mapper.readTree(jsonDate);
			String date = node.path(path).asText();
			if(StringUtils.isNotEmpty(date)){
				formatDate = TransformString.formatString(date);
			}
			if(formatDate!=0){
				((ObjectNode)node).put(path, formatDate);
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return node.toString();
	}

	private static Long formatString(String date) {
		if(date!=null && !"".equals(date)){
			
			if(date.matches("\\d{4}-\\d{2}-\\d{2}")){//转换为年月日
				try {
					Date parse = dateFormat.parse(date);
					return parse.getTime();
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}else{
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				try {
					return dateFormat1.parse(date).getTime();
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			
		}
		return null;
	}
	

}
