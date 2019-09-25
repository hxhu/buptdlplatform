package com.bupt.dlplatform.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.mapper.TModelRecordRepository;
import com.bupt.dlplatform.mapper.TModelRepository;
import com.bupt.dlplatform.model.TModelEntity;
import com.bupt.dlplatform.model.TModelRecordEntity;
import com.bupt.dlplatform.service.ModelRecordInfoService;
import com.bupt.dlplatform.vo.ModelRecordInputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ModelRecordInfoServiceImpl implements ModelRecordInfoService {
    @Autowired
    private TModelRecordRepository tModelRecordRepository;
    @Autowired
    private TModelRepository tModelRepository;

    //查询模型记录
    @Override
    public ResponseVO<List<TModelEntity>> modelRecord(ModelRecordInputVO request){
        ResponseVO responseVO =new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);
        try {

            String userId = request.getUserId();

            List<TModelRecordEntity> list = tModelRecordRepository.selectList
                    (Wrappers.<TModelRecordEntity>lambdaQuery().eq(TModelRecordEntity::getUserId,userId));

            if (CollectionUtils.isEmpty(list)) {
                responseVO.setCode(ResponseCode.RECORD_NULL.value());
                responseVO.setMsg("用户没有任何模型记录！");
                return responseVO;
            }else {
                List<TModelEntity> list_data = new ArrayList<TModelEntity>();
                for(int i=0;i<list.size();i++){
                    String setid= list.get(i).getModelId();
                    list_data.add(tModelRepository.selectOne(Wrappers.<TModelEntity>lambdaQuery().eq(TModelEntity::getModelId,setid)));
                }

                responseVO.setCode(ResponseCode.OK.value());
                responseVO.setMsg(ResponseCode.OK.getDescription());
                responseVO.setData(list_data);
                return responseVO;
            }
        } catch (Exception e) {
            log.error("SearchModelRecord 异常", e);
            return responseVO;
        }

    }

}
