package com.flash.toolbar.omp.mapper;

import com.flash.toolbar.omp.model.RzOperatorlog;

public interface RzOperatorlogMapper {
    int deleteByPrimaryKey(String operatorlogid);

    int insert(RzOperatorlog record);

    int insertSelective(RzOperatorlog record);

    RzOperatorlog selectByPrimaryKey(String operatorlogid);

    int updateByPrimaryKeySelective(RzOperatorlog record);

    int updateByPrimaryKey(RzOperatorlog record);
}