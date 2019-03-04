package com.ccwsz.server.service.bbs;

import com.ccwsz.server.dao.dock.bbs.BbsReplyRepository;
import com.ccwsz.server.dao.dock.bbs.BbsTopicRepository;
import com.ccwsz.server.dao.dock.user.UserRepository;
import com.ccwsz.server.dao.entity.BbsReplyEntity;
import com.ccwsz.server.dao.entity.UserEntity;
import com.ccwsz.server.service.util.JsonManage;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BbsReplyService {
    private final BbsReplyRepository bbsReplyRepository;
    private final UserRepository userRepository;
    private final BbsTopicRepository bbsTopicRepository;
    @Autowired
    public BbsReplyService(BbsReplyRepository bbsReplyRepository,UserRepository userRepository,BbsTopicRepository bbsTopicRepository){
        this.bbsReplyRepository=bbsReplyRepository;
        this.userRepository=userRepository;
        this.bbsTopicRepository=bbsTopicRepository;
    }
    //回帖
    public String postReply(long topicID,String college,String personNumber,String content){
        UserEntity currentUser = userRepository.findByCollegeAndPersonNumber(college, personNumber);
        if (currentUser == null) {
            return JsonManage.buildFailureMessage("未找到用户！");
        }
        if(bbsTopicRepository.existsById(topicID)){
            BbsReplyEntity newReply=new BbsReplyEntity();
            newReply.setUserId(currentUser.getId());
            newReply.setClickingRate(0);
            newReply.setReplyText(content);
            newReply.setTopicId(topicID);
            bbsReplyRepository.save(newReply);
            JSONObject responseJson=new JSONObject();
            return responseJson.put("success",true).toString();
        }
        else{
            return JsonManage.buildFailureMessage("该话题已经不存在，请核实！");
        }
    }
}
