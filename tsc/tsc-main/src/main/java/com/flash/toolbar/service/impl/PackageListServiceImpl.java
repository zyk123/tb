package com.flash.toolbar.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.flash.toolbar.common.util.*;
import com.flash.toolbar.mapper.*;
import com.flash.toolbar.model.*;
import com.flash.toolbar.redis.RedisOperation;
import com.flash.toolbar.service.PackageListService;
import com.flash.toolbar.token.TokenManager;
import com.flash.toolbar.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;

/**
 * 套餐shop页相关接口
 * @author ocean
 *
 */
@Service(value="packageListServiceImpl")
public class PackageListServiceImpl implements PackageListService{
	
	@Autowired
	private TcFlowpackageDetailMapper tcFlowpackageDetailMapper;

	/**
	 * 管理套餐
	 */
	@Autowired
	private TcFlowPackageMapper tcFlowPackageMapper;
	
	/**
	 * 管理套餐类型
	 */
	@Autowired
	private TcPackageTypeMapper tcPackageTypeMapper;
	
	/**
	 * 管理套餐订购记录
	 */
	@Autowired
	private TcFlowSubMapper tcFlowSubMapper;
	
	/**
	 * 操作运营商登记表
	 */
	@Autowired
	private SysTelecomOperatorMapper sysTelecomOperatorMapper;
	
	/**
	 * 操作会员
	 */
	@Autowired
	private HyMemberMapper hyMemberMapper;
	
	/**
	 * 获取token管理
	 */
	@Resource
	private TokenManager tokenManager;
	
	@Autowired
	private RedisOperation redisOperation;	
	
	/**
	 * 默认修改人
	 */
	private static final String modifyman = "system"; 
	
	/**
	 * 订单状态
	 */
	public interface orderStatus {
       static String paying = "0";//支付中
       static String paySuccess = "1";//支付成功
       static String payFailed = "2";//支付失败
    }
	
	/**
	 * 套餐查询接口
	 * @param businessStr 入参
	 * @return
	 */
	@Override
	public String getPackageList(String businessStr) throws Exception{
		JSONObject requestJSON = JSONObject.parseObject(businessStr);
		String tOperatorId = requestJSON.getString("tOperatorId");
		String countryNo = requestJSON.getString("countryNo");
		String packageTypeId = requestJSON.getString("packageTypeId");
		String memberId = requestJSON.getString("memberId");
//		String serviceType = requestJSON.getString("serviceType");
		
		String brand = "new xpax";
//		if(memberId != null){
//			HyMember member = hyMemberMapper.selectByPrimaryKey(memberId);
//			if(member != null){
//				brand = member.getBrand();
//			}
//		}
		
		Map<String,String> map =  new HashMap<String,String>();
		map.put("tOperatorId", tOperatorId);
		map.put("countryNo", countryNo);
		map.put("packageTypeId", packageTypeId);
		map.put("brand", brand);
		
		List<TcPackageAndPackageType> list =  new ArrayList<TcPackageAndPackageType>();
		//获取所有已经上架的套餐类型
		List<TcPackageType> tcPackageTypeL = tcPackageTypeMapper.selectAllPackageType(map);
		for(int i = 0;i < tcPackageTypeL.size();i++){
			TcPackageType tcPackageType = tcPackageTypeL.get(i);
			String ptid = tcPackageType.getPackagetypeid();
//			List<TcFlowPackage> packageList =  tcFlowPackageMapper.selectByPackageTypeId(ptid);
			List<TcFlowPackage> packageList = tcFlowPackageMapper.selectByFlowPackageTypeId(ptid);
			TcPackageAndPackageType tcPackageAndPackageType = new TcPackageAndPackageType();
			setPackageAndPackageType(tcPackageAndPackageType,tcPackageType,packageList);
			list.add(tcPackageAndPackageType);
		}
		return JSONObject.toJSONString(list);
	}
	
	/**
	 * 返回套餐类型和套餐集合列表
	 * @param tcPackageAndPackageType
	 * @param tcPackageType
	 * @param packageList
	 */
	private void setPackageAndPackageType(TcPackageAndPackageType tcPackageAndPackageType,TcPackageType tcPackageType,List<TcFlowPackage> packageList){
		tcPackageAndPackageType.setAdddate(tcPackageType.getAdddate());
		tcPackageAndPackageType.setCountryno(tcPackageType.getCountryno());
		tcPackageAndPackageType.setOrderno(tcPackageType.getOrderno());
		tcPackageAndPackageType.setPackageList(packageList);
		tcPackageAndPackageType.setPackagetypeid(tcPackageType.getPackagetypeid());
		tcPackageAndPackageType.setPtname(tcPackageType.getPtname());
		tcPackageAndPackageType.setToperatorid(tcPackageType.getToperatorid());
	}

	/**
	 * 套餐订购
	 */
	@Override
	public String tcFlowSub(String businessStr) throws Exception{
		String rtn = null;
		JSONObject requestJSON = JSONObject.parseObject(businessStr);
		String userId = requestJSON.getString("userId");
		String fGNo = requestJSON.getString("fGNo");
		String tOperatorId = requestJSON.getString("tOperatorId");
		String countryNo = requestJSON.getString("countryNo");
		String requestSerial = requestJSON.getString("requestSerial");	
		TcFlowPackage tcFlowPackage = tcFlowPackageMapper.selectByPackageNo(fGNo);
		
		Map<String,String> map =  new HashMap<String,String>();
		map.put("memberid", userId);
		map.put("toperatorid", tOperatorId);
		map.put("countryno", countryNo);
		HyMember hyMember = hyMemberMapper.selectByMap(map);
		if(null==hyMember){
			JSONObject json = new JSONObject();
			json.put("retCode", Constant.RETURN_EMPTY);
			json.put("retMsg", "the member is not exit");
			return ResponseUtil.unifyFailReturn(json.toJSONString());
		}
		
		SysTelecomOperatorAndHyMember sysTelecomOperatorAndHyMember = sysTelecomOperatorMapper.selectByMemberId(userId);
		String timeZone = "+8";
		if(null == sysTelecomOperatorAndHyMember){
			JSONObject json = new JSONObject();
			json.put("retCode", Constant.RETURN_EMPTY);
			json.put("retMsg", "sysTelecomOperatorAndHyMember is not exit");
			return ResponseUtil.unifyFailReturn(json.toJSONString());
		}
		timeZone = sysTelecomOperatorAndHyMember.getTimezone();
		
		if(null == tcFlowPackage){
			JSONObject json = new JSONObject();
			json.put("retCode", Constant.RETURN_EMPTY);
			json.put("retMsg", "the package is not exit");
			return ResponseUtil.unifyFailReturn(json.toJSONString());
		}
		
		String flowsubid = RuleUtil.generateUUID();
		String orderNumber = tcFlowSubMapper.selectOrderSequence();
		BigDecimal amount = tcFlowPackage.getAmount();
		String currency = tcFlowPackage.getCurrency();
		String fgid = tcFlowPackage.getPackageid();
		String fgname = tcFlowPackage.getFgname();
		
		String mobileno = sysTelecomOperatorAndHyMember.getMobileno();
		
		TcFlowSub tcFlowSub = new TcFlowSub();
		tcFlowSub.setAmount(amount);
		tcFlowSub.setCountryno(countryNo);
		tcFlowSub.setCurrency(currency);
		tcFlowSub.setFgid(fgid);
		tcFlowSub.setFgname(fgname);
		tcFlowSub.setFlowsubid(flowsubid);
		tcFlowSub.setMemberid(userId);
		tcFlowSub.setMobileno(mobileno);
		tcFlowSub.setModifydate(DateUtil.getTimeZoneDate(new Date(),timeZone,"yyyy-MM-dd HH:mm:ss"));//--timeZone--
		tcFlowSub.setModifyman(modifyman);
		tcFlowSub.setOperatororderno("");
		tcFlowSub.setOrdernumber(orderNumber);
		tcFlowSub.setStatus(orderStatus.paying);
		tcFlowSub.setSubdate(DateUtil.getTimeZoneDate(new Date(),timeZone,"yyyy-MM-dd HH:mm:ss"));//--timeZone--
		tcFlowSub.setToperatorid(tOperatorId);
		//tsc套餐订购入库操作业务日志记录开始
		BusinessLogger.info(requestSerial+"|start insert TC_FLOWSUB");
		int ret = tcFlowSubMapper.insertSelective(tcFlowSub);//保存订单
		//tsc套餐订购入库操作业务日志记录结束
		BusinessLogger.info(requestSerial+"|end insert TC_FLOWSUB");
		if(1==ret){
			JSONObject planpurchase = planpurchase(mobileno,fGNo);
			String status = orderStatus.paying;
			String errorCode = "-1";
			String errorDescription = "The network is currently busy, Please try again later.";
			String expireDate = "";
			if(null != planpurchase){
				try{
					JSONObject planPurchaseResponse = planpurchase.getJSONObject("planPurchaseResponse");
					JSONObject header = planPurchaseResponse.getJSONObject("header");
					JSONObject productOrderInfo = planPurchaseResponse.getJSONObject("productOrderInfo");
					status = header.getString("status");
					if("OK".equals(status)){
						status = orderStatus.paySuccess;
//						String mobilenoSub = mobileno.substring(Integer.parseInt(ParamPropertiesUtil.getiSegLenthB()),Integer.parseInt(ParamPropertiesUtil.getiSegLenthE()));
						String mobilenoSub = mobileno;
//						ExceptionLogger.error("tcFlowSub wangxiaoran test  celcom.user.volume."+mobileno + redisOperation.get("celcom.user.volume."+mobilenoSub));


						redisOperation.del("celcom.user.balance."+mobilenoSub);
						redisOperation.del("celcom.user.volume."+mobilenoSub);
					}else{
						status = orderStatus.payFailed;
					}
					errorCode = header.getString("errorCode");
					errorDescription = header.getString("errorDescription");
					expireDate = productOrderInfo!=null?productOrderInfo.getString("expireDate"):"";
				}catch(Exception e){
					status = orderStatus.payFailed;
					errorDescription = "The network is currently busy, Please try again later.";
					ExceptionLogger.LoggerInfo(requestSerial,mobileno,"subFlow failed,the Format is error!",e);
				}
			}else{
				status = orderStatus.payFailed;
			}
			
			//更新订单状态
			TcFlowSub tcFlowSubOcs = new TcFlowSub();
			tcFlowSubOcs.setFlowsubid(flowsubid);
			tcFlowSubOcs.setStatus(status);
			tcFlowSubOcs.setModifydate(DateUtil.getTimeZoneDate(new Date(),timeZone,"yyyy-MM-dd HH:mm:ss"));//--timeZone--
			//tsc套餐订购更新数据库业务日志记录开始
			BusinessLogger.info(requestSerial+"|start update TC_FLOWSUB");
			tcFlowSubMapper.updateByPrimaryKeySelective(tcFlowSubOcs);
			//tsc套餐订购更新数据库业务日志记录结束
			BusinessLogger.info(requestSerial+"|end update TC_FLOWSUB");
			
			JSONObject json = new JSONObject();
			json.put("retCode", errorCode);
			json.put("retMsg", errorDescription);
			json.put("orderNo", orderNumber);
			json.put("orderStatus", status);
			json.put("orderDesc", errorDescription);
			json.put("expireDate", expireDate);
			rtn = ResponseUtil.unifySuccessReturn(json.toJSONString());
		}else{
			JSONObject json = new JSONObject();
			json.put("retCode", "-1");
			json.put("retMsg", "failed");
			rtn = ResponseUtil.unifyFailReturn(json.toJSONString());
		}
		return rtn;
	}
	
	/**
	 * ocs 套餐订购
	 */
	@Override
	public JSONObject planpurchase(String msisdn,String productID) {
			Configuration cfg = new Configuration(null, "environment.properties");
			String url = cfg.getValue("planpurchase.url");
			//msisdn = "60192070335";
			//productID = "40512";
			//url = MessageFormat.format(url, new Object[]{msisdn.substring(Integer.parseInt(ParamPropertiesUtil.getiSegLenthB()),Integer.parseInt(ParamPropertiesUtil.getiSegLenthE()))});
			url = MessageFormat.format(url, new Object[]{msisdn});
			
			StringBuilder sb = new StringBuilder();
			sb.append("{\"productID\": \""+productID+"\"}");
			
//			String key = "bgC2AZCI618je2eOwUBFJ9Q6OWgM";//tokenManager.queryToken();
			String key = tokenManager.queryToken();
			Map<String,String> map = new HashMap<String,String>();
			map.put("Authorization", "Bearer "+key);
			map.put("Content-Type", "application/json");
			map.put("Host", "celcom-prod.apigee.net");
			//tsc调用接口跟踪日志记录开始
			TraceLogger.debugInterface("tsc", "celcom", "tcFlowSub", Constant.ACTION_IN, sb.toString());
			//调用运营商接口
			JSONObject jsonHttp = HttpHelper.httpPostSubPackage(url,map,sb.toString());
			//tsc调用接口跟踪日志记录结束
			TraceLogger.debugInterface("celcom", "tsc", "tcFlowSub", Constant.ACTION_OUT, null==jsonHttp ? "null":jsonHttp.toString());
			if(jsonHttp != null){
//				TraceLogger.info("tsc-PackageListServiceImpl-planpurchase return:"+jsonHttp.toJSONString());
				return jsonHttp;
			}else{
//				TraceLogger.info("tsc-PackageListServiceImpl-planpurchase return:"+null);
				return null;
			}
	}

	/**
	 * 套餐详情
	 */
	@Override
	public String getPackageDetail(String businessStr) {
		JSONArray arr = new JSONArray();
		JSONObject json = null;
		JSONObject requestJSON = JSONObject.parseObject(businessStr);
		String packageid = requestJSON.getString("packageId");
		List<TcFlowpackageDetail> parentList = tcFlowpackageDetailMapper.selectByPackageId(packageid);
		for (int i = 0; i < parentList.size(); i++) {
			TcFlowpackageDetail parent = parentList.get(i);
			json = new JSONObject();
			json.put("packageDetailId", parent.getPackagedetailid());
			json.put("packageId", parent.getPackageid());
			json.put("detailName", parent.getDetailname());
			json.put("itemName", parent.getItemname());
			json.put("itemValue", parent.getItemvalue());
			json.put("orderNo", parent.getOrderno());
			json.put("parentId", parent.getParentid());
			arr.add(json);
			List<TcFlowpackageDetail> childList = tcFlowpackageDetailMapper.selectByParentId(parent.getPackagedetailid());
			for (int j = 0; j < childList.size(); j++) {
				TcFlowpackageDetail child = childList.get(j);
				json = new JSONObject();
				json.put("packageDetailId", child.getPackagedetailid());
				json.put("packageId", child.getPackageid());
				json.put("detailName", child.getDetailname());
				json.put("itemName", child.getItemname());
				json.put("itemValue", child.getItemvalue());
				json.put("orderNo", child.getOrderno());
				json.put("parentId", child.getParentid());
				arr.add(json);
			}
		}
		return arr.toJSONString();
	}
	
}
