package com.bupt.dlplatform.controller;

import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.service.EModelService;
import com.bupt.dlplatform.vo.EModelInputVO;
import com.bupt.dlplatform.vo.EModelOutputVO;
import com.bupt.dlplatform.vo.PushModelInputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by huhx on 2020/12/4
 */
@RestController
@RequestMapping("/dlplatform/EModel")
public class EModelController {
    @Autowired
    private EModelService eModelService;

    /**
     * 增加模型
     * @return
     */
    @PostMapping("/create")
    public ResponseVO addEModel(@RequestBody EModelInputVO request){
        return eModelService.addEModel(request);
    }

    /**
     * 修改模型
     * @return
     */
    @PostMapping("/update")
    public ResponseVO updateEModel(@RequestBody EModelInputVO request){
        return eModelService.updateEModel(request);
    }

    /**
     * 查询模型
     * Id方式
     * @return
     */
    @GetMapping("/getById")
    public ResponseVO<EModelOutputVO> getEModel(@RequestParam(value = "modelId")String modelId){
        return eModelService.getEModel(modelId);
    }

    /**
     * 查询模型列表
     * @return
     */
    @GetMapping("/getList")
    public ResponseVO<List<EModelOutputVO>> getEModelList(){
        return eModelService.getEModelList();
    }

    /**
     * 删除模型
     * @return
     */
    @DeleteMapping("/delete")
    public ResponseVO deleteEModel(@RequestParam(value = "modelId")String modelId){
        return eModelService.deleteEModel(modelId);
    }

    /**
     * 推送模型
     * @return
     */
    @PostMapping("/pushModel")
    public ResponseVO pushModelWithDevices(@RequestBody PushModelInputVO request){
        return eModelService.pushModelWithDevices(request);
    }
}
