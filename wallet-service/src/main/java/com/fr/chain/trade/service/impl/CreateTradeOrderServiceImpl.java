package com.fr.chain.trade.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fr.chain.property.db.entity.Property;
import com.fr.chain.trade.db.dao.TradeOrderDao;
import com.fr.chain.trade.db.entity.TradeOrder;
import com.fr.chain.trade.service.CreateTradeOrderService;
import com.fr.chain.utils.DateUtil;
import com.fr.chain.utils.IDGenerator;

@Service("createTradeOrderService")
public class CreateTradeOrderServiceImpl implements CreateTradeOrderService {
	
	@Resource 
	TradeOrderDao tradeOrderDao;
	
	
	@Override
	public	int insert(TradeOrder info){
		return tradeOrderDao.insert(info);			
	}
	
	@Override
	public int insertSelective(TradeOrder record ){
		return tradeOrderDao.insertSelective(record);	
	}

	@Override
	public int batchInsert(List<TradeOrder> records){
		return tradeOrderDao.batchInsert(records);
	}
	
	/**
	 * 通过资产创建订单
	 */
	public	int insertTradeByProperty(Property property, int tradeType){
		//创建订单
		TradeOrder tradeOrder = new TradeOrder();
		tradeOrder.setOrderId(IDGenerator.nextID());
		tradeOrder.setMerchantId(property.getMerchantId());
		tradeOrder.setAppId(property.getAppId());
		tradeOrder.setOpenId(property.getOpenId());
		tradeOrder.setPropertyType(property.getPropertyType());
		tradeOrder.setIsSelfSupport(property.getIsSelfSupport());
		tradeOrder.setProductId(property.getProductId());
		tradeOrder.setProductDesc(property.getProductDesc());
		tradeOrder.setIsDigit(property.getIsDigit());
		tradeOrder.setSigntype(property.getSignType());
		tradeOrder.setPropertyName(property.getPropertyName());
		tradeOrder.setUnit(property.getUnit());
		tradeOrder.setMincount(property.getMinCount());
		tradeOrder.setCount(property.getCount());
		tradeOrder.setUrl(property.getUrl());
		tradeOrder.setAmount(property.getAmount());
		tradeOrder.setDescription(property.getDescription());
		tradeOrder.setCreateTime(DateUtil.getSystemDate());
		tradeOrder.setAddress(property.getAddress());
		tradeOrder.setTradeType(tradeType);
		tradeOrder.setCreateTime(DateUtil.getSystemDate());		
		return tradeOrderDao.insertSelective(tradeOrder);			
	}
	
}
