package com.flash.toolbar.utils;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


/**
 * 
 * @ClassName: DateUtil
 * @Description: TODO(时间操作工具类)
 * @author harry harry.wang@oooo3d.com
 * @date 2013-3-22 下午06:03:28
 * 
 */
public class DateUtil {
	public static final String FORMAT1 = "yyyy-MM-dd";
	public static final String FORMAT2 = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMAT3 = "yyyy-MM";
	public static final String FORMAT4 = "HH:mm";
	public static final String FORMAT5 = "yyyyMMdd";

	/**
	 * 
	 * @Title: getFormatDate
	 * @Description: TODO(格式化时间)
	 * @param @param date
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String getFormatDate(Date date) {
		DateFormat mat = new SimpleDateFormat(FORMAT2);
		return mat.format(date);
	}

	public static String getFormatDate(Date date, String format) {
		DateFormat mat = new SimpleDateFormat(format);
		return mat.format(date);
	}

	/**
	 * 
	 * @Title: getWeekOfDate
	 * @Description: TODO(根据日期字符串，返回星期)
	 * @param @param date
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String getWeekOfDate(String date) {
		String[] weekDaysName = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五",
				"星期六" };
		// String[] weekDaysCode = { "0", "1", "2", "3", "4", "5", "6" };
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateUtil.getDateFromStr(date, FORMAT1));
		int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		return weekDaysName[intWeek];
	}

	/**
	 * 
	 * @Title: getDateFromStr
	 * @Description: TODO(根据日期字符串获取日期对象)
	 * @param @param str
	 * @param @param format
	 * @param @return 设定文件
	 * @return Date 返回类型
	 * @throws
	 */
	public static Date getDateFromStr(String str, String format) {
		Date rtn = null;
		DateFormat df = new SimpleDateFormat(format);
		try {
			rtn = df.parse(str);
		} catch (ParseException e) {
			rtn = new Date();
		}
		return rtn;
	}

	/**
	 * 
	 * @Title: findMaxDayInMonth
	 * @Description: TODO(获得某年某月份的最大天数)
	 * @param @param date
	 * @param @return 设定文件
	 * @return int 返回类型
	 * @throws
	 */
	public static int findMaxDayInMonth(String date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateUtil.getDateFromStr(date, FORMAT3));
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 
	 * @Title: formatStrDate
	 * @Description: TODO(格式化字符串日期)
	 * @param @param date
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String formatStrDate(String date) {
		String retrunDate = "";
		DateFormat mat = new SimpleDateFormat(FORMAT1);
		DateFormat mat2 = new SimpleDateFormat(FORMAT5);
		try {
			retrunDate = mat2.format(mat.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return retrunDate;
	}

	/**
	 * 
	 * @Title: getDateStrByIndex
	 * @Description: TODO(根据月份和下表整数返回日期)
	 * @param @param date
	 * @param @param index
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String getDateStrByIndex(String date, int index) {
		return date + ((index > 9) ? "-" + index : "-0" + index);
	}

	/**
	 * 
	 * @Title: getAllDateByDateInterval
	 * @Description: TODO(根据时间区间返回区间内所有的日期和日期对应的周几)
	 * @param @param beginTime
	 * @param @param endTime
	 * @param @return 设定文件
	 * @return List<String> 返回类型 String格式 '07-10/星期五'
	 * @throws
	 */
	public static List<String> getAllDateByDateInterval(String beginTime,
			String endTime) {
		beginTime = DateUtil.formatStrDate(beginTime);
		endTime = DateUtil.formatStrDate(endTime);
		List<String> dateList = new ArrayList<String>();
		dateList.add(beginTime);
		if (beginTime.equals(endTime))// 起始截止时间允许选择同一天
			return dateList;

		String nextDay = "";
		int count = 1;
		do {
			nextDay = DateUtil.getNDayAfterOneDate(beginTime, count++);
			dateList.add(nextDay);
		} while (!endTime.equals(nextDay));
		
		return dateList;
	}

	public static boolean isValidTime(String time) {
		if (time.length() != 5)
			return false;
		DateFormat df = new SimpleDateFormat(FORMAT4);
		try {
			df.parse(time);
			return true;
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * @Title: getNDayAfterOneDate
	 * @Description: TODO(返回一个日期后面的n天的日期)
	 * @param @param sDate
	 * @param @param n
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String getNDayAfterOneDate(String date, int n) {
		SimpleDateFormat df = new SimpleDateFormat(FORMAT1);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateUtil.getDateFromStr(date, FORMAT1));
		calendar.add(Calendar.DAY_OF_MONTH, n);
		return df.format(calendar.getTime());
	}
	
	public static float getTimeBetween(String beginTime , String endTime){
		DateFormat df = new SimpleDateFormat(FORMAT4);
		try
		{
		    Date d1 = df.parse(beginTime);
		    Date d2 = df.parse(endTime);
		    float diff = d2.getTime() - d1.getTime();
		    float hourLag = diff / (1000 * 60 * 60);
		    return (float)(Math.round(hourLag*10))/10; 
		}
		catch (Exception e)
		{
			return 0;
		}


	}
	
	public static int daysBetween(String beginDate, String endDate) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			cal.setTime(sdf.parse(beginDate));
			long time1 = cal.getTimeInMillis();
			cal.setTime(sdf.parse(endDate));
			long time2 = cal.getTimeInMillis();
			long between_days = (time2 - time1) / (1000 * 3600 * 24);
			return Integer.parseInt(String.valueOf(between_days));
		} catch (Exception e) {
			return 0;
		}
	}
	
	public static Date getTimeZoneDate(Date date,String timeZone,String format){
		return getDateFromStr(getTimeZoneStr(date,timeZone,format),format);
	}
	
	public static String getTimeZoneStr(Date date,String timeZone,String format){
		String timeStr = "";
		try {  
		    SimpleDateFormat dateFormat = new SimpleDateFormat(format);  
		    dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"+timeZone));
		    timeStr = dateFormat.format(date);  
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    } 
	    return timeStr;
	}
	
	public static String getLastDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DATE, -1);
		return getFormatDate(calendar.getTime(), FORMAT1);
	}
	
	/**
	 * 把印尼时间转化为UTC时间，保证商务报表统计数据最大限度和运营商保持一致
	 * @param dateStr	格式为 yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String convertDate(String dateStr) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));
		Date date;
		try {
			date = dateFormat.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		//System.out.println(dateFormat.format(date));
		return dateFormat.format(date);
	}

	// test
	public static void main(String[] args) {
		// System.out.println(DateUtil.getDateStrByIndex("2012-12", 10));
		// System.out.println(DateUtil.findMaxDayInMonth("2012-08"));
		System.out.println(DateUtil.getWeekOfDate("2012-07-17"));
		// System.out.println(DateUtil.formatStrDate("2012-7-17"));
		System.out.println(DateUtil.getNDayAfterOneDate("2012-2-29", 1));
		System.out.println("2012-07-17".substring(5, 10));
		System.out.println(DateUtil.getTimeBetween("08:38", "18:00")+"");
//		Date date = getDateFromStr("2016-8-26 14:58:21", FORMAT2);
//		System.out.println(getTimeZoneStr(date, "-6", FORMAT2));

	}

}
