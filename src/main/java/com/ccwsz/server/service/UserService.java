package com.ccwsz.server.service;

import com.ccwsz.server.dao.User;
import com.ccwsz.server.dao.entity.UserEntity;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private User user;
    @Autowired
    public UserService(User user){this.user=user;}
    public String getStudentId(String openId){
        JSONObject jsonObject=new JSONObject();
        try{
            UserEntity student=user.getStudentIdByOpenId(openId);
            if(student!=null) {
                String studentId = student.getPersonNumber();
                jsonObject.put("success", true);
                jsonObject.put("result", studentId);
            }
            else jsonObject.put("success",false);
        }
        catch (Exception e){
            e.printStackTrace();
            jsonObject.put("success",false);
        }
        return  jsonObject.toString();
    }
}
