package com.fr.chain.facadeservice.property.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import com.fr.chain.vo.trade.SendPropertyVo;
import com.fr.chain.enums.BaseStatusEnum;
import com.fr.chain.enums.TradeTypeEnum;
import com.fr.chain.facadeservice.property.PropertyService;
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
import com.fr.chain.trade.service.CreateTradeOrderService;
import com.fr.chain.utils.DateUtil;
import com.fr.chain.utils.IDGenerator;
import com.fr.chain.utils.NumberUtil;
import com.fr.chain.utils.StringUtil;
import com.fr.chain.utils.JsonUtil;

@Slf4j
@Service("propertyService")
public class PropertyServiceImpl implements PropertyService {

	@Resource
	CreatePropertyService  createPropertyService;
	@Resource
	QueryPropertyService queryPropertyService;
	@Resource
	DelPropertyService delPropertyService;
	@Resource
	UpdatePropertyService updatePropertyService;
	@Resource
	CreateTradeOrderService createTradeOrderService;
	


	private void buildCreatePropertyBody(Message<CreatePropertyVo> gpmsg) {
		List<CreatePropertyVo> bodys = new ArrayList<>();
		for (JsonNode node : gpmsg.getAnDatas()) {
			bodys.add(JsonUtil.json2Bean(node, CreatePropertyVo.class));
		} 
		gpmsg.setBodyDatas(bodys);
	}
	
	private void buildQueryPropertyBody(Message<QueryPropertyVo> gpmsg) {
		List<QueryPropertyVo> bodys = new ArrayList<>();
		for (JsonNode node : gpmsg.getAnDatas()) {
			bodys.add(JsonUtil.json2Bean(node, QueryPropertyVo.class));
		} 
		gpmsg.setBodyDatas(bodys);		
	}

	@Override
	public Message<MsgBody> processCreateProperty(Message<CreatePropertyVo> msg) {
		buildCreatePropertyBody(msg);
		log.debug("Create property msg:" + msg);
		Map<String,MsgBody> resp = new LinkedHashMap<String, MsgBody>();
		try {
			List<CreatePropertyVo> datas = msg.getBodyDatas();
			if (datas != null && datas.size() > 0) {
				for (CreatePropertyVo msgVo : datas) {
					try{					
						this.validNull(msgVo);
						Res_CreatePropertyVo res_CreatePropertyVo =new Res_CreatePropertyVo(msgVo.getDatano());
						Property property= new Property();						
						property.setMerchantId(msg.getMerchantid());
						property.setOpenId(msg.getOpenid()); 
						property.setAppId(msg.getAppid());	
						property.setProductId(msgVo.getProductid());
						property.setPropertyType(msgVo.getPropertytype());	
						String address = ""; //钱包地址
						Property tmpProperty = queryPropertyService.selectOneByExample(property);
						if(tmpProperty!=null){
							//已经存在此资产(再次创建),不用生成地址,只需增加数量
							int nCount = NumberUtil.toInt(tmpProperty.getCount()) + NumberUtil.toInt(msgVo.getCount());	
							tmpProperty.setCount(String.valueOf(nCount));
							updatePropertyService.updateByPrimaryKey(tmpProperty);
							//获取返回地址
							address = tmpProperty.getAddress();
							
							//创建订单
							createTradeOrderService.insertTradeByProperty(tmpProperty, TradeTypeEnum.创建资产.getValue());
							
						}else{
							//新增资产
							property.setPropertyId(IDGenerator.nextID());//自动生成Id
							property.setProductDesc(msgVo.getPropertytype());
							property.setIsSelfSupport(msgVo.getIsselfsupport());
							property.setIsDigit(msgVo.getIsdigit());
							property.setProductDesc(msgVo.getProductdesc());
							property.setSignType(msgVo.getSigntype());
							property.setPropertyName(msgVo.getPropertyname());
							property.setUnit(msgVo.getUnit());
							property.setMinCount(msgVo.getMincount());
							property.setCount(msgVo.getCount());
							property.setUrl(msgVo.getUrl());
							if(msgVo.getAmount()!=null){
								property.setAmount(NumberUtil.toDouble(msgVo.getAmount()));
							}						
							property.setDescription(msgVo.getDescription());							
							//调用底层区块链生成地址
							address = IDGenerator.nextID();	//模拟生成地址							
							property.setAddress(address);
							property.setCreateTime(DateUtil.getSystemDate());
							property.setOriginOpenid(msg.getOpenid());
							property.setStatus(1);
							createPropertyService.insert(property);
							
							//创建订单
							createTradeOrderService.insertTradeByProperty(property, TradeTypeEnum.创建资产.getValue());
							
						}
						
						//设置返回报文
						res_CreatePropertyVo.setProductid(msgVo.getProductid());
						res_CreatePropertyVo.setPropertytype(msgVo.getPropertytype());		
						res_CreatePropertyVo.setAddress(address);						
						resp.put(msgVo.getDatano(), res_CreatePropertyVo);
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
	public Message<MsgBody> processQueryProperty(Message<QueryPropertyVo> msg){
		buildQueryPropertyBody(msg);
		log.debug("Create property msg:" + msg);
		Map<String,MsgBody> resp = new LinkedHashMap<String, MsgBody>();
		try {
			List<QueryPropertyVo> datas = msg.getBodyDatas();
			if (datas != null && datas.size() > 0) {
				for (QueryPropertyVo msgVo : datas) {
					try{					
						this.validNull(msgVo);
						Property property= new Property();
						property.setMerchantId(msg.getMerchantid());
						property.setOpenId(msg.getOpenid()); 
						property.setAppId(msg.getAppid());	
						property.setProductId(msgVo.getProductid());
						property.setPropertyType(msgVo.getPropertytype());						
						property.setProductId(msgVo.getProductid());
						property.setPropertyType(msgVo.getPropertytype());	
						property.setIsSelfSupport(msgVo.getIsselfsupport());
						property.setSignType(msgVo.getSigntype());
						List<Property> propertyList = queryPropertyService.selectByExample(property);
						
						Res_QueryPropertyVo res_QueryPropertyVo = new Res_QueryPropertyVo(msgVo.getDatano());
						if(propertyList != null && propertyList.size()>0){ 
							for(Property tmpProperty : propertyList){
								//BeanUtils.copyProperties(propertyInfo, tmpProperty); //拷贝
								Res_QueryPropertyVo.PropertyInfo propertyInfo = new Res_QueryPropertyVo.PropertyInfo();
								propertyInfo.setPropertytype(tmpProperty.getPropertyType());
								propertyInfo.setIsselfsupport(tmpProperty.getIsSelfSupport());
								propertyInfo.setProductid(tmpProperty.getProductId());
								propertyInfo.setProductdesc(tmpProperty.getProductDesc());
								propertyInfo.setIsdigit(tmpProperty.getIsDigit());
								propertyInfo.setSigntype(tmpProperty.getSignType());
								propertyInfo.setPropertyname(tmpProperty.getPropertyName());
								propertyInfo.setUnit(tmpProperty.getUnit());
								propertyInfo.setMincount(tmpProperty.getMinCount());
								propertyInfo.setCount(tmpProperty.getCount());
								propertyInfo.setUrl(tmpProperty.getUrl());
								propertyInfo.setAmount(tmpProperty.getAmount());
								propertyInfo.setDescription(tmpProperty.getDescription());
								res_QueryPropertyVo.getPropertyInfoList().add(propertyInfo);
							}
						}
						//设置返回报文
						resp.put(msgVo.getDatano(), res_QueryPropertyVo);
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
    public int insert(Property info){
    	return createPropertyService.insert(info);
    }
	
	@Override
	public int batchInsert(List<Property> records){
		return createPropertyService.batchInsert(records);
	}
	
	@Override
	public int deleteByPrimaryKey (PropertyKey key){
		return delPropertyService.deleteByPrimaryKey(key);
	}
	
	@Override
	public List<Property>  selectByExample(Property info){
		return queryPropertyService.selectByExample(info);
	}
	
	@Override
	public List<Property>  selectByExample(PropertyExample info){
		return queryPropertyService.selectByExample(info);
	}
	
	@Override
	public int updateByExampleSelective (Property record, PropertyExample example){
		return updatePropertyService.updateByExampleSelective(record,example);
	}
	

	@SuppressWarnings("unused")
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
	
	private void validNull(QueryPropertyVo msgVo){
		String error="%s is null or empty";
		if(StringUtil.isBlank(msgVo.getDatano())) throw new NullPointerException(String.format(error,"datano"));
		if(StringUtil.isBlank(msgVo.getPropertytype())) throw new NullPointerException(String.format(error,"propertytype"));
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
