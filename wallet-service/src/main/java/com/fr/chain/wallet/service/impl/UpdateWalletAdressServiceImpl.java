package com.fr.chain.wallet.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fr.chain.ewallet.db.dao.WalletAdressDao;
import com.fr.chain.ewallet.db.entity.WalletAdress;
import com.fr.chain.ewallet.db.entity.WalletAdressExample;
import com.fr.chain.wallet.service.UpdateWalletAdressService;

@Service("updateWalletAdressService")
public class UpdateWalletAdressServiceImpl implements UpdateWalletAdressService {
	
	@Resource 
	WalletAdressDao walletAdressDao;
	
	
	@Override
	public int updateByExampleSelective (WalletAdress record, WalletAdressExample example){
		return walletAdressDao.updateByExampleSelective(record, example);			
	}

}
