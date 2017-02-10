package com.fr.chain.trade.service;

import com.fr.chain.trade.db.entity.TradeOrderKey;

public interface DelTradeOrderService {
	public int deleteByPrimaryKey (TradeOrderKey key);

}
