package com.fr.chain.controller.property;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.codehaus.jackson.JsonNode;
import org.springframework.stereotype.Component;

import com.fr.chain.enums.BaseStatusEnum;
import com.fr.chain.facadeservice.property.PropertyService;
import com.fr.chain.message.Message;
import com.fr.chain.message.MsgBody;
import com.fr.chain.message.ResponseMsg;
import com.fr.chain.utils.JsonUtil;
import com.fr.chain.utils.StringUtil;
import com.fr.chain.vo.property.CreatePropertyVo;
import com.fr.chain.vo.property.QueryPropertyVo;
import com.fr.chain.vo.property.Res_CreatePropertyVo;
import com.fr.chain.vo.property.Res_QueryPropertyVo;


@Slf4j
@Component
public class ProcessPropertyMsg {
	@Resource
	private PropertyService propertyService;

	private void buildCreatePropertyBody(Message<CreatePropertyVo> gpmsg) {
		List<CreatePropertyVo> bodys = new ArrayList<>();
		for (JsonNode node : gpmsg.getAnDatas()) {
			bodys.add(JsonUtil.json2Bean(node, CreatePropertyVo.class));
		} 
		gpmsg.setBodyDatas(bodys);
	}
	
	private void buildQueryPropertyBody(Message<QueryPropertyVo> gpmsg) {
		List<QueryPropertyVo> bodys = new ArrayList<>();
		for (JsonNode node : gpmsg.getAnDatas()) {
			bodys.add(JsonUtil.json2Bean(node, QueryPropertyVo.class));
		} 
		gpmsg.setBodyDatas(bodys);		
	}

	
	public Message<MsgBody> processCreateProperty(Message<CreatePropertyVo> msg) {
		buildCreatePropertyBody(msg);
		log.debug("Create property msg:" + msg);
		Map<String,MsgBody> resp = new LinkedHashMap<String, MsgBody>();
		try {
			List<CreatePropertyVo> datas = msg.getBodyDatas();
			if (datas != null && datas.size() > 0) {
				for (CreatePropertyVo msgVo : datas) {
					try{	
						//验证报文
						this.validNull(msgVo);
						//新建返回报文
						Res_CreatePropertyVo res_CreatePropertyVo =new Res_CreatePropertyVo(msgVo.getDatano());
						//具体每笔业务
						propertyService.createProperty(msg, msgVo, res_CreatePropertyVo);
						//组装返回报文
						resp.put(msgVo.getDatano(), res_CreatePropertyVo);
					}catch (NullPointerException ne) {
						log.error("Create Account is failed:" + ne.getMessage(), ne);
						resp.put(msgVo.getDatano(), new ResponseMsg(msgVo.getDatano(),BaseStatusEnum.失败.getCode().toString(),ne.getMessage()));
					}
					catch (IllegalArgumentException ile) {
						log.error("Create Account is failed:" + ile.getMessage(), ile);
						resp.put(msgVo.getDatano(), new ResponseMsg(msgVo.getDatano(),BaseStatusEnum.失败.getCode().toString(),ile.getMessage()));
					}
					catch (Exception e) {
						log.error("Create Account is failed:" + e.getMessage(), e);
						resp.put(msgVo.getDatano(), new ResponseMsg(msgVo.getDatano(),BaseStatusEnum.失败.getCode().toString(),e.getMessage()));
					}
				}
			}
		}catch (Exception e) {
			log.error("Create Account is failed:" + e.getMessage(), e);
			Map<String, MsgBody> errResp = new LinkedHashMap<String, MsgBody>();
			for (MsgBody body : msg.getBodyDatas()) {	
				resp.put(body.getDatano(), new ResponseMsg(body.getDatano(),BaseStatusEnum.失败.getCode().toString(), e.getMessage()));
			}
			return msg.asResponse(errResp);			
		}
		
		//回复所有报文
		return msg.asResponse(resp);

	}
	
	
	public Message<MsgBody> processQueryProperty(Message<QueryPropertyVo> msg){
		buildQueryPropertyBody(msg);
		log.debug("Create property msg:" + msg);
		Map<String,MsgBody> resp = new LinkedHashMap<String, MsgBody>();
		try {
			List<QueryPropertyVo> datas = msg.getBodyDatas();
			if (datas != null && datas.size() > 0) {
				for (QueryPropertyVo msgVo : datas) {
					try{					
						this.validNull(msgVo);						
						//新建返回报文
						Res_QueryPropertyVo res_QueryPropertyVo =new Res_QueryPropertyVo(msgVo.getDatano());
						//具体每笔业务
						propertyService.queryProperty(msg, msgVo, res_QueryPropertyVo);						
						//设置返回报文
						resp.put(msgVo.getDatano(), res_QueryPropertyVo);
					}catch (NullPointerException ne) {
						log.error("Create Account is failed:" + ne.getMessage(), ne);
						resp.put(msgVo.getDatano(), new ResponseMsg(msgVo.getDatano(),BaseStatusEnum.失败.getCode().toString(),ne.getMessage()));
					}
					catch (IllegalArgumentException ile) {
						log.error("Create Account is failed:" + ile.getMessage(), ile);
						resp.put(msgVo.getDatano(), new ResponseMsg(msgVo.getDatano(),BaseStatusEnum.失败.getCode().toString(),ile.getMessage()));
					}
					catch (Exception e) {
						log.error("Create Account is failed:" + e.getMessage(), e);
						resp.put(msgVo.getDatano(), new ResponseMsg(msgVo.getDatano(),BaseStatusEnum.失败.getCode().toString(),e.getMessage()));
					}
				}				
			}
		}		
		catch (Exception e) {
			log.error("Create Account is failed:" + e.getMessage(), e);
			Map<String, MsgBody> errResp = new LinkedHashMap<String, MsgBody>();
			for (MsgBody body : msg.getBodyDatas()) {	
				resp.put(body.getDatano(), new ResponseMsg(body.getDatano(),BaseStatusEnum.失败.getCode().toString(), e.getMessage()));
			}
			return msg.asResponse(errResp);			
		}
		//回复所有报文
		return msg.asResponse(resp);	
	}
	
	
	@SuppressWarnings("unused")
	private void validNull(CreatePropertyVo msgVo){
		String error="%s is null or empty";
		if(StringUtil.isBlank(msgVo.getDatano())) throw new NullPointerException(String.format(error,"datano"));
		if(StringUtil.isBlank(msgVo.getPropertytype())) throw new NullPointerException(String.format(error,"propertytype"));
		if(StringUtil.isBlank(msgVo.getIsselfsupport())) throw new NullPointerException(String.format(error,"isselfsupport"));
		if(StringUtil.isBlank(msgVo.getProductid())) throw new NullPointerException(String.format(error,"productid"));
		if(StringUtil.isBlank(msgVo.getIsdigit())) throw new NullPointerException(String.format(error,"isdigit"));
		if(StringUtil.isBlank(msgVo.getSigntype())) throw new NullPointerException(String.format(error,"signtype"));
		if(StringUtil.isBlank(msgVo.getPropertyname())) throw new NullPointerException(String.format(error,"propertyname"));
		if(StringUtil.isBlank(msgVo.getCount())) throw new NullPointerException(String.format(error,"count"));		
	}
	
	private void validNull(QueryPropertyVo msgVo){
		String error="%s is null or empty";
		if(StringUtil.isBlank(msgVo.getDatano())) throw new NullPointerException(String.format(error,"datano"));
		if(StringUtil.isBlank(msgVo.getPropertytype())) throw new NullPointerException(String.format(error,"propertytype"));
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

