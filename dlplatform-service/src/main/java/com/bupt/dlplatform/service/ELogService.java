package com.bupt.dlplatform.service;

import com.bupt.dlplatform.vo.*;

import java.util.List;

/**
 * Created by huhx on 2020/12/15
 */
public interface ELogService {
    /**
     * 增加模型
     * @return
     */
    public ResponseVO addELog(ELogInputVO eLogInputVO);

    /**
     * 查询模型
     * Id方式
     * @return
     */
    public ResponseVO<ELogOutputVO> getELog(String logId);

    /**
     * 查询日志列表
     * modelId方式
     * @return
     */
    public ResponseVO<List<ELogOutputVO>> getELogListByModelId(String modelId);

    /**
     * 查询日志列表
     * deviceId方式
     * @return
     */
    public ResponseVO<List<ELogOutputVO>> getELogListByDeviceId(String deviceId);

    /**
     * 查询日志列表
     * @return
     */
    public ResponseVO<List<ELogOutputVO>> getEDeviceList();
}
