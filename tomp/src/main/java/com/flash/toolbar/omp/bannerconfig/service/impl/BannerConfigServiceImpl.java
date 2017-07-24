package com.flash.toolbar.omp.bannerconfig.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flash.toolbar.omp.bannerconfig.service.BannerConfigService;
import com.flash.toolbar.omp.common.util.Page;
import com.flash.toolbar.omp.mapper.PzBannerConfigMapper;
import com.flash.toolbar.omp.model.PzBannerConfig;

@Service
@Transactional
public class BannerConfigServiceImpl implements BannerConfigService{

	@Resource
	private PzBannerConfigMapper mapper;
	
	@Override
	public List<PzBannerConfig> selectBannerconfigByPage(
			PzBannerConfig config, Page page) {
		page.setTotalCount(mapper.selectTotalCount(config));
		if(page.getCurrentPage() > page.getTotalPage()){
			page.setCurrentPage(1);
		}
		return mapper.selectBannerconfigByPage(config, page);
	}

	@Override
	public PzBannerConfig selectBannerconfigByPrimaryKey(
			PzBannerConfig config) {
		return mapper.selectByPrimaryKey(config.getId());
	}

	@Override
	public boolean insertBannerconfig(PzBannerConfig config) {
		if(mapper.insertSelective(config) > 0){
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteBannerconfig(PzBannerConfig config, String[] array) {
		if(mapper.deleteBannerconfig(config, array) > 0){
			return true;
		}
		return false;
	}

	@Override
	public boolean updateBannerconfig(PzBannerConfig config) {
		if(mapper.updateByPrimaryKeySelective(config) > 0){
			return true;
		}
		return false;
	}

//	@Override
//	public List<PzBannerConfig> selectAllBannerconfig(PzBannerConfig config) {
//		return mapper.selectAllBannerconfig(config);
//	}

}
