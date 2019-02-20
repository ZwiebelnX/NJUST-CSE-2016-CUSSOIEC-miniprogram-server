package com.ccwsz.server.service.util;

import org.json.JSONObject;

//json的通用方法封装类
public class  JsonManage {
    public static String buildFailureMessage(String reason){
        JSONObject json = new JSONObject();
        json.put("success", false);
        json.put("reason", reason);
        return json.toString();
    }
}
