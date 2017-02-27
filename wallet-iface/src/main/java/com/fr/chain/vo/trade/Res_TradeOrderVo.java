package com.fr.chain.vo.trade;

import java.util.ArrayList;
import java.util.List;

import com.fr.chain.message.MsgBody;
import com.fr.chain.vo.trade.Res_SendPropertyVo.PackageData;

import lombok.Data;

@Data
public class Res_TradeOrderVo extends MsgBody{
		private String openid;
		private String toopenid;
		private String productid;
		private String count;
		private String tradeid;
	
	public Res_TradeOrderVo(String datano) {
		this.datano = datano;
	}
	
}
