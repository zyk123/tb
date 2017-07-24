package com.flash.toolbar.mapper;

import com.flash.toolbar.model.HyBlackImportBatch;

public interface HyBlackImportBatchMapper {
    int deleteByPrimaryKey(String batchid);

    int insert(HyBlackImportBatch record);

    int insertSelective(HyBlackImportBatch record);

    HyBlackImportBatch selectByPrimaryKey(String batchid);

    int updateByPrimaryKeySelective(HyBlackImportBatch record);

    int updateByPrimaryKey(HyBlackImportBatch record);
}