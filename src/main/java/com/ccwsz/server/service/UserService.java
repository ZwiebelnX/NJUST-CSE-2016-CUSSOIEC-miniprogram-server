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
            UserEntity student=user.getStudentIdByOpenId(openId);
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
    public String getOpenId(String code){
        return "1";
    }
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
}
