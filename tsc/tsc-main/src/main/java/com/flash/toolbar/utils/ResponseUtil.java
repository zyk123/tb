package com.flash.toolbar.utils;

import net.sf.json.JSONObject;

/**
 * 返回数据统一格式
 * @author ocean
 *
 */
public class ResponseUtil {

    /**
     * 返回失败信息
     * @param body
     * @return
     */
    public static String unifySuccessReturn(String body) {
        JSONObject responseJson = new JSONObject();
        responseJson.put("resultCode", "0");
        responseJson.put("resultMsg", "success");
        responseJson.put("body", body);
        return responseJson.toString();
    }
    
    /**
     * 返回成功信息
     * @param body
     * @return
     */
    public static String unifyFailReturn(String body) {
        JSONObject responseJson = new JSONObject();
        responseJson.put("resultCode", "-1");
        responseJson.put("resultMsg", "failed");
        responseJson.put("body", body);
        return responseJson.toString();
    }
}