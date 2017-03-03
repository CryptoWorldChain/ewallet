package com.fr.chain.schedule;

import java.util.List;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fr.chain.enums.SystemOpenIdEnum;
import com.fr.chain.enums.TradeStatusEnum;
import com.fr.chain.enums.TradeTypeEnum;
import com.fr.chain.facadeservice.trade.TradeOrderService;
import com.fr.chain.trade.db.entity.TradeOrder;
import com.fr.chain.trade.db.entity.TradeOrderExample;
import com.fr.chain.utils.FConfig;
import com.fr.chain.utils.NumberUtil;

@Slf4j
@Component
public class TradeOrderSchedule {
	@Resource
	TradeOrderService tradeOrderService;
	
	private static final long refundPropertyRate= 1*60*1000; //1分钟执行一次
	
	private static  final long refundPropertyTime = NumberUtil.toLong(FConfig.getProValue("refundPropertyTime")); // 时间
	
	//private static final long refundPropertyTime = 3*60*1000; // 时间 3分钟
	
	/** 
	 * 发送出去的资产,24小时不领取,将退回给发送者
	 */
	@Scheduled(fixedDelay = refundPropertyRate)
	public void refundPropertyTradeOrder(){
		log.info("refundProperty -----begin------");
		TradeOrderExample tradeOrderExample = new TradeOrderExample();
		tradeOrderExample.setOrderByClause("CREATE_TIME DESC");
		TradeOrderExample.Criteria criteria = tradeOrderExample.createCriteria();
        //ToOpenId = 系统默认账户,status=发送成功,createTime时间超过24小时
		criteria.andToOpenIdEqualTo(SystemOpenIdEnum.系统默认账户.getName());
		criteria.andTradeTypeEqualTo(TradeTypeEnum.发送资产.getValue());
		criteria.andStatusEqualTo(TradeStatusEnum.成功.getValue());
		List<TradeOrder> tradeOrderList = tradeOrderService.selectByExample(tradeOrderExample);
		if(tradeOrderList == null || tradeOrderList.size() == 0){
			return;
		}						
		//退回逻辑处理
		for(TradeOrder tmpTradeOrder:tradeOrderList){ 
			long diffTime = System.currentTimeMillis() - tmpTradeOrder.getCreateTime().getTime();
			if(diffTime < refundPropertyTime){ 
				continue;
			}
			tradeOrderService.refundAndDeleteProperty(tmpTradeOrder);			
		}
		log.info("refundProperty -----end------");
	}
}