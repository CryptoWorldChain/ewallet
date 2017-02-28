package com.fr.chain.vo.trade;

import java.util.ArrayList;
import java.util.List;

import com.fr.chain.message.MsgBody;
import com.fr.chain.vo.trade.Res_SendPropertyVo.PackageData;

import lombok.Data;

@Data
public class Res_QueryTradeFlowVo extends MsgBody{
	private List<TradeOrderData> data;
	
	@Data
	public static class TradeOrderData {		
		private String propertytype;
		private String propertyname;	
		private String productid;
		private String signtype;
		private String tradetype;
		private String unit;
		private String count;
		private String tradetime;
		public TradeOrderData(){			
		}
	}
	
	public Res_QueryTradeFlowVo(String datano) {
		this.datano = datano;
		this.data = new  ArrayList<TradeOrderData>();
	}
	
	public Res_QueryTradeFlowVo(){
		this.data = new  ArrayList<TradeOrderData>();
	}
}
