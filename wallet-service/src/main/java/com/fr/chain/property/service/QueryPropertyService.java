package com.fr.chain.property.service;

import java.util.List;

import com.fr.chain.property.db.entity.ProductDigit;
import com.fr.chain.property.db.entity.ProductInfo;
import com.fr.chain.property.db.entity.Property;
import com.fr.chain.property.db.entity.PropertyExample;

public interface QueryPropertyService {
	public List<Property>  selectByExample(Property info);
	public Property  selectOneByExample(Property info);
	public List<Property>  selectByExample(PropertyExample info);
	public ProductInfo selectProductInfoByKey(String key);
	public ProductDigit selectProductDigitByKey(String key);
}
