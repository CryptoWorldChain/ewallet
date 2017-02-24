package com.fr.chain.trade.service;

import java.util.List;

import com.fr.chain.property.db.entity.Property;
import com.fr.chain.trade.db.entity.TradeFlow;
import com.fr.chain.trade.db.entity.TradeOrder;

public interface CreateTradeOrderService {
	public int insert(TradeOrder info);
	public int insertSelective(TradeOrder record);
	
	public int batchInsert(List<TradeOrder> records);
	
	public	int insertTradeByProperty(Property property, int tradeType);
	
	/**
	 * TradeFlow
	 * @param info
	 * @return
	 */
	public int insertFlow(TradeFlow info);
	public int insertFlowSelective(TradeFlow record);
	
	public int batchInsertFlow(List<TradeFlow> records);
	
	//创建资产流水创建
	public	int insertTradeFlowByOrder(TradeOrder orderRecord);

	//发送资产流水创建
	public boolean insertFlow4Sent(TradeOrder orderRecord);
	//获取资产流水创建
	public boolean insertFlow4Get(TradeOrder orderRecord);
}
