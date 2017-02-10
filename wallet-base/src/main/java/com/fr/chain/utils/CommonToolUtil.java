package com.fr.chain.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @date 2014.10.11 15:40
 * @author APaul
 * @desc Create some common utility methods class
 */
public class CommonToolUtil {

	public static Date strToDate(String str){
		Date date = null;
		if(str!=null&&str!=""&&!"".equals(str)){
			try {
				SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
				date = s.parse(str);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return date;
	}
}
