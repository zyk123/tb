package com.flash.toolbar.omp.mapper;

import com.flash.toolbar.omp.model.SysLanguage;

public interface SysLanguageMapper {
    int deleteByPrimaryKey(String languageid);

    int insert(SysLanguage record);

    int insertSelective(SysLanguage record);

    SysLanguage selectByPrimaryKey(String languageid);

    int updateByPrimaryKeySelective(SysLanguage record);

    int updateByPrimaryKey(SysLanguage record);
}