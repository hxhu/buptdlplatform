package com.bupt.dlplatform.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Api
public class LvController {
    @RequestMapping(value = "/dlplatform/lv",method = RequestMethod.GET)
    public String test(){
        return "来自钟灵的计算机";
    }
}
