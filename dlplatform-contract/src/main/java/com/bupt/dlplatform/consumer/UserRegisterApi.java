package com.bupt.dlplatform.consumer;

import com.bupt.dlplatform.Hystrix.UserRegisterApiHystrix;
import com.bupt.dlplatform.model.TUserEntity;
import com.bupt.dlplatform.vo.RegisterInputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



/**
 * 用户注册消费层
 */

@FeignClient(value = "service-producer-dlplatform", fallback = UserRegisterApiHystrix.class)
public interface UserRegisterApi {

    /**
     * 用户注册
     * @param registerInputVO
     * @return
     */
    @RequestMapping(value = "/dlplatform/register",method = RequestMethod.POST)
    ResponseVO<TUserEntity> userRegister(@RequestBody RegisterInputVO registerInputVO);

}
