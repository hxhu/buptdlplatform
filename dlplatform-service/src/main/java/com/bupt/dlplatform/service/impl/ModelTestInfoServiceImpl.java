package com.bupt.dlplatform.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.mapper.*;
import com.bupt.dlplatform.model.*;
import com.bupt.dlplatform.service.ModelTestInfoService;
import com.bupt.dlplatform.util.FtpUtil;
import com.bupt.dlplatform.vo.*;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.InputStream;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class ModelTestInfoServiceImpl implements ModelTestInfoService {
    @Autowired
    private TTestRecordRepository tTestRecordRepository;

    @Autowired
    private TModelRecordRepository tModelRecordRepository;

    @Autowired
    private TModelRepository tModelRepository;

    @Autowired
    private TTestsetRepository tTestsetRepository;

    @Autowired
    private TTestsetRecordRepository tTestsetRecordRepository;

    @Autowired
    private  TTestResultRepository tTestResultRepository;

    @Autowired
    private FtpUtil ftpUtil;

     // 模型测试记录查询
    @Override
    public ResponseVO<List<TTestRecordEntity>> testRecord(SearchTestInputVO request) {
        ResponseVO responseVO =new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);
        try {
            String userId = request.getUserId();

            List<TTestRecordEntity> list = tTestRecordRepository.selectList
                    (Wrappers.<TTestRecordEntity>lambdaQuery().eq(TTestRecordEntity::getUserId,userId));

            if (CollectionUtils.isEmpty(list)) {
                //判断用户是否有测试记录
                responseVO.setCode(ResponseCode.RECORD_NULL.value());
                responseVO.setMsg("用户还没有模型检测记录！");
                return responseVO;
            }else {
                responseVO.setCode(ResponseCode.OK.value());
                responseVO.setMsg(ResponseCode.OK.getDescription());
                responseVO.setData(list);
                return responseVO;
            }
        } catch (Exception e) {
            log.error("SearchTestRecord 异常", e);
            return responseVO;
        }

    }


    //查询"自定义测试"所需相关选项
    //查询测试网络
    @Override
    public ResponseVO getOptionNetwork(BaseInputVO request){
        ResponseVO responseVO =new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);
        try{
            String[] network = new String[]{
                    "Yolo",
                    "SSD",
                    "Refinedet"
            };
            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());


        }catch (Exception e){
            log.error("SearchNetwork异常",e);
            responseVO.setData("Have an error for getOptionNetwork!");
        }

        return responseVO;
    }

    //查询可用的测试模型
    @Override
    public ResponseVO getOptionModel(ModelTestInputVO request){
        ResponseVO responseVO =new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);
        try{
            String  userId=request.getUserId();
            String  network =request.getTestNetwork();
            List<TModelEntity> model_message = new ArrayList();

            List<TModelRecordEntity> model_list_own= tModelRecordRepository.selectList
                    (Wrappers.<TModelRecordEntity>lambdaQuery().eq(TModelRecordEntity::getUserId,userId));

            if (!CollectionUtils.isEmpty(model_list_own)){
                TModelEntity temp_own;
                //有自己的模型
                for(int i=0;i<model_list_own.size();i++){
                    String temp_modelId= model_list_own.get(i).getModelId();
                    temp_own=tModelRepository.selectOne
                            (Wrappers.<TModelEntity>lambdaQuery().eq(TModelEntity::getModelId,temp_modelId).eq(TModelEntity::getNetwork,network));
                    model_message.add(temp_own);
                }
            }
            //获得通用模型的模型信息
            List<TModelEntity> model_message_common = tModelRepository.selectList
                    (Wrappers.<TModelEntity>lambdaQuery().eq(TModelEntity::getModelCommon,0).eq(TModelEntity::getNetwork,network));

            model_message.addAll(model_message_common);
            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData(model_message);
        }catch (Exception e){
            log.error("getOptionModel异常",e);
            responseVO.setData("Have an error for getOptionModel!");
        }
        return responseVO;

    }
    //查询可用的测试集
    @Override
    public ResponseVO getOptionTestset(BaseInputVO request){
        ResponseVO responseVO =new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);
        try{
            String  userId=request.getUserId();

            List<TTestsetRecordEntity> testset_list=tTestsetRecordRepository.selectList
                    (Wrappers.<TTestsetRecordEntity>lambdaQuery().eq(TTestsetRecordEntity::getUserId,userId));

            if(!CollectionUtils.isEmpty(testset_list)){
                List<TTestsetEntity> testset_message =new ArrayList<>();
                TTestsetEntity temp_testset;
                for(int i=0;i<testset_list.size();i++){
                    String temp_testsetId = testset_list.get(i).getTestsetId();
                    temp_testset=tTestsetRepository.selectOne
                            (Wrappers.<TTestsetEntity>lambdaQuery().eq(TTestsetEntity::getTestsetId ,temp_testsetId));
                    testset_message.add(temp_testset);
                }
                responseVO.setCode(ResponseCode.OK.value());
                responseVO.setMsg(ResponseCode.OK.getDescription());
                responseVO.setData(testset_message);
            }else{
                responseVO.setCode(ResponseCode.RECORD_NULL.value());
                responseVO.setMsg(ResponseCode.RECORD_NULL.getDescription());
                responseVO.setData("没有上传测试集，请先上传！");
            }

        }catch (Exception e){
            log.error("getOptionTestset异常",e);
            responseVO.setData("Have an error for getOptionTestset!");
        }

        return responseVO;

    }

    //查询测试标签
    @Override
    public ResponseVO getOptionTestLabel(ModelTestInputVO request){
        ResponseVO responseVO =new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);
        try{
            String  userId=request.getUserId();
            String  model = request.getModelID();
            TModelEntity model_message=tModelRepository.selectOne
                    (Wrappers.<TModelEntity>lambdaQuery().eq(TModelEntity::getModelId, model));
            String labelString=model_message.getModelLabel();
            String[] label_list=labelString.split("\\/");
            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData(label_list);

        }catch (Exception e){
            log.error("getOptionTestLabel 异常",e);
            responseVO.setData("Have an error for getOptionTestLabel!");
        }

        return responseVO;
    }


    //增加测试记录
    @Override
    public ResponseVO addTestRecord(ModelTestInputVO request){
        ResponseVO responseVO =new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);
        try{
            TTestRecordEntity tTestRecordEntity =new TTestRecordEntity();
            Long time =System.currentTimeMillis();
            Date createTime = new Date(time);
            String timeString= createTime.toString();
            tTestRecordEntity.setCreateTime(createTime);
            //生成testID
            String userID=request.getUserId();
            String testID="test"+userID+timeString;
            String resultID="result"+userID+timeString;

            //获取ConfigID
            String modelID=request.getModelID();
            TModelRecordEntity tModelRecordEntity=new TModelRecordEntity();
            tModelRecordEntity=tModelRecordRepository.selectOne
                    (Wrappers.<TModelRecordEntity>lambdaQuery().eq(TModelRecordEntity::getModelId,modelID));
            String configID=tModelRecordEntity.getConfigId();

            tTestRecordEntity.setTestId(testID);
            tTestRecordEntity.setTestName(request.getTestName());
            tTestRecordEntity.setTestTime(request.getTestTime());
            tTestRecordEntity.setTestsetId(request.getTestsetID());
            tTestRecordEntity.setModelId(modelID);
            tTestRecordEntity.setResultId(resultID);
            tTestRecordEntity.setUserId(request.getUserId());
            tTestRecordEntity.setTestNetwork(request.getTestNetwork());
            tTestRecordEntity.setConfigId(configID);
            tTestRecordEntity.setThreshold(request.getThreshold());
            tTestRecordEntity.setTestLabel(request.getLabel());
            tTestRecordRepository.insert(tTestRecordEntity);

            TTestResultEntity tTestResultEntity= runTest(tTestRecordEntity);
            tTestResultRepository.insert(tTestResultEntity);

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData("检测完成，记录添加成功！");

        }catch (Exception e){
            log.error("addTestRecord 异常",e);
            responseVO.setData("Have an error for addTestRecord!");
        }
        return responseVO;
    }

    //进行检测并且返回result_location
    public TTestResultEntity runTest(TTestRecordEntity tTestRecordEntity){
        String result_location;

        Long time =System.currentTimeMillis();
        Date createTime = new Date(time);
        /**
         *获取数据-->调用pyyhon 脚本-->获取返回的result_location
         */
        result_location="/Users/lin_z/Downloads/pic1.png";
        TTestResultEntity tTestResultEntity=new TTestResultEntity();
        tTestResultEntity.setCreateTime(createTime);
        tTestResultEntity.setResultId(tTestRecordEntity.getResultId());
        tTestResultEntity.setResultTime(createTime);
        tTestResultEntity.setResultLocation(result_location);

        return tTestResultEntity;
    }


    //查看检测结果
    @Override
    public ResponseVO searchTestResult(ModelTestInputVO request){
        ResponseVO responseVO =new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);
        String resultID= request.getResultID();
        try{
            TTestResultEntity result= tTestResultRepository.selectOne
                    (Wrappers.<TTestResultEntity>lambdaQuery().eq(TTestResultEntity::getResultId,resultID));
            if (result==null){
                responseVO.setCode(ResponseCode.RECORD_NULL.value());
                responseVO.setMsg(ResponseCode.RECORD_NULL.getDescription());
                responseVO.setData("没有结果");
            }
            String resultName=result.getResultName();
            //String resultLocation= result.getResultLocation();
            Date resultTime = result.getResultTime();
            String network = request.getTestNetwork();
            String modelName= request.getModelName();

            TestResultInputVO testResultInputVO=new TestResultInputVO();
            testResultInputVO.setResultName(resultName);
            testResultInputVO.setResultTime( resultTime);
            testResultInputVO.setNetwork(network);
            testResultInputVO.setModelName(modelName);

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData(testResultInputVO);

        }catch (Exception e){
            log.error("searchTestResult 异常",e);
            responseVO.setData("Have an error for searchTestResult！");
        }
        return responseVO;
    }




    //获取图片数据流并且显示
    @Override
    public void showTestResult(ModelTestInputVO request, HttpServletResponse response){
        String resultID= request.getResultID();

        try{
            TTestResultEntity result= tTestResultRepository.selectOne
                    (Wrappers.<TTestResultEntity>lambdaQuery().eq(TTestResultEntity::getResultId,resultID));

            //只是目录！
            String resultLocation= result.getResultLocation();

           // InputStream imageIn = new FileInputStream(new File((resultLocation)));
            InputStream imageIn =ftpUtil.getPicStream(resultLocation);
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
        }catch (Exception e){
            log.error("showTestResult 异常",e);
        }
    }


    //下载检测结果
    @Override
    public ResponseVO downloadResult(DownloadInputVO request){
        ResponseVO responseVO =new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);
        try{
            String resultId= request.getResultId();
            String local = request.getLocalLocation();
            TTestResultEntity result= tTestResultRepository.selectOne
                    (Wrappers.<TTestResultEntity>lambdaQuery().eq(TTestResultEntity::getResultId,resultId));
            String resultLocation=result.getResultLocation();

            if(ftpUtil.downloadFile(resultLocation,local)){
                responseVO.setCode(ResponseCode.OK.value());
                responseVO.setMsg(ResponseCode.OK.getDescription());
                responseVO.setData("下载成功");
            }else{
                responseVO.setCode(ResponseCode.OPERATE_ERROR.value());
                responseVO.setMsg(ResponseCode.OPERATE_ERROR.getDescription());
                responseVO.setData("下载失败");
            }

        }catch (Exception e){
            log.error("downloadResult 异常",e);
            responseVO.setData("Have an error for downloadResult");

        }
        return responseVO;
    }


    //删除检测记录
    @Override
    public ResponseVO deleteTestRecord(ModelTestInputVO request){
        ResponseVO responseVO =new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);
        String testId = request.getTestID();
        TTestRecordEntity tTestRecordEntity=tTestRecordRepository.selectOne
                (Wrappers.<TTestRecordEntity>lambdaQuery().eq(TTestRecordEntity::getTestId,testId));
        String resultId = tTestRecordEntity.getResultId();
        tTestRecordRepository.delete(Wrappers.<TTestRecordEntity>lambdaQuery().eq(TTestRecordEntity::getTestId,testId));

        tTestResultRepository.delete(Wrappers.<TTestResultEntity>lambdaQuery().eq(TTestResultEntity::getResultId,resultId));

        responseVO.setCode(ResponseCode.OK.value());
        responseVO.setMsg(ResponseCode.OK.getDescription());
        responseVO.setData("删除成功！");
        return responseVO;
    }



}
