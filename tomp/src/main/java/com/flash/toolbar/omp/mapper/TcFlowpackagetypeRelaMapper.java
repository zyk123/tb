package com.flash.toolbar.omp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.flash.toolbar.omp.model.TcFlowpackagetypeRela;

public interface TcFlowpackagetypeRelaMapper {
    int deleteByPrimaryKey(String relaid);
    
    int deleteSelective(TcFlowpackagetypeRela record);
    
    int deleteBatch(@Param("bListIds")String[] bListIds);

    int insert(TcFlowpackagetypeRela record);

    int insertSelective(TcFlowpackagetypeRela record);
    
    int insertBatch(List<TcFlowpackagetypeRela> list);

    TcFlowpackagetypeRela selectByPrimaryKey(String relaid);

    int updateByPrimaryKeySelective(TcFlowpackagetypeRela record);

    int updateByPrimaryKey(TcFlowpackagetypeRela record);
}