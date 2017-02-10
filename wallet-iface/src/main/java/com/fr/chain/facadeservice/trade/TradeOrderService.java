package com.fr.chain.facadeservice.trade;

import java.util.List;

import com.fr.chain.message.Message;
import com.fr.chain.message.MsgBody;
import com.fr.chain.trade.db.entity.TradeOrder;
import com.fr.chain.trade.db.entity.TradeOrderExample;
import com.fr.chain.trade.db.entity.TradeOrderKey;
import com.fr.chain.vo.trade.ChangePropertyVo;
import com.fr.chain.vo.trade.GetPropertyVo;
import com.fr.chain.vo.trade.QueryTradeOrderVo;
import com.fr.chain.vo.trade.SendPropertyVo;

public interface TradeOrderService {
	
    public int insert(TradeOrder info);
	
	public int batchInsert(List<TradeOrder> records);	
	
	public int deleteByPrimaryKey (TradeOrderKey key);
	
	public List<TradeOrder>  selectByExample(TradeOrder info);
	public List<TradeOrder>  selectByExample(TradeOrderExample info);
	
	public int updateByExampleSelective (TradeOrder record, TradeOrderExample example);
	
	public Message<MsgBody> processQueryTradeOrder(Message<QueryTradeOrderVo> msg);
	
	public Message<MsgBody> processSendProperty(Message<SendPropertyVo> msg);
	
	public Message<MsgBody> processGetProperty(Message<GetPropertyVo> msg);
	
	public Message<MsgBody> processChangeProperty(Message<ChangePropertyVo> msg);
	
}
