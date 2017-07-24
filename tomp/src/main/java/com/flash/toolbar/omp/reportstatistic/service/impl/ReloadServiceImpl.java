package com.flash.toolbar.omp.reportstatistic.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.flash.toolbar.omp.mapper.ReloadreportMapper;
import com.flash.toolbar.omp.mapper.RpClickEventReportMapper;
import com.flash.toolbar.omp.model.Reloadreport;
import com.flash.toolbar.omp.model.RpClickEventReport;
import com.flash.toolbar.omp.reportstatistic.bo.ReloadreportModel;
import com.flash.toolbar.omp.reportstatistic.bo.RpClickEventReportModel;
import com.flash.toolbar.omp.reportstatistic.service.ReloadService;

@Service
public class ReloadServiceImpl implements ReloadService{
	@Resource
	private RpClickEventReportMapper  rpClickEventReportMapper;
	@Resource
	private ReloadreportMapper  reloadreportMapper;
	
	@Override
	public int countByCondition(ReloadreportModel reloadreportModel)
			throws Exception {
		return reloadreportMapper.countByCondition(reloadreportModel);
	}
	
	
	@Override
	public List<Reloadreport> getClickEventInfo(
			ReloadreportModel reloadreportModel) throws Exception {
		int totalRows = countByCondition(reloadreportModel);
		reloadreportModel.getPager().setTotalRowsCount(totalRows);
		reloadreportModel.getPager().doPage();
		int totalPageNum = reloadreportModel.getPager().getTotalPageNum();
		int pageIndex = reloadreportModel.getPageIndex();
		if(pageIndex>totalPageNum){
			reloadreportModel.setPageIndex(1);
			reloadreportModel.getPager().doPage();
		}
		List<Reloadreport> list = reloadreportMapper.selectByPageSelective(reloadreportModel);
		return list;
	}
	
	
	@Override
	public List<RpClickEventReport> getClickEventInfoAll(
			RpClickEventReportModel rpClickEventReportModel) throws Exception {
		List<RpClickEventReport> list = rpClickEventReportMapper.selectBySelective(rpClickEventReportModel);
		return list;
	}
}
