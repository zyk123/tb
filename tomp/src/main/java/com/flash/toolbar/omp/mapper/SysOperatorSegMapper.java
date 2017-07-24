package com.flash.toolbar.omp.mapper;

import com.flash.toolbar.omp.model.SysOperatorSeg;

public interface SysOperatorSegMapper {
    int deleteByPrimaryKey(String operatorsegid);

    int insert(SysOperatorSeg record);

    int insertSelective(SysOperatorSeg record);

    SysOperatorSeg selectByPrimaryKey(String operatorsegid);

    int updateByPrimaryKeySelective(SysOperatorSeg record);

    int updateByPrimaryKey(SysOperatorSeg record);
}