package com.ccwsz.server.service;

import com.ccwsz.server.dao.dock.CollegeRepository;
import com.ccwsz.server.dao.dock.UserRepository;
import com.ccwsz.server.dao.entity.CollegeEntity;
import com.ccwsz.server.dao.entity.UserEntity;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class UserService {
    private UserRepository userRepository;
    private CollegeRepository collegeRepository;
    @Autowired
    public UserService(UserRepository userRepository, CollegeRepository collegeRepository){this.userRepository = userRepository;this.collegeRepository = collegeRepository;}
    //获取学号
    public String getStudentId(String openId){
        JSONObject jsonObject=new JSONObject();
        try{
            UserEntity student= userRepository.findByOpenid(openId);
            if(student!=null) {
                String studentId = student.getPersonNumber();
                String collegeName=student.getCollege();
                jsonObject.put("success", true);
                JSONObject result=new JSONObject();
                result.put("CollegeRepository",collegeName);
                result.put("personID",studentId);
                jsonObject.put("result", result);
            }
            else jsonObject.put("success",false);
        }
        catch (Exception e){
            e.printStackTrace();
            jsonObject.put("success",false);
        }
        return  jsonObject.toString();
    }
    //获取openid
//    public String getOpenId(String code){
//        JSONObject result=new JSONObject();
//        try {
//            String wechatAppId = "";
//            String wechatSecretKey = "";
//            String grantType = "authorization_code";
//            String params = "appid=" + wechatAppId + "&secret=" + wechatSecretKey + "&js_code=" + code + "&grant_type=" + grantType;
//            String sr = HttpRequest.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);
//            JSONObject json = new JSONObject(sr);
//            String openId = json.get("openid").toString();
//            result.put("success",true);
//            result.put("result",openId);
//        }
//        catch (Exception e){
//            e.printStackTrace();
//            result.put("success",false);
//        }
//        return result.toString();
//    }
    //获取周次信息
    public String getWeekMessage(String data,String collegeName,String personId) {
        JSONObject result=new JSONObject();
        try{
            //获取开学时间以字符串的形式
            CollegeEntity currentCollege = collegeRepository.findByName(collegeName);
            UserEntity currentUser = userRepository.findByCollegeAndPersonNumber(personId, collegeName);
            //空查询处理
            if (currentUser == null) {
                JSONObject tempJson = new JSONObject();
                tempJson.put("success", false);
                return tempJson.toString();
            }
            String grade = currentUser.getGrade();
            Date openingTime;
            Timestamp endTime;
            switch(grade){
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
            Date openingDate = format.parse(openingTime.toString());
            Date currentDate = format.parse(data);
            long diffEnd = currentDate.getTime() - openingDate.getTime();
            int currentWeek = (int) (diffEnd / (24 * 60 * 60 * 1000) / 7);
            Array[] range = new Array[2];
            result.put("success",true);
            JSONObject resultTmp=new JSONObject();
            resultTmp.put("numOfWeek",currentWeek+1);
            resultTmp.put("range",range);
            result.put("result",resultTmp);
        }
        catch (Exception e){
            e.printStackTrace();
            result.put("success",false);
        }
        return result.toString();
    }

    //绑定学号/.教工号
    public String updateUser(String openId,JSONObject data){
        String college=data.getString("CollegeRepository");
        String personID=data.getString("personID");
        String realName=data.getString("realName");
        String nickName=data.getString("nickName");
        JSONObject result=new JSONObject();
        try{
            userRepository.updateUserCollege(openId,college);
            userRepository.updateUserNickName(openId,nickName);
            userRepository.updateUserPersonNumber(openId,personID);
            userRepository.updateUserRealName(openId,realName);
            Timestamp nowTimestamp = new Timestamp(new Date().getTime());
            userRepository.updateUserGetModified(openId,nowTimestamp);
//            userRepository.updateUserGmtCreate(openId,nowTimestamp);
            result.put("success",true);
        }
        catch (Exception e){
            e.printStackTrace();
            result.put("success",false);
        }
        return result.toString();
    }
    //获取用户信息
    public String getUserMessage(String openId){
        JSONObject result=new JSONObject();
        try {
            UserEntity currentUser = userRepository.findByOpenid(openId);
            result.put("success",true);
            JSONObject resultTmp=new JSONObject();
            resultTmp.put("userType",currentUser.getUserType());
            resultTmp.put("CollegeRepository",currentUser.getCollege());
            resultTmp.put("personID",currentUser.getPersonNumber());
            resultTmp.put("realName",currentUser.getRealName());
            resultTmp.put("nickName",currentUser.getNickName());
            resultTmp.put("gender",currentUser.getGender());
            resultTmp.put("grade",currentUser.getGrade());
            resultTmp.put("academy",currentUser.getAcademy());
            resultTmp.put("major",currentUser.getMajor());
            resultTmp.put("phone",currentUser.getPhone());
            resultTmp.put("email",currentUser.getEmail());
        }
        catch (Exception e){
            e.printStackTrace();
            result.put("success",false);
        }
        return result.toString();
    }
}
