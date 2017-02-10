package com.fr.chain.trade.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fr.chain.trade.db.dao.TradeOrderDao;
import com.fr.chain.trade.db.entity.TradeOrderKey;
import com.fr.chain.trade.service.DelTradeOrderService;

@Service("delTradeOrderService")
public class DelTradeOrderServiceImpl implements DelTradeOrderService {
	
	@Resource 
	TradeOrderDao tradeOrderDao;
	
	
	@Override
	public	int deleteByPrimaryKey (TradeOrderKey key){
		return tradeOrderDao.deleteByPrimaryKey(key);			
	}

}
