package com.flash.toolbar.omp.mapper;

import java.util.List;
import java.util.Map;

import com.flash.toolbar.omp.model.RpClickEventReport;
import com.flash.toolbar.omp.reportstatistic.bo.RpClickEventReportModel;

public interface RpClickEventReportMapper {

    RpClickEventReport selectByPrimaryKey(String wsrid);
    
    List<RpClickEventReport> selectByPageSelective(RpClickEventReportModel rpClickEventReportModel);
    
    List<RpClickEventReport> selectBySelective(RpClickEventReportModel rpClickEventReportModel);
    
    int countByCondition(RpClickEventReportModel rpClickEventReportModel);
    
    Map<String, Object> selectMinMaxDate(RpClickEventReportModel rpClickEventReportModel);
}