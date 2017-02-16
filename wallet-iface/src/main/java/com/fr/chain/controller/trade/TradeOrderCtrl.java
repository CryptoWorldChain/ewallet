package com.fr.chain.controller.trade;

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
import com.fr.chain.message.Message;
import com.fr.chain.message.MessageBuilder;
import com.fr.chain.message.MessageException;
import com.fr.chain.trade.db.mapper.TradeOrderMapper;
import com.fr.chain.trade.db.entity.TradeOrder;
import com.fr.chain.trade.db.entity.TradeOrderExample;
import com.fr.chain.trade.db.entity.TradeOrderKey;
import com.fr.chain.common.HttpRequestHelper;
import com.fr.chain.db.service.DataService;
import com.fr.chain.facadeservice.property.PropertyService;
import com.fr.chain.facadeservice.trade.TradeOrderService;
import com.fr.chain.web.action.BasicCtrl;

@Slf4j
@Controller
@RequestMapping("/tradeorder")
public class TradeOrderCtrl extends BasicCtrl {
	@Resource 
	private HttpRequestHelper httpRequestHelper;
	@Resource
	private ProcessTradeMsg processTradeMsg;
	@Resource
	private TradeOrderService tradeOrderService;
	@Resource
	private PropertyService propertyService;	
	
	private static DataService mysqlDataService = (DataService) BeanFactory
			.getBean("mysqlDataService");	
	
	
	/**
	 * 查询订单流水
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/v1_00/querytradeOrder", method = RequestMethod.POST)
	@ResponseBody
	public Object queryTradeOrder(HttpServletRequest req, HttpServletResponse resp)  {
		String jsontxt = httpRequestHelper.getJsonTxt(req);
		Message inMsg=null;
		try {
			inMsg = MessageBuilder.buildMessage(jsontxt);		
			Object obj=processTradeMsg.processQueryTradeOrder(inMsg);
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
	 * 发送资产
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/v1_00/sendproperty", method = RequestMethod.POST)
	@ResponseBody
	public Object sendProperty(HttpServletRequest req, HttpServletResponse resp)  {
		String jsontxt = httpRequestHelper.getJsonTxt(req);
		Message inMsg=null;
		try {
			inMsg = MessageBuilder.buildMessage(jsontxt);			
			Object obj=processTradeMsg.processSendProperty(inMsg);
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
	 * 领取资产
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/v1_00/getproperty", method = RequestMethod.POST)
	@ResponseBody
	public Object getProperty(HttpServletRequest req, HttpServletResponse resp)  {
		String jsontxt = httpRequestHelper.getJsonTxt(req);
		Message inMsg=null;
		try {
			inMsg = MessageBuilder.buildMessage(jsontxt);		
			Object obj=processTradeMsg.processGetProperty(inMsg);
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
	 * 消费或者丢弃
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/v1_00/changeproperty", method = RequestMethod.POST)
	@ResponseBody
	public Object changeProperty(HttpServletRequest req, HttpServletResponse resp)  {
		String jsontxt = httpRequestHelper.getJsonTxt(req);
		Message inMsg=null;
		try {
			inMsg = MessageBuilder.buildMessage(jsontxt);			
			Object obj=processTradeMsg.processChangeProperty(inMsg);
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
	 * url:'http://ip/rest/tradeorder' 
	 * data:'{"key1":"value1","key2":"value2",...}' 
	 * type:’POST
	 */
	@RequestMapping(value="",method=RequestMethod.POST)
	@ResponseBody
	public ReturnInfo insert(@Valid @RequestBody TradeOrder info,HttpServletRequest req) {
		try {
			tradeOrderService.insert(info);
			return ReturnInfo.Success;
		} catch (Exception e) {
			log.warn("TradeOrderCtrl insert error..",e);
//			e.printStackTrace();
		}
		return ReturnInfo.Faild;
	}
	
	/**
	 * ajax精确查询请求 
	 * url: 'http://ip/app/tradeorder/?query=({"key1":"value1","key2":"value2",...})' 
	 * dataType: 'json' 
	 * type: 'get'
	 * 
	 * ajax无条件查询全部请求
	 * url: 'http://ip/app/tradeorder' 
	 * dataType: 'json' 
	 * type: 'get' 
	 * 
	 * ajax模糊查询请求 
	 * url: 'http://ip/app/tradeorder/?query={"(colname)":{"$regex":"(colvalue)","$options":"i"} 
	 * dataType: 'json' 
	 * type: 'get'
	 * 
	 * ajax分页查询
	 * 请求 url: 'http://ip/app/tradeorder/?query=(空或{"key1":"value1","key2":"value2",...})&skip=" + beginRow + "&limit=" + rowNum' 
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
			dc.setEntityClass(TradeOrder.class);
			dc.setKeyClass(TradeOrderKey.class);
			dc.setMapperClass(TradeOrderMapper.class);
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
			log.warn("TradeOrderCtrl get error..",e);
		}
		if(para.isPage()){
			return new ListInfo<>(totalCount, list, para);
		}else{
			return list;
		}
	}
	
	/**
	 * ajax根据ID主键查询
	 * 请求 url: 'http://ip/app/tradeorder/(_id值)' 
	 * dataType: 'json' 
	 * type: 'get'
	 */
	@RequestMapping(value="/{key}",method=RequestMethod.GET)
	@ResponseBody
	public ListInfo<TradeOrder> get(@PathVariable String key,HttpServletRequest req) {
		int totalCount = 1;
		List<TradeOrder> list = null;
		try {
			TradeOrder akey = new TradeOrder();
				
				Field keyField=FieldUtils.allDeclaredField(TradeOrderKey.class).get(0);

				if(keyField.getType().isInstance(1)){
					FieldUtils.setObjectValue(akey, keyField, Integer.parseInt(key));
				}else if(keyField.getType().isInstance(1L)){
					FieldUtils.setObjectValue(akey, keyField, Long.parseLong(key));
				}else{
					FieldUtils.setObjectValue(akey, keyField, key);
				}
				
			
			if(true && akey.getOrderId() == null ){
				list = new ArrayList<TradeOrder>();
			}else{
				TradeOrderExample example = new TradeOrderExample();
				example.createCriteria().andOrderIdEqualTo(akey.getOrderId());
				list = tradeOrderService.selectByExample(example);
			}
			totalCount = list.size();
		} catch (Exception e) {
			log.warn("TradeOrderCtrl get by key error..",e);
		}
		return  new ListInfo<>(totalCount, list, 0, 1);
	}
	
	/**
	 * ajax根据主键删除 
	 * url:'http://ip/app/tradeorder/(_id值)' 
	 * type: 'DELETE' 
	 * dataType: 'json' 
	 */
	@RequestMapping(value="/{key}",method=RequestMethod.DELETE)
	@ResponseBody
	public ReturnInfo delete(@PathVariable String key,HttpServletRequest req) {
		try {
			TradeOrder akey = new TradeOrder();
				
				Field keyField=FieldUtils.allDeclaredField(TradeOrderKey.class).get(0);

				if(keyField.getType().isInstance(1)){
					FieldUtils.setObjectValue(akey, keyField, Integer.parseInt(key));
				}else if(keyField.getType().isInstance(1L)){
					FieldUtils.setObjectValue(akey, keyField, Long.parseLong(key));
				}else{
					FieldUtils.setObjectValue(akey, keyField, key);
				}
				
			if(true && akey.getOrderId() != null ){
				tradeOrderService.deleteByPrimaryKey(akey);
				return ReturnInfo.Success;
			}
		} catch (Exception e) {
			log.warn("TradeOrderCtrl delete by key error..");
		}
		return ReturnInfo.Faild;
	}
	
	/**
	 * ajax根据主键单条修改 
	 * url:'http://ip/app/tradeorder/(_id值)' 
	 * data:'{"key1":"value1","key2":"value2",...}' 
	 * type:'PUT'
	 */
	@RequestMapping(value="/{key}",method=RequestMethod.PUT)
	@ResponseBody
	public ReturnInfo update(@PathVariable String key,@Valid @RequestBody TradeOrder info,HttpServletRequest req) {
		try {
			if(info!=null){
				TradeOrder akey = new TradeOrder();
				
				Field keyField=FieldUtils.allDeclaredField(TradeOrderKey.class).get(0);

				if(keyField.getType().isInstance(1)){
					FieldUtils.setObjectValue(akey, keyField, Integer.parseInt(key));
				}else if(keyField.getType().isInstance(1L)){
					FieldUtils.setObjectValue(akey, keyField, Long.parseLong(key));
				}else{
					FieldUtils.setObjectValue(akey, keyField, key);
				}
				TradeOrderExample example = new TradeOrderExample();
				example.createCriteria().andOrderIdEqualTo(akey.getOrderId());
				tradeOrderService.updateByExampleSelective(info, example);
			}
			return ReturnInfo.Success;
		} catch (Exception e) {
			log.warn("TradeOrder update by key error..");
		}
		return ReturnInfo.Faild;
	}
	
	private void setTableName(DbCondi dc){
		String tName = DBBean.getTableName2Class(TradeOrder.class);
		if(dc.getOther() == null){
			Map<String,Object> o = new HashMap<String,Object>();
			o.put(SqlMaker.TABLE_NAME, tName);
			dc.setOther(o);
		}else{
			dc.getOther().put(SqlMaker.TABLE_NAME, tName);
		}
	}
	
	@SuppressWarnings("serial")
	public static class TradeOrders extends ArrayList<TradeOrder> {  
	    public TradeOrders() { super(); }  
	}
}