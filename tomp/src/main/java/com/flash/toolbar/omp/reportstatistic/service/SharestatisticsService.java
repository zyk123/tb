package com.flash.toolbar.omp.reportstatistic.service;

import java.util.List;

import com.flash.toolbar.omp.reportstatistic.bo.BrowsermobileosreportModel;
import com.flash.toolbar.omp.reportstatistic.bo.KeyAndValue;
import com.flash.toolbar.omp.reportstatistic.bo.KeyAndValueStr;

public interface SharestatisticsService {

	public List<KeyAndValue> loadData1(BrowsermobileosreportModel browsermobileosreportModel);
	
	public List<KeyAndValue> loadData2(BrowsermobileosreportModel browsermobileosreportModel);
	
	public List<KeyAndValue> loadData3(BrowsermobileosreportModel browsermobileosreportModel);
	
	public List<KeyAndValueStr> loadData4(BrowsermobileosreportModel browsermobileosreportModel);
}
