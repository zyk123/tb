package com.flash.toolbar.mapper;

import com.flash.toolbar.model.CzPayResponse;

public interface CzPayResponseMapper {
    int deleteByPrimaryKey(String id);

    int insert(CzPayResponse record);

    int insertSelective(CzPayResponse record);

    CzPayResponse selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CzPayResponse record);

    int updateByPrimaryKey(CzPayResponse record);
}