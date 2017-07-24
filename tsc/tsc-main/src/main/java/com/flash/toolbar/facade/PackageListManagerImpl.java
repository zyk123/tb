package com.flash.toolbar.facade;

import java.util.Date;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.flash.toolbar.common.util.BusinessLogger;
import com.flash.toolbar.common.util.ExceptionLogger;
import com.flash.toolbar.common.util.StringUtil;
import com.flash.toolbar.common.util.TraceLogger;
import com.flash.toolbar.service.impl.PackageListServiceImpl;

@Service(value="packageListManager")
public class PackageListManagerImpl implements PackageListManager{

	@Autowired
	private PackageListServiceImpl packageListServiceImpl;
	
	/**
	 * 套餐查询接口
	 * @param businessStr 入参
	 * @return
	 */
	@Override
	public String getPackageList(String businessStr) {
		String rtn = null;
//		TraceLogger.info("tsc-packageListManager-getPackageList param:"+businessStr);
		try {
			JSONObject requestJSON = JSONObject.parseObject(businessStr);
			String requestSerial = requestJSON.getString("requestSerial");			
			//tsc套餐列表展示业务日志记录开始
			BusinessLogger.LoggerInfo(requestSerial, "tsc", "getPackageList", true, null, String.valueOf(new Date().getTime()));
			rtn = packageListServiceImpl.getPackageList(businessStr);
			//tsc套餐列表展示业务日志记录结束
			BusinessLogger.LoggerInfo(requestSerial, "tsc", "getPackageList", false, null, String.valueOf(new Date().getTime()));
		} catch (Exception e) {
			ExceptionLogger.info("getPackageList Exception", e);
		}
//		TraceLogger.info("tsc-packageListManager-getPackageList return:"+rtn);
		return rtn;
	}

	/**
	 * 套餐订购接口
	 */
	@Override
	public String tcFlowSub(String businessStr) {
		String rtn = null;
//		TraceLogger.info("tsc-packageListManager-tcFlowSub param:"+businessStr);
		try{
			JSONObject requestJSON = JSONObject.parseObject(businessStr);
			String requestSerial = requestJSON.getString("requestSerial");			
			//tsc套餐订购业务日志记录开始
			BusinessLogger.LoggerInfo(requestSerial, "tsc", "tcFlowSub", true, null, String.valueOf(new Date().getTime()));
			rtn = packageListServiceImpl.tcFlowSub(businessStr);
			//tsc套餐订购业务日志记录结束
			BusinessLogger.LoggerInfo(requestSerial, "tsc", "tcFlowSub", false, rtn, String.valueOf(new Date().getTime()));
		}catch(Exception e){
			ExceptionLogger.info("tcFlowSub Exception", e);
		}
//		TraceLogger.info("tsc-packageListManager-tcFlowSub return:"+rtn);
		return rtn;
	}

	/**
	 * 获取套餐详情
	 */
	@Override
	public String getPackageDetail(String businessStr) {
		try{
			JSONObject requestJSON = JSONObject.parseObject(businessStr);
			String requestSerial = requestJSON.getString("requestSerial");
			BusinessLogger.LoggerInfo(requestSerial, "tsc", "getPackageDetail", true, businessStr, String.valueOf(new Date().getTime()));
			String result = packageListServiceImpl.getPackageDetail(businessStr);
			BusinessLogger.LoggerInfo(requestSerial, "tsc", "getPackageDetail", false, result, String.valueOf(new Date().getTime()));
			return result;
		}
		catch(Exception e){
			ExceptionLogger.info("getPackageDetail Exception", e);
		}
		return null;
	}
	
}
