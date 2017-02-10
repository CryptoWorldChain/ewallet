package com.fr.chain.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
public class DateRangeUtil {
	
	/*	add by ramos */
	public static String getFullTime(){
		String time="";
		Date now = new Date();		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		time = dateFormat.format(now); 
		return time;		
	}
	
	public static String getDateFormat(Date date){
		String time="";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		time = dateFormat.format(date);
		return time;
	}
	
	/* only for test*/
	public static String getFiveMinAgo(){
		String time="";
		Date now = new Date(System.currentTimeMillis()-60*5); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		time = dateFormat.format(now); 
		return time;			
	}
	
	
	public static String getYesterday(){
		String time="";
		Date now = new Date(System.currentTimeMillis()-1000*60*60*24); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		time = dateFormat.format(now); 
		return time;			
	}
	
	//three months age
	public static String getSeasonAgo(){
		
		Calendar calendar = Calendar.getInstance();
		//calendar.add(Calendar.DATE, -1);//得到前一天
		calendar.add(Calendar.MONTH, -3); //得到前3个月		
		return getDateFormat(calendar.getTime());
	}
	
	public static String getTendaysAgo(){
		String time="";
		Date now = new Date(System.currentTimeMillis()-1000*60*60*24*10); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		time = dateFormat.format(now); 
		return time;			
	}
	
	public static String getAllTime(){
		String time="";
		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		time = dateFormat.format(now); 
		return time;		
	}
	
	public static String getDateTime(){
		String time="";
		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		time = dateFormat.format(now); 
		return time;		
	}

	public static Date getStartOfDay(Date date) {
		return DateUtil.truncate(date, Calendar.DAY_OF_MONTH);
	}

	public static Date[] getDayRange(Date date) {
		Date start = getStartOfDay(date);
		Date end = DateUtil.addMilliseconds(DateUtil.addDays(start, 1), -1);
		return new Date[] { start, end };
	}

	public static Date getStartOfMonth(Date date) {
		return DateUtil.truncate(date, Calendar.MONDAY);
	}

	public static Date[] getMonthRange(Date date) {
		Date start = getStartOfMonth(date);
		Date end = DateUtil.addMilliseconds(DateUtil.addMonths(start, 1), -1);
		return new Date[] { start, end };
	}

	public static Date getStartOfYear(Date date) {
		return DateUtil.truncate(date, Calendar.YEAR);
	}

	public static Date[] getYearRange(Date date) {
		Date start = getStartOfYear(date);
		Date end = DateUtil.addMilliseconds(DateUtil.addYears(start, 1), -1);
		return new Date[] { start, end };
	}

	public static Date[] getYearRange(int year){
		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			c.setTime(df.parse("2012-01-01 00:00:00"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.set(Calendar.YEAR, year);
		Date start = c.getTime();
		c.set(Calendar.YEAR, year + 1);
		Date end = c.getTime();
		return new Date[] { start, end };
	}
	
	public static int getRandomNo(){
		int ss = (int) (Math.random()*9000+1000);
		return ss;
	}
	
	public static Date stringToDate(String stime,String stype) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(stype);
		Date sd = sdf.parse(stime);
		return sd;
	}
	
	//public static void main(String[] args) {
		/*
		Date calcdate = new Date();

		Date start = DateUtils.truncate(calcdate, Calendar.DAY_OF_MONTH);
		Date end = DateUtils.addDays(start, 1);
		System.out.println("calcdate=" + calcdate + ":start=" + start + ":end="
				+ end);
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		try {
			calcdate = df.parse("2012/10/18");

			System.out.println(getStartOfWeek(calcdate));

			Date[] drange = getDayRange(calcdate);
			System.out.println("day::calcdate=" + calcdate + ":start="
					+ drange[0] + ":end=" + drange[1]);

			Date[] range = getWeekRange(calcdate);
			System.out.println("weekly::calcdate=" + calcdate + ":start="
					+ range[0] + ":end=" + range[1]);

			Date[] mrange = getMonthRange(calcdate);
			System.out.println("month::calcdate=" + calcdate + ":start="
					+ mrange[0] + ":end=" + mrange[1]);
			Date[] yrange = getYearRange(calcdate);
			System.out.println("year::calcdate=" + calcdate + ":start="
					+ yrange[0] + ":end=" + yrange[1]);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	*/
	
	//}
}
