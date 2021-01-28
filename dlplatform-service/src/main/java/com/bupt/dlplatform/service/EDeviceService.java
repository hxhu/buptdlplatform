package com.bupt.dlplatform.service;

import com.baomidou.mybatisplus.extension.api.R;
import com.bupt.dlplatform.vo.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
     * 批量增加设备
     */
    public R createDevices(MultipartFile avatar);

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
     * 删除文件
     * @return
     */
    public ResponseVO deleteFile(String deviceId, List<String> fileIds);
}
