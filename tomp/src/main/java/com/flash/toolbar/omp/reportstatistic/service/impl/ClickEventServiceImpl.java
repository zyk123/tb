package com.flash.toolbar.omp.reportstatistic.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.flash.toolbar.omp.mapper.RpClickEventReportMapper;
import com.flash.toolbar.omp.model.RpClickEventReport;
import com.flash.toolbar.omp.reportstatistic.bo.RpClickEventReportModel;
import com.flash.toolbar.omp.reportstatistic.service.ClickEventService;

@Service
public class ClickEventServiceImpl implements ClickEventService{
	@Resource
	private RpClickEventReportMapper  rpClickEventReportMapper;
	
	@Override
	public int countByCondition(RpClickEventReportModel rpClickEventReportModel)
			throws Exception {
		return rpClickEventReportMapper.countByCondition(rpClickEventReportModel);
	}
	
	
	@Override
	public List<RpClickEventReport> getClickEventInfo(
			RpClickEventReportModel rpClickEventReportModel) throws Exception {
		int totalRows = countByCondition(rpClickEventReportModel);
		rpClickEventReportModel.getPager().setTotalRowsCount(totalRows);
		rpClickEventReportModel.getPager().doPage();
		int totalPageNum = rpClickEventReportModel.getPager().getTotalPageNum();
		int pageIndex = rpClickEventReportModel.getPageIndex();
		if(pageIndex>totalPageNum){
			rpClickEventReportModel.setPageIndex(1);
			rpClickEventReportModel.getPager().doPage();
		}
		List<RpClickEventReport> list = rpClickEventReportMapper.selectByPageSelective(rpClickEventReportModel);
		return list;
	}
	
	
	@Override
	public List<RpClickEventReport> getClickEventInfoAll(
			RpClickEventReportModel rpClickEventReportModel) throws Exception {
		List<RpClickEventReport> list = rpClickEventReportMapper.selectBySelective(rpClickEventReportModel);
		return list;
	}
}
