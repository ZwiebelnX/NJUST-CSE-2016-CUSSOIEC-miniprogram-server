package com.ccwsz.server.service;

import com.ccwsz.server.dao.College;
import com.ccwsz.server.dao.User;
import com.ccwsz.server.dao.entity.CollegeEntity;
import com.ccwsz.server.dao.entity.UserEntity;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class UserService {
    private User user;
    private College college;
    @Autowired
    public UserService(User user,College college){this.user=user;this.college=college;}
    //获取学号
    public String getStudentId(String openId){
        JSONObject jsonObject=new JSONObject();
        try{
            UserEntity student=user.getUserByOpenId(openId);
            if(student!=null) {
                String studentId = student.getPersonNumber();
                String collegeName=student.getCollege();
                jsonObject.put("success", true);
                JSONObject result=new JSONObject();
                result.put("college",collegeName);
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
            CollegeEntity currentCollege = college.getByName(collegeName);
            UserEntity currentUser = user.getUserEntitiesByStudentNumber(personId, collegeName);
            String grade = currentUser.getGrade();
            Timestamp openingTime;
            Timestamp endTime;
            if (grade.equals("本科一年级")) {
                openingTime = currentCollege.getOpeningDate1();
            } else if (grade.equals("本科二年级")) {
            openingTime = currentCollege.getOpeningDate2();
            } else if (grade.equals("本科三年级")) {
            openingTime = currentCollege.getOpeningDate3();
            } else {
                openingTime = currentCollege.getOpeningDate4();
            }
            //将时间戳转换为Data
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date openingData = format.parse(openingTime.toString());
            Date currentData = format.parse(data);
            long diffEnd = currentData.getTime() - openingData.getTime();
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
        String college=data.getString("college");
        String personID=data.getString("personID");
        String realName=data.getString("realName");
        String nickName=data.getString("nickName");
        JSONObject result=new JSONObject();
        try{
            user.updateUserCollege(openId,college);
            user.updateUserNickName(openId,nickName);
            user.updateUserPersonNumber(openId,personID);
            user.updateUserRealName(openId,realName);
            Timestamp nowTimestamp = new Timestamp(new Date().getTime());
            user.updateUserGetModified(openId,nowTimestamp);
//            user.updateUserGmtCreate(openId,nowTimestamp);
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
            UserEntity currentUser = user.getUserByOpenId(openId);
            result.put("success",true);
            JSONObject resultTmp=new JSONObject();
            resultTmp.put("userType",currentUser.getUserType());
            resultTmp.put("college",currentUser.getCollege());
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
