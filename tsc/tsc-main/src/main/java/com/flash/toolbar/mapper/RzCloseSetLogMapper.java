package com.flash.toolbar.mapper;

import com.flash.toolbar.model.RzCloseSetLog;

public interface RzCloseSetLogMapper {
    int deleteByPrimaryKey(String closesetlogid);

    int insert(RzCloseSetLog record);

    int insertSelective(RzCloseSetLog record);

    RzCloseSetLog selectByPrimaryKey(String closesetlogid);

    int updateByPrimaryKeySelective(RzCloseSetLog record);

    int updateByPrimaryKey(RzCloseSetLog record);
}