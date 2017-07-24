package com.flash.toolbar.omp.mapper;

import com.flash.toolbar.omp.model.HdReceiverInfo;

public interface HdReceiverInfoMapper {
    int deleteByPrimaryKey(String receiverid);

    int insert(HdReceiverInfo record);

    int insertSelective(HdReceiverInfo record);

    HdReceiverInfo selectByPrimaryKey(String receiverid);

    int updateByPrimaryKeySelective(HdReceiverInfo record);

    int updateByPrimaryKey(HdReceiverInfo record);
}