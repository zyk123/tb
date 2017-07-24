package com.flash.toolbar.mapper;

import java.util.List;
import java.util.Map;

import com.flash.toolbar.model.HyCloseSet;

public interface HyCloseSetMapper {
    int deleteByPrimaryKey(String closesetid);

    int insert(HyCloseSet record);

    int insertSelective(HyCloseSet record);

    HyCloseSet selectByPrimaryKey(String closesetid);
    
    HyCloseSet selectByMobileNo(String mobileNo);
    
    List<HyCloseSet> atCloseTime(Map map);
    
    int countAtCloseTime(Map map);
    
    HyCloseSet selectByMemberId(HyCloseSet record);

    int updateByPrimaryKeySelective(HyCloseSet record);

    int updateByPrimaryKey(HyCloseSet record);
}