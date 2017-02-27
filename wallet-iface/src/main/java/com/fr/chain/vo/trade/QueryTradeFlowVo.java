package com.fr.chain.vo.trade;

import com.fr.chain.message.MsgBody;

import lombok.Data;

@Data
public class QueryTradeFlowVo extends MsgBody{
	private String propertytype;
	private String productid;
}
