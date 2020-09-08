package com.bupt.dlplatform.controller;

import com.bupt.dlplatform.consumer.TrainsetConsumer;
import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.model.TTrainsetEntity;
import com.bupt.dlplatform.util.FtpUtil;
import com.bupt.dlplatform.vo.ResponseVO;
import com.bupt.dlplatform.vo.TrainsetInputVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class TrainsetController {
    @Autowired
    TrainsetConsumer trainsetConsumer;
    @Autowired
    FtpUtil ftpUtil;

    /**
     * 查询训练集记录
     * @param trainsetInputVO
     * @return
     */
    @RequestMapping(value = "/dlplatform/searchTrainset", method = RequestMethod.POST)
    public ResponseVO searchTrainset(@Validated TrainsetInputVO trainsetInputVO){
        ResponseVO responseVO = new ResponseVO(ResponseCode.PARAM_INVALID);
        try{
            ResponseVO<List<TTrainsetEntity>> res = trainsetConsumer.searchTrainset(trainsetInputVO);
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
            responseVO.setCode(ResponseCode.SYSTEM_EXCEPTION.value());
            responseVO.setMsg(ResponseCode.SYSTEM_EXCEPTION.getDescription());
            log.error("TrainsetController ---> 异常！", e);
        }
        return responseVO;

    }
    /**
     * 删除训练集
     * @param request
     * @return
     */

    @RequestMapping(value = "/dlplatform/deleteTrainset", method = RequestMethod.POST)
    public ResponseVO deleteTestset(@RequestBody @Validated TrainsetInputVO request){
        ResponseVO responseVO = new ResponseVO(ResponseCode.PARAM_INVALID);
        try{
            ResponseVO res= trainsetConsumer.deleteTrainset(request);
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
            responseVO.setCode(ResponseCode.SYSTEM_EXCEPTION.value());
            responseVO.setMsg(ResponseCode.SYSTEM_EXCEPTION.getDescription());
            log.error("deleteTrainset ---> 异常！", e);
        }
        return responseVO;
    }


    /**
     * 上传训练集
     * @param trainsetInputVO
     * @param httprequest
     * @return
     */
    @RequestMapping(value = "dlplatform/uploadsetTrain",method = RequestMethod.POST)
    public ResponseVO uploadsetTrain(@Validated TrainsetInputVO trainsetInputVO,
                                     @Validated  HttpServletRequest httprequest){
        ResponseVO responseVO = new ResponseVO(ResponseCode.PARAM_INVALID);
        try{
            String userId=trainsetInputVO.getUserId();
            //转型为MultipartHttpServletRequest
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) httprequest;
            Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
            String fileName=null;

            //生成文件ID:userID+上传时间戳+文件大小
            Long time =System.currentTimeMillis();
            Date uploadTime =new Date(time);
            String uploadTimeString = time.toString();

            //生成路径 userId/testset/uploadTimeString/
            String pathName=userId+"/trainset/"+uploadTimeString+"/";

            boolean  flag=false;

            for(Map.Entry<String,MultipartFile> entity : fileMap.entrySet()){
                flag=false;
                //获取单个文件
                MultipartFile mf = entity.getValue(); // 获得原始文件名
                fileName = mf.getOriginalFilename(); // 截取文件类型; 这里可以根据文件类型进行判断
                String fileType = fileName.substring(fileName.lastIndexOf('.'));
                try{
                    // 截取上传的文件名称
                    String newFileName = fileName.substring(0, fileName.lastIndexOf('.'));
                    //获得输入流
                    InputStream inputStream = mf.getInputStream();

                    flag=ftpUtil.uploadFile(pathName, fileName, inputStream);
                    if(flag==true){
                        responseVO.setCode(ResponseCode.OK.value());
                        responseVO.setMsg(ResponseCode.OK.getDescription());
                        responseVO.setData(userId);
                    }
                    else{
                        //只要有一次没上传成功就结束
                        responseVO.setCode(ResponseCode.OPERATE_ERROR.value());
                        responseVO.setMsg(ResponseCode.OPERATE_ERROR.getDescription());
                        responseVO.setData("文件上传失败");
                        return responseVO;
                    }

                }catch (Exception e) {
                    responseVO.setCode(ResponseCode.OPERATE_ERROR.value());
                    responseVO.setMsg(ResponseCode.OPERATE_ERROR.getDescription());
                    responseVO.setData("文件上传失败");
                    e.printStackTrace();
                    return responseVO;
                }
            }

        }catch (Exception e){
            responseVO.setCode(ResponseCode.SYSTEM_EXCEPTION.value());
            responseVO.setMsg(ResponseCode.SYSTEM_EXCEPTION.getDescription());
            log.error("uploadTrainSet ---> 异常！", e);

        }
        return responseVO;
    }
}
