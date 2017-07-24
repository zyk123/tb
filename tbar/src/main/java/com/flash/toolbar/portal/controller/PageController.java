package com.flash.toolbar.portal.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.flash.common.log.BusinessLogger;
import com.flash.common.util.*;
import com.flash.toolbar.portal.bean.HdPrizeShipUnion;
import com.flash.toolbar.portal.bean.PackageBean;
import com.flash.toolbar.portal.bean.ServiceUrlBean;
import com.flash.toolbar.service.SessionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping(value="/page")
public class PageController {
	@Autowired
	private ServiceUrlBean serviceUrlBean;
	
	@Autowired
	private SessionContext sessionContext;
	
	/**
	 * 跳转到usage。jsp
	 * @param response
	 * @param request
	 * @param suid
	 * @return
	 */
	@RequestMapping(value="/turnUsage")
	@ResponseBody
	public ModelAndView turnUsage(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "suid",defaultValue = "") String suid){			
		Map<String,String> map = new HashMap<String,String>();
		ModelAndView modelAndView = new ModelAndView();
		if(StringUtil.isNotNullOrEmpty(suid)){			
			ToolbarSession session = sessionContext.getSession(suid);
			String tOperatorId = session.gettOperatorId();
	        String userId = session.getMemberId();
	        String countryNo = session.getCountryNo();
	        String timeZone = session.getTimeZone();
	        
			map.put("suid", suid);
			map.put("tOperatorId", tOperatorId);
			map.put("userId", userId);
			map.put("countryNo", countryNo);
			map.put("timeZone", timeZone);
			if("Prepaid".equals(session.getServiceType())){
				modelAndView = new ModelAndView("toolbar/preHome",map);
			}else{
				modelAndView = new ModelAndView("toolbar/postHome",map);
			}
		}
		return modelAndView;
	}
	
	/**
	 * 跳转到设置页
	 * @param response
	 * @param request
	 * @param suid
	 * @return
	 */
	@RequestMapping(value="/turnSetting")
	@ResponseBody
	public ModelAndView turnSetting(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "suid",defaultValue = "") String suid
			){
		ToolbarSession session = sessionContext.getSession(suid);
		String tOperatorId = session.gettOperatorId();
        String userId = session.getMemberId();
        String timeZone = session.getTimeZone();
        String language = session.getLanguage();
        String countryNo = session.getCountryNo();
//        String openToolbar = (String) session.getAttribute("openToolbar");//??--zhy
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("tOperatorId", tOperatorId);
		map.put("userId", userId);
		map.put("timeZone", timeZone);
		map.put("language", language);
//		map.put("openToolbar", openToolbar);
		map.put("countryNo", countryNo);
		map.put("suid", suid);
		ModelAndView modelAndView = new ModelAndView("toolbar/settings",map);
		return modelAndView;
	}
	
	@RequestMapping(value="/turnPreLucky")
	@ResponseBody
	public ModelAndView turnPreLucky(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "suid",defaultValue = "") String suid,
			@RequestParam(value = "promotionId",defaultValue = "") String promotionId
			){
		ToolbarSession session = sessionContext.getSession(suid);
		String tOperatorId = session.gettOperatorId();
		String userId = session.getMemberId();
		String timeZone = session.getTimeZone();
		String language = session.getLanguage();
		String countryNo = session.getCountryNo();
//        String openToolbar = (String) session.getAttribute("openToolbar");//??--zhy
		String requestSerial = RandomString.getRandomNumber();
		StringBuilder sb = new StringBuilder();
		sb.append("memberId=").append(userId);
		sb.append("&tOperatorId=").append(tOperatorId);
		sb.append("&countryNo=").append(countryNo);
		sb.append("&requestSerial=").append(requestSerial);	
		sb.append("&promotionId=").append(promotionId);	
		String responseUserStr = HttpUtil.sendPost(serviceUrlBean.getGetReceiverInfo(), sb.toString(),requestSerial);
		String receiveraddress = "";
		String receiverid = "";
		if(StringUtil.isNotNullOrEmpty(responseUserStr)){
			JSONObject obj = JSONObject.parseObject(responseUserStr);
			if(obj!=null){
				JSONObject objBody = obj.getJSONObject("body");
				String  resultCode = obj.getString("resultCode");
				if(StringUtil.isNotNullOrEmpty(resultCode) && objBody!=null && "0".equals(resultCode)){
					String retCode = objBody.getString("retCode");
					if(StringUtil.isNotNullOrEmpty(retCode) && "0".equals(retCode) ){
						receiveraddress = objBody.getString("receiveraddress");
						receiverid = objBody.getString("receiverid");
					}
				}
			}
		}
		String responseUserStr1 = HttpUtil.sendPost(serviceUrlBean.getRequireRestOnedaytimes(), sb.toString(),requestSerial);
		String restOnedaytimes = "";
		if(StringUtil.isNotNullOrEmpty(responseUserStr1)){
			JSONObject obj = JSONObject.parseObject(responseUserStr1);
			if(obj!=null){
				JSONObject objBody = obj.getJSONObject("body");
				String  resultCode = obj.getString("resultCode");
				if(StringUtil.isNotNullOrEmpty(resultCode) && objBody!=null && "0".equals(resultCode)){
					String retCode = objBody.getString("retCode");
					if(StringUtil.isNotNullOrEmpty(retCode) && "0".equals(retCode) ){
						restOnedaytimes = objBody.getString("restOnedaytimes");
					}
				}
			}
		}
		
		
		StringBuilder sb1 = new StringBuilder();
		sb1.append("promotionId=").append(promotionId);
		sb1.append("&tOperatorId=").append(tOperatorId);
		sb1.append("&countryNo=").append(countryNo);
		sb1.append("&requestSerial=").append(requestSerial);			
		sb1.append("&startNum=").append("1");			
		sb1.append("&endNum=").append("10");			
		String responseStr = HttpUtil.sendPost(serviceUrlBean.getGetLuckyList(), sb1.toString(),requestSerial);
		String record = "";
		
		if(StringUtil.isNotNullOrEmpty(responseStr)){
			JSONObject obj = JSONObject.parseObject(responseStr);
			String resultCode = obj.getString("resultCode");
			if("0".equals(resultCode)){
				JSONObject body = obj.getJSONObject("body");
				record = body.getString("record");
			}
		}
		
		List<HdPrizeShipUnion> list = new ArrayList<HdPrizeShipUnion>();
		
		if(StringUtil.isNotNullOrEmpty(record)){
			list = JSONArray.parseArray(record, HdPrizeShipUnion.class);
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("receiverAddress", receiveraddress);
		map.put("receiverid", receiverid);
		map.put("restOnedaytimes", restOnedaytimes);
		map.put("tOperatorId", tOperatorId);
		map.put("userId", userId);
		map.put("timeZone", timeZone);
		map.put("language", language);
//		map.put("openToolbar", openToolbar);
		map.put("countryNo", countryNo);
		map.put("suid", suid);
		map.put("promotionId", promotionId);
		map.put("record", list);
		
		ModelAndView modelAndView = new ModelAndView("toolbar/preLuckydraw",map);
		return modelAndView;
	}
	
	@RequestMapping(value="/turnPostLucky")
	@ResponseBody
	public ModelAndView turnPostLucky(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "suid",defaultValue = "") String suid,
			@RequestParam(value = "promotionId",defaultValue = "") String promotionId
			){
		ToolbarSession session = sessionContext.getSession(suid);
		String tOperatorId = session.gettOperatorId();
		String userId = session.getMemberId();
		String timeZone = session.getTimeZone();
		String language = session.getLanguage();
		String countryNo = session.getCountryNo();
//        String openToolbar = (String) session.getAttribute("openToolbar");//??--zhy
		String requestSerial = RandomString.getRandomNumber();
		StringBuilder sb = new StringBuilder();
		sb.append("memberId=").append(userId);
		sb.append("&tOperatorId=").append(tOperatorId);
		sb.append("&countryNo=").append(countryNo);
		sb.append("&requestSerial=").append(requestSerial);	
		sb.append("&promotionId=").append(promotionId);	
		String responseUserStr = HttpUtil.sendPost(serviceUrlBean.getGetReceiverInfo(), sb.toString(),requestSerial);
		String receiveraddress = "";
		String receiverid = "";
		if(StringUtil.isNotNullOrEmpty(responseUserStr)){
			JSONObject obj = JSONObject.parseObject(responseUserStr);
			if(obj!=null){
				JSONObject objBody = obj.getJSONObject("body");
				String  resultCode = obj.getString("resultCode");
				if(StringUtil.isNotNullOrEmpty(resultCode) && objBody!=null && "0".equals(resultCode)){
					String retCode = objBody.getString("retCode");
					if(StringUtil.isNotNullOrEmpty(retCode) && "0".equals(retCode) ){
						receiveraddress = objBody.getString("receiveraddress");
						receiverid = objBody.getString("receiverid");
					}
				}
			}
		}
		String responseUserStr1 = HttpUtil.sendPost(serviceUrlBean.getRequireRestOnedaytimes(), sb.toString(),requestSerial);
		String restOnedaytimes = "";
		if(StringUtil.isNotNullOrEmpty(responseUserStr1)){
			JSONObject obj = JSONObject.parseObject(responseUserStr1);
			if(obj!=null){
				JSONObject objBody = obj.getJSONObject("body");
				String  resultCode = obj.getString("resultCode");
				if(StringUtil.isNotNullOrEmpty(resultCode) && objBody!=null && "0".equals(resultCode)){
					String retCode = objBody.getString("retCode");
					if(StringUtil.isNotNullOrEmpty(retCode) && "0".equals(retCode) ){
						restOnedaytimes = objBody.getString("restOnedaytimes");
					}
				}
			}
		}
		
		StringBuilder sb1 = new StringBuilder();
		sb1.append("promotionId=").append(promotionId);
		sb1.append("&tOperatorId=").append(tOperatorId);
		sb1.append("&countryNo=").append(countryNo);
		sb1.append("&requestSerial=").append(requestSerial);			
		sb1.append("&startNum=").append("1");			
		sb1.append("&endNum=").append("10");			
		String responseStr = HttpUtil.sendPost(serviceUrlBean.getGetLuckyList(), sb1.toString(),requestSerial);
		String record = "";
		
		if(StringUtil.isNotNullOrEmpty(responseStr)){
			JSONObject obj = JSONObject.parseObject(responseStr);
			String resultCode = obj.getString("resultCode");
			if("0".equals(resultCode)){
				JSONObject body = obj.getJSONObject("body");
				record = body.getString("record");
			}
		}
		
        List<HdPrizeShipUnion> list = new ArrayList<HdPrizeShipUnion>();
		
		if(StringUtil.isNotNullOrEmpty(record)){
			list = JSONArray.parseArray(record, HdPrizeShipUnion.class);
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("receiverAddress", receiveraddress);
		map.put("receiverid", receiverid);
		map.put("restOnedaytimes", restOnedaytimes);
		map.put("tOperatorId", tOperatorId);
		map.put("userId", userId);
		map.put("timeZone", timeZone);
		map.put("language", language);
//		map.put("openToolbar", openToolbar);
		map.put("countryNo", countryNo);
		map.put("suid", suid);
		map.put("promotionId", promotionId);
		map.put("record", list);
		ModelAndView modelAndView = new ModelAndView("toolbar/postLuckydraw",map);
		return modelAndView;
	}
	
	@RequestMapping(value="/turnReload")
	@ResponseBody
	public ModelAndView turnReload(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "suid",defaultValue = "") String suid,
			@RequestParam(value = "flag",defaultValue = "") String flag,
			@RequestParam(value = "flagamount",defaultValue = "") String flagamount
			){
		ToolbarSession session = sessionContext.getSession(suid);
		String tOperatorId = session.gettOperatorId();
		String userId = session.getMemberId();
		String timeZone = session.getTimeZone();
		String language = session.getLanguage();
		String countryNo = session.getCountryNo();
		String msisdn = session.getPhoneNum();
		String serviceType = session.getServiceType();
//        String openToolbar = (String) session.getAttribute("openToolbar");//??--zhy
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("tOperatorId", tOperatorId);
		map.put("userId", userId);
		map.put("timeZone", timeZone);
		map.put("language", language);
//		map.put("openToolbar", openToolbar);
		map.put("countryNo", countryNo);
		map.put("suid", suid);
		map.put("msisdn", msisdn);
		map.put("serviceType", serviceType);
		map.put("flag", flag);
		map.put("flagamount", flagamount);
		ModelAndView modelAndView = null;
		if("Prepaid".equals(serviceType)){
			modelAndView = new ModelAndView("toolbar/preReload",map);
		}else{
			modelAndView = new ModelAndView("toolbar/postReload",map);
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/turnPreAccount")
	@ResponseBody
	public ModelAndView turnPreAccount(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "suid",defaultValue = "") String suid
			){
		ToolbarSession session = sessionContext.getSession(suid);
		String tOperatorId = session.gettOperatorId();
		String userId = session.getMemberId();
		String timeZone = session.getTimeZone();
		String language = session.getLanguage();
		String countryNo = session.getCountryNo();
		String phoneNum = session.getPhoneNum();
		phoneNum = phoneNum.startsWith("6")?phoneNum.substring(1):phoneNum;			
//        String openToolbar = (String) session.getAttribute("openToolbar");//??--zhy
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("tOperatorId", tOperatorId);
		map.put("userId", userId);
		map.put("timeZone", timeZone);
		map.put("language", language);
//		map.put("openToolbar", openToolbar);
		map.put("countryNo", countryNo);
		map.put("phoneNum", phoneNum);
		map.put("suid", suid);
		ModelAndView modelAndView = new ModelAndView("toolbar/preAccount",map);
		return modelAndView;
	}
	@RequestMapping(value="/turnPostAccount")
	@ResponseBody
	public ModelAndView turnPostAccount(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "suid",defaultValue = "") String suid
			){
		ToolbarSession session = sessionContext.getSession(suid);
		String tOperatorId = session.gettOperatorId();
		String userId = session.getMemberId();
		String timeZone = session.getTimeZone();
		String language = session.getLanguage();
		String countryNo = session.getCountryNo();
		String phoneNum = session.getPhoneNum();
		phoneNum = phoneNum.startsWith("6")?phoneNum.substring(1):phoneNum;				
//        String openToolbar = (String) session.getAttribute("openToolbar");//??--zhy
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("tOperatorId", tOperatorId);
		map.put("userId", userId);
		map.put("timeZone", timeZone);
		map.put("language", language);
//		map.put("openToolbar", openToolbar);
		map.put("countryNo", countryNo);
		map.put("phoneNum", phoneNum);
		map.put("suid", suid);
		ModelAndView modelAndView = new ModelAndView("toolbar/postAccount",map);
		return modelAndView;
	}
	
	@RequestMapping(value="/turnPackageDetailOrder")
	@ResponseBody
	public ModelAndView turnPackageDetailOrder(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "suid",defaultValue = "") String suid,
			@RequestParam(value = "isShowbuy",defaultValue = "") String isShowbuy,
			PackageBean packageBean
			){
		Map<String,Object> map = new HashMap<String,Object>();
		ModelAndView modelAndView = null;
		try {
			if(StringUtil.isNotNullOrEmpty(suid)){		
				String requestSerial = RandomString.getRandomNumber();
				
				ToolbarSession session = sessionContext.getSession(suid);
				String tOperatorId = session.gettOperatorId();
		        String userId = session.getMemberId();
		        String timeZone = session.getTimeZone();
		        String language = session.getLanguage();
		        String countryNo = session.getCountryNo();
				String phoneNum = session.getPhoneNum();
				 String serviceType = session.getServiceType();
				phoneNum = phoneNum.startsWith("6")?phoneNum.substring(1):phoneNum;		        
			    
//			    map.put("packageBean", packageBean);
//				map.put("suid", suid);
//				map.put("tOperatorId", tOperatorId);
//				map.put("userId", userId);
//				map.put("countryNo", countryNo);
			    
				StringBuilder sb = new StringBuilder();
				sb.append("memberId=").append(userId);
				sb.append("&tOperatorId=").append(tOperatorId);
				sb.append("&countryNo=").append(countryNo);
				sb.append("&requestSerial=");
			    
			    map.put("packageBean", packageBean);
				map.put("suid", suid);
				map.put("tOperatorId", tOperatorId);
				map.put("userId", userId);
				map.put("countryNo", countryNo);
				map.put("phoneNum", phoneNum);
				
				String balance = "0"; 
				String responseStr = HttpUtil.sendPost(serviceUrlBean.getQueryUserFlow(), sb.toString(),requestSerial);
				if(StringUtil.isNotNullOrEmpty(responseStr)){
					JSONObject obj = JSONObject.parseObject(responseStr);
					if(obj!=null){
						JSONObject objBody = obj.getJSONObject("body");
						if(objBody!=null){
							String balanceTemp = objBody.getString("balance");
							if(StringUtil.isNotNullOrEmpty(balanceTemp)){
								balance = balanceTemp;
							}
						}
					}
				}
				map.put("balance", Float.valueOf(balance));
				String amoutFloat = "0";
				if(StringUtil.isNotNullOrEmpty(packageBean.getAmount())){
					amoutFloat = packageBean.getAmount();
				}
				boolean flag = true;
				if(Float.valueOf(balance)>=Float.valueOf(amoutFloat)){
					flag = false;
				}
				map.put("flag", flag);		
				
				//获取套餐详情
				StringBuilder builder = new StringBuilder();
				builder.append("packageId=").append(packageBean.getPackageid());
				builder.append("&requestSerial=").append(requestSerial);
				
				BusinessLogger.LoggerInfo(requestSerial, "tbar", "getPackageDetail", true, builder.toString(), String.valueOf(new Date().getTime()));				
				String detailReturn = HttpUtil.sendPost(serviceUrlBean.getPackageDetail(), builder.toString(),requestSerial);
				BusinessLogger.LoggerInfo(requestSerial, "tbar", "getPackageDetail", false, detailReturn, String.valueOf(new Date().getTime()));
				if(StringUtil.isNotNullOrEmpty(detailReturn)){
					map.put("details", JSONArray.parseArray(detailReturn));
				}
				else{
					map.put("details", "");
				}
				
				map.put("isShowbuy",isShowbuy);
				
				if("Prepaid".equals(serviceType)){
					modelAndView = new ModelAndView("toolbar/prePackageDetailOrder",map);
				}else{
					modelAndView = new ModelAndView("toolbar/postPackageDetailOrder",map);
				}
			}

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return modelAndView;
	}
	
	@RequestMapping(value="/turnReloadPackageDetailOrder")
	@ResponseBody
	public ModelAndView turnReloadPackageDetailOrder(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "suid",defaultValue = "") String suid,
			@RequestParam(value = "isShowbuy",defaultValue = "") String isShowbuy,
			@RequestParam(value = "flagamount",defaultValue = "") String flagamount,
			PackageBean packageBean
			){
		Map<String,Object> map = new HashMap<String,Object>();
		ModelAndView modelAndView = null;
		try {
			if(StringUtil.isNotNullOrEmpty(suid)){		
				String requestSerial = RandomString.getRandomNumber();
				
				ToolbarSession session = sessionContext.getSession(suid);
				String tOperatorId = session.gettOperatorId();
				String userId = session.getMemberId();
				String timeZone = session.getTimeZone();
				String language = session.getLanguage();
				String countryNo = session.getCountryNo();
				String phoneNum = session.getPhoneNum();
				String serviceType = session.getServiceType();
				phoneNum = phoneNum.startsWith("6")?phoneNum.substring(1):phoneNum;		        
				
//			    map.put("packageBean", packageBean);
//				map.put("suid", suid);
//				map.put("tOperatorId", tOperatorId);
//				map.put("userId", userId);
//				map.put("countryNo", countryNo);
				
				StringBuilder sb = new StringBuilder();
				sb.append("memberId=").append(userId);
				sb.append("&tOperatorId=").append(tOperatorId);
				sb.append("&countryNo=").append(countryNo);
				sb.append("&requestSerial=");
				
				map.put("packageBean", packageBean);
				map.put("suid", suid);
				map.put("tOperatorId", tOperatorId);
				map.put("userId", userId);
				map.put("countryNo", countryNo);
				map.put("phoneNum", phoneNum);
				
				String balance = "0"; 
				String responseStr = HttpUtil.sendPost(serviceUrlBean.getQueryUserFlow(), sb.toString(),requestSerial);
				if(StringUtil.isNotNullOrEmpty(responseStr)){
					JSONObject obj = JSONObject.parseObject(responseStr);
					if(obj!=null){
						JSONObject objBody = obj.getJSONObject("body");
						if(objBody!=null){
							String balanceTemp = objBody.getString("balance");
							if(StringUtil.isNotNullOrEmpty(balanceTemp)){
								balance = balanceTemp;
							}
						}
					}
				}
				map.put("balance", Float.valueOf(balance));
				String amoutFloat = "0";
				if(StringUtil.isNotNullOrEmpty(packageBean.getAmount())){
					amoutFloat = packageBean.getAmount();
				}
				boolean flag = true;
				if(Float.valueOf(balance)>Float.valueOf(amoutFloat)){
					flag = false;
				}
				map.put("flag", flag);		
				
				//获取套餐详情
				StringBuilder builder = new StringBuilder();
				builder.append("packageId=").append(packageBean.getPackageid());
				builder.append("&requestSerial=").append(requestSerial);
				
				BusinessLogger.LoggerInfo(requestSerial, "tbar", "getPackageDetail", true, builder.toString(), String.valueOf(new Date().getTime()));				
				String detailReturn = HttpUtil.sendPost(serviceUrlBean.getPackageDetail(), builder.toString(),requestSerial);
				BusinessLogger.LoggerInfo(requestSerial, "tbar", "getPackageDetail", false, detailReturn, String.valueOf(new Date().getTime()));
				if(StringUtil.isNotNullOrEmpty(detailReturn)){
					map.put("details", JSONArray.parseArray(detailReturn));
				}
				else{
					map.put("details", "");
				}
				
				map.put("isShowbuy",isShowbuy);
				map.put("flagamount",flagamount);
				
				if("Prepaid".equals(serviceType)){
					modelAndView = new ModelAndView("toolbar/preReloadPackageDetailOrder",map);
				}else{
					modelAndView = new ModelAndView("toolbar/postReloadPackageDetailOrder",map);
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return modelAndView;
	}	
	
	
	/**
	 * 跳转到商城页面
	 * */
	@RequestMapping(value="/turnStore")
	@ResponseBody
	public ModelAndView turnStore(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "suid",defaultValue = "") String suid){
		ModelAndView mav = null;
		Map<String, String> map = new HashMap<String,String>();
		try {
			map = new HashMap<String,String>();
			if(StringUtil.isNotNullOrEmpty(suid)){
				String requestSerial = RandomString.getRandomNumber();
				
				ToolbarSession session = sessionContext.getSession(suid);
				String tOperatorId = session.gettOperatorId();
			    String countryNo = session.getCountryNo();
		        String userId = session.getMemberId();
		        String serviceType = session.getServiceType();
				StringBuilder sb = new StringBuilder();
				sb.append("memberId=").append(userId);
				sb.append("&tOperatorId=").append(tOperatorId);
				sb.append("&countryNo=").append(countryNo);
				sb.append("&requestSerial=");
				String remainedData = "";
				String totalData = "";
				String lastUpdate = "";
				String validity = "";
				String responseStr = HttpUtil.sendPost(serviceUrlBean.getQueryUserFlow(), sb.toString(),requestSerial);
				if(StringUtil.isNotNullOrEmpty(responseStr)){
					JSONObject obj = JSONObject.parseObject(responseStr);
					if(obj!=null){
						JSONObject objBody = obj.getJSONObject("body");
						String  resultCode = obj.getString("resultCode");
						if(StringUtil.isNotNullOrEmpty(resultCode) && objBody!=null && "0".equals(resultCode)){
							String retCode = objBody.getString("retCode");
							if(StringUtil.isNotNullOrEmpty(retCode) && "0".equals(retCode) ){
							    String remainTemp = objBody.getString("remainedData");
								String totalTemp = objBody.getString("totalData");
								if(StringUtil.isNotNullOrEmpty(remainTemp) && StringUtil.isNotNullOrEmpty(totalTemp)){
									remainedData =  new BigDecimal(remainTemp).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
									totalData = new BigDecimal(totalTemp).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
									String lastOn2 = objBody.getString("lastOn2");
									if(StringUtil.isNotNullOrEmpty(lastOn2)){
										lastUpdate = lastOn2;
									}else{
										lastUpdate = objBody.getString("lastOn");
									}
									validity = objBody.getString("validity2");
								}
							}
						}
					}
				}			    
			    
				map.put("suid", suid);
				map.put("tOperatorId", tOperatorId);
				map.put("countryNo", countryNo);
				map.put("remainedData", remainedData);//TODO TEST
				map.put("totalData", totalData);//TODO TEST
				map.put("lastUpdate", lastUpdate);
				map.put("validity", validity);
				map.put("serviceType", serviceType);
				if("Prepaid".equals(serviceType)){
					mav = new ModelAndView("toolbar/preStore",map);
				}else{
					mav = new ModelAndView("toolbar/postStore",map);
				}
						
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}
	
	@RequestMapping(value="/turnFeedback")
	@ResponseBody
	public ModelAndView turnFeedback(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "suid",defaultValue = "") String suid){
		Map<String,String> map = new HashMap<String,String>();
		ModelAndView mav = null;
		if(StringUtil.isNotNullOrEmpty(suid)){			
			ToolbarSession session = sessionContext.getSession(suid);
			String tOperatorId = session.gettOperatorId();
	        String countryNo = session.getCountryNo();
	        String serviceType = session.getServiceType();
	        
			map.put("suid", suid);
			map.put("tOperatorId", tOperatorId);
			map.put("countryNo", countryNo);
			map.put("serviceType", serviceType);
			
			if("Prepaid".equals(serviceType)){
				mav = new ModelAndView("toolbar/feedback",map);
			}else{
				mav = new ModelAndView("toolbar/postFeedback",map);
			}
		}
		

		return mav;		
	}
	
	
	@RequestMapping(value="/turnAbout")
	@ResponseBody
	public ModelAndView turnAbout(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "suid",defaultValue = "") String suid){
		Map<String,String> map = new HashMap<String,String>();
		map.put("suid", suid);
		ModelAndView mav = new ModelAndView("toolbar/about",map);
		return mav;		
	}
	
	
	@RequestMapping(value="/turnOrderFail")
	@ResponseBody
	public ModelAndView turnOrderFail(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "suid",defaultValue = "") String suid,
			@RequestParam(value = "retMsg",defaultValue = "") String retMsg,
			PackageBean packageBean){
		Map<String,Object> map = new HashMap<String,Object>();
		ModelAndView mav = null;
		if(StringUtil.isNotNullOrEmpty(suid)){
			ToolbarSession session = sessionContext.getSession(suid);
			String serviceType = session.getServiceType();
			map.put("suid", suid);
			map.put("retMsg", retMsg);
			map.put("packageBean", packageBean);
			if("Prepaid".equals(serviceType)){
				mav = new ModelAndView("toolbar/preOrderFailed",map);
			}else{
				mav = new ModelAndView("toolbar/postOrderFailed",map);
			}			
		}

		return mav;		
	}
	
	
	@RequestMapping(value="/turnOrderSuccess")
	@ResponseBody
	public ModelAndView turnOrderSuccess(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "suid",defaultValue = "") String suid,
			@RequestParam(value = "expireDate",defaultValue = "") String expireDate,
			PackageBean packageBean){
		ToolbarSession session = sessionContext.getSession(suid);
		String phoneNum = session.getPhoneNum();
		String serviceType = session.getServiceType();
		phoneNum = phoneNum.startsWith("6")?phoneNum.substring(1):phoneNum;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("suid", suid);
		map.put("packageBean", packageBean);
		map.put("phoneNum", phoneNum);
		String time = "";
		if(!"".equals(expireDate)){
			try {
				Date date = DateTimeUtil.getDateFromDateString(expireDate,"yyyyMMddHHmmss");
				String milStr = String.valueOf(date.getTime());
				String week = DateTimeUtil.getWeekOfDate2(milStr);
				String day = DateTimeUtil.getDateTime(date, "dd");
				String month = DateTimeUtil.getMonthOfDate(milStr);
				String year = DateTimeUtil.getDateTime(date, "yyyy");
				time = week + ", " + day + " " + month + " " + year;//Fri,15 Nov,2016
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		map.put("expireDate", time);
		
		ModelAndView mav = null;
		if("Prepaid".equals(serviceType)){
			mav = new ModelAndView("toolbar/preOrderSuccess",map);
		}else{
			mav = new ModelAndView("toolbar/postOrderSuccess",map);
		}	
		return mav;		
	}
	
	@RequestMapping(value="turnGameList")
	public ModelAndView turnGameList(HttpServletRequest request,
			@RequestParam(value = "suid",defaultValue = "") String suid,
			@RequestParam(value = "useragent",defaultValue = "") String useragent,
			@RequestParam(value = "prefix",defaultValue = "") String prefix){
		Map<String,Object> map = new HashMap<String,Object>();
		ModelAndView mav = null;
			try {
				if(StringUtil.isNotNullOrEmpty(suid)){
					String requestSerial = RandomString.getRandomNumber();
					ToolbarSession session = sessionContext.getSession(suid);
					String tOperatorId = session.gettOperatorId();
				    String countryNo = session.getCountryNo();
					map = new HashMap<String,Object>();
					String type = Constant.GAME_TYPE_ANDROID;
					if(useragent.toLowerCase().contains("ios")){
						type = Constant.GAME_TYPE_IOS;
					}
					map.put("suid", suid);
					StringBuilder sb = new StringBuilder();
					sb.append("tOperatorId=").append(tOperatorId);
					sb.append("&countryNo=").append(countryNo);
					sb.append("&requestSerial=").append(requestSerial);			
					sb.append("&type=").append(type);			
					String responseStr = HttpUtil.sendPost(serviceUrlBean.getQueryGameList(), sb.toString(),requestSerial);
					if(StringUtil.isNotNullOrEmpty(responseStr)){
						JSONObject obj = JSONObject.parseObject(responseStr);
						if(obj!=null){
							JSONObject objBody = obj.getJSONObject("body");
							String  resultCode = obj.getString("resultCode");
							JSONObject listObj = objBody.getJSONObject("list");
							
							if(StringUtil.isNotNullOrEmpty(resultCode) && objBody!=null && "0".equals(resultCode)){
								String retCode = listObj.getString("retCode");
								if(StringUtil.isNotNullOrEmpty(retCode) && "0".equals(retCode) ){
									map.put("list",listObj.getJSONArray("result"));
								}
							}
						}
					}					
					mav = new ModelAndView("toolbar/" +  prefix + "GameList",map);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		return mav;			
	}
	
	@RequestMapping(value="turnNavigation")
	public ModelAndView turnNavigation(HttpServletRequest request,
			@RequestParam(value = "suid",defaultValue = "") String suid,
			@RequestParam(value = "prefix",defaultValue = "") String prefix){
		Map<String,Object> map = new HashMap<String,Object>();
		ModelAndView mav = null;
			try {
				if(StringUtil.isNotNullOrEmpty(suid)){
					String requestSerial = RandomString.getRandomNumber();
					ToolbarSession session = sessionContext.getSession(suid);
					String tOperatorId = session.gettOperatorId();
				    String countryNo = session.getCountryNo();
					map = new HashMap<String,Object>();
					map.put("suid", suid);
					StringBuilder sb = new StringBuilder();
					sb.append("tOperatorId=").append(tOperatorId);
					sb.append("&countryNo=").append(countryNo);
					sb.append("&requestSerial=").append(requestSerial);			
					String responseStr = HttpUtil.sendPost(serviceUrlBean.getQueryWebsiteList(), sb.toString(),requestSerial);
					if(StringUtil.isNotNullOrEmpty(responseStr)){
						JSONObject obj = JSONObject.parseObject(responseStr);
						if(obj!=null){
							JSONObject objBody = obj.getJSONObject("body");
							String  resultCode = obj.getString("resultCode");
							JSONObject listObj = objBody.getJSONObject("list");
							
							if(StringUtil.isNotNullOrEmpty(resultCode) && objBody!=null && "0".equals(resultCode)){
								String retCode = listObj.getString("retCode");
								if(StringUtil.isNotNullOrEmpty(retCode) && "0".equals(retCode) ){
									map.put("list1",listObj.getJSONArray("result1"));
									map.put("list2",listObj.getJSONArray("result2"));
								}
							}
						}
					}					
					mav = new ModelAndView("toolbar/" +  prefix + "Navigation",map);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		return mav;			
	}
	
	@RequestMapping(value="turnPromotion")
	public ModelAndView turnPromotion(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "suid",defaultValue = "") String suid){
		Map<String,Object> map = new HashMap<String,Object>();
		ModelAndView mav = null;
			try {
				if(StringUtil.isNotNullOrEmpty(suid)){
					String requestSerial = RandomString.getRandomNumber();
					ToolbarSession session = sessionContext.getSession(suid);
					String tOperatorId = session.gettOperatorId();
				    String countryNo = session.getCountryNo();
			        String userId = session.getMemberId();
			        String serviceType = session.getServiceType();
					map = new HashMap<String,Object>();
					map.put("suid", suid);
					StringBuilder sb = new StringBuilder();
					sb.append("memberId=").append(userId);
					sb.append("&tOperatorId=").append(tOperatorId);
					sb.append("&countryNo=").append(countryNo);
					sb.append("&requestSerial=").append(requestSerial);			
					String responseStr = HttpUtil.sendPost(serviceUrlBean.getGetPromotionList(), sb.toString(),requestSerial);
					if(StringUtil.isNotNullOrEmpty(responseStr)){
						JSONObject obj = JSONObject.parseObject(responseStr);
						if(obj!=null){
							JSONObject objBody = obj.getJSONObject("body");
							String  resultCode = obj.getString("resultCode");
							if(StringUtil.isNotNullOrEmpty(resultCode) && objBody!=null && "0".equals(resultCode)){
								String retCode = objBody.getString("retCode");
								if(StringUtil.isNotNullOrEmpty(retCode) && "0".equals(retCode) ){
									map.put("list",objBody.getJSONArray("list"));
								}

							}
						}
					}					
					if("Prepaid".equals(serviceType)){
						mav = new ModelAndView("toolbar/prePromotion",map);
					}else{
						mav = new ModelAndView("toolbar/postPromotion",map);
					}					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		return mav;	
	}
	
	@RequestMapping(value="turnRewardInfo")
	public ModelAndView turnRewardInfo(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "suid",defaultValue = "") String suid,
			@RequestParam(value = "receiverid",defaultValue = "") String receiverid){
		Map<String,Object> map = new HashMap<String,Object>();
		ModelAndView mav = null;
		try {
			if(StringUtil.isNotNullOrEmpty(suid)){
				String requestSerial = RandomString.getRandomNumber();
				ToolbarSession session = sessionContext.getSession(suid);
				String serviceType = session.getServiceType();
				String memberId = session.getMemberId();
				String tOperatorId = session.gettOperatorId();
			    String countryNo = session.getCountryNo();
				String phoneNum = session.getPhoneNum();
				phoneNum = phoneNum.startsWith("6")?phoneNum.substring(1):phoneNum;	
				StringBuilder sb = new StringBuilder();
				sb.append("memberId=").append(memberId);
				sb.append("&tOperatorId=").append(tOperatorId);
				sb.append("&countryNo=").append(countryNo);
				sb.append("&requestSerial=").append(requestSerial);	    
				String name = "";
				String gender = "";
				String id = "";
				String email = "";
				String address = "";
				map.put("suid", suid);
				map.put("phoneNum", phoneNum);
				if(!"".equals(receiverid)){
					String responseUserStr = HttpUtil.sendPost(serviceUrlBean.getGetReceiverInfo(), sb.toString(),requestSerial);
					if(StringUtil.isNotNullOrEmpty(responseUserStr)){
						JSONObject obj = JSONObject.parseObject(responseUserStr);
						if(obj!=null){
							JSONObject objBody = obj.getJSONObject("body");
							String  resultCode = obj.getString("resultCode");
							if(StringUtil.isNotNullOrEmpty(resultCode) && objBody!=null && "0".equals(resultCode)){
								String retCode = objBody.getString("retCode");
								if(StringUtil.isNotNullOrEmpty(retCode) && "0".equals(retCode) ){
									name = objBody.getString("receivername");
									gender = objBody.getString("gender");
									id = objBody.getString("identitycard");
									email = objBody.getString("email");
									address = objBody.getString("receiveraddress");
								}
							}
						}
					}
				}
				map.put("name", name);
				map.put("gender", gender);
				map.put("id", id);
				map.put("email", email);
				map.put("address", address);
				map.put("receiverid", receiverid);
				if("Prepaid".equals(serviceType)){
					mav = new ModelAndView("toolbar/preReceiverInfo",map);
				}else{
					mav = new ModelAndView("toolbar/postReceiverInfo",map);
				}	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;	
	}
	@RequestMapping(value="turnLotteryRewardInfo")
	public ModelAndView turnLotteryRewardInfo(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "suid",defaultValue = "") String suid,
			@RequestParam(value = "receiverid",defaultValue = "") String receiverid){
		Map<String,Object> map = new HashMap<String,Object>();
		ModelAndView mav = null;
		try {
			if(StringUtil.isNotNullOrEmpty(suid)){
				String requestSerial = RandomString.getRandomNumber();
				ToolbarSession session = sessionContext.getSession(suid);
				String serviceType = session.getServiceType();
				String memberId = session.getMemberId();
				String tOperatorId = session.gettOperatorId();
				String countryNo = session.getCountryNo();
				StringBuilder sb = new StringBuilder();
				sb.append("memberId=").append(memberId);
				sb.append("&tOperatorId=").append(tOperatorId);
				sb.append("&countryNo=").append(countryNo);
				sb.append("&requestSerial=").append(requestSerial);	    
				String name = "";
				String gender = "";
				String id = "";
				String email = "";
				String address = "";
				map.put("suid", suid);
				if(!"".equals(receiverid)){
					String responseUserStr = HttpUtil.sendPost(serviceUrlBean.getGetReceiverInfo(), sb.toString(),requestSerial);
					if(StringUtil.isNotNullOrEmpty(responseUserStr)){
						JSONObject obj = JSONObject.parseObject(responseUserStr);
						if(obj!=null){
							JSONObject objBody = obj.getJSONObject("body");
							String  resultCode = obj.getString("resultCode");
							if(StringUtil.isNotNullOrEmpty(resultCode) && objBody!=null && "0".equals(resultCode)){
								String retCode = objBody.getString("retCode");
								if(StringUtil.isNotNullOrEmpty(retCode) && "0".equals(retCode) ){
									name = objBody.getString("receivername");
									gender = objBody.getString("gender");
									id = objBody.getString("identitycard");
									email = objBody.getString("email");
									address = objBody.getString("receiveraddress");
								}
							}
						}
					}
				}
				map.put("name", name);
				map.put("gender", gender);
				map.put("id", id);
				map.put("email", email);
				map.put("address", address);
				map.put("receiverid", receiverid);
				if("Prepaid".equals(serviceType)){
					mav = new ModelAndView("toolbar/preLotteryRewardinformation",map);
				}else{
					mav = new ModelAndView("toolbar/postLotteryRewardinformation",map);
				}	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;	
	}
	@RequestMapping(value="turnLotteryCongratulations")
	public ModelAndView turnLotteryCongratulations(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "suid",defaultValue = "") String suid,
			@RequestParam(value = "receiverid",defaultValue = "") String receiverid){
		Map<String,Object> map = new HashMap<String,Object>();
		ModelAndView mav = null;
		try {
			if(StringUtil.isNotNullOrEmpty(suid)){
				String requestSerial = RandomString.getRandomNumber();
				ToolbarSession session = sessionContext.getSession(suid);
				String serviceType = session.getServiceType();
				String memberId = session.getMemberId();
				String tOperatorId = session.gettOperatorId();
				String countryNo = session.getCountryNo();
				StringBuilder sb = new StringBuilder();
				sb.append("memberId=").append(memberId);
				sb.append("&tOperatorId=").append(tOperatorId);
				sb.append("&countryNo=").append(countryNo);
				sb.append("&requestSerial=").append(requestSerial);	    
				String name = "";
				String gender = "";
				String id = "";
				String email = "";
				String address = "";
				map.put("suid", suid);
				if(!"".equals(receiverid)){
					String responseUserStr = HttpUtil.sendPost(serviceUrlBean.getGetReceiverInfo(), sb.toString(),requestSerial);
					if(StringUtil.isNotNullOrEmpty(responseUserStr)){
						JSONObject obj = JSONObject.parseObject(responseUserStr);
						if(obj!=null){
							JSONObject objBody = obj.getJSONObject("body");
							String  resultCode = obj.getString("resultCode");
							if(StringUtil.isNotNullOrEmpty(resultCode) && objBody!=null && "0".equals(resultCode)){
								String retCode = objBody.getString("retCode");
								if(StringUtil.isNotNullOrEmpty(retCode) && "0".equals(retCode) ){
									name = objBody.getString("receivername");
									gender = objBody.getString("gender");
									id = objBody.getString("identitycard");
									email = objBody.getString("email");
									address = objBody.getString("receiveraddress");
								}
							}
						}
					}
				}
				map.put("name", name);
				map.put("gender", gender);
				map.put("id", id);
				map.put("email", email);
				map.put("address", address);
				map.put("receiverid", receiverid);
				if("Prepaid".equals(serviceType)){
					mav = new ModelAndView("toolbar/preLotteryCongratulations",map);
				}else{
					mav = new ModelAndView("toolbar/postLotteryCongratulations",map);
				}	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;	
	}
	
	@RequestMapping(value="turnNotice")
	public ModelAndView turnNotice(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "suid",defaultValue = "") String suid,
			@RequestParam(value = "isJumpCompetition",defaultValue = "") String isJumpCompetition){
		Map<String,Object> map = new HashMap<String,Object>();
		ModelAndView mav = null;
		try {
			if(StringUtil.isNotNullOrEmpty(suid)){
				String requestSerial = RandomString.getRandomNumber();
				ToolbarSession session = sessionContext.getSession(suid);
				String tOperatorId = session.gettOperatorId();
			    String countryNo = session.getCountryNo();
		        String userId = session.getMemberId();
		        String serviceType = session.getServiceType();
				map.put("suid", suid);
				map.put("isJumpCompetition", isJumpCompetition);
				StringBuilder sb = new StringBuilder();
				sb.append("memberId=").append(userId);
				sb.append("&tOperatorId=").append(tOperatorId);
				sb.append("&countryNo=").append(countryNo);
				sb.append("&requestSerial=").append(requestSerial);			
				String responseStr = HttpUtil.sendPost(serviceUrlBean.getGetMyLuckyList(), sb.toString(),requestSerial);
				if(StringUtil.isNotNullOrEmpty(responseStr)){
					JSONObject obj = JSONObject.parseObject(responseStr);
					if(obj!=null){
						JSONObject objBody = obj.getJSONObject("body");
						String  resultCode = obj.getString("resultCode");
						if(StringUtil.isNotNullOrEmpty(resultCode) && objBody!=null && "0".equals(resultCode)){
							String retCode = objBody.getString("retCode");
							if(StringUtil.isNotNullOrEmpty(retCode) && "0".equals(retCode) ){
								String record = objBody.getString("record");
								List<HdPrizeShipUnion> list = JSONArray.parseArray(record, HdPrizeShipUnion.class);
								for (HdPrizeShipUnion hdPrizeShipUnion : list) {
									hdPrizeShipUnion.setShipstatus(getStatusStr(hdPrizeShipUnion.getShipstatus()));
									String time = "";
												try {
													Date date = hdPrizeShipUnion.getShipdate();
													if(null==date){
														date = new Date();
													}
													String milStr = String.valueOf(date.getTime());
													String day = DateTimeUtil.getDateTime(date, "dd");
													String month = DateTimeUtil.getMonthOfDate(milStr);
													String year = DateTimeUtil.getDateTime(date, "yyyy");
													time = day + " " + month + "," + year;//Fri,15 Nov,2016
												} catch (Exception e) {
													e.printStackTrace();
												}
									hdPrizeShipUnion.setShipdatestr(time);
								}
								map.put("prizeListInfo", list);
							}

						}
					}
				}
				String responseUserStr = HttpUtil.sendPost(serviceUrlBean.getGetReceiverInfo(), sb.toString(),requestSerial);
				String receiveraddress = "";
				String receiverid = "";
				if(StringUtil.isNotNullOrEmpty(responseUserStr)){
					JSONObject obj = JSONObject.parseObject(responseUserStr);
					if(obj!=null){
						JSONObject objBody = obj.getJSONObject("body");
						String  resultCode = obj.getString("resultCode");
						if(StringUtil.isNotNullOrEmpty(resultCode) && objBody!=null && "0".equals(resultCode)){
							String retCode = objBody.getString("retCode");
							if(StringUtil.isNotNullOrEmpty(retCode) && "0".equals(retCode) ){
								receiveraddress = objBody.getString("receiveraddress");
								receiverid = objBody.getString("receiverid");
							}
						}
					}
				}
				map.put("receiverAddress", receiveraddress);
				map.put("receiverid", receiverid);
				if("Prepaid".equals(serviceType)){
					mav = new ModelAndView("toolbar/preNotice",map);
				}else{
					mav = new ModelAndView("toolbar/postNotice",map);
				}				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return mav;	
	}
	
	
	
	@RequestMapping(value="/turnHelp")
	@ResponseBody
	public ModelAndView turnHelp(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "suid",defaultValue = "") String suid
			){
		ToolbarSession session = sessionContext.getSession(suid);
		String serviceType = session.getServiceType();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("suid", suid);		
		ModelAndView mav = null;
		if("Prepaid".equals(serviceType)){
			mav = new ModelAndView("toolbar/preHelp",map);
		}else{
			mav = new ModelAndView("toolbar/postHelp",map);
		}	
		return mav;	
	}
	
	@RequestMapping(value="/turnGameRule")
	@ResponseBody
	public ModelAndView turnGameRule(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "suid",defaultValue = "") String suid
			){
		ToolbarSession session = sessionContext.getSession(suid);
		String serviceType = session.getServiceType();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("suid", suid);		
		ModelAndView mav = null;
		if("Prepaid".equals(serviceType)){
			mav = new ModelAndView("toolbar/preGameRule",map);
		}else{
			mav = new ModelAndView("toolbar/postGameRule",map);
		}	
		return mav;	
	}	
	
	
	@RequestMapping(value="/turnCompetition")
	@ResponseBody
	public ModelAndView turnCompetition(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "suid",defaultValue = "") String suid,
			@RequestParam(value = "promotionId",defaultValue = "") String promotionId
			){
		ModelAndView mav = null;
		Map<String,Object> map = new HashMap<String, Object>(); 
		try {
			if(StringUtil.isNotNullOrEmpty(suid)){
				String requestSerial = RandomString.getRandomNumber();
				ToolbarSession session = sessionContext.getSession(suid);
				String serviceType = session.getServiceType();
				String memberId = session.getMemberId();
				String tOperatorId = session.gettOperatorId();
			    String countryNo = session.getCountryNo();				
				StringBuilder sb = new StringBuilder();
				sb.append("memberId=").append(memberId);
				sb.append("&tOperatorId=").append(tOperatorId);
				sb.append("&countryNo=").append(countryNo);
				sb.append("&requestSerial=").append(requestSerial);			
				sb.append("&promotionId=").append(promotionId);
				sb.append("&startNum=").append(1);
				sb.append("&endNum=").append(10);
				String responseStr = HttpUtil.sendPost(serviceUrlBean.getGetCompetitionInfo(), sb.toString(),requestSerial);
				if(StringUtil.isNotNullOrEmpty(responseStr)){
					JSONObject obj = JSONObject.parseObject(responseStr);
					if(obj!=null){
						JSONObject objBody = obj.getJSONObject("body");
						String  resultCode = obj.getString("resultCode");
						if(StringUtil.isNotNullOrEmpty(resultCode) && objBody!=null && "0".equals(resultCode)){
							String retCode = objBody.getString("retCode");
							JSONObject promotionPrizeInfo = objBody.getJSONObject("promotionPrizeInfo");
							JSONArray luckListInfo = objBody.getJSONArray("luckListInfo");
							if(StringUtil.isNotNullOrEmpty(retCode) && "0".equals(retCode) &&  promotionPrizeInfo!=null && luckListInfo!=null){
								map.put("userLimits", promotionPrizeInfo.getString("userLimits"));
								map.put("record", luckListInfo);
							}
								
						}
					}
				}

				map.put("suid", suid);
				map.put("promotionId", promotionId);
				if("Prepaid".equals(serviceType)){
					mav = new ModelAndView("toolbar/preCompetition",map);
				}else{
					mav = new ModelAndView("toolbar/postCompetition",map);
				}					
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;	
	}
	
	
	@RequestMapping(value="/turnPhoneGameCongratulations")
	@ResponseBody
	public ModelAndView turnPhoneGameCongratulations(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "suid",defaultValue = "") String suid,
			@RequestParam(value = "promotionId",defaultValue = "") String promotionId,
			@RequestParam(value = "receiverid",defaultValue = "") String receiverid,
			@RequestParam(value = "prizeName",defaultValue = "") String prizeName,
			@RequestParam(value = "shipId",defaultValue = "") String shipId,
			@RequestParam(value = "prizeType",defaultValue = "") String prizeType
			){
		ModelAndView mav = null;
		Map<String,Object> map1 = new HashMap<String, Object>(); 
		Map<String,Object> map2 = new HashMap<String, Object>(); 
		try {
			if(StringUtil.isNotNullOrEmpty(suid)){
				map2.put("prizeName", prizeName);
				String requestSerial = RandomString.getRandomNumber();
				ToolbarSession session = sessionContext.getSession(suid);
				String serviceType = session.getServiceType();
				String memberId = session.getMemberId();
				String tOperatorId = session.gettOperatorId();
			    String countryNo = session.getCountryNo();				
				StringBuilder sb = new StringBuilder();
				map1.put("suid", suid);
				map1.put("promotionId", promotionId);
				map2.put("suid", suid);
				map2.put("promotionId", promotionId);
				sb.append("memberId=").append(memberId);
				sb.append("&tOperatorId=").append(tOperatorId);
				sb.append("&countryNo=").append(countryNo);
				sb.append("&requestSerial=").append(requestSerial);			
				sb.append("&promotionId=").append(promotionId);	
				sb.append("&shipId=").append(shipId);	
				sb.append("&status=").append("0");	
				String name = "";
				String gender = "";
				String id = "";
				String email = "";
				String address = "";
				String respStr = HttpUtil.sendPost(serviceUrlBean.getUpdateManOrRobotInfo(), sb.toString(),requestSerial);
				if(!"".equals(receiverid)){
					String responseUserStr = HttpUtil.sendPost(serviceUrlBean.getGetReceiverInfo(), sb.toString(),requestSerial);
					if(StringUtil.isNotNullOrEmpty(responseUserStr)){
						JSONObject obj = JSONObject.parseObject(responseUserStr);
						if(obj!=null){
							JSONObject objBody = obj.getJSONObject("body");
							String  resultCode = obj.getString("resultCode");
							if(StringUtil.isNotNullOrEmpty(resultCode) && objBody!=null && "0".equals(resultCode)){
								String retCode = objBody.getString("retCode");
								if(StringUtil.isNotNullOrEmpty(retCode) && "0".equals(retCode) ){
									name = objBody.getString("receivername");
									gender = objBody.getString("gender");
									id = objBody.getString("identitycard");
									email = objBody.getString("email");
									address = objBody.getString("receiveraddress");
								}
							}
						}
					}
				}
				map1.put("name", name);
				map1.put("gender", gender);
				map1.put("id", id);
				map1.put("email", email);
				map1.put("address", address);
				map1.put("receiverid", receiverid);
				if("1".equals(prizeType)){
					if("Prepaid".equals(serviceType)){
						mav = new ModelAndView("toolbar/preRewardinformation",map1);
					}else{
						mav = new ModelAndView("toolbar/postRewardinformation",map1);
					}					
				}else if("2".equals(prizeType)){
					if("Prepaid".equals(serviceType)){
						mav = new ModelAndView("toolbar/prePhoneGameCongratulations",map2);
					}else{
						mav = new ModelAndView("toolbar/postPhoneGameCongratulations",map2);
					}	
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;	
	}		
	
	private String getStatusStr(String status){
		if(!StringUtil.isNotNullOrEmpty(status)){
			return "";
		}
		if("0".equals(status)){
			return "Already won";
		}else if("1".equals(status)){
			return "Already receiver info completed";
		}else{
			return "Shipped On";
		}
	}
}
