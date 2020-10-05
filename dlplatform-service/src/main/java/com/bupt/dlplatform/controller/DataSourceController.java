package com.bupt.dlplatform.controller;

import com.bupt.dlplatform.service.DataSourceService;
import com.bupt.dlplatform.vo.MDataEntityInputVO;
import com.bupt.dlplatform.vo.MDataEntityOutputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by huhx on 2020/9/29
 */
@RestController
@RequestMapping("/dlplatform/dataSource")
public class DataSourceController {
    @Autowired
    private DataSourceService dataSourceService;

    /*
    * 新建数据
    */
    @PostMapping("/create")
    public ResponseVO createMDataEntity(@RequestBody MDataEntityInputVO request){
        return dataSourceService.createMDataEntity(request);
    }

    /**
     * 更新数据
     */
    @PostMapping("/update")
    public ResponseVO updateMDataEntity(@RequestBody MDataEntityInputVO request){
        return dataSourceService.updateMDataEntity(request);
    }

    /*
    * 读取一条数据（Id方式）
    */
    @GetMapping("/getById")
    public ResponseVO<MDataEntityOutputVO> getData(@RequestParam(value = "id") String id){
        return dataSourceService.getMDataEntityById(id);
    }

    /**
     * 删除数据
     */
    @DeleteMapping("/delete")
    public ResponseVO daleteMDataEntity(@RequestParam(value = "id") String id){
        return dataSourceService.deleteMDataEntity(id);
    }

}
