package com.fr.chain.property.service.copy;

import java.util.List;

import com.fr.chain.property.db.entity.Property;

public interface CreatePropertyService {
	public int insert(Property info);
	
	public int batchInsert(List<Property> records);
	
	

}
