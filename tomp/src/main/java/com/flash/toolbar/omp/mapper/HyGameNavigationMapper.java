package com.flash.toolbar.omp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.flash.toolbar.omp.model.HyGameNavigation;
import com.flash.toolbar.omp.navigation.bo.HyGameNavigationModel;

public interface HyGameNavigationMapper {
	HyGameNavigation selectByPrimaryKey(String id);
	
	List<HyGameNavigation> selectByPageSelective(HyGameNavigationModel hyGameNavigationModel);
	
	int countByCondition(HyGameNavigationModel model);
	
	int insertSelective(HyGameNavigation record);
	
	int insert(HyGameNavigation record);
	
	int updateByPrimaryKeySelective(HyGameNavigation record);
	
	HyGameNavigation selectIconByPrimaryKey(HyGameNavigation record);
	
	int deleteByPrimaryKey(String id);
	
	void updateOrderSort(@Param("operation")String operation,@Param("startIndex")int startIndex, @Param("endIndex")int endIndex);
}
