package com.fr.chain.facadeservice.trade.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.fr.chain.enums.PropertyStatusEnum;
import com.fr.chain.enums.PropertyTypeEnum;
import com.fr.chain.enums.SystemOpenIdEnum;
import com.fr.chain.enums.TradeStatusEnum;
import com.fr.chain.enums.TradeTypeEnum;
import com.fr.chain.facadeservice.trade.TradeOrderService;
import com.fr.chain.message.Message;
import com.fr.chain.message.ResponseMsg;
import com.fr.chain.property.db.entity.ProductDigit;
import com.fr.chain.property.db.entity.ProductInfo;
import com.fr.chain.property.db.entity.Property;
import com.fr.chain.property.db.entity.PropertyExample;
import com.fr.chain.property.db.entity.PropertyExample.Criteria;
import com.fr.chain.property.service.CreatePropertyService;
import com.fr.chain.property.service.DelPropertyService;
import com.fr.chain.property.service.QueryPropertyService;
import com.fr.chain.property.service.UpdatePropertyService;
import com.fr.chain.trade.db.entity.TradeFlow;
import com.fr.chain.trade.db.entity.TradeFlowExample;
import com.fr.chain.trade.db.entity.TradeOrder;
import com.fr.chain.trade.db.entity.TradeOrderExample;
import com.fr.chain.trade.db.entity.TradeOrderKey;
import com.fr.chain.trade.service.CreateTradeOrderService;
import com.fr.chain.trade.service.DelTradeOrderService;
import com.fr.chain.trade.service.QueryTradeOrderService;
import com.fr.chain.trade.service.UpdateTradeOrderService;
import com.fr.chain.utils.DateStyle;
import com.fr.chain.utils.DateUtil;
import com.fr.chain.utils.IDGenerator;
import com.fr.chain.utils.NumberUtil;
import com.fr.chain.utils.StringUtil;
import com.fr.chain.vo.property.ProductPublicInfoVo;
import com.fr.chain.vo.trade.ChangePropertyVo;
import com.fr.chain.vo.trade.GetPropertyVo;
import com.fr.chain.vo.trade.QueryTradeFlowVo;
import com.fr.chain.vo.trade.QueryTradeOrderVo;
import com.fr.chain.vo.trade.Res_QueryTradeFlowVo;
import com.fr.chain.vo.trade.Res_QueryTradeOrderVo;
import com.fr.chain.vo.trade.Res_SendPropertyVo;
import com.fr.chain.vo.trade.Res_TransDigitVo;
import com.fr.chain.vo.trade.SendPropertyVo;
import com.fr.chain.vo.trade.TransDigitVo;

@Slf4j
@Service("tradeOrderService")
public class TradeOrderServiceImpl implements TradeOrderService {
	@Resource
	QueryPropertyService queryPropertyService;
	@Resource
	DelPropertyService delPropertyService;
	@Resource
	UpdatePropertyService updatePropertyService;
	@Resource
	CreatePropertyService createPropertyService;
	@Resource
	CreateTradeOrderService createTradeOrderService;
	@Resource
	QueryTradeOrderService queryTradeOrderService;
	@Resource
	DelTradeOrderService delTradeOrderService;
	@Resource
	UpdateTradeOrderService updateTradeOrderService;
	
	
	private static final long refundPropertyRate = 1*60*1000; // 时间?小时
	
	
	@Override
	public void createTransDigit(Message msg,TransDigitVo msgVo,Res_TransDigitVo res_TransDigitVo){
		String openId = msg.getOpenid();
		String productId = msgVo.getProductid();
		String count = msgVo.getCount();
		String toOpenid = msgVo.getToopenid();
		
		Property srcProperty = new Property();
		srcProperty.setOpenId(openId);
		srcProperty.setProductId(productId);
		srcProperty.setStatus(PropertyStatusEnum.可用.getValue());
		//查询用户下产品所有资产
		List<Property> listProperty = queryPropertyService.selectByExample(srcProperty);
		if(listProperty == null || listProperty.size() == 0){
			String errMsg = String.format("openId:%s,没有相应的资产!",openId);
			throw new IllegalArgumentException(errMsg);
		}		
		//资产总数
		BigDecimal tmpBD = new BigDecimal("0");
		for(Property tmpPro : listProperty){
			tmpBD  =tmpBD.add(new BigDecimal(tmpPro.getCount()));
		}
		BigDecimal tradeBD = new BigDecimal(count);
		
		String srcCount;
		if(tmpBD.compareTo(tradeBD)==-1){
			String errMsg = String.format("openId:%s,没有足够资产可以使用!",openId);
			throw new IllegalArgumentException(errMsg);
		}else if(tmpBD.compareTo(tradeBD)==0){
			srcCount = "0";
		}else{
			srcCount = tradeBD.subtract(tmpBD)+"";
		}
		
		ProductDigit productInfo =queryPropertyService.selectProductDigitByKey(productId);
		if(productInfo==null){
			String errMsg = String.format("productId:%s,系统无该资产!",productId);
			throw new IllegalArgumentException(errMsg);
		}
		
		TradeOrder orderRecord = getOrderByProduct(productInfo, openId, toOpenid, count, TradeTypeEnum.资产转移.getValue());
		//创建订单
		createTradeOrderService.insert(orderRecord);
		String srcAddress=IDGenerator.nextID();
		String receAddress=IDGenerator.nextID();
		//创建资产
		createPropertyService.inserProperty4Trans(orderRecord, srcCount, srcAddress, tradeBD+"", receAddress);
		//创建流水
		createTradeOrderService.insertFolw4Trans(orderRecord);
		
		res_TransDigitVo.setOpenid(openId);
		res_TransDigitVo.setToopenid(toOpenid);
		res_TransDigitVo.setCount(count);
		res_TransDigitVo.setProductid(productId);
		res_TransDigitVo.setTradeid(orderRecord.getOrderId());
	}
	
	@Override
	public void queryTradeFlow(Message msg, QueryTradeFlowVo msgVo, Res_QueryTradeFlowVo res_QueryTradeFlowVo ){
		TradeFlowExample tradeFlowExample = new TradeFlowExample();
		tradeFlowExample.setOrderByClause("CREATE_TIME DESC");
		TradeFlowExample.Criteria criteria = tradeFlowExample.createCriteria();
			criteria.andMerchantIdEqualTo(msg.getMerchantid());
			criteria.andOpenIdEqualTo(msg.getOpenid());
			criteria.andAppIdEqualTo(msg.getAppid());
			criteria.andPropertyTypeEqualTo(msgVo.getPropertytype());
			if(!StringUtil.isBlank(msgVo.getProductid())){
			    criteria.andProductIdEqualTo(msgVo.getProductid());
			}
			
		
		List<TradeFlow> tradeFlowList = queryTradeOrderService.selectByExample(tradeFlowExample);
		
		if(tradeFlowList == null || tradeFlowList.size() == 0){
			//String errMsg = String.format("没有查询到相应的结果!");
			throw new IllegalArgumentException("没有查询到相应的结果!");
		}						
		//创建返回报文
		for(TradeFlow tradeFlow:tradeFlowList){ 
			Res_QueryTradeFlowVo.TradeOrderData tradeFlowData = new Res_QueryTradeFlowVo.TradeOrderData();
			//BeanUtils.copyProperties(tradeOrderData, tradeOrder);
			tradeFlowData.setPropertytype(tradeFlow.getPropertyType());
			tradeFlowData.setPropertyname(tradeFlow.getPropertyName());
			tradeFlowData.setProductid(tradeFlow.getProductId());
			tradeFlowData.setSigntype(tradeFlow.getSigntype());
			tradeFlowData.setTradetype(tradeFlow.getTradeType()+"");
			tradeFlowData.setUnit(tradeFlow.getUnit());
			tradeFlowData.setCount(tradeFlow.getCount());
			tradeFlowData.setTradetime(DateUtil.format(tradeFlow.getCreateTime(), DateStyle.YYYY_MM_DD_HH_MM_SS));
			res_QueryTradeFlowVo.getData().add(tradeFlowData);
		}
	}
	
	
	@Override
	public void queryTradeOrder(Message msg, QueryTradeOrderVo msgVo, Res_QueryTradeOrderVo res_QueryTradeOrderVo ){
		TradeOrderExample tradeOrderExample = new TradeOrderExample();
		tradeOrderExample.setOrderByClause("CREATE_TIME DESC");
		TradeOrderExample.Criteria criteria = tradeOrderExample.createCriteria();
		criteria.andMerchantIdEqualTo(msg.getMerchantid());
		criteria.andOpenIdEqualTo(msg.getOpenid());
		criteria.andAppIdEqualTo(msg.getAppid());
		if(StringUtils.isNotBlank(msgVo.getPropertytype())){
			criteria.andPropertyTypeEqualTo(msgVo.getPropertytype());
		}
		if(StringUtils.isNotBlank(msgVo.getProductid())){
			criteria.andProductIdEqualTo(msgVo.getProductid());
		}
	
		List<TradeOrder> tradeOrderList = queryTradeOrderService.selectByExample(tradeOrderExample);
		if(tradeOrderList == null || tradeOrderList.size() == 0){
			throw new IllegalArgumentException("没有查询到相应的结果!");
		}						
		//创建返回报文
		for(TradeOrder tmpTradeOrder:tradeOrderList){ 
			Res_QueryTradeOrderVo.TradeOrderData tradeOrderData = new Res_QueryTradeOrderVo.TradeOrderData();
			tradeOrderData.setPropertytype(tmpTradeOrder.getPropertyType());
			tradeOrderData.setIsselfsupport(tmpTradeOrder.getIsSelfSupport());
			tradeOrderData.setProductid(tmpTradeOrder.getProductId());
			tradeOrderData.setIsdigit(tmpTradeOrder.getIsDigit());
			tradeOrderData.setSigntype(tmpTradeOrder.getSigntype());
			tradeOrderData.setPropertyname(tmpTradeOrder.getPropertyName());
			tradeOrderData.setUnit(tmpTradeOrder.getUnit());
			tradeOrderData.setCount(tmpTradeOrder.getCount());
			tradeOrderData.setAddress(tmpTradeOrder.getAddress());	
			tradeOrderData.setTradetype(tmpTradeOrder.getTradeType());
			res_QueryTradeOrderVo.getData().add(tradeOrderData);
		}
	}
	
	@Override
	public void sendAndCreateProperty(Message msg, SendPropertyVo msgVo, Res_SendPropertyVo res_SendPropertyVo ){
		//获取报文包信息List
		List<SendPropertyVo.PackageData> packageDataList  =  msgVo.getData();
		for(SendPropertyVo.PackageData packageData :packageDataList){
			String productId = packageData.getProductid();

			//0公链 1私链
			int chainType = 1;
			
			//1数字 0个性			
			ProductPublicInfoVo publicInfo =  getPublicProduct(msgVo, productId);
			if(publicInfo==null){
				String errMsg = String.format("productId:%s,没有查询到该资产ID!",packageData.getProductid());
				throw new IllegalArgumentException(errMsg);
			}
			
			PropertyExample propertyExample = new PropertyExample();
			PropertyExample.Criteria criteria = propertyExample.createCriteria();
			criteria.andMerchantIdEqualTo(msg.getMerchantid());
			criteria.andOpenIdEqualTo(msg.getOpenid());
			criteria.andAppIdEqualTo(msg.getAppid());
			criteria.andProductIdEqualTo(packageData.getProductid());
			criteria.andStatusEqualTo(PropertyStatusEnum.可用.getValue());//1可用 0不可用
			if("1".equals(publicInfo.getChainType())){
				List<Property> listProperty = queryPropertyService.selectByExample(propertyExample);
				if(listProperty!=null && listProperty.size()<1){
					String errMsg = String.format("productId:%s,此用户没有相应资产!",packageData.getProductid());
					throw new IllegalArgumentException(errMsg);
				}
				//计算productid下所有count值
				int diff = 0;
				for(Property propertyRecord :listProperty){
					diff+=NumberUtil.toInt(propertyRecord.getCount());
				}
				diff = diff - NumberUtil.toInt(packageData.getCount()); 
				if(diff < 0){
					String errMsg = String.format("productId:%s,count:%s is to big",packageData.getProductid(), packageData.getCount());
					throw new IllegalArgumentException(errMsg);
				}
				
				int propertytype = PropertyTypeEnum.数字资产.getValue();
				if(!"1".equals(publicInfo.getIsdigit())){
					propertytype = PropertyTypeEnum.个性资产.getValue();
				}
				//生成新订单
				String orderId = IDGenerator.nextID();
				TradeOrder orderRecord =  new TradeOrder();
				orderRecord.setOrderId(orderId);
				orderRecord.setMerchantId(msg.getMerchantid());
				orderRecord.setAppId(msg.getAppid());
				orderRecord.setOpenId(msg.getOpenid());
				orderRecord.setFromOpenId(msg.getOpenid());
				orderRecord.setToOpenId(SystemOpenIdEnum.系统默认账户.getName());
				orderRecord.setOriginOpenid(publicInfo.getOriginOpenid());	
				orderRecord.setProductId(packageData.getProductid());
				orderRecord.setPropertyType(propertytype+"");
				orderRecord.setProductDesc(publicInfo.getProductdesc());
				orderRecord.setIsDigit(publicInfo.getIsdigit());
				orderRecord.setSigntype(publicInfo.getSigntype());
				orderRecord.setPropertyName(publicInfo.getPropertyname());
				orderRecord.setUnit(publicInfo.getUnit());
				orderRecord.setMincount(publicInfo.getMincount());
				orderRecord.setCount(packageData.getCount());
				orderRecord.setUrl(publicInfo.getUrl());
				orderRecord.setDescription(publicInfo.getDescription());
				orderRecord.setAddress(orderId);
				orderRecord.setCreateTime(DateUtil.getSystemDate());
				orderRecord.setUpdateTime(DateUtil.getSystemDate());
				orderRecord.setTradeType(TradeTypeEnum.发送资产.getValue());
				orderRecord.setStatus(TradeStatusEnum.成功.getValue());//接入区块链之前，认为成功
				createTradeOrderService.insert(orderRecord);
				
				//发送方原有资产置成不可用
				for(Property propertyRecord :listProperty){
					propertyRecord.setStatus(PropertyStatusEnum.不可用.getValue());
					updatePropertyService.updateByPrimaryKeySelective(propertyRecord);
				}
				//新资产迭换,生成新的资产挂载到系统账户中，如果原有资产如有剩余，原有资产综合生成新的一条资产
				String srcAddress = IDGenerator.nextID();
				String receAddress = IDGenerator.nextID();
				createPropertyService.inserPropertyFreezen(orderRecord, diff, srcAddress, NumberUtil.toInt(packageData.getCount()), receAddress);
				
				//创建流水
				createTradeOrderService.insertFlow4Sent(orderRecord);
				
				//组装返回报文
				Res_SendPropertyVo.PackageData resPackageData = new Res_SendPropertyVo.PackageData();
				resPackageData.setProductid(packageData.getProductid());
				resPackageData.setCount(packageData.getCount());
				resPackageData.setOrderid(orderId);
				res_SendPropertyVo.getData().add(resPackageData);							
			}
		}
	}
	
	@Override
	public void getAndCreateProperty(Message msg, GetPropertyVo msgVo, ResponseMsg responseMsg  ){
		List<GetPropertyVo.PackageData> packageDataList  =  msgVo.getData();
		for(GetPropertyVo.PackageData packageData :packageDataList){
			String orderId = packageData.getOrderid();
			TradeOrderKey orderKey = new TradeOrderKey();
			orderKey.setOrderId(orderId);
			TradeOrder orderRecord =  queryTradeOrderService.selectOrderByKey(orderId);
			if(orderRecord==null){
				String errMsg = String.format("没有查询到tradeId=%s相应的结果!",orderId);
				throw new IllegalArgumentException(errMsg);
			}
			if(orderRecord.getTradeType()!=TradeTypeEnum.发送资产.getValue()){
				String errMsg = String.format("订单tradeId=%s交易类型不正确!",orderId);
				throw new IllegalArgumentException(errMsg);
			}
						
			//订单更新状态
			orderRecord.setToOpenId(msg.getOpenid());
			orderRecord.setTradeType(TradeTypeEnum.领取资产.getValue());
			orderRecord.setStatus(TradeStatusEnum.成功.getValue());//调用区块链之前，认为成功
			orderRecord.setUpdateTime(DateUtil.getSystemDate());
			updateTradeOrderService.updateTradeOrder(orderRecord);
			
			//流水创建
			createTradeOrderService.insertFlow4Get(orderRecord);
			
			//资产置换
			String address = IDGenerator.nextID();
			PropertyExample example = new PropertyExample();
			example.createCriteria().andOrderIdEqualTo(orderId).andOpenIdEqualTo(SystemOpenIdEnum.系统默认账户.getName());
			List<Property> list =queryPropertyService.selectByExample(example);
			if(list == null && list.size() < 1){
				throw new IllegalArgumentException("领取资产不成功");
			}
			//系统临时资产更新状态
			Property propertySys = list.get(0);
			propertySys.setStatus(PropertyStatusEnum.不可用.getValue());//接入区块链前，认为成功,更新不可用
			updatePropertyService.updateByPrimaryKey(propertySys);
			//系统临时资产转移给接收方
			Property propertyGetOwner  = propertySys;
			propertyGetOwner.setPropertyId(IDGenerator.nextID());
			propertyGetOwner.setOpenId(orderRecord.getToOpenId());
			propertyGetOwner.setStatus(PropertyStatusEnum.可用.getValue());//接入区块链之前，认为成功
			propertyGetOwner.setAddress(address);
			createPropertyService.insert(propertyGetOwner);
		}
	}
	
	
	@Override
	public void changeAndDeleteProperty(Message msg, ChangePropertyVo msgVo, ResponseMsg responseMsg){
		String productId = msgVo.getProductid();
		ProductInfo infoRecord = queryPropertyService.selectProductInfoByKey(productId);
		if(infoRecord==null){
			String errMsg = String.format("没有相应的个性资产productId=%s!",msgVo.getProductid());
			throw new IllegalArgumentException(errMsg);
		}
		if(TradeTypeEnum.丢弃资产.getValue()!=NumberUtil.toInt(msgVo.getTradetype()) 
				&&TradeTypeEnum.消费资产.getValue() != NumberUtil.toInt(msgVo.getTradetype())){
			throw new IllegalArgumentException("资产变更目前仅支持：【"+TradeTypeEnum.丢弃资产.getName()+"】 和 【" + TradeTypeEnum.消费资产.getName() +"】" );
		}
		
		//生成订单号
		String oderId = IDGenerator.nextID();
		
		//更新资产
		Property property= new Property();
		property.setMerchantId(msg.getMerchantid());
		property.setOpenId(msg.getOpenid()); 
		property.setAppId(msg.getAppid());	
		property.setProductId(msgVo.getProductid());
		property.setStatus(PropertyStatusEnum.可用.getValue());
		
		List<Property> tmpListProperty = queryPropertyService.selectByExample(property);
		if(tmpListProperty!=null && tmpListProperty.size()==0){
			String errMsg = String.format("您没有此个性资产了,productId=%s!",msgVo.getProductid());
			throw new IllegalArgumentException(errMsg);
		}

		//消费/丢弃数量
		int changeCount = 0;
		//更新资产状态
		for(Property tmpProperty:tmpListProperty){
			changeCount+=NumberUtil.toInt(tmpProperty.getCount());
			tmpProperty.setDescription("丢弃TradeId:"+ oderId);
			tmpProperty.setStatus(PropertyStatusEnum.不可用.getValue());
			updatePropertyService.updateByPrimaryKeySelective(tmpProperty);
		}
		
		
		//创建丢弃订单
		TradeOrder orderRecord = new TradeOrder();
		orderRecord.setOrderId(oderId);
		orderRecord.setMerchantId(infoRecord.getMerchantId());
		orderRecord.setAppId(infoRecord.getAppId());
		orderRecord.setOpenId(msg.getOpenid());
		orderRecord.setOriginOpenid(infoRecord.getOriginOpenid());
		orderRecord.setProductId(infoRecord.getProductId());
		orderRecord.setPropertyType(infoRecord.getPropertyType()+"");
		orderRecord.setProductDesc(infoRecord.getProductDesc());
		orderRecord.setSigntype(infoRecord.getSignType());
		orderRecord.setPropertyName(infoRecord.getPropertyName());
		orderRecord.setCount(changeCount+"");
		orderRecord.setUnit(infoRecord.getUnit());
		orderRecord.setMincount(infoRecord.getMinCount());
		orderRecord.setUrl(infoRecord.getUrl());
		orderRecord.setDescription(infoRecord.getDescription());
		orderRecord.setCreateTime(DateUtil.getSystemDate());
		orderRecord.setTradeType(NumberUtil.toInt(msgVo.getTradetype()));
		orderRecord.setStatus(TradeStatusEnum.成功.getValue());//接入区块链之前，订单认为成功
		createTradeOrderService.insert(orderRecord);
	
		//创建流水		
		createTradeOrderService.insertFlow4Drop(orderRecord);
	}
	
	@Override
    public int insert(TradeOrder info){
    	return createTradeOrderService.insert(info);
    }
	
	@Override
	public int batchInsert(List<TradeOrder> records){
		return createTradeOrderService.batchInsert(records);
	}
	
	@Override
	public int deleteByPrimaryKey (TradeOrderKey key){
		return delTradeOrderService.deleteByPrimaryKey(key);
	}
	
	@Override
	public List<TradeOrder>  selectByExample(TradeOrder info){
		return queryTradeOrderService.selectByExample(info);
	}
	
	@Override
	public List<TradeOrder>  selectByExample(TradeOrderExample info){
		return queryTradeOrderService.selectByExample(info);
	}
	
	@Override
	public int updateByExampleSelective (TradeOrder record, TradeOrderExample example){
		return updateTradeOrderService.updateByExampleSelective(record,example);
	}
	
	public ProductPublicInfoVo getPublicProduct(SendPropertyVo msgVo,String productId){
		
		ProductPublicInfoVo info = new ProductPublicInfoVo();
		if(("1").equals(msgVo.getPropertytype())){
			ProductDigit digitProduct = queryPropertyService.selectProductDigitByKey(productId);
			if(digitProduct==null){
				return null;
			}
			info.setProductId(productId);
			info.setMerchantId(digitProduct.getMerchantId());
			info.setAppId(digitProduct.getAppId());
			info.setProductdesc(digitProduct.getProductDesc());
			info.setPropertyname(digitProduct.getPropertyName());
			info.setOriginOpenid(digitProduct.getOriginOpenid());
			info.setSigntype(digitProduct.getSignType());
			info.setUnit(digitProduct.getUnit());
			info.setMincount(digitProduct.getMinCount());
			info.setUrl(digitProduct.getUrl());
			info.setDescription(digitProduct.getDescription());
			info.setIsdigit(1+"");
//			info.setpro
			info.setChainType(digitProduct.getChainType()+"");
		}else{
			ProductInfo infoProduct = queryPropertyService.selectProductInfoByKey(productId);
			if(infoProduct==null){
				return null;
			}
			info.setProductId(productId);
			info.setMerchantId(infoProduct.getMerchantId());
			info.setAppId(infoProduct.getAppId());
			info.setProductdesc(infoProduct.getProductDesc());
			info.setPropertyname(infoProduct.getPropertyName());
			info.setOriginOpenid(infoProduct.getOriginOpenid());
			info.setSigntype(infoProduct.getSignType());
			info.setUnit(infoProduct.getUnit());
			info.setMincount(infoProduct.getMinCount());
			info.setUrl(infoProduct.getUrl());
			info.setDescription(infoProduct.getDescription());
			info.setIsdigit(0+"");
			info.setChainType(1+"");
		}
		
		
		
		return info;
		
	}
	/**
	 * 根据资产信息得到订单
	 * @param productInfo
	 * @param fromOpenid
	 * @param toOpenid
	 * @param count
	 * @param tradeType
	 * @return
	 */
	public TradeOrder getOrderByProduct(ProductDigit productInfo,String fromOpenid,String toOpenid,String count,int tradeType){
		TradeOrder orderInfo = new TradeOrder();
		orderInfo.setOrderId(IDGenerator.nextID());
		orderInfo.setMerchantId(productInfo.getMerchantId());
		orderInfo.setAppId(productInfo.getAppId());
		orderInfo.setOpenId(fromOpenid);
		orderInfo.setFromOpenId(fromOpenid);
		orderInfo.setToOpenId(toOpenid);
		orderInfo.setOriginOpenid(productInfo.getOriginOpenid());
		orderInfo.setProductId(productInfo.getProductId());
		orderInfo.setPropertyType(productInfo.getPropertyType()+"");
		orderInfo.setProductDesc(productInfo.getProductDesc());
		orderInfo.setPropertyName(productInfo.getPropertyName());
		orderInfo.setUnit(productInfo.getUnit()+"");
		orderInfo.setMincount(productInfo.getMinCount());
		orderInfo.setCount(count);
		orderInfo.setUrl(productInfo.getUrl());
		orderInfo.setDescription(productInfo.getDescription());
		orderInfo.setCreateTime(DateUtil.getSystemDate());
		orderInfo.setTradeType(tradeType);
		orderInfo.setStatus(TradeStatusEnum.成功.getValue());
		
		return orderInfo;
	}
	
	/**
	 * 	退回逻辑处理
	 */
	@Override
	public void refundAndDeleteProperty(TradeOrder tradeOrder){
		//订单更新状态
		TradeOrder orderRecord =  tradeOrder;
		orderRecord.setToOpenId(tradeOrder.getFromOpenId());
		orderRecord.setTradeType(TradeTypeEnum.退回资产.getValue());
		orderRecord.setStatus(TradeStatusEnum.成功.getValue());//调用区块链之前，认为成功
		orderRecord.setUpdateTime(DateUtil.getSystemDate());
		updateTradeOrderService.updateTradeOrder(orderRecord);
			
		//流水创建
		createTradeOrderService.insertFlow4Refund(orderRecord);
		
		//资产置换
		//根据订单号找出所有已经送出去的资产,并退回给发送者,更新资产状态及地址
		PropertyExample propertyExample = new PropertyExample();
		PropertyExample.Criteria propertyCriteria = propertyExample.createCriteria();
		propertyCriteria.andOrderIdEqualTo(tradeOrder.getOrderId());
		propertyCriteria.andOpenIdEqualTo(SystemOpenIdEnum.系统默认账户.getName());
		List<Property> listProperty =  queryPropertyService.selectByExample(propertyExample);
		if(listProperty == null || listProperty.size() < 1){
			throw new IllegalArgumentException("系统退回失败！");
		}
		//系统临时资产更新状态
		Property propertySys = listProperty.get(0);
		propertySys.setStatus(PropertyStatusEnum.不可用.getValue());//接入区块链前，认为成功,更新不可用
		updatePropertyService.updateByPrimaryKey(propertySys);
		
		//系统临时资产退回给原发送方
		Property propertyRefundOwner  = propertySys;
		propertyRefundOwner.setPropertyId(IDGenerator.nextID());
		propertyRefundOwner.setOpenId(orderRecord.getToOpenId());
		propertyRefundOwner.setStatus(PropertyStatusEnum.可用.getValue());//接入区块链之前，认为成功
		String address = IDGenerator.nextID();
		propertyRefundOwner.setAddress(address);
		createPropertyService.insert(propertyRefundOwner);
	}
}