package com.fr.chain.utils;

import java.io.File;
import java.util.regex.Pattern;

/**
 * 数据验证工具类
 * @author fcpays
 *
 */
public class CheckUtil{
    /**
     * 默认构造器
     */
    private CheckUtil() {
    	
    }

	/**
	 * 检查是否为合法的Email(合法Email返回true，非法Email返回false)
	 * @param mailStr 被检查字符串Email
	 * @return boolean 合法返回true，非法返回false
	 */
	public static boolean isMailAddressTrue(String mailStr) {
		if (mailStr == null) {
			return false;
		}
		String mailstr = "[\\w|.]{3,16}@[\\w+\\.]+[\\w]{2,3}";
		Pattern pn = Pattern.compile(mailstr);
		boolean b = pn.matcher(mailStr).matches();
		System.out.println(b);
		return b;
	}
	
	/**
	 * 判断文件是否存在(文件存在返回true,文件不存在返回false)
	 * @param fileSrc 文件路径
	 * @return boolean 文件存在返回true，文件不存在返回false.
	 */
	public static boolean isFileExist(String fileSrc) {
		File file = new File(fileSrc);
		return (file.exists());
	}
}
