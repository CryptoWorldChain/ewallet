package com.fr.chain.controller.property;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fr.chain.message.Message;
import com.fr.chain.message.MessageBuilder;
import com.fr.chain.message.MessageException;
import com.fr.chain.property.db.entity.Property;
import com.fr.chain.property.db.entity.PropertyExample;
import com.fr.chain.property.db.entity.PropertyKey;
import com.fr.chain.property.db.mapper.PropertyMapper;
import com.fr.chain.utils.BeanFactory;
import com.fr.chain.utils.DBBean;
import com.fr.chain.utils.JsonUtil;
import com.fr.chain.utils.SerializerUtil;
import com.fr.chain.web.bean.DbCondi;
import com.fr.chain.web.bean.FieldsMapperBean;
import com.fr.chain.web.bean.ListInfo;
import com.fr.chain.web.bean.PageInfo;
import com.fr.chain.web.bean.QueryMapperBean;
import com.fr.chain.web.bean.ReturnInfo;
import com.fr.chain.web.bean.SqlMaker;
import com.fr.chain.web.bind.FieldUtils;
import com.fr.chain.web.bind.KeyExplainHandler;
import com.fr.chain.web.bind.RequestJsonParam;
import com.fr.chain.common.HttpRequestHelper;
import com.fr.chain.db.service.DataService;
import com.fr.chain.facadeservice.property.PropertyService;
import com.fr.chain.web.action.BasicCtrl;

@Slf4j
@Controller
@RequestMapping("/property")
public class PropertyCtrl extends BasicCtrl {
	@Resource
	HttpRequestHelper httpRequestHelper;
	@Resource
	ProcessPropertyMsg ProcessPropertyMsg;
	@Resource
	PropertyService propertyService;
	
	private static DataService mysqlDataService = 
			(DataService)BeanFactory.getBean("mysqlDataService");
	
	/**
	 * 创建资产（数字资产无法创建资产，通过购买或转入添加）
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/v1_00/createproperty", method = RequestMethod.POST)
	@ResponseBody
	public Object createProperty(HttpServletRequest req, HttpServletResponse resp)  {
		String jsontxt = httpRequestHelper.getJsonTxt(req);	      
		Message inMsg=null;
		try {
			inMsg = MessageBuilder.buildMessage(jsontxt);
			Object obj=ProcessPropertyMsg.processCreateProperty(inMsg);
			try{
				log.debug("[RESP]:"+JsonUtil.bean2Json(obj));
			}catch(Exception e){
				log.error("message Resp Error:", e);
			}
			return obj;			
		} catch (MessageException je) {
			log.warn("Message error", je);			
			return new ReturnInfo(je.getMessage(), 0, null,false);
		} catch (Exception e) {
			log.error("unknow error", e);			
			return new ReturnInfo(e.getMessage(), 0, null,false);
		}
	}
	
	@RequestMapping(value = "/v1_00/queryproperty", method = RequestMethod.POST)
	@ResponseBody
	public Object queryProperty(HttpServletRequest req, HttpServletResponse resp)  {
		String jsontxt = httpRequestHelper.getJsonTxt(req);	      
		Message inMsg=null;
		try {
			inMsg = MessageBuilder.buildMessage(jsontxt);
			Object obj=ProcessPropertyMsg.processQueryProperty(inMsg);
			try{
				log.debug("[RESP]:"+JsonUtil.bean2Json(obj));
			}catch(Exception e){
				log.error("message Resp Error:", e);
			}
			return obj;			
		} catch (MessageException je) {
			log.warn("Message error", je);			
			return new ReturnInfo(je.getMessage(), 0, null,false);
		} catch (Exception e) {
			log.error("unknow error", e);			
			return new ReturnInfo(e.getMessage(), 0, null,false);
		}
	}
	
	
	
	/**
	 * ajax单条数据插入 
	 * url:'http://ip/rest/property' 
	 * data:'{"key1":"value1","key2":"value2",...}' 
	 * type:’POST
	 */
	@RequestMapping(value="",method=RequestMethod.POST)
	@ResponseBody
	public ReturnInfo insert(@Valid @RequestBody Property info,HttpServletRequest req) {
		try {
			propertyService.insert(info);
			return ReturnInfo.Success;
		} catch (Exception e) {
			log.warn("PropertyCtrl insert error..",e);
//			e.printStackTrace();
		}
		return ReturnInfo.Faild;
	}
	
	/**
	 * ajax精确查询请求 
	 * url: 'http://ip/app/property/?query=({"key1":"value1","key2":"value2",...})' 
	 * dataType: 'json' 
	 * type: 'get'
	 * 
	 * ajax无条件查询全部请求
	 * url: 'http://ip/app/property' 
	 * dataType: 'json' 
	 * type: 'get' 
	 * 
	 * ajax模糊查询请求 
	 * url: 'http://ip/app/property/?query={"(colname)":{"$regex":"(colvalue)","$options":"i"} 
	 * dataType: 'json' 
	 * type: 'get'
	 * 
	 * ajax分页查询
	 * 请求 url: 'http://ip/app/property/?query=(空或{"key1":"value1","key2":"value2",...})&skip=" + beginRow + "&limit=" + rowNum' 
	 * dataType:'json' 
	 * type:'get' 
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public Object get(@RequestJsonParam(value = "query",required=false) QueryMapperBean info,
			@RequestJsonParam(value = "fields",required=false) FieldsMapperBean fmb,
			PageInfo para, HttpServletRequest req) {
		int totalCount = 0;
		List<HashMap> list = null;
		try {
			DbCondi dc = new DbCondi();
			dc.setEntityClass(Property.class);
			dc.setKeyClass(PropertyKey.class);
			dc.setMapperClass(PropertyMapper.class);
			dc.setQmb(info);
			dc.setPageinfo(para);
			dc.setFmb(fmb);
			this.setTableName(dc);
			// TEST  query
//			if(info!=null){
//				StringBuffer keyValues = new StringBuffer();
//				for(EqualBean b : info.getEqual()){
//					keyValues.append(b.getFieldName()).append(" = ").append(b.getValue()).append(",");
//				}
//				for(LikeBean b : info.getLikes()){
//					keyValues.append(b.getFieldName()).append(" like ").append(b.getRegex()).append(",");
//				}
//				for(OrBean b : info.getOr()){
//					keyValues.append(b.getFieldName()).append(" or ").append(b.getValue()).append(",");
//				}
//				for(ConditionBean b : info.getCondition()){
//					keyValues.append(b.getFieldName()).append(" ").append(b.getCondi())
//					.append(" ").append(b.getValue()).append(",");
//				}
//				log.debug("传递参数为="+keyValues.toString());
//			}
			////////////////////
			String sql = SqlMaker.getReferenceCountSql(dc);
			totalCount = mysqlDataService.getCount(sql);
			sql = SqlMaker.getReferenceData(dc); 
			list = SerializerUtil.deserializeArray(mysqlDataService.doBySQL(sql), HashMap.class);
			for(HashMap map : list){
				for(Field filed:FieldUtils.allDeclaredField(dc.getKeyClass())){
					if(map.get(filed.getName())==null){
						map.put(filed.getName(), "");
					}
				}
				map.put(KeyExplainHandler.ID_KEY, KeyExplainHandler.genKey(map, dc.getKeyClass()));
			}
		} catch (Exception e) {
			log.warn("PropertyCtrl get error..",e);
		}
		if(para.isPage()){
			return new ListInfo<>(totalCount, list, para);
		}else{
			return list;
		}
	}
	
	/**
	 * ajax根据ID主键查询
	 * 请求 url: 'http://ip/app/property/(_id值)' 
	 * dataType: 'json' 
	 * type: 'get'
	 */
	@RequestMapping(value="/{key}",method=RequestMethod.GET)
	@ResponseBody
	public ListInfo<Property> get(@PathVariable String key,HttpServletRequest req) {
		int totalCount = 1;
		List<Property> list = null;
		try {
			Property akey = new Property();
				
				Field keyField=FieldUtils.allDeclaredField(PropertyKey.class).get(0);

				if(keyField.getType().isInstance(1)){
					FieldUtils.setObjectValue(akey, keyField, Integer.parseInt(key));
				}else if(keyField.getType().isInstance(1L)){
					FieldUtils.setObjectValue(akey, keyField, Long.parseLong(key));
				}else{
					FieldUtils.setObjectValue(akey, keyField, key);
				}
				
			
			if(true && akey.getPropertyId() == null ){
				list = new ArrayList<Property>();
			}else{
				PropertyExample example = new PropertyExample();
				example.createCriteria().andPropertyIdEqualTo(akey.getPropertyId());
				list = propertyService.selectByExample(example);
			}
			totalCount = list.size();
		} catch (Exception e) {
			log.warn("PropertyCtrl get by key error..",e);
		}
		return  new ListInfo<>(totalCount, list, 0, 1);
	}
	
	/**
	 * ajax根据主键删除 
	 * url:'http://ip/app/property/(_id值)' 
	 * type: 'DELETE' 
	 * dataType: 'json' 
	 */
	@RequestMapping(value="/{key}",method=RequestMethod.DELETE)
	@ResponseBody
	public ReturnInfo delete(@PathVariable String key,HttpServletRequest req) {
		try {
			Property akey = new Property();
				
				Field keyField=FieldUtils.allDeclaredField(PropertyKey.class).get(0);

				if(keyField.getType().isInstance(1)){
					FieldUtils.setObjectValue(akey, keyField, Integer.parseInt(key));
				}else if(keyField.getType().isInstance(1L)){
					FieldUtils.setObjectValue(akey, keyField, Long.parseLong(key));
				}else{
					FieldUtils.setObjectValue(akey, keyField, key);
				}
				
			if(true && akey.getPropertyId() != null ){
				propertyService.deleteByPrimaryKey(akey);
				return ReturnInfo.Success;
			}
		} catch (Exception e) {
			log.warn("PropertyCtrl delete by key error..");
		}
		return ReturnInfo.Faild;
	}
	
	/**
	 * ajax根据主键单条修改 
	 * url:'http://ip/app/property/(_id值)' 
	 * data:'{"key1":"value1","key2":"value2",...}' 
	 * type:'PUT'
	 */
	@RequestMapping(value="/{key}",method=RequestMethod.PUT)
	@ResponseBody
	public ReturnInfo update(@PathVariable String key,@Valid @RequestBody Property info,HttpServletRequest req) {
		try {
			if(info!=null){
				Property akey = new Property();
				
				Field keyField=FieldUtils.allDeclaredField(PropertyKey.class).get(0);

				if(keyField.getType().isInstance(1)){
					FieldUtils.setObjectValue(akey, keyField, Integer.parseInt(key));
				}else if(keyField.getType().isInstance(1L)){
					FieldUtils.setObjectValue(akey, keyField, Long.parseLong(key));
				}else{
					FieldUtils.setObjectValue(akey, keyField, key);
				}
				PropertyExample example = new PropertyExample();
				example.createCriteria().andPropertyIdEqualTo(akey.getPropertyId());
				propertyService.updateByExampleSelective(info, example);
			}
			return ReturnInfo.Success;
		} catch (Exception e) {
			log.warn("Property update by key error..");
		}
		return ReturnInfo.Faild;
	}
	
	private void setTableName(DbCondi dc){
		String tName = DBBean.getTableName2Class(Property.class);
		if(dc.getOther() == null){
			Map<String,Object> o = new HashMap<String,Object>();
			o.put(SqlMaker.TABLE_NAME, tName);
			dc.setOther(o);
		}else{
			dc.getOther().put(SqlMaker.TABLE_NAME, tName);
		}
	}
	
	
	@SuppressWarnings("serial")
	public static class Propertys extends ArrayList<Property> {  
	    public Propertys() { super(); }  
	}
}