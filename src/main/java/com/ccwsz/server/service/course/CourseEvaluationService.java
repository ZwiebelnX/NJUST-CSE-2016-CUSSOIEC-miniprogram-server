package com.ccwsz.server.service.course;

import com.ccwsz.server.dao.dock.course.CourseChooseRepository;
import com.ccwsz.server.dao.dock.course.CourseRepository;
import com.ccwsz.server.dao.dock.course.evaluation.CourseEvaluationQuestionRepository;
import com.ccwsz.server.dao.dock.user.UserRepository;
import com.ccwsz.server.dao.entity.CourseChooseEntity;
import com.ccwsz.server.dao.entity.CourseEntity;
import com.ccwsz.server.dao.entity.CourseEvaluationQuestionEntity;
import com.ccwsz.server.dao.entity.UserEntity;
import com.ccwsz.server.service.util.JsonManage;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CourseEvaluationService {
    private final UserRepository userRepository;
    private final CourseChooseRepository courseChooseRepository;
    private final CourseEvaluationQuestionRepository courseEvaluationQuestionRepository;
    private final CourseRepository courseRepository;
    @Autowired
    public  CourseEvaluationService(UserRepository userRepository,CourseChooseRepository courseChooseRepository,
                                    CourseRepository courseRepository,CourseEvaluationQuestionRepository courseEvaluationQuestionRepository){
        this.userRepository=userRepository;
        this.courseChooseRepository=courseChooseRepository;
        this.courseEvaluationQuestionRepository=courseEvaluationQuestionRepository;
        this.courseRepository=courseRepository;
    }
    //获取评教问卷列表
    public String getCourseEvaluationList(String college, String personNumber, long courseId){
        JSONObject responseJson = new JSONObject();
        JSONObject result = new JSONObject();
        JSONArray evaluationList = new JSONArray();
        UserEntity currentUser = userRepository.findByCollegeAndPersonNumber(college, personNumber); //当前用户
        if (currentUser == null) {
            return JsonManage.buildFailureMessage("找不到对应用户！");
        }

        long personId=currentUser.getId();
        CourseChooseEntity currentCourseChoose= courseChooseRepository.findByStudentIdAndCourseId(personId,courseId);
        if(currentCourseChoose == null){
            return JsonManage.buildFailureMessage("您未选该门课，不能进行评教！");
        }

        CourseEntity currentCourse=courseRepository.findById(courseId);
        result.put("isOpen",currentCourse.getIsEvaluated());
        List<CourseEvaluationQuestionEntity> evaluationQuestionList=courseEvaluationQuestionRepository.findByCourseId(courseId);
        if(evaluationList.length()==0){
            return JsonManage.buildFailureMessage("未有评教问题!");
        }
        for(CourseEvaluationQuestionEntity question:evaluationQuestionList) {
            JSONObject questionJson = new JSONObject();
            questionJson.put("type", question.getQuestionType());
            questionJson.put("questionID", question.getId());
            questionJson.put("question", question.getQuestionText());
            //构造选项数组
            JSONArray choseList = new JSONArray();
            String[] choseText = question.getChooseText().split(";");
            if (choseText.length != 0) {
                for (String choose : choseText) {
                    JSONObject chooseJson = new JSONObject();
                    String[] chooseIndexAndText = choose.split(":");
                    chooseJson.put("choseID", chooseIndexAndText[0]);
                    chooseJson.put("name", chooseIndexAndText[0]);
                    choseList.put(chooseJson);
                }
            }
            questionJson.put("choseList", choseList);
            evaluationList.put(questionJson);
        }
        result.put("evaluationList",evaluationList);
        responseJson.put("result", result);
        responseJson.put("success", true);
        return responseJson.toString();
    }
}
