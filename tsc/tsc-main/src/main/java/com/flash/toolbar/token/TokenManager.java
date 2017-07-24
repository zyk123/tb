package com.flash.toolbar.token;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.flash.toolbar.common.util.BusinessLogger;
import com.flash.toolbar.common.util.Configuration;
import com.flash.toolbar.common.util.Constant;
import com.flash.toolbar.common.util.HttpHelper;
import com.flash.toolbar.common.util.ParamPropertiesUtil;
import com.flash.toolbar.common.util.TraceLogger;
import com.flash.toolbar.redis.RedisOperation;

@Component("tokenManager")
public class TokenManager {

	@Autowired
	private RedisOperation redisOperation;

	public String queryToken() {
		BusinessLogger.LoggerInfo("", "tsc", "queryToken", true, null, String.valueOf(new Date().getTime()));
		try {
			String token = redisOperation.get("celcom.token");
			if(null!=token&&!"".equals(token)&&!"null".equals(token)){
				BusinessLogger.LoggerInfo("", "tsc", "queryToken exist", false, token, String.valueOf(new Date().getTime()));
				return token;
			}
			Configuration cfg = new Configuration(null,
					"environment.properties");
			String url = cfg.getValue("queryToken.url");
			String clientId = cfg.getValue("queryToken.clientId");
			String clientSecret = cfg.getValue("queryToken.clientSecret");
			StringBuilder sb = new StringBuilder();
			sb.append("grant_type=client_credentials&client_id=").append(clientId).append("&client_secret=").append(clientSecret).append("&scope=Location");
			// 调用运营商接口
			TraceLogger.debugInterface("tsc", "celcom", "queryToken", Constant.ACTION_IN, url+"?"+sb.toString());
            Map<String,String> map = new HashMap<String,String>();
            map.put("Host", ParamPropertiesUtil.getCelcomHost());
            map.put("Content-Type", "application/x-www-form-urlencoded");
			JSONObject jsonHttp = HttpHelper.httpPostWithHead(url,map,sb.toString());
			TraceLogger.debugInterface("celcom", "tsc", "queryToken", Constant.ACTION_OUT, null==jsonHttp ? null:JSONObject.toJSONString(jsonHttp));
			if (jsonHttp != null) {
				redisOperation.add("celcom.token", jsonHttp.getString("access_token"));
				redisOperation.expire("celcom.token", 30*60,TimeUnit.SECONDS);
				BusinessLogger.LoggerInfo("", "tsc", "queryToken", false, jsonHttp.getString("access_token"), String.valueOf(new Date().getTime()));
				return jsonHttp.getString("access_token");
			}else{
				BusinessLogger.LoggerInfo("", "tsc", "queryToken output is null", false, null, String.valueOf(new Date().getTime()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		BusinessLogger.LoggerInfo("", "tsc", "queryToken return null", false, null, String.valueOf(new Date().getTime()));
		return null;
	}
}
