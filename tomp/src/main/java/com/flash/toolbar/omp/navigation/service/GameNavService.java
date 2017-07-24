package com.flash.toolbar.omp.navigation.service;

import java.util.List;

import com.flash.toolbar.omp.model.HyGameNavigation;
import com.flash.toolbar.omp.navigation.bo.HyGameNavigationModel;

public interface GameNavService {
	List<HyGameNavigation> getGameNavList(HyGameNavigationModel model) throws Exception;
	
	int countByCondition(HyGameNavigationModel model) throws Exception;
	
	boolean insertIcon(HyGameNavigation hyGameNavigation) throws Exception;
	
	HyGameNavigation selectIconByPrimaryKey(HyGameNavigation hyGameNavigation) throws Exception;
	
	void deleteGameNav(String id,int orderCount,int orderNo) throws Exception;
	
	boolean modifyGameNav(HyGameNavigation hyGameNavigation) throws Exception;
}
