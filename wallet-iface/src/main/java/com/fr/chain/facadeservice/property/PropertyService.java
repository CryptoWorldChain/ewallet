package com.fr.chain.facadeservice.property;

import java.util.List;

import com.fr.chain.message.Message;
import com.fr.chain.property.db.entity.ProductInfo;
import com.fr.chain.property.db.entity.Property;
import com.fr.chain.property.db.entity.PropertyExample;
import com.fr.chain.property.db.entity.PropertyKey;
import com.fr.chain.vo.property.CreatePropertyVo;
import com.fr.chain.vo.property.QueryPropertyVo;
import com.fr.chain.vo.property.Res_CreatePropertyVo;
import com.fr.chain.vo.property.Res_QueryPropertyVo;

public interface PropertyService {
	
    public int insert(Property info);
	
	public int batchInsert(List<Property> records);
	
	
	public int deleteByPrimaryKey (PropertyKey key);
	
	public List<Property>  selectByExample(Property info);
	public List<Property>  selectByExample(PropertyExample info);
	
	public int updateByExampleSelective (Property record, PropertyExample example);
	

	public void createProperty(Message msg, CreatePropertyVo msgVo, Res_CreatePropertyVo res_CreatePropertyVo,ProductInfo productInfo);
	public void queryProperty(Message msg, QueryPropertyVo msgVo, Res_QueryPropertyVo res_QueryPropertyVo);
	//根据主键查询资产
	public ProductInfo selectProduct4CreateProperty(Message msg, CreatePropertyVo msgVo);
	
}
