package com.fr.chain.vo.trade;

import java.util.List;

import com.fr.chain.message.MsgBody;

import lombok.Data;

@Data
public class SendPropertyVo extends MsgBody{
	
	private String packageid;
	private String propertytype;
	
	private List<PackageData> data;
	
	@Data
	public static class PackageData {
		
		private String productid;
		
		private String count;

	}
}
