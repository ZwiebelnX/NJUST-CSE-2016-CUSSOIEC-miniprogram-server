package com.ccwsz.server.service;

import com.ccwsz.server.dao.entity.CourseChooseEntity;
import com.ccwsz.server.dao.entity.CourseEntity;
import com.ccwsz.server.dao.CourseChoose;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ScheduleService {
    private CourseChoose courseChooseDB;
    //通过学生id找到所有课程名字
    @Autowired
    public ScheduleService(CourseChoose courseChooseDB){this.courseChooseDB=courseChooseDB;}
    public String getCourseNameByStudentId(long studentId,String data){
        List<CourseChooseEntity> courseChooseEntitiesList=courseChooseDB.getCourseChooseEntitiesByStudentId(studentId);
        try {
            String[] currentTime=data.split("-");
            JSONObject tmp=new JSONObject();
            JSONArray result=new JSONArray();
            for (CourseChooseEntity courseChoose : courseChooseEntitiesList) {
                long courseId = courseChoose.getCourseId();
                CourseEntity course = courseChooseDB.getCourseEntityById(courseId);
                String classWeek=course.getClassWeek();
                String startWeek=classWeek.split(",")[0];
                String endWeek=classWeek.split(",")[1];
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("courseID",course.getId());
                //jsonObject.put("active",);
                JSONArray position=new JSONArray();

                jsonObject.put("position",course.getClassroomLocation());

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
