package com.flash.toolbar.facade;

import com.alibaba.fastjson.JSONObject;
import com.flash.toolbar.common.util.*;
import com.flash.toolbar.mapper.CzPayResponseMapper;
import com.flash.toolbar.mapper.CzReloadOrderMapper;
import com.flash.toolbar.model.CzPayResponse;
import com.flash.toolbar.model.CzReloadOrder;
import com.flash.toolbar.redis.RedisOperation;
import com.flash.toolbar.service.ReloadManagerService;
import com.flash.toolbar.utils.SHA1HashSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 充值接口实现
 * @author ocean
 *
 */
@Service(value="reloadManager")
public class ReloadManagerImpl implements ReloadManager{

	@Autowired
	private ReloadManagerService reloadManagerService;
	
	@Autowired
	private CzPayResponseMapper czPayResponseMapper;
	
	@Autowired
	private CzReloadOrderMapper czReloadOrderMapper;
	
	@Autowired
	private RedisOperation redisOperation;
	
	/**
	 * 保存订单
	 */
	@Override
	public String saveOrder(String businessStr) {
		String rtn = null;
		try{
			JSONObject requestJSON = JSONObject.parseObject(businessStr);
			String requestSerial = requestJSON.getString("requestSerial");			
			//tsc套餐订购业务日志记录开始
			BusinessLogger.LoggerInfo(requestSerial, "tsc", "saveOrder", true, null, String.valueOf(new Date().getTime()));
			rtn = reloadManagerService.saveOrder(businessStr);
			//tsc套餐订购业务日志记录结束
			BusinessLogger.LoggerInfo(requestSerial, "tsc", "saveOrder", false, rtn, String.valueOf(new Date().getTime()));
		}catch(Exception e){
			ExceptionLogger.info("saveOrder Exception", e);
		}
		return rtn;
	}

	/**
	 * 改变订单状态
	 */
	@Override
	public String changeOrderStatus(String busyStr) {
		return null;
	}

	@Override
	public String saveReloadResult(String busyStr) {
		try{
			JSONObject json = (JSONObject) JSONObject.parse(busyStr);
			String storeId = json.getString("storeId");
			String orderId = json.getString("orderId");
			String transDate = json.getString("transDate");
			String totalAmount = json.getString("totalAmount");
			String msisdn = json.getString("msisdn");
			String balance = json.getString("balance");
			String suspendDate = json.getString("suspendDate");
			String returnCode = json.getString("returnCode");
			String reasonCode = json.getString("reasonCode");
			String reasonDesc = json.getString("reasonDesc");
			String referId = json.getString("referId");
			String signature = json.getString("signature");
			String paymentMethod = json.getString("paymentMethod");
			String cardPaddedNum = json.getString("cardPaddedNum");
			String authCode = json.getString("authCode");
			String token = json.getString("token");
			String transactionId = json.getString("transactionId");
			String accountNum = json.getString("accountNum");

//			ExceptionLogger.info("saveReloadResult_wangxiaorantest : " + json.toJSONString());
			try{
				if(!msisdn.startsWith("6")){//6代表马来西亚，后期需要改成动态读取
					msisdn = "6"+msisdn;
				}
//				ExceptionLogger.error("saveReloadResult_wangxiaorantest  celcom.user.balance. : " + msisdn  +"  " + redisOperation.get("celcom.user.balance."+msisdn));
//				ExceptionLogger.error("saveReloadResult_wangxiaorantest  celcom.user.volume.  :" + msisdn +"  " + redisOperation.get("celcom.user.volume."+msisdn));
				redisOperation.del("celcom.user.balance."+msisdn);
				redisOperation.del("celcom.user.volume."+msisdn);
			}catch(Exception e){
				ExceptionLogger.error("saveReloadResult: " , e );
			}
//			ExceptionLogger.info("saveReloadResult_wangxiaorantest del redis done" );

			String mima = ParamPropertiesUtil.getReloadPassword();
			if(ParamPropertiesUtil.getReloadStoreid2().equals(storeId)){
				mima = ParamPropertiesUtil.getReloadPassword2();
			}
			String s = storeId+mima+orderId+returnCode;
			SHA1HashSignature sha1Hash = new SHA1HashSignature();
			String rtn = sha1Hash.generateSHA(s);
			if(!signature.equals(rtn)){
				return null;
			}
			
			CzPayResponse czPayResponse = new CzPayResponse();
			String czPayResponseId = RuleUtil.generateUUID();
			czPayResponse.setId(czPayResponseId);
			czPayResponse.setStoreid(storeId);
			czPayResponse.setOrderid(orderId);
			czPayResponse.setTransdate(transDate);
			if(!StringUtil.isNotNullOrEmpty(totalAmount)||"null".equals(totalAmount)){
				totalAmount = "0";
			}
			czPayResponse.setTotalamount(new BigDecimal(totalAmount));
			czPayResponse.setMsisdn(msisdn);
			if(!StringUtil.isNotNullOrEmpty(balance)||"null".equals(balance)){
				balance = "0";
			}
			czPayResponse.setBalance(new BigDecimal(balance));
			czPayResponse.setSuspenddate(suspendDate);
			czPayResponse.setReturncode(returnCode);
			if(!StringUtil.isNotNullOrEmpty(reasonCode)){
				reasonCode = "0";
			}
			czPayResponse.setReasoncode(new BigDecimal(reasonCode));
			czPayResponse.setReasondesc(reasonDesc);
			czPayResponse.setReferid(referId);
			czPayResponse.setSignature(signature);
			if(!StringUtil.isNotNullOrEmpty(paymentMethod)||"null".equals(paymentMethod)){
				paymentMethod = "1";
			}
			czPayResponse.setPaymentmethod(new BigDecimal(paymentMethod));
			czPayResponse.setCardpaddednum(cardPaddedNum);
			czPayResponse.setAuthcode(authCode);
			czPayResponse.setToken(token);
			czPayResponse.setTransactionid(transactionId);
			czPayResponse.setAccountnum(accountNum);
			
			czPayResponseMapper.insertSelective(czPayResponse);
			
			
			String reloadStatus="2";
			if(StringUtil.isNotNullOrEmpty(returnCode)&&StringUtil.isNotNullOrEmpty(reasonCode)&&"1".equals(returnCode)&&("10000".equals(reasonCode)||"10001".equals(reasonCode)||"10002".equals(reasonCode)||"10003".equals(reasonCode)||"10004".equals(reasonCode)||"11000".equals(reasonCode)||"14000".equals(reasonCode))){
				reloadStatus="1";
			}
			
			CzReloadOrder czReloadOrder = new CzReloadOrder();
			czReloadOrder.setId(orderId);
			czReloadOrder.setStatus(reloadStatus);
			czReloadOrder.setModifydate(new Date());
			czReloadOrderMapper.updateByPrimaryKeySelective(czReloadOrder);
			}catch(Exception e){
				ExceptionLogger.debug(e);
			}
		return null;
	}
	
	public static void main(String []args){
		String ss = "xpaxtoolbar201702041511117565";
		String s = "TYOcJlJDgaLC0FvU3ci/gP6A SA=";
		SHA1HashSignature sha1Hash = new SHA1HashSignature();
		String rtn = sha1Hash.generateSHA(ss);
		System.out.println(rtn);
	}

}
