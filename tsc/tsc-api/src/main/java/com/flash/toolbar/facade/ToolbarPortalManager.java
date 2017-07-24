package com.flash.toolbar.facade;

/**
 * toolbar入口界面管理
 * @author ocean
 *
 */
public interface ToolbarPortalManager {
     
	/**
	 * 判断toolbar是否可见，并且判断用户是否要初始化，做初始化操作
	 * @param businessStr
	 * @return
	 */
	public String isToolbarAvaliable(String businessStr);
	
	/**
	 * 从数据库中捞取用户信息放到session中
	 * @param businessStr
	 * @return
	 */
	public String queryToolbarSession(String businessStr);
	
	/**
	 * 将会员表中首次访问字段置为1
	 * @param businessStr
	 * @return
	 */
	public String welHasShown(String businessStr);
	
	
	public String getAllIcon(String businessStr);

	public String activityHasShown(String businessStr);

	public String isActivityPopup(String businessStr);
}
