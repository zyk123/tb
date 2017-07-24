package com.flash.toolbar.omp.mapper;

import com.flash.toolbar.omp.model.HdPrizeRecord;

public interface HdPrizeRecordMapper {
    int deleteByPrimaryKey(String recordid);

    int insert(HdPrizeRecord record);

    int insertSelective(HdPrizeRecord record);

    HdPrizeRecord selectByPrimaryKey(String recordid);

    int updateByPrimaryKeySelective(HdPrizeRecord record);

    int updateByPrimaryKey(HdPrizeRecord record);
}