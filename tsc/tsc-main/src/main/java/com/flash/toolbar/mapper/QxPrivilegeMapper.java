package com.flash.toolbar.mapper;

import com.flash.toolbar.model.QxPrivilege;

public interface QxPrivilegeMapper {
    int deleteByPrimaryKey(String privilegeid);

    int insert(QxPrivilege record);

    int insertSelective(QxPrivilege record);

    QxPrivilege selectByPrimaryKey(String privilegeid);

    int updateByPrimaryKeySelective(QxPrivilege record);

    int updateByPrimaryKey(QxPrivilege record);
}