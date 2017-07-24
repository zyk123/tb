///**
// * 
// */
//package com.flash.common.util;
//
//import java.util.HashMap;
//
//import javax.servlet.http.HttpSession;
//
///**
// * @包名 com.huawei.Utils
// * @文件名 SessionContext.java
// * @作者 jack
// * @创建日期 2016-5-13
// * @版本 V 1.0
// */
//public class SessionContext {
//	
//	private static SessionContext instance;
//	private HashMap<String, HttpSession> smap;
//
//	private SessionContext() {
//		smap = new HashMap<String, HttpSession>();
//	}
//
//	public static SessionContext getInstance() {
//		if (instance == null) {
//			instance = new SessionContext();
//		}
//		return instance;
//	}
//
//	public synchronized void AddSession(HttpSession session) {
//		if (session != null) {
//			smap.put(session.getId(), session);
//		}
//	}
//
//	public synchronized void DelSession(HttpSession session) {
//		if (session != null) {
//			smap.remove(session.getId());
//		}
//	}
//
//	public synchronized HttpSession getSession(String session_id) {
//		if (session_id == null)
//			return null;
//		return (HttpSession) smap.get(session_id);
//	}
//
//}
