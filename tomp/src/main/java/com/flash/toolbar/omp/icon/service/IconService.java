package com.flash.toolbar.omp.icon.service;

import java.util.List;
import com.flash.toolbar.omp.common.util.Page;
import com.flash.toolbar.omp.model.SysMainInterfaceIconConfig;

public interface IconService {
    
    List<SysMainInterfaceIconConfig> selectIconByPage(SysMainInterfaceIconConfig config,Page page);

    SysMainInterfaceIconConfig selectIconByPrimaryKey(SysMainInterfaceIconConfig config);

    SysMainInterfaceIconConfig selectPopupByPrimaryKey(SysMainInterfaceIconConfig config);
    
    boolean insertIcon(SysMainInterfaceIconConfig config);

    boolean deleteIcon(SysMainInterfaceIconConfig config,String[] array);

    boolean updateIcon(SysMainInterfaceIconConfig config);

    List<SysMainInterfaceIconConfig> selectAllIcon(SysMainInterfaceIconConfig config);
}
