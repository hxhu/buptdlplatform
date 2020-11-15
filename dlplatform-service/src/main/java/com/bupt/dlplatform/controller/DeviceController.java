package com.bupt.dlplatform.controller;

import com.bupt.dlplatform.service.DataDisplayService;
import com.bupt.dlplatform.service.DeviceService;
import com.bupt.dlplatform.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huhx on 2020/10/3
 */
@RestController
@RequestMapping("/dlplatform/device")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    /*
     * 新建数据
     */
    @PostMapping("/create")
    public ResponseVO createMDisplayEntity(@RequestBody MDeviceEntityInputVO request){
        return deviceService.createMDeviceEntity(request);
    }

    /**
     * 更新数据
     */
    @PostMapping("/update")
    public ResponseVO updateMDisplayEntity(@RequestBody MDeviceEntityInputVO request){
        return deviceService.updateMDeviceEntity(request);
    }

    /*
     * 读取一条数据（Id方式）
     */
    @GetMapping("/getById")
    public ResponseVO<MDeviceEntityOutputVO> getConfig(@RequestParam(value = "id") String id){
        return deviceService.getMDeviceEntityById(id);
    }

    /*
     * 获得某设备的全部配置（Id方式）
     */
    @GetMapping("/getDisplaysById")
    public ResponseVO<List<MDisplayEntityOutputVO>> getConfigsById(@RequestParam(value = "id") String deviceId){
        return deviceService.getConfigsById(deviceId);
    }

    /*
     * 获得某设备的图表显示（Id方式）
     */
    @GetMapping("/getFiguresById")
    public ResponseVO<List<MDisplayEntityOutputVO>> getFiguresById(@RequestParam(value = "id") String deviceId){
        return deviceService.getFiguresById(deviceId);
    }

    /*
     * 根据用户名找到设备列表
     */
    @GetMapping("/getListByUserId")
    public ResponseVO<ArrayList<MDeviceEntityOutputVO>> getMDeviceEntityByUserId(@RequestParam(value = "userId")String userId){
        return deviceService.getMDeviceEntityByUserId(userId);
    }

    /*
     * 根据设备ID找到数据列表
     */
    @GetMapping("/getDataByDeviceId")
    public ResponseVO<ArrayList<MDataEntityOutputVO>> getMDataEntityByDeviceId(@RequestParam(value = "deviceId")String deviceId){
        return deviceService.getMDataEntityByDeviceId(deviceId);
    }

    /**
     * 根据设备ID及类型，找到配置信息
     */
    @GetMapping("/getDisplayByDeviceIdAndDisplayType")
    public ResponseVO<MDisplayEntityOutputVO> getMDisplayEntityByDeviceId(@RequestParam(value = "deviceId")String deviceId, @RequestParam(value = "type")String type){
        return deviceService.getMDisplayEntityByDeviceId(deviceId, type);
    }

    /**
     * 删除数据
     */
    @DeleteMapping("/delete")
    public ResponseVO daleteMDisplayEntity(@RequestParam(value = "id") String id){
        return deviceService.deleteMDeviceEntity(id);
    }
}
