package com.flash.toolbar.mapper;

import com.flash.toolbar.model.QxUserRole;

public interface QxUserRoleMapper {
    int deleteByPrimaryKey(String userroleid);

    int insert(QxUserRole record);

    int insertSelective(QxUserRole record);

    QxUserRole selectByPrimaryKey(String userroleid);

    int updateByPrimaryKeySelective(QxUserRole record);

    int updateByPrimaryKey(QxUserRole record);
}