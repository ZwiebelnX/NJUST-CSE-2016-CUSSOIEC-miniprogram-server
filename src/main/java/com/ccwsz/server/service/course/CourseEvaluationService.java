package com.ccwsz.server.service.course;

import com.ccwsz.server.dao.dock.course.CourseChooseRepository;
import com.ccwsz.server.dao.dock.course.CourseRepository;
import com.ccwsz.server.dao.dock.course.evaluation.CourseEvaluationQuestionRepository;
import com.ccwsz.server.dao.dock.course.evaluation.UserEvaluationAnswerRepository;
import com.ccwsz.server.dao.dock.user.UserRepository;
import com.ccwsz.server.dao.entity.*;
import com.ccwsz.server.service.util.JsonManage;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
@Service
public class CourseEvaluationService {
    private final UserRepository userRepository;
    private final CourseChooseRepository courseChooseRepository;
    private final CourseEvaluationQuestionRepository courseEvaluationQuestionRepository;
    private final CourseRepository courseRepository;
    private final UserEvaluationAnswerRepository userEvaluationAnswerRepository;
    @Autowired
    public  CourseEvaluationService(UserRepository userRepository,CourseChooseRepository courseChooseRepository,
                                    CourseRepository courseRepository, UserEvaluationAnswerRepository userEvaluationAnswerRepository,CourseEvaluationQuestionRepository courseEvaluationQuestionRepository){
        this.userRepository=userRepository;
        this.courseChooseRepository=courseChooseRepository;
        this.courseEvaluationQuestionRepository=courseEvaluationQuestionRepository;
        this.courseRepository=courseRepository;
        this.userEvaluationAnswerRepository=userEvaluationAnswerRepository;
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
        if(evaluationQuestionList.size()==0){
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
    //提交评教作答
    public String submitEvaluationAnswer(String collegeName, String personNumber, long courseId, JSONArray userAnswerArray){
        JSONObject responseJson = new JSONObject(); //回应体
        JSONArray result = new JSONArray();
        UserEntity currentUser = userRepository.findByCollegeAndPersonNumber(collegeName, personNumber);
        if (currentUser == null) {
            return JsonManage.buildFailureMessage("找不到用户！");
        }
        List<CourseEvaluationQuestionEntity> evaluationQuestionList=courseEvaluationQuestionRepository.findByCourseId(courseId);
        if(evaluationQuestionList.size()==0){
            return JsonManage.buildFailureMessage("评教问题为空！请检查数据库");
        }
        //前置判断：学生选课
        if(courseChooseRepository.existsByCourseIdAndStudentId(courseId,currentUser.getId())){
            Iterator userAnswer = userAnswerArray.iterator();
            for(;userAnswer.hasNext();){
                JSONObject answerJson=(JSONObject)userAnswer.next();
                String userAnswerString;
                long answerIndex;
                try{
                    userAnswerString=answerJson.getString("userAnswer");
                    answerIndex = (long) answerJson.getInt("indexNum");
                }
                catch(Exception e){
                    e.printStackTrace();
                    return JsonManage.buildFailureMessage("json解析错误，请检查表单");
                }
                UserEvaluationAnswerEntity answerEntity = new UserEvaluationAnswerEntity();
                answerEntity.setUserAnswer(userAnswerString);
                answerEntity.setCourseId(courseId);
//                answerEntity.setGmtModified();
//                answerEntity.setGmtCreate();
//                answerEntity.setQuestionId(answerIndex);
                for(CourseEvaluationQuestionEntity question:evaluationQuestionList){
                    if(question.getId() == answerIndex){
                        answerEntity.setQuestionId(question.getId());
                        evaluationQuestionList.remove(question);
                        break;
                    }
                }
                answerEntity.setQuestionId(answerIndex);
                userEvaluationAnswerRepository.save(answerEntity);
            }
            return responseJson.put("success",true).toString();
        }
        else{
            return JsonManage.buildFailureMessage("学生未选择此课或作业不存在！");
        }
    }
}
