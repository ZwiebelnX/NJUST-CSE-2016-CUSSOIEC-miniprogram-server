package com.ccwsz.server.controller.course;

import com.ccwsz.server.service.course.CourseCheckingService;
import com.ccwsz.server.service.util.JsonManage;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class CourseCheckingController {
    private final CourseCheckingService courseCheckingService;

    @Autowired
    public CourseCheckingController(CourseCheckingService courseCheckingService){
        this.courseCheckingService = courseCheckingService;
    }


    //考勤状态
    @RequestMapping(value="/course/check_in",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getCheckingInState(HttpServletRequest request){
        String college=request.getParameter("college");
        String personNumber=request.getParameter("personID");
        long courseID;
        try{
            courseID = Integer.parseInt(request.getParameter("courseID"));
        } catch (Exception e){
            e.printStackTrace();
            return JsonManage.buildFailureMessage("数据格式错误！");
        }
        return courseCheckingService.getCheckingInState(college,personNumber,courseID);
    }

    //尝试考勤
    @RequestMapping(value="/course/check_in", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String tryCheckingIn(@RequestBody String jsonString){
        JSONObject jsonObject =new JSONObject(jsonString);
        Integer numOfWeek=jsonObject.getInt("numOfWeek");
        Integer dayOfWeek=jsonObject.getInt("dayOfWeek");
        Integer indexOfDay=jsonObject.getInt("indexOfDay");
        String college=jsonObject.getString("college");
        String personID=jsonObject.getString("personID");
        String courseID=jsonObject.getString("courseID");
        return courseCheckingService.tryCheckingIn(college,personID,courseID,numOfWeek,dayOfWeek,indexOfDay);
    }

    //获取签到历史
    @RequestMapping(value="/course/check_in/history",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getCheckingHistory(HttpServletRequest request){
        long courseId;
        try{
            courseId = Integer.parseInt(request.getParameter("courseID"));
        } catch (Exception e){
            e.printStackTrace();
            return JsonManage.buildFailureMessage("数据格式错误！");
        }
        return courseCheckingService.getCheckingHistory(courseId);
    }
}
