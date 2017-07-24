package com.flash.toolbar.omp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.flash.toolbar.omp.common.util.Page;
import com.flash.toolbar.omp.model.PzBannerConfig;

public interface PzBannerConfigMapper {
    int deleteByPrimaryKey(String id);

    int insert(PzBannerConfig record);

    int insertSelective(PzBannerConfig record);

    PzBannerConfig selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PzBannerConfig record);

    int updateByPrimaryKeyWithBLOBs(PzBannerConfig record);

    int updateByPrimaryKey(PzBannerConfig record);

	int selectTotalCount(PzBannerConfig config);

	List<PzBannerConfig> selectBannerconfigByPage(@Param(value="config")PzBannerConfig config,
			@Param(value="page")Page page);

	int deleteBannerconfig(@Param(value="config")PzBannerConfig config, @Param(value="array")String[] array);
}