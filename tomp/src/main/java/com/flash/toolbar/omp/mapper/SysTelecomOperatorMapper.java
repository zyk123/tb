package com.flash.toolbar.omp.mapper;

import com.flash.toolbar.omp.model.SysTelecomOperator;

public interface SysTelecomOperatorMapper {
    int deleteByPrimaryKey(String toperatorid);

    int insert(SysTelecomOperator record);

    int insertSelective(SysTelecomOperator record);

    SysTelecomOperator selectByPrimaryKey(String toperatorid);

    int updateByPrimaryKeySelective(SysTelecomOperator record);

    int updateByPrimaryKey(SysTelecomOperator record);
}