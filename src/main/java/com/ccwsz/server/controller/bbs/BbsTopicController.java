package com.ccwsz.server.controller.bbs;

import com.ccwsz.server.service.bbs.BbsTopicService;
import com.ccwsz.server.service.util.JsonManage;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class BbsTopicController {
    private final BbsTopicService bbsTopicService;
    @Autowired
    public BbsTopicController(BbsTopicService bbsTopicService){
        this.bbsTopicService=bbsTopicService;
    }
    //发帖
    @RequestMapping(value = "/bbs/topic", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String postTopic(@RequestBody String jsonString){
        try{
            JSONObject jsonObject=new JSONObject(jsonString);
            long courseID;
            courseID = jsonObject.getLong("courseID");
            JSONObject data=jsonObject.getJSONObject("data");
            String college=data.getString("college");
            String personID=data.getString("personID");
            String title=data.getString("title");
            String content=data.getString("content");
            return bbsTopicService.postTopic(courseID,college,personID,title,content);
        }
        catch (Exception ex){
            ex.printStackTrace();
            return JsonManage.buildFailureMessage("数据格式错误！请检查代码！");
        }
    }
    //主题列表
    @RequestMapping(value = "/bbs/topics", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getSectors(HttpServletRequest request){
        long courseId;
        try {
            courseId = Integer.parseInt(request.getParameter("courseID"));
        } catch (Exception e) {
            e.printStackTrace();
            return JsonManage.buildFailureMessage("数据格式错误");
        }
        int startIndex=Integer.parseInt(request.getParameter("startIndex"));
        return bbsTopicService.getTopic(courseId,startIndex);
    }
}
