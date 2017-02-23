package com.fr.chain.property.db.dao;

import java.util.List;
import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fr.chain.property.db.entity.ProductInfo;
import com.fr.chain.property.db.entity.ProductInfoExample;
import com.fr.chain.property.db.entity.ProductInfoExample.Criteria;
import com.fr.chain.property.db.entity.ProductInfoKey;

import com.fr.chain.db.iface.StaticTableDaoSupport;

import com.fr.chain.property.db.mapper.ProductInfoMapper;

@Repository
public class ProductInfoDao implements StaticTableDaoSupport<ProductInfo, ProductInfoExample, ProductInfoKey>{

	@Resource
	private ProductInfoMapper mapper;
	
	
	@Override
	public int countByExample(ProductInfoExample example) {
		return mapper.countByExample(example);
	}

	@Override
	public int deleteByExample(ProductInfoExample example) {
		return mapper.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(ProductInfoKey key) {
		return mapper.deleteByPrimaryKey(key);
	}

	@Override
	public int insert(ProductInfo record)  {
		return mapper.insert(record);
	}

	@Override
	public int insertSelective(ProductInfo record)  {
		return mapper.insertSelective(record);
	}

	@Override
	@Transactional
	public int batchInsert(List<ProductInfo> records)
			 {
		for(ProductInfo record : records){
			mapper.insert(record);
		}
		return records.size();
	}

	@Override
	@Transactional
	public int batchUpdate(List<ProductInfo> records)
			 {
		for(ProductInfo record : records){
			mapper.updateByPrimaryKeySelective(record);
		}
		return records.size();
	}

	@Override
	@Transactional
	public int batchDelete(List<ProductInfo> records)
			 {
		for(ProductInfo record : records){
			mapper.deleteByPrimaryKey(record);
		}
		return records.size();
	}

	@Override
	public List<ProductInfo> selectByExample(ProductInfoExample example)
			 {
		return mapper.selectByExample(example);
	}

	@Override
	public ProductInfo selectByPrimaryKey(ProductInfoKey key)
			 {
		return mapper.selectByPrimaryKey(key);
	}

	@Override
	public List<ProductInfo> findAll(List<ProductInfo> records) {
		if(records==null||records.size()<=0){
			return mapper.selectByExample(new ProductInfoExample());
		}
		List<ProductInfo> list = new ArrayList<>();
		for(ProductInfo record : records){
			ProductInfo result = mapper.selectByPrimaryKey(record);
			if(result!=null){
				list.add(result);
			}
		}
		return list;
	}

	@Override
	public int updateByExampleSelective(ProductInfo record, ProductInfoExample example)  {
		return mapper.updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExample(ProductInfo record, ProductInfoExample example) {
		return mapper.updateByExample(record, example);
	}

	@Override
	public int updateByPrimaryKeySelective(ProductInfo record) {
		return mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(ProductInfo record) {
		return mapper.updateByPrimaryKey(record);
	}

	@Override
	public int sumByExample(ProductInfoExample example) {
		return 0;
	}

	@Override
	public void deleteAll()  {
		mapper.deleteByExample(new ProductInfoExample());
	}

	@Override
	public ProductInfoExample getExample(ProductInfo record) {
		ProductInfoExample example = new ProductInfoExample();
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
