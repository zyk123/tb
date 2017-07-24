package com.flash.toolbar.mapper;

import java.util.List;

import com.flash.toolbar.model.SysOperatorSeg;
import com.flash.toolbar.model.SysTelecomOperatorAndSysOperatorSeg;

public interface SysOperatorSegMapper {
    int deleteByPrimaryKey(String operatorsegid);

    int insert(SysOperatorSeg record);

    int insertSelective(SysOperatorSeg record);

    SysOperatorSeg selectByPrimaryKey(String operatorsegid);
    
    SysOperatorSeg selectByMobileNo(String mobileNo);
    
    List<SysTelecomOperatorAndSysOperatorSeg> selectByMobileNoUnionTelecomOperator(String mobileNo);

    int updateByPrimaryKeySelective(SysOperatorSeg record);

    int updateByPrimaryKey(SysOperatorSeg record);
    
    List<SysOperatorSeg> selectAll();
}