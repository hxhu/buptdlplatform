package com.bupt.dlplatform.service;

import com.bupt.dlplatform.vo.*;

import java.util.List;
import java.util.Map;

/**
 * Created by huhx on 2020/12/9
 */
public interface ERHeartbeatService {
    /**
     * 增加心跳
     * @return
     */
    public ResponseVO addERHeartbeat(ERHeartbeatInputVO erHeartbeatInputVO);

    /**
     * 修改心跳
     * @return
     */
    public ResponseVO updateERHeartbeat(ERHeartbeatInputVO erHeartbeatInputVO);

    /**
     * 查询心跳
     * Id方式
     * @return
     */
    public ResponseVO<ERHeartbeatOutputVO> getERHeartbeat(String deviceId);

    /**
     * 查询设备状态
     * Id列表方式
     * @return
     */
    public ResponseVO<Map<String,String>> getDeviceStatus(StatusMapOutputVO statusMapOutputVO);

    /**
     * 删除心跳
     * @return
     */
    public ResponseVO deleteERHeartbeat(String deviceId);
}
