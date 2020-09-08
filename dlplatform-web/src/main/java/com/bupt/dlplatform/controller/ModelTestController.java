package com.bupt.dlplatform.controller;

import com.bupt.dlplatform.consumer.ModelTestConsumer;
import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.model.TTestRecordEntity;
import com.bupt.dlplatform.util.FtpUtil;
import com.bupt.dlplatform.util.HttpUtil;
import com.bupt.dlplatform.vo.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;


@RestController
@Slf4j
public class ModelTestController {

    @Autowired
    private HttpUtil httpUtil;

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
    public ResponseVO getOptionNetwork( @Validated BaseInputVO baseInputVO){
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
    public ResponseVO getOptionTestset( @Validated BaseInputVO baseInputVO){
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
     * 添加检测记录,并请求GPU服务器
     */
    @RequestMapping(value = "/dlplatform/addTestRecord",method = RequestMethod.POST)
    public ResponseVO addTestRecord(@RequestBody @Validated ModelTestInputVO modelTestInputVO){
        ResponseVO responseVO = new ResponseVO(ResponseCode.PARAM_INVALID);
        try{
            ResponseVO res=modelTestConsumer.addTestRecord(modelTestInputVO);
            if(res.getCode()==ResponseCode.OK.value()){
                Object se = res.getData();
                ObjectMapper objectMapper = new ObjectMapper();
                TestParamVO testParamVO=objectMapper.convertValue(se,TestParamVO.class);
                final String url = "http://127.0.0.1:8008/dltrain/test/getparam";
                Map<String,Object> params = new HashMap<>();
                FileSystemResource modelPath=new FileSystemResource(testParamVO.getTestmodelPath());
                FileSystemResource testsetPath = new FileSystemResource(testParamVO.getTestsetPath());
                FileSystemResource configPath = new FileSystemResource(testParamVO.getConfigPath());
                /**
                 * testId,
                 * resultId,
                 * userId
                 * testnetwork,
                 * testsetpath,
                 * testsetname,
                 * testmodelpath,
                 * testmodelname,
                 * testconfigpath,
                 * testconfigname
                 * testThreshold,
                 * testlabel,
                 */
                Map<String,String> headers =null;
                params.put("task","test");
                params.put("testId",testParamVO.getTestId());
                params.put("resultId",testParamVO.getResultId());
                params.put("userId",testParamVO.getUserId());
                params.put("testNetwork",testParamVO.getTestNetwork());
                params.put("testsetname",testParamVO.getTestsetName());
                params.put("testmodelname",testParamVO.getTestmodelName());
                params.put("testconfigname",testParamVO.getConfigName());
                params.put("testThreshold",testParamVO.getTestThreshold());
                params.put("testlabel",testParamVO.getTestlabel());
                params.put("testsetPath",testsetPath);
                //params.put("modelPath",modelPath);
                params.put("configPath",configPath);
                log.info(testParamVO.getTestId()+"开始请求");
                try{
                    Object o = httpUtil.httpPostFrom(url,params,headers,String.class);
                    //判断返回参数值是否为OK！没写！
                    log.info(testParamVO.getTestId()+"完成检测！");
                    responseVO.setCode(ResponseCode.OK.value());
                    responseVO.setMsg(ResponseCode.OK.getDescription());
                    responseVO.setData(o);
                }catch (Exception e){
                    responseVO.setCode(ResponseCode.SYSTEM_EXCEPTION.value());
                    responseVO.setMsg(ResponseCode.SYSTEM_EXCEPTION.getDescription());
                    log.error("目标检测请求参数完成检测 ---> 异常！", e);
                }
            }
        }catch (Exception e){
            responseVO.setCode(ResponseCode.SYSTEM_EXCEPTION.value());
            responseVO.setMsg(ResponseCode.SYSTEM_EXCEPTION.getDescription());
            log.error("ModelTestController.addTestRecord ---> 异常！", e);
        }
        return responseVO;
    }

    /**
     * 添加检测结果记录，以及上传结果图
     */
    @RequestMapping(value = "/dlplatform/getTestResult",method = RequestMethod.POST)
    public ResponseVO getTestResult( @RequestParam("resultId") String resultId,
                                     @RequestParam("resultName")String resultName,
                                     @RequestParam("resultTime") Date resultTime,
                                     @RequestParam("timeString") String timeString,
                                     @RequestParam("resultfile") MultipartFile[] resultfiles,
                                     @RequestParam("userId") String userId,
                                     HttpServletRequest httprequest){
        ResponseVO responseVO = new ResponseVO(ResponseCode.PARAM_INVALID);
        try{

            //生成路径 userId/testResult/uploadTimeString/
            String resultPath=userId+"/testResult/"+timeString;

            //转型为MultipartHttpServletRequest
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) httprequest;
            Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

            //文件个数
            int filesNum= resultfiles.length;
            boolean  flag=false;
            Long size;
            for(int i=0;i<filesNum;i++){
                //for(Map.Entry<String,MultipartFile> entity : fileMap.entrySet()){
                flag= false;

                String fileName= resultfiles[i].getOriginalFilename();
                //获得输入流
                InputStream inputStream = resultfiles[i].getInputStream();

                //结果图保存在ftp文件服务器里
                flag=ftpUtil.uploadFile(resultPath, fileName, inputStream);
                if(flag==true){
                }
                else{
                    //只要有一次没上传成功就结束
                    responseVO.setCode(ResponseCode.OPERATE_ERROR.value());
                    responseVO.setMsg(ResponseCode.OPERATE_ERROR.getDescription());
                    responseVO.setData("getTestResult-检测结果图上传失败");
                    return responseVO;
                }

            }
            Long time =System.currentTimeMillis();
            Date createTime =new Date(time);
            TestResultParamVO  testResultParamVO=new TestResultParamVO();
            testResultParamVO.setCreateTime(createTime);
            testResultParamVO.setResultId(resultId);
            testResultParamVO.setResultLocation(resultPath);
            testResultParamVO.setUserId(userId);
            testResultParamVO.setResultTime(resultTime);
            testResultParamVO.setResultName(resultName);

            ResponseVO res= modelTestConsumer.getTestResult(testResultParamVO);

            if(res.getCode()== ResponseCode.OK.value()){
                Object se = res.getData();
                responseVO.setCode(ResponseCode.OK.value());
                responseVO.setMsg(ResponseCode.OK.getDescription());
                responseVO.setData(se);
            }else {
                responseVO.setCode(res.getCode());
                responseVO.setMsg(res.getMsg());
            }

        }catch (Exception e){
            responseVO.setCode(ResponseCode.OPERATE_ERROR.value());
            responseVO.setMsg(ResponseCode.OPERATE_ERROR.getDescription());
            responseVO.setData("getTestResult-->异常");
            e.printStackTrace();

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
                List<Map<String, byte[]>> imageIn = ftpUtil.getPicMap(resultLoc);
                OutputStream out=null;

                for(int i =0;i<imageIn.size();i++){
                    Map<String, byte[]> temp = new HashMap<>();
                    temp=imageIn.get(i);
                    byte[] value=null;
                    for(String key : temp.keySet()){
                        value= temp.get(key);
                    }
                    InputStream picStream = new ByteArrayInputStream(value);
                    // 得到输入的编码器，将文件流进行jpg格式编码
                    JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(picStream);
                    // 得到编码后的图片对象
                    BufferedImage image = decoder.decodeAsBufferedImage();
                    out= response.getOutputStream();
                    ImageIO.write(image, "jpg", out);
                    picStream.close();// 关闭文件流
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.setContentType("image/jpeg");
                    image.flush();
                    out.flush();
                }
                out.close();
            }else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
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


