package com.flash.common.util;

import java.util.Date;

public class RandomString {

    private static long currNo;
    
    /**
     * size 代表几位数字
     * @param size
     * @return
     */
    public static String getFormatString(int size)
    {
	if(currNo == 0)
	{
	    currNo = new Date().getTime();
	}
	int degree = 1;
	for(int i=0;i<size;i++)
	{
	    degree*=10;
	}
	long rv = currNo%degree;
	int rs = ((Long)rv).toString().length();
	StringBuffer sb = new StringBuffer();
	for(int j=0;j<size-rs;j++)
	{
	    sb.append("0");
	}
	sb.append(((Long)rv).toString());
	if(currNo+1==Long.MAX_VALUE)
	{
	    currNo=Long.MIN_VALUE;
	}else{
	    currNo ++;
	}
	return sb.toString();
    }
    
    /**
     * 记录日志唯一标志随机数
     * @return String
     */
    public static String getRandomNumber(){
		String date = DateTimeUtil.getDateTime(new Date(), "yyyyMMddHHmmss");
		String randow = String.valueOf((int)(Math.random()*900)+100);
		return date+randow;
    }
}
