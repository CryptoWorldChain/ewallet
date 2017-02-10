package com.fr.chain.facadeservice.trade.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.lang.IllegalArgumentException;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.codehaus.jackson.JsonNode;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.fr.chain.vo.property.CreatePropertyVo;
import com.fr.chain.vo.property.QueryPropertyVo;
import com.fr.chain.vo.property.Res_CreatePropertyVo;
import com.fr.chain.vo.property.Res_QueryPropertyVo;
import com.fr.chain.vo.trade.ChangePropertyVo;
import com.fr.chain.vo.trade.GetPropertyVo;
import com.fr.chain.vo.trade.QueryTradeOrderVo;
import com.fr.chain.vo.trade.Res_QueryTradeOrderVo;
import com.fr.chain.vo.trade.Res_SendPropertyVo;
import com.fr.chain.vo.trade.SendPropertyVo;
import com.fr.chain.enums.BaseStatusEnum;
import com.fr.chain.facadeservice.property.PropertyService;
import com.fr.chain.facadeservice.trade.TradeOrderService;
import com.fr.chain.message.Message;
import com.fr.chain.message.MsgBody;
import com.fr.chain.message.ResponseMsg;
import com.fr.chain.property.db.entity.Property;
import com.fr.chain.property.db.entity.PropertyExample;
import com.fr.chain.property.db.entity.PropertyKey;
import com.fr.chain.property.service.CreatePropertyService;
import com.fr.chain.property.service.DelPropertyService;
import com.fr.chain.property.service.QueryPropertyService;
import com.fr.chain.property.service.UpdatePropertyService;
import com.fr.chain.trade.db.entity.TradeOrder;
import com.fr.chain.trade.db.entity.TradeOrderExample;
import com.fr.chain.trade.db.entity.TradeOrderKey;
import com.fr.chain.trade.service.CreateTradeOrderService;
import com.fr.chain.trade.service.DelTradeOrderService;
import com.fr.chain.trade.service.QueryTradeOrderService;
import com.fr.chain.trade.service.UpdateTradeOrderService;
import com.fr.chain.utils.DateUtil;
import com.fr.chain.utils.IDGenerator;
import com.fr.chain.utils.NumberUtil;
import com.fr.chain.utils.StringUtil;
import com.fr.chain.utils.JsonUtil;

@Slf4j
@Service("tradeOrderService")
public class TradeOrderServiceImpl implements TradeOrderService {
	@Resource
	QueryPropertyService queryPropertyService;
	@Resource
	DelPropertyService delPropertyService;
	@Resource
	UpdatePropertyService updatePropertyService;
	@Resource
	CreatePropertyService createPropertyService;
	@Resource
	CreateTradeOrderService createTradeOrderService;
	@Resource
	QueryTradeOrderService queryTradeOrderService;
	@Resource
	DelTradeOrderService delTradeOrderService;
	@Resource
	UpdateTradeOrderService updateTradeOrderService;
	
	private void buildQueryTradeOrderBody(Message<QueryTradeOrderVo> gpmsg) {
		List<QueryTradeOrderVo> bodys = new ArrayList<>();
		for (JsonNode node : gpmsg.getAnDatas()) {
			bodys.add(JsonUtil.json2Bean(node, QueryTradeOrderVo.class));
		} 
		gpmsg.setBodyDatas(bodys);
	}
	
	private void buildSendPropertyBody(Message<SendPropertyVo> gpmsg) {
		List<SendPropertyVo> bodys = new ArrayList<>();
		for (JsonNode node : gpmsg.getAnDatas()) {
			bodys.add(JsonUtil.json2Bean(node, SendPropertyVo.class));
		} 
		gpmsg.setBodyDatas(bodys);
	}
	
	private void buildGetPropertyBody(Message<GetPropertyVo> gpmsg) {
		List<GetPropertyVo> bodys = new ArrayList<>();
		for (JsonNode node : gpmsg.getAnDatas()) {
			bodys.add(JsonUtil.json2Bean(node, GetPropertyVo.class));
		} 
		gpmsg.setBodyDatas(bodys);
	}
	
	private void buildChangePropertyBody(Message<ChangePropertyVo> gpmsg) {
		List<ChangePropertyVo> bodys = new ArrayList<>();
		for (JsonNode node : gpmsg.getAnDatas()) {
			bodys.add(JsonUtil.json2Bean(node, ChangePropertyVo.class));
		} 
		gpmsg.setBodyDatas(bodys);
	}
	
	@Override
	public Message<MsgBody> processQueryTradeOrder(Message<QueryTradeOrderVo> msg){
		buildQueryTradeOrderBody(msg);
		log.debug("Create property msg:" + msg);
		Map<String,MsgBody> resp = new LinkedHashMap<String, MsgBody>();
		try {
			List<QueryTradeOrderVo> datas = msg.getBodyDatas();
			if (datas != null && datas.size() > 0) {
				for (QueryTradeOrderVo msgVo : datas) {
					try{					
						this.validNull(msgVo);
						TradeOrder tradeOrder= new TradeOrder();
						tradeOrder.setMerchantId(msg.getMerchantid());
						tradeOrder.setOpenId(msg.getOpenid()); 
						tradeOrder.setAppId(msg.getAppid());	
						tradeOrder.setProductId(msgVo.getProductid());
						tradeOrder.setPropertyType(msgVo.getPropertytype());						
						tradeOrder.setProductId(msgVo.getProductid());
						tradeOrder.setIsDigit(msgVo.getIsselfsupport());	
						tradeOrder.setIsSelfSupport(msgVo.getIsselfsupport());
						tradeOrder.setSigntype(msgVo.getSigntype());
						List<TradeOrder> tradeOrderList = queryTradeOrderService.selectByExample(tradeOrder);
						//创建返回报文
						Res_QueryTradeOrderVo res_QueryTradeOrderVo = new Res_QueryTradeOrderVo(msgVo.getDatano());
						if(tradeOrderList != null && tradeOrderList.size()>0){ 
							TradeOrder tmpTradeOrder = tradeOrderList.get(0);
							BeanUtils.copyProperties(res_QueryTradeOrderVo, tmpTradeOrder); //拷贝
						}
						//设置返回报文
						resp.put(msgVo.getDatano(), res_QueryTradeOrderVo);
					}catch (NullPointerException ne) {
						log.error("Create Account is failed:" + ne.getMessage(), ne);
						resp.put(msgVo.getDatano(), new ResponseMsg(msgVo.getDatano(),BaseStatusEnum.失败.getCode().toString(),ne.getMessage()));
					}
					catch (IllegalArgumentException ile) {
						log.error("Create Account is failed:" + ile.getMessage(), ile);
						resp.put(msgVo.getDatano(), new ResponseMsg(msgVo.getDatano(),BaseStatusEnum.失败.getCode().toString(),ile.getMessage()));
					}
					catch (Exception e) {
						log.error("Create Account is failed:" + e.getMessage(), e);
						resp.put(msgVo.getDatano(), new ResponseMsg(msgVo.getDatano(),BaseStatusEnum.失败.getCode().toString(),e.getMessage()));
					}
				}				
			}
		}		
		catch (Exception e) {
			log.error("Create Account is failed:" + e.getMessage(), e);
			Map<String, MsgBody> errResp = new LinkedHashMap<String, MsgBody>();
			for (MsgBody body : msg.getBodyDatas()) {	
				resp.put(body.getDatano(), new ResponseMsg(body.getDatano(),BaseStatusEnum.失败.getCode().toString(), e.getMessage()));
			}
			return msg.asResponse(errResp);			
		}
		
		//回复所有报文
		return msg.asResponse(resp);	
	}
	
	
	@Override
	public Message<MsgBody> processSendProperty(Message<SendPropertyVo> msg){
		buildSendPropertyBody(msg);
		log.debug("Create property msg:" + msg);
		Map<String,MsgBody> resp = new LinkedHashMap<String, MsgBody>();
		try {
			List<SendPropertyVo> datas = msg.getBodyDatas();
			if (datas != null && datas.size() > 0) {
				for (SendPropertyVo msgVo : datas) {
					try{	
						//验证报文信息
						this.validNull(msgVo);
						//构建对应的返回报文
						Res_SendPropertyVo res_SendPropertyVo =new Res_SendPropertyVo(msgVo.getDatano());
						Property property= new Property();
						property.setMerchantId(msg.getMerchantid());
						property.setOpenId(msg.getOpenid()); 
						property.setAppId(msg.getAppid());	
						List<SendPropertyVo.PackageData> packageDataList  =  msgVo.getData();
						for(SendPropertyVo.PackageData packageData :packageDataList){
							property.setProductId(packageData.getProductid());
							Property tmpProperty = queryPropertyService.selectOneByExample(property);
							int diff = NumberUtil.toInt(tmpProperty.getCount()) - NumberUtil.toInt(packageData.getCount()); 
							if(diff < 0){
								String errMsg = String.format("productId:%s,count:%s is to big",packageData.getProductid(), packageData.getCount());
								throw new IllegalArgumentException(errMsg);
							}else if(diff == 0){
								//送出全部资产挂载到系统账户中,删除送出人资产
								delPropertyService.deleteByPrimaryKey(tmpProperty);
							}else{
							    //送出部分资产
								tmpProperty.setCount(String.valueOf(diff));
								updatePropertyService.updateByPrimaryKey(tmpProperty);
							}
							
							//增加系统资产
							tmpProperty.setCount(packageData.getCount());
							tmpProperty.setOpenId("OpenId_sys");
							tmpProperty.setCreateTime(DateUtil.getSystemDate());
							String address= "chainAdress";//调用底层生成区块链地址
							tmpProperty.setAddress(address);
							createPropertyService.insert(tmpProperty);
							
							
							//创建订单
							TradeOrder tradeOrder = new TradeOrder();
							BeanUtils.copyProperties(tmpProperty, tradeOrder);
							tradeOrder.setOpenId(msg.getOpenid());
							tradeOrder.setTradeType("send");
							tradeOrder.setCreateTime(DateUtil.getSystemDate());
							createTradeOrderService.insert(tradeOrder);	
							
							//系统订单
							tradeOrder.setOpenId("OpenId_sys");
							tradeOrder.setTradeType("create");						
							createTradeOrderService.insert(tradeOrder);
							
							//组装返回报文
							Res_SendPropertyVo.PackageData resPackageData = new Res_SendPropertyVo.PackageData();
							BeanUtils.copyProperties(resPackageData, packageData);
							resPackageData.setAddress(address);
							res_SendPropertyVo.getData().add(resPackageData);							
						}
						resp.put(msgVo.getDatano(), res_SendPropertyVo);
					}catch (NullPointerException ne) {
						log.error("Create Account is failed:" + ne.getMessage(), ne);
						resp.put(msgVo.getDatano(), new ResponseMsg(msgVo.getDatano(),BaseStatusEnum.失败.getCode().toString(),ne.getMessage()));
					}
					catch (IllegalArgumentException ile) {
						log.error("Create Account is failed:" + ile.getMessage(), ile);
						resp.put(msgVo.getDatano(), new ResponseMsg(msgVo.getDatano(),BaseStatusEnum.失败.getCode().toString(),ile.getMessage()));
					}
					catch (Exception e) {
						log.error("Create Account is failed:" + e.getMessage(), e);
						resp.put(msgVo.getDatano(), new ResponseMsg(msgVo.getDatano(),BaseStatusEnum.失败.getCode().toString(),e.getMessage()));
					}
				}
			}
		}		
		catch (Exception e) {
			log.error("Create Account is failed:" + e.getMessage(), e);
			Map<String, MsgBody> errResp = new LinkedHashMap<String, MsgBody>();
			for (MsgBody body : msg.getBodyDatas()) {	
				resp.put(body.getDatano(), new ResponseMsg(body.getDatano(),BaseStatusEnum.失败.getCode().toString(), e.getMessage()));
			}
			return msg.asResponse(errResp);			
		}
		
		//回复所有报文
		return msg.asResponse(resp);
	}
	
	
	
	public Message<MsgBody> processGetProperty(Message<GetPropertyVo> msg){
		buildGetPropertyBody(msg);
		log.debug("Create property msg:" + msg);
		Map<String,MsgBody> resp = new LinkedHashMap<String, MsgBody>();
		try {
			List<GetPropertyVo> datas = msg.getBodyDatas();
			if (datas != null && datas.size() > 0) {
				List<Property> propertys = new ArrayList<Property>();				
				for (GetPropertyVo msgVo : datas) {
					try{					
						//验证报文信息
						this.validNull(msgVo);						
						Property property= new Property();
						property.setMerchantId(msg.getMerchantid());
						property.setAppId(msg.getAppid());	
						property.setProductId(msgVo.getProductid());
						property.setAddress(msgVo.getAddress());
						Property tmpProperty = queryPropertyService.selectOneByExample(property);
						int diff = NumberUtil.toInt(tmpProperty.getCount()) - NumberUtil.toInt(msgVo.getCount()); 
						if(diff != 0){
							String errMsg = String.format("productId:%s,the count:%s is error",msgVo.getProductid(), msgVo.getCount());
							throw new IllegalArgumentException(errMsg);
						}
						//领取资产,只变更用户,区块链地址不变
						tmpProperty.setOpenId(msg.getOpenid());	
						tmpProperty.setCreateTime(DateUtil.getSystemDate());
						updatePropertyService.updateByPrimaryKey(tmpProperty);
						
						//创建订单
						TradeOrder tradeOrder = new TradeOrder();
						BeanUtils.copyProperties(tmpProperty, tradeOrder);
						tradeOrder.setOpenId(msg.getOpenid());
						tradeOrder.setTradeType("recv");
						tradeOrder.setCreateTime(DateUtil.getSystemDate());
						createTradeOrderService.insert(tradeOrder);	
						
						//系统订单
						tradeOrder.setOpenId("OpenId_sys");
						tradeOrder.setTradeType("recv");						
						createTradeOrderService.insert(tradeOrder);
						
						//组装返回报文
					    ResponseMsg responseMsg =new ResponseMsg(msgVo.getDatano());
						resp.put(msgVo.getDatano(), responseMsg);
					}catch (NullPointerException ne) {
						log.error("Create Account is failed:" + ne.getMessage(), ne);
						resp.put(msgVo.getDatano(), new ResponseMsg(msgVo.getDatano(),BaseStatusEnum.失败.getCode().toString(),ne.getMessage()));
					}
					catch (IllegalArgumentException ile) {
						log.error("Create Account is failed:" + ile.getMessage(), ile);
						resp.put(msgVo.getDatano(), new ResponseMsg(msgVo.getDatano(),BaseStatusEnum.失败.getCode().toString(),ile.getMessage()));
					}
					catch (Exception e) {
						log.error("Create Account is failed:" + e.getMessage(), e);
						resp.put(msgVo.getDatano(), new ResponseMsg(msgVo.getDatano(),BaseStatusEnum.失败.getCode().toString(),e.getMessage()));
					}
				}
				
				//批量入库
				createPropertyService.batchInsert(propertys);
			}
		}		
		catch (Exception e) {
			log.error("Create Account is failed:" + e.getMessage(), e);
			Map<String, MsgBody> errResp = new LinkedHashMap<String, MsgBody>();
			for (MsgBody body : msg.getBodyDatas()) {	
				resp.put(body.getDatano(), new ResponseMsg(body.getDatano(),BaseStatusEnum.失败.getCode().toString(), e.getMessage()));
			}
			return msg.asResponse(errResp);			
		}
		
		//回复所有报文
		return msg.asResponse(resp);
	
	}

	
	public Message<MsgBody> processChangeProperty(Message<ChangePropertyVo> msg){
		buildChangePropertyBody(msg);
		log.debug("Change property msg:" + msg);
		Map<String,MsgBody> resp = new LinkedHashMap<String, MsgBody>();
		try {
			List<ChangePropertyVo> datas = msg.getBodyDatas();
			if (datas != null && datas.size() > 0) {
				List<Property> propertys = new ArrayList<Property>();				
				for (ChangePropertyVo msgVo : datas) {
					try{					
						this.validNull(msgVo);
						Property property= new Property();
						property.setMerchantId(msg.getMerchantid());
						property.setOpenId(msg.getOpenid()); 
						property.setAppId(msg.getAppid());	
						property.setProductId(msgVo.getProductid());
						Property tmpProperty = queryPropertyService.selectOneByExample(property);
						//丢弃或者使用资产,资产表中直接删除
						delPropertyService.deleteByPrimaryKey(tmpProperty);
						
						//创建订单
						TradeOrder tradeOrder = new TradeOrder();
						BeanUtils.copyProperties(tmpProperty, tradeOrder);
						tradeOrder.setTradeType(msgVo.getTradetype());
						tradeOrder.setCreateTime(DateUtil.getSystemDate());
						createTradeOrderService.insert(tradeOrder);	
						
						//组装返回报文
					    ResponseMsg responseMsg =new ResponseMsg(msgVo.getDatano());
						resp.put(msgVo.getDatano(), responseMsg);
					}catch (NullPointerException ne) {
						log.error("Create Account is failed:" + ne.getMessage(), ne);
						resp.put(msgVo.getDatano(), new ResponseMsg(msgVo.getDatano(),BaseStatusEnum.失败.getCode().toString(),ne.getMessage()));
					}
					catch (IllegalArgumentException ile) {
						log.error("Create Account is failed:" + ile.getMessage(), ile);
						resp.put(msgVo.getDatano(), new ResponseMsg(msgVo.getDatano(),BaseStatusEnum.失败.getCode().toString(),ile.getMessage()));
					}
					catch (Exception e) {
						log.error("Create Account is failed:" + e.getMessage(), e);
						resp.put(msgVo.getDatano(), new ResponseMsg(msgVo.getDatano(),BaseStatusEnum.失败.getCode().toString(),e.getMessage()));
					}
				}
				
				//批量入库
				createPropertyService.batchInsert(propertys);
			}
		}		
		catch (Exception e) {
			log.error("Create Account is failed:" + e.getMessage(), e);
			Map<String, MsgBody> errResp = new LinkedHashMap<String, MsgBody>();
			for (MsgBody body : msg.getBodyDatas()) {	
				resp.put(body.getDatano(), new ResponseMsg(body.getDatano(),BaseStatusEnum.失败.getCode().toString(), e.getMessage()));
			}
			return msg.asResponse(errResp);			
		}
		
		//回复所有报文
		return msg.asResponse(resp);	
	}
	
	
	@Override
    public int insert(TradeOrder info){
    	return createTradeOrderService.insert(info);
    }
	
	@Override
	public int batchInsert(List<TradeOrder> records){
		return createTradeOrderService.batchInsert(records);
	}
	
	@Override
	public int deleteByPrimaryKey (TradeOrderKey key){
		return delTradeOrderService.deleteByPrimaryKey(key);
	}
	
	@Override
	public List<TradeOrder>  selectByExample(TradeOrder info){
		return queryTradeOrderService.selectByExample(info);
	}
	
	@Override
	public List<TradeOrder>  selectByExample(TradeOrderExample info){
		return queryTradeOrderService.selectByExample(info);
	}
	
	@Override
	public int updateByExampleSelective (TradeOrder record, TradeOrderExample example){
		return updateTradeOrderService.updateByExampleSelective(record,example);
	}
	
	


	private void validNull(QueryTradeOrderVo msgVo){
		String error="%s is null or empty";
		if(StringUtil.isBlank(msgVo.getDatano())) throw new NullPointerException(String.format(error,"datano"));
		if(StringUtil.isBlank(msgVo.getPropertytype())) throw new NullPointerException(String.format(error,"propertytype"));
	}
	
	private void validNull(CreatePropertyVo msgVo){
		String error="%s is null or empty";
		if(StringUtil.isBlank(msgVo.getDatano())) throw new NullPointerException(String.format(error,"datano"));
		if(StringUtil.isBlank(msgVo.getPropertytype())) throw new NullPointerException(String.format(error,"propertytype"));
		if(StringUtil.isBlank(msgVo.getIsselfsupport())) throw new NullPointerException(String.format(error,"isselfsupport"));
		if(StringUtil.isBlank(msgVo.getProductid())) throw new NullPointerException(String.format(error,"productid"));
		if(StringUtil.isBlank(msgVo.getIsdigit())) throw new NullPointerException(String.format(error,"isdigit"));
		if(StringUtil.isBlank(msgVo.getSigntype())) throw new NullPointerException(String.format(error,"signtype"));
		if(StringUtil.isBlank(msgVo.getPropertyname())) throw new NullPointerException(String.format(error,"propertyname"));
		if(StringUtil.isBlank(msgVo.getCount())) throw new NullPointerException(String.format(error,"count"));		
	}
	
	private void validNull(SendPropertyVo msgVo){
		String error="%s is null or empty";
		if(StringUtil.isBlank(msgVo.getDatano())) throw new NullPointerException(String.format(error,"datano"));
		if(StringUtil.isBlank(msgVo.getPackageid())) throw new NullPointerException(String.format(error,"packageid"));
		SendPropertyVo.PackageData data = new SendPropertyVo.PackageData();
		if(data==null) throw new NullPointerException(String.format(error,"data"));
		if(StringUtil.isBlank(data.getCount())) throw new NullPointerException(String.format(error,"count"));
		if(StringUtil.isBlank(data.getProductid())) throw new NullPointerException(String.format(error,"productid"));
	}
	
	private void validNull(GetPropertyVo msgVo){
		String error="%s is null or empty";
		if(StringUtil.isBlank(msgVo.getDatano())) throw new NullPointerException(String.format(error,"datano"));
		if(StringUtil.isBlank(msgVo.getProductid())) throw new NullPointerException(String.format(error,"productid"));
		if(StringUtil.isBlank(msgVo.getCount())) throw new NullPointerException(String.format(error,"count"));
		if(StringUtil.isBlank(msgVo.getAddress())) throw new NullPointerException(String.format(error,"address"));
		if(StringUtil.isBlank(msgVo.getOwner())) throw new NullPointerException(String.format(error,"owner"));
	}
	
	private void validNull(ChangePropertyVo msgVo){
		String error="%s is null or empty";
		if(StringUtil.isBlank(msgVo.getDatano())) throw new NullPointerException(String.format(error,"datano"));
		if(StringUtil.isBlank(msgVo.getProductid())) throw new NullPointerException(String.format(error,"productid"));
		if(StringUtil.isBlank(msgVo.getTradetype())) throw new NullPointerException(String.format(error,"tradetype"));
	}
	
	@SuppressWarnings("unused")
	private void validStatus(String status){
		switch(status){
		case "0":
		case "1":
			break;
		default :
			throw new IllegalArgumentException(String.format("%s is illegal argument", "status"));
		}
	}	
}
