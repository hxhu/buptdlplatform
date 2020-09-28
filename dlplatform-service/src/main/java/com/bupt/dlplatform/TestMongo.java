package com.bupt.dlplatform;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huhx on 2020/9/29
 */
@RestController
public class TestMongo {
    @Resource
    private MongoTemplate mongoTemplate;

    @GetMapping("/momgoTest")
    public String mongoTest(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name","12345");
        this.mongoTemplate.save(map, "test");

        return "well";
    }
}
