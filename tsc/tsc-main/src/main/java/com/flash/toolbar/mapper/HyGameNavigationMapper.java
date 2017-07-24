package com.flash.toolbar.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.flash.toolbar.model.HyGameNavigation;


public interface HyGameNavigationMapper {
	HyGameNavigation selectByPrimaryKey(String id);
	
	List<HyGameNavigation> selectBySelective(HyGameNavigation record);
	
	int insertSelective(HyGameNavigation record);
	
	int insert(HyGameNavigation record);
	
	int updateByPrimaryKeySelective(HyGameNavigation record);
	
	HyGameNavigation selectIconByPrimaryKey(HyGameNavigation record);
	
	int deleteByPrimaryKey(String id);
	
}
