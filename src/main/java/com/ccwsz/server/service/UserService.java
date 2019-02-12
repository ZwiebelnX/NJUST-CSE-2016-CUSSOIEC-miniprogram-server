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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
            System.out.print(personId+" "+collegeName);
            UserEntity currentUser = userRepository.findByCollegeAndPersonNumber(collegeName,personId);
            //空查询处理
            System.out.println(currentUser.getNickName()+" "+currentCollege.getOpeningDate1());
            if (currentUser == null) {
                System.out.print("null");
                JSONObject tempJson = new JSONObject();
                tempJson.put("success", false);
                return tempJson.toString();
            }
            String grade = currentUser.getGrade();
            Date openingTime;
            Date endWeek=currentCollege.getEndingDate();
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
            //Date openingDate = format.parse(openingTime.toString());
            Date currentDate = format.parse(data);
//            Date endDate=format.parse(endTime.toString());
            long diffEnd = currentDate.getTime() - openingTime.getTime();
            long countEnd = endWeek.getTime()-openingTime.getTime();
            int currentWeek = (int) (diffEnd / (24 * 60 * 60 * 1000) / 7);
            int countWeek=(int)(countEnd/(24*60*60*1000)/7);
            List<Integer> range = new ArrayList<>();
            range.add(1);
            range.add(countWeek+1);
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
        //TODO Spring报错说需要事务
        String college=data.getString("college");
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
        UserEntity currentUser = userRepository.findByOpenid(openId);
        if(currentUser == null){
            //TODO 空查询处理
            result.put("success", false);
            return result.toString();
        }
        result.put("success",true);
        result.put("userType",currentUser.getUserType());
        result.put("CollegeRepository",currentUser.getCollege());
        result.put("personID",currentUser.getPersonNumber());
        result.put("realName",currentUser.getRealName());
        result.put("nickName",currentUser.getNickName());
        result.put("gender",currentUser.getGender());
        result.put("grade",currentUser.getGrade());
        result.put("academy",currentUser.getAcademy());
        result.put("major",currentUser.getMajor());
        result.put("phone",currentUser.getPhone());
        result.put("email",currentUser.getEmail());
        return result.toString();
    }
}
