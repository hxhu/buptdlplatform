package com.bupt.dlplatform.consumer;


import com.bupt.dlplatform.Hystrix.TestsetApiHystrix;
import com.bupt.dlplatform.model.TTestsetEntity;
import com.bupt.dlplatform.model.TTestsetRecordEntity;
import com.bupt.dlplatform.vo.ResponseVO;
import com.bupt.dlplatform.vo.TestsetInputVO;
import com.bupt.dlplatform.vo.TestsetOutputVO;
import com.bupt.dlplatform.vo.TestsetTempVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@FeignClient(value = "service-producer-dlplatform",fallback = TestsetApiHystrix.class)
public interface TestsetConsumer {

    @RequestMapping(value = "/dlplatform/searchTestset", method = RequestMethod.POST)
    ResponseVO<List<TestsetOutputVO>> searchTestset(TestsetInputVO testsetInputVO);


    @RequestMapping(value = "/dlplatform/uploadComplete", method = RequestMethod.POST)
    ResponseVO uploadTestsetComp(@RequestParam("userId") String userId,
                             @RequestParam("uploadTime") Date uploadTime,
                             @RequestParam("testsetId") String testsetId,
                             @RequestParam("testsetname") String testsetName,
                             @RequestParam("size") Double size,
                             @RequestParam("pathName") String pathName,
                             @RequestParam("Description") String description);


    @RequestMapping(value = "/dlplatform/deleteTestset", method = RequestMethod.POST)
    ResponseVO deleteTestset(TestsetInputVO request);
}
