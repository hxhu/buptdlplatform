package com.bupt.dlplatform.controller;

import com.bupt.dlplatform.consumer.ModelTestConsumer;
import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.model.TTestRecordEntity;
import com.bupt.dlplatform.util.FtpUtil;
import com.bupt.dlplatform.vo.*;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;


@RestController
@Slf4j
public class ModelTestController {


    @Autowired
    private ModelTestConsumer modelTestConsumer;

    @Autowired
    private FtpUtil ftpUtil;

    /**
     * 查询检测记录
     * @param searchTestInputVO
     * @return
     */
    @RequestMapping(value = "/dlplatform/searchTestRecord", method = RequestMethod.POST)
    public ResponseVO searchTestRecord(@Validated SearchTestInputVO searchTestInputVO){
        ResponseVO responseVO = new ResponseVO(ResponseCode.PARAM_INVALID);
        try{
            ResponseVO<List<ModelTestOutputVO>> res = modelTestConsumer.searchTestRecord(searchTestInputVO);
            if(res.getCode()==ResponseCode.OK.value()){
                Object se = res.getData();
                responseVO.setCode(ResponseCode.OK.value());
                responseVO.setMsg(ResponseCode.OK.getDescription());
                responseVO.setData(se);
            }else {
                responseVO.setCode(res.getCode());
                responseVO.setMsg(res.getMsg());
            }
        }catch (Exception e){
            responseVO.setCode(ResponseCode.SYSTEM_EXCEPTION.value());
            responseVO.setMsg(ResponseCode.SYSTEM_EXCEPTION.getDescription());
            log.error("ModelTestController ---> 异常！", e);
        }
        return responseVO;

    }

    /**
     *
     * 点击"自定义测试"-->查询相关可选项信息
     */
    /*测试网络*/
    @RequestMapping(value = "/dlplatform/getOptionNetwork",method = RequestMethod.POST)
    public ResponseVO getOptionNetwork(@RequestBody @Validated BaseInputVO baseInputVO){
        ResponseVO responseVO = new ResponseVO(ResponseCode.PARAM_INVALID);
        try{
            ResponseVO res=modelTestConsumer.getOptionNetwork(baseInputVO);
            if(res.getCode()==ResponseCode.OK.value()){
                Object se = res.getData();
                responseVO.setCode(ResponseCode.OK.value());
                responseVO.setMsg(ResponseCode.OK.getDescription());
                responseVO.setData(se);
            }
        }catch (Exception e){
            responseVO.setCode(ResponseCode.SYSTEM_EXCEPTION.value());
            responseVO.setMsg(ResponseCode.SYSTEM_EXCEPTION.getDescription());
            log.error("ModelTestController ---> 异常！", e);
        }
        return responseVO;

    }

    /*测试模型*/
    @RequestMapping(value = "/dlplatform/getOptionModel",method = RequestMethod.POST)
    public ResponseVO getOptionModel(@RequestBody @Validated ModelTestInputVO modelTestInputVO){
        ResponseVO responseVO = new ResponseVO(ResponseCode.PARAM_INVALID);
        try{
            ResponseVO<List<OptionVO>> res=modelTestConsumer.getOptionModel(modelTestInputVO);
            if(res.getCode()==ResponseCode.OK.value()){
                Object se = res.getData();
                responseVO.setCode(ResponseCode.OK.value());
                responseVO.setMsg(ResponseCode.OK.getDescription());
                responseVO.setData(se);
            }

        }catch (Exception e){
            responseVO.setCode(ResponseCode.SYSTEM_EXCEPTION.value());
            responseVO.setMsg(ResponseCode.SYSTEM_EXCEPTION.getDescription());
            log.error("ModelTestController ---> 异常！", e);
        }
        return responseVO;
    }

    /*测试集*/
    @RequestMapping(value = "/dlplatform/getOptionTestset",method = RequestMethod.POST)
    public ResponseVO getOptionTestset(@RequestBody @Validated BaseInputVO baseInputVO){
        ResponseVO responseVO = new ResponseVO(ResponseCode.PARAM_INVALID);
        try{
            ResponseVO<List<OptionVO>> res=modelTestConsumer.getOptionTestset(baseInputVO);
            if(res.getCode()==ResponseCode.OK.value()){
                Object se = res.getData();
                responseVO.setCode(ResponseCode.OK.value());
                responseVO.setMsg(ResponseCode.OK.getDescription());
                responseVO.setData(se);
            }

        }catch (Exception e){
            responseVO.setCode(ResponseCode.SYSTEM_EXCEPTION.value());
            responseVO.setMsg(ResponseCode.SYSTEM_EXCEPTION.getDescription());
            log.error("ModelTestController ---> 异常！", e);
        }
        return responseVO;
    }

    /*测试标签*/
    @RequestMapping(value = "/dlplatform/getOptionTestLabel",method = RequestMethod.POST)
    public ResponseVO getOptionTestLabel(@RequestBody @Validated ModelTestInputVO  modelTestInputVO){
        ResponseVO responseVO = new ResponseVO(ResponseCode.PARAM_INVALID);
        try{
            ResponseVO res=modelTestConsumer.getOptionTestLabel(modelTestInputVO);
            if(res.getCode()==ResponseCode.OK.value()){
                Object se = res.getData();
                responseVO.setCode(ResponseCode.OK.value());
                responseVO.setMsg(ResponseCode.OK.getDescription());
                responseVO.setData(se);
            }

        }catch (Exception e){
            responseVO.setCode(ResponseCode.SYSTEM_EXCEPTION.value());
            responseVO.setMsg(ResponseCode.SYSTEM_EXCEPTION.getDescription());
            log.error("ModelTestController ---> 异常！", e);
        }
        return responseVO;
    }

    /**
     *
     * 添加检测记录
     */
    @RequestMapping(value = "/dlplatform/addTestRecord",method = RequestMethod.POST)
    public ResponseVO addTestRecord(@RequestBody @Validated ModelTestInputVO modelTestInputVO){
        ResponseVO responseVO = new ResponseVO(ResponseCode.PARAM_INVALID);
        try{
            ResponseVO res=modelTestConsumer.addTestRecord(modelTestInputVO);
            if(res.getCode()==ResponseCode.OK.value()){
                Object se = res.getData();
                responseVO.setCode(ResponseCode.OK.value());
                responseVO.setMsg(ResponseCode.OK.getDescription());
                responseVO.setData(se);
            }

        }catch (Exception e){
            responseVO.setCode(ResponseCode.SYSTEM_EXCEPTION.value());
            responseVO.setMsg(ResponseCode.SYSTEM_EXCEPTION.getDescription());
            log.error("ModelTestController ---> 异常！", e);
        }
        return responseVO;
    }
    /**
     * 查看检测结果
     */
    @RequestMapping(value = "/dlplatform/searchTestResult",method = RequestMethod.POST)
    public ResponseVO searchTestResult(@RequestBody @Validated ModelTestInputVO  modelTestInputVO){
        ResponseVO responseVO = new ResponseVO(ResponseCode.PARAM_INVALID);
        try{
            ResponseVO res=modelTestConsumer.searchTestResult(modelTestInputVO);
            if(res.getCode()==ResponseCode.OK.value()){
                Object se = res.getData();
                responseVO.setCode(ResponseCode.OK.value());
                responseVO.setMsg(ResponseCode.OK.getDescription());
                responseVO.setData(se);
            }
        }catch (Exception e){
            responseVO.setCode(ResponseCode.SYSTEM_EXCEPTION.value());
            responseVO.setMsg(ResponseCode.SYSTEM_EXCEPTION.getDescription());
            log.error("ModelTestController ---> 异常！", e);
        }
        return responseVO;
    }

    /**
     * 显示图片
     * @param response
     * picOne, testId
     * @param testResultInputVO
     */
    @RequestMapping(value = "/dlplatform/showTestResult",method = RequestMethod.POST)
    public void showTestResult(HttpServletResponse response, @RequestBody @Validated TestResultInputVO testResultInputVO){
        String picName= testResultInputVO.getPicOne();
        try {

            ResponseVO res=modelTestConsumer.getResultLoc(testResultInputVO);
            if(res.getCode()==ResponseCode.OK.value()){
                String resultLoc = res.getData().toString();
                InputStream imageIn =ftpUtil.getPicStream(resultLoc,picName);
                // 得到输入的编码器，将文件流进行jpg格式编码
                JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(imageIn);
                // 得到编码后的图片对象
                BufferedImage image = decoder.decodeAsBufferedImage();
                OutputStream out = response.getOutputStream();
                ImageIO.write(image, "jpg", out);
                imageIn.close();// 关闭文件流
                image.flush();
                out.flush();
                out.close();
            }
        }catch (Exception e){
            log.error("showTestResult ---> 异常！", e);
        }
    }



    /**
     * 下载检测结果
     */
    @RequestMapping(value = "/dlplatform/downloadResult",method = RequestMethod.POST)
    public ResponseVO downloadResult(@RequestBody @Validated DownloadInputVO downloadInputVO){
        ResponseVO responseVO = new ResponseVO(ResponseCode.PARAM_INVALID);
        try{
            ResponseVO res=modelTestConsumer.downloadResult(downloadInputVO);
            if(res.getCode()==ResponseCode.OK.value()){
                Object se = res.getData();
                responseVO.setCode(ResponseCode.OK.value());
                responseVO.setMsg(ResponseCode.OK.getDescription());
                responseVO.setData(se);
            }

        }catch (Exception e){
            responseVO.setCode(ResponseCode.SYSTEM_EXCEPTION.value());
            responseVO.setMsg(ResponseCode.SYSTEM_EXCEPTION.getDescription());
            log.error("ModelTestController ---> 异常！", e);
        }
        return responseVO;
    }
    /**
     * 删除检测记录
     */
    @RequestMapping(value = "/dlplatform/deleteTestRecord",method = RequestMethod.POST)
    public ResponseVO deleteTestRecord(@RequestBody @Validated ModelTestInputVO  modelTestInputVO){
        ResponseVO responseVO = new ResponseVO(ResponseCode.PARAM_INVALID);
        try{
            ResponseVO res=modelTestConsumer.deleteTestRecord(modelTestInputVO);
            if(res.getCode()==ResponseCode.OK.value()){
                Object se = res.getData();
                responseVO.setCode(ResponseCode.OK.value());
                responseVO.setMsg(ResponseCode.OK.getDescription());
                responseVO.setData(se);
            }

        }catch (Exception e){
            responseVO.setCode(ResponseCode.SYSTEM_EXCEPTION.value());
            responseVO.setMsg(ResponseCode.SYSTEM_EXCEPTION.getDescription());
            log.error("ModelTestController ---> 异常！", e);
        }
        return responseVO;
    }

}


