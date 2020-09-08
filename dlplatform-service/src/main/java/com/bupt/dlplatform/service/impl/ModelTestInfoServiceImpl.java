package com.bupt.dlplatform.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.mapper.*;
import com.bupt.dlplatform.model.*;
import com.bupt.dlplatform.service.ModelTestInfoService;
import com.bupt.dlplatform.util.FtpUtil;
import com.bupt.dlplatform.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
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
    private  TConfigRepository tConfigRepository;

    @Autowired
    private TConfigRecordRepository tConfigRecordRepository;

    @Autowired
    private FtpUtil ftpUtil;

    /**
     * 测试记录查询
     * @param request
     * @return
     * testTime,
     */
    @Override
    public ResponseVO<List<ModelTestOutputVO>> testRecord(SearchTestInputVO request) {
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
                List<ModelTestOutputVO> list_result=new ArrayList<>();
                for(int i=0;i<list.size();i++){
                    ModelTestOutputVO modelTestOutputVO = new ModelTestOutputVO();
                    String testId = list.get(i).getTestId();
                    String testName = list.get(i).getTestName();
                    Date testTime = list.get(i).getTestTime();
                    String network = list.get(i).getTestNetwork();
                    String modelId = list.get(i).getModelId();
                    String modelName = tModelRepository.selectOne
                            (Wrappers.<TModelEntity>lambdaQuery().eq(TModelEntity::getModelId,modelId)).getModelName();
                    String testsetId = list.get(i).getTestsetId();
                    //String testsetName = tTestsetRepository.selectOne
                            //(Wrappers.<TTestsetEntity>lambdaQuery().eq(TTestsetEntity::getTestsetId,testsetId)).getTestsetName();

                    String threshold = list.get(i).getThreshold();
                    String resultId = list.get(i).getResultId();

                    modelTestOutputVO.setTestId(testId);
                    modelTestOutputVO.setTestName(testName);
                    modelTestOutputVO.setTestTime(testTime);
                    modelTestOutputVO.setNetwork(network);
                    modelTestOutputVO.setModelId(modelId);
                    modelTestOutputVO.setModelName(modelName);
                    modelTestOutputVO.setTestsetId(testsetId);
                    //modelTestOutputVO.setTestsetName(testsetName);
                    modelTestOutputVO.setThreshold(threshold);
                    modelTestOutputVO.setResultId(resultId);

                    list_result.add(modelTestOutputVO);
                }
                responseVO.setCode(ResponseCode.OK.value());
                responseVO.setMsg(ResponseCode.OK.getDescription());
                responseVO.setData(list_result);
                return responseVO;
            }
        } catch (Exception e) {
            log.error("SearchTestRecord 异常", e);
            return responseVO;
        }

    }


    //查询"自定义测试"所需相关选项

    /**
     * 查询可用测试网络
     * @param request
     * @return
     */
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
            responseVO.setData(network);


        }catch (Exception e){
            log.error("SearchNetwork异常",e);
            responseVO.setData("Have an error for getOptionNetwork!");
        }

        return responseVO;
    }

    /**
     * 查询可用的测试模型
     * @param request
     * @return
     * message:modelId,modelName
     */
    @Override
    public ResponseVO<List<OptionVO>> getOptionModel(ModelTestInputVO request){
        ResponseVO responseVO =new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);
        try{
            String  userId=request.getUserId();
            String  network =request.getTestNetwork();
            List<TModelEntity> model_message = new ArrayList();

            List<TModelRecordEntity> model_list_own= tModelRecordRepository.selectList
                    (Wrappers.<TModelRecordEntity>lambdaQuery().eq(TModelRecordEntity::getUserId,userId));

            if (!CollectionUtils.isEmpty(model_list_own)){

                //有自己的模型
                for(int i=0;i<model_list_own.size();i++){
                    TModelEntity temp_own;
                    String temp_modelId= model_list_own.get(i).getModelId();
                    temp_own=tModelRepository.selectOne
                            (Wrappers.<TModelEntity>lambdaQuery().eq(TModelEntity::getModelId,temp_modelId).eq(TModelEntity::getNetwork,network));
                    if(temp_own!= null){
                        model_message.add(temp_own);
                    }
                }
            }
            //获得通用模型的模型信息
            List<TModelEntity> model_message_common = tModelRepository.selectList
                    (Wrappers.<TModelEntity>lambdaQuery().eq(TModelEntity::getModelCommon,0).eq(TModelEntity::getNetwork,network));

            model_message.addAll(model_message_common);

            List<OptionVO> list_result= new ArrayList<>();
            for (int i =0; i<model_message.size();i++){
                OptionVO modelOptionVO = new OptionVO();
                String modelId = model_message.get(i).getModelId();
                String modelName = model_message.get(i).getModelName();
                modelOptionVO.setId(modelId);
                modelOptionVO.setName(modelName);
                list_result.add(modelOptionVO);
            }

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData(list_result);
        }catch (Exception e){
            log.error("getOptionModel异常",e);
            responseVO.setData("Have an error for getOptionModel!");
        }
        return responseVO;

    }

    /**
     * 查询可用的测试集
     * @param request
     * @return
     */
    @Override
    public ResponseVO<List<OptionVO>> getOptionTestset(BaseInputVO request){
        ResponseVO responseVO =new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);
        try{
            String  userId=request.getUserId();

            List<TTestsetRecordEntity> testset_list=tTestsetRecordRepository.selectList
                    (Wrappers.<TTestsetRecordEntity>lambdaQuery().eq(TTestsetRecordEntity::getUserId,userId));

            if(!CollectionUtils.isEmpty(testset_list)){
                List<OptionVO> list_result= new ArrayList<>();

                for(int i=0;i<testset_list.size();i++){
                    OptionVO testsetOption = new OptionVO();
                    String testsetId = testset_list.get(i).getTestsetId();
                    TTestsetEntity temp_testset=tTestsetRepository.selectOne
                            (Wrappers.<TTestsetEntity>lambdaQuery().eq(TTestsetEntity::getTestsetId ,testsetId));
                    if(temp_testset !=null){
                        String testsetName= temp_testset.getTestsetName();
                        testsetOption.setId(testsetId);
                        testsetOption.setName(testsetName);
                        list_result.add(testsetOption);
                    }

                }
                responseVO.setCode(ResponseCode.OK.value());
                responseVO.setMsg(ResponseCode.OK.getDescription());
                responseVO.setData(list_result);
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

    /**
     * 查询测试标签
     * @param request
     * @return
     */
    @Override
    public ResponseVO getOptionTestLabel(ModelTestInputVO request){
        ResponseVO responseVO =new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);
        try{
            String  userId=request.getUserId();
            String  modelId = request.getModelId();
            TModelEntity model_message=tModelRepository.selectOne
                    (Wrappers.<TModelEntity>lambdaQuery().eq(TModelEntity::getModelId, modelId));
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


    /**
     * 增加测试记录并且得到GPU检测参数
     * @param request
     * @return
     */
    @Override
    public ResponseVO addTestRecord(ModelTestInputVO request){
        ResponseVO responseVO =new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);
        try{
            TTestRecordEntity tTestRecordEntity =new TTestRecordEntity();
            Long time =System.currentTimeMillis();
            Date createTime = new Date(time);
            String timeString= time.toString();
            tTestRecordEntity.setCreateTime(createTime);
            //生成testID
            String userId=request.getUserId();
            String testId="t"+userId+timeString;
            String resultId="r"+userId+timeString;

            //获取ConfigID
            String modelId=request.getModelId();
            TModelRecordEntity tModelRecordEntity=new TModelRecordEntity();
            tModelRecordEntity=tModelRecordRepository.selectOne
                    (Wrappers.<TModelRecordEntity>lambdaQuery().eq(TModelRecordEntity::getModelId,modelId));
            String configID=tModelRecordEntity.getConfigId();

            //获取标签列表
            tTestRecordEntity.setTestId(testId);
            tTestRecordEntity.setTestName(request.getTestName());
            tTestRecordEntity.setTestTime(createTime);
            tTestRecordEntity.setTestsetId(request.getTestsetId());
            tTestRecordEntity.setModelId(modelId);
            tTestRecordEntity.setResultId(resultId);
            tTestRecordEntity.setUserId(request.getUserId());
            tTestRecordEntity.setTestNetwork(request.getTestNetwork());
            tTestRecordEntity.setConfigId(configID);
            tTestRecordEntity.setThreshold(request.getThreshold());
            tTestRecordEntity.setTestLabel(request.getLabel());
            tTestRecordRepository.insert(tTestRecordEntity);


            //传检测参数-->GPU服务器
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

            String testNetwork = request.getTestNetwork();
            TTestsetEntity tTestsetEntity = tTestsetRepository.selectOne
                    (Wrappers.<TTestsetEntity>lambdaQuery().eq(TTestsetEntity::getTestsetId,request.getTestsetId()));
            String testsetName=tTestsetEntity.getTestsetName();
            String testsetPath=tTestsetEntity.getTestsetLocation();
            TModelEntity tModelEntity=tModelRepository.selectOne
                    (Wrappers.<TModelEntity>lambdaQuery().eq(TModelEntity::getModelId,request.getModelId()));
            String testsetmodelName=tModelEntity.getModelName();
            String testsetmodelPath=tModelEntity.getModelLocation();
            TConfigEntity tConfigEntity = tConfigRepository.selectOne
                    (Wrappers.<TConfigEntity>lambdaQuery().eq(TConfigEntity::getConfigId,configID));
            String configName = tConfigEntity.getConfigName();
            String configPath = tConfigEntity.getConfigLocation();
            String testlabel =request.getLabel();
            String testThreshold = request.getThreshold();

            TestParamVO testParamVO=new TestParamVO();
            testParamVO.setUserId(userId);
            testParamVO.setTestId(testId);
            testParamVO.setResultId(resultId);
            testParamVO.setTestNetwork(testNetwork);
            testParamVO.setTestsetName(testsetName);
            testParamVO.setTestsetPath(testsetPath);
            testParamVO.setConfigName(configName);
            testParamVO.setConfigPath(configPath);
            testParamVO.setTestThreshold(testThreshold);
            testParamVO.setTestlabel(testlabel);
            testParamVO.setTestmodelName(testsetmodelName);
            testParamVO.setTestmodelPath(testsetmodelPath);

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData(testParamVO);

        }catch (Exception e){
            log.error("addTestRecord 异常",e);
            responseVO.setData("Have an error for addTestRecord!");
        }
        return responseVO;
    }

    /**
     * 插入检测结果
     * @param testResultParamVO
     * @return
     */
    public ResponseVO getTestResult(TestResultParamVO testResultParamVO){
        ResponseVO responseVO =new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);
        try{
            TTestResultEntity tTestResultEntity=new TTestResultEntity();
            tTestResultEntity.setCreateTime(testResultParamVO.getCreateTime());
            tTestResultEntity.setResultId(testResultParamVO.getResultId());
            tTestResultEntity.setResultTime(testResultParamVO.getResultTime());
            tTestResultEntity.setResultLocation(testResultParamVO.getResultLocation());
            tTestResultEntity.setResultName(testResultParamVO.getResultName());
            tTestResultRepository.insert(tTestResultEntity);

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData(testResultParamVO.getResultId()+"检测结果记录插入成功！");

        }catch (Exception e){
            log.error("getTestResult 异常",e);
            responseVO.setData("Have an error for getTestResult！");

        }
        return responseVO;

    }


    /**
     * 查看检测结果
     * @param request
     * testId
     * @return
     * 返回的是检测相关信息和结果路径
     */
    @Override
    public ResponseVO searchTestResult(ModelTestInputVO request){
        ResponseVO responseVO =new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);
        String testId =request.getTestId();
        try{
            TTestRecordEntity tTestRecordEntity = tTestRecordRepository.selectOne(
                    (Wrappers.<TTestRecordEntity>lambdaQuery().eq(TTestRecordEntity::getTestId,testId)));
            String resultId = tTestRecordEntity.getResultId();

            TTestResultEntity result= tTestResultRepository.selectOne
                    (Wrappers.<TTestResultEntity>lambdaQuery().eq(TTestResultEntity::getResultId,resultId));
            if (result==null){
                responseVO.setCode(ResponseCode.RECORD_NULL.value());
                responseVO.setMsg(ResponseCode.RECORD_NULL.getDescription());
                responseVO.setData("没有结果");
                return responseVO;
            }

            String resultName=result.getResultName();
            String resultLocation= result.getResultLocation();
            Date resultTime = result.getResultTime();

            String network = tTestRecordEntity.getTestNetwork();
            String modelId= tTestRecordEntity.getModelId();
            String modelName = tModelRepository.selectOne
                    (Wrappers.<TModelEntity>lambdaQuery().eq(TModelEntity::getModelId,modelId)).getModelName();


            //获取结果图的图片名列表
            List<String> listPicName=ftpUtil.getPicName(resultLocation);
            String[] strsPath=listPicName.toArray(new String[listPicName.size()]);

            TestResultInputVO testResultInputVO=new TestResultInputVO();
            testResultInputVO.setTestId(testId);
            testResultInputVO.setResultName(resultName);
            testResultInputVO.setResultTime(resultTime);
            testResultInputVO.setResultId(resultId);
            testResultInputVO.setNetwork(network);
            testResultInputVO.setModelName(modelName);
            testResultInputVO.setPicList(strsPath);

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData(testResultInputVO);

        }catch (Exception e){
            log.error("searchTestResult 异常",e);
            responseVO.setData("Have an error for searchTestResult！");
        }
        return responseVO;
    }

    @Override
    public ResponseVO getResultLoc(TestResultInputVO request){
        ResponseVO responseVO =new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);
        String testId =request.getTestId();
        try{
            TTestRecordEntity tTestRecordEntity = tTestRecordRepository.selectOne(
                    (Wrappers.<TTestRecordEntity>lambdaQuery().eq(TTestRecordEntity::getTestId,testId)));
            String resultId = tTestRecordEntity.getResultId();
            String resultLoc = tTestResultRepository.selectOne
                    (Wrappers.<TTestResultEntity>lambdaQuery().eq(TTestResultEntity::getResultId,resultId)).getResultLocation();
            //完整的路径列表
            /*List<String> listPicName=ftpUtil.getPicName(resultLoc);
            List<String> picCompPath= new ArrayList<>();
            for(int i =0;i< listPicName.size();i++){
                String name = listPicName.get(i);
                String picpath = resultLoc+"/"+name;
                picCompPath.add(picpath);
            }*/

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData(resultLoc);

        }catch (Exception e){
            log.error("getResultLoc 异常",e);
            responseVO.setData("Have an error for getResultLoc！");
        }
        return responseVO;
    }



    /**
     * 下载结果
     * @param request
     * @return
     */
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


    /**
     * 删除检测结果
     * @param request
     * TestId
     * @return
     */
    @Override
    public ResponseVO deleteTestRecord(ModelTestInputVO request){
        ResponseVO responseVO =new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);
        String testId = request.getTestId();
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
