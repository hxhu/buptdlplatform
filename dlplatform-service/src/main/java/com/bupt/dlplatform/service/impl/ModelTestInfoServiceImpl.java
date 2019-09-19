package com.bupt.dlplatform.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.mapper.TTestRecordRepository;
import com.bupt.dlplatform.model.TTestRecordEntity;
import com.bupt.dlplatform.service.SearchTestInfoService;
import com.bupt.dlplatform.vo.ResponseVO;
import com.bupt.dlplatform.vo.SearchTestInputVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service

public class SearchTestInfoServiceImpl implements SearchTestInfoService {
    @Autowired
    private TTestRecordRepository tTestRecordRepository;

     // 模型测试记录查询
    @Override
    public ResponseVO<TTestRecordEntity> testRecord(SearchTestInputVO request) {
        ResponseVO responseVO =new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);
        try {

            String phoneNo = request.getCellPhone();
            String userId = request.getUserId();

            List<TTestRecordEntity> list = tTestRecordRepository.selectList
                    (Wrappers.<TTestRecordEntity>lambdaQuery().eq(TTestRecordEntity::getUserId,userId));

            if (CollectionUtils.isEmpty(list)) {
                //判断用户是否有测试记录
                responseVO.setCode(ResponseCode.RECORD_NULL.value());
                responseVO.setMsg("用户还没有模型检测记录！");
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
