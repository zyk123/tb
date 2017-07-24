package com.flash.toolbar.omp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.flash.toolbar.omp.common.util.Page;
import com.flash.toolbar.omp.model.QxPrivilege;

public interface QxPrivilegeMapper {
	
	int selectTotalCount(QxPrivilege privilege);
	
    int selectPrivilegeCount(@Param(value="privilege")QxPrivilege privilege);
    
    QxPrivilege selectPrivilegeByPrimaryKey(QxPrivilege privilege);
    
    List<QxPrivilege> selectPrivilegeListByPage(@Param(value="privilege")QxPrivilege privilege,@Param(value="page")Page page);
    
    int deletePrivilege(@Param(value="array")String[] array,@Param(value="countryno")String countryno,@Param(value="toperatorid")String toperatorid);
    
    int insertPrivilege(QxPrivilege privilege);
    
    int updatePrivilege(QxPrivilege privilege);
}