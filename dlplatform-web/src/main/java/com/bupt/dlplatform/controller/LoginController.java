package com.bupt.dlplatform.controller;

import com.bupt.dlplatform.consumer.UserLoginApi;
import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.model.TUserEntity;
import com.bupt.dlplatform.model.common.TkGenerateParameter;
import com.bupt.dlplatform.util.TokenUtil;
import com.bupt.dlplatform.util.ValidationUtil;
import com.bupt.dlplatform.vo.LoginInputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import static com.bupt.dlplatform.config.SecretKeyConfig.secretKeySave;

@RestController
@Slf4j
@Api
public class LoginController {
    @Resource
    private UserLoginApi userLoginApi;


    @ApiOperation("注册用户")
    @RequestMapping(value = "/dlplatform/login",method = RequestMethod.POST)
    public ResponseVO mobileLogin(@RequestBody @Validated LoginInputVO loginInputVO, HttpServletResponse response){
        ResponseVO<String> responseVO = new ResponseVO<>(ResponseCode.PARAM_INVALID);
        if(loginInputVO == null || StringUtils.isBlank(loginInputVO.getCellPhone()) || StringUtils.isBlank(loginInputVO.getPassword())|| StringUtils.isBlank(loginInputVO.getUserType())){
            return responseVO;
        }
        if(!ValidationUtil.assertMobile(loginInputVO.getCellPhone()))
        {
            responseVO.setMsg("请输入正确的手机号！");
            return responseVO;
        }
        try{
            ResponseVO<TUserEntity> res = userLoginApi.mobileLogin(loginInputVO);
            if(res.getCode()==ResponseCode.OK.value()){
                TUserEntity se = res.getData();
                String token = createToken(se,secretKeySave());
                if(StringUtils.isBlank(token)){
                    responseVO.setCode(ResponseCode.AUTH_TOKEN_INVALID.value());
                    responseVO.setMsg(ResponseCode.AUTH_TOKEN_INVALID.getDescription());
                    return responseVO;
                }
                response.setHeader("Access-token",token);
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

    //生成token
    private String createToken(TUserEntity tUserEntity,String secretKey){
        TkGenerateParameter tk = new TkGenerateParameter();
        tk.setCellPhone(tUserEntity.getPhoneNumber());
        tk.setUserName(tUserEntity.getUserName());
        tk.setUserType(tUserEntity.getUserType());
        tk.setUserId(tUserEntity.getUserId());
        return TokenUtil.generateToken(tk,secretKey);
    }
}
