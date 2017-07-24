package com.flash.toolbar.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.flash.toolbar.common.util.*;
import com.flash.toolbar.mapper.*;
import com.flash.toolbar.model.*;
import com.flash.toolbar.service.MemberService;
import com.flash.toolbar.service.ToolbarPortalManagerService;
import com.flash.toolbar.token.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * toolbar入口界面管理service实现
 * @author ocean
 *
 */
@Transactional
@Service(value="toolbarPortalManagerService")
public class ToolbarPortalManagerServiceImpl implements ToolbarPortalManagerService{

	/**
	 * 操作运营商登记表
	 */
	@Autowired
	private SysTelecomOperatorMapper sysTelecomOperatorMapper;
	
	/**
	 * 操作号段运营商对应表
	 */
	@Autowired
	private SysOperatorSegMapper sysOperatorSegMapper;
	
	/**
	 * 操作会员表
	 */
	@Autowired
	private HyMemberMapper hyMemberMapper;
	
	/**
	 * 操作白名单表
	 */
	@Autowired
	private HyWhiteListMapper hyWhiteListMapper;
	
	/**
	 * 操作白名单号段表
	 */
	@Autowired
	private HyWhiteSectionMapper hyWhiteSectionMapper;
	
	/**
	 * 操作黑名单表
	 */
	@Autowired
	private HyBlackListMapper hyBlackListMapper;
	
	/**
	 * 操作黑名单号段表
	 */
	@Autowired
	private HyBlackSectionMapper hyBlackSectionMapper;
	
	/**
	 * 操作用户关闭设置表
	 */
	@Autowired
	private HyCloseSetMapper hyCloseSetMapper;
	
	/**
	 * 操作用户流量阀值提醒设置表
	 */
	@Autowired
	private HyAlertThresholdMapper hyAlertThresholdMapper;
	
	@Autowired
	private HyBlacklistDeviceMapper hyBlacklistDeviceMapper;
	
	/**
	 * 
	 */
	@Autowired
    IdGeneratorMapper idGeneratorMapper;
	
	@Resource
	private MemberService memberService;
	
	@Autowired
	private SysMainInterfaceIconConfigMapper configMapper;
	
	@Autowired
	private HyFlowExpandAlertMapper HyFlowExpandAlertMapper;
	
	@Autowired
	private HyActivityExpandAlertMapper hyActivityExpandAlertMapper;
	
	@Autowired
	private SysRemindConfigMapper sysRemindConfigMapper;
	
	/**
	 * "0"表示展示toolbar
	 */
	private static final String open = "0";
	
	/**
	 * "1"表示不展示toolbar
	 */
	private static final String close = "1";
	
	/**
	 * 会员是否有效，0无效，1有效
	 */
	private static final String memberDelFlag = "1";
	
	/**
	 * 修改人
	 */
	private static final String modifyman = "system";
	
	/**
	 * 首次流量阀值
	 */
	private static final int firstflowvalue = 70;
	
	/**
	 * 二次流量阀值
	 */
	private static final int secondflowvalue = 10;
	
	/**
	 * 用户流量提醒阀值设置开关状态（0打开（默认打开）、1关闭）
	 */
	private static final String alertThresholdStatus = "0";
	
	@Resource
	private TokenManager tokenManager;
	
	/**
	 * 判断用户是否展示toolbar，并初始化用户
	 */
	public String isToolbarAvaliable(String businessContent) throws Exception{
		JSONObject requestJSON = JSONObject.parseObject(businessContent);
		String mobileNo = requestJSON.getString("mobileNo");
		String serviceType = requestJSON.getString("serviceType");
		String requestSerial = requestJSON.getString("requestSerial");
		String userAgent = requestJSON.getString("userAgent");
		boolean isAllProcess = requestJSON.getBoolean("isAllProcess")!=null?requestJSON.getBoolean("isAllProcess"):true;
		String retMsg = "";
		String timeZone = "+8";
		
		if(StringUtil.isNotNullOrEmpty(mobileNo)){
			if(!mobileNo.startsWith("6")){//6代表马来西亚，后期需要改成动态读取
				mobileNo = "6"+mobileNo;
			}
		}
		
		SysTelecomOperatorAndHyMember sysTelecomOperatorAndHyMember = getMemberSysTelecomOperatorInfo(mobileNo);
		//----获取时区begin-------
		if(null != sysTelecomOperatorAndHyMember){
			timeZone = sysTelecomOperatorAndHyMember.getTimezone(); 
		}

//		ExceptionLogger.error("JSONObject wangxiaoran test : "+ requestJSON.toJSONString());
//		ExceptionLogger.error("isAllProcess wangxiaoran test : "+ isAllProcess);

		//判断是否要展示toolbar
		String openToolbar =  open;	
		
		boolean isAtTheWhiteList = true;
		if(isAllProcess){
			if(StringUtil.isNotNullOrEmpty(userAgent)){
				List<String> deviceNames = hyBlacklistDeviceMapper.selectDeviceNames(sysTelecomOperatorAndHyMember);
				if(deviceNames!=null && deviceNames.size()>0){
					for (String str : deviceNames) {
						if(userAgent.indexOf(str.trim())!=-1){
							JSONObject json = new JSONObject();
							json.put("openToolbar", close);
							json.put("retCode", Constant.RETURN_FAILURE);
							json.put("retMsg", "The browser or device is prohibited!");
							json.put("isFirstUseToolbar", false);
							json.put("serviceType", serviceType);
							return ResponseUtil.unifyFailReturn(json.toJSONString());						
						}
					}
				}
			}
			
			boolean atTheBlackList = atTheBlackList(mobileNo);
			//用户不在黑名单中，则判断是否在白名单中，并做相应的处理
			if(!atTheBlackList){
				boolean atTheWhiteList = atTheWhiteList(mobileNo);
				if(!atTheWhiteList){
					retMsg = "allProcess-mobileNo:"+mobileNo+"is not in whiteList";
					isAtTheWhiteList = false;
					openToolbar = close;
				}
			}else{
				retMsg = "allProcess-mobileNo:"+mobileNo+"is in blackList";
				openToolbar = close;
			}			
		}
//		ExceptionLogger.error("isAtTheWhiteList wangxiaoran test : "+ isAtTheWhiteList);

		if(isAtTheWhiteList){
			Map<String,String> map = new HashMap<String,String>();
			map.put("mobileNo", mobileNo);
			map.put("currentTime", DateUtil.getFormatDate(DateUtil.getTimeZoneDate(new Date(),timeZone,"yyyy-MM-dd HH:mm:ss")));//--timeZone--

//			ExceptionLogger.error("mobileNo wangxiaoran test : "+ mobileNo);
//			ExceptionLogger.error("currentTime wangxiaoran test  : "+ DateUtil.getFormatDate(DateUtil.getTimeZoneDate(new Date(),timeZone,"yyyy-MM-dd HH:mm:ss")) );

			int listCount = hyCloseSetMapper.countAtCloseTime(map);

//			ExceptionLogger.error("listCount wangxiaoran test : "+ listCount);

				if(listCount > 0){
					retMsg = "mobileNo:"+mobileNo+"is already closed";
					openToolbar = close;
				}
		}


		
		//判断是否需要初始化会员信息
		boolean needInitMemberInfo = false;
		if(open.equals(openToolbar)){
			needInitMemberInfo = sysTelecomOperatorAndHyMember!=null?false:true;
		}
		
		//成功初始化会员信息
		boolean successInit = false;
		//初始化会员信息
		if(needInitMemberInfo ){
			String brand = "";
			String profileRet = queryPrePosIndicator(mobileNo,requestSerial);
			if(profileRet != null){
				JSONObject jsonRet = JSONObject.parseObject(profileRet);
				JSONObject result = jsonRet.getJSONObject("outputSDResp");
				String postPrepaid = result.getString("serviceType");
				brand = StringUtil.getStr(result.getString("plan")).trim();
				if(!StringUtil.isNotNullOrEmpty(brand) || !(brand.toLowerCase().contains("turbo") || brand.toLowerCase().contains("magic")
						|| "IOX Plan".equalsIgnoreCase(brand) || "Xpax24 Plan".equalsIgnoreCase(brand) || "XPAX Plan".equalsIgnoreCase(brand) || "Celcom Frenz Plan".equalsIgnoreCase(brand) || "Xmid Plan".equalsIgnoreCase(brand)
						|| "Xmax Plan".equalsIgnoreCase(brand) || "Xlite Plan".equalsIgnoreCase(brand) || "X2 Plan".equalsIgnoreCase(brand) || "UOX Plan".equalsIgnoreCase(brand) || "Traveller SIM Plan".equalsIgnoreCase(brand)
						|| "Sukses Int Plan".equalsIgnoreCase(brand) || "Sukses Plan".equalsIgnoreCase(brand) || "SOX Plan".equalsIgnoreCase(brand) || "New UOX Plan".equalsIgnoreCase(brand) || "New SOX Plan".equalsIgnoreCase(brand)
						|| "IOX Disposable Plan".equalsIgnoreCase(brand) || "Frenz Plan".equalsIgnoreCase(brand) || "Data Only".equalsIgnoreCase(brand) || "CEP".equalsIgnoreCase(brand) || "Blue Khas Plan".equalsIgnoreCase(brand)
						|| "Blue Plan".equalsIgnoreCase(brand) || "1Malaysia Plan".equalsIgnoreCase(brand) || "New Xpax".equalsIgnoreCase(brand)
						)){
					retMsg = "interface-brand is null or brand is not turo and brand is not magic";
					openToolbar = close;
				}
				if(!StringUtil.isNotNullOrEmpty(postPrepaid)){
					retMsg = "postPrepaidInfo is null";
					openToolbar = close;
				}else if(!Constant.PRE_POS_INDICATOR.equals(postPrepaid) && !Constant.POST_POS_INDICATOR.equals(postPrepaid)){
					retMsg = "postPrepaidInfo is Prepaid or postpaid user";
					serviceType = postPrepaid;
					openToolbar = close;
				}else{
					serviceType = postPrepaid;
					openToolbar = open;
				}
				if(brand.toLowerCase().contains("turbo")){
					brand="turbo";
				}
				else if(brand.toLowerCase().contains("magic")){
					brand="magic";
				}
				else if("IOX Plan".equalsIgnoreCase(brand)){
					brand="iox";
				}
				else if("Xpax24 Plan".equalsIgnoreCase(brand)){
					brand="xpax24";
				}
				else if("New UOX Plan".equalsIgnoreCase(brand)){
					brand="new uox";
				}
				else if("New SOX Plan".equalsIgnoreCase(brand)){
					brand="new sox";
				}					
				else if("XPAX Plan".equalsIgnoreCase(brand)){
					brand="xpax";
				}
				else if("Celcom Frenz Plan".equalsIgnoreCase(brand)){
					brand="celcom international";
				}
				else if("Xmid Plan".equalsIgnoreCase(brand)){
					brand="xmid";
				}
				else if("Xmax Plan".equalsIgnoreCase(brand)){
					brand="xmax";
				}
				else if("Xlite Plan".equalsIgnoreCase(brand)){
					brand="xlite";
				}
				else if("X2 Plan".equalsIgnoreCase(brand)){
					brand="x2";
				}
				else if("UOX Plan".equalsIgnoreCase(brand)){
					brand="uox";
				}
				else if("Traveller SIM Plan".equalsIgnoreCase(brand)){
					brand="travelers pack";
				}
				else if("Sukses Int Plan".equalsIgnoreCase(brand)){
					brand="suksesin";
				}
				else if("Sukses Plan".equalsIgnoreCase(brand)){
					brand="sukses";
				}
				else if("SOX Plan".equalsIgnoreCase(brand)){
					brand="sox";
				}
				else if("IOX Disposable Plan".equalsIgnoreCase(brand)){
					brand="iox data only";
				}
				else if("Frenz Plan".equalsIgnoreCase(brand)){
					brand="frenz";
				}
				else if("Data Only".equalsIgnoreCase(brand)){
					brand="dataonly";
				}
				else if("CEP".equalsIgnoreCase(brand)){
					brand="cep";
				}
				else if("Blue Khas Plan".equalsIgnoreCase(brand)){
					brand="bluekhas";
				}
				else if("Blue Plan".equalsIgnoreCase(brand)){
					brand="blue";
				}
				else if("1Malaysia Plan".equalsIgnoreCase(brand)){
					brand="1malaysia";
				}
				else if("New Xpax".equalsIgnoreCase(brand)){
					brand="new xpax";
				}
				else{
					brand="other";
				}
			}else{
				retMsg="Failed to call interface profileretrieve";
				openToolbar = close;
			}			
			
			if(open.equals(openToolbar)){
				successInit = initMemberInfo(mobileNo,serviceType,requestSerial,brand);
//				if(!(StringUtil.isNotNullOrEmpty(serviceType) && Constant.PRE_POS_INDICATOR.equals(serviceType))){
//					JSONObject json = new JSONObject();
//					json.put("openToolbar", close);
//					json.put("retCode", Constant.RETURN_FAILURE);
//					json.put("retMsg", "the current user is not a prepaid user!");
//					json.put("isFirstUseToolbar", false);
//					json.put("serviceType", serviceType);
//					return ResponseUtil.unifyFailReturn(json.toJSONString());			
//				}			
				if(!successInit){
					JSONObject json = new JSONObject();
					json.put("openToolbar", close);
					json.put("retCode", Constant.RETURN_EMPTY);
					json.put("retMsg", "init MemberInfo failed!");
					json.put("isFirstUseToolbar", false);
					json.put("serviceType", serviceType);
					return ResponseUtil.unifyFailReturn(json.toJSONString());
				}				
			}

		}
		String serviceTypeT = serviceType;
		if(open.equals(openToolbar)){
		if (!StringUtil.isNotNullOrEmpty(serviceType) || (1==1)) {
			if(needInitMemberInfo){
				sysTelecomOperatorAndHyMember = getMemberSysTelecomOperatorInfo(mobileNo);
				
			}
			
			String preposindicator = sysTelecomOperatorAndHyMember.getPreposindicator();
			String memberId = sysTelecomOperatorAndHyMember.getMemberid();
			String sysBrand = sysTelecomOperatorAndHyMember.getBrand();
			if(!StringUtil.isNotNullOrEmpty(preposindicator) || !StringUtil.isNotNullOrEmpty(sysBrand)){
				String profileRet = queryPrePosIndicator(mobileNo,requestSerial);
				if(profileRet != null){
					JSONObject jsonRet = JSONObject.parseObject(profileRet);
					JSONObject result = jsonRet.getJSONObject("outputSDResp");
					String postPrepaid = result.getString("serviceType");
					String brand = StringUtil.getStr(result.getString("plan")).trim();
					if(!StringUtil.isNotNullOrEmpty(brand) || !(brand.toLowerCase().contains("turbo") || brand.toLowerCase().contains("magic")
							|| "IOX Plan".equalsIgnoreCase(brand) || "Xpax24 Plan".equalsIgnoreCase(brand) || "XPAX Plan".equalsIgnoreCase(brand) || "Celcom Frenz Plan".equalsIgnoreCase(brand) || "Xmid Plan".equalsIgnoreCase(brand)
							|| "Xmax Plan".equalsIgnoreCase(brand) || "Xlite Plan".equalsIgnoreCase(brand) || "X2 Plan".equalsIgnoreCase(brand) || "UOX Plan".equalsIgnoreCase(brand) || "Traveller SIM Plan".equalsIgnoreCase(brand)
							|| "Sukses Int Plan".equalsIgnoreCase(brand) || "Sukses Plan".equalsIgnoreCase(brand) || "SOX Plan".equalsIgnoreCase(brand) || "New UOX Plan".equalsIgnoreCase(brand) || "New SOX Plan".equalsIgnoreCase(brand)
							|| "IOX Disposable Plan".equalsIgnoreCase(brand) || "Frenz Plan".equalsIgnoreCase(brand) || "Data Only".equalsIgnoreCase(brand) || "CEP".equalsIgnoreCase(brand) || "Blue Khas Plan".equalsIgnoreCase(brand)
							|| "Blue Plan".equalsIgnoreCase(brand) || "1Malaysia Plan".equalsIgnoreCase(brand) || "New Xpax".equalsIgnoreCase(brand)
							)){
						retMsg = "interface-brand is null or brand is not turo and brand is not magic";
						openToolbar = close;
					}
					if(!StringUtil.isNotNullOrEmpty(postPrepaid)){
						retMsg = "postPrepaidInfo is null";
						serviceTypeT = serviceType;
						openToolbar = close;
					}else if(!Constant.PRE_POS_INDICATOR.equals(postPrepaid) && !Constant.POST_POS_INDICATOR.equals(postPrepaid)){
						retMsg = "postPrepaidInfo is Prepaid or postpaid user";
						serviceTypeT = postPrepaid;
						openToolbar = close;
					}else{
						serviceTypeT = postPrepaid;
						openToolbar = open;
					}
					HyMember hyMemberT = new HyMember();
					hyMemberT.setMemberid(memberId);
					hyMemberT.setPreposindicator(StringUtil.getStr(postPrepaid));
					if(brand.toLowerCase().contains("turbo")){
						hyMemberT.setBrand("turbo");
					}
					else if(brand.toLowerCase().contains("magic")){
						hyMemberT.setBrand("magic");
					}
					else if("IOX Plan".equalsIgnoreCase(brand)){
						hyMemberT.setBrand("iox");
					}
					else if("Xpax24 Plan".equalsIgnoreCase(brand)){
						hyMemberT.setBrand("xpax24");
					}
					else if("New UOX Plan".equalsIgnoreCase(brand)){
						hyMemberT.setBrand("new uox");
					}
					else if("New SOX Plan".equalsIgnoreCase(brand)){
						hyMemberT.setBrand("new sox");
					}					
					else if("XPAX Plan".equalsIgnoreCase(brand)){
						hyMemberT.setBrand("xpax");
					}
					else if("Celcom Frenz Plan".equalsIgnoreCase(brand)){
						hyMemberT.setBrand("celcom international");
					}
					else if("Xmid Plan".equalsIgnoreCase(brand)){
						hyMemberT.setBrand("xmid");
					}
					else if("Xmax Plan".equalsIgnoreCase(brand)){
						hyMemberT.setBrand("xmax");
					}
					else if("Xlite Plan".equalsIgnoreCase(brand)){
						hyMemberT.setBrand("xlite");
					}
					else if("X2 Plan".equalsIgnoreCase(brand)){
						hyMemberT.setBrand("x2");
					}
					else if("UOX Plan".equalsIgnoreCase(brand)){
						hyMemberT.setBrand("uox");
					}
					else if("Traveller SIM Plan".equalsIgnoreCase(brand)){
						hyMemberT.setBrand("travelers pack");
					}
					else if("Sukses Int Plan".equalsIgnoreCase(brand)){
						hyMemberT.setBrand("suksesin");
					}
					else if("Sukses Plan".equalsIgnoreCase(brand)){
						hyMemberT.setBrand("sukses");
					}
					else if("SOX Plan".equalsIgnoreCase(brand)){
						hyMemberT.setBrand("sox");
					}
					else if("IOX Disposable Plan".equalsIgnoreCase(brand)){
						hyMemberT.setBrand("iox data only");
					}
					else if("Frenz Plan".equalsIgnoreCase(brand)){
						hyMemberT.setBrand("frenz");
					}
					else if("Data Only".equalsIgnoreCase(brand)){
						hyMemberT.setBrand("dataonly");
					}
					else if("CEP".equalsIgnoreCase(brand)){
						hyMemberT.setBrand("cep");
					}
					else if("Blue Khas Plan".equalsIgnoreCase(brand)){
						hyMemberT.setBrand("bluekhas");
					}
					else if("Blue Plan".equalsIgnoreCase(brand)){
						hyMemberT.setBrand("blue");
					}
					else if("1Malaysia Plan".equalsIgnoreCase(brand)){
						hyMemberT.setBrand("1malaysia");
					}
					else if("New Xpax".equalsIgnoreCase(brand)){
						hyMemberT.setBrand("new xpax");
					}
					else{
						hyMemberT.setBrand("other");
					}
					hyMemberMapper.updateByPrimaryKeySelective(hyMemberT);
				}else{
					retMsg="Failed to call interface profileretrieve";
					openToolbar = close;
				}
			}else if(!Constant.PRE_POS_INDICATOR.equals(preposindicator) && !Constant.POST_POS_INDICATOR.equals(preposindicator)){
				retMsg = "preposindicator is Prepaid or postpaid user";
				serviceTypeT = preposindicator;
				openToolbar = close;
			}else if("other".equals(sysBrand.toLowerCase())){
				retMsg = "brand is other";
				serviceTypeT = preposindicator;
				openToolbar = close;
			}else{
				serviceTypeT = preposindicator;
				openToolbar = open;
			}
		}
	}
	boolean isAlertOpen = false;
	String flowThreshold = "";
	if(open.equals(openToolbar)){
//		String alertTime = "";
		long alertDateTime = 0;
		HyFlowExpandAlert record = new HyFlowExpandAlert();
		record.setCountryno(sysTelecomOperatorAndHyMember.getCountryno());
		record.setToperatorid(sysTelecomOperatorAndHyMember.getToperatorid());
		record.setMemberid(sysTelecomOperatorAndHyMember.getMemberid());
//		record.setAlerttime(new Date());
		
		List<HyFlowExpandAlert> HyFlowExpandAlertList = getHyFlowExpandAlertInfo(record);
		if(HyFlowExpandAlertList!=null && HyFlowExpandAlertList.size()==1 &&  HyFlowExpandAlertList.get(0).getAlerttime()!=null){
//			alertTime = DateUtil.getFormatDate(HyFlowExpandAlertList.get(0).getAlerttime(), "yyyy-MM-dd");
			alertDateTime = (DateUtil.getTimeZoneDate(new Date(), timeZone, "yyyy-MM-dd HH:mm:ss").getTime()-HyFlowExpandAlertList.get(0).getAlerttime().getTime())/(1000*60*60);
		}else{
			record.setAlerttime(DateUtil.getTimeZoneDate(new Date(), timeZone, "yyyy-MM-dd HH:mm:ss"));
			record.setId(RuleUtil.generateUUID());
			record.setMobileno(mobileNo);
			HyFlowExpandAlertMapper.insert(record);
			alertDateTime = 0;			
		}
		
//		if("".equals(alertTime) || alertTime.compareTo(nowDate)!=0){
//			record.setId(RuleUtil.generateUUID());
//			record.setMobileno(mobileNo);
//			sysFlowExpandAlertMapper.insert(record);
//			alertDateTime = (new Date().getTime()-record.getAlerttime().getTime())/(1000*60*60);
//		}
		List<SysRemindConfig> list = null;
		SysRemindConfig sysRemindConfig = new SysRemindConfig();
		sysRemindConfig.setCountryno(sysTelecomOperatorAndHyMember.getCountryno());
		sysRemindConfig.setToperatorid(sysTelecomOperatorAndHyMember.getToperatorid());
		sysRemindConfig.setIsopen(Constant.REMIND_VAILD);
		sysRemindConfig.setPreposindicator(serviceTypeT);
		sysRemindConfig.setBrand(sysTelecomOperatorAndHyMember.getBrand());
		if(serviceTypeT!="" && sysTelecomOperatorAndHyMember.getBrand()!=""){
			list = getSysRemindConfig(sysRemindConfig);
		}
		if(list!=null && list.size()>0 ){
			String remindVal = "";
			for (SysRemindConfig sysRemindConfigTemp : list) {
				if("1".equals(sysRemindConfigTemp.getType())){
					flowThreshold = sysRemindConfigTemp.getRemindval();
				}else if("2".equals(sysRemindConfigTemp.getType())){
					remindVal = sysRemindConfigTemp.getRemindval();
				}
			}
			if(StringUtil.isNotNullOrEmpty(remindVal) && alertDateTime>Long.valueOf(remindVal)){
				isAlertOpen = true;
				HyFlowExpandAlert newRecord = new HyFlowExpandAlert();
				newRecord.setId(RuleUtil.generateUUID());
				newRecord.setAlerttime(DateUtil.getTimeZoneDate(new Date(), timeZone, "yyyy-MM-dd HH:mm:ss"));
				HyFlowExpandAlertMapper.updateByPrimaryKeySelective(newRecord);
			}
		}
	}
		//查询会员信息，联同是否展示toolbar信息一起返回到前台tbar
		String memberInfo = null;
		if(open.equals(openToolbar)){
			memberInfo = searchMemberInfo(mobileNo,openToolbar,needInitMemberInfo,serviceTypeT,timeZone,sysTelecomOperatorAndHyMember,flowThreshold,isAlertOpen);
			return ResponseUtil.unifySuccessReturn(memberInfo);
		}else{
			JSONObject json = new JSONObject();
			json.put("openToolbar", close);
			json.put("retCode", Constant.RETURN_EMPTY);
			json.put("retMsg", "".equals(retMsg)?"not need open toolbar!":retMsg);
			json.put("isFirstUseToolbar", false);
			json.put("serviceType", serviceType);
			return ResponseUtil.unifyFailReturn(json.toJSONString());
		}
		
	}
	
	
	
	/**
	 * 从数据库中捞取用户信息放到session中
	 */
	public String queryToolbarSession(String businessContent) throws Exception{
		JSONObject requestJSON = JSONObject.parseObject(businessContent);
		String memberId = requestJSON.getString("memberId");
		String requestSerial = requestJSON.getString("requestSerial");
		//查询会员信息，联同是否展示toolbar信息一起返回到前台tbar
		String memberInfo = searchMemberInfo(memberId);
		return ResponseUtil.unifySuccessReturn(memberInfo);
		
	}
	
	
	private String queryPrePosIndicator(String mobileNo,String requestSerial){
		//tsc判断是否是预付费用户业务日志记录开始
		BusinessLogger.LoggerInfo(requestSerial, "tsc", "queryPrePosIndicator", true, null, String.valueOf(new Date().getTime()));
		String postPrepaid = null;
		JSONObject jsonTemp = new JSONObject();
		jsonTemp.put("mobileNo", mobileNo);
		jsonTemp.put("requestSerial", requestSerial);
		String paramTemp = jsonTemp.toJSONString();
//		TraceLogger
//		.info("tsc-ToolbarPortalManagerServiceImpl-queryPrePosIndicator-0 query user Prepaid or Postpaid");
		
		// 查询用户是否是预付费用户
		String profileRet = queryProfileRetrieve(paramTemp);
//		String profileRet = queryCustomerRetrieve(paramTemp);
		
//		if (profileRet != null) {
//			JSONObject jsonRet = JSONObject.parseObject(profileRet);
//			JSONObject result = jsonRet.getJSONObject("outputCPResp");
//			JSONObject status = result
//					.getJSONArray("headerCustomerProfile").getJSONObject(0);
//			if (status.getString("status").toUpperCase().equals("OK")) {
//				JSONObject listOfServices = result
//						.getJSONObject("listOfServices");
//				JSONObject service = listOfServices
//						.getJSONArray("services").getJSONObject(0);
//				postPrepaid = service.getString("pre_Pos_Indicator");
//			}
//		}
//		try{
//			JSONObject jsonRet = JSONObject.parseObject(profileRet);
//			JSONObject result = jsonRet.getJSONObject("outputSDResp");
//			postPrepaid = result.getString("serviceType");
//		}catch(Exception e){
//			ExceptionLogger.LoggerInfo(requestSerial, "", "query postPrepaid failed!", e);
//		}
		
//		TraceLogger.info("tsc-ToolbarPortalManagerServiceImpl-queryPrePosIndicator-0 query user Prepaid or Postpaid result:"
//				+ postPrepaid);
		//tsc判断是否是预付费用户业务日志记录结束
		BusinessLogger.LoggerInfo(requestSerial, "tsc", "queryPrePosIndicator", false, postPrepaid, String.valueOf(new Date().getTime()));
		return profileRet;
	}
	
	/**
	 * 运营商接口
	 * 查询用户基本信息（包含预付费和后付费用户）
	 * @param params
	 * @return
	 */
//	private String queryCustomerRetrieve(String params) {
////		TraceLogger.debug("tsc-main-ToolbarPortalManagerServiceImpl:queryCustomerRetrieve 3qii input params:" + params);
//		JSONObject json = (JSONObject) JSONObject.parse(params);
//		if(!json.isEmpty()){
//			String mobileNo = json.getString("mobileNo");
//			String memberId = json.getString("memberId");
//			String requestSerial = json.getString("requestSerial");
//			//tsc查询用户基本信息业务日志记录开始
//			BusinessLogger.LoggerInfo(requestSerial, "tsc", "queryCustomerRetrieve", true, null, String.valueOf(new Date().getTime()));
//			if(!StringUtil.isNotNullOrEmpty(memberId) && StringUtil.isNotNullOrEmpty(mobileNo)){
//				HyMember hyMember  = hyMemberMapper.selectByMobileNo(mobileNo);
//				if(hyMember!=null){
//					memberId = hyMember.getMemberid();
//				}
//			}
//			//查询手机号所属运营商信息
//			SysTelecomOperatorAndHyMember memsys = memberService.queryMemberSys(memberId);
//			JSONObject logJson = null;
//			if(memsys != null){
//				Configuration cfg = new Configuration(null, "environment.properties");
//				String url = cfg.getValue("customerretrieve.url");
//				StringBuilder sb = new StringBuilder();
//				sb.append("?userAddress=").append(memsys.getMobileno());
//				//调用运营商接口
//				try {
//					String req = url+sb.toString();
////					TraceLogger.debug("tsc-main-ToolbarPortalManagerServiceImpl:queryCustomerRetrieve celecom input params:" + req);
//					//tsc调用接口跟踪日志记录开始
//					TraceLogger.debugInterface("tsc", "celcom", "customerretrieve", Constant.ACTION_IN, sb.toString());
//					JSONObject jsonHttp = HttpHelper.httpGet(req);
//					//tsc调用接口跟踪日志记录结束
//					TraceLogger.debugInterface("celcom", "tsc", "customerretrieve", Constant.ACTION_OUT, JSONObject.toJSONString(logJson));
//					if(jsonHttp != null){
//						logJson = new JSONObject();
//						JSONObject tempJson = new JSONObject();
//						JSONObject result = jsonHttp.getJSONObject("outputCPResp");
//						JSONArray array = result.getJSONArray("headerCustomerProfile");
//						tempJson.put("headerCustomerProfile", array);
//						tempJson.put("customerRowId", result.getString("customerRowId")!=null?result.getString("customerRowId"):"");
//						tempJson.put("masterAccountNumber", result.getString("masterAccountNumber")!=null?result.getString("masterAccountNumber"):"");
//						tempJson.put("masterAccountNumber", result.getString("masterAccountNumber")!=null?result.getString("masterAccountNumber"):"");
//						tempJson.put("customerID", result.getString("customerID")!=null?result.getString("customerID"):"");
//						tempJson.put("customerIDType", result.getString("customerIDType")!=null?result.getString("customerIDType"):"");
//						JSONObject listOfServices = result.getJSONObject("listOfServices");
//						tempJson.put("listOfServices",listOfServices);
//						logJson.put("outputCPResp", tempJson);
////						TraceLogger.debug("tsc-main-ToolbarPortalManagerServiceImpl:queryCustomerRetrieve celecom output params:" + logJson.toJSONString());
//						return jsonHttp.toJSONString();
//					}
//				} catch (Exception e) {
//					ExceptionLogger.debug("tsc-main-ToolbarPortalManagerServiceImpl:queryCustomerRetrieve",e);
//					e.printStackTrace();
//				}
//			}
//			else{
//				BusinessLogger.info("tsc-main-ToolbarPortalManagerServiceImpl:queryCustomerRetrieve 3qii output: memberid does not exist:" + json.getString("memberId"));
//			}
//			//tsc查询用户基本信息业务日志记录结束
//			BusinessLogger.LoggerInfo(requestSerial, "tsc", "queryCustomerRetrieve", false, JSONObject.toJSONString(logJson), String.valueOf(new Date().getTime()));
//		}
//		return null;
//	}
	
	public String queryProfileRetrieve(String params) {
		TraceLogger.debug("tsc-main-ToolbarPortalManagerServiceImpl:queryProfileRetrieve 3qii input params:" + params);
		
		JSONObject json = (JSONObject) JSONObject.parse(params);
		if(!json.isEmpty()){
			//查询手机号所属运营商信息
				Configuration cfg = new Configuration(null, "environment.properties");
				String url = cfg.getValue("profileretrieve.url");
//				StringBuilder sb = new StringBuilder();
//				sb.append("?businessEvent=").append("ProfileRetrieveQuery");
//				sb.append("&sourceSystem=").append("Siebel");
//				sb.append("&callerNumber=").append(memsys.getMobileno());
				//调用运营商接口
				try {
//					String req = url+sb.toString();
//					int iStart = Integer.parseInt(ParamPropertiesUtil.getiSegLenthB());
//					int iEnd = Integer.parseInt(ParamPropertiesUtil.getiSegLenthE());
//					String req = MessageFormat.format(url, new Object[]{memsys.getMobileno().substring(iStart,iEnd)});
					String req = MessageFormat.format(url, new Object[]{json.getString("mobileNo")});
					Map<String,String> map = new HashMap<String,String>();
					map.put("Accept", "application/json");
					map.put("Authorization", "Bearer "+tokenManager.queryToken());
					map.put("Host", ParamPropertiesUtil.getCelcomHost());
//					TraceLogger.debug("tsc-main-ToolbarPortalManagerServiceImpl:queryProfileRetrieve celecom input params:" + req);
					TraceLogger.debugInterface("tsc", "celcom", "ToolbarPortalManagerServiceImpl_queryProfileRetrieve"+json.getString("mobileNo"), Constant.ACTION_IN, req);
//					JSONObject jsonHttp = HttpHelper.httpGet(req);
					JSONObject jsonHttp = HttpHelper.httpGetWithHead(req,map);
					TraceLogger.debugInterface("celcom", "tsc", "ToolbarPortalManagerServiceImpl_queryProfileRetrieve"+json.getString("mobileNo"), Constant.ACTION_OUT, null==jsonHttp ? null:JSONObject.toJSONString(jsonHttp));
					if(jsonHttp != null){
//						TraceLogger.debug("tsc-main-ToolbarPortalManagerServiceImpl:queryProfileRetrieve celecom output params:" + jsonHttp.toJSONString());
						return jsonHttp.toJSONString();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
		return null;
	}
	
	/**
	 * 判断"用户关闭设置表"中用户有没有设置不展示toolbar时间段
	 * @param mobileNo 用户手机号
	 * @return
	 */
	private boolean hasCloseRecord(String mobileNo){
		boolean hasCloseRecord =  false;
		HyCloseSet hyCloseSet = hyCloseSetMapper.selectByMobileNo(mobileNo);
		if(null != hyCloseSet){
			hasCloseRecord = true;
		}
		return hasCloseRecord;
	}
	
	/**
	 * 判断是否在黑名单或者黑名单号段中
	 * @param mobileNo 用户手机号
	 * @return
	 */
	private boolean atTheBlackList(String mobileNo){
		boolean rtn = false;
		int listCount = hyBlackListMapper.countAllByMobileNo(mobileNo);
		if(listCount>0){
				rtn = true;
		}
		
		return rtn;
	}
	
	/**
	 * 判断是否在白名单或者白名单号码段中
	 * @param mobileNo 用户手机号
	 * @return
	 */
	private boolean atTheWhiteList(String mobileNo){
		boolean rtn = false;
		int listCount = hyWhiteListMapper.countAllByMobileNo(mobileNo);
		if(listCount>0){
				rtn = true;
		}
		
		return rtn;
	}
	
	/**
	 * 判断是否要初始化会员信息
	 * @param mobileNo 用户手机号
	 * @return
	 */
	private SysTelecomOperatorAndHyMember getMemberSysTelecomOperatorInfo(String mobileNo){
		return sysTelecomOperatorMapper.selectByMobileno(mobileNo);
	}
	
	/**
	 * 获取alert信息
	 * @param HyFlowExpandAlert 对象信息
	 * @return
	 */
	private List<HyFlowExpandAlert> getHyFlowExpandAlertInfo(HyFlowExpandAlert HyFlowExpandAlert){
		return HyFlowExpandAlertMapper.selectBySelective(HyFlowExpandAlert);
	}
	
	/**
	 * 获取活动alert信息
	 * @param hyActivityExpandAlert
	 * @return
	 */
	private List<HyActivityExpandAlert> getHyActivityExpandAlertInfo(HyActivityExpandAlert hyActivityExpandAlert){
		return hyActivityExpandAlertMapper.selectBySelective(hyActivityExpandAlert);
	}
	
	private List<SysRemindConfig> getSysRemindConfig(SysRemindConfig sysRemindConfig){
		return sysRemindConfigMapper.selectBySelective(sysRemindConfig);
	}
	
	/**
	 * 初始化用户信息
	 * @param mobileNo
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	private boolean initMemberInfo(String mobileNo,String serviceType,String requestSerial,String brand){
		boolean rtn = true;
		HyMember record = new HyMember();
		
		//String mobileSeq = mobileNo.substring(Integer.parseInt(ParamPropertiesUtil.getSegLenthB()),Integer.parseInt(ParamPropertiesUtil.getSegLenthE()));//???
		String mobileSeq = mobileNo;
		List<SysTelecomOperatorAndSysOperatorSeg> list = sysOperatorSegMapper.selectByMobileNoUnionTelecomOperator(mobileSeq);
		
		if(list==null || list.size()==0){
			return false;
		}
		
		String toperatorId = list.get(0).getToperatorid();
		
		String timeZone = list.get(0).getTimezone();
		
		Date addDate = DateUtil.getTimeZoneDate(new Date(),timeZone,"yyyy-MM-dd HH:mm:ss");//--timeZone--
		Date modifyDate = DateUtil.getTimeZoneDate(new Date(),timeZone,"yyyy-MM-dd HH:mm:ss");//--timeZone--
		
		String countryno = list.get(0).getCountryno();
		String langno = list.get(0).getLangno();
//		String memberno = createMemberno();
		final String uuid = RuleUtil.generateUUID();
		
		//初始化会员信息
		record.setMobileno(mobileNo);
		record.setMemberid(uuid);
		record.setAdddate(addDate);
		record.setCountryno(countryno);
		record.setDelflag(memberDelFlag);
		record.setLangno(langno);
		record.setMemberno("");
		record.setModifydate(modifyDate);
		record.setModifyman(modifyman);
		record.setToperatorid(toperatorId);
		record.setPreposindicator(serviceType);
		record.setFirstshow("0");
		record.setBrand(brand);
		//tsc会员入库操作业务日志记录开始
		BusinessLogger.info(requestSerial+"|start insert HY_MEMBER");
		hyMemberMapper.insertSelective(record);
		//tsc会员入库操作业务日志记录结束
		BusinessLogger.info(requestSerial+"|end insert HY_MEMBER");		
		
//		//初始化用户流量提醒阀值设置信息
//		HyAlertThreshold hyAlertThreshold = new HyAlertThreshold();
//		final String thresholdid = RuleUtil.generateUUID();
//		String memberId = record.getMemberid();
//		
//		hyAlertThreshold.setThresholdid(thresholdid);
//		hyAlertThreshold.setCountryno(countryno);
//		hyAlertThreshold.setFirstflowvalue(firstflowvalue);
//		hyAlertThreshold.setMemberid(memberId);
//		hyAlertThreshold.setMobileno(mobileNo);
//		hyAlertThreshold.setModifydate(modifyDate);
//		hyAlertThreshold.setModifyman(memberId);
//		hyAlertThreshold.setSecondflowvalue(secondflowvalue);
//		hyAlertThreshold.setSetdate(addDate);
//		hyAlertThreshold.setStatus(alertThresholdStatus);
//		hyAlertThreshold.setToperatorid(toperatorId);
//		//tsc流量提醒阈值入库操作业务日志记录开始
//		BusinessLogger.info(requestSerial+"|start insert HY_ALERTTHRESHOLD");
//		hyAlertThresholdMapper.insertSelective(hyAlertThreshold);
//		//tsc流量提醒阈值入库操作业务日志记录结束
//		BusinessLogger.info(requestSerial+"|end insert HY_ALERTTHRESHOLD");		
		return rtn;
	}
	
	/**
	 * 创建memberNo（yyyyMMdd+10位自增序号）
	 * @return
	 */
	private String createMemberno(){
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//		String prifx = sdf.format(new Date());
//        String returnNo = "";
//		String maxcount = getSequnceIdValue("membernoseq");
//		String orderno = getSerialNo(maxcount,10);
//		returnNo = prifx + orderno;
//		return returnNo;
		return hyMemberMapper.selectSequence();
	}
	
	private String getSerialNo(String sno, int length) {
		int zeronum = length - ((sno + "").length());
		String returnSno = "";
		for (int i = 0; i < zeronum; i++) {
			returnSno = returnSno + "0";
		}
		returnSno = returnSno + sno;
		return returnSno;
	}
	
	/**
	 * 获取业务主键序列化最大id
	 * @param busiTypeName
	 * @return
	 */
    public String getSequnceIdValue(String busiTypeName)
    {
        IdGenerator idGenerator = new IdGenerator();
        idGenerator.setFieldname(busiTypeName);
        idGenerator = idGeneratorMapper.getSequenceByFieldName(idGenerator);
        if (idGenerator == null)
        {// 此业务类型没有id序列，创建新的序列
        	idGenerator = new IdGenerator();
            idGenerator.setFieldname(busiTypeName);// 业务名
            idGenerator.setMaxid("1");
            idGeneratorMapper.insertSequenceByFieldName(idGenerator);
        }
        String maxvalue = idGenerator.getMaxid();
        // 初始化下一个序列id
        increaseSequnce(Long.valueOf(maxvalue) + 1, busiTypeName);
        return maxvalue;
    }

    /**
     * 写入当前生成的最大序列号值
     * @param NextId
     * @param busiTypeName
     */
    public void increaseSequnce(long NextId, String busiTypeName)
    {
        IdGenerator idGenerator = new IdGenerator();
        idGenerator.setMaxid(String.valueOf(NextId));
        idGenerator.setFieldname(busiTypeName);
        idGeneratorMapper.updateMaxValueByFieldName(idGenerator);
    }
	
	/**
	 * 查询会员信息附带是否展示toolbar返回tbar
	 * @param openToolbar
	 * @return
	 */
	private String searchMemberInfo(String mobileNo,String openToolbar,boolean isFirstUseToolbar,String serviceType,String timeZone,SysTelecomOperatorAndHyMember sysTelecomOperatorAndHyMember,String flowThreshold,boolean isAlertOpen){
		String userId = sysTelecomOperatorAndHyMember!=null?sysTelecomOperatorAndHyMember.getMemberid():"";
		String tOperatorId = sysTelecomOperatorAndHyMember!=null?sysTelecomOperatorAndHyMember.getToperatorid():"";
	    String countryNo = sysTelecomOperatorAndHyMember!=null?sysTelecomOperatorAndHyMember.getCountryno():"";
	    String language = sysTelecomOperatorAndHyMember!=null?sysTelecomOperatorAndHyMember.getLangno():"";
	    
	    if(!isFirstUseToolbar){
	    	String firstShow = StringUtil.getStr(sysTelecomOperatorAndHyMember!=null?sysTelecomOperatorAndHyMember.getFirstshow():"");
	    	if("0".equals(firstShow)){
	    		isFirstUseToolbar = true;
	    	}
	    }
	    
//	    String mobileSeq = mobileNo.substring(Integer.parseInt(ParamPropertiesUtil.getSegLenthB()),Integer.parseInt(ParamPropertiesUtil.getSegLenthE()));//???
//		SysOperatorSeg sysOperatorSeg = sysOperatorSegMapper.selectByMobileNo(mobileSeq);
//		
//		String toperatorId = sysOperatorSeg.getToperatorid();
//		SysTelecomOperator sysTelecomOperator = sysTelecomOperatorMapper.selectByPrimaryKey(toperatorId);
	    
	    JSONObject json = new JSONObject();
		json.put("userId", userId);
		json.put("tOperatorId", tOperatorId);
		json.put("countryNo", countryNo);
		json.put("language", language);
		json.put("timeZone", timeZone);
		json.put("openToolbar", openToolbar);
		json.put("isFirstUseToolbar", isFirstUseToolbar);
		json.put("serviceType", serviceType);
		json.put("flowThreshold", flowThreshold);
		json.put("isAlertOpen", isAlertOpen);
		json.put("retCode", "0");
		json.put("retMsg", "success");
		
		return json.toJSONString();
	}
	
	/**
	 * 
	 * @param memberId
	 * @return
	 */
	private String searchMemberInfo(String memberid){
		HyMember hyMember = hyMemberMapper.selectByPrimaryKey(memberid);
		String userId = hyMember.getMemberid();
		String tOperatorId = hyMember.getToperatorid();
		String countryNo = hyMember.getCountryno();
		String language = hyMember.getLangno();
		String serviceType = hyMember.getPreposindicator();
		String phoneNum = hyMember.getMobileno();
		
		SysTelecomOperator sysTelecomOperator = sysTelecomOperatorMapper.selectByPrimaryKey(tOperatorId);
		String timeZone = sysTelecomOperator.getTimezone();
		
		JSONObject json = new JSONObject();
		json.put("userId", userId);
		json.put("tOperatorId", tOperatorId);
		json.put("countryNo", countryNo);
		json.put("language", language);
		json.put("timeZone", timeZone);
		json.put("serviceType", serviceType);
		json.put("phoneNum", phoneNum);
		json.put("retCode", "0");
		json.put("retMsg", "success");
		
		return json.toJSONString();
	}

	/**
	 * 将会员表中首次访问字段置为1
	 * @param memberId
	 * @return
	 */
	@Override
	public String updateFirstShowState(String memberId,String tOperatorId,String countryNo) throws Exception {
		HyMember member = new HyMember();
		member.setMemberid(memberId);
		member.setToperatorid(tOperatorId);
		member.setCountryno(countryNo);
		if(hyMemberMapper.updateFirstShowState(member) == 1){
			return "SUCCESS";
		}
		return "FAIL";
	}

	@Override
	public List<SysMainInterfaceIconConfig> queryIcon(String type,String tOperatorId,String countryNo) {
		SysMainInterfaceIconConfig config = new SysMainInterfaceIconConfig();
		config.setType(type);
		config.setToperatorid(tOperatorId);
		config.setCountryno(countryNo);
		return configMapper.selectAllIcon(config);
	}

	@Override
	public String updateActivityShowDate(String memberId,String tOperatorId,String countryNo,Date date) throws Exception {
		HyActivityExpandAlert activityExpandAlert = new HyActivityExpandAlert();
		activityExpandAlert.setMemberid(memberId);
		activityExpandAlert.setAlerttime(date);
		activityExpandAlert.setToperatorid(tOperatorId);
		activityExpandAlert.setCountryno(countryNo);
		if(hyActivityExpandAlertMapper.updateByPrimaryKeySelective(activityExpandAlert) == 1){
			return "SUCCESS";
		}
		return "FAIL";
	}

	@Override
	public boolean isActivityPopup(String mobileNo,String memberId,String serviceType){
		String brand = "";
		boolean isActivityOpen = false;
		long activityAlertDateTime = 0;
		SysTelecomOperatorAndHyMember sysTelecomOperatorAndHyMember = getMemberSysTelecomOperatorInfo(mobileNo);
		if(sysTelecomOperatorAndHyMember==null){
			return false;
		}
		brand = sysTelecomOperatorAndHyMember.getBrand();
		
		HyActivityExpandAlert recordActivity = new HyActivityExpandAlert();
		recordActivity.setCountryno(sysTelecomOperatorAndHyMember.getCountryno());
		recordActivity.setToperatorid(sysTelecomOperatorAndHyMember.getToperatorid());
		recordActivity.setMemberid(sysTelecomOperatorAndHyMember.getMemberid());
		List<HyActivityExpandAlert> HyActivityExpandAlertList = getHyActivityExpandAlertInfo(recordActivity);
		if(HyActivityExpandAlertList!=null && HyActivityExpandAlertList.size()==1 &&  HyActivityExpandAlertList.get(0).getAlerttime()!=null){
			activityAlertDateTime = (DateUtil.getTimeZoneDate(new Date(), sysTelecomOperatorAndHyMember.getTimezone(), "yyyy-MM-dd HH:mm:ss").getTime()-HyActivityExpandAlertList.get(0).getAlerttime().getTime())/(1000*60*60);
		}else{
			recordActivity.setAlerttime(DateUtil.getTimeZoneDate(new Date(), sysTelecomOperatorAndHyMember.getTimezone(), "yyyy-MM-dd HH:mm:ss"));
			recordActivity.setId(RuleUtil.generateUUID());
			recordActivity.setMobileno(mobileNo);
			hyActivityExpandAlertMapper.insert(recordActivity);
			activityAlertDateTime = 0;
		}
		List<SysRemindConfig> list = null;
		SysRemindConfig sysRemindConfig = new SysRemindConfig();
		sysRemindConfig.setCountryno(sysTelecomOperatorAndHyMember.getCountryno());
		sysRemindConfig.setToperatorid(sysTelecomOperatorAndHyMember.getToperatorid());
		sysRemindConfig.setIsopen(Constant.REMIND_VAILD);
		sysRemindConfig.setPreposindicator(serviceType);
		sysRemindConfig.setBrand(brand);
		if(brand!="" && serviceType!=""){
			list = getSysRemindConfig(sysRemindConfig);
		}
		if(list!=null && list.size()>0 ){
			String activityVal = "";
			for (SysRemindConfig sysRemindConfigTemp : list) {
				if("3".equals(sysRemindConfigTemp.getType())){
					activityVal = sysRemindConfigTemp.getRemindval();
					break;
				}
			}
			if(StringUtil.isNotNullOrEmpty(activityVal) && activityAlertDateTime>Long.valueOf(activityVal)){
				isActivityOpen = true;
			}
		}
		return  isActivityOpen;
	}
}
