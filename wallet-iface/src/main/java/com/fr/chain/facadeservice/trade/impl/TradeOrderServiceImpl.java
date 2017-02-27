package com.fr.chain.facadeservice.trade.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.apache.poi.ss.formula.functions.Count;
import org.springframework.stereotype.Service;

import com.fr.chain.enums.PropertyStatusEnum;
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
import com.fr.chain.trade.db.entity.TradeOrder;
import com.fr.chain.trade.db.entity.TradeOrderExample;
import com.fr.chain.trade.db.entity.TradeOrderKey;
import com.fr.chain.trade.service.CreateTradeOrderService;
import com.fr.chain.trade.service.DelTradeOrderService;
import com.fr.chain.trade.service.QueryTradeOrderService;
import com.fr.chain.trade.service.UpdateTradeOrderService;
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
import com.fr.chain.vo.trade.Res_TradeOrderVo;
import com.fr.chain.vo.trade.SendPropertyVo;
import com.fr.chain.vo.trade.TradeOrderVo;

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
	
	@Override
	public void createTradeOrder(Message msg,TradeOrderVo msgVo,Res_TradeOrderVo res_TradeOrderVo){
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
		
		res_TradeOrderVo.setOpenid(openId);
		res_TradeOrderVo.setToopenid(toOpenid);
		res_TradeOrderVo.setCount(count);
		res_TradeOrderVo.setProductid(productId);
		res_TradeOrderVo.setTradeid(orderRecord.getOrderId());
	}
	
	@Override
	public void queryAndCreateTradeFlow(Message msg, QueryTradeFlowVo msgVo, Res_QueryTradeFlowVo res_QueryTradeFlowVo ){
		
		TradeFlow flowInfo = new TradeFlow();
		flowInfo.setOpenId(msg.getOpenid());
		flowInfo.setPropertyType(msgVo.getPropertytype());
		if(!StringUtil.isBlank(msgVo.getProductid())){
			flowInfo.setProductId(msgVo.getProductid());
		}
		
		List<TradeFlow> tradeFlowList = queryTradeOrderService.selectByExample(flowInfo);
		
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
			res_QueryTradeFlowVo.getData().add(tradeFlowData);
		}
	}
	
	
	@Override
	public void queryAndCreateTradeOrder(Message msg, QueryTradeOrderVo msgVo, Res_QueryTradeOrderVo res_QueryTradeOrderVo ){
		TradeOrder tradeOrder= new TradeOrder();
		tradeOrder.setMerchantId(msg.getMerchantid());
		tradeOrder.setOpenId(msg.getOpenid()); 
		tradeOrder.setAppId(msg.getAppid());	
		tradeOrder.setProductId(msgVo.getProductid());
		tradeOrder.setPropertyType(msgVo.getPropertytype());						
		tradeOrder.setProductId(msgVo.getProductid());
//		tradeOrder.setIsDigit(msgVo.getIsselfsupport());s
		List<TradeOrder> tradeOrderList = queryTradeOrderService.selectByExample(tradeOrder);
		if(tradeOrderList == null || tradeOrderList.size() == 0){
			//String errMsg = String.format("没有查询到相应的结果!");
			throw new IllegalArgumentException("没有查询到相应的结果!");
		}						
		//创建返回报文
		for(TradeOrder tmpTradeOrder:tradeOrderList){ 
			Res_QueryTradeOrderVo.TradeOrderData tradeOrderData = new Res_QueryTradeOrderVo.TradeOrderData();
			//BeanUtils.copyProperties(tradeOrderData, tradeOrder);
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
		PropertyExample propertyExample = new PropertyExample();
		Criteria criteria = propertyExample.createCriteria();
			criteria.andMerchantIdEqualTo(msg.getMerchantid());
			criteria.andOpenIdEqualTo(msg.getOpenid());
			criteria.andAppIdEqualTo(msg.getAppid());;
		//获取报文包信息List
		List<SendPropertyVo.PackageData> packageDataList  =  msgVo.getData();
		for(SendPropertyVo.PackageData packageData :packageDataList){
			
			String productId = packageData.getProductid();

			//0公链 1私链
			int chainType = 1;
			//1数字 0个性
			
			ProductPublicInfoVo publicInfo =  getPublicProduct(msgVo, productId);
			
			criteria.andProductIdEqualTo(packageData.getProductid());
			criteria.andStatusEqualTo(1);//1可用 0不可用
			if("1".equals(publicInfo.getChainType())){
				
				List<Property> listProperty = queryPropertyService.selectByExample(propertyExample);
				if(listProperty.size()<1){
					String errMsg = String.format("productId:%s,没有查询到相应的结果!",packageData.getProductid());
					throw new IllegalArgumentException(errMsg);
				}
				//计算productid下所有count值
				int diff = 0;
				for(Property propertyRecord :listProperty){
					diff+=NumberUtil.toInt(propertyRecord.getCount());
				}
				diff = diff - NumberUtil.toInt(packageData.getCount()); 
				
				//生成新订单
				String orderId = IDGenerator.nextID();
				TradeOrder orderRecord =  new TradeOrder();
				orderRecord.setOrderId(orderId);
				orderRecord.setMerchantId(msg.getMerchantid());
				orderRecord.setAppId(msg.getAppid());
				orderRecord.setOpenId(msg.getOpenid());
				orderRecord.setFromOpenId(msg.getOpenid());
				orderRecord.setToOpenId("sysTemp");
				orderRecord.setOriginOpenid(publicInfo.getOriginOpenid());	
				orderRecord.setProductId(packageData.getProductid());
				orderRecord.setPropertyType(publicInfo.getIsdigit());
//				orderRecord.setIsSelfSupport(isSelfSupport);
				orderRecord.setProductDesc(publicInfo.getProductdesc());
				orderRecord.setIsDigit(publicInfo.getIsdigit());
				orderRecord.setSigntype(publicInfo.getSigntype());
				orderRecord.setPropertyName(publicInfo.getPropertyname());
				orderRecord.setUnit(publicInfo.getUnit());
				orderRecord.setMincount(publicInfo.getMincount());
				orderRecord.setCount(packageData.getCount());
				orderRecord.setUrl(publicInfo.getUrl());
//				orderRecord.setAmount(amount);
				orderRecord.setDescription(publicInfo.getDescription());
				orderRecord.setAddress(orderId);
				orderRecord.setCreateTime(DateUtil.getSystemDate());
				orderRecord.setTradeType(TradeTypeEnum.发送资产.getValue());
				orderRecord.setStatus(1);
				
				createTradeOrderService.insert(orderRecord);
				
				if(diff < 0){
					String errMsg = String.format("productId:%s,count:%s is to big",packageData.getProductid(), packageData.getCount());
					throw new IllegalArgumentException(errMsg);
				}
				
				
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
				resPackageData.setTradeId(orderId);
				res_SendPropertyVo.getData().add(resPackageData);							
			}
		}
	}
	
	@Override
	public void getAndCreateProperty(Message msg, GetPropertyVo msgVo, ResponseMsg responseMsg  ){
		Property property= new Property();
		property.setMerchantId(msg.getMerchantid());
		property.setAppId(msg.getAppid());	
		List<GetPropertyVo.PackageData> packageDataList  =  msgVo.getData();
		for(GetPropertyVo.PackageData packageData :packageDataList){
			
			String orderId = packageData.getOrderId();
			
			TradeOrderKey orderKey = new TradeOrderKey();
			orderKey.setOrderId(orderId);
			TradeOrder orderRecord =  queryTradeOrderService.selectOrderByKey(orderId);
			if(orderRecord==null){
				String errMsg = String.format("没有查询到tradeId=%s相应的结果!",orderId);
				throw new IllegalArgumentException(errMsg);
			}
			if(orderRecord.getStatus()!=TradeStatusEnum.处理中.getValue()&&orderRecord.getTradeType()==TradeTypeEnum.发送资产.getValue()){
				String errMsg = String.format("订单tradeId=%s状态不正确!",orderId);
				throw new IllegalArgumentException(errMsg);
			}
			
			//订单更新状态
			orderRecord.setToOpenId(msg.getOpenid());
			orderRecord.setTradeType(TradeTypeEnum.领取资产.getValue());
			orderRecord.setStatus(TradeStatusEnum.处理中.getValue());
			orderRecord.setUpdateTime(DateUtil.getSystemDate());
			updateTradeOrderService.updateTradeOrder(orderRecord);
			
			//流水创建
			createTradeOrderService.insertFlow4Get(orderRecord);
			
			//资产置换
			String getAddress = IDGenerator.nextID();
			updatePropertyService.updateByOrder(orderRecord, getAddress);
			
		}
		responseMsg.setStatus(1+"");
		responseMsg.setRetCode(1+"");
	}
	
	
	@Override
	public void changeAndDeleteProperty(Message msg, ChangePropertyVo msgVo, ResponseMsg responseMsg){
		
		String productId = msgVo.getProductid();
		ProductInfo infoRecord = queryPropertyService.selectProductInfoByKey(productId);
		if(infoRecord==null){
			String errMsg = String.format("没有相应的个性资产productId=%s!",msgVo.getProductid());
			throw new IllegalArgumentException(errMsg);
		}
		if(TradeTypeEnum.丢弃资产.getValue()!=Integer.parseInt(msgVo.getTradetype())){
			throw new IllegalArgumentException("资产变更暂不支持非：【"+TradeTypeEnum.丢弃资产.getName()+"】");
		}
		
		//创建丢弃订单
		TradeOrder orderRecord = new TradeOrder();
		orderRecord.setOrderId(IDGenerator.nextID());
		orderRecord.setMerchantId(infoRecord.getMerchantId());
		orderRecord.setAppId(infoRecord.getAppId());
		orderRecord.setOpenId(msg.getOpenid());
		orderRecord.setOriginOpenid(infoRecord.getOriginOpenid());
		orderRecord.setProductId(infoRecord.getProductId());
		orderRecord.setPropertyType(infoRecord.getPropertyType()+"");
		orderRecord.setProductDesc(infoRecord.getProductDesc());
		orderRecord.setSigntype(infoRecord.getSignType());
		orderRecord.setPropertyName(infoRecord.getPropertyName());
		orderRecord.setUnit(infoRecord.getUnit());
		orderRecord.setMincount(infoRecord.getMinCount());
		orderRecord.setUrl(infoRecord.getUrl());
		orderRecord.setDescription(infoRecord.getDescription());
		orderRecord.setCreateTime(DateUtil.getSystemDate());
		orderRecord.setTradeType(TradeTypeEnum.丢弃资产.getValue());
		orderRecord.setStatus(TradeStatusEnum.处理中.getValue());
		createTradeOrderService.insert(orderRecord);
		
		//更新资产
		Property property= new Property();
		property.setMerchantId(msg.getMerchantid());
		property.setOpenId(msg.getOpenid()); 
		property.setAppId(msg.getAppid());	
		property.setProductId(msgVo.getProductid());
		property.setStatus(PropertyStatusEnum.可用.getValue());
		
		List<Property> tmpListProperty = queryPropertyService.selectByExample(property);
		if(tmpListProperty.size()==0){
			String errMsg = String.format("没有查询到productId=%s相应的结果!",msgVo.getProductid());
			throw new IllegalArgumentException(errMsg);
		}

		int flowCount = 0;
		//更新资产状态
		for(Property tmpProperty:tmpListProperty){
			flowCount+=NumberUtil.toInt(tmpProperty.getCount());
			tmpProperty.setDescription("丢弃TradeId:"+orderRecord.getOrderId());
			tmpProperty.setStatus(PropertyStatusEnum.不可用.getValue());
			updatePropertyService.updateByPrimaryKeySelective(tmpProperty);
		}
	
		//创建流水
		orderRecord.setCount(flowCount+"");
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
		if(("1").equals(msgVo.getIsDigit())){
			ProductDigit digitProduct = queryPropertyService.selectProductDigitByKey(productId);
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
			info.setChainType(digitProduct.getChainType()+"");
		}else{
			ProductInfo infoProduct = queryPropertyService.selectProductInfoByKey(productId);
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
	
}