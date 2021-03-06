package com.bupt.dlplatform.controller;


import com.bupt.dlplatform.consumer.TestsetConsumer;
import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.model.TTestsetEntity;
import com.bupt.dlplatform.model.TTestsetRecordEntity;
import com.bupt.dlplatform.util.FtpUtil;
import com.bupt.dlplatform.vo.ResponseVO;
import com.bupt.dlplatform.vo.TestsetInputVO;
import com.bupt.dlplatform.vo.TestsetOutputVO;
import com.bupt.dlplatform.vo.TestsetTempVO;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class TestsetController {
    @Autowired
    TestsetConsumer testsetConsumer;
    @Autowired
    FtpUtil ftpUtil;


    /**
     * 查询测试集记录
     * @param testsetInputVO
     * @return
     */
    @RequestMapping(value = "/dlplatform/searchTestset", method = RequestMethod.POST)
    public ResponseVO searchTestset(@Validated TestsetInputVO testsetInputVO){
        ResponseVO responseVO = new ResponseVO(ResponseCode.PARAM_INVALID);
        try{
            ResponseVO<List<TestsetOutputVO>> res = testsetConsumer.searchTestset(testsetInputVO);
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
            log.error("searchTestset ---> 异常！", e);
        }
        return responseVO;

    }

    /**
     * 上传测试集
     * @param
     * @return
     */

    @RequestMapping(value = "/dlplatform/uploadComplete", method = RequestMethod.POST)
    public ResponseVO uploadTestsetComp(@Validated TestsetInputVO testsetInputVO,
                                        @RequestParam("testsetName") String testsetName,
                                        @RequestParam("description")String description,
                                        @RequestParam("file") MultipartFile[] files,
                                        HttpServletRequest httprequest
    ){
        ResponseVO responseVO = new ResponseVO(ResponseCode.PARAM_INVALID);
        try{
            String userId=testsetInputVO.getUserId();
            //生成文件ID:userID+上传时间戳+文件大小
            Long time =System.currentTimeMillis();
            Date uploadTime =new Date(time);
            String uploadTimeString = time.toString();
            Long size_all=0L;

            //生成路径 userId/testset/uploadTimeString/
            String pathName=userId+"/testset/"+uploadTimeString;

            //转型为MultipartHttpServletRequest
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) httprequest;
            Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();


            //文件个数
            int filesNum= files.length;
            boolean  flag=false;
            Long size;
            for(int i=0;i<filesNum;i++){
            //for(Map.Entry<String,MultipartFile> entity : fileMap.entrySet()){
                flag= false;
                //获取单个文件
                //MultipartFile mf = entity.getValue(); // 获得原始文件名
                //String fileName = mf.getOriginalFilename(); // 截取文件类型; 这里可以根据文件类型进行判断

                String fileName= files[i].getOriginalFilename();
                //获得输入流
                InputStream inputStream = files[i].getInputStream();
                size = files[i].getSize();
                size_all=size_all+size;

                flag=ftpUtil.uploadFile(pathName, fileName, inputStream);

                if(flag==true){
                }
                else{
                    //只要有一次没上传成功就结束
                    responseVO.setCode(ResponseCode.OPERATE_ERROR.value());
                    responseVO.setMsg(ResponseCode.OPERATE_ERROR.getDescription());
                    responseVO.setData("文件上传失败");
                    return responseVO;
                }

            }

            String testsetId=userId+uploadTimeString+size_all.toString();
            TestsetTempVO testsetTempVO= new TestsetTempVO();
            testsetTempVO.setUserId(userId);
            testsetTempVO.setUploadTime(uploadTime);
            testsetTempVO.setTestsetId(testsetId);
            testsetTempVO.setTestsetName(testsetInputVO.getTestsetName());
            testsetTempVO.setSize(size_all.doubleValue());
            testsetTempVO.setPathname(pathName);
            testsetTempVO.setDescription(testsetInputVO.getDescription());

            //ResponseVO res= testsetConsumer.uploadTestSet(testsetTempVO);
            ResponseVO res= testsetConsumer.uploadTestsetComp(
                    userId,
                    uploadTime,
                    testsetId,
                    testsetInputVO.getTestsetName(),
                    size_all.doubleValue(),
                    pathName,
                    testsetInputVO.getDescription());
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
            responseVO.setData("uploadTestSet-->异常");
            e.printStackTrace();

        }
        return responseVO;
    }





    /**
     * 删除测试集
     * @param request
     * @return
     */

    @RequestMapping(value = "/dlplatform/deleteTestset", method = RequestMethod.POST)
    public ResponseVO deleteTestset(@RequestBody  @Validated TestsetInputVO request){
        ResponseVO responseVO = new ResponseVO(ResponseCode.PARAM_INVALID);
        try{
            ResponseVO res= testsetConsumer.deleteTestset(request);
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
            log.error("deleteTestset ---> 异常！", e);
        }
        return responseVO;
    }

}
