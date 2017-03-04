package com.fr.chain.facadeservice.trade;

import java.util.List;

import com.fr.chain.message.Message;
import com.fr.chain.message.ResponseMsg;
import com.fr.chain.trade.db.entity.TradeOrder;
import com.fr.chain.trade.db.entity.TradeOrderExample;
import com.fr.chain.trade.db.entity.TradeOrderKey;
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

/**
 * 注意事务用AOP配置的,参见配置文件
 * @title 
 * @author Dylan
 * @date 2017年2月8日
 *
 */
public interface TradeOrderService {
	
    public int insert(TradeOrder info);
	
	public int batchInsert(List<TradeOrder> records);	
	
	public int deleteByPrimaryKey (TradeOrderKey key);
	
	public List<TradeOrder>  selectByExample(TradeOrder info);
	public List<TradeOrder>  selectByExample(TradeOrderExample info);
	
	public int updateByExampleSelective (TradeOrder record, TradeOrderExample example);
	
	/**
	 *数字资产转账 
	 * @param msg
	 * @param msgVo
	 * @param res_TradeOrderVo
	 */
	public void createTransDigit(Message msg,TransDigitVo msgVo,Res_TransDigitVo res_TransDigitVo);
	
	/**
	 * 查询订单
	 * @param msg
	 * @param msgVo
	 * @param res_QueryTradeOrderVo
	 */
	public void queryTradeOrder(Message msg, QueryTradeOrderVo msgVo, Res_QueryTradeOrderVo res_QueryTradeOrderVo );
	
	/**
	 * 查询流水
	 * @param msg
	 * @param msgVo
	 * @param res_QueryTradeFlowVo
	 */
	public void queryTradeFlow(Message msg, QueryTradeFlowVo msgVo, Res_QueryTradeFlowVo res_QueryTradeFlowVo );
	
	/**
	 * 发送资产
	 * @param msg
	 * @param msgVo
	 * @param res_SendPropertyVo
	 */
	public void sendAndCreateProperty(Message msg, SendPropertyVo msgVo, Res_SendPropertyVo res_SendPropertyVo );
	
	/**
	 * 领取资产
	 * @param msg
	 * @param msgVo
	 * @param responseMsg
	 */
	public void getAndCreateProperty(Message msg, GetPropertyVo msgVo, ResponseMsg responseMsg  );
	
	/**
	 * 资产变更
	 * @param msg
	 * @param msgVo
	 * @param responseMsg
	 */
	public void changeAndDeleteProperty(Message msg, ChangePropertyVo msgVo, ResponseMsg responseMsg);
	
	/**
	 * 发送资产之后,24小时未领取,资产退回
	 */
	public void refundAndDeleteProperty(TradeOrder tradeOrder);
	
}
