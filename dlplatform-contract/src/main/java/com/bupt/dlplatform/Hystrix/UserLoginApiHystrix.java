package com.bupt.dlplatform.Hystrix;

import com.bupt.dlplatform.consumer.UserLoginApi;
import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.model.TUserEntity;
import com.bupt.dlplatform.vo.LoginInputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Component
public class UserLoginApiHystrix implements UserLoginApi {
    private ResponseVO rtn = new ResponseVO<>(ResponseCode.SYSTEM_EXCEPTION);

    @Override
    public ResponseVO<TUserEntity> mobileLogin(@RequestBody LoginInputVO loginInputVO) {
        return rtn;
    }

}
