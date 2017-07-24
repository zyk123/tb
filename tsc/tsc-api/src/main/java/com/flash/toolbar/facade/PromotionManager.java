package com.flash.toolbar.facade;

public interface PromotionManager {
	/**
	 * 获取活动列表
	 * @return
	 */
	public String getPromotionList(String bussinessStr);
	
	/**
	 * 获取活动奖品信息
	 * @param bussinessStr
	 * @return
	 */
	public String getPromotionPrize(String bussinessStr);
	
	/**
	 * 用户抽奖
	 * @param bussinessStr
	 * @return
	 */
	public String winPrize(String bussinessStr);
	
	/**
	 * 用户手机游戏抽奖
	 * @param bussinessStr
	 * @return
	 */
	public String phoneGameLuckDraw(String bussinessStr);
	
	/**
	 * 获取中奖记录
	 * @param bussinessStr
	 * @return
	 */
	public String getLuckyList(String bussinessStr);
	
	/**
	 * 记录手速活动得分
	 * @param bussinessStr
	 * @return
	 */
	public String saveSpeedRecord(String bussinessStr);
	
	/**
	 * 获取手速活动得分
	 * @param bussinessStr
	 * @return
	 */
	public String getSpeedList(String bussinessStr);
	
	/**
	 * 我的中奖记录
	 * @param bussinessStr
	 * @return
	 */
	public String getMyLuckyList(String bussinessStr);
	
	/**
	 * 判断抓手机游戏得分是否在档位中
	 * @param bussinessStr
	 * @return
	 */
	public String judgeLimits(String bussinessStr);
	
	
	
	
	/**
	 * 保存收件人信息
	 * @param bussinessStr
	 * @return
	 */
	public String saveReceiverInfo(String bussinessStr);
	
	/**
	 * 获取收件人信息
	 * @param bussinessStr
	 * @return
	 */
	public String getReceiverInfo(String bussinessStr);

	/**
	 * 查询每日剩余次数
	 * @param bussinessStr
	 * @return
	 */
	public String getRestOnedaytimes(String bussinessStr);

	/**
	 * 更新人还是机器人
	 * @param bussinessStr
	 * @return
	 */
	public String updateManOrRobotInfo(String bussinessStr);
}
