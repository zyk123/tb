package com.flash.toolbar.service;

import java.util.Date;
import java.util.List;

import com.flash.toolbar.model.SysMainInterfaceIconConfig;

public interface ToolbarPortalManagerService {

	/**
	 * 判断用户是否展示toolbar，并初始化用户
	 * @param businessContent
	 * @return
	 * @throws Exception 
	 */
	public String isToolbarAvaliable(String businessContent) throws Exception;

	/**
	 * 从数据库中捞取用户信息放到session中
	 * @param businessStr
	 * @return
	 * @throws Exception
	 */
	public String queryToolbarSession(String businessStr) throws Exception;
	
	/**
	 * 将会员表中首次访问字段置为1
	 * @param memberId
	 * @param tOperatorId
	 * @param countryNo
	 * @return
	 */
	public String updateFirstShowState(String memberId,String tOperatorId,String countryNo) throws Exception;
	
	public List<SysMainInterfaceIconConfig> queryIcon(String type,String tOperatorId,String countryNo);

	public String updateActivityShowDate(String memberId, String tOperatorId, String countryNo, Date date) throws Exception;

	public boolean isActivityPopup(String mobileNo,String memberId,String serviceType);
}
