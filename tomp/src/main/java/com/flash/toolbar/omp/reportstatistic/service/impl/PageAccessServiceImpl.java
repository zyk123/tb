package com.flash.toolbar.omp.reportstatistic.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.flash.toolbar.omp.common.util.StringUtil;
import com.flash.toolbar.omp.mapper.RpWindowStatReportMapper;
import com.flash.toolbar.omp.model.RpWindowStatReport;
import com.flash.toolbar.omp.reportstatistic.bo.RpWindowStatReportModel;
import com.flash.toolbar.omp.reportstatistic.service.PageAccessService;

@Service
public class PageAccessServiceImpl implements PageAccessService{
	@Resource
	private RpWindowStatReportMapper rpWindowStatReportMapper;
	
	@Override
	public List<RpWindowStatReportModel> getPageAccessListInfo(
			RpWindowStatReportModel rpWindowStatReportModel) throws Exception{
			Map<String, Object> map =  rpWindowStatReportMapper.selectMinMaxDate(rpWindowStatReportModel);
			if(map==null){
				return null;
			}
			Date minDate = map.get("MINDATE")!=null?(Date)map.get("MINDATE"):null;
			Date statTime = rpWindowStatReportModel.getStartTime();
			if(minDate!=null && (statTime==null || statTime.compareTo(minDate)<0)){
				rpWindowStatReportModel.setStartTime(minDate);
			}
			Date maxDate = map.get("MAXDATE")!=null?(Date)map.get("MAXDATE"):null;
			Date endTime = rpWindowStatReportModel.getEndTime();
			if(maxDate!=null && (endTime == null || endTime.compareTo(maxDate)>0)){
				rpWindowStatReportModel.setEndTime(maxDate);
			}
		int totalRows = countByCondition(rpWindowStatReportModel);
		rpWindowStatReportModel.getPager().setTotalRowsCount(totalRows);
		rpWindowStatReportModel.getPager().doPage();
		int totalPageNum = rpWindowStatReportModel.getPager().getTotalPageNum();
		int pageIndex = rpWindowStatReportModel.getPageIndex();
		if(pageIndex>totalPageNum){
			rpWindowStatReportModel.setPageIndex(1);
			rpWindowStatReportModel.getPager().doPage();
		}
		List<RpWindowStatReportModel> list = rpWindowStatReportMapper.selectByPageSelective(rpWindowStatReportModel);
		return list;
	}
	
	
	@Override
	public List<RpWindowStatReport> getPageAccessListAll(
			RpWindowStatReportModel rpWindowStatReportModel) throws Exception {
		Map<String, Object> map =  rpWindowStatReportMapper.selectMinMaxDate(rpWindowStatReportModel);
		if(map==null){
			return null;
		}
		Date minDate = map.get("MINDATE")!=null?(Date)map.get("MINDATE"):null;
		Date statTime = rpWindowStatReportModel.getStartTime();
		if(minDate!=null && (statTime==null || statTime.compareTo(minDate)<0)){
			rpWindowStatReportModel.setStartTime(minDate);
		}
		Date maxDate = map.get("MAXDATE")!=null?(Date)map.get("MAXDATE"):null;
		Date endTime = rpWindowStatReportModel.getEndTime();
		if(maxDate!=null && (endTime == null || endTime.compareTo(maxDate)>0)){
			rpWindowStatReportModel.setEndTime(maxDate);
		}
		List<RpWindowStatReport> list = rpWindowStatReportMapper.selectBySelective(rpWindowStatReportModel);
		return list;
	}
	
	@Override
	public int countByCondition(RpWindowStatReportModel rpWindowStatReportModel)
			throws Exception {
			return rpWindowStatReportMapper.countByCondition(rpWindowStatReportModel);
	}
	
}
