package com.flash.toolbar.omp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.flash.toolbar.omp.common.util.Page;
import com.flash.toolbar.omp.model.HyWhitelistDevice;

public interface HyWhitelistDeviceMapper {
	
	int selectTotalCount(HyWhitelistDevice wDevice);
	
    int deleteByPrimaryKey(@Param(value="wDevice")HyWhitelistDevice wDevice,
    		@Param(value="array")String[] array);

    int insert(HyWhitelistDevice wDevice);

    List<HyWhitelistDevice> selectByPage(@Param(value="wDevice")HyWhitelistDevice wDevice,
    		@Param(value="page")Page page);

    int updateByPrimaryKey(HyWhitelistDevice wDevice);
}