package com.flash.toolbar.omp.mapper;

import java.util.List;
import java.util.Set;

import com.flash.toolbar.omp.model.HyBlackImportBatch;

public interface HyBlackImportBatchMapper {
    int insert(HyBlackImportBatch record);

    int insertSelective(HyBlackImportBatch record);

    HyBlackImportBatch selectByPrimaryKey(String batchId);
    
    HyBlackImportBatch selectByBatchNo(String batchNo);
    
    List<String> selectByIdBatch(List<String> list);

}
