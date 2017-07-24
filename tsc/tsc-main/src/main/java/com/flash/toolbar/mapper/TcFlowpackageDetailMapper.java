package com.flash.toolbar.mapper;

import java.util.List;

import com.flash.toolbar.model.TcFlowpackageDetail;

public interface TcFlowpackageDetailMapper {
    int deleteByPrimaryKey(String packagedetailid);

    int insert(TcFlowpackageDetail record);

    int insertSelective(TcFlowpackageDetail record);

    TcFlowpackageDetail selectByPrimaryKey(String packagedetailid);

    int updateByPrimaryKeySelective(TcFlowpackageDetail record);

    int updateByPrimaryKey(TcFlowpackageDetail record);
    
    List<TcFlowpackageDetail> selectByPackageId(String packageid);
    
    List<TcFlowpackageDetail> selectByParentId(String parentid);
}