package com.bupt.dlplatform.controller;

import com.bupt.dlplatform.model.MDataEntity;
import com.bupt.dlplatform.service.DataDisplayService;
import com.bupt.dlplatform.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by huhx on 2020/9/29
 */
@RestController
@RequestMapping("/dlplatform/dataDisplay")
public class DataDisplayController {
    @Autowired
    private DataDisplayService dataDisplayService;

    /*
    * 新建数据
    */
    @PostMapping("/create")
    public ResponseVO createMDataEntity(){
        return dataDisplayService.createMDataEntity();
    }

    /**
     * 更新数据
     */
    @PostMapping("/update")
    public ResponseVO updateMDataEntity(){ return dataDisplayService.updateMDataEntity(); }

    /*
    * 读取一条数据（Id方式）
    */
    @GetMapping("/getById")
    public ResponseVO<MDataEntity> getData(){
        return dataDisplayService.getMDataEntityById();
    }

    /**
     * 删除数据
     */
    @DeleteMapping("/delete")
    public ResponseVO daleteMDataEntity(){
        return dataDisplayService.daleteMDataEntity();
    }

}
