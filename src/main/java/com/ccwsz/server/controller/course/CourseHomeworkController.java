package com.ccwsz.server.controller.course;

import com.ccwsz.server.service.course.CourseHomeworkService;
import com.ccwsz.server.service.util.JsonManage;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class CourseHomeworkController {
    private final CourseHomeworkService courseHomeworkService;

    @Autowired
    public CourseHomeworkController(CourseHomeworkService courseHomeworkService){
        this.courseHomeworkService = courseHomeworkService;
    }

    //获取作业列表
    @RequestMapping(value = "/course/homework_list", method = RequestMethod.GET,
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
        return courseHomeworkService.getCourseHomeworkList(college, personNumber, courseId);
    }

    //获取作业列表
    @RequestMapping(value = "/course/homework", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getCourseSpecificHomework(HttpServletRequest request){
        long homeworkId;
        String college = request.getParameter("college");
        String personNumber = request.getParameter("personID");
        try{
            homeworkId = Integer.parseInt(request.getParameter("homeworkID"));
        } catch (Exception e){
            e.printStackTrace();
            return JsonManage.buildFailureMessage("数据格式错误！请检查代码");
        }
        return courseHomeworkService.getCourseSpecificHomework(college, personNumber, homeworkId);
    }

    //学生提交作答情况
    @RequestMapping(value = "/course/homework_answer", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String submitHomeworkAnswer(@RequestBody String jsonString){
        try{
            JSONObject json = new JSONObject(jsonString);
            String personNumber = json.getString("personID");
            String collegeName = json.getString("college");
            long homeworkId = Integer.parseInt(json.getString("homeworkID"));
            long courseId = Integer.parseInt(json.getString("courseID"));
            return courseHomeworkService.submitHomeworkAnswer(collegeName, personNumber, courseId, homeworkId,
                    json.getJSONObject("data").getJSONArray("answer"));
        } catch (Exception e){
            e.printStackTrace();
            return JsonManage.buildFailureMessage("数据格式错误！请检查代码");
        }
    }
}
