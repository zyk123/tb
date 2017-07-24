package com.flash.toolbar.omp.reportstatistic.bo;

import java.util.Date;

import com.flash.toolbar.omp.common.model.BaseModel;
import com.flash.toolbar.omp.model.RpPackageSubReport;

public class RpPackageSubReportModel extends BaseModel<RpPackageSubReport>{
	/**
	 * 扩展开始时间
	 */
	private Date startTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	
	/**
	 * 扩展订购数总和
	 */
	private String alltotnum;
	/**
	 * 扩展用户数总和
	 */
	private String allconsnum;
	/**
	 * 扩展收入总和
	 */
	private String allpaynum;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public RpPackageSubReportModel(){
		bean = new RpPackageSubReport();
	}
	
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getAlltotnum() {
		return alltotnum;
	}

	public void setAlltotnum(String alltotnum) {
		this.alltotnum = alltotnum;
	}

	public String getAllconsnum() {
		return allconsnum;
	}

	public void setAllconsnum(String allconsnum) {
		this.allconsnum = allconsnum;
	}

	public String getAllpaynum() {
		return allpaynum;
	}

	public void setAllpaynum(String allpaynum) {
		this.allpaynum = allpaynum;
	}
	
	
}
