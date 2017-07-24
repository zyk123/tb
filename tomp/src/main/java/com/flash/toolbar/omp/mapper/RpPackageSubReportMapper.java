package com.flash.toolbar.omp.mapper;

import java.util.List;
import java.util.Map;

import com.flash.toolbar.omp.model.RpPackageSubReport;
import com.flash.toolbar.omp.reportstatistic.bo.RpPackageSubReportModel;

public interface RpPackageSubReportMapper {

    RpPackageSubReport selectByPrimaryKey(String psid);
    
    List<RpPackageSubReportModel> selectByPageSelective(RpPackageSubReportModel rpPackageSubReportModel);
    
    List<RpPackageSubReportModel> selectBySelective(RpPackageSubReportModel rpPackageSubReportModel);
    
    int countByCondition(RpPackageSubReportModel model);
    
    Map<String, Object> selectMinMaxDate(RpPackageSubReportModel rpPackageSubReportModel);

}