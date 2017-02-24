package com.fr.chain.trade.service;

import com.fr.chain.trade.db.entity.TradeOrder;
import com.fr.chain.trade.db.entity.TradeOrderExample;

public interface UpdateTradeOrderService {
	public int updateByExampleSelective (TradeOrder record, TradeOrderExample example);
	public int updateTradeOrder(TradeOrder record);
	
}
