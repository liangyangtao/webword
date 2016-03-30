package com.common.manager;

import javax.servlet.http.HttpSession;

public class SessionManager {

	public static final String SESSION_LOGIN_INFO = "loginInfo";

	public static void clearLogin() {
		CommonManager cm = CommonManager.getCurrentManager();
		if(cm != null) {
			HttpSession session = cm.getRequest().getSession();
			session.removeAttribute(SESSION_LOGIN_INFO);
		}
	}
	
	public static void setSessionInfo(String key, Object obj) {
		CommonManager cm = CommonManager.getCurrentManager();
		if (cm != null) {
			HttpSession session = cm.getRequest().getSession();
			session.setAttribute(key, obj);
		}
	}

	public static Object getSessionInfo(String key) {
		CommonManager cm = CommonManager.getCurrentManager();
		if (cm != null) {
			HttpSession session = cm.getRequest().getSession();
			return session.getAttribute(key);
		} else {
			return null;
		}
	}
}
