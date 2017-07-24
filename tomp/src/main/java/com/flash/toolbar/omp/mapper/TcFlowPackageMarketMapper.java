package com.flash.toolbar.omp.mapper;

import com.flash.toolbar.omp.model.TcFlowPackageMarket;

public interface TcFlowPackageMarketMapper {
    int deleteByPrimaryKey(String packagemarketid);

    int insert(TcFlowPackageMarket record);

    int insertSelective(TcFlowPackageMarket record);

    TcFlowPackageMarket selectByPrimaryKey(String packagemarketid);

    int updateByPrimaryKeySelective(TcFlowPackageMarket record);

    int updateByPrimaryKey(TcFlowPackageMarket record);
}