package com.fr.chain.wallet.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fr.chain.ewallet.db.dao.WalletAdressDao;
import com.fr.chain.ewallet.db.entity.WalletAdress;
import com.fr.chain.ewallet.db.entity.WalletAdressExample;
import com.fr.chain.property.service.QueryPropertyService;
import com.fr.chain.property.db.dao.PropertyDao;
import com.fr.chain.property.db.entity.Property;
import com.fr.chain.property.db.entity.PropertyExample;
import com.fr.chain.wallet.service.QueryWalletAdressService;

@Service("queryWalletAdressService")
public class QueryWalletAdressServiceImpl implements QueryWalletAdressService {
	
	@Resource 
	WalletAdressDao walletAdressDao;
	
	
	@Override
	public List<WalletAdress>  selectByExample(WalletAdress info){
		return  walletAdressDao.selectByExample(walletAdressDao.getExample(info));		
	}

	@Override
	public List<WalletAdress>  selectByExample(WalletAdressExample info){
		return  walletAdressDao.selectByExample(info);	
	}
	
	@Override
	public WalletAdress  selectOneByExample(WalletAdress info){
		List<WalletAdress>  walletAdressList = walletAdressDao.selectByExample(walletAdressDao.getExample(info));
		if(walletAdressList != null && walletAdressList.size() > 0){
			return walletAdressList.get(0);
		}		
		return null;
	}
}
