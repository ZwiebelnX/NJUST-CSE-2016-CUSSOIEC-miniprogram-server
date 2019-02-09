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

    //课表的控制器,第一行等待确认！！
    @RequestMapping(value = "getSchedule",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getCourseName(MultipartHttpServletRequest request){
        String studentId=request.getParameter("studentId");
        try{
            long studentIdTmp=Long.parseLong(studentId);
            return scheduleService.getCourseNameByStudentId(studentIdTmp);
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("msg","error");
            return jsonObject.toString();
        }
    }
}
