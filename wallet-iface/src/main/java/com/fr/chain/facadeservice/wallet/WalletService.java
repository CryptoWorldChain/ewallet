package com.fr.chain.facadeservice.wallet;

import java.util.List;

import com.fr.chain.message.Message;
import com.fr.chain.message.MsgBody;
import com.fr.chain.ewallet.db.entity.WalletAdress;
import com.fr.chain.ewallet.db.entity.WalletAdressExample;
import com.fr.chain.ewallet.db.entity.WalletAdressKey;
import com.fr.chain.vo.wallet.QueryWalletAdressVo;
import com.fr.chain.vo.wallet.Res_QueryWalletAdressVo;

public interface WalletService {
	
	public List<String> getWalletAdress(String walletCode, String OpenId);
	
	public String getNewWalletAdress(String walletCode, String OpenId );

	public void getAndCreateWallet(Message msg, QueryWalletAdressVo msgVo, Res_QueryWalletAdressVo res_QueryWalletAdressVo);
	
}
