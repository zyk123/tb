package com.flash.toolbar.omp.icon.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flash.toolbar.omp.common.util.Page;
import com.flash.toolbar.omp.icon.service.IconService;
import com.flash.toolbar.omp.mapper.SysMainInterfaceIconConfigMapper;
import com.flash.toolbar.omp.model.SysMainInterfaceIconConfig;

@Service
@Transactional
public class IconServiceImpl implements IconService{

	@Resource
	private SysMainInterfaceIconConfigMapper mapper;
	
	@Override
	public List<SysMainInterfaceIconConfig> selectIconByPage(
			SysMainInterfaceIconConfig config, Page page) {
		page.setTotalCount(mapper.selectTotalCount(config));
		if(page.getCurrentPage() > page.getTotalPage()){
			page.setCurrentPage(1);
		}
		return mapper.selectIconByPage(config, page);
	}

	@Override
	public SysMainInterfaceIconConfig selectIconByPrimaryKey(
			SysMainInterfaceIconConfig config) {
		return mapper.selectIconByPrimaryKey(config);
	}

	@Override
	public SysMainInterfaceIconConfig selectPopupByPrimaryKey(
			SysMainInterfaceIconConfig config) {
		return mapper.selectPopupByPrimaryKey(config);
	}

	@Override
	public boolean insertIcon(SysMainInterfaceIconConfig config) {
		if(mapper.insertIcon(config) > 0){
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteIcon(SysMainInterfaceIconConfig config, String[] array) {
		if(mapper.deleteIcon(config, array) > 0){
			return true;
		}
		return false;
	}

	@Override
	public boolean updateIcon(SysMainInterfaceIconConfig config) {
		if(mapper.updateIcon(config) > 0){
			return true;
		}
		return false;
	}

	@Override
	public List<SysMainInterfaceIconConfig> selectAllIcon(SysMainInterfaceIconConfig config) {
		return mapper.selectAllIcon(config);
	}

}
