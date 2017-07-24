package com.flash.toolbar.omp.common.util;

import java.util.Enumeration;

import javax.servlet.http.HttpSession;

public class SessionUtil {
	public static void invalidateSession(HttpSession session) {
		if (session == null)
			return;
		clearAttribute(session);
		try {
			session.invalidate();
		} catch (IllegalStateException ex) {
			// do nothing
		}
	}

	public static void clearAttribute(HttpSession session) {
		if (session == null)
			return;
		try {
			Enumeration e = session.getAttributeNames();
			while (e.hasMoreElements()) {
				String name = (String) e.nextElement();
				session.removeAttribute(name);
			}
		} catch (IllegalStateException ex) {
			// do nothing
		}
	}
	
	
	public static void removeAttribute(HttpSession session,String key) {
		if (session == null || StringUtil.isNull(key))
			return;
		try {
			Enumeration e = session.getAttributeNames();
			while (e.hasMoreElements()) {
				String name = (String) e.nextElement();
				if(key.equals(name)){
					session.removeAttribute(name);
				}
			}
		} catch (IllegalStateException ex) {
			// do nothing
		}
	}	
}
