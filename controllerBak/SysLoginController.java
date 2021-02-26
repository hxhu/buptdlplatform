package com.bupt.dlplatform.controller;

import com.bupt.dlplatform.service.SysLoginInfoService;
import com.bupt.dlplatform.vo.LoginInputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "用户登录")
public class SysLoginController {

   @Autowired
   SysLoginInfoService sysLoginInfoService;


   /**
    * 系统用户登录
    * @param request
    * @return
    */
   @ApiOperation(value = "mobileLogin",notes = "系统用户登录",httpMethod = "post")
   @PostMapping("/dlplatform/login")
   public ResponseVO mobileLogin(@RequestBody @Validated LoginInputVO request){
       return sysLoginInfoService.mobileLogin(request);
   }

}
