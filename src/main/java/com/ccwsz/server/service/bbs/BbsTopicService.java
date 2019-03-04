package com.ccwsz.server.service.bbs;

import com.ccwsz.server.dao.dock.bbs.BbsSectorRepository;
import com.ccwsz.server.dao.dock.bbs.BbsTopicRepository;
import com.ccwsz.server.dao.dock.user.UserRepository;
import com.ccwsz.server.dao.entity.BbsSectorEntity;
import com.ccwsz.server.dao.entity.BbsTopicEntity;
import com.ccwsz.server.dao.entity.UserEntity;
import com.ccwsz.server.service.util.JsonManage;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BbsTopicService {
    private final UserRepository userRepository;
    private final BbsSectorRepository bbsSectorRepository;
    private final BbsTopicRepository bbsTopicRepository;
    @Autowired
    //发帖
    public BbsTopicService(UserRepository userRepository,BbsSectorRepository bbsSectorRepository,BbsTopicRepository bbsTopicRepository){
        this.userRepository=userRepository;
        this.bbsSectorRepository=bbsSectorRepository;
        this.bbsTopicRepository=bbsTopicRepository;
    }
    public String postTopic(long courseID,String college,String personNumber,String title,String content){
        UserEntity currentUser = userRepository.findByCollegeAndPersonNumber(college, personNumber);
        if (currentUser == null) {
            return JsonManage.buildFailureMessage("未找到用户！");
        }
        try {
            BbsSectorEntity currentSector = bbsSectorRepository.findByCourseId(courseID);
            long sectorId = currentSector.getId();
            BbsTopicEntity newTopic = new BbsTopicEntity();
            newTopic.setReplyCount(0);
            newTopic.setSectorId(sectorId);
            newTopic.setClickingRate(0);
            newTopic.setTitle(title);
            newTopic.setUserId(currentUser.getId());
            newTopic.setTopicText(content);
            newTopic.setCourseId(courseID);
            bbsTopicRepository.save(newTopic);
            JSONObject responseJson=new JSONObject();
            return responseJson.put("success",true).toString();
        }catch (Exception e){
            e.printStackTrace();
            return JsonManage.buildFailureMessage("操作数据库有误!");
        }
    }
    //获取主贴表
    @Transactional
    public String getTopic(long courseId,int startIndex){
        int pageSize=5;
        Pageable page=PageRequest.of(startIndex, pageSize, Sort.by(Sort.Direction.DESC, "age"));
        //List<BbsTopicEntity> topicList=bbsTopicRepository.findTopicPage(page,courseId);

        return "1";
    }
}
