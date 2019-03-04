package com.ccwsz.server.controller.info;

import com.ccwsz.server.service.info.InfoStreamService;
import com.ccwsz.server.service.util.JsonManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class InfoStreamController {
    private final InfoStreamService infoStreamService;

    @Autowired
    public InfoStreamController(InfoStreamService infoStreamService){
        this.infoStreamService = infoStreamService;
    }


    @RequestMapping(value = "/stream/infos", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getInfoStream(HttpServletRequest request){
        String collegeName;
        String personNumber;
        String type;
        int startIndex;
        try{
            collegeName = request.getParameter("college");
            personNumber = request.getParameter("personID");
            type = request.getParameter("type");
            startIndex = Integer.parseInt(request.getParameter("startIndex"));
        } catch (Exception e){
            e.printStackTrace();
            return JsonManage.buildFailureMessage("数据格式错误！请检查代码");
        }
        return infoStreamService.getInfoStream(collegeName, personNumber, type, startIndex);
    }
}
