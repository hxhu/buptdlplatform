package com.bupt.dlplatform.controller;

import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.model.ERHeartbeatEntity;
import com.bupt.dlplatform.service.ERHeartbeatService;
import com.bupt.dlplatform.vo.ERHeartbeatInputVO;
import com.bupt.dlplatform.vo.ERHeartbeatOutputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import com.bupt.dlplatform.vo.StatusMapOutputVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by huhx on 2020/12/9
 */
@Api(tags="心跳接口")
@RestController
@RequestMapping("/dlplatform/ERHeartbeat")
public class ERHeartbeatController {
    @Autowired
    private ERHeartbeatService erHeartbeatService;
    /**
     * 增加心跳
     * @return
     */
    @ApiOperation("增加心跳")
    @PostMapping("/create")
    public ResponseVO addERHeartbeat(@RequestBody ERHeartbeatInputVO erHeartbeatInputVO){
        return erHeartbeatService.addERHeartbeat(erHeartbeatInputVO);
    }

    /**
     * 修改心跳
     * @return
     */
    @ApiOperation("修改心跳")
    @PostMapping("/update")
    public ResponseVO updateERHeartbeat(@RequestBody ERHeartbeatInputVO erHeartbeatInputVO){
        return erHeartbeatService.updateERHeartbeat(erHeartbeatInputVO);
    }

    /**
     * 查询心跳
     * Id方式
     * @return
     */
    @ApiOperation("Id方式查询心跳")
    @GetMapping("/getById")
    public ResponseVO<ERHeartbeatOutputVO> getERHeartbeat(@RequestParam(value = "deviceId") String deviceId){
        return erHeartbeatService.getERHeartbeat(deviceId);
    }

    /**
     * 查询设备状态
     * Id列表方式
     * @return
     */
    @ApiOperation("Id列表方式查询设备状态")
    @PostMapping("/getStatusMap")
    public ResponseVO<Map<String,String>> getDeviceStatus(@RequestBody StatusMapOutputVO statusMapOutputVO){
        return erHeartbeatService.getDeviceStatus(statusMapOutputVO);
    }


    /**
     * 删除心跳
     * @return
     */
    @ApiOperation("删除心跳")
    @DeleteMapping("/delete")
    public ResponseVO deleteERHeartbeat(@RequestParam(value = "deviceId")String deviceId){
        return erHeartbeatService.deleteERHeartbeat(deviceId);
    }
}
