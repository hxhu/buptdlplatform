package com.bupt.dlplatform.service;

import com.bupt.dlplatform.vo.*;

/**
 * Created by huhx on 2020/10/3
 */
public interface DeviceService {
    /**
     * 新建设备
     */
    public ResponseVO createMDeviceEntity(MDeviceEntityInputVO request);

    /**
     * 更新设备
     */
    public ResponseVO updateMDeviceEntity(MDeviceEntityInputVO request);

    /**
     * 读取一个设备（Id方式）
     */
    public ResponseVO<MDeviceEntityOutputVO> getMDeviceEntityById(String id);

    /**
     * 删除设备
     */
    public ResponseVO deleteMDeviceEntity(String id);
}
