package com.bupt.dlplatform.controller;


import com.bupt.dlplatform.service.SysRegisterInfoService;
import com.bupt.dlplatform.vo.RegisterInputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SysRegisterController {
    @Autowired
    SysRegisterInfoService sysRegisterInfoService;

    /**
     * 用户注册
     * @param request
     * @return
     */
    @PostMapping("/dlplatform/register")
    public ResponseVO register(@RequestBody @Validated RegisterInputVO request){
        return sysRegisterInfoService.userRegister(request);
    }

}
