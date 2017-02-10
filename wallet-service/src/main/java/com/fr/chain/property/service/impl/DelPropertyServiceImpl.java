package com.fr.chain.property.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fr.chain.property.service.DelPropertyService;
import com.fr.chain.property.db.dao.PropertyDao;
import com.fr.chain.property.db.entity.PropertyKey;

@Service("delPropertyService")
public class DelPropertyServiceImpl implements DelPropertyService {
	
	@Resource 
	PropertyDao propertyDao;
	
	
	@Override
	public	int deleteByPrimaryKey (PropertyKey key){
		return propertyDao.deleteByPrimaryKey(key);			
	}

}
