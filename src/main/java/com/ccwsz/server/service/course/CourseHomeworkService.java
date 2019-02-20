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
        List<UserHomeworkAnswerEntity> userAnswers = userHomeworkAnswerRepository.
                findByHomeworkIdAndUserId(homeworkId, currentUser.getId()); //查找用户答案，可以为空
        //设置作业信息
        for (CourseHomeworkQuestionEntity question : questionList) {
            JSONObject questionJson = new JSONObject();
            String[] imageUrls = question.getImageUrls().split(";");
            String[] chooseList = question.getChooseText().split(";");
            questionJson.put("type", question.getQuestionType());
            questionJson.put("question", question.getQuestionText());
            //设置图片url
            JSONArray imageUrlsArray = new JSONArray();
            for (String url : imageUrls) {
                JSONObject urlJson = new JSONObject();
                urlJson.put("url", url);
                imageUrlsArray.put(urlJson);
            }
            questionJson.put("imageURLs", imageUrlsArray);
            //设置选项
            JSONArray chooseListArray = new JSONArray();
            for (String text : chooseList) {
                chooseListArray.put(text);
            }
            questionJson.put("choseList", chooseListArray);

            questionJson.put("correctAnswer", question.getCorrectAnswer());
            //设置用户答案
            if (userAnswers.isEmpty()) { //为空则全部填充空字符串
                questionJson.put("userAnswer", "");
            } else {
                for (UserHomeworkAnswerEntity userAnswer : userAnswers) {
                    if (userAnswer.getQuestionId() == question.getId()) {
                        questionJson.put("userAnswer", userAnswer.getUserAnswer());
                        userAnswers.remove(userAnswer);
                        break;
                    } else { //userAnswers不全保护
                        questionJson.put("userAnswer", "");
                    }
                }
            }
            questionJson.put("choseList", chooseList);
            result.put(questionJson);//信息构造完毕 写入result
        }
        responseJson.put("result", result);
        responseJson.put("success",true);
        return responseJson.toString();
    }
}
