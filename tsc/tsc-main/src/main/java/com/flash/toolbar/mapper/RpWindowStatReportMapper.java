package com.flash.toolbar.mapper;

import com.flash.toolbar.model.RpWindowStatReport;

public interface RpWindowStatReportMapper {
    int deleteByPrimaryKey(String wsrid);

    int insert(RpWindowStatReport record);

    int insertSelective(RpWindowStatReport record);

    RpWindowStatReport selectByPrimaryKey(String wsrid);

    int updateByPrimaryKeySelective(RpWindowStatReport record);

    int updateByPrimaryKey(RpWindowStatReport record);
}