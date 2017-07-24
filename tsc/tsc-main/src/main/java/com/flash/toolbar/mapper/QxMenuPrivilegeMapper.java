package com.flash.toolbar.mapper;

import com.flash.toolbar.model.QxMenuPrivilege;

public interface QxMenuPrivilegeMapper {
    int deleteByPrimaryKey(String menuprivilegeid);

    int insert(QxMenuPrivilege record);

    int insertSelective(QxMenuPrivilege record);

    QxMenuPrivilege selectByPrimaryKey(String menuprivilegeid);

    int updateByPrimaryKeySelective(QxMenuPrivilege record);

    int updateByPrimaryKey(QxMenuPrivilege record);
}