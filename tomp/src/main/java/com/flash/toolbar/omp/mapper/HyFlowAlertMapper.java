package com.flash.toolbar.omp.mapper;

import com.flash.toolbar.omp.model.HyFlowAlert;

public interface HyFlowAlertMapper {
    int deleteByPrimaryKey(String flowalertid);

    int insert(HyFlowAlert record);

    int insertSelective(HyFlowAlert record);

    HyFlowAlert selectByPrimaryKey(String flowalertid);

    int updateByPrimaryKeySelective(HyFlowAlert record);

    int updateByPrimaryKey(HyFlowAlert record);
}