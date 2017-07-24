package com.flash.toolbar.omp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.flash.toolbar.omp.model.TcPackageType;
import com.flash.toolbar.omp.packagemange.bo.TcPackageTypeModel;
import com.flash.toolbar.omp.user.bo.QxUserModel;

public interface TcPackageTypeMapper {
    int deleteByPrimaryKey(String packagetypeid);
    
    int deleteBacth(@Param("bListIds")String[] bListIds,@Param("qxUserModel")QxUserModel qxUserModel);

    int insert(TcPackageType record);

    int insertSelective(TcPackageType record);

    TcPackageType selectByPrimaryKey(String packagetypeid);
    
    List<TcPackageType> selectByPageSelective(TcPackageTypeModel model);   

    int updateByPrimaryKeySelective(TcPackageType record);

    int updateByPrimaryKey(TcPackageType record);
    
    int updateOrderNo(@Param("packagetypeid")String packagetypeid,@Param("type")String type,@Param("qxUserModel")QxUserModel qxUserModel);
    
    int countByCondition(TcPackageTypeModel model);
    
    int countBySelective(TcPackageTypeModel model);
}