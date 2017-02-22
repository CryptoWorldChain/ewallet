package com.fr.chain.trade.db.dao;

import java.util.List;
import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fr.chain.trade.db.entity.TradeFlow;
import com.fr.chain.trade.db.entity.TradeFlowExample;
import com.fr.chain.trade.db.entity.TradeFlowExample.Criteria;
import com.fr.chain.trade.db.entity.TradeFlowKey;

import com.fr.chain.db.iface.StaticTableDaoSupport;

import com.fr.chain.trade.db.mapper.TradeFlowMapper;

@Repository
public class TradeFlowDao implements StaticTableDaoSupport<TradeFlow, TradeFlowExample, TradeFlowKey>{

	@Resource
	private TradeFlowMapper mapper;
	
	
	@Override
	public int countByExample(TradeFlowExample example) {
		return mapper.countByExample(example);
	}

	@Override
	public int deleteByExample(TradeFlowExample example) {
		return mapper.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(TradeFlowKey key) {
		return mapper.deleteByPrimaryKey(key);
	}

	@Override
	public int insert(TradeFlow record)  {
		return mapper.insert(record);
	}

	@Override
	public int insertSelective(TradeFlow record)  {
		return mapper.insertSelective(record);
	}

	@Override
	@Transactional
	public int batchInsert(List<TradeFlow> records)
			 {
		for(TradeFlow record : records){
			mapper.insert(record);
		}
		return records.size();
	}

	@Override
	@Transactional
	public int batchUpdate(List<TradeFlow> records)
			 {
		for(TradeFlow record : records){
			mapper.updateByPrimaryKeySelective(record);
		}
		return records.size();
	}

	@Override
	@Transactional
	public int batchDelete(List<TradeFlow> records)
			 {
		for(TradeFlow record : records){
			mapper.deleteByPrimaryKey(record);
		}
		return records.size();
	}

	@Override
	public List<TradeFlow> selectByExample(TradeFlowExample example)
			 {
		return mapper.selectByExample(example);
	}

	@Override
	public TradeFlow selectByPrimaryKey(TradeFlowKey key)
			 {
		return mapper.selectByPrimaryKey(key);
	}

	@Override
	public List<TradeFlow> findAll(List<TradeFlow> records) {
		if(records==null||records.size()<=0){
			return mapper.selectByExample(new TradeFlowExample());
		}
		List<TradeFlow> list = new ArrayList<>();
		for(TradeFlow record : records){
			TradeFlow result = mapper.selectByPrimaryKey(record);
			if(result!=null){
				list.add(result);
			}
		}
		return list;
	}

	@Override
	public int updateByExampleSelective(TradeFlow record, TradeFlowExample example)  {
		return mapper.updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExample(TradeFlow record, TradeFlowExample example) {
		return mapper.updateByExample(record, example);
	}

	@Override
	public int updateByPrimaryKeySelective(TradeFlow record) {
		return mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(TradeFlow record) {
		return mapper.updateByPrimaryKey(record);
	}

	@Override
	public int sumByExample(TradeFlowExample example) {
		return 0;
	}

	@Override
	public void deleteAll()  {
		mapper.deleteByExample(new TradeFlowExample());
	}

	@Override
	public TradeFlowExample getExample(TradeFlow record) {
		TradeFlowExample example = new TradeFlowExample();
		if(record!=null){
			Criteria criteria = example.createCriteria();
							if(record.getFlowId()!=null){
				criteria.andFlowIdEqualTo(record.getFlowId());
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
				if(record.getTallyTag()!=null){
				criteria.andTallyTagEqualTo(record.getTallyTag());
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
				if(record.getTradeType()!=null){
				criteria.andTradeTypeEqualTo(record.getTradeType());
				}
				if(record.getCreateTime()!=null){
				criteria.andCreateTimeEqualTo(record.getCreateTime());
				}
				if(record.getUpdateTime()!=null){
				criteria.andUpdateTimeEqualTo(record.getUpdateTime());
				}

		}
		return example;
	}
}
