package com.bupt.dlplatform.service;

import com.bupt.dlplatform.mapper.TAnalyseRecordRepository;
import com.bupt.dlplatform.model.TAnalyseRecordEntity;
import com.bupt.dlplatform.model.TAnalyseResultEntity;
import com.bupt.dlplatform.vo.AnalyzeInputVO;
import com.bupt.dlplatform.vo.AnalyzeOutputVO;
import com.bupt.dlplatform.vo.ResponseVO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface AnalyzeInfoService {
    /**
     * 查询检测记录
     * @param request
     * @return
     */
    ResponseVO<List<AnalyzeOutputVO>>  searchAnalyzeRecord(AnalyzeInputVO request);

    /**
     * 获取已有训练记录
     * @param request
     * @return
     */
    ResponseVO<List<AnalyzeOutputVO>> getTrainRecord(AnalyzeInputVO request);

    /**
     * 创建评估
     * @param request
     * @return
     */
    ResponseVO createAnalyze(AnalyzeInputVO request);

    /**
     * 查看评估结果
     * @param request
     * @return
     */
    ResponseVO searchAnalyzeResult(AnalyzeInputVO request);

    /**
     * 显示评估结果图
     * @param request
     */
    ResponseVO<TAnalyseResultEntity> showAnalyzeResult(AnalyzeInputVO request);

    /**
     * 下载评估结果
     * @param request
     * @return
     */
    ResponseVO downAnalyzeResult(AnalyzeInputVO request);

    /**
     * 删除评估记录
     * @param request
     * @return
     */
    ResponseVO deleteAnalyze( AnalyzeInputVO request);
}
