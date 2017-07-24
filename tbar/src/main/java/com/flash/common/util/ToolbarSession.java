package com.flash.common.util;

import java.io.Serializable;

/**
 * tbar会话
 * @author ocean
 *
 */
public class ToolbarSession implements Serializable {

	/**
	 * 对象唯一标识 
	 */
	private static final long serialVersionUID = 3829567067398826837L;

	/**
	 * 运营商Id
	 */
	private String tOperatorId;
	
	/**
	 * 会员id
	 */
	private String memberId;
	
	/**
	 * 时区
	 */
	private String timeZone;
	
	/**
	 * 语言
	 */
	private String language;
	
	/**
	 * 国家编码
	 */
	private String countryNo;
	
	/**
	 * 预付费后付费标识
	 */
	private String serviceType;
	
	/**
	 * 流量提醒阈值
	 */
	private String flowThreshold;
	
	/**
	 * 手机号
	 */
	private String phoneNum;

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String gettOperatorId() {
		return tOperatorId;
	}

	public void settOperatorId(String tOperatorId) {
		this.tOperatorId = tOperatorId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCountryNo() {
		return countryNo;
	}

	public void setCountryNo(String countryNo) {
		this.countryNo = countryNo;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getFlowThreshold() {
		return flowThreshold;
	}

	public void setFlowThreshold(String flowThreshold) {
		this.flowThreshold = flowThreshold;
	}
	
	
}
