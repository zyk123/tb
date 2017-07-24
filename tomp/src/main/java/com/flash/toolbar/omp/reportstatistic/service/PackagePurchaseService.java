package com.flash.toolbar.omp.reportstatistic.service;

import java.util.List;

import com.flash.toolbar.omp.model.RpPackageSubReport;
import com.flash.toolbar.omp.reportstatistic.bo.RpPackageSubReportModel;

public interface PackagePurchaseService {
	List<RpPackageSubReportModel> getPackagePurchaseListInfo(RpPackageSubReportModel rpPackageSubReportModel) throws Exception;
	
	List<RpPackageSubReportModel> getPackagePurchaseListAll(RpPackageSubReportModel rpPackageSubReportModel) throws Exception;
	
	int countByCondition(RpPackageSubReportModel rpPackageSubReportModel) throws Exception;
}
