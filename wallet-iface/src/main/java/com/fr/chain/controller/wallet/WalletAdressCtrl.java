package com.fr.chain.controller.wallet;

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
import com.fr.chain.ewallet.db.mapper.WalletAdressMapper;
import com.fr.chain.ewallet.db.entity.WalletAdress;
import com.fr.chain.ewallet.db.entity.WalletAdressExample;
import com.fr.chain.ewallet.db.entity.WalletAdressKey;
import com.fr.chain.facadeservice.wallet.WalletService;
import com.fr.chain.common.HttpRequestHelper;
import com.fr.chain.db.service.DataService;
import com.fr.chain.web.action.BasicCtrl;

@Slf4j
@Controller
@RequestMapping("/walletadress")
public class WalletAdressCtrl extends BasicCtrl {
	@Resource 
	private HttpRequestHelper httpRequestHelper;
	@Resource
	private WalletService walletService;
	@Resource
	private ProcessWalletAdressMsg processWalletAdressMsg;
	
	private static DataService mysqlDataService = 
			(DataService)BeanFactory.getBean("mysqlDataService");
	
	/**
	 * 获取钱包地址
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/v1_00/getwalletadress", method = RequestMethod.POST)
	@ResponseBody
	public Object getWalletAdress(HttpServletRequest req, HttpServletResponse resp)  {
		String jsontxt = httpRequestHelper.getJsonTxt(req);
		Message inMsg=null;
		try {
			inMsg = MessageBuilder.buildMessage(jsontxt);
			Object obj = processWalletAdressMsg.processGetWallet(inMsg);
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
	 * url:'http://ip/rest/walletadress' 
	 * data:'{"key1":"value1","key2":"value2",...}' 
	 * type:’POST
	 */
	@RequestMapping(value="",method=RequestMethod.POST)
	@ResponseBody
	public ReturnInfo insert(@Valid @RequestBody WalletAdress info,HttpServletRequest req) {
		try {
			walletService.insert(info);
			return ReturnInfo.Success;
		} catch (Exception e) {
			log.warn("WalletAdressCtrl insert error..",e);
//			e.printStackTrace();
		}
		return ReturnInfo.Faild;
	}
	
	/**
	 * ajax精确查询请求 
	 * url: 'http://ip/app/walletadress/?query=({"key1":"value1","key2":"value2",...})' 
	 * dataType: 'json' 
	 * type: 'get'
	 * 
	 * ajax无条件查询全部请求
	 * url: 'http://ip/app/walletadress' 
	 * dataType: 'json' 
	 * type: 'get' 
	 * 
	 * ajax模糊查询请求 
	 * url: 'http://ip/app/walletadress/?query={"(colname)":{"$regex":"(colvalue)","$options":"i"} 
	 * dataType: 'json' 
	 * type: 'get'
	 * 
	 * ajax分页查询
	 * 请求 url: 'http://ip/app/walletadress/?query=(空或{"key1":"value1","key2":"value2",...})&skip=" + beginRow + "&limit=" + rowNum' 
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
			dc.setEntityClass(WalletAdress.class);
			dc.setKeyClass(WalletAdressKey.class);
			dc.setMapperClass(WalletAdressMapper.class);
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
			log.warn("WalletAdressCtrl get error..",e);
		}
		if(para.isPage()){
			return new ListInfo<>(totalCount, list, para);
		}else{
			return list;
		}
	}
	
	/**
	 * ajax根据ID主键查询
	 * 请求 url: 'http://ip/app/walletadress/(_id值)' 
	 * dataType: 'json' 
	 * type: 'get'
	 */
	@RequestMapping(value="/{key}",method=RequestMethod.GET)
	@ResponseBody
	public ListInfo<WalletAdress> get(@PathVariable String key,HttpServletRequest req) {
		int totalCount = 1;
		List<WalletAdress> list = null;
		try {
			WalletAdress akey = new WalletAdress();
				
				Field keyField=FieldUtils.allDeclaredField(WalletAdressKey.class).get(0);

				if(keyField.getType().isInstance(1)){
					FieldUtils.setObjectValue(akey, keyField, Integer.parseInt(key));
				}else if(keyField.getType().isInstance(1L)){
					FieldUtils.setObjectValue(akey, keyField, Long.parseLong(key));
				}else{
					FieldUtils.setObjectValue(akey, keyField, key);
				}
				
			
			if(true && akey.getWalletId() == null ){
				list = new ArrayList<WalletAdress>();
			}else{
				WalletAdressExample example = new WalletAdressExample();
				example.createCriteria().andWalletIdEqualTo(akey.getWalletId());
				list = walletService.selectByExample(example);
			}
			totalCount = list.size();
		} catch (Exception e) {
			log.warn("WalletAdressCtrl get by key error..",e);
		}
		return  new ListInfo<>(totalCount, list, 0, 1);
	}
	
	/**
	 * ajax根据主键删除 
	 * url:'http://ip/app/walletadress/(_id值)' 
	 * type: 'DELETE' 
	 * dataType: 'json' 
	 */
	@RequestMapping(value="/{key}",method=RequestMethod.DELETE)
	@ResponseBody
	public ReturnInfo delete(@PathVariable String key,HttpServletRequest req) {
		try {
			WalletAdress akey = new WalletAdress();
				
				Field keyField=FieldUtils.allDeclaredField(WalletAdressKey.class).get(0);

				if(keyField.getType().isInstance(1)){
					FieldUtils.setObjectValue(akey, keyField, Integer.parseInt(key));
				}else if(keyField.getType().isInstance(1L)){
					FieldUtils.setObjectValue(akey, keyField, Long.parseLong(key));
				}else{
					FieldUtils.setObjectValue(akey, keyField, key);
				}
				
			if(true && akey.getWalletId() != null ){
				walletService.deleteByPrimaryKey(akey);
				return ReturnInfo.Success;
			}
		} catch (Exception e) {
			log.warn("WalletAdressCtrl delete by key error..");
		}
		return ReturnInfo.Faild;
	}
	
	/**
	 * ajax根据主键单条修改 
	 * url:'http://ip/app/walletadress/(_id值)' 
	 * data:'{"key1":"value1","key2":"value2",...}' 
	 * type:'PUT'
	 */
	@RequestMapping(value="/{key}",method=RequestMethod.PUT)
	@ResponseBody
	public ReturnInfo update(@PathVariable String key,@Valid @RequestBody WalletAdress info,HttpServletRequest req) {
		try {
			if(info!=null){
				WalletAdress akey = new WalletAdress();
				
				Field keyField=FieldUtils.allDeclaredField(WalletAdressKey.class).get(0);

				if(keyField.getType().isInstance(1)){
					FieldUtils.setObjectValue(akey, keyField, Integer.parseInt(key));
				}else if(keyField.getType().isInstance(1L)){
					FieldUtils.setObjectValue(akey, keyField, Long.parseLong(key));
				}else{
					FieldUtils.setObjectValue(akey, keyField, key);
				}
				WalletAdressExample example = new WalletAdressExample();
				example.createCriteria().andWalletIdEqualTo(akey.getWalletId());
				walletService.updateByExampleSelective(info, example);
			}
			return ReturnInfo.Success;
		} catch (Exception e) {
			log.warn("WalletAdress update by key error..");
		}
		return ReturnInfo.Faild;
	}
	
	private void setTableName(DbCondi dc){
		String tName = DBBean.getTableName2Class(WalletAdress.class);
		if(dc.getOther() == null){
			Map<String,Object> o = new HashMap<String,Object>();
			o.put(SqlMaker.TABLE_NAME, tName);
			dc.setOther(o);
		}else{
			dc.getOther().put(SqlMaker.TABLE_NAME, tName);
		}
	}
	
	@SuppressWarnings("serial")
	public static class WalletAdresss extends ArrayList<WalletAdress> {  
	    public WalletAdresss() { super(); }  
	}
}