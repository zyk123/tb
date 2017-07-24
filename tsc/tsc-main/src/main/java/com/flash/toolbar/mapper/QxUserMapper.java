package com.flash.toolbar.mapper;

import com.flash.toolbar.model.QxUser;

public interface QxUserMapper {
    int deleteByPrimaryKey(String userid);

    int insert(QxUser record);

    int insertSelective(QxUser record);

    QxUser selectByPrimaryKey(String userid);

    int updateByPrimaryKeySelective(QxUser record);

    int updateByPrimaryKey(QxUser record);
}