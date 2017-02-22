package com.fr.chain.property.service;

import java.util.List;

import com.fr.chain.property.db.entity.Property;
import com.fr.chain.trade.db.entity.TradeOrder;

public interface CreatePropertyService {
	public int insert(Property info);
	
	public int batchInsert(List<Property> records);
	
	//通过订单插入资产
	public int inserPropertyByOrder(TradeOrder orderRecord);

}
