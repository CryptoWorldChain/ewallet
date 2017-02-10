package com.fr.chain.trade.service;

import java.util.List;

import com.fr.chain.trade.db.entity.TradeOrder;

public interface CreateTradeOrderService {
	public int insert(TradeOrder info);
	
	public int batchInsert(List<TradeOrder> records);
	
	

}
