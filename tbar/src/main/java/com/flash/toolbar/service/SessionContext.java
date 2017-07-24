/**
 * 
 */
package com.flash.toolbar.service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.flash.common.log.BusinessLogger;
import com.flash.common.util.Constant;
import com.flash.common.util.HttpUtil;
import com.flash.common.util.RandomString;
import com.flash.common.util.StringUtil;
import com.flash.common.util.ToolbarSession;
import com.flash.toolbar.portal.bean.ServiceUrlBean;
import com.flash.toolbar.redis.RedisOperation;

/**
 * Toolbar session管理
 * @author ocean
 *
 */
@Component("sessionContext")
public class SessionContext {
	
	@Autowired
	private RedisOperation redisOperation;
	
	@Autowired
	private ServiceUrlBean serviceUrlBean;

	public synchronized void AddSession(String sId,ToolbarSession session) {
		if (StringUtil.isNotNullOrEmpty(sId) && null!=session) {
			String sessionT = JSONObject.toJSONString(session);
			redisOperation.add(sId, sessionT);
			redisOperation.expire(sId, Long.parseLong(serviceUrlBean.getToolbarSessionOutTime()), TimeUnit.SECONDS);
		}
	}

	public synchronized void DelSession(String sId) {
		if (StringUtil.isNotNullOrEmpty(sId)) {
			redisOperation.del(sId);
		}
	}

	public synchronized ToolbarSession getSession(String sId) {
		if (StringUtil.isNull(sId)){
			return null;
		}else{
			String session = redisOperation.get(sId);
			if(StringUtil.isNull(session)||"null".equals(session)){
				String memberId = sId.substring(Constant.TB_SESSION_PRIFIX.length());
				String requestSerial = RandomString.getRandomNumber();
				String param = "memberId=" + memberId +"&requestSerial="+requestSerial;
				BusinessLogger.LoggerInfo(requestSerial, "tbar", "getSession", true, param, String.valueOf(new Date().getTime()));
				String responseStr = HttpUtil.sendPost(serviceUrlBean.getQueryToolbarSession(),param,requestSerial);
				BusinessLogger.LoggerInfo(requestSerial, "tbar", "getSession", false, responseStr, String.valueOf(new Date().getTime()));
				JSONObject requestJSON = JSONObject.parseObject(responseStr);
				JSONObject body = null;
				if(null == requestJSON){//尝试两次
					responseStr = HttpUtil.sendPost(serviceUrlBean.getQueryToolbarSession(),param,requestSerial);
					BusinessLogger.LoggerInfo(requestSerial, "tbar", "getSession second", false, responseStr, String.valueOf(new Date().getTime()));
					requestJSON = JSONObject.parseObject(responseStr);
					body = requestJSON.getJSONObject("body");
				}else{
					body = requestJSON.getJSONObject("body");
				}
				
				ToolbarSession toolbarSession = new ToolbarSession();
				toolbarSession.setCountryNo(body.getString("countryNo"));
				toolbarSession.setLanguage(body.getString("language"));
				toolbarSession.setMemberId(body.getString("userId"));
				toolbarSession.setServiceType(body.getString("serviceType"));
				toolbarSession.setTimeZone(body.getString("timeZone"));
				toolbarSession.settOperatorId(body.getString("tOperatorId"));
				toolbarSession.setPhoneNum(body.getString("phoneNum"));
				String sessionT = JSONObject.toJSONString(toolbarSession);
				redisOperation.add(sId, sessionT);
				redisOperation.expire(sId, Long.parseLong(serviceUrlBean.getToolbarSessionOutTime()), TimeUnit.SECONDS);
				return toolbarSession;
			}else{
				JSONObject jsonObj = (JSONObject) JSONObject.parse(session);
				ToolbarSession toolbarSession = new ToolbarSession();
				toolbarSession.setCountryNo(jsonObj.getString("countryNo"));
				toolbarSession.setLanguage(jsonObj.getString("language"));
				toolbarSession.setMemberId(jsonObj.getString("memberId"));
				toolbarSession.setServiceType(jsonObj.getString("serviceType"));
				toolbarSession.setTimeZone(jsonObj.getString("timeZone"));
				toolbarSession.settOperatorId(jsonObj.getString("tOperatorId"));
				toolbarSession.setPhoneNum(jsonObj.getString("phoneNum"));
				return toolbarSession;
			}
		}
	}

}
