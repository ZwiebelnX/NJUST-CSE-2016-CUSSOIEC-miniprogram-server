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
    public String getCourseHomeworkList(String openid, long courseId) {
        JSONObject responseJson = new JSONObject();
        JSONObject result = new JSONObject();
        JSONArray homeworkList = new JSONArray();

        UserEntity currentUser = userRepository.findByOpenid(openid); //当前用户
        if (currentUser == null) {
            return JsonManage.buildFailureMessage("找不到对应用户！");
        }
        List<CourseHomeworkInfoEntity> courseHomeworkList =
                courseHomeworkInfoRepository.findByCourseId(courseId); //所选课程的作业列表
        List<UserHomeworkAnswerEntity> userAnsweredList =
                userHomeworkAnswerRepository.findByUserId(currentUser.getId()); //当前用户在当前课程下的答题情况
        for (CourseHomeworkInfoEntity homeworkInfo : courseHomeworkList) {
            JSONObject homeworkInfoJson = new JSONObject();
            homeworkInfoJson.put("name", homeworkInfo.getName());
            homeworkInfoJson.put("homeworkID", homeworkInfo.getId());
            //设置当前用户是否已经作答本作业
            if (userAnsweredList.isEmpty()) {
                homeworkInfoJson.put("isFinished", false);
            } else {
                for (UserHomeworkAnswerEntity userAnswer : userAnsweredList) {
                    if (userAnswer.getHomeworkId() == homeworkInfo.getId()) {
                        homeworkInfoJson.put("isFinished", true);
                        userAnsweredList.remove(userAnswer);
                        break;
                    } else {
                        homeworkInfoJson.put("isFinished", false);
                    }
                }
            }
            homeworkList.put(homeworkInfoJson);
        }
        result.put("homeworkList", homeworkList);
        responseJson.put("result", result);
        responseJson.put("success", true);
        return responseJson.toString();
    }

    public String getCourseSpecificHomework(long homeworkId, String openid) {
        JSONObject responseJson = new JSONObject();
        JSONArray result = new JSONArray();
        UserEntity currentUser = userRepository.findByOpenid(openid);
        if (currentUser == null) {
            return JsonManage.buildFailureMessage("找不到用户！");
        }
        List<CourseHomeworkQuestionEntity> questionList = courseHomeworkQuestionRepository.
                findByHomeworkId(homeworkId);
        UserHomeworkAnswerEntity userAnswerEntity = userHomeworkAnswerRepository.
                findByHomeworkIdAndUserId(homeworkId, currentUser.getId());
        String[] userAnswers;
        if (userAnswerEntity == null) {
            userAnswers = null;
        } else {
            userAnswers = userAnswerEntity.getUserAnswer().split(";");
        }

        for (int index = 0; index <= questionList.size() - 1; index++) {
            CourseHomeworkQuestionEntity question = questionList.get(index);
            JSONObject questionJson = new JSONObject();
            String[] imgUrls = question.getImageUrls().split(";");
            String[] chooseTexts = question.getChooseText().split(";");

            questionJson.put("type", question.getQuestionType());
            questionJson.put("question", question.getQuestionText());
            JSONArray imageURLs = new JSONArray();
            for (String url : imgUrls) {
                imageURLs.put(url);
            }
            questionJson.put("imageURLs", imageURLs);
            JSONArray chooseList = new JSONArray();
            for (String text : chooseTexts) {
                chooseList.put(text);
            }
            questionJson.put("correctAnswer", question.getCorrectAnswer());
            if(userAnswers == null){ //空指针保护
                questionJson.put("userAnswer", "");
            }else if (index >= userAnswers.length) { //越界保护
                questionJson.put("userAnswer", "Internal Error! [index out of bound]");
            } else {
                questionJson.put("userAnswer", userAnswers[index]);
            }
            result.put(questionJson);
        }
        responseJson.put("result", result);
        responseJson.put("success", true);
        return responseJson.toString();
    }
}
