package com.ccwsz.server.service.util;

import org.json.JSONObject;

public class  JsonManage {
    public static String buildFailureMessage(String reason){
        JSONObject json = new JSONObject();
        json.put("success", false);
        json.put("reason", reason);
        return json.toString();
    }
}
