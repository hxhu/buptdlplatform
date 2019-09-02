package com.bupt.dlplatform.t_user.service.impl;

import com.bupt.dlplatform.t_user.entity.TUserEntity;
import com.bupt.dlplatform.t_user.mapper.TUserRepository;
import com.bupt.dlplatform.t_user.service.ITUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhongling
 * @since 2019-09-02
 */
@Service
public class TUserServiceImpl extends ServiceImpl<TUserRepository, TUserEntity> implements ITUserService {

}
