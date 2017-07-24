package com.flash.toolbar.omp.menu.service;

import java.util.List;

import com.flash.toolbar.omp.common.util.Page;
import com.flash.toolbar.omp.model.QxMenu;
import com.flash.toolbar.omp.model.QxMenuPrivilege;
import com.flash.toolbar.omp.model.QxPrivilege;

public interface QxMenuService {
	
	QxMenu selectMenuByPrimaryKey(QxMenu menu);
	
	List<QxMenu> selectParentMenu(QxMenu menu);

	List<QxMenu> selectMenuListByPage(QxMenu menu,Page page);
	
	boolean deleteMenu(String[] array,String countryno,String toperatorid);
	
	boolean insertMenu(QxMenu menu);
    
    boolean updateMenu(QxMenu menu);
    
    List<QxPrivilege> selectPrivilegeByMenu(QxMenuPrivilege menuPrivilege);
	
	boolean updateMenuPrivilege(QxMenuPrivilege mp,List<QxMenuPrivilege> menuPrivileges);
	
	boolean isExsit(QxMenu menu);
}
