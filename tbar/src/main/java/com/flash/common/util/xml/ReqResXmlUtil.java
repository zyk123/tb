package com.flash.common.util.xml;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.flash.common.log.DebugLog;
import com.flash.common.util.StringUtil;
import com.flash.common.util.validator.CommonErrorInfo;
import com.thoughtworks.xstream.XStream;

/**
 * 请求和响应工具类
 */
public class ReqResXmlUtil {
	public static final String OK = "0";
	public static final String REQ_URL = "<reqUrl>%1$s</reqUrl>";
	public static final String ERROR_CODE = "<errorCode>%1$s</errorCode>";
	public static final String ERROR_DESC = "<errorDesc>%1$s</errorDesc>";
	public static final String RESULTCODE = "<resultCode>%1$s</resultCode>";
	public static final String RESULTDESC = "<resultDesc>%1$s</resultDesc>";
	public static final String XML_NEW_LINE = "\n";
	private static String xmlHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	private static String result = "<result resultCode=\"%1$s\">";
	private static String endResult = "</result>";
	private static DebugLog log = new DebugLog(ReqResXmlUtil.class);

	private ReqResXmlUtil() {
	}

	/**
	 * 去掉其中的换行符号
	 * 
	 * @param s
	 * @return
	 */
	public static String removeNewLineSymbol(String s) {
		return s.replace(XML_NEW_LINE, "");
	}

	/**
	 * xml转换Object
	 * 
	 * @param type
	 * @param key
	 * @param inXml
	 * @param <T>
	 * @return
	 * @throws Exception
	 */
	public static <T> T parseXml2Object(Class<T> type, String key, String inXml)
			throws Exception {
		XStream xStream = new XStream();
		xStream.alias(key, type);
		return (T) xStream.fromXML(inXml);
	}

	/**
	 * object转换为xml格式
	 * 
	 * @param object
	 * @return
	 */
	public static String toXml(String key, Object object) {
		XStream xStream = new XStream();
		// 组装成xml形式
		xStream.alias(key, object.getClass());
		return xStream.toXML(object);
	}

	/**
	 * 没有头的object
	 * 
	 * @param object
	 * @return
	 */
	public static String toXmlWithoutHead(Object object) {
		XStream xStream = new XStream();
		String xml = xStream.toXML(object);
		xml = xml.replace("<" + object.getClass().getName() + ">", "");
		xml = xml.replace("</" + object.getClass().getName() + ">", "");
		return xml;
	}

	/**
	 * 解析成String格式
	 * 
	 * @param httpServletRequest
	 * @return
	 * @throws java.io.IOException
	 */
	public static String stream2String(HttpServletRequest httpServletRequest)
			throws IOException {
		ServletInputStream servletInputStream = httpServletRequest
				.getInputStream();
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(
				servletInputStream, "utf-8"));
		String temp;
		while ((temp = br.readLine()) != null) {
			sb.append(temp);
		}
		br.close();

		return sb.toString();
	}

	/**
	 * 组装成xml格式
	 * 
	 * @param content
	 * @return
	 */
	public static String composeOkXml(String content) {
		StringBuilder sb = new StringBuilder();

		sb.append(xmlHeader);
		sb.append(XML_NEW_LINE); // xml换行符
		sb.append(String.format(result, OK));
		if (content != null) {
			sb.append(XML_NEW_LINE); // xml换行符
			sb.append(content);
		}
		sb.append(XML_NEW_LINE); // xml换行符
		sb.append(endResult);

		String composeXml = sb.toString();
		return composeXml;
	}

	/**
	 * @param errorCode
	 * @param reqUrl
	 * @param errorDesc
	 * @return
	 */
	public static String composeErrorXml(String errorCode, String reqUrl,
			String errorDesc) {
		StringBuilder sb = new StringBuilder();
		sb.append(xmlHeader);
		sb.append("\n"); // xml换行符
		sb.append(String.format(result, errorCode));
		sb.append("\n"); // xml换行符
		sb.append(String.format("     " + REQ_URL, reqUrl));
		sb.append("\n"); // xml换行符
		sb.append(String.format("     " + ERROR_DESC, errorDesc));
		sb.append("\n"); // xml换行符
		sb.append(endResult);

		String composeXml = sb.toString();
		// if (log.isDebugEnabled()) {
		// log.debug(composeOkXml);
		// }
		return composeXml;
	}

	public static String composeErrorXml(String errorCode, String content) {
		StringBuilder sb = new StringBuilder();
		sb.append(xmlHeader);
		sb.append("\n"); // xml换行符
		sb.append(String.format(result, errorCode));
		sb.append("\n"); // xml换行符
		sb.append(content);
		sb.append("\n"); // xml换行符
		sb.append(endResult);

		String composeErrorXml = sb.toString();

		if (log.isDebugEnabled()) {
			log.debug(composeErrorXml);
		}
		return composeErrorXml;
	}

	/**
	 * 没有内容的返回
	 * 
	 * @return
	 */
	public static String composeOkXml() {
		return composeOkXml(null);
	}

	/**
	 * 成功响应消息
	 * 
	 * @param outXml
	 * @return
	 */
	public static ResponseEntity<String> buildOkResponse(String outXml) {
		return buildResponse(outXml, HttpStatus.OK);
	}

	/**
	 * 请求格式错误
	 * 
	 * @param errorInfo
	 * @return
	 */
	public static ResponseEntity<String> buildInputError(
			CommonErrorInfo errorInfo) {
		String error = composeErrorXml(errorInfo.getErrorCode(), errorInfo
				.getErrorUrl(), errorInfo.getErrorDesc());
		if (log.isDebugEnabled()) {
			log.debug(error);
		}
		return buildResponse(error, HttpStatus.METHOD_NOT_ALLOWED);
	}

	/**
	 * 没有数据错误
	 * 
	 * @return errorInfo CommonError
	 */
	public static ResponseEntity<String> buildNoDataFound(
			CommonErrorInfo errorInfo) {
		String error = composeErrorXml(errorInfo.getErrorCode(), errorInfo
				.getErrorUrl(), errorInfo.getErrorDesc());
		if (log.isDebugEnabled()) {
			log.debug(error);
		}
		return buildResponse(error, HttpStatus.METHOD_NOT_ALLOWED);
	}

	public static ResponseEntity<String> buildResponse(String outXml,
			HttpStatus httpStatus) {
		if (log.isDebugEnabled()) {
			log.debug(outXml);
		}
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Content-Type", "application/xml;charset=UTF-8");

		ResponseEntity<String> entity = new ResponseEntity<String>(outXml,
				responseHeaders, httpStatus);
		if (log.isDebugEnabled()) {
			log.debug(entity);
		}
		return entity;
	}

	/**
	 * 错误信息
	 * 
	 * @param errorInfo
	 * @return
	 */
	public static String composeErrorXml(CommonErrorInfo errorInfo) {
		return composeErrorXml(errorInfo.getErrorCode(), errorInfo
				.getErrorUrl(), errorInfo.getErrorDesc());
	}

	public static String toUtf8String(String s)
			throws UnsupportedEncodingException {
		return new String(s.getBytes("UTF-8"), "UTF-8");
	}

	public static void doXmlResponse(HttpServletResponse response,
			String content) throws IOException {
		response.setContentType("application/xml");
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();
		writer.write(toUtf8String(content));
		writer.flush();
		writer.close();
	}

	public static ResponseEntity<String> buildXmlResponse(String outXml,
			HttpStatus httpStatus) {
		if (log.isDebugEnabled()) {
			log.debug(outXml);
		}
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Content-Type", "application/xml;charset=UTF-8");

		ResponseEntity<String> entity = new ResponseEntity<String>(outXml,
				responseHeaders, httpStatus);
		if (log.isDebugEnabled()) {
			log.debug(entity);
		}
		return entity;
	}

	/**
	 * 系统错误，数据库访问异常等
	 * 
	 * @param errorInfo
	 * @return
	 */
	public static ResponseEntity<String> buildSysError(CommonErrorInfo errorInfo) {
		String error = composeErrorXml(errorInfo.getErrorCode(), errorInfo
				.getErrorUrl(), errorInfo.getErrorDesc());
		if (log.isDebugEnabled()) {
			log.debug(error);
		}
		return buildResponse(error, HttpStatus.METHOD_NOT_ALLOWED);
		// return buildResponse(error, HttpStatus.SERVICE_UNAVAILABLE);
	}

	public static String composeErrorXml(String inErrorCode,
			String outErrorCode, String content, String errorDesc) {
		StringBuilder sb = new StringBuilder();
		sb.append(xmlHeader);
		sb.append("\n"); // xml换行符
		sb.append(String.format(result, inErrorCode));
		sb.append("\n"); // xml换行符
		sb.append(String.format("     " + RESULTCODE, outErrorCode));
		sb.append("\n"); // xml换行符
		sb.append(String.format("     " + RESULTDESC, errorDesc));
		sb.append("\n"); // xml换行符
		if (!StringUtil.isNull(content)) {
			sb.append(content);
			sb.append("\n"); // xml换行符
		}
		sb.append(endResult);

		String composeErrorXml = sb.toString();
		if (log.isDebugEnabled()) {
			log.debug(composeErrorXml);
		}
		return composeErrorXml;
	}

}