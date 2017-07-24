package com.flash.toolbar.omp.whiteList.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flash.toolbar.omp.common.util.Page;
import com.flash.toolbar.omp.mapper.HyWhitelistDeviceMapper;
import com.flash.toolbar.omp.model.HyWhitelistDevice;
import com.flash.toolbar.omp.whiteList.service.WhiteListDeviceService;

@Service
@Transactional
public class WhiteListDeviceServiceImpl implements WhiteListDeviceService {

	@Resource
	private HyWhitelistDeviceMapper whitelistDeviceMapper;
	
	@Override
	public boolean deleteByPrimaryKey(HyWhitelistDevice wDevice,String[] array) {
		if(whitelistDeviceMapper.deleteByPrimaryKey(wDevice, array)>0){
			return true;
		}
		return false;
	}

	@Override
	public boolean insert(HyWhitelistDevice wDevice) {
		if(whitelistDeviceMapper.insert(wDevice)>0){
			return true;
		}
		return false;
	}

	@Override
	public List<HyWhitelistDevice> selectByPage(HyWhitelistDevice wDevice,
			Page page) {
		page.setTotalCount(whitelistDeviceMapper.selectTotalCount(wDevice));
		if(page.getCurrentPage() > page.getTotalPage()){
			page.setCurrentPage(1);
		}		
		return whitelistDeviceMapper.selectByPage(wDevice, page);
	}

	@Override
	public boolean updateByPrimaryKey(HyWhitelistDevice wDevice) {
		if(whitelistDeviceMapper.updateByPrimaryKey(wDevice)>0){
			return true;
		}
		return false;
	}

	@Override
	public boolean isExsit(HyWhitelistDevice wDevice) {
		if(whitelistDeviceMapper.selectTotalCount(wDevice) > 0){
			return true;
		}
		return false;
	}

}
