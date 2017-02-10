package com.fr.chain.wallet.service;

import com.fr.chain.ewallet.db.entity.WalletAdressKey;

public interface DelWalletAdressService {
	public int deleteByPrimaryKey (WalletAdressKey key);

}
