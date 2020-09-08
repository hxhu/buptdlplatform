package com.bupt.dlplatform.controller;

import com.bupt.dlplatform.consumer.AnalyzeConsumer;
import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.model.TAnalyseRecordEntity;
import com.bupt.dlplatform.model.TAnalyseResultEntity;
import com.bupt.dlplatform.util.FtpUtil;
import com.bupt.dlplatform.vo.AnalyzeInputVO;
import com.bupt.dlplatform.vo.AnalyzeOutputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

@Slf4j
@RestController
public class AnalyzeController {
    @Autowired
    AnalyzeConsumer analyzeConsumer;
    @Autowired
    FtpUtil ftpUtil;


    /**
     * 查询评估记录
     * @param analyzeInputVO
     * @return
     */
    @RequestMapping(value = "/dlplatform/searchAnalyzeRecord", method = RequestMethod.POST)
    public ResponseVO searchAnalyzeRecord(@Validated AnalyzeInputVO analyzeInputVO){
        ResponseVO responseVO = new ResponseVO(ResponseCode.PARAM_INVALID);
        try{
            ResponseVO<List<AnalyzeOutputVO>> res = analyzeConsumer.searchAnalyzeRecord(analyzeInputVO);
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
            log.error("searchAnalyzeRecord ---> 异常！", e);
        }
        return responseVO;

    }


    /**
     * 获取已有训练记录
     * @param analyzeInputVO
     * @return
     */

    @RequestMapping(value = "/dlplatform/getTrainRecord",method = RequestMethod.POST)
    public ResponseVO getTrainRecord( @Validated AnalyzeInputVO analyzeInputVO){
        ResponseVO responseVO = new ResponseVO(ResponseCode.PARAM_INVALID);
        try{
            ResponseVO<List<AnalyzeOutputVO>> res=analyzeConsumer.getTrainRecord(analyzeInputVO);
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
            log.error("getTrainRecord ---> 异常！", e);
        }
        return responseVO;
    }

    /**
     * 创建评估
     * @param analyzeInputVO
     * @return
     */

    @RequestMapping(value = "/dlplatform/createAnalyze",method = RequestMethod.POST)
    public ResponseVO createAnalyze( @RequestBody @Validated AnalyzeInputVO analyzeInputVO){
        ResponseVO responseVO = new ResponseVO(ResponseCode.PARAM_INVALID);
        try{
            ResponseVO res=analyzeConsumer.createAnalyze(analyzeInputVO);
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
            log.error("createAnalyze ---> 异常！", e);
        }
        return responseVO;
    }


    /**
     * 查看评估结果
     * @param analyzeInputVO
     * @return
     */
    @RequestMapping(value = "/dlplatform/searchAnalyzeResult",method = RequestMethod.POST)
    public ResponseVO searchAnalyzeResult(@RequestBody @Validated  AnalyzeInputVO analyzeInputVO){
        ResponseVO responseVO = new ResponseVO(ResponseCode.PARAM_INVALID);
        try{
            ResponseVO res=analyzeConsumer.searchAnalyzeResult(analyzeInputVO);
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
            log.error("searchAnalyzeResult ---> 异常！", e);
        }
        return responseVO;
    }


    /**
     * 显示评估结果图
     * @param response
     * @param analyzeInputVO
     */
    @RequestMapping(value = "/dlplatform/showAnalyzeResult",method = RequestMethod.POST)
    public void showAnalyzeResult(HttpServletResponse response, @RequestBody @Validated  AnalyzeInputVO analyzeInputVO){
        //ResponseVO responseVO = new ResponseVO(ResponseCode.PARAM_INVALID);
        try{
            ResponseVO<TAnalyseResultEntity> res=analyzeConsumer.showAnalyzeResult(analyzeInputVO);
            if(res.getCode()==ResponseCode.OK.value()){
                TAnalyseResultEntity tAnalyseResultEntity=res.getData();
                String location=tAnalyseResultEntity.getAnalyseLocation();

                InputStream imageIn =ftpUtil.getPicStream(location);
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
            log.error("showAnalyzeResult ---> 异常！", e);
        }

    }

    @RequestMapping(value = "/dlplatform/downAnalyzeResult",method = RequestMethod.POST)
    public ResponseVO downAnalyzeResult( @RequestBody @Validated AnalyzeInputVO analyzeInputVO){
        ResponseVO responseVO = new ResponseVO(ResponseCode.PARAM_INVALID);
        try{
            ResponseVO res=analyzeConsumer.downAnalyzeResult(analyzeInputVO);
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
            log.error("downAnalyzeResult ---> 异常！", e);
        }
        return responseVO;
    }

    @RequestMapping(value = "/dlplatform/deleteAnalyze",method = RequestMethod.POST)
    public ResponseVO deleteAnalyze(@RequestBody @Validated AnalyzeInputVO analyzeInputVO){
        ResponseVO responseVO = new ResponseVO(ResponseCode.PARAM_INVALID);
        try{
            ResponseVO res=analyzeConsumer.deleteAnalyze(analyzeInputVO);
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
            log.error("deleteAnalyze ---> 异常！", e);
        }
        return responseVO;
    }

}
