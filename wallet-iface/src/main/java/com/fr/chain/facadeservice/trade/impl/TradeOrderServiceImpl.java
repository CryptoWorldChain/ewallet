package com.fr.chain.facadeservice.trade.impl;

import java.util.List;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.fr.chain.enums.TradeTypeEnum;
import com.fr.chain.facadeservice.trade.TradeOrderService;
import com.fr.chain.message.Message;
import com.fr.chain.message.ResponseMsg;
import com.fr.chain.property.db.entity.Property;
import com.fr.chain.property.db.entity.PropertyExample;
import com.fr.chain.property.db.entity.PropertyExample.Criteria;
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
import com.fr.chain.vo.trade.ChangePropertyVo;
import com.fr.chain.vo.trade.GetPropertyVo;
import com.fr.chain.vo.trade.QueryTradeOrderVo;
import com.fr.chain.vo.trade.Res_QueryTradeOrderVo;
import com.fr.chain.vo.trade.Res_SendPropertyVo;
import com.fr.chain.vo.trade.SendPropertyVo;

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
	
	
	@Override
	public void queryAndCreateTradeOrder(Message msg, QueryTradeOrderVo msgVo, Res_QueryTradeOrderVo res_QueryTradeOrderVo ){
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
		if(tradeOrderList == null || tradeOrderList.size() == 0){
			//String errMsg = String.format("没有查询到相应的结果!");
			throw new IllegalArgumentException("没有查询到相应的结果!");
		}						
		//创建返回报文
		for(TradeOrder tmpTradeOrder:tradeOrderList){ 
			Res_QueryTradeOrderVo.TradeOrderData tradeOrderData = new Res_QueryTradeOrderVo.TradeOrderData();
			//BeanUtils.copyProperties(tradeOrderData, tradeOrder);
			tradeOrderData.setPropertytype(tmpTradeOrder.getPropertyType());
			tradeOrderData.setIsselfsupport(tmpTradeOrder.getIsSelfSupport());
			tradeOrderData.setProductid(tmpTradeOrder.getProductId());
			tradeOrderData.setIsdigit(tmpTradeOrder.getIsDigit());
			tradeOrderData.setSigntype(tmpTradeOrder.getSigntype());
			tradeOrderData.setPropertyname(tmpTradeOrder.getPropertyName());
			tradeOrderData.setUnit(tmpTradeOrder.getUnit());
			tradeOrderData.setCount(tmpTradeOrder.getCount());
			tradeOrderData.setAddress(tmpTradeOrder.getAddress());	
			tradeOrderData.setTradetype(tmpTradeOrder.getTradeType());
			res_QueryTradeOrderVo.getData().add(tradeOrderData);
		}
	}
	
	@Override
	public void sendAndCreateProperty(Message msg, SendPropertyVo msgVo, Res_SendPropertyVo res_SendPropertyVo ){
		PropertyExample propertyExample = new PropertyExample();
		Criteria criteria = propertyExample.createCriteria();
			criteria.andMerchantIdEqualTo(msg.getMerchantid());
			criteria.andOpenIdEqualTo(msg.getOpenid());
			criteria.andAppIdEqualTo(msg.getAppid());;
		//获取报文包信息List
		List<SendPropertyVo.PackageData> packageDataList  =  msgVo.getData();
		for(SendPropertyVo.PackageData packageData :packageDataList){
			criteria.andProductIdEqualTo(packageData.getProductid());
			criteria.andStatusEqualTo(1);//1可用 0不可用
			List<Property> listProperty = queryPropertyService.selectByExample(propertyExample);
			if(listProperty==null){
				String errMsg = String.format("productId:%s,没有查询到相应的结果!",packageData.getProductid());
				throw new IllegalArgumentException(errMsg);
			}
			//计算productid下所有count值
			int diff = 0;
			for(Property propertyRecord :listProperty){
				diff+=NumberUtil.toInt(propertyRecord.getCount());
			}
			diff = diff - NumberUtil.toInt(packageData.getCount()); 
			
			//基本属性---需要赋值给新的（系统及发送方剩余）
			Property srcProperty = listProperty.get(0);
			
			Property basicProperty = new Property();
			basicProperty.setMerchantId(srcProperty.getMerchantId());
			basicProperty.setAppId(srcProperty.getAppId());
			basicProperty.setProductId(srcProperty.getProductId());
			basicProperty.setPropertyType(srcProperty.getPropertyType());
			basicProperty.setOriginOpenid(srcProperty.getOriginOpenid());
			basicProperty.setIsSelfSupport(srcProperty.getIsSelfSupport());
			basicProperty.setProductDesc(srcProperty.getProductDesc());
			basicProperty.setIsDigit(srcProperty.getIsDigit());
			basicProperty.setPropertyName(srcProperty.getPropertyName());
			basicProperty.setUnit(srcProperty.getUnit());
			basicProperty.setMinCount(srcProperty.getMinCount());
			basicProperty.setUrl(srcProperty.getUrl());
			basicProperty.setDescription(srcProperty.getDescription());
			basicProperty.setCreateTime(DateUtil.getSystemDate());
			basicProperty.setStatus(1);
			
			//原有资产置成不可用，生成新的资产挂载到系统账户中，如果原有资产如有剩余，原有资产综合生成新的一条资产
			if(diff < 0){
				String errMsg = String.format("productId:%s,count:%s is to big",packageData.getProductid(), packageData.getCount());
				throw new IllegalArgumentException(errMsg);
			}else if(diff != 0){
				//更新自身资产
				Property selfProperty = srcProperty;
				selfProperty.setPropertyId(IDGenerator.nextID());
				String address= IDGenerator.nextID();//"chainAdress";//调用底层生成区块链地址
				selfProperty.setAddress(address);
				selfProperty.setCount(diff+"");
				createPropertyService.insert(selfProperty);
			}
			for(Property propertyRecord :listProperty){
				propertyRecord.setStatus(0);
				updatePropertyService.updateByPrimaryKeySelective(propertyRecord);
				
			}
			
			//增加系统资产
			Property sysProperty = basicProperty;
			sysProperty.setPropertyId(IDGenerator.nextID());
			sysProperty.setOpenId("OpenId_sys");
			String address= IDGenerator.nextID();//"chainAdress";//调用底层生成区块链地址
			sysProperty.setAddress(address);
			sysProperty.setCount(packageData.getCount());
//			sysProperty.setAmount(amount);
			createPropertyService.insert(sysProperty);
			
			//创建订单--发送方发送
			sysProperty.setOpenId(srcProperty.getOpenId());
			createTradeOrderService.insertTradeByProperty(sysProperty, TradeTypeEnum.发送资产.getValue());
			
			//系统订单--系统方领取
			sysProperty.setOpenId("OpenId_sys");
			createTradeOrderService.insertTradeByProperty(sysProperty, TradeTypeEnum.领取资产.getValue());

			//组装返回报文
			Res_SendPropertyVo.PackageData resPackageData = new Res_SendPropertyVo.PackageData();
			resPackageData.setProductid(packageData.getProductid());
			resPackageData.setCount(packageData.getCount());
			resPackageData.setAddress(address);
			res_SendPropertyVo.getData().add(resPackageData);							
		}
	}
	
	@Override
	public void getAndCreateProperty(Message msg, GetPropertyVo msgVo, ResponseMsg responseMsg  ){
		Property property= new Property();
		property.setMerchantId(msg.getMerchantid());
		property.setAppId(msg.getAppid());	
		List<GetPropertyVo.PackageData> packageDataList  =  msgVo.getData();
		for(GetPropertyVo.PackageData packageData :packageDataList){
			property.setProductId(packageData.getProductid());
			property.setAddress(packageData.getAddress());
			Property tmpProperty = queryPropertyService.selectOneByExample(property);
			if(tmpProperty==null){
				String errMsg = String.format("没有查询到productId=%s相应的结果!",packageData.getProductid());
				throw new IllegalArgumentException(errMsg);
			}
			int diff = NumberUtil.toInt(tmpProperty.getCount()) - NumberUtil.toInt(packageData.getCount()); 
			if(diff != 0){
				String errMsg = String.format("productId:%s,the count:%s is error",packageData.getProductid(), packageData.getCount());
				throw new IllegalArgumentException(errMsg);
			}
			//领取资产,只变更用户,区块链地址不变
			tmpProperty.setOpenId(msg.getOpenid());	
			tmpProperty.setCreateTime(DateUtil.getSystemDate());
			updatePropertyService.updateByPrimaryKey(tmpProperty);
			
			//创建订单
			tmpProperty.setOpenId(msg.getOpenid());
			createTradeOrderService.insertTradeByProperty(tmpProperty, TradeTypeEnum.领取资产.getValue());
			
			//系统订单
			tmpProperty.setOpenId("OpenId_sys");
			createTradeOrderService.insertTradeByProperty(property, TradeTypeEnum.发送资产.getValue());
		}
	}
	
	
	@Override
	public void changeAndDeleteProperty(Message msg, ChangePropertyVo msgVo, ResponseMsg responseMsg){
		Property property= new Property();
		property.setMerchantId(msg.getMerchantid());
		property.setOpenId(msg.getOpenid()); 
		property.setAppId(msg.getAppid());	
		property.setProductId(msgVo.getProductid());
		Property tmpProperty = queryPropertyService.selectOneByExample(property);
		if(tmpProperty==null){
			String errMsg = String.format("没有查询到productId=%s相应的结果!",msgVo.getProductid());
			throw new IllegalArgumentException(errMsg);
		}
	
		//丢弃或者使用资产,资产表中直接删除
		delPropertyService.deleteByPrimaryKey(tmpProperty);
		
		//创建订单
		createTradeOrderService.insertTradeByProperty(tmpProperty, TradeTypeEnum.领取资产.getValue());
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
	
}
