package com.flash.toolbar.omp.mapper;

import com.flash.toolbar.omp.model.SysDictionary;

public interface SysDictionaryMapper {
    int deleteByPrimaryKey(String dictionaryid);

    int insert(SysDictionary record);

    int insertSelective(SysDictionary record);

    SysDictionary selectByPrimaryKey(String dictionaryid);

    int updateByPrimaryKeySelective(SysDictionary record);

    int updateByPrimaryKey(SysDictionary record);
}