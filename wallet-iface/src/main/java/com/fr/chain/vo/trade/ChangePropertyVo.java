package com.fr.chain.vo.trade;

import com.fr.chain.message.MsgBody;

import lombok.Data;

@Data
public class ChangePropertyVo extends MsgBody{
	private String productid;
	private String propertyType;
	private String tradetype;
}
