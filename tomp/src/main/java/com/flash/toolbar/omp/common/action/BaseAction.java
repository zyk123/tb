package com.flash.toolbar.omp.common.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.alibaba.fastjson.JSONObject;
import com.flash.toolbar.omp.common.model.BaseModel;
import com.flash.toolbar.omp.common.util.Constant;
import com.flash.toolbar.omp.common.util.HtmlUtil;
import com.flash.toolbar.omp.user.bo.QxUserModel;

public class BaseAction {

		public final static String SUCCESS = "success";

		public final static String MSG = "msg";

		public final static String DATA = "data";
		
		@Resource
		private HttpServletRequest request;
		

		@InitBinder
		protected void initBinder(WebDataBinder binder) {
			binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
		}
		
		public QxUserModel getSessionModel(){
			HttpSession session =  request.getSession();
			QxUserModel qxUserModel = new QxUserModel();
			if(session!=null && session.getAttribute(Constant.USER_MODEL_SESSION)!=null){
				qxUserModel = (QxUserModel)session.getAttribute(Constant.USER_MODEL_SESSION);
			}
			return qxUserModel;
		}
		
		
		/**
		 * 提示成功信息
		 * 
		 * @param message
		 */
		public void sendSuccessMessage(HttpServletResponse response, String message) {
			JSONObject result = new JSONObject();
			result.put(SUCCESS, true);
			result.put(MSG, message);
			HtmlUtil.writerJson(response, result);
		}

		/**
		 * 提示成功信息
		 * 
		 * @param message
		 */
		public void sendSuccessMessage(HttpServletResponse response, String message, Object data) {
			JSONObject result = new JSONObject();
			result.put(SUCCESS, true);
			result.put(MSG, message);
			result.put(DATA,data);
			HtmlUtil.writerJson(response, result);
		}
		
		public String sendSuccessHtml(String message){
			JSONObject result = new JSONObject();
			result.put(SUCCESS, true);
			result.put(MSG, message);
			return "<script>parent.callback(" + JSONObject.toJSON(result) + ");</script>";
		}

		/**
		 * 提示失败信息
		 * 
		 * @param message
		 */
		public void sendFailureMessage(HttpServletResponse response, String message) {
			JSONObject result = new JSONObject();
			result.put(SUCCESS, false);
			result.put(MSG, message);
			HtmlUtil.writerJson(response, result);
		}
		
		public String sendFailureHtml(String message){
			JSONObject result = new JSONObject();
			result.put(SUCCESS, false);
			result.put(MSG, message);
			return "<script>parent.callback(" + JSONObject.toJSON(result) + ");</script>";
		}		

		/**
		 * 提示失败信息
		 * 
		 * @param message
		 */
		public void sendFailureMessage(HttpServletResponse response, String message, Object data) {
			JSONObject result = new JSONObject();
			result.put(SUCCESS, false);
			result.put(MSG, message);
			result.put(DATA,data);
			HtmlUtil.writerJson(response, result);
		}

		/**
		 * 提示失败信息
		 * 
		 * @param message
		 */
		public void sendFailureMessage(HttpServletResponse response, String message, Throwable e) {
			Map<String, Object> result = new HashMap<String, Object>();
			result.put(SUCCESS, false);
			result.put(MSG, message + "异常信息：[" + e.getMessage() + "]");
			HtmlUtil.writerJson(response, result);
		}

}
