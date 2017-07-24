///**
// * 
// */
//package com.flash.common.util;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.servlet.http.HttpSession;
//import javax.servlet.http.HttpSessionEvent;
//import javax.servlet.http.HttpSessionListener;
//
///**
// * @包名 com.huawei.Utils
// * @文件名 SessionListener.java
// * @作者 jack
// * @创建日期 2016-5-13
// * @版本 V 1.0
// */
//public class SessionListener implements HttpSessionListener {
//	
//	public static Map<String, HttpSession> userMap = new HashMap<String, HttpSession>();
//	
//	private SessionContext sc = SessionContext.getInstance();
//
//	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
//		sc.AddSession(httpSessionEvent.getSession());
//	}
//
//	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
//		HttpSession session = httpSessionEvent.getSession();
//		sc.DelSession(session);
//	}
//
//}
