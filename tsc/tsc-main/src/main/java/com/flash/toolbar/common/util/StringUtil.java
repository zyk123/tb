package com.flash.toolbar.common.util;

import java.util.UUID;

public class StringUtil {
	public static boolean isNotNullOrEmpty(String... args){
		boolean flag = true;
		for (String s : args){
	         if(s==null||"".equals(s)){
	        	 flag = false;
	         }
	    }
		return flag;
	}
	
	public static String formatUUID(boolean full){
		if(full){
			return UUID.randomUUID().toString();
		}else{
			return UUID.randomUUID().toString().replaceAll("-", "");
		}
	}
	
    /**
     * Object to String
     *
     * @param obj
     * @return
     */
    public static String getStr(Object obj) {

        return obj != null ? String.valueOf(obj) : "";
    }	
}
