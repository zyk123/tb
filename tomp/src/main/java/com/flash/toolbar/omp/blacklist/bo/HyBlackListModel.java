package com.flash.toolbar.omp.blacklist.bo;

import java.util.Date;

import com.flash.toolbar.omp.common.model.BaseModel;
import com.flash.toolbar.omp.model.HyBlackList;

/**
 * 包含实体bean和业务需要字段等
 * @author henry
 *
 */ 
public class HyBlackListModel extends BaseModel<HyBlackList>{
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
	private String batchNo;
	
	public HyBlackListModel(){
		bean =  new HyBlackList();
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



	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	
	
	
}
