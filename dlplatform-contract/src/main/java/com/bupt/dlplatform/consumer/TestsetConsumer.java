package com.bupt.dlplatform.consumer;


import com.bupt.dlplatform.Hystrix.TestsetApiHystrix;
import com.bupt.dlplatform.model.TTestsetEntity;
import com.bupt.dlplatform.model.TTestsetRecordEntity;
import com.bupt.dlplatform.vo.ResponseVO;
import com.bupt.dlplatform.vo.TestsetInputVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "service-producer-dlplatform",fallback = TestsetApiHystrix.class)
public interface TestsetConsumer {
    @RequestMapping(value = "/dlplatform/searchTestset", method = RequestMethod.POST)
    ResponseVO<List<TTestsetEntity>> searchTestset(TestsetInputVO testsetInputVO);
}
