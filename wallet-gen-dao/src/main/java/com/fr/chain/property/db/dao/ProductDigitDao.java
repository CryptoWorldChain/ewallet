package com.fr.chain.property.db.dao;

import java.util.List;
import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fr.chain.property.db.entity.ProductDigit;
import com.fr.chain.property.db.entity.ProductDigitExample;
import com.fr.chain.property.db.entity.ProductDigitExample.Criteria;
import com.fr.chain.property.db.entity.ProductDigitKey;

import com.fr.chain.db.iface.StaticTableDaoSupport;

import com.fr.chain.property.db.mapper.ProductDigitMapper;

@Repository
public class ProductDigitDao implements StaticTableDaoSupport<ProductDigit, ProductDigitExample, ProductDigitKey>{

	@Resource
	private ProductDigitMapper mapper;
	
	
	@Override
	public int countByExample(ProductDigitExample example) {
		return mapper.countByExample(example);
	}

	@Override
	public int deleteByExample(ProductDigitExample example) {
		return mapper.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(ProductDigitKey key) {
		return mapper.deleteByPrimaryKey(key);
	}

	@Override
	public int insert(ProductDigit record)  {
		return mapper.insert(record);
	}

	@Override
	public int insertSelective(ProductDigit record)  {
		return mapper.insertSelective(record);
	}

	@Override
	@Transactional
	public int batchInsert(List<ProductDigit> records)
			 {
		for(ProductDigit record : records){
			mapper.insert(record);
		}
		return records.size();
	}

	@Override
	@Transactional
	public int batchUpdate(List<ProductDigit> records)
			 {
		for(ProductDigit record : records){
			mapper.updateByPrimaryKeySelective(record);
		}
		return records.size();
	}

	@Override
	@Transactional
	public int batchDelete(List<ProductDigit> records)
			 {
		for(ProductDigit record : records){
			mapper.deleteByPrimaryKey(record);
		}
		return records.size();
	}

	@Override
	public List<ProductDigit> selectByExample(ProductDigitExample example)
			 {
		return mapper.selectByExample(example);
	}

	@Override
	public ProductDigit selectByPrimaryKey(ProductDigitKey key)
			 {
		return mapper.selectByPrimaryKey(key);
	}

	@Override
	public List<ProductDigit> findAll(List<ProductDigit> records) {
		if(records==null||records.size()<=0){
			return mapper.selectByExample(new ProductDigitExample());
		}
		List<ProductDigit> list = new ArrayList<>();
		for(ProductDigit record : records){
			ProductDigit result = mapper.selectByPrimaryKey(record);
			if(result!=null){
				list.add(result);
			}
		}
		return list;
	}

	@Override
	public int updateByExampleSelective(ProductDigit record, ProductDigitExample example)  {
		return mapper.updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExample(ProductDigit record, ProductDigitExample example) {
		return mapper.updateByExample(record, example);
	}

	@Override
	public int updateByPrimaryKeySelective(ProductDigit record) {
		return mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(ProductDigit record) {
		return mapper.updateByPrimaryKey(record);
	}

	@Override
	public int sumByExample(ProductDigitExample example) {
		return 0;
	}

	@Override
	public void deleteAll()  {
		mapper.deleteByExample(new ProductDigitExample());
	}

	@Override
	public ProductDigitExample getExample(ProductDigit record) {
		ProductDigitExample example = new ProductDigitExample();
		if(record!=null){
			Criteria criteria = example.createCriteria();
							if(record.getProductId()!=null){
				criteria.andProductIdEqualTo(record.getProductId());
				}
				if(record.getMerchantId()!=null){
				criteria.andMerchantIdEqualTo(record.getMerchantId());
				}
				if(record.getAppId()!=null){
				criteria.andAppIdEqualTo(record.getAppId());
				}
				if(record.getProductDesc()!=null){
				criteria.andProductDescEqualTo(record.getProductDesc());
				}
				if(record.getChainType()!=null){
				criteria.andChainTypeEqualTo(record.getChainType());
				}
				if(record.getPropertyType()!=null){
				criteria.andPropertyTypeEqualTo(record.getPropertyType());
				}
				if(record.getPropertyCode()!=null){
				criteria.andPropertyCodeEqualTo(record.getPropertyCode());
				}
				if(record.getOriginOpenid()!=null){
				criteria.andOriginOpenidEqualTo(record.getOriginOpenid());
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
				if(record.getUrl()!=null){
				criteria.andUrlEqualTo(record.getUrl());
				}
				if(record.getDescription()!=null){
				criteria.andDescriptionEqualTo(record.getDescription());
				}
				if(record.getCreateTime()!=null){
				criteria.andCreateTimeEqualTo(record.getCreateTime());
				}
				if(record.getUpdateTime()!=null){
				criteria.andUpdateTimeEqualTo(record.getUpdateTime());
				}
				if(record.getStatus()!=null){
				criteria.andStatusEqualTo(record.getStatus());
				}

		}
		return example;
	}
}
