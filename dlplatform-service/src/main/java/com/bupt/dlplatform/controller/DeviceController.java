package com.bupt.dlplatform.controller;

import com.bupt.dlplatform.service.DataDisplayService;
import com.bupt.dlplatform.service.DeviceService;
import com.bupt.dlplatform.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

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
     * 根据用户名找到设备列表
     */
    @GetMapping("/getListByUserId")
    public ResponseVO<ArrayList<MDeviceEntityOutputVO>> getMDeviceEntityByUserId(String userId){
        return deviceService.getMDeviceEntityByUserId(userId);
    }

    /**
     * 删除数据
     */
    @DeleteMapping("/delete")
    public ResponseVO daleteMDisplayEntity(@RequestParam(value = "id") String id){
        return deviceService.deleteMDeviceEntity(id);
    }
}
