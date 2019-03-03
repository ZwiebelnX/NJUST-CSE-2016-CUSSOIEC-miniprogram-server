package com.ccwsz.server.controller.bbs;

import com.ccwsz.server.dao.dock.bbs.BbsSectorRepository;
import com.ccwsz.server.service.bbs.BbsSectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class BbsSectorController {
    private BbsSectorService bbsSectorService;
    @Autowired
    public BbsSectorController(BbsSectorService bbsSectorService){
        this.bbsSectorService=bbsSectorService;
    }
    @RequestMapping(value = "/bbs/sectors", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getSectors(HttpServletRequest request){
        String personId = request.getParameter("personID");
        String college = request.getParameter("college");
        return bbsSectorService.getSectors(college,personId);
    }
}
