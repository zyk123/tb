package com.flash.toolbar.omp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.flash.toolbar.omp.common.util.Page;
import com.flash.toolbar.omp.model.QxMenu;

public interface QxMenuMapper {
	
	int selectTotalCount(QxMenu menu);
	
	int selectMenuCount(@Param(value="menu")QxMenu menu);
	
	QxMenu selectMenuByPrimaryKey(QxMenu menu);
	
	List<QxMenu> selectParentMenu(QxMenu menu);
	
	List<QxMenu> selectMenuListByPage(@Param(value="menu")QxMenu menu,@Param(value="page")Page page);
	
	int deleteMenu(@Param(value="array")String[] array,@Param(value="countryno")String countryno,@Param(value="toperatorid")String toperatorid);
	
	int insertMenu(QxMenu menu);
	
	int updateMenu(QxMenu menu);
}