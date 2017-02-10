package com.fr.chain.vo.trade;

import com.fr.chain.message.MsgBody;

import lombok.Data;

@Data
public class QueryTradeOrderVo extends MsgBody{
	private String propertytype;
	private String isselfsupport;
	private String productid;
	private String isdigit;
	private String signtype;
	private String status;	
}
