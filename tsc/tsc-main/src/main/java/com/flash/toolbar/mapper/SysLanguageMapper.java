package com.flash.toolbar.mapper;

import com.flash.toolbar.model.SysLanguage;

public interface SysLanguageMapper {
    int deleteByPrimaryKey(String languageid);

    int insert(SysLanguage record);

    int insertSelective(SysLanguage record);

    SysLanguage selectByPrimaryKey(String languageid);

    int updateByPrimaryKeySelective(SysLanguage record);

    int updateByPrimaryKey(SysLanguage record);
}