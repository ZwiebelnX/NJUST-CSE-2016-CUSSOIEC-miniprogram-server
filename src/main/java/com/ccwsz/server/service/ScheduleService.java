package com.ccwsz.server.service;

import com.ccwsz.server.dao.entity.CourseChooseEntity;
import com.ccwsz.server.dao.entity.CourseEntity;
import com.ccwsz.server.dao.repository.CourseChooseDB;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
@Service
public class ScheduleService {
    private CourseChooseDB courseChooseDB;
    //通过学生id找到所有课程名字
    @Autowired
    public ScheduleService(CourseChooseDB courseChooseDB){this.courseChooseDB=courseChooseDB;}
    public String getCourseNameByStudentId(long studentId){
        List<CourseChooseEntity> courseChooseEntitieslist=courseChooseDB.getCourseChooseEntitiesByStudentId(studentId);
        try {
            JSONObject tmp=new JSONObject();
            JSONArray jsonArray=new JSONArray();
            for (CourseChooseEntity courseChoose : courseChooseEntitieslist) {
                long courseId = courseChoose.getCourseId();
                CourseEntity course = courseChooseDB.getCourseEntityById(courseId);
                String courseName = course.getCourseName();
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("courseName",courseName);
                jsonArray.put(jsonObject);
            }
            tmp.put("courseNamelist",jsonArray);
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
}
