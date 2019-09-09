package com.bupt.dlplatform.service;

import com.bupt.dlplatform.model.TUserEntity;
import com.bupt.dlplatform.vo.RegisterInputVO;
import com.bupt.dlplatform.vo.ResponseVO;

public interface SysRegisterInfoService {
    ResponseVO<TUserEntity> userRegister(RegisterInputVO request);
}
