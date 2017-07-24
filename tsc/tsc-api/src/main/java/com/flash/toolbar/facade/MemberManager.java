package com.flash.toolbar.facade;

public interface MemberManager {
	/**
	 * 设置用户界面开关
	 * @param params json字符串
	 * @return
	 */
	String setToolbarAvaliable(String params);
	
	/**
	 * 获取默认设置
	 * @param params
	 * @return
	 */
	String getDefaultSetting(String params);
	
	/**
	 * 查询用户流量是否已提醒
	 * @param params json字符串
	 * @return boolean
	 */
	Boolean queryFlowAlert(String params);
	
	/**
	 * 保存用户流量提醒
	 * @param params json字符串
	 * @return
	 */
	int saveFlowAlert(String params);
	
	/**
	 * 查询套餐详情
	 * @param params json字符串
	 * @return
	 */
	String queryFlowPackage(String params);
	
	/**
	 * 查询套餐类型
	 * @param params
	 * @return
	 */
	String queryPackageType(String params);
	
	/**
	 * 查询用户阀值设置
	 * @param params json字符串
	 * @return json字符串
	 */
	String queryAlertThreshold(String params);
	
	/**
	 * 运营商接口
	 * 查询用户所有套餐使用情况
	 * @param params json字符串
	 * @return json字符串
	 */
	String queryQuotaUsageFup(String params);
	
	/**
	 * 运营商接口
	 * 查询用户基本信息（包含预付费和后付费用户）
	 * @param params
	 * @return
	 */
	String queryProfileRetrieve(String params);
	
	/**
	 * 运营商接口
	 * 查询用户总流量使用情况
	 * @param params
	 * @return
	 */
	String queryActquotaUsageFup(String params);
	
	/**
	 * 运营商接口
	 * 查询预付费用户话费余额
	 * @param params
	 * @return
	 */
	String queryAccountBalance(String params);
	
	/**
	 * 运营商接口
	 * 查询用户基本信息（包含预付费和后付费用户）
	 * @param params
	 * @return
	 */
	String queryCustomerRetrieve(String params);
	
	/**
	 * 设置用户反馈信息
	 * @param params
	 * @return
	 */
	String sendFeedback(String params);
	
	/**
	 * 查询游戏列表
	 * @param params
	 * @return
	 */
	String queryGameList(String params);
	
	/**
	 * 读取游戏图标
	 * @param params
	 * @return
	 */
	String readGameIcon(String params);
	
	/**
	 * 查询网站列表
	 * @param params
	 * @return
	 */
	String queryWebsiteList(String params);
	
	/**
	 * 读取网站图标
	 * @param params
	 * @return
	 */
	String readWebsiteIcon(String params);		
	
}
