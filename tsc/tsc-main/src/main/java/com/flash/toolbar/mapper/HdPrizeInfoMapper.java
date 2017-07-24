package com.flash.toolbar.mapper;

import com.flash.toolbar.model.HdPrizeInfo;

public interface HdPrizeInfoMapper {
    int deleteByPrimaryKey(String prizeid);

    int insert(HdPrizeInfo record);

    int insertSelective(HdPrizeInfo record);

    HdPrizeInfo selectByPrimaryKey(String prizeid);

    int updateByPrimaryKeySelective(HdPrizeInfo record);

    int updateByPrimaryKey(HdPrizeInfo record);
}