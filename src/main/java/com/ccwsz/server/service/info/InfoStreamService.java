package com.ccwsz.server.service.info;

import com.ccwsz.server.dao.dock.info.InfoStreamBbsReplyRepository;
import com.ccwsz.server.dao.dock.info.InfoStreamExamRepository;
import com.ccwsz.server.dao.dock.info.InfoStreamNoticeRepository;
import com.ccwsz.server.dao.dock.info.InfoStreamScoreRepository;
import com.ccwsz.server.dao.dock.user.UserRepository;
import com.ccwsz.server.dao.entity.UserEntity;
import com.ccwsz.server.service.util.JsonManage;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InfoStreamService {
    private final UserRepository userRepository;
    private final InfoStreamExamRepository infoStreamExamRepository;
    private final InfoStreamNoticeRepository infoStreamNoticeRepository;
    private final InfoStreamScoreRepository infoStreamScoreRepository;
    private final InfoStreamBbsReplyRepository infoStreamBbsReplyRepository;

    @Autowired
    public InfoStreamService(UserRepository userRepository, InfoStreamExamRepository infoStreamExamRepository,
                             InfoStreamNoticeRepository infoStreamNoticeRepository,
                             InfoStreamScoreRepository infoStreamScoreRepository,
                             InfoStreamBbsReplyRepository infoStreamBbsReplyRepository){
        this.userRepository = userRepository;
        this.infoStreamExamRepository = infoStreamExamRepository;
        this.infoStreamNoticeRepository = infoStreamNoticeRepository;
        this.infoStreamScoreRepository = infoStreamScoreRepository;
        this.infoStreamBbsReplyRepository = infoStreamBbsReplyRepository;
    }

    public String getInfoStream(String collegeName, String personNumber, String type, int startIndex){
        JSONObject responseJson = new JSONObject();
        UserEntity currentUser = userRepository.findByCollegeAndPersonNumber(collegeName, personNumber);
        if(currentUser == null){
            return JsonManage.buildFailureMessage("找不到用户");
        }
        switch (type){
            case "all":
            case "notice":
            case "activity":
            case "exam":
            case "score":
            case "community":
        }
        return null;
    }

    private JSONArray getTypeNotice(){
        return null;
    }
}
