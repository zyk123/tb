package com.flash.toolbar.common.util;

import com.alibaba.fastjson.JSONObject;


/**
 * 返回数据统一格式
 * @author ocean
 *
 */
public class ResponseUtil {

    /**
     * 返回成功信息
     * @param body
     * @return
     */
    public static String unifySuccessReturn(JSONObject body) {
        JSONObject responseJson = new JSONObject();
        responseJson.put("resultCode", "0");
        responseJson.put("resultMsg", "success");
        responseJson.put("body", body);
        return responseJson.toString();
    }
    
    /**
     * 返回失败信息
     * @param body
     * @return
     */
    public static String unifyFailReturn(JSONObject body) {
        JSONObject responseJson = new JSONObject();
        responseJson.put("resultCode", "-1");
        responseJson.put("resultMsg", "fail");
        responseJson.put("body", body);
        return responseJson.toString();
    }
}