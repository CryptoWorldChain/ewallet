package com.fr.chain.vo.trade;

import com.fr.chain.message.MsgBody;

import lombok.Data;

@Data
public class GetPropertyVo extends MsgBody{
	private String productid;
	private String count;
	private String address;
	private String owner;
}
