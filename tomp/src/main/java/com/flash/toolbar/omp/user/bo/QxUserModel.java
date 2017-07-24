package com.flash.toolbar.omp.user.bo;

import java.util.Date;

import com.flash.toolbar.omp.common.model.BaseModel;
import com.flash.toolbar.omp.model.QxUser;

public class QxUserModel extends BaseModel<QxUser>{
	
	/**
	 * 扩展开始时间
	 */
	private Date startTime;
	/**
	 * 结束时间
	 */
	private Date endTime;	
	
	/**
	 * 时区
	 */
	private String timeZone;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 运营商编号
	 */
	private String tOperatorNo;
	/**
	 * 运营商名称
	 */
	private String tOperatorName;
	/**
	 * 运营商号码长度
	 */
	private String mobileLength;
	public QxUserModel(){
		bean = new  QxUser();
	}
	
	public String gettOperatorNo() {
		return tOperatorNo;
	}
	public void settOperatorNo(String tOperatorNo) {
		this.tOperatorNo = tOperatorNo;
	}
	public String gettOperatorName() {
		return tOperatorName;
	}
	public void settOperatorName(String tOperatorName) {
		this.tOperatorName = tOperatorName;
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

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	public String getMobileLength() {
		return mobileLength;
	}

	public void setMobileLength(String mobileLength) {
		this.mobileLength = mobileLength;
	}

	
}
