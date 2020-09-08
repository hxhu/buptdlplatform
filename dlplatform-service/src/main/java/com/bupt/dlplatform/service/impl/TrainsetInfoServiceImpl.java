package com.bupt.dlplatform.service.impl;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.mapper.TTrainsetRecordRepository;
import com.bupt.dlplatform.mapper.TTrainsetRepository;

import com.bupt.dlplatform.model.TTrainsetEntity;
import com.bupt.dlplatform.model.TTrainsetRecordEntity;
import com.bupt.dlplatform.service.TrainsetInfoService;
import com.bupt.dlplatform.vo.ResponseVO;
import com.bupt.dlplatform.vo.TrainsetInputVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class TrainsetInfoServiceImpl implements TrainsetInfoService {
    @Autowired
    private TTrainsetRepository tTrainsetRepository;
    @Autowired
    private TTrainsetRecordRepository tTrainsetRecordRepository;

    //训练集记录查看
    @Override
    public ResponseVO<TTrainsetEntity> trainsetRecord(TrainsetInputVO request) {
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);
        try {

            String userId = request.getUserId();

            List<TTrainsetRecordEntity> list = tTrainsetRecordRepository.selectList
                    (Wrappers.<TTrainsetRecordEntity>lambdaQuery().eq(TTrainsetRecordEntity::getUserId,userId));

            if (CollectionUtils.isEmpty(list)) {
                //判断用户是否有训练集上传记录
                responseVO.setCode(ResponseCode.RECORD_NULL.value());
                responseVO.setMsg("用户还没有上传任何训练集！");
                return responseVO;
            }else {
                List<TTrainsetEntity> list_data = new ArrayList<TTrainsetEntity>();
                for(int i=0;i<list.size();i++){
                    String setid= list.get(i).getTrainsetId();
                    list_data.add(tTrainsetRepository.selectOne(Wrappers.<TTrainsetEntity>lambdaQuery().eq(TTrainsetEntity::getTrainsetId,setid)));
                }

                responseVO.setCode(ResponseCode.OK.value());
                responseVO.setMsg(ResponseCode.OK.getDescription());
                responseVO.setData(list_data);
                return responseVO;
            }
        } catch (Exception e) {
            log.error("SearchTrainsetRecord 异常", e);
            return responseVO;
        }
    }


    /**
     * 删除训练集
     * @param request
     * @return
     */
    @Override
    public ResponseVO deleteTrainset(TrainsetInputVO request){
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);
        try {
            String trainsetId=request.getTrainsetId();
            int rows= tTrainsetRepository.delete(Wrappers.<TTrainsetEntity>lambdaQuery().eq(TTrainsetEntity::getTrainsetId,trainsetId));
            int rows_record = tTrainsetRecordRepository.delete(Wrappers.<TTrainsetRecordEntity>lambdaQuery().eq(TTrainsetRecordEntity::getTrainsetId,trainsetId));
            if(rows==1 && rows_record==1){
                responseVO.setCode(ResponseCode.OK.value());
                responseVO.setMsg(ResponseCode.OK.getDescription());
                responseVO.setData("训练集删除成功");

            }else{
                responseVO.setCode(ResponseCode.OPERATE_ERROR.value());
                responseVO.setMsg(ResponseCode.OPERATE_ERROR.getDescription());
                log.error("deleteTrainset异常");
            }
        }catch (Exception e){
            log.error("deleteTrainset异常");
        }
        return responseVO;
    }

}
