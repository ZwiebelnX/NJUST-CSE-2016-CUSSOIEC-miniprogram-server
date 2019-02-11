package com.ccwsz.server.controller;

import com.ccwsz.server.service.CourseService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

//课程相关控制器，负责分发课程表、课程内容等请求
@RestController
public class CourseController {
    private final CourseService courseService;
    private final Logger logger = Logger.getLogger("ScheduleController");

    //通过构造器进行依赖注入
    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    //课表的控制器,获取课程信息
    @RequestMapping(value = "/course/courses", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getCourseName(@RequestBody String jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);
        String personId = jsonObject.getString("personID");
        //System.out.print("hahahahahah"+personId);
        logger.info("json received:" + jsonString);
        String college = jsonObject.getString("college");
        return courseService.getCourseByCollegeAndPersonNumber(college, personId);
    }

}
