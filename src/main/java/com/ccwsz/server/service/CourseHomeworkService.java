package com.ccwsz.server.service;

import com.ccwsz.server.dao.dock.CourseHomeworkInfoRepository;
import com.ccwsz.server.dao.dock.CourseHomeworkQuestionRepository;
import com.ccwsz.server.dao.dock.UserHomeworkAnswerRepository;
import com.ccwsz.server.dao.dock.UserRepository;
import com.ccwsz.server.dao.entity.CourseHomeworkInfoEntity;
import com.ccwsz.server.dao.entity.CourseHomeworkQuestionEntity;
import com.ccwsz.server.dao.entity.UserEntity;
import com.ccwsz.server.dao.entity.UserHomeworkAnswerEntity;
import com.ccwsz.server.service.util.JsonManage;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseHomeworkService {
    private final CourseHomeworkInfoRepository courseHomeworkInfoRepository;
    private final UserRepository userRepository;
    private final UserHomeworkAnswerRepository userHomeworkAnswerRepository;
    private final CourseHomeworkQuestionRepository courseHomeworkQuestionRepository;

    @Autowired
    public CourseHomeworkService(CourseHomeworkInfoRepository courseHomeworkInfoRepository,
                                 UserRepository userRepository,
                                 UserHomeworkAnswerRepository userHomeworkAnswerRepository,
                                 CourseHomeworkQuestionRepository courseHomeworkQuestionRepository) {
        this.courseHomeworkInfoRepository = courseHomeworkInfoRepository;
        this.userRepository = userRepository;
        this.userHomeworkAnswerRepository = userHomeworkAnswerRepository;
        this.courseHomeworkQuestionRepository = courseHomeworkQuestionRepository;
    }

    //获取作业列表
    public String getCourseHomeworkList(String college, String personNumber, long courseId) {
        JSONObject responseJson = new JSONObject();
        JSONObject result = new JSONObject();
        JSONArray homeworkList = new JSONArray();

        UserEntity currentUser = userRepository.findByCollegeAndPersonNumber(college, personNumber); //当前用户
        if (currentUser == null) {
            return JsonManage.buildFailureMessage("找不到对应用户！");
        }
        List<CourseHomeworkInfoEntity> courseHomeworkList =
                courseHomeworkInfoRepository.findByCourseId(courseId); //所选课程的作业列表
        for (CourseHomeworkInfoEntity homeworkInfo : courseHomeworkList) {
            JSONObject homeworkInfoJson = new JSONObject();
            homeworkInfoJson.put("name", homeworkInfo.getName());
            homeworkInfoJson.put("homeworkID", homeworkInfo.getId());
            //设置当前用户是否已经作答本作业
            if(userHomeworkAnswerRepository.existsByHomeworkId(homeworkInfo.getId())){
                homeworkInfoJson.put("isFinished", true);
            }
            else{
                homeworkInfoJson.put("isFinished", false);
            }
            homeworkList.put(homeworkInfoJson);
        }
        result.put("homeworkList", homeworkList);
        responseJson.put("result", result);
        responseJson.put("success", true);
        return responseJson.toString();
    }

    public String getCourseSpecificHomework(String college, String personNumber, long homeworkId) {
        JSONObject responseJson = new JSONObject();
        JSONArray result = new JSONArray();
        UserEntity currentUser = userRepository.findByCollegeAndPersonNumber(college, personNumber);
        if (currentUser == null) {
            return JsonManage.buildFailureMessage("找不到用户！");
        }
        List<CourseHomeworkQuestionEntity> questionList = courseHomeworkQuestionRepository.
                findByHomeworkId(homeworkId);
        List<UserHomeworkAnswerEntity> userAnswers = userHomeworkAnswerRepository.
                findByHomeworkIdAndUserId(homeworkId, currentUser.getId());
        for (CourseHomeworkQuestionEntity question : questionList) {
            JSONObject questionJson = new JSONObject();
            String[] imageUrls = question.getImageUrls().split(";");
            String[] chooseList = question.getChooseText().split(";");
            questionJson.put("type", question.getQuestionType());
            questionJson.put("question", question.getQuestionText());
            JSONArray imageUrlsArray = new JSONArray();
            for (String url : imageUrls) {
                imageUrlsArray.put(url);
            }
            questionJson.put("imageURLs", imageUrlsArray);
            JSONArray chooseListArray = new JSONArray();
            for (String text : chooseList) {
                chooseListArray.put(text);
            }
            questionJson.put("choseList", chooseListArray);
            questionJson.put("correctAnswer", question.getCorrectAnswer());
            if (userAnswers.isEmpty()) {
                questionJson.put("userAnswer", "");
            } else {
                for (UserHomeworkAnswerEntity userAnswer : userAnswers) {
                    if (userAnswer.getQuestionId() == question.getId()) {
                        questionJson.put("userAnswer", userAnswer.getUserAnswer());
                        userAnswers.remove(userAnswer);
                        break;
                    } else {
                        questionJson.put("userAnswer", "");
                    }
                }
            }
            questionJson.put("choseList", chooseList);

            result.put(questionJson);
        }
        responseJson.put("result", result);
        responseJson.put("success",true);
        return responseJson.toString();
    }
}
