package com.fr.chain.vo.wallet;

import com.fr.chain.message.MsgBody;

import lombok.Data;

@Data
public class Res_QueryWalletAdressVo extends MsgBody{
	private String walletcode;
	private String walletaddress;	
	
	public Res_QueryWalletAdressVo(){		
	}
	
	public Res_QueryWalletAdressVo(String datano){
		this.datano = datano;
	}
	
	
	
}
