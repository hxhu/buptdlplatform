package com.bupt.dlplatform.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.mapper.TTrainRecordRepository;
import com.bupt.dlplatform.model.TTrainRecordEntity;
import com.bupt.dlplatform.service.ModelTrainInfoService;
import com.bupt.dlplatform.vo.ModelTrainInputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ModelTrainInfoServiceImpl implements ModelTrainInfoService {
    @Autowired
    TTrainRecordRepository tTrainRecordRepository;

    // 模型训练记录查询
    @Override
    public ResponseVO<List<TTrainRecordEntity>> trainRecord(ModelTrainInputVO request) {
        ResponseVO responseVO =new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);
        try {

            String userId = request.getUserId();

            List<TTrainRecordEntity> list = tTrainRecordRepository.selectList
                    (Wrappers.<TTrainRecordEntity>lambdaQuery().eq(TTrainRecordEntity::getUserId,userId));

            if (CollectionUtils.isEmpty(list)) {
                //判断用户是否有训练记录
                responseVO.setCode(ResponseCode.RECORD_NULL.value());
                responseVO.setMsg("用户还没有训练记录！");
                return responseVO;
            }else {
                responseVO.setCode(ResponseCode.OK.value());
                responseVO.setMsg(ResponseCode.OK.getDescription());
                responseVO.setData(list);
                return responseVO;
            }
        } catch (Exception e) {
            log.error("SearchTestRecord 异常", e);
            return responseVO;
        }

    }


}
