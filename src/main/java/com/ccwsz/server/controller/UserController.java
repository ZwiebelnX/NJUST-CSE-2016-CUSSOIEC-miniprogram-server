package com.ccwsz.server.controller;

import com.ccwsz.server.service.UserService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

//用户相关控制器，分发绑定用户、查找用户信息等请求
@RestController
public class UserController {
    private final UserService userService;
    private final Logger logger = Logger.getLogger("UserController");

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //获取学号
    @RequestMapping(value = "/global/person_id", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getStudentId(HttpServletRequest request) {
        String openId = request.getParameter("openid");
        System.out.print(openId);
        return userService.getStudentId(openId);
    }

    //获取openid
    @RequestMapping(value="/global/openid",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getOpenid(HttpServletRequest request){
        String code=request.getParameter("code");
        return userService.getOpenId(code);
    }

    //获取周次信息
    @RequestMapping(value = "/global/week_info", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getWeekMessage(HttpServletRequest request) {
        String date = request.getParameter("date");
        String college = request.getParameter("college");
        String personID = request.getParameter("personID");
        logger.info("request received:" + "date:" + date + " ,college:" + college + " ,personID:" + personID);
        return userService.getWeekMessage(date, college, personID);
    }

    //绑定学号/.教工号
    @RequestMapping(value = "/user/binding", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String updateUser(@RequestBody String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            String openid = jsonObject.getString("openid");
            JSONObject data = jsonObject.getJSONObject("data");
            return userService.updateUser(openid, data);
        } catch (JSONException e) {
            e.printStackTrace();
            JSONObject result = new JSONObject();
            result.put("success", false);
            return result.toString();
        }
    }

    //获取用户信息
    @RequestMapping(value = "/user/info", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getUserMessage(HttpServletRequest request) {
        String openid = request.getParameter("openid");
        return userService.getUserMessage(openid);
    }
}
