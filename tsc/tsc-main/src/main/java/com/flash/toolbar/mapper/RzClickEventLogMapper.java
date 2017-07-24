package com.flash.toolbar.mapper;

import java.util.List;

import com.flash.toolbar.model.RzClickEventLog;

public interface RzClickEventLogMapper {
    int deleteByPrimaryKey(String accesslogid);

    int insert(RzClickEventLog record);

    int insertSelective(RzClickEventLog record);

    RzClickEventLog selectByPrimaryKey(String accesslogid);

    int updateByPrimaryKeySelective(RzClickEventLog record);

    int updateByPrimaryKey(RzClickEventLog record);
    
    int insertBatch(List<RzClickEventLog> list);
}