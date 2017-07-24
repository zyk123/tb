package com.flash.toolbar.ic.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.flash.toolbar.common.bean.PageLoadBean;
import com.flash.toolbar.common.util.BusinessLogger;
import com.flash.toolbar.common.util.Constant;
import com.flash.toolbar.common.util.DateTimeUtil;
import com.flash.toolbar.common.util.DateUtil;
import com.flash.toolbar.common.util.ExceptionLogger;
import com.flash.toolbar.common.util.ResponseUtil;
import com.flash.toolbar.common.util.StringUtil;
import com.flash.toolbar.common.util.TraceLogger;
import com.flash.toolbar.ic.dubbo.DubboSupport;

@Controller
@RequestMapping(value = "/member")
public class MemberController {
	@Autowired
	private DubboSupport dubboSupport;
	
	@RequestMapping(value = "/manualDoStaticCache")
	public void manualDoStaticCache(){
		dubboSupport.getLotteryFileManager().manualDoStaticCache();
	}
	
	@RequestMapping(value = "/manualReadPageCsv")
	public void manualReadPageCsv(){
		dubboSupport.getLotteryFileManager().manualReadPageCsv();
	}
	
	@RequestMapping(value = "/manualReadClickEventCsv")
	public void manualReadClickEventCsv(){
		dubboSupport.getLotteryFileManager().manualReadClickEventCsv();
	}
	

	/**
	 * 记录用户访问页面事件接口
	 * 
	 * @param response
	 * @param request
	 * @param memberId
	 * @param userAgent
	 * @param referer
	 * @param curUrl
	 * @param browseUrl
	 * @param tOperatorId
	 * @param countryNo
	 * @param timeZone
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/recordVisitAction", method = RequestMethod.POST)
	@ResponseBody
	public String recordVisit(
			HttpServletResponse response,
			HttpServletRequest request,
			@RequestParam(value = "memberId", defaultValue = "") String memberId,
			@RequestParam(value = "userAgent", defaultValue = "") String userAgent,
			@RequestParam(value = "referer", defaultValue = "") String referer,
			@RequestParam(value = "curUrl", defaultValue = "") String curUrl,
			@RequestParam(value = "browseUrl", defaultValue = "") String browseUrl,
			@RequestParam(value = "tOperatorId", defaultValue = "") String tOperatorId,
			@RequestParam(value = "countryNo", defaultValue = "") String countryNo,
			@RequestParam(value = "timeZone", defaultValue = "") String timeZone,
			@RequestParam(value = "ip", defaultValue = "") String ip,
			@RequestParam(value = "requestSerial", defaultValue = "") String requestSerial,
			@RequestParam(value = "imei", defaultValue = "") String imei,
			@RequestParam(value = "msisdn", defaultValue = "") String msisdn,
			@RequestParam(value = "msip", defaultValue = "") String msip)
			throws UnsupportedEncodingException {
//		TraceLogger.info("tic-member-recordVisitAction start:params:memberId="
//				+ memberId + ", tOperatorId=" + tOperatorId + ", countryNo="
//				+ countryNo + ", timeZone=" + timeZone + ", userAgent="
//				+ userAgent + ", referer=" + referer + ", curUrl=" + curUrl
//				+ ", browseUrl=" + browseUrl);
		//tic记录用户访问页面事件业务日志记录开始
		BusinessLogger.LoggerInfo(requestSerial, "tic", "recordVisit", true, null, String.valueOf(new Date().getTime()));
		JSONObject jsonResult = new JSONObject();
		if (StringUtil.isNotNullOrEmpty(memberId, tOperatorId, countryNo)) {
			PageLoadBean bean = new PageLoadBean();
			bean.setMemberId(memberId);
			bean.setUserAgent(userAgent);
			bean.setReferer(URLDecoder.decode(referer, "UTF-8"));
			bean.setHref(URLDecoder.decode(curUrl, "UTF-8"));
			bean.setPageName(browseUrl);
			bean.settOperatorId(tOperatorId);
			bean.setCountryNo(countryNo);
			bean.setTimeZone(timeZone);
			bean.setIp(ip);
			bean.setPhoneNo(msisdn);
			bean.setImei(imei);
			bean.setMsip(msip);

			String tmp = dubboSupport.getLotteryFileManager()
					.savePageLoad(bean,requestSerial);
			if (tmp.equals(Constant.SUCCEED)) {
				jsonResult.put("retCode", Constant.RETURN_SUCCESS);
				jsonResult.put("retMsg", "");
				BusinessLogger.LoggerInfo(requestSerial, "tic", "recordVisit", false, JSONObject.toJSONString(jsonResult), String.valueOf(new Date().getTime()));
				return ResponseUtil.unifySuccessReturn(jsonResult);
			} else {
				jsonResult.put("retCode", Constant.SYSERROR_ELSE);
				jsonResult.put("retMsg", "save failed");
			}
		} else {
			jsonResult.put("retCode", Constant.PARAMS_NOTNULL);
			jsonResult.put("retMsg", "params empty");
		}
//		TraceLogger.info("tic-member-recordVisitAction end");
		//tic查询用户剩余话费流量业务日志记录结束
		BusinessLogger.LoggerInfo(requestSerial, "tic", "recordVisit", false, JSONObject.toJSONString(jsonResult), String.valueOf(new Date().getTime()));
		return ResponseUtil.unifyFailReturn(jsonResult);
	}
	
	/**
	 * 记录用户点击页面事件接口
	 * 
	 * @param response
	 * @param request
	 * @param memberId
	 * @param userAgent
	 * @param referer
	 * @param curUrl
	 * @param browseUrl
	 * @param tOperatorId
	 * @param countryNo
	 * @param timeZone
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/recordClickEventAction", method = RequestMethod.POST)
	@ResponseBody
	public String recordClickEventAction(
			HttpServletResponse response,
			HttpServletRequest request,
			@RequestParam(value = "memberId", defaultValue = "") String memberId,
			@RequestParam(value = "userAgent", defaultValue = "") String userAgent,
			@RequestParam(value = "referer", defaultValue = "") String referer,
			@RequestParam(value = "curUrl", defaultValue = "") String curUrl,
			@RequestParam(value = "browseUrl", defaultValue = "") String browseUrl,
			@RequestParam(value = "tOperatorId", defaultValue = "") String tOperatorId,
			@RequestParam(value = "countryNo", defaultValue = "") String countryNo,
			@RequestParam(value = "timeZone", defaultValue = "") String timeZone,
			@RequestParam(value = "ip", defaultValue = "") String ip,
			@RequestParam(value = "requestSerial", defaultValue = "") String requestSerial,
			@RequestParam(value = "imei", defaultValue = "") String imei,
			@RequestParam(value = "msisdn", defaultValue = "") String msisdn,
			@RequestParam(value = "msip", defaultValue = "") String msip)
			throws UnsupportedEncodingException {
		//tic记录用户访问页面事件业务日志记录开始
		BusinessLogger.LoggerInfo(requestSerial, "tic", "recordClickEventAction", true, null, String.valueOf(new Date().getTime()));
		JSONObject jsonResult = new JSONObject();
		if (StringUtil.isNotNullOrEmpty(memberId, tOperatorId, countryNo)) {
			PageLoadBean bean = new PageLoadBean();
			bean.setMemberId(memberId);
			bean.setUserAgent(userAgent);
			bean.setReferer(URLDecoder.decode(referer, "UTF-8"));
			bean.setHref(URLDecoder.decode(curUrl, "UTF-8"));
			bean.setClickEventName(browseUrl);
			bean.settOperatorId(tOperatorId);
			bean.setCountryNo(countryNo);
			bean.setTimeZone(timeZone);
			bean.setIp(ip);
			bean.setPhoneNo(msisdn);
			bean.setImei(imei);
			bean.setMsip(msip);

			String tmp = dubboSupport.getLotteryFileManager()
					.saveClickEventCsv(bean,requestSerial);
			if (tmp.equals(Constant.SUCCEED)) {
				jsonResult.put("retCode", Constant.RETURN_SUCCESS);
				jsonResult.put("retMsg", "");
				BusinessLogger.LoggerInfo(requestSerial, "tic", "recordClickEventAction", false, JSONObject.toJSONString(jsonResult), String.valueOf(new Date().getTime()));
				return ResponseUtil.unifySuccessReturn(jsonResult);
			} else {
				jsonResult.put("retCode", Constant.SYSERROR_ELSE);
				jsonResult.put("retMsg", "save failed");
			}
		} else {
			jsonResult.put("retCode", Constant.PARAMS_NOTNULL);
			jsonResult.put("retMsg", "params empty");
		}
		//tic查询用户剩余话费流量业务日志记录结束
		BusinessLogger.LoggerInfo(requestSerial, "tic", "recordClickEventAction", false, JSONObject.toJSONString(jsonResult), String.valueOf(new Date().getTime()));
		return ResponseUtil.unifyFailReturn(jsonResult);
	}

	/**
	 * 用户界面开关设置接口
	 * 
	 * @param memberId
	 * @param tOperatorId
	 * @param countryNo
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/setToolbarAvaliable", method = RequestMethod.POST)
	@ResponseBody
	public String setToolbarAvaliable(
			@RequestParam(value = "memberId", defaultValue = "") String memberId,
			@RequestParam(value = "tOperatorId", defaultValue = "") String tOperatorId,
			@RequestParam(value = "countryNo", defaultValue = "") String countryNo,
			@RequestParam(value = "type", defaultValue = "") int type,
			@RequestParam(value = "requestSerial", defaultValue = "") String requestSerial) {
//		TraceLogger
//				.info("tic-member-setToolbarAvaliable start:params:memberId="
//						+ memberId + ", tOperatorId=" + tOperatorId
//						+ ", countryNo=" + countryNo + ", type=" + type);
		//tic用户关闭toolbar业务日志记录开始
		BusinessLogger.LoggerInfo(requestSerial, "tic", "setToolbarAvaliable", true, null, String.valueOf(new Date().getTime()));
		JSONObject jsonBody = new JSONObject();
		if (StringUtil.isNotNullOrEmpty(memberId, tOperatorId, countryNo)) {
			String closeType = "";
			switch (type) {
			case 1:
				closeType = "10";
				break;
			case 2:
				closeType = "11";
				break;
			case 3:
				closeType = "12";
				break;
			default:
				break;
			}

			if (!StringUtil.isNotNullOrEmpty(closeType)) {
				jsonBody.put("retCode", Constant.PARAMS_ILLEGAL);
				jsonBody.put("retMsg", "param invalid");
			} else {
				JSONObject json = new JSONObject();
				json.put("memberId", memberId);
				json.put("tOperatorId", tOperatorId);
				json.put("countryNo", countryNo);
				json.put("closeType", closeType);
				json.put("requestSerial", requestSerial);
				String tmp = dubboSupport.getMemberManager()
						.setToolbarAvaliable(json.toJSONString());
				jsonBody.put("retCode",
						JSONObject.parseObject(tmp).get("retCode"));
				jsonBody.put("retMsg", "");
				//tic用户关闭toolbar业务日志记录结束
				BusinessLogger.LoggerInfo(requestSerial, "tic", "setToolbarAvaliable", false, JSONObject.toJSONString(jsonBody), String.valueOf(new Date().getTime()));
				return ResponseUtil.unifySuccessReturn(jsonBody);
			}
		} else {
			jsonBody.put("retCode", Constant.PARAMS_NOTNULL);
			jsonBody.put("retMsg", "params empty");
		}
		//tic用户关闭toolbar业务日志记录结束
		BusinessLogger.LoggerInfo(requestSerial, "tic", "setToolbarAvaliable", false, JSONObject.toJSONString(jsonBody), String.valueOf(new Date().getTime()));
//		TraceLogger.info("tic-member-setToolbarAvaliable end");
		return ResponseUtil.unifyFailReturn(jsonBody);
	}

	/**
	 * 获取用户默认设置
	 * 
	 * @param memberId
	 * @param tOperatorId
	 * @param countryNo
	 * @return
	 */
	@RequestMapping(value = "/getDefaultSetting", method = RequestMethod.POST)
	@ResponseBody
	public String getDefaultSetting(
			@RequestParam(value = "memberId", defaultValue = "") String memberId,
			@RequestParam(value = "tOperatorId", defaultValue = "") String tOperatorId,
			@RequestParam(value = "countryNo", defaultValue = "") String countryNo,
			@RequestParam(value = "requestSerial", defaultValue = "") String requestSerial) {
//		TraceLogger.info("tic-member-getDefaultSetting start:params:memberId="
//				+ memberId + ", tOperatorId=" + tOperatorId + ", countryNo="
//				+ countryNo);
		//tic获取用户关闭toolbar信息业务日志记录开始
		BusinessLogger.LoggerInfo(requestSerial, "tic", "getDefaultSetting", true, null, String.valueOf(new Date().getTime()));
		JSONObject jsonBody = new JSONObject();
		if (StringUtil.isNotNullOrEmpty(memberId, tOperatorId, countryNo)) {
			JSONObject json = new JSONObject();
			json.put("memberId", memberId);
			json.put("tOperatorId", tOperatorId);
			json.put("countryNo", countryNo);
			json.put("requestSerial", requestSerial);
			String tmp = dubboSupport.getMemberManager().getDefaultSetting(
					json.toJSONString());
			JSONObject jsonO = JSONObject.parseObject(tmp);
			jsonBody.put("retCode", jsonO.get("retCode"));
			jsonBody.put("retMsg", "");
			jsonBody.put("tmp", jsonO);
			//tic获取用户关闭toolbar信息业务日志记录结束
			BusinessLogger.LoggerInfo(requestSerial, "tic", "getDefaultSetting", false, JSONObject.toJSONString(jsonBody), String.valueOf(new Date().getTime()));
			return ResponseUtil.unifySuccessReturn(jsonBody);
		} else {
			JSONObject jsonO = new JSONObject();
			jsonBody.put("retCode", Constant.PARAMS_NOTNULL);
			jsonBody.put("retMsg", "params empty");
			jsonBody.put("tmp", jsonO);
		}
		//tic获取用户关闭toolbar信息业务日志记录结束
		BusinessLogger.LoggerInfo(requestSerial, "tic", "getDefaultSetting", false, JSONObject.toJSONString(jsonBody), String.valueOf(new Date().getTime()));
//		TraceLogger.info("tic-member-getDefaultSetting end");
		return ResponseUtil.unifyFailReturn(jsonBody);
	}

	/**
	 * 查询用户已订购套餐
	 * 
	 * @return
	 */
	@RequestMapping(value = "/queryMyPackage", method = RequestMethod.POST)
	@ResponseBody
	public String queryMyPackage(
			@RequestParam(value = "memberId", defaultValue = "") String memberId,
			@RequestParam(value = "tOperatorId", defaultValue = "") String tOperatorId,
			@RequestParam(value = "countryNo", defaultValue = "") String countryNo,
			@RequestParam(value = "requestSerial", defaultValue = "") String requestSerial) {

		//tic查询用户已订购套餐业务日志记录开始
		BusinessLogger.LoggerInfo(requestSerial, "tic", "queryMyPackage", true, null, String.valueOf(new Date().getTime()));
		JSONObject jsonBody = new JSONObject();
		if (StringUtil.isNotNullOrEmpty(memberId, tOperatorId, countryNo)) {
			// 参数
			JSONObject param = new JSONObject();
			param.put("memberId", memberId);
			param.put("dateFrom", "2016-08-29 12:00:00");
			param.put("dateTo", "2016-09-29 12:00:00");
			param.put("requestSerial", requestSerial);

			try {
				// 查询用户已订购套餐列表
				String ret = dubboSupport.getMemberManager()
						.queryQuotaUsageFup(param.toJSONString());

				if (ret != null) {
					JSONObject jsonRet = JSONObject.parseObject(ret);
					JSONObject result = jsonRet
							.getJSONObject("outputQuotaUsageResp");
					if (result.getString("statusCode").toUpperCase()
							.equals("OK")) {
						JSONObject object = result.getJSONObject("listOfUsageInfoRecord");
						JSONArray array = object.getJSONArray("usageInfoRecord");
						Iterator<Object> iter = array.iterator();
						// Map<String, JSONObject> map = new HashMap<String,
						// JSONObject>();
						JSONArray objarr = new JSONArray();
						while (iter.hasNext()) {
							JSONObject content = (JSONObject) iter.next();
							JSONObject obj = new JSONObject();
							obj.put("packageID", content.getString("packageID"));// 获取供应商接口返回套餐名称和id
							obj.put("packageName", content.getString("packageName"));
							obj.put("startTime", content.getString("startTime"));
							obj.put("endTime", content.getString("endTime"));
							obj.put("subscribedSpeed",content.getString("subscribedSpeed"));
							obj.put("allocatedVolume",content.getString("allocatedVolume"));
							obj.put("purchasedVolume",content.getString("purchasedVolume"));
							obj.put("volumeUsed",content.getString("volumeUsed"));

							objarr.add(obj);

							// 再到数据库中查找该套餐的详细信息
							/*
							 * TraceLogger.info(
							 * "tic-member-queryMyPackage-1 query db flowpackage"
							 * );
							 * 
							 * JSONObject pgParam = new JSONObject();
							 * pgParam.put("fGNo",
							 * content.getString("PackageID"));
							 * pgParam.put("countryNo", countryNo);
							 * pgParam.put("tOperatorId", tOperatorId);
							 * 
							 * String pgRet =
							 * dubboSupport.getMemberManager().queryFlowPackage
							 * (pgParam.toJSONString());
							 * 
							 * TraceLogger.info(
							 * "tic-member-queryMyPackage-1 query db flowpackage result:"
							 * + pgRet);
							 * 
							 * if(pgRet != null){ JSONObject jsonpg
							 * =JSONObject.parseObject(pgRet);
							 * obj.put("fGremark",
							 * jsonpg.getString("remark")==null
							 * ?"":jsonpg.getString("remark"));
							 * obj.put("tOperatorId", tOperatorId);
							 * obj.put("langNo",
							 * jsonpg.getString("langno")==null
							 * ?"":jsonpg.getString("langno"));
							 * obj.put("expenses",
							 * jsonpg.getString("expenses")==
							 * null?"":jsonpg.getString("expenses"));
							 * obj.put("orderNo",
							 * jsonpg.getInteger("orderno")==null
							 * ?0:jsonpg.getInteger("orderno"));
							 * 
							 * //查询套餐类型 TraceLogger.info(
							 * "tic-member-queryMyPackage-2 query db packagetype"
							 * );
							 * 
							 * JSONObject pgtParam = new JSONObject();
							 * pgtParam.put("packageTypeId",
							 * jsonpg.getString("packagetypeid"));
							 * 
							 * String pgtRet =
							 * dubboSupport.getMemberManager().queryPackageType
							 * (pgtParam.toJSONString());
							 * 
							 * TraceLogger.info(
							 * "tic-member-queryMyPackage-2 query db packagetype result:"
							 * + pgtRet);
							 * 
							 * if(pgtRet != null){ JSONObject jsonpgt =
							 * JSONObject.parseObject(pgtRet);
							 * if(map.containsKey
							 * (jsonpgt.getString("packagetypeid"))){ JSONObject
							 * jsonPT =
							 * map.get(jsonpgt.getString("packagetypeid"));
							 * jsonPT.getJSONArray("packageInfo").add(obj); }
							 * else{ JSONObject jsonPT = new JSONObject();
							 * jsonPT.put("packageTypeId",
							 * jsonpgt.getString("packagetypeid"));
							 * jsonPT.put("packageTypeName",
							 * jsonpgt.getString("ptname"
							 * )==null?"":jsonpgt.getString("ptname"));
							 * jsonPT.put("sortNo",
							 * jsonpgt.getInteger("orderno")
							 * ==null?0:jsonpgt.getInteger("orderno"));
							 * JSONArray objarr = new JSONArray();
							 * objarr.add(obj); jsonPT.put("packageInfo",
							 * objarr);
							 * map.put(jsonpgt.getString("packagetypeid"),
							 * jsonPT); } } }
							 */
						}

						jsonBody.put("retCode", Constant.RETURN_SUCCESS);
						jsonBody.put("retMsg", "success");
						jsonBody.put("packageInfo", objarr);

						//tic查询用户已订购套餐业务日志记录结束
						BusinessLogger.LoggerInfo(requestSerial, "tic", "queryMyPackage", false, JSONObject.toJSONString(jsonBody), String.valueOf(new Date().getTime()));
						return ResponseUtil.unifySuccessReturn(jsonBody);

						// JSONArray conArr = new JSONArray();
						// //拼凑返回值
						// if(!map.isEmpty()){
						// Iterator<String> itor = map.keySet().iterator();
						// while(itor.hasNext()){
						// JSONObject json = (JSONObject) map.get(itor.next());
						// conArr.add(json);
						// }
						//
						// jsonBody.put("retCode", Constant.RETURN_SUCCESS);
						// jsonBody.put("retMsg", "success");
						// jsonBody.put("dataArr", conArr);
						//
						// TraceLogger.debug("tic-member-queryMyPackage success result info:");
						// TraceLogger.debug(ResponseUtil.unifySuccessReturn(jsonBody));
						// TraceLogger.info("tic-member-queryMyPackage end");
						//
						// return ResponseUtil.unifySuccessReturn(jsonBody);
						// }
						// else{
						// jsonBody.put("retCode", Constant.RETURN_EMPTY);
						// jsonBody.put("retMsg", "member package empty");
						// }
					}

				} else {
					jsonBody.put("retCode", Constant.RETURN_EMPTY);
					jsonBody.put("retMsg", "package empty");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			jsonBody.put("retCode", Constant.PARAMS_NOTNULL);
			jsonBody.put("retMsg", "params empty");
		}

		//tic查询用户已订购套餐业务日志记录结束
		BusinessLogger.LoggerInfo(requestSerial, "tic", "queryMyPackage", false, JSONObject.toJSONString(jsonBody), String.valueOf(new Date().getTime()));

		return ResponseUtil.unifyFailReturn(jsonBody);
	}

	/**
	 * 查询用户剩余话费流量接口
	 * 
	 * @return
	 */
	@RequestMapping(value = "/queryUserFlow", method = RequestMethod.POST)
	@ResponseBody
	public String queryUserFlow(
			@RequestParam(value = "memberId", defaultValue = "") String memberId,
			@RequestParam(value = "tOperatorId", defaultValue = "") String tOperatorId,
			@RequestParam(value = "countryNo", defaultValue = "") String countryNo,
			@RequestParam(value = "requestSerial", defaultValue = "") String requestSerial) {
		
		//tic查询用户剩余话费流量业务日志记录开始
		BusinessLogger.LoggerInfo(requestSerial, "tic", "queryUserFlow", true, null, String.valueOf(new Date().getTime()));
		JSONObject jsonBody = new JSONObject();
		if (StringUtil.isNotNullOrEmpty(memberId)) {
			try {
				// 参数
				JSONObject param = new JSONObject();
				param.put("memberId", memberId);
				param.put("requestSerial", requestSerial);
				
				//查询用户是否是预付费用户
				BusinessLogger.LoggerInfo(requestSerial, "tic", "queryProfileRetrieve", true, null, String.valueOf(new Date().getTime()));
				String profileRet = dubboSupport.getMemberManager().queryProfileRetrieve(param.toJSONString());
				BusinessLogger.LoggerInfo(requestSerial, "tic", "queryProfileRetrieve", false, profileRet, String.valueOf(new Date().getTime()));
//				String profileRet = dubboSupport.getMemberManager().queryCustomerRetrieve(param.toJSONString());
				
				if(profileRet != null){
//					JSONObject jsonRet = JSONObject.parseObject(profileRet);
//					JSONObject result = jsonRet.getJSONObject("outputSDResp");
//					String serviceType = result.getString("serviceType");
					if (profileRet.toLowerCase().equals("prepaid")) {
						jsonBody.put("serviceType", "prepaid");
						// 查询预付费用户剩余话费
						try{
							BusinessLogger.LoggerInfo(requestSerial, "tic", "queryAccountBalance", true, null, String.valueOf(new Date().getTime()));
							String accountRet = dubboSupport.getMemberManager().queryAccountBalance(param.toJSONString());
							BusinessLogger.LoggerInfo(requestSerial, "tic", "queryAccountBalance", false, accountRet, String.valueOf(new Date().getTime()));
							if(accountRet != null){
								JSONObject accRet = JSONObject.parseObject(accountRet);
								jsonBody.put("balance", accRet.getString("balance"));
								jsonBody.put("unit", accRet.getString("unit"));
								String validity = accRet.getString("validity");
								if(StringUtil.isNotNullOrEmpty(validity)){
									Date date = DateTimeUtil.getDateFromDateString(validity,"MM/dd/yyyy");
									String milStr = String.valueOf(date.getTime());
									String week = DateTimeUtil.getWeekOfDate2(milStr);
									String day = DateTimeUtil.getDateTime(date, "dd");
									String month = DateTimeUtil.getMonthOfDate(milStr);
									String year = DateTimeUtil.getDateTime(date, "yyyy");
									String time = week + ", " + day + " " + month + " " + year;//Fri,15 Nov,2016
									jsonBody.put("validity", time);
								}
								else{
									jsonBody.put("validity", "");
								}
								String laston = accRet.getString("lastOn");
								if(StringUtil.isNotNullOrEmpty(laston)){
									Date date = DateTimeUtil.parseDate(laston);
									String time = DateTimeUtil.getDateTime(date, "HH:mm:ss");
									jsonBody.put("lastOn", time);
								}
								else{
									jsonBody.put("lastOn", "");
								}
//								JSONObject jsonBlance = accRet.getJSONObject("outputPrepaidBalQueryResp");
//								JSONArray array = jsonBlance.getJSONArray("prepaidAccountsBalance");
//								Iterator<Object> iter = array.iterator();
//								while (iter.hasNext()) {
//									JSONObject record = (JSONObject) iter.next();
//									if(record.getString("accountCode").equals("2000") && record.getString("accountType").equals("PrepaidBalanceSubaccount")){
//										jsonBody.put("balance", record.getString("balance"));
//										jsonBody.put("unit", record.getString("unit"));
//										break;
//									}
//								}
							}
						}
						catch(Exception ex){
							ex.printStackTrace();
						}
					}
				}

				// 查询用户总流量接口
				BusinessLogger.LoggerInfo(requestSerial, "tic", "queryActquotaUsageFup", true, null, String.valueOf(new Date().getTime()));
				String ret = dubboSupport.getMemberManager().queryActquotaUsageFup(param.toJSONString());
				BusinessLogger.LoggerInfo(requestSerial, "tic", "queryActquotaUsageFup", false, ret, String.valueOf(new Date().getTime()));

				if (ret != null) {
					JSONObject jsonRet = JSONObject.parseObject(ret);
					float total = jsonRet.getFloatValue("VolumeTotal");
					float used = jsonRet.getFloatValue("VolumeUsed");
					jsonBody.put("totalData", jsonRet.getString("VolumeTotal"));	
					jsonBody.put("remainedData", String.valueOf(total - used));
					jsonBody.put("retCode", Constant.RETURN_SUCCESS);
					jsonBody.put("retMsg", "success");
					String validity = jsonRet.getString("SubscriptionEndDateTime");
					if(StringUtil.isNotNullOrEmpty(validity)){
						Date date = DateTimeUtil.getDateFromDateString(validity,"MM/dd/yyyy");
						String milStr = String.valueOf(date.getTime());
						String week = DateTimeUtil.getWeekOfDate2(milStr);
						String day = DateTimeUtil.getDateTime(date, "dd");
						String month = DateTimeUtil.getMonthOfDate(milStr);
						String year = DateTimeUtil.getDateTime(date, "yyyy");
						String time = week + ", " + day + " " + month + " " + year;//Fri,15 Nov,2016
						jsonBody.put("validity2", time);
					}
					else{
						jsonBody.put("validity2", "");
					}
					String laston = jsonRet.getString("LastOn");
					if(StringUtil.isNotNullOrEmpty(laston)){
						Date date = DateTimeUtil.parseDate(laston);
						String time = DateTimeUtil.getDateTime(date, "HH:mm:ss");
						jsonBody.put("lastOn2", time);
					}
					else{
						jsonBody.put("lastOn2", "");
					}

					BusinessLogger.LoggerInfo(requestSerial, "tic", "queryUserFlow", false, JSONObject.toJSONString(jsonBody), String.valueOf(new Date().getTime()));
					
					
				} else {
					jsonBody.put("retCode", Constant.RETURN_EMPTY);
					jsonBody.put("retMsg", "ActquotaUsageFup empty");
				}
				return ResponseUtil.unifySuccessReturn(jsonBody);
			} catch (Exception e) {
				//e.printStackTrace();
				ExceptionLogger.LoggerInfo(requestSerial, memberId, "MemberController queryUserFlow", e);
			}
		} else {
			jsonBody.put("retCode", Constant.PARAMS_NOTNULL);
			jsonBody.put("retMsg", "params empty");
		}
		//tic查询用户剩余话费流量业务日志记录结束
		BusinessLogger.LoggerInfo(requestSerial, "tic", "queryUserFlow", false, JSONObject.toJSONString(jsonBody), String.valueOf(new Date().getTime()));

		return ResponseUtil.unifyFailReturn(jsonBody);
	}

	/**
	 * 流量提醒接口
	 * 
	 * @return
	 */
	@RequestMapping(value = "/queryFlowPortal", method = RequestMethod.POST)
	@ResponseBody
	public String queryFlowPortal(
			@RequestParam(value = "memberId", defaultValue = "") String memberId,
			@RequestParam(value = "tOperatorId", defaultValue = "") String tOperatorId,
			@RequestParam(value = "countryNo", defaultValue = "") String countryNo,
			@RequestParam(value = "requestSerial", defaultValue = "") String requestSerial) {

		//tic查询流量提醒业务日志记录开始
		BusinessLogger.LoggerInfo(requestSerial, "tic", "queryFlowPortal", true, null, String.valueOf(new Date().getTime()));
		JSONObject jsonBody = new JSONObject();
		if (StringUtil.isNotNullOrEmpty(memberId, tOperatorId, countryNo)) {
			try {
				// 参数
				JSONObject param = new JSONObject();
				param.put("memberId", memberId);
				param.put("requestSerial", requestSerial);

				// 查询用户总流量接口
				String ret = dubboSupport.getMemberManager().queryActquotaUsageFup(param.toJSONString());

				if (ret != null) {
					JSONObject jsonRet = JSONObject.parseObject(ret);
//					JSONObject result = jsonRet.getJSONObject("outputActQuotaUsageResp");
//					if (result.getString("statusCode").toUpperCase().equals("OK")) {						
					
						float total = jsonRet.getFloatValue("VolumeTotal");// 总流量
						float used = jsonRet.getFloatValue("VolumeUsed");// 已使用流量
	
						// 查询是否已提醒过用户
						JSONObject paramAlert = new JSONObject();
						paramAlert.put("memberId", memberId);
						paramAlert.put("tOperatorId", tOperatorId);
						paramAlert.put("countryNo", countryNo);
						paramAlert.put("alertDate", DateUtil.getFormatDate(
								new Date(), DateUtil.FORMAT1));
						paramAlert.put("currentvalue", total - used);
						paramAlert.put("requestSerial", requestSerial);
	
						// 查询用户流量提醒阈值设置
						String strRet = dubboSupport.getMemberManager().queryAlertThreshold(paramAlert.toJSONString());
	
						if (strRet != null) {
							JSONObject thresholdRet = JSONObject
									.parseObject(strRet);
							int firstValue = thresholdRet.getIntValue("firstValue");
							int secondValue = thresholdRet.getIntValue("secondValue");
							//if (!isAlert) {
	
								int lastPer = (int) (((total - used) / total) * 100);//剩余百分比
								if (lastPer <= firstValue && lastPer > secondValue) {//
									paramAlert.put("alertvalue", firstValue);
									paramAlert.put("requestSerial", requestSerial);
									Boolean isAlert = dubboSupport.getMemberManager().queryFlowAlert(paramAlert.toJSONString());
								
									if (!isAlert) {
										// 超过首次提醒阈值 保存记录
										dubboSupport.getMemberManager().saveFlowAlert(paramAlert.toJSONString());
		
										jsonBody.put("status", "1");
										jsonBody.put("colorValue", "");
										jsonBody.put("content", "remind first value");
										jsonBody.put("picUrl", "");
										jsonBody.put("redirectUrl", "");
										jsonBody.put("totalData", jsonRet.getString("VolumeTotal"));
										jsonBody.put("remainedData", String.valueOf(total - used));
		
		
										return ResponseUtil.unifySuccessReturn(jsonBody);
									}else{
										jsonBody.put("retCode", Constant.RETURN_FAILURE);
										jsonBody.put("retMsg", "already alert");
									}
								} else if (lastPer <= secondValue) {
									paramAlert.put("alertvalue", secondValue);
									paramAlert.put("requestSerial", requestSerial);
									Boolean isAlert = dubboSupport.getMemberManager().queryFlowAlert(paramAlert.toJSONString());
									
									if (!isAlert) {
										// 超过二次提醒阈值 保存记录
										dubboSupport.getMemberManager().saveFlowAlert(paramAlert.toJSONString());
		
										jsonBody.put("status", "1");
										jsonBody.put("colorValue", "");
										jsonBody.put("content", "remind second value");
										jsonBody.put("picUrl", "");
										jsonBody.put("redirectUrl", "");
										jsonBody.put("totalData", jsonRet.getString("VolumeTotal"));
										jsonBody.put("remainedData", String.valueOf(total - used));
		
										return ResponseUtil.unifySuccessReturn(jsonBody);
									}else{
										jsonBody.put("retCode", Constant.RETURN_FAILURE);
										jsonBody.put("retMsg", "already alert");
									}
								}
							//}
						} else {
							jsonBody.put("retCode", Constant.RETURN_FAILURE);
							jsonBody.put("retMsg", "already alert");
						}
//					}
//					else {
//						jsonBody.put("retCode", Constant.RETURN_FAILURE);
//						jsonBody.put("retMsg", "ActquotaUsageFup fail");
//					}
				} else {
					jsonBody.put("retCode", Constant.RETURN_EMPTY);
					jsonBody.put("retMsg", "ActquotaUsageFup empty");
				}
			} catch (Exception e) {
				e.printStackTrace();
				jsonBody.put("retCode", Constant.SYSERROR_ELSE);
				jsonBody.put("retMsg", e.getMessage());
			}
		} else {
			jsonBody.put("retCode", Constant.PARAMS_NOTNULL);
			jsonBody.put("retMsg", "params empty");
		}

		jsonBody.put("status", "0");

		//tic查询用户剩余话费流量业务日志记录结束
		BusinessLogger.LoggerInfo(requestSerial, "tic", "queryFlowPortal", false, JSONObject.toJSONString(jsonBody), String.valueOf(new Date().getTime()));

		return ResponseUtil.unifyFailReturn(jsonBody);
	}

	/**
	 * 保存用户反馈信息
	 * 
	 * @param memberId
	 * @param tOperatorId
	 * @param countryNo
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/sendFeedback", method = RequestMethod.POST)
	@ResponseBody
	public String sendFeedback(
			@RequestParam(value = "memberId", defaultValue = "") String memberId,
			@RequestParam(value = "tOperatorId", defaultValue = "") String tOperatorId,
			@RequestParam(value = "countryNo", defaultValue = "") String countryNo,
			@RequestParam(value = "feedBack", defaultValue = "") String feedBack,
			@RequestParam(value = "username", defaultValue = "") String username,
			@RequestParam(value = "state", defaultValue = "") String state,
			@RequestParam(value = "email", defaultValue = "") String email,
			@RequestParam(value = "requestSerial", defaultValue = "") String requestSerial) {
//		TraceLogger.info("tic-member-sendFeedback start:params:memberId="
//				+ memberId + ", tOperatorId=" + tOperatorId + ", countryNo="
//				+ countryNo + ", feedBack=" + feedBack);
		//tic保存用户反馈信息业务日志记录开始
		BusinessLogger.LoggerInfo(requestSerial, "tic", "sendFeedback", true, null, String.valueOf(new Date().getTime()));
		JSONObject jsonBody = new JSONObject();
		if (StringUtil.isNotNullOrEmpty(memberId, tOperatorId, countryNo,
				feedBack)) {
			if (feedBack.length() > 500) {
				jsonBody.put("retCode", Constant.PARAMS_ILLEGAL);
				jsonBody.put("retMsg", "feedBack too long");
			} else {
				JSONObject json = new JSONObject();
				json.put("memberId", memberId);
				json.put("tOperatorId", tOperatorId);
				json.put("countryNo", countryNo);
				json.put("feedBack", feedBack);
				json.put("username", username);
				json.put("state", state);
				json.put("email", email);
				json.put("requestSerial", requestSerial);
				String tmp = dubboSupport.getMemberManager().sendFeedback(
						json.toJSONString());
				jsonBody.put("retCode",
						JSONObject.parseObject(tmp).get("retCode"));
				jsonBody.put("retMsg", "");
				//tic查询用户剩余话费流量业务日志记录结束
				BusinessLogger.LoggerInfo(requestSerial, "tic", "queryFlowPortal", false, JSONObject.toJSONString(jsonBody), String.valueOf(new Date().getTime()));
				return ResponseUtil.unifySuccessReturn(jsonBody);
			}
		} else {
			jsonBody.put("retCode", Constant.PARAMS_NOTNULL);
			jsonBody.put("retMsg", "params empty");
		}
//		TraceLogger.info("tic-member-sendFeedback end");
		//tic查询用户剩余话费流量业务日志记录结束
		BusinessLogger.LoggerInfo(requestSerial, "tic", "queryFlowPortal", false, JSONObject.toJSONString(jsonBody), String.valueOf(new Date().getTime()));
		return ResponseUtil.unifyFailReturn(jsonBody);
	}
	
	/**
	 * 游戏列表
	 * 
	 * @param tOperatorId
	 * @param countryNo
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/queryGameList", method = RequestMethod.POST)
	@ResponseBody
	public String queryGameList(
			@RequestParam(value = "tOperatorId", defaultValue = "") String tOperatorId,
			@RequestParam(value = "countryNo", defaultValue = "") String countryNo,
			@RequestParam(value = "type", defaultValue = "") String type,
			@RequestParam(value = "requestSerial", defaultValue = "") String requestSerial) {
		//tic保存用户反馈信息业务日志记录开始
		BusinessLogger.LoggerInfo(requestSerial, "tic", "queryGameList", true, null, String.valueOf(new Date().getTime()));
		JSONObject jsonBody = new JSONObject();
		if (StringUtil.isNotNullOrEmpty(type)) {
				JSONObject json = new JSONObject();
				json.put("tOperatorId", tOperatorId);
				json.put("countryNo", countryNo);
				json.put("type", type);
				json.put("requestSerial", requestSerial);
				String tmp = dubboSupport.getMemberManager().queryGameList(
						json.toJSONString());
				jsonBody.put("list",
						JSONObject.parseObject(tmp));
				jsonBody.put("retMsg", "");
				//tic查询用户剩余话费流量业务日志记录结束
				BusinessLogger.LoggerInfo(requestSerial, "tic", "queryGameList", false, JSONObject.toJSONString(jsonBody), String.valueOf(new Date().getTime()));
				return ResponseUtil.unifySuccessReturn(jsonBody);
		} else {
			jsonBody.put("retCode", Constant.PARAMS_NOTNULL);
			jsonBody.put("retMsg", "params empty");
		}
//		TraceLogger.info("tic-member-sendFeedback end");
		//tic查询用户剩余话费流量业务日志记录结束
		BusinessLogger.LoggerInfo(requestSerial, "tic", "queryGameList", false, JSONObject.toJSONString(jsonBody), String.valueOf(new Date().getTime()));
		return ResponseUtil.unifyFailReturn(jsonBody);
	}
	
	/**
	 * 网站列表
	 * 
	 * @param tOperatorId
	 * @param countryNo
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/queryWebsiteList", method = RequestMethod.POST)
	@ResponseBody
	public String queryWebsiteList(
			@RequestParam(value = "tOperatorId", defaultValue = "") String tOperatorId,
			@RequestParam(value = "countryNo", defaultValue = "") String countryNo,
			@RequestParam(value = "requestSerial", defaultValue = "") String requestSerial) {
		//tic保存用户反馈信息业务日志记录开始
		BusinessLogger.LoggerInfo(requestSerial, "tic", "queryWebsiteList", true, null, String.valueOf(new Date().getTime()));
		JSONObject jsonBody = new JSONObject();
		try {
			JSONObject json = new JSONObject();
			json.put("tOperatorId", tOperatorId);
			json.put("countryNo", countryNo);
			json.put("requestSerial", requestSerial);
			String tmp = dubboSupport.getMemberManager().queryWebsiteList(
					json.toJSONString());
			jsonBody.put("list",
					JSONObject.parseObject(tmp));
			jsonBody.put("retMsg", "");
			//tic查询用户剩余话费流量业务日志记录结束
			BusinessLogger.LoggerInfo(requestSerial, "tic", "queryWebsiteList", false, JSONObject.toJSONString(jsonBody), String.valueOf(new Date().getTime()));
			return ResponseUtil.unifySuccessReturn(jsonBody);			
		} catch (Exception e) {
			e.printStackTrace();
			jsonBody.put("retCode", Constant.PARAMS_NOTNULL);
			jsonBody.put("retMsg", "params empty");			
		}

//		TraceLogger.info("tic-member-sendFeedback end");
		//tic查询用户剩余话费流量业务日志记录结束
		BusinessLogger.LoggerInfo(requestSerial, "tic", "queryWebsiteList", false, JSONObject.toJSONString(jsonBody), String.valueOf(new Date().getTime()));
		return ResponseUtil.unifyFailReturn(jsonBody);
	}	
	
	
	/**
	 * 读取游戏图标
	 * 
	 * @param tOperatorId
	 * @param countryNo
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/queryGameIcon", method = RequestMethod.POST)
	@ResponseBody
	public String queryGameIcon(
			@RequestParam(value = "tOperatorId", defaultValue = "") String tOperatorId,
			@RequestParam(value = "countryNo", defaultValue = "") String countryNo,
			@RequestParam(value = "id", defaultValue = "") String id,
			@RequestParam(value = "requestSerial", defaultValue = "") String requestSerial) {
		//tic保存用户反馈信息业务日志记录开始
		BusinessLogger.LoggerInfo(requestSerial, "tic", "queryGameIcon", true, null, String.valueOf(new Date().getTime()));
		JSONObject jsonBody = new JSONObject();
		if (StringUtil.isNotNullOrEmpty(id)) {
				JSONObject json = new JSONObject();
				json.put("tOperatorId", tOperatorId);
				json.put("countryNo", countryNo);
				json.put("id", id);
				json.put("requestSerial", requestSerial);
				String tmp = dubboSupport.getMemberManager().readGameIcon(
						json.toJSONString());
				jsonBody.put("result",
						JSONObject.parseObject(tmp));
				jsonBody.put("retMsg", "");
				//tic查询用户剩余话费流量业务日志记录结束
				BusinessLogger.LoggerInfo(requestSerial, "tic", "queryGameIcon", false, JSONObject.toJSONString(jsonBody), String.valueOf(new Date().getTime()));
				return ResponseUtil.unifySuccessReturn(jsonBody);
		} else {
			jsonBody.put("retCode", Constant.PARAMS_NOTNULL);
			jsonBody.put("retMsg", "params empty");
		}
//		TraceLogger.info("tic-member-sendFeedback end");
		//tic查询用户剩余话费流量业务日志记录结束
		BusinessLogger.LoggerInfo(requestSerial, "tic", "queryGameIcon", false, JSONObject.toJSONString(jsonBody), String.valueOf(new Date().getTime()));
		return ResponseUtil.unifyFailReturn(jsonBody);
	}
	
	/**
	 * 读取网站图标
	 * 
	 * @param tOperatorId
	 * @param countryNo
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/queryWebsiteIcon", method = RequestMethod.POST)
	@ResponseBody
	public String queryWebsiteIcon(
			@RequestParam(value = "tOperatorId", defaultValue = "") String tOperatorId,
			@RequestParam(value = "countryNo", defaultValue = "") String countryNo,
			@RequestParam(value = "id", defaultValue = "") String id,
			@RequestParam(value = "requestSerial", defaultValue = "") String requestSerial) {
		//tic保存用户反馈信息业务日志记录开始
		BusinessLogger.LoggerInfo(requestSerial, "tic", "queryWebsiteIcon", true, null, String.valueOf(new Date().getTime()));
		JSONObject jsonBody = new JSONObject();
		if (StringUtil.isNotNullOrEmpty(id)) {
				JSONObject json = new JSONObject();
				json.put("tOperatorId", tOperatorId);
				json.put("countryNo", countryNo);
				json.put("id", id);
				json.put("requestSerial", requestSerial);
				String tmp = dubboSupport.getMemberManager().readWebsiteIcon(
						json.toJSONString());
				jsonBody.put("result",
						JSONObject.parseObject(tmp));
				jsonBody.put("retMsg", "");
				//tic查询用户剩余话费流量业务日志记录结束
				BusinessLogger.LoggerInfo(requestSerial, "tic", "queryWebsiteIcon", false, JSONObject.toJSONString(jsonBody), String.valueOf(new Date().getTime()));
				return ResponseUtil.unifySuccessReturn(jsonBody);
		} else {
			jsonBody.put("retCode", Constant.PARAMS_NOTNULL);
			jsonBody.put("retMsg", "params empty");
		}
//		TraceLogger.info("tic-member-sendFeedback end");
		//tic查询用户剩余话费流量业务日志记录结束
		BusinessLogger.LoggerInfo(requestSerial, "tic", "queryWebsiteIcon", false, JSONObject.toJSONString(jsonBody), String.valueOf(new Date().getTime()));
		return ResponseUtil.unifyFailReturn(jsonBody);
	}		
	
}
