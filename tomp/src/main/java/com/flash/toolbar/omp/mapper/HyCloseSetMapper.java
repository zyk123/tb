package com.flash.toolbar.omp.mapper;

import com.flash.toolbar.omp.model.HyCloseSet;

public interface HyCloseSetMapper {
    int deleteByPrimaryKey(String closesetid);

    int insert(HyCloseSet record);

    int insertSelective(HyCloseSet record);

    HyCloseSet selectByPrimaryKey(String closesetid);

    int updateByPrimaryKeySelective(HyCloseSet record);

    int updateByPrimaryKey(HyCloseSet record);
}