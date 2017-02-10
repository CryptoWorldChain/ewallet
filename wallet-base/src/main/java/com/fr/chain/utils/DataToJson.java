package com.fr.chain.utils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
@Slf4j
public class DataToJson {
	static ObjectMapper mapper = new ObjectMapper();
	static 
	{
		mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	public static String dataToJson(String data,int page,int totalRecord,int rows) throws JsonProcessingException, IOException{
		String s="{}";
		ObjectMapper mapper = new ObjectMapper(); 
		JsonNode rootNode = mapper.readTree(s);
		JsonNode dataNode = mapper.readTree(data);
		((ObjectNode)rootNode).put("page", page);
		((ObjectNode)rootNode).put("records", totalRecord);
		int totalPage = totalRecord % rows == 0 ? totalRecord / rows : totalRecord / rows + 1; // 计算总页数
		((ObjectNode)rootNode).put("total", totalPage);
		((ObjectNode)rootNode).put("rows", dataNode);
		return rootNode.toString();
	}
	public static String dataToJson(String data,int page,int totalRecord,int rows,List<String> date_list,boolean time) throws JsonProcessingException, IOException{
		String s="{}";
		ObjectMapper mapper = new ObjectMapper(); 
		JsonNode rootNode = mapper.readTree(s);
		JsonNode dataNode = mapper.readTree(data);
		String dataJson = TransformDate.transform(data, date_list,time);
		if(dataJson.endsWith(",")){
			dataJson = dataJson.substring(0, dataJson.length() -1);
		}
		dataNode = mapper.readTree("["+dataJson.replace("\\", "")+"]");
		
		((ObjectNode)rootNode).put("page", page);
		((ObjectNode)rootNode).put("records", totalRecord);
		int totalPage = totalRecord % rows == 0 ? totalRecord / rows : totalRecord / rows + 1; // 计算总页数
		((ObjectNode)rootNode).put("total", totalPage);
		((ObjectNode)rootNode).put("rows", dataNode);
		return rootNode.toString();
	}
	
	
	public static String setYeAmountToJson(String date) throws JsonProcessingException, IOException{
		JsonNode dataNode = mapper.readTree(date);
		for(JsonNode node:dataNode){
			((ObjectNode)node).put("extrafield1",node.get("mertrademoney").asLong()-node.get("tradeamount").asLong());
		}
		return dataNode.toString();
	}
	public static JsonNode dataToJson(String data) throws JsonProcessingException, IOException{
		ObjectMapper mapper = new ObjectMapper(); 
		JsonNode dataNode = mapper.readTree(data);
		return dataNode;
	}
	public static String list2Json(List<Map> list,List<String> layout,Object obj){
		ArrayNode arrynode = newArrayNode();
		for(Map t: list){
			arrynode.add(bean2Json(t,layout,obj));
		}
		return arrynode.toString();
	}

	public static JsonNode bean2Json(Map bean,List<String> layout,Object obj) {
			if(bean==null)
			{
				return null;
			}
			Class<?> clazz = obj.getClass();
			Field[] fields = clazz.getDeclaredFields();
			List<Field> list = new ArrayList<Field>();
			for (Field field : fields) {
				list.add(field);
			}
			fields = clazz.getSuperclass().getDeclaredFields();
			for (Field field : fields) {
				list.add(field);
			}
			String str = "{}";
			JsonNode node;
			try {
				node = mapper.readTree(str);
				for (Field field : list) {
					String columnname = field.getName();
					if(layout.indexOf(columnname)>=0){
						if (field.getType()==Date.class&&bean.get(columnname)!=null) {
							((ObjectNode)node).put(columnname, DateUtil.format((Date)bean.get(columnname),DateStyle.YYYY_MM_DD));
						}else{
							((ObjectNode)node).put(columnname, bean.get(columnname)==null?"":bean.get(columnname).toString());
						}
					}
				}
				return node;
			} catch (JsonProcessingException e) {
				log.error("json转换出错:",e);
			} catch (IOException e) {
				log.error("io错误:",e);
				throw new RuntimeException(e);
			}
			return null;
	}
	
	public static String jsonLongTimeToDate(String data,String timedata) throws JsonProcessingException, IOException{
		ObjectMapper mapper = new ObjectMapper(); 
		JsonNode dataNode = mapper.readTree(data);
		String []time = timedata.split(",");
		for (JsonNode jsonNode : dataNode) {
			for (String str : time) {
				((ObjectNode)jsonNode).put(str, (jsonNode.get(str)==null||"".equals(jsonNode.get(str).asText()))?"1970-01-01":DateUtil.format(new Date(jsonNode.get(str).asLong()),DateStyle.YYYY_MM_DD));
			}
		}
		return dataNode.toString();
	}

	
	public static ArrayNode newArrayNode() {
		return mapper.createArrayNode();
	}
	private static String fristCharacterToUpcase(String str) {
		return str.substring(0, 1).toUpperCase()
				+ str.substring(1, str.length());
	}
}
