package com.flash.toolbar.omp.mapper;

import java.util.List;

import com.flash.toolbar.omp.model.Browsermobileosreport;
import com.flash.toolbar.omp.reportstatistic.bo.BrowsermobileosreportModel;
import com.flash.toolbar.omp.reportstatistic.bo.KeyAndValue;
import com.flash.toolbar.omp.reportstatistic.bo.KeyAndValueStr;

public interface BrowsermobileosreportMapper {
    int deleteByPrimaryKey(String bmoid);

    int insert(Browsermobileosreport record);

    int insertSelective(Browsermobileosreport record);

    Browsermobileosreport selectByPrimaryKey(String bmoid);

    int updateByPrimaryKeySelective(Browsermobileosreport record);

    int updateByPrimaryKey(Browsermobileosreport record);
    
    List<KeyAndValue> loadData(BrowsermobileosreportModel browsermobileosreportModel);
    
    List<KeyAndValueStr> loadData4(BrowsermobileosreportModel browsermobileosreportModel);
}