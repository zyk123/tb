package com.flash.toolbar.service;

import com.alibaba.fastjson.JSONObject;

public interface PackageListService {

	/**
	 * 套餐查询接口
	 * @param businessStr 入参
	 * @return
	 * @throws Exception 
	 */
	public String getPackageList(String businessStr) throws Exception;
	
	/**
	 * 套餐订购
	 * @param businessStr 入参
	 * @return
	 * @throws Exception 
	 */
	public String tcFlowSub(String businessStr) throws Exception;

	/**
	 * ocs 套餐订购
	 * @param params
	 * @return
	 */
	public JSONObject planpurchase(String msisdn,String productID);
	
	/**
	 * 套餐详情
	 * @param businessStr
	 * @return
	 */
	public String getPackageDetail(String businessStr);
}
