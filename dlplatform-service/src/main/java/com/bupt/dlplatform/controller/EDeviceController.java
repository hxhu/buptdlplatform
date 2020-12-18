package com.bupt.dlplatform.controller;

import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.exception.ServiceException;
import com.bupt.dlplatform.model.EDeviceEntity;
import com.bupt.dlplatform.model.EFileEntity;
import com.bupt.dlplatform.service.EDeviceService;
import com.bupt.dlplatform.vo.EDeviceInputVO;
import com.bupt.dlplatform.vo.EDeviceOutputVO;
import com.bupt.dlplatform.vo.ELogInputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by huhx on 2020/12/4
 */
@RestController
@RequestMapping("/dlplatform/EDevice")
public class EDeviceController {
    @Autowired
    private EDeviceService eDeviceService;

    /**
     * 增加设备
     * @return
     */
    @PostMapping("/create")
    public ResponseVO addEDevice(@RequestBody EDeviceInputVO request){
        return eDeviceService.addEDevice(request);
    }

    /**
     * 修改设备
     * @return
     */
    @PostMapping("/update")
    public ResponseVO updateEDevice(@RequestBody EDeviceInputVO request){
        return eDeviceService.updateEDevice(request);
    }

    /**
     * 更新设备的配置文件
     *
     * @return
     */
    @GetMapping("/updateFile")
    public ResponseVO updateEFileIdSet(@RequestParam(value = "deviceId")String deviceId, @RequestParam(value = "fileId")String fileId) {
        return eDeviceService.updateEFileIdSet(deviceId, fileId);
    }

    /**
     * 查询设备
     * Id方式
     * @return
     */
    @GetMapping("/getById")
    public ResponseVO<EDeviceOutputVO> getEDevice(@RequestParam(value = "deviceId")String deviceId){
        return eDeviceService.getEDevice(deviceId);
    }

    /**
     * 查询设备列表
     * @return
     */
    @GetMapping("/getList")
    public ResponseVO<List<EDeviceOutputVO>> getEDeviceList(){
        return eDeviceService.getEDeviceList();
    }

    /**
     * 删除设备
     * @return
     */
    @DeleteMapping("/delete")
    public ResponseVO deleteEDevice(@RequestParam(value = "deviceId")String deviceId){
        return eDeviceService.deleteEDevice(deviceId);
    }

    /**
     * 删除某个文件
     * @return
     */
    @DeleteMapping("/deleteFile")
    public ResponseVO deleteFile(@RequestParam(value = "deviceId")String deviceId, @RequestParam(value = "fileId")String fileId){
        return eDeviceService.deleteFile(deviceId, fileId);
    }
}
