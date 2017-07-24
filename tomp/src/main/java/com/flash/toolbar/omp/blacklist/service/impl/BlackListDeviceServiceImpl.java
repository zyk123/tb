package com.flash.toolbar.omp.blacklist.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flash.toolbar.omp.blacklist.service.BlackListDeviceService;
import com.flash.toolbar.omp.common.util.Page;
import com.flash.toolbar.omp.mapper.HyBlacklistDeviceMapper;
import com.flash.toolbar.omp.model.HyBlacklistDevice;
import com.flash.toolbar.omp.model.HyWhitelistDevice;

@Service
@Transactional
public class BlackListDeviceServiceImpl implements BlackListDeviceService {

	@Resource
	private HyBlacklistDeviceMapper blacklistDeviceMapper;
	
	@Override
	public boolean deleteByPrimaryKey(HyBlacklistDevice bDevice,String[] array) {
		if(blacklistDeviceMapper.deleteByPrimaryKey(bDevice, array)>0){
			return true;
		}
		return false;
	}

	@Override
	public boolean insert(HyBlacklistDevice bDevice) {
		if(blacklistDeviceMapper.insert(bDevice)>0){
			return true;
		}
		return false;
	}

	@Override
	public List<HyBlacklistDevice> selectByPage(HyBlacklistDevice bDevice,
			Page page) {
		page.setTotalCount(blacklistDeviceMapper.selectTotalCount(bDevice));
		if(page.getCurrentPage() > page.getTotalPage()){
			page.setCurrentPage(1);
		}		
		return blacklistDeviceMapper.selectByPage(bDevice, page);
	}

	@Override
	public boolean updateByPrimaryKey(HyBlacklistDevice bDevice) {
		if(blacklistDeviceMapper.updateByPrimaryKey(bDevice)>0){
			return true;
		}
		return false;
	}
	
	@Override
	public boolean isExsit(HyBlacklistDevice bDevice) {
		if(blacklistDeviceMapper.selectTotalCount(bDevice) > 0){
			return true;
		}
		return false;
	}

}
