package com.bupt.dlplatform.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.mapper.TAnalyseRecordRepository;
import com.bupt.dlplatform.mapper.TAnalyseResultRepository;
import com.bupt.dlplatform.model.TAnalyseRecordEntity;
import com.bupt.dlplatform.model.TAnalyseResultEntity;
import com.bupt.dlplatform.service.AnalyzeInfoService;
import com.bupt.dlplatform.vo.AnalyzeInputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class AnalyzeInfoServiceImpl implements AnalyzeInfoService {
    @Autowired
    private TAnalyseRecordRepository tAnalyseRecordRepository;
    @Autowired
    private TAnalyseResultRepository tAnalyseResultRepository;


    //查询评估记录
    @Override
    public ResponseVO<TAnalyseRecordEntity> searchAnalyzeRecord(AnalyzeInputVO request){
        ResponseVO responseVO =new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);
        try {
            String userId = request.getUserId();

            List<TAnalyseRecordEntity> list = tAnalyseRecordRepository.selectList
                    (Wrappers.<TAnalyseRecordEntity>lambdaQuery().eq(TAnalyseRecordEntity::getUserId,userId));

            if (CollectionUtils.isEmpty(list)) {
                //判断用户是否有评估记录
                responseVO.setCode(ResponseCode.RECORD_NULL.value());
                responseVO.setMsg("用户还没有进行任何评估！");
                return responseVO;
            }else {
                List<TAnalyseResultEntity> list_data = new ArrayList<TAnalyseResultEntity>();
                for (int i = 0; i < list.size(); i++) {
                    String setid = list.get(i).getAnalyseId();
                    list_data.add(tAnalyseResultRepository.selectOne(Wrappers.<TAnalyseResultEntity>lambdaQuery().eq(TAnalyseResultEntity::getAnalyseId, setid)));
                }

                responseVO.setCode(ResponseCode.OK.value());
                responseVO.setMsg(ResponseCode.OK.getDescription());
                responseVO.setData(list);
                return responseVO;
            }

        }catch (Exception e){
            log.error("SearchAnalyzeRecord 异常", e);
            return responseVO;
        }
    }


}
