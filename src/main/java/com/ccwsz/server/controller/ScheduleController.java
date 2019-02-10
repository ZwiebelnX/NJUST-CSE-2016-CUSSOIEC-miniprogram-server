package com.ccwsz.server.controller;

import com.ccwsz.server.service.ScheduleService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
public class ScheduleController {
    private final ScheduleService scheduleService;
    //通过构造器进行依赖注入
    @Autowired
    public ScheduleController(ScheduleService scheduleService){this.scheduleService=scheduleService;}

    //课表的控制器,获取课程信息
    @RequestMapping(value = "/course/courses",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getCourseName(MultipartHttpServletRequest request){
        String studentID=request.getParameter("studentID");
        try{
            long studentIdTmp=Long.parseLong(studentID);
            return scheduleService.getCourseNameByStudentId(studentIdTmp);
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("success",false);
            return jsonObject.toString();
        }
    }
//    //获取周次信息
//    @RequestMapping(value="/global/week_info",produces = "application/json;charset=UTF-8")
//    @ResponseBody
//    public String getWeekMessage(MultipartHttpServletRequest request){
//        String data=request.getParameter("data");
//        return scheduleService.getWeekMessage(data);
//    }
//    //获取openid
//    @RequestMapping(value="/global/openid",produces = "application/json;charset=UTF-8")
//    @ResponseBody
//    public String getOpenid(MultipartHttpServletRequest request){
//        String code=request.getParameter("code");
//        return "1";
//    }
}
