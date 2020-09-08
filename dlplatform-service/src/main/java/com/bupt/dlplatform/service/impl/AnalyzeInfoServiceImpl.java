package com.bupt.dlplatform.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.mapper.TAnalyseRecordRepository;
import com.bupt.dlplatform.mapper.TAnalyseResultRepository;
import com.bupt.dlplatform.mapper.TTrainRecordRepository;
import com.bupt.dlplatform.model.TAnalyseRecordEntity;
import com.bupt.dlplatform.model.TAnalyseResultEntity;
import com.bupt.dlplatform.model.TTrainRecordEntity;
import com.bupt.dlplatform.service.AnalyzeInfoService;
import com.bupt.dlplatform.util.FtpUtil;
import com.bupt.dlplatform.vo.AnalyzeInputVO;
import com.bupt.dlplatform.vo.AnalyzeOutputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class AnalyzeInfoServiceImpl implements AnalyzeInfoService {
    @Autowired
    private TAnalyseRecordRepository tAnalyseRecordRepository;
    @Autowired
    private TAnalyseResultRepository tAnalyseResultRepository;
    @Autowired
    private  TTrainRecordRepository tTrainRecordRepository;
    @Autowired
    private FtpUtil ftpUtil;


    /**
     * 查询评估记录
     * @param request
     * userId
     * @return
     */
    @Override
    public ResponseVO<List<AnalyzeOutputVO>> searchAnalyzeRecord(AnalyzeInputVO request){
        ResponseVO responseVO =new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);
        try {
            String userId = request.getUserId();

            List<TAnalyseRecordEntity> list = tAnalyseRecordRepository.selectList
                    (Wrappers.<TAnalyseRecordEntity>lambdaQuery().eq(TAnalyseRecordEntity::getUserId,userId));

            if (CollectionUtils.isEmpty(list)) {
                //判断用户是否有评估记录
                responseVO.setCode(ResponseCode.RECORD_NULL.value());
                responseVO.setMsg("用户还没有进行任何评估！");
                return responseVO;
            }else {
                List<AnalyzeOutputVO> list_output = new ArrayList<>();

                TTrainRecordEntity tTrainRecordEntity =new TTrainRecordEntity();
                for (int i = 0; i < list.size(); i++) {
                    String analyseRecordId = list.get(i).getAnalyseRecordId();
                    String analyseId = list.get(i).getAnalyseId();
                    String analyseRecordName=list.get(i).getAnalyseRecordName();
                    Date analyseRecordTime =list.get(i).getAnalyseRecordTime();
                    String type=list.get(i).getAnalyseClass();
                    String trainId=list.get(i).getTrainId();


                    tTrainRecordEntity=tTrainRecordRepository.selectOne(Wrappers.<TTrainRecordEntity>lambdaQuery().eq(TTrainRecordEntity::getTrainId,trainId));
                    String trainName = tTrainRecordEntity.getTrainName();
                    String network =tTrainRecordEntity.getNetwork();
                    AnalyzeOutputVO one_output = new AnalyzeOutputVO();
                    one_output.setAnalyzeRecordId(analyseRecordId);
                    one_output.setAnalyzeRecordName(analyseRecordName);
                    one_output.setAnalyzeRecordTime(analyseRecordTime);
                    one_output.setTrainId(trainId);
                    one_output.setTrainName(trainName);
                    one_output.setNetwork(network);
                    one_output.setType(type);
                    one_output.setAnalyzeId(analyseId);
                    list_output.add(one_output);
                }
                responseVO.setCode(ResponseCode.OK.value());
                responseVO.setMsg(ResponseCode.OK.getDescription());
                responseVO.setData(list_output);
                return responseVO;
            }

        }catch (Exception e){
            log.error("SearchAnalyzeRecord 异常", e);
            return responseVO;
        }
    }

    /**
     * 获取已有训练记录
     * @param request
     * userId
     * @return
     * trainId, trainName,logId
     */
    @Override
    public  ResponseVO<List<AnalyzeOutputVO>> getTrainRecord(AnalyzeInputVO request){
        ResponseVO responseVO =new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);
        try {
            String userId = request.getUserId();
            List<TTrainRecordEntity> list_train = tTrainRecordRepository.selectList
                    (Wrappers.<TTrainRecordEntity>lambdaQuery().eq(TTrainRecordEntity::getUserId,userId));

            List<AnalyzeOutputVO> list = new ArrayList<>() ;
            for (int i =0;i<list_train.size();i++){
                AnalyzeOutputVO data = new AnalyzeOutputVO();
                data.setTrainId(list_train.get(i).getTrainId());
                data.setTrainName(list_train.get(i).getTrainName());
                data.setLogId(list_train.get(i).getLogId());
                list.add(data);
            }
            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData(list);
        }catch (Exception e){
            responseVO.setData("Have an error for getTrainRecord！");
            log.error("getTrainRecord 异常", e);
        }
        return responseVO;
    }

    /**
     * 生成评估记录
     * @param request
     *
     * @return
     */
    @Override
    public ResponseVO createAnalyze(AnalyzeInputVO request) {
        ResponseVO responseVO =new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);
        try {
            TAnalyseRecordEntity tAnalyseRecordEntity= new TAnalyseRecordEntity();
            //create_time
            Long time =System.currentTimeMillis();
            Date createTime = new Date(time);
            String timeString= time.toString();

            String analyzeName = request.getAnalyzeName();
            String trainId = request.getTrainId();
            String  type = request.getType();
            String userId = request.getUserId();
            String logId = request.getLogId();
            //记录Id
            String analyzeRecordId="ar"+userId+timeString;
            //结果Id
            String analyzeId= "a"+userId+timeString;

            tAnalyseRecordEntity.setCreateTime(createTime);
            tAnalyseRecordEntity.setAnalyseRecordId(analyzeRecordId);
            tAnalyseRecordEntity.setAnalyseRecordName(analyzeName);
            tAnalyseRecordEntity.setAnalyseRecordTime(createTime);
            tAnalyseRecordEntity.setAnalyseId(analyzeId);
            tAnalyseRecordEntity.setLogId(logId);
            tAnalyseRecordEntity.setUserId(userId);
            tAnalyseRecordEntity.setAnalyseClass(type);
            tAnalyseRecordEntity.setTrainId(trainId);

            //进行评估

            TAnalyseResultEntity tAnalyseResultEntity;
            tAnalyseResultEntity=runAnalyze(tAnalyseRecordEntity);

            tAnalyseRecordRepository.insert(tAnalyseRecordEntity);
            tAnalyseResultRepository.insert(tAnalyseResultEntity);

            AnalyzeOutputVO analyzeOutputVO=new AnalyzeOutputVO();
            analyzeOutputVO.setAnalyzeRecordId(analyzeRecordId);
            analyzeOutputVO.setAnalyzeId(analyzeId);

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData(analyzeOutputVO);

        }catch (Exception e){
            responseVO.setData("Have an error for createAnalyze！");
            log.error("createAnalyze 异常", e);
        }
        return responseVO;
    }


    public TAnalyseResultEntity runAnalyze(TAnalyseRecordEntity tAnalyseRecordEntity){

        TAnalyseResultEntity tAnalyseResultEntity=new TAnalyseResultEntity();
        String analyze_location;
        Long time =System.currentTimeMillis();
        Date createTime = new Date(time);
        /**
         *获取数据-->调用pyyhon 脚本-->返回analyze_location
         */
        analyze_location="156801456984113164294368/testset/1573439144710";



        tAnalyseResultEntity.setCreateTime(createTime);
        tAnalyseResultEntity.setAnalyseId(tAnalyseRecordEntity.getAnalyseId());
        tAnalyseResultEntity.setAnalyseName(tAnalyseRecordEntity.getAnalyseRecordName());
        tAnalyseResultEntity.setAnalyseTime(createTime);
        tAnalyseResultEntity.setAnalyseType(tAnalyseRecordEntity.getAnalyseClass());
        tAnalyseResultEntity.setAnalyseLocation(analyze_location);
        return tAnalyseResultEntity;
    }


    /**
     * 获取训练结果
     * @param request
     * AnalyseRecordId
     * @return
     */
    @Override
    public ResponseVO searchAnalyzeResult(AnalyzeInputVO request) {
        ResponseVO responseVO =new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);
        try {
            String userId= request.getUserId();
            String analyzeRecordId =request.getAnalyzeRecordId();
            TAnalyseRecordEntity tAnalyseRecordEntity=tAnalyseRecordRepository.selectOne(
                    (Wrappers.<TAnalyseRecordEntity>lambdaQuery().eq(TAnalyseRecordEntity::getAnalyseRecordId,analyzeRecordId)));
            if (tAnalyseRecordEntity==null){
                responseVO.setCode(ResponseCode.RECORD_NULL.value());
                responseVO.setMsg(ResponseCode.RECORD_NULL.getDescription());
                responseVO.setData("没有结果");
                return responseVO;
            }
            Date time = tAnalyseRecordEntity.getCreateTime();
            String analyzeName= tAnalyseRecordEntity.getAnalyseRecordName();
            String type = tAnalyseRecordEntity.getAnalyseClass();
            String trainId= tAnalyseRecordEntity.getTrainId();
            TTrainRecordEntity tTrainRecordEntity=tTrainRecordRepository.selectOne(
                    (Wrappers.<TTrainRecordEntity>lambdaQuery().eq(TTrainRecordEntity::getTrainId,trainId)));
            String trainName=tTrainRecordEntity.getTrainName();
            String network = tTrainRecordEntity.getNetwork();


            AnalyzeOutputVO analyzeOutputVO =new AnalyzeOutputVO();
            analyzeOutputVO.setAnalyzeRecordId(analyzeRecordId);
            analyzeOutputVO.setAnalyzeRecordName(analyzeName);
            analyzeOutputVO.setTrainName(trainName);
            analyzeOutputVO.setTrainId(trainId);
            analyzeOutputVO.setType(type);
            analyzeOutputVO.setAnalyzeRecordTime(time);
            analyzeOutputVO.setNetwork(network);

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData(analyzeOutputVO);

        }catch (Exception e){
            responseVO.setData("Have an error for searchAnalyzeResult！");
            log.error("searchAnalyzeResult 异常", e);
        }
        return responseVO;
    }

    /**
     * 显示结果图
     * @param request
     * AnalyzeRecordId
     */
    @Override
    public ResponseVO showAnalyzeResult( AnalyzeInputVO request) {
        ResponseVO responseVO =new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);
        try{
            String analyzeRecordId= request.getAnalyzeRecordId();
            TAnalyseRecordEntity tAnalyseRecordEntity=tAnalyseRecordRepository.selectOne(
                (Wrappers.<TAnalyseRecordEntity>lambdaQuery().eq(TAnalyseRecordEntity::getAnalyseRecordId,analyzeRecordId)));
            String analyzeId = tAnalyseRecordEntity.getAnalyseId();

            TAnalyseResultEntity  tAnalyseResultEntity=tAnalyseResultRepository.selectOne(
                Wrappers.<TAnalyseResultEntity>lambdaQuery().eq(TAnalyseResultEntity::getAnalyseId,analyzeId));
            //String location =tAnalyseResultEntity.getAnalyseLocation();
            //String name=tAnalyseResultEntity.getAnalyseName();
            //String picpath=location+'/'+name;
            //AnalyzeInputVO analyzeInputVO=new AnalyzeInputVO();
            //analyzeInputVO.setLocalPath(location);
            //analyzeInputVO.setPicPath(picpath);

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData(tAnalyseResultEntity);
        }catch (Exception e){
            responseVO.setData("Have an error for showTestResult！");
            log.error("showTestResult 异常",e);
        }
        return responseVO;

    }

    /**
     * 下载评估结果
     * @param request
     * @return
     */
    @Override
    public ResponseVO downAnalyzeResult(AnalyzeInputVO request) {
        ResponseVO responseVO =new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);
        try {
            String analyzeRecordId = request.getAnalyzeRecordId();
            String local =request.getLocalPath();
            TAnalyseRecordEntity tAnalyseRecordEntity=tAnalyseRecordRepository.selectOne(
                    (Wrappers.<TAnalyseRecordEntity>lambdaQuery().eq(TAnalyseRecordEntity::getAnalyseRecordId,analyzeRecordId)));
            String analyzeId= tAnalyseRecordEntity.getAnalyseId();
            TAnalyseResultEntity  tAnalyseResultEntity=tAnalyseResultRepository.selectOne(
                    Wrappers.<TAnalyseResultEntity>lambdaQuery().eq(TAnalyseResultEntity::getAnalyseId,analyzeId));
            String location =tAnalyseResultEntity.getAnalyseLocation();
            if(ftpUtil.downloadFile(location,local)){
                responseVO.setCode(ResponseCode.OK.value());
                responseVO.setMsg(ResponseCode.OK.getDescription());
                responseVO.setData("下载成功");
            }else{
                responseVO.setCode(ResponseCode.OPERATE_ERROR.value());
                responseVO.setMsg(ResponseCode.OPERATE_ERROR.getDescription());
                responseVO.setData("下载失败");
            }

        }catch (Exception e){
            responseVO.setData("Have an error for downAnalyzeResult！");
            log.error("downAnalyzeResult 异常", e);
        }
        return responseVO;
    }

    /**
     * 删除评估记录
     * @param request
     * @return
     */
    @Override
    public ResponseVO deleteAnalyze(AnalyzeInputVO request) {
        ResponseVO responseVO =new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);
        try {
            String analyzeRecordId= request.getAnalyzeRecordId();
            TAnalyseRecordEntity tAnalyseRecordEntity=tAnalyseRecordRepository.selectOne(
                    (Wrappers.<TAnalyseRecordEntity>lambdaQuery().eq(TAnalyseRecordEntity::getAnalyseRecordId,analyzeRecordId)));
            String analyzeId= tAnalyseRecordEntity.getAnalyseId();
            tAnalyseRecordRepository.delete(Wrappers.<TAnalyseRecordEntity>lambdaQuery().eq(TAnalyseRecordEntity::getAnalyseRecordId,analyzeRecordId));
            tAnalyseResultRepository.delete(Wrappers.<TAnalyseResultEntity>lambdaQuery().eq(TAnalyseResultEntity::getAnalyseId,analyzeId));
            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData("删除成功！");

        }catch (Exception e){
            responseVO.setData("Have an error for deleteAnalyze！");
            log.error("deleteAnalyze 异常", e);
        }
        return responseVO;
    }


}
