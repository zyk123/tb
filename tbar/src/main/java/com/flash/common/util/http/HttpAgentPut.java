package com.flash.common.util.http;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;


public class HttpAgentPut extends HttpPut {
	private String charset = "UTF-8";
	private static final int DEFAULT_TIMEOUT = 10000;//毫秒
	public static final String REQ_XML_DEFAULTHEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	private int timeout=0;
	private  int statusLine ;// 返回状态码
	public int getStatusLine() {
		return statusLine;
	}

	public HttpAgentPut(String url) {
		super(url);
	}

	public HttpAgentPut() {
		super();
	}

	public void setDefaultHeader() {
		this.addHeader("Content-type", "text/xml; charset=UTF-8");
	}
	
	public void setJsonHeader() {
		this.addHeader("Content-type", "application/json; charset=UTF-8");
	}

	/**
	 * 提交XML请求并获得响应
	 * 
	 * @param reqXML
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public String doPut(String reqXML) throws ClientProtocolException, IOException {
		HttpClient httpclient = new DefaultHttpClient();
		//StringBuilder responseStr = new StringBuilder();
		String content="";
		try {
			if(timeout>0){
				httpclient.getParams().setIntParameter("http.socket.timeout", timeout);
			}else{
				httpclient.getParams().setIntParameter("http.socket.timeout", DEFAULT_TIMEOUT);
			}	
			if (reqXML != null && !"".equals(reqXML)) {
				StringEntity reqEntity = new StringEntity(reqXML, charset);
				this.setEntity(reqEntity);
			}
			HttpResponse response = httpclient.execute(this);
			statusLine = response.getStatusLine().getStatusCode();
			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {
				content = EntityUtils.toString(resEntity, "UTF-8");
			}
		}  finally {
			httpclient.getConnectionManager().shutdown();
		}
		return content;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public void setUrl(String url) throws URISyntaxException {
		this.setURI(new URI(url));
	}
	/**
	 * 设置连接超时
	 * @param timeout
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
}
