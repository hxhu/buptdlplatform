package com.bupt.dlplatform.controller;

import com.bupt.dlplatform.service.EDataSetService;
import com.bupt.dlplatform.vo.EDataSetInputVO;
import com.bupt.dlplatform.vo.EDataSetOutputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by huhx on 2020/12/26
 */
@RestController
@RequestMapping("/dlplatform/EDataSet")
public class EDataSetController {
    @Autowired
    private EDataSetService eDataSetService;

    /**
     * 增加数据集
     *
     * @return
     */
    @PostMapping("/create")
    public ResponseVO addEDataSet(@RequestBody EDataSetInputVO eDataSetInputVO){
        return eDataSetService.addEDataSet(eDataSetInputVO);
    }

    /**
     * 修改数据集
     *
     * @return
     */
    @PostMapping("/update")
    public ResponseVO updateEDataSet(@RequestBody EDataSetInputVO eDataSetInputVO){
        return eDataSetService.updateEDataSet(eDataSetInputVO);
    }

    /**
     * 查询数据集
     * Id方式
     *
     * @return
     */
    @GetMapping("/getById")
    public ResponseVO<EDataSetOutputVO> getEDataSet(@RequestParam(value = "dataSetId")String dataSetId){
        return eDataSetService.getEDataSet(dataSetId);
    }

    /**
     * 删除数据集
     *
     * @return
     */
    @DeleteMapping("/delete")
    public ResponseVO deleteEDataSet(@RequestParam(value = "dataSetId")String dataSetId){
        return eDataSetService.deleteEDataSet(dataSetId);
    }
}
