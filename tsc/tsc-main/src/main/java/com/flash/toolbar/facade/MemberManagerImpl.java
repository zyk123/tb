package com.flash.toolbar.facade;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.flash.toolbar.common.util.*;
import com.flash.toolbar.mapper.HyMemberMapper;
import com.flash.toolbar.model.*;
import com.flash.toolbar.redis.RedisOperation;
import com.flash.toolbar.service.MemberService;
import com.flash.toolbar.token.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service("memberManager")
public class MemberManagerImpl implements MemberManager{
	
	@Resource
	private MemberService memberService;
	
	@Resource
	private HyMemberMapper hyMemberMapper;
	
	@Resource
	private TokenManager tokenManager;
	
	@Autowired
	private RedisOperation redisOperation;

	/**
	 * 用户关闭界面设置
	 */
	@Override
	public String setToolbarAvaliable(String params) {
//		TraceLogger.debug("tsc-main-MemberManagerImpl:setToolbarAvaliable 3qii input params:" + params);
		JSONObject requestJSON = JSONObject.parseObject(params);
		String requestSerial = requestJSON.getString("requestSerial");			
		//tsc用户关闭toolbar业务日志记录开始
		BusinessLogger.LoggerInfo(requestSerial, "tsc", "setToolbarAvaliable", true, null, String.valueOf(new Date().getTime()));
		JSONObject jsonReturn = new JSONObject();
		JSONObject json = (JSONObject) JSONObject.parse(params);
		if(!json.isEmpty()){
			String type = json.getString("closeType");
			Date firstDate = new Date();
			Date endDate = null;
			//查询手机号所属运营商信息
			String memberId = json.getString("memberId");
			SysTelecomOperatorAndHyMember memsys = memberService.queryMemberSys(memberId);
			if(memsys != null){
				firstDate = DateUtil.getTimeZoneDate(firstDate, memsys.getTimezone(), DateUtil.FORMAT2);
				
				if(type.equals("10")){
					endDate = DateUtil.getNextDay(firstDate, 1);
				}else if(type.equals("11")){
					endDate = DateUtil.getNextDay(firstDate, 7);
				}else if(type.equals("12")){
					endDate = DateUtil.getNextDay(firstDate, 30);
				}
				else{
					endDate = DateUtil.getNextDay(firstDate, 1);
				}
				
				HyCloseSet close = new HyCloseSet();
				close.setClosesetid(StringUtil.formatUUID(false));
				close.setMemberid(json.getString("memberId"));
				close.setMobileno(memsys.getMobileno());
				close.setClosetype(json.getString("closeType"));
				close.setBegindate(firstDate);
				close.setEnddate(endDate);
				close.setCountryno(json.getString("countryNo"));
				close.setToperatorid(json.getString("tOperatorId"));
				close.setModifyman(memsys.getMobileno());
				close.setModifydate(DateUtil.getTimeZoneDate(new Date(), memsys.getTimezone(), DateUtil.FORMAT2));
				memberService.setToolbarAvaliable(close,requestSerial);
				redisOperation.del("ita"+(memsys.getMobileno().startsWith("6") ? memsys.getMobileno().substring(1):memsys.getMobileno()));//删除缓存
				jsonReturn.put("retCode", Constant.RETURN_SUCCESS);
			}
			else{
				jsonReturn.put("retCode", Constant.RETURN_EMPTY);
			}
		}
		else{
			jsonReturn.put("retCode", Constant.PARAMS_JSON_EMPTY);
		}
		//tsc用户关闭toolbar业务日志记录结束
		BusinessLogger.LoggerInfo(requestSerial, "tsc", "setToolbarAvaliable", false, JSONObject.toJSONString(jsonReturn), String.valueOf(new Date().getTime()));
//		TraceLogger.debug("tsc-main-MemberManagerImpl:setToolbarAvaliable 3qii output params:" + jsonReturn.toJSONString());
		return jsonReturn.toJSONString();
	}
	
	/**
	 * 获取默认设置
	 */
	@Override
	public String getDefaultSetting(String params) {
//		TraceLogger.debug("tsc-main-MemberManagerImpl:getDefaultSetting 3qii input params:" + params);
		
		JSONObject jsonReturn = new JSONObject();
		JSONObject json = (JSONObject) JSONObject.parse(params);
		String requestSerial = json.getString("requestSerial");			
		//tsc获取用户关闭toolbar信息业务日志记录开始
		BusinessLogger.LoggerInfo(requestSerial, "tsc", "getDefaultSetting", true, null, String.valueOf(new Date().getTime()));
		if(!json.isEmpty()){
			//查询手机号所属运营商信息
			SysTelecomOperatorAndHyMember memsys = memberService.queryMemberSys(json.getString("memberId"));
			if(memsys != null){
				
				HyCloseSet close = new HyCloseSet();
				close.setMemberid(json.getString("memberId"));
				close.setMobileno(memsys.getMobileno());
				close.setClosetype(json.getString("closeType"));
				close.setCountryno(json.getString("countryNo"));
				close.setToperatorid(json.getString("tOperatorId"));
				close.setModifyman(memsys.getMobileno());
				close.setModifydate(DateUtil.getTimeZoneDate(new Date(), memsys.getTimezone(), DateUtil.FORMAT2));
				String result = memberService.getDefaultSetting(close);	
				if(!"-1".equals(result)){
					JSONObject jsonO = JSONObject.parseObject(result);
					jsonReturn.put("retCode", Constant.RETURN_SUCCESS);
					jsonReturn.put("result", jsonO);
				}else{
					JSONObject jsonO = new JSONObject();
					jsonReturn.put("retCode", Constant.RETURN_FAILURE);
					jsonReturn.put("result", jsonO);
				}
			}
			else{
				jsonReturn.put("retCode", Constant.RETURN_EMPTY);
			}
		}
		else{
			jsonReturn.put("retCode", Constant.PARAMS_JSON_EMPTY);
		}
		//tsc获取用户关闭toolbar信息业务日志记录结束
		BusinessLogger.LoggerInfo(requestSerial, "tsc", "getDefaultSetting", false, JSONObject.toJSONString(jsonReturn), String.valueOf(new Date().getTime()));
//		TraceLogger.debug("tsc-main-MemberManagerImpl:getDefaultSetting 3qii output params:" + jsonReturn.toJSONString());
		return jsonReturn.toJSONString();
	}
	
	/**
	 * 运营商接口
	 * 查询用户所有套餐使用情况
	 * @param params json字符串
	 * @return json字符串
	 */
	@Override
	public String queryQuotaUsageFup(String params) {
//		TraceLogger.debug("tsc-main-MemberManagerImpl:queryQuotaUsageFup 3qii input params:" + params);
		
		JSONObject json = (JSONObject) JSONObject.parse(params);
		if(!json.isEmpty()){
			String requestSerial = json.getString("requestSerial");
			//tsc查询用户所有套餐使用情况业务日志记录开始
			BusinessLogger.LoggerInfo(requestSerial, "tsc", "queryQuotaUsageFup", true, null, String.valueOf(new Date().getTime()));
			//查询手机号所属运营商信息
			SysTelecomOperatorAndHyMember memsys = memberService.queryMemberSys(json.getString("memberId"));
			if(memsys != null){
				Configuration cfg = new Configuration(null, "environment.properties");
				String url = cfg.getValue("quotausagefup.url");
//				StringBuilder sb = new StringBuilder();
//				sb.append("?businessEvent=").append("QuotaUsageQueryFUP");
//				sb.append("&sourceSystem=").append("Siebel");
//				sb.append("&msisdn=").append(memsys.getMobileno());
//				//查询前一个月的数据
//				Date date = DateUtil.getNextDay(new Date(), -30);
//				String dateFrom = DateUtil.getFormatDate(date);
//				String dateTo = DateUtil.getFormatDate(new Date());
//				try {
//					dateFrom = URLEncoder.encode(DateUtil.getTimeZoneStr(date, memsys.getTimezone(), DateUtil.FORMAT2), "UTF-8");
//					dateTo = URLEncoder.encode(DateUtil.getTimeZoneStr(new Date(), memsys.getTimezone(), DateUtil.FORMAT2), "UTF-8");
//				} catch (UnsupportedEncodingException e1) {
//					e1.printStackTrace();
//				}
//				sb.append("&dateFrom=").append(dateFrom);
//				sb.append("&dateTo=").append(dateTo);
				//调用运营商接口
				try {
//					String req = url+sb.toString();
//					String req = MessageFormat.format(url, new Object[]{memsys.getMobileno().substring(Integer.parseInt(ParamPropertiesUtil.getiSegLenthB()),Integer.parseInt(ParamPropertiesUtil.getiSegLenthE()))});
					String req = MessageFormat.format(url, new Object[]{memsys.getMobileno()});
					Map<String,String> map = new HashMap<String,String>();
					map.put("Accept", "application/json");
					map.put("Authorization", "Bearer "+tokenManager.queryToken());
					map.put("Host", ParamPropertiesUtil.getCelcomHost());
//					TraceLogger.debug("tsc-main-MemberManagerImpl:queryQuotaUsageFup celecom input params:" + req);
					//tsc调用接口跟踪日志记录开始
//					TraceLogger.debugInterface("tsc", "celcom", "quotausagefup", Constant.ACTION_IN, req.toString());
//					JSONObject jsonHttp = HttpHelper.httpGet(req);
					TraceLogger.debugInterface("tsc", "celcom", "quotausagefup"+memsys.getMobileno(), Constant.ACTION_IN, req);
					JSONObject jsonHttp = HttpHelper.httpGetWithHead(req,map);
					TraceLogger.debugInterface("celcom", "tsc", "quotausagefup"+memsys.getMobileno(), Constant.ACTION_OUT, null==jsonHttp ? null:JSONObject.toJSONString(jsonHttp));
					//tsc调用接口跟踪日志记录结束
//					TraceLogger.debugInterface("celcom", "tsc", "quotausagefup", Constant.ACTION_OUT, JSONObject.toJSONString(jsonHttp));
					if(jsonHttp != null){
						//tsc查询用户所有套餐使用情况业务日志记录结束
						BusinessLogger.LoggerInfo(requestSerial, "tsc", "queryQuotaUsageFup", false,JSONObject.toJSONString(jsonHttp), String.valueOf(new Date().getTime()));
						return jsonHttp.toJSONString();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else{
				BusinessLogger.info("tsc-main-MemberManagerImpl:queryQuotaUsageFup 3qii output: memberid does not exist:" + json.getString("memberId"));
			}
		}
		return null;
	}

	/**
	 * 运营商接口
	 * 查询用户基本信息（包含预付费和后付费用户）
	 * @param params
	 * @return
	 */
	@Override
	public String queryProfileRetrieve(String params) {
		TraceLogger.debug("tsc-main-MemberManagerImpl:queryProfileRetrieve 3qii input params:" + params);
		
		JSONObject json = (JSONObject) JSONObject.parse(params);
		if(!json.isEmpty()){
			//查询手机号所属运营商信息
			SysTelecomOperatorAndHyMember memsys = memberService.queryMemberSys(json.getString("memberId"));
			if(memsys != null){
				if(StringUtil.isNotNullOrEmpty(memsys.getPreposindicator()) && memsys.getPreposindicator().toLowerCase().equals("prepaid")){
					TraceLogger.debug("tsc-main-MemberManagerImpl:queryProfileRetrieve query result from db");
					return "prepaid";
				}
				else{
					TraceLogger.debug("tsc-main-MemberManagerImpl:queryProfileRetrieve query result from celcom interface");
					Configuration cfg = new Configuration(null, "environment.properties");
					String url = cfg.getValue("profileretrieve.url");
					//调用运营商接口
					try {
	//					String req = url+sb.toString();
	//					String req = MessageFormat.format(url, new Object[]{memsys.getMobileno().substring(Integer.parseInt(ParamPropertiesUtil.getiSegLenthB()),Integer.parseInt(ParamPropertiesUtil.getiSegLenthE()))});
						String req = MessageFormat.format(url, new Object[]{memsys.getMobileno()});
						Map<String,String> map = new HashMap<String,String>();
						map.put("Accept", "application/json");
						map.put("Authorization", "Bearer "+tokenManager.queryToken());
						map.put("Host", ParamPropertiesUtil.getCelcomHost());
//						TraceLogger.debug("tsc-main-MemberManagerImpl:queryProfileRetrieve celecom input params:" + req);
						TraceLogger.debugInterface("tsc", "celcom", "queryProfileRetrieve"+memsys.getMobileno(), Constant.ACTION_IN, req);
						JSONObject jsonHttp = HttpHelper.httpGetWithHead(req,map);
//						TraceLogger.debug("tsc-main-MemberManagerImpl:queryProfileRetrieve celecom output params:" + jsonHttp.toJSONString());
						TraceLogger.debugInterface("celcom", "tsc", "queryProfileRetrieve"+memsys.getMobileno(), Constant.ACTION_OUT, null==jsonHttp ? null:JSONObject.toJSONString(jsonHttp));
						if(jsonHttp != null){
							JSONObject result = jsonHttp.getJSONObject("outputSDResp");
							String serviceType = result.getString("serviceType");
							if (serviceType.toLowerCase().equals("prepaid")) {
								return "prepaid";
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			else{
				TraceLogger.debug("tsc-main-MemberManagerImpl:queryProfileRetrieve 3qii output: memberid does not exist:" + json.getString("memberId"));
			}
		}
		return null;
	}

	/**
	 * 运营商接口
	 * 查询用户总流量使用情况
	 * @param params
	 * @return
	 */
	@Override
	public String queryActquotaUsageFup(String params) {
		
		JSONObject json = (JSONObject) JSONObject.parse(params);
		if(!json.isEmpty()){
			String requestSerial = json.getString("requestSerial");
			//ts查询用户总流量使用情况业务日志记录开始
			BusinessLogger.LoggerInfo(requestSerial, "tsc", "queryActquotaUsageFup", true, null, String.valueOf(new Date().getTime()));
			//查询手机号所属运营商信息
			SysTelecomOperatorAndHyMember memsys = memberService.queryMemberSys(json.getString("memberId"));
			JSONObject jsonHttp = null;
			if(memsys != null){
				Configuration cfg = new Configuration(null, "environment.properties");
				String url = cfg.getValue("actquotausagefup.url");
				//调用运营商接口
				try {
//					String mobileno = memsys.getMobileno().substring(Integer.parseInt(ParamPropertiesUtil.getiSegLenthB()),Integer.parseInt(ParamPropertiesUtil.getiSegLenthE()));
					String mobileno = memsys.getMobileno();
					if(StringUtil.isNotNullOrEmpty(mobileno)){
						String jsonStr = redisOperation.get("celcom.user.volume."+mobileno);
						if(StringUtil.isNotNullOrEmpty(jsonStr)){
							BusinessLogger.LoggerInfo(requestSerial, "tsc", "queryActquotaUsageFup"+mobileno, false, "redis existing volume:"+jsonStr, String.valueOf(new Date().getTime()));
							return jsonStr;
						}
						else{

							String req = MessageFormat.format(url, new Object[]{mobileno});
							Map<String,String> map = new HashMap<String,String>();
							map.put("Accept", "application/json");
							map.put("Authorization", "Bearer "+tokenManager.queryToken());
							map.put("Host", ParamPropertiesUtil.getCelcomHost());
							//tsc调用接口跟踪日志记录开始
							TraceLogger.debugInterface("tsc", "celcom", "actquotausagefup"+mobileno, Constant.ACTION_IN, req);
							jsonHttp = HttpHelper.httpGetWithHead(req,map);
							//tsc调用接口跟踪日志记录结束
							TraceLogger.debugInterface("celcom", "tsc", "actquotausagefup"+mobileno, Constant.ACTION_OUT, null==jsonHttp ? null:JSONObject.toJSONString(jsonHttp));
							if(jsonHttp != null){
								JSONObject jsonb = null;
								JSONObject result = jsonHttp.getJSONObject("outputActQuotaUsageResp");
								if (result.getString("statusCode").toUpperCase().equals("OK")) {
									float total = 0;
									if(StringUtil.isNotNullOrEmpty(result.getString("VolumeTotal"))){
										total = result.getFloatValue("VolumeTotal");
									}
									float used = 0;
									if(StringUtil.isNotNullOrEmpty(result.getString("VolumeUsed"))){
										used = result.getFloatValue("VolumeUsed");
									}
									jsonb = new JSONObject();
									jsonb.put("VolumeTotal", total);
									jsonb.put("VolumeUsed", used);
									jsonb.put("SubscriptionEndDateTime", result.getString("SubscriptionEndDateTime"));
									jsonb.put("LastOn", DateUtil.getFormatDate(new Date()));
									redisOperation.add("celcom.user.volume."+mobileno, jsonb.toJSONString());
//									ExceptionLogger.error("queryActquotaUsageFup wangxiaoran test  celcom.user.volume."+mobileno+" : "+jsonb.toJSONString());
									redisOperation.expire("celcom.user.volume."+mobileno, Integer.parseInt(cfg.getValue("celcom.user.balance.timeout").trim()), TimeUnit.SECONDS);
								}
								//ts查询用户总流量使用情况业务日志记录结束
								BusinessLogger.LoggerInfo(requestSerial, "tsc", "queryActquotaUsageFup", false,JSONObject.toJSONString(jsonHttp), String.valueOf(new Date().getTime()));
								if(jsonb != null){
									return jsonb.toJSONString();
								}
							}
						}
					}
				} catch (Exception e) {
					ExceptionLogger.LoggerInfo(requestSerial, "", "MemberManagerImpl queryActquotaUsageFup!", e);
				}
			}
			else{
				BusinessLogger.info("tsc-main-MemberManagerImpl:queryActquotaUsageFup 3qii output: memberid does not exist:" + json.getString("memberId"));
			}
			//ts查询用户总流量使用情况业务日志记录结束
			BusinessLogger.LoggerInfo(requestSerial, "tsc", "queryActquotaUsageFup", false,JSONObject.toJSONString(jsonHttp), String.valueOf(new Date().getTime()));
		}
		return null;
	}

	/**
	 * 运营商接口
	 * 查询预付费用户话费余额
	 * @param params
	 * @return
	 */
	@Override
	public String queryAccountBalance(String params) {
		JSONObject json = (JSONObject) JSONObject.parse(params);
		if(!json.isEmpty()){
			String requestSerial = json.getString("requestSerial");
			//tsc查询预付费用户话费余额业务日志记录开始
			BusinessLogger.LoggerInfo(requestSerial, "tsc", "queryAccountBalance", true, null, String.valueOf(new Date().getTime()));
			//查询手机号所属运营商信息
			SysTelecomOperatorAndHyMember memsys = memberService.queryMemberSys(json.getString("memberId"));
			JSONObject jsonHttp = null;
			if(memsys != null){
				Configuration cfg = new Configuration(null, "environment.properties");
				String url = cfg.getValue("queryaccountbalance.url");
				//调用运营商接口
				try {
					//String mobileno = memsys.getMobileno().substring(Integer.parseInt(ParamPropertiesUtil.getiSegLenthB()),Integer.parseInt(ParamPropertiesUtil.getiSegLenthE()));
					String mobileno = memsys.getMobileno();
					if(StringUtil.isNotNullOrEmpty(mobileno)){
						String jsonStr = redisOperation.get("celcom.user.balance."+mobileno);
						if(StringUtil.isNotNullOrEmpty(jsonStr)){
							BusinessLogger.LoggerInfo(requestSerial, "tsc", "queryAccountBalance"+mobileno, false, "redis existing balance:"+jsonStr, String.valueOf(new Date().getTime()));
							return jsonStr;
						}
						else{

							String req = MessageFormat.format(url, new Object[]{mobileno});
							Map<String,String> map = new HashMap<String,String>();
							map.put("Accept", "application/json");
							map.put("Authorization", "Bearer "+tokenManager.queryToken());
							map.put("Host", ParamPropertiesUtil.getCelcomHost());
							//tsc调用接口跟踪日志记录开始
							TraceLogger.debugInterface("tsc", "celcom", "queryaccountbalance"+mobileno, Constant.ACTION_IN, req);
							jsonHttp = HttpHelper.httpGetWithHead(req, map);
							//tsc调用接口跟踪日志记录结束
							TraceLogger.debugInterface("celcom", "tsc", "queryaccountbalance"+mobileno, Constant.ACTION_OUT, null==jsonHttp ? null:JSONObject.toJSONString(jsonHttp));
							if(jsonHttp != null){
								//解析余额成功后放入redis中缓存
								JSONObject jsonBlance = jsonHttp.getJSONObject("outputPrepaidBalQueryResp");
								JSONArray array = jsonBlance.getJSONArray("prepaidAccountsBalance");
								Iterator<Object> iter = array.iterator();
								JSONObject jsonb = null;
								while (iter.hasNext()) {
									JSONObject record = (JSONObject) iter.next();
									if(record.getString("accountCode").equals("2000") && record.getString("accountType").equals("PrepaidBalanceSubaccount")){
										String balance = record.getString("balance");
										String unit = record.getString("unit");
										jsonb = new JSONObject();
										jsonb.put("balance", balance);
										jsonb.put("unit", unit);
										jsonb.put("validity", record.getString("validity"));
										jsonb.put("lastOn", DateUtil.getFormatDate(new Date()));
										redisOperation.add("celcom.user.balance."+mobileno, jsonb.toJSONString());
										redisOperation.expire("celcom.user.balance."+mobileno, Integer.parseInt(cfg.getValue("celcom.user.balance.timeout").trim()), TimeUnit.SECONDS);
										break;
									}
								}

								//tsc查询预付费用户话费余额业务日志记录结束
								BusinessLogger.LoggerInfo(requestSerial, "tsc", "queryAccountBalance", false, JSONObject.toJSONString(jsonb), String.valueOf(new Date().getTime()));
								if(jsonb !=null){
									return jsonb.toJSONString();
								}
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else{
				BusinessLogger.info("tsc-main-MemberManagerImpl:queryAccountBalance 3qii output: memberid does not exist:" + json.getString("memberId"));
			}
			//tsc查询预付费用户话费余额业务日志记录结束
			BusinessLogger.LoggerInfo(requestSerial, "tsc", "queryAccountBalance", false, JSONObject.toJSONString(jsonHttp), String.valueOf(new Date().getTime()));
		}
		return null;
	}

	/**
	 * 查询用户流量提醒
	 * @param memberId 会员ID
	 * @param alertDate 提醒日期-天
	 * @param tOperatorId 运营商ID
	 * @param countryNo 国家编码
	 */
	@Override
	public Boolean queryFlowAlert(String params) {
//		TraceLogger.debug("tsc-main-MemberManagerImpl:queryFlowAlert 3qii input params:" + params);
		
		JSONObject json = (JSONObject) JSONObject.parse(params);
		if(!json.isEmpty()){
			String requestSerial = json.getString("requestSerial");
			//tsc查询用户流量提醒业务日志记录开始
			BusinessLogger.LoggerInfo(requestSerial, "tsc", "queryFlowAlert", true, null, String.valueOf(new Date().getTime()));
			Date alertDate = null;
			//查询手机号所属运营商信息
			SysTelecomOperatorAndHyMember memsys = memberService.queryMemberSys(json.getString("memberId"));			
			if(memsys != null){
				alertDate = DateUtil.getTimeZoneDate(json.getDate("alertDate"), memsys.getTimezone(), DateUtil.FORMAT1);
			
				HyFlowAlert alert = new HyFlowAlert();
				alert.setMemberid(json.getString("memberId"));
				alert.setAlertdate(alertDate==null?json.getDate("alertDate"):alertDate);
				alert.setToperatorid(json.getString("tOperatorId"));
				alert.setCountryno(json.getString("countryNo"));
				alert.setAlertvalue(json.getString("alertvalue"));
				List<String> list = memberService.selectByMemberIdAndDate(alert);
				if(list != null && !list.isEmpty() && 1<=list.size()){
					//tsc查询用户基本信息业务日志记录结束
					BusinessLogger.LoggerInfo(requestSerial, "tsc", "queryFlowAlert", false,String.valueOf(true), String.valueOf(new Date().getTime()));
					return true;
				}
			}
			else{
				BusinessLogger.info("tsc-main-MemberManagerImpl:queryFlowAlert 3qii output: memberid does not exist:" + json.getString("memberId"));
			}
		}
		return false;
	}

	/**
	 * 查询用户阀值设置
	 * @param memberId 会员ID
	 * @param tOperatorId 运营商ID
	 * @param countryNo 国家编码
	 * return firstValue secondValue
	 */
	@Override
	public String queryAlertThreshold(String params) {
//		TraceLogger.debug("tsc-main-MemberManagerImpl:queryAlertThreshold 3qii input params:" + params);
		
		JSONObject json = (JSONObject) JSONObject.parse(params);
		String requestSerial = "";
		if(!json.isEmpty()){
			requestSerial = json.getString("requestSerial");
			//tsc 查询用户阀值设置业务日志记录开始
			BusinessLogger.LoggerInfo(requestSerial, "tsc", "queryAlertThreshold", true, null, String.valueOf(new Date().getTime()));
			HyAlertThreshold bean = new HyAlertThreshold();
			bean.setMemberid(json.getString("memberId"));
			bean.setToperatorid(json.getString("tOperatorId"));
			bean.setCountryno(json.getString("countryNo"));
			HyAlertThreshold threshold = memberService.queryAlertThreshold(bean);
			if(threshold != null){
				JSONObject result = new JSONObject();
				result.put("firstValue", threshold.getFirstflowvalue());
				result.put("secondValue", threshold.getSecondflowvalue());
				//tsc 查询用户阀值设置业务日志记录结束
				BusinessLogger.LoggerInfo(requestSerial, "tsc", "queryAlertThreshold", false,JSONObject.toJSONString(result), String.valueOf(new Date().getTime()));
//				TraceLogger.debug("tsc-main-MemberManagerImpl:queryAlertThreshold 3qii output params:" + result.toJSONString());
				return result.toJSONString();
			}

		}
		//tsc 查询用户阀值设置业务日志记录结束
		BusinessLogger.LoggerInfo(requestSerial, "tsc", "queryAlertThreshold", false,null, String.valueOf(new Date().getTime()));
		return null;
	}

	/**
	 * 保存用户流量提醒记录
	 * @param memberId 会员ID
	 * @param alertDate 提醒日期-天
	 * @param tOperatorId 运营商ID
	 * @param countryNo 国家编码
	 */
	@Override
	public int saveFlowAlert(String params) {
//		TraceLogger.debug("tsc-main-MemberManagerImpl:saveFlowAlert 3qii input params:" + params);
		
		JSONObject json = (JSONObject) JSONObject.parse(params);
		String requestSerial = "";
		if(!json.isEmpty()){
			requestSerial = json.getString("requestSerial");
			//tsc保存用户流量提醒记录业务日志记录开始
			BusinessLogger.LoggerInfo(requestSerial, "tsc", "saveFlowAlert", true, null, String.valueOf(new Date().getTime()));
			Date alertDate = null;
			//查询手机号所属运营商信息
			SysTelecomOperatorAndHyMember memsys = memberService.queryMemberSys(json.getString("memberId"));
			if(memsys != null){
				alertDate = DateUtil.getTimeZoneDate(json.getDate("alertDate"), memsys.getTimezone(), DateUtil.FORMAT2);
				
				HyFlowAlert alert = new HyFlowAlert();
				alert.setMemberid(json.getString("memberId"));
				alert.setAlertdate(alertDate==null?json.getDate("alertDate"):alertDate);
				alert.setToperatorid(json.getString("tOperatorId"));
				alert.setCountryno(json.getString("countryNo"));
				alert.setFlowalertid(StringUtil.formatUUID(false));
				alert.setMobileno(memsys==null?"":memsys.getMobileno());
				alert.setDelflag("1");
				alert.setModifydate(memsys==null?new Date():DateUtil.getTimeZoneDate(new Date(), memsys.getTimezone(), DateUtil.FORMAT2));
				alert.setModifyman(json.getString("memberId"));
				alert.setCurrentvalue(json.getString("currentvalue"));
				alert.setAlertvalue(json.getString("alertvalue"));
				int alertValue = memberService.insertSelective(alert);
				//tsc保存用户流量提醒记录业务日志记录结束
				BusinessLogger.LoggerInfo(requestSerial, "tsc", "saveFlowAlert", false,String.valueOf(alertValue), String.valueOf(new Date().getTime()));
				return alertValue;	
			}
			else{
				BusinessLogger.info("tsc-main-MemberManagerImpl:saveFlowAlert 3qii output: memberid does not exist:" + json.getString("memberId"));
			}
		}
		//tsc保存用户流量提醒记录业务日志记录结束
		BusinessLogger.LoggerInfo(requestSerial, "tsc", "saveFlowAlert", false,"0", String.valueOf(new Date().getTime()));
		return 0;
	}

	/**
	 * 查询套餐详情
	 * @param fGNo 运营商套餐ID
	 * @param tOperatorId 运营商ID
	 * @param countryNo 国家编码
	 * return json格式运营商套餐json格式
	 */
	@Override
	public String queryFlowPackage(String params) {
		TraceLogger.debug("tsc-main-MemberManagerImpl:queryFlowPackage 3qii input params:" + params);
		
		JSONObject json = (JSONObject) JSONObject.parse(params);
		if(!json.isEmpty()){
			TcFlowPackage bean = new TcFlowPackage();
			bean.setFgno(json.getString("fGNo"));
			bean.setCountryno(json.getString("countryNo"));
			bean.setToperatorid(json.getString("tOperatorId"));
			bean.setStatus("0");
			bean.setDelflag("1");
			TcFlowPackage result = memberService.queryFlowPackageByFGNo(bean);
			if(result != null){
				TraceLogger.debug("tsc-main-MemberManagerImpl:queryFlowPackage 3qii output params:" + JSONObject.toJSONString(result));
				
				return JSONObject.toJSONString(result);
			}
		}
		return null;
	}

	/**
	 * 查询套餐类型
	 * @param packageTypeId 套餐类型id
	 */
	@Override
	public String queryPackageType(String params) {
		TraceLogger.debug("tsc-main-MemberManagerImpl:queryPackageType 3qii input params:" + params);
		
		JSONObject json = (JSONObject) JSONObject.parse(params);
		if(!json.isEmpty()){			
			TcPackageType result = memberService.queryPackageTypeByPrimaryKey(json.getString("packageTypeId"));
			if(result != null){
				TraceLogger.debug("tsc-main-MemberManagerImpl:queryPackageType 3qii output params:" + JSONObject.toJSONString(result));
				
				return JSONObject.toJSONString(result);
			}
		}
		return null;
	}

	/**
	 * 运营商接口
	 * 查询用户基本信息（包含预付费和后付费用户）
	 * @param params
	 * @return
	 */
	@Override
	public String queryCustomerRetrieve(String params) {
//		TraceLogger.debug("tsc-main-MemberManagerImpl:queryCustomerRetrieve 3qii input params:" + params);
		JSONObject json = (JSONObject) JSONObject.parse(params);
		if(!json.isEmpty()){
			String mobileNo = json.getString("mobileNo");
			String memberId = json.getString("memberId");
			String requestSerial = json.getString("requestSerial");
			//tsc查询用户基本信息业务日志记录开始
			BusinessLogger.LoggerInfo(requestSerial, "tsc", "queryCustomerRetrieve", true, null, String.valueOf(new Date().getTime()));
			if(!StringUtil.isNotNullOrEmpty(memberId) && StringUtil.isNotNullOrEmpty(mobileNo)){
				HyMember hyMember  = hyMemberMapper.selectByMobileNo(mobileNo);
				if(hyMember!=null){
					memberId = hyMember.getMemberid();
				}
			}
			//查询手机号所属运营商信息
			SysTelecomOperatorAndHyMember memsys = memberService.queryMemberSys(memberId);
			JSONObject logJson = null;
			if(memsys != null){
				Configuration cfg = new Configuration(null, "environment.properties");
				String url = cfg.getValue("customerretrieve.url");
				StringBuilder sb = new StringBuilder();
				sb.append("?userAddress=").append(memsys.getMobileno());
				//调用运营商接口
				try {
					String req = url+sb.toString();
//					TraceLogger.debug("tsc-main-MemberManagerImpl:queryCustomerRetrieve celecom input params:" + req);
					//tsc调用接口跟踪日志记录开始
					TraceLogger.debugInterface("tsc", "celcom", "customerretrieve", Constant.ACTION_IN, req);
					JSONObject jsonHttp = HttpHelper.httpGet(req);
					//tsc调用接口跟踪日志记录结束
					TraceLogger.debugInterface("celcom", "tsc", "customerretrieve", Constant.ACTION_OUT, JSONObject.toJSONString(jsonHttp));
					if(jsonHttp != null){
						logJson = new JSONObject();
						JSONObject tempJson = new JSONObject();
						JSONObject result = jsonHttp.getJSONObject("outputCPResp");
						JSONArray array = result.getJSONArray("headerCustomerProfile");
						tempJson.put("headerCustomerProfile", array);
						tempJson.put("customerRowId", result.getString("customerRowId")!=null?result.getString("customerRowId"):"");
						tempJson.put("masterAccountNumber", result.getString("masterAccountNumber")!=null?result.getString("masterAccountNumber"):"");
						tempJson.put("masterAccountNumber", result.getString("masterAccountNumber")!=null?result.getString("masterAccountNumber"):"");
						tempJson.put("customerID", result.getString("customerID")!=null?result.getString("customerID"):"");
						tempJson.put("customerIDType", result.getString("customerIDType")!=null?result.getString("customerIDType"):"");
						JSONObject listOfServices = result.getJSONObject("listOfServices");
						tempJson.put("listOfServices",listOfServices);
						logJson.put("outputCPResp", tempJson);
//						TraceLogger.debug("tsc-main-MemberManagerImpl:queryCustomerRetrieve celecom output params:" + logJson.toJSONString());
						//tsc查询用户基本信息业务日志记录结束
						BusinessLogger.LoggerInfo(requestSerial, "tsc", "queryCustomerRetrieve", false,JSONObject.toJSONString(logJson), String.valueOf(new Date().getTime()));
						return jsonHttp.toJSONString();
					}
				} catch (Exception e) {
					ExceptionLogger.debug("tsc-main-MemberManagerImpl:queryCustomerRetrieve",e);
					e.printStackTrace();
				}
			}
			else{
				BusinessLogger.info("tsc-main-MemberManagerImpl:queryCustomerRetrieve 3qii output: memberid does not exist:" + json.getString("memberId"));
			}
			//tsc查询用户基本信息业务日志记录结束
			BusinessLogger.LoggerInfo(requestSerial, "tsc", "queryCustomerRetrieve", false,JSONObject.toJSONString(logJson), String.valueOf(new Date().getTime()));
		}
		return null;
	}

	/**
	 * 设置用户反馈信息
	 */
	@Override
	public String sendFeedback(String params) {
//		TraceLogger.debug("tsc-main-MemberManagerImpl:sendFeedback 3qii input params:" + params);
		
		JSONObject jsonReturn = new JSONObject();
		JSONObject json = (JSONObject) JSONObject.parse(params);
		if(!json.isEmpty()){
			String requestSerial = json.getString("requestSerial");
			//tsc设置用户反馈信息业务日志记录开始
			BusinessLogger.LoggerInfo(requestSerial, "tsc", "sendFeedback", true, null, String.valueOf(new Date().getTime()));
			//查询手机号所属运营商信息
			SysTelecomOperatorAndHyMember memsys = memberService.queryMemberSys(json.getString("memberId"));
			if(memsys != null){
				HyFeedback fb = new HyFeedback();
				fb.setFeedbackid(StringUtil.formatUUID(false));
				fb.setMemberid(json.getString("memberId"));
				fb.setMobileno(memsys.getMobileno());
				fb.setFeedback(json.getString("feedBack"));
				fb.setFeeddate(DateUtil.getTimeZoneDate(new Date(), memsys.getTimezone(), DateUtil.FORMAT2));
				fb.setCountryno(json.getString("countryNo"));
				fb.setToperatorid(json.getString("tOperatorId"));
				fb.setEmail(json.getString("email"));
				fb.setName(json.getString("username"));
				fb.setState(json.getString("state"));
				memberService.saveFeedback(fb);
				jsonReturn.put("retCode", Constant.RETURN_SUCCESS);
			}
			else{
				jsonReturn.put("retCode", Constant.RETURN_EMPTY);
			}
			//tsc设置用户反馈信息业务日志记录结束
			BusinessLogger.LoggerInfo(requestSerial, "tsc", "sendFeedback", false, JSONObject.toJSONString(jsonReturn), String.valueOf(new Date().getTime()));
		}
		else{
			jsonReturn.put("retCode", Constant.PARAMS_JSON_EMPTY);
		}
//		TraceLogger.debug("tsc-main-MemberManagerImpl:sendFeedback 3qii output params:" + jsonReturn.toJSONString());
		return jsonReturn.toJSONString();
	}
	
	
	@Override
	public String queryGameList(String params) {
		
		JSONObject jsonReturn = new JSONObject();
		JSONObject json = (JSONObject) JSONObject.parse(params);
		if(!json.isEmpty()){
			String requestSerial = json.getString("requestSerial");
			//tsc设置用户反馈信息业务日志记录开始
			BusinessLogger.LoggerInfo(requestSerial, "tsc", "queryGameList", true, null, String.valueOf(new Date().getTime()));
				HyGameNavigation  hgn = new HyGameNavigation();
				hgn.setCountryno(json.getString("countryNo"));
				hgn.setToperatorid(json.getString("tOperatorId"));
				hgn.setType(json.getString("type"));
				List<HyGameNavigation> list = memberService.queryGameList(hgn);
				if(list != null && !list.isEmpty() && 1<=list.size()){
					JSONArray array = new JSONArray();
					for (HyGameNavigation hyGameNavigation : list) {
						JSONObject obj = new JSONObject();
						obj.put("id", hyGameNavigation.getId());
						obj.put("name", hyGameNavigation.getName());
						obj.put("type", hyGameNavigation.getType());
						obj.put("recommendlevel", hyGameNavigation.getRecommendlevel());
						obj.put("downloadnum", hyGameNavigation.getDownloadnum());
						obj.put("orderno", hyGameNavigation.getOrderno());
						obj.put("url", hyGameNavigation.getUrl());
						array.add(obj);
					}
					jsonReturn.put("result", array);
				}				
				jsonReturn.put("retCode", Constant.RETURN_SUCCESS);
			//tsc设置用户反馈信息业务日志记录结束
			BusinessLogger.LoggerInfo(requestSerial, "tsc", "queryGameList", false, JSONObject.toJSONString(jsonReturn), String.valueOf(new Date().getTime()));
		}
		else{
			jsonReturn.put("retCode", Constant.PARAMS_JSON_EMPTY);
		}
		return jsonReturn.toJSONString();
	}
	
	
	@Override
	public String queryWebsiteList(String params) {

		JSONObject jsonReturn = new JSONObject();
		JSONObject json = (JSONObject) JSONObject.parse(params);
		if(!json.isEmpty()){
			String requestSerial = json.getString("requestSerial");
			//tsc设置用户反馈信息业务日志记录开始
			BusinessLogger.LoggerInfo(requestSerial, "tsc", "queryWebsiteList", true, null, String.valueOf(new Date().getTime()));
				HyWebsiteNavigation  hwn = new HyWebsiteNavigation();
				hwn.setCountryno(json.getString("countryNo"));
				hwn.setToperatorid(json.getString("tOperatorId"));
				List<HyWebsiteNavigation> list = memberService.queryWebsiteList(hwn);
				if(list != null && !list.isEmpty() && 1<=list.size()){
					JSONArray array1 = new JSONArray();
					JSONArray array2 = new JSONArray();
					for (HyWebsiteNavigation hyWebsiteNavigation : list) {
						if(Constant.WEBSITE_TYPE_PRIMARY.equals(hyWebsiteNavigation.getType())){
							JSONObject obj = new JSONObject();
							obj.put("id", hyWebsiteNavigation.getId());
							obj.put("name", hyWebsiteNavigation.getName());
							obj.put("type", hyWebsiteNavigation.getType());
							obj.put("orderno", hyWebsiteNavigation.getOrderno());
							obj.put("url", hyWebsiteNavigation.getUrl());
							array1.add(obj);
						}
						if(Constant.WEBSITE_TYPE_SECONDRY.equals(hyWebsiteNavigation.getType())){
							JSONObject obj = new JSONObject();
							obj.put("id", hyWebsiteNavigation.getId());
							obj.put("name", hyWebsiteNavigation.getName());
							obj.put("type", hyWebsiteNavigation.getType());
							obj.put("orderno", hyWebsiteNavigation.getOrderno());
							obj.put("url", hyWebsiteNavigation.getUrl());
							array2.add(obj);
						}						
					}
					jsonReturn.put("result1", array1);
					jsonReturn.put("result2", array2);
				}				
				jsonReturn.put("retCode", Constant.RETURN_SUCCESS);
			//tsc设置用户反馈信息业务日志记录结束
			BusinessLogger.LoggerInfo(requestSerial, "tsc", "queryWebsiteList", false, JSONObject.toJSONString(jsonReturn), String.valueOf(new Date().getTime()));
		}
		else{
			jsonReturn.put("retCode", Constant.PARAMS_JSON_EMPTY);
		}
		return jsonReturn.toJSONString();
	}
	
	
	@Override
	public String readGameIcon(String params) {
		JSONObject jsonReturn = new JSONObject();
		JSONObject json = (JSONObject) JSONObject.parse(params);
		if(!json.isEmpty()){
			String requestSerial = json.getString("requestSerial");
			//tsc设置用户反馈信息业务日志记录开始
			BusinessLogger.LoggerInfo(requestSerial, "tsc", "readGameIcon", true, null, String.valueOf(new Date().getTime()));
				HyGameNavigation  hgn = new HyGameNavigation();
				hgn.setCountryno(json.getString("countryNo"));
				hgn.setToperatorid(json.getString("tOperatorId"));
				hgn.setId(json.getString("id"));
				HyGameNavigation hyGameNavigation = memberService.queryGameIcon(hgn);
				if(hyGameNavigation != null ){
					jsonReturn.put("icon", hyGameNavigation.getIcon());
				}				
				jsonReturn.put("retCode", Constant.RETURN_SUCCESS);
			//tsc设置用户反馈信息业务日志记录结束
			BusinessLogger.LoggerInfo(requestSerial, "tsc", "readGameIcon", false, JSONObject.toJSONString(jsonReturn), String.valueOf(new Date().getTime()));
		}
		else{
			jsonReturn.put("retCode", Constant.PARAMS_JSON_EMPTY);
		}
		return jsonReturn.toJSONString();
	}
	
	
	@Override
	public String readWebsiteIcon(String params) {
		JSONObject jsonReturn = new JSONObject();
		JSONObject json = (JSONObject) JSONObject.parse(params);
		if(!json.isEmpty()){
			String requestSerial = json.getString("requestSerial");
			//tsc设置用户反馈信息业务日志记录开始
			BusinessLogger.LoggerInfo(requestSerial, "tsc", "readWebsiteIcon", true, null, String.valueOf(new Date().getTime()));
				HyWebsiteNavigation  hwn = new HyWebsiteNavigation();
				hwn.setCountryno(json.getString("countryNo"));
				hwn.setToperatorid(json.getString("tOperatorId"));
				hwn.setId(json.getString("id"));
				HyWebsiteNavigation hyWebsiteNavigation = memberService.queryWebsiteIcon(hwn);
				if(hyWebsiteNavigation != null ){
					jsonReturn.put("icon", hyWebsiteNavigation.getIcon());
				}				
				jsonReturn.put("retCode", Constant.RETURN_SUCCESS);
			//tsc设置用户反馈信息业务日志记录结束
			BusinessLogger.LoggerInfo(requestSerial, "tsc", "readWebsiteIcon", false, JSONObject.toJSONString(jsonReturn), String.valueOf(new Date().getTime()));
		}
		else{
			jsonReturn.put("retCode", Constant.PARAMS_JSON_EMPTY);
		}
		return jsonReturn.toJSONString();
	}

}
