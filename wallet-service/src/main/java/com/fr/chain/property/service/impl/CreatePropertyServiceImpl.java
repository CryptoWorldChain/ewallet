package com.fr.chain.property.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fr.chain.enums.PropertyStatusEnum;
import com.fr.chain.property.db.dao.PropertyDao;
import com.fr.chain.property.db.entity.Property;
import com.fr.chain.property.service.CreatePropertyService;
import com.fr.chain.trade.db.entity.TradeOrder;
import com.fr.chain.utils.DateUtil;
import com.fr.chain.utils.IDGenerator;

@Service("createPropertyService")
public class CreatePropertyServiceImpl implements CreatePropertyService {
	
	@Resource 
	PropertyDao propertyDao;
	
	
	@Override
	public	int insert(Property info){
		return propertyDao.insert(info);			
	}

	@Override
	public int batchInsert(List<Property> records){
		return propertyDao.batchInsert(records);
	}
	
	@Override
	public int inserPropertyByOrder(TradeOrder orderRecord){
		
		//生产新的资产
		Property property =new Property();
		
		property.setPropertyId(IDGenerator.nextID());//自动生成Id
		property.setOrderId(orderRecord.getOrderId());
		property.setMerchantId(orderRecord.getMerchantId());
		property.setAppId(orderRecord.getAppId());	
		property.setOpenId(orderRecord.getOpenId()); 
		property.setProductId(orderRecord.getProductId());
		property.setPropertyType(orderRecord.getPropertyType());	
		//模拟生成地址
		String address = ""; //钱包地址
		address = IDGenerator.nextID();								
		property.setAddress(address);
		property.setOriginOpenid(orderRecord.getOriginOpenid());
		property.setIsSelfSupport(orderRecord.getIsSelfSupport());
		property.setProductDesc(orderRecord.getProductDesc());
		property.setIsDigit(orderRecord.getIsDigit());
		property.setSignType(orderRecord.getSigntype());
		property.setPropertyName(orderRecord.getPropertyName());
		property.setUnit(orderRecord.getUnit());
		property.setMinCount(orderRecord.getMincount());
		property.setCount(orderRecord.getCount());
		property.setUrl(orderRecord.getUrl());
		if(orderRecord.getAmount()!=null){
			property.setAmount(orderRecord.getAmount());
		}						
		property.setDescription(orderRecord.getDescription());							
		//调用底层区块链生成地址
		property.setCreateTime(DateUtil.getSystemDate());
		property.setStatus(1);
		return propertyDao.insert(property);
	}
	/**
	 * 通过订单插入资产，资产发送接口，资产待激活(等chain返回结果)
	 */
	@Override
	public boolean inserPropertyFreezen(TradeOrder orderRecord,int srcCount,String srcAddress,int receCount,String receAddress){
		//srcCount等于0，原有商品资产全部送出
		//srcCount非0，原有商品资产有剩余
		if(srcCount!=0){
			Property srcProperty = new Property();
			srcProperty.setPropertyId(IDGenerator.nextID());
			srcProperty.setOrderId(orderRecord.getOrderId());
			srcProperty.setMerchantId(orderRecord.getMerchantId());
			srcProperty.setAppId(orderRecord.getAppId());
			srcProperty.setOpenId(orderRecord.getOpenId());
			srcProperty.setProductId(orderRecord.getProductId());
			srcProperty.setAddress(srcAddress);
			srcProperty.setCount(srcCount+"");
			srcProperty.setStatus(PropertyStatusEnum.锁定.getValue());
			propertyDao.insert(srcProperty);
		}
		Property sysProperty = new Property();
		sysProperty.setPropertyId(IDGenerator.nextID());
		sysProperty.setOrderId(orderRecord.getOrderId());
		sysProperty.setMerchantId(orderRecord.getMerchantId());
		sysProperty.setAppId(orderRecord.getAppId());
		sysProperty.setOpenId("OpenId_sys");
		sysProperty.setProductId(orderRecord.getProductId());
		sysProperty.setAddress(receAddress);
		sysProperty.setCount(receCount+"");
		sysProperty.setStatus(PropertyStatusEnum.锁定.getValue());
		
		propertyDao.insert(sysProperty);
		
		return true;
	}
}
