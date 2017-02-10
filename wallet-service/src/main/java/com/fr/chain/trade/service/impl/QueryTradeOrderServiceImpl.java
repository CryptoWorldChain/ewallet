package com.fr.chain.trade.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fr.chain.property.service.QueryPropertyService;
import com.fr.chain.property.db.dao.PropertyDao;
import com.fr.chain.property.db.entity.Property;
import com.fr.chain.property.db.entity.PropertyExample;
import com.fr.chain.trade.db.dao.TradeOrderDao;
import com.fr.chain.trade.db.entity.TradeOrder;
import com.fr.chain.trade.db.entity.TradeOrderExample;
import com.fr.chain.trade.service.QueryTradeOrderService;

@Service("queryTradeOrderService")
public class QueryTradeOrderServiceImpl implements QueryTradeOrderService {
	
	@Resource 
	TradeOrderDao tradeOrderDao;
	
	
	@Override
	public List<TradeOrder>  selectByExample(TradeOrder property) {
		return  tradeOrderDao.selectByExample(tradeOrderDao.getExample(property));		
	}

	@Override
	public List<TradeOrder>  selectByExample(TradeOrderExample info){
		return  tradeOrderDao.selectByExample(info);	
	}
}
