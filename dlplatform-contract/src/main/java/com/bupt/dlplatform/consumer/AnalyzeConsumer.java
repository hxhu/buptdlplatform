package com.bupt.dlplatform.consumer;

import com.bupt.dlplatform.Hystrix.AnalyzeApiHystrix;
import com.bupt.dlplatform.model.TAnalyseRecordEntity;
import com.bupt.dlplatform.vo.AnalyzeInputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "service-producer-dlplatform",fallback = AnalyzeApiHystrix.class)
public interface AnalyzeConsumer {
    @RequestMapping(value = "/dlplatform/searchAnalyzeRecord", method = RequestMethod.POST)
    ResponseVO<List<TAnalyseRecordEntity>> searchAnalyzeRecord(AnalyzeInputVO analyzeInputVO);
}
