package com.flash.toolbar.portal.controller;

import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.flash.common.log.BusinessLogger;
import com.flash.common.util.HttpUtil;
import com.flash.common.util.RandomString;
import com.flash.common.util.StringUtil;
import com.flash.common.util.ToolbarSession;
import com.flash.toolbar.portal.bean.ServiceUrlBean;
import com.flash.toolbar.service.SessionContext;

@Controller
@RequestMapping(value="/package")
public class PackageController {
	@Autowired
	private ServiceUrlBean serviceUrlBean;	
	
	@Autowired
	private SessionContext sessionContext;
	
	@RequestMapping(value="/subScribePackage")
	@ResponseBody
	public void subScribePackage(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "fGNo",defaultValue = "") String fGNo,
			@RequestParam(value = "suid",defaultValue = "") String suid
			){
		try {
			String requestSerial = RandomString.getRandomNumber();
			//tbar套餐订购业务日志记录开始
			BusinessLogger.LoggerInfo(requestSerial, "tbar", "subScribePackage", true, null, String.valueOf(new Date().getTime()));
			String tOperatorId = "", memberId = "", countryNo = "", timeZone="";
			if(StringUtil.isNotNullOrEmpty(suid)){			
				ToolbarSession session = sessionContext.getSession(suid);
				tOperatorId = session.gettOperatorId();
				memberId = session.getMemberId();
		        countryNo = session.getCountryNo();
		        timeZone = session.getTimeZone();
			}				
			
			response.setContentType("text/plain");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			PrintWriter out = response.getWriter();
			StringBuffer sb = new StringBuffer();
			sb.append("tOperatorId=");
			sb.append(URLEncoder.encode(URLEncoder.encode(tOperatorId, "UTF-8"),"UTF-8"));
			sb.append("&userId=");
			sb.append(URLEncoder.encode(URLEncoder.encode(memberId, "UTF-8"),"UTF-8"));
			sb.append("&fGNo=");
			sb.append(URLEncoder.encode(URLEncoder.encode(fGNo, "UTF-8"),"UTF-8"));
			sb.append("&countryNo=");
			sb.append(URLEncoder.encode(URLEncoder.encode(countryNo, "UTF-8"),"UTF-8"));
			sb.append("&requestSerial=");
			sb.append(URLEncoder.encode(URLEncoder.encode(requestSerial, "UTF-8"),"UTF-8"));
			String responseStr = HttpUtil.sendPost(serviceUrlBean.getSubScribePackage(), sb.toString(),requestSerial);
			//tbar套餐订购业务日志记录结束
			BusinessLogger.LoggerInfo(requestSerial, "tbar", "subScribePackage", false, responseStr, String.valueOf(new Date().getTime()));
			
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("result", JSONObject.parseObject(responseStr));
			
//			String jsonpCallback = request.getParameter("jsonpCallback");
//			String resultStr = jsonpCallback + "(" + resultJSON.toJSONString() + ")";
		
			out.println(resultJSON);
			out.flush();
			out.close();			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
}
