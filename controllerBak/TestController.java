package com.bupt.dlplatform.controller;


import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.vo.ResponseVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping(value = "/test",method = RequestMethod.POST)
    public ResponseVO test(){
        ResponseVO responseVO = new ResponseVO<>(ResponseCode.OK);
        responseVO.setData("hello world");
        return responseVO;
    }
}
