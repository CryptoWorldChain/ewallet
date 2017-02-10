package com.fr.chain.web.action;

import java.util.List;

import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fr.chain.web.bean.ReturnInfo;

public class BasicCtrl {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public ReturnInfo handleMethodArgumentNotValidException( MethodArgumentNotValidException error ) {
		
		//return  ReturnInfo.Faild;
	    List<ObjectError> errors =  error.getBindingResult().getAllErrors();
	   
	    StringBuffer errorStr = new StringBuffer();
	    int count = 0;
	    for(ObjectError  er: errors){
	    	if(count != 0){
	    		
	    		errorStr.append("∞");
	    	}
	    	errorStr.append(er.getDefaultMessage());
	    	count++;
	    }
		return new ReturnInfo(  (errorStr.toString()), 0, null,false);
	}	
	
}
