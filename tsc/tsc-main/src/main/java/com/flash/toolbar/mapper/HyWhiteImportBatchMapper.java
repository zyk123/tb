package com.flash.toolbar.mapper;

import com.flash.toolbar.model.HyWhiteImportBatch;

public interface HyWhiteImportBatchMapper {
    int deleteByPrimaryKey(String batchid);

    int insert(HyWhiteImportBatch record);

    int insertSelective(HyWhiteImportBatch record);

    HyWhiteImportBatch selectByPrimaryKey(String batchid);

    int updateByPrimaryKeySelective(HyWhiteImportBatch record);

    int updateByPrimaryKey(HyWhiteImportBatch record);
}