package com.bupt.dlplatform.controller;


import com.bupt.dlplatform.consumer.ModelRecordConsumer;
import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.model.TModelEntity;
import com.bupt.dlplatform.vo.ModelRecordInputVO;
import com.bupt.dlplatform.vo.ModelRecordOutputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class ModelRecordController {
    @Autowired
    ModelRecordConsumer modelRecordConsumer;

    /**
     * 查询模型记录
     * @param modelRecordInputVO
     * @return
     */
    @ApiOperation(value = "查询私有模型",notes = "通过获取的userId，在数据库中查询模型记录")
    @ApiResponse(code=200,message = "模型生成时间，模型Id,模型名，训练网络，训练集名，任务")
    @RequestMapping(value = "/dlplatform/searchModelRecord", method = RequestMethod.POST)
    public ResponseVO searchTerstRecord(@Validated ModelRecordInputVO modelRecordInputVO){
        ResponseVO responseVO = new ResponseVO(ResponseCode.PARAM_INVALID);
        try{
            ResponseVO<List<ModelRecordOutputVO>> res = modelRecordConsumer.searchModelRecord(modelRecordInputVO);
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
            log.error("searchModelRecord ---> 异常！", e);
        }
        return responseVO;

    }

    /**
     * 删除私有模型
     * @param modelRecordInputVO
     * @return
     */
    @ApiOperation(value = "删除私有模型",notes = "通过获取的modelId，在数据库中查询该条记录并且删除")
    @ApiResponse(code=200,message = "记录已删除")
    @RequestMapping(value = "/dlplatform/deleteModel", method = RequestMethod.POST)
    public ResponseVO deleteModel(@RequestBody  @Validated ModelRecordInputVO modelRecordInputVO){
        ResponseVO responseVO = new ResponseVO(ResponseCode.PARAM_INVALID);
        try{
            ResponseVO res = modelRecordConsumer.deleteModel(modelRecordInputVO);
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
            log.error("deleteModel ---> 异常！", e);
        }
        return responseVO;

    }





}
