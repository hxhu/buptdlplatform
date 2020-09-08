package com.bupt.dlplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.mapper.TTrainRecordRepository;
import com.bupt.dlplatform.mapper.TTrainsetRecordRepository;
import com.bupt.dlplatform.mapper.TTrainsetRepository;
import com.bupt.dlplatform.model.TTrainRecordEntity;
import com.bupt.dlplatform.model.TTrainsetEntity;
import com.bupt.dlplatform.model.TTrainsetRecordEntity;
import com.bupt.dlplatform.service.ModelTrainInfoService;
import com.bupt.dlplatform.vo.ModelTrainInputVO;
import com.bupt.dlplatform.vo.ModelTrainOutputVO;
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
public class ModelTrainInfoServiceImpl implements ModelTrainInfoService {
    @Autowired
    TTrainRecordRepository tTrainRecordRepository;
    @Autowired
    TTrainsetRepository tTrainsetRepository;
    @Autowired
    TTrainsetRecordRepository tTrainsetRecordRepository;

    /**
     * 查询训练记录
     * @param request
     * @return
     */
    @Override
    public ResponseVO<List<ModelTrainOutputVO>> trainRecord(ModelTrainInputVO request) {
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
                List<ModelTrainOutputVO> train_list= new ArrayList<>();
                for (int i =0; i< list.size();i++){
                    ModelTrainOutputVO trainRecord = new ModelTrainOutputVO();
                    trainRecord.setTrainId(list.get(i).getTrainId());
                    trainRecord.setTrainName(list.get(i).getTrainName());
                    trainRecord.setTraintStartTime(list.get(i).getTrainStartTime());

                    trainRecord.setNetwork(list.get(i).getNetwork());
                    trainRecord.setTrainsetId(list.get(i).getTrainsetId());
                    trainRecord.setTrainStatus(list.get(i).getTrainStatus());
                    trainRecord.setTraintStopTime(list.get(i).getTrainStopTime());

                    TTrainsetEntity tTrainsetEntity = tTrainsetRepository.selectOne
                            (Wrappers.<TTrainsetEntity>lambdaQuery().eq(TTrainsetEntity::getTrainsetId,list.get(i).getTrainsetId()));
                    String trainsetName = tTrainsetEntity.getTrainsetName();
                    trainRecord.setTrainsetName(trainsetName);
                    train_list.add(trainRecord);
                }

                responseVO.setCode(ResponseCode.OK.value());
                responseVO.setMsg(ResponseCode.OK.getDescription());
                responseVO.setData(train_list);
                return responseVO;
            }
        } catch (Exception e) {
            log.error("SearchTrainRecord 异常", e);
            return responseVO;
        }

    }

    /**
     * 查询训练状态
     * @param request
     * @return
     */
    @Override
    public ResponseVO<ModelTrainOutputVO> trainStatus(ModelTrainInputVO request) {
        ResponseVO responseVO =new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);
        try{
            String userId = request.getUserId();
            String trainId = request.getTrainId();
            TTrainRecordEntity tTrainRecordEntity = tTrainRecordRepository.selectOne
                    (Wrappers.<TTrainRecordEntity>lambdaQuery().eq(TTrainRecordEntity::getTrainId,trainId));
            String trainName = tTrainRecordEntity.getTrainName();
            String trainStatus = tTrainRecordEntity.getTrainStatus();
            Date startTime = tTrainRecordEntity.getTrainStartTime();
            Date stopTime = tTrainRecordEntity.getTrainStopTime();
            String configId = tTrainRecordEntity.getConfigId();
            String logId = tTrainRecordEntity.getLogId();
            String network =tTrainRecordEntity.getNetwork();
            String trainsetId = tTrainRecordEntity.getTrainsetId();
            String trainsetName = tTrainsetRepository.selectOne
                    (Wrappers.<TTrainsetEntity>lambdaQuery().eq(TTrainsetEntity::getTrainsetId,trainsetId)).getTrainsetName();

            ModelTrainOutputVO modelTrainOutputVO = new ModelTrainOutputVO();
            modelTrainOutputVO.setTrainId(trainId);
            modelTrainOutputVO.setTrainName(trainName);
            modelTrainOutputVO.setTrainStatus(trainStatus);
            modelTrainOutputVO.setTraintStartTime(startTime);
            modelTrainOutputVO.setTraintStopTime(stopTime);
            modelTrainOutputVO.setConfigId(configId);
            modelTrainOutputVO.setLogId(logId);
            modelTrainOutputVO.setNetwork(network);
            modelTrainOutputVO.setTrainsetName(trainsetName);
            modelTrainOutputVO.setTrainsetId(trainsetId);

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData(modelTrainOutputVO);
            return responseVO;

        }catch (Exception e) {
            log.error("SearchTrainStatus 异常", e);
            responseVO.setCode(ResponseCode.SYSTEM_EXCEPTION.value());
            responseVO.setMsg(ResponseCode.SYSTEM_EXCEPTION.getDescription());
            return responseVO;
        }
    }

    /**
     * 模型训练--获取训练集名
     * @param request
     * @return
     */
    @Override
    public ResponseVO<List<ModelTrainOutputVO>> trainsetName(ModelTrainInputVO request){
        ResponseVO responseVO =new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);
        try{
            String  userId=request.getUserId();

            List<TTrainsetRecordEntity> trainset_list=tTrainsetRecordRepository.selectList
                    (Wrappers.<TTrainsetRecordEntity>lambdaQuery().eq(TTrainsetRecordEntity::getUserId,userId));

            if(!CollectionUtils.isEmpty(trainset_list)){
                List<ModelTrainOutputVO> list_result= new ArrayList<>();

                for(int i=0;i<trainset_list.size();i++){
                    ModelTrainOutputVO trainsetOption = new ModelTrainOutputVO();
                    String trainsetId = trainset_list.get(i).getTrainsetId();
                    TTrainsetEntity temp_trainset=tTrainsetRepository.selectOne
                            (Wrappers.<TTrainsetEntity>lambdaQuery().eq(TTrainsetEntity::getTrainsetId ,trainsetId));
                    if(temp_trainset !=null){
                        String trainsetName= temp_trainset.getTrainsetName();
                        trainsetOption.setTrainsetId(trainsetId);
                        trainsetOption.setTrainsetName(trainsetName);
                        list_result.add(trainsetOption);
                    }
                }
                responseVO.setCode(ResponseCode.OK.value());
                responseVO.setMsg(ResponseCode.OK.getDescription());
                responseVO.setData(list_result);
            }else{
                responseVO.setCode(ResponseCode.RECORD_NULL.value());
                responseVO.setMsg(ResponseCode.RECORD_NULL.getDescription());
                responseVO.setData("没有上传训练集，请先上传！");
            }

        }catch (Exception e){
            log.error("trainsetName 异常",e);
            responseVO.setData("Have an error for get trainsetName!");
        }

        return responseVO;
    }

    /**
     * 模型训练--获取训练网络
     * @param request
     * @return
     */
    @Override
    public ResponseVO getTrainNetwork(ModelTrainInputVO request){
        ResponseVO responseVO =new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);
        try{
            String[] network = new String[]{
                    "Yolo",
                    "SSD",
                    "Refinedet"
            };
            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData(network);


        }catch (Exception e){
            log.error("getTrainNetwork",e);
            responseVO.setData("Have an error for getTrainNetwork!");
        }
        return responseVO;
    }


    /**
     * 模型训练--提交基本信息
     * Input: trainName, trainsetId, network, gpus, description
     * 中间生成：trainId, configId, logId
     * 返回：trainId, 默认超参数文件路径
     * @param request
     * @return
     */
    @Override
    public ResponseVO<ModelTrainOutputVO> submitTrainMessage(ModelTrainInputVO request){
        ResponseVO responseVO =new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);
        try{
            TTrainRecordEntity tTrainRecordEntity = new TTrainRecordEntity();

            // create_time
            Long time =System.currentTimeMillis();
            Date createTime = new Date(time);
            String timeString= time.toString();
            tTrainRecordEntity.setCreateTime(createTime);

            //trainName
            String trainName = request.getTrainName();

            // trainset_id
            String trainsetId = request.getTrainsetId();

            // user_id
            String userId = request.getUserId();

            // network
            String network =request.getNetwork();

            // description
            String description = request.getDescription();

            // gpus
            String gpus = request.getGpus();

            // 生成trainId
            String trainId="tr"+userId+timeString;

            // train_status
            String trainStatus ="Stop";

            // 生成config_id
            String configId = "con" + userId+ timeString;

            // log_id
            String logId ="l"+userId+timeString;

            tTrainRecordEntity.setUserId(userId);
            tTrainRecordEntity.setTrainId(trainId);
            tTrainRecordEntity.setTrainName(trainName);
            tTrainRecordEntity.setTrainsetId(trainsetId);
            tTrainRecordEntity.setTrainStatus(trainStatus);
            tTrainRecordEntity.setNetwork(network);
            tTrainRecordEntity.setLogId(logId);
            tTrainRecordEntity.setCreateTime(createTime);
            tTrainRecordEntity.setGpus(gpus);
            tTrainRecordEntity.setDescription(description);
            tTrainRecordEntity.setConfigId(configId);

            tTrainRecordRepository.insert(tTrainRecordEntity);

            String defaultParam= "/home/"+network+".prototxt";

            //responseVO 内容
            ModelTrainOutputVO modelTrainOutputVO = new ModelTrainOutputVO();
            modelTrainOutputVO.setTrainId(trainId);
            modelTrainOutputVO.setConfigId(configId);
            modelTrainOutputVO.setDefaultParam(defaultParam);

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData(modelTrainOutputVO);

        }catch (Exception e){
            log.error("insertTrainRecord");
            responseVO.setData("Have an error for insertTrainRecord");
        }

        return responseVO;
    }

    /**
     * 下载参数文件
     * @param request
     * @return
     */
    @Override
    public ResponseVO<ModelTrainOutputVO> douwnloadParameter(ModelTrainInputVO request){
        ResponseVO responseVO =new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);
        return responseVO;
    }

    /**
     * 模型训练--上传参数文件
     * @param request
     * @return
     */
    @Override
    public ResponseVO submitParameter(ModelTrainInputVO request){
        ResponseVO responseVO =new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);
        return responseVO;
    }


    /**
     * 模型训练--开始训练
     * @param request
     * @return
     */
    @Override
    public ResponseVO startTrain(ModelTrainInputVO request){
        ResponseVO responseVO =new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);
        try{

            TTrainRecordEntity tTrainRecordEntity = new TTrainRecordEntity();
            tTrainRecordEntity.setTrainStatus("start");
            Long time =System.currentTimeMillis();
            Date startTime = new Date(time);
            tTrainRecordEntity.setTrainStartTime(startTime);
            //UpdateWrapper<TTrainRecordEntity> updateWrapper = new UpdateWrapper<>();
            //updateWrapper.eq("trainId",request.getTrainId());
            //tTrainRecordRepository.update(tTrainRecordEntity,updateWrapper);

            int update =tTrainRecordRepository.update(tTrainRecordEntity,Wrappers.<TTrainRecordEntity>lambdaUpdate().
                    eq(TTrainRecordEntity::getTrainId,request.getTrainId()));

            /////////////-------------------/////////

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData("开始训练");

        }catch (Exception e){
            log.error("startTrain 异常",e);
            responseVO.setData("Have an error for startTrain！");
        }

        return responseVO;
    }


}
