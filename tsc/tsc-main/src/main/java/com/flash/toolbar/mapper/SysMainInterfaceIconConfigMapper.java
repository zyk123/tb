package com.flash.toolbar.mapper;

import java.util.List;

import com.flash.toolbar.model.SysMainInterfaceIconConfig;

public interface SysMainInterfaceIconConfigMapper {
	
    List<SysMainInterfaceIconConfig> selectAllIcon(SysMainInterfaceIconConfig config);
    
    SysMainInterfaceIconConfig selectIconByPrimaryKey(SysMainInterfaceIconConfig config);
}