package com.flash.toolbar.ic.controller;

import java.util.Date;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.flash.toolbar.common.util.BusinessLogger;
import com.flash.toolbar.common.util.Constant;
import com.flash.toolbar.common.util.ResponseUtil;
import com.flash.toolbar.common.util.StringUtil;
import com.flash.toolbar.common.util.TraceLogger;
import com.flash.toolbar.facade.PackageListManager;
import com.flash.toolbar.ic.dubbo.DubboSupport;

@Controller
@RequestMapping(value = "/PackageListController")
public class PackageListController {

	@Autowired
	private DubboSupport dubboSupport;

	/**
	 * 套餐订购套餐展示页shop获取套餐
	 * @param mobileNo 手机号码
	 * @return
	 */
	@RequestMapping(value = "/getPackageListManager", method = RequestMethod.POST)
	@ResponseBody
	public String getPackageListManager(
			@RequestParam(value = "tOperatorId", defaultValue = "") String tOperatorId,
			@RequestParam(value = "countryNo", defaultValue = "") String countryNo,
			@RequestParam(value = "packageTypeId", defaultValue = "") String packageTypeId,
			@RequestParam(value = "requestSerial", defaultValue = "") String requestSerial,
			@RequestParam(value = "memberId", defaultValue = "") String memberId,
			@RequestParam(value = "serviceType", defaultValue = "") String serviceType
			) {
		if(!StringUtil.isNotNullOrEmpty(tOperatorId)){
			JSONObject jsonFailed = new JSONObject();
			jsonFailed.put("retCode", Constant.PARAMS_NOTNULL);
			jsonFailed.put("retMsg", "tOperatorId can not be empty!");
			return ResponseUtil.unifySuccessReturn(jsonFailed);
		}
		if(!StringUtil.isNotNullOrEmpty(countryNo)){
			JSONObject jsonFailed = new JSONObject();
			jsonFailed.put("retCode", Constant.PARAMS_NOTNULL);
			jsonFailed.put("retMsg", "countryNo can not be empty!");
			return ResponseUtil.unifySuccessReturn(jsonFailed);
		}
		if(!StringUtil.isNotNullOrEmpty(serviceType)){
			JSONObject jsonFailed = new JSONObject();
			jsonFailed.put("retCode", Constant.PARAMS_NOTNULL);
			jsonFailed.put("retMsg", "serviceType can not be empty!");
			return ResponseUtil.unifySuccessReturn(jsonFailed);
		}
		JSONObject json = new JSONObject();
		json.put("tOperatorId", tOperatorId);
		json.put("countryNo", countryNo);
		json.put("packageTypeId", packageTypeId);
		json.put("requestSerial", requestSerial);
		json.put("memberId", memberId);
		json.put("serviceType", serviceType);
		String param = json.toJSONString();
//		TraceLogger.info("tic-PackageListController-getPackageListManager param:"+param);
		//tic套餐列表展示业务日志记录开始
		BusinessLogger.LoggerInfo(requestSerial, "tic", "getPackageListManager", true, null, String.valueOf(new Date().getTime()));
		PackageListManager toolbarPortalManager = dubboSupport.getPackageListManager();
		String tmp = toolbarPortalManager.getPackageList(
				param);
//		TraceLogger.info("tic-PackageListController-getPackageListManager return:"+tmp);
		//tic套餐列表展示业务日志记录结束
		BusinessLogger.LoggerInfo(requestSerial, "tic", "getPackageListManager", false, null, String.valueOf(new Date().getTime()));
		return tmp;
	}
	
	/**
	 * 套餐订购
	 * @param tOperatorId
	 * @param countryNo
	 * @param packageTypeId
	 * @return
	 */
	@RequestMapping(value = "/tcFlowSub", method = RequestMethod.POST)
	@ResponseBody
	public String tcFlowSub(
			@RequestParam(value = "userId", defaultValue = "") String userId,
			@RequestParam(value = "fGNo", defaultValue = "") String fGNo,
			@RequestParam(value = "tOperatorId", defaultValue = "") String tOperatorId,
			@RequestParam(value = "countryNo", defaultValue = "") String countryNo,
			@RequestParam(value = "requestSerial", defaultValue = "") String requestSerial
			) {
		if(!StringUtil.isNotNullOrEmpty(userId)){
			JSONObject jsonFailed = new JSONObject();
			jsonFailed.put("retCode", Constant.PARAMS_NOTNULL);
			jsonFailed.put("retMsg", "userId can not be empty!");
			return ResponseUtil.unifySuccessReturn(jsonFailed);
		}
		if(!StringUtil.isNotNullOrEmpty(fGNo)){
			JSONObject jsonFailed = new JSONObject();
			jsonFailed.put("retCode", Constant.PARAMS_NOTNULL);
			jsonFailed.put("retMsg", "fGNo can not be empty!");
			return ResponseUtil.unifySuccessReturn(jsonFailed);
		}
		if(!StringUtil.isNotNullOrEmpty(tOperatorId)){
			JSONObject jsonFailed = new JSONObject();
			jsonFailed.put("retCode", Constant.PARAMS_NOTNULL);
			jsonFailed.put("retMsg", "tOperatorId can not be empty!");
			return ResponseUtil.unifySuccessReturn(jsonFailed);
		}
		if(!StringUtil.isNotNullOrEmpty(countryNo)){
			JSONObject jsonFailed = new JSONObject();
			jsonFailed.put("retCode", Constant.PARAMS_NOTNULL);
			jsonFailed.put("retMsg", "countryNo can not be empty!");
			return ResponseUtil.unifySuccessReturn(jsonFailed);
		}
		JSONObject json = new JSONObject();
		json.put("userId", userId);
		json.put("fGNo", fGNo);
		json.put("tOperatorId", tOperatorId);
		json.put("countryNo", countryNo);
		json.put("requestSerial", requestSerial);
		String param = json.toJSONString();
//		TraceLogger.info("tic-PackageListController-tcFlowSub param:"+param);
		//tic套餐订购业务日志记录开始
		BusinessLogger.LoggerInfo(requestSerial, "tic", "tcFlowSub", true, null, String.valueOf(new Date().getTime()));
		PackageListManager toolbarPortalManager = dubboSupport.getPackageListManager();
		String tmp = toolbarPortalManager.tcFlowSub(param);
		//tic套餐订购业务日志记录结束
		BusinessLogger.LoggerInfo(requestSerial, "tic", "tcFlowSub", false, tmp, String.valueOf(new Date().getTime()));
//		TraceLogger.info("tic-PackageListController-tcFlowSub return:"+tmp);
		return tmp;
	}
	
	@RequestMapping(value = "/getPackageDetail", method = RequestMethod.POST)
	@ResponseBody
	public String getPackageDetail(
			@RequestParam(value = "packageId", defaultValue = "") String packageId,
			@RequestParam(value = "requestSerial", defaultValue = "") String requestSerial
			) {
		if(StringUtil.isNotNullOrEmpty(packageId)){
			JSONObject json = new JSONObject();
			json.put("packageId", packageId);
			json.put("requestSerial", requestSerial);
			BusinessLogger.LoggerInfo(requestSerial, "tic", "getPackageDetail", true, json.toJSONString(), String.valueOf(new Date().getTime()));
			String result = dubboSupport.getPackageListManager().getPackageDetail(json.toJSONString());
			BusinessLogger.LoggerInfo(requestSerial, "tic", "getPackageDetail", false, result, String.valueOf(new Date().getTime()));
			return result;
		}
		return null;
	}
}
