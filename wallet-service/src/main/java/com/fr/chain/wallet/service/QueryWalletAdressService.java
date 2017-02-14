package com.fr.chain.wallet.service;

import java.util.List;

import com.fr.chain.ewallet.db.entity.WalletAdress;
import com.fr.chain.ewallet.db.entity.WalletAdressExample;

public interface QueryWalletAdressService {
	public List<WalletAdress>  selectByExample(WalletAdress info);
	public WalletAdress  selectOneByExample(WalletAdress info);
	public List<WalletAdress>  selectByExample(WalletAdressExample info);

}
