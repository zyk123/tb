package com.flash.toolbar.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.flash.toolbar.common.util.*;
import com.flash.toolbar.mapper.CzPayRequestMapper;
import com.flash.toolbar.mapper.CzPayResponseMapper;
import com.flash.toolbar.mapper.CzReloadOrderMapper;
import com.flash.toolbar.model.CzPayRequest;
import com.flash.toolbar.model.CzReloadOrder;
import com.flash.toolbar.service.ReloadManagerService;
import com.flash.toolbar.utils.SHA1HashSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service("reloadManagerService")
public class ReloadManagerServiceImpl implements ReloadManagerService{

	@Autowired
	private CzPayRequestMapper czPayRequestMapper;
	
	@Autowired
	private CzPayResponseMapper czPayResponseMapper;
	
	@Autowired
	private CzReloadOrderMapper czReloadOrderMapper;
	
	/**
	 * 保存订单
	 */
	@Override
	public String saveOrder(String busStr) {
		String rtn = null;
		JSONObject requestJSON = JSONObject.parseObject(busStr);
		String totalAmount = requestJSON.getString("totalAmount");
		String storeFlag = requestJSON.getString("storeFlag");
//		String password = requestJSON.getString("mima");
//		String storeId = requestJSON.getString("storeId");
		String password ="toolbar";
		String storeId = "toolbar";
		if("t".equals(storeFlag)){//todo wangxiaoran
			password =ParamPropertiesUtil.getReloadPassword();
			storeId = ParamPropertiesUtil.getReloadStoreid();
		}else if("x".equals(storeFlag)){
			password =ParamPropertiesUtil.getReloadPassword2();
			storeId = ParamPropertiesUtil.getReloadStoreid2();
		}
		String serviceType = requestJSON.getString("serviceType");
		if(!StringUtil.isNotNullOrEmpty(totalAmount)){
			totalAmount = "0";
		}
		totalAmount = totalAmount.indexOf(".")==-1?totalAmount+".00":totalAmount;
		String tOperatorId = requestJSON.getString("tOperatorId");
		String countryNo = requestJSON.getString("countryNo");
		String msisdn = requestJSON.getString("msisdn");
		String paymentMethod = requestJSON.getString("paymentMethod");
		if(!StringUtil.isNotNullOrEmpty(paymentMethod)){
			paymentMethod = "1";
		}
		String requestSerial = requestJSON.getString("requestSerial");
		String orderId = createOrderId();
//		String storeId = "toolbar";
		String responseUrl = null;
		if("Prepaid".equals(serviceType)){//todo wangxiaoran
			responseUrl = ParamPropertiesUtil.getReloadResponseUrl()+"?storeFlag="+storeFlag;
		}else{
			responseUrl = ParamPropertiesUtil.getReloadPostResponseUrl()+"?storeFlag="+storeFlag;
		}
		String requestUrl = ParamPropertiesUtil.getReloadRequestUrl();
//		String password = ParamPropertiesUtil.getReloadPassword();
		String reconFilename = createReconFilename();
		String transDate = createTransDate();
		String signature = createSignature(storeId, password, orderId, totalAmount);
		
		CzPayRequest czPayRequest = new CzPayRequest();
		String czPayRequestId = RuleUtil.generateUUID();
		czPayRequest.setId(czPayRequestId);
		czPayRequest.setTotalamount(new BigDecimal(totalAmount));
		czPayRequest.setToperatorid(tOperatorId);
		czPayRequest.setCountryno(countryNo);
		czPayRequest.setMsisdn(msisdn);
		czPayRequest.setOrderid(orderId);
		czPayRequest.setStoreid(storeId);
		czPayRequest.setReconfilename(reconFilename);
		czPayRequest.setTransdate(transDate);
		czPayRequest.setSignature(signature);
		czPayRequest.setPrepayment("Y");
		czPayRequest.setPaymentmethod(new BigDecimal(paymentMethod));
		czPayRequest.setModifydate(new Date());
		czPayRequest.setModifyman("admin|"+storeFlag); //todo wangxiaoran   区分充值类别
		
		CzReloadOrder czReloadOrder = new CzReloadOrder();
		czReloadOrder.setId(orderId);
		czReloadOrder.setStoreid(storeId);
		czReloadOrder.setTotalamount(new BigDecimal(totalAmount));
		czReloadOrder.setToperatorid(tOperatorId);
		czReloadOrder.setCountryno(countryNo);
		czReloadOrder.setMsisdn(msisdn);
		czReloadOrder.setTransdate(transDate);
		czReloadOrder.setStatus(Constant.RELOAD_PAYING);
		czReloadOrder.setModifydate(new Date());
		czReloadOrder.setModifyman("admin|"+storeFlag);//todo wangxiaoran   区分充值类别
		czReloadOrder.setReloaddate(new Date());
		
		czPayRequestMapper.insertSelective(czPayRequest);
		czReloadOrderMapper.insertSelective(czReloadOrder);
		
		JSONObject json = new JSONObject();
		json.put("retCode", "0");
		json.put("retMsg", "success");
		json.put("orderId", orderId);
		json.put("storeId", storeId);
		json.put("responseUrl", responseUrl);
		json.put("requestUrl", requestUrl);
		json.put("reconFilename", reconFilename);
		json.put("transDate", transDate);
		json.put("signature", signature);
		json.put("msisdn", msisdn);
		json.put("totalAmount", totalAmount);
		rtn = ResponseUtil.unifySuccessReturn(json.toJSONString());
		return rtn;
	}
	
	/**
	 * 创建orderid
	 * @return
	 */
	private synchronized String createOrderId(){
		String rtn = null;
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		rtn = sdf.format(date);
		return rtn;
	}
	
	/**
	 * 创建reconFilename
	 * @return
	 */
	private String createReconFilename(){
		String rtn = null;
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		rtn = sdf.format(date);
		return rtn;
	}
	
	/**
	 * 创建交易日期
	 * @return
	 */
	private String createTransDate(){
		String rtn = null;
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		rtn = sdf.format(date)+"+0800";
		return rtn;
	}
	
	/**
	 * 创建签名
	 * @param storeId 店铺id
	 * @param password 密码
	 * @param orderId 订单id
	 * @param amount 金额
	 * @return
	 */
	private String createSignature(String storeId,String password,String orderId,String amount){
		String rtn = null;
		String signature = storeId + password + orderId + amount.replace(".", "");
		SHA1HashSignature sha1Hash = new SHA1HashSignature();
		rtn = sha1Hash.generateSHA(signature);
		return rtn;
	}

}
