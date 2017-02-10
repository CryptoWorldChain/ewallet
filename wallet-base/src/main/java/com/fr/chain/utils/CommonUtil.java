package com.fr.chain.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fr.chain.web.bean.PageInfo;

public class CommonUtil {
	public static Date getTime() {

		Date now = new Date();

		return now;
	}

	public static String getTime2() {

		String time = "";
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("HHmmss");
		time = dateFormat.format(now);
		return time;
	}

	public static long getnanosec() {
		long s = System.nanoTime();
		return s;
	}

	public static String getTime3() {

		String time = "";
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		time = dateFormat.format(now);
		return time;
	}

	public static int ckeyCompare(List<String> list, String keys) {
		int res = 1;
		String str = null;
		for (int i = 0; i < list.size(); i++) {
			str = list.get(i);
			if (str.equals(keys)) {
				res = 0;
			}
		}
		return res;
	}



	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	public static int getCmdType(String source) {
		int res = 0;
		String temp = null;
		String[] aa = source.split("\\,");
		for (int y = 0; y < aa.length; y++) {
			if (aa[y].indexOf("CmdType") > -1) {
				// System.out.printf( " xxxxxxx1= " + aa[y] + "\n");
				String[] bb = aa[y].split("\\:");
				for (int yx = 0; yx < bb.length; yx++) {
					// System.out.printf( " yyyyy = " + bb[yx] + "\n");
					if (bb[yx].indexOf("\"") >= 0) {
						temp = bb[yx].replaceAll("\"", "");
					}
					// System.out.print("temp = " + temp + "\n");
					if (temp.indexOf("}") >= 0) {
						temp = temp.replaceAll("\\}", "");
					}
					// System.out.print("temp = " + temp + "id = " +
					// temp.indexOf("{") +"\n");
					if (temp.indexOf("{") >= 0) {
						temp = temp.replaceAll("\\{", "");
					}
					if (isNumeric(temp.trim())) {
						res = Integer.valueOf(temp.trim());
						break;
					}
				}
			}
		}
		return res;
	}
	
	
	public static String getAllTime(){
		String time="";
		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		time = dateFormat.format(now); 
		return time;		
	}
	
	public static int getRandomNo(){
		int ss = (int) (Math.random()*9000+1000);
		return ss;
	}
	
	public static String getOrderid(String cmdtype){
		String ordid = new String();
		if(cmdtype.length()==1){
			ordid ="FC000"+cmdtype + getAllTime() + getRandomNo();
		}else if(cmdtype.length()==2){
			ordid ="FC00"+cmdtype + getAllTime() + getRandomNo();
		}
		return ordid;
	}
	
    /**
     * 判断是否需要计算总笔数, 并取得变更後的sql语法
     * 
     * @param sql
     * @param para
     * @return sql
     */
    public static String getSqlByPageInfo(StringBuffer sql, PageInfo para) {

        if (para == null) {
            sql.insert(0, "SELECT count(*) AS COUNT FROM ( ");
            sql.append(")tmp ");
        } else {
            sql.append(" LIMIT " + para.getSkip() + "," + para.getLimit());
        }

        return sql.toString();
    }


}
