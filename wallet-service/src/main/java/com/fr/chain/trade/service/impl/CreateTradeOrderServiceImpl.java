package com.fr.chain.trade.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fr.chain.property.db.entity.Property;
import com.fr.chain.trade.db.dao.TradeFlowDao;
import com.fr.chain.trade.db.dao.TradeOrderDao;
import com.fr.chain.trade.db.entity.TradeFlow;
import com.fr.chain.trade.db.entity.TradeOrder;
import com.fr.chain.trade.service.CreateTradeOrderService;
import com.fr.chain.utils.DateUtil;
import com.fr.chain.utils.IDGenerator;

@Service("createTradeOrderService")
public class CreateTradeOrderServiceImpl implements CreateTradeOrderService {
	
	@Resource 
	TradeOrderDao tradeOrderDao;
	@Resource
	TradeFlowDao tradeFlowDao;
	
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
	
	
	@Override
	public	int insertFlow(TradeFlow info){
		return tradeFlowDao.insert(info);			
	}
	
	@Override
	public int insertFlowSelective(TradeFlow record ){
		return tradeFlowDao.insertSelective(record);	
	}

	@Override
	public int batchInsertFlow(List<TradeFlow> records){
		return tradeFlowDao.batchInsert(records);
	}
	
	public	int insertTradeFlowByOrder(TradeOrder orderRecord){
		//创建流水
		TradeFlow tradeFlow = new TradeFlow();
		tradeFlow.setFlowId(IDGenerator.nextID());
		tradeFlow.setOrderId(orderRecord.getOrderId());
		tradeFlow.setMerchantId(orderRecord.getMerchantId());
		tradeFlow.setAppId(orderRecord.getAppId());
		tradeFlow.setOpenId(orderRecord.getOpenId());
		tradeFlow.setTallyTag(1);//1+;0-
		tradeFlow.setOriginOpenid(orderRecord.getOriginOpenid());
		tradeFlow.setProductId(orderRecord.getProductId());
		tradeFlow.setPropertyType(orderRecord.getPropertyType());
		tradeFlow.setIsSelfSupport(orderRecord.getIsSelfSupport());
		tradeFlow.setProductDesc(orderRecord.getProductDesc());
		tradeFlow.setIsDigit(orderRecord.getIsDigit());
		tradeFlow.setSigntype(orderRecord.getSigntype());
		tradeFlow.setPropertyName(orderRecord.getPropertyName());
		tradeFlow.setUnit(orderRecord.getUnit());
		tradeFlow.setMincount(orderRecord.getMincount());
		tradeFlow.setCount(orderRecord.getCount());
		tradeFlow.setUrl(orderRecord.getUrl());
		tradeFlow.setAmount(orderRecord.getAmount());
		tradeFlow.setDescription(orderRecord.getDescription());
		tradeFlow.setAddress(orderRecord.getAddress());
		tradeFlow.setTradeType(orderRecord.getTradeType());
		tradeFlow.setCreateTime(DateUtil.getSystemDate());
		
		return tradeFlowDao.insertSelective(tradeFlow);			
	}
	
	/**
	 * 发送资产流水创建。
	 * 1，发送发减少资产流水
	 * 2，系统临时增加资产流水
	 */
	@Override
	public boolean insertFlow4Sent(TradeOrder orderRecord){
		
		TradeFlow srcFlow = new TradeFlow();
		srcFlow.setFlowId(IDGenerator.nextID());
		srcFlow.setOrderId(orderRecord.getOrderId());
		srcFlow.setOpenId(orderRecord.getOpenId());
		srcFlow.setMerchantId(orderRecord.getMerchantId());
		srcFlow.setAppId(orderRecord.getAppId());
		srcFlow.setProductId(orderRecord.getProductId());
		srcFlow.setTallyTag(0);
		srcFlow.setCount(orderRecord.getCount());
		srcFlow.setTradeType(2);
		srcFlow.setCreateTime(DateUtil.getSystemDate());
		tradeFlowDao.insert(srcFlow);
		
		TradeFlow sysFlow = new TradeFlow();
		sysFlow.setFlowId(IDGenerator.nextID());
		sysFlow.setOrderId(orderRecord.getOrderId());
		sysFlow.setOpenId("OpenId_sys");
		sysFlow.setMerchantId(orderRecord.getMerchantId());
		sysFlow.setAppId(orderRecord.getAppId());
		srcFlow.setProductId(orderRecord.getProductId());
		sysFlow.setTallyTag(1);
		sysFlow.setCount(orderRecord.getCount());
		sysFlow.setTradeType(2);
		sysFlow.setCreateTime(DateUtil.getSystemDate());
		tradeFlowDao.insert(sysFlow);
		
		return true;
	}
	/**
	 * 发送资产流水创建。
	 * 1，系统减少资产流水
	 * 2，获取人增加资产流水
	 */
	public boolean insertFlow4Get(TradeOrder orderRecord){
		
		TradeFlow sysFlow = new TradeFlow();
		sysFlow.setFlowId(IDGenerator.nextID());
		sysFlow.setOrderId(orderRecord.getOrderId());
		sysFlow.setOpenId("OpenId_sys");
		sysFlow.setMerchantId(orderRecord.getMerchantId());
		sysFlow.setAppId(orderRecord.getAppId());
		sysFlow.setTallyTag(0);
		sysFlow.setCount(orderRecord.getCount());
		sysFlow.setTradeType(3);
		sysFlow.setCreateTime(DateUtil.getSystemDate());
		tradeFlowDao.insert(sysFlow);
		
		TradeFlow receFlow = new TradeFlow();
		receFlow.setFlowId(IDGenerator.nextID());
		receFlow.setOrderId(orderRecord.getOrderId());
		receFlow.setOpenId(orderRecord.getToOpenId());
		receFlow.setMerchantId(orderRecord.getMerchantId());
		receFlow.setAppId(orderRecord.getAppId());
		receFlow.setTallyTag(1);
		receFlow.setCount(orderRecord.getCount());
		receFlow.setTradeType(3);
		receFlow.setCreateTime(DateUtil.getSystemDate());
		tradeFlowDao.insert(receFlow);
		
		return true;
	}
}
