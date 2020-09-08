package com.bupt.dlplatform.controller;


import com.bupt.dlplatform.consumer.ModelTrainConsumer;
import com.bupt.dlplatform.data.ResponseCode;

import com.bupt.dlplatform.model.TTrainRecordEntity;
import com.bupt.dlplatform.vo.ModelTrainInputVO;
import com.bupt.dlplatform.vo.ModelTrainOutputVO;
import com.bupt.dlplatform.vo.ResponseVO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@Slf4j
public class ModelTrainController {
    @Autowired
    ModelTrainConsumer modelTrainConsumer;

    /**
     * 查看训练记录
     * @param modelTrainInputVO
     * @return
     */
    @RequestMapping(value = "/dlplatform/searchTrainRecord", method = RequestMethod.POST)
    public ResponseVO searchTerstRecord(@Validated ModelTrainInputVO modelTrainInputVO){
        ResponseVO responseVO = new ResponseVO(ResponseCode.PARAM_INVALID);
        try{
            ResponseVO<List<ModelTrainOutputVO>> res = modelTrainConsumer.searchTrainRecord(modelTrainInputVO);
            if(res.getCode()==ResponseCode.OK.value()){
                Object se = res.getData();
                responseVO.setCode(ResponseCode.OK.value());
                responseVO.setMsg(ResponseCode.OK.getDescription());
                responseVO.setData(se);
            }else {
                responseVO.setCode(res.getCode());
                responseVO.setMsg(res.getMsg());
            }
        }catch (Exception e){
            responseVO.setCode(ResponseCode.SYSTEM_EXCEPTION.value());
            responseVO.setMsg(ResponseCode.SYSTEM_EXCEPTION.getDescription());
            log.error("ModelTrainController.searchTerstRecord ---> 异常！", e);
        }
        return responseVO;

    }

    /**
     * 查看训练情况
     * @param modelTrainInputVO
     * @return
     */
    @RequestMapping(value = "/dlplatform/searchTrainStatus", method = RequestMethod.POST)
    public ResponseVO searchTerstStatus( @RequestBody @Validated ModelTrainInputVO modelTrainInputVO){
        ResponseVO responseVO = new ResponseVO(ResponseCode.PARAM_INVALID);
        try{
            ResponseVO<ModelTrainOutputVO> res = modelTrainConsumer.searchTrainStatus(modelTrainInputVO);
            if(res.getCode()==ResponseCode.OK.value()){
                Object se = res.getData();
                responseVO.setCode(ResponseCode.OK.value());
                responseVO.setMsg(ResponseCode.OK.getDescription());
                responseVO.setData(se);
            }else {
                responseVO.setCode(res.getCode());
                responseVO.setMsg(res.getMsg());
            }
        }catch (Exception e){
            responseVO.setCode(ResponseCode.SYSTEM_EXCEPTION.value());
            responseVO.setMsg(ResponseCode.SYSTEM_EXCEPTION.getDescription());
            log.error("ModelTrainController.searchTerstStatus ---> 异常！", e);
        }
        return responseVO;
    }

    /**
     * 模型训练--获取训练集
     * @param modelTrainInputVO
     * @return
     */
    @RequestMapping(value = "/dlplatform/getTrainsetName", method = RequestMethod.POST)
    public ResponseVO getTrainsetName(@Validated ModelTrainInputVO modelTrainInputVO){
        ResponseVO responseVO = new ResponseVO(ResponseCode.PARAM_INVALID);
        try{
            ResponseVO<List<ModelTrainOutputVO>> res=modelTrainConsumer.getTrainsetName(modelTrainInputVO);
            if(res.getCode()==ResponseCode.OK.value()){
                Object se = res.getData();
                responseVO.setCode(ResponseCode.OK.value());
                responseVO.setMsg(ResponseCode.OK.getDescription());
                responseVO.setData(se);
            }

        }catch (Exception e){
            responseVO.setCode(ResponseCode.SYSTEM_EXCEPTION.value());
            responseVO.setMsg(ResponseCode.SYSTEM_EXCEPTION.getDescription());
            log.error("ModelTrainController.getTrainsetName ---> 异常！", e);
        }

        return responseVO;
    }

    /**
     * 模型训练--获取训练网络
     * @param modelTrainInputVO
     * @return
     */
    @RequestMapping(value = "/dlplatform/getTrainNetwork", method = RequestMethod.POST)
    public ResponseVO getTrainNetwork(ModelTrainInputVO modelTrainInputVO){
        ResponseVO responseVO = new ResponseVO(ResponseCode.PARAM_INVALID);
        try{
            ResponseVO res=modelTrainConsumer.getTrainNetwork(modelTrainInputVO);
            if(res.getCode()==ResponseCode.OK.value()){
                Object se = res.getData();
                responseVO.setCode(ResponseCode.OK.value());
                responseVO.setMsg(ResponseCode.OK.getDescription());
                responseVO.setData(se);
            }
        }catch (Exception e){
            responseVO.setCode(ResponseCode.SYSTEM_EXCEPTION.value());
            responseVO.setMsg(ResponseCode.SYSTEM_EXCEPTION.getDescription());
            log.error("ModelTrainController.getTrainNetwork ---> 异常！", e);
        }
        return responseVO;
    }

    /**
     * 模型训练--提交基本信息
     * Input: trainName, trainsetId, network, gpus, description
     * 中间生成：trainId, configId, logId
     * 返回：trainId, 默认超参数文件路径
     * @param modelTrainInputVO
     * @return
     */
    @RequestMapping(value = "/dlplatform/submitTrainMessage", method = RequestMethod.POST)
    public ResponseVO submitTrainMessage( @RequestBody @Validated ModelTrainInputVO modelTrainInputVO){
        ResponseVO responseVO = new ResponseVO(ResponseCode.PARAM_INVALID);
        try{
            ResponseVO res=modelTrainConsumer.submitTrainMessage(modelTrainInputVO);
            if(res.getCode()==ResponseCode.OK.value()){
                Object se = res.getData();
                responseVO.setCode(ResponseCode.OK.value());
                responseVO.setMsg(ResponseCode.OK.getDescription());
                responseVO.setData(se);
            }

        }catch (Exception e){
            responseVO.setCode(ResponseCode.SYSTEM_EXCEPTION.value());
            responseVO.setMsg(ResponseCode.SYSTEM_EXCEPTION.getDescription());
            log.error("ModelTrainController.submitTrainMessage ---> 异常！", e);
        }
        return responseVO;
    }

    /**
     * 模型训练--下载默认参数文件
     * @param request
     * @return
     */
    @RequestMapping( value = "/dlplatform/douwnloadParameter",method = RequestMethod.POST)
    public ResponseVO chooseParameter( @RequestBody @Validated ModelTrainInputVO request){
        ResponseVO responseVO = new ResponseVO(ResponseCode.PARAM_INVALID);
        return responseVO;
    }

    /**
     * 模型训练--提交参数文件
     * @param request
     * @return
     */
    @RequestMapping(value = "/dlplatform/submitParameter",method = RequestMethod.POST)
    public ResponseVO submitParameter( @RequestBody @Validated ModelTrainInputVO request){
        ResponseVO responseVO = new ResponseVO(ResponseCode.PARAM_INVALID);
        return responseVO;
    }

    /**
     * 模型训练--开始训练
     * @param modelTrainInputVO
     * @return
     */
    @RequestMapping(value = "/dlplatform/startTrain", method = RequestMethod.POST)
    public ResponseVO startTrain( @RequestBody @Validated ModelTrainInputVO modelTrainInputVO){
        ResponseVO responseVO = new ResponseVO(ResponseCode.PARAM_INVALID);
        try{
            ResponseVO res=modelTrainConsumer.startTrain(modelTrainInputVO);

            final String uri = "http://127.0.0.1:8008/dltrain/getParam";
            ModelTrainInputVO modelTrainInputVO1= new ModelTrainInputVO();
            modelTrainInputVO.setTrainId("001");
            modelTrainInputVO.setDescription("test");
            modelTrainInputVO.setGpus("2");
            modelTrainInputVO.setTrainName("001t");
            modelTrainInputVO.setUserId("13164294368");
            modelTrainInputVO.setNetwork("SSD");

            RestTemplate restTemplate = new RestTemplate();
            ModelTrainInputVO result = restTemplate.postForObject( uri, modelTrainInputVO, ModelTrainInputVO.class);

            System.out.println(result);


            if(res.getCode()==ResponseCode.OK.value()){
                Object se = res.getData();
                responseVO.setCode(ResponseCode.OK.value());
                responseVO.setMsg(ResponseCode.OK.getDescription());
                responseVO.setData(se);
            }

        }catch (Exception e){
            responseVO.setCode(ResponseCode.SYSTEM_EXCEPTION.value());
            responseVO.setMsg(ResponseCode.SYSTEM_EXCEPTION.getDescription());
            log.error("ModelTrainController.startTrain ---> 异常！", e);
        }
        return responseVO;
    }


}
