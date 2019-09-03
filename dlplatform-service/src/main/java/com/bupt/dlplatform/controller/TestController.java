package com.bupt.dlplatform.controller;


import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.vo.ResponseVO;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    public ResponseVO test(){
        ResponseVO responseVO = new ResponseVO<>(ResponseCode.OK);
        responseVO.setData("hello world");
        return responseVO;
    }
}
