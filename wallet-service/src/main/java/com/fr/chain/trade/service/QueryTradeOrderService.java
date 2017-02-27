package com.fr.chain.trade.service;

import java.util.List;

import com.fr.chain.trade.db.entity.TradeFlow;
import com.fr.chain.trade.db.entity.TradeOrder;
import com.fr.chain.trade.db.entity.TradeOrderExample;

public interface QueryTradeOrderService {
	public List<TradeOrder>  selectByExample(TradeOrder info);
	public List<TradeOrder>  selectByExample(TradeOrderExample info);
	public TradeOrder selectOrderByKey(String orderId);
	public List<TradeFlow> selectByExample(TradeFlow info);
}
