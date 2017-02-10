package com.fr.chain.property.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fr.chain.property.service.UpdatePropertyService;
import com.fr.chain.property.db.dao.PropertyDao;
import com.fr.chain.property.db.entity.Property;
import com.fr.chain.property.db.entity.PropertyExample;

@Service("updatePropertyService")
public class UpdatePropertyServiceImpl implements UpdatePropertyService {
	
	@Resource 
	PropertyDao propertyDao;
	
	
	@Override
	public	int updateByExampleSelective(Property record, PropertyExample example){
		return propertyDao.updateByExampleSelective(record, example);			
	}
	
	@Override
	public int updateByPrimaryKey(Property record) {
		return propertyDao.updateByPrimaryKey(record);
	}

}
