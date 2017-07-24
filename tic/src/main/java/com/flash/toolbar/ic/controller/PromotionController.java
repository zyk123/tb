package com.flash.toolbar.ic.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.flash.toolbar.common.util.BusinessLogger;
import com.flash.toolbar.common.util.Constant;
import com.flash.toolbar.common.util.DateUtil;
import com.flash.toolbar.common.util.ExceptionLogger;
import com.flash.toolbar.common.util.ResponseUtil;
import com.flash.toolbar.common.util.StringUtil;
import com.flash.toolbar.common.util.TraceLogger;
import com.flash.toolbar.ic.dubbo.DubboSupport;

@Controller
@RequestMapping(value = "/promotion")
public class PromotionController {
	@Autowired
	private DubboSupport dubboSupport;
	
	/**
	 * 获取活动列表
	 * @param tOperatorId
	 * @param countryNo
	 * @param requestSerial
	 * @return
	 */
	@RequestMapping(value = "/getPromotionList", method = RequestMethod.POST)
	@ResponseBody
	public String getPromotionList(
			@RequestParam(value = "tOperatorId", defaultValue = "") String tOperatorId,
			@RequestParam(value = "countryNo", defaultValue = "") String countryNo,
			@RequestParam(value = "requestSerial", defaultValue = "") String requestSerial) {
		BusinessLogger.LoggerInfo(requestSerial, "tic", "getPromotionList", true, countryNo + "," + tOperatorId, String.valueOf(new Date().getTime()));
		JSONObject jsonBody = new JSONObject();
		try{
			JSONObject param = new JSONObject();
			param.put("countryNo", countryNo);
			param.put("tOperatorId", tOperatorId);
			param.put("requestSerial", requestSerial);
			
			TraceLogger.debugInterface("tic", "tsc", "getPromotionList", Constant.ACTION_IN, param.toJSONString());
			String jsonRet = dubboSupport.getPromotionManager().getPromotionList(param.toJSONString());
			TraceLogger.debugInterface("tsc", "tic", "getPromotionList", Constant.ACTION_OUT, jsonRet);
			
			if(StringUtil.isNotNullOrEmpty(jsonRet)){
				JSONArray array = JSONArray.parseArray(jsonRet);
				if(array.size() > 0){					
					jsonBody.put("list", array);
					jsonBody.put("retCode", Constant.RETURN_SUCCESS);
					jsonBody.put("retMsg", "success");
					return ResponseUtil.unifySuccessReturn(jsonBody);
				}
				else{
					jsonBody.put("retCode", Constant.RETURN_EMPTY);
					jsonBody.put("retMsg", "getPromotionList empty");
				}
			}
			else{
				jsonBody.put("retCode", Constant.RETURN_EMPTY);
				jsonBody.put("retMsg", "getPromotionList empty");
			}
		}
		catch(Exception e){
			ExceptionLogger.LoggerInfo(requestSerial, "", "PromotionController getPromotionList", e);
		}
		
		BusinessLogger.LoggerInfo(requestSerial, "tic", "getPromotionList", false, jsonBody.toJSONString(), String.valueOf(new Date().getTime()));
		return ResponseUtil.unifyFailReturn(jsonBody);
	}
	
	/**
	 * 获取活动奖品信息
	 * @param promotionId
	 * @param tOperatorId
	 * @param countryNo
	 * @param requestSerial
	 * @return
	 */
	@RequestMapping(value = "/getPromotionPrize", method = RequestMethod.POST)
	@ResponseBody
	public String getPromotionPrize(
			@RequestParam(value = "memberId", defaultValue = "") String memberId,
			@RequestParam(value = "promotionId", defaultValue = "") String promotionId,
			@RequestParam(value = "tOperatorId", defaultValue = "") String tOperatorId,
			@RequestParam(value = "countryNo", defaultValue = "") String countryNo,
			@RequestParam(value = "requestSerial", defaultValue = "") String requestSerial) {
		BusinessLogger.LoggerInfo(requestSerial, "tic", "getPromotionPrize", true, memberId, String.valueOf(new Date().getTime()));
		JSONObject jsonBody = new JSONObject();
		if(StringUtil.isNotNullOrEmpty(memberId,promotionId)){
			try{
				JSONObject param = new JSONObject();
				param.put("memberId", memberId);
				param.put("promotionId", promotionId);
				param.put("countryNo", countryNo);
				param.put("tOperatorId", tOperatorId);
				param.put("requestSerial", requestSerial);
				
				TraceLogger.debugInterface("tic", "tsc", "getPromotionPrize", Constant.ACTION_IN, param.toJSONString());
				String jsonRet = dubboSupport.getPromotionManager().getPromotionPrize(param.toJSONString());
				TraceLogger.debugInterface("tsc", "tic", "getPromotionPrize", Constant.ACTION_OUT, jsonRet);
				
				if(StringUtil.isNotNullOrEmpty(jsonRet)){
					jsonBody = JSONObject.parseObject(jsonRet);
					jsonBody.put("retCode", Constant.RETURN_SUCCESS);
					jsonBody.put("retMsg", "success");
					return ResponseUtil.unifySuccessReturn(jsonBody);
				}
				else{
					jsonBody.put("retCode", Constant.RETURN_EMPTY);
					jsonBody.put("retMsg", "getPromotionList empty");
				}
			}
			catch(Exception e){
				ExceptionLogger.LoggerInfo(requestSerial, "", "PromotionController getPromotionPrize", e);
			}
		}
		else{
			jsonBody.put("retCode", Constant.PARAMS_NOTNULL);
			jsonBody.put("retMsg", "param empty");
		}
		
		BusinessLogger.LoggerInfo(requestSerial, "tic", "getPromotionList", false, jsonBody.toJSONString(), String.valueOf(new Date().getTime()));
		return ResponseUtil.unifyFailReturn(jsonBody);
	}
	
	/**
	 * 抽奖
	 * @param memberId
	 * @param promotionId
	 * @param tOperatorId
	 * @param countryNo
	 * @param requestSerial
	 * @return
	 */
	@RequestMapping(value = "/getPrize", method = RequestMethod.POST)
	@ResponseBody
	public String getPrize(
			@RequestParam(value = "memberId", defaultValue = "") String memberId,
			@RequestParam(value = "promotionId", defaultValue = "") String promotionId,
			@RequestParam(value = "tOperatorId", defaultValue = "") String tOperatorId,
			@RequestParam(value = "countryNo", defaultValue = "") String countryNo,
			@RequestParam(value = "requestSerial", defaultValue = "") String requestSerial) {
		BusinessLogger.LoggerInfo(requestSerial, "tic", "getPrize", true, memberId, String.valueOf(new Date().getTime()));
		JSONObject jsonBody = new JSONObject();
		if(StringUtil.isNotNullOrEmpty(memberId,promotionId)){
			try{
				JSONObject param = new JSONObject();
				param.put("memberId", memberId);
				param.put("promotionId", promotionId);
				param.put("countryNo", countryNo);
				param.put("tOperatorId", tOperatorId);
				param.put("requestSerial", requestSerial);
				
				TraceLogger.debugInterface("tic", "tsc", "winPrize", Constant.ACTION_IN, param.toJSONString());
				String jsonRet = dubboSupport.getPromotionManager().winPrize(param.toJSONString());
				TraceLogger.debugInterface("tsc", "tic", "winPrize", Constant.ACTION_OUT, jsonRet);
				
				if(StringUtil.isNotNullOrEmpty(jsonRet)){
					JSONObject obj = JSONObject.parseObject(jsonRet);					
					if(obj.getString("retCode") != null && obj.getString("retCode").equals(Constant.RETURN_FAILURE)){
						return ResponseUtil.unifyFailReturn(obj);
					}
					else{
						obj.put("retCode", Constant.RETURN_SUCCESS);
						obj.put("retMsg", "success");
						return ResponseUtil.unifySuccessReturn(obj);
					}
				}
				else{
					jsonBody.put("retCode", Constant.RETURN_EMPTY);
					jsonBody.put("retMsg", "getPrize empty");
				}
			}
			catch(Exception e){
				ExceptionLogger.LoggerInfo(requestSerial, "", "PromotionController getPrize", e);
			}
		}
		else{
			jsonBody.put("retCode", Constant.PARAMS_NOTNULL);
			jsonBody.put("retMsg", "param empty");
		}
		
		BusinessLogger.LoggerInfo(requestSerial, "tic", "getPrize", false, jsonBody.toJSONString(), String.valueOf(new Date().getTime()));
		return ResponseUtil.unifyFailReturn(jsonBody);
	}
	
	
	/**
	 * 抓手机抽奖
	 * @param memberId
	 * @param promotionId
	 * @param tOperatorId
	 * @param countryNo
	 * @param requestSerial
	 * @return
	 */
	@RequestMapping(value = "/phoneGameLuckDraw", method = RequestMethod.POST)
	@ResponseBody
	public String phoneGameLuckDraw(
			@RequestParam(value = "memberId", defaultValue = "") String memberId,
			@RequestParam(value = "promotionId", defaultValue = "") String promotionId,
			@RequestParam(value = "tOperatorId", defaultValue = "") String tOperatorId,
			@RequestParam(value = "countryNo", defaultValue = "") String countryNo,
			@RequestParam(value = "scoreNum", defaultValue = "") String scoreNum,
			@RequestParam(value = "mobileNo", defaultValue = "") String mobileNo,
			@RequestParam(value = "requestSerial", defaultValue = "") String requestSerial) {
		BusinessLogger.LoggerInfo(requestSerial, "tic", "phoneGameLuckDraw", true, memberId, String.valueOf(new Date().getTime()));
		JSONObject jsonBody = new JSONObject();
		if(StringUtil.isNotNullOrEmpty(memberId,promotionId)){
			try{
				JSONObject param = new JSONObject();
				param.put("memberId", memberId);
				param.put("promotionId", promotionId);
				param.put("countryNo", countryNo);
				param.put("tOperatorId", tOperatorId);
				param.put("requestSerial", requestSerial);
				param.put("scoreNum", scoreNum);
				param.put("mobileNo", mobileNo);
				
				TraceLogger.debugInterface("tic", "tsc", "phoneGameLuckDraw", Constant.ACTION_IN, param.toJSONString());
				String jsonRet = dubboSupport.getPromotionManager().phoneGameLuckDraw(param.toJSONString());
				TraceLogger.debugInterface("tsc", "tic", "phoneGameLuckDraw", Constant.ACTION_OUT, jsonRet);
				
				if(StringUtil.isNotNullOrEmpty(jsonRet)){
					JSONObject obj = JSONObject.parseObject(jsonRet);					
					if(obj.getString("retCode") != null && obj.getString("retCode").equals(Constant.RETURN_FAILURE)){
						return ResponseUtil.unifyFailReturn(obj);
					}
					else{
						obj.put("retCode", Constant.RETURN_SUCCESS);
						obj.put("retMsg", "success");
						return ResponseUtil.unifySuccessReturn(obj);
					}
				}
				else{
					jsonBody.put("retCode", Constant.RETURN_EMPTY);
					jsonBody.put("retMsg", "getPrize empty");
				}
			}
			catch(Exception e){
				ExceptionLogger.LoggerInfo(requestSerial, "", "PromotionController getPrize", e);
			}
		}
		else{
			jsonBody.put("retCode", Constant.PARAMS_NOTNULL);
			jsonBody.put("retMsg", "param empty");
		}
		
		BusinessLogger.LoggerInfo(requestSerial, "tic", "phoneGameLuckDraw", false, jsonBody.toJSONString(), String.valueOf(new Date().getTime()));
		return ResponseUtil.unifyFailReturn(jsonBody);
	}	
	
	/**
	 * 获取中奖记录
	 * @param memberId
	 * @param promotionId
	 * @param tOperatorId
	 * @param countryNo
	 * @param requestSerial
	 * @return
	 */
	@RequestMapping(value = "/getLuckyList", method = RequestMethod.POST)
	@ResponseBody
	public String getLuckyList(
			@RequestParam(value = "memberId", defaultValue = "") String memberId,
			@RequestParam(value = "promotionId", defaultValue = "") String promotionId,
			@RequestParam(value = "tOperatorId", defaultValue = "") String tOperatorId,
			@RequestParam(value = "countryNo", defaultValue = "") String countryNo,
			@RequestParam(value = "requestSerial", defaultValue = "") String requestSerial,
			@RequestParam(value = "startNum", defaultValue = "0") String startNum,
			@RequestParam(value = "endNum", defaultValue = "0") String endNum) {
		BusinessLogger.LoggerInfo(requestSerial, "tic", "getLuckyList", true, memberId, String.valueOf(new Date().getTime()));
		JSONObject jsonBody = new JSONObject();
		if(StringUtil.isNotNullOrEmpty(promotionId)){
			try{
				JSONObject param = new JSONObject();
				param.put("memberId", memberId);
				param.put("promotionId", promotionId);
				param.put("countryNo", countryNo);
				param.put("tOperatorId", tOperatorId);
				param.put("requestSerial", requestSerial);
				param.put("startNum", startNum);
				param.put("endNum", endNum);
				
				TraceLogger.debugInterface("tic", "tsc", "getLuckyList", Constant.ACTION_IN, param.toJSONString());
				String jsonRet = dubboSupport.getPromotionManager().getLuckyList(param.toJSONString());
				TraceLogger.debugInterface("tsc", "tic", "getLuckyList", Constant.ACTION_OUT, jsonRet);
				
				if(StringUtil.isNotNullOrEmpty(jsonRet)){
					JSONArray obj = JSONArray.parseArray(jsonRet);
					jsonBody.put("retCode", Constant.RETURN_SUCCESS);
					jsonBody.put("retMsg", "success");
					jsonBody.put("record", obj);
					return ResponseUtil.unifySuccessReturn(jsonBody);					
				}
				else{
					jsonBody.put("retCode", Constant.RETURN_EMPTY);
					jsonBody.put("retMsg", "getLuckyList empty");
				}
			}
			catch(Exception e){
				ExceptionLogger.LoggerInfo(requestSerial, "", "PromotionController getLuckyList", e);
			}
		}
		else{
			jsonBody.put("retCode", Constant.PARAMS_NOTNULL);
			jsonBody.put("retMsg", "param empty");
		}
		
		BusinessLogger.LoggerInfo(requestSerial, "tic", "getLuckyList", false, jsonBody.toJSONString(), String.valueOf(new Date().getTime()));
		return ResponseUtil.unifyFailReturn(jsonBody);
	}
	
	/**
	 * 获取中奖记录
	 * @param memberId
	 * @param promotionId
	 * @param tOperatorId
	 * @param countryNo
	 * @param requestSerial
	 * @return
	 */
	@RequestMapping(value = "/saveSpeedRecord", method = RequestMethod.POST)
	@ResponseBody
	public String saveSpeedRecord(
			@RequestParam(value = "memberId", defaultValue = "") String memberId,
			@RequestParam(value = "promotionId", defaultValue = "") String promotionId,
			@RequestParam(value = "tOperatorId", defaultValue = "") String tOperatorId,
			@RequestParam(value = "countryNo", defaultValue = "") String countryNo,
			@RequestParam(value = "requestSerial", defaultValue = "") String requestSerial,
			@RequestParam(value = "score", defaultValue = "0") String score,
			@RequestParam(value = "startNum", defaultValue = "0") String startNum,
			@RequestParam(value = "endNum", defaultValue = "0") String endNum) {
		BusinessLogger.LoggerInfo(requestSerial, "tic", "saveSpeedRecord", true, memberId, String.valueOf(new Date().getTime()));
		JSONObject jsonBody = new JSONObject();
		if(StringUtil.isNotNullOrEmpty(memberId,promotionId)){
			try{
				JSONObject param = new JSONObject();
				param.put("memberId", memberId);
				param.put("promotionId", promotionId);
				param.put("countryNo", countryNo);
				param.put("tOperatorId", tOperatorId);
				param.put("requestSerial", requestSerial);
				param.put("score", score);
				param.put("startNum", startNum);
				param.put("endNum", endNum);
				
				TraceLogger.debugInterface("tic", "tsc", "saveSpeedRecord", Constant.ACTION_IN, param.toJSONString());
				String jsonRet = dubboSupport.getPromotionManager().saveSpeedRecord(param.toJSONString());
				TraceLogger.debugInterface("tsc", "tic", "saveSpeedRecord", Constant.ACTION_OUT, jsonRet);
				
				if(StringUtil.isNotNullOrEmpty(jsonRet)){
					return ResponseUtil.unifySuccessReturn(JSONObject.parseObject(jsonRet));					
				}
				else{
					jsonBody.put("retCode", Constant.RETURN_EMPTY);
					jsonBody.put("retMsg", "saveSpeedRecord empty");
				}
			}
			catch(Exception e){
				ExceptionLogger.LoggerInfo(requestSerial, "", "PromotionController saveSpeedRecord", e);
			}
		}
		else{
			jsonBody.put("retCode", Constant.PARAMS_NOTNULL);
			jsonBody.put("retMsg", "param empty");
		}
		
		BusinessLogger.LoggerInfo(requestSerial, "tic", "saveSpeedRecord", false, jsonBody.toJSONString(), String.valueOf(new Date().getTime()));
		return ResponseUtil.unifyFailReturn(jsonBody);
	}
	
	/**
	 * 保存收件人信息
	 * @param memberId
	 * @param promotionId
	 * @param tOperatorId
	 * @param countryNo
	 * @param requestSerial
	 * @return
	 */
	@RequestMapping(value = "/saveReceiverInfo", method = RequestMethod.POST)
	@ResponseBody
	public String saveReceiverInfo(
			@RequestParam(value = "memberId", defaultValue = "") String memberId,
			@RequestParam(value = "tOperatorId", defaultValue = "") String tOperatorId,
			@RequestParam(value = "countryNo", defaultValue = "") String countryNo,
			@RequestParam(value = "requestSerial", defaultValue = "") String requestSerial,
			@RequestParam(value = "receiverName", defaultValue = "") String receiverName,
			@RequestParam(value = "gender", defaultValue = "") String gender,
			@RequestParam(value = "identityCard", defaultValue = "") String identityCard,
			@RequestParam(value = "email", defaultValue = "") String email,
			@RequestParam(value = "address", defaultValue = "") String address,
			@RequestParam(value = "age", defaultValue = "") String age,
			@RequestParam(value = "receiverid", defaultValue = "") String receiverid,
			@RequestParam(value = "remark", defaultValue = "") String remark
			) {
		BusinessLogger.LoggerInfo(requestSerial, "tic", "saveReceiverInfo", true, memberId, String.valueOf(new Date().getTime()));
		JSONObject jsonBody = new JSONObject();
		if(StringUtil.isNotNullOrEmpty(memberId)){
			try{
				JSONObject param = new JSONObject();
				param.put("memberId", memberId);
				param.put("countryNo", countryNo);
				param.put("tOperatorId", tOperatorId);
				param.put("requestSerial", requestSerial);
				param.put("receiverName", receiverName);
				param.put("gender", gender);
				param.put("identityCard", identityCard);
				param.put("email", email);
				param.put("address", address);
				param.put("age", "");
				param.put("receiverid", receiverid);
				param.put("remark", remark);
				
				TraceLogger.debugInterface("tic", "tsc", "saveReceiverInfo", Constant.ACTION_IN, param.toJSONString());
				String jsonRet = dubboSupport.getPromotionManager().saveReceiverInfo(param.toJSONString());
				TraceLogger.debugInterface("tsc", "tic", "saveReceiverInfo", Constant.ACTION_OUT, jsonRet);
				
				if(StringUtil.isNotNullOrEmpty(jsonRet)){
					return ResponseUtil.unifySuccessReturn(JSONObject.parseObject(jsonRet));					
				}
				else{
					jsonBody.put("retCode", Constant.RETURN_EMPTY);
					jsonBody.put("retMsg", "saveReceiverInfo empty");
				}
			}
			catch(Exception e){
				ExceptionLogger.LoggerInfo(requestSerial, "", "PromotionController saveReceiverInfo", e);
			}
		}
		else{
			jsonBody.put("retCode", Constant.PARAMS_NOTNULL);
			jsonBody.put("retMsg", "param empty");
		}
		
		BusinessLogger.LoggerInfo(requestSerial, "tic", "saveReceiverInfo", false, jsonBody.toJSONString(), String.valueOf(new Date().getTime()));
		return ResponseUtil.unifyFailReturn(jsonBody);
	}
	
	@RequestMapping(value = "/updateManOrRobotInfo", method = RequestMethod.POST)
	@ResponseBody
	public String updateManOrRobotInfo(
			@RequestParam(value = "tOperatorId", defaultValue = "") String tOperatorId,
			@RequestParam(value = "countryNo", defaultValue = "") String countryNo,
			@RequestParam(value = "requestSerial", defaultValue = "") String requestSerial,
			@RequestParam(value = "shipId", defaultValue = "") String shipId,
			@RequestParam(value = "status", defaultValue = "") String status,
			@RequestParam(value = "promotionprizeid", defaultValue = "") String promotionprizeid
			) {
		BusinessLogger.LoggerInfo(requestSerial, "tic", "updateManOrRobotInfo", true, shipId, String.valueOf(new Date().getTime()));
		JSONObject jsonBody = new JSONObject();
		if(StringUtil.isNotNullOrEmpty(shipId)){
			try{
				JSONObject param = new JSONObject();
				param.put("countryNo", countryNo);
				param.put("tOperatorId", tOperatorId);
				param.put("requestSerial", requestSerial);
				param.put("shipId", shipId);
				param.put("status", status);
				param.put("promotionprizeid", promotionprizeid);
				
				TraceLogger.debugInterface("tic", "tsc", "updateManOrRobotInfo", Constant.ACTION_IN, param.toJSONString());
				String jsonRet = dubboSupport.getPromotionManager().updateManOrRobotInfo(param.toJSONString());
				TraceLogger.debugInterface("tsc", "tic", "updateManOrRobotInfo", Constant.ACTION_OUT, jsonRet);
				
				if(StringUtil.isNotNullOrEmpty(jsonRet)){
					return ResponseUtil.unifySuccessReturn(JSONObject.parseObject(jsonRet));					
				}
				else{
					jsonBody.put("retCode", Constant.RETURN_EMPTY);
					jsonBody.put("retMsg", "updateManOrRobotInfo empty");
				}
			}
			catch(Exception e){
				ExceptionLogger.LoggerInfo(requestSerial, "", "PromotionController updateManOrRobotInfo", e);
			}
		}
		else{
			jsonBody.put("retCode", Constant.PARAMS_NOTNULL);
			jsonBody.put("retMsg", "param empty");
		}
		
		BusinessLogger.LoggerInfo(requestSerial, "tic", "updateManOrRobotInfo", false, jsonBody.toJSONString(), String.valueOf(new Date().getTime()));
		return ResponseUtil.unifyFailReturn(jsonBody);
	}
	
	/**
	 * 获取收件人
	 * @param memberId
	 * @param promotionId
	 * @param tOperatorId
	 * @param countryNo
	 * @param requestSerial
	 * @return
	 */
	@RequestMapping(value = "/getReceiverInfo", method = RequestMethod.POST)
	@ResponseBody
	public String getReceiverInfo(
			@RequestParam(value = "memberId", defaultValue = "") String memberId,
			@RequestParam(value = "tOperatorId", defaultValue = "") String tOperatorId,
			@RequestParam(value = "countryNo", defaultValue = "") String countryNo,
			@RequestParam(value = "requestSerial", defaultValue = "") String requestSerial) {
		BusinessLogger.LoggerInfo(requestSerial, "tic", "getReceiverInfo", true, memberId, String.valueOf(new Date().getTime()));
		JSONObject jsonBody = new JSONObject();
		if(StringUtil.isNotNullOrEmpty(memberId)){
			try{
				JSONObject param = new JSONObject();
				param.put("memberId", memberId);
				param.put("countryNo", countryNo);
				param.put("tOperatorId", tOperatorId);
				param.put("requestSerial", requestSerial);
				
				TraceLogger.debugInterface("tic", "tsc", "getReceiverInfo", Constant.ACTION_IN, param.toJSONString());
				String jsonRet = dubboSupport.getPromotionManager().getReceiverInfo(param.toJSONString());
				TraceLogger.debugInterface("tsc", "tic", "getReceiverInfo", Constant.ACTION_OUT, jsonRet);
				
				if(StringUtil.isNotNullOrEmpty(jsonRet)){
					JSONObject obj = JSONObject.parseObject(jsonRet);
					obj.put("retCode", Constant.RETURN_SUCCESS);
					obj.put("retMsg", "success");
					return ResponseUtil.unifySuccessReturn(obj);					
				}
				else{
					jsonBody.put("retCode", Constant.RETURN_EMPTY);
					jsonBody.put("retMsg", "getReceiverInfo empty");
				}
			}
			catch(Exception e){
				ExceptionLogger.LoggerInfo(requestSerial, "", "PromotionController getReceiverInfo", e);
			}
		}
		else{
			jsonBody.put("retCode", Constant.PARAMS_NOTNULL);
			jsonBody.put("retMsg", "param empty");
		}
		
		BusinessLogger.LoggerInfo(requestSerial, "tic", "getReceiverInfo", false, jsonBody.toJSONString(), String.valueOf(new Date().getTime()));
		return ResponseUtil.unifyFailReturn(jsonBody);
	}
	
	/**
	 * 获取每天剩余抽奖次数
	 * @param memberId
	 * @param tOperatorId
	 * @param countryNo
	 * @param requestSerial
	 * @return
	 */
	@RequestMapping(value = "/getRestOnedaytimes", method = RequestMethod.POST)
	@ResponseBody
	public String getRestOnedaytimes(
			@RequestParam(value = "memberId", defaultValue = "") String memberId,
			@RequestParam(value = "tOperatorId", defaultValue = "") String tOperatorId,
			@RequestParam(value = "countryNo", defaultValue = "") String countryNo,
			@RequestParam(value = "requestSerial", defaultValue = "") String requestSerial,
			@RequestParam(value = "promotionId", defaultValue = "") String promotionId
			) {
		BusinessLogger.LoggerInfo(requestSerial, "tic", "getRestOnedaytimes", true, memberId, String.valueOf(new Date().getTime()));
		JSONObject jsonBody = new JSONObject();
		if(StringUtil.isNotNullOrEmpty(memberId)){
			try{
				JSONObject param = new JSONObject();
				param.put("memberId", memberId);
				param.put("countryNo", countryNo);
				param.put("tOperatorId", tOperatorId);
				param.put("requestSerial", requestSerial);
				param.put("promotionId", promotionId);
				
				TraceLogger.debugInterface("tic", "tsc", "getRestOnedaytimes", Constant.ACTION_IN, param.toJSONString());
				String jsonRet = dubboSupport.getPromotionManager().getRestOnedaytimes(param.toJSONString());
				TraceLogger.debugInterface("tsc", "tic", "getRestOnedaytimes", Constant.ACTION_OUT, jsonRet);
				
				if(StringUtil.isNotNullOrEmpty(jsonRet)){
					JSONObject obj = JSONObject.parseObject(jsonRet);
					obj.put("retCode", Constant.RETURN_SUCCESS);
					obj.put("retMsg", "success");
					return ResponseUtil.unifySuccessReturn(obj);					
				}
				else{
					jsonBody.put("retCode", Constant.RETURN_EMPTY);
					jsonBody.put("retMsg", "getRestOnedaytimes empty");
				}
			}
			catch(Exception e){
				ExceptionLogger.LoggerInfo(requestSerial, "", "PromotionController getRestOnedaytimes", e);
			}
		}
		else{
			jsonBody.put("retCode", Constant.PARAMS_NOTNULL);
			jsonBody.put("retMsg", "param empty");
		}
		
		BusinessLogger.LoggerInfo(requestSerial, "tic", "getRestOnedaytimes", false, jsonBody.toJSONString(), String.valueOf(new Date().getTime()));
		return ResponseUtil.unifyFailReturn(jsonBody);
	}
	
	/**
	 * 获取我的中奖记录
	 * @param memberId
	 * @param promotionId
	 * @param tOperatorId
	 * @param countryNo
	 * @param requestSerial
	 * @return
	 */
	@RequestMapping(value = "/getMyLuckyList", method = RequestMethod.POST)
	@ResponseBody
	public String getMyLuckyList(
			@RequestParam(value = "memberId", defaultValue = "") String memberId,
			@RequestParam(value = "tOperatorId", defaultValue = "") String tOperatorId,
			@RequestParam(value = "countryNo", defaultValue = "") String countryNo,
			@RequestParam(value = "requestSerial", defaultValue = "") String requestSerial) {
		BusinessLogger.LoggerInfo(requestSerial, "tic", "getMyLuckyList", true, memberId, String.valueOf(new Date().getTime()));
		JSONObject jsonBody = new JSONObject();
		if(StringUtil.isNotNullOrEmpty(memberId)){
			try{
				JSONObject param = new JSONObject();
				param.put("memberId", memberId);
				param.put("countryNo", countryNo);
				param.put("tOperatorId", tOperatorId);
				param.put("requestSerial", requestSerial);
				
				TraceLogger.debugInterface("tic", "tsc", "getMyLuckyList", Constant.ACTION_IN, param.toJSONString());
				String jsonRet = dubboSupport.getPromotionManager().getMyLuckyList(param.toJSONString());
				TraceLogger.debugInterface("tsc", "tic", "getMyLuckyList", Constant.ACTION_OUT, jsonRet);
				
				if(StringUtil.isNotNullOrEmpty(jsonRet)){
					JSONArray obj = JSONArray.parseArray(jsonRet);
					jsonBody.put("retCode", Constant.RETURN_SUCCESS);
					jsonBody.put("retMsg", "success");
					jsonBody.put("record", obj);
					return ResponseUtil.unifySuccessReturn(jsonBody);					
				}
				else{
					jsonBody.put("retCode", Constant.RETURN_EMPTY);
					jsonBody.put("retMsg", "getMyLuckyList empty");
				}
			}
			catch(Exception e){
				ExceptionLogger.LoggerInfo(requestSerial, "", "PromotionController getMyLuckyList", e);
			}
		}
		else{
			jsonBody.put("retCode", Constant.PARAMS_NOTNULL);
			jsonBody.put("retMsg", "param empty");
		}
		
		BusinessLogger.LoggerInfo(requestSerial, "tic", "getMyLuckyList", false, jsonBody.toJSONString(), String.valueOf(new Date().getTime()));
		return ResponseUtil.unifyFailReturn(jsonBody);
	}
	
	
	/**
	 * 判断抓手机游戏得分是否在档位中
	 * @param memberId
	 * @param promotionId
	 * @param tOperatorId
	 * @param userLimits
	 * @param countryNo
	 * @param requestSerial
	 * @return
	 */
	@RequestMapping(value = "/judgeLimits", method = RequestMethod.POST)
	@ResponseBody
	public String judgeLimits(
			@RequestParam(value = "memberId", defaultValue = "") String memberId,
			@RequestParam(value = "promotionId", defaultValue = "") String promotionId,
			@RequestParam(value = "tOperatorId", defaultValue = "") String tOperatorId,
			@RequestParam(value = "countryNo", defaultValue = "") String countryNo,
			@RequestParam(value = "scoreNum", defaultValue = "") String scoreNum,
			@RequestParam(value = "requestSerial", defaultValue = "") String requestSerial) {
		BusinessLogger.LoggerInfo(requestSerial, "tic", "judgeLimits", true, memberId, String.valueOf(new Date().getTime()));
		JSONObject jsonBody = new JSONObject();
		if(StringUtil.isNotNullOrEmpty(memberId)){
			try{
				JSONObject param = new JSONObject();
				param.put("memberId", memberId);
				param.put("countryNo", countryNo);
				param.put("tOperatorId", tOperatorId);
				param.put("requestSerial", requestSerial);
				param.put("scoreNum", scoreNum);
				param.put("promotionId", promotionId);
				
				TraceLogger.debugInterface("tic", "tsc", "judgeLimits", Constant.ACTION_IN, param.toJSONString());
				String jsonRet = dubboSupport.getPromotionManager().judgeLimits(param.toJSONString());
				TraceLogger.debugInterface("tsc", "tic", "judgeLimits", Constant.ACTION_OUT, jsonRet);
				
				if(StringUtil.isNotNullOrEmpty(jsonRet)){
					JSONObject obj = JSONObject.parseObject(jsonRet);
					obj.put("retCode", Constant.RETURN_SUCCESS);
					obj.put("retMsg", "success");
					return ResponseUtil.unifySuccessReturn(obj);					
				}
				else{
					jsonBody.put("retCode", Constant.RETURN_EMPTY);
					jsonBody.put("retMsg", "getJudgeLimitsReturn empty");
				}
			}
			catch(Exception e){
				ExceptionLogger.LoggerInfo(requestSerial, "", "PromotionController judgeLimits", e);
			}
		}
		else{
			jsonBody.put("retCode", Constant.PARAMS_NOTNULL);
			jsonBody.put("retMsg", "param empty");
		}
		
		BusinessLogger.LoggerInfo(requestSerial, "tic", "judgeLimits", false, jsonBody.toJSONString(), String.valueOf(new Date().getTime()));
		return ResponseUtil.unifyFailReturn(jsonBody);
	}
	
	
	
	/**
	 * 获取跳转抓游戏页面信息
	 * @param promotionId
	 * @param tOperatorId
	 * @param countryNo
	 * @param requestSerial
	 * @return
	 */
	@RequestMapping(value = "/getCompetitionInfo", method = RequestMethod.POST)
	@ResponseBody
	public String getCompetitionInfo(
			@RequestParam(value = "memberId", defaultValue = "") String memberId,
			@RequestParam(value = "promotionId", defaultValue = "") String promotionId,
			@RequestParam(value = "tOperatorId", defaultValue = "") String tOperatorId,
			@RequestParam(value = "countryNo", defaultValue = "") String countryNo,
			@RequestParam(value = "requestSerial", defaultValue = "") String requestSerial,
			@RequestParam(value = "startNum", defaultValue = "0") String startNum,
			@RequestParam(value = "endNum", defaultValue = "0") String endNum) {
		BusinessLogger.LoggerInfo(requestSerial, "tic", "getCompetitionInfo", true, memberId, String.valueOf(new Date().getTime()));
		JSONObject jsonBody = new JSONObject();
		if(StringUtil.isNotNullOrEmpty(memberId,promotionId)){
			String remark = "PromotionController getPromotionPrize";
			try{
				JSONObject param = new JSONObject();
				param.put("memberId", memberId);
				param.put("promotionId", promotionId);
				param.put("countryNo", countryNo);
				param.put("tOperatorId", tOperatorId);
				param.put("requestSerial", requestSerial);
				param.put("startNum", startNum);
				param.put("endNum", endNum);
				boolean flag1 = false; 
				TraceLogger.debugInterface("tic", "tsc", "getPromotionPrize", Constant.ACTION_IN, param.toJSONString());
				String jsonRet = dubboSupport.getPromotionManager().getPromotionPrize(param.toJSONString());
				TraceLogger.debugInterface("tsc", "tic", "getPromotionPrize", Constant.ACTION_OUT, jsonRet);
				
				if(StringUtil.isNotNullOrEmpty(jsonRet)){
					jsonBody.put("retCode", Constant.RETURN_SUCCESS);
					jsonBody.put("retMsg", "success");
					jsonBody.put("promotionPrizeInfo", JSONObject.parseObject(jsonRet));
					flag1 = true;
				}
				else{
					jsonBody.put("retCode", Constant.RETURN_EMPTY);
					jsonBody.put("retMsg", "getPromotionList empty");
				}
				boolean flag2 = false; 
				remark = "PromotionController getLuckList";
				TraceLogger.debugInterface("tic", "tsc", "getLuckyList", Constant.ACTION_IN, param.toJSONString());
				String jsonLuck = dubboSupport.getPromotionManager().getLuckyList(param.toJSONString());
				TraceLogger.debugInterface("tsc", "tic", "getLuckyList", Constant.ACTION_OUT, jsonRet);
				
				if(StringUtil.isNotNullOrEmpty(jsonLuck)){
					jsonBody.put("retCode", Constant.RETURN_SUCCESS);
					jsonBody.put("retMsg", "success");
					jsonBody.put("luckListInfo", JSONArray.parseArray(jsonLuck));
					flag2 = true;
				}
				else{
					jsonBody.put("retCode", Constant.RETURN_EMPTY);
					jsonBody.put("retMsg", "getPromotionList empty");
				}
				
				if(flag1 && flag2){
					return ResponseUtil.unifySuccessReturn(jsonBody);
				}
			}
			catch(Exception e){
				ExceptionLogger.LoggerInfo(requestSerial, "", remark, e);
			}
		}
		else{
			jsonBody.put("retCode", Constant.PARAMS_NOTNULL);
			jsonBody.put("retMsg", "param empty");
		}
		
		BusinessLogger.LoggerInfo(requestSerial, "tic", "getCompetitionInfo", false, jsonBody.toJSONString(), String.valueOf(new Date().getTime()));
		return ResponseUtil.unifyFailReturn(jsonBody);
	}	
}
