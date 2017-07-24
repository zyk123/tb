package com.celcom.ws;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.jersey.spi.resource.Singleton;

@Produces("application/json")
@Path("queryprofile")
@Singleton
public class ProfileRetrieve {
	
	/**
	 * 用户信息（包含预付费和后付费用户）5.1
	 * @param businessEvent
	 * @param sourceSystem
	 * @param callerNumber
	 * @return
	 */
	@GET
	@Path("customerretrieve")
	@Produces("application/json")
	@Consumes("application/json")
	public String customerretrieve(
			@PathParam("userAddress") String userAddress) {
		JSONObject reponse = new JSONObject();
		reponse.put("customerRowId", "1-IZD9YN");
		reponse.put("customerID", "870502328743");
		reponse.put("customerIDType", "New NRIC");
		
		JSONObject status = new JSONObject();
		status.put("status", "OK");
		status.put("errorCode", "OK");
		status.put("errorMessage", "OK");
		
		JSONArray staarr = new JSONArray();
		staarr.add(status);
		
		reponse.put("headerCustomerProfile", staarr);
		
		JSONObject service = new JSONObject();
		service.put("mobileNumber", "0192888586");
		service.put("pre_Pos_Indicator", "Prepaid");//Prepaid Postpaid
		JSONArray services = new JSONArray();
		services.add(service);
		JSONObject serviceObj = new JSONObject();
		serviceObj.put("services", services);
		
		reponse.put("listOfServices", serviceObj);
		
		JSONObject result = new JSONObject();
		result.put("outputCPResp", reponse);
		return result.toJSONString();
	}

	/**
	 * 用户已订购套餐 6.2
	 * @param businessEvent
	 * @param sourceSystem
	 * @param msisdn
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	@GET
	@Path("quotausagefup")
	@Produces("application/json")
	@Consumes("application/json")
	public String quotausagefup(
			@PathParam("businessEvent") String businessEvent,
			@PathParam("sourceSystem") String sourceSystem,
			@PathParam("msisdn") String msisdn,
			@PathParam("dateFrom") Date dateFrom,
			@PathParam("dateTo") Date dateTo) {
		JSONObject json = new JSONObject();
		json.put("packageID", "1");
		json.put("packageName", "Monthly RM58 Internet Plan");
		json.put("startTime", "11/28/2014 10:48:28");
		json.put("endTime", "12/28/2014 00:00:00");
		json.put("subscribedSpeed", "");
		json.put("allocatedVolume", 2048);
		json.put("purchasedVolume", 2048);
		json.put("volumeUsed", 1858);
		
		JSONObject json2 = new JSONObject();
		json2.put("packageID", "2");
		json2.put("packageName", "Monthly RM58 Internet Plan");
		json2.put("startTime", "11/28/2014 10:48:28");
		json2.put("endTime", "12/28/2014 00:00:00");
		json2.put("subscribedSpeed", "");
		json2.put("allocatedVolume", 1024);
		json2.put("purchasedVolume", 1024);
		json2.put("volumeUsed", 250);
		
		JSONArray array = new JSONArray();
		array.add(json);
		array.add(json2);
		
		JSONObject record = new JSONObject();
		record.put("usageInfoRecord", array);
		
		JSONObject reponse = new JSONObject();
		reponse.put("statusCode", "OK");
		reponse.put("statusDescription", "success");
		reponse.put("listOfUsageInfoRecord", record);
		
		JSONObject result = new JSONObject();
		result.put("outputQuotaUsageResp", reponse);
		return result.toJSONString();
	}

	/**
	 * 用户信息（包含预付费和后付费用户）5.2
	 * @param businessEvent
	 * @param sourceSystem
	 * @param callerNumber
	 * @return
	 */
	@GET
	@Path("profileretrieve")
	@Produces("application/json")
	@Consumes("application/json")
	public String profileretrieve(
			@PathParam("businessEvent") String businessEvent,
			@PathParam("sourceSystem") String sourceSystem,
			@PathParam("callerNumber") String callerNumber) {
		JSONObject reponse = new JSONObject();
		reponse.put("crmServiceID", "1-L4SYNJ");
		reponse.put("serviceType", "Prepaid");//Prepaid Postpaid
		reponse.put("plan", "Xpax Turbo Plan");//品牌
		
		JSONObject result = new JSONObject();
		result.put("outputSDResp", reponse);
		return result.toJSONString();
	}

	/**
	 * 用户总流量使用情况 6.3
	 * @param businessEvent
	 * @param sourceSystem
	 * @param msisdn
	 * @return
	 */
	@GET
	@Path("actquotausagefup")
	@Produces("application/json")
	@Consumes("application/json")
	public String actquotausagefup(
			@PathParam("businessEvent") String businessEvent,
			@PathParam("sourceSystem") String sourceSystem,
			@PathParam("msisdn") String msisdn) {
		JSONObject response = new JSONObject();
		response.put("statusCode", "OK");
		response.put("statusDescription", "700 Transaction success");
		response.put("VolumeUsed", 4.6);
		response.put("VolumeTotal", 6.8);
		response.put("SubscriptionStartDateTime", "12/03/2014 00:00:00");
		response.put("SubscriptionEndDateTime", "10/15/2016 00:00:00");
		
		JSONObject result = new JSONObject();
		result.put("outputActQuotaUsageResp", response);
		return result.toJSONString();
	}

	/**
	 * 预付费用户余额 7.2
	 * @param serialNumber
	 * @return
	 */
	@GET
	@Path("queryaccountbalance")
	@Produces("application/json")
	@Consumes("application/json")
	public String queryaccountbalance(
			@PathParam("serialNumber") String serialNumber) {
		JSONObject response = new JSONObject();
		response.put("daysLeft", "22");
		response.put("validity", "01/13/2015 00:00:00");
		response.put("product", "55009");
		response.put("unit", "RM");
		response.put("balance", "68");
		response.put("accountCode", "2000");
		response.put("accountType", "PrepaidBalanceSubaccount");
		response.put("responseCode", "Ok");
		response.put("responseMessage", "Operation successful.");
		
		JSONArray array = new JSONArray();
		array.add(response);
		
		JSONObject banlance = new JSONObject();
		banlance.put("prepaidAccountsBalance", array);
		
		JSONObject result = new JSONObject();
		result.put("outputPrepaidBalQueryResp", banlance);
		return result.toJSONString();
	}
	/**
	 * ToolBar_temp_APIGW_interface.docx
	 * 1.5.7PLAN PURCHASE
	 * @param serialNumber
	 * @return
	 */
	@POST
	@Path("tcFlowSub")
	@Produces("application/json")
	@Consumes("application/json")
	public String tcFlowSub(
			@PathParam("serialNumber") String serialNumber) {
		JSONObject header = new JSONObject();
		JSONObject productOrderInfo = new JSONObject();
		JSONObject result = new JSONObject();
		
		result.put("header", header);
		result.put("productOrderInfo", productOrderInfo);
		
		header.put("status", "OK");
		header.put("errorCode", "");
		header.put("errorDescription", "");
		
		productOrderInfo.put("productID", "45680");
		productOrderInfo.put("effectiveDate", "20150604135617");
		productOrderInfo.put("expireDate", "20370101000000");
		productOrderInfo.put("balance", "0.00");
		productOrderInfo.put("chargeAmount", "0.00");
		
		JSONObject json = new JSONObject();
		json.put("planPurchaseResponse", result);
		return json.toJSONString();
	}
	
	@POST
	@Path("queryToken")
	@Produces("application/json")
	@Consumes("application/json")
	public String queryToken() {
		return "{\"access_token\":\"ky8mGG24xXZn7j6cTfoYm1Jg5so4\",\"issued_at\":\"1474336281951\",\"expires_in\":\"3599\",\"scope\":\"\",\"api_product_list\":\"[QueryProfileSTG, MobileInternet]\"}";
	}
}