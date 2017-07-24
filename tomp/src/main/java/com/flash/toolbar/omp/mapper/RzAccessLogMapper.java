package com.flash.toolbar.omp.mapper;

import com.flash.toolbar.omp.model.RzAccessLog;

public interface RzAccessLogMapper {
    int deleteByPrimaryKey(String accesslogid);

    int insert(RzAccessLog record);

    int insertSelective(RzAccessLog record);

    RzAccessLog selectByPrimaryKey(String accesslogid);

    int updateByPrimaryKeySelective(RzAccessLog record);

    int updateByPrimaryKey(RzAccessLog record);
}