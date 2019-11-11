package com.bupt.dlplatform.controller;


import com.bupt.dlplatform.service.ModelRecordInfoService;
import com.bupt.dlplatform.vo.ModelRecordInputVO;
import com.bupt.dlplatform.vo.ModelRecordOutputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ModelRecordController {
    @Autowired
    ModelRecordInfoService modelRecordInfoService;

    /**
     * 查询模型记录
     * @param request
     * @return
     */
    @PostMapping("/dlplatform/searchModelRecord")
    public ResponseVO<List<ModelRecordOutputVO>> searchTestRecord(@RequestBody @Validated ModelRecordInputVO request){
        return  modelRecordInfoService.modelRecord(request);

    }

    /**
     * 下载模型
     * @param request
     * model_ID
     * @return

    @PostMapping("/dlplatform/downloadModel")
    public ResponseVO downloadModel(@RequestBody @Validated ModelRecordInputVO request){
        return modelRecordInfoService.downloadModel(request);
    }*/

    /**
     * 删除模型
     * @param request
     * model_ID
     * @return
     */

    @PostMapping("/dlplatform/deleteModel")
    public ResponseVO deleteModel(@RequestBody @Validated ModelRecordInputVO request){
        return modelRecordInfoService.deleteModel(request);
    }

}
