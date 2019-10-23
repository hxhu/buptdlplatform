package com.bupt.dlplatform.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.mapper.TUserRepository;
import com.bupt.dlplatform.model.TUserEntity;
import com.bupt.dlplatform.service.SysRegisterInfoService;
import com.bupt.dlplatform.vo.RegisterInputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class SysRegisterInfoServiceImpl implements SysRegisterInfoService {
    @Autowired
    private TUserRepository tUserRepository;

    @Override
    public ResponseVO<TUserEntity> userRegister(RegisterInputVO request) {
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);
        try {
            String userName = request.getUserName();
            String phoneNo = request.getCellPhone();
            String password = request.getPassword();
            String userType = "user";
            /*判断信息填写是否完整*/
            if(userName.isEmpty() || phoneNo.isEmpty() || password.isEmpty()){
                responseVO.setCode(ResponseCode.INFO_NOT_FULL.value());
                responseVO.setMsg("信息填写不完整，请填写未填写部分");
                return responseVO;
            }
            /*通过电话号码判断是否已存在*/
            List<TUserEntity> list = tUserRepository.selectList
                    (Wrappers.<TUserEntity>lambdaQuery().eq(TUserEntity::getPhoneNumber,phoneNo));
            if (!CollectionUtils.isEmpty(list)) {
                responseVO.setCode(ResponseCode.AUTH_USER_EXIT.value());
                responseVO.setMsg("手机号已注册，请直接登陆!");
                return responseVO;
            }else {
                /*将数据插入TUser表中*/
                Long time =System.currentTimeMillis();
                Date createTime = new Date(time);
                String createTimeString = time.toString();
                String userId = createTimeString + phoneNo;

                TUserEntity tUserEntity = new TUserEntity();
                tUserEntity.setUserName(userName);
                tUserEntity.setPhoneNumber(phoneNo);
                tUserEntity.setPassword(password);
                tUserEntity.setUserType(userType);
                tUserEntity.setCreateTime(createTime);
                tUserEntity.setUserId(userId);
                tUserRepository.insert(tUserEntity);

                responseVO.setCode(ResponseCode.OK.value());
                responseVO.setMsg(ResponseCode.OK.getDescription());

                responseVO.setData(tUserEntity);
                return responseVO;
            }
        } catch (Exception e) {
            log.error("RegisterServiceImpl 异常", e);
            return responseVO;
        }
    }

}
