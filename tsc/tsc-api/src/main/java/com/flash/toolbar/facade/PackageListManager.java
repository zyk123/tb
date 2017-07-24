package com.flash.toolbar.facade;

/**
 * 套餐shop页相关接口
 * @author ocean
 *
 */
public interface PackageListManager {
     
	/**
	 * 套餐查询接口
	 * @param businessStr 入参
	 * @return
	 */
	public String getPackageList(String businessStr);
	
	/**
	 * 套餐订购
	 * @param businessStr
	 * @return
	 */
	public String tcFlowSub(String businessStr);
	
	/**
	 * 套餐详情
	 * @param businessStr
	 * @return
	 */
	public String getPackageDetail(String businessStr);
}
