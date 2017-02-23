package com.fr.chain.property.service;

import java.util.List;

import com.fr.chain.property.db.entity.Property;
import com.fr.chain.trade.db.entity.TradeOrder;

public interface CreatePropertyService {
	public int insert(Property info);
	
	public int batchInsert(List<Property> records);
	
	//通过订单插入资产
	public int inserPropertyByOrder(TradeOrder orderRecord);

	//通过订单插入资产，资产发送接口，资产待激活(等chain返回结果)
	public boolean inserPropertyFreezen(TradeOrder orderRecord,int srcCount,String srcAddress,int receCount,String receAddress);
}
