package com.bupt.dlplatform.controller;

import com.bupt.dlplatform.service.LogService;
import com.bupt.dlplatform.vo.MDeviceEntityInputVO;
import com.bupt.dlplatform.vo.MLogEntityInputVO;
import com.bupt.dlplatform.vo.MLogEntityOutputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Created by huhx on 2020/11/22
 */
@RestController
@RequestMapping("/dlplatform/log")
public class LogController {
    @Autowired
    private LogService logService;

    /**
     * 更新日志
     */
    @PostMapping("/update")
    public ResponseVO updateMDisplayEntity(@RequestBody MLogEntityInputVO request){
        return logService.updateMLogEntity(request);
    }

    /**
     * 查询日志
     */
    @GetMapping("/getLogs")
    public ResponseVO<ArrayList<ArrayList<MLogEntityOutputVO>>> getLogEntities(){
        return logService.getLogEntities();
    }
}
