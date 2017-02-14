package com.fr.chain.message;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import lombok.Data;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.node.ArrayNode;

import com.fr.chain.utils.JsonUtil;

@Data
public class Message<T extends MsgBody> {

	String merchantid; // 商户Id
	String appid; // 应用Id
	String openid; //身份表示
	String senttime; // 报文发送时间
	String exptime; // 报文有效时间
	String charset; // 报文编码格式
	String signtype; // 签名类型
	String sign; // 签名
	@JsonProperty(value = "smessageno")
	String smessageno = "000000000"; // 报文发送流水号

	@JsonIgnore
	private ArrayNode anDatas; // 数据信息-JsonNode

	@JsonProperty(value = "datas")
	List<T> bodyDatas = new ArrayList<T>(); // 数据信息--Body

	public Message() {
	}

	public Message<ResponseMsg> asResponseSuccess() {
		Message<ResponseMsg> resp = new Message<ResponseMsg>(merchantid, appid, openid, signtype, null, getCurTime(), getNextTime(), charset,
				smessageno);
		List<ResponseMsg> repbody = new ArrayList<ResponseMsg>();
		for (MsgBody body : bodyDatas) {
			repbody.add(new ResponseMsg(body.getDatano()));
		}
		resp.setBodyDatas(repbody);
		if (getSign() != null && getSigntype().equalsIgnoreCase("md5"))
			try {
				MessageBuilder.calcSign(resp);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		return resp;
	}
	


	public < R extends MsgBody> Message<R> asResponse(Map<String,R> datas) {		
		Message<R> resp = new Message<R>(merchantid, appid, openid, signtype, null, getCurTime(), getNextTime(), charset,
				smessageno);
		List<R> repbody = new ArrayList<R>();
		for (MsgBody body : bodyDatas) {
			R rep=datas.get(body.getDatano());
			repbody.add(rep);
		}
		resp.setBodyDatas(repbody);
		if (getSign() != null && getSigntype().equalsIgnoreCase("md5"))
			try {
				MessageBuilder.calcSign(resp);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		return resp;
   }
	
	public static String getCurTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(new Date());
	}

	public static String getNextTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(new Date(System.currentTimeMillis() + 3600));
	}

	public Date toSentTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return df.parse(senttime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Date toExpTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return df.parse(exptime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Message<ResponseMsg> asResponseError(String errorNo, String errorMessage) {
		Message<ResponseMsg> resp = new Message<ResponseMsg>(merchantid, appid, openid, signtype, null, getCurTime(), getNextTime(), charset,
				smessageno);
		List<ResponseMsg> repbody = new ArrayList<ResponseMsg>();
		for (MsgBody body : bodyDatas) {
			repbody.add(new ResponseMsg(body.getDatano(), errorNo, errorMessage));
		}
		resp.setBodyDatas(repbody);
		if (getSign() != null && getSigntype().equalsIgnoreCase("md5"))
			try {
				MessageBuilder.calcSign(resp);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		return resp;
	}
	

	public Message(String merchantid, String appid, String openid, String signType, String sign, String sentTime, String expTime,
			String charset, String sMessageNo) {
		super();
		this.merchantid = merchantid;
		this.appid = appid;
		this.openid = openid;
		this.signtype = signType;
		this.sign = sign;	
		this.senttime = sentTime;
		this.exptime = expTime;
		this.charset = charset;
		this.smessageno = sMessageNo;
	}
	public static void main(String[] args) {
		Message msg=new Message<MsgBody>();
		msg.setSmessageno("11111");
		System.out.println(JsonUtil.bean2Json(msg).toString());
	}

}



