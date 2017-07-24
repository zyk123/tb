package com.flash.toolbar.omp.reportstatistic.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.flash.toolbar.omp.mapper.BrowsermobileosreportMapper;
import com.flash.toolbar.omp.reportstatistic.bo.BrowsermobileosreportModel;
import com.flash.toolbar.omp.reportstatistic.bo.KeyAndValue;
import com.flash.toolbar.omp.reportstatistic.bo.KeyAndValueStr;
import com.flash.toolbar.omp.reportstatistic.service.SharestatisticsService;

@Service
public class SharestatisticsServiceImpl implements SharestatisticsService{

	@Resource
	private BrowsermobileosreportMapper browsermobileosreportMapper;
	
	@Override
	public List<KeyAndValue> loadData1(BrowsermobileosreportModel browsermobileosreportModel) {
		return browsermobileosreportMapper.loadData(browsermobileosreportModel);
	}

	@Override
	public List<KeyAndValue> loadData2(BrowsermobileosreportModel browsermobileosreportModel) {
		return browsermobileosreportMapper.loadData(browsermobileosreportModel);
	}

	@Override
	public List<KeyAndValue> loadData3(BrowsermobileosreportModel browsermobileosreportModel) {
		return browsermobileosreportMapper.loadData(browsermobileosreportModel);
	}

	@Override
	public List<KeyAndValueStr> loadData4(BrowsermobileosreportModel browsermobileosreportModel) {
		return browsermobileosreportMapper.loadData4(browsermobileosreportModel);
	}

}
