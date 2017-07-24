package com.flash.toolbar.omp.reportstatistic.service;

import java.util.List;

import com.flash.toolbar.omp.model.Reloadreport;
import com.flash.toolbar.omp.model.RpClickEventReport;
import com.flash.toolbar.omp.reportstatistic.bo.ReloadreportModel;
import com.flash.toolbar.omp.reportstatistic.bo.RpClickEventReportModel;

public interface ReloadService {

	List<Reloadreport> getClickEventInfo(
			ReloadreportModel reloadreportModel) throws Exception;

	List<RpClickEventReport> getClickEventInfoAll(
			RpClickEventReportModel rpClickEventReportModel) throws Exception;

	int countByCondition(ReloadreportModel reloadreportModel)
			throws Exception;

}
