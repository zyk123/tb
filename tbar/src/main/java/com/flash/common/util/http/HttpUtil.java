package com.flash.common.util.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.flash.common.log.CommonExceptionLog;
import com.flash.common.log.DebugLog;

/**
 * @author: Edward
 * Date: 13-6-7
 * Time: 上午9:42
 * Description:
 */
public class HttpUtil {

    private static DebugLog log = new DebugLog(HttpUtil.class);

    public static HttpPost buildPost(String url, String content) throws UnsupportedEncodingException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader(HttpHeaders.CONNECTION, "close");   //  永久关闭不复用连接
        httpPost.setHeader("Content-type", "application/xml;charset=UTF-8");
        HttpEntity httEntity = new StringEntity(content, "UTF-8");
        httpPost.setEntity(httEntity);
        return httpPost;
    }

    public static String buildContent(String content) {
        String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        String xml = XML_HEADER + "\n";
        xml += content;
        /*try {
            xml = new String(xml.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            CommonExceptionLog.error(e.getMessage(), e);
        }*/
        if (log.isDebugEnabled()) {
            log.debug(xml);
        }
        return xml;
    }

    /**
     * 发送请求
     *
     * @param httpPost
     * @return
     */
    public static HttpResponse sendReq(HttpPost httpPost) {
        //  发起 http请求
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpPost);
        } catch (IOException e) {
            CommonExceptionLog.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return httpResponse;
    }

    /**
     * 返回请求的结果
     *
     * @param httpResponse
     * @return
     */
    public static String getResponse(HttpResponse httpResponse) {
        HttpEntity entity = httpResponse.getEntity();
        if (entity != null) {
//            long len = entity.getContentLength();
//            if (len != -1 && len < 2048) {
            try {
                String s = EntityUtils.toString(entity);
                if (log.isDebugEnabled()) {
                    log.debug(s);
                }
                return s;
            } catch (IOException e) {
                CommonExceptionLog.error(e.getMessage(), e);
//                }
            }
        }
        return "";
    }

    /**
     * 发送请求返回结果
     *
     * @param xml
     * @param url
     * @return
     * @throws Exception
     */
    public static String doRequest(String xml, String url) throws Exception {
        String content = HttpUtil.buildContent(xml);
        HttpPost httpPost = HttpUtil.buildPost(url, content);
        //  发送xml信息
        HttpResponse httpResponse = HttpUtil.sendReq(httpPost);
        return HttpUtil.getResponse(httpResponse);
    }

    public static void main(String[] args) {
        String xml = "<updateMobileOrder>\n" +
                "  <payId>1</payId>\n" +
                "  <status>0</status>\n" +
                "</updateMobileOrder>";
        String url = "http://192.168.1.160:8080/pay/paynotify/updateMobileOrder";
//        String url = "http://127.0.0.1:8080/pay/paynotify/updateMobileOrder";

        try {
            String s = doRequest(xml, url);
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

}
