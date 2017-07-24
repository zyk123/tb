package com.flash.toolbar.omp.navigation.service;

import java.util.List;

import com.flash.toolbar.omp.model.HyWebsiteNavigation;
import com.flash.toolbar.omp.navigation.bo.HyWebsiteNavigationModel;

public interface WebsiteNavService {
	List<HyWebsiteNavigation> getWebsiteNavList(HyWebsiteNavigationModel model) throws Exception;
	
	int countByCondition(HyWebsiteNavigationModel model) throws Exception;
	
	boolean insertIcon(HyWebsiteNavigation hyWebsiteNavigation) throws Exception;
	
	HyWebsiteNavigation selectIconByPrimaryKey(HyWebsiteNavigation hyWebsiteNavigation) throws Exception;
	
	void deleteWebsiteNav(String id,int orderCount,int orderNo) throws Exception;
	
	boolean modifyWebsiteNav(HyWebsiteNavigation hyWebsiteNavigation) throws Exception;
}
