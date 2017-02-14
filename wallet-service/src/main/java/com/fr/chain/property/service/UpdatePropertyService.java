package com.fr.chain.property.service;

import com.fr.chain.property.db.entity.Property;
import com.fr.chain.property.db.entity.PropertyExample;

public interface UpdatePropertyService {
	public int updateByExampleSelective (Property record, PropertyExample example);
	public int updateByPrimaryKey(Property record);
	public int updateByPrimaryKeySelective(Property record);

}
