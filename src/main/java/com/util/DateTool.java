package com.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTool {

	/*
	 * 判断两个日期是否是同一天
	 */
	public static boolean isSameDate(Date day1, Date day2) {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    String ds1 = sdf.format(day1);
	    String ds2 = sdf.format(day2);
	    if (ds1.equals(ds2)) {
	        return true;//是同一天
	    } else {
	        return false;//不是同一天
	    }
	}
	/*
	 * 日期间隔天数
	 * day1-day2
	 */
	public static long betweenDate(Date day1, Date day2){        
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    if(day1==null||day2==null){
	    	return 1;
	    }
	    try {	    
	    	String ds1 = sdf.format(day1);
	    	String ds2 = sdf.format(day2);
			day1=sdf.parse(ds1);
			day2=sdf.parse(ds2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			
		}
	    long betweenDate = (day2.getTime()-day1.getTime())/(24*60*60*1000);
	    if(betweenDate<0){
	    	return betweenDate;
	    }
	    return betweenDate+1;
	}
	/*
	 * 日期增加
	 */
	public static Date addDate(Date date1,int day){
		Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        cal1.add(Calendar.DATE, day);
        Date date=cal1.getTime();
        return date;
	}
	/*
	 * 判断日期是否在里面
	 */
	public static boolean isInDate(Date day1, Date day2,Date day3) {
		if(day1.getTime()<=day3.getTime()&&day3.getTime()<=day2.getTime()){
			 return true;//在日期里面
		}else{
			return false;//在日期外
		}
	}
	/*
	 * 判断日期是否在里面
	 */
	public static Date getDate(Date day1) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    String ds1 = sdf.format(day1);
	    Date date = new Date();
	    try {
	    	date=sdf.parse(ds1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			
		}
	    return date;
	}
	/**
	 * 
	 * 方法名称    ：取得传入yyyy-mm-dd日期和前、后标志向后几天、向前几天，返回该天日期
	 * 功能描述    ：
	 * 逻辑描述    :
	 * @param   :sdate 时间
	 * @param   :dnum  向前向后天数
	 * @return  :String
	 * @throws  :无
	 */
	public static Date getOneDay(Date sdate, int dnum) {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String sNewDate = df.format(sdate);
			Date newdate = df.parse(sNewDate);
			java.util.Calendar cal = new java.util.GregorianCalendar();
			cal.setTime(newdate);
			if (dnum > 0)
				cal.add(Calendar.DATE, dnum);
			else
				cal.add(Calendar.DATE, dnum);
			Date dt = cal.getTime();
			return dt;
			//return df.format(dt);
		} catch (Exception e) {
			return new Date();
		}
	}
	/**
	 * 字符串转时间 
	 * @param date 2008-07-10 19:20:00 要把它转成日期
	 * @return
	 */
	public static Date strToDate(String date){
		SimpleDateFormat df =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date newdate = null;
		try {
			newdate = df.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newdate;
	}
	/*
	 * 时间戳转日期格式
	 */
    public static Date stampToDate(long newsDate ) {
        long time = Long.valueOf(newsDate);
        Calendar c = Calendar. getInstance();
        c.setTimeInMillis(time);
        return c.getTime();
    }
    
	/**
	 * @param 测试接口
	 */
	public static void main(String[] args) {
		System.out.println(strToDate("2013-06-08 0:0:10"));
	}
	
	/**
	 * long类型转换成字符串时间
	 * 
	 */
	public static String longToString(long newsDate,String pattern){
		
		String retStr = "";
		Date date = stampToDate(newsDate);
		if (date != null){
			retStr = new SimpleDateFormat(pattern).format(date);
		}
		return retStr;
	}

}
