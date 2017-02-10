package com.fr.chain.vo.trade;

import com.fr.chain.message.MsgBody;

import lombok.Data;

@Data
public class Res_QueryTradeOrderVo extends MsgBody{
	private String propertytype;
	private String isselfsupport;
	private String productid;
	private String isdigit;
	private String signtype;
	private String propertyname;	
	private String unit;
	private String count;
	private String address;
	private String tradetype;
	
	public Res_QueryTradeOrderVo(){		
	}
	
	public Res_QueryTradeOrderVo(String datano){
		this.datano = datano;
	}
}
