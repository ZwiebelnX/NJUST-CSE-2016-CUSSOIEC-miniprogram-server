package com.ccwsz.server.controller.course;

import com.ccwsz.server.service.course.CourseEvaluationService;
import com.ccwsz.server.service.util.JsonManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class CourseEvaluationController {
    private final CourseEvaluationService courseEvaluationService;
    @Autowired
    public CourseEvaluationController(CourseEvaluationService courseEvaluationService){
        this.courseEvaluationService=courseEvaluationService;
    }
    //获取评教问卷列表
    @RequestMapping(value = "/course/teach_evaluation", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getCourseHomeworkList(HttpServletRequest request){
        String college = request.getParameter("college");
        String personNumber = request.getParameter("personID");
        long courseId;
        try{
            courseId = Integer.parseInt(request.getParameter("courseID"));
        } catch (Exception e){
            e.printStackTrace();
            return JsonManage.buildFailureMessage("数据格式错误！请检查代码");
        }
        return courseEvaluationService.getCourseEvaluationList(college, personNumber, courseId);
    }
}
