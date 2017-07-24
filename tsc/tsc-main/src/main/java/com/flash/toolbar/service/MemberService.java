package com.flash.toolbar.service;

import java.util.List;

import com.flash.toolbar.model.HyAlertThreshold;
import com.flash.toolbar.model.HyCloseSet;
import com.flash.toolbar.model.HyFeedback;
import com.flash.toolbar.model.HyFlowAlert;
import com.flash.toolbar.model.HyGameNavigation;
import com.flash.toolbar.model.HyWebsiteNavigation;
import com.flash.toolbar.model.SysTelecomOperatorAndHyMember;
import com.flash.toolbar.model.TcFlowPackage;
import com.flash.toolbar.model.TcPackageType;

public interface MemberService {
	/**
	 * 用户关闭设置
	 * @param bean
	 * @return
	 */
	String setToolbarAvaliable(HyCloseSet bean,String requestSerial);
	
	/**
	 * 获取默认设置
	 * @param bean
	 * @return
	 */
	String getDefaultSetting(HyCloseSet bean);
	
	/**
	 * 查询用户是否已提醒流量
	 * @param alert
	 * @return
	 */
	List<String> selectByMemberIdAndDate(HyFlowAlert alert); 
	
	/**
	 * 保存流量提醒
	 * @param alert
	 * @return
	 */
	int insertSelective(HyFlowAlert alert);
	
	/**
	 * 查询用户流量阈值设置
	 * @param threshold
	 * @return
	 */
	HyAlertThreshold queryAlertThreshold(HyAlertThreshold threshold);
	
	/**
	 * 查询套餐详情
	 * @param record
	 * @return
	 */
	TcFlowPackage queryFlowPackageByFGNo(TcFlowPackage record);
	
	/**
	 * 查询套餐类型
	 * @param packageTypeid
	 * @return
	 */
	TcPackageType queryPackageTypeByPrimaryKey(String packageTypeid);
	
	/**
	 * 查询手机号所属运营商信息
	 * @param memberid
	 * @return
	 */
	SysTelecomOperatorAndHyMember queryMemberSys(String memberid);
	
	/**
	 * 保存用户反馈信息
	 * @param record
	 * @return
	 */
	int saveFeedback(HyFeedback record);
	
	
	/**
	 * 查询游戏列表
	 * @param record
	 * @return
	 */
	List<HyGameNavigation> queryGameList(HyGameNavigation record);
	
	/**
	 * 查询游戏图标
	 * @param record
	 * @return
	 */
	HyGameNavigation queryGameIcon(HyGameNavigation record);
	
	/**
	 * 查询网站列表
	 * @param record
	 * @return
	 */
	List<HyWebsiteNavigation> queryWebsiteList(HyWebsiteNavigation record);
	
	/**
	 * 查询网站图标
	 * @param record
	 * @return
	 */
	HyWebsiteNavigation queryWebsiteIcon(HyWebsiteNavigation record);		
}
