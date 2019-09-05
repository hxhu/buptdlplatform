package com.bupt.dlplatform.service;

import com.bupt.dlplatform.model.TUserEntity;
import com.bupt.dlplatform.vo.LoginInputVO;
import com.bupt.dlplatform.vo.ResponseVO;

public interface SysLoginInfoService {
    ResponseVO<TUserEntity> mobileLogin(LoginInputVO request);

}
