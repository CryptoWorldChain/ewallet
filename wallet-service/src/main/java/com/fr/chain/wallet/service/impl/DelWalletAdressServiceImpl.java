package com.fr.chain.wallet.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fr.chain.ewallet.db.dao.WalletAdressDao;
import com.fr.chain.ewallet.db.entity.WalletAdressKey;
import com.fr.chain.wallet.service.DelWalletAdressService;

@Service("delWalletAdressService")
public class DelWalletAdressServiceImpl implements DelWalletAdressService {
	
	@Resource 
	WalletAdressDao walletAdressDao;
	
	
	@Override
	public	int deleteByPrimaryKey (WalletAdressKey key){
		return walletAdressDao.deleteByPrimaryKey(key);			
	}

}
