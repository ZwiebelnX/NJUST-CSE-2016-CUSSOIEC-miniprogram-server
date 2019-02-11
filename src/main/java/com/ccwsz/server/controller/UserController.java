package com.ccwsz.server.controller;

import com.ccwsz.server.service.UserService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;

@RestController
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService){this.userService=userService;}
    //获取学号
    @RequestMapping(value="/global/student_id",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getStudentId(MultipartHttpServletRequest request){
        String openId=request.getParameter("openid");
        System.out.print(openId);
        return userService.getStudentId(openId);
    }

    //获取openid
//    @RequestMapping(value="/global/openid",produces = "application/json;charset=UTF-8")
//    @ResponseBody
//    public String getOpenid(MultipartHttpServletRequest request){
//        String code=request.getParameter("code");
//        return userService.getOpenId(code);
//    }

    //获取周次信息
    @RequestMapping(value="/global/week_info",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getWeekMessage(@RequestBody String jsonString){
        JSONObject jsonObject = new JSONObject(jsonString);
        String data=jsonObject.getString("data");
        String college=jsonObject.getString("college");
        String personID=jsonObject.getString("personID");
        return userService.getWeekMessage(data,college,personID);
    }

    //绑定学号/.教工号
    @RequestMapping(value="/user/binding",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String updateUser(@RequestBody String jsonString){
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            String openid = jsonObject.getString("openid");
            JSONObject data = jsonObject.getJSONObject("data");
            return userService.updateUser(openid,data);
        }
        catch (JSONException e) {
            e.printStackTrace();
            JSONObject result=new JSONObject();
            result.put("success",false);
            return result.toString();
        }
    }
    //获取用户信息
    @RequestMapping(value="/user/info",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getUserMessage(@RequestBody String jsonString){
        JSONObject jsonObject = new JSONObject(jsonString);
        String openid=jsonObject.getString("openid");
        return userService.getUserMessage(openid);
    }
}
