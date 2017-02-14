package com.fr.chain.message;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

import com.fr.chain.utils.DateTimeFM;
import com.fr.chain.utils.DecryptUtil;
import com.fr.chain.utils.IDGenerator;
import com.fr.chain.utils.JsonUtil;

@Slf4j
public class MessageBuilder {

	public static String getRequestContent(HttpServletRequest request) throws IOException {
		InputStream is = null;
		String jsonTxt = null;
		try {
			is = request.getInputStream();
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			byte[] data = new byte[8192];
			int rsize = 0;
			do {
				rsize = is.read(data);
				if (rsize > 0) {
					bout.write(data, 0, rsize);
				}
			} while (rsize > 0);
			jsonTxt = new String(bout.toByteArray());
			bout.close();
		} finally {
			if (is != null) {
				is.close();
			}
		}
		return jsonTxt;
	}



	public static void appendFieldValues(JsonNode jn, StringBuffer sb) {
		Iterator<Entry<String, JsonNode>> it = jn.getFields();
		// System.out.println("field.size="+jn.size());
		while (it.hasNext()) {
			Entry<String, JsonNode> en = it.next();
			// System.out.println("::"+en.getKey()+"==>"+en.getValue());
			if (en.getKey().equalsIgnoreCase("sign")) {
			} else if (en.getKey().equals("signType")) {
			} else if (en.getKey().equals("smessageNo")) {
				// omit
			} else if (en.getValue().isArray()) {
				// System.out.println("isArray:" + en.getKey());
				ArrayNode an = (ArrayNode) en.getValue();
				for (int i = 0; i < an.size(); i++) {
					appendFieldValues(an.get(i), sb);
				}
			} else {
				if(!en.getValue().asText().equalsIgnoreCase("null"))
				sb.append(en.getValue().asText());
			}
		}
	}

	private static String calcSign(String sysPlatCode, JsonNode jn) throws UnsupportedEncodingException {
		/*//BusinessInfo biz = bizCache.getBiz(sysPlatCode);
		if (biz == null) {
			throw new MessageException("平台编码错误:" + sysPlatCode);
		}*/
		StringBuffer sb = new StringBuffer();
		appendFieldValues(jn, sb);
		//sb.append(biz.getBusinessKey());
		sb.append("32ousdjf9343djjomvsdf2233dskdlfb");
		log.debug(sb.toString());
		String sign = DecryptUtil.getMD5(sb.toString());
		
		return sign;
	}

	public static void calcSign(Message<?> msg) throws UnsupportedEncodingException {
		JsonNode jn = JsonUtil.bean2Json(msg);
		String sign = calcSign(msg.getMerchantid(), jn);
		msg.setSign(sign);
		msg.setSigntype("md5");
	}

	public static Message buildMessage(String json) {
		if (StringUtils.isBlank(json)) {
			return null;
		}
		try {
			ObjectNode on = JsonUtil.toObjectNode(json);
			Message gpmsg = JsonUtil.json2Bean(on, Message.class);
			String sign = calcSign(gpmsg.getMerchantid(), on);
			gpmsg.setSign(on.findValue("sign") !=null ?on.findValue("sign").asText() : on.findValue("Sign").asText());
			
			if (gpmsg.getSigntype().equalsIgnoreCase("md5") && !StringUtils.equalsIgnoreCase(sign, gpmsg.getSign())) {
				//throw new MessageException("sign not correct:" + gpmsg.getSign());
			}

			ArrayNode an = (ArrayNode) on.findValue("datas");
			gpmsg.setAnDatas(an);

			return gpmsg;
		} catch (MessageException me) {
			throw me;
		} catch (Exception e) {
			throw new MessageException("MessageParseError:" + e.getMessage());
		}
	}

	public static String getLastV(String v, int length) {
		if (v.length() >= 4)
			return v.substring(v.length() - length, v.length());
		else {
			return getLastV(v + ((int) (Math.random() * 1000 / 10)), length);
		}
	}

	public static void main(String[] args) {
		System.out.println(getLastV("2", 4));
	}

	public static <T extends MsgBody> Message<T> newMsg(String merchantid, String appid, String openid, T body) {

		String sMessageNo = merchantid + DateTimeFM.getCurTimestamp() + getLastV(IDGenerator.nextID(), 4);
		Message<T> msg = new Message<T>(merchantid, appid, openid, null, null, DateTimeFM.getCurTime(), DateTimeFM.getNextTime(), 
				"utf8",	sMessageNo);
	
		List<T> datas = new ArrayList<T>();
		datas.add(body);
		msg.setBodyDatas(datas);
		try {
			calcSign(msg);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return msg;
	}
	
	public static <T extends MsgBody> Message<T> newMsg(String merchantid, String appid, String openid, List< T> body) {
		String sMessageNo = merchantid + DateTimeFM.getCurTimestamp() + getLastV(IDGenerator.nextID(), 4);
		Message<T> msg = new Message<T>(merchantid, appid, openid, null, null, DateTimeFM.getCurTime(), DateTimeFM.getNextTime(), 
				"utf8",	sMessageNo);
		msg.setBodyDatas(body);
		try {
			calcSign(msg);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return msg;
	}


	public static <T extends MsgBody> String newSendMsg(String merchantid, String appid, String openid, T body) {
		return JsonUtil.bean2Json(newMsg(merchantid, appid, openid, body)).toString();
	}

	public static <T extends MsgBody> String newSendMsg(String merchantid, String appid, String openid, List< T> body) {
		return JsonUtil.bean2Json(newMsg(merchantid, appid, openid, body)).toString();
	}

}
