package com.flash.toolbar.mapper;

import com.flash.toolbar.model.TcFlowpackagetypeRela;

public interface TcFlowpackagetypeRelaMapper {
    int deleteByPrimaryKey(String relaid);

    int insert(TcFlowpackagetypeRela record);

    int insertSelective(TcFlowpackagetypeRela record);

    TcFlowpackagetypeRela selectByPrimaryKey(String relaid);

    int updateByPrimaryKeySelective(TcFlowpackagetypeRela record);

    int updateByPrimaryKey(TcFlowpackagetypeRela record);
}