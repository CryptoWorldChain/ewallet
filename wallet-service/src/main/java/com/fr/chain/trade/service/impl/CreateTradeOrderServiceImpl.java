package com.fr.chain.trade.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fr.chain.trade.db.dao.TradeOrderDao;
import com.fr.chain.trade.db.entity.TradeOrder;
import com.fr.chain.trade.service.CreateTradeOrderService;

@Service("createTradeOrderService")
public class CreateTradeOrderServiceImpl implements CreateTradeOrderService {
	
	@Resource 
	TradeOrderDao tradeOrderDao;
	
	
	@Override
	public	int insert(TradeOrder info){
		return tradeOrderDao.insert(info);			
	}

	@Override
	public int batchInsert(List<TradeOrder> records){
		return tradeOrderDao.batchInsert(records);
	}
}
