package com.ccwsz.server.controller;

import com.ccwsz.server.service.CourseHomeworkService;
import com.ccwsz.server.service.util.JsonManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class CourseHomeworkController {
    private final CourseHomeworkService courseHomeworkService;

    @Autowired
    public CourseHomeworkController(CourseHomeworkService courseHomeworkService){
        this.courseHomeworkService = courseHomeworkService;
    }

    //获取作业列表
    @RequestMapping(value = "/course/homework_list", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getCourseHomeworkList(HttpServletRequest request){
        String openid = request.getParameter("openid");
        long courseId;
        try{
            courseId = Integer.parseInt(request.getParameter("courseID"));
        } catch (Exception e){
            e.printStackTrace();
            return JsonManage.buildFailureMessage("数据格式错误！请检查代码");
        }
        return courseHomeworkService.getCourseHomeworkList(openid, courseId);
    }

    //获取作业列表
    @RequestMapping(value = "/course/homework", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getCourseSpecificHomework(HttpServletRequest request){
        long homeworkId;
        String openid = request.getParameter("openid");
        try{
            homeworkId = Integer.parseInt(request.getParameter("homeworkID"));
        } catch (Exception e){
            e.printStackTrace();
            return JsonManage.buildFailureMessage("数据格式错误！请检查代码");
        }
        return courseHomeworkService.getCourseSpecificHomework(homeworkId, openid);
    }
}
