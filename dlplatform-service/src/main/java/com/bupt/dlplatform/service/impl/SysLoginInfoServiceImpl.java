package com.bupt.dlplatform.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.mapper.TUserRepository;
import com.bupt.dlplatform.model.TUserEntity;
import com.bupt.dlplatform.service.SysLoginInfoService;
import com.bupt.dlplatform.vo.LoginInputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class SysLoginInfoServiceImpl implements SysLoginInfoService {

    @Autowired
    private TUserRepository tUserRepository;

    @Override
    public ResponseVO<TUserEntity> mobileLogin(LoginInputVO request) {
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);
        try {

            String phoneNo = request.getCellPhone();
            String password = request.getPassword();
            String userType = request.getUserType();

            List<TUserEntity> list = tUserRepository.selectList
                    (Wrappers.<TUserEntity>lambdaQuery().eq(TUserEntity::getPhoneNumber,phoneNo).eq(TUserEntity::getUserType, userType).eq(TUserEntity::getPassword,password));
            if (CollectionUtils.isEmpty(list)) {
                responseVO.setCode(ResponseCode.AUTH_USER_NULL.value());
                responseVO.setMsg("未找到信息，请用户注册!");
                return responseVO;
            }
            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            return responseVO;
        } catch (Exception e) {
            log.error("LoginUsersServiceImpl 异常", e);
            return responseVO;
        }
    }
}


