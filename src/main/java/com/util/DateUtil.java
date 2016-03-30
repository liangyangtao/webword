package com.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

/**
 * 处理时间工具类
 * @author zyh add at 2014/12/15
 *
 */
public class DateUtil {
	private static Log log = LogFactory.getLog(DateUtil.class);
	private static final String DATE_PATTERN = "yyyy-MM-dd";
	private static final String TIME_PATTERN = "HH:mm:ss";
	private static final String TIMESTAMP_PATTERN = DATE_PATTERN + " "
			+ TIME_PATTERN;

	private DateUtil() {

	}
	
	/**
	 * 获取当前日期
	 * 
	 * 
	 * @return Date类型，“yyyy-MM-dd”格式的日期
	 */
	public static Date currentDate() {
		SimpleDateFormat df = new SimpleDateFormat(DATE_PATTERN);
		return convertStringToDate(df.format(new Date()));
	}

	/**
	 * 获取当前日期时间
	 * 
	 * 
	 * @return Date类型，“yyyy-MM-dd HH:mm:ss”格式的日期时间
	 */
	public static Date currentDateTime() {
		SimpleDateFormat df = new SimpleDateFormat(TIMESTAMP_PATTERN);
		return convertStringToDateTime(df.format(new Date()));
	}

	/**
	 * 日期类型参数转换为相应格式的字符串类型参数
	 * 
	 * @param aDate
	 *            Date类型日期
	 * @param pattern
	 *            转化之后的日期格式
	 * @return 对应格式的字符串日期
	 */
	public static String convertDateToString(Date aDate, String pattern) {
		if (aDate == null) {
			if (log.isErrorEnabled()) {
				log.error("日期参数为空！");
				throw new IllegalArgumentException("日期参数为空！");
			}
		}
		if (!StringUtils.hasText(pattern)) {
			if (log.isErrorEnabled()) {
				log.error("格式参数为空！");
				throw new IllegalArgumentException("格式参数为空！");
			}
		}
		return new SimpleDateFormat(pattern).format(aDate);
	}

	/**
	 * 字符串类型参数转换为相应格式的日期类型参数
	 * 
	 * @param aDate
	 *            Date类型日期
	 * @param pattern
	 *            转化之后的日期格式
	 * @return 对应格式的字符串日期
	 */
	public static Date convertStringToDate(String strDate, String pattern) {
		if (!StringUtils.hasText(strDate)) {
			if (log.isErrorEnabled()) {
				log.error("日期参数为空！");
				throw new IllegalArgumentException("日期参数为空！");
			}
		}
		if (!StringUtils.hasText(pattern)) {
			if (log.isErrorEnabled()) {
				log.error("格式参数为空！");
				throw new IllegalArgumentException("格式参数为空！");
			}
		}
		SimpleDateFormat df;
		Date date = null;
		df = new SimpleDateFormat(pattern);
		try {
			date = df.parse(strDate);
		} catch (ParseException e) {
			if (log.isErrorEnabled()) {
				log.error("剖析参数异常！" + pattern, e);
			}
		}
		return date;
	}

	/**
	 * 按照 yyyy-MM-dd HH:mm:ss 格式把字符串类型参数转化为日期类型参数
	 * 
	 * @param strDate
	 *            字符串类型
	 * 
	 * @return 对应格式的Date类型日期
	 */
	public static Date convertStringToDateTime(String strDate) {
		return convertStringToDate(strDate, TIMESTAMP_PATTERN);
	}

	/**
	 * 按照 yyyy-MM-dd 格式把字符串类型参数转化为日期类型参数
	 * 
	 * @param strDate
	 *            字符串类型
	 * 
	 * @return 对应格式的Date类型日期
	 */
	public static Date convertStringToDate(String strDate) {
		return convertStringToDate(strDate, DATE_PATTERN);
	}

	/**
	 * 计算指定日期前 num 年的日期
	 * 
	 * @param date
	 * @param num
	 * @param pattern
	 * @return
	 */
	public static String theDateStrBeforeSomeYears(Date date, int num,
			String pattern) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		int newYear = year - num;
		calendar.set(Calendar.YEAR, newYear);
		Date resultDate = calendar.getTime();
		return convertDateToString(resultDate, pattern);
	}

	/**
	 * 计算两个日期之间相差的月数
	 * 
	 * @param strDate1
	 * @param strDate2
	 * @return
	 */
	public static int dispersionDateForMonth(String strDate1, String strDate2) {
		int iMonth = 0;
		int flag = 0;
		try {
			Calendar objCalendarDate1 = Calendar.getInstance();
			objCalendarDate1.setTime(DateFormat.getDateInstance().parse(
					strDate1));

			Calendar objCalendarDate2 = Calendar.getInstance();
			objCalendarDate2.setTime(DateFormat.getDateInstance().parse(
					strDate2));

			if (objCalendarDate2.equals(objCalendarDate1))
				return 0;
			if (objCalendarDate1.after(objCalendarDate2)) {
				Calendar temp = objCalendarDate1;
				objCalendarDate1 = objCalendarDate2;
				objCalendarDate2 = temp;
			}
			if (objCalendarDate2.get(Calendar.DAY_OF_MONTH) < objCalendarDate1
					.get(Calendar.DAY_OF_MONTH))
				flag = 1;

			if (objCalendarDate2.get(Calendar.YEAR) > objCalendarDate1
					.get(Calendar.YEAR))
				iMonth = ((objCalendarDate2.get(Calendar.YEAR) - objCalendarDate1
						.get(Calendar.YEAR))
						* 12
						+ objCalendarDate2.get(Calendar.MONTH) - flag)
						- objCalendarDate1.get(Calendar.MONTH);
			else
				iMonth = objCalendarDate2.get(Calendar.MONTH)
						- objCalendarDate1.get(Calendar.MONTH) - flag;

		} catch (Exception e) {
			e.printStackTrace();
			log.error("计算两个日期相差月份异常！");
		}
		return iMonth;
	}
	
	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param smdate
	 *            较小的时间
	 * @param bdate
	 *            较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate, Date bdate)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
		smdate = sdf.parse(sdf.format(smdate));
		bdate = sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 计算两个日期之间相差的年数
	 * 
	 * @param curdate
	 *            当前的时间
	 * @param olddate
	 *            比较的时间
	 * @return 相差年数
	 * @throws ParseException
	 */
	public static int yearsBetween(Date curdate, Date olddate)
			throws ParseException {
		Calendar cal1 = Calendar.getInstance();
		//Calendar类是一个抽象基类，需要通过Calendar.getInstance方法返回一个Calendar的实例对象
		cal1.setTime(curdate);//将当前时间放入Calendar
		int time1 =cal1.get(Calendar.YEAR);
		//得到当前时间转换成的年数   -Calendar.get用于获得日期对象中的字段值（这里获得的是年份）
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(olddate);
		int time2 = cal2.get(Calendar.YEAR);
		int between_years;
		if(time1>time2){
			between_years= time1 -time2 ;
		}else{
			between_years = time2 - time1;
		}
		return between_years;
	}
	
	/**
	 * 获得指定日期是星期几
	 * @param date 指定日期
	 * @return
	 */
	public static String getDayOfWeek(Date date){

		if (date == null) {
			if (log.isErrorEnabled()) {
				log.error("日期参数为空！");
				throw new IllegalArgumentException("日期参数为空！");
			}
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int num = cal.get(Calendar.DAY_OF_WEEK);
		switch (num) {
		case 1:
			return "星期日";
		case 2:
			return "星期一";
		case 3:
			return "星期二";
		case 4:
			return "星期三";
		case 5:
			return "星期四";
		case 6:
			return "星期五";
		case 7:
			return "星期六";

		default:
			return "星期X";
		}
		
	}
	
	/**
	 * 获得指定日期后num天的日期
	 * @param date 指定日期
	 * @param num 天数
	 * @return 
	 */
	public static Date theDateAfterDays(Date date,int num){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int day = cal.get(Calendar.DAY_OF_YEAR);
		int newDay = day+num;
		cal.set(Calendar.DAY_OF_YEAR, newDay);
		Date result = cal.getTime();
		SimpleDateFormat df = new SimpleDateFormat(DATE_PATTERN);	
		return convertStringToDate(df.format(result));
	}

	/**
	 * 得到指定日期是一年中第几天
	 * 
	 * @return
	 */
	public static Long getDayOfYear(Date date) {
		if (date == null) {
			if (log.isErrorEnabled()) {
				log.error("日期参数为空！");
				throw new IllegalArgumentException("日期参数为空！");
			}
		}
		// %tj表示一年中的第几天
		String strDate = String.format("%tj", date);
		return Long.parseLong(strDate);
	}

	/**
	 * 将给给定日期转换成大写形式 ，如 将 2013/09/26转换成二〇一三年九月二十六日
	 * 
	 * @param date
	 *            如果 date为空
	 * @return
	 */
	public static String getDateZh_CN(Date date) {
		Calendar c = Calendar.getInstance();
		if (date == null) {
			c.setTime(new Date());
		} else {
			c.setTime(date);
		}
		int y = c.get(Calendar.YEAR);
		int m = c.get(Calendar.MONTH) + 1;
		int d = c.get(Calendar.DATE);
		String Y = getYearZh_CN(String.valueOf(y));
		String M = getMonthZh_CN(String.valueOf(m));
		String D = getDayZh_CN(String.valueOf(d));
		return Y + "年" + M + "月" + D + "日";
	}
	
	/**
	 * 将给给定日期转换成大写形式 ，如 将 2013/09/26转换成2013年09月26日
	 * 
	 * @param date
	 *            如果 date为空
	 * @return
	 */
	public static String getDateZh(Date date) {
		Calendar c = Calendar.getInstance();
		if (date == null) {
			c.setTime(new Date());
		} else {
			c.setTime(date);
		}
		int y = c.get(Calendar.YEAR);
		int m = c.get(Calendar.MONTH) + 1;
		int d = c.get(Calendar.DATE);
		return y + "年" + m + "月" + d + "日";
	}
	
	/**
	 * 将给给定日期转换成英文版格式 ，如 将 2013/09/26转换成 September 26,2013
	 * 
	 * @param date
	 *            如果 date为空
	 * @return
	 */
	public static String getEnglishDate(Date date) {
		Calendar c = Calendar.getInstance();
		if (date == null) {
			c.setTime(new Date());
		} else {
			c.setTime(date);
		}
		int y = c.get(Calendar.YEAR);
		int m = c.get(Calendar.MONTH) + 1;
		int d = c.get(Calendar.DATE);
		String M=getEnglishMonth(m);
		return M+" "+d+","+y;
	}
	
	/**
	 * 年的转换（中文）
	 * 
	 * @param year
	 * @return
	 */
	public static String getEnglishMonth(int Month) {

		String month = "";
			switch (Month) {
			case 1:
				month = "January";
				break;
			case 2:
				month = "February";
				break;
			case 3:
				month = "March";
				break;
			case 4:
				month = "April";
				break;
			case 5:
				month = "May";
				break;
			case 6:
				month = "June";
				break;
			case 7:
				month= "July ";
				break;
			case 8:
				month = "August ";
				break;
			case 9:
				month = "September";
				break;
			case 10:
				month = "October";
				break;
			case 11:
				month ="November";
				break;
			case 12:
				month = "December";
				break;
			default:
				break;
			}
		return month;
	}
	/**
	 * 年的转换（中文）
	 * 
	 * @param year
	 * @return
	 */
	public static String getYearZh_CN(String year) {

		String YEAR = "";
		for (int i = 0; i < year.length(); i++) {
			char charMenber = year.charAt(i);

			switch (charMenber) {
			case '0':
				YEAR += '○';
				break;
			case '1':
				YEAR += '一';
				break;
			case '2':
				YEAR += '二';
				break;
			case '3':
				YEAR += '三';
				break;
			case '4':
				YEAR += '四';
				break;
			case '5':
				YEAR += '五';
				break;
			case '6':
				YEAR += '六';
				break;
			case '7':
				YEAR += '七';
				break;
			case '8':
				YEAR += '八';
				break;
			case '9':
				YEAR += '九';
				break;
			default:
				break;
			}

		}
		return YEAR;
	}

	/**
	 * 月的装换
	 * 
	 * @param month
	 *            （中文） 月份数字，如09
	 * @return
	 */
	public static String getMonthZh_CN(String month) {
		String MOUNTH = "";
		if (month.equals("10")) {
			MOUNTH = "十";
		} else if (month.equals("11")) {
			MOUNTH = "十一";
		} else if (month.equals("12")) {
			MOUNTH = "十二";
		} else if (Long.parseLong(month) < 10) {
			MOUNTH = getYearZh_CN(month);
		}
		return MOUNTH;

	}

	/**
	 * 日的转换（中文）
	 * 
	 * @param day
	 *            日期号数字，如19
	 * @return
	 */
	public static String getDayZh_CN(String day) {
		if(day.length()==1){
			day="0"+day;
		}
		String DAY = "";
		if (day.equals("10")) {
			DAY = "十";
		} else if (day.equals("20")) {
			DAY = "二十";
		} else if (day.equals("30")) {
			DAY = "三十";
		} else if (day.charAt(0) == '1' && day.charAt(1) != '0') {
			String daytemp = getYearZh_CN(day);
			DAY = "十" + daytemp.charAt(1);
		} else if ((day.charAt(0) == '2' && day.charAt(1) != '0')
				|| (day.equals("31"))) {
			String daytemp = getYearZh_CN(day);
			DAY = daytemp.charAt(0) + "十" + daytemp.charAt(1);
		} else {
			DAY = getYearZh_CN(day);

		}
		return DAY;

	}
	
	/**
	 * 精确获得两个时间的之间的时间差，单位为分钟
	 * @author majie <br>
	 * 2014年6月20日 上午9:39:03
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static int calculateWorkTimeOfMinutes(String startTime,String endTime){
		
		if(startTime == null || endTime == null)
			return 0;
		if(startTime.length() == 8){
			startTime = "1990-01-01 "+startTime;
		}
		
		if(endTime.length() == 8){
			endTime = "1990-01-01 "+endTime;
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = format.parse(startTime);
			endDate = format.parse(endTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		//时间差,单位为分钟
		int minutes = (int)((endDate.getTime() - startDate.getTime())/(1000*60));
		
		return minutes;
	}

}
