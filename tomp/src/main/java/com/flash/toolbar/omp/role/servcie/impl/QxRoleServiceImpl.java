package com.flash.toolbar.omp.role.servcie.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flash.toolbar.omp.common.util.Page;
import com.flash.toolbar.omp.mapper.QxRoleMapper;
import com.flash.toolbar.omp.mapper.QxRoleMenuPrivilegeMapper;
import com.flash.toolbar.omp.model.QxMenuPrivilege;
import com.flash.toolbar.omp.model.QxRoleMenuPrivilege;
import com.flash.toolbar.omp.model.QxRole;
import com.flash.toolbar.omp.role.servcie.QxRoleService;

@Service
public class QxRoleServiceImpl  implements QxRoleService{
	
	@Resource
	private QxRoleMapper qxRoleMapper;
	
	@Resource
	private QxRoleMenuPrivilegeMapper qxRoleMenuPrivilegeMapper;

	@Override
	public List<QxMenuPrivilege> selectMenuByRole(	QxRoleMenuPrivilege roleMenuPrivilege) {
		return qxRoleMenuPrivilegeMapper.selectMenuByRole(roleMenuPrivilege);
	}

	@Override
	@Transactional(value="transactionManager",rollbackFor=java.lang.Exception.class)
	public boolean updateRoleMenuPrivilege(QxRoleMenuPrivilege qrmp,List<QxRoleMenuPrivilege> roleMenuPrivileges) {
		if(StringUtils.isNotEmpty(qrmp.getRoleid())){
			qxRoleMenuPrivilegeMapper.deleteRoleMenuPrivilege(qrmp);
			if(roleMenuPrivileges.size() > 0){
				if(qxRoleMenuPrivilegeMapper.insertRoleMenuPrivilege(roleMenuPrivileges) <= 0){
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	@Override
	public QxRole selectByPrimaryKey(QxRole role) {
		return qxRoleMapper.selectRoleByPrimaryKey(role);
	}

	@Override
	public List<QxRole> selectRoleListByPage(QxRole role, Page page) {
		int total = qxRoleMapper.selectRoleCount(role);
		page.setTotalCount(total);
		if(page.getTotalPage() < page.getCurrentPage()){
			page.setCurrentPage(1);
		}
		return qxRoleMapper.selectRoleListByPage(role, page);
	}

	@Override
	public boolean insertRole(QxRole role) {
		if(qxRoleMapper.insertRole(role) > 0){
			return true;
		}
		return false;
	}

	@Override
	public boolean updateRole(QxRole role) {
		if(qxRoleMapper.updateRole(role) > 0){
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteRole(String[] ids, QxRole role) {
		if(qxRoleMapper.deleteRole(ids, role) > 0){
			return true;
		}
		return false;
	}

	@Override
	public boolean isExsit(QxRole role) {
		if(qxRoleMapper.selectTotalCount(role) > 0){
			return true;
		}
		return false;
	}
}
