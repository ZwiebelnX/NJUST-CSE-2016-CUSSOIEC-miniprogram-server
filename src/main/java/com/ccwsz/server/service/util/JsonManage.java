package com.ccwsz.server.service.util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;

public class  JsonManage {
    public static String buildFailureMessage(String reason){
        JSONObject json = new JSONObject();
        json.put("success", false);
        json.put("reason", reason);
        return json.toString();
    }

    public static JSONObject filterNull(JSONObject jsonObj) {
        Iterator<String> it = jsonObj.keys();
        Object obj = null;
        String key = null;
        while (it.hasNext()) {
            key = it.next();
            obj = jsonObj.get(key);
            if (obj instanceof JSONObject) {
                filterNull((JSONObject) obj);
            }
            if (obj instanceof JSONArray) {
                JSONArray objArr = (JSONArray) obj;
                for (int i = 0; i < objArr.length(); i++) {
                    filterNull(objArr.getJSONObject(i));
                }
            }
            if (obj == JSONObject.NULL) {
                jsonObj.put(key, "");
            }
        }
        return jsonObj;
    }
}
