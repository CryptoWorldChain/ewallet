package com.fr.chain.vo.property;

import com.fr.chain.message.MsgBody;

import lombok.Data;

@Data
public class CreatePropertyVo extends MsgBody{
	private String productid;
	private String productdesc;
	private String signtype;
	private String propertyname;	
	private String unit;
	private String mincount;
	private String count;
	private String url;
	private String description;
}
