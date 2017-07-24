/**
 * TimeFormatUtil.java Created on May 21, 2009
 * Copyright 2009@JSHX. 
 * All right reserved. 
 */
package com.flash.common.util;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 时间格式工具类
 *
 * @author lynn
 * @Time 5:14:11 PM
 */
public class DateTimeUtil {

    public static final String dateformat1 = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * 取得日期datestring 格式yyyymmdd
     *
     * @param masktype "yyyy-MM-dd" or "yyyyMMdd"
     * @throws ParseException
     */
    public static Date getDateFromDateString(String datestring, String masktype)
            throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat(masktype);
        return sdf.parse(datestring);
    }

    public static Date parseDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            return new Date();
        }
    }

    public static boolean checkStrDate(String date) {
        boolean validstatus = false;
        String[] dateformats = {"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd",
                "yyyyMMdd", "yyyy/MM/dd"};
        SimpleDateFormat sdf;
        for (String dateformat : dateformats) {
            sdf = new SimpleDateFormat(dateformat);
            try {
                sdf.parse(date);
                validstatus = true;
            } catch (ParseException e) {
            }
        }
        return validstatus;
    }

    public static String convertGmtToLocalTime(String time) {
        TimeZone GMT_TIMEZONE = TimeZone.getTimeZone("GMT");
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formater.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date;
        try {
            date = formater.parse(time);
            Calendar calendar = new GregorianCalendar(GMT_TIMEZONE);
            calendar.setTime(date);
            Date datea = new Date(calendar.getTimeInMillis());
            return datea.toLocaleString();
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 转换成      "yyyy-MM-dd HH:mm:ss" 时间串
     *
     * @param date
     * @return
     */
    public static String convert2String(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = simpleDateFormat.format(date);
        return s;
    }

    public static String convert2String(Timestamp timestamp) {
        Date date = new Date(timestamp.getTime());
        return convert2String(date);
    }

    /**
     * 转换为UTC时间
     *
     * @param date
     * @return
     */
    public static String convert2UTC(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        TimeZone pst = TimeZone.getTimeZone("Etc/GMT+0");
        simpleDateFormat.setTimeZone(pst);

        String s = simpleDateFormat.format(date);
        return s;
    }

    /**
     * 取得系统当前时间
     *
     * @return 系统当前时间
     */
    public static Date getSystemDateTime() {
        return new Date();
    }

    /**
     * 根据年度和期间得到某期第一天或最后一天
     *
     * @param type 'first'or'last'
     * @param year
     * @param qj
     * @return 系统当前时间 如 2010 3 first 返回2010-03-01的date形式
     * @throws ParseException
     */
    public static String getDateTimeInACurtainYearAndMonth(String year,
                                                           String qj, String type) throws ParseException {
        if (new Integer(qj) < 10) {
            qj = "0" + qj;
        }
        if ("first".equals(type)) {
            return year + "-" + qj + "-01";
        }
        if ("last".equals(type)) {
            return lastDayOfDate(year + "-" + qj);
        }
        return null;
    }

    /**
     * 取得系统当前日期组成的字符串 默认格式为：yyyy-MM-dd
     *
     * @return 系统当前日期组成的字符串
     */
    public static String getSystemDateString() {
        return getDateTime(getSystemDateTime(), "yyyy-MM-dd");
    }

    /**
     * 取得系统当前日期＋时间组成的字符串 默认格式为：yyyy-MM-dd HH:mm:ss
     *
     * @return 系统当前日期＋时间组成的字符串
     */
    public static String getSystemDateTimeString() {
        return getDateTime(getSystemDateTime(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 根据给定的时间（为空时默认使用当前时间）和时间格式 返回时间字符串
     *
     * @param aMask 时间格式
     * @param aDate 时间参数
     * @return 格式化后的时间字符串
     * @see java.text.SimpleDateFormat
     */
    public static String getDateTime(Date aDate, String aMask) {
        SimpleDateFormat df = null;
        String returnValue = "";
        if (StringUtil.isNull(aMask)) {
            return "";
        } else if (aDate == null) {
            df = new SimpleDateFormat(aMask);
            returnValue = df.format(getSystemDateTime());
        } else {
            df = new SimpleDateFormat(aMask);
            returnValue = df.format(aDate);
        }
        return (returnValue);
    }

    /**
     * 返回时间的字符串
     *
     * @param dt
     * @return
     */
    public static String getLongStringByDate(Date aDate) {
        if (aDate == null)
            return "";
        return String.valueOf(aDate.getTime());
    }

    /**
     * 根据 时间格式 时间Long字符串 转换时间格式
     *
     * @param aMask
     * @param aDateLong
     * @return
     */
    public static String getDateStrByLongStr(String aDateLong, String aMask) {
        SimpleDateFormat df = null;
        String returnValue = "";
        if (aDateLong != null && !"".equals(aDateLong)) {
            if (aMask == null || "".equals(aMask))
                aMask = "yyyy-MM-dd HH:mm:ss";
            df = new SimpleDateFormat(aMask);
            returnValue = df.format(new Date(Long.parseLong(aDateLong)));
        }
        return returnValue;
    }

    /**
     * 获取上一个月字符串
     *
     * @param aDate 2010-06-01
     * @return 05
     */
    @SuppressWarnings("deprecation")
    public static String getPreMonthStr(Date aDate) {
        return getMonthStr(aDate, -1);
    }

    /**
     * 根据当前时间和参数 获取月份字符
     *
     * @param aDate
     * @param m
     * @return
     */
    public static String getMonthStr(Date aDate, int m) {
        Calendar calendar = initCalendar(aDate);
        int month = calendar.get(Calendar.MONTH);
        month = (month + m % 12 + 13) % 12;
        month = (month == 0) ? 12 : month;
        return (month > 9) ? String.valueOf(month) : "0"
                + String.valueOf(month);
    }

    /**
     * 根据当前时间和参数 获取年份字符
     *
     * @param aDate
     * @param y
     * @return
     */
    public static String getYearStr(Date aDate, int y) {
        Calendar calendar = initCalendar(aDate);
        int year = calendar.get(Calendar.YEAR);
        year = year + y;
        return String.valueOf(year);
    }

    /**
     * 根据当前时间和参数 获取日期字符
     *
     * @param aDate
     * @param d
     * @return
     */
    public static String getDayStr(Date aDate, int d) {
        Calendar calendar = initCalendar(aDate);
        int day = calendar.get(Calendar.DAY_OF_YEAR);
        day = day + d;
        calendar.set(Calendar.DAY_OF_YEAR, day);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        return (day > 9) ? String.valueOf(day) : "0" + String.valueOf(day);
    }

    /**
     * 根据日历的规则，为给定的时间字段添加或减去指定的月数
     *
     * @param 时间字段     （默认为当前时间）
     * @param 为字段添加的月数
     * @return
     */
    public static Date getMonth(Date aDate, int amount) {
        Calendar calendar = initCalendar(aDate);
        calendar.add(Calendar.MONTH, amount);
        return calendar.getTime();
    }
    
	    /**
	     * 根据日期获得星期
	     * @param date
	     * @return
	     */
	public static String getWeekOfDate(String timeMillis) {
	  String[] weekDaysName = { "SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT" };
	  Calendar calendar = Calendar.getInstance();
	  calendar.setTimeInMillis(Long.valueOf(timeMillis));
	  int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
	  return weekDaysName[intWeek];
	}     
	
	public static String getWeekOfDate2(String timeMillis) {
		String[] weekDaysName = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri",	"Sat" };
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(Long.valueOf(timeMillis));
		int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		return weekDaysName[intWeek];
	}

	public static String getMonthOfDate(String timeMillis) {
		String[] weekDaysName = { "Jan", "Feb", "Mar", "Apr", "May", "Jun",	"Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(Long.valueOf(timeMillis));
		int intMonth = calendar.get(Calendar.MONTH);
		return weekDaysName[intMonth];
	}

    /**
     * 根据日历的规则，为给定的时间字段添加或减去指定的年数
     *
     * @param 时间字段     （默认为当前时间）
     * @param 为字段添加的年数
     * @return
     */
    public static Date getYear(Date aDate, int amount) {
        Calendar calendar = initCalendar(aDate);
        calendar.add(Calendar.YEAR, amount);
        return calendar.getTime();
    }

    /**
     * 根据日历的规则，为给定的时间字段添加或减去指定的天数
     *
     * @param 时间字段     （默认为当前时间）
     * @param 为字段添加的天数
     * @return
     */
    public static Date getDay(Date aDate, int amount) {
        Calendar calendar = initCalendar(aDate);
        calendar.add(Calendar.DAY_OF_MONTH, amount);
        return calendar.getTime();
    }

    /**
     * 设置日期
     *
     * @param aDate
     * @param field
     * @param value
     * @return
     */
    public static Date set(Date aDate, int field, int value) {
        Calendar c = initCalendar(aDate);
        c.set(field, value);
        return c.getTime();
    }

    public static Calendar initCalendar(Date aDate) {
        Calendar calendar = null;
        if (aDate == null) {
            calendar = Calendar.getInstance();
        } else {
            calendar = new GregorianCalendar();
            calendar.setTime(aDate);
        }
        return calendar;
    }

    /**
     * 取得2个日期之间的天
     */
    public static List<String> getDayBetweenTwoDate(Date startdate, Date enddate) {
        List<String> list = new ArrayList();

        Calendar cal_start = Calendar.getInstance();
        Calendar cal_end = Calendar.getInstance();
        cal_start.setTime(startdate);
        cal_end.setTime(enddate);
        // cal_end.set(cal_end.DAY_OF_YEAR,
        // cal_end.get(Calendar.DAY_OF_YEAR)+1);
        for (; cal_start.compareTo(cal_end) <= 0; cal_start.set(
                cal_start.DAY_OF_MONTH,
                cal_start.get(Calendar.DAY_OF_MONTH) + 1)) {
            String dd = new SimpleDateFormat("yyyy-MM-dd").format(cal_start
                    .getTime());
            String util[] = dd.split("-");
            // System.out.println(util[0]+"年"+util[1]+"月"+util[2]+"日"+"  ;");
            list.add(util[0] + "-" + util[1] + "-" + util[2]);
        }

        return list;
    }

    /**
     * 取得2个日期之间的月份
     */
    public static List<String> getMonthBetweenTwoDate(Date startdate,
                                                      Date enddate) {
        List<String> list = new ArrayList();

        Calendar cal_start = Calendar.getInstance();
        Calendar cal_end = Calendar.getInstance();
        cal_start.setTime(startdate);
        cal_start.set(cal_start.DATE, 1);

        String ddd = new SimpleDateFormat("yyyy-MM-dd").format(cal_start
                .getTime());
        cal_end.setTime(enddate);
        // cal_end.set(cal_end.MONTH, cal_end.get(Calendar.MONTH)+1);
        for (; cal_start.compareTo(cal_end) <= 0; cal_start.set(
                cal_start.MONTH, cal_start.get(Calendar.MONTH) + 1)) {
            String dd = new SimpleDateFormat("yyyy-MM-dd").format(cal_start
                    .getTime());
            // System.out.println(dd);
            String util[] = dd.split("-");
            list.add(util[0] + "-" + util[1]);
        }

        return list;
    }

    /**
     * 根据当前时间和参数 获取某个的日期，正数表示加，负数表示倒退
     *
     * @param startdate 起始时间yyyy-mm-dd
     * @param y         年
     * @param m         月
     * @param d         日
     * @return yy-mm-dd
     * @throws ParseException
     */
    public static String calDate(String startdatestring, int y, int m, int d)
            throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startdate = sdf.parse(startdatestring);
        Calendar cal_start = Calendar.getInstance();
        cal_start.setTime(startdate);
        if (!StringUtil.isNull(y))
            cal_start.add(cal_start.YEAR, y);
        if (!StringUtil.isNull(m))
            cal_start.add(cal_start.MONTH, m);
        if (!StringUtil.isNull(d))
            cal_start.add(cal_start.DATE, d);
        String dd = new SimpleDateFormat("yyyy-MM-dd").format(cal_start
                .getTime());
        // System.out.println(dd);
        return dd;
    }

    /*
     * 传月2009－09返回本月最后一天
     */
    public static String lastDayOfDate(String startdatestring)
            throws ParseException {
        String[] date = startdatestring.split("-");
        String year = date[0];
        int month = Integer.parseInt(date[1]) + 1;
        String datebase = "";
        if (month < 10) {
            datebase = year + "-0" + month + "-01";
        } else {
            datebase = year + "-" + month + "-01";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startdate = sdf.parse(datebase);
        Calendar cal_start = Calendar.getInstance();
        cal_start.setTime(startdate);
        cal_start.add(cal_start.DATE, -1);
        String dd = new SimpleDateFormat("yyyy-MM-dd").format(cal_start
                .getTime());

        return dd;
    }

    /**
     * 取得2个日期之间的年
     */
    public static List<String> getYearBetweenTwoDate(Date startdate,
                                                     Date enddate) {
        List<String> list = new ArrayList();

        Calendar cal_start = Calendar.getInstance();
        Calendar cal_end = Calendar.getInstance();
        cal_start.setTime(startdate);
        cal_end.setTime(enddate);
        // cal_end.set(cal_end.YEAR, cal_end.get(Calendar.YEAR)+1);
        for (; cal_start.compareTo(cal_end) <= 0; cal_start.set(cal_start.YEAR,
                cal_start.get(Calendar.YEAR) + 1)) {
            String dd = new SimpleDateFormat("yyyy-MM-dd").format(cal_start
                    .getTime());
            String util[] = dd.split("-");
            list.add(util[0]);
        }
        return list;
    }

    /**
     * 取得2个日期之间的年月
     */
    public static List<String> getYearMonthBetweenTwoDate(Date startdate,
                                                          Date enddate) {
        List<String> list = new ArrayList();

        Calendar cal_start = Calendar.getInstance();
        Calendar cal_end = Calendar.getInstance();
        cal_start.setTime(startdate);
        cal_end.setTime(enddate);
        // cal_end.set(cal_end.YEAR, cal_end.get(Calendar.YEAR)+1);
        for (; cal_start.compareTo(cal_end) <= 0; cal_start.set(
                cal_start.MONTH, cal_start.get(Calendar.MONTH) + 1)) {
            String dd = new SimpleDateFormat("yyyyMM").format(cal_start
                    .getTime());

            list.add(dd);
        }
        return list;
    }

    /**
     * @param args
     * @throws ParseException
     * @throws UnsupportedEncodingException
     */
    public static void main(String[] args) throws ParseException,
            UnsupportedEncodingException {
        /*
         * SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
		 * getYearMonthBetweenTwoDate(sdf.parse("201005"), sdf.parse("201006"));
		 */
        // System.out.println(URLEncoder.encode("%E2%88%9A","utf-8"));
        // System.out.println(URLDecoder.decode("%25E2%2588%259A","utf-8"));
        // URLDecoder.decode(s, enc)
        /*
         * Date dt = new SimpleDateFormat("yyyy-MM-dd").parse("2009-3-3"); Date
		 * dt1 = new SimpleDateFormat().parse("2009-3"); String s = new
		 * SimpleDateFormat("yyyy-MM-dd").format(getDay(dt, -6));
		 * System.out.println(s); System.out.println(dt1);
		 */
        // System.out.println(DateTimeUtil.getReturnDate(new Date())+"-28");
        // System.out.println(d);
        // System.out.println(getDayStr(new Date(), 0));
        // getDateList("201005","201009");\
        /*
		 * System.out.println(DateTimeUtil.getDateTime(new Date(), "yyyyMMdd")+
		 * DateTimeUtil.getLongStringByDate(new Date()));
		 */
		/*
		 * Set<String> pzidset=new HashSet<String>(); pzidset.add("aaa");
		 * pzidset.add("bbb"); pzidset.add("ccc");
		 * 
		 * String pzids=""; Iterator<String> it=pzidset.iterator();
		 * for(;it.hasNext();){ pzids=pzids+" '"+it.next()+"' ,"; }
		 * System.out.println("in ("+pzids.substring(0,
		 * pzids.lastIndexOf(","))+")");
		 */
        // System.out.println(getDateTimeInACurtainYearAndMonth("2010", "2",
        // "first"));
        // System.out.println("CWGLS3201A"+DateTimeUtil.getDateTime(new Date(),
        // "yyyyMMddhhmmss"));
        System.out
                .println(getDateTime(new Date(), "dd MM yyyy"));
    }

    /**
     * 取得当前年的前一年的最后一天,或前一月的最后一天,或前一天
     *
     * @param flag y,m,d
     */
    public static String getDateForLastYearOrMonthOrDay(String flag) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        String returnResults = "";

        if ("y".equals(flag)) {
            cal.set(cal.MONTH, 0);
            cal.set(cal.DATE, 1);
            cal.add(cal.DATE, -1);
            returnResults = sdf.format(cal.getTime());
        } else if ("m".equals(flag)) {
            // cal.set(cal.MONTH,-1);
            cal.set(cal.DATE, 1);
            cal.add(cal.DATE, -1);
            returnResults = sdf.format(cal.getTime());
        } else {
            cal.add(cal.DATE, -1);
            returnResults = sdf.format(cal.getTime());
        }
        return returnResults;
    }

    // 计算相差天数
    public static int daysOfTwo(Date fDate, Date oDate) {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(fDate);
        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
        aCalendar.setTime(oDate);
        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
        return day2 - day1;
    }

    public static long getUTC() {
        // 1、取得本地时间：
        Calendar cal = GregorianCalendar.getInstance();
        // 2、取得时间偏移量：
        int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);
        // 3、取得夏令时差：
        int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);
        // 4、从本地时间里扣除这些差量，即可以取得UTC时间：
        cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        return cal.getTime().getTime();
    }


//    public static String toUTC() {
//        // 1、取得本地时间：
//        Calendar cal = GregorianCalendar.getInstance();
//        // 2、取得时间偏移量：
//        int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);
//        // 3、取得夏令时差：
//        int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);
//        // 4、从本地时间里扣除这些差量，即可以取得UTC时间：
//        cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
//        String utctime = DateTimeUtil.getDateTime(cal.getTime(), "yyyyMMdd")
//                + "000000";
//        return utctime;
//    }
}
