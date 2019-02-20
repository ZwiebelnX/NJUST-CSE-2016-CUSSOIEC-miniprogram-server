package com.ccwsz.server.service.course;

import com.ccwsz.server.dao.dock.course.homework.CourseHomeworkInfoRepository;
import com.ccwsz.server.dao.dock.course.homework.CourseHomeworkQuestionRepository;
import com.ccwsz.server.dao.dock.course.homework.UserHomeworkAnswerRepository;
import com.ccwsz.server.dao.dock.user.UserRepository;
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

    //获取特定的作业 个性化配置信息
    public String getCourseSpecificHomework(String college, String personNumber, long homeworkId) {
        JSONObject responseJson = new JSONObject(); //回应体
        JSONArray result = new JSONArray();
        UserEntity currentUser = userRepository.findByCollegeAndPersonNumber(college, personNumber);
        if (currentUser == null) {
            return JsonManage.buildFailureMessage("找不到用户！");
        }
        List<CourseHomeworkQuestionEntity> questionList = courseHomeworkQuestionRepository.
                findByHomeworkId(homeworkId); //查找指定作业的所有题目
        List<UserHomeworkAnswerEntity> userAnswerList = userHomeworkAnswerRepository.
                findByHomeworkIdAndUserId(homeworkId, currentUser.getId()); //查找用户答案，可以为空
        //构造result
        for(CourseHomeworkQuestionEntity question : questionList){
            JSONObject questionJson = new JSONObject();
            questionJson.put("type", question.getQuestionType());
            questionJson.put("questionID", question.getQuestionIndex());
            questionJson.put("question", question.getQuestionText());
            //构造图片url数组
            JSONArray imageURLs = new JSONArray();
            if(question.getImageUrls() != null){
                String[] imgUrls = question.getImageUrls().split(";");
                for(String url : imgUrls){
                    JSONObject urlJson = new JSONObject();
                    urlJson.put("url", url);
                    imageURLs.put(urlJson);
                }
            }
            questionJson.put("imageURLs", imageURLs);

            //构造选项数组
            JSONArray choseList = new JSONArray();
            String[] choseText = question.getChooseText().split(";");
            if(choseText.length != 0){
                for(String choose : choseText){
                    JSONObject chooseJson = new JSONObject();
                    String[] chooseIndexAndText = choose.split(":");
                    chooseJson.put("choseID", chooseIndexAndText[0]);
                    chooseJson.put("name", chooseIndexAndText[1]);
                    choseList.put(chooseJson);
                }
            }
            questionJson.put("choseList", choseList);

            //构造正确答案数组
            JSONArray correctAnswer = new JSONArray(); //正确答案的数组
            String[] answers = question.getCorrectAnswer().split(",");
            for(String s : answers){
                correctAnswer.put(s);
            }
            questionJson.put("correctAnswer", correctAnswer);

            //构造用户答案数组
            JSONArray userAnswersArray = new JSONArray();
            for(UserHomeworkAnswerEntity userAnswer : userAnswerList){
                if(userAnswer.getQuestionId() == question.getId()){
                    String[] answer = question.getCorrectAnswer().split(",");
                    for(String s : answer){
                        userAnswersArray.put(s);
                    }
                    userAnswerList.remove(userAnswer);
                    break;
                }
            }
            questionJson.put("userAnswer", userAnswersArray);

            //压入result
            result.put(questionJson);
        }
        responseJson.put("result", result);
        responseJson.put("success", true);
        return responseJson.toString();
    }
}
