package com.flash.toolbar.omp.mapper;

import com.flash.toolbar.omp.model.SysThresholdColor;

public interface SysThresholdColorMapper {
    int deleteByPrimaryKey(String thresholdcolorid);

    int insert(SysThresholdColor record);

    int insertSelective(SysThresholdColor record);

    SysThresholdColor selectByPrimaryKey(String thresholdcolorid);

    int updateByPrimaryKeySelective(SysThresholdColor record);

    int updateByPrimaryKey(SysThresholdColor record);
}