package com.fr.chain.property.service.copy;

import java.util.List;

import com.fr.chain.property.db.entity.Property;
import com.fr.chain.property.db.entity.PropertyExample;

public interface QueryPropertyService {
	public List<Property>  selectByExample(Property info);
	public List<Property>  selectByExample(PropertyExample info);

}
