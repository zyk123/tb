package com.flash.toolbar.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.flash.toolbar.model.HyWebsiteNavigation;

public interface HyWebsiteNavigationMapper {
	HyWebsiteNavigation selectByPrimaryKey(String id);
	
	List<HyWebsiteNavigation> selectBySelective(HyWebsiteNavigation record);
	
	int insertSelective(HyWebsiteNavigation record);
	
	int insert(HyWebsiteNavigation record);
	
	int updateByPrimaryKeySelective(HyWebsiteNavigation record);
	
	HyWebsiteNavigation selectIconByPrimaryKey(HyWebsiteNavigation record);
	
	int deleteByPrimaryKey(String id);
	
}
