package com.ccwsz.server.service;

import com.ccwsz.server.dao.College;
import com.ccwsz.server.dao.User;
import com.ccwsz.server.dao.entity.CollegeEntity;
import com.ccwsz.server.dao.entity.CourseChooseEntity;
import com.ccwsz.server.dao.entity.CourseEntity;
import com.ccwsz.server.dao.CourseChoose;
import com.ccwsz.server.dao.entity.UserEntity;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@Service
public class ScheduleService {
    private CourseChoose courseChooseDB;
    private User user;
    private College college;
    //通过学生id找到所有课程名字
    @Autowired
    public ScheduleService(CourseChoose courseChooseDB,User user,College college){this.courseChooseDB=courseChooseDB;this.user=user;this.college=college;}
    public String getCourseNameByStudentId(String personId,String collegeName){
        UserEntity currentUser=user.getUserEntitiesByStudentNumber(personId,collegeName);
        //获取了该学生的课程列表
        List<CourseChooseEntity> courseChooseEntitiesList=courseChooseDB.getCourseChooseEntitiesByStudentId(currentUser.getId());
        try {
            //为最后传出的json
            JSONObject tmp=new JSONObject();
            //名字和说明分别对应前段的
            JSONArray result=new JSONArray();
            for (CourseChooseEntity courseChoose : courseChooseEntitiesList) {
                long courseId = courseChoose.getCourseId();
                CourseEntity course = courseChooseDB.getCourseEntityById(courseId);
                String classWeek=course.getClassWeek();
                String startWeek=classWeek.split(",")[0];
                String endWeek=classWeek.split(",")[1];
                int start=Integer.parseInt(startWeek);
                int end=Integer.parseInt(endWeek);
                int len=end-start+1;
                Integer[] active=new Integer[len];
                for(int i=0;i<len;i++){
                    active[i]=i+start;
                }
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("courseID",course.getId());
                jsonObject.put("active",active);
                JSONObject position=new JSONObject();
                String courseTime=course.getClassTime();
                String[] classTime=courseTime.split(",");
                Integer[] indexOfDay=new Integer[20];
                Integer dayOfWeek=0;
                for(String time:classTime){
                    int timeLen=time.length();
                    Integer flagTmp=0;
                    Integer index=0;
                    Integer sumTmp=0;
                    for(int i=0;i<timeLen;i++){
                        int chr=time.charAt(i);
                        if(chr<48||chr>57){
                            if(flagTmp==0){
                                flagTmp=1;
                                dayOfWeek=sumTmp;
                            }
                            else{
                                indexOfDay[index]=sumTmp;
                                index=index+1;
                            }
                            sumTmp=0;
                        }
                        else sumTmp+=(chr-48);
                    }
                }
                position.put("dayOfWeek",dayOfWeek);
                position.put("indexOfDay",indexOfDay);
                jsonObject.put("position",position);
                JSONObject info=new JSONObject();
                info.put("name",course.getCourseName());
                info.put("teacher",course.getTeacherName());
                info.put("location",course.getClassroomLocation());
                jsonObject.put("info",info);
                result.put(jsonObject);
            }
            tmp.put("success",true);
            tmp.put("result",result);
            return tmp.toString();
        }
        catch (Exception ex){
            ex.printStackTrace();
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("success",false);
            return  jsonObject.toString();
        }
    }
}
