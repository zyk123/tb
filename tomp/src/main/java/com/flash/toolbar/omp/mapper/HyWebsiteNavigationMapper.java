package com.flash.toolbar.omp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.flash.toolbar.omp.model.HyWebsiteNavigation;
import com.flash.toolbar.omp.navigation.bo.HyWebsiteNavigationModel;

public interface HyWebsiteNavigationMapper {
	HyWebsiteNavigation selectByPrimaryKey(String id);
	
	List<HyWebsiteNavigation> selectByPageSelective(HyWebsiteNavigationModel hyWebsiteNavigationModel);
	
	int countByCondition(HyWebsiteNavigationModel model);
	
	int insertSelective(HyWebsiteNavigation record);
	
	int insert(HyWebsiteNavigation record);
	
	int updateByPrimaryKeySelective(HyWebsiteNavigation record);
	
	HyWebsiteNavigation selectIconByPrimaryKey(HyWebsiteNavigation record);
	
	int deleteByPrimaryKey(String id);
	
	void updateOrderSort(@Param("operation")String operation,@Param("startIndex")int startIndex, @Param("endIndex")int endIndex);
}
