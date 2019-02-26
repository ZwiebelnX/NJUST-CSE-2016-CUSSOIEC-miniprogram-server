package com.ccwsz.server.service.course;

import com.ccwsz.server.dao.dock.course.CourseChooseRepository;
import com.ccwsz.server.dao.dock.course.CourseRepository;
import com.ccwsz.server.dao.dock.course.homework.CourseHomeworkInfoRepository;
import com.ccwsz.server.dao.dock.course.homework.CourseHomeworkQuestionRepository;
import com.ccwsz.server.dao.dock.course.homework.UserHomeworkAnswerRepository;
import com.ccwsz.server.dao.dock.user.UserRepository;
import com.ccwsz.server.dao.entity.*;
import com.ccwsz.server.service.util.JsonManage;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class CourseHomeworkService {
    private final CourseHomeworkInfoRepository courseHomeworkInfoRepository;
    private final UserRepository userRepository;
    private final UserHomeworkAnswerRepository userHomeworkAnswerRepository;
    private final CourseHomeworkQuestionRepository courseHomeworkQuestionRepository;
    private final CourseChooseRepository courseChooseRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public CourseHomeworkService(CourseHomeworkInfoRepository courseHomeworkInfoRepository,
                                 UserRepository userRepository,
                                 UserHomeworkAnswerRepository userHomeworkAnswerRepository,
                                 CourseHomeworkQuestionRepository courseHomeworkQuestionRepository,
                                 CourseChooseRepository courseChooseRepository,
                                 CourseRepository courseRepository) {
        this.courseHomeworkInfoRepository = courseHomeworkInfoRepository;
        this.userRepository = userRepository;
        this.userHomeworkAnswerRepository = userHomeworkAnswerRepository;
        this.courseHomeworkQuestionRepository = courseHomeworkQuestionRepository;
        this.courseChooseRepository = courseChooseRepository;
        this.courseRepository = courseRepository;
    }

    //获取作业列表
    public String getCourseHomeworkList(String college, String personNumber, long courseId) {
        JSONObject responseJson = new JSONObject();
        JSONArray result = new JSONArray();
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
            if(userHomeworkAnswerRepository.existsByUserId(currentUser.getId())){
                homeworkInfoJson.put("isFinished", true);
            }
            else{
                homeworkInfoJson.put("isFinished", false);
            }
            result.put(homeworkInfoJson);
        }
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
            questionJson.put("questionIndex", question.getQuestionIndex());
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
                    chooseJson.put("choseIndex", chooseIndexAndText[0]);
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

    //学生提交作业
    public String submitHomeworkAnswer(String collegeName, String personNumber, long courseId, long homeworkId,
                                       JSONArray userAnswerArray){
        JSONObject responseJson = new JSONObject(); //回应体
        JSONArray result = new JSONArray();
        UserEntity currentUser = userRepository.findByCollegeAndPersonNumber(collegeName, personNumber);
        if (currentUser == null) {
            return JsonManage.buildFailureMessage("找不到用户！");
        }
        //前置判断：学生选课 && 作业存在
        if(courseChooseRepository.existsByCourseIdAndStudentId(courseId, currentUser.getId()) &&
         courseHomeworkInfoRepository.existsById(homeworkId)){
            List<CourseHomeworkQuestionEntity> questionList =
                    courseHomeworkQuestionRepository.findByHomeworkId(homeworkId);
            if(questionList.isEmpty()){
                return JsonManage.buildFailureMessage("所选作业题库为空！请检查数据库");
            }
            Iterator userAnswer = userAnswerArray.iterator();
            for(;userAnswer.hasNext();){
                JSONObject answerJson = (JSONObject)userAnswer.next();
                String userAnswerString="";
                boolean userAnswerIsCorrect;
                byte answerIndex;
                try{
                    JSONArray userAnswerJSONArray=answerJson.getJSONArray("userAnswer");
                    Iterator userAnswerTmp=userAnswerJSONArray.iterator();
                    for(;userAnswerTmp.hasNext();){
                        String tmp=(String)userAnswerTmp.next();
                        userAnswerString+=tmp;
                        if(userAnswerTmp.hasNext()){
                            userAnswerString+=",";
                        }
                    }
                    userAnswerIsCorrect = answerJson.getBoolean("isCorrect");
                    answerIndex = (byte)answerJson.getInt("indexNum");
                } catch (Exception e){
                    e.printStackTrace();
                    return JsonManage.buildFailureMessage("json解析错误，请检查表单");
                }
                UserHomeworkAnswerEntity answerEntity = new UserHomeworkAnswerEntity();
                answerEntity.setUserId(currentUser.getId());
                answerEntity.setHomeworkId(homeworkId);
                answerEntity.setUserAnswer(userAnswerString);
                if(userAnswerIsCorrect){
                    answerEntity.setIsCorrect((byte)1);
                }
                else{
                    answerEntity.setIsCorrect((byte)0);
                }
                for(CourseHomeworkQuestionEntity question : questionList){
                    if(question.getQuestionIndex() == answerIndex){
                        answerEntity.setQuestionId(question.getId());
                        questionList.remove(question);
                        break;
                    }
                }
                userHomeworkAnswerRepository.save(answerEntity);
            }
            return responseJson.put("success",true).toString();
        }
        else{
            return JsonManage.buildFailureMessage("学生未选择此课或作业不存在！");
        }

    }

    //发布作业
    public String postHomework(String collegeName, String personNumber, long courseId, String homeowrkName, JSONArray data){
        JSONObject responseJson = new JSONObject(); //回应体
        UserEntity currentUser = userRepository.findByCollegeAndPersonNumber(collegeName, personNumber);
        if (currentUser == null) {
            return JsonManage.buildFailureMessage("找不到用户！");
        }
        CourseEntity currentCourse = courseRepository.findById(courseId);
        if(currentCourse == null){
            return JsonManage.buildFailureMessage("找不到课程！");
        }
        //前置鉴权
        if(currentUser.getUserType().equals("teacher") && currentCourse.getTeacherId() == currentUser.getId()){
            //添加作业基本信息
            CourseHomeworkInfoEntity homeworkInfoEntity = new CourseHomeworkInfoEntity();
            homeworkInfoEntity.setName(homeowrkName);
            homeworkInfoEntity.setCourseId(currentCourse.getId());
            courseHomeworkInfoRepository.save(homeworkInfoEntity);
            //添加作业题目
            long homeworkId = homeworkInfoEntity.getId();
            Iterator questionIterator = data.iterator();
            for(;questionIterator.hasNext();){
                JSONObject questionJson = (JSONObject)questionIterator.next();
                CourseHomeworkQuestionEntity questionEntity = new CourseHomeworkQuestionEntity();
                try{
                    questionEntity.setQuestionType((byte)questionJson.getInt("type"));
                    questionEntity.setHomeworkId(homeworkId);
                    questionEntity.setQuestionText(questionJson.getString("question"));
                    questionEntity.setQuestionIndex((byte)Integer.parseInt(questionJson.getString("questionIndex")));
                    Iterator imageURLsIterator = questionJson.getJSONArray("imageURLs").iterator();
                    String imageURLsString = "";
                    for(; imageURLsIterator.hasNext();){
                        JSONObject url = (JSONObject)imageURLsIterator.next();
                        imageURLsString = imageURLsString + url.getString("url");
                        if(imageURLsIterator.hasNext()){
                            imageURLsString += ";";
                        }
                    }
                    questionEntity.setImageUrls(imageURLsString);
                    Iterator chooseIterator = questionJson.getJSONArray("choseList").iterator();
                    String chooseString = "";
                    for(; chooseIterator.hasNext();){
                        JSONObject choose = (JSONObject)chooseIterator.next();
                        chooseString += choose.getString("choseIndex") + ":";
                        chooseString += choose.getString("name");
                        if(chooseIterator.hasNext()){
                            chooseString += ";";
                        }
                    }
                    questionEntity.setChooseText(chooseString);
                    String answerString = "";
                    Iterator answerIterator = questionJson.getJSONArray("correctAnswer").iterator();
                    for(; answerIterator.hasNext();){
                        String answer = (String)answerIterator.next();
                        answerString += answer;
                        if(answerIterator.hasNext()){
                            answerString += ",";
                        }
                    }
                    questionEntity.setCorrectAnswer(answerString);
                } catch (Exception e){
                    e.printStackTrace();
                    return JsonManage.buildFailureMessage("数据格式错误！请检查格式");
                }
                courseHomeworkQuestionRepository.save(questionEntity);
            }
            responseJson.put("success", true);
            return responseJson.toString();
        }
        else{
            return JsonManage.buildFailureMessage("鉴权错误！请联系管理员");
        }
    }
}
