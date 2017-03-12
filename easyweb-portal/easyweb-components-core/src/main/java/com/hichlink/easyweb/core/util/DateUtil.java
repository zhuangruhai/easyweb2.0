package com.hichlink.easyweb.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * <b>Title：</b>DateUtil.java<br/>
 * <b>Description：</b> 日期操作工具类<br/>
 * <b>@author： </b>zhuangruhai<br/>
 * <b>@date：</b>2012-7-2 下午04:05:18<br/>
 * <b>Copyright (c) 2012 ASPire Tech.</b>
 * 
 */
public class DateUtil {
	protected static Logger log = LoggerFactory.getLogger(DateUtil.class);
	public static final String PATTEM_DATE = "yyyy-MM-dd";
	public static final String PATTEM_DATE2 = "yyyy年MM月dd日";
	public static final String PATTEM2_DATE = "yyyyMMdd";
	public static final String PATTEM_TIME = "HH:mm:ss";
	public static final String PATTEM_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
	public static final String PATTEM_DATE_TIME2 = "yyyyMMddHHmmss";

	public static final String PATTEM_TIMESTAMP = "yyyy-MM-dd HH:mm:ss.SSS";

	public static final String PATTEM_DAY_BEGIN = "yyyy-MM-dd 00:00:00";
	public static final String PATTEM_DAY_END = "yyyy-MM-dd 23:59:59";
	public static final long ONEDAY_MILLISECONDS = 24 * 60 * 60 * 1000;

	public static final int DATE_COMPARE_BEFORE = -1;
	public static final int DATE_COMPARE_BETWEEN = 0;
	public static final int DATE_COMPARE_AFTER = 1;

	/**
	 * 将字符串转换成时间格式
	 * 
	 * @param value
	 *            字符串
	 * @param pattem
	 *            格式
	 * @return 时间类型对象
	 * @author zhuangruhai
	 */
	public static Date getDate(String value, String pattem) {
		SimpleDateFormat format = null;
		if (pattem != null) {
			format = new SimpleDateFormat(pattem);
		} else {
			format = new SimpleDateFormat(PATTEM_DATE_TIME);
		}
		try {
			return format.parse(value);
		} catch (Exception e) {
			log.error("getDate:", e);
			return null;
		}
	}

	/**
	 * 把字符转换成时间，格式为默认格式
	 * 
	 * @param value
	 *            字符串
	 * @return 时间类型对象
	 * @author zhuangruhai
	 */
	public static Date getDate(String value) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(PATTEM_DATE_TIME);
			return format.parse(value);
		} catch (Exception e) {
			log.error("getDate:", e);
			return null;
		}
	}

	/**
	 * 格式化时间
	 * 
	 * @param date
	 *            时间
	 * @param pattem
	 *            格式
	 * @return 字符串类型的时间
	 * @author zhuangruhai
	 */
	public static String formatDate(Date date, String pattem) {
		if (date != null) {
			try {
				SimpleDateFormat format = new SimpleDateFormat(pattem);
				return format.format(date);
			} catch (Exception e) {
				return "";
			}

		}
		return "";
	}

	/**
	 * 以默认格式格式化时间
	 * 
	 * @param date
	 *            时间
	 * @return 字符串类型的时间
	 * @author zhuangruhai
	 */
	public static String formatDate(Date date) {
		if (date != null) {
			SimpleDateFormat format = new SimpleDateFormat(PATTEM_DATE_TIME);
			return format.format(date);
		}
		return null;
	}

	/**
	 * 获得当前时间的默认格式字符串
	 * 
	 * @return 字符串类型的时间
	 * @author zhuangruhai
	 */
	public static String now() {
		return formatDate(new Date());
	}
	public static String now2() {
		return formatDate(new Date(),PATTEM_DATE_TIME2);
	}

	/**
	 * 获得今天时间日期格式的字符串
	 * 
	 * @return 字符串类型的日期 yyyy-MM-dd
	 * @author zhuangruhai
	 */
	public static String getToday() {
		return formatDate(new Date(), PATTEM_DATE);
	}

	/**
	 * 获得今天时间日期格式的字符串
	 * 
	 * @return 字符串类型的日期 yyyyMMdd
	 * @author zhuangruhai
	 */
	public static String getToday2() {
		return formatDate(new Date(), PATTEM2_DATE);
	}

	/**
	 * 获得明天时间日期格式的字符串
	 * 
	 * @return 字符串类型的日期
	 * @author zhuangruhai
	 */
	public static String getTomorrow() {
		long time = new Date().getTime() + ONEDAY_MILLISECONDS;
		return formatDate(new Date(time), PATTEM_DATE);
	}

	/**
	 * 获得昨时间日期格式的字符串
	 * 
	 * @return 字符串类型的日期
	 * @author zhuangruhai
	 */
	public static String getYesterday() {
		long time = new Date().getTime() - ONEDAY_MILLISECONDS;
		return formatDate(new Date(time), PATTEM_DATE);
	}

	/**
	 * 获得今天开始时间点 如：2011-09-09 00:00:00
	 * 
	 * @return 今天开始时间
	 * @author zhuangruhai
	 */
	public static String getTodayBegin() {
		return formatDate(new Date(), PATTEM_DAY_BEGIN);
	}

	/**
	 * 获得今天的结束时间 如：2011-09-09 23:59:59
	 * 
	 * @return 今天的结束时间
	 * @author zhuangruhai
	 */
	public static String getTodayEnd() {
		return formatDate(new Date(), PATTEM_DAY_END);
	}

	/**
	 * 获得某天的开始时间
	 * 
	 * @param dateStr
	 *            字符类型的时间
	 * @return 某天的开始时间
	 * @author zhuangruhai
	 */
	public static String getDayBegin(String dateStr) {
		if (StringTools.isEmptyString(dateStr) || dateStr.length() < 10)
			return null;
		return dateStr.substring(0, 10) + " 00:00:00";
	}

	/**
	 * 获得某天的开始时间
	 * 
	 * @param date
	 *            时间
	 * @return 某天的开始时间
	 * @author zhuangruhai
	 */
	public static String getDayBegin(Date date) {
		if (date == null)
			return null;
		return formatDate(date, PATTEM_DAY_BEGIN);
	}

	/**
	 * 获得某天的结束时间
	 * 
	 * @param dateStr
	 *            字符类型的时间
	 * @return 某天的结束时间
	 * @author zhuangruhai
	 */
	public static String getDayEnd(String dateStr) {
		if (StringTools.isEmptyString(dateStr) || dateStr.length() < 10)
			return null;
		return dateStr.substring(0, 10) + " 23:59:59";
	}

	/**
	 * 获得某天的结束时间
	 * 
	 * @param date
	 *            时间
	 * @return 某天的结束时间
	 * @author zhuangruhai
	 */
	public static String getDayEnd(Date date) {
		if (date == null)
			return null;
		return formatDate(date, PATTEM_DAY_END);
	}

	/**
	 * 当前时间与一段时间进行比较
	 * 
	 * @param begin
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @return 比较结果 -1 在时间段之前，0 在时间段内，1 在时间段后
	 * @author zhuangruhai
	 */
	public static int compareTimeByNow(Date begin, Date end) {
		long now = new Date().getTime();
		if (now < begin.getTime())
			return DATE_COMPARE_BEFORE;
		if (now > end.getTime())
			return DATE_COMPARE_AFTER;

		return DATE_COMPARE_BETWEEN;
	}

	/**
	 * 当前时间与一段时间进行比较
	 * 
	 * @param begin
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @return 比较结果 -1 在时间段之前，0 在时间段内，1 在时间段后
	 * @author zhuangruhai
	 */
	public static int compareTimeByNow(String begin, String end) {
		long now = new Date().getTime();
		if (now < getDate(begin).getTime())
			return DATE_COMPARE_BEFORE;
		if (now > getDate(end).getTime())
			return DATE_COMPARE_AFTER;

		return DATE_COMPARE_BETWEEN;
	}

	/**
	 * 判断当前时间是否在一段时间内
	 * 
	 * @param begin
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @return 比较结果 true 在时间段内 false 不在时间段内
	 * @author zhuangruhai
	 */
	public static boolean isBetweenTime(Date begin, Date end) {
		return compareTimeByNow(begin, end) == DATE_COMPARE_BETWEEN;
	}

	/**
	 * 判断当前时间是否在一段时间内
	 * 
	 * @param begin
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @return 比较结果 true 在时间段内 false 不在时间段内
	 * @author zhuangruhai
	 */
	public static boolean isBetweenTime(String begin, String end) {
		return compareTimeByNow(begin, end) == DATE_COMPARE_BETWEEN;
	}

	/**
	 * 计算当前时间与给出时间的毫秒数
	 * 
	 * @param date
	 *            时间点
	 * @return 毫秒数
	 * @author zhuangruhai
	 */
	public static long calTimeByNow(Date date) {
		return new Date().getTime() - date.getTime();
	}

	/**
	 * 计算当前时间与给出时间的毫秒数
	 * 
	 * @param date
	 *            时间点
	 * @return 毫秒数
	 * @author zhuangruhai
	 */
	public static long calTimeByNow(String date) {
		return new Date().getTime() - getDate(date).getTime();
	}

	/**
	 * 在当前时间点上加上/减去一定天数
	 * 
	 * @param days
	 *            需要加/减的天数
	 * @return 加上/减去后的时间点
	 * @author zhuangruhai
	 */
	public static Date addDays(int days) {
		return new Date(new Date().getTime() + days * ONEDAY_MILLISECONDS);
	}

	/**
	 * 在当前时间点上加上/减去一定天数 并转成字符串返回
	 * 
	 * @param days
	 *            需要加/减的天数
	 * @return 加上/减去后的时间点
	 * @author zhuangruhai
	 */
	public static String addDaysForStr(int days) {
		return formatDate(addDays(days));
	}

	/**
	 * 在当前时间点上加上/减去一定的毫秒数
	 * 
	 * @param milliseconds
	 *            加上/减去的毫秒数
	 * @return 加上/减去后的时间点
	 * @author zhuangruhai
	 */
	public static Date addMilliseconds(int milliseconds) {
		return new Date(new Date().getTime() + milliseconds);
	}

	/**
	 * 在当前时间点上加上/减去一定的毫秒数 并转成字符串返回
	 * 
	 * @param milliseconds
	 *            加上/减去的毫秒数
	 * @return 加上/减去后的时间点
	 * @author zhuangruhai
	 */
	public static String addMillisecondsForStr(int milliseconds) {
		return formatDate(addMilliseconds(milliseconds));
	}

	/**
	 * 在当前时间点萨很难过加上/减去一定的秒数
	 * 
	 * @param seconds
	 *            加上/减去的秒数
	 * @return 加上/减去后的时间点
	 * @author zhuangruhai
	 */
	public static Date addSeconds(int seconds) {
		return new Date(new Date().getTime() + 1000 * seconds);
	}

	/**
	 * 在当前时间点萨很难过加上/减去一定的秒数 并转成字符串返回
	 * 
	 * @param seconds
	 *            加上/减去的秒数
	 * @return 加上/减去后的时间点
	 * @author zhuangruhai
	 */
	public static String addSecondsForStr(int seconds) {
		return formatDate(addSeconds(seconds));
	}

	public static int getMin(Date d) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(d);
		return now.get(Calendar.MINUTE);
	}

	/**
	 * 获取Date中的小时(24小时)
	 * 
	 * @param d
	 * @return
	 */
	public static int getHour(Date d) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(d);
		return now.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取Date中的秒
	 * 
	 * @param d
	 * @return
	 */
	public static int getSecond(Date d) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(d);
		return now.get(Calendar.SECOND);
	}

	/**
	 * 获取xxxx-xx-xx的日
	 * 
	 * @param d
	 * @return
	 */
	public static int getDay(Date d) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(d);
		return now.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取月份，1-12月
	 * 
	 * @param d
	 * @return
	 */
	public static int getMonth(Date d) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(d);
		return now.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取19xx,20xx形式的年
	 * 
	 * @param d
	 * @return
	 */
	public static int getYear(Date d) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(d);
		return now.get(Calendar.YEAR);
	}

	public static String getYYYYMMDDHHMMSSDate(Date date) {
		if (date == null)
			return null;
		String yyyy = getYear(date) + "";
		String mm = getMonth(date) + "";
		String dd = getDay(date) + "";
		String hh = getHour(date) + "";
		String min = getMin(date) + "";
		String ss = getSecond(date) + "";

		mm = StringUtils.rightPad(mm, 2, "0");
		dd = StringUtils.rightPad(dd, 2, "0");
		hh = StringUtils.rightPad(hh, 2, "0");
		min = StringUtils.rightPad(min, 2, "0");
		ss = StringUtils.rightPad(ss, 2, "0");

		return yyyy + mm + dd + hh + min + ss;
	}

	public static String getCurTimeStamp() {
		return formatDate(new Date(), PATTEM_TIMESTAMP);
	}

	/**
	 * 根据给定年月得到上一个月，格式：yyyyMM 如果传入格式不符合要求，将反回""
	 * 
	 * @param yearMonth
	 *            支持格式：yyyyMM yyyy-MM yyyy-MM-dd
	 * @return month 格式：yyyyMM
	 */
	public static String getBeforeMonth(String yearMonth) {
		String beforeMonth = "";
		if (null == yearMonth) {
			return "";
		}
		yearMonth = yearMonth.replace("-", "");
		if (yearMonth.length() == 6 || yearMonth.length() == 5
				|| yearMonth.length() == 8) {
			try {
				Integer.parseInt(yearMonth);
			} catch (Exception e) {
				log.error("Integer.parseInt(yearMonth)=>", e);
			}
			String year = yearMonth.substring(0, 4);
			Integer yearI = Integer.parseInt(year);
			String month = yearMonth.substring(4, 6);
			Integer monthI = Integer.parseInt(month);
			if (monthI >= 10) {
				if (monthI == 10) {
					beforeMonth = year + "0" + (monthI - 1);
				} else {
					beforeMonth = year + (monthI - 1);
				}
			} else if (monthI == 1) {
				beforeMonth = (yearI - 1) + "12";
			} else {
				beforeMonth = year + "0" + (monthI - 1);
			}
		}
		return beforeMonth;
	}

	/**
	 * 根据给定年月得到上一个月，格式：yyyyMM 如果传入格式不符合要求，将反回""
	 * 
	 * @param yearMonth
	 *            支持格式：yyyyMM yyyy-MM yyyy-MM-dd
	 * @return month 格式：yyyyMM
	 */
	public static String getAfterMonth(String yearMonth) {
		String afterMonth = "";
		if (null == yearMonth) {
			return "";
		}
		yearMonth = yearMonth.replace("-", "");
		if (yearMonth.length() == 6 || yearMonth.length() == 5
				|| yearMonth.length() == 8) {
			try {
				Integer.parseInt(yearMonth);
			} catch (Exception e) {
				log.error("Integer.parseInt(yearMonth)", e);
			}
			String year = yearMonth.substring(0, 4);
			Integer yearI = Integer.parseInt(year);
			String month = yearMonth.substring(4, 6);
			Integer monthI = Integer.parseInt(month);
			if (monthI >= 9) {
				afterMonth = year + (monthI + 1);
				if (monthI == 12) {
					afterMonth = (yearI + 1) + "01";
				}
			} else {
				afterMonth = year + "0" + (monthI + 1);
			}
		}
		return afterMonth;
	}

	/**
	 * 根据传个日期得到月年，格式：yyyyMM 如传入格式不对，原格式反回
	 * 
	 * @param date
	 *            日期，支持格式：yyyy-MM-dd yyyy.MM.dd yyyy/MM/dd
	 * @return value yyyyMM
	 */
	public static String getYearMonth(String date) {
		String value = "";
		if (null == date || date.equals("")) {
			return "";
		}
		if (date.contains("-")) {
			date = date.replace("-", "");
		} else if (date.contains(".")) {
			date = date.replace(".", "");
		} else if (date.contains("/")) {
			date = date.replace("/", "");
		} else {
			return date;
		}
		String year = date.substring(0, 4);
		String month = date.substring(4, 6);
		value = year + month;
		return value;
	}

	/**
	 * 根据两个时间，得到之间间隔，不保护当月数，如 2014-02 2014-05 间隔为2个月 即可3，4两个月 支持参数格式：yyyy-MM-dd
	 * yyyy-MM yyyyMM
	 * 
	 * @param starDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @return
	 */
	public static int getMonthSpacingNum(String starDateStr, String endDateStr) {
		int monthSpacingNum = 0;
		if (null == starDateStr || "".equals(starDateStr) || null == endDateStr
				|| "".equals(endDateStr)) {
			log.error("getMonthSpacingNum() 时间格式错误starDateStr=" + starDateStr
					+ "，endDateStr=" + endDateStr
					+ " 支持参数格式：yyyy-MM-dd yyyy-MM yyyyMM");
			return monthSpacingNum;
		}
		if (starDateStr.indexOf("-") == -1
				&& (starDateStr.length() == 5 || starDateStr.length() == 6)) {
			starDateStr = starDateStr.substring(0, 4) + "-"
					+ starDateStr.substring(4, starDateStr.length());
		}
		if (endDateStr.indexOf("-") == -1
				&& (endDateStr.length() == 5 || endDateStr.length() == 6)) {
			endDateStr = endDateStr.substring(0, 4) + "-"
					+ endDateStr.substring(4, endDateStr.length());
		}
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM");
		try {
			Date starDate = sd.parse(starDateStr);
			Date endDate = sd.parse(endDateStr);
			int sYear = getYear(starDate);
			int sMonth = getMonth(starDate);
			int eYear = getYear(endDate);
			int eMonth = getMonth(endDate);
			int fullMonth = 12; // 一年的十二个月
			if (sYear - eYear <= -1) {
				// 表示结束时间相对当前是近期时间，开始时间是相对结束时间的过去时间
				// 如starDateStr=2013-04,endDateStr=2014-06
				int yearSpacingNum = eYear - sYear - 1;// 年间隔，除去本年,
														// 如2014年间隔2012年数为1
				// 计算开始年到的月份到12月份的间隔数， 如2013-04 12-4=8 8就是间隔数之一
				int monthSNumTemp = fullMonth - sMonth;
				// 结束年的月份数就是间隔数，如2014-06 6就是间隔数之一
				monthSpacingNum = monthSNumTemp + eMonth - 1;
				// 加上年份间隔月,1年12个月
				monthSpacingNum += yearSpacingNum * fullMonth;
			} else if (sYear - eYear >= 1) {
				// 表示开始时间相对当前是近期时间，结束时间是相对开始时间的过去时间
				// 如starDateStr=2014-04,endDateStr=2013-06
				int yearSpacingNum = sYear - eYear - 1;// 年间隔，除去本年,
														// 如2014年间隔2013年数为1
				// 计算结束年到12月份的间隔数，如2013-07 12-7=5 5就是间隔数之一
				int monthSNumTemp = fullMonth - eMonth;
				// 开始年的月份数就是间隔数，如2014-05 5就是间隔数之一，当前月要去掉，
				monthSpacingNum = monthSNumTemp + sMonth - 1;
				// 加上年份间隔月,1年12个月
				monthSpacingNum += yearSpacingNum * fullMonth;
			} else {
				// 表示在同一年的，直接月份计算
				if (sMonth - eMonth <= -1) {
					// 表示开始时间相对结束时间为过去时间，如2014-03 2014-08
					monthSpacingNum = eMonth - sMonth - 1;
				} else if (sMonth - eMonth >= 1) {
					// 表示结束时间相对开始时间为过去时间，如2014-08 2014-03
					monthSpacingNum = sMonth - eMonth - 1;
				}
			}
		} catch (ParseException e) {
			log.error("时间格式错误starDateStr=" + starDateStr + "，endDateStr="
					+ endDateStr, e);
			e.printStackTrace();
		}
		return monthSpacingNum;
	}

	/**
	 * 返回天数
	 * 
	 * @param date1
	 *            开始
	 * @param date2结束
	 * @return
	 */
	public static int betweenDays(Date date1, Date date2) {
		long min = date2.getTime() - date1.getTime();
		long d = min / 1000 / 3600 / 24;
		return new Long(d).intValue();
	}

	public static int betweenMonths(Date date1, Date date2) {
		Calendar cal1 = new GregorianCalendar();
		cal1.setTime(date1);
		Calendar cal2 = new GregorianCalendar();
		cal2.setTime(date2);
		int c = (cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR)) * 12
				+ cal1.get(Calendar.MONTH) - cal2.get(Calendar.MONTH);
		return c == 0 && cal1.get(Calendar.DATE) > cal2.get(Calendar.DATE) ? 1
				: c;
	}

	public static void main(String[] args) {
		System.out.println(formatDate(new Date(), PATTEM2_DATE));
		// String starDateStr = "201403";
		// String endDateStr = "2014-5";
		// System.out.println(DateUtil.getMonthSpacingNum(starDateStr,
		// endDateStr));
		/*
		 * System.out.println(getYYYYMMDDHHMMSSDate(new Date()));
		 * System.out.println(now()); System.out.println(getToday());
		 * System.out.println(getTomorrow());
		 * System.out.println(getYesterday());
		 * System.out.println(getTodayBegin());
		 * System.out.println(getTodayEnd()); System.out.println(getDayBegin(new
		 * Date())); System.out.println(getDayBegin("2011-09-10 00:00:18"));
		 * System.out.println(getDayEnd(new Date()));
		 * System.out.println(getDayEnd("2011-09-10 00:00:18"));
		 * 
		 * System.out.println(compareTimeByNow(getDate("2011-09-08 00:00:18"),
		 * getDate("2011-09-09 00:00:18")));
		 * System.out.println(compareTimeByNow("2011-09-08 00:00:18",
		 * "2011-09-09 00:00:18"));
		 * 
		 * System.out.println(isBetweenTime(getDate("2011-09-09 00:00:18"),
		 * getDate("2011-09-10 00:00:18")));
		 * System.out.println(isBetweenTime("2011-09-09 00:00:18",
		 * "2011-09-10 00:00:18"));
		 * 
		 * System.out.println(addDaysForStr(2));
		 */
		// System.out.println("" +getDay(new Date()));
		/*
		 * int days = getDay(new Date()); for (int i = 1 ; i <= days; i++){
		 * System.out.println(DateUtil.formatDate(DateUtil.addDays(-1*days + i),
		 * PATTEM2_DATE)); }
		 */

	}
}
