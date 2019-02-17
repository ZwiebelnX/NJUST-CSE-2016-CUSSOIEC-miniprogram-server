package com.ccwsz.server.controller;

import com.ccwsz.server.service.CourseService;
import com.ccwsz.server.service.util.JsonManage;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

//课程相关控制器，负责分发课程表、课程内容等请求
@RestController
public class CourseController {
    private final CourseService courseService;

    //通过构造器进行依赖注入
    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    //获取课程信息
    @RequestMapping(value = "/course/courses", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getCourseInfo(HttpServletRequest request) {
        String personId = request.getParameter("personID");
        String college = request.getParameter("college");
        return courseService.getCourseByCollegeAndPersonNumber(college, personId);
    }

    //返回课程的直播信息
    @RequestMapping(value = "/course/live_info", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getCourseLiveInfo(HttpServletRequest request) {
        long courseId;
        try {
            courseId = Integer.parseInt(request.getParameter("courseID"));
        } catch (Exception e) {
            e.printStackTrace();
            return JsonManage.buildFailureMessage("数据格式错误！");
        }
        return courseService.getCourseLiveInfo(courseId);
    }

    //针对个人，返回课程的视频
    @RequestMapping(value = "/course/videos", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getCourseVideoInfo(HttpServletRequest request) {
        String openid = request.getParameter("openid");
        long courseId;
        try {
            courseId = Integer.parseInt(request.getParameter("courseID"));
        } catch (Exception e) {
            return JsonManage.buildFailureMessage("数据格式错误！");
        }
        return courseService.getCourseVideoInfo(openid, courseId);
    }

}
