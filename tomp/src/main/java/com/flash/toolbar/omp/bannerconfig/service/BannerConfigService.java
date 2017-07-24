package com.flash.toolbar.omp.bannerconfig.service;

import java.util.List;

import com.flash.toolbar.omp.common.util.Page;
import com.flash.toolbar.omp.model.PzBannerConfig;

public interface BannerConfigService {
    
    List<PzBannerConfig> selectBannerconfigByPage(PzBannerConfig config,Page page);

    PzBannerConfig selectBannerconfigByPrimaryKey(PzBannerConfig config);

    boolean insertBannerconfig(PzBannerConfig config);

    boolean deleteBannerconfig(PzBannerConfig config,String[] array);

    boolean updateBannerconfig(PzBannerConfig config);

//    List<PzBannerConfig> selectAllBannerconfig(PzBannerConfig config);
}
