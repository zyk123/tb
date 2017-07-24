package com.flash.toolbar.omp.whiteList.service;

import java.util.List;
import java.util.Map;

import com.flash.toolbar.omp.common.util.Page;
import com.flash.toolbar.omp.model.HyWhiteImportBatch;
import com.flash.toolbar.omp.model.HyWhiteList;
import com.flash.toolbar.omp.user.bo.QxUserModel;


public interface HyWhiteListService {

	Map<String, Object> selectWhiteList(HyWhiteList hwl,Page page);
	
	boolean saveWhiteList(List<HyWhiteList> hwList,HyWhiteImportBatch hwib);
	
	boolean delWhiteList(String[] ids,String countryNo,String tOperatorId);
	
	void importWhiteList2(String uuid, List<String> list,QxUserModel qxUserModel) throws Exception;
	
	void addWhiteListImp2(String uuid,QxUserModel qxUserModel, int count) throws Exception;
}
