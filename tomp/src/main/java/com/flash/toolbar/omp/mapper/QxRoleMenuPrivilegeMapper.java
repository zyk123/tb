package com.flash.toolbar.omp.mapper;

import java.util.List;

import com.flash.toolbar.omp.model.QxMenuPrivilege;
import com.flash.toolbar.omp.model.QxRoleMenuPrivilege;

public interface QxRoleMenuPrivilegeMapper {
	
	List<QxMenuPrivilege> selectMenuByRole(QxRoleMenuPrivilege roleMenuPrivilege);
	
	int deleteRoleMenuPrivilege(QxRoleMenuPrivilege roleMenuPrivilege);
	
	int insertRoleMenuPrivilege(List<QxRoleMenuPrivilege> rQxRoleMenuPrivileges);
}