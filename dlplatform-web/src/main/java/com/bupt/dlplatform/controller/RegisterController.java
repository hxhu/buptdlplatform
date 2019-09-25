package com.bupt.dlplatform.controller;


import com.bupt.dlplatform.consumer.UserRegisterApi;
import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.model.TUserEntity;
import com.bupt.dlplatform.util.ValidationUtil;
import com.bupt.dlplatform.vo.RegisterInputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
public class RegisterController {
    @Resource
    private UserRegisterApi userRegisterApi;


    @RequestMapping(value = "/dlplatform/register",method = RequestMethod.POST)
    public ResponseVO userRegister(@RequestBody @Validated RegisterInputVO registerInputVO, HttpServletResponse response){
        ResponseVO<String> responseVO = new ResponseVO<>(ResponseCode.PARAM_INVALID);
        if(registerInputVO == null || StringUtils.isBlank(registerInputVO.getCellPhone()) || StringUtils.isBlank(registerInputVO.getPassword())|| StringUtils.isBlank(registerInputVO.getUserName())){
            return responseVO;
        }
        if(!ValidationUtil.assertMobile(registerInputVO.getCellPhone()))
        {
            responseVO.setMsg("请输入正确的手机号！");
            return responseVO;
        }
        try{
            ResponseVO<TUserEntity> res = userRegisterApi.userRegister(registerInputVO);
            if(res.getCode()==ResponseCode.OK.value()){
                //TUserEntity se = res.getData();
                responseVO.setCode(ResponseCode.OK.value());
                responseVO.setMsg(ResponseCode.OK.getDescription());
                //responseVO.setData(se.getUserName());
            }else {
                responseVO.setCode(res.getCode());
                responseVO.setMsg(res.getMsg());
            }
        }catch (Exception e){
            responseVO.setCode(ResponseCode.SYSTEM_EXCEPTION.value());
            responseVO.setMsg(ResponseCode.SYSTEM_EXCEPTION.getDescription());
            log.error("UserRegisterController ---> UserRegister异常！", e);
        }
        return responseVO;

    }
}
