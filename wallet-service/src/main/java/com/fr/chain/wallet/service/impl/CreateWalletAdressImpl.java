package com.fr.chain.wallet.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fr.chain.ewallet.db.dao.WalletAdressDao;
import com.fr.chain.ewallet.db.entity.WalletAdress;
import com.fr.chain.wallet.service.CreateWalletAdressService;

@Service("createWalletAdressService")
public class CreateWalletAdressImpl implements CreateWalletAdressService {
	
	@Resource 
	WalletAdressDao walletAdressDao;
	
	
	@Override
	public	int insert(WalletAdress info){
		return walletAdressDao.insert(info);			
	}
	
	@Override
	public int insertSelective(WalletAdress record){
		return walletAdressDao.insertSelective(record);
	}

	@Override
	public int batchInsert(List<WalletAdress> records){
		return walletAdressDao.batchInsert(records);
	}
}
