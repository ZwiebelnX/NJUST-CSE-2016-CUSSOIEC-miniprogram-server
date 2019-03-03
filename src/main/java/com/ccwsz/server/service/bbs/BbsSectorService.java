package com.ccwsz.server.service.bbs;

import com.ccwsz.server.dao.dock.bbs.BbsSectorRepository;
import com.ccwsz.server.dao.dock.course.CourseChooseRepository;
import com.ccwsz.server.dao.dock.user.UserRepository;
import com.ccwsz.server.dao.entity.CourseChooseEntity;
import com.ccwsz.server.dao.entity.UserEntity;
import com.ccwsz.server.service.util.JsonManage;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BbsSectorService {
    private final BbsSectorRepository bbsSectorRepository;
    private final UserRepository userRepository;
    private final CourseChooseRepository courseChooseRepository;
    @Autowired
    public BbsSectorService(BbsSectorRepository bbsSectorRepository,UserRepository userRepository,CourseChooseRepository courseChooseRepository){
        this.bbsSectorRepository=bbsSectorRepository;
        this.userRepository=userRepository;
        this.courseChooseRepository=courseChooseRepository;
    }
    public String getSectors(String collegeName,String personNumber){
        JSONObject responseJson = new JSONObject(); //为最后传出的json
        UserEntity currentUser = userRepository.findByCollegeAndPersonNumber(collegeName, personNumber);
        if (currentUser == null) {
            return JsonManage.buildFailureMessage("未找到用户！");
        }
        //获取该学生课程列表
        List<CourseChooseEntity> courseChooseEntitiesList = courseChooseRepository.findByStudentId(currentUser.getId());
        if (courseChooseEntitiesList == null) {
            responseJson.put("success", true);
            responseJson.put("result", new JSONObject());
            return responseJson.toString();
        }
        List<JSONObject> result = new ArrayList<>();
        for(CourseChooseEntity courseChoose : courseChooseEntitiesList){
            JSONObject resultTmp=new JSONObject();
            long courseId=courseChoose.getCourseId();
            resultTmp.put("courseID",courseId);
            long teacherId=courseChoose.getTeacherId();
            UserEntity userEntity=userRepository.findById(teacherId);
            String teacherName=userEntity.getRealName();
            resultTmp.put("teacher",teacherName);
            BbsSectorEntity currentSector=bbsSectorRepository.findByCourseId(courseId);
            String name=currentSector.getSectorName();
            resultTmp.put("name",name);
            long clickingRate=currentSector.getClickingRate();
            long topicCount=currentSector.getTopicCount();
            resultTmp.put("clickingRate",clickingRate);
            resultTmp.put("topicCount",topicCount);
            result.add(resultTmp);
        }
        responseJson.put("success",true);
        responseJson.put("result",result);
        return responseJson.toString();
    }
}
