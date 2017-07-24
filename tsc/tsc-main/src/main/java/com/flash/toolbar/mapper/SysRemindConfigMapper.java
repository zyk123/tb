package com.flash.toolbar.mapper;

import java.util.List;

import com.flash.toolbar.model.SysRemindConfig;

public interface SysRemindConfigMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysRemindConfig record);

    int insertSelective(SysRemindConfig record);

    SysRemindConfig selectByPrimaryKey(String id);
    
    List<SysRemindConfig> selectBySelective(SysRemindConfig sysRemindConfig);

    int updateByPrimaryKeySelective(SysRemindConfig record);

    int updateByPrimaryKey(SysRemindConfig record);
}