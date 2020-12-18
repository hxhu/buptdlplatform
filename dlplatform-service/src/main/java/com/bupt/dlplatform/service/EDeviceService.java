package com.bupt.dlplatform.service;

import com.bupt.dlplatform.vo.*;

import java.util.List;

/**
 * Created by huhx on 2020/12/4
 */
public interface EDeviceService {
    /**
     * 增加设备
     * @return
     */
    public ResponseVO addEDevice(EDeviceInputVO eDeviceInputVO);

    /**
     * 修改设备
     * @return
     */
    public ResponseVO updateEDevice(EDeviceInputVO eDeviceInputVO);

    /**
     * 更新设备的配置文件
     * @return
     */
    public ResponseVO updateEFileIdSet(String deviceId, String fileId);

    /**
     * 查询设备
     * Id方式
     * @return
     */
    public ResponseVO<EDeviceOutputVO> getEDevice(String deviceId);

    /**
     * 查询设备列表
     * @return
     */
    public ResponseVO<List<EDeviceOutputVO>> getEDeviceList();

    /**
     * 删除设备
     * @return
     */
    public ResponseVO deleteEDevice(String deviceId);

    /**
     * 删除某个文件
     * @return
     */
    public ResponseVO deleteFile(String deviceId, String fileId);
}
