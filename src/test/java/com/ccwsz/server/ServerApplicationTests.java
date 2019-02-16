package com.ccwsz.server;

import com.ccwsz.server.service.util.JsonManage;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServerApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void jsonNullTest(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("notNull", "good");
        jsonObject.put("null", JSONObject.NULL);
        JsonManage.filterNull(jsonObject);
        System.out.println(jsonObject);
    }
}

