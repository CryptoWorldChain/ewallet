package com.fr.chain.ewallet.db.dao;

import java.util.List;
import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fr.chain.ewallet.db.entity.WalletAdress;
import com.fr.chain.ewallet.db.entity.WalletAdressExample;
import com.fr.chain.ewallet.db.entity.WalletAdressExample.Criteria;
import com.fr.chain.ewallet.db.entity.WalletAdressKey;

import com.fr.chain.db.iface.StaticTableDaoSupport;

import com.fr.chain.ewallet.db.mapper.WalletAdressMapper;

@Repository
public class WalletAdressDao implements StaticTableDaoSupport<WalletAdress, WalletAdressExample, WalletAdressKey>{

	@Resource
	private WalletAdressMapper mapper;
	
	
	@Override
	public int countByExample(WalletAdressExample example) {
		return mapper.countByExample(example);
	}

	@Override
	public int deleteByExample(WalletAdressExample example) {
		return mapper.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(WalletAdressKey key) {
		return mapper.deleteByPrimaryKey(key);
	}

	@Override
	public int insert(WalletAdress record)  {
		return mapper.insert(record);
	}

	@Override
	public int insertSelective(WalletAdress record)  {
		return mapper.insertSelective(record);
	}

	@Override
	@Transactional
	public int batchInsert(List<WalletAdress> records)
			 {
		for(WalletAdress record : records){
			mapper.insert(record);
		}
		return records.size();
	}

	@Override
	@Transactional
	public int batchUpdate(List<WalletAdress> records)
			 {
		for(WalletAdress record : records){
			mapper.updateByPrimaryKeySelective(record);
		}
		return records.size();
	}

	@Override
	@Transactional
	public int batchDelete(List<WalletAdress> records)
			 {
		for(WalletAdress record : records){
			mapper.deleteByPrimaryKey(record);
		}
		return records.size();
	}

	@Override
	public List<WalletAdress> selectByExample(WalletAdressExample example)
			 {
		return mapper.selectByExample(example);
	}

	@Override
	public WalletAdress selectByPrimaryKey(WalletAdressKey key)
			 {
		return mapper.selectByPrimaryKey(key);
	}

	@Override
	public List<WalletAdress> findAll(List<WalletAdress> records) {
		if(records==null||records.size()<=0){
			return mapper.selectByExample(new WalletAdressExample());
		}
		List<WalletAdress> list = new ArrayList<>();
		for(WalletAdress record : records){
			WalletAdress result = mapper.selectByPrimaryKey(record);
			if(result!=null){
				list.add(result);
			}
		}
		return list;
	}

	@Override
	public int updateByExampleSelective(WalletAdress record, WalletAdressExample example)  {
		return mapper.updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExample(WalletAdress record, WalletAdressExample example) {
		return mapper.updateByExample(record, example);
	}

	@Override
	public int updateByPrimaryKeySelective(WalletAdress record) {
		return mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(WalletAdress record) {
		return mapper.updateByPrimaryKey(record);
	}

	@Override
	public int sumByExample(WalletAdressExample example) {
		return 0;
	}

	@Override
	public void deleteAll()  {
		mapper.deleteByExample(new WalletAdressExample());
	}

	@Override
	public WalletAdressExample getExample(WalletAdress record) {
		WalletAdressExample example = new WalletAdressExample();
		if(record!=null){
			Criteria criteria = example.createCriteria();
							if(record.getWalletId()!=null){
				criteria.andWalletIdEqualTo(record.getWalletId());
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
				if(record.getWalletCode()!=null){
				criteria.andWalletCodeEqualTo(record.getWalletCode());
				}
				if(record.getWalletAddress()!=null){
				criteria.andWalletAddressEqualTo(record.getWalletAddress());
				}
				if(record.getCreateTime()!=null){
				criteria.andCreateTimeEqualTo(record.getCreateTime());
				}

		}
		return example;
	}
}
