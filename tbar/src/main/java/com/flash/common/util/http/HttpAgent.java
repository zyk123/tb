package com.flash.common.util.http;

import com.flash.common.log.DebugLog;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


public class HttpAgent extends HttpPost {

    public static final String REQ_XML_DEFAULTHEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    private static final int DEFAULT_TIMEOUT = 10000;// 毫秒
    private static DebugLog log = new DebugLog(HttpAgent.class);
    private String charset = "UTF-8";
    private int statusLine;// 返回状态码
    private int timeout = 0;
    private int bufferSize = 2048;

    public HttpAgent(String url) {
        super(url);
    }

    public HttpAgent() {
        super();
    }

    public static int getDefaultTimeout() {
        return DEFAULT_TIMEOUT;
    }

    public int getStatusLine() {
        return statusLine;
    }

    public void setDefaultHeader() {
        this.addHeader("Content-type", "text/xml; charset=UTF-8");
    }

    public void setJsonHeader() {
        this.addHeader("Content-type", "application/json; charset=UTF-8");
    }

    public void setContentHeader() {
        this.setHeader("contentEncoding", "charset=UTF-8");
    }

    /**
     * 提交XML请求并获得响应
     *
     * @param reqXML
     * @return
     * @throws IOException
     * @throws ClientProtocolException
     */
    public String doPost(String reqXML) throws ClientProtocolException, IOException {
        HttpClient httpclient = new DefaultHttpClient();
//		StringBuilder responseStr = new StringBuilder();
        String content = "";

        if (timeout > 0) {
            httpclient.getParams().setIntParameter("http.socket.timeout",
                    timeout);
        } else {
            httpclient.getParams().setIntParameter("http.socket.timeout",
                    DEFAULT_TIMEOUT);
        }
        if (reqXML != null && !"".equals(reqXML)) {
            StringEntity reqEntity = new StringEntity(reqXML, charset);
            this.setEntity(reqEntity);
        }
        HttpResponse response = httpclient.execute(this);
        statusLine = response.getStatusLine().getStatusCode();
//        if (log.isDebugEnabled()) {
//            log.debug(null, null, "doPost", "Response Status", statusLine + "");
//        }

        HttpEntity resEntity = response.getEntity();
        if (resEntity != null) {
            content = EntityUtils.toString(resEntity, "UTF-8");
        }

        return content;
    }

    public String getstatusLine() {
        String statusLine = "";
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

    /**
     * 设置读取字节缓冲区，最小512字节
     *
     * @param bufferSize
     */
    public void setBufferSize(int bufferSize) {
        if (bufferSize < 512) {
            this.bufferSize = 512;
        } else {
            this.bufferSize = bufferSize;
        }
    }
}
