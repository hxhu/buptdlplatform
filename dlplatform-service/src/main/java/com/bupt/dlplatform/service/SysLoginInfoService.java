package com.bupt.dlplatform.service;

import com.bupt.dlplatform.model.TUserEntity;
import com.bupt.dlplatform.vo.LoginInputVO;
import com.bupt.dlplatform.vo.ResponseVO;

public interface SysLoginInfoService {
    /**
     * 系统用户登录接口
     * @param request
     * @return
     */
    ResponseVO<TUserEntity> mobileLogin(LoginInputVO request);
}
