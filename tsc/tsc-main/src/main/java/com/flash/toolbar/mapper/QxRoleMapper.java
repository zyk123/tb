package com.flash.toolbar.mapper;

import com.flash.toolbar.model.QxRole;

public interface QxRoleMapper {
    int deleteByPrimaryKey(String roleid);

    int insert(QxRole record);

    int insertSelective(QxRole record);

    QxRole selectByPrimaryKey(String roleid);

    int updateByPrimaryKeySelective(QxRole record);

    int updateByPrimaryKey(QxRole record);
}