package com.flash.common.util;

import com.flash.common.log.ExceptionLogger;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class HttpUtil {

	private static final int READ_TIME_OUT = 18000;
	private static final int CONN_TIME_OUT = 18000;

	public static String sendGet(String uri,String requestSerial) {
		try {
			URLConnection conn = getURLConnection(uri);
			// 发送请求
			conn.connect();
			return getResponseStr(conn);
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			//e.printStackTrace();
			ExceptionLogger.LoggerInfo(requestSerial, "", uri, e);
		}
		return "";
	}
	
	public static String sendPost(String url, String param,String requestSerial) {
		PrintWriter out = null;
		try {
			URLConnection conn = getURLConnection(url);
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setReadTimeout(READ_TIME_OUT);
			conn.setConnectTimeout(CONN_TIME_OUT);
			// 发送请求
			if(param != null) {
				out = new PrintWriter(conn.getOutputStream());
				out.print(param);
				out.flush();
			}
			return getResponseStr(conn);
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！"+e);
			//e.printStackTrace();
			ExceptionLogger.LoggerInfo(requestSerial, "", url, e);
		} finally {
			if(out != null) {
				out.close();
			}
		}
		return "";
	}
	
	public static byte[] sendPost1(String url, String param,String requestSerial) {
		PrintWriter out = null;
		try {
			URLConnection conn = getURLConnection(url);
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);

			// 发送请求
			if(param != null) {
				out = new PrintWriter(conn.getOutputStream());
				out.print(param);
				out.flush();
			}
			return getResponseByte(conn);
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！"+e);
			//e.printStackTrace();
			ExceptionLogger.LoggerInfo(requestSerial, "", url, e);
		} finally {
			if(out != null) {
				out.close();
			}
		}
		return new byte[0];
	}
	
	/**
	 * 获取URL连接，并设置骑牛属性
	 */
	private static URLConnection getURLConnection(String urlStr) throws IOException {
		URL url = new URL(urlStr);
		// 打开和URL之间的连接
		URLConnection connection = url.openConnection();
		// 设置通用的请求属性
		connection.setRequestProperty("accept", "*/*");
		connection.setRequestProperty("connection", "Keep-Alive");
		connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
		return connection;
	}
	
	/**
	 * 获取请求相应数据
	 * @param conn
	 * @return
	 * @throws IOException
	 */
	private static String getResponseStr(URLConnection conn) throws IOException {
		StringBuffer result = new StringBuffer("");
		String line = null;
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		while ((line = in.readLine()) != null) {
			result.append(line);
		}
		try {
			in.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return result.toString();
	}
	
	/**
	 * 获取请求相应数据
	 * @param conn
	 * @return
	 * @throws IOException
	 */
	private static byte[] getResponseByte(URLConnection conn) throws IOException {
		InputStream in = conn.getInputStream();
		byte[] bytes = new byte[in.available()];
		in.read(bytes);
		try {
			in.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return bytes;
	}
	
}