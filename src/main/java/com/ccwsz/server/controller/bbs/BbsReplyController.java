package com.ccwsz.server.controller.bbs;

import com.ccwsz.server.service.bbs.BbsReplyService;
import com.ccwsz.server.service.util.JsonManage;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class BbsReplyController {
    private final BbsReplyService bbsReplyService;
    @Autowired
    public BbsReplyController(BbsReplyService bbsReplyService){
        this.bbsReplyService=bbsReplyService;
    }
    //回帖
    @RequestMapping(value = "/bbs/reply", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String postTopic(@RequestBody String jsonString){
        try{
            JSONObject jsonObject=new JSONObject(jsonString);
            long topicID=jsonObject.getLong("topicID");
            JSONObject data=jsonObject.getJSONObject("data");
            String college=data.getString("college");
            String personID=data.getString("personID");
            String content=data.getString("content");
            return bbsReplyService.postReply(topicID,college,personID,content);
        }
        catch (Exception ex){
            ex.printStackTrace();
            return JsonManage.buildFailureMessage("数据格式错误！请检查代码！");
        }
    }
    //回复列表
    @RequestMapping(value = "/bbs/replys", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getSectors(HttpServletRequest request){
        long topicID;
        try {
            topicID = Integer.parseInt(request.getParameter("topicID"));
        } catch (Exception e) {
            e.printStackTrace();
            return JsonManage.buildFailureMessage("数据格式错误");
        }
        int startIndex=Integer.parseInt(request.getParameter("startIndex"));
        return bbsReplyService.getReply(topicID,startIndex);
    }
}
