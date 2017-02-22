package com.fr.chain.property.db.dao;

import java.util.List;
import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fr.chain.property.db.entity.Property;
import com.fr.chain.property.db.entity.PropertyExample;
import com.fr.chain.property.db.entity.PropertyExample.Criteria;
import com.fr.chain.property.db.entity.PropertyKey;

import com.fr.chain.db.iface.StaticTableDaoSupport;

import com.fr.chain.property.db.mapper.PropertyMapper;

@Repository
public class PropertyDao implements StaticTableDaoSupport<Property, PropertyExample, PropertyKey>{

	@Resource
	private PropertyMapper mapper;
	
	
	@Override
	public int countByExample(PropertyExample example) {
		return mapper.countByExample(example);
	}

	@Override
	public int deleteByExample(PropertyExample example) {
		return mapper.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(PropertyKey key) {
		return mapper.deleteByPrimaryKey(key);
	}

	@Override
	public int insert(Property record)  {
		return mapper.insert(record);
	}

	@Override
	public int insertSelective(Property record)  {
		return mapper.insertSelective(record);
	}

	@Override
	@Transactional
	public int batchInsert(List<Property> records)
			 {
		for(Property record : records){
			mapper.insert(record);
		}
		return records.size();
	}

	@Override
	@Transactional
	public int batchUpdate(List<Property> records)
			 {
		for(Property record : records){
			mapper.updateByPrimaryKeySelective(record);
		}
		return records.size();
	}

	@Override
	@Transactional
	public int batchDelete(List<Property> records)
			 {
		for(Property record : records){
			mapper.deleteByPrimaryKey(record);
		}
		return records.size();
	}

	@Override
	public List<Property> selectByExample(PropertyExample example)
			 {
		return mapper.selectByExample(example);
	}

	@Override
	public Property selectByPrimaryKey(PropertyKey key)
			 {
		return mapper.selectByPrimaryKey(key);
	}

	@Override
	public List<Property> findAll(List<Property> records) {
		if(records==null||records.size()<=0){
			return mapper.selectByExample(new PropertyExample());
		}
		List<Property> list = new ArrayList<>();
		for(Property record : records){
			Property result = mapper.selectByPrimaryKey(record);
			if(result!=null){
				list.add(result);
			}
		}
		return list;
	}

	@Override
	public int updateByExampleSelective(Property record, PropertyExample example)  {
		return mapper.updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExample(Property record, PropertyExample example) {
		return mapper.updateByExample(record, example);
	}

	@Override
	public int updateByPrimaryKeySelective(Property record) {
		return mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Property record) {
		return mapper.updateByPrimaryKey(record);
	}

	@Override
	public int sumByExample(PropertyExample example) {
		return 0;
	}

	@Override
	public void deleteAll()  {
		mapper.deleteByExample(new PropertyExample());
	}

	@Override
	public PropertyExample getExample(Property record) {
		PropertyExample example = new PropertyExample();
		if(record!=null){
			Criteria criteria = example.createCriteria();
							if(record.getPropertyId()!=null){
				criteria.andPropertyIdEqualTo(record.getPropertyId());
				}
				if(record.getOrderId()!=null){
				criteria.andOrderIdEqualTo(record.getOrderId());
				}
				if(record.getMerchantId()!=null){
				criteria.andMerchantIdEqualTo(record.getMerchantId());
				}
				if(record.getAppId()!=null){
				criteria.andAppIdEqualTo(record.getAppId());
				}
				if(record.getOpenId()!=null){
				criteria.andOpenIdEqualTo(record.getOpenId());
				}
				if(record.getProductId()!=null){
				criteria.andProductIdEqualTo(record.getProductId());
				}
				if(record.getPropertyType()!=null){
				criteria.andPropertyTypeEqualTo(record.getPropertyType());
				}
				if(record.getAddress()!=null){
				criteria.andAddressEqualTo(record.getAddress());
				}
				if(record.getOriginOpenid()!=null){
				criteria.andOriginOpenidEqualTo(record.getOriginOpenid());
				}
				if(record.getIsSelfSupport()!=null){
				criteria.andIsSelfSupportEqualTo(record.getIsSelfSupport());
				}
				if(record.getProductDesc()!=null){
				criteria.andProductDescEqualTo(record.getProductDesc());
				}
				if(record.getIsDigit()!=null){
				criteria.andIsDigitEqualTo(record.getIsDigit());
				}
				if(record.getSignType()!=null){
				criteria.andSignTypeEqualTo(record.getSignType());
				}
				if(record.getPropertyName()!=null){
				criteria.andPropertyNameEqualTo(record.getPropertyName());
				}
				if(record.getUnit()!=null){
				criteria.andUnitEqualTo(record.getUnit());
				}
				if(record.getMinCount()!=null){
				criteria.andMinCountEqualTo(record.getMinCount());
				}
				if(record.getCount()!=null){
				criteria.andCountEqualTo(record.getCount());
				}
				if(record.getUrl()!=null){
				criteria.andUrlEqualTo(record.getUrl());
				}
				if(record.getAmount()!=null){
				criteria.andAmountEqualTo(record.getAmount());
				}
				if(record.getDescription()!=null){
				criteria.andDescriptionEqualTo(record.getDescription());
				}
				if(record.getCreateTime()!=null){
				criteria.andCreateTimeEqualTo(record.getCreateTime());
				}
				if(record.getStatus()!=null){
				criteria.andStatusEqualTo(record.getStatus());
				}

		}
		return example;
	}
}
