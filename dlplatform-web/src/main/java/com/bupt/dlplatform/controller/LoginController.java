package com.bupt.dlplatform.controller;

import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.vo.LoginInputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
public class LoginController {
    @RequestMapping(value = "/dlplatform/Login",method = RequestMethod.POST)
    public ResponseVO mobileLogin(@RequestBody @Validated LoginInputVO loginInputVO, HttpServletResponse response){
        ResponseVO<String> responseVO = new ResponseVO<>(ResponseCode.PARAM_INVALID);
        if(loginInputVO == null || StringUtils.isBlank(loginInputVO.getCellPhone()) || StringUtils.isBlank(loginInputVO.getRoundCode())){
            return responseVO;
        }
        if((loginInputVO.getCellPhone()))
        {
            responseVO.setMsg("请输入正确的手机号！");
            return responseVO;
        }
        if(phoneLoginInputVo.getRoundCode().length()!=6){
            responseVO.setMsg("验证码位数不正确!");
            return responseVO;
        }
        try{
            ResponseVO<SysStaffsInfoEntity> res = userLoginApi.mobilePhoneLogin(phoneLoginInputVo);
            log.info("短信登陆调用service返回: {}",res);
            if(res.getCode()==ResponseCode.OK.value()){
                SysStaffsInfoEntity se = res.getData();
                String token = createToken(se, SecretKeyConfig.encrypt_decrypt_key);
                if(StringUtils.isBlank(token)){
                    responseVO.setCode(ResponseCode.AUTH_TOKEN_INVALID.value());
                    responseVO.setMsg(ResponseCode.AUTH_TOKEN_INVALID.getDescription());
                    return responseVO;
                }
                response.setHeader("access-token",token);
                responseVO.setCode(ResponseCode.OK.value());
                responseVO.setMsg(ResponseCode.OK.getDescription());
            }else {
                responseVO.setCode(res.getCode());
                responseVO.setMsg(res.getMsg());
            }
        }catch (Exception e){
            responseVO.setCode(ResponseCode.SYSTEM_EXCEPTION.value());
            responseVO.setMsg(ResponseCode.SYSTEM_EXCEPTION.getDescription());
            log.error("UserLoginController ---> MobilePhoneLogin 生成token异常！", e);
        }
        return responseVO;
    }
}
