package com.bupt.dlplatform.service;

import com.bupt.dlplatform.vo.*;

import java.util.List;

/**
 * Created by huhx on 2020/12/23
 */
public interface EDataSetService {
    /**
     * 增加数据集
     *
     * @return
     */
    public ResponseVO addEDataSet(EDataSetInputVO eDataSetInputVO);

    /**
     * 修改数据集
     *
     * @return
     */
    public ResponseVO updateEDataSet(EDataSetInputVO eDataSetInputVO);

    /**
     * 查询数据集
     * Id方式
     *
     * @return
     */
    public ResponseVO<EDataSetOutputVO> getEDataSet(String dataSetId);

    /**
     * 删除数据集
     *
     * @return
     */
    public ResponseVO deleteEDataSet(String dataSetId);
}
