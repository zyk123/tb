package com.flash.toolbar.omp.privilege.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flash.toolbar.omp.common.util.Page;
import com.flash.toolbar.omp.mapper.QxPrivilegeMapper;
import com.flash.toolbar.omp.model.QxMenu;
import com.flash.toolbar.omp.model.QxPrivilege;
import com.flash.toolbar.omp.privilege.service.QxPrivilegeServcie;

@Service
public class QxPrivilegeServiceImpl implements QxPrivilegeServcie {

	@Resource
	private QxPrivilegeMapper qxPrivilegeMapper;

	@Override
	public QxPrivilege selectPrivilegeByPrimaryKey(QxPrivilege privilege) {
		return qxPrivilegeMapper.selectPrivilegeByPrimaryKey(privilege);
	}	
	
	@Override
	public List<QxPrivilege> selectPrivilegeListByPage(QxPrivilege privilege,Page page) {
		int total = qxPrivilegeMapper.selectPrivilegeCount(privilege);
		page.setTotalCount(total);
		if(page.getCurrentPage() > page.getTotalPage()){
			page.setCurrentPage(1);
		}
		if(0 < total){
			return qxPrivilegeMapper.selectPrivilegeListByPage(privilege, page);
		}
		return null;
	}

	@Override
	@Transactional(value="transactionManager",rollbackFor=java.lang.Exception.class)
	public boolean deletePrivilege(String[] array, String countryno,
			String toperatorid) {
		int i = qxPrivilegeMapper.deletePrivilege(array, countryno, toperatorid);
		if(0 < i)
		{
			return true;
		}
		return false;
	}

	@Override
	@Transactional(value="transactionManager",rollbackFor=java.lang.Exception.class)
	public boolean insertPrivilege(QxPrivilege privilege) {
		int i = qxPrivilegeMapper.insertPrivilege(privilege);
		if(0 < i)
		{
			return true;
		}
		return false;
	}

	@Override
	@Transactional(value="transactionManager",rollbackFor=java.lang.Exception.class)
	public boolean updatePrivilege(QxPrivilege privilege) {
		int i = qxPrivilegeMapper.updatePrivilege(privilege);
		if(0 < i)
		{
			return true;
		}
		return false;
	}
	
	@Override
	public boolean isExsit(QxPrivilege privilege) {
		if(qxPrivilegeMapper.selectTotalCount(privilege) > 0){
			return true;
		}
		return false;
	}
}
