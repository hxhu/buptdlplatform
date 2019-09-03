package com.bupt.dlplatform.controller;


import com.bupt.dlplatform.consumer.TestConsumer;
import com.bupt.dlplatform.vo.ResponseVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {


    @Resource
    private TestConsumer testConsumer;

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public ResponseVO test(){
        return testConsumer.test();
    }
}
