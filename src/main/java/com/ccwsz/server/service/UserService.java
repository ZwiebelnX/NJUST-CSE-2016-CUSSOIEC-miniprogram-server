package com.ccwsz.server.service;

import com.ccwsz.server.dao.dock.*;
import com.ccwsz.server.dao.entity.*;
import com.ccwsz.server.service.util.GlobalData;
import com.ccwsz.server.service.util.JsonManage;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;
    private CollegeRepository collegeRepository;
    private CourseRepository courseRepository;
    private CourseChooseRepository courseChooseRepository;
    private CourseCheckingInRepository courseCheckingInRepository;
    @Autowired
    public UserService(UserRepository userRepository, CollegeRepository collegeRepository,CourseRepository courseRepository
                        ,CourseChooseRepository courseChooseRepository,CourseCheckingInRepository courseCheckingInRepository) {
        this.userRepository = userRepository;
        this.collegeRepository = collegeRepository;
        this.courseRepository = courseRepository;
        this.courseChooseRepository=courseChooseRepository;
        this.courseCheckingInRepository=courseCheckingInRepository;
    }

    //获取学号
    public String getStudentNumber(String openId) {
        JSONObject jsonObject = new JSONObject();
        try {
            UserEntity student = userRepository.findByOpenid(openId);
            if (student != null) {
                String studentId = student.getPersonNumber();
                String collegeName = student.getCollege();
                jsonObject.put("success", true);
                JSONObject result = new JSONObject();
                result.put("college", collegeName);
                result.put("personID", studentId);
                jsonObject.put("result", result);
            } else jsonObject.put("success", false);
        } catch (Exception e) {
            e.printStackTrace();
            jsonObject.put("reason","未能获取学号");
            jsonObject.put("success", false);
        }
        return jsonObject.toString();
    }

    /*
    获取openid
    同时定义用户登录状态
     */
    public String getOpenId(String code) {
        JSONObject resultJson = new JSONObject();
        try {
            String url = "https://api.weixin.qq.com/sns/jscode2session"; //微信服务器API端口
            //构造请求参数
            String params = "appid=" + GlobalData.APP_ID
                    + "&secret=" + GlobalData.APP_SECRET
                    + "&grant_type=" + GlobalData.GRANT_TYPE
                    + "&js_code=" + code;
            url = url + "?" + params;
            //向微信服务器发送请求
            OkHttpClient httpClient = new OkHttpClient();
            final Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
            final Call call = httpClient.newCall(request);
            Response response = call.execute();
            if (response.isSuccessful()) {
                if (response.body() != null) {
                    JSONObject tempJson = new JSONObject(response.body().string());
                    System.out.println(tempJson.toString());
                    if (!tempJson.has("errcode") || tempJson.getInt("errcode") == 0) {
                        UserEntity newUser = new UserEntity();
                        newUser.setOpenid(tempJson.getString("openid"));
                        newUser.setUserType("student");
                        userRepository.save(newUser);
                        resultJson.put("result", tempJson.getString("openid"));
                        resultJson.put("success", true);
                    } else {
                        switch (tempJson.getInt("errcode")) {
                            case -1:
                                return JsonManage.buildFailureMessage("微信服务器忙，稍后重试");
                            case 40029:
                                return JsonManage.buildFailureMessage("code无效");
                            case 45011:
                                return JsonManage.buildFailureMessage("请求次数过多，请过多几分钟重试");
                            default:
                                return JsonManage.buildFailureMessage("请求无效，请重试");
                        }
                    }
                }
            } else {
                return JsonManage.buildFailureMessage("微信服务器连接失败！请稍后再试");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return JsonManage.buildFailureMessage("Internal error!");
        }
        return resultJson.toString();
    }

    //获取周次信息
    public String getWeekMessage(String data, String collegeName, String personId) {
        JSONObject result = new JSONObject();
        try {
            //获取开学时间以字符串的形式
            CollegeEntity currentCollege = collegeRepository.findByName(collegeName);
            //System.out.print(personId+" "+collegeName);
            UserEntity currentUser = userRepository.findByCollegeAndPersonNumber(collegeName, personId);
            //空查询处理
            //System.out.println(currentUser.getNickName()+" "+currentCollege.getOpeningDate1());
            if (currentUser == null) {
                return JsonManage.buildFailureMessage("user not found!");
            }
            String grade = currentUser.getGrade();
            Date openingTime;
            Date endWeek = currentCollege.getEndingDate();
            switch (grade) {
                case "本科一年级":
                    openingTime = currentCollege.getOpeningDate1();
                    break;
                case "本科二年级":
                    openingTime = currentCollege.getOpeningDate2();
                    break;
                case "本科三年级":
                    openingTime = currentCollege.getOpeningDate3();
                    break;
                case "本科四年级":
                    openingTime = currentCollege.getOpeningDate4();
                    break;
                default:
                    openingTime = currentCollege.getOpeningDate1();
                    break;

            }
            //将时间戳转换为Date
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date currentDate = format.parse(data);
            long diffEnd = currentDate.getTime() - openingTime.getTime();
            long countEnd = endWeek.getTime() - openingTime.getTime();
            int currentWeek = (int) (diffEnd / (24 * 60 * 60 * 1000) / 7);
            int countWeek = (int) (countEnd / (24 * 60 * 60 * 1000) / 7);
            List<Integer> range = new ArrayList<>();
            range.add(1);
            range.add(countWeek + 1);
            result.put("success", true);
            JSONObject resultTmp = new JSONObject();
            resultTmp.put("numOfWeek", currentWeek + 1);
            resultTmp.put("range", range);
            result.put("result", resultTmp);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("reason","获取周次信息失败!");
        }
        return result.toString();
    }

    //绑定学号/.教工号
    @Transactional
    public String bindingUser(String openId, String college, String personNumber, String realName, String nickName) {
        JSONObject result = new JSONObject();
        try {
            UserEntity currentUser = userRepository.findByOpenid(openId);
            if (currentUser == null) {
                result.put("success", false);
            } else {
                currentUser.setPersonNumber(personNumber);
                currentUser.setRealName(realName);
                currentUser.setNickName(nickName);
                currentUser.setCollege(college);
                userRepository.save(currentUser);
                result.put("success", true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonManage.buildFailureMessage("数据存储失败！");
        }
        result.put("success", true);
        return result.toString();
    }

    //获取用户信息
    public String getUserInfo(String openId) {
        JSONObject responseJson = new JSONObject();
        UserEntity currentUser = userRepository.findByOpenid(openId);
        if (currentUser == null) {
            //TODO 空查询处理
            return JsonManage.buildFailureMessage("用户不存在");
        }
        responseJson.put("success", true);
        JSONObject result = new JSONObject();
        result.put("userType", currentUser.getUserType());
        result.put("college", currentUser.getCollege());
        result.put("personID", currentUser.getPersonNumber());
        result.put("realName", currentUser.getRealName());
        result.put("nickName", currentUser.getNickName());
        result.put("gender", currentUser.getGender());
        result.put("grade", currentUser.getGrade());
        result.put("academy", currentUser.getAcademy());
        result.put("major", currentUser.getMajor());
        result.put("phone", currentUser.getPhone());
        result.put("email", currentUser.getEmail());
        responseJson.put("result", result);
        return responseJson.toString();
    }

    //修改用户信息
    public String changeUserInfo(String college, String personNumber, JSONObject data) {
        UserEntity currentUser = userRepository.findByCollegeAndPersonNumber(college, personNumber);
        if (currentUser == null) {
            return JsonManage.buildFailureMessage("用户不存在！");
        }
        currentUser.setNickName(data.getString("nickName"));
        currentUser.setPhone(data.getString("phone"));
        currentUser.setEmail(data.getString("email"));
        try {
            userRepository.save(currentUser);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonManage.buildFailureMessage("数据存储失败！请检查数据格式");
        }
        JSONObject resultJson = new JSONObject();
        resultJson.put("success", true);
        return resultJson.toString();
    }

    //尝试签到
    @Transactional
    public String tryCheckingIn(String collegeName, String personNumber, String courseNumber, Integer numOfWeek, Integer dayOfWeek, Integer indexOfDay){
        UserEntity currentUser=userRepository.findByCollegeAndPersonNumber(collegeName,personNumber); //当前用户
        JSONObject jsonObject=new JSONObject();
        if(currentUser==null){
            return JsonManage.buildFailureMessage("用户不存在！");
        }
        CourseEntity currentCourse=courseRepository.findByCourseNumber(courseNumber);
        if(currentCourse==null){
            return JsonManage.buildFailureMessage("不存在该门课程！");
        }
        String isChecking=Byte.toString(currentCourse.getIsCheckingIn());
        if(isChecking.equals("0")){
            return JsonManage.buildFailureMessage("课程还未开始签到！");
        }
        //获取选课列表避免错误签到

        //改成查询签到表内是否存在该学生即可
        Long userId=currentUser.getId();
        Long courseId=currentCourse.getId();
//        CourseChooseEntity currentCourseChoose=courseChooseRepository.findByStudentIdAndCourseId(userId,courseId);
//        if(currentCourseChoose==null){
//            JsonManage.buildFailureMessage("您未选该门课，不能签到！");
//        }
        try {
            CourseCheckingInEntity courseCheckingInEntity = courseCheckingInRepository.findByUserIdAndCourseId(userId,courseId);
            if(courseCheckingInEntity==null){
                return JsonManage.buildFailureMessage("您未选该门课，不能签到！");
            }
            courseCheckingInEntity.setCheckingStatus("按时签到");
            courseCheckingInEntity.setWeekNum(numOfWeek);
            courseCheckingInEntity.setDayNum(dayOfWeek);
            courseCheckingInEntity.setIndexNum(indexOfDay);
            courseCheckingInRepository.save(courseCheckingInEntity);
            jsonObject.put("success", true);
            return jsonObject.toString();
        }
        catch (Exception e){
            e.printStackTrace();
            return JsonManage.buildFailureMessage("数据存储失败！请检查数据格式");
        }
    }

    //考勤状态
    public String getCheckingInState(String collegeName,String personNumber,String courseNumber){
        UserEntity currentUser = userRepository.findByCollegeAndPersonNumber(collegeName, personNumber);
        JSONObject resultJSON=new JSONObject();
        if(currentUser==null){
            return JsonManage.buildFailureMessage("此用户不存在！");
        }
        CourseEntity currentCourse=courseRepository.findByCourseNumber(courseNumber);
        if(currentCourse==null){
            return JsonManage.buildFailureMessage("此课程不存在！");
        }
        Long personId=currentUser.getId();
        Long courseId=currentCourse.getId();
        CourseCheckingInEntity courseCheckingInEntity=courseCheckingInRepository.findByUserIdAndCourseId(personId,courseId);
        if(courseCheckingInEntity==null){
            return JsonManage.buildFailureMessage("该课您还未有考勤情况！");
        }
        String isChecking=Byte.toString(currentCourse.getIsCheckingIn()); //为啥要转成String再比较？
        JSONObject result=new JSONObject();
        if(isChecking.equals("0")){
            result.put("isOpen",false);
        }
        else{
            result.put("isOpen",true);
        }
        String hasChecked=courseCheckingInEntity.getCheckingStatus();
        if(hasChecked.equals("未签到")) {
            result.put("hasChecked",false);
        }
        else{
            result.put("hasChecked",true);
        }
        resultJSON.put("success",true);
        resultJSON.put("result",result);
        return resultJSON.toString();
    }
}
