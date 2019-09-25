package com.bupt.dlplatform.consumer;


import com.bupt.dlplatform.Hystrix.ModelTestApiHystrix;
import com.bupt.dlplatform.model.TTestRecordEntity;
import com.bupt.dlplatform.vo.ResponseVO;
import com.bupt.dlplatform.vo.SearchTestInputVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "service-producer-dlplatform",fallback = ModelTestApiHystrix.class)
public interface ModelTestConsumer {
    @RequestMapping(value = "/dlplatform/searchTestRecord", method = RequestMethod.POST)
    ResponseVO<List<TTestRecordEntity>> searchTestRecord(SearchTestInputVO searchTestInputVO);
    //ResponseVO<List<TTestRecordEntity>> searchTestRecord(@RequestBody SearchTestInputVO searchTestInputVO);
}

