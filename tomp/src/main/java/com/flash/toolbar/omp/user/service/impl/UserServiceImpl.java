package com.flash.toolbar.omp.user.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flash.toolbar.omp.common.util.MD5Util;
import com.flash.toolbar.omp.common.util.Page;
import com.flash.toolbar.omp.common.util.StringUtil;
import com.flash.toolbar.omp.mapper.QxUserMapper;
import com.flash.toolbar.omp.mapper.QxUserRoleMapper;
import com.flash.toolbar.omp.model.AuthorizedMenuPrivilege;
import com.flash.toolbar.omp.model.QxRole;
import com.flash.toolbar.omp.model.QxUser;
import com.flash.toolbar.omp.model.QxUserRole;
import com.flash.toolbar.omp.user.bo.QxUserModel;
import com.flash.toolbar.omp.user.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService{
	
	@Resource
	private QxUserMapper qxUserMapper;
	
	@Resource
	private QxUserRoleMapper qxUserRoleMapper;
	
	@Override
	public QxUserModel checkUserLogin(QxUser qxUser) throws Exception{
		if(qxUser==null ||  StringUtil.isNull(qxUser.getUsername()) || StringUtil.isNull(qxUser.getPassword())){
			return null;
		}
		qxUser.setPassword(MD5Util.MD5(qxUser.getPassword()));
		QxUserModel qxUserNew = qxUserMapper.selectByBean(qxUser);
		return qxUserNew;
	}

	@Override
	public QxUser selectByPrimaryKey(QxUser user) {
		return qxUserMapper.selectByPrimaryKey(user);
	}

	@Override
	public List<QxUser> selectUserListByPage(QxUser user, Page page) {
		int total = qxUserMapper.selectUserCount(user);
		page.setTotalCount(total);
		if(page.getTotalPage() < page.getCurrentPage()){
			page.setCurrentPage(1);
		}
		return qxUserMapper.selectUserListByPage(user, page);
	}
	
	@Override
	public List<AuthorizedMenuPrivilege> selectAuthorizedMenuPrivileges (
			String userid, String countryno, String toperatorid) throws Exception {
		List<AuthorizedMenuPrivilege> listMenuPrivileges = qxUserMapper.selectMenuPrivilegesByRole(userid, countryno, toperatorid);
		return listMenuPrivileges;
	}

	@Override
	public boolean insertUser(QxUser user) {
		user.setPassword(MD5Util.MD5(user.getPassword()));
		if(qxUserMapper.insertUser(user) > 0){
			return true;
		}
		return false;
	}

	@Override
	public boolean updateUser(QxUser user) {
		if(qxUserMapper.updateUser(user) > 0){
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteUser(String[] ids, QxUser user) {
		if(qxUserMapper.deleteUser(ids, user) > 0){
			return true;
		}
		return false;
	}

	@Override
	public List<QxRole> selectRoleByUser(QxUserRole userRole) {
		return qxUserRoleMapper.selectRoleByUser(userRole);
	}

	@Override
	public boolean insertUserRole(QxUserRole ur,List<QxUserRole> userRoles) {
		if(StringUtils.isNotEmpty(ur.getUserid())){
			qxUserRoleMapper.deleteUserRole(ur);
			if(userRoles.size() > 0){
				if(qxUserRoleMapper.insertUserRole(userRoles) <= 0){
					return false;
				}
			}
			return true;
		}
		return false;
	}	
	
	@Override
	public boolean isExsit(QxUser user) {
		if(qxUserMapper.selectTotalCount(user) > 0){
			return true;
		}
		return false;
	}
}
