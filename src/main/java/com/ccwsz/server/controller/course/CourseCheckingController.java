package com.ccwsz.server.controller.course;

import com.ccwsz.server.service.course.CourseCheckingService;
import com.ccwsz.server.service.util.JsonManage;
import org.json.JSONObject;
import org.json.JSONString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

@RestController
public class CourseCheckingController {
    private final CourseCheckingService courseCheckingService;

    @Autowired
    public CourseCheckingController(CourseCheckingService courseCheckingService) {
        this.courseCheckingService = courseCheckingService;
    }


    //考勤状态
    @RequestMapping(value = "/course/check_in", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getCheckingInState(HttpServletRequest request) {
        String college = request.getParameter("college");
        String personNumber = request.getParameter("personID");
        long courseID;
        try {
            courseID = Integer.parseInt(request.getParameter("courseID"));
        } catch (Exception e) {
            e.printStackTrace();
            return JsonManage.buildFailureMessage("数据格式错误！");
        }
        return courseCheckingService.getCheckingInState(college, personNumber, courseID);
    }

    //尝试考勤
    @RequestMapping(value = "/course/check_in", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String tryCheckingIn(@RequestBody String jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);
        String college = jsonObject.getString("college");
        String personID = jsonObject.getString("personID");
        long courseID;
        Timestamp time;
        try{
            courseID = Integer.parseInt(jsonObject.getString("courseID"));
            time = Timestamp.valueOf(jsonObject.getString("time"));
        } catch (Exception e){
            e.printStackTrace();
            return JsonManage.buildFailureMessage("数据格式错误！");
        }
        return courseCheckingService.tryCheckingIn(college, personID, courseID, time);
    }

    //获取签到历史
    @RequestMapping(value = "/course/check_in/history",
            method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getCheckingHistory(HttpServletRequest request) {
        long courseId;
        try {
            courseId = Integer.parseInt(request.getParameter("courseID"));
        } catch (Exception e) {
            e.printStackTrace();
            return JsonManage.buildFailureMessage("数据格式错误！");
        }
        return courseCheckingService.getCheckingHistory(courseId);
    }

    //发布/关闭/删除签到
    @RequestMapping(value = "/course/check_in/item",
            method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String manageChecking(@RequestBody String jsonString){
        String courseID;
        Timestamp time;
        boolean isOpen;
        try{
            JSONObject json = new JSONObject(jsonString);
            courseID = json.getString("courseID");
            time = Timestamp.valueOf(json.getString("time"));


        } catch (Exception e){
            e.printStackTrace();
            return JsonManage.buildFailureMessage("数据格式错误，请检查代码！");
        }
        return null;
    }


}
