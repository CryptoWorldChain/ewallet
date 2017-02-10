package com.fr.chain.vo.wallet;

import com.fr.chain.message.MsgBody;

import lombok.Data;

@Data
public class QueryWalletAdressVo extends MsgBody{
	private String walletcode;
}
