package com.bupt.dlplatform.controller;

import com.bupt.dlplatform.model.MDisplayEntity;
import com.bupt.dlplatform.service.DataDisplayService;
import com.bupt.dlplatform.service.DataSourceService;
import com.bupt.dlplatform.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by huhx on 2020/10/3
 */
@RestController
@RequestMapping("/dlplatform/dataDisplay")
public class DataDisplayController {
    @Autowired
    private DataDisplayService dataDisplayService;

    /*
     * 新建配置
     */
    @PostMapping("/create")
    public ResponseVO createMDisplayEntity(@RequestBody MDisplayEntityInputVO request){
        return dataDisplayService.createMDisplayEntity(request);
    }

    /**
     * 更新配置
     */
    @PostMapping("/update")
    public ResponseVO updateMDisplayEntity(@RequestBody MDisplayEntityInputVO request){
        return dataDisplayService.updateMDisplayEntity(request);
    }

    /*
     * 读取一条配置（Id方式）
     */
    @GetMapping("/getById")
    public ResponseVO<MDisplayEntityOutputVO> getConfig(@RequestParam(value = "id") String id){
        return dataDisplayService.getMDisplayEntityById(id);
    }

    /**
     * 删除配置
     */
    @DeleteMapping("/delete")
    public ResponseVO daleteMDisplayEntity(@RequestParam(value = "id") String id){
        return dataDisplayService.deleteMDisplayEntity(id);
    }
}
