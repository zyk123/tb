package com.flash.toolbar.omp.reportstatistic.bo;

import com.flash.toolbar.omp.common.model.BaseModel;
import com.flash.toolbar.omp.model.Browsermobileosreport;
import com.flash.toolbar.omp.model.HdPrizeInfo;

public class BrowsermobileosreportModel extends
		BaseModel<Browsermobileosreport> {
	private String beginDate;

	private String endDate;
	
	private String flag;
	
	public BrowsermobileosreportModel(){
		bean =  new Browsermobileosreport();
	}
	
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
