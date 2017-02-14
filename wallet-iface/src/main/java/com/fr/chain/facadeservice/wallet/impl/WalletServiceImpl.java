package com.fr.chain.facadeservice.wallet.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.codehaus.jackson.JsonNode;
import org.springframework.stereotype.Service;

import com.fr.chain.enums.BaseStatusEnum;
import com.fr.chain.ewallet.db.entity.WalletAdress;
import com.fr.chain.ewallet.db.entity.WalletAdressExample;
import com.fr.chain.ewallet.db.entity.WalletAdressKey;
import com.fr.chain.facadeservice.wallet.WalletService;
import com.fr.chain.message.Message;
import com.fr.chain.message.MsgBody;
import com.fr.chain.message.ResponseMsg;
import com.fr.chain.utils.StringUtil;
import com.fr.chain.utils.JsonUtil;
import com.fr.chain.vo.wallet.QueryWalletAdressVo;
import com.fr.chain.vo.wallet.Res_QueryWalletAdressVo;
import com.fr.chain.wallet.service.CreateWalletAdressService;
import com.fr.chain.wallet.service.DelWalletAdressService;
import com.fr.chain.wallet.service.QueryWalletAdressService;
import com.fr.chain.wallet.service.UpdateWalletAdressService;

@Slf4j
@Service("walletService")
public class WalletServiceImpl implements WalletService {

	@Resource
	CreateWalletAdressService  createWalletAdressService;
	
	@Resource
	QueryWalletAdressService queryWalletAdressService;
	
	@Resource
	DelWalletAdressService delWalletAdressService;
	
	@Resource
	UpdateWalletAdressService updateWalletAdressService;
	


	private void buildGetWalletBody(Message<QueryWalletAdressVo> gpmsg) {
		List<QueryWalletAdressVo> bodys = new ArrayList<>();
		for (JsonNode node : gpmsg.getAnDatas()) {
			bodys.add(JsonUtil.json2Bean(node, QueryWalletAdressVo.class));
		} 
		gpmsg.setBodyDatas(bodys);
	}
	

	@Override
	public Message<MsgBody> processGetWallet(Message<QueryWalletAdressVo> msg) {
		log.debug("Create property msg:" + msg);
		buildGetWalletBody(msg);
		Map<String,MsgBody> resp = new LinkedHashMap<String, MsgBody>();
		try {
			List<QueryWalletAdressVo> datas = msg.getBodyDatas();
			if (datas != null && datas.size() > 0) {
				for (QueryWalletAdressVo msgVo : datas) {
					try{					
						this.validNull(msgVo);
						WalletAdress walletAdress= new WalletAdress();
						walletAdress.setMerchantId(msg.getMerchantid());
						walletAdress.setOpenId(msg.getOpenid()); 
						walletAdress.setAppId(msg.getAppid());	
						walletAdress.setWalletCode(msgVo.getWalletcode());
						//调用底层区块链生成地址
						String address = "";	
						
						WalletAdress tmpWalletAdress = queryWalletAdressService.selectOneByExample(walletAdress);
						//如果已经存在则返回钱包地址，否则创建地址
						if(tmpWalletAdress!=null){
							address = tmpWalletAdress.getWalletAddress();
						}else{
							address = "aaaa";  //调用底层chain
							//新建的钱包地址需要存入数据库
							walletAdress.setWalletAddress(address);
							createWalletAdressService.insert(walletAdress);
						}
						
						Res_QueryWalletAdressVo res_queryWalletAdressVo =new Res_QueryWalletAdressVo(msgVo.getDatano());
						res_queryWalletAdressVo.setWalletcode(msgVo.getWalletcode());
						res_queryWalletAdressVo.setWalletaddress(address);
						
						//设置返回报文
						resp.put(msgVo.getDatano(), res_queryWalletAdressVo);
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
	
	@Override
    public int insert(WalletAdress info){
    	return createWalletAdressService.insert(info);
    }
	
	@Override
	public int batchInsert(List<WalletAdress> records){
		return createWalletAdressService.batchInsert(records);
	}
	
	@Override
	public int deleteByPrimaryKey (WalletAdressKey key){
		return delWalletAdressService.deleteByPrimaryKey(key);
	}
	
	@Override
	public List<WalletAdress>  selectByExample(WalletAdress info){
		return queryWalletAdressService.selectByExample(info);
	}
	
	@Override
	public List<WalletAdress>  selectByExample(WalletAdressExample info){
		return queryWalletAdressService.selectByExample(info);
	}
	
	@Override
	public int updateByExampleSelective (WalletAdress record, WalletAdressExample example){
		return updateWalletAdressService.updateByExampleSelective(record,example);
	}
	
	

	
	private void validNull(QueryWalletAdressVo msgVo){
		String error="%s is null or empty";
		if(StringUtil.isBlank(msgVo.getDatano())) throw new NullPointerException(String.format(error,"datano"));
		if(StringUtil.isBlank(msgVo.getWalletcode())) throw new NullPointerException(String.format(error,"propertytype"));
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
