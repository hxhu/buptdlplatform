package com.bupt.dlplatform.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.mapper.TTestsetRecordRepository;
import com.bupt.dlplatform.mapper.TTestsetRepository;

import com.bupt.dlplatform.model.TTestsetEntity;
import com.bupt.dlplatform.model.TTestsetRecordEntity;
import com.bupt.dlplatform.service.TestsetInfoService;
import com.bupt.dlplatform.vo.ResponseVO;
import com.bupt.dlplatform.vo.TestsetInputVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class TestsetInfoServiceImpl implements TestsetInfoService {
    @Autowired
    private TTestsetRepository tTestsetRepository;
    @Autowired
    private TTestsetRecordRepository tTestsetRecordRepository;

    //测试集记录查看
    @Override
    public ResponseVO<TTestsetEntity> testsetRecord(TestsetInputVO request) {
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);
        try {

            String userId = request.getUserId();

            List<TTestsetRecordEntity> list = tTestsetRecordRepository.selectList
                    (Wrappers.<TTestsetRecordEntity>lambdaQuery().eq(TTestsetRecordEntity::getUserId,userId));

            if (CollectionUtils.isEmpty(list)) {
                //判断用户是否有测试集上传记录
                responseVO.setCode(ResponseCode.RECORD_NULL.value());
                responseVO.setMsg("用户还没有上传任何测试集！");
                return responseVO;
            }else {
                List<TTestsetEntity> list_data = new ArrayList<TTestsetEntity>();
                for(int i=0;i<list.size();i++){
                    String setid= list.get(i).getTestsetId();
                    list_data.add(tTestsetRepository.selectOne(Wrappers.<TTestsetEntity>lambdaQuery().eq(TTestsetEntity::getTestsetId,setid)));
                }

                responseVO.setCode(ResponseCode.OK.value());
                responseVO.setMsg(ResponseCode.OK.getDescription());
                responseVO.setData(list_data);
                return responseVO;
            }
        } catch (Exception e) {
            log.error("SearchTestsetRecord 异常", e);
            return responseVO;
        }
    }



}
