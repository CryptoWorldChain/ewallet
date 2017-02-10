package com.fr.chain.property.service;

import com.fr.chain.property.db.entity.PropertyKey;

public interface DelPropertyService {
	public int deleteByPrimaryKey (PropertyKey key);

}
