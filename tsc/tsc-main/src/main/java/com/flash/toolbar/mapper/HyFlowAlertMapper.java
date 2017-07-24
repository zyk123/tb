package com.flash.toolbar.mapper;

import java.util.List;

import com.flash.toolbar.model.HyFlowAlert;

public interface HyFlowAlertMapper {
    int deleteByPrimaryKey(String flowalertid);

    int insert(HyFlowAlert record);

    int insertSelective(HyFlowAlert record);

    HyFlowAlert selectByPrimaryKey(String flowalertid);
    
    List<String> selectByMemberIdAndDate(HyFlowAlert alert);

    int updateByPrimaryKeySelective(HyFlowAlert record);

    int updateByPrimaryKey(HyFlowAlert record);
}