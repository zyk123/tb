package com.flash.toolbar.omp.whiteList.service;

import java.util.List;

import com.flash.toolbar.omp.common.util.Page;
import com.flash.toolbar.omp.model.HyWhitelistDevice;

public interface WhiteListDeviceService {

	boolean deleteByPrimaryKey(HyWhitelistDevice wDevice,String[] array);

    boolean insert(HyWhitelistDevice wDevice);

    List<HyWhitelistDevice> selectByPage(HyWhitelistDevice wDevice,Page page);

    boolean updateByPrimaryKey(HyWhitelistDevice wDevice);
    
    boolean isExsit(HyWhitelistDevice wDevice);
}
