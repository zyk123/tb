package com.flash.toolbar.mapper;

import java.util.List;

import com.flash.toolbar.model.TcFlowPackage;

public interface TcFlowPackageMapper {
    int deleteByPrimaryKey(String packageid);

    int insert(TcFlowPackage record);

    int insertSelective(TcFlowPackage record);

    TcFlowPackage selectByPrimaryKey(String packageid);
    
    TcFlowPackage selectByPackageNo(String packageno);
    
    List<TcFlowPackage> selectByPackageTypeId(String packagetypeid);
    
    int updateByPrimaryKeySelective(TcFlowPackage record);

    int updateByPrimaryKey(TcFlowPackage record);
    
    TcFlowPackage selectByFGNo(TcFlowPackage record);
    
    List<TcFlowPackage> selectByFlowPackageTypeId(String packagetypeid);
}