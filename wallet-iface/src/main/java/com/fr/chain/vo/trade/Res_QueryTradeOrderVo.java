package com.fr.chain.vo.trade;

import java.util.ArrayList;
import java.util.List;

import com.fr.chain.message.MsgBody;
import com.fr.chain.vo.trade.Res_SendPropertyVo.PackageData;

import lombok.Data;

@Data
public class Res_QueryTradeOrderVo extends MsgBody{
	private List<TradeOrderData> data;
	
	@Data
	public static class TradeOrderData {		
		private String propertytype;
		private String isselfsupport;
		private String productid;
		private String isdigit;
		private String signtype;
		private String propertyname;	
		private String unit;
		private String count;
		private String address;
		private int tradetype;
		public TradeOrderData(){			
		}
	}
	
	public Res_QueryTradeOrderVo(String datano) {
		this.datano = datano;
		this.data = new  ArrayList<TradeOrderData>();
	}
	
	public Res_QueryTradeOrderVo(){
		this.data = new  ArrayList<TradeOrderData>();
	}
}
