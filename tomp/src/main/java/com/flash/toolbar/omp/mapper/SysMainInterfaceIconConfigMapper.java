package com.flash.toolbar.omp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.flash.toolbar.omp.common.util.Page;
import com.flash.toolbar.omp.model.SysMainInterfaceIconConfig;

public interface SysMainInterfaceIconConfigMapper {
	
    int selectTotalCount(SysMainInterfaceIconConfig config);
    
    List<SysMainInterfaceIconConfig> selectIconByPage(@Param(value="config")SysMainInterfaceIconConfig config,
    		@Param(value="page")Page page);
    
    SysMainInterfaceIconConfig selectIconByPrimaryKey(SysMainInterfaceIconConfig config);

    SysMainInterfaceIconConfig selectPopupByPrimaryKey(SysMainInterfaceIconConfig config);

    int insertIcon(SysMainInterfaceIconConfig config);

    int deleteIcon(@Param(value="config")SysMainInterfaceIconConfig config,
    		@Param(value="array")String[] array);

    int updateIcon(SysMainInterfaceIconConfig config);

    List<SysMainInterfaceIconConfig> selectAllIcon(SysMainInterfaceIconConfig config);
}