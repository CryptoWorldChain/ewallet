package com.fr.chain.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import com.fr.chain.message.MessageBuilder;

@Component("httpRequestHelper")
@Slf4j
public class HttpRequestHelper {
	public String getJsonTxt(HttpServletRequest req)  {
		String jsontxt = null;
	      try {
			req.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
	     
		try {							
			   jsontxt = MessageBuilder.getRequestContent(req);			
		} catch (IOException e) {
			jsontxt = "Body is Null";
		}
		log.debug("[RECV]:" + jsontxt);
		return jsontxt;
	}

}
