package com.ccwsz.server.service;

import com.ccwsz.server.dao.dock.*;
import com.ccwsz.server.dao.entity.*;
import com.ccwsz.server.service.util.JsonManage;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CourseService {
    private CourseChooseRepository courseChooseRepository;
    private UserRepository userRepository;
    private CollegeRepository collegeRepository;
    private CourseRepository courseRepository;
    private CourseVideoRepository courseVideoRepository;
    private VideoWatchRepository videoWatchRepository;

    @Autowired
    public CourseService(CourseChooseRepository courseChooseRepository, UserRepository userRepository,
                         CollegeRepository collegeRepository, CourseRepository courseRepository,
                         CourseVideoRepository courseVideoRepository, VideoWatchRepository videoWatchRepository) {
        this.courseChooseRepository = courseChooseRepository;
        this.userRepository = userRepository;
        this.collegeRepository = collegeRepository;
        this.courseRepository = courseRepository;
        this.courseVideoRepository = courseVideoRepository;
        this.videoWatchRepository = videoWatchRepository;
    }

    //通过学生id找到所有课程名字
    public String getCourseByCollegeAndPersonNumber(String collegeName, String personId) {
        JSONObject responseJson = new JSONObject(); //为最后传出的json
        UserEntity currentUser = userRepository.findByCollegeAndPersonNumber(collegeName, personId);
        if (currentUser == null) {
            return JsonManage.buildFailureMessage("未找到用户！");
        }
        //获取该学生课程列表
        List<CourseChooseEntity> courseChooseEntitiesList = courseChooseRepository.findByStudentId(currentUser.getId());
        if (courseChooseEntitiesList == null) {
            responseJson.put("success", true);
            responseJson.put("result", new JSONObject());
            return responseJson.toString();
        }
        try {
            //名字和说明分别对应前段的
            List<JSONObject> result = new ArrayList<>();
            for (CourseChooseEntity courseChoose : courseChooseEntitiesList) {
                long courseId = courseChoose.getCourseId();
                CourseEntity course = courseRepository.findById(courseId);
                String classWeek = course.getClassWeek();
                String startWeek = classWeek.split(",")[0];
                String endWeek = classWeek.split(",")[1];
                int start = Integer.parseInt(startWeek);
                int end = Integer.parseInt(endWeek);
                int len = end - start + 1;
                Integer[] active = new Integer[len];
                for (int i = 0; i < len; i++) {
                    active[i] = i + start;
                }
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("courseID", course.getId());
                jsonObject.put("active", active);
                List<JSONObject> positionTmp = new ArrayList<>();
                String courseTime = course.getClassTime();
                String[] classTime = courseTime.split(";");
                for (String time : classTime) {
                    JSONObject positionTmpJSON = new JSONObject();
                    int timeLen = time.length();
                    int flagTmp = 0;
                    Integer sumTmp = 0;
                    Integer dayOfWeek = 0;
                    List<Integer> indexOfDay = new ArrayList<>();
                    for (int i = 0; i < timeLen; i++) {
                        int chr = time.charAt(i);
                        if (chr < 48 || chr > 57) {
                            if (flagTmp == 0) {
                                flagTmp = 1;
                                dayOfWeek = sumTmp;
                            } else {
                                indexOfDay.add(sumTmp);
                            }
                            System.out.println(sumTmp);
                            sumTmp = 0;
                        } else sumTmp = sumTmp * 10 + (chr - 48);
                    }
                    indexOfDay.add(sumTmp);
                    positionTmpJSON.put("dayOfWeek", dayOfWeek);
                    positionTmpJSON.put("indexOfDay", indexOfDay);
                    positionTmp.add(positionTmpJSON);
                }
                jsonObject.put("position", positionTmp);
                JSONObject info = new JSONObject();
                info.put("name", course.getCourseName());
                info.put("teacher", course.getTeacherName());
                info.put("location", course.getClassroomLocation());
                jsonObject.put("info", info);
                result.add(jsonObject);
            }
            responseJson.put("success", true);
            responseJson.put("result", result);
            return responseJson.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("success", false);
            return jsonObject.toString();
        }
    }

    //返回课程直播信息
    public String getCourseLiveInfo(long courseId) {
        JSONObject responseJson = new JSONObject();
        CourseEntity course = courseRepository.findById(courseId);
        if (course == null) {
            return JsonManage.buildFailureMessage("找不到指定课程！");
        }
        JSONObject result = new JSONObject();
        if (course.getIsLive() == null || course.getIsLive() == 0 ) {
            result.put("isLive", false);
        } else {
            responseJson.put("isLive", true);
        }
        result.put("url", course.getLiveUrl());
        responseJson.put("result", result);
        responseJson.put("success", true);
        return responseJson.toString();
    }

    //根据个人返回视频信息
    public String getCourseVideoInfo(String openid, long courseId) {
        JSONObject responseJson = new JSONObject();
        UserEntity user = userRepository.findByOpenid(openid);
        if (user == null) {
            return JsonManage.buildFailureMessage("没有找到对应用户！");
        }
        long userId = user.getId();
        List<CourseVideoEntity> videoList = courseVideoRepository.findByCourseId(courseId);
        List<VideoWatchEntity> watchList = videoWatchRepository.findByUserIdAndCourseId(userId, courseId);
        //根据日期分类视频，同时打上是否观看的标签
        //初始化第一次循环
        JSONArray result = new JSONArray(); //result字段
        LocalDate videoDate = videoList.get(0).getGmtModified().toLocalDateTime().toLocalDate(); //用于视频分类
        JSONArray videos = new JSONArray(); //以日期为分类的视频列表
        //分类循环
        for (int index = 0;;) {
            //按日期分类视频
            if (videoList.get(index).getGmtModified().toLocalDateTime().toLocalDate().isEqual(videoDate)) {
                JSONObject video = new JSONObject();
                video.put("videoID", videoList.get(index).getId());
                video.put("name", videoList.get(index).getVideoName());
                video.put("url", videoList.get(index).getVideoUrl());
                //定制针对于不同用户的观看信息
                for (VideoWatchEntity watch : watchList) {
                    if (watch.getVideoId() == videoList.get(index).getId()) {
                        video.put("isWatch", true);
                        break;
                    } else {
                        video.put("isWatch", false);
                    }
                }
                videos.put(video);
                videoList.remove(index); //加入json后 从链表中移除视频
                //由于删除了第一个节点 不移动index即可指向下一个节点
            }
            else{
                index++; //如果不匹配 寻找下一个节点
            }
            //当一次循环完成时
            if (index >= videoList.size()) {
                index = 0;
                JSONObject dateObject = new JSONObject(); //一次循环完成后，用于压入数组的临时object
                dateObject.put("date", videoDate.toString());
                dateObject.put("videos", videos);
                result.put(dateObject); //加入result字段
                //视频分类完毕 退出循环
                if(videoList.isEmpty()){
                    break;
                }
                videoDate = videoList.get(0).getGmtModified().toLocalDateTime().toLocalDate();
                videos = new JSONArray();
            }
        }
        responseJson.put("success", true);
        responseJson.put("result", result);
        return responseJson.toString();
    }
}
