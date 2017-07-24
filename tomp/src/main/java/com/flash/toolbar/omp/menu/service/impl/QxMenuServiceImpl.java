package com.flash.toolbar.omp.menu.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flash.toolbar.omp.common.util.Page;
import com.flash.toolbar.omp.mapper.QxMenuMapper;
import com.flash.toolbar.omp.mapper.QxMenuPrivilegeMapper;
import com.flash.toolbar.omp.menu.service.QxMenuService;
import com.flash.toolbar.omp.model.QxMenu;
import com.flash.toolbar.omp.model.QxMenuPrivilege;
import com.flash.toolbar.omp.model.QxPrivilege;

@Service
public class QxMenuServiceImpl implements QxMenuService{
	
	@Resource
	private QxMenuMapper qxMenuMapper;
	
	@Resource
	private QxMenuPrivilegeMapper qxMenuPrivilegeMapper;

	@Override
	public QxMenu selectMenuByPrimaryKey(QxMenu menu) {
		return qxMenuMapper.selectMenuByPrimaryKey(menu);
	}	
	
	@Override
	public List<QxMenu> selectParentMenu(QxMenu menu){
		return qxMenuMapper.selectParentMenu(menu);
	}
	
	@Override
	public List<QxMenu> selectMenuListByPage(QxMenu menu,Page page) {
		int total = qxMenuMapper.selectMenuCount(menu);
		page.setTotalCount(total);
		if(page.getCurrentPage() > page.getTotalPage()){
			page.setCurrentPage(1);
		}
		if(0 < total){
			return qxMenuMapper.selectMenuListByPage(menu, page);
		}
		return null;
	}

	@Override
	@Transactional(value="transactionManager",rollbackFor=java.lang.Exception.class)
	public boolean deleteMenu(String[] array, String countryno,
			String toperatorid) {
		int i = qxMenuMapper.deleteMenu(array, countryno, toperatorid);
		if(0 < i)
		{
			return true;
		}
		return false;
	}

	@Override
	@Transactional(value="transactionManager",rollbackFor=java.lang.Exception.class)
	public boolean insertMenu(QxMenu menu) {
		int i = qxMenuMapper.insertMenu(menu);
		if(0 < i)
		{
			return true;
		}
		return false;
	}

	@Override
	@Transactional(value="transactionManager",rollbackFor=java.lang.Exception.class)
	public boolean updateMenu(QxMenu menu) {
		int i = qxMenuMapper.updateMenu(menu);
		if(0 < i)
		{
			return true;
		}
		return false;
	}

	@Override
	public List<QxPrivilege> selectPrivilegeByMenu(QxMenuPrivilege menuPrivilege) {
		return qxMenuPrivilegeMapper.selectPrivilegeByMenu(menuPrivilege);
	}

	@Override
	@Transactional(value="transactionManager",rollbackFor=java.lang.Exception.class)
	public boolean updateMenuPrivilege(QxMenuPrivilege mp,List<QxMenuPrivilege> menuPrivileges) {
		if(StringUtils.isNotEmpty(mp.getMenuid())){
			qxMenuPrivilegeMapper.deleteMenuPrivilege(mp);
			if(menuPrivileges.size() > 0){
				if(qxMenuPrivilegeMapper.insertMenuPrivilege(menuPrivileges) <= 0){
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	@Override
	public boolean isExsit(QxMenu menu) {
		if(qxMenuMapper.selectTotalCount(menu) > 0){
			return true;
		}
		return false;
	}

}
