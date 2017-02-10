package com.fr.chain.property.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fr.chain.property.service.CreatePropertyService;
import com.fr.chain.property.db.dao.PropertyDao;
import com.fr.chain.property.db.entity.Property;

@Service("createPropertyService")
public class CreatePropertyServiceImpl implements CreatePropertyService {
	
	@Resource 
	PropertyDao propertyDao;
	
	
	@Override
	public	int insert(Property info){
		return propertyDao.insert(info);			
	}

	@Override
	public int batchInsert(List<Property> records){
		return propertyDao.batchInsert(records);
	}
}
