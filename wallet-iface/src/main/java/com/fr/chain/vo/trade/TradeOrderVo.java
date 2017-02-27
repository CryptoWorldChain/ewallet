package com.fr.chain.vo.trade;

import com.fr.chain.message.MsgBody;

import lombok.Data;

@Data
public class TradeOrderVo extends MsgBody{
	private String productid;
	private String toopenid;
	private String count;
}
