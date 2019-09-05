package com.bupt.dlplatform.controller;

import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.service.SysLoginInfoService;
import com.bupt.dlplatform.vo.LoginInputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class SysLoginController {

   @Autowired
   SysLoginInfoService sysLoginInfoService;

   @PostMapping("/dlplatform/Login")
   public ResponseVO mobileLogin(@RequestBody @Validated LoginInputVO request){
       return sysLoginInfoService.mobileLogin(request);

   }
   /*
   @RequestMapping(value = "/test",method = RequestMethod.POST)
   public ResponseVO test(){
       ResponseVO responseVO = new ResponseVO<>(ResponseCode.OK);
       responseVO.setData("hello world");
       return responseVO;
   }*/

}
