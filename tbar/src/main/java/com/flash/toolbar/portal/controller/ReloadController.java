package com.flash.toolbar.portal.controller;

import com.alibaba.fastjson.JSONObject;
import com.flash.common.log.BusinessLogger;
import com.flash.common.log.TraceLogger;
import com.flash.common.util.*;
import com.flash.toolbar.portal.bean.ServiceUrlBean;
import com.flash.toolbar.service.SessionContext;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "/reload")
public class ReloadController {
	@Autowired
	private ServiceUrlBean serviceUrlBean;	
	
	@Autowired
	private SessionContext sessionContext;

	@RequestMapping(value = "/prepaidPayment")
	@ResponseBody
	public void prepaidPayment(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "suid",defaultValue = "") String suid,
			@RequestParam(value = "totalAmount",defaultValue = "") String totalAmount,
			@RequestParam(value = "paymentMethod",defaultValue = "") String paymentMethod,
			@RequestParam(value = "msisdn",defaultValue = "") String msisdn,
			@RequestParam(value = "mima",defaultValue = "") String mima,
			@RequestParam(value = "storeId",defaultValue = "") String storeId,
			@RequestParam(value = "storeFlag",defaultValue = "") String storeFlag
			) {
		String requestSerial = RandomString.getRandomNumber();
		
		BusinessLogger.LoggerInfo(requestSerial, "tbar", "prepaidPayment", true, null, String.valueOf(new Date().getTime()));
		String tOperatorId = "", userId = "", countryNo = "", timeZone="", serviceType="";
		if(StringUtil.isNotNullOrEmpty(suid)){			
			ToolbarSession session = sessionContext.getSession(suid);
			tOperatorId = session.gettOperatorId();
			userId = session.getMemberId();
	        countryNo = session.getCountryNo();
	        timeZone = session.getTimeZone();
	        serviceType = session.getServiceType();
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("totalAmount=").append(totalAmount);
		sb.append("&tOperatorId=").append(tOperatorId);
        sb.append("&countryNo=").append(countryNo);
        sb.append("&msisdn=").append(msisdn);
        sb.append("&paymentMethod=").append(paymentMethod);        
		sb.append("&requestSerial=").append(requestSerial);
		sb.append("&mima=").append(mima);
		sb.append("&storeId=").append(storeId);
		sb.append("&storeFlag=").append(storeFlag);
		sb.append("&serviceType=").append(serviceType);
        
		response.setContentType("text/plain");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		try {
			String responseStr = HttpUtil.sendPost(serviceUrlBean.getSaveReloadOrder(), sb.toString(),requestSerial);
			
			//tbar 用户反馈业务日志记录结束
			BusinessLogger.LoggerInfo(requestSerial, "tbar", "prepaidPayment", false, responseStr, String.valueOf(new Date().getTime()));
			
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("result", responseStr);
			
			String jsonpCallback = request.getParameter("jsonpCallback");
			String resultStr = jsonpCallback + "(" + resultJSON.toJSONString() + ")";
		
			PrintWriter	out = response.getWriter();

			out.println(resultJSON);
//			out.println(resultStr);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping(value="/turnReloadResult")
	@ResponseBody
	public ModelAndView turnReloadResult(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "suid",defaultValue = "") String suid){			
		Map<String,String> map = new HashMap<String,String>();
		ModelAndView modelAndView = new ModelAndView();
		String reloadStatus="2";
		String reloadPackage = "Credit";
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
			String storeFlag = request.getParameter("storeFlag");
			StringBuffer log = new StringBuffer();
			log.append(storeId);
			log.append("|");
			log.append(orderId);
			log.append("|");
			log.append(transDate);
			log.append("|");
			log.append(totalAmount);
			log.append("|");
			log.append(msisdn);
			log.append("|");
			log.append(balance);
			log.append("|");
			log.append(suspendDate);
			log.append("|");
			log.append(returnCode);
			log.append("|");
			log.append(reasonCode);
			log.append("|");
			log.append(reasonDesc);
			log.append("|");
			log.append(referId);
			log.append("|");
			log.append(signature);
			log.append("|");
			log.append(paymentMethod);
			log.append("|");
			log.append(cardPaddedNum);
			log.append("|");
			log.append(authCode);
			log.append("|");
			log.append(token);
			log.append("|");
			log.append(transactionId);
			log.append("|");
			log.append(accountNum);
			log.append("|");
			TraceLogger.LoggerInfo(transactionId, "","turnReloadResult", "", log.toString());
			String requestSerial = RandomString.getRandomNumber();
			StringBuffer param = new StringBuffer();
			param.append("storeId=");
			param.append(storeId);
			param.append("&orderId=");
			param.append(orderId);
			param.append("&transDate=");
			param.append(transDate);
			param.append("&totalAmount=");
			param.append(totalAmount);
			param.append("&msisdn=");
			param.append(msisdn);
			param.append("&balance=");
			param.append(balance);
			param.append("&suspendDate=");
			param.append(suspendDate);
			param.append("&returnCode=");
			param.append(returnCode);
			param.append("&reasonCode=");
			param.append(reasonCode);
			param.append("&reasonDesc=");
			param.append(reasonDesc);
			param.append("&referId=");
			param.append(referId);
			param.append("&paymentMethod=");
			param.append(paymentMethod);
			param.append("&cardPaddedNum=");
			param.append(cardPaddedNum);
			param.append("&authCode=");
			param.append(authCode);
			param.append("&token=");
			param.append(token);
			param.append("&transactionId=");
			param.append(transactionId);
			param.append("&accountNum=");
			param.append(accountNum);
			param.append("&signature=");
			param.append(java.net.URLEncoder.encode(signature,"UTF-8"));
			String responseStr = HttpUtil.sendPost(serviceUrlBean.getSaveReloadResult(),param.toString(),requestSerial);
			
			if(StringUtil.isNotNullOrEmpty(returnCode)&&StringUtil.isNotNullOrEmpty(reasonCode)&&"1".equals(returnCode)&&("10000".equals(reasonCode)||"10001".equals(reasonCode)||"10002".equals(reasonCode)||"10003".equals(reasonCode)||"10004".equals(reasonCode)||"11000".equals(reasonCode)||"14000".equals(reasonCode))){
				reloadStatus="1";
			}
			if("10001".equals(reasonCode)){
				reloadPackage = "Credit";
			}else if("10004".equals(reasonCode)){
				reloadPackage = "Internet";
			}
			
			if(StringUtil.isNotNullOrEmpty(storeId)){
				if("t".equals(storeFlag)){
					reloadPackage = "Credit";
				}else if("x".equals(storeFlag)){
					reloadPackage = "Internet";
				}
			}
			map.put("returnCode", returnCode);
			map.put("reasonDesc", reasonDesc);
			map.put("amount", totalAmount);
			map.put("msisdn", msisdn);
		}catch(Exception e){
			
		}
//		if(StringUtil.isNotNullOrEmpty(suid)){			
//			ToolbarSession session = sessionContext.getSession(suid);
//			String tOperatorId = session.gettOperatorId();
//	        String userId = session.getMemberId();
//	        String countryNo = session.getCountryNo();
//	        String timeZone = session.getTimeZone();
//	        
//			map.put("suid", suid);
//			map.put("tOperatorId", tOperatorId);
//			map.put("userId", userId);
//			map.put("countryNo", countryNo);
//			map.put("timeZone", timeZone);
//				modelAndView = new ModelAndView("toolbar/reloadresult",map);
//		}
		map.put("reloadStatus", reloadStatus);
		map.put("reloadPackage", reloadPackage);
		if("1".equals(reloadStatus)){
			modelAndView = new ModelAndView("toolbar/preReloadresult",map);
		}else{
			modelAndView = new ModelAndView("toolbar/preReloadfailed",map);
		}
		return modelAndView;
	}
	@RequestMapping(value="/turnPostReloadResult")
	@ResponseBody
	public ModelAndView turnPostReloadResult(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "suid",defaultValue = "") String suid){
		Map<String,String> map = new HashMap<String,String>();
		ModelAndView modelAndView = new ModelAndView();
		String reloadStatus="2";
		String reloadPackage = "Credit";
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
			String storeFlag = request.getParameter("storeFlag");
			StringBuffer log = new StringBuffer();
			log.append(storeId);
			log.append("|");
			log.append(orderId);
			log.append("|");
			log.append(transDate);
			log.append("|");
			log.append(totalAmount);
			log.append("|");
			log.append(msisdn);
			log.append("|");
			log.append(balance);
			log.append("|");
			log.append(suspendDate);
			log.append("|");
			log.append(returnCode);
			log.append("|");
			log.append(reasonCode);
			log.append("|");
			log.append(reasonDesc);
			log.append("|");
			log.append(referId);
			log.append("|");
			log.append(signature);
			log.append("|");
			log.append(paymentMethod);
			log.append("|");
			log.append(cardPaddedNum);
			log.append("|");
			log.append(authCode);
			log.append("|");
			log.append(token);
			log.append("|");
			log.append(transactionId);
			log.append("|");
			log.append(accountNum);
			log.append("|");
			TraceLogger.LoggerDebug(transactionId, "","turnReloadResult", "", log.toString());
			String requestSerial = RandomString.getRandomNumber();
			StringBuffer param = new StringBuffer();
			param.append("storeId=");
			param.append(storeId);
			param.append("&orderId=");
			param.append(orderId);
			param.append("&transDate=");
			param.append(transDate);
			param.append("&totalAmount=");
			param.append(totalAmount);
			param.append("&msisdn=");
			param.append(msisdn);
			param.append("&balance=");
			param.append(balance);
			param.append("&suspendDate=");
			param.append(suspendDate);
			param.append("&returnCode=");
			param.append(returnCode);
			param.append("&reasonCode=");
			param.append(reasonCode);
			param.append("&reasonDesc=");
			param.append(reasonDesc);
			param.append("&referId=");
			param.append(referId);
			param.append("&paymentMethod=");
			param.append(paymentMethod);
			param.append("&cardPaddedNum=");
			param.append(cardPaddedNum);
			param.append("&authCode=");
			param.append(authCode);
			param.append("&token=");
			param.append(token);
			param.append("&transactionId=");
			param.append(transactionId);
			param.append("&accountNum=");
			param.append(accountNum);
			param.append("&signature=");
			param.append(java.net.URLEncoder.encode(signature,"UTF-8"));
			String responseStr = HttpUtil.sendPost(serviceUrlBean.getSaveReloadResult(),param.toString(),requestSerial);
			
			if(StringUtil.isNotNullOrEmpty(returnCode)&&StringUtil.isNotNullOrEmpty(reasonCode)&&"1".equals(returnCode)&&("10001".equals(reasonCode)||"10002".equals(reasonCode)||"10004".equals(reasonCode))){
				reloadStatus="1";
			}
			if("10001".equals(reasonCode)){
				reloadPackage = "Credit";
			}else if("10004".equals(reasonCode)){
				reloadPackage = "Internet";
			}

			if(StringUtil.isNotNullOrEmpty(storeId)){
				if("t".equals(storeFlag)){
					reloadPackage = "Credit";
				}else if("x".equals(storeFlag)){
					reloadPackage = "Internet";
				}
			}
			map.put("returnCode", returnCode);
			map.put("reasonDesc", reasonDesc);
			map.put("amount", totalAmount);
			map.put("msisdn", msisdn);
		}catch(Exception e){
			
		}
//		if(StringUtil.isNotNullOrEmpty(suid)){			
//			ToolbarSession session = sessionContext.getSession(suid);
//			String tOperatorId = session.gettOperatorId();
//	        String userId = session.getMemberId();
//	        String countryNo = session.getCountryNo();
//	        String timeZone = session.getTimeZone();
//	        
//			map.put("suid", suid);
//			map.put("tOperatorId", tOperatorId);
//			map.put("userId", userId);
//			map.put("countryNo", countryNo);
//			map.put("timeZone", timeZone);
//				modelAndView = new ModelAndView("toolbar/reloadresult",map);
//		}
		map.put("reloadStatus", reloadStatus);
		map.put("reloadPackage", reloadPackage);
		if("1".equals(reloadStatus)){
			modelAndView = new ModelAndView("toolbar/postReloadresult",map);
		}else{
			modelAndView = new ModelAndView("toolbar/postReloadfailed",map);
		}
		return modelAndView;
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
	private static String createSignature(String storeId,String password,String orderId,String amount){
		String rtn = null;
		String signature = storeId + password + orderId + amount;
		SHA1HashSignature sha1Hash = new SHA1HashSignature();
		rtn = sha1Hash.generateSHA(signature);
		return rtn;
	}
	
	
	

	@RequestMapping(value = "/test1")
	@ResponseBody
	public void test1(HttpServletResponse response, HttpServletRequest request) {
		System.out.println(request.getAttribute("orderId"));
		System.out.println(request.getAttribute("storeId"));
		System.out.println(request.getAttribute("transDate"));
		System.out.println(request.getAttribute("returnCode"));
		System.out.println(request.getAttribute("reasonCode"));
		System.out.println(request.getAttribute("reasonDesc"));
		System.out.println(request.getAttribute("totalAmount"));
		System.out.println(request.getAttribute("signature"));
		System.out.println(request.getAttribute("paymentMethod"));
		System.out.println(request.getAttribute("referId"));
		System.out.println(request.getAttribute("authCode"));
		System.out.println(request.getAttribute("token"));
		System.out.println(request.getAttribute("expiryDate"));
		System.out.println(request.getAttribute("cardPaddedNum"));
		System.out.println(request.getAttribute("msisdn"));
		System.out.println(request.getAttribute("balance"));
		System.out.println(request.getAttribute("suspendDate"));
	}

	private static Map<String, String> parseResponse(HttpResponse response)
			throws IOException {
		Map<String, String> map = new HashMap<String, String>();
		String responseStr = getResponseContent(response);
		System.out.println("repsonse for PG");
		System.out.println("responseStr:" + responseStr);
		List<NameValuePair> responseParams = new ArrayList<NameValuePair>();
		URLEncodedUtils
				.parse(responseParams, new Scanner(responseStr), "UTF-8");
		for (NameValuePair nvp : responseParams) {
			System.out.println("nvp:" + nvp);
			map.put(nvp.getName(), nvp.getValue());
		}
		return map;
	}

	private static String getResponseContent(HttpResponse response)
			throws IOException {
		String result = "";
		InputStream is = response.getEntity().getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line = null;
		while ((line = br.readLine()) != null) {
			result += line;
		}
		return result;
	}

	public static void main(String agrs[]) {
//		queryPayment();
//		System.out.println(createOrderId());
		String orderId = "20170105120717";
		String pas = "toolbar";
		String storeId = "toolbar";
		String amount = "1000";
		System.out.print(createSignature(storeId,pas,orderId,amount));
	}
	
//	public static void queryPayment(){
//		try {
//			DefaultHttpClient httpclient = new DefaultHttpClient();
//			HttpPost httpost = new HttpPost(
//					"https://onlinepayment.celcom.com.my/Payment-Testing/QueryPayment");
//			String orderId = "20170105120716";
//			String storeId = "xpax";
//			String transDate = "20170105120716+0800";// 20160406155813SGT
//			String pass = "xpax";
//			String totalAmount = "10.00";
//			String signature = storeId + pass + orderId
//					+ totalAmount.replace(".", "");
//			SHA1HashSignature sha1Hash = new SHA1HashSignature();
//			signature = sha1Hash.generateSHA(signature);
//			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//			nvps.add(new BasicNameValuePair("orderId", orderId));
//			nvps.add(new BasicNameValuePair("storeId", storeId));
//			nvps.add(new BasicNameValuePair("signature", signature));
//			nvps.add(new BasicNameValuePair("transDate", transDate));
//			nvps.add(new BasicNameValuePair("totalAmount", totalAmount));
//			httpost.setEntity((HttpEntity) new UrlEncodedFormEntity(nvps,
//					Consts.UTF_8));
//			System.out.println("Request:" + nvps);
//			HttpResponse response = httpclient.execute(httpost);
//			System.out.println("response:" + response);
//			Map<String, String> responseParams = parseResponse(response);
//			System.out.println("Return Code : "
//					+ responseParams.get("returnCode"));
//			System.out.println("Reason Desc : "
//					+ responseParams.get("reasonDesc"));
//			System.out.println("reason Code : "
//					+ responseParams.get("reasonCode"));
//			System.out.println("cardPaddedNum:"
//					+ responseParams.get("cardPaddedNum"));
//			System.out.println("singnature:" + responseParams.get("signature"));
//		} catch (IOException ex) {
//		}
//	}

	public void sentReconciliation() {
		HttpClient httpclient = new DefaultHttpClient();
		httpclient = WebClientDevWrapper.wrapClient(httpclient);
		try {
			HttpPost httppost = new HttpPost("<url_to_connect>");
			FileBody bin = new FileBody(new File("<file location>"));
			StringBody storeId = null;
			StringBody password = null;
			StringBody filename = null;
			try {
				storeId = new StringBody("<storeId>");
				password = new StringBody("<password>");
			    filename = new StringBody("<filename>");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			MultipartEntity reqEntity = new MultipartEntity();
			reqEntity.addPart("file", bin);
			reqEntity.addPart("storeId", storeId);
			reqEntity.addPart("password", password);
			reqEntity.addPart("filename", filename);
			httppost.setEntity(reqEntity);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {
				InputStream content = resEntity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(content));
				String returnCode = reader.readLine(); // response form Online
														// Payment
				// do something
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				httpclient.getConnectionManager().shutdown();
			} catch (Exception ignore) {
			}
		}
	}
}
