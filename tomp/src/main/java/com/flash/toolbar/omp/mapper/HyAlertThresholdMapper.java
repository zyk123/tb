package com.flash.toolbar.omp.mapper;

import com.flash.toolbar.omp.model.HyAlertThreshold;

public interface HyAlertThresholdMapper {
    int deleteByPrimaryKey(String thresholdid);

    int insert(HyAlertThreshold record);

    int insertSelective(HyAlertThreshold record);

    HyAlertThreshold selectByPrimaryKey(String thresholdid);

    int updateByPrimaryKeySelective(HyAlertThreshold record);

    int updateByPrimaryKey(HyAlertThreshold record);
}