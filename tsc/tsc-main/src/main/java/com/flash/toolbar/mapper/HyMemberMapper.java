package com.flash.toolbar.mapper;

import java.util.Map;

import com.flash.toolbar.model.HyMember;

public interface HyMemberMapper {
    int deleteByPrimaryKey(String memberid);

    int insert(HyMember record);

    int insertSelective(HyMember record);

    HyMember selectByPrimaryKey(String memberid);
    
    HyMember selectByMobileNo(String mobileNo);
    
    HyMember selectByMap(Map map);
    
    String selectSequence();
    
    int updateByPrimaryKeySelective(HyMember record);

    int updateByPrimaryKey(HyMember record);
    
    int updateFirstShowState(HyMember member);
}