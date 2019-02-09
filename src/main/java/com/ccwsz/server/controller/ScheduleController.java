package com.ccwsz.server.controller;

import com.ccwsz.server.service.ScheduleService;
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
    public String getCousreName(MultipartHttpServletRequest request){
        String studentId=request.getParameter("studentId");
        long studentIdtmp;
        try{
            studentIdtmp=Long.getLong(studentId);
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return scheduleService.getCourseNameByStudentId(studentIdtmp);
    }
}
