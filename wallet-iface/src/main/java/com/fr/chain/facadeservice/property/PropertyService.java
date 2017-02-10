package com.fr.chain.facadeservice.property;

import java.util.List;

import com.fr.chain.message.Message;
import com.fr.chain.message.MsgBody;
import com.fr.chain.property.db.entity.Property;
import com.fr.chain.property.db.entity.PropertyExample;
import com.fr.chain.property.db.entity.PropertyKey;
import com.fr.chain.vo.property.CreatePropertyVo;
import com.fr.chain.vo.property.QueryPropertyVo;
import com.fr.chain.vo.trade.ChangePropertyVo;
import com.fr.chain.vo.trade.GetPropertyVo;
import com.fr.chain.vo.trade.SendPropertyVo;

public interface PropertyService {
	
    public int insert(Property info);
	
	public int batchInsert(List<Property> records);
	
	
	public int deleteByPrimaryKey (PropertyKey key);
	
	public List<Property>  selectByExample(Property info);
	public List<Property>  selectByExample(PropertyExample info);
	
	public int updateByExampleSelective (Property record, PropertyExample example);
	

	public Message<MsgBody> processCreateProperty(Message<CreatePropertyVo> msg);
	public Message<MsgBody> processQueryProperty(Message<QueryPropertyVo> msg);
	
}
