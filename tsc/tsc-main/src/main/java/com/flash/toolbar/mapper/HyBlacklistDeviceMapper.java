package com.flash.toolbar.mapper;

import java.util.List;
import java.util.Map;

import com.flash.toolbar.model.HyBlacklistDevice;
import com.flash.toolbar.model.SysTelecomOperatorAndHyMember;

public interface HyBlacklistDeviceMapper {
    int deleteByPrimaryKey(String id);

    int insert(HyBlacklistDevice record);

    int insertSelective(HyBlacklistDevice record);

    HyBlacklistDevice selectByPrimaryKey(String id);
    
    List<String> selectDeviceNames(SysTelecomOperatorAndHyMember sysTelecomOperatorAndHyMember);

    int updateByPrimaryKeySelective(HyBlacklistDevice record);

    int updateByPrimaryKey(HyBlacklistDevice record);
    
    List<String> selectByToperator(Map map);
}