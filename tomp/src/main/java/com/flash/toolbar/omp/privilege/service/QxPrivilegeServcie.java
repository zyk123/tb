package com.flash.toolbar.omp.privilege.service;

import java.util.List;

import com.flash.toolbar.omp.common.util.Page;
import com.flash.toolbar.omp.model.QxPrivilege;

public interface QxPrivilegeServcie {
	
	QxPrivilege selectPrivilegeByPrimaryKey(QxPrivilege privilege);

	List<QxPrivilege> selectPrivilegeListByPage(QxPrivilege privilege,Page page);
	
	boolean deletePrivilege(String[] array,String countryno,String toperatorid);
	
	boolean insertPrivilege(QxPrivilege privilege);
    
    boolean updatePrivilege(QxPrivilege privilege);
    
    boolean isExsit(QxPrivilege privilege);
}
