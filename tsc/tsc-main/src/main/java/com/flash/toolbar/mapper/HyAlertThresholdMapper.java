package com.flash.toolbar.mapper;

import com.flash.toolbar.model.HyAlertThreshold;

public interface HyAlertThresholdMapper {
    int deleteByPrimaryKey(String thresholdid);

    int insert(HyAlertThreshold record);

    int insertSelective(HyAlertThreshold record);

    HyAlertThreshold selectByPrimaryKey(String thresholdid);

    int updateByPrimaryKeySelective(HyAlertThreshold record);

    int updateByPrimaryKey(HyAlertThreshold record);
    
    HyAlertThreshold selectByMemberId(HyAlertThreshold record);
}