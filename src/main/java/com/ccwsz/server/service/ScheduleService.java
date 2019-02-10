package com.ccwsz.server.service;

import com.ccwsz.server.dao.User;
import com.ccwsz.server.dao.entity.CourseChooseEntity;
import com.ccwsz.server.dao.entity.CourseEntity;
import com.ccwsz.server.dao.CourseChoose;
import com.ccwsz.server.dao.entity.UserEntity;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ScheduleService {
    private CourseChoose courseChooseDB;
    private User user;
    //通过学生id找到所有课程名字
    @Autowired
    public ScheduleService(CourseChoose courseChooseDB,User user){this.courseChooseDB=courseChooseDB;this.user=user;}
    public String getCourseNameByStudentId(long studentId){
        UserEntity currentUser=user.getUserEntitiesByStudentId(studentId);
        String collegeId=currentUser.getCollege();
        List<CourseChooseEntity> courseChooseEntitiesList=courseChooseDB.getCourseChooseEntitiesByStudentId(studentId);
        try {
            JSONObject tmp=new JSONObject();
            JSONArray result=new JSONArray();
            for (CourseChooseEntity courseChoose : courseChooseEntitiesList) {
                long courseId = courseChoose.getCourseId();
                CourseEntity course = courseChooseDB.getCourseEntityById(courseId);
                String classWeek=course.getClassWeek();
                String startWeek=classWeek.split(",")[0];
                String[] startTime=startWeek.split("-");
                String endWeek=classWeek.split(",")[1];
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("courseID",course.getId());
                JSONObject info=new JSONObject();
                info.put("name",course.getCourseName());
                info.put("teacher",course.getTeacherName());
                info.put("location",course.getClassroomLocation());
                jsonObject.put("info",info);
                result.put(jsonObject);
            }
            tmp.put("success",true);
            tmp.put("result",result);
            tmp.put("msg","SUCCESS");
            return tmp.toString();
        }
        catch (Exception ex){
            ex.printStackTrace();
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("msg","SERVER_ERROR");
            return  jsonObject.toString();
        }
    }
    //获取周次信息
    public String getWeekMessage(String data){
        JSONObject result=new JSONObject();
        return "1";
    }
}
