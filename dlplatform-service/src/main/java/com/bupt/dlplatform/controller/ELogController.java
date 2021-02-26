package com.bupt.dlplatform.controller;

import com.bupt.dlplatform.service.ELogService;
import com.bupt.dlplatform.vo.ELogInputVO;
import com.bupt.dlplatform.vo.ELogOutputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by huhx on 2020/12/15
 */
@Api(tags="日志接口")
@RestController
@RequestMapping("/dlplatform/ELog")
public class ELogController {
    @Autowired
    private ELogService eLogService;

    /**
     * 增加日志
     * @return
     */
    @ApiOperation("增加日志")
    @PostMapping("/create")
    public ResponseVO addELog(@RequestBody ELogInputVO eLogInputVO){
        return eLogService.addELog(eLogInputVO);
    }

    /**
     * 查询日志
     * Id方式
     * @return
     */
    @ApiOperation("Id方式查询日志")
    @GetMapping("/getById")
    public ResponseVO<ELogOutputVO> getELog(@RequestParam(value = "logId")String logId){
        return eLogService.getELog(logId);
    }

    /**
     * 查询日志列表
     * modelId方式
     * @return
     */
    @ApiOperation("modelId方式查询日志列表")
    @GetMapping("/getByModelId")
    public ResponseVO<List<ELogOutputVO>> getELogListByModelId(@RequestParam(value = "modelId")String modelId){
        return eLogService.getELogListByModelId(modelId);
    }

    /**
     * 查询日志列表
     * deviceId方式
     * @return
     */
    @ApiOperation("deviceId方式查询日志列表")
    @GetMapping("/getByDeviceId")
    public ResponseVO<List<ELogOutputVO>> getELogListByDeviceId(@RequestParam(value = "deviceId")String deviceId){
        return eLogService.getELogListByDeviceId(deviceId);
    }

    /**
     * 查询日志列表
     * @return
     */
    @ApiOperation("查询日志列表")
    @GetMapping("/getList")
    public ResponseVO<List<ELogOutputVO>> getEDeviceList(){
        return eLogService.getEDeviceList();
    }
}
