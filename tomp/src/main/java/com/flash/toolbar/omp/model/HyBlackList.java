package com.flash.toolbar.omp.model;

import java.io.Serializable;
import java.util.Date;

public class HyBlackList implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 黑名单主键ID
	 */
	private String bListId;
	/**
	 * 黑名单导入批次ID
	 */
	private String batchId;
	/**
	 * 手机号码
	 */
	private String mobileNo;
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
	public String getbListId() {
		return bListId;
	}
	public void setbListId(String bListId) {
		this.bListId = bListId;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
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
