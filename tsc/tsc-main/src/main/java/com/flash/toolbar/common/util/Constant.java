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
	
	//接口预付费
	public static final String PRE_POS_INDICATOR="Prepaid";
	public static final String POST_POS_INDICATOR="Postpaid";
	
	// 接口发送响应标示
	public static final String ACTION_IN = "request";
	public static final String ACTION_OUT = "response";
	
	//网站一二级区域
	public static final String WEBSITE_TYPE_PRIMARY="1";
	public static final String WEBSITE_TYPE_SECONDRY="2";		
	
	/**
	 * 提醒开启状态
	 */
	public static final String REMIND_VAILD = "1";
	
	/**
	 * 提醒类型
	 */
	public static final String REMIND_TYPE_FLOW="2";
	public static final String REMIND_TYPE_ACTIVITY="3";
	
	//缓存key
	public static final String CACHE_SYS_TELECOMOPERATOR = "sys_TelecomOperator";
	public static final String CACHE_SYS_OPERATORSEG = "sys_OperatorSeg";
	public static final String CACHE_HY_WHITELIST = "hy_WhiteList_";
	public static final String CACHE_HY_BLACKLIST = "hy_BlackList_";
	public static final String CACHE_HY_BLACKLISTDEVICE = "hy_BlackListDevice_";
	public static final String CACHE_HY_WHITESECTION = "hy_WhiteSection_";
	public static final String CACHE_HY_BLACKSECTION = "hy_BlackSection_";
	public static final String CACHE_TELECOMOPERATOR_MEMBER = "sys_TM_";				//用户基础信息 SysTelecomOperatorAndHyMember
	
	public static final String CACHE_PROMOTION_LIST = "hd_PromotionInfo";				//活动信息集合 存储ZSet
	public static final String CACHE_PROMOTION_INFO = "hd_PromotionInfo_";				//单个活动具体信息 _promotionId 存储String
	public static final String CACHE_PROMOTION_PRIZE = "hd_PromotionPrize_";			//活动奖品信息集合 _promotionId 存储ZSet
	public static final String CACHE_PROMOTION_RECEIVERINFO = "hd_ReceiverInfo_";		//收件人信息 _memberId 存储String
	public static final String CACHE_PROMOTION_PRIZESHIP = "hd_PrizeShip_";				//单个活动中奖记录 _promotionId 存储ZSet
	public static final String CACHE_PROMOTION_SCORE = "hd_PromotionSort_";				//用于分数排名 _promotionId 存储ZSet 手机号 分数
	public static final String CACHE_PROMOTION_SCORELIST = "hd_PromotionSortList_";		//用于排名集合 _promotionId 存储ZSet
	
	public static final String RELOAD_PAYING = "0";		//充值中
	public static final String RELOAD_PAY_SUCCESS = "1";		//充值成功
	public static final String RELOAD_PAY_FAILED = "2";		//充值失败
	
	
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