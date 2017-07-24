package com.flash.toolbar.facade;

import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.flash.toolbar.common.util.Constant;
import com.flash.toolbar.common.util.ExceptionLogger;
import com.flash.toolbar.common.util.ParamPropertiesUtil;
import com.flash.toolbar.common.util.RuleUtil;
import com.flash.toolbar.common.util.StringUtil;
import com.flash.toolbar.common.util.TraceLogger;
import com.flash.toolbar.mapper.CzPayResponseMapper;
import com.flash.toolbar.mapper.CzReloadOrderMapper;
import com.flash.toolbar.model.CzPayResponse;
import com.flash.toolbar.model.CzReloadOrder;
import com.flash.toolbar.utils.SHA1HashSignature;

@Controller
@RequestMapping(value = "/reloadManagerInterface")
public class ReloadManagerInterface {

	@Autowired
	private CzPayResponseMapper czPayResponseMapper;
	
	@Autowired
	private CzReloadOrderMapper czReloadOrderMapper;
	
	@RequestMapping(value = "/changeOrderStatus")
	@ResponseBody
	public void changeStatus(HttpServletRequest request,HttpServletResponse response){
		try{
		String storeId = request.getParameter("storeId");
		String orderId = request.getParameter("orderId");
		String transDate = request.getParameter("transDate");
		String totalAmount = request.getParameter("totalAmount");
		String msisdn = request.getParameter("msisdn");
		String balance = request.getParameter("balance");
		String suspendDate = request.getParameter("suspendDate");
		String returnCode = request.getParameter("returnCode");
		String reasonCode = request.getParameter("reasonCode");
		String reasonDesc = request.getParameter("reasonDesc");
		String referId = request.getParameter("referId");
		String signature = request.getParameter("signature");
		String paymentMethod = request.getParameter("paymentMethod");
		String cardPaddedNum = request.getParameter("cardPaddedNum");
		String authCode = request.getParameter("authCode");
		String token = request.getParameter("token");
		String transactionId = request.getParameter("transactionId");
		String accountNum = request.getParameter("accountNum");
		
		StringBuffer responseStr = new StringBuffer();
		responseStr.append(storeId);
		responseStr.append("|");
		responseStr.append(orderId);
		responseStr.append("|");
		responseStr.append(transDate);
		responseStr.append("|");
		responseStr.append(totalAmount);
		responseStr.append("|");
		responseStr.append(msisdn);
		responseStr.append("|");
		responseStr.append(balance);
		responseStr.append("|");
		responseStr.append(suspendDate);
		responseStr.append("|");
		responseStr.append(returnCode);
		responseStr.append("|");
		responseStr.append(reasonCode);
		responseStr.append("|");
		responseStr.append(reasonDesc);
		responseStr.append("|");
		responseStr.append(referId);
		responseStr.append("|");
		responseStr.append(signature);
		responseStr.append("|");
		responseStr.append(paymentMethod);
		responseStr.append("|");
		responseStr.append(cardPaddedNum);
		responseStr.append("|");
		responseStr.append(authCode);
		responseStr.append("|");
		responseStr.append(token);
		responseStr.append("|");
		responseStr.append(transactionId);
		responseStr.append("|");
		responseStr.append(accountNum);
		responseStr.append("|");
		TraceLogger.debugInterface("celcom", "tsc", "changeOrderStatus", Constant.ACTION_OUT, responseStr.toString());
		
		String s = storeId+ParamPropertiesUtil.getReloadPassword()+orderId+returnCode;
		SHA1HashSignature sha1Hash = new SHA1HashSignature();
		String rtn = sha1Hash.generateSHA(s);
		if(!signature.equals(rtn)){
			return;
		}
		
		CzPayResponse czPayResponse = new CzPayResponse();
		String czPayResponseId = RuleUtil.generateUUID();
		czPayResponse.setId(czPayResponseId);
		czPayResponse.setStoreid(storeId);
		czPayResponse.setOrderid(orderId);
		czPayResponse.setTransdate(transDate);
		if(!StringUtil.isNotNullOrEmpty(totalAmount)){
			totalAmount = "0";
		}
		czPayResponse.setTotalamount(new BigDecimal(totalAmount));
		czPayResponse.setMsisdn(msisdn);
		if(!StringUtil.isNotNullOrEmpty(balance)){
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
		if(!StringUtil.isNotNullOrEmpty(paymentMethod)){
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
	}
	
	public static void main(String []args){
		String storeId = "xpax";
		String password = "1234";
		String orderId = "20170117101124843";
		String returnCode = "1";
		String s = storeId+password+orderId+returnCode;
		SHA1HashSignature sha1Hash = new SHA1HashSignature();
		String rtn = sha1Hash.generateSHA(s);
		System.out.println(rtn);
	}
}
