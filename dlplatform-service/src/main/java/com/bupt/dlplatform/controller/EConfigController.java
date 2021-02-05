package com.bupt.dlplatform.controller;

import com.bupt.dlplatform.service.EConfigService;
import com.bupt.dlplatform.service.EDeviceService;
import com.bupt.dlplatform.vo.EConfigInputVO;
import com.bupt.dlplatform.vo.EConfigOutputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by huhx on 2021/2/5
 */
@RestController
@RequestMapping("/dlplatform/EConfig")
public class EConfigController {
    @Autowired
    private EConfigService eConfigService;

    /**
     * 增加配置
     *
     * @return
     */
    @PostMapping("/create")
    public ResponseVO addEConfig(@RequestBody EConfigInputVO eConfigInputVO){
        System.out.println( eConfigInputVO.toString() );
        return eConfigService.addEConfig(eConfigInputVO);
    }

    /**
     * 修改配置
     *
     * @return
     */
    @PostMapping("/update")
    public ResponseVO updateEConfig(@RequestBody EConfigInputVO eConfigInputVO){
        return eConfigService.updateEConfig(eConfigInputVO);
    }

    /**
     * 查询配置列表
     * @return
     */
    @GetMapping("/getList")
    public ResponseVO<List<EConfigOutputVO>> getEConfigList(){
        return eConfigService.getEConfigList();
    }

    /**
     * 查询配置
     * Id方式
     *
     * @return
     */
    @GetMapping("/getById")
    public ResponseVO<EConfigOutputVO> getEConfig(@RequestParam(value = "configId")String configId){
        return eConfigService.getEConfig(configId);
    }

    /**
     * 删除配置
     *
     * @return
     */
    @DeleteMapping("/delete")
    public ResponseVO deleteEConfig(@RequestParam(value = "configId")String configId){
        return eConfigService.deleteEConfig(configId);
    }
}
