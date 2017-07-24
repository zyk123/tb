package com.flash.toolbar.mapper;

import java.util.List;

import com.flash.toolbar.model.HyFlowExpandAlert;

public interface HyFlowExpandAlertMapper {
    int deleteByPrimaryKey(String id);

    int insert(HyFlowExpandAlert record);

    int insertSelective(HyFlowExpandAlert record);

    HyFlowExpandAlert selectByPrimaryKey(String id);
    
    List<HyFlowExpandAlert> selectBySelective(HyFlowExpandAlert record);

    int updateByPrimaryKeySelective(HyFlowExpandAlert record);

    int updateByPrimaryKey(HyFlowExpandAlert record);
}