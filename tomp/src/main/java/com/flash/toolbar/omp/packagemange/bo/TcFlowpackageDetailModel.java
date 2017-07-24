package com.flash.toolbar.omp.packagemange.bo;

import com.flash.toolbar.omp.common.model.BaseModel;
import com.flash.toolbar.omp.model.TcFlowpackageDetail;

public class TcFlowpackageDetailModel extends BaseModel<TcFlowpackageDetail>{
	
	private String packagename;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public TcFlowpackageDetailModel(){
		bean = new TcFlowpackageDetail();
	}

	public String getPackagename() {
		return packagename;
	}

	public void setPackagename(String packagename) {
		this.packagename = packagename;
	}
	
	
}
