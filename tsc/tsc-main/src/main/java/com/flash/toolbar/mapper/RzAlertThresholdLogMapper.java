package com.flash.toolbar.mapper;

import com.flash.toolbar.model.RzAlertThresholdLog;

public interface RzAlertThresholdLogMapper {
    int deleteByPrimaryKey(String alertthresholdlogid);

    int insert(RzAlertThresholdLog record);

    int insertSelective(RzAlertThresholdLog record);

    RzAlertThresholdLog selectByPrimaryKey(String alertthresholdlogid);

    int updateByPrimaryKeySelective(RzAlertThresholdLog record);

    int updateByPrimaryKey(RzAlertThresholdLog record);
}