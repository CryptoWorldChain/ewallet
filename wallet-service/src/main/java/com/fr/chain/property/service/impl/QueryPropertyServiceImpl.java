package com.fr.chain.property.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fr.chain.property.service.QueryPropertyService;
import com.fr.chain.property.db.dao.PropertyDao;
import com.fr.chain.property.db.entity.Property;
import com.fr.chain.property.db.entity.PropertyExample;

@Service("queryPropertyService")
public class QueryPropertyServiceImpl implements QueryPropertyService {
	
	@Resource 
	PropertyDao propertyDao;
	
	
	@Override
	public List<Property>  selectByExample(Property property) {
		return  propertyDao.selectByExample(propertyDao.getExample(property));		
	}

	public  Property selectOneByExample(Property property){
		  List<Property> propertyList = propertyDao.selectByExample(propertyDao.getExample(property));
		  if(propertyList != null && propertyList.size() > 0){
			  return propertyList.get(0); 
		  }
		  return null;
	}
	
	
	@Override
	public List<Property>  selectByExample(PropertyExample info){
		return  propertyDao.selectByExample(info);	
	}
}
