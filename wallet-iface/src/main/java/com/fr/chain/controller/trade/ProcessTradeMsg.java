package com.fr.chain.controller.trade;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.codehaus.jackson.JsonNode;
import org.springframework.stereotype.Component;

import com.fr.chain.enums.BaseStatusEnum;
import com.fr.chain.facadeservice.trade.TradeOrderService;
import com.fr.chain.message.Message;
import com.fr.chain.message.MsgBody;
import com.fr.chain.message.ResponseMsg;
import com.fr.chain.utils.BeanFactory;
import com.fr.chain.utils.JsonUtil;
import com.fr.chain.utils.StringUtil;
import com.fr.chain.vo.trade.ChangePropertyVo;
import com.fr.chain.vo.trade.GetPropertyVo;
import com.fr.chain.vo.trade.QueryTradeOrderVo;
import com.fr.chain.vo.trade.Res_QueryTradeOrderVo;
import com.fr.chain.vo.trade.Res_SendPropertyVo;
import com.fr.chain.vo.trade.SendPropertyVo;


@Slf4j
@Component
public class ProcessTradeMsg {
	@Resource
	private TradeOrderService tradeOrderService;
	
	private void buildQueryTradeOrderBody(Message<QueryTradeOrderVo> gpmsg) {
		List<QueryTradeOrderVo> bodys = new ArrayList<>();
		for (JsonNode node : gpmsg.getAnDatas()) {
			bodys.add(JsonUtil.json2Bean(node, QueryTradeOrderVo.class));
		} 
		gpmsg.setBodyDatas(bodys);
	}
	
	private void buildSendPropertyBody(Message<SendPropertyVo> gpmsg) {
		List<SendPropertyVo> bodys = new ArrayList<>();
		for (JsonNode node : gpmsg.getAnDatas()) {
			bodys.add(JsonUtil.json2Bean(node, SendPropertyVo.class));
		} 
		gpmsg.setBodyDatas(bodys);
	}
	
	private void buildGetPropertyBody(Message<GetPropertyVo> gpmsg) {
		List<GetPropertyVo> bodys = new ArrayList<>();
		for (JsonNode node : gpmsg.getAnDatas()) {
			bodys.add(JsonUtil.json2Bean(node, GetPropertyVo.class));
		} 
		gpmsg.setBodyDatas(bodys);
	}
	
	private void buildChangePropertyBody(Message<ChangePropertyVo> gpmsg) {
		List<ChangePropertyVo> bodys = new ArrayList<>();
		for (JsonNode node : gpmsg.getAnDatas()) {
			bodys.add(JsonUtil.json2Bean(node, ChangePropertyVo.class));
		} 
		gpmsg.setBodyDatas(bodys);
	}
	
	
	public Message<MsgBody> processQueryTradeOrder(Message<QueryTradeOrderVo> msg){
		buildQueryTradeOrderBody(msg);
		log.debug("QueryTradeOrder msg:" + msg);
		Map<String,MsgBody> resp = new LinkedHashMap<String, MsgBody>();
		try {
			List<QueryTradeOrderVo> datas = msg.getBodyDatas();
			if (datas != null && datas.size() > 0) {
				for (QueryTradeOrderVo msgVo : datas) {
					try{					
						this.validNull(msgVo);						
						//新建返回报文
						Res_QueryTradeOrderVo res_QueryTradeOrderVo =new Res_QueryTradeOrderVo(msgVo.getDatano());
						//具体每笔业务
						tradeOrderService.queryAndCreateTradeOrder(msg, msgVo, res_QueryTradeOrderVo);
						//设置返回报文
						resp.put(msgVo.getDatano(), res_QueryTradeOrderVo);
					}catch (NullPointerException ne) {
						log.error("QueryTradeOrder is failed:" + ne.getMessage(), ne);
						resp.put(msgVo.getDatano(), new ResponseMsg(msgVo.getDatano(),BaseStatusEnum.失败.getCode().toString(),ne.getMessage()));
					}catch (IllegalArgumentException ile) {
						log.error("QueryTradeOrder is failed:" + ile.getMessage(), ile);
						resp.put(msgVo.getDatano(), new ResponseMsg(msgVo.getDatano(),BaseStatusEnum.失败.getCode().toString(),ile.getMessage()));
					}catch (Exception e) {
						log.error("QueryTradeOrder is failed:" + e.getMessage(), e);
						resp.put(msgVo.getDatano(), new ResponseMsg(msgVo.getDatano(),BaseStatusEnum.失败.getCode().toString(),e.getMessage()));
					}
				}				
			}
		}		
		catch (Exception e) {
			log.error("QueryTradeOrder is failed:" + e.getMessage(), e);
			Map<String, MsgBody> errResp = new LinkedHashMap<String, MsgBody>();
			for (MsgBody body : msg.getBodyDatas()) {	
				resp.put(body.getDatano(), new ResponseMsg(body.getDatano(),BaseStatusEnum.失败.getCode().toString(), e.getMessage()));
			}
			return msg.asResponse(errResp);			
		}
		//回复所有报文
		return msg.asResponse(resp);	
	}
	

	public Message<MsgBody> processSendProperty(Message<SendPropertyVo> msg){
		buildSendPropertyBody(msg);
		log.debug("SendProperty msg:" + msg);
		Map<String,MsgBody> resp = new LinkedHashMap<String, MsgBody>();
		try {
			List<SendPropertyVo> datas = msg.getBodyDatas();
			if (datas != null && datas.size() > 0) {
				for (SendPropertyVo msgVo : datas) {
					try{	
						//验证报文信息
						this.validNull(msgVo);
						//构建对应的返回报文
						Res_SendPropertyVo res_SendPropertyVo =new Res_SendPropertyVo(msgVo.getDatano());
						//具体每笔业务
						tradeOrderService.sendAndCreateProperty(msg, msgVo, res_SendPropertyVo);
						//设置返回报文
						resp.put(msgVo.getDatano(), res_SendPropertyVo);
					}catch (NullPointerException ne) {
						log.error("SendProperty is failed:" + ne.getMessage(), ne);
						resp.put(msgVo.getDatano(), new ResponseMsg(msgVo.getDatano(),BaseStatusEnum.失败.getCode().toString(),ne.getMessage()));
					}
					catch (IllegalArgumentException ile) {
						log.error("SendProperty is failed:" + ile.getMessage(), ile);
						resp.put(msgVo.getDatano(), new ResponseMsg(msgVo.getDatano(),BaseStatusEnum.失败.getCode().toString(),ile.getMessage()));
					}
					catch (Exception e) {
						log.error("SendProperty is failed:" + e.getMessage(), e);
						resp.put(msgVo.getDatano(), new ResponseMsg(msgVo.getDatano(),BaseStatusEnum.失败.getCode().toString(),e.getMessage()));
					}
				}
			}
		}		
		catch (Exception e) {
			log.error("SendProperty is failed:" + e.getMessage(), e);
			Map<String, MsgBody> errResp = new LinkedHashMap<String, MsgBody>();
			for (MsgBody body : msg.getBodyDatas()) {	
				resp.put(body.getDatano(), new ResponseMsg(body.getDatano(),BaseStatusEnum.失败.getCode().toString(), e.getMessage()));
			}
			return msg.asResponse(errResp);			
		}
		//回复所有报文
		return msg.asResponse(resp);
	}
	
	
	
	public Message<MsgBody> processGetProperty(Message<GetPropertyVo> msg){
		buildGetPropertyBody(msg);
		log.debug("GetProperty msg:" + msg);
		Map<String,MsgBody> resp = new LinkedHashMap<String, MsgBody>();
		try {
			List<GetPropertyVo> datas = msg.getBodyDatas();
			if (datas != null && datas.size() > 0) {
				for (GetPropertyVo msgVo : datas) {
					try{					
						//验证报文信息
						this.validNull(msgVo);	
						ResponseMsg responseMsg =new ResponseMsg(msgVo.getDatano());
						//具体做每笔业务
						tradeOrderService.getAndCreateProperty(msg, msgVo, responseMsg);
						//组装返回报文
						resp.put(msgVo.getDatano(), responseMsg);
					}catch (NullPointerException ne) {
						log.error("GetProperty is failed:" + ne.getMessage(), ne);
						resp.put(msgVo.getDatano(), new ResponseMsg(msgVo.getDatano(),BaseStatusEnum.失败.getCode().toString(),ne.getMessage()));
					}
					catch (IllegalArgumentException ile) {
						log.error("GetProperty is failed:" + ile.getMessage(), ile);
						resp.put(msgVo.getDatano(), new ResponseMsg(msgVo.getDatano(),BaseStatusEnum.失败.getCode().toString(),ile.getMessage()));
					}
					catch (Exception e) {
						log.error("GetProperty is failed:" + e.getMessage(), e);
						resp.put(msgVo.getDatano(), new ResponseMsg(msgVo.getDatano(),BaseStatusEnum.失败.getCode().toString(),e.getMessage()));
					}
				}
			}
		}		
		catch (Exception e) {
			log.error("GetProperty is failed:" + e.getMessage(), e);
			Map<String, MsgBody> errResp = new LinkedHashMap<String, MsgBody>();
			for (MsgBody body : msg.getBodyDatas()) {	
				resp.put(body.getDatano(), new ResponseMsg(body.getDatano(),BaseStatusEnum.失败.getCode().toString(), e.getMessage()));
			}
			return msg.asResponse(errResp);			
		}
		//回复所有报文
		return msg.asResponse(resp);
	}

	
	public Message<MsgBody> processChangeProperty(Message<ChangePropertyVo> msg){
		buildChangePropertyBody(msg);
		log.debug("ChangeProperty msg:" + msg);
		Map<String,MsgBody> resp = new LinkedHashMap<String, MsgBody>();
		try {
			List<ChangePropertyVo> datas = msg.getBodyDatas();
			if (datas != null && datas.size() > 0) {
				for (ChangePropertyVo msgVo : datas) {
					try{					
						this.validNull(msgVo);
						ResponseMsg responseMsg =new ResponseMsg(msgVo.getDatano());
						//具体做每笔业务
						tradeOrderService.changeAndDeleteProperty(msg, msgVo, responseMsg);
						//组装返回报文
						resp.put(msgVo.getDatano(), responseMsg);
					}catch (NullPointerException ne) {
						log.error("ChangeProperty is failed:" + ne.getMessage(), ne);
						resp.put(msgVo.getDatano(), new ResponseMsg(msgVo.getDatano(),BaseStatusEnum.失败.getCode().toString(),ne.getMessage()));
					}
					catch (IllegalArgumentException ile) {
						log.error("ChangeProperty is failed:" + ile.getMessage(), ile);
						resp.put(msgVo.getDatano(), new ResponseMsg(msgVo.getDatano(),BaseStatusEnum.失败.getCode().toString(),ile.getMessage()));
					}
					catch (Exception e) {
						log.error("ChangeProperty is failed:" + e.getMessage(), e);
						resp.put(msgVo.getDatano(), new ResponseMsg(msgVo.getDatano(),BaseStatusEnum.失败.getCode().toString(),e.getMessage()));
					}
				}			
			}
		}catch (Exception e) {
			log.error("ChangeProperty is failed:" + e.getMessage(), e);
			Map<String, MsgBody> errResp = new LinkedHashMap<String, MsgBody>();
			for (MsgBody body : msg.getBodyDatas()) {	
				resp.put(body.getDatano(), new ResponseMsg(body.getDatano(),BaseStatusEnum.失败.getCode().toString(), e.getMessage()));
			}
			return msg.asResponse(errResp);			
		}
		//回复所有报文
		return msg.asResponse(resp);	
	}
	
	

	private void validNull(QueryTradeOrderVo msgVo){
		String error="%s is null or empty";
		if(StringUtil.isBlank(msgVo.getDatano())) throw new NullPointerException(String.format(error,"datano"));
		if(StringUtil.isBlank(msgVo.getPropertytype())) throw new NullPointerException(String.format(error,"propertytype"));
	}
	
	private void validNull(SendPropertyVo msgVo){
		String error="%s is null or empty";
		if(StringUtil.isBlank(msgVo.getDatano())) throw new NullPointerException(String.format(error,"datano"));
		if(StringUtil.isBlank(msgVo.getPackageid())) throw new NullPointerException(String.format(error,"packageid"));
		List<SendPropertyVo.PackageData> dataList = msgVo.getData();
		if(dataList == null) throw new NullPointerException(String.format(error,"data"));
		for(SendPropertyVo.PackageData data: dataList){
			if(StringUtil.isBlank(data.getCount())) throw new NullPointerException(String.format(error,"count"));
			if(StringUtil.isBlank(data.getProductid())) throw new NullPointerException(String.format(error,"productid"));
		}
	}
	
	private void validNull(GetPropertyVo msgVo){
		String error="%s is null or empty";
		if(StringUtil.isBlank(msgVo.getDatano())) throw new NullPointerException(String.format(error,"datano"));
		List<GetPropertyVo.PackageData> dataList = msgVo.getData();
		if(dataList == null) throw new NullPointerException(String.format(error,"data"));
		for(GetPropertyVo.PackageData data: dataList){
			if(StringUtil.isBlank(data.getProductid())) throw new NullPointerException(String.format(error,"productid"));
//			if(StringUtil.isBlank(data.getCount())) throw new NullPointerException(String.format(error,"count"));
			if(StringUtil.isBlank(data.getOrderId())) throw new NullPointerException(String.format(error,"address"));
//			if(StringUtil.isBlank(data.getOwner())) throw new NullPointerException(String.format(error,"owner"));
		}
	}
	
	private void validNull(ChangePropertyVo msgVo){
		String error="%s is null or empty";
		if(StringUtil.isBlank(msgVo.getDatano())) throw new NullPointerException(String.format(error,"datano"));
		if(StringUtil.isBlank(msgVo.getProductid())) throw new NullPointerException(String.format(error,"productid"));
		if(StringUtil.isBlank(msgVo.getTradetype())) throw new NullPointerException(String.format(error,"tradetype"));
	}
	
	@SuppressWarnings("unused")
	private void validStatus(String status){
		switch(status){
		case "0":
		case "1":
			break;
		default :
			throw new IllegalArgumentException(String.format("%s is illegal argument", "status"));
		}
	}	
}

