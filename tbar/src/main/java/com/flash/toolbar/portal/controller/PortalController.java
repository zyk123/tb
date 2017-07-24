package com.flash.toolbar.portal.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.flash.common.log.BusinessLogger;
import com.flash.common.log.CommonExceptionLog;
import com.flash.common.log.TraceLogger;
import com.flash.common.util.Configuration;
import com.flash.common.util.Constant;
import com.flash.common.util.FileHelper;
import com.flash.common.util.HttpUtil;
import com.flash.common.util.RandomString;
import com.flash.common.util.StringUtil;
import com.flash.common.util.ToolbarSession;
import com.flash.common.util.http.IpUtil;
import com.flash.toolbar.portal.bean.ServiceUrlBean;
import com.flash.toolbar.portal.bean.SysOperatorSeg;
import com.flash.toolbar.redis.RedisOperation;
import com.flash.toolbar.service.SessionContext;

@Controller
@RequestMapping(value="/portal")
public class PortalController {
	
	@Autowired
	private ServiceUrlBean serviceUrlBean;
	
	@Autowired
	private SessionContext sessionContext;
	
	@Autowired
	private RedisOperation redisOperation;
	
	@RequestMapping(value="/getPage")
	@ResponseBody
	public void getPage(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "pageUrl",defaultValue = "") String pageUrl){
		try {
			TraceLogger.info("==============url=========="+pageUrl);
			String baseUrl = serviceUrlBean.getBaseUrl();
			String url = baseUrl + pageUrl;
			
			String responseStr = HttpUtil.sendGet(url,"");
			
			response.setContentType("text/plain");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("result", responseStr);
			
			String jsonpCallback = request.getParameter("jsonpCallback");
			String resultStr = jsonpCallback + "(" + resultJSON.toJSONString() + ")";
			
			PrintWriter out = response.getWriter();
			out.println(resultStr);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		TraceLogger.info("==============url end=========="+pageUrl);
	}
	
	/**
	 * 加载toolbar球
	 * @param response
	 * @param request
	 * @param pageUrl toolbar球页面相对路径
	 * @param requestUrl 当前浏览器地址
	 * @param mobilephone	手机号（加密）
	 * @param userAgent 浏览器UA
	 * @param referrer 前一个地址
	 */
	@RequestMapping(value="/getBallContent")
	@ResponseBody
	public void getBallContent(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "pageUrl",defaultValue = "") String pageUrl,
			@RequestParam(value = "requestUrl",defaultValue = "") String requestUrl,
			@RequestParam(value = "mobilephone",defaultValue = "") String mobilephone,
			@RequestParam(value = "userAgent",defaultValue = "") String userAgent,
			@RequestParam(value = "referrer",defaultValue = "") String referrer,
			@RequestParam(value = "language",defaultValue = "") String language,
			@RequestParam(value = "suid",defaultValue = "") String suid,
			@RequestParam(value = "isFirstUseToolbar",defaultValue = "") String isFirstUseToolbar,
			@RequestParam(value = "serviceType",defaultValue = "") String serviceType,
			@RequestParam(value="flowThreshold",defaultValue="")String flowThreshold,
			@RequestParam(value="isAlertOpen",defaultValue="")String isAlertOpen
			){
		try {
			String requestSerial = RandomString.getRandomNumber();
			//tbar加载toolbar球业务日志记录开始
			BusinessLogger.LoggerInfo(requestSerial, "tbar", "getBallContent", true, null, String.valueOf(new Date().getTime()));
			String tOperatorId = "", memberId = "", countryNo = "", timeZone="";
			if(StringUtil.isNotNullOrEmpty(suid)){			
				TraceLogger.info("=======getBallContent=====the user suid is:"+suid);
				ToolbarSession session = sessionContext.getSession(suid);
				
				tOperatorId = session.gettOperatorId();
				memberId = session.getMemberId();
		        countryNo = session.getCountryNo();
		        timeZone = session.getTimeZone();
			}				
			
			PrintWriter out = response.getWriter();
			
			String path = request.getContextPath();
			String baseUrl = serviceUrlBean.getBaseUrl();
			String url = baseUrl + pageUrl;
			TraceLogger.info("==============index.jsp url=========="+url);
			
			StringBuffer sb = new StringBuffer("?");
			sb.append("tOperatorId=");
			sb.append(URLEncoder.encode(URLEncoder.encode(tOperatorId, "UTF-8"),"UTF-8"));
			sb.append("&userId=");
			sb.append(URLEncoder.encode(URLEncoder.encode(memberId, "UTF-8"),"UTF-8"));
			sb.append("&timeZone=");
			sb.append(URLEncoder.encode(URLEncoder.encode(timeZone, "UTF-8"),"UTF-8"));
			sb.append("&language=");
			sb.append(URLEncoder.encode(URLEncoder.encode(language, "UTF-8"),"UTF-8"));
			sb.append("&countryNo=");
			sb.append(URLEncoder.encode(URLEncoder.encode(countryNo, "UTF-8"),"UTF-8"));
			sb.append("&suid=");
			sb.append(URLEncoder.encode(URLEncoder.encode(suid, "UTF-8"),"UTF-8"));
			sb.append("&isFirstUseToolbar=");
			sb.append(URLEncoder.encode(URLEncoder.encode(isFirstUseToolbar, "UTF-8"),"UTF-8"));
			sb.append("&requestSerial=");
			sb.append(URLEncoder.encode(URLEncoder.encode(requestSerial, "UTF-8"),"UTF-8"));
			sb.append("&serviceType=");
			sb.append(URLEncoder.encode(URLEncoder.encode(serviceType, "UTF-8"),"UTF-8"));
			sb.append("&flowThreshold=");
			sb.append(URLEncoder.encode(URLEncoder.encode(flowThreshold, "UTF-8"),"UTF-8"));
			sb.append("&isAlertOpen=");
			sb.append(URLEncoder.encode(URLEncoder.encode(isAlertOpen, "UTF-8"),"UTF-8"));
			
			String responseStr = HttpUtil.sendGet(url+sb.toString(),requestSerial);
			
			//tbar加载toolbar球业务日志记录结束
//			BusinessLogger.LoggerInfo(requestSerial, "tbar", "getBallContent", false, responseStr, String.valueOf(new Date().getTime()));
			BusinessLogger.LoggerInfo(requestSerial, "tbar", "getBallContent", false, pageUrl, String.valueOf(new Date().getTime()));
			
			response.setContentType("text/plain");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			
//			Map<String,String> map = new HashMap<String,String>();
//			map.put("result", responseStr);
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("result", responseStr);
			
			String jsonpCallback = request.getParameter("jsonpCallback");
			String resultStr = jsonpCallback + "(" + resultJSON.toJSONString() + ")";
			
			out.println(resultStr);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
//		TraceLogger.info("getBallContent end");
	}
	
	@RequestMapping(value="/isToolbarAvalible")
	@ResponseBody
	public void isToolbarAvalible(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "mobileNo",defaultValue = "") String mobileNo,
			@RequestParam(value = "userAgent",defaultValue = "") String userAgent){
		try {
			String requestSerial = RandomString.getRandomNumber();
			//tbar判断是否展示toolbar球业务日志记录开始
			BusinessLogger.LoggerInfo(requestSerial, "tbar", "isToolbarAvalible", true, null, String.valueOf(new Date().getTime()));
			String imei = request.getHeader("imei");
			String msisdn = request.getHeader("msisdn");//运营商传的手机号	
			String sgsnip = request.getHeader("sgsnip");
			String msip = request.getHeader("msip");
			String apn = request.getHeader("apn");
			TraceLogger.info("isToolbarAvalible imei:" + imei + ", msisdn="+msisdn + ", sgsnip=" + sgsnip + ", msip=" + msip + ", apn=" + apn +", userAgent="+userAgent);
			PrintWriter out = response.getWriter();
			response.setContentType("text/plain");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			JSONObject requestJSON = new JSONObject();
			StringBuffer sb = new StringBuffer("\n");
			if(StringUtil.isNotNullOrEmpty(msisdn)){
				String ita = redisOperation.isExistKey(Constant.TB_ISTOOLBARAVALIBLE_PRIFIX+msisdn)?redisOperation.get(Constant.TB_ISTOOLBARAVALIBLE_PRIFIX+msisdn):null;
				if(StringUtil.isNotNullOrEmpty(ita)&&!"null".equals(ita)){//从缓存中取
					JSONObject jsonObject = JSONObject.parseObject(ita);
					jsonObject.getJSONObject("body").put("isAlertOpen",false);
//					jsonObject.getJSONObject("body").put("isActivityAlertOpen",false);
					ita = JSONObject.toJSONString(jsonObject);
					sb.append("cache from "+Constant.TB_ISTOOLBARAVALIBLE_PRIFIX+msisdn+" is :"+ita);
//					BusinessLogger.info("cache from "+Constant.TB_ISTOOLBARAVALIBLE_PRIFIX+msisdn);
					requestJSON = JSONObject.parseObject(ita);
				}else{
							//sb.append("cache from judge cache start;\n");
		//					BusinessLogger.info("cache from judge cache");
							//是否需要进入tsc中操作
							boolean isGoTsc = true;
							//是否要走全流程
							boolean isAllProcess = false;
		
							String mobileNumber = msisdn;
							if(StringUtil.isNotNullOrEmpty(msisdn)){
								if(!msisdn.startsWith("6")){//6代表马来西亚，后期需要改成动态读取
									mobileNumber = "6"+msisdn;
								}
							}
							//sb.append("mobileNumber is :"+mobileNumber+";\n");
		//					BusinessLogger.info("mobileNumber is :"+mobileNumber);
							String countryno= "";
							String toperatorid = "";
							//获取redis中号段数据，找到国家编码和运营商ID------开始
							Set<String> set = redisOperation.isExistKey(Constant.SYS_OPERATORSEG_PRIFIX)?redisOperation.SMEMBERS(Constant.SYS_OPERATORSEG_PRIFIX):null;
							//sb.append(Constant.SYS_OPERATORSEG_PRIFIX+" is : "+(set!=null?"true":"false")+";\n");
		//					BusinessLogger.info(Constant.SYS_OPERATORSEG_PRIFIX+" is : "+(set!=null?"true":"false"));
							if(set!=null&&!"null".equals(set) && set.size()>0){
								Iterator it = set.iterator();
								while(it.hasNext()){
									SysOperatorSeg sos = JSONObject.parseObject((String)it.next(), SysOperatorSeg.class);
									String startNo = StringUtil.getStr(sos.getStartsegno());
									String endNo = StringUtil.getStr(sos.getEndsegno());
									if(mobileNumber.compareTo(startNo)>0 && mobileNumber.compareTo(endNo)<0){
										countryno = sos.getCountryno();
										toperatorid = sos.getToperatorid();
										break;
									}
								}						
		
							//国家编码和运营商ID------结束
							
							//设备黑名单判断-----开始
							Set<String> blackListDevice = redisOperation.isExistKey(Constant.HY_BLACKLISTDEVICE_PRIFIX+"_"+countryno+"_"+toperatorid)?redisOperation.SMEMBERS(Constant.HY_BLACKLISTDEVICE_PRIFIX+"_"+countryno+"_"+toperatorid):null;
							//sb.append(Constant.HY_BLACKLISTDEVICE_PRIFIX+"_"+countryno+"_"+toperatorid+" is :"+(blackListDevice!=null?"true":"false")+";\n");
		//					BusinessLogger.info(Constant.HY_BLACKLISTDEVICE_PRIFIX+"_"+countryno+"_"+toperatorid+" is :"+(blackListDevice!=null?"true":"false"));
							if(blackListDevice!=null&&!"null".equals(blackListDevice)){
									boolean isDeviceProhibit = false;
									Iterator itTemp = blackListDevice.iterator();
									while(itTemp.hasNext()){
										String name = (String)itTemp.next();
										if(StringUtil.isNotNullOrEmpty(name)){
											if(userAgent.indexOf(name)!=-1){
												isDeviceProhibit = true;
												break;
											}									
										}
									}
									if(isDeviceProhibit){
										isGoTsc = false;
							//设备黑名单判断------结束
									}else{
										//黑名单判断-----开始
//										Set<String>  blackList = redisOperation.isExistKey(Constant.HY_BLACKLIST_PRIFIX+"_"+countryno+"_"+toperatorid)?redisOperation.SMEMBERS(Constant.HY_BLACKLIST_PRIFIX+"_"+countryno+"_"+toperatorid):null;
										//sb.append(Constant.HY_BLACKLIST_PRIFIX+"_"+countryno+"_"+toperatorid+" is :"+(blackList!=null?"true":"false")+";\n");
		//								BusinessLogger.info(Constant.HY_BLACKLIST_PRIFIX+"_"+countryno+"_"+toperatorid+" is :"+(blackList!=null?"true":"false"));
										if(redisOperation.isExistKey(Constant.HY_BLACKLIST_PRIFIX+"_"+countryno+"_"+toperatorid)){
												if(redisOperation.isSetsValue(Constant.HY_BLACKLIST_PRIFIX+"_"+countryno+"_"+toperatorid, mobileNumber)){
													isGoTsc = false;
										//黑名单解析----结束
												}else{
													//黑名单号段判断-----开始
													Set<String> blackSection = redisOperation.isExistKey(Constant.HY_BLACKSECTION_PRIFIX+"_"+countryno+"_"+toperatorid)?redisOperation.SMEMBERS(Constant.HY_BLACKSECTION_PRIFIX+"_"+countryno+"_"+toperatorid):null;
													//sb.append(Constant.HY_BLACKSECTION_PRIFIX+"_"+countryno+"_"+toperatorid+" is :"+(blackSection!=null?"true":"false")+";\n");
		//											BusinessLogger.info(Constant.HY_BLACKSECTION_PRIFIX+"_"+countryno+"_"+toperatorid+" is :"+(blackSection!=null?"true":"false"));
													if(blackSection!=null &&!"null".equals(blackSection)){
														boolean isInBlackSection = false;
														Iterator itTemp1 = blackSection.iterator();
														while(itTemp1.hasNext()){
															String section = (String)itTemp1.next();
															if(StringUtil.isNotNullOrEmpty(section)){
																String[] sec = StringUtil.getStr(section).split(",");
																if(sec!=null && sec.length==2){
																	String startNo = sec[0];
																	String endNo = sec[1];
																	if(mobileNumber.compareTo(startNo)>0 && mobileNumber.compareTo(endNo)<0){
																		isInBlackSection = true;
																		break;
																	}
																}
															}
														}
														if(isInBlackSection){
															isGoTsc = false;
												    //黑名单号码判断----结束
														}else{
															//白名单号码判断------开始
//															Set<String> whiteList = redisOperation.isExistKey(Constant.HY_WHITELIST_PRIFIX+"_"+countryno+"_"+toperatorid)?redisOperation.SMEMBERS(Constant.HY_WHITELIST_PRIFIX+"_"+countryno+"_"+toperatorid):null;
															//sb.append(Constant.HY_WHITELIST_PRIFIX+"_"+countryno+"_"+toperatorid+" is :"+(whiteList!=null?"true":"false")+";\n");
		//													BusinessLogger.info(Constant.HY_WHITELIST_PRIFIX+"_"+countryno+"_"+toperatorid+" is :"+(whiteList!=null?"true":"false"));
															if(redisOperation.isExistKey(Constant.HY_WHITELIST_PRIFIX+"_"+countryno+"_"+toperatorid)){
																	boolean isInWhiteOrWhiteSection = false;
																	if(redisOperation.isSetsValue(Constant.HY_WHITELIST_PRIFIX+"_"+countryno+"_"+toperatorid, mobileNumber)){
																		isInWhiteOrWhiteSection = true;
															//白名单号码判断结束
																	}else{
																		//白名单号段判断-----开始
																		Set<String> whiteSection = redisOperation.isExistKey(Constant.HY_WHITESECTION_PRIFIX+"_"+countryno+"_"+toperatorid)?redisOperation.SMEMBERS(Constant.HY_WHITESECTION_PRIFIX+"_"+countryno+"_"+toperatorid):null;
																		//sb.append(Constant.HY_WHITESECTION_PRIFIX+"_"+countryno+"_"+toperatorid+" is :"+(whiteSection!=null?"true":"false")+";\n");
		//																BusinessLogger.info(Constant.HY_WHITESECTION_PRIFIX+"_"+countryno+"_"+toperatorid+" is :"+(whiteSection!=null?"true":"false"));
																		if(whiteSection!=null &&!"null".equals(whiteSection)){
																			Iterator itTemp2 = whiteSection.iterator();
																			while(itTemp2.hasNext()){
																				String section = (String)itTemp2.next();
																				if(StringUtil.isNotNullOrEmpty(section)){
																					String[] sec = StringUtil.getStr(section).split(",");
																					if(sec!=null && sec.length==2){
																						String startNo = sec[0];
																						String endNo = sec[1];
																						if(mobileNumber.compareTo(startNo)>0 && mobileNumber.compareTo(endNo)<0){
																							isInWhiteOrWhiteSection = true;
																							break;
																						}
																					}																			
																				}
																			}
																		}else{
																			isAllProcess = true;
																		}
																	//白名单号段------结束
																	}
																	if(!isInWhiteOrWhiteSection){
																		isGoTsc = false;
																	}	
															}else{
																isAllProcess = true;
															}
														}												
													}else{
														isAllProcess = true;
													}
		
												}
										}else{
											isAllProcess = true;
										}
									}
									
							}else{
								isAllProcess = true;
							}
					}else{
						isAllProcess = true;
					}		
					//sb.append("cache from judge cache end;\n");
					//sb.append("isGoTsc is :"+isGoTsc+";\n");
					//sb.append("isAllProcess is :"+isAllProcess+";\n");
					JSONObject body = null;
					if(!isGoTsc){
						body = new JSONObject();
						body.put("openToolbar", "1");
						body.put("retCode", "1011");
						body.put("retMsg", "MobileNo"+mobileNumber+" are blocked by cached data!");
						body.put("isFirstUseToolbar", false);
						body.put("serviceType", "");
						requestJSON.put("body", body);
						sb.append("MobileNo : "+mobileNumber+" was intercepted by cache,return result directly :"+JSONObject.toJSONString(requestJSON)+";\n");
//						BusinessLogger.LoggerInfo(requestSerial, "tbar", "isToolbarAvalible", false, JSONObject.toJSONString(body), String.valueOf(new Date().getTime()));
					}else{
						sb.append("goTsc start;\n");
						String postPrepaid = "";
						String memberId = "";
						String param = "mobileNo=" + msisdn +"&postPrepaid=" +postPrepaid +"&memberId=" +memberId+"&requestSerial="+requestSerial+"&userAgent="+userAgent+"&isAllProcess="+isAllProcess;
						String responseStr = HttpUtil.sendPost(serviceUrlBean.getIsToolbarAvalible(),param,requestSerial);
						
						sb.append("request TSC data is :"+responseStr+";\n");
						//tbar判断是否展示toolbar球业务日志记录结束
//						BusinessLogger.LoggerInfo(requestSerial, "tbar", "isToolbarAvalible", false, responseStr, String.valueOf(new Date().getTime()));
						
						requestJSON = JSONObject.parseObject(responseStr);
						
						if(null == requestJSON){//尝试两次
							responseStr = HttpUtil.sendPost(serviceUrlBean.getIsToolbarAvalible(),param,requestSerial);
							sb.append("request TSC data secondly is :"+responseStr+";\n");
//							BusinessLogger.LoggerInfo(requestSerial, "tbar", "isToolbarAvalible second", false, responseStr, String.valueOf(new Date().getTime()));
							requestJSON = JSONObject.parseObject(responseStr);
							if(requestJSON!=null){
							body = requestJSON.getJSONObject("body");
							}
						}else{
							body = requestJSON.getJSONObject("body");
						}
						sb.append("goTsc end;\n");
					}
					String suid = "";
					if(body!=null && "0".equals(body.getString("retCode"))&&"0".equals(body.getString("openToolbar"))){
						String tOperatorId = body.getString("tOperatorId");
						String retCode= body.getString("retCode");
						String retMsg = body.getString("retMsg");
						String userId = body.getString("userId");
						String timeZone = body.getString("timeZone");
						String language = body.getString("language");
						String openToolbar = body.getString("openToolbar");
						String countryNo = body.getString("countryNo");
						String serviceType = body.getString("serviceType");
						String flowThreshold = body.getString("flowThreshold");
						ToolbarSession toolbarSession = new ToolbarSession();
						toolbarSession.setCountryNo(countryNo);
						toolbarSession.setLanguage(language);
						toolbarSession.setMemberId(userId);
						toolbarSession.setServiceType(serviceType);
						toolbarSession.setTimeZone(timeZone);
						toolbarSession.settOperatorId(tOperatorId);
						toolbarSession.setPhoneNum(msisdn);
						toolbarSession.setFlowThreshold(flowThreshold);
						sessionContext.AddSession(Constant.TB_SESSION_PRIFIX+userId, toolbarSession);
						body.put("suid", Constant.TB_SESSION_PRIFIX+userId);
						redisOperation.add(Constant.TB_ISTOOLBARAVALIBLE_PRIFIX+msisdn, requestJSON.toJSONString());
						redisOperation.expire(Constant.TB_ISTOOLBARAVALIBLE_PRIFIX+msisdn, Long.parseLong(serviceUrlBean.getIsToolbarAvalibleOutTime()), TimeUnit.SECONDS);
					}				
				}
			}
			else{
				sb.append("MobileNo is empty,return fail directly;\n");
				JSONObject body = new JSONObject();
				body.put("retCode", "-1");
				body.put("openToolbar", "-1");
				requestJSON.put("body", body);
			}
			
			if(requestJSON==null){
				requestJSON = new JSONObject();
				JSONObject body = new JSONObject();
				body.put("openToolbar", "1");
				body.put("retCode", "1011");
				body.put("retMsg", "request TSC data is empty!");
				body.put("isFirstUseToolbar", false);
				body.put("serviceType", "");
				requestJSON.put("body", body);
				sb.append("requestJSON is null,return empty msg : "+JSONObject.toJSONString(requestJSON)+";\n");
			}
			BusinessLogger.LoggerInfo(requestSerial, "tbar", "isToolbarAvalible", false, sb.toString(), String.valueOf(new Date().getTime()));
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("result", JSONObject.toJSONString(requestJSON));
			
			String jsonpCallback = request.getParameter("jsonpCallback");
			String resultStr = jsonpCallback + "(" + resultJSON.toJSONString() + ")";
			
			out.println(resultStr);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			CommonExceptionLog.info("isToolbarAvalible", e);
		}
	}
	
	
	
	/**
	 * 调用用户关闭界面设置接口
	 * @param response
	 * @param request
	 * @param memberId
	 * @param tOperatorId
	 * @param countryNo
	 * @param type
	 */
	@RequestMapping(value="/setToolbarAvaliable")
	@ResponseBody
	public void setToolbarAvaliable(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "suid",defaultValue = "") String suid,
			@RequestParam(value = "type",defaultValue = "") int type){
		
		String requestSerial = RandomString.getRandomNumber();
		//tbar用户关闭toolbar业务日志记录开始
		BusinessLogger.LoggerInfo(requestSerial, "tbar", "setToolbarAvaliable", true, null, String.valueOf(new Date().getTime()));
		String tOperatorId = "", memberId = "", countryNo = "", timeZone="" , msisdn = "";
		if(StringUtil.isNotNullOrEmpty(suid)){			
			ToolbarSession session = sessionContext.getSession(suid);
			tOperatorId = session.gettOperatorId();
			memberId = session.getMemberId();
	        countryNo = session.getCountryNo();
	        timeZone = session.getTimeZone();
			msisdn = session.getPhoneNum();
		}			
		
		StringBuilder sb = new StringBuilder();
		sb.append("memberId=").append(memberId);
		sb.append("&tOperatorId=").append(tOperatorId);
		sb.append("&countryNo=").append(countryNo);
		sb.append("&type=").append(type);
		sb.append("&requestSerial=");
		sb.append(requestSerial);
		
		
		response.setContentType("text/plain");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		try {
			String responseStr = HttpUtil.sendPost(serviceUrlBean.getSetToolbarAvaliable(), sb.toString(),requestSerial);
			
			//tbar用户关闭toolbar业务日志记录结束
			BusinessLogger.LoggerInfo(requestSerial, "tbar", "setToolbarAvaliable", false, responseStr, String.valueOf(new Date().getTime()));
			redisOperation.del(Constant.TB_ISTOOLBARAVALIBLE_PRIFIX+msisdn);
			redisOperation.del(Constant.TB_ISTOOLBARAVALIBLE_PRIFIX+"6"+msisdn); //todo wangxiaoran

			BusinessLogger.info( "setToolbarAvaliable wangxiaoran test :  del redis  " + Constant.TB_ISTOOLBARAVALIBLE_PRIFIX+msisdn );
			BusinessLogger.info( "setToolbarAvaliable wangxiaoran test :  del redis  " + Constant.TB_ISTOOLBARAVALIBLE_PRIFIX+"6"+msisdn );

			JSONObject resultJSON = new JSONObject();
			resultJSON.put("result", responseStr);
			
			String jsonpCallback = request.getParameter("jsonpCallback");
			String resultStr = jsonpCallback + "(" + resultJSON.toJSONString() + ")";
		
			PrintWriter	out = response.getWriter();

			out.println(resultStr);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 调用用户关闭界面设置接口
	 * @param response
	 * @param request
	 * @param memberId
	 * @param tOperatorId
	 * @param countryNo
	 * @param type
	 */
	@RequestMapping(value="/judgeLimits")
	@ResponseBody
	public void judgeLimits(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "suid",defaultValue = "") String suid,
			@RequestParam(value = "promotionId",defaultValue = "") String promotionId,
			@RequestParam(value = "scoreNum",defaultValue = "") int scoreNum){
		
		String requestSerial = RandomString.getRandomNumber();
		//tbar用户关闭toolbar业务日志记录开始
		BusinessLogger.LoggerInfo(requestSerial, "tbar", "judgeLimits", true, null, String.valueOf(new Date().getTime()));
		String tOperatorId = "", memberId = "", countryNo = "", timeZone="";
		if(StringUtil.isNotNullOrEmpty(suid)){			
			ToolbarSession session = sessionContext.getSession(suid);
			tOperatorId = session.gettOperatorId();
			memberId = session.getMemberId();
	        countryNo = session.getCountryNo();
	        timeZone = session.getTimeZone();
		}			
		
		StringBuilder sb = new StringBuilder();
		sb.append("memberId=").append(memberId);
		sb.append("&tOperatorId=").append(tOperatorId);
		sb.append("&countryNo=").append(countryNo);
		sb.append("&scoreNum=").append(scoreNum);
		sb.append("&promotionId=").append(promotionId);
		sb.append("&requestSerial=");
		sb.append(requestSerial);
		
		
		response.setContentType("text/plain");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		try {
			String responseStr = HttpUtil.sendPost(serviceUrlBean.getJudgeLimits(), sb.toString(),requestSerial);
			
			//tbar用户关闭toolbar业务日志记录结束
			BusinessLogger.LoggerInfo(requestSerial, "tbar", "judgeLimits", false, responseStr, String.valueOf(new Date().getTime()));
			
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("result", responseStr);
			
			String jsonpCallback = request.getParameter("jsonpCallback");
			String resultStr = jsonpCallback + "(" + resultJSON.toJSONString() + ")";
		
			PrintWriter	out = response.getWriter();

			out.println(resultStr);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	/**
	 * 调用用户关闭界面设置接口
	 * @param response
	 * @param request
	 * @param memberId
	 * @param tOperatorId
	 * @param countryNo
	 * @param type
	 */
	@RequestMapping(value="/getDefaultSetting")
	@ResponseBody
	public void getDefaultSetting(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "suid",defaultValue = "") String suid){
		
		String requestSerial = RandomString.getRandomNumber();
		//tbar获取用户关闭toolbar信息业务日志记录开始
		BusinessLogger.LoggerInfo(requestSerial, "tbar", "getDefaultSetting", true, null, String.valueOf(new Date().getTime()));
		String tOperatorId = "", memberId = "", countryNo = "", timeZone="";
		if(StringUtil.isNotNullOrEmpty(suid)){			
			ToolbarSession session = sessionContext.getSession(suid);
			tOperatorId = session.gettOperatorId();
			memberId = session.getMemberId();
	        countryNo = session.getCountryNo();
	        timeZone = session.getTimeZone();
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("memberId=").append(memberId);
		sb.append("&tOperatorId=").append(tOperatorId);
		sb.append("&countryNo=").append(countryNo);
		sb.append("&requestSerial=");
		sb.append(requestSerial);
		
		response.setContentType("text/plain");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		
		try {
			String responseStr = HttpUtil.sendPost(serviceUrlBean.getGetDefaultSetting(), sb.toString(),requestSerial);
			
			//tbar获取用户关闭toolbar信息业务日志记录结束
			BusinessLogger.LoggerInfo(requestSerial, "tbar", "getDefaultSetting", false, responseStr, String.valueOf(new Date().getTime()));
			
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("result", responseStr);
			
			String jsonpCallback = request.getParameter("jsonpCallback");
			String resultStr = jsonpCallback + "(" + resultJSON.toJSONString() + ")";
			
			PrintWriter	out = response.getWriter();
			
			out.println(resultStr);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询用户剩余流量接口
	 * @param response
	 * @param request
	 * @param memberId
	 * @param tOperatorId
	 * @param countryNo
	 * @param type
	 */
	@RequestMapping(value="/queryUserFlow")
	@ResponseBody
	public void queryUserFlow(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "suid",defaultValue = "") String suid){
		
		String requestSerial = RandomString.getRandomNumber();
		//tbar查询用户剩余流量业务日志记录开始
		BusinessLogger.LoggerInfo(requestSerial, "tbar", "queryUserFlow", true, null, String.valueOf(new Date().getTime()));
		String tOperatorId = "", memberId = "", countryNo = "", timeZone="";
		if(StringUtil.isNotNullOrEmpty(suid)){			
			ToolbarSession session = sessionContext.getSession(suid);
			tOperatorId = session.gettOperatorId();
			memberId = session.getMemberId();
	        countryNo = session.getCountryNo();
	        timeZone = session.getTimeZone();
		}
		StringBuilder sb = new StringBuilder();
		sb.append("memberId=").append(memberId);
		sb.append("&tOperatorId=").append(tOperatorId);
		sb.append("&countryNo=").append(countryNo);
		sb.append("&requestSerial=");
		sb.append(requestSerial);
		
		response.setContentType("text/plain");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		try {
			String responseStr = HttpUtil.sendPost(serviceUrlBean.getQueryUserFlow(), sb.toString(),requestSerial);
			
			//tbar查询用户剩余流量业务日志记录结束
			BusinessLogger.LoggerInfo(requestSerial, "tbar", "queryUserFlow", false, responseStr, String.valueOf(new Date().getTime()));
			
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("result", responseStr);
			
			String jsonpCallback = request.getParameter("jsonpCallback");
			String resultStr = jsonpCallback + "(" + resultJSON.toJSONString() + ")";
		
			PrintWriter	out = response.getWriter();

			out.println(resultStr);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询流量提醒接口
	 * @param response
	 * @param request
	 * @param memberId
	 * @param tOperatorId
	 * @param countryNo
	 * @param type
	 */
	@RequestMapping(value="/queryFlowPortal")
	@ResponseBody
	public void queryFlowPortal(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "memberId",defaultValue = "") String memberId,
			@RequestParam(value = "tOperatorId",defaultValue = "") String tOperatorId,
			@RequestParam(value = "countryNo",defaultValue = "") String countryNo){
		String requestSerial = RandomString.getRandomNumber();
		//tbar查询流量提醒业务日志记录开始
		BusinessLogger.LoggerInfo(requestSerial, "tbar", "queryFlowPortal", true, null, String.valueOf(new Date().getTime()));
		
		StringBuilder sb = new StringBuilder();
		sb.append("memberId=").append(memberId);
		sb.append("&tOperatorId=").append(tOperatorId);
		sb.append("&countryNo=").append(countryNo);
		sb.append("&requestSerial=");
		sb.append(requestSerial);
		
		response.setContentType("text/plain");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		try {
			String responseStr = HttpUtil.sendPost(serviceUrlBean.getQueryFlowPortal(), sb.toString(),requestSerial);
			//tbar查询流量提醒业务日志记录结束
			BusinessLogger.LoggerInfo(requestSerial, "tbar", "queryFlowPortal", false, responseStr, String.valueOf(new Date().getTime()));
			
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("result", responseStr);
			
			String jsonpCallback = request.getParameter("jsonpCallback");
			String resultStr = jsonpCallback + "(" + resultJSON.toJSONString() + ")";
		
			PrintWriter	out = response.getWriter();

			out.println(resultStr);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询用户已订购套餐
	 * @param response
	 * @param request
	 * @param memberId
	 * @param tOperatorId
	 * @param countryNo
	 */
	@RequestMapping(value="/queryMyPackage")
	@ResponseBody
	public void queryMyPackage(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "suid",defaultValue = "") String suid){
		
		String requestSerial = RandomString.getRandomNumber();
		//tbar查询用户已订购套餐业务日志记录开始
		BusinessLogger.LoggerInfo(requestSerial, "tbar", "queryMyPackage", true, null, String.valueOf(new Date().getTime()));
		
		String tOperatorId = "", memberId = "", countryNo = "", timeZone="";
		if(StringUtil.isNotNullOrEmpty(suid)){			
			ToolbarSession session = sessionContext.getSession(suid);
			tOperatorId = session.gettOperatorId();
			memberId = session.getMemberId();
	        countryNo = session.getCountryNo();
	        timeZone = session.getTimeZone();
		}		
		
		StringBuilder sb = new StringBuilder();
		sb.append("memberId=").append(memberId);
		sb.append("&tOperatorId=").append(tOperatorId);
		sb.append("&countryNo=").append(countryNo);
		sb.append("&requestSerial=");
		sb.append(requestSerial);
		
		response.setContentType("text/plain");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		try {
			String responseStr = HttpUtil.sendPost(serviceUrlBean.getQueryMyPackage(), sb.toString(),requestSerial);
			//tbar查询用户已订购套餐业务日志记录结束
			BusinessLogger.LoggerInfo(requestSerial, "tbar", "queryMyPackage", false, responseStr, String.valueOf(new Date().getTime()));
			
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("result", responseStr);
			
			String jsonpCallback = request.getParameter("jsonpCallback");
			String resultStr = jsonpCallback + "(" + resultJSON.toJSONString() + ")";
		
			PrintWriter	out = response.getWriter();

			out.println(resultStr);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * 记录用户页面访问事件
	 * @param response
	 * @param request
	 * @param memberId
	 * @param tOperatorId
	 * @param countryNo
	 * @param userAgent
	 * @param referer
	 * @param curUrl
	 */
	@RequestMapping(value="/recordVisitAction")
	@ResponseBody
	public void recordVisitAction(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "suid",defaultValue = "") String suid,
			@RequestParam(value = "userAgent",defaultValue = "") String userAgent,
			@RequestParam(value = "referer",defaultValue = "") String referer,
			@RequestParam(value = "curUrl",defaultValue = "") String curUrl,
			@RequestParam(value = "browseUrl",defaultValue = "") String browseUrl){
		
		String requestSerial = RandomString.getRandomNumber();
		//tbar记录用户页面访问事件业务日志记录开始
		BusinessLogger.LoggerInfo(requestSerial, "tbar", "recordVisitAction", true, null, String.valueOf(new Date().getTime()));
		
		String tOperatorId = "", userId = "", countryNo = "", timeZone="", imei = "", msisdn = "", msip = "";
		if(StringUtil.isNotNullOrEmpty(suid)){			
			ToolbarSession session = sessionContext.getSession(suid);
			tOperatorId = session.gettOperatorId();
			userId = session.getMemberId();
	        countryNo = session.getCountryNo();
	        timeZone = session.getTimeZone();
//			imei = session.getAttribute("imei").toString();//?????--zhy
//			msisdn = session.getAttribute("msisdn").toString();
			msisdn = session.getPhoneNum();
//			msip = session.getAttribute("msip").toString();
		}
			
//		StringBuilder sb = new StringBuilder();
//		sb.append("memberId=").append(userId);
//		sb.append("&userAgent=").append(userAgent);
//        sb.append("&referer=").append(referer);
//        sb.append("&curUrl=").append(curUrl);
//        sb.append("&browseUrl=").append(browseUrl);        
//		sb.append("&tOperatorId=").append(tOperatorId);
//		sb.append("&countryNo=").append(countryNo);
//		sb.append("&timeZone=").append(timeZone);
//		sb.append("&ip=").append(IpUtil.getIpAddr(request));
//		sb.append("&requestSerial=");
//		sb.append(requestSerial);
//		sb.append("&imei=").append(imei);
//		sb.append("&msisdn=").append(msisdn);
//		sb.append("&msip=").append(msip);
        
		response.setContentType("text/plain");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		
		try {
			//String responseStr = HttpUtil.sendPost(serviceUrlBean.getRecordVisitAction(), sb.toString(),requestSerial);
			
			String responseStr = "false";
			
			Configuration cfg = new Configuration(null, "environment.properties");
			String fileURL = cfg.getValue("lottery.pageload.path");
			String splitSize = cfg.getValue("lottery.split.size");//文件分割大小配置
			BufferedWriter writer = null;
			try {
				DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
				String time = format.format(new Date());
//				fileURL = fileURL + time.substring(0, 8);
				String fileURLy = fileURL + time.substring(0, 6);
				File diry = new File(fileURLy);
				if (!diry.exists()) {
					diry.mkdirs();
				}
				String fileURLt = fileURLy + File.separator + time.substring(0, 8);
				File dirt = new File(fileURLt);
				if (!dirt.exists()) {
					dirt.mkdirs();
				}
				int fileIndex = FileHelper.getSplitFileIndex(fileURLt);
				String fileName = fileURLt + "/pageload_" +fileIndex+ ".csv";
				File file = new File(fileName);
				if (!file.exists()) {
					file.createNewFile();
				}
				//文件大于设定值则创建一个新文件
				long lSize = Long.parseLong(splitSize);
				if(file.length() > lSize){
					fileName = fileURLt + "/pageload_" + (fileIndex + 1) + ".csv";
					file = new File(fileName);
					if (!file.exists()) {
						file.createNewFile();
					}
				}

				StringBuilder sb = new StringBuilder();
				sb.append(msisdn.replaceAll(",", "#")).append(",");
				sb.append(imei.replaceAll(",", "#")).append(",");
				sb.append(msip.replaceAll(",", "#")).append(",");
				sb.append(userId.replaceAll(",", "#")).append(",");
				sb.append(tOperatorId.replaceAll(",", "#")).append(",");
				sb.append(countryNo.replaceAll(",", "#")).append(",");
				sb.append(browseUrl.replaceAll(",", "#")).append(",");
				sb.append(time.replaceAll(",", "#")).append(",");
				sb.append(timeZone.replaceAll(",", "#")).append(",");
				sb.append(IpUtil.getIpAddr(request).replaceAll(",", "#")).append(",");
				curUrl = requireFirtDomain(curUrl);
				sb.append(curUrl.replaceAll(",", "#")).append(",");
				referer = requireFirtDomain(referer);
				sb.append(referer.replaceAll(",", "#")).append(",");
				sb.append(userAgent.replaceAll(",", "#"));
				
				writer = new BufferedWriter(new FileWriter(file, true));
				writer.write(sb.toString());
				writer.newLine();
				writer.flush();
				responseStr = "true";
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if(null != writer){
					try {
						writer.close();
						writer = null;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
			//tbar记录用户页面访问事件业务日志记录结束
			BusinessLogger.LoggerInfo(requestSerial, "tbar", "recordVisitAction", false, responseStr, String.valueOf(new Date().getTime()));
			
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("result", responseStr);
			
			String jsonpCallback = request.getParameter("jsonpCallback");
			String resultStr = jsonpCallback + "(" + resultJSON.toJSONString() + ")";
		
			PrintWriter	out = response.getWriter();

			out.println(resultStr);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 记录用户点击页面访问事件
	 * @param response
	 * @param request
	 * @param memberId
	 * @param tOperatorId
	 * @param countryNo
	 * @param userAgent
	 * @param referer
	 * @param curUrl
	 */
	@RequestMapping(value="/recordClickEventAction")
	@ResponseBody
	public void recordClickEventAction(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "suid",defaultValue = "") String suid,
			@RequestParam(value = "userAgent",defaultValue = "") String userAgent,
			@RequestParam(value = "referer",defaultValue = "") String referer,
			@RequestParam(value = "curUrl",defaultValue = "") String curUrl,
			@RequestParam(value = "clickName",defaultValue = "") String clickName){
		
		String requestSerial = RandomString.getRandomNumber();
		//tbar记录用户页面访问事件业务日志记录开始
		BusinessLogger.LoggerInfo(requestSerial, "tbar", "recordClickEventAction", true, null, String.valueOf(new Date().getTime()));
		
		String tOperatorId = "", userId = "", countryNo = "", timeZone="", imei = "", msisdn = "", msip = "";
		if(StringUtil.isNotNullOrEmpty(suid)){			
			ToolbarSession session = sessionContext.getSession(suid);
			tOperatorId = session.gettOperatorId();
			userId = session.getMemberId();
	        countryNo = session.getCountryNo();
	        timeZone = session.getTimeZone();
//			imei = session.getAttribute("imei").toString();//?????--zhy
//			msisdn = session.getAttribute("msisdn").toString();
			msisdn = session.getPhoneNum();
//			msip = session.getAttribute("msip").toString();
		}
			
//		StringBuilder sb = new StringBuilder();
//		sb.append("memberId=").append(userId);
//		sb.append("&userAgent=").append(userAgent);
//        sb.append("&referer=").append(referer);
//        sb.append("&curUrl=").append(curUrl);
//        sb.append("&browseUrl=").append(clickName);        
//		sb.append("&tOperatorId=").append(tOperatorId);
//		sb.append("&countryNo=").append(countryNo);
//		sb.append("&timeZone=").append(timeZone);
//		sb.append("&ip=").append(IpUtil.getIpAddr(request));
//		sb.append("&requestSerial=");
//		sb.append(requestSerial);
//		sb.append("&imei=").append(imei);
//		sb.append("&msisdn=").append(msisdn);
//		sb.append("&msip=").append(msip);
        
		response.setContentType("text/plain");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		try {
			//String responseStr = HttpUtil.sendPost(serviceUrlBean.getRecordClickEventAction(), sb.toString(),requestSerial);
			String responseStr = "false";
			
			Configuration cfg = new Configuration(null, "environment.properties");
			String fileURL = cfg.getValue("lottery.clickevent.path");
			String splitSize = cfg.getValue("lottery.split.size");//文件分割大小配置
			BufferedWriter writer = null;
			try {
				DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
				String time = format.format(new Date());
//				fileURL = fileURL + time.substring(0, 8);
				String fileURLy = fileURL + time.substring(0, 6);
				File diry = new File(fileURLy);
				if (!diry.exists()) {
					diry.mkdirs();
				}
				String fileURLt = fileURLy + File.separator + time.substring(0, 8);
				File dirt = new File(fileURLt);
				if (!dirt.exists()) {
					dirt.mkdirs();
				}
				int fileIndex = FileHelper.getSplitFileIndex(fileURLt);
				String fileName = fileURLt + "/clickevent_" +fileIndex+ ".csv";
				File file = new File(fileName);
				if (!file.exists()) {
					file.createNewFile();
				}
				//文件大于设定值则创建一个新文件
				long lSize = Long.parseLong(splitSize);
				if(file.length() > lSize){
					fileName = fileURLt + "/clickevent_" + (fileIndex + 1) + ".csv";
					file = new File(fileName);
					if (!file.exists()) {
						file.createNewFile();
					}
				}

				StringBuilder sb = new StringBuilder();
				sb.append(msisdn.replaceAll(",", "#")).append(",");
				sb.append(imei.replaceAll(",", "#")).append(",");
				sb.append(msip.replaceAll(",", "#")).append(",");
				sb.append(userId.replaceAll(",", "#")).append(",");
				sb.append(tOperatorId.replaceAll(",", "#")).append(",");
				sb.append(countryNo.replaceAll(",", "#")).append(",");
				sb.append(clickName.replaceAll(",", "#")).append(",");
				sb.append(time.replaceAll(",", "#")).append(",");
				sb.append(timeZone.replaceAll(",", "#")).append(",");
				sb.append(IpUtil.getIpAddr(request).replaceAll(",", "#")).append(",");
				curUrl = requireFirtDomain(curUrl);
				sb.append(curUrl.replaceAll(",", "#")).append(",");
				referer = requireFirtDomain(referer);
				sb.append(referer.replaceAll(",", "#")).append(",");
				sb.append(userAgent.replaceAll(",", "#"));
				
				writer = new BufferedWriter(new FileWriter(file, true));
				writer.write(sb.toString());
				writer.newLine();
				writer.flush();
				responseStr = "true";
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if(null != writer){
					try {
						writer.close();
						writer = null;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
			//tbar记录用户页面访问事件业务日志记录结束
			BusinessLogger.LoggerInfo(requestSerial, "tbar", "recordClickEventAction", false, responseStr, String.valueOf(new Date().getTime()));
			
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("result", responseStr);
			
			String jsonpCallback = request.getParameter("jsonpCallback");
			String resultStr = jsonpCallback + "(" + resultJSON.toJSONString() + ")";
		
			PrintWriter	out = response.getWriter();

			out.println(resultStr);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取一级域名
	 * @param url
	 * @return
	 */
	private String requireFirtDomain(String url) {
	    String rtn = "";
	    try{
	    	Matcher m = Pattern.compile("^(ftps|ftp|http|https|android-app)://[^/]+").matcher(url);
	    	while(m.find()){
	    		rtn = m.group();
	    	}
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	    return rtn;
	}
	
	@RequestMapping(value="/sendFeedBack")
	@ResponseBody
	public void sendFeedBack(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "suid",defaultValue = "") String suid,
			@RequestParam(value = "username",defaultValue = "") String username,
			@RequestParam(value = "state",defaultValue = "") String state,
			@RequestParam(value = "email",defaultValue = "") String email,
			@RequestParam(value = "comments",defaultValue = "") String comments){
		
		String requestSerial = RandomString.getRandomNumber();
		//tbar 用户反馈业务日志记录开始
		BusinessLogger.LoggerInfo(requestSerial, "tbar", "sendFeedBack", true, null, String.valueOf(new Date().getTime()));
		String tOperatorId = "", userId = "", countryNo = "", timeZone="";
		if(StringUtil.isNotNullOrEmpty(suid)){			
			ToolbarSession session = sessionContext.getSession(suid);
			tOperatorId = session.gettOperatorId();
			userId = session.getMemberId();
	        countryNo = session.getCountryNo();
	        timeZone = session.getTimeZone();
		}
		StringBuilder sb = new StringBuilder();
		sb.append("memberId=").append(userId);
		sb.append("&username=").append(username);
        sb.append("&state=").append(state);
        sb.append("&email=").append(email);
        sb.append("&feedBack=").append(comments);        
		sb.append("&tOperatorId=").append(tOperatorId);
		sb.append("&countryNo=").append(countryNo);
		sb.append("&requestSerial=");
		sb.append(requestSerial);
        
		response.setContentType("text/plain");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		try {
			String responseStr = HttpUtil.sendPost(serviceUrlBean.getSendFeedBack(), sb.toString(),requestSerial);
			
			//tbar 用户反馈业务日志记录结束
			BusinessLogger.LoggerInfo(requestSerial, "tbar", "sendFeedBack", false, responseStr, String.valueOf(new Date().getTime()));
			
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("result", responseStr);
			
			String jsonpCallback = request.getParameter("jsonpCallback");
			String resultStr = jsonpCallback + "(" + resultJSON.toJSONString() + ")";
		
			PrintWriter	out = response.getWriter();

			out.println(resultStr);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="welHasShown")
	public void welHasShown(String suid,HttpServletRequest request,HttpServletResponse response) {
		String requestSerial = RandomString.getRandomNumber();
		BusinessLogger.LoggerInfo(requestSerial, "tbar", "welHasShown", true, null, String.valueOf(new Date().getTime()));
		String respStr = "";
		if(StringUtils.isNotEmpty(suid)){
			try {
				StringBuilder sb = new StringBuilder();
				ToolbarSession session = sessionContext.getSession(suid);
				sb.append("memberId=").append(session.getMemberId())
				.append("&requestSerial=").append(requestSerial)
				.append("&tOperatorId=").append(session.gettOperatorId())
				.append("&countryNo=").append(session.getCountryNo());
				respStr = HttpUtil.sendPost(serviceUrlBean.getWelHasShown(), sb.toString(),requestSerial);
				if("SUCCESS".equals(respStr)){					
					JSONObject jsonObject = JSONObject.parseObject(redisOperation.get(Constant.TB_ISTOOLBARAVALIBLE_PRIFIX + session.getPhoneNum()));
					jsonObject.getJSONObject("body").put("isFirstUseToolbar", false);	
					redisOperation.del(Constant.TB_ISTOOLBARAVALIBLE_PRIFIX+session.getPhoneNum());
					redisOperation.add(Constant.TB_ISTOOLBARAVALIBLE_PRIFIX+session.getPhoneNum(), jsonObject.toJSONString());
					redisOperation.expire(Constant.TB_ISTOOLBARAVALIBLE_PRIFIX+session.getPhoneNum(), Long.parseLong(serviceUrlBean.getIsToolbarAvalibleOutTime()), TimeUnit.SECONDS);
				}
				String jsonpCallback = request.getParameter("jsonpCallback");
				PrintWriter out = response.getWriter();
				out.print(jsonpCallback + "('" + respStr +  "')");
				out.flush();
				out.close();
			} catch (IOException e) {
				BusinessLogger.error("tbar Exception" + e.getMessage());
			}
		}
		BusinessLogger.LoggerInfo(requestSerial, "tbar", "welHasShown", false, respStr, String.valueOf(new Date().getTime()));
	}
	
	
	@RequestMapping(value="saveReceiverInfo")
	public void saveReceiverInfo(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "suid",defaultValue = "") String suid,
			@RequestParam(value = "name",defaultValue = "") String name,
			@RequestParam(value = "gender",defaultValue = "") String gender,
			@RequestParam(value = "id",defaultValue = "") String id,
			@RequestParam(value = "email",defaultValue = "") String email,
			@RequestParam(value = "address",defaultValue = "") String address,
			@RequestParam(value = "receiverid",defaultValue = "") String receiverid,
			@RequestParam(value = "remark",defaultValue = "") String remark
			) {
		String requestSerial = RandomString.getRandomNumber();
		BusinessLogger.LoggerInfo(requestSerial, "tbar", "saveReceiverInfo", true, null, String.valueOf(new Date().getTime()));
		String respStr = "";
		if(StringUtils.isNotEmpty(suid)){
			try {
				StringBuilder sb = new StringBuilder();
				ToolbarSession session = sessionContext.getSession(suid);
				String memberId = session.getMemberId();
				String tOperatorId = session.gettOperatorId();
				String countryNo = session.getCountryNo();
				sb.append("memberId=").append(memberId)
				.append("&requestSerial=").append(requestSerial)
				.append("&tOperatorId=").append(tOperatorId)
				.append("&countryNo=").append(countryNo)
				.append("&receiverName=").append(name)
				.append("&gender=").append(gender)
				.append("&identityCard=").append(id)
				.append("&email=").append(email)
				.append("&age=").append("")
				.append("&address=").append(address)
				.append("&receiverid=").append(receiverid)
				.append("&remark=").append(remark);
				respStr = HttpUtil.sendPost(serviceUrlBean.getSaveReceiverInfo(), sb.toString(),requestSerial);
				
				JSONObject resultJSON = new JSONObject();
				resultJSON.put("result", respStr);
				
				String jsonpCallback = request.getParameter("jsonpCallback");
				String resultStr = jsonpCallback + "(" + resultJSON.toJSONString() + ")";				
				
				PrintWriter out = response.getWriter();
				out.print(resultStr);
				out.flush();
				out.close();
			} catch (IOException e) {
				BusinessLogger.error("tbar Exception" + e.getMessage());
			}
		}
		BusinessLogger.LoggerInfo(requestSerial, "tbar", "saveReceiverInfo", false, respStr, String.valueOf(new Date().getTime()));
	}
	
	@RequestMapping(value="updateManOrRobotInfo")
	public void updateManOrRobotInfo(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "suid",defaultValue = "") String suid,
			@RequestParam(value = "shipId",defaultValue = "") String shipId,
			@RequestParam(value = "status",defaultValue = "") String status,
			@RequestParam(value = "promotionprizeid",defaultValue = "") String promotionprizeid
			) {
		String requestSerial = RandomString.getRandomNumber();
		BusinessLogger.LoggerInfo(requestSerial, "tbar", "updateManOrRobotInfo", true, null, String.valueOf(new Date().getTime()));
		String respStr = "";
		if(StringUtils.isNotEmpty(suid)){
			try {
				StringBuilder sb = new StringBuilder();
				ToolbarSession session = sessionContext.getSession(suid);
				String tOperatorId = session.gettOperatorId();
				String countryNo = session.getCountryNo();
				sb.append("requestSerial=").append(requestSerial)
				.append("&tOperatorId=").append(tOperatorId)
				.append("&countryNo=").append(countryNo)
				.append("&shipId=").append(shipId)
				.append("&status=").append(status)
				.append("&promotionprizeid=").append(promotionprizeid);
				respStr = HttpUtil.sendPost(serviceUrlBean.getUpdateManOrRobotInfo(), sb.toString(),requestSerial);
				
				JSONObject resultJSON = new JSONObject();
				resultJSON.put("result", respStr);
				
				String jsonpCallback = request.getParameter("jsonpCallback");
				String resultStr = jsonpCallback + "(" + resultJSON.toJSONString() + ")";				
				
				PrintWriter out = response.getWriter();
				out.print(resultStr);
				out.flush();
				out.close();
			} catch (IOException e) {
				BusinessLogger.error("tbar Exception" + e.getMessage());
			}
		}
		BusinessLogger.LoggerInfo(requestSerial, "tbar", "updateManOrRobotInfo", false, respStr, String.valueOf(new Date().getTime()));
	}	
	
	@RequestMapping(value="getAllIcon")
	public  void getAllIcon(String suid,HttpServletRequest request,HttpServletResponse response){
		String requestSerial = RandomString.getRandomNumber();
		BusinessLogger.LoggerInfo(requestSerial, "tbar", "getAllIcon", true, null, String.valueOf(new Date().getTime()));
		String resp = "";
		ToolbarSession session = sessionContext.getSession(suid);
		if("Prepaid".equalsIgnoreCase(session.getServiceType())&&redisOperation.isExistKey("pre_sys_icon")){
			try {
				resp = redisOperation.get("pre_sys_icon");
				String jsonpCallback = request.getParameter("jsonpCallback");
				PrintWriter out = response.getWriter();
				out.print(jsonpCallback + "(" + resp + ")");
				out.flush();
				out.close();
			} catch (IOException e) {
				BusinessLogger.error("tbar Exception" + e.getMessage());
			}
		}else if("Postpaid".equalsIgnoreCase(session.getServiceType())&&redisOperation.isExistKey("post_sys_icon")){
			try {
				resp = redisOperation.get("post_sys_icon");
				String jsonpCallback = request.getParameter("jsonpCallback");
				PrintWriter out = response.getWriter();
				out.print(jsonpCallback + "(" + resp + ")");
				out.flush();
				out.close();
			} catch (IOException e) {
				BusinessLogger.error("tbar Exception" + e.getMessage());
			}
		}else{
			try {
				StringBuilder sb = new StringBuilder();
				sb.append("?requestSerial=").append(requestSerial)
				.append("&serviceType=").append("Prepaid".equalsIgnoreCase(session.getServiceType())?1:2)
				.append("&tOperatorId=").append(session.gettOperatorId())
				.append("&countryNo=").append(session.getCountryNo());
				resp = HttpUtil.sendPost(serviceUrlBean.getAllIcon(), sb.toString(), requestSerial);
				if("Prepaid".equalsIgnoreCase(session.getServiceType())){
					redisOperation.add("pre_sys_icon", resp);
					redisOperation.expire("pre_sys_icon", Long.parseLong(serviceUrlBean.getIconExpireTime()), TimeUnit.DAYS);
				}else{
					redisOperation.add("post_sys_icon", resp);
					redisOperation.expire("post_sys_icon", Long.parseLong(serviceUrlBean.getIconExpireTime()), TimeUnit.DAYS);
				}
				String jsonpCallback = request.getParameter("jsonpCallback");
				PrintWriter out = response.getWriter();
				out.print(jsonpCallback + "(" + resp + ")");
				out.flush();
				out.close();
			} catch (Exception e) {
				BusinessLogger.error("tbar Exception" + e.getMessage());
			}
		}		
		BusinessLogger.LoggerInfo(requestSerial, "tbar", "getAllIcon", false, null, String.valueOf(new Date().getTime()));
	}
	
	/**
	 * 获取奖品
	 * @param suid
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="requirePrize")
	public  void requirePrize(HttpServletRequest request,HttpServletResponse response,String suid,String promotionId){
		String requestSerial = RandomString.getRandomNumber();
		BusinessLogger.LoggerInfo(requestSerial, "tbar", "getRequirePrize", true, null, String.valueOf(new Date().getTime()));
		String resp = "";
		try {
			ToolbarSession session = sessionContext.getSession(suid);			
			StringBuilder sb = new StringBuilder();
			sb.append("&requestSerial=").append(requestSerial)
			.append("&tOperatorId=").append(session.gettOperatorId())
			.append("&countryNo=").append(session.getCountryNo())
			.append("&promotionId=").append(promotionId)
			.append("&memberId=").append(session.getMemberId());
			resp = HttpUtil.sendPost(serviceUrlBean.getRequirePrize(), sb.toString(), requestSerial);
			String jsonpCallback = request.getParameter("jsonpCallback");
			PrintWriter out = response.getWriter();
			out.print(jsonpCallback + "(" + resp + ")");
			out.flush();
			out.close();
		} catch (Exception e) {
			BusinessLogger.error("tbar Exception" + e.getMessage());
		}
//		}		
		BusinessLogger.LoggerInfo(requestSerial, "tbar", "getRequirePrize", false, null, String.valueOf(new Date().getTime()));
	}
	
	
	/**
	 * 获取奖品
	 * @param suid
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="phoneGameLuckDraw")
	public  void phoneGameLuckDraw(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "suid",defaultValue = "") String suid,
			@RequestParam(value = "promotionId",defaultValue = "") String promotionId,
			@RequestParam(value = "scoreNum",defaultValue = "") String scoreNum){
		String requestSerial = RandomString.getRandomNumber();
		BusinessLogger.LoggerInfo(requestSerial, "tbar", "luckDraw", true, null, String.valueOf(new Date().getTime()));
		String resp = "";
		try {
			ToolbarSession session = sessionContext.getSession(suid);
			String phoneNum = session.getPhoneNum();
			if(StringUtil.isNotNullOrEmpty(phoneNum)){
				if(!phoneNum.startsWith("6")){//6代表马来西亚，后期需要改成动态读取
					phoneNum = "6"+phoneNum;
				}
			}			
			StringBuilder sb = new StringBuilder();
			sb.append("&requestSerial=").append(requestSerial)
			.append("&tOperatorId=").append(session.gettOperatorId())
			.append("&countryNo=").append(session.getCountryNo())
			.append("&promotionId=").append(promotionId)
			.append("&memberId=").append(session.getMemberId())
			.append("&scoreNum=").append(scoreNum)
			.append("&mobileNo=").append(phoneNum);
			resp = HttpUtil.sendPost(serviceUrlBean.getPhoneGameLuckDraw(), sb.toString(), requestSerial);
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("result", resp);
			
			String jsonpCallback = request.getParameter("jsonpCallback");
			String resultStr = jsonpCallback + "(" + resultJSON.toJSONString() + ")";		
			PrintWriter out = response.getWriter();
			out.print(resultStr);
			out.flush();
			out.close();
		} catch (Exception e) {
			BusinessLogger.error("tbar Exception" + e.getMessage());
		}
//		}		
		BusinessLogger.LoggerInfo(requestSerial, "tbar", "luckDraw", false, null, String.valueOf(new Date().getTime()));
	}

	@RequestMapping(value="activityHasShown")
	public void activityHasShown(String suid,HttpServletRequest request,HttpServletResponse response) {
		String requestSerial = RandomString.getRandomNumber();
		BusinessLogger.LoggerInfo(requestSerial, "tbar", "activityHasShown", true, null, String.valueOf(new Date().getTime()));
		String respStr = "";
		if(StringUtils.isNotEmpty(suid)){
			try {
				StringBuilder sb = new StringBuilder();
				ToolbarSession session = sessionContext.getSession(suid);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
				sdf.setTimeZone(TimeZone.getTimeZone("GMT"+session.getTimeZone()));
				String date = sdf.format(new Date());
				sb.append("memberId=").append(session.getMemberId())
						.append("&requestSerial=").append(requestSerial)
						.append("&tOperatorId=").append(session.gettOperatorId())
						.append("&countryNo=").append(session.getCountryNo())
						.append("&date=").append(date);
				respStr = HttpUtil.sendPost(serviceUrlBean.getActivityHasShown(), sb.toString(),requestSerial);
				String jsonpCallback = request.getParameter("jsonpCallback");
				PrintWriter out = response.getWriter();
				out.print(jsonpCallback + "('" + respStr +  "')");
				out.flush();
				out.close();
			} catch (Exception e) {
				BusinessLogger.error("tbar Exception" + e.getMessage());
			}
		}
		BusinessLogger.LoggerInfo(requestSerial, "tbar", "activityHasShown", false, respStr, String.valueOf(new Date().getTime()));
	}

	@RequestMapping(value="isActivityPopup")
	public void isActivityPopup(String suid,HttpServletRequest request,HttpServletResponse response){
		String requestSerial = RandomString.getRandomNumber();
		BusinessLogger.LoggerInfo(requestSerial, "tbar", "isActivityPopup", true, null, String.valueOf(new Date().getTime()));
		String respStr = "";
		if(StringUtils.isNotEmpty(suid)){
			try {
				StringBuilder sb = new StringBuilder();
				ToolbarSession session = sessionContext.getSession(suid);
				String serviceType = session.getServiceType();
				String memberId =  session.getMemberId();
				sb.append("mobileNo=").append(session.getPhoneNum())
						.append("&requestSerial=").append(requestSerial)
						.append("&serviceType=").append(serviceType)
						.append("&memberId=").append(memberId);
				respStr = HttpUtil.sendPost(serviceUrlBean.getIsActivityPopup(), sb.toString(),requestSerial);
				String jsonpCallback = request.getParameter("jsonpCallback");
				PrintWriter out = response.getWriter();
				out.print(jsonpCallback + "('" + respStr +  "')");
				out.flush();
				out.close();
			} catch (Exception e) {
				BusinessLogger.error("tbar Exception" + e.getMessage());
			}
		}
		BusinessLogger.LoggerInfo(requestSerial, "tbar", "isActivityPopup", false, respStr, String.valueOf(new Date().getTime()));
	}
	
	@RequestMapping(value="luckyRestOnedaytimes")
	public void luckyRestOnedaytimes(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "suid",defaultValue = "") String suid,
			@RequestParam(value = "promotionId",defaultValue = "") String promotionId
			){
		String requestSerial = RandomString.getRandomNumber();
		BusinessLogger.LoggerInfo(requestSerial, "tbar", "luckyRestOnedaytimes", true, null, String.valueOf(new Date().getTime()));
		String restOnedaytimes = "";
		if(StringUtils.isNotEmpty(suid)){
			try {
				ToolbarSession session = sessionContext.getSession(suid);
				String tOperatorId = session.gettOperatorId();
				String userId = session.getMemberId();
				String timeZone = session.getTimeZone();
				String language = session.getLanguage();
				String countryNo = session.getCountryNo();
				StringBuilder sb = new StringBuilder();
				sb.append("memberId=").append(userId);
				sb.append("&tOperatorId=").append(tOperatorId);
				sb.append("&countryNo=").append(countryNo);
				sb.append("&requestSerial=").append(requestSerial);	
				sb.append("&promotionId=").append(promotionId);	
				String responseUserStr = HttpUtil.sendPost(serviceUrlBean.getRequireRestOnedaytimes(), sb.toString(),requestSerial);
				if(StringUtil.isNotNullOrEmpty(responseUserStr)){
					JSONObject obj = JSONObject.parseObject(responseUserStr);
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
				String jsonpCallback = request.getParameter("jsonpCallback");
				PrintWriter out = response.getWriter();
				out.print(jsonpCallback + "('" + restOnedaytimes +  "')");
				out.flush();
				out.close();
			} catch (Exception e) {
				BusinessLogger.error("tbar Exception" + e.getMessage());
			}
		}
		BusinessLogger.LoggerInfo(requestSerial, "tbar", "luckyRestOnedaytimes", false, restOnedaytimes, String.valueOf(new Date().getTime()));
	}
	
	
	@RequestMapping(value="readGameIcon")
	public void readGameIcon(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "suid",defaultValue = "") String suid,
			@RequestParam(value = "id",defaultValue = "") String id
			){
		String requestSerial = RandomString.getRandomNumber();
		BusinessLogger.LoggerInfo(requestSerial, "tbar", "readGameIcon", true, null, String.valueOf(new Date().getTime()));
		String restOnedaytimes = "";
		if(StringUtils.isNotEmpty(suid)){
			try {
				ToolbarSession session = sessionContext.getSession(suid);
				String tOperatorId = session.gettOperatorId();
				String countryNo = session.getCountryNo();
				StringBuilder sb = new StringBuilder();
				sb.append("tOperatorId=").append(tOperatorId);
				sb.append("&countryNo=").append(countryNo);
				sb.append("&requestSerial=").append(requestSerial);	
				sb.append("&id=").append(id);	
				String responseUserStr = HttpUtil.sendPost(serviceUrlBean.getQueryGameIcon(), sb.toString(),requestSerial);
				byte[] bs =null;
				if(StringUtil.isNotNullOrEmpty(responseUserStr)){
					JSONObject obj = JSONObject.parseObject(responseUserStr);
					if(obj!=null){
						JSONObject objBody = obj.getJSONObject("body");
						String  resultCode = obj.getString("resultCode");
						if(StringUtil.isNotNullOrEmpty(resultCode) && objBody!=null && "0".equals(resultCode)){
							JSONObject resultObj = objBody.getJSONObject("result");
							String retCode = resultObj.getString("retCode");
							if(StringUtil.isNotNullOrEmpty(retCode) && "0".equals(retCode) ){
								bs = resultObj.getBytes("icon");
							}
						}
					}
				}
				ServletOutputStream out = response.getOutputStream();
				int off = 0;
				int len = 1024;
				do{
					out.write(bs, off, len);
					off = off + len;
				}while(off+len < bs.length);
				out.flush();
				out.close();
			} catch (Exception e) {
				BusinessLogger.error("tbar Exception" + e.getMessage());
			}
		}
		BusinessLogger.LoggerInfo(requestSerial, "tbar", "readGameIcon", false, restOnedaytimes, String.valueOf(new Date().getTime()));
	}	
	
	
	
	@RequestMapping(value="readWebsiteIcon")
	public void readWebsiteIcon(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "suid",defaultValue = "") String suid,
			@RequestParam(value = "id",defaultValue = "") String id
			){
		String requestSerial = RandomString.getRandomNumber();
		BusinessLogger.LoggerInfo(requestSerial, "tbar", "readWebsiteIcon", true, null, String.valueOf(new Date().getTime()));
		String restOnedaytimes = "";
		if(StringUtils.isNotEmpty(suid)){
			try {
				ToolbarSession session = sessionContext.getSession(suid);
				String tOperatorId = session.gettOperatorId();
				String countryNo = session.getCountryNo();
				StringBuilder sb = new StringBuilder();
				sb.append("tOperatorId=").append(tOperatorId);
				sb.append("&countryNo=").append(countryNo);
				sb.append("&requestSerial=").append(requestSerial);	
				sb.append("&id=").append(id);	
				String responseUserStr = HttpUtil.sendPost(serviceUrlBean.getQueryWebsiteIcon(), sb.toString(),requestSerial);
				byte[] bs =null;
				if(StringUtil.isNotNullOrEmpty(responseUserStr)){
					JSONObject obj = JSONObject.parseObject(responseUserStr);
					if(obj!=null){
						JSONObject objBody = obj.getJSONObject("body");
						String  resultCode = obj.getString("resultCode");
						if(StringUtil.isNotNullOrEmpty(resultCode) && objBody!=null && "0".equals(resultCode)){
							JSONObject resultObj = objBody.getJSONObject("result");
							String retCode = resultObj.getString("retCode");
							if(StringUtil.isNotNullOrEmpty(retCode) && "0".equals(retCode) ){
								bs = resultObj.getBytes("icon");
							}
						}
					}
				}
				ServletOutputStream out = response.getOutputStream();
				int off = 0;
				int len = 1024;
				do{
					out.write(bs, off, len);
					off = off + len;
				}while(off+len < bs.length);
				out.flush();
				out.close();
			} catch (Exception e) {
				BusinessLogger.error("tbar Exception" + e.getMessage());
			}
		}
		BusinessLogger.LoggerInfo(requestSerial, "tbar", "readWebsiteIcon", false, restOnedaytimes, String.valueOf(new Date().getTime()));
	}		

    /**
     * 返回成功信息
     * @param body
     * @return
     */
    public String unifyFailReturn(String body) {
        JSONObject responseJson = new JSONObject();
        responseJson.put("resultCode", "-1");
        responseJson.put("resultMsg", "failed");
        responseJson.put("body", body);
        return responseJson.toString();
    }
    
}
