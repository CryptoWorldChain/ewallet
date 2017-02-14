package com.fr.chain.vo.trade;

import java.util.List;

import com.fr.chain.message.MsgBody;
import com.fr.chain.vo.trade.SendPropertyVo.PackageData;

import lombok.Data;

@Data
public class GetPropertyVo extends MsgBody{
	private List<PackageData> data;
	@Data
	public static class PackageData {
		private String productid;
		private String count;
		private String address;
		private String owner;
		public PackageData(){
			
		}
	}
}
