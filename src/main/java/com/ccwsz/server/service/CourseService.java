package com.ccwsz.server.service;

import com.ccwsz.server.dao.dock.CourseRepository;
import com.ccwsz.server.dao.dock.UserRepository;
import com.ccwsz.server.dao.dock.CollegeRepository;
import com.ccwsz.server.dao.entity.CourseChooseEntity;
import com.ccwsz.server.dao.entity.CourseEntity;
import com.ccwsz.server.dao.dock.CourseChooseRepository;
import com.ccwsz.server.dao.entity.UserEntity;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private CourseChooseRepository courseChooseRepository;
    private UserRepository userRepository;
    private CollegeRepository collegeRepository;
    private CourseRepository courseRepository;

    //通过学生id找到所有课程名字
    @Autowired
    public CourseService(CourseChooseRepository courseChooseRepository, UserRepository userRepository,
                         CollegeRepository collegeRepository, CourseRepository courseRepository) {
        this.courseChooseRepository = courseChooseRepository;
        this.userRepository = userRepository;
        this.collegeRepository = collegeRepository;
        this.courseRepository = courseRepository;
    }

    public String getCourseByCollegeAndPersonNumber(String collegeName, String personId) {
        UserEntity currentUser = userRepository.findByCollegeAndPersonNumber(collegeName, personId);
        //空查询处理
        if (currentUser == null) {
            JSONObject tempJson = new JSONObject();
            tempJson.put("success", false);
            return tempJson.toString();
        }
        //获取了该学生的课程列表
        List<CourseChooseEntity> courseChooseEntitiesList = courseChooseRepository.findByStudentId(currentUser.getId());
        try {
            //为最后传出的json
            JSONObject tmp = new JSONObject();
            //名字和说明分别对应前段的
            JSONArray result = new JSONArray();
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
                JSONObject position = new JSONObject();
                String courseTime = course.getClassTime();
                String[] classTime = courseTime.split(",");
                Integer[] indexOfDay = new Integer[20];
                Integer dayOfWeek = 0;
                for (String time : classTime) {
                    int timeLen = time.length();
                    int flagTmp = 0;
                    int index = 0;
                    Integer sumTmp = 0;
                    for (int i = 0; i < timeLen; i++) {
                        int chr = time.charAt(i);
                        if (chr < 48 || chr > 57) {
                            if (flagTmp == 0) {
                                flagTmp = 1;
                                dayOfWeek = sumTmp;
                            } else {
                                indexOfDay[index] = sumTmp;
                                index = index + 1;
                            }
                            sumTmp = 0;
                        } else sumTmp += (chr - 48);
                    }
                }
                position.put("dayOfWeek", dayOfWeek);
                position.put("indexOfDay", indexOfDay);
                jsonObject.put("position", position);
                JSONObject info = new JSONObject();
                info.put("name", course.getCourseName());
                info.put("teacher", course.getTeacherName());
                info.put("location", course.getClassroomLocation());
                jsonObject.put("info", info);
                result.put(jsonObject);
            }
            tmp.put("success", true);
            tmp.put("result", result);
            return tmp.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("success", false);
            return jsonObject.toString();
        }
    }
}
