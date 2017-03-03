package com.fr.chain.vo.trade;


import com.fr.chain.message.MsgBody;

import lombok.Data;

@Data
public class Res_TransDigitVo extends MsgBody{
		private String openid;
		private String toopenid;
		private String productid;
		private String count;
		private String tradeid;
	
	public Res_TransDigitVo(String datano) {
		this.datano = datano;
	}
	
}
