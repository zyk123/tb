package com.flash.toolbar.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.flash.toolbar.token.TokenManager;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class HttpHelper {
	public static CloseableHttpClient createSSLClientDefault() {
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(
					null, new TrustStrategy() {
						// 信任所有
						public boolean isTrusted(X509Certificate[] chain,
								String authType) throws CertificateException {
							return true;
						}
					}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
					sslContext);
			return HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (KeyManagementException e) {
			ExceptionLogger.error( e);
		} catch (NoSuchAlgorithmException e) {
			ExceptionLogger.error( e);
		} catch (KeyStoreException e) {
			ExceptionLogger.error( e);
		}
		return HttpClients.createDefault();
	}

	public static JSONObject httpGet(String url) {
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = createSSLClientDefault();		
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(Integer.parseInt(ParamPropertiesUtil.getSocketTimeOut())).setConnectTimeout(Integer.parseInt(ParamPropertiesUtil.getConnectTimeOut())).build();
		httpGet.setConfig(requestConfig);

		try {
			response = httpClient.execute(httpGet, new BasicHttpContext());

			if (response.getStatusLine().getStatusCode() != 200) {
				System.out.println("request url failed, http code="
						+ response.getStatusLine().getStatusCode() + ", url="
						+ url);
				return null;
			}
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String resultStr = EntityUtils.toString(entity, "utf-8");
				JSONObject result = JSON.parseObject(resultStr);
				return result;
			}
		} catch (IOException e) {
			System.out.println("request url=" + url + ", exception, msg="
					+ e.getMessage());
			ExceptionLogger.error("url = " + url , e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					ExceptionLogger.error("url = " + url , e);
				}
			}
		}

		return null;
	}
	
	public static JSONObject httpGetWithHead(String url,Map<String,String> headMap) {
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = createSSLClientDefault();		
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(Integer.parseInt(ParamPropertiesUtil.getSocketTimeOut())).setConnectTimeout(Integer.parseInt(ParamPropertiesUtil.getConnectTimeOut())).build();
		httpGet.setConfig(requestConfig);
		
		Iterator<?> iterator = headMap.entrySet().iterator();
		while(iterator.hasNext()){
			Entry<String,String> entry = (Entry<String,String>) iterator.next();
			String headName = entry.getKey();
			String headValue = entry.getValue();
			httpGet.addHeader(headName,headValue);
		}
		TraceLogger.debug("httpGetWithHead request start , url = "+url);
		try {
			response = httpClient.execute(httpGet, new BasicHttpContext());
			HttpEntity entity = response.getEntity();
			String resultStr11 = null;
			if (entity != null) {
				resultStr11 = EntityUtils.toString(entity, "utf-8");
				TraceLogger.debug(resultStr11);
			}
			
			if (response.getStatusLine().getStatusCode() != 200) {
				TraceLogger.debug("response code is not 200 , http code="
						+ response.getStatusLine().getStatusCode() + ", url="
						+ url);
				return null;
			}
			if (entity != null) {
//				String resultStr = EntityUtils.toString(entity, "utf-8");
				JSONObject result = JSON.parseObject(resultStr11);
				return result;
			}
		} catch (IOException e) {
			System.out.println("request url=" + url + ", exception, msg="
					+ e.getMessage());
			ExceptionLogger.error("url = " + url , e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					ExceptionLogger.error("url = " + url , e);
				}
			}
		}
		
		return null;
	}
	

	public static JSONObject httpPost(String url, Object data) {
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = createSSLClientDefault();
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(Integer.parseInt(ParamPropertiesUtil.getSocketTimeOut())).setConnectTimeout(Integer.parseInt(ParamPropertiesUtil.getConnectTimeOut())).build();
		httpPost.setConfig(requestConfig);
		httpPost.addHeader("Content-Type", "application/json");

		try {
			StringEntity requestEntity = new StringEntity(
					JSON.toJSONString(data), "utf-8");
			httpPost.setEntity(requestEntity);

			response = httpClient.execute(httpPost, new BasicHttpContext());

			if (response.getStatusLine().getStatusCode() != 200) {

				System.out.println("request url failed, http code="
						+ response.getStatusLine().getStatusCode() + ", url="
						+ url);
				return null;
			}
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String resultStr = EntityUtils.toString(entity, "utf-8");
				JSONObject result = JSON.parseObject(resultStr);
				return result;
			}
		} catch (IOException e) {
			System.out.println("request url=" + url + ", exception, msg="
					+ e.getMessage());
			ExceptionLogger.error("url = " + url , e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					ExceptionLogger.error("url = " + url , e);
				}
			}
		}

		return null;
	}
	public static JSONObject httpPostWithHead(String url,Map<String,String> heads, Object data) {
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = createSSLClientDefault();
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(Integer.parseInt(ParamPropertiesUtil.getSocketTimeOut())).setConnectTimeout(Integer.parseInt(ParamPropertiesUtil.getConnectTimeOut())).build();
		httpPost.setConfig(requestConfig);
		//httpPost.addHeader("Content-Type", "application/json");
		
		Iterator<?> iterator = heads.entrySet().iterator();
		while(iterator.hasNext()){
			Entry<String,String> entry = (Entry<String,String>) iterator.next();
			String headName = entry.getKey();
			String headValue = entry.getValue();
			httpPost.addHeader(headName,headValue);
		}
		
		try {
//			StringEntity requestEntity = new StringEntity(JSON.toJSONString(data), "utf-8");
			StringEntity requestEntity = new StringEntity(data.toString(), "utf-8");
			httpPost.setEntity(requestEntity);
			
			response = httpClient.execute(httpPost, new BasicHttpContext());
			
			HttpEntity entity = response.getEntity();
			String resultStr11 = null;
			if (entity != null) {
				resultStr11 = EntityUtils.toString(entity, "utf-8");
				TraceLogger.debug(resultStr11);
			}
			TraceLogger.debug("httpPostWithHead request start , url = "+url);

			if (response.getStatusLine().getStatusCode() != 200) {
				
				TraceLogger.debug("request url failed, http code="
						+ response.getStatusLine().getStatusCode() + ", url="
						+ url);
				return null;
			}
			if (entity != null) {
//				String resultStr = EntityUtils.toString(entity, "utf-8");
				JSONObject result = JSON.parseObject(resultStr11);
				return result;
			}
		} catch (IOException e) {
			System.out.println("request url=" + url + ", exception, msg="
					+ e.getMessage());
			ExceptionLogger.error("url = " + url , e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					ExceptionLogger.error("url = " + url , e);
				}
			}
		}
		
		return null;
	}
	public static JSONObject httpPostSubPackage(String url,Map<String,String> heads, Object data) {
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = createSSLClientDefault();
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(Integer.parseInt(ParamPropertiesUtil.getSocketTimeOut())).setConnectTimeout(Integer.parseInt(ParamPropertiesUtil.getConnectTimeOut())).build();
		httpPost.setConfig(requestConfig);
		//httpPost.addHeader("Content-Type", "application/json");
		
		Iterator<?> iterator = heads.entrySet().iterator();
		while(iterator.hasNext()){
			Entry<String,String> entry = (Entry<String,String>) iterator.next();
			String headName = entry.getKey();
			String headValue = entry.getValue();
			httpPost.addHeader(headName,headValue);
		}
		
		try {
//			StringEntity requestEntity = new StringEntity(JSON.toJSONString(data), "utf-8");
			StringEntity requestEntity = new StringEntity(data.toString(), "utf-8");
			httpPost.setEntity(requestEntity);
			TraceLogger.debug("httpPostSubPackage request start , url = "+url);
			long t1 = System.currentTimeMillis();
			response = httpClient.execute(httpPost, new BasicHttpContext());
			long t2 = System.currentTimeMillis();
			TraceLogger.debug("httpPostSubPackage request end , use "+ (t2 - t1) + " ms" );

			HttpEntity entity = response.getEntity();
			String resultStr11 = null;
			if (entity != null) {
				resultStr11 = EntityUtils.toString(entity, "utf-8");
				TraceLogger.debug(resultStr11);
			}

			if (response.getStatusLine().getStatusCode() != 200) {
				
				TraceLogger.debug("response code is not 200 , http code="
						+ response.getStatusLine().getStatusCode() + ", url="
						+ url);
				//return null;
			}
			if (entity != null) {
//				String resultStr = EntityUtils.toString(entity, "utf-8");
				JSONObject result = JSON.parseObject(resultStr11);
				return result;
			}
		} catch (Exception e) {
			System.out.println("request url=" + url + ", exception, msg="
					+ e.getMessage());
			ExceptionLogger.error("url = " + url , e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					ExceptionLogger.error("url = " + url , e);
				}
			}
		}
		
		return null;
	}
	
	public static JSONObject httpPostJsonWithHead(String url,Map<String,String> heads, Object data) {
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = createSSLClientDefault();
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(Integer.parseInt(ParamPropertiesUtil.getSocketTimeOut())).setConnectTimeout(Integer.parseInt(ParamPropertiesUtil.getConnectTimeOut())).build();
		httpPost.setConfig(requestConfig);
		//httpPost.addHeader("Content-Type", "application/json");
		
		Iterator<?> iterator = heads.entrySet().iterator();
		while(iterator.hasNext()){
			Entry<String,String> entry = (Entry<String,String>) iterator.next();
			String headName = entry.getKey();
			String headValue = entry.getValue();
			httpPost.addHeader(headName,headValue);
		}
		
		try {
			StringEntity requestEntity = new StringEntity(JSON.toJSONString(data), "utf-8");
			httpPost.setEntity(requestEntity);
			
			response = httpClient.execute(httpPost, new BasicHttpContext());
			
			HttpEntity entity = response.getEntity();
			String resultStr11 = null;
			if (entity != null) {
				resultStr11 = EntityUtils.toString(entity, "utf-8");
				TraceLogger.debug(resultStr11);
			}
			if (response.getStatusLine().getStatusCode() != 200) {
				
				TraceLogger.debug("request url failed, http code="
						+ response.getStatusLine().getStatusCode() + ", url="
						+ url);
				return null;
			}
			if (entity != null) {
//				String resultStr = EntityUtils.toString(entity, "utf-8");
				JSONObject result = JSON.parseObject(resultStr11);
				return result;
			}
		} catch (IOException e) {
			System.out.println("request url=" + url + ", exception, msg="
					+ e.getMessage());
			ExceptionLogger.error("url = " + url , e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					ExceptionLogger.error("url = " + url , e);
				}
			}
		}
		
		return null;
	}

	public static JSONObject execute(HttpUriRequest request) {
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = createSSLClientDefault();
		try {
			response = httpClient.execute(request, new BasicHttpContext());

			if (response.getStatusLine().getStatusCode() != 200) {

				System.out.println("request url failed, http code="
						+ response.getStatusLine().getStatusCode());
				return null;
			}
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String resultStr = EntityUtils.toString(entity, "utf-8");
				JSONObject result = JSON.parseObject(resultStr);
				return result;
			}
		} catch (IOException e) {
			ExceptionLogger.error("url = " + request.getURI() , e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					ExceptionLogger.error("url = " + request.getURI() , e);
				}
			}
		}

		return null;
	}


	public static void main(String[] args)
	{
		TokenManager tokenManager = new TokenManager();
		tokenManager.queryToken();
		Map<String,String> map = new HashMap<String,String>();
//		map.put("Authorization", "Bearer "+key);
		map.put("Content-Type", "application/json");
		map.put("Host", "celcom-prod.apigee.net");
	}
}
