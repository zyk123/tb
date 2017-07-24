package com.flash.toolbar.omp.feedback.bo;

import java.util.Date;

import com.flash.toolbar.omp.common.model.BaseModel;
import com.flash.toolbar.omp.model.HyFeedback;

public class HyFeedBackModel extends BaseModel<HyFeedback>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 扩展开始时间
	 */
	private Date startTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	/**
	 * 扩展批次编号
	 */
	
	public HyFeedBackModel(){
		bean = new HyFeedback();
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
	
	
	
}
