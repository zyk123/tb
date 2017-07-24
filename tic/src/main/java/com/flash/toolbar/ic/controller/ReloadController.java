package com.flash.toolbar.ic.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.flash.toolbar.common.util.BusinessLogger;
import com.flash.toolbar.common.util.Constant;
import com.flash.toolbar.common.util.ResponseUtil;
import com.flash.toolbar.common.util.StringUtil;
import com.flash.toolbar.facade.ReloadManager;
import com.flash.toolbar.ic.dubbo.DubboSupport;

@Controller
@RequestMapping(value = "/reload")
public class ReloadController {

	@Autowired
	private DubboSupport dubboSupport;
	
	@RequestMapping(value = "/saveOrder", method = RequestMethod.POST)
	@ResponseBody
	public String saveOrder(
			@RequestParam(value = "paymentMethod", defaultValue = "") String paymentMethod,
			@RequestParam(value = "totalAmount", defaultValue = "") String totalAmount,
			@RequestParam(value = "tOperatorId", defaultValue = "") String tOperatorId,
			@RequestParam(value = "countryNo", defaultValue = "") String countryNo,
			@RequestParam(value = "msisdn", defaultValue = "") String msisdn,
			@RequestParam(value = "requestSerial", defaultValue = "") String requestSerial,
			@RequestParam(value = "mima", defaultValue = "") String mima,
			@RequestParam(value = "storeId", defaultValue = "") String storeId,
			@RequestParam(value = "storeFlag", defaultValue = "") String storeFlag,
			@RequestParam(value = "serviceType", defaultValue = "") String serviceType
			) {
		if(!StringUtil.isNotNullOrEmpty(tOperatorId)){
			JSONObject jsonFailed = new JSONObject();
			jsonFailed.put("retCode", Constant.PARAMS_NOTNULL);
			jsonFailed.put("retMsg", "tOperatorId can not be empty!");
			return ResponseUtil.unifySuccessReturn(jsonFailed);
		}
		if(!StringUtil.isNotNullOrEmpty(countryNo)){
			JSONObject jsonFailed = new JSONObject();
			jsonFailed.put("retCode", Constant.PARAMS_NOTNULL);
			jsonFailed.put("retMsg", "countryNo can not be empty!");
			return ResponseUtil.unifySuccessReturn(jsonFailed);
		}
		JSONObject json = new JSONObject();
		json.put("paymentMethod", paymentMethod);
		json.put("totalAmount", totalAmount);
		json.put("tOperatorId", tOperatorId);
		json.put("countryNo", countryNo);
		json.put("msisdn", msisdn);
		json.put("requestSerial", requestSerial);
		json.put("mima", mima);
		json.put("storeId", storeId);
		json.put("storeFlag", storeFlag);
		json.put("serviceType", serviceType);
		String param = json.toJSONString();
		BusinessLogger.LoggerInfo(requestSerial, "tic", "saveOrder", true, null, String.valueOf(new Date().getTime()));
		ReloadManager reloadManager = dubboSupport.getReloadManager();
		String tmp = reloadManager.saveOrder(param);
		BusinessLogger.LoggerInfo(requestSerial, "tic", "saveOrder", false, tmp, String.valueOf(new Date().getTime()));
		return tmp;
	}
	
	@RequestMapping(value = "/saveReloadResult", method = RequestMethod.POST)
	@ResponseBody
	public String saveReloadResult(
			@RequestParam(value = "storeId", defaultValue = "") String storeId,
			@RequestParam(value = "orderId", defaultValue = "") String orderId,
			@RequestParam(value = "transDate", defaultValue = "") String transDate,
			@RequestParam(value = "totalAmount", defaultValue = "") String totalAmount,
			@RequestParam(value = "msisdn", defaultValue = "") String msisdn,
			@RequestParam(value = "balance", defaultValue = "") String balance,
			@RequestParam(value = "suspendDate", defaultValue = "") String suspendDate,
			@RequestParam(value = "returnCode", defaultValue = "") String returnCode,
			@RequestParam(value = "reasonCode", defaultValue = "") String reasonCode,
			@RequestParam(value = "reasonDesc", defaultValue = "") String reasonDesc,
			@RequestParam(value = "referId", defaultValue = "") String referId,
			@RequestParam(value = "paymentMethod", defaultValue = "") String paymentMethod,
			@RequestParam(value = "cardPaddedNum", defaultValue = "") String cardPaddedNum,
			@RequestParam(value = "authCode", defaultValue = "") String authCode,
			@RequestParam(value = "token", defaultValue = "") String token,
			@RequestParam(value = "transactionId", defaultValue = "") String transactionId,
			@RequestParam(value = "accountNum", defaultValue = "") String accountNum,
			@RequestParam(value = "signature", defaultValue = "") String signature,
			@RequestParam(value = "requestSerial", defaultValue = "") String requestSerial
			) {
		JSONObject json = new JSONObject();
		json.put("storeId", storeId);
		json.put("orderId", orderId);
		json.put("transDate", transDate);
		json.put("totalAmount", totalAmount);
		json.put("msisdn", msisdn);
		json.put("balance", balance);
		json.put("suspendDate", suspendDate);
		json.put("returnCode", returnCode);
		json.put("reasonCode", reasonCode);
		json.put("reasonDesc", reasonDesc);
		json.put("referId", referId);
		json.put("paymentMethod", paymentMethod);
		json.put("cardPaddedNum", cardPaddedNum);
		json.put("authCode", authCode);
		json.put("token", token);
		json.put("transactionId", transactionId);
		json.put("accountNum", accountNum);
		json.put("signature", signature);
		json.put("requestSerial", requestSerial);
		String param = json.toJSONString();
		BusinessLogger.LoggerInfo(requestSerial, "tic", "saveOrder", true, null, String.valueOf(new Date().getTime()));
		ReloadManager reloadManager = dubboSupport.getReloadManager();
		String tmp = reloadManager.saveReloadResult(param);
		BusinessLogger.LoggerInfo(requestSerial, "tic", "saveOrder", false, tmp, String.valueOf(new Date().getTime()));
		return tmp;
	}
}
