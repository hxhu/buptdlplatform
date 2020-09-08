package com.bupt.dlplatform.controller;


import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.vo.ModelRecordInputVO;
import com.bupt.dlplatform.vo.ModelUploadInputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ModelController {

    /**
     * 添加模型记录
     */
    @RequestMapping(value = "/dlplatform/model/uploadRecord", method = RequestMethod.POST)
    public ResponseVO uploadRecord(ModelUploadInputVO modelUploadInputVO){

        ResponseVO responseVO = new ResponseVO(ResponseCode.PARAM_INVALID);

        return responseVO;


    }
}
