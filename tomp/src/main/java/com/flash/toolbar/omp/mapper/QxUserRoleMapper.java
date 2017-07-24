package com.flash.toolbar.omp.mapper;

import java.util.List;

import com.flash.toolbar.omp.model.QxRole;
import com.flash.toolbar.omp.model.QxUserRole;

public interface QxUserRoleMapper {
    
	List<QxRole> selectRoleByUser(QxUserRole userRole);
	
	int deleteUserRole(QxUserRole userRoles);
	
	int insertUserRole(List<QxUserRole> userRoles);
	
}