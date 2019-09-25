package com.bupt.dlplatform.controller;

import com.bupt.dlplatform.consumer.AnalyzeConsumer;
import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.model.TAnalyseRecordEntity;
import com.bupt.dlplatform.vo.AnalyzeInputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class AnalyzeController {
    @Autowired
    AnalyzeConsumer analyzeConsumer;

    @RequestMapping(value = "/dlplatform/searchAnalyzeRecord", method = RequestMethod.POST)
    public ResponseVO searchAnalyzeRecord(@Validated AnalyzeInputVO analyzeInputVO){
        ResponseVO responseVO = new ResponseVO(ResponseCode.PARAM_INVALID);
        try{
            ResponseVO<List<TAnalyseRecordEntity>> res = analyzeConsumer.searchAnalyzeRecord(analyzeInputVO);
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
            log.error("ModelRecordController ---> 异常！", e);
        }
        return responseVO;

    }

}
