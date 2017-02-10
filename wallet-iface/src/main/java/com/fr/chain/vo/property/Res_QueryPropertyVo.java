package com.fr.chain.vo.property;

import com.fr.chain.message.MsgBody;

import lombok.Data;

@Data
public class Res_QueryPropertyVo extends MsgBody{
	private String propertytype;
	private String isselfsupport;
	private String productid;
	private String productdesc;
	private String isdigit;
	private String signtype;
	private String propertyname;	
	private String unit;
	private String mincount;
	private String count;
	private String url;
	private String amount;
	private String description;
	
	
	public Res_QueryPropertyVo(){		
	}
	
	public Res_QueryPropertyVo(String datano){
		this.datano = datano;
	}
}
