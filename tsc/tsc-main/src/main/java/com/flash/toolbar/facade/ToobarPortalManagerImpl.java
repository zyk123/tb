package com.flash.toolbar.facade;

import com.alibaba.fastjson.JSONObject;
import com.flash.toolbar.common.util.BusinessLogger;
import com.flash.toolbar.common.util.ExceptionLogger;
import com.flash.toolbar.model.SysMainInterfaceIconConfig;
import com.flash.toolbar.service.ToolbarPortalManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * toolbar入口界面管理入口类具体实现
 * @author ocean
 *
 */
@Service(value="toobarPortalManager")
public class ToobarPortalManagerImpl implements ToolbarPortalManager{

	@Autowired
	private ToolbarPortalManagerService toolbarPortalManagerService;
	
	/**
	 * 判断toolbar是否可见，并且判断用户是否要初始化，做初始化操作
	 */
	@Override
	public String isToolbarAvaliable(String businessStr) {
		String rtn = null;
		try {
			JSONObject requestJSON = JSONObject.parseObject(businessStr);
			String requestSerial = requestJSON.getString("requestSerial");			
			//tsc判断是否展示toolbar球业务日志记录开始
			BusinessLogger.LoggerInfo(requestSerial, "tsc", "isToolbarAvaliable", true, null, String.valueOf(new Date().getTime()));
			rtn = toolbarPortalManagerService.isToolbarAvaliable(businessStr);
			//tsc判断是否展示toolbar球业务日志记录结束
			BusinessLogger.LoggerInfo(requestSerial, "tsc", "isToolbarAvaliable", false, rtn, String.valueOf(new Date().getTime()));
		} catch (Exception e) {
			ExceptionLogger.error("isToolbarAvaliable Exception", e);
		}
		return rtn;
	}
	
	/**
	 * 从数据库中捞取用户信息放到session中
	 */
	@Override
	public String queryToolbarSession(String businessStr) {
		String rtn = null;
		try {
			JSONObject requestJSON = JSONObject.parseObject(businessStr);
			String requestSerial = requestJSON.getString("requestSerial");			
			//tsc判断是否展示toolbar球业务日志记录开始
			BusinessLogger.LoggerInfo(requestSerial, "tsc", "queryToolbarSession", true, null, String.valueOf(new Date().getTime()));
			rtn = toolbarPortalManagerService.queryToolbarSession(businessStr);
			//tsc判断是否展示toolbar球业务日志记录结束
			BusinessLogger.LoggerInfo(requestSerial, "tsc", "queryToolbarSession", false, rtn, String.valueOf(new Date().getTime()));
		} catch (Exception e) {
			ExceptionLogger.error("queryToolbarSession Exception", e);
		}
		return rtn;
	}

	/**
	 * 将会员表中首次访问字段置为1
	 * @param businessStr
	 * @return
	 */
	@Override
	public String welHasShown(String businessStr) {
		String rtn = "";
		try {
			JSONObject requestJSON = JSONObject.parseObject(businessStr);
			String requestSerial = requestJSON.getString("requestSerial");
			BusinessLogger.LoggerInfo(requestSerial, "tsc", "welHasShown", true, null, String.valueOf(new Date().getTime()));
			rtn = toolbarPortalManagerService.updateFirstShowState(requestJSON.getString("memberId"),requestJSON.getString("tOperatorId"),requestJSON.getString("countryNo"));
			BusinessLogger.LoggerInfo(requestSerial, "tsc", "welHasShown", false, rtn, String.valueOf(new Date().getTime()));
		} catch (Exception e) {
			ExceptionLogger.info("welHasShown Exception", e);
		}
		return rtn;
	}

	@Override
	public String getAllIcon(String businessStr) {
		String resp = "";
		try {
			JSONObject requestJSON = JSONObject.parseObject(businessStr);
			String requestSerial = requestJSON.getString("requestSerial");
			BusinessLogger.LoggerInfo(requestSerial, "tsc", "getAllIcon", true, null, String.valueOf(new Date().getTime()));
			List<SysMainInterfaceIconConfig> list = toolbarPortalManagerService.queryIcon(requestJSON.getString("type"),requestJSON.getString("tOperatorId"),requestJSON.getString("countryNo"));
			resp = JSONObject.toJSONString(list);
			BusinessLogger.LoggerInfo(requestSerial, "tsc", "getAllIcon", false, resp, String.valueOf(new Date().getTime()));
		} catch (Exception e) {
			ExceptionLogger.info("getAllIcon Exception", e);
		}
		return resp;
	}

	@Override
	public String activityHasShown(String businessStr) {
		String rtn = "";
		try {
			JSONObject requestJSON = JSONObject.parseObject(businessStr);
			String requestSerial = requestJSON.getString("requestSerial");
			BusinessLogger.LoggerInfo(requestSerial, "tsc", "activityHasShown", true, null, String.valueOf(new Date().getTime()));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
			Date date = sdf.parse(requestJSON.getString("date"));
			rtn = toolbarPortalManagerService.updateActivityShowDate(requestJSON.getString("memberId"),requestJSON.getString("tOperatorId"),requestJSON.getString("countryNo"),date);
			BusinessLogger.LoggerInfo(requestSerial, "tsc", "activityHasShown", false, rtn, String.valueOf(new Date().getTime()));
		} catch (Exception e) {
			ExceptionLogger.info("activityHasShown Exception", e);
		}
		return rtn;
	}

	@Override
	public String isActivityPopup(String businessStr) {
		String rtn = "";
		try {
			JSONObject requestJSON = JSONObject.parseObject(businessStr);
			String requestSerial = requestJSON.getString("requestSerial");
			BusinessLogger.LoggerInfo(requestSerial, "tsc", "isActivityPopup", true, null, String.valueOf(new Date().getTime()));
			rtn = toolbarPortalManagerService.isActivityPopup(requestJSON.getString("mobileNo"),requestJSON.getString("memberId"),requestJSON.getString("serviceType"))==true?"SUCCESS":"FAIL";
			BusinessLogger.LoggerInfo(requestSerial, "tsc", "isActivityPopup", false, String.valueOf(rtn), String.valueOf(new Date().getTime()));
		} catch (Exception e) {
			ExceptionLogger.info("isActivityPopup Exception", e);
		}
		return rtn;
	}
}
