package com.bupt.dlplatform.consumer;

import com.bupt.dlplatform.Hystrix.ModelTrainApiHystrix;
import com.bupt.dlplatform.model.TTrainRecordEntity;
import com.bupt.dlplatform.vo.ModelTrainInputVO;
import com.bupt.dlplatform.vo.ModelTrainOutputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "service-producer-dlplatform",fallback = ModelTrainApiHystrix.class)
public interface ModelTrainConsumer {

    @RequestMapping(value = "/dlplatform/searchTrainRecord", method = RequestMethod.POST)
    ResponseVO<List<ModelTrainOutputVO>> searchTrainRecord(ModelTrainInputVO modelTrainInputVO);

    @RequestMapping(value = "/dlplatform/searchTrainStatus", method = RequestMethod.POST)
    ResponseVO<ModelTrainOutputVO> searchTrainStatus(ModelTrainInputVO modelTrainInputVO);

    @RequestMapping(value = "/dlplatform/getTrainsetName", method = RequestMethod.POST)
    ResponseVO getTrainsetName( ModelTrainInputVO modelTrainInputVO);

    @RequestMapping(value = "/dlplatform/getTrainNetwork", method = RequestMethod.POST)
    ResponseVO getTrainNetwork(ModelTrainInputVO modelTrainInputVO);

    @RequestMapping(value = "/dlplatform/submitTrainMessage", method = RequestMethod.POST)
    ResponseVO submitTrainMessage( ModelTrainInputVO modelTrainInputVO);

    @RequestMapping(value = "/dlplatform/startTrain", method = RequestMethod.POST)
    ResponseVO startTrain(ModelTrainInputVO modelTrainInputVO);



}
