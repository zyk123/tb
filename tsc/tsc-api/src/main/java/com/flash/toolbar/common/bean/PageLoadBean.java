package com.flash.toolbar.common.bean;

import java.io.Serializable;

public class PageLoadBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String phoneNo;
	
	private String memberId;
	
	private String userAgent;
	
	private String ip;
	
	private String pageName;
	
	private String referer;
	
	private String href;
	
	private String tOperatorId;
	
	private String countryNo;
	
	private String timeZone;
	
	private String imei;
	
	private String msip;
	
	private String clickEventName;

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getMsip() {
		return msip;
	}

	public void setMsip(String msip) {
		this.msip = msip;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String gettOperatorId() {
		return tOperatorId;
	}

	public void settOperatorId(String tOperatorId) {
		this.tOperatorId = tOperatorId;
	}

	public String getCountryNo() {
		return countryNo;
	}

	public void setCountryNo(String countryNo) {
		this.countryNo = countryNo;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getClickEventName() {
		return clickEventName;
	}

	public void setClickEventName(String clickEventName) {
		this.clickEventName = clickEventName;
	}
	
	
}
