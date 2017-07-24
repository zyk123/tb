package com.flash.toolbar.omp.mapper;

import java.util.List;

import com.flash.toolbar.omp.model.Reloadreport;
import com.flash.toolbar.omp.reportstatistic.bo.ReloadreportModel;

public interface ReloadreportMapper {
    int deleteByPrimaryKey(String rrid);

    int insert(Reloadreport record);

    int insertSelective(Reloadreport record);

    Reloadreport selectByPrimaryKey(String rrid);

    int updateByPrimaryKeySelective(Reloadreport record);

    int updateByPrimaryKey(Reloadreport record);
    
    List<Reloadreport> selectByPageSelective(ReloadreportModel reloadreportModel);
    
    int countByCondition(ReloadreportModel reloadreportModel);
}