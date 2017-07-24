package com.flash.toolbar.mapper;

import com.flash.toolbar.model.CzPayRequest;

public interface CzPayRequestMapper {
    int deleteByPrimaryKey(String id);

    int insert(CzPayRequest record);

    int insertSelective(CzPayRequest record);

    CzPayRequest selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CzPayRequest record);

    int updateByPrimaryKey(CzPayRequest record);
}