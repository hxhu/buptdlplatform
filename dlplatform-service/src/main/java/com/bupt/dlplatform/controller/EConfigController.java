package com.bupt.dlplatform.controller;

import com.bupt.dlplatform.service.EConfigService;
import com.bupt.dlplatform.service.EDeviceService;
import com.bupt.dlplatform.vo.EConfigDownInputVO;
import com.bupt.dlplatform.vo.EConfigInputVO;
import com.bupt.dlplatform.vo.EConfigOutputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by huhx on 2021/2/5
 */
@Api(tags="参数组接口")
@RestController
@RequestMapping("/dlplatform/EConfig")
public class EConfigController {
    @Autowired
    private EConfigService eConfigService;

    /**
     * 增加参数组
     *
     * @return
     */
    @ApiOperation("增加参数组")
    @PostMapping("/create")
    public ResponseVO addEConfig(@RequestBody EConfigInputVO eConfigInputVO){
        System.out.println( eConfigInputVO.toString() );
        return eConfigService.addEConfig(eConfigInputVO);
    }

    /**
     * 修改参数组
     *
     * @return
     */
    @ApiOperation("修改参数组")
    @PostMapping("/update")
    public ResponseVO updateEConfig(@RequestBody EConfigInputVO eConfigInputVO){
        return eConfigService.updateEConfig(eConfigInputVO);
    }

    /**
     * 查询参数组列表
     * @return
     */
    @ApiOperation("查询参数组列表")
    @GetMapping("/getList")
    public ResponseVO<List<EConfigOutputVO>> getEConfigList(){
        return eConfigService.getEConfigList();
    }

    /**
     * 查询参数组
     * Id方式
     *
     * @return
     */
    @ApiOperation("Id方式查询参数组")
    @GetMapping("/getById")
    public ResponseVO<EConfigOutputVO> getEConfig(@RequestParam(value = "configId")String configId){
        return eConfigService.getEConfig(configId);
    }

    /**
     * 删除参数组
     *
     * @return
     */
    @ApiOperation("删除参数组")
    @DeleteMapping("/delete")
    public ResponseVO deleteEConfig(@RequestParam(value = "configId")String configId){
        return eConfigService.deleteEConfig(configId);
    }


    /**
     * 推送参数组到设备
     *
     * @return
     */
    @ApiOperation("推送参数组到设备")
    @PostMapping("/pushDownConfigs")
    public ResponseVO pushDownConfigs(@RequestBody EConfigDownInputVO eConfigDownInputVO){
        return eConfigService.pushDownConfigs(eConfigDownInputVO);
    }
}
