package com.flash.toolbar.omp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.flash.toolbar.omp.common.util.Page;
import com.flash.toolbar.omp.model.QxRole;

public interface QxRoleMapper {
	
	int selectTotalCount(QxRole role);
    
	int selectRoleCount(@Param(value="role")QxRole role);
    
    QxRole selectRoleByPrimaryKey(QxRole role);
    
    List<QxRole> selectRoleListByPage(@Param(value="role")QxRole role,@Param(value="page")Page page);
    
    int insertRole(QxRole role);
    
    int updateRole(QxRole role);
    
    int deleteRole(@Param(value="array")String[] ids,@Param(value="role")QxRole role);
}