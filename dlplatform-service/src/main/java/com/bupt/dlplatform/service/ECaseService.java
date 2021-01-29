package com.bupt.dlplatform.service;

import com.baomidou.mybatisplus.extension.api.R;
import com.bupt.dlplatform.vo.*;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by huhx on 2020/12/23
 */
public interface ECaseService {
    /**
     * 增加用例
     *
     * @return
     */
    public ResponseVO addECase(ECaseInputVO eCaseInputVO);

    /**
     * 修改用例
     *
     * @return
     */
    public ResponseVO updateECase(ECaseInputVO eCaseInputVO);

    /**
     * 查询用例
     * Id方式
     *
     * @return
     */
    public ResponseVO<ECaseOutputVO> getECase(String caseId);

    /**
     * 删除用例
     *
     * @return
     */
    public ResponseVO deleteECase(String caseId);


    //训练/测试 流程所需方法

    /**
     * 1.数据集选择
     *
     * 0-未编辑状态 1-获取数据集列表 2-选定某个数据集 9-查询当前数据集
     * @return
     */
    public ResponseVO<T> chooseDataSet(String caseId, String dataSetId, Integer status);

    /**
     * 2.环境准备
     *
     * 0-未编辑状态 1-执行环境准备 9-查询执行结果
     * @return
     */
    public ResponseVO prepareEnvironment(String caseId, Integer status);

    /**
     * 3.开始训练
     *
     * 0-未编辑状态 1-执行开始训练 9-查询训练结果
     * @return
     */
    public ResponseVO startTraining(String caseId, Integer status);

    /**
     * 4.训练情况
     *
     * 0-未编辑状态 9-查询训练情况
     * @return
     */
    public ResponseVO getTrainingCondition(String caseId, Integer status);

    /**
     * 5.训练损失
     *
     * 0-未编辑状态 9-返回lossList
     * @return
     */
    public ResponseVO getTrainingLoss(String caseId, Integer status);

    /**
     * 6.模型测试
     *
     * 0-未编辑状态 1-上传模型 9-查看是否上传
     * @return
     */
    public R testModel(String caseId, MultipartFile avatar, Integer status);

    /**
     * 7.测试结果
     *
     * 0-未编辑状态 1-执行测试 9-查询测试情况
     * @return
     */
    public ResponseVO getTestResult(String caseId, Integer status);
}
