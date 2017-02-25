package com.fr.chain.controller.wallet;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.codehaus.jackson.JsonNode;
import org.springframework.stereotype.Component;

import com.fr.chain.enums.BaseStatusEnum;
import com.fr.chain.facadeservice.wallet.WalletService;
import com.fr.chain.message.Message;
import com.fr.chain.message.MsgBody;
import com.fr.chain.message.ResponseMsg;
import com.fr.chain.utils.BeanFactory;
import com.fr.chain.utils.JsonUtil;
import com.fr.chain.utils.StringUtil;
import com.fr.chain.vo.wallet.QueryWalletAdressVo;
import com.fr.chain.vo.wallet.Res_QueryWalletAdressVo;


@Slf4j
@Component
public class ProcessWalletAdressMsg {
	
	@Resource
	private WalletService walletService; 
	
	private void buildGetWalletBody(Message<QueryWalletAdressVo> gpmsg) {
		List<QueryWalletAdressVo> bodys = new ArrayList<>();
		for (JsonNode node : gpmsg.getAnDatas()) {
			bodys.add(JsonUtil.json2Bean(node, QueryWalletAdressVo.class));
		} 
		gpmsg.setBodyDatas(bodys);
	}
	


	public Message<MsgBody> processGetWallet(Message<QueryWalletAdressVo> msg) {
		log.debug("GetWallet msg:" + msg);
		buildGetWalletBody(msg);
		Map<String,MsgBody> resp = new LinkedHashMap<String, MsgBody>();
		try {
			List<QueryWalletAdressVo> datas = msg.getBodyDatas();
			if (datas != null && datas.size() > 0) {
				for (QueryWalletAdressVo msgVo : datas) {
					try{					
						this.validNull(msgVo);
						Res_QueryWalletAdressVo res_QueryWalletAdressVo =new Res_QueryWalletAdressVo(msgVo.getDatano());
						//具体业务
						walletService.getAndCreateWallet(msg, msgVo, res_QueryWalletAdressVo);
						//设置返回报文
						resp.put(msgVo.getDatano(), res_QueryWalletAdressVo);
					}catch (NullPointerException ne) {
						log.error("GetWallet is failed:" + ne.getMessage(), ne);
						resp.put(msgVo.getDatano(), new ResponseMsg(msgVo.getDatano(),BaseStatusEnum.失败.getCode().toString(),ne.getMessage()));
					}
					catch (IllegalArgumentException ile) {
						log.error("GetWallet is failed:" + ile.getMessage(), ile);
						resp.put(msgVo.getDatano(), new ResponseMsg(msgVo.getDatano(),BaseStatusEnum.失败.getCode().toString(),ile.getMessage()));
					}
					catch (Exception e) {
						log.error("GetWallet is failed:" + e.getMessage(), e);
						resp.put(msgVo.getDatano(), new ResponseMsg(msgVo.getDatano(),BaseStatusEnum.失败.getCode().toString(),e.getMessage()));
					}
				}
			}
		}		
		catch (Exception e) {
			log.error("GetWallet is failed:" + e.getMessage(), e);
			Map<String, MsgBody> errResp = new LinkedHashMap<String, MsgBody>();
			for (MsgBody body : msg.getBodyDatas()) {	
				resp.put(body.getDatano(), new ResponseMsg(body.getDatano(),BaseStatusEnum.失败.getCode().toString(), e.getMessage()));
			}
			return msg.asResponse(errResp);			
		}
		//回复所有报文
		return msg.asResponse(resp);
	}	
	
	
	private void validNull(QueryWalletAdressVo msgVo){
		String error="%s is null or empty";
		if(StringUtil.isBlank(msgVo.getDatano())) throw new NullPointerException(String.format(error,"datano"));
		if(StringUtil.isBlank(msgVo.getWalletcode())) throw new NullPointerException(String.format(error,"walletcode"));
	}
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

