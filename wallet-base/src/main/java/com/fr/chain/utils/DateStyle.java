package com.fr.chain.utils;
public enum DateStyle {  
      
	YYYY("yyyy"),
    MM_DD("MM-dd"),  
    YYYY_MM("yyyy-MM"),  
    YYYY_MM_DD("yyyy-MM-dd"),  
    MM_DD_HH_MM("MM-dd HH:mm"),  
    MM_DD_HH_MM_SS("MM-dd HH:mm:ss"),  
    MM_DD_HH_MM_SS_SSS("MM-dd HH:mm:ss SSS"),  
    YYYY_MM_DD_HH("yyyy-MM-dd HH"),  
    YYYY_MM_DD_HH_MM("yyyy-MM-dd HH:mm"),  
    YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss"),  
    YYYY_MM_DD_HH_MM_SS_SSS("yyyy-MM-dd HH:mm:ss SSS"),  
      
    MM_DD_EN("MM/dd"),  
    YYYY_MM_EN("yyyy/MM"),  
    YYYY_MM_DD_EN("yyyy/MM/dd"),  
    MM_DD_HH_MM_EN("MM/dd HH:mm"),  
    MM_DD_HH_MM_SS_EN("MM/dd HH:mm:ss"),  
    MM_DD_HH_MM_SS_SSS_EN("MM/dd HH:mm:ss SSS"),  
    YYYY_MM_DD_HH_MM_EN("yyyy/MM/dd HH:mm"),  
    YYYY_MM_DD_HH_MM_SS_EN("yyyy/MM/dd HH:mm:ss"),  
    YYYY_MM_DD_HH_MM_SS_SSS_EN("yyyy/MM/dd HH:mm:ss SSS"),  
      
    MM_DD_CN("MM月dd日"),  
    YYYY_MM_CN("yyyy年MM月"),  
    YYYY_MM_DD_CN("yyyy年MM月dd日"),  
    MM_DD_HH_MM_CN("MM月dd日 HH时mm分"),  
    MM_DD_HH_MM_SS_CN("MM月dd日 HH时mm分ss秒"),  
    MM_DD_HH_MM_SS_SSS_CN("MM月dd日 HH时mm分ss秒SSS"),  
    YYYY_MM_DD_HH_MM_CN("yyyy年MM月dd日 HH时mm分"),  
    YYYY_MM_DD_HH_MM_SS_CN("yyyy年MM月dd日 HH时mm分ss秒"),  
    YYYY_MM_DD_HH_MM_SS_SSS_CN("yyyy年MM月dd日 HH时mm分ss秒SSS"),  
      
    HH_MM("HH:mm"),  
    HH_MM_SS("HH:mm:ss"),
	HH_MM_SS_SSS("HH:mm:ss SSS"),
      
	YYYYMMDD("yyyyMMdd"),
	YYYYMMDDHHMMSS("yyyyMMddHHmmss"),
	YYYYMMDDHHMMSSSSS_EN("yyyyMMddHHmmssSSS");
      
    private String value;  
      
    DateStyle(String value) {  
        this.value = value;  
    }  
      
    public String getValue() {  
        return value;  
    }  
}  