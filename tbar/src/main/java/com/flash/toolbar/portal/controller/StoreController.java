package com.flash.toolbar.portal.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.flash.common.log.BusinessLogger;
import com.flash.common.util.HttpUtil;
import com.flash.common.util.RandomString;
import com.flash.common.util.StringUtil;
import com.flash.common.util.ToolbarSession;
import com.flash.toolbar.portal.bean.ServiceUrlBean;
import com.flash.toolbar.service.SessionContext;

@Controller
@RequestMapping(value="/store")
public class StoreController {
	
	@Autowired
	private ServiceUrlBean serviceUrlBean;
	
	@Autowired
	private SessionContext sessionContext;
	
	/**
	 * 查询套餐列表
	 * @param response
	 * @param request
	 * @param tOperatorId
	 * @param countryNo
	 */
	@RequestMapping(value="/getPackageListManager")
	@ResponseBody
	public void getPackageListManager(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "suid",defaultValue = "") String suid,
			@RequestParam(value = "serviceType",defaultValue = "") String serviceType
			){
		
		try {
			String requestSerial = RandomString.getRandomNumber();
			//tbar套餐列表查询业务日志记录开始
			BusinessLogger.LoggerInfo(requestSerial, "tbar", "getPackageListManager", true, null, String.valueOf(new Date().getTime()));
			
			String tOperatorId = "", memberId = "", countryNo = "", timeZone="";
			if(StringUtil.isNotNullOrEmpty(suid)){			
				ToolbarSession session = sessionContext.getSession(suid);
				tOperatorId = session.gettOperatorId();
				memberId = session.getMemberId();
		        countryNo = session.getCountryNo();
		        timeZone = session.getTimeZone();
			}				
			
			StringBuilder sb = new StringBuilder();
			sb.append("tOperatorId=").append(tOperatorId);
			sb.append("&countryNo=").append(countryNo);
			sb.append("&serviceType=").append(serviceType);
			sb.append("&requestSerial=");
			sb.append(requestSerial);
			sb.append("&memberId=").append(memberId);
			
			response.setContentType("text/plain");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);

		
			String responseStr = HttpUtil.sendPost(serviceUrlBean.getGetPackageListManager(), sb.toString(),requestSerial);
			
			//tbar套餐列表查询业务日志记录结束
			BusinessLogger.LoggerInfo(requestSerial, "tbar", "getPackageListManager", false, null, String.valueOf(new Date().getTime()));
			
			
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
}
