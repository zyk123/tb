package com.flash.toolbar.mapper;

import com.flash.toolbar.model.QxRoleMenuPrivilege;

public interface QxRoleMenuPrivilegeMapper {
    int deleteByPrimaryKey(String rmpid);

    int insert(QxRoleMenuPrivilege record);

    int insertSelective(QxRoleMenuPrivilege record);

    QxRoleMenuPrivilege selectByPrimaryKey(String rmpid);

    int updateByPrimaryKeySelective(QxRoleMenuPrivilege record);

    int updateByPrimaryKey(QxRoleMenuPrivilege record);
}