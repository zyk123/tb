package com.flash.toolbar.common.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 系统常量定义
 * 
 * @Time 9:44:21 AM
 * @author lynn
 */
public final class Constant {
	
	/**
	 * 状态常量
	 * 0：不可用  1：可用  2：锁定
	 */
	public static final String STATUS_VALID = "1";
	public static final String STATUS_INVALID = "0";
	public static final String STATUS_LOCKING = "2";
	
	
	// 成功
	public static final String FAILURE = "0";
	public static final String SUCCEED = "1";
	public static final String NO_LOGIN = "2";
	
	// 接口错误码
	public static final String RETURN_SUCCESS = "0";		//返回成功
	public static final String RETURN_FAILURE = "-1";		//返回失败
	public static final String PARAMS_NOTNULL = "1001";		//必填字段为空
	public static final String PARAMS_JSON_EMPTY = "1002";	//json请求为空
	public static final String PARAMS_JSON_ILLEGAL = "1003";//json格式不合法
	public static final String PARAMS_ILLEGAL = "1010";		//输入参数不合法
	public static final String RETURN_EMPTY = "1011";		//返回数据不合法
	
	public static final String SYSERROR_DB_CONNECT = "2001";		//系统异常（数据库连接）
	public static final String SYSERROR_DB_EXECUTE = "2002";		//系统异常（数据库执行）
	public static final String SYSERROR_ELSE = "2003";				//其它异常
	public static final String SYSERROR_IP_AUTH_FAILURE = "2101";	//IP鉴权不通过
	
	// 接口发送响应标示
	public static final String ACTION_IN = "request";
	public static final String ACTION_OUT = "response";
		
	/*
	 * 获取IP地址
	 */
	public static final String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        } else {
			String[] ips = ip.split(",");
			if (ips.length > 1) {
				for(int i=0;i<ips.length;i++){
					if(!ips[i].equalsIgnoreCase("unknown")) {
						ip = ips[i].trim();
						break;
					}
				}
			}
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
	
	
}