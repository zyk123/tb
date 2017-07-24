package com.flash.toolbar.facade;

/**
 * 充值接口
 * @author ocean
 *
 */
public interface ReloadManager {
     
	/**
	 * 保存订单
	 * @param businessStr
	 * @return
	 */
	public String saveOrder(String businessStr);
	
	
	public String changeOrderStatus(String busyStr);
	
	public String saveReloadResult(String busyStr);
}
