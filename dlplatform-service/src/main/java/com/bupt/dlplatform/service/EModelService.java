package com.bupt.dlplatform.service;

import com.bupt.dlplatform.vo.EModelInputVO;
import com.bupt.dlplatform.vo.EModelOutputVO;
import com.bupt.dlplatform.vo.PushModelInputVO;
import com.bupt.dlplatform.vo.ResponseVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huhx on 2020/12/4
 */
public interface EModelService {

    /**
     * 增加模型
     * @return
     */
    public ResponseVO addEModel(EModelInputVO eModelInputVO);

    /**
     * 修改模型
     * @return
     */
    public ResponseVO updateEModel(EModelInputVO eModelInputVO);

    /**
     * 查询模型
     * Id方式
     * @return
     */
    public ResponseVO<EModelOutputVO> getEModel(String modelId);

    /**
     * 查询模型列表
     * @return
     */
    public ResponseVO<List<EModelOutputVO>> getEModelList();

    /**
     * 删除模型
     * @return
     */
    public ResponseVO deleteEModel(String modelId);


    /**
     * 推送模型
     * @return
     */
    public ResponseVO pushModelWithDevices(PushModelInputVO pushModelInputVO);
}
