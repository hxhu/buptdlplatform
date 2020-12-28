package com.bupt.dlplatform.controller;

import com.bupt.dlplatform.service.ECaseService;
import com.bupt.dlplatform.vo.ECaseInputVO;
import com.bupt.dlplatform.vo.ECaseOutputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by huhx on 2020/12/26
 */
@RestController
@RequestMapping("/dlplatform/ECase")
public class ECaseController {
    @Autowired
    private ECaseService eCaseService;

    /**
     * 增加用例
     *
     * @return
     */
    @PostMapping("/create")
    public ResponseVO addECase(@RequestBody ECaseInputVO eCaseInputVO){
        return eCaseService.addECase(eCaseInputVO);
    }

    /**
     * 修改用例
     *
     * @return
     */
    @PostMapping("/update")
    public ResponseVO updateECase(@RequestBody ECaseInputVO eCaseInputVO){
        return eCaseService.updateECase(eCaseInputVO);
    }

    /**
     * 查询用例
     * Id方式
     *
     * @return
     */
    @GetMapping("/getById")
    public ResponseVO<ECaseOutputVO> getECase(@RequestParam(value = "caseId")String caseId){
        return eCaseService.getECase(caseId);
    }

    /**
     * 删除用例
     *
     * @return
     */
    @DeleteMapping("/delete")
    public ResponseVO deleteECase(@RequestParam(value = "caseId")String caseId){
        return eCaseService.deleteECase(caseId);
    }
}
