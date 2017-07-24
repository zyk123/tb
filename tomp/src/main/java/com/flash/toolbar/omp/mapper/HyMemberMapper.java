package com.flash.toolbar.omp.mapper;

import com.flash.toolbar.omp.model.HyMember;

public interface HyMemberMapper {
    int deleteByPrimaryKey(String memberid);

    int insert(HyMember record);

    int insertSelective(HyMember record);

    HyMember selectByPrimaryKey(String memberid);

    int updateByPrimaryKeySelective(HyMember record);

    int updateByPrimaryKey(HyMember record);
}