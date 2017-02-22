package com.fr.chain.chaintrade.db.dao;

import java.util.List;
import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fr.chain.chaintrade.db.entity.ChainTrade;
import com.fr.chain.chaintrade.db.entity.ChainTradeExample;
import com.fr.chain.chaintrade.db.entity.ChainTradeExample.Criteria;
import com.fr.chain.chaintrade.db.entity.ChainTradeKey;

import com.fr.chain.db.iface.StaticTableDaoSupport;

import com.fr.chain.chaintrade.db.mapper.ChainTradeMapper;

@Repository
public class ChainTradeDao implements StaticTableDaoSupport<ChainTrade, ChainTradeExample, ChainTradeKey>{

	@Resource
	private ChainTradeMapper mapper;
	
	
	@Override
	public int countByExample(ChainTradeExample example) {
		return mapper.countByExample(example);
	}

	@Override
	public int deleteByExample(ChainTradeExample example) {
		return mapper.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(ChainTradeKey key) {
		return mapper.deleteByPrimaryKey(key);
	}

	@Override
	public int insert(ChainTrade record)  {
		return mapper.insert(record);
	}

	@Override
	public int insertSelective(ChainTrade record)  {
		return mapper.insertSelective(record);
	}

	@Override
	@Transactional
	public int batchInsert(List<ChainTrade> records)
			 {
		for(ChainTrade record : records){
			mapper.insert(record);
		}
		return records.size();
	}

	@Override
	@Transactional
	public int batchUpdate(List<ChainTrade> records)
			 {
		for(ChainTrade record : records){
			mapper.updateByPrimaryKeySelective(record);
		}
		return records.size();
	}

	@Override
	@Transactional
	public int batchDelete(List<ChainTrade> records)
			 {
		for(ChainTrade record : records){
			mapper.deleteByPrimaryKey(record);
		}
		return records.size();
	}

	@Override
	public List<ChainTrade> selectByExample(ChainTradeExample example)
			 {
		return mapper.selectByExample(example);
	}

	@Override
	public ChainTrade selectByPrimaryKey(ChainTradeKey key)
			 {
		return mapper.selectByPrimaryKey(key);
	}

	@Override
	public List<ChainTrade> findAll(List<ChainTrade> records) {
		if(records==null||records.size()<=0){
			return mapper.selectByExample(new ChainTradeExample());
		}
		List<ChainTrade> list = new ArrayList<>();
		for(ChainTrade record : records){
			ChainTrade result = mapper.selectByPrimaryKey(record);
			if(result!=null){
				list.add(result);
			}
		}
		return list;
	}

	@Override
	public int updateByExampleSelective(ChainTrade record, ChainTradeExample example)  {
		return mapper.updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExample(ChainTrade record, ChainTradeExample example) {
		return mapper.updateByExample(record, example);
	}

	@Override
	public int updateByPrimaryKeySelective(ChainTrade record) {
		return mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(ChainTrade record) {
		return mapper.updateByPrimaryKey(record);
	}

	@Override
	public int sumByExample(ChainTradeExample example) {
		return 0;
	}

	@Override
	public void deleteAll()  {
		mapper.deleteByExample(new ChainTradeExample());
	}

	@Override
	public ChainTradeExample getExample(ChainTrade record) {
		ChainTradeExample example = new ChainTradeExample();
		if(record!=null){
			Criteria criteria = example.createCriteria();
							if(record.getChainTradeId()!=null){
				criteria.andChainTradeIdEqualTo(record.getChainTradeId());
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
				if(record.getOrderId()!=null){
				criteria.andOrderIdEqualTo(record.getOrderId());
				}
				if(record.getTxid()!=null){
				criteria.andTxidEqualTo(record.getTxid());
				}
				if(record.getChainCode()!=null){
				criteria.andChainCodeEqualTo(record.getChainCode());
				}
				if(record.getProductId()!=null){
				criteria.andProductIdEqualTo(record.getProductId());
				}
				if(record.getIsDigit()!=null){
				criteria.andIsDigitEqualTo(record.getIsDigit());
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
				if(record.getAmount()!=null){
				criteria.andAmountEqualTo(record.getAmount());
				}
				if(record.getCreateTime()!=null){
				criteria.andCreateTimeEqualTo(record.getCreateTime());
				}
				if(record.getUpdateTime()!=null){
				criteria.andUpdateTimeEqualTo(record.getUpdateTime());
				}
				if(record.getProcessStatus()!=null){
				criteria.andProcessStatusEqualTo(record.getProcessStatus());
				}

		}
		return example;
	}
}
