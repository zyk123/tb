package com.flash.toolbar.common.util;

import java.util.Date;
import java.util.UUID;

public class RuleUtil {
	/**
	 * 为导入号码生产导入批次号
	 * @return
	 */
	public static String generateNumebrImportNo(){
		String date = DateUtil.getFormatDate(new Date(), "yyyyMMdd");
		String randow = String.valueOf((int)(Math.random()*900)+100);
		return date+"0"+randow;
	}
	
	/**
	 * 为号段生成导入批次号
	 * @return
	 */
	public static String generateNumSelectionImportNo(){
		String date = DateUtil.getFormatDate(new Date(), "yyyyMMdd");
		String randow = String.valueOf((int)(Math.random()*900)+100);
		return date+"1"+randow;
	}
	
	/**
	 * 为数据库插入生成UUID编号
	 * @return
	 */
	public static String generateUUID(){
	        String s = UUID.randomUUID().toString(); 
	        //去掉“-”符号 
	        return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24); 
	}
	
}
