package com.ccwsz.server.service.course;

import com.ccwsz.server.dao.dock.course.CourseChooseRepository;
import com.ccwsz.server.dao.dock.course.checking.CourseCheckingInInfoRepository;
import com.ccwsz.server.dao.dock.course.checking.UserCourseCheckingInRepository;
import com.ccwsz.server.dao.dock.course.CourseRepository;
import com.ccwsz.server.dao.dock.user.UserRepository;
import com.ccwsz.server.dao.entity.CourseCheckingInInfoEntity;
import com.ccwsz.server.dao.entity.CourseEntity;
import com.ccwsz.server.dao.entity.UserCourseCheckingInEntity;
import com.ccwsz.server.dao.entity.UserEntity;
import com.ccwsz.server.service.util.JsonManage;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class CourseCheckingService {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final UserCourseCheckingInRepository userCourseCheckingInRepository;
    private final CourseCheckingInInfoRepository courseCheckingInInfoRepository;
    private final CourseChooseRepository courseChooseRepository;

    @Autowired
    public CourseCheckingService(UserRepository userRepository, CourseRepository courseRepository,
                                 UserCourseCheckingInRepository userCourseChooseRepository,
                                 CourseCheckingInInfoRepository courseCheckingInInfoRepository,
                                 CourseChooseRepository courseChooseRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.userCourseCheckingInRepository = userCourseChooseRepository;
        this.courseCheckingInInfoRepository = courseCheckingInInfoRepository;
        this.courseChooseRepository = courseChooseRepository;
    }

    //尝试签到
    public String tryCheckingIn(String collegeName, String personNumber, long courseId, Timestamp time) {
        JSONObject responseJson = new JSONObject();
        UserEntity currentUser = userRepository.findByCollegeAndPersonNumber(collegeName, personNumber);
        if(currentUser == null){
            return JsonManage.buildFailureMessage("找不到用户！");
        }
        CourseEntity currentCourse = courseRepository.findById(courseId);
        if(currentCourse == null){
            return JsonManage.buildFailureMessage("找不到课程！");
        }
        CourseCheckingInInfoEntity checkingInfo = courseCheckingInInfoRepository.findByBeginningTime(time);
        if(checkingInfo == null){
            return JsonManage.buildFailureMessage("指定签到还未有记录，请稍候再试");
        }
        UserCourseCheckingInEntity userCheckingIn = userCourseCheckingInRepository
                .findByCheckingInfoIdAndUserId(checkingInfo.getId(), currentUser.getId());
        if(userCheckingIn == null){
            return JsonManage.buildFailureMessage("当前用户在当前签到中没有记录！");
        }
        if(currentCourse.getIsCheckingIn() == 1){
            userCheckingIn.setCheckingStatus((byte)0);
            userCourseCheckingInRepository.save(userCheckingIn);
            responseJson.put("success", true);
            return responseJson.toString();
        }
        else{
            return JsonManage.buildFailureMessage("当前课程还未开始签到！");
        }

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
        //课程是否正在签到
        if(currentCourse.getIsCheckingIn() == 1){
            result.put("isOpen", true);
        }
        else{
            result.put("isOpen", false);
            result.put("time", "");
        }
        List<CourseCheckingInInfoEntity> checkingInList =
                courseCheckingInInfoRepository.findByCourseIdOrderByBeginningTimeDesc(courseId); //签到表，按时间降序排序
        if(checkingInList == null){ //本门课没有签到记录
            result.put("hasChecked", false);
        }
        else{
            if(currentCourse.getIsCheckingIn() == 1){
                result.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").
                        format(checkingInList.get(0).getBeginningTime()));
            }
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
        return responseJson.toString();
    }

    //获取签到历史
    public String getCheckingHistory(long courseId) {
        JSONObject responseJson = new JSONObject();
        JSONArray result = new JSONArray();
        List<CourseCheckingInInfoEntity> courseCheckingInInfoList =
                courseCheckingInInfoRepository.findByCourseIdOrderByBeginningTimeDesc(courseId);
        for(CourseCheckingInInfoEntity checkingInInfo : courseCheckingInInfoList){
            JSONObject checkingInfoJson = new JSONObject();
            checkingInfoJson.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            .format(checkingInInfo.getBeginningTime()));
            checkingInfoJson.put("count",
                    userCourseCheckingInRepository.countByCheckingInfoId(checkingInInfo.getId()));
            result.put(checkingInfoJson);
            checkingInfoJson.put("expectation", courseChooseRepository.countByCourseId(courseId));
        }
        responseJson.put("result",result);
        responseJson.put("success", true);
        return responseJson.toString();
    }
}
