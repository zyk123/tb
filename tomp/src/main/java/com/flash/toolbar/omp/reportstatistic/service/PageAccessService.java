package com.flash.toolbar.omp.reportstatistic.service;

import java.util.List;

import com.flash.toolbar.omp.model.RpWindowStatReport;
import com.flash.toolbar.omp.reportstatistic.bo.RpWindowStatReportModel;


public interface PageAccessService {
	List<RpWindowStatReportModel> getPageAccessListInfo(RpWindowStatReportModel rpWindowStatReportModel) throws Exception;
	
	List<RpWindowStatReport> getPageAccessListAll(RpWindowStatReportModel rpWindowStatReportModel) throws Exception;
	
	int countByCondition(RpWindowStatReportModel rpWindowStatReportModel) throws Exception;
}

