package com.fr.chain.controller.wallet;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fr.chain.message.Message;
import com.fr.chain.message.MessageBuilder;
import com.fr.chain.message.MessageException;
import com.fr.chain.utils.BeanFactory;
import com.fr.chain.utils.DBBean;
import com.fr.chain.utils.JsonUtil;
import com.fr.chain.utils.SerializerUtil;
import com.fr.chain.web.bean.DbCondi;
import com.fr.chain.web.bean.FieldsMapperBean;
import com.fr.chain.web.bean.ListInfo;
import com.fr.chain.web.bean.PageInfo;
import com.fr.chain.web.bean.QueryMapperBean;
import com.fr.chain.web.bean.ReturnInfo;
import com.fr.chain.web.bean.SqlMaker;
import com.fr.chain.web.bind.FieldUtils;
import com.fr.chain.web.bind.KeyExplainHandler;
import com.fr.chain.web.bind.RequestJsonParam;
import com.fr.chain.ewallet.db.mapper.WalletAdressMapper;
import com.fr.chain.ewallet.db.entity.WalletAdress;
import com.fr.chain.ewallet.db.entity.WalletAdressExample;
import com.fr.chain.ewallet.db.entity.WalletAdressKey;
import com.fr.chain.facadeservice.wallet.WalletService;
import com.fr.chain.common.HttpRequestHelper;
import com.fr.chain.db.service.DataService;
import com.fr.chain.web.action.BasicCtrl;

@Slf4j
@Controller
@RequestMapping("/walletadress")
public class WalletAdressCtrl extends BasicCtrl {
	@Resource 
	private HttpRequestHelper httpRequestHelper;
	@Resource
	private WalletService walletService;
	@Resource
	private ProcessWalletAdressMsg processWalletAdressMsg;
	
	private static DataService mysqlDataService = 
			(DataService)BeanFactory.getBean("mysqlDataService");
	
	/**
	 * 获取钱包地址
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/v1_00/getwalletadress", method = RequestMethod.POST)
	@ResponseBody
	public Object getWalletAdress(HttpServletRequest req, HttpServletResponse resp)  {
		String jsontxt = httpRequestHelper.getJsonTxt(req);
		Message inMsg=null;
		try {
			inMsg = MessageBuilder.buildMessage(jsontxt);
			Object obj = processWalletAdressMsg.processGetWallet(inMsg);
			try{
				log.debug("[RESP]:"+JsonUtil.bean2Json(obj));
			}catch(Exception e){
				log.error("message Resp Error:", e);
			}
			return obj;			
		} catch (MessageException je) {
			log.warn("Message error", je);			
			return new ReturnInfo(je.getMessage(), 0, null,false);
		} catch (Exception e) {
			log.error("unknow error", e);			
			return new ReturnInfo(e.getMessage(), 0, null,false);
		}
	}
	
	
}