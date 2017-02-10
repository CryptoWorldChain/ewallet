package com.fr.chain.db.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import com.fr.chain.db.dao.CommonSqlMapper;
import com.fr.chain.db.service.DataService;
import com.fr.chain.utils.DateUtils;
import com.fr.chain.utils.JsonUtil;
import com.fr.chain.utils.SerializerUtil;

@Slf4j
@Component("mysqlDataService")
public class MysqlDataService implements DataService {
	
	@Resource private CommonSqlMapper commonSqlMapper;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object doBySQL(String sql) throws Exception {
		List<Map> newMap = new ArrayList<>();
		List<HashMap<String,Object>> maps = commonSqlMapper.executeSql(sql);
		if (maps != null && maps.size() > 0) {
			for (Map map : maps) { 
				if (map != null) {
					for (Object key : map.keySet()) {
						Object obj = map.get(key);
						if (obj instanceof Timestamp) {
							map.put(key,  DateUtils.formatDate("yyyy-MM-dd HH:mm:ss",((Timestamp) obj)));
						}
					}
					newMap.add(map);
				}
			}
			maps.clear();
		}
		if (newMap.size() > 0) {
			return SerializerUtil.serializeArray(newMap);
		}
		return null;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String doBySQL2jsonstr(String sql) throws Exception {
		List<Map> newMap = new ArrayList<>();
		List<HashMap<String,Object>> maps = commonSqlMapper.executeSql(sql);
		if (maps != null && maps.size() > 0) {
			for (Map map : maps) { 
				if (map != null) {
					for (Object key : map.keySet()) {
						Object obj = map.get(key);
						if (obj instanceof Timestamp) {
							map.put(key, ((Timestamp) obj).getTime());
						}
					}
					newMap.add(map);
				}
			}
			maps.clear();
		}
		String json_str = "";
		if (newMap.size() > 0) {
			try {
				Object obj = doBySQL(sql);
				if (obj != null) {
					try {
						List<Map> list = SerializerUtil.deserializeArray(obj,Map.class);
						if (list != null) {
							json_str = JsonUtil.list2Json(list);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				log.error("",e);
			}
		}
		return "".equals(json_str) ? "[]" : json_str;
	}
	
	@SuppressWarnings({ "rawtypes" })
	public int getCount(String sql) throws Exception {
		Object obj = doBySQL(sql);
		if(obj==null){
			return 0;
		}
		List<HashMap> result = SerializerUtil.deserializeArray(obj, HashMap.class);
		return ((Number) result.get(0).get("COUNT")).intValue();
	}
	
}
