package com.flash.toolbar.mapper;

import com.flash.toolbar.model.JqOuterIpAuth;

public interface JqOuterIpAuthMapper {
    int deleteByPrimaryKey(String ipauthid);

    int insert(JqOuterIpAuth record);

    int insertSelective(JqOuterIpAuth record);

    JqOuterIpAuth selectByPrimaryKey(String ipauthid);

    int updateByPrimaryKeySelective(JqOuterIpAuth record);

    int updateByPrimaryKey(JqOuterIpAuth record);
}