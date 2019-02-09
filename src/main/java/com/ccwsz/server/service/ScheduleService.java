package com.ccwsz.server.service;

import com.ccwsz.server.dao.entity.CourseChooseEntity;
import com.ccwsz.server.dao.entity.CourseEntity;
import com.ccwsz.server.dao.repository.CourseChooseDB;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.JSONArray;
import org.json.JsonObject;

import java.util.List;

public class ScheduleService {
    private CourseChooseDB courseChooseDB;
    //通过学生id找到所有课程名字
    public String getCourseNameByStudentId(long studentId){
        List<CourseChooseEntity> courseChooseEntitieslist=courseChooseDB.getCourseChooseEntitiesByStudentId(studentId);
        try {
            JSONArray tmp=new JSONArray();
            JSONArray jsonArray=new JSONArray();
            for (CourseChooseEntity courseChoose : courseChooseEntitieslist) {
                long courseId = courseChoose.getCourseId();
                CourseEntity course = courseChooseDB.getCourseEntityById(courseId);
                String courseName = course.getCourseName();
                JSONPObject jsonpObject= new JSONPObject();
                jsonpObject.put("courseName",courseName);
                jsonArray.put(jsonpObject);
            }
            tmp.put("courseNamelist",jsonArray);
            tmp.put("msg","SUCCESS");
            return tmp.toString();
        }
        catch (Exception e){
            JSONPObject jsonpObject=new JSONPObject();
            jsonpObject.put("msg","SERVER_ERROR");
            return  jsonpObject.toString();
            e.printStackTrace();
        }
    }
}
