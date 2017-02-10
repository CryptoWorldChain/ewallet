package com.fr.chain.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;

/**
 * 日期时间处理工具类
 * 
 * @author fcpays 这里面的有些方法引用apache.commons.lang-2.3里的DateUtils类
 */
public class DateUtil extends java.util.Date {
	private static final long serialVersionUID = 1L;

	private final static Map<DateStyle, DateFormat> formats = new HashMap<DateStyle, DateFormat>();

	static {
		for (DateStyle dateStyle : DateStyle.values()) {
			formats.put(dateStyle, new SimpleDateFormat(dateStyle.getValue()));
		}
	}

	/**
	 * 构造函数
	 */
	public DateUtil() {
		super(getSystemCalendar().getTime().getTime());
	}
	
	/**
	 * 格式化日期
	 * @param date
	 * @param dateStyle
	 * @return
	 */
	public static String format(Date date, DateStyle dateStyle) {
		return formats.get(dateStyle).format(date);
	}
	
	/**
     * 格式化当前时间
     * @param date
     * @param dateStyle
     * @return
     */
    public static String formatCurrentTime(DateStyle dateStyle) {
        return formats.get(dateStyle).format(DateUtil.getSystemDate());
    }
	/**
	 * 取当前系统的时间(时间类型为Calendar)。
	 * 
	 * @return Calendar 当前系统时间
	 */
	public static Calendar getSystemCalendar() {
		return Calendar.getInstance();
	}

	/**
	 * 取当前系统的时间
	 * 
	 * @return 时间Date
	 */
	public java.sql.Date getSqlDate() {
		return new java.sql.Date(this.getTime());
	}

	public static java.sql.Date getJavaSqlDate() {
		DateUtil dt = new DateUtil();
		return dt.getSqlDate();
	}

	/**
	 * 取当前系统的时间(时间类型为Date)。
	 * 
	 * @return Date 当前系统时间
	 */
	public static java.util.Date getSystemDate() {
		return new java.util.Date();
	}

	/**
	 * 获得增加一定天数的日期（返回Date类型日期）,引用org.apache.commons.lang.time.DateUtils类addDays(
	 * Date date, int amount)方法。 <br>
	 * 原始的日期没有改变 <br>
	 * 
	 * @param date
	 *            原日期 - 不能为null
	 * @param amount
	 *            天数 - 天数可以为负数
	 * @return Date 返回增加天数后的日期
	 * @throws 如果Date为空时抛出IllegalArgumentException异常
	 */
	public static Date addDays(Date date, int amount) {
		return DateUtils.addDays(date, amount);
	}

	/**
	 * 获得增加几个月的日期（返回Date类型日期）,引用org.apache.commons.lang.time.DateUtils类addMonths
	 * (Date date, int amount)方法。 <br>
	 * 原始的日期没有改变 <br>
	 * 
	 * @param date
	 *            原日期 - 不能为null
	 * @param amount
	 *            增加的月数 - 月数可以为负数
	 * @return Date 返回增加几个月后的日期
	 * @throws 如果Date为空时抛出IllegalArgumentException异常
	 */
	public static Date addMonths(Date date, int amount) {
		GregorianCalendar cal = new GregorianCalendar();
		if (date == null) {
			cal.setTime(new DateUtil());
		} else {
			cal.setTime(date);
		}
		cal.add(java.util.Calendar.MONTH, amount);
		return cal.getTime();
	}

	/**
	 * 获得增加几个小时后的日期（返回Date类型日期）,引用org.apache.commons.lang.time.
	 * DateUtils类addHours(Date date, int amount)方法。 <br>
	 * 原始的日期没有改变 <br>
	 * 
	 * @param date
	 *            原日期 - 不能为null
	 * @param amount
	 *            增加的小时数 - 小时数可以为负数
	 * @return Date 返回增加几个小时后的日期
	 * @throws 如果Date为空时抛出IllegalArgumentException异常
	 */
	public static Date addHours(Date date, int amount) {
		return DateUtils.addHours(date, amount);
	}

	/**
	 * 获得增加n毫秒后的日期（返回Date类型日期）,引用org.apache.commons.lang.time.
	 * DateUtils类addMilliseconds(Date date, int amount)方法。 <br>
	 * 原始的日期没有改变 <br>
	 * 
	 * @param date
	 *            原日期 - 不能为null
	 * @param amount
	 *            增加的毫秒数 - 毫秒数可以为负数
	 * @return Date 返回增加n毫秒后的日期
	 * @throws 如果Date为空时抛出IllegalArgumentException异常
	 */
	public static Date addMilliseconds(Date date, int amount) {
		return DateUtils.addMilliseconds(date, amount);
	}

	/**
	 * 获得增加n秒后的日期（返回Date类型日期）,引用org.apache.commons.lang.time.
	 * DateUtils类addSeconds(Date date, int amount)方法。 <br>
	 * 原始的日期没有改变 <br>
	 * 
	 * @param date
	 *            原日期 - 不能为null
	 * @param amount
	 *            增加的秒数 - 秒数可以为负数
	 * @return Date 返回增加n秒后的日期
	 * @throws 如果Date为空时抛出IllegalArgumentException异常
	 */
	public static Date addSeconds(Date date, int amount) {
		return DateUtils.addSeconds(date, amount);
	}

	/**
	 * 获得增加几个星期后的日期（返回Date类型日期）,引用org.apache.commons.lang.time.
	 * DateUtils类addWeeks(Date date, int amount)方法。 <br>
	 * 原始的日期没有改变 <br>
	 * 
	 * @param date
	 *            date 原日期 - 不能为null
	 * @param amount
	 *            增加的星期数 - 星期数可以为负数
	 * @return Date 返回增加几个星期后的日期
	 * @throws 如果Date为空时抛出IllegalArgumentException异常
	 */
	public static Date addWeeks(Date date, int amount) {
		return DateUtils.addWeeks(date, amount);
	}

	/**
	 * 获得增加几年后的日期（返回Date类型日期）,引用org.apache.commons.lang.time.DateUtils类addYears(
	 * Date date, int amount)方法。 <br>
	 * 原始的日期没有改变 <br>
	 * 
	 * @param date
	 *            原日期 - 不能为null
	 * @param amount
	 *            增加的年数 - 年数可以为负数
	 * @return Date 返回增加几年后的日期
	 * @throws 如果Date为空时抛出IllegalArgumentException异常
	 */
	public static Date addYears(Date date, int amount) {
		return DateUtils.addYears(date, amount);
	}

	/**
	 * 获得增加n分钟后的日期（返回Date类型日期）,引用org.apache.commons.lang.time.
	 * DateUtils类addMinutes(Date date, int amount)方法。 <br>
	 * 
	 * @param date
	 *            原日期 - 不能为null
	 * @param amount
	 *            增加的分钟数 - 分钟数可以为负数
	 * @return Date 返回增加n分钟后的日期
	 * @throws 如果Date为空时抛出IllegalArgumentException异常
	 */
	public static Date addMinutes(Date date, int amount) {		
		long lngTime = date.getTime() - amount * 60 * 1000;
		return new Date(lngTime);

	}

	/**
	 * 取得当前时间n分钟前的时间。
	 * 
	 * @param amount
	 *            分钟
	 * @return Date 返回n分钟前的时间
	 */
	public static Date addMinutes(int amount) {
		Date tsp = getSystemDate();
		long lngTime = tsp.getTime() - amount * 60 * 1000;
		return new Date(lngTime);

	}

	/**
	 * 取得两个日期的天数差（取得date1和date2日期之间的天数差）。
	 * 
	 * @param date1
	 *            开始日期
	 * @param date2
	 *            结束日期
	 * @return long 返回日期2-日期1的天数差
	 */
	public static long getDaysDifference(Date date1, Date date2) {
		Calendar cld1Work = Calendar.getInstance();
		Calendar cld2Work = Calendar.getInstance();
		Calendar cld1 = Calendar.getInstance();
		Calendar cld2 = Calendar.getInstance();
		long lTime1;
		long lTime2;

		cld1Work.setTime(date1);
		cld2Work.setTime(date2);
		cld1.clear();
		cld2.clear();
		cld1.set(cld1Work.get(Calendar.YEAR), cld1Work.get(Calendar.MONTH),
				cld1Work.get(Calendar.DATE));
		cld2.set(cld2Work.get(Calendar.YEAR), cld2Work.get(Calendar.MONTH),
				cld2Work.get(Calendar.DATE));
		lTime1 = (cld1.getTime()).getTime();
		lTime2 = (cld2.getTime()).getTime();

		return (lTime2 - lTime1) / (1000 * 60 * 60 * 24);
	}

	/**
	 * 取得两个日期的小时时差（取得date1日期和date2日期之间的小时差）。
	 * 
	 * @param date1
	 *            开始日期
	 * @param date2
	 *            结束日期
	 * @return long 返回日期2-日期1的小时时差
	 */
	public static long getHoursDifference(Date date1, Date date2) {
		long lTime1 = date1.getTime();
		long lTime2 = date2.getTime();
		return (lTime2 - lTime1) / (1000 * 60 * 60);
	}
	/**
	 * 取得两个日期的分差（取得date1日期和date2日期之间的分差）。
	 * 
	 * @param date1
	 *            开始日期
	 * @param date2
	 *            结束日期
	 * @return long 返回日期2-日期1的分差
	 */
	public static long getMinutesDifference(Date date1, Date date2) {
		long lTime1 = date1.getTime();
		long lTime2 = date2.getTime();
		return (lTime2 - lTime1) / (1000 * 60);
	}

	/**
	 * 取得给定日期当月的最后一天日期。
	 * 
	 * @param dateS
	 *            当月日期
	 * @return Date 返回当月的最后一天
	 */
	public static Date getMonthLastDay(Date dateS) {
		GregorianCalendar gc = new GregorianCalendar();
		if (dateS == null)
			gc.setTime(new DateUtil());
		else
			gc.setTime(dateS);

		gc.add(Calendar.MONTH, 1);
		int days = gc.get(Calendar.DAY_OF_MONTH);
		gc.add(Calendar.DATE, -days);
		Date dateTemp = gc.getTime();
		return dateTemp;
	}

	/**
	 * 按field（小时和月）类型截去日期(向前截取)，引用org.apache.commons.lang.time.
	 * DateUtils类truncate(Date date, int field)方法。 <br>
	 * 例：如果现在的时间是2002 3 28 13:45:01.231, 按小时截取将返回2002 3 28
	 * 13:00:00.000，按月截取则返回2002 3 1 00:00:00.000。 <br>
	 * 
	 * @param date
	 *            与工作相关的日期
	 * @param field
	 *            截取类型
	 * @return Date 返回一个新的日期
	 * @throws 如果参数Date为空时抛出IllegalArgumentException异常
	 *             。
	 */
	public static Date truncate(Date date, int field) {
		return DateUtils.truncate(date, field);
	}

	/**
	 * 比较两个日期对象是否在同一天（不比较时间），引用org.apache.commons.lang.time.DateUtils类isSameDay(
	 * Date date1, Date date2)方法。 <br>
	 * 如：28 Mar 2002 13:45 和 28 Mar 2002 06:01比返回true. 28 Mar 2002 13:45和12 Mar
	 * 2002 13:45比返回false. <br>
	 * 
	 * @param date1
	 *            日期1 - 不能为null
	 * @param date2
	 *            日期2 - 不能为null
	 * @return boolean 同一天返回true, 否则返回false.
	 * @throws 如果Date1和Date2有一个为空时抛出IllegalArgumentException异常
	 */
	public static boolean isSameDay(Date date1, Date date2) {
		return DateUtils.isSameDay(date1, date2);
	}

	/**
	 * 判断传入日期是否在今天之前，在今天之前（包括今天）返回true，否则返回false。 <br>
	 * 
	 * @param date
	 *            要的比较日期
	 * @return boolean 在今天之前（包括今天）返回true，否则返回false。
	 */
	public static boolean isBeforeToday(Date date) {
		boolean blnReturn = true;
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(date);

			Calendar c2 = Calendar.getInstance();
			c2.setTime(new Date());

			blnReturn = c.before(c2);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return blnReturn;
	}

	/**
	 * 判断传入日期是否在今天之后，在今天之后(不包括今天)返回true，在今天之前(包括今天)则返回false。 <br>
	 * 
	 * @param date
	 *            要的比较日期
	 * @return boolean 在今天之后(不包括今天)返回true，在今天之前(包括今天)则返回false
	 */
	public static boolean isAfterToday(Date date) {
		boolean blnReturn = true;
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(date);

			Calendar c2 = Calendar.getInstance();
			c2.setTime(new Date());

			blnReturn = c.after(c2);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return blnReturn;
	}

	/**
	 * 日期格式化（对各种不同日期字符串格式化），按照parsePatterns参数给定的格式模式进行格式，引用org.apache.commons.
	 * lang.time.DateUtils类isSameDay(String str, String[] parsePatterns)方法。 <br>
	 * 
	 * @param str
	 *            需格式的日期串 - 不能为null
	 * @param parsePatterns
	 *            日期格式模式(查看SimpleDateFormat) - 不能为null
	 * @return Date 返回格式后的日期
	 * @throws IllegalArgumentException
	 *             如果日期串为空时抛出IllegalArgumentException异常，
	 * @throws ParseException
	 *             如果parsePatterns数组中没有格式模式则抛出ParseException异常
	 */
	public static Date parseDate(String str, String[] parsePatterns) {
		try {
			if (str == null || parsePatterns == null) {
				throw new IllegalArgumentException(
						"str and Patterns must not be null");
			}
			return DateUtils.parseDate(str, parsePatterns);
		} catch (Exception e) {
			throw new IllegalArgumentException("Unable to parse the date: "
					+ str);
		}
	}

	/**
	 * 获取航程时间(国内机票), 如果结束时间小于开始时间，按照第二天算
	 * 
	 * @param start
	 *            四位数字字符串(小时分钟，九点半如：0930)
	 * @param end
	 *            四位数字字符串(小时分钟，九点半如：0930)
	 * @return String 小时，例1小时30分钟
	 */
	public static String getFlyingTime(String start, String end) {
		int iSt = Integer.valueOf(start).intValue();
		int iEn = Integer.valueOf(end).intValue();
		String rtn = null;
		// 是否第二天
		if (iEn <= iSt) {
			iEn += 2400;
		}
		// 小时
		int hour = iEn / 100 - 1 - iSt / 100;
		// 分钟
		int mini = iEn % 100 + 60 - iSt % 100;

		if (mini >= 60) {
			mini -= 60;
			hour += 1;
		}
		if (mini == 0) {
			rtn = hour + "小时";
		} else if (hour == 0) {
			rtn = mini + "分钟";
		} else {
			rtn = hour + "小时" + mini + "分钟";
		}
		return rtn;
	}

	/**
	 * @param str
	 * @return
	 */
	@SuppressWarnings("unused")
	private static String formatString(String str) {

		if (str == null || "".equals(str)) {
			return str = "null or empty";
		}

		if (str.length() == 10) {
			return str + " 00:00:00";

		} else if (str.length() > 10) {
			String[] sDatas = str.split("\\.");

			if (sDatas.length > 2) {
				if (sDatas.length > 1) {
					String[] sDates = sDatas[0].split("-");
					if (sDates[1].length() == 1)
						sDates[1] = "0" + sDates[1];
					if (sDates[2].length() == 1)
						sDates[2] = "0" + sDates[2];

					for (int i = 1; i < 4; i++) {
						sDatas[i] = sDatas[i].trim();
						if (sDatas[i].length() == 1)
							sDatas[i] = "0" + sDatas[i];
					}
					str = sDates[0] + "-" + sDates[1] + "-" + sDates[2] + " "
							+ sDatas[1] + ":" + sDatas[2] + ":" + sDatas[3]
							+ ".000000000";
				}
			}
			return str;
		} else {
			return str = "null or empty";
		}
	}
    /**
    * 将字符串转化为日期类型
    * 
    * @param pattern
    *            转化格式
    * @param dateStr
    *            要转化的字符串
    * @return
    */
   public static Date getDate(String pattern, String dateStr) {
       Date date = null;

       try {
           SimpleDateFormat formatter = new SimpleDateFormat(pattern);
           date = formatter.parse(dateStr);
       } catch (ParseException e) {
           e.printStackTrace();
       }

       return date;
   }

   /**
    * 获取时间字符串
    * 
    * @param pattern
    * @return
    */
   public static String formatDate(String pattern, Date date) {
       String str = null;
       try {
           str = new SimpleDateFormat(pattern).format(date);
       } catch (Exception e) {
           e.printStackTrace();
       }
       return str;
   }

	// /**
	// * 检查传入的字符串日期格式是否为yyyy-MM-dd格式（是指定的日期格式返回true；否则返回false）
	// * @param str 被检查的字符串日期
	// * @return boolean 是指定的日期格式返回true；否则返回false
	// */
	// public static boolean isFormatDate(String str) {
	// return isFormatDate(str, "yyyy-MM-dd");
	// }
	//
	// /**
	// * 检查传入的日期格式是否为指定的日期格式（是指定的日期格式返回true；否则返回false）
	// * @param str 被检查的日期
	// * @param formatStr 指定的日期格式
	// * @return boolean 是指定的日期格式返回true；否则返回false
	// */
	// public static boolean isFormatDate(String str, String formatStr) {
	// if (!ValidateUtil.isBlank(formatStr)) {
	// return false;
	// }
	// final SimpleDateFormat sdFormat = new SimpleDateFormat(formatStr);
	// Date dateCompare = null;
	// String strDate = "";
	// try {
	// dateCompare = sdFormat.parse(str, new ParsePosition(0));
	// strDate = sdFormat.format(dateCompare);
	// } catch (final Exception e) {
	// return false;
	// }
	// if (dateCompare == null) {
	// return false;
	// } else {
	// return strDate.equals(str);
	// }
	// }

}
