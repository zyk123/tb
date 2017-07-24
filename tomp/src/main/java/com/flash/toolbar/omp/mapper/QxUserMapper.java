package com.flash.toolbar.omp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.flash.toolbar.omp.common.util.Page;
import com.flash.toolbar.omp.model.AuthorizedMenuPrivilege;
import com.flash.toolbar.omp.model.QxRole;
import com.flash.toolbar.omp.model.QxUser;
import com.flash.toolbar.omp.user.bo.QxUserModel;

public interface QxUserMapper {
	
	int selectTotalCount(QxUser user);
    
    QxUserModel selectByBean(QxUser qxUser);
    
    int selectUserCount(@Param(value="user")QxUser user);
    
    QxUser selectByPrimaryKey(QxUser user);
    
    List<QxUser> selectUserListByPage(@Param(value="user")QxUser user,@Param(value="page")Page page);
    
    List<AuthorizedMenuPrivilege> selectMenuPrivilegesByRole(@Param(value="userid")String userid,@Param(value="countryno")String countryno,@Param(value="toperatorid")String toperatorid);
    
    int insertUser(QxUser user);
    
    int updateUser(QxUser user);
    
    int deleteUser(@Param(value="array")String[] ids,@Param(value="user")QxUser user);
}