package com.fr.chain.facadeservice.wallet;

import java.util.List;

import com.fr.chain.message.Message;
import com.fr.chain.message.MsgBody;
import com.fr.chain.ewallet.db.entity.WalletAdress;
import com.fr.chain.ewallet.db.entity.WalletAdressExample;
import com.fr.chain.ewallet.db.entity.WalletAdressKey;
import com.fr.chain.vo.wallet.QueryWalletAdressVo;

public interface WalletService {
	
    public int insert(WalletAdress info);
	
	public int batchInsert(List<WalletAdress> records);
	
	
	public int deleteByPrimaryKey (WalletAdressKey key);
	
	public List<WalletAdress>  selectByExample(WalletAdress info);
	public List<WalletAdress>  selectByExample(WalletAdressExample info);
	
	public int updateByExampleSelective (WalletAdress record, WalletAdressExample example);

	public Message<MsgBody> processGetWallet(Message<QueryWalletAdressVo> msg);
	
}
