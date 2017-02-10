package com.fr.chain.web.bind;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

import com.fr.chain.utils.JsonUtil;
import com.fr.chain.web.bean.FieldsMapperBean;
import com.fr.chain.web.bean.FieldsMapperBean.SearchField;

public class FieldsMapperResolver {
	
	public static FieldsMapperBean genQueryMapper(String json) {
		FieldsMapperBean fmb = new FieldsMapperBean();
		ObjectNode node = JsonUtil.toObjectNode(json);
		Iterator<Map.Entry<String, JsonNode>> iter = node.getFields();
		while (iter.hasNext()) {
			Entry<String, JsonNode> entry = iter.next();
			String key = entry.getKey();
			JsonNode value = entry.getValue();
			SearchField sf = new SearchField();
			sf.setFieldName(key);
			sf.setShow(value.asInt());
			fmb.getFields().add(sf);
		}
		return fmb;
	}
}
