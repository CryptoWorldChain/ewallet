package com.fr.chain.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;

/**
 * Json中的时间转换 有long 转换为 String（年月日）
 *
 */
public class TransformDate {
	
	public static String transform(String json,List<String> list,boolean time) throws JsonProcessingException, IOException{
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(json);
		String jsonDate = null;
		String jsonDate2="";
		
		if(node.isArray()){
			for(JsonNode jsonNode : node){
				if(jsonDate==null){
					jsonDate=jsonNode.toString();
				}
				for(String path : list){
					 jsonDate =TransformDate.formatJsonDate(jsonDate, path,time);
				}
				jsonDate2+=jsonDate+",";
				jsonDate=null;
				
			}
		}
		return jsonDate2;
		
	}
	
	public static String formatDate(Long date,boolean time){
		
		if(date!=null && date!=0){
			if(time){
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				Date date2 = new Date(date);
				return format.format(date2);
			}
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date date2 = new Date(date);
			return format.format(date2);
		}
		return "";
		
	}
	public static String formatJsonDate(String json,String path,boolean time){
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = null;
		try {
			node = mapper.readTree(json);
			int size = node.size();
			long jsondate = node.path(path).asLong();
			
			String formatDate = TransformDate.formatDate(jsondate,time);
			if(formatDate!=null && !"".equals(formatDate)){
				((ObjectNode)node).put(path, formatDate);			
			}
		

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return node.toString();
				
	}
	
	public static void main(String[] args) {
//		String aa="{\"region\":\"TJ\",\"ltype\":\"GXPCK3\",\"closesharedate\":\"1371225600000\",\"notifystatus\":\"1\",\"processstatus\":\"2100\",\"status\":\"1\",\"period\":\"2012001\",\"prestartdate\":\"1365336000000\",\"iperiod\":\"2012001\",\"startdate\":\"1365343200000\",\"realenddate\":\"1365508740075\",\"realstartdate\":\"1396762982080\",\"salestatus\":\"1\",\"updatedate\":\"1396762982282\"}";
//		// String str = "{\"data\":{\"birth_day\":7,\"birth_month\":6},\"errcode\":10,\"msg\":\"ok\",\"ret\":0}";
//		ObjectMapper mapper = new ObjectMapper();
//		JsonNode node;
//		try {
//			node = mapper.readTree(aa);
//			 long path = node.path("closesharedate").asLong();//closesharedate
//			 
//			 String asText = node.path("ltype").asText();
//			System.out.println(path);
//		} catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		//String date = TransformDate.formatDate(1465508800000L);
		//System.out.println(date);
		
	}
	
}
