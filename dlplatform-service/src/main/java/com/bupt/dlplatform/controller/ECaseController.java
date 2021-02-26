package com.bupt.dlplatform.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.bupt.dlplatform.service.ECaseService;
import com.bupt.dlplatform.vo.ChooseDataSetInputVO;
import com.bupt.dlplatform.vo.ECaseInputVO;
import com.bupt.dlplatform.vo.ECaseOutputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by huhx on 2020/12/26
 */
@Api(tags="训练测试用例接口")
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
    @ApiOperation("增加用例")
    @PostMapping("/create")
    public ResponseVO addECase(@RequestBody ECaseInputVO eCaseInputVO){
        return eCaseService.addECase(eCaseInputVO);
    }

    /**
     * 修改用例
     *
     * @return
     */
    @ApiOperation("修改用例")
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
    @ApiOperation("Id方式查询用例")
    @GetMapping("/getById")
    public ResponseVO<ECaseOutputVO> getECase(@RequestParam(value = "caseId")String caseId){
        return eCaseService.getECase(caseId);
    }

    /**
     * 删除用例
     *
     * @return
     */
    @ApiOperation("删除用例")
    @DeleteMapping("/delete")
    public ResponseVO deleteECase(@RequestParam(value = "caseId")String caseId){
        return eCaseService.deleteECase(caseId);
    }





    //训练/测试 流程所需方法

    /**
     * 1.数据集选择
     *
     * status: 0-未编辑状态 1-获取数据集列表 2-选定某个数据集
     * @return
     */
    @ApiOperation("1.数据集选择")
    @PostMapping("/chooseDataSet")
    public ResponseVO<T> chooseDataSet(@RequestBody ChooseDataSetInputVO chooseDataSetInputVO){
        return eCaseService.chooseDataSet(chooseDataSetInputVO.getCaseId(), chooseDataSetInputVO.getDataSetId(), chooseDataSetInputVO.getStatus());
    }

    /**
     * 2.环境准备
     *
     * 0-未编辑状态 1-执行环境准备 2-执行结果查询
     * @return
     */
    @ApiOperation("2.环境准备")
    @GetMapping("/prepareEnvironment")
    public ResponseVO prepareEnvironment(@RequestParam(value = "caseId")String caseId,
                                         @RequestParam(value = "status")Integer status){
        return eCaseService.prepareEnvironment(caseId, status);
    }

    /**
     * 3.开始训练
     *
     * 0-未编辑状态 1-执行开始训练 9-查询训练结果
     * @return
     */
    @ApiOperation("3.开始训练")
    @GetMapping("/startTraining")
    public ResponseVO startTraining(@RequestParam(value = "caseId")String caseId,
                                    @RequestParam(value = "status")Integer status){
        return eCaseService.startTraining(caseId, status);
    }

    /**
     * 4.训练情况
     *
     * 0-未编辑状态 9-查询训练情况
     * @return
     */
    @ApiOperation("4.训练情况")
    @GetMapping("/getTrainingCondition")
    public ResponseVO getTrainingCondition(@RequestParam(value = "caseId")String caseId,
                                           @RequestParam(value = "status")Integer status){
        return eCaseService.getTrainingCondition(caseId, status);
    }

    /**
     * 5.训练损失
     *
     * 0-未编辑状态 9-返回lossList
     * @return
     */
    @ApiOperation("5.训练损失")
    @GetMapping("/getTrainingLoss")
    public ResponseVO getTrainingLoss(@RequestParam(value = "caseId")String caseId,
                                        @RequestParam(value = "status")Integer status){
        return eCaseService.getTrainingLoss(caseId, status);
    }

    /**
     * 6.模型测试
     *
     * 0-未编辑状态 1-上传模型 9-查看是否上传
     * @return
     */
    @ApiOperation("6.模型测试")
    @PostMapping("/testModel/{caseId}/{status}")
    public R testModel(@RequestParam(value = "avatar") MultipartFile avatar,
                       @PathVariable(value = "caseId") String caseId,
                       @PathVariable(value = "status") Integer status){
        return eCaseService.testModel(caseId, avatar, status);
    }

    /**
     * 7.测试结果
     *
     * 0-未编辑状态 1-执行测试 9-查询测试情况
     * @return
     */
    @ApiOperation("7.测试结果")
    @GetMapping("/getTestResult")
    public ResponseVO getTestResult(@RequestParam(value = "caseId")String caseId,
                                    @RequestParam(value = "status")Integer status){
        return eCaseService.getTestResult(caseId, status);
    }

}
