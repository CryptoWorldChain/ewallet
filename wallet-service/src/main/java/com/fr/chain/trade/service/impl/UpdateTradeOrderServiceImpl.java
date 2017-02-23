package com.fr.chain.trade.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fr.chain.trade.db.dao.TradeOrderDao;
import com.fr.chain.trade.db.entity.TradeOrder;
import com.fr.chain.trade.db.entity.TradeOrderExample;
import com.fr.chain.trade.service.UpdateTradeOrderService;

@Service("updateTradeOrderService")
public class UpdateTradeOrderServiceImpl implements UpdateTradeOrderService {
	
	@Resource 
	TradeOrderDao tradeOrderDao;
	
	
	@Override
	public	int updateByExampleSelective(TradeOrder record, TradeOrderExample example){
		return tradeOrderDao.updateByExampleSelective(record, example);			
	}

	@Override
	public int updateTradeOrder(TradeOrder record){
		return tradeOrderDao.updateByPrimaryKey(record);
	}
}
