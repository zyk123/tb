package com.flash.toolbar.omp.role.servcie;

import java.util.List;

import com.flash.toolbar.omp.common.util.Page;
import com.flash.toolbar.omp.model.QxMenuPrivilege;
import com.flash.toolbar.omp.model.QxRoleMenuPrivilege;
import com.flash.toolbar.omp.model.QxRole;

public interface QxRoleService {
	
QxRole selectByPrimaryKey(QxRole role);
    
	List<QxRole> selectRoleListByPage(QxRole role,Page page);
    
    boolean insertRole(QxRole role);
    
    boolean updateRole(QxRole role);
    
    boolean deleteRole(String[] ids,QxRole role);

	List<QxMenuPrivilege> selectMenuByRole(QxRoleMenuPrivilege roleMenuPrivilege);
	
	boolean updateRoleMenuPrivilege(QxRoleMenuPrivilege qrmp,List<QxRoleMenuPrivilege> roleMenuPrivileges);

	boolean isExsit(QxRole role);
}
