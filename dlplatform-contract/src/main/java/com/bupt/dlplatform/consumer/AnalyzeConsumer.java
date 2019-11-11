package com.bupt.dlplatform.consumer;

import com.bupt.dlplatform.Hystrix.AnalyzeApiHystrix;
import com.bupt.dlplatform.model.TAnalyseRecordEntity;
import com.bupt.dlplatform.model.TAnalyseResultEntity;
import com.bupt.dlplatform.vo.AnalyzeInputVO;
import com.bupt.dlplatform.vo.AnalyzeOutputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@FeignClient(value = "service-producer-dlplatform",fallback = AnalyzeApiHystrix.class)
public interface AnalyzeConsumer {


    /**
     * 查询评估记录
     * @param analyzeInputVO
     * @return
     */
    @RequestMapping(value = "/dlplatform/searchAnalyzeRecord", method = RequestMethod.POST)
    ResponseVO<List<AnalyzeOutputVO>> searchAnalyzeRecord(AnalyzeInputVO analyzeInputVO);

    /**
     * 获取已有训练记录
     * @param analyzeInputVO
     * @return
     */
    @RequestMapping(value = "/dlplatform/getTrainRecord",method = RequestMethod.POST)
    ResponseVO<List<AnalyzeOutputVO>> getTrainRecord( AnalyzeInputVO analyzeInputVO);

    /**
     * 创建评估
     * @param analyzeInputVO
     * @return
     */
    @RequestMapping(value = "/dlplatform/createAnalyze",method = RequestMethod.POST)
    ResponseVO createAnalyze( AnalyzeInputVO analyzeInputVO);

    /**
     *  查看评估结果
     * @param analyzeInputVO
     * @return
     */
    @RequestMapping(value = "/dlplatform/searchAnalyzeResult",method = RequestMethod.POST)
    ResponseVO searchAnalyzeResult( AnalyzeInputVO analyzeInputVO);

    /**
     * 显示评估结果图
     * @param analyzeInputVO
     * @return
     */
    @RequestMapping(value = "/dlplatform/showAnalyzeResult",method = RequestMethod.POST)
    ResponseVO<TAnalyseResultEntity> showAnalyzeResult(AnalyzeInputVO analyzeInputVO);

    /**
     * 下载评估结果
     * @param analyzeInputVO
     * @return
     */
    @RequestMapping(value = "/dlplatform/downAnalyzeResult",method = RequestMethod.POST)
    ResponseVO downAnalyzeResult( AnalyzeInputVO analyzeInputVO);

    /**
     * 删除评估记录
     * @param analyzeInputVO
     * @return
     */
    @RequestMapping(value = "/dlplatform/deleteAnalyze",method = RequestMethod.POST)
    ResponseVO deleteAnalyze( AnalyzeInputVO analyzeInputVO);



}
