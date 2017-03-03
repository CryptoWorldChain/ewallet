package com.fr.chain.trade.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fr.chain.enums.PropertyTypeEnum;
import com.fr.chain.enums.SystemOpenIdEnum;
import com.fr.chain.enums.TradeTypeEnum;
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
		srcFlow.setTradeType(TradeTypeEnum.发送资产.getValue());
		srcFlow.setCreateTime(DateUtil.getSystemDate());
		
		srcFlow.setOriginOpenid(orderRecord.getOriginOpenid());
		srcFlow.setSigntype(orderRecord.getSigntype());
		srcFlow.setPropertyName(orderRecord.getPropertyName());
		srcFlow.setUnit(orderRecord.getUnit());
		srcFlow.setMincount(orderRecord.getMincount());
		srcFlow.setUrl(orderRecord.getUrl());
		srcFlow.setDescription(orderRecord.getDescription());
		srcFlow.setPropertyType(orderRecord.getPropertyType());
		tradeFlowDao.insert(srcFlow);
		
		TradeFlow sysFlow = new TradeFlow();
		sysFlow.setFlowId(IDGenerator.nextID());
		sysFlow.setOrderId(orderRecord.getOrderId());
		sysFlow.setOpenId(SystemOpenIdEnum.系统默认账户.getName());
		sysFlow.setMerchantId(orderRecord.getMerchantId());
		sysFlow.setAppId(orderRecord.getAppId());
		sysFlow.setProductId(orderRecord.getProductId());
		sysFlow.setTallyTag(1);
		sysFlow.setCount(orderRecord.getCount());
		sysFlow.setTradeType(TradeTypeEnum.发送资产.getValue());
		sysFlow.setCreateTime(DateUtil.getSystemDate());
		
		sysFlow.setOriginOpenid(orderRecord.getOriginOpenid());
		sysFlow.setSigntype(orderRecord.getSigntype());
		sysFlow.setPropertyName(orderRecord.getPropertyName());
		sysFlow.setUnit(orderRecord.getUnit());
		sysFlow.setMincount(orderRecord.getMincount());
		sysFlow.setUrl(orderRecord.getUrl());
		sysFlow.setDescription(orderRecord.getDescription());
		sysFlow.setPropertyType(orderRecord.getPropertyType());
		tradeFlowDao.insert(sysFlow);
		
		return true;
	}
	/**
	 * 发送资产流水创建。
	 * 1，系统减少资产--流水
	 * 2，获取人增加资产--流水
	 */
	public boolean insertFlow4Get(TradeOrder orderRecord){
		TradeFlow sysFlow = new TradeFlow();
		sysFlow.setFlowId(IDGenerator.nextID());
		sysFlow.setOrderId(orderRecord.getOrderId());
		sysFlow.setOpenId(SystemOpenIdEnum.系统默认账户.getName());
		sysFlow.setMerchantId(orderRecord.getMerchantId());
		sysFlow.setAppId(orderRecord.getAppId());
		sysFlow.setProductId(orderRecord.getProductId());
		sysFlow.setTallyTag(0);
		sysFlow.setCount(orderRecord.getCount());
		sysFlow.setTradeType(TradeTypeEnum.领取资产.getValue());
		sysFlow.setCreateTime(DateUtil.getSystemDate());
		sysFlow.setOriginOpenid(orderRecord.getOriginOpenid());
		sysFlow.setSigntype(orderRecord.getSigntype());
		sysFlow.setPropertyName(orderRecord.getPropertyName());
		sysFlow.setUnit(orderRecord.getUnit());
		sysFlow.setMincount(orderRecord.getMincount());
		sysFlow.setUrl(orderRecord.getUrl());
		sysFlow.setDescription(orderRecord.getDescription());
		sysFlow.setPropertyType("1".equals(orderRecord.getIsDigit())?PropertyTypeEnum.数字资产.getValue()+"":PropertyTypeEnum.个性资产.getValue()+"");
		tradeFlowDao.insert(sysFlow);
		
		TradeFlow receFlow = new TradeFlow();
		receFlow.setFlowId(IDGenerator.nextID());
		receFlow.setOrderId(orderRecord.getOrderId());
		receFlow.setOpenId(orderRecord.getToOpenId());
		receFlow.setMerchantId(orderRecord.getMerchantId());
		receFlow.setAppId(orderRecord.getAppId());
		receFlow.setProductId(orderRecord.getProductId());
		receFlow.setTallyTag(1);
		receFlow.setCount(orderRecord.getCount());
		receFlow.setTradeType(TradeTypeEnum.领取资产.getValue());
		receFlow.setCreateTime(DateUtil.getSystemDate());
		receFlow.setOriginOpenid(orderRecord.getOriginOpenid());
		receFlow.setSigntype(orderRecord.getSigntype());
		receFlow.setPropertyName(orderRecord.getPropertyName());
		receFlow.setUnit(orderRecord.getUnit());
		receFlow.setMincount(orderRecord.getMincount());
		receFlow.setUrl(orderRecord.getUrl());
		receFlow.setDescription(orderRecord.getDescription());
		receFlow.setPropertyType("1".equals(orderRecord.getIsDigit())?PropertyTypeEnum.数字资产.getValue()+"":PropertyTypeEnum.个性资产.getValue()+"");
		tradeFlowDao.insert(receFlow);
		
		return true;
	}
	
	/**
	 * 退回资产流水创建。
	 * 1，系统减少资产--流水
	 * 2，获退回人增加资产--流水
	 */
	public boolean insertFlow4Refund(TradeOrder orderRecord){
		TradeFlow sysFlow = new TradeFlow();
		sysFlow.setFlowId(IDGenerator.nextID());
		sysFlow.setOrderId(orderRecord.getOrderId());
		sysFlow.setOpenId(SystemOpenIdEnum.系统默认账户.getName());
		sysFlow.setMerchantId(orderRecord.getMerchantId());
		sysFlow.setAppId(orderRecord.getAppId());
		sysFlow.setProductId(orderRecord.getProductId());
		sysFlow.setTallyTag(0);
		sysFlow.setCount(orderRecord.getCount());
		sysFlow.setTradeType(TradeTypeEnum.退回资产.getValue());
		sysFlow.setCreateTime(DateUtil.getSystemDate());
		sysFlow.setOriginOpenid(orderRecord.getOriginOpenid());
		sysFlow.setSigntype(orderRecord.getSigntype());
		sysFlow.setPropertyName(orderRecord.getPropertyName());
		sysFlow.setUnit(orderRecord.getUnit());
		sysFlow.setMincount(orderRecord.getMincount());
		sysFlow.setUrl(orderRecord.getUrl());
		sysFlow.setDescription(orderRecord.getDescription());
		sysFlow.setPropertyType("1".equals(orderRecord.getIsDigit())?PropertyTypeEnum.数字资产.getValue()+"":PropertyTypeEnum.个性资产.getValue()+"");
		tradeFlowDao.insert(sysFlow);
		
		TradeFlow receFlow = new TradeFlow();
		receFlow.setFlowId(IDGenerator.nextID());
		receFlow.setOrderId(orderRecord.getOrderId());
		receFlow.setOpenId(orderRecord.getToOpenId());
		receFlow.setMerchantId(orderRecord.getMerchantId());
		receFlow.setAppId(orderRecord.getAppId());
		receFlow.setProductId(orderRecord.getProductId());
		receFlow.setTallyTag(1);
		receFlow.setCount(orderRecord.getCount());
		receFlow.setTradeType(TradeTypeEnum.退回资产.getValue());
		receFlow.setCreateTime(DateUtil.getSystemDate());
		receFlow.setOriginOpenid(orderRecord.getOriginOpenid());
		receFlow.setSigntype(orderRecord.getSigntype());
		receFlow.setPropertyName(orderRecord.getPropertyName());
		receFlow.setUnit(orderRecord.getUnit());
		receFlow.setMincount(orderRecord.getMincount());
		receFlow.setUrl(orderRecord.getUrl());
		receFlow.setDescription(orderRecord.getDescription());
		receFlow.setPropertyType("1".equals(orderRecord.getIsDigit())?PropertyTypeEnum.数字资产.getValue()+"":PropertyTypeEnum.个性资产.getValue()+"");
		tradeFlowDao.insert(receFlow);
		
		return true;
	}
	
	
	/**
	 * 丢弃资产流水创建。
	 */
	public boolean insertFlow4Drop(TradeOrder orderRecord){
		TradeFlow dropFlow = new TradeFlow();
		dropFlow.setFlowId(IDGenerator.nextID());
		dropFlow.setOrderId(orderRecord.getOrderId());
		dropFlow.setOpenId(orderRecord.getOpenId());
		dropFlow.setMerchantId(orderRecord.getMerchantId());
		dropFlow.setAppId(orderRecord.getAppId());
		dropFlow.setProductId(orderRecord.getProductId());
		dropFlow.setTallyTag(0);
		dropFlow.setCount(orderRecord.getCount());
		dropFlow.setTradeType(orderRecord.getTradeType());
		dropFlow.setCreateTime(DateUtil.getSystemDate());
		
		dropFlow.setOriginOpenid(orderRecord.getOriginOpenid());
		dropFlow.setSigntype(orderRecord.getSigntype());
		dropFlow.setPropertyName(orderRecord.getPropertyName());
		dropFlow.setUnit(orderRecord.getUnit());
		dropFlow.setMincount(orderRecord.getMincount());
		dropFlow.setUrl(orderRecord.getUrl());
		dropFlow.setDescription(orderRecord.getDescription());
		dropFlow.setPropertyType(orderRecord.getPropertyType());
		tradeFlowDao.insert(dropFlow);
		
		return true;
	}
	
	/**
	 * 资产交易流水创建
	 */
	public boolean insertFolw4Trans(TradeOrder orderRecord){
		
		TradeFlow fromFlow = new TradeFlow();
		fromFlow.setFlowId(IDGenerator.nextID());
		fromFlow.setOrderId(orderRecord.getOrderId());
		fromFlow.setOpenId(orderRecord.getFromOpenId());
		fromFlow.setMerchantId(orderRecord.getMerchantId());
		fromFlow.setAppId(orderRecord.getAppId());
		fromFlow.setProductId(orderRecord.getProductId());
		fromFlow.setTallyTag(0);
		fromFlow.setCount(orderRecord.getCount());
		fromFlow.setTradeType(TradeTypeEnum.资产转移.getValue());
		fromFlow.setCreateTime(DateUtil.getSystemDate());
		
		fromFlow.setOriginOpenid(orderRecord.getOriginOpenid());
		fromFlow.setSigntype(orderRecord.getSigntype());
		fromFlow.setPropertyName(orderRecord.getPropertyName());
		fromFlow.setUnit(orderRecord.getUnit());
		fromFlow.setMincount(orderRecord.getMincount());
		fromFlow.setUrl(orderRecord.getUrl());
		fromFlow.setDescription(orderRecord.getDescription());
		fromFlow.setPropertyType(orderRecord.getPropertyType());
		tradeFlowDao.insert(fromFlow);
		
		TradeFlow receFlow = new TradeFlow();
		receFlow.setFlowId(IDGenerator.nextID());
		receFlow.setOrderId(orderRecord.getOrderId());
		receFlow.setOpenId(orderRecord.getToOpenId());
		receFlow.setMerchantId(orderRecord.getMerchantId());
		receFlow.setAppId(orderRecord.getAppId());
		receFlow.setProductId(orderRecord.getProductId());
		receFlow.setTallyTag(1);
		receFlow.setCount(orderRecord.getCount());
		receFlow.setTradeType(TradeTypeEnum.资产转移.getValue());
		receFlow.setCreateTime(DateUtil.getSystemDate());
		
		receFlow.setOriginOpenid(orderRecord.getOriginOpenid());
		receFlow.setSigntype(orderRecord.getSigntype());
		receFlow.setPropertyName(orderRecord.getPropertyName());
		receFlow.setUnit(orderRecord.getUnit());
		receFlow.setMincount(orderRecord.getMincount());
		receFlow.setUrl(orderRecord.getUrl());
		receFlow.setDescription(orderRecord.getDescription());
		receFlow.setPropertyType(orderRecord.getPropertyType());
		tradeFlowDao.insert(receFlow);
		
		return true;
	}
}
