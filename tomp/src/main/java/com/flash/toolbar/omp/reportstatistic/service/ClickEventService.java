package com.flash.toolbar.omp.reportstatistic.service;

import java.util.List;

import com.flash.toolbar.omp.model.RpClickEventReport;
import com.flash.toolbar.omp.reportstatistic.bo.RpClickEventReportModel;

public interface ClickEventService {
	List<RpClickEventReport> getClickEventInfo(RpClickEventReportModel rpClickEventReportModel) throws Exception;
	
	List<RpClickEventReport> getClickEventInfoAll(RpClickEventReportModel rpClickEventReportModel) throws Exception;
	
	int countByCondition(RpClickEventReportModel rpClickEventReportModel) throws Exception;
}
