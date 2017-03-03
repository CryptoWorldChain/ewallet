package com.fr.chain.message;

import lombok.Data;

/*
 * 消息返回
 */
@Data
public class ResponseMsg extends MsgBody {

	protected String status = "1";// 返回接收状态,1是成功，0为失败
	protected String retcode = "1";// 返回代码
	protected String retmessage = "successful";// 返回描述编码
	
	/**
	 * error
	 * 
	 * @param retCode
	 * @param retMessage
	 */
	public ResponseMsg(String datano, String retCode, String retMessage) {
		super();
		this.datano = datano;
		this.status = "0";
		this.retcode = retCode;
		this.retmessage = retMessage;
	}

	public ResponseMsg(String datano) {
		this.datano = datano;
	}

	public ResponseMsg() {

	}

}
