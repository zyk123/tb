package com.flash.toolbar.service.impl;



import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.flash.toolbar.common.util.BusinessLogger;
import com.flash.toolbar.common.util.Constant;
import com.flash.toolbar.common.util.ParamPropertiesUtil;
import com.flash.toolbar.common.util.StringUtil;
import com.flash.toolbar.mapper.HyAlertThresholdMapper;
import com.flash.toolbar.mapper.HyCloseSetMapper;
import com.flash.toolbar.mapper.HyFeedbackMapper;
import com.flash.toolbar.mapper.HyFlowAlertMapper;
import com.flash.toolbar.mapper.HyGameNavigationMapper;
import com.flash.toolbar.mapper.HyWebsiteNavigationMapper;
import com.flash.toolbar.mapper.RzCloseSetLogMapper;
import com.flash.toolbar.mapper.SysTelecomOperatorMapper;
import com.flash.toolbar.mapper.TcFlowPackageMapper;
import com.flash.toolbar.mapper.TcPackageTypeMapper;
import com.flash.toolbar.model.HyAlertThreshold;
import com.flash.toolbar.model.HyCloseSet;
import com.flash.toolbar.model.HyFeedback;
import com.flash.toolbar.model.HyFlowAlert;
import com.flash.toolbar.model.HyGameNavigation;
import com.flash.toolbar.model.HyWebsiteNavigation;
import com.flash.toolbar.model.RzCloseSetLog;
import com.flash.toolbar.model.SysTelecomOperatorAndHyMember;
import com.flash.toolbar.model.TcFlowPackage;
import com.flash.toolbar.model.TcPackageType;
import com.flash.toolbar.redis.RedisOperation;
import com.flash.toolbar.service.MemberService;

@Transactional
@Service("memberService")
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	private HyCloseSetMapper hyCloseSetMapper;
	@Autowired
	private RzCloseSetLogMapper rzCloseSetLogMapper;
	@Autowired
	private HyFlowAlertMapper hyFlowAlertMapper;
	@Autowired
	private HyAlertThresholdMapper hyAlertThresholdMapper;
	@Autowired
	private TcFlowPackageMapper tcFlowPackageMapper;
	@Autowired
	private TcPackageTypeMapper tcPackageTypeMapper;
	@Autowired
	private SysTelecomOperatorMapper sysTelecomOperatorMapper;
	@Autowired
	private HyFeedbackMapper hyFeedbackMapper;
	@Autowired
	private HyGameNavigationMapper hyGameNavigationMapper;	
	@Autowired
	private HyWebsiteNavigationMapper hyWebsiteNavigationMapper;		
	@Autowired
	private RedisOperation redisOperation;	

	@Transactional(rollbackFor=Exception.class)
	@Override
	public String setToolbarAvaliable(HyCloseSet bean,String requestSerial) {
		//记录用户关闭设置，存在则更新
		HyCloseSet closeSet = hyCloseSetMapper.selectByMemberId(bean);
		if(closeSet == null){
			//tsc用户关闭toolbar设置入库操作业务日志记录开始
			BusinessLogger.info(requestSerial+"|start insert HY_CLOSESET");
			hyCloseSetMapper.insert(bean);
			//tsc用户关闭toolbar设置入库操作业务日志记录结束
			BusinessLogger.info(requestSerial+"|end insert HY_CLOSESET");
		}
		else{
			closeSet.setMobileno(bean.getMobileno());
			closeSet.setClosetype(bean.getClosetype());
			closeSet.setBegindate(bean.getBegindate());
			closeSet.setEnddate(bean.getEnddate());
			closeSet.setCountryno(bean.getCountryno());
			closeSet.setToperatorid(bean.getToperatorid());
			closeSet.setModifyman(bean.getModifyman());
			closeSet.setModifydate(bean.getModifydate());
			//tsc用户关闭toolbar设置修改操作业务日志记录开始
			BusinessLogger.info(requestSerial+"|start update HY_CLOSESET");
			hyCloseSetMapper.updateByPrimaryKeySelective(closeSet);
			//tsc用户关闭toolbar设置修改操作业务日志记录结束
			BusinessLogger.info(requestSerial+"|end update HY_CLOSESET");
		}
		//记录关闭设置日志表
		RzCloseSetLog setLog = new RzCloseSetLog();
		setLog.setClosesetlogid(StringUtil.formatUUID(false));
		setLog.setMemberid(bean.getMemberid());
		setLog.setMobileno(bean.getMobileno());
		setLog.setClosetype(bean.getClosetype());
		setLog.setCountryno(bean.getCountryno());
		setLog.setToperatorid(bean.getToperatorid());
		setLog.setSetdate(new Date());
		setLog.setDelflag("1");
		setLog.setModifyman(bean.getModifyman());
		setLog.setModifydate(new Date());
		//tsc用户关闭toolbar设置日志入库操作业务日志记录开始
		BusinessLogger.info(requestSerial+"|start insert RZ_CLOSESETLOG");
		rzCloseSetLogMapper.insert(setLog);
		//tsc用户关闭toolbar设置日志入库操作业务日志记录结束
		BusinessLogger.info(requestSerial+"|end insert RZ_CLOSESETLOG");
		
		return Constant.SUCCEED;
	}
	
	/**
	 * 获取默认设置
	 */
	@Override
	public String getDefaultSetting(HyCloseSet bean) {
		//记录用户关闭设置，存在则更新
		HyCloseSet closeSet = hyCloseSetMapper.selectByMemberId(bean);
		JSONObject jsonObject = (JSONObject) JSONObject.toJSON(closeSet);
		if(null == jsonObject||jsonObject.isEmpty()){
			return "-1";
		}else{
			return jsonObject.toString();
		}
	}

	@Override
	public List<String> selectByMemberIdAndDate(HyFlowAlert alert) {
		return hyFlowAlertMapper.selectByMemberIdAndDate(alert);
	}

	@Override
	public int insertSelective(HyFlowAlert alert) {
		return hyFlowAlertMapper.insertSelective(alert);
	}

	@Override
	public HyAlertThreshold queryAlertThreshold(HyAlertThreshold threshold) {
		return hyAlertThresholdMapper.selectByMemberId(threshold);
	}

	@Override
	public TcFlowPackage queryFlowPackageByFGNo(TcFlowPackage record) {
		return tcFlowPackageMapper.selectByFGNo(record);
	}

	@Override
	public TcPackageType queryPackageTypeByPrimaryKey(String packageTypeid) {
		return tcPackageTypeMapper.selectByPrimaryKey(packageTypeid);
	}

	@Override
	public SysTelecomOperatorAndHyMember queryMemberSys(String memberid) {
		if(redisOperation.exists(Constant.CACHE_TELECOMOPERATOR_MEMBER + memberid)){
			BusinessLogger.info("query SysTelecomOperatorAndHyMember from cache");
			String jsonRet = redisOperation.get(Constant.CACHE_TELECOMOPERATOR_MEMBER + memberid);
			return JSONObject.toJavaObject(JSONObject.parseObject(jsonRet), SysTelecomOperatorAndHyMember.class);
		}
		else{			
			BusinessLogger.info("query SysTelecomOperatorAndHyMember from db");
			SysTelecomOperatorAndHyMember sys = sysTelecomOperatorMapper.selectByMemberId(memberid);
			if(sys != null){
				redisOperation.add(Constant.CACHE_TELECOMOPERATOR_MEMBER + memberid, JSONObject.toJSONString(sys));
				redisOperation.expire(Constant.CACHE_TELECOMOPERATOR_MEMBER + memberid, Long.parseLong(ParamPropertiesUtil.getCacheTimeOut()), TimeUnit.SECONDS);
			}
			return sys;
		}
	}

	@Override
	public int saveFeedback(HyFeedback record) {
		return hyFeedbackMapper.insertSelective(record);
	}
	
	
	@Override
	public List<HyGameNavigation> queryGameList(HyGameNavigation record) {
		return hyGameNavigationMapper.selectBySelective(record);
	}
	
	public HyGameNavigation queryGameIcon(HyGameNavigation record){
		return hyGameNavigationMapper.selectIconByPrimaryKey(record);
	}
	
	@Override
	public HyWebsiteNavigation queryWebsiteIcon(HyWebsiteNavigation record) {
		return hyWebsiteNavigationMapper.selectIconByPrimaryKey(record);
	}
	
	
	@Override
	public List<HyWebsiteNavigation> queryWebsiteList(HyWebsiteNavigation record) {
		return hyWebsiteNavigationMapper.selectBySelective(record);
	}

}
