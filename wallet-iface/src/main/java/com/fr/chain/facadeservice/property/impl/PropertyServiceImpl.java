package com.fr.chain.facadeservice.property.impl;

import java.util.List;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.fr.chain.vo.property.CreatePropertyVo;
import com.fr.chain.vo.property.QueryPropertyVo;
import com.fr.chain.vo.property.Res_CreatePropertyVo;
import com.fr.chain.vo.property.Res_QueryPropertyVo;
import com.fr.chain.enums.TradeTypeEnum;
import com.fr.chain.facadeservice.property.PropertyService;
import com.fr.chain.message.Message;
import com.fr.chain.property.db.entity.Property;
import com.fr.chain.property.db.entity.PropertyExample;
import com.fr.chain.property.db.entity.PropertyKey;
import com.fr.chain.property.service.CreatePropertyService;
import com.fr.chain.property.service.DelPropertyService;
import com.fr.chain.property.service.QueryPropertyService;
import com.fr.chain.property.service.UpdatePropertyService;
import com.fr.chain.trade.service.CreateTradeOrderService;
import com.fr.chain.utils.DateUtil;
import com.fr.chain.utils.IDGenerator;
import com.fr.chain.utils.NumberUtil;

@Slf4j
@Service("propertyService")
public class PropertyServiceImpl implements PropertyService {

	@Resource
	private CreatePropertyService  createPropertyService;
	@Resource
	private QueryPropertyService queryPropertyService;
	@Resource
	private DelPropertyService delPropertyService;
	@Resource
	private UpdatePropertyService updatePropertyService;
	@Resource
	private CreateTradeOrderService createTradeOrderService;

	

	/**
	 * 创建资产
	 */
	@Override
	public void createProperty(Message msg, CreatePropertyVo msgVo, Res_CreatePropertyVo res_CreatePropertyVo ) {
		Property property= new Property();						
		property.setMerchantId(msg.getMerchantid());
		property.setOpenId(msg.getOpenid()); 
		property.setAppId(msg.getAppid());	
		property.setProductId(msgVo.getProductid());
		property.setPropertyType(msgVo.getPropertytype());	
		String address = ""; //钱包地址
		Property tmpProperty = queryPropertyService.selectOneByExample(property);
		if(tmpProperty!=null){
			//已经存在此资产(再次创建),不用生成地址,只需增加数量
			int nCount = NumberUtil.toInt(tmpProperty.getCount()) + NumberUtil.toInt(msgVo.getCount());	
			tmpProperty.setCount(String.valueOf(nCount));
			updatePropertyService.updateByPrimaryKey(tmpProperty);
			//获取返回地址
			address = tmpProperty.getAddress();
			
			//创建订单
			createTradeOrderService.insertTradeByProperty(tmpProperty, TradeTypeEnum.创建资产.getValue());
			
		}else{
			//新增资产
			property.setPropertyId(IDGenerator.nextID());//自动生成Id
			property.setProductDesc(msgVo.getPropertytype());
			property.setIsSelfSupport(msgVo.getIsselfsupport());
			property.setIsDigit(msgVo.getIsdigit());
			property.setProductDesc(msgVo.getProductdesc());
			property.setSignType(msgVo.getSigntype());
			property.setPropertyName(msgVo.getPropertyname());
			property.setUnit(msgVo.getUnit());
			property.setMinCount(msgVo.getMincount());
			property.setCount(msgVo.getCount());
			property.setUrl(msgVo.getUrl());
			if(msgVo.getAmount()!=null){
				property.setAmount(NumberUtil.toDouble(msgVo.getAmount()));
			}						
			property.setDescription(msgVo.getDescription());							
			//调用底层区块链生成地址
			address = IDGenerator.nextID();	//模拟生成地址							
			property.setAddress(address);
			property.setCreateTime(DateUtil.getSystemDate());
			property.setOriginOpenid(msg.getOpenid());
			property.setStatus(1);
			createPropertyService.insert(property);
			
			//创建订单
			createTradeOrderService.insertTradeByProperty(property, TradeTypeEnum.创建资产.getValue());
			
		}		
		//设置返回报文
		res_CreatePropertyVo.setProductid(msgVo.getProductid());
		res_CreatePropertyVo.setPropertytype(msgVo.getPropertytype());		
		res_CreatePropertyVo.setAddress(address);	
	}
	
	
	
	/**
	 * 查询资产
	 */
	@Override
	public void queryProperty(Message msg, QueryPropertyVo msgVo, Res_QueryPropertyVo res_QueryPropertyVo ){
		Property property= new Property();
		property.setMerchantId(msg.getMerchantid());
		property.setOpenId(msg.getOpenid()); 
		property.setAppId(msg.getAppid());	
		property.setProductId(msgVo.getProductid());
		property.setPropertyType(msgVo.getPropertytype());						
		property.setProductId(msgVo.getProductid());
		property.setPropertyType(msgVo.getPropertytype());	
		property.setIsSelfSupport(msgVo.getIsselfsupport());
		property.setSignType(msgVo.getSigntype());
		List<Property> propertyList = queryPropertyService.selectByExample(property);
		
		if(propertyList != null && propertyList.size()>0){ 
			for(Property tmpProperty : propertyList){
				//BeanUtils.copyProperties(propertyInfo, tmpProperty); //拷贝
				Res_QueryPropertyVo.PropertyInfo propertyInfo = new Res_QueryPropertyVo.PropertyInfo();
				propertyInfo.setPropertytype(tmpProperty.getPropertyType());
				propertyInfo.setIsselfsupport(tmpProperty.getIsSelfSupport());
				propertyInfo.setProductid(tmpProperty.getProductId());
				propertyInfo.setProductdesc(tmpProperty.getProductDesc());
				propertyInfo.setIsdigit(tmpProperty.getIsDigit());
				propertyInfo.setSigntype(tmpProperty.getSignType());
				propertyInfo.setPropertyname(tmpProperty.getPropertyName());
				propertyInfo.setUnit(tmpProperty.getUnit());
				propertyInfo.setMincount(tmpProperty.getMinCount());
				propertyInfo.setCount(tmpProperty.getCount());
				propertyInfo.setUrl(tmpProperty.getUrl());
				propertyInfo.setAmount(tmpProperty.getAmount());
				propertyInfo.setDescription(tmpProperty.getDescription());
				res_QueryPropertyVo.getPropertyInfoList().add(propertyInfo);
			}
		}
	}
	
	@Override
    public int insert(Property info){
    	return createPropertyService.insert(info);
    }
	
	@Override
	public int batchInsert(List<Property> records){
		return createPropertyService.batchInsert(records);
	}
	
	@Override
	public int deleteByPrimaryKey (PropertyKey key){
		return delPropertyService.deleteByPrimaryKey(key);
	}
	
	@Override
	public List<Property>  selectByExample(Property info){
		return queryPropertyService.selectByExample(info);
	}
	
	@Override
	public List<Property>  selectByExample(PropertyExample info){
		return queryPropertyService.selectByExample(info);
	}
	
	@Override
	public int updateByExampleSelective (Property record, PropertyExample example){
		return updatePropertyService.updateByExampleSelective(record,example);
	}
}
