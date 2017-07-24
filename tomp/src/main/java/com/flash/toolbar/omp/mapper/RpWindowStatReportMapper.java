package com.flash.toolbar.omp.mapper;

import java.util.List;
import java.util.Map;

import com.flash.toolbar.omp.model.RpWindowStatReport;
import com.flash.toolbar.omp.reportstatistic.bo.RpWindowStatReportModel;

public interface RpWindowStatReportMapper {

    RpWindowStatReport selectByPrimaryKey(String wsrid);
    
    List<RpWindowStatReportModel> selectByPageSelective(RpWindowStatReportModel rpWindowStatReportModel);
    
    List<RpWindowStatReport> selectBySelective(RpWindowStatReportModel rpWindowStatReportModel);
    
    int countByCondition(RpWindowStatReportModel model);
    
    Map<String, Object> selectMinMaxDate(RpWindowStatReportModel rpWindowStatReportModel);

}