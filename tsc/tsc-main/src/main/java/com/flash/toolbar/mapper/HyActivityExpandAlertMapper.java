package com.flash.toolbar.mapper;

import java.util.List;

import com.flash.toolbar.model.HyActivityExpandAlert;

public interface HyActivityExpandAlertMapper {
    int deleteByPrimaryKey(String id);

    int insert(HyActivityExpandAlert record);

    int insertSelective(HyActivityExpandAlert record);

    HyActivityExpandAlert selectByPrimaryKey(String id);
    
    List<HyActivityExpandAlert> selectBySelective(HyActivityExpandAlert record);

    int updateByPrimaryKeySelective(HyActivityExpandAlert record);

    int updateByPrimaryKey(HyActivityExpandAlert record);
}
