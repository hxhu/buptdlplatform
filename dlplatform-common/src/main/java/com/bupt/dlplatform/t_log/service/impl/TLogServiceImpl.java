package com.bupt.dlplatform.t_log.service.impl;

import com.bupt.dlplatform.t_log.entity.TLogEntity;
import com.bupt.dlplatform.t_log.mapper.TLogRepository;
import com.bupt.dlplatform.t_log.service.ITLogService;
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
public class TLogServiceImpl extends ServiceImpl<TLogRepository, TLogEntity> implements ITLogService {

}
