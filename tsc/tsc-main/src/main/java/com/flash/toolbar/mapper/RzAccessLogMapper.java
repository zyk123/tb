package com.flash.toolbar.mapper;

import java.util.List;

import com.flash.toolbar.model.RzAccessLog;

public interface RzAccessLogMapper {
    int deleteByPrimaryKey(String accesslogid);

    int insert(RzAccessLog record);

    int insertSelective(RzAccessLog record);

    RzAccessLog selectByPrimaryKey(String accesslogid);

    int updateByPrimaryKeySelective(RzAccessLog record);

    int updateByPrimaryKey(RzAccessLog record);
    
    int insertBatch(List<RzAccessLog> list);
}