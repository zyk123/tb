package com.flash.toolbar.omp.reportstatistic.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.flash.toolbar.omp.mapper.RpPackageSubReportMapper;
import com.flash.toolbar.omp.model.RpPackageSubReport;
import com.flash.toolbar.omp.reportstatistic.bo.RpPackageSubReportModel;
import com.flash.toolbar.omp.reportstatistic.service.PackagePurchaseService;

@Service
public class PackagePurchaseServiceImpl implements PackagePurchaseService{
	
	@Resource
	private RpPackageSubReportMapper rpPackageSubReportMapper;
	
	@Override
	public int countByCondition(RpPackageSubReportModel rpPackageSubReportModel)
			throws Exception {
		return rpPackageSubReportMapper.countByCondition(rpPackageSubReportModel);
	}
	
	@Override
	public List<RpPackageSubReportModel> getPackagePurchaseListInfo(
			RpPackageSubReportModel rpPackageSubReportModel) throws Exception {
		Map<String , Object> map = rpPackageSubReportMapper.selectMinMaxDate(rpPackageSubReportModel);
		if(map==null){
			return null;
		}
		Date minDate = map.get("MINDATE")!=null?(Date)map.get("MINDATE"):null;
		Date statTime = rpPackageSubReportModel.getStartTime();
		if(minDate!=null && (statTime==null || statTime.compareTo(minDate)<0)){
			rpPackageSubReportModel.setStartTime(minDate);
		}
		Date maxDate = map.get("MAXDATE")!=null?(Date)map.get("MAXDATE"):null;
		Date endTime = rpPackageSubReportModel.getEndTime();
		if(maxDate!=null && (endTime == null || endTime.compareTo(maxDate)>0)){
			rpPackageSubReportModel.setEndTime(maxDate);
		}
		int totalRows = countByCondition(rpPackageSubReportModel);
		rpPackageSubReportModel.getPager().setTotalRowsCount(totalRows);
		rpPackageSubReportModel.getPager().doPage();
		int totalPageNum = rpPackageSubReportModel.getPager().getTotalPageNum();
		int pageIndex = rpPackageSubReportModel.getPageIndex();
		if(pageIndex>totalPageNum){
			rpPackageSubReportModel.setPageIndex(1);
			rpPackageSubReportModel.getPager().doPage();
		}
		List<RpPackageSubReportModel> list = rpPackageSubReportMapper.selectByPageSelective(rpPackageSubReportModel);
		return list;
	}
	
	
	
	@Override
	public List<RpPackageSubReportModel> getPackagePurchaseListAll(
			RpPackageSubReportModel rpPackageSubReportModel) throws Exception {
		Map<String , Object> map = rpPackageSubReportMapper.selectMinMaxDate(rpPackageSubReportModel);
		if(map==null){
			return null;
		}
		Date minDate = map.get("MINDATE")!=null?(Date)map.get("MINDATE"):null;
		Date statTime = rpPackageSubReportModel.getStartTime();
		if(minDate!=null && (statTime==null || statTime.compareTo(minDate)<0)){
			rpPackageSubReportModel.setStartTime(minDate);
		}
		Date maxDate = map.get("MAXDATE")!=null?(Date)map.get("MAXDATE"):null;
		Date endTime = rpPackageSubReportModel.getEndTime();
		if(maxDate!=null && (endTime == null || endTime.compareTo(maxDate)>0)){
			rpPackageSubReportModel.setEndTime(maxDate);
		}
		List<RpPackageSubReportModel> list = rpPackageSubReportMapper.selectBySelective(rpPackageSubReportModel);
		return list;
	}
}
