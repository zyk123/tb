package com.flash.toolbar.omp.reportstatistic.bo;

import java.util.Date;

import com.flash.toolbar.omp.common.model.BaseModel;
import com.flash.toolbar.omp.model.RpWindowStatReport;

/**
 * 窗口统计扩展实体类
 * @author admin4
 *
 */
public class RpWindowStatReportModel extends BaseModel<RpWindowStatReport>{
	
	/**
	 * 扩展开始时间
	 */
	private Date startTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public RpWindowStatReportModel(){
		bean = new RpWindowStatReport();
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
