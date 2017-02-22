package com.fr.chain.trade.db.dao;

import java.util.List;
import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fr.chain.trade.db.entity.TradeOrder;
import com.fr.chain.trade.db.entity.TradeOrderExample;
import com.fr.chain.trade.db.entity.TradeOrderExample.Criteria;
import com.fr.chain.trade.db.entity.TradeOrderKey;

import com.fr.chain.db.iface.StaticTableDaoSupport;

import com.fr.chain.trade.db.mapper.TradeOrderMapper;

@Repository
public class TradeOrderDao implements StaticTableDaoSupport<TradeOrder, TradeOrderExample, TradeOrderKey>{

	@Resource
	private TradeOrderMapper mapper;
	
	
	@Override
	public int countByExample(TradeOrderExample example) {
		return mapper.countByExample(example);
	}

	@Override
	public int deleteByExample(TradeOrderExample example) {
		return mapper.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(TradeOrderKey key) {
		return mapper.deleteByPrimaryKey(key);
	}

	@Override
	public int insert(TradeOrder record)  {
		return mapper.insert(record);
	}

	@Override
	public int insertSelective(TradeOrder record)  {
		return mapper.insertSelective(record);
	}

	@Override
	@Transactional
	public int batchInsert(List<TradeOrder> records)
			 {
		for(TradeOrder record : records){
			mapper.insert(record);
		}
		return records.size();
	}

	@Override
	@Transactional
	public int batchUpdate(List<TradeOrder> records)
			 {
		for(TradeOrder record : records){
			mapper.updateByPrimaryKeySelective(record);
		}
		return records.size();
	}

	@Override
	@Transactional
	public int batchDelete(List<TradeOrder> records)
			 {
		for(TradeOrder record : records){
			mapper.deleteByPrimaryKey(record);
		}
		return records.size();
	}

	@Override
	public List<TradeOrder> selectByExample(TradeOrderExample example)
			 {
		return mapper.selectByExample(example);
	}

	@Override
	public TradeOrder selectByPrimaryKey(TradeOrderKey key)
			 {
		return mapper.selectByPrimaryKey(key);
	}

	@Override
	public List<TradeOrder> findAll(List<TradeOrder> records) {
		if(records==null||records.size()<=0){
			return mapper.selectByExample(new TradeOrderExample());
		}
		List<TradeOrder> list = new ArrayList<>();
		for(TradeOrder record : records){
			TradeOrder result = mapper.selectByPrimaryKey(record);
			if(result!=null){
				list.add(result);
			}
		}
		return list;
	}

	@Override
	public int updateByExampleSelective(TradeOrder record, TradeOrderExample example)  {
		return mapper.updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExample(TradeOrder record, TradeOrderExample example) {
		return mapper.updateByExample(record, example);
	}

	@Override
	public int updateByPrimaryKeySelective(TradeOrder record) {
		return mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(TradeOrder record) {
		return mapper.updateByPrimaryKey(record);
	}

	@Override
	public int sumByExample(TradeOrderExample example) {
		return 0;
	}

	@Override
	public void deleteAll()  {
		mapper.deleteByExample(new TradeOrderExample());
	}

	@Override
	public TradeOrderExample getExample(TradeOrder record) {
		TradeOrderExample example = new TradeOrderExample();
		if(record!=null){
			Criteria criteria = example.createCriteria();
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
				if(record.getFromOpenId()!=null){
				criteria.andFromOpenIdEqualTo(record.getFromOpenId());
				}
				if(record.getToOpenId()!=null){
				criteria.andToOpenIdEqualTo(record.getToOpenId());
				}
				if(record.getOriginOpenid()!=null){
				criteria.andOriginOpenidEqualTo(record.getOriginOpenid());
				}
				if(record.getProductId()!=null){
				criteria.andProductIdEqualTo(record.getProductId());
				}
				if(record.getPropertyType()!=null){
				criteria.andPropertyTypeEqualTo(record.getPropertyType());
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
				if(record.getSigntype()!=null){
				criteria.andSigntypeEqualTo(record.getSigntype());
				}
				if(record.getPropertyName()!=null){
				criteria.andPropertyNameEqualTo(record.getPropertyName());
				}
				if(record.getUnit()!=null){
				criteria.andUnitEqualTo(record.getUnit());
				}
				if(record.getMincount()!=null){
				criteria.andMincountEqualTo(record.getMincount());
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
				if(record.getAddress()!=null){
				criteria.andAddressEqualTo(record.getAddress());
				}
				if(record.getUpdateTime()!=null){
				criteria.andUpdateTimeEqualTo(record.getUpdateTime());
				}
				if(record.getCreateTime()!=null){
				criteria.andCreateTimeEqualTo(record.getCreateTime());
				}
				if(record.getTradeType()!=null){
				criteria.andTradeTypeEqualTo(record.getTradeType());
				}
				if(record.getStatus()!=null){
				criteria.andStatusEqualTo(record.getStatus());
				}

		}
		return example;
	}
}
