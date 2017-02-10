package com.fr.chain.wallet.service;

import java.util.List;

import com.fr.chain.ewallet.db.entity.WalletAdress;

public interface CreateWalletAdressService {
	public int insert(WalletAdress info);
	
	public int batchInsert(List<WalletAdress> records);
	
}
