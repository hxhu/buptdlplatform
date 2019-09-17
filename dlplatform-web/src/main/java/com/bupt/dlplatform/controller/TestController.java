package com.bupt.dlplatform.controller;


import com.bupt.dlplatform.consumer.TestConsumer;
import com.bupt.dlplatform.vo.BaseInputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
public class TestController {

    @Autowired
    private TestConsumer testConsumer;

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public ResponseVO test(BaseInputVO baseInputVO){

        return testConsumer.test();
    }
}
