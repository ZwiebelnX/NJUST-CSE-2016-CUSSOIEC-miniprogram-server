package com.ccwsz.server.service;

import com.ccwsz.server.dao.dock.CourseRepository;
import com.ccwsz.server.dao.dock.UserRepository;
import com.ccwsz.server.dao.dock.CollegeRepository;
import com.ccwsz.server.dao.entity.CourseChooseEntity;
import com.ccwsz.server.dao.entity.CourseEntity;
import com.ccwsz.server.dao.dock.CourseChooseRepository;
import com.ccwsz.server.dao.entity.UserEntity;
import com.ccwsz.server.service.util.JsonManage;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {
    private CourseChooseRepository courseChooseRepository;
    private UserRepository userRepository;
    private CollegeRepository collegeRepository;
    private CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseChooseRepository courseChooseRepository, UserRepository userRepository,
                         CollegeRepository collegeRepository, CourseRepository courseRepository) {
        this.courseChooseRepository = courseChooseRepository;
        this.userRepository = userRepository;
        this.collegeRepository = collegeRepository;
        this.courseRepository = courseRepository;
    }

    //通过学生id找到所有课程名字
    public String getCourseByCollegeAndPersonNumber(String collegeName, String personId) {
        JSONObject responseJson = new JSONObject(); //为最后传出的json
        UserEntity currentUser = userRepository.findByCollegeAndPersonNumber(collegeName, personId);
        if (currentUser == null) {
            return JsonManage.buildFailureMessage("未找到用户！");
        }
        //获取该学生课程列表
        List<CourseChooseEntity> courseChooseEntitiesList = courseChooseRepository.findByStudentId(currentUser.getId());
        if(courseChooseEntitiesList == null){
            responseJson.put("success", true);
            responseJson.put("result", new JSONObject());
            return responseJson.toString();
        }
        try {
            //名字和说明分别对应前段的
            List<JSONObject> result = new ArrayList<>();
            for (CourseChooseEntity courseChoose : courseChooseEntitiesList) {
                long courseId = courseChoose.getCourseId();
                CourseEntity course = courseRepository.findById(courseId);
                String classWeek = course.getClassWeek();
                String startWeek = classWeek.split(",")[0];
                String endWeek = classWeek.split(",")[1];
                int start = Integer.parseInt(startWeek);
                int end = Integer.parseInt(endWeek);
                int len = end - start + 1;
                Integer[] active = new Integer[len];
                for (int i = 0; i < len; i++) {
                    active[i] = i + start;
                }
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("courseID", course.getId());
                jsonObject.put("active", active);
                List<JSONObject> positionTmp = new ArrayList<>();
                String courseTime = course.getClassTime();
                String[] classTime = courseTime.split(";");
                for (String time : classTime) {
                    JSONObject positionTmpJSON = new JSONObject();
                    int timeLen = time.length();
                    int flagTmp = 0;
                    Integer sumTmp = 0;
                    Integer dayOfWeek = 0;
                    List<Integer> indexOfDay = new ArrayList<>();
                    for (int i = 0; i < timeLen; i++) {
                        int chr = time.charAt(i);
                        if (chr < 48 || chr > 57) {
                            if (flagTmp == 0) {
                                flagTmp = 1;
                                dayOfWeek = sumTmp;
                            } else {
                                indexOfDay.add(sumTmp);
                            }
                            System.out.println(sumTmp);
                            sumTmp = 0;
                        } else sumTmp = sumTmp * 10 + (chr - 48);
                    }
                    indexOfDay.add(sumTmp);
                    positionTmpJSON.put("dayOfWeek", dayOfWeek);
                    positionTmpJSON.put("indexOfDay", indexOfDay);
                    positionTmp.add(positionTmpJSON);
                }
                jsonObject.put("position", positionTmp);
                JSONObject info = new JSONObject();
                info.put("name", course.getCourseName());
                info.put("teacher", course.getTeacherName());
                info.put("location", course.getClassroomLocation());
                jsonObject.put("info", info);
                result.add(jsonObject);
            }
            responseJson.put("success", true);
            responseJson.put("result", result);
            return responseJson.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("success", false);
            return jsonObject.toString();
        }
    }
}
