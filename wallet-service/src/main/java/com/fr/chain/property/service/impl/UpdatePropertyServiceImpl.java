package com.fr.chain.property.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fr.chain.enums.PropertyStatusEnum;
import com.fr.chain.property.db.dao.PropertyDao;
import com.fr.chain.property.db.entity.Property;
import com.fr.chain.property.db.entity.PropertyExample;
import com.fr.chain.property.service.UpdatePropertyService;
import com.fr.chain.trade.db.entity.TradeOrder;
import com.fr.chain.utils.IDGenerator;

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
	
	
	@Override
	public int updateByPrimaryKeySelective(Property record) {
		return propertyDao.updateByPrimaryKeySelective(record);
	}
	
	@Override
	public boolean updateByOrder(TradeOrder record,String address){
		
		PropertyExample example = new PropertyExample();
		example.createCriteria().andOrderIdEqualTo(record.getOrderId()).andOpenIdEqualTo("OpenId_sys");
		List<Property> list =propertyDao.selectByExample(example);
		
		//系统资产更新锁定
		Property propertySys=null;
		if(list!=null&&list.size()>0){
			propertySys = list.get(0);
		}else{
			return false;
		}
		propertySys.setStatus(PropertyStatusEnum.不可用.getValue());//***接入链子前，认为成功,更新不可用
		propertyDao.updateByPrimaryKey(propertySys);
		
		
		//接收方创建资产
		Property propertyGetOwner  = propertySys;
		propertyGetOwner.setPropertyId(IDGenerator.nextID());
		propertyGetOwner.setOpenId(record.getToOpenId());
		propertyGetOwner.setStatus(PropertyStatusEnum.可用.getValue());//***接入链子前，认为成功
		propertyGetOwner.setAddress(address);
		propertyDao.insert(propertyGetOwner);
		
		
		return true;
	}

}
