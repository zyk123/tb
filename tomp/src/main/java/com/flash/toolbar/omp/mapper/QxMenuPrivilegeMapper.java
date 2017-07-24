package com.flash.toolbar.omp.mapper;

import java.util.List;

import com.flash.toolbar.omp.model.QxMenuPrivilege;
import com.flash.toolbar.omp.model.QxPrivilege;

public interface QxMenuPrivilegeMapper {
	
	List<QxPrivilege> selectPrivilegeByMenu(QxMenuPrivilege menuPrivilege);
	
	int deleteMenuPrivilege(QxMenuPrivilege menuPrivilege);
	
	int insertMenuPrivilege(List<QxMenuPrivilege> menuPrivilege);
}