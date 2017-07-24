package com.flash.toolbar.mapper;

import com.flash.toolbar.model.QxMenu;

public interface QxMenuMapper {
    int deleteByPrimaryKey(String menuid);

    int insert(QxMenu record);

    int insertSelective(QxMenu record);

    QxMenu selectByPrimaryKey(String menuid);

    int updateByPrimaryKeySelective(QxMenu record);

    int updateByPrimaryKey(QxMenu record);
}