package com.fr.chain.property.service;

import com.fr.chain.property.db.entity.Property;
import com.fr.chain.property.db.entity.PropertyExample;
import com.fr.chain.trade.db.entity.TradeOrder;

public interface UpdatePropertyService {
	public int updateByExampleSelective (Property record, PropertyExample example);
	public int updateByPrimaryKey(Property record);
	public int updateByPrimaryKeySelective(Property record);

	//根据订单更改资产状态
	public boolean updateByOrder(TradeOrder record,String address);
}
