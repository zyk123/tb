package com.flash.toolbar.omp.mapper;

import com.flash.toolbar.omp.model.SysOperatorLang;

public interface SysOperatorLangMapper {
    int deleteByPrimaryKey(String operatorlangid);

    int insert(SysOperatorLang record);

    int insertSelective(SysOperatorLang record);

    SysOperatorLang selectByPrimaryKey(String operatorlangid);

    int updateByPrimaryKeySelective(SysOperatorLang record);

    int updateByPrimaryKey(SysOperatorLang record);
}