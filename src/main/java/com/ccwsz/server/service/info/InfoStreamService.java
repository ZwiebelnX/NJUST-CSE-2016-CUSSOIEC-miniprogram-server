package com.ccwsz.server.service.info;

import com.ccwsz.server.dao.dock.course.CourseChooseRepository;
import com.ccwsz.server.dao.dock.course.CourseRepository;
import com.ccwsz.server.dao.dock.info.InfoStreamBbsReplyRepository;
import com.ccwsz.server.dao.dock.info.InfoStreamExamRepository;
import com.ccwsz.server.dao.dock.info.InfoStreamNoticeRepository;
import com.ccwsz.server.dao.dock.info.InfoStreamScoreRepository;
import com.ccwsz.server.dao.dock.user.UserRepository;
import com.ccwsz.server.dao.entity.*;
import com.ccwsz.server.service.util.JsonManage;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InfoStreamService {
    private final UserRepository userRepository;
    private final InfoStreamExamRepository infoStreamExamRepository;
    private final InfoStreamNoticeRepository infoStreamNoticeRepository;
    private final InfoStreamScoreRepository infoStreamScoreRepository;
    private final InfoStreamBbsReplyRepository infoStreamBbsReplyRepository;
    private final CourseChooseRepository courseChooseRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public InfoStreamService(UserRepository userRepository, InfoStreamExamRepository infoStreamExamRepository,
                             InfoStreamNoticeRepository infoStreamNoticeRepository,
                             InfoStreamScoreRepository infoStreamScoreRepository,
                             InfoStreamBbsReplyRepository infoStreamBbsReplyRepository,
                             CourseChooseRepository courseChooseRepository,
                             CourseRepository courseRepository) {
        this.userRepository = userRepository;
        this.infoStreamExamRepository = infoStreamExamRepository;
        this.infoStreamNoticeRepository = infoStreamNoticeRepository;
        this.infoStreamScoreRepository = infoStreamScoreRepository;
        this.infoStreamBbsReplyRepository = infoStreamBbsReplyRepository;
        this.courseChooseRepository = courseChooseRepository;
        this.courseRepository = courseRepository;
    }

    public String getInfoStream(String collegeName, String personNumber, String type, int startIndex) {
        JSONObject responseJson = new JSONObject();
        JSONArray result;
        UserEntity currentUser = userRepository.findByCollegeAndPersonNumber(collegeName, personNumber);
        if (currentUser == null) {
            return JsonManage.buildFailureMessage("找不到用户");
        }
        switch (type) {
            case "all":
            case "notice":
                result = getNotice(currentUser);
                break;
            case "activity":
                result = getActivities();
            case "exam":
                result = getExams(currentUser);
            case "score":
                result = getScore(currentUser);
            case "community":

        }
        return null;
    }

    private JSONArray getNotice(UserEntity user) {
        List<CourseChooseEntity> courseChooses = courseChooseRepository.findByStudentId(user.getId());
        List<Long> courseIds = new ArrayList<>();
        for (CourseChooseEntity courseChoose : courseChooses) {
            courseIds.add(courseChoose.getCourseId());
        }

        List<InfoStreamNoticeEntity> notices =
                infoStreamNoticeRepository.findAllByTargetCourseIdInOrTargetCourseIdIsNullOrderByGmtModified(courseIds);

        JSONArray result = new JSONArray();

        for (InfoStreamNoticeEntity notice : notices) {
            JSONObject noticeJson = new JSONObject();
            if(notice.getType().equals("notice")){
                noticeJson.put("type", "notice");
                if (notice.getDeliverId() == null) {
                    noticeJson.put("source", "教务处");
                } else {
                    UserEntity deliver = userRepository.findById(notice.getDeliverId());
                    if (deliver != null) {
                        noticeJson.put("source", deliver.getPersonNumber());
                    } else {
                        noticeJson.put("source", " ");
                    }
                }
                noticeJson.put("image", notice.getImage() == null ? "" : notice.getImage());
                noticeJson.put("title", notice.getTitle());
                noticeJson.put("description", notice.getText());

                if (notice.getTag() != null) {
                    String[] tags = notice.getTag().split(",");

                    JSONArray tagsArray = new JSONArray();
                    for (String tag : tags) {
                        tagsArray.put(tag);
                    }
                    noticeJson.put("tag", tagsArray);
                }

                result.put(noticeJson);
            }
        }

        return result;
    }

    private JSONArray getActivities(){
        List<InfoStreamNoticeEntity> activities = infoStreamNoticeRepository.findAllByType("activity");
        JSONArray result = new JSONArray();

        for (InfoStreamNoticeEntity activity : activities) {
            JSONObject actJson = new JSONObject();

            actJson.put("type", "activity");
            actJson.put("image", activity.getImage() == null ? "" : activity.getImage());
            actJson.put("description", activity.getText());
            actJson.put("tag", activity.getTag() == null ? "" : activity.getTag());

            result.put(actJson);
        }

        return result;
    }

    private JSONArray getExams(UserEntity user){
        List<InfoStreamExamEntity> exams = infoStreamExamRepository.findAllByTargetUserIdOrderByGmtModified(user.getId());
        JSONArray result = new JSONArray();

        for (InfoStreamExamEntity exam : exams) {
            JSONObject examJson = new JSONObject();

            examJson.put("type", "exam");
            examJson.put("name", courseRepository.findById(exam.getCourseId()).getCourseName());
            examJson.put("time", exam.getExamTime());
            examJson.put("location", exam.getLocation());
            examJson.put("seatNumber", exam.getSetNumber());

            result.put(examJson);
        }

        return result;
    }

    private JSONArray getScore(UserEntity user){
        List<InfoStreamScoreEntity> scores = infoStreamScoreRepository.findAllByTargetUserId(user.getId());
        JSONArray result = new JSONArray();

        for (InfoStreamScoreEntity score : scores) {
            JSONObject scoreJson = new JSONObject();

            scoreJson.put("type", "score");
            scoreJson.put("name", courseRepository.findById(score.getCourseId()).getCourseName());
            scoreJson.put("score", score.getScore());
            scoreJson.put("GPA", score.getGp());

            result.put(scoreJson);
        }

        return result;
    }
}
