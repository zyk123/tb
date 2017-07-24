package com.flash.toolbar.omp.blacklist.service;

import java.util.List;

import com.flash.toolbar.omp.common.util.Page;
import com.flash.toolbar.omp.model.HyBlacklistDevice;

public interface BlackListDeviceService {

	boolean deleteByPrimaryKey(HyBlacklistDevice bDevice,String[] array);

    boolean insert(HyBlacklistDevice bDevice);

    List<HyBlacklistDevice> selectByPage(HyBlacklistDevice bDevice,Page page);

    boolean updateByPrimaryKey(HyBlacklistDevice bDevice);
    
    boolean isExsit(HyBlacklistDevice bDevice);
}
