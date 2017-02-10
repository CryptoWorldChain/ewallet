package com.fr.chain.utils;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.codehaus.jackson.util.TokenBuffer;

public class JsonUtil {
	static ObjectMapper mapper = new ObjectMapper();
	static 
	{
		mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.setVisibilityChecker(mapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
	}

	public static <T> T json2Bean(JsonNode node, Class<T> clazz) {
		try {
			return mapper.readValue(node, clazz);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	
	public static <T> T json2Bean(String jsonTxt, Class<T> clazz) {
		try {
			return mapper.readValue(jsonTxt, clazz);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	public static <T> List<T>  json2List(String jsontxt,Class<T> clazz){
		List<T> list = new ArrayList<>();
		ArrayNode nodes = toArrayNode(jsontxt);
		for(JsonNode node : nodes){
			list.add(json2Bean(node, clazz));
		}
		return list;
	}
	public static <T> String list2Json(List<T> list){
		ArrayNode arrynode = newArrayNode();
		for(T t: list){
			arrynode.add(bean2Json(t));
		}
		return arrynode.toString();
	}

	public static JsonNode bean2Json(Object bean) {
		try {
			if(bean==null)
			{
				return null;
			}
			TokenBuffer buffer = new TokenBuffer(mapper);
			mapper.writeValue(buffer, bean);
			JsonNode node = mapper.readTree(buffer.asParser());
			return node;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static ArrayNode newArrayNode() {
		return mapper.createArrayNode();
	}

	public static ArrayNode toArrayNode(String jsontxt) {
		try {
			return mapper.readValue(jsontxt, ArrayNode.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static ObjectNode toObjectNode(String jsontxt) {
		try {
			return mapper.readValue(jsontxt, ObjectNode.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
