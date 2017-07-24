package com.flash.toolbar.omp.blacklist.service;

import java.util.List;

import com.flash.toolbar.omp.blacklist.bo.HyBlackListModel;
import com.flash.toolbar.omp.user.bo.QxUserModel;

public interface BlackListService {
	List<HyBlackListModel> getBlackListInfo(HyBlackListModel blackListModel) throws Exception;
	
	void deleteBlackList(String[] bListIds,QxUserModel qxUserModel) throws Exception;
	
	int countByCondition(HyBlackListModel blackListModel) throws Exception;
	
	void importBlackList(List<String> list,QxUserModel qxUserModel) throws Exception;
	
	void importBlackList2(String uuid, List<String> list,QxUserModel qxUserModel) throws Exception;
	
	void addBlackListImp2(String uuid,QxUserModel qxUserModel, int count) throws Exception;
}
