package com.fr.chain.facadeservice.wallet.impl;

import java.util.List;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.fr.chain.ewallet.db.entity.WalletAdress;
import com.fr.chain.ewallet.db.entity.WalletAdressExample;
import com.fr.chain.ewallet.db.entity.WalletAdressKey;
import com.fr.chain.facadeservice.wallet.WalletService;
import com.fr.chain.message.Message;
import com.fr.chain.utils.DateUtil;
import com.fr.chain.utils.IDGenerator;
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
	
	@Override
	public void getAndCreateWallet(Message msg, QueryWalletAdressVo msgVo, Res_QueryWalletAdressVo res_QueryWalletAdressVo){
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
			address = IDGenerator.nextID();  //调用底层chain
			//新建的钱包地址需要存入数据库
			walletAdress.setCreateTime(DateUtil.getSystemDate());
			walletAdress.setWalletId(IDGenerator.nextID());
			walletAdress.setWalletAddress(address);
			createWalletAdressService.insertSelective(walletAdress);
		}
		
		
		res_QueryWalletAdressVo.setWalletcode(msgVo.getWalletcode());
		res_QueryWalletAdressVo.setWalletaddress(address);
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
	
	
}
