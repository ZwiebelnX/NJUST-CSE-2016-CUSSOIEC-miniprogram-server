package com.ccwsz.server.service.course;

import com.ccwsz.server.dao.dock.course.checking.CourseCheckingInInfoRepository;
import com.ccwsz.server.dao.dock.course.checking.UserCourseCheckingInRepository;
import com.ccwsz.server.dao.dock.course.CourseRepository;
import com.ccwsz.server.dao.dock.user.UserRepository;
import com.ccwsz.server.dao.entity.CourseCheckingInInfoEntity;
import com.ccwsz.server.dao.entity.CourseEntity;
import com.ccwsz.server.dao.entity.UserCourseCheckingInEntity;
import com.ccwsz.server.dao.entity.UserEntity;
import com.ccwsz.server.service.util.JsonManage;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class CourseCheckingService {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final UserCourseCheckingInRepository userCourseCheckingInRepository;
    private final CourseCheckingInInfoRepository courseCheckingInInfoRepository;

    @Autowired
    public CourseCheckingService(UserRepository userRepository, CourseRepository courseRepository,
                                 UserCourseCheckingInRepository courseChooseRepository,
                                 CourseCheckingInInfoRepository courseCheckingInInfoRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.userCourseCheckingInRepository = courseChooseRepository;
        this.courseCheckingInInfoRepository = courseCheckingInInfoRepository;
    }

    //尝试签到
    public String tryCheckingIn(String collegeName, String personNumber, String courseNumber, Integer numOfWeek, Integer dayOfWeek, Integer indexOfDay) {
        return null;
    }

    //考勤状态
    public String getCheckingInState(String collegeName, String personNumber, long courseId) {
        JSONObject responseJson = new JSONObject();
        JSONObject result = new JSONObject();
        UserEntity currentUser = userRepository.findByCollegeAndPersonNumber(collegeName, personNumber);
        if (currentUser == null) {
            return JsonManage.buildFailureMessage("找不到用户！");
        }
        CourseEntity currentCourse = courseRepository.findById(courseId);
        if(currentCourse == null){
            return JsonManage.buildFailureMessage("找不到课程！");
        }
        if(currentCourse.getIsCheckingIn() == 1){
            result.put("isOpen", true);
        }
        else{
            result.put("isOpen", false);
        }
        List<CourseCheckingInInfoEntity> checkingInList =
                courseCheckingInInfoRepository.findByCourseIdOrderByBeginningTimeDesc(courseId);
        if(checkingInList == null){ //本门课没有签到记录
            result.put("hasChecked", false);
            result.put("time", "");
        }
        else{
            result.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").
                    format(checkingInList.get(0).getBeginningTime()));
            UserCourseCheckingInEntity userCheckingIn =
                    userCourseCheckingInRepository.findByCheckingInfoIdAndUserId(checkingInList.get(0).getId(),
                            currentUser.getId());
            if(userCheckingIn == null){ //本用户没有签到记录
                result.put("hasChecked", false);
            }
            else{
                if(userCheckingIn.getCheckingStatus() == 0){
                    result.put("hasChecked", true);
                }
                else{
                    result.put("hasChecked", false);
                }
            }
        }
        responseJson.put("result", result);
        responseJson.put("success", true);
        return null;
    }

    //获取签到历史
    public String getCheckingHistory(long courseId) {
        return null;
    }
}
