package com.flash.toolbar.omp.model;

import java.io.Serializable;
import java.util.Date;

public class HyBlackSection implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 黑名单号段ID
	 */
	private String bSectionId;
	/**
	 * 导入批次ID
	 */
	private String batchId;
	/**
	 * 手机号码（起始）
	 */
	private String mobileNoStart;
	/**
	 * 手机号码（结束）
	 */
	private String mobileNoEnd;
	/**
	 * 国家编码
	 */
	private String countryNo;
	/**
	 * 运营商ID
	 */
	private String tOperatorId;
	/**
	 * 创建日期
	 */
	private Date addDate;
	public String getbSectionId() {
		return bSectionId;
	}
	public void setbSectionId(String bSectionId) {
		this.bSectionId = bSectionId;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getMobileNoStart() {
		return mobileNoStart;
	}
	public void setMobileNoStart(String mobileNoStart) {
		this.mobileNoStart = mobileNoStart;
	}
	public String getMobileNoEnd() {
		return mobileNoEnd;
	}
	public void setMobileNoEnd(String mobileNoEnd) {
		this.mobileNoEnd = mobileNoEnd;
	}
	public String getCountryNo() {
		return countryNo;
	}
	public void setCountryNo(String countryNo) {
		this.countryNo = countryNo;
	}
	public String gettOperatorId() {
		return tOperatorId;
	}
	public void settOperatorId(String tOperatorId) {
		this.tOperatorId = tOperatorId;
	}
	public Date getAddDate() {
		return addDate;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	
}
