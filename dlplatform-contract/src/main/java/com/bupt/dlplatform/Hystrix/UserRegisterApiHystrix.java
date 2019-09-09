package com.bupt.dlplatform.Hystrix;

import com.bupt.dlplatform.consumer.UserRegisterApi;
import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.model.TUserEntity;
import com.bupt.dlplatform.vo.RegisterInputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
@Slf4j
@Component
public class UserRegisterApiHystrix implements UserRegisterApi {
    private ResponseVO rtn = new ResponseVO<>(ResponseCode.SYSTEM_EXCEPTION);

    @Override
    public ResponseVO<TUserEntity> userRegister(@RequestBody RegisterInputVO registerInputVO) {
        return rtn;
    }
}
