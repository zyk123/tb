package com.flash.toolbar.mapper;

import com.flash.toolbar.model.TcFlowSub;

public interface TcFlowSubMapper {
    int deleteByPrimaryKey(String flowsubid);

    int insert(TcFlowSub record);

    int insertSelective(TcFlowSub record);

    TcFlowSub selectByPrimaryKey(String flowsubid);
    
    String selectOrderSequence();

    int updateByPrimaryKeySelective(TcFlowSub record);

    int updateByPrimaryKey(TcFlowSub record);
}