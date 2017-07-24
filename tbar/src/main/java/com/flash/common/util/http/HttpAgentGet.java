package com.flash.common.util.http;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;


public class HttpAgentGet extends HttpGet {
	private String charset = "UTF-8";
	private static final int DEFAULT_TIMEOUT = 10000;// 毫秒
	private  int statusLine ;// 返回状态码
	public static final String REQ_XML_DEFAULTHEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	private int timeout = 0;
	
	public HttpAgentGet(String url) {
		super(url);
	}

	public HttpAgentGet() {
		super();
	}

	public void setDefaultHeader() {
		this.addHeader("Content-type", "text/xml; charset=" + charset);
	}

	public void setJsonHeader() {
		this.addHeader("Content-type", "application/json; charset=" + charset);
	}

	/**
	 * 提交XML或json请求并获得响应
	 * 
	 * @param reqXML
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public String doGet() throws ClientProtocolException, IOException {
		
		HttpClient httpclient = new DefaultHttpClient(); 
		String content="";
		//StringBuilder responseStr = new StringBuilder();
		try {
			if (timeout > 0) {
				httpclient.getParams().setIntParameter("http.socket.timeout",
						timeout);
			} else {
				httpclient.getParams().setIntParameter("http.socket.timeout",
						DEFAULT_TIMEOUT);
			}
			this.getMethod().getBytes("UTF-8");
			httpclient.getParams().setParameter(HttpProtocolParams.HTTP_CONTENT_CHARSET, "UTF-8");
			HttpResponse response = httpclient.execute(this);
			statusLine = response.getStatusLine().getStatusCode();
			response.addHeader("Content-type", "application/json; charset=UTF-8");
			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {
				content = EntityUtils.toString(resEntity, "UTF-8");
				// 终止请求
				// this.abort();
			}
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return content;
	}

	public int getStatusLine() {
		return statusLine;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public void setUrl(String url) throws URISyntaxException {
		this.setURI(new URI(url));
	}

	/**
	 * 设置连接超时
	 * 
	 * @param timeout
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
}
