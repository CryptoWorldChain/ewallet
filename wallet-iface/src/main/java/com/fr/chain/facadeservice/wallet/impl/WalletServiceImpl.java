package com.fr.chain.facadeservice.wallet.impl;

import java.util.List;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.fr.chain.enums.PropertyStatusEnum;
import com.fr.chain.enums.TradeTypeEnum;
import com.fr.chain.ewallet.db.entity.WalletAdress;
import com.fr.chain.ewallet.db.entity.WalletAdressExample;
import com.fr.chain.ewallet.db.entity.WalletAdressKey;
import com.fr.chain.facadeservice.wallet.WalletService;
import com.fr.chain.message.Message;
import com.fr.chain.property.db.entity.ProductDigit;
import com.fr.chain.property.db.entity.Property;
import com.fr.chain.property.db.entity.PropertyExample;
import com.fr.chain.property.service.CreatePropertyService;
import com.fr.chain.property.service.QueryPropertyService;
import com.fr.chain.trade.db.entity.TradeOrder;
import com.fr.chain.trade.service.CreateTradeOrderService;
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
	@Resource
	QueryPropertyService queryPropertyService ;
	@Resource
	CreateTradeOrderService createTradeOrderService;
	@Resource
	CreatePropertyService createPropertyService;
	
	
	@Override
	public void getAndCreateWallet(Message msg, QueryWalletAdressVo msgVo, Res_QueryWalletAdressVo res_QueryWalletAdressVo){
		
		String address = "";	
		
		PropertyExample example = new PropertyExample();
		example.createCriteria()
//			.andMerchantIdEqualTo(msg.getMerchantid())
			.andOpenIdEqualTo(msg.getOpenid())
//			.andAppIdEqualTo(msg.getAppid())
			.andProductIdEqualTo(msgVo.getWalletcode());
		
		List<Property> listProperty = queryPropertyService.selectByExample(example);
		if(listProperty!=null&&listProperty.size()>0){
			address = listProperty.get(0).getAddress();
		}else{
			address = IDGenerator.nextID();  //调用底层chain
			
			ProductDigit digitProduct = queryPropertyService.selectProductDigitByKey(msgVo.getWalletcode());
			
			//创建订单---创建资产的订单
			TradeOrder orderRecord = new TradeOrder();
			orderRecord.setOrderId(IDGenerator.nextID());
			orderRecord.setMerchantId(msg.getMerchantid());
			orderRecord.setAppId(msg.getAppid());
			orderRecord.setOpenId(msg.getOpenid());
//			orderRecord.setFromOpenId();
//			orderRecord.setToOpenId();
			orderRecord.setOriginOpenid(digitProduct.getOriginOpenid());	
			orderRecord.setProductId(digitProduct.getProductId());
			orderRecord.setPropertyType(digitProduct.getPropertyType()+"");
//			orderRecord.setIsSelfSupport(isSelfSupport);
			orderRecord.setProductDesc(digitProduct.getProductDesc());
			orderRecord.setIsDigit(1+"");
			orderRecord.setSigntype(digitProduct.getSignType());
			orderRecord.setPropertyName(digitProduct.getPropertyName());
			orderRecord.setUnit(digitProduct.getUnit());
			orderRecord.setMincount(digitProduct.getMinCount());
			orderRecord.setCount(0+"");
			orderRecord.setUrl(digitProduct.getUrl());
//			orderRecord.setAmount(amount);
			orderRecord.setDescription(digitProduct.getDescription());
			orderRecord.setAddress(address);
			orderRecord.setCreateTime(DateUtil.getSystemDate());
			orderRecord.setTradeType(TradeTypeEnum.创建资产.getValue());
			orderRecord.setStatus(1);
			
			createTradeOrderService.insert(orderRecord);
			
			
			//创建资产
			Property propertyRecord = new Property();
			propertyRecord.setPropertyId(IDGenerator.nextID());
			propertyRecord.setOrderId(orderRecord.getOrderId());
			propertyRecord.setMerchantId(orderRecord.getMerchantId());
			propertyRecord.setAppId(orderRecord.getAppId());
			propertyRecord.setOpenId(orderRecord.getOpenId());
			propertyRecord.setProductId(orderRecord.getProductId());
			propertyRecord.setPropertyType(orderRecord.getPropertyType());
			propertyRecord.setAddress(address);
			propertyRecord.setOriginOpenid(orderRecord.getOriginOpenid());
			propertyRecord.setProductDesc(orderRecord.getProductDesc());
			propertyRecord.setIsDigit(1+"");
			propertyRecord.setSignType(orderRecord.getSigntype());
			propertyRecord.setPropertyName(orderRecord.getPropertyName());
			propertyRecord.setUnit(orderRecord.getUnit());
			propertyRecord.setMinCount(orderRecord.getMincount());
			propertyRecord.setCount("0");
			propertyRecord.setUrl(orderRecord.getUrl());
			propertyRecord.setDescription(orderRecord.getDescription());
			propertyRecord.setCreateTime(DateUtil.getSystemDate());
			propertyRecord.setStatus(PropertyStatusEnum.可用.getValue());
			createPropertyService.insert(propertyRecord);
			
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
