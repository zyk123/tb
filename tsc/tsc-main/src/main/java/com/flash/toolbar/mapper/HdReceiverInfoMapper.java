package com.flash.toolbar.mapper;

import com.flash.toolbar.model.HdReceiverInfo;

public interface HdReceiverInfoMapper {
    int deleteByPrimaryKey(String receiverid);

    int insert(HdReceiverInfo record);

    int insertSelective(HdReceiverInfo record);

    HdReceiverInfo selectByPrimaryKey(String receiverid);

    int updateByPrimaryKeySelective(HdReceiverInfo record);

    int updateByPrimaryKey(HdReceiverInfo record);
    
    HdReceiverInfo selectByMemberId(String memberid);
}