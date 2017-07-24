package com.flash.toolbar.ic.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import com.flash.toolbar.facade.ToolbarPortalManager;
import com.flash.toolbar.ic.dubbo.DubboSupport;

@Controller
@RequestMapping(value = "/toolbarportal")
public class ToolbarPortalController {

	@Autowired
	private DubboSupport dubboSupport;

	/**
	 * 判断toolbar是否可见，并且判断用户是否要初始化，做初始化操作
	 * @param mobileNo 手机号码
	 * @return
	 */
	@RequestMapping(value = "/isToolbarAvaliable", method = RequestMethod.POST)
	@ResponseBody
	public String isToolbarAvaliable(
			@RequestParam(value = "mobileNo", defaultValue = "") String mobileNo,
			@RequestParam(value = "userAgent", defaultValue = "") String userAgent,
			@RequestParam(value = "memberId", defaultValue = "") String memberId,
			@RequestParam(value = "postPrepaid", defaultValue = "") String postPrepaid,
			@RequestParam(value = "requestSerial", defaultValue = "") String requestSerial,
			boolean isAllProcess) {
		if(!StringUtil.isNotNullOrEmpty(mobileNo)){
			JSONObject jsonFailed = new JSONObject();
			jsonFailed.put("retCode", Constant.PARAMS_NOTNULL);
			jsonFailed.put("retMsg", "monileNo can not be empty!");
			return ResponseUtil.unifySuccessReturn(jsonFailed);
		}
		
		JSONObject json = new JSONObject();
		json.put("mobileNo", mobileNo);
		json.put("serviceType", postPrepaid);
		json.put("userAgent", userAgent);
		json.put("requestSerial", requestSerial);
		json.put("isAllProcess", isAllProcess);
		String param = json.toJSONString();
//		TraceLogger.info("tic-toolbarportal-isToolbarAvaliable param:"+param);
		//tic判断是否展示toolbar球业务日志记录开始
		BusinessLogger.LoggerInfo(requestSerial, "tic", "isToolbarAvaliable", true, null, String.valueOf(new Date().getTime()));		
		ToolbarPortalManager toolbarPortalManager = dubboSupport.getToolbarPortalManager();
		String tmp = toolbarPortalManager.isToolbarAvaliable(
				param);
//		TraceLogger.info("tic-toolbarportal-isToolbarAvaliable return:"+tmp);
		//tic判断是否展示toolbar球业务日志记录结束
		BusinessLogger.LoggerInfo(requestSerial, "tic", "isToolbarAvaliable", false, tmp, String.valueOf(new Date().getTime()));	
		return tmp;
	}
	
	/**
	 * 从数据库中捞取用户信息放到session中
	 * @param memberId
	 * @param requestSerial
	 * @return
	 */
	@RequestMapping(value = "/queryToolbarSession", method = RequestMethod.POST)
	@ResponseBody
	public String queryToolbarSession(
			@RequestParam(value = "memberId", defaultValue = "") String memberId,
			@RequestParam(value = "requestSerial", defaultValue = "") String requestSerial) {
		if(!StringUtil.isNotNullOrEmpty(memberId)){
			JSONObject jsonFailed = new JSONObject();
			jsonFailed.put("retCode", Constant.PARAMS_NOTNULL);
			jsonFailed.put("retMsg", "memberId can not be empty!");
			return ResponseUtil.unifySuccessReturn(jsonFailed);
		}
		
		JSONObject json = new JSONObject();
		json.put("memberId", memberId);
		json.put("requestSerial", requestSerial);
		String param1 = json.toJSONString();
		BusinessLogger.LoggerInfo(requestSerial, "tic", "queryToolbarSession", true, param1, String.valueOf(new Date().getTime()));		
		ToolbarPortalManager toolbarPortalManager = dubboSupport.getToolbarPortalManager();
		String tmp = toolbarPortalManager.queryToolbarSession(param1);
		BusinessLogger.LoggerInfo(requestSerial, "tic", "queryToolbarSession", false, tmp, String.valueOf(new Date().getTime()));	
		return tmp;
	}
	
	@RequestMapping(value="welHasShown")
	@ResponseBody
	public String welHasShown(String memberId,String requestSerial,String tOperatorId,String countryNo){
		String respStr = "";
		if(StringUtils.isNotEmpty(memberId)){
			JSONObject json = new JSONObject();
			json.put("memberId", memberId);
			json.put("requestSerial", requestSerial);
			json.put("tOperatorId", tOperatorId);
			json.put("countryNo", countryNo);
			String param = json.toJSONString();
			BusinessLogger.LoggerInfo(requestSerial, "tic", "welHasShown", true, param, String.valueOf(new Date().getTime()));		
			ToolbarPortalManager toolbarPortalManager = dubboSupport.getToolbarPortalManager();
			respStr = toolbarPortalManager.welHasShown(param);
			BusinessLogger.LoggerInfo(requestSerial, "tic", "welHasShown", false, param, String.valueOf(new Date().getTime()));
		}
		return respStr;
	}
	
	@RequestMapping(value="getAllIcon")
	@ResponseBody
	public  String getAllIcon(String serviceType,String requestSerial,String tOperatorId,String countryNo){
		BusinessLogger.LoggerInfo(requestSerial, "tbar", "getAllIcon", true, null, String.valueOf(new Date().getTime()));
		String resp = "";
		JSONObject json = new JSONObject();
		json.put("type", serviceType);
		json.put("requestSerial", requestSerial);
		json.put("tOperatorId", tOperatorId);
		json.put("countryNo", countryNo);
		String param = json.toJSONString();
		ToolbarPortalManager toolbarPortalManager = dubboSupport.getToolbarPortalManager();
		resp = toolbarPortalManager.getAllIcon(param);
		BusinessLogger.LoggerInfo(requestSerial, "tbar", "getAllIcon", false, null, String.valueOf(new Date().getTime()));
		return resp;
	}

	@RequestMapping(value="activityHasShown")
	@ResponseBody
	public String activityHasShown(String memberId,String requestSerial,String tOperatorId,String countryNo,String date){
		String respStr = "";
		if(StringUtils.isNotEmpty(memberId)){
			JSONObject json = new JSONObject();
			json.put("memberId", memberId);
			json.put("requestSerial", requestSerial);
			json.put("tOperatorId", tOperatorId);
			json.put("countryNo", countryNo);
			json.put("date",date);
			String param = json.toJSONString();
			BusinessLogger.LoggerInfo(requestSerial, "tic", "activityHasShown", true, param, String.valueOf(new Date().getTime()));
			ToolbarPortalManager toolbarPortalManager = dubboSupport.getToolbarPortalManager();
			respStr = toolbarPortalManager.activityHasShown(param);
			BusinessLogger.LoggerInfo(requestSerial, "tic", "activityHasShown", false, param, String.valueOf(new Date().getTime()));
		}
		return respStr;
	}

	@RequestMapping(value="isActivityPopup")
	@ResponseBody
	public String isActivityPopup(
			@RequestParam(value = "mobileNo", defaultValue = "") String mobileNo,
			@RequestParam(value = "requestSerial", defaultValue = "") String requestSerial,
			@RequestParam(value = "memberId", defaultValue = "") String memberId,
			@RequestParam(value = "serviceType", defaultValue = "") String serviceType
			){
		String respStr = "";
		if(StringUtils.isNotEmpty(mobileNo)){
			JSONObject json = new JSONObject();
			json.put("mobileNo", mobileNo);
			json.put("requestSerial", requestSerial);
			json.put("serviceType", serviceType);
			json.put("memberId", memberId);
			String param = json.toJSONString();
			BusinessLogger.LoggerInfo(requestSerial, "tic", "isActivityPopup", true, param, String.valueOf(new Date().getTime()));
			ToolbarPortalManager toolbarPortalManager = dubboSupport.getToolbarPortalManager();
			respStr = toolbarPortalManager.isActivityPopup(param);
			BusinessLogger.LoggerInfo(requestSerial, "tic", "isActivityPopup", false, param, String.valueOf(new Date().getTime()));
		}
		return respStr;
	}
}
