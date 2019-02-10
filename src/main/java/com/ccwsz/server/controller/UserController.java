package com.ccwsz.server.controller;

import com.ccwsz.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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
    @RequestMapping(value="/global/openid",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getOpenid(MultipartHttpServletRequest request){
        String code=request.getParameter("code");
        return userService.getOpenId(code);
    }
}
