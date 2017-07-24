package com.flash.toolbar.omp.remind.service.impl;

import java.util.List;

import com.flash.toolbar.omp.blacklist.bo.HyBlackListModel;
import com.flash.toolbar.omp.model.SysRemindConfig;
import com.flash.toolbar.omp.remind.bo.SysRemindConfigModel;
import com.flash.toolbar.omp.user.bo.QxUserModel;


public interface RemindService {
	List<SysRemindConfig> getRemindListInfo(SysRemindConfigModel sysRemindConfigModel) throws Exception ;
	
	int countByCondition(SysRemindConfigModel sysRemindConfigModel) throws Exception;
	
	void batchOnOff(String[] batchIds,String type,QxUserModel qxUserModel) throws Exception;
	
	void batchSetTimeInterval(String[] batchIds,String remindVal,QxUserModel qxUserModel) throws Exception;
	
	int modify(SysRemindConfig sysRemindConfig) throws Exception;
	
	int add(SysRemindConfig sysRemindConfig) throws Exception;
	
	void deleteRemindList(String[] bListIds,QxUserModel qxUserModel) throws Exception;
}
