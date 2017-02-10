package com.fr.chain.vo.trade;

import java.util.ArrayList;
import java.util.List;

import com.fr.chain.message.MsgBody;
import com.fr.chain.vo.trade.SendPropertyVo.PackageData;

import lombok.Data;

@Data
public class Res_SendPropertyVo extends MsgBody{
    private String packageid;
	
	private List<PackageData> data;
	
	@Data
	public static class PackageData {
		
		private String productid;
		
		private String count;
		
		private String address;

		public PackageData(){
			
		}
	}
	
	public Res_SendPropertyVo(String datano) {
		this.datano = datano;
		this.data = new  ArrayList<PackageData>();
	}
	
	public Res_SendPropertyVo(){
		this.data = new  ArrayList<PackageData>();
	}
	
}
