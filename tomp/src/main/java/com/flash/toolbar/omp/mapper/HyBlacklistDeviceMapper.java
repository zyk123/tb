package com.flash.toolbar.omp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.flash.toolbar.omp.common.util.Page;
import com.flash.toolbar.omp.model.HyBlacklistDevice;

public interface HyBlacklistDeviceMapper {
	
	int selectTotalCount(HyBlacklistDevice bDevice);
	
    int deleteByPrimaryKey(@Param(value="bDevice")HyBlacklistDevice bDevice,
    		@Param(value="array")String[] array);

    int insert(HyBlacklistDevice bDevice);

    List<HyBlacklistDevice> selectByPage(@Param(value="bDevice")HyBlacklistDevice bDevice,
    		@Param(value="page")Page page);

    int updateByPrimaryKey(HyBlacklistDevice bDevice);
}