package com.flash.toolbar.omp.user.service;

import java.util.List;

import com.flash.toolbar.omp.common.util.Page;
import com.flash.toolbar.omp.model.AuthorizedMenuPrivilege;
import com.flash.toolbar.omp.model.QxRole;
import com.flash.toolbar.omp.model.QxUser;
import com.flash.toolbar.omp.model.QxUserRole;
import com.flash.toolbar.omp.user.bo.QxUserModel;

public interface UserService {
	
	QxUserModel checkUserLogin(QxUser qxUser) throws Exception;
	
	QxUser selectByPrimaryKey(QxUser user);
    
    List<QxUser> selectUserListByPage(QxUser user,Page page);
    
    List<AuthorizedMenuPrivilege> selectAuthorizedMenuPrivileges(String userid,String countryno,String toperatorid) throws Exception;
    
    boolean insertUser(QxUser user);
    
    boolean updateUser(QxUser user);
    
    boolean deleteUser(String[] ids,QxUser user);
    
    List<QxRole> selectRoleByUser(QxUserRole userRole);
	
	boolean insertUserRole(QxUserRole ur,List<QxUserRole> userRoles);
	
	boolean isExsit(QxUser user);
}
