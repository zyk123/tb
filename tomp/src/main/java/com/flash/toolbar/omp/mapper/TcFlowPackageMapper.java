package com.flash.toolbar.omp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.flash.toolbar.omp.model.TcFlowPackage;
import com.flash.toolbar.omp.packagemange.bo.TcFlowPackageModel;
import com.flash.toolbar.omp.packagemange.bo.TcFlowPackageRelation;
import com.flash.toolbar.omp.user.bo.QxUserModel;

public interface TcFlowPackageMapper {
    int deleteByPrimaryKey(String packageid);

    int insert(TcFlowPackage record);

    int insertSelective(TcFlowPackage record);

    TcFlowPackage selectByPrimaryKey(String packageid);
    
    List<TcFlowPackage> selectByPageSelective(TcFlowPackageModel model);    
    
    List<TcFlowPackageRelation> getRelationPackageList(@Param("packagetypeid") String packagetypeid,@Param("qxUserModel")QxUserModel qxUserModel);

    int updateByPrimaryKeySelective(TcFlowPackage record);

    int updateByPrimaryKey(TcFlowPackage record);
    
    int batchUpdatePackageDel(@Param("bListIds")String[] bListIds,@Param("qxUserModel")QxUserModel qxUserModel);
    
    int batchUpdateStatus(@Param("bListIds")String[] bListIds,@Param("status") String status,@Param("qxUserModel")QxUserModel qxUserModel);
    
    int countByCondition(TcFlowPackageModel model);
}