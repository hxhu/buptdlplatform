package com.bupt.dlplatform.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.mapper.TModelRecordRepository;
import com.bupt.dlplatform.mapper.TModelRepository;
import com.bupt.dlplatform.mapper.TTrainRecordRepository;
import com.bupt.dlplatform.model.TModelEntity;
import com.bupt.dlplatform.model.TModelRecordEntity;
import com.bupt.dlplatform.model.TTrainRecordEntity;
import com.bupt.dlplatform.service.ModelRecordInfoService;
import com.bupt.dlplatform.util.FtpUtil;
import com.bupt.dlplatform.vo.ModelRecordInputVO;
import com.bupt.dlplatform.vo.ModelRecordOutputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class ModelRecordInfoServiceImpl implements ModelRecordInfoService {
    @Autowired
    private TModelRecordRepository tModelRecordRepository;
    @Autowired
    private TModelRepository tModelRepository;
    @Autowired
    private TTrainRecordRepository tTrainRecordRepository;
    @Autowired
    private FtpUtil ftpUtil;

    /**
     * 查找模型记录
     * @param request
     * @return
     */
    @Override
    public ResponseVO<List<ModelRecordOutputVO>> modelRecord(ModelRecordInputVO request){
        ResponseVO responseVO =new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);
        try {
            /*从request里获取UserId*/
            String userId = request.getUserId();

            /*从ModelRecord表里获取模型信息：modelId,trainId,configId.*/
            List<TModelRecordEntity> list = tModelRecordRepository.selectList
                    (Wrappers.<TModelRecordEntity>lambdaQuery().eq(TModelRecordEntity::getUserId,userId));
            if (CollectionUtils.isEmpty(list)) {
                responseVO.setCode(ResponseCode.RECORD_NULL.value());
                responseVO.setMsg("用户没有任何模型记录！");
                return responseVO;
            }else {
                List<ModelRecordOutputVO> res = new ArrayList<>();
                TModelEntity eachModelRecord = new TModelEntity();
                TTrainRecordEntity train_data =new TTrainRecordEntity();
                ModelRecordOutputVO eachOutputRecord = new ModelRecordOutputVO();
                Date createTime;
                String modelId;
                String modelName;
                String trainId;
                String trainName;
                String task;
                for(int i=0;i<list.size();i++){
                    modelId = list.get(i).getModelId();
                    trainId = list.get(i).getTrainId();

                    train_data =tTrainRecordRepository.selectOne(Wrappers.<TTrainRecordEntity>lambdaQuery().eq(TTrainRecordEntity::getTrainId,trainId));
                    trainName = train_data.getTrainName();
                    eachModelRecord=tModelRepository.selectOne(Wrappers.<TModelEntity>lambdaQuery().eq(TModelEntity::getModelId,modelId));
                    modelName =eachModelRecord.getModelName();
                    createTime = eachModelRecord.getCreateTime();
                    task = eachModelRecord.getTask();

                    eachOutputRecord.setCreateTime(createTime);
                    eachOutputRecord.setModelId(modelId);
                    eachOutputRecord.setModelName(modelName);
                    eachOutputRecord.setTrainName(trainName);
                    eachOutputRecord.setTask(task);
                    res.add(eachOutputRecord);
                }
                responseVO.setCode(ResponseCode.OK.value());
                responseVO.setMsg(ResponseCode.OK.getDescription());
                responseVO.setData(res);
            }
        } catch (Exception e) {
            log.error("SearchModelRecord 异常", e);
            responseVO.setMsg("SearchModelRecord 异常");

        }
        return responseVO;

    }

    /**
     * 下载模型
     * @param request
     * modelId
     * @return

    @Override
    public ResponseVO downloadModel(ModelRecordInputVO request) {
        ResponseVO responseVO =new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);
        try{
            String modelId = request.getModelId();
            TModelEntity tModelEntity=tModelRepository.selectOne(Wrappers.<TModelEntity>lambdaQuery().eq(TModelEntity::getModelId,modelId));
            String location = tModelEntity.getModelLocation();
            String destLocation =request.getDestLocation();
            //下载模型文件
            if(ftpUtil.downloadFile(location,destLocation)){
                responseVO.setCode(ResponseCode.OK.value());
                responseVO.setMsg(ResponseCode.OK.getDescription());
                responseVO.setData("下载成功");
            }else{
                responseVO.setCode(ResponseCode.OPERATE_ERROR.value());
                responseVO.setMsg(ResponseCode.OPERATE_ERROR.getDescription());
                responseVO.setData("下载失败");
            }
        }catch (Exception e) {
            log.error("downloadModel 异常", e);
            responseVO.setMsg(ResponseCode.SYSTEM_EXCEPTION.getDescription());
            responseVO.setData("下载出错");
        }
        return responseVO;
    }*/

    /**
     * 删除模型记录
     * @param request
     * @return
     */
    @Override
    public ResponseVO deleteModel(ModelRecordInputVO request) {
        ResponseVO responseVO =new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);
        try{
            String modelId=request.getModelId();
            int res_model=tModelRepository.delete(Wrappers.<TModelEntity>lambdaQuery().eq(TModelEntity::getModelId,modelId));
            int res_modelRecord=tModelRecordRepository.delete(Wrappers.<TModelRecordEntity>lambdaQuery().eq(TModelRecordEntity::getModelId,modelId));
            if(res_model==1 && res_modelRecord==1){
                responseVO.setCode(ResponseCode.OK.value());
                responseVO.setMsg(ResponseCode.OK.getDescription());
                responseVO.setData("删除成功！");
            }else {
                responseVO.setCode(ResponseCode.OPERATE_ERROR.value());
                responseVO.setMsg(ResponseCode.OPERATE_ERROR.getDescription());
                responseVO.setData("删除异常！");
            }

        }catch (Exception e) {
            log.error("downloadModel 异常", e);
            responseVO.setMsg(ResponseCode.SYSTEM_EXCEPTION.getDescription());
            responseVO.setData("删除模型出错！");
        }
        return responseVO;
    }

}
