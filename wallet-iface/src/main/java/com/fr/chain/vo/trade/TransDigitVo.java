package com.fr.chain.vo.trade;

import com.fr.chain.message.MsgBody;

import lombok.Data;

@Data
public class TransDigitVo extends MsgBody{
	private String productid;
	private String toopenid;
	private String count;
}
