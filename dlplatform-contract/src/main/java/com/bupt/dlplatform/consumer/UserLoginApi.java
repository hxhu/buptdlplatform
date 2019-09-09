package com.bupt.dlplatform.consumer;


import com.bupt.dlplatform.Hystrix.UserLoginApiHystrix;
import com.bupt.dlplatform.model.TUserEntity;
import com.bupt.dlplatform.vo.LoginInputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 用户登录消费层
 */
@FeignClient(value = "service-producer-dlplatform", fallback = UserLoginApiHystrix.class)
public interface UserLoginApi {

    @RequestMapping(value = "/dlplatform/login",method = RequestMethod.POST)
    ResponseVO<TUserEntity> mobileLogin(@RequestBody LoginInputVO loginInputVO);

}
