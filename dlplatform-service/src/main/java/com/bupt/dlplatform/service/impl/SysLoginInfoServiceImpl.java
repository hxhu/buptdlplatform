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


import java.util.List;

@Slf4j
@Service
public class SysLoginInfoServiceImpl implements SysLoginInfoService {

    @Autowired
    private TUserRepository tUserRepository;

    /**
     * 系统用户登录实现
     * @param request
     * @return
     */
    @Override
    public ResponseVO<TUserEntity> mobileLogin(LoginInputVO request) {
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);
        try {

            //手机号
            String phoneNo = request.getCellPhone();
            //密码
            String password = request.getPassword();
            //用户类型（1--管理员 2--用户）
            String userType_data = request.getUserType();
            String userType = new String();
            if(Integer.parseInt(userType_data)==1) {
                userType="administrator";
            }
            else if (Integer.parseInt(userType_data)==2){
                userType="user";
            }

            //通过手机号查询数据库匹配用户
            List<TUserEntity> list = tUserRepository.selectList
                    (Wrappers.<TUserEntity>lambdaQuery().eq(TUserEntity::getPhoneNumber,phoneNo));

            if (CollectionUtils.isEmpty(list)) {
                //判断手机号是否存在
                responseVO.setCode(ResponseCode.AUTH_USER_NULL.value());
                responseVO.setMsg("未找到信息，请用户注册!");
                return responseVO;
            }else if(!list.get(0).getPassword().equals(password)  || !list.get(0).getUserType().equals(userType)){
                //判断密码或者用户类型是否错误
                responseVO.setCode(ResponseCode.AUTH_USER_ERROR.value());
                responseVO.setMsg("密码或者用户类型错误，请重新输入");
                return responseVO;
            }else {
                //获取用户名
                String userName = list.get(0).getUserName();
                //用户Id
                String userId =list.get(0).getUserId();
                TUserEntity returnData = new TUserEntity();
                //返回信息
                returnData.setUserName(userName);
                returnData.setPhoneNumber(phoneNo);
                returnData.setUserId(userId);
                responseVO.setCode(ResponseCode.OK.value());
                responseVO.setMsg(ResponseCode.OK.getDescription());
                responseVO.setData(returnData);
                    return responseVO;
            }
        } catch (Exception e) {
            log.error("LoginUsersServiceImpl 异常", e);
            return responseVO;
        }
    }
}


