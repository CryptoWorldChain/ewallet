package com.fr.chain.wallet.service;

import com.fr.chain.ewallet.db.entity.WalletAdress;
import com.fr.chain.ewallet.db.entity.WalletAdressExample;

public interface UpdateWalletAdressService {
	public int updateByExampleSelective (WalletAdress record, WalletAdressExample example);

}
