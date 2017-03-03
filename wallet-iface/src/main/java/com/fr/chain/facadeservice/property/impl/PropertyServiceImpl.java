package com.fr.chain.facadeservice.property.impl;

import java.util.List;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.fr.chain.enums.PropertyStatusEnum;
import com.fr.chain.enums.PropertyTypeEnum;
import com.fr.chain.enums.TradeStatusEnum;
import com.fr.chain.enums.TradeTypeEnum;
import com.fr.chain.facadeservice.property.PropertyService;
import com.fr.chain.message.Message;
import com.fr.chain.message.MessageException;
import com.fr.chain.property.db.entity.ProductInfo;
import com.fr.chain.property.db.entity.Property;
import com.fr.chain.property.db.entity.PropertyExample;
import com.fr.chain.property.db.entity.PropertyExample.Criteria;
import com.fr.chain.property.db.entity.PropertyKey;
import com.fr.chain.property.service.CreatePropertyService;
import com.fr.chain.property.service.DelPropertyService;
import com.fr.chain.property.service.QueryPropertyService;
import com.fr.chain.property.service.UpdatePropertyService;
import com.fr.chain.trade.db.entity.TradeOrder;
import com.fr.chain.trade.service.CreateTradeOrderService;
import com.fr.chain.utils.DateUtil;
import com.fr.chain.utils.IDGenerator;
import com.fr.chain.utils.StringUtil;
import com.fr.chain.vo.property.CreatePropertyVo;
import com.fr.chain.vo.property.QueryPropertyVo;
import com.fr.chain.vo.property.Res_CreatePropertyVo;
import com.fr.chain.vo.property.Res_QueryPropertyVo;

@Slf4j
@Service("propertyService")
public class PropertyServiceImpl implements PropertyService {

	@Resource
	private CreatePropertyService  createPropertyService;
	@Resource
	private QueryPropertyService queryPropertyService;
	@Resource
	private DelPropertyService delPropertyService;
	@Resource
	private UpdatePropertyService updatePropertyService;
	@Resource
	private CreateTradeOrderService createTradeOrderService;

	

	/**
	 * 创建资产
	 */
	@Override
	public void createProperty(Message msg, CreatePropertyVo msgVo, Res_CreatePropertyVo res_CreatePropertyVo ,
			ProductInfo productInfo) {
		//生成新的订单
		String orderId = IDGenerator.nextID();
		TradeOrder orderRecord =  new TradeOrder();
		orderRecord.setOrderId(orderId);
		orderRecord.setMerchantId(msg.getMerchantid());
		orderRecord.setAppId(msg.getAppid());
		orderRecord.setOpenId(msg.getOpenid());
		orderRecord.setFromOpenId("");
		orderRecord.setToOpenId("");
		orderRecord.setOriginOpenid(productInfo.getOriginOpenid());
		orderRecord.setProductId(productInfo.getProductId());
		orderRecord.setPropertyType(productInfo.getPropertyType()+"");
		orderRecord.setProductDesc(productInfo.getProductDesc());
		orderRecord.setIsDigit(0+"");
		orderRecord.setSigntype(productInfo.getSignType());
		orderRecord.setPropertyName(msgVo.getPropertyname());
		orderRecord.setUnit(productInfo.getUnit());
		orderRecord.setMincount(productInfo.getMinCount());
		orderRecord.setCount(msgVo.getCount());
		orderRecord.setUrl(productInfo.getUrl());
		orderRecord.setDescription(productInfo.getDescription());
		orderRecord.setCreateTime(DateUtil.getSystemDate());
		orderRecord.setTradeType(TradeTypeEnum.创建资产.getValue());
		orderRecord.setStatus(TradeStatusEnum.成功.getValue());//接入区块链之前，先认为成功
		
		//插入新的订单
		createTradeOrderService.insert(orderRecord);
		
		//插入新的流水
		createTradeOrderService.insertTradeFlowByOrder(orderRecord);
		
		//插入新的资产
		createPropertyService.inserPropertyByOrder(orderRecord);
		
		//设置返回报文
		res_CreatePropertyVo.setProductid(msgVo.getProductid());
	}
	
	
	
	/**
	 * 查询资产
	 */
	@Override
	public void queryProperty(Message msg, QueryPropertyVo msgVo, Res_QueryPropertyVo res_QueryPropertyVo ){
		PropertyExample example = new PropertyExample();
		Criteria criteria = example.createCriteria();
		criteria.andMerchantIdEqualTo(msg.getMerchantid());
		criteria.andOpenIdEqualTo(msg.getOpenid());
		criteria.andAppIdEqualTo(msg.getAppid());
		criteria.andPropertyTypeEqualTo(msgVo.getPropertytype());
		if(!StringUtil.isBlank(msgVo.getProductid())){
			criteria.andPropertyIdEqualTo(msgVo.getProductid());
		}
		if(!StringUtil.isBlank(msgVo.getStatus())){
			criteria.andStatusEqualTo(Integer.parseInt(msgVo.getStatus()));
		}else{
			criteria.andStatusNotEqualTo(PropertyStatusEnum.不可用.getValue());
		}
		List<Property> propertyList = queryPropertyService.selectByExample(example);
		
		if(propertyList != null && propertyList.size()>0){ 
			for(Property tmpProperty : propertyList){
				Res_QueryPropertyVo.PropertyInfo propertyInfo = new Res_QueryPropertyVo.PropertyInfo();
				propertyInfo.setPropertytype(tmpProperty.getPropertyType());
				propertyInfo.setProductid(tmpProperty.getProductId());
				propertyInfo.setProductdesc(tmpProperty.getProductDesc());
				propertyInfo.setSigntype(tmpProperty.getSignType());
				propertyInfo.setPropertyname(tmpProperty.getPropertyName());
				propertyInfo.setUnit(tmpProperty.getUnit());
				propertyInfo.setMincount(tmpProperty.getMinCount());
				propertyInfo.setCount(tmpProperty.getCount());
				propertyInfo.setUrl(tmpProperty.getUrl());
				propertyInfo.setDescription(tmpProperty.getDescription());
				propertyInfo.setStatus(tmpProperty.getStatus()+"");
				res_QueryPropertyVo.getPropertyinfolist().add(propertyInfo);
			}
		}
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
	
	public int updateByPrimaryKeySelective (Property record){
		return updatePropertyService.updateByPrimaryKeySelective(record);
	}
	
	@Override
	public ProductInfo selectProduct4CreateProperty(Message msg, CreatePropertyVo msgVo){
		ProductInfo info = null;
		//如果productid不为空,则跟据productid查找个性资产的基本信息是否存在
		//查询到个性资产基本信息,则直接返回, 否则添加该个性资产的基本信息
		if(StringUtil.isNotBlank(msgVo.getProductid())){
			info = queryPropertyService.selectProductInfoByKey(msgVo.getProductid());
			if(info == null){
				//一旦productid不为空,则说明之前创建过,如果没有查询到,则说明productid错误
				throw new MessageException("没有查询到该资产基本信息productid:" + msgVo.getProductid());
			}else{
				if(!info.getOriginOpenid().equals(msg.getOpenid())){
					throw new MessageException("没有权限创建此资产");
				}				
			}			
		}else{
			//创建个性资产的基本信息
			String productid =IDGenerator.nextID(); 
			info = new ProductInfo();
			info.setProductId(productid);
			info.setMerchantId(msg.getMerchantid());
			info.setAppId(msg.getAppid());
			info.setProductDesc(msgVo.getProductdesc());
			info.setPropertyType(PropertyTypeEnum.个性资产.getValue());//添加的都是个性资产
			info.setOriginOpenid(msg.getOpenid());
			info.setSignType(msgVo.getSigntype());
			info.setPropertyName(msgVo.getPropertyname());
			info.setUnit(msgVo.getUnit());
			info.setMinCount(msgVo.getMincount());
			info.setUrl(msgVo.getUrl());
			info.setCreateTime(DateUtil.getSystemDate());
			info.setStatus(1);
			createPropertyService.insertProductInfo(info);
		}
		
		//返回新建的productid
		msgVo.setProductid(info.getProductId());
		
		return info;
	}
	
}
