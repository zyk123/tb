package com.flash.toolbar.common.util;

import org.springframework.stereotype.Component;

/**
 * 读取配置文件的工具类
 * @author ocean
 *
 */
@Component("paramPropertiesUtil")
public final class ParamPropertiesUtil {
    
    /**
     * 截取手机号段开始位置
     */
    private static String segLenthB;
    
    /**
     * 截取手机号段结束位置
     */
    private static String segLenthE;
    
    /**
     * 查询接口时候截取手机号段开始位置
     */
    private static String iSegLenthB;
    
    /**
     * 查询接口时候截取手机号段结束位置
     */
    private static String iSegLenthE;
    
	/**
     * socket链接超时时间
     */
    private static String socketTimeOut;
    
    /**
     * connect链接超时时间
     */
    private static String connectTimeOut;
    
    /**
     * redis缓存过期时间
     */
    private static String cacheTimeOut;
    
    /**
     * redis活动排名列表过期时间
     */
    private static String cacheScoreListTimeOut;
    
    /**
     * celcomHost
     */
    private static String celcomHost;
    
    /**
     * 充值回调地址
     */
    private static String reloadResponseUrl;
    
    /**
     * 充值密码
     */
    private static String reloadPassword;
    
    private static String reloadStoreid;
    
    private static String reloadPassword2;
    
    private static String reloadStoreid2;
    
	private static String reloadRequestUrl;
    
    private static String reloadPostResponseUrl;
    
    public static String getReloadStoreid() {
		return reloadStoreid;
	}

	public static void setReloadStoreid(String reloadStoreid) {
		ParamPropertiesUtil.reloadStoreid = reloadStoreid;
	}

	public static String getReloadPassword2() {
		return reloadPassword2;
	}

	public static void setReloadPassword2(String reloadPassword2) {
		ParamPropertiesUtil.reloadPassword2 = reloadPassword2;
	}

	public static String getReloadStoreid2() {
		return reloadStoreid2;
	}

	public static void setReloadStoreid2(String reloadStoreid2) {
		ParamPropertiesUtil.reloadStoreid2 = reloadStoreid2;
	}
    
	public static String getReloadPostResponseUrl() {
		return reloadPostResponseUrl;
	}

	public static void setReloadPostResponseUrl(String reloadPostResponseUrl) {
		ParamPropertiesUtil.reloadPostResponseUrl = reloadPostResponseUrl;
	}

	public static String getReloadRequestUrl() {
		return reloadRequestUrl;
	}

	public static void setReloadRequestUrl(String reloadRequestUrl) {
		ParamPropertiesUtil.reloadRequestUrl = reloadRequestUrl;
	}

	public static String getReloadResponseUrl() {
		return reloadResponseUrl;
	}

	public static void setReloadResponseUrl(String reloadResponseUrl) {
		ParamPropertiesUtil.reloadResponseUrl = reloadResponseUrl;
	}

	public static String getReloadPassword() {
		return reloadPassword;
	}

	public static void setReloadPassword(String reloadPassword) {
		ParamPropertiesUtil.reloadPassword = reloadPassword;
	}

	public static String getCelcomHost() {
		return celcomHost;
	}

	public static void setCelcomHost(String celcomHost) {
		ParamPropertiesUtil.celcomHost = celcomHost;
	}

	public static String getCacheScoreListTimeOut() {
		return cacheScoreListTimeOut;
	}

	public static void setCacheScoreListTimeOut(String cacheScoreListTimeOut) {
		ParamPropertiesUtil.cacheScoreListTimeOut = cacheScoreListTimeOut;
	}

	public static String getSocketTimeOut() {
		return socketTimeOut;
	}

	public static void setSocketTimeOut(String socketTimeOut) {
		ParamPropertiesUtil.socketTimeOut = socketTimeOut;
	}

	public static String getConnectTimeOut() {
		return connectTimeOut;
	}

	public static void setConnectTimeOut(String connectTimeOut) {
		ParamPropertiesUtil.connectTimeOut = connectTimeOut;
	}

	public static String getSegLenthB() {
		return segLenthB;
	}

	public static void setSegLenthB(String segLenthB) {
		ParamPropertiesUtil.segLenthB = segLenthB;
	}

	public static String getSegLenthE() {
		return segLenthE;
	}

	public static void setSegLenthE(String segLenthE) {
		ParamPropertiesUtil.segLenthE = segLenthE;
	}
	
	public static String getiSegLenthB() {
		return iSegLenthB;
	}

	public static void setiSegLenthB(String iSegLenthB) {
		ParamPropertiesUtil.iSegLenthB = iSegLenthB;
	}

	public static String getiSegLenthE() {
		return iSegLenthE;
	}

	public static void setiSegLenthE(String iSegLenthE) {
		ParamPropertiesUtil.iSegLenthE = iSegLenthE;
	}

	public static String getCacheTimeOut() {
		return cacheTimeOut;
	}

	public static void setCacheTimeOut(String cacheTimeOut) {
		ParamPropertiesUtil.cacheTimeOut = cacheTimeOut;
	}
    
	
}
