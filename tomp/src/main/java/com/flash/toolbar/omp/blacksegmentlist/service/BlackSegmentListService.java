package com.flash.toolbar.omp.blacksegmentlist.service;

import java.util.List;
import java.util.Map;

import com.flash.toolbar.omp.blacksegmentlist.bo.HyBlackSectionModel;
import com.flash.toolbar.omp.user.bo.QxUserModel;

public interface BlackSegmentListService {
	List<HyBlackSectionModel> getBlackSectionListInfo(HyBlackSectionModel blackSectionModel) throws Exception;
	
	void deleteBlackSectionList(String[] bListIds,QxUserModel qxUserModel) throws Exception;
	
	int countByCondition(HyBlackSectionModel blackSectionModel) throws Exception;
	
	void importBlackSectionList(List<Map<String, String>> list,QxUserModel qxUserModel) throws Exception;

}
