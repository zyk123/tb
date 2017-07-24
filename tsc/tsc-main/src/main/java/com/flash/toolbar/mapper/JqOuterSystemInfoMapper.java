package com.flash.toolbar.mapper;

import com.flash.toolbar.model.JqOuterSystemInfo;

public interface JqOuterSystemInfoMapper {
    int deleteByPrimaryKey(String outerid);

    int insert(JqOuterSystemInfo record);

    int insertSelective(JqOuterSystemInfo record);

    JqOuterSystemInfo selectByPrimaryKey(String outerid);

    int updateByPrimaryKeySelective(JqOuterSystemInfo record);

    int updateByPrimaryKey(JqOuterSystemInfo record);
}