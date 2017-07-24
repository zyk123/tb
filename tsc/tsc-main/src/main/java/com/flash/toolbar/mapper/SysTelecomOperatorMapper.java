package com.flash.toolbar.mapper;

import java.util.List;

import com.flash.toolbar.model.SysTelecomOperator;
import com.flash.toolbar.model.SysTelecomOperatorAndHyMember;

public interface SysTelecomOperatorMapper {
    int deleteByPrimaryKey(String toperatorid);

    int insert(SysTelecomOperator record);

    int insertSelective(SysTelecomOperator record);

    SysTelecomOperator selectByPrimaryKey(String toperatorid);
    
    SysTelecomOperatorAndHyMember selectByMemberId(String memberid);
    
    SysTelecomOperatorAndHyMember selectByMobileno(String mobileno);
    
    int updateByPrimaryKeySelective(SysTelecomOperator record);

    int updateByPrimaryKey(SysTelecomOperator record);
    
    List<SysTelecomOperator> selectAll();
}