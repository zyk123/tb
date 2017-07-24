package com.flash.toolbar.mapper;

import java.util.List;
import java.util.Map;

import com.flash.toolbar.model.TcPackageType;

public interface TcPackageTypeMapper {
    int deleteByPrimaryKey(String packagetypeid);

    int insert(TcPackageType record);

    int insertSelective(TcPackageType record);

    TcPackageType selectByPrimaryKey(String packagetypeid);
    
    List<TcPackageType> selectAllPackageType(Map map);

    int updateByPrimaryKeySelective(TcPackageType record);

    int updateByPrimaryKey(TcPackageType record);
}