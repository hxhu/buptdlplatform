package com.bupt.dlplatform.service;

import com.bupt.dlplatform.model.TModelEntity;
import com.bupt.dlplatform.model.TModelRecordEntity;
import com.bupt.dlplatform.vo.ModelRecordInputVO;
import com.bupt.dlplatform.vo.ModelRecordOutputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ModelRecordInfoService {

    /**
     * 查询模型记录列表
     * @param request
     * @return
     */
    ResponseVO<List<ModelRecordOutputVO>> modelRecord(ModelRecordInputVO request);

    /**
     * 下载模型
     * @param request
     * @return

    ResponseVO downloadModel( ModelRecordInputVO request);*/

    /**
     * 删除模型
     * @param request
     * @return
     */
    ResponseVO deleteModel( ModelRecordInputVO request);
}
