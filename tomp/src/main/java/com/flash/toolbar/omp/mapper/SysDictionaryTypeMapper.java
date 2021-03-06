package com.flash.toolbar.omp.mapper;

import com.flash.toolbar.omp.model.SysDictionaryType;

public interface SysDictionaryTypeMapper {
    int deleteByPrimaryKey(String dictionarytypeid);

    int insert(SysDictionaryType record);

    int insertSelective(SysDictionaryType record);

    SysDictionaryType selectByPrimaryKey(String dictionarytypeid);

    int updateByPrimaryKeySelective(SysDictionaryType record);

    int updateByPrimaryKey(SysDictionaryType record);
}