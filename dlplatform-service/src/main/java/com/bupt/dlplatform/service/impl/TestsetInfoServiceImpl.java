package com.bupt.dlplatform.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bupt.dlplatform.config.SecretKeyConfig;
import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.mapper.TTestsetRecordRepository;
import com.bupt.dlplatform.mapper.TTestsetRepository;

import com.bupt.dlplatform.model.TTestsetEntity;
import com.bupt.dlplatform.model.TTestsetRecordEntity;
import com.bupt.dlplatform.model.common.TkGenerateParameter;
import com.bupt.dlplatform.service.TestsetInfoService;
import com.bupt.dlplatform.util.FtpUtil;
import com.bupt.dlplatform.util.TokenUtil;
import com.bupt.dlplatform.vo.ResponseVO;
import com.bupt.dlplatform.vo.TestsetInputVO;
import com.bupt.dlplatform.vo.TestsetOutputVO;
import com.bupt.dlplatform.vo.TestsetTempVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
public class TestsetInfoServiceImpl implements TestsetInfoService {
    @Autowired
    private TTestsetRepository tTestsetRepository;
    @Autowired
    private TTestsetRecordRepository tTestsetRecordRepository;
    @Autowired
    private FtpUtil ftpUtil;

    /**
     * 测试集记录查看
     * @param request
     * @return
     */
    @Override
    public ResponseVO<List<TestsetOutputVO>> testsetRecord(TestsetInputVO request) {
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);
        try {

            //获取用户Id -->直接通过token解析得到的
            String userId = request.getUserId();

            List<TTestsetRecordEntity> list = tTestsetRecordRepository.selectList
                    (Wrappers.<TTestsetRecordEntity>lambdaQuery().eq(TTestsetRecordEntity::getUserId,userId));

            if (CollectionUtils.isEmpty(list)) {
                //判断用户是否有测试集上传记录
                responseVO.setCode(ResponseCode.RECORD_NULL.value());
                responseVO.setMsg("用户还没有上传任何测试集！");
                return responseVO;
            }else {
                List<TestsetOutputVO> list_data = new ArrayList<>();
                for(int i=0;i<list.size();i++){
                    String testsetId= list.get(i).getTestsetId();
                    if(testsetId== null) continue;
                    else {
                        TTestsetEntity tTestsetEntity = tTestsetRepository.selectOne(Wrappers.<TTestsetEntity>lambdaQuery().eq(TTestsetEntity::getTestsetId, testsetId));
                        String testsetName = tTestsetEntity.getTestsetName();
                        Date uploadTime = tTestsetEntity.getTestsetTime();
                        String description = tTestsetEntity.getDescription();
                        TestsetOutputVO record = new TestsetOutputVO();
                        record.setTestsetId(testsetId);
                        record.setTestsetName(testsetName);
                        record.setUploadTime(uploadTime);
                        record.setDescription(description);
                        list_data.add(record);
                    }
                }
                responseVO.setCode(ResponseCode.OK.value());
                responseVO.setMsg(ResponseCode.OK.getDescription());
                responseVO.setData(list_data);
                return responseVO;
            }
        } catch (Exception e) {
            log.error("SearchTestsetRecord 异常", e);
            return responseVO;
        }
    }



    /**
     * 上传检测集--数据库更新
     * @param
     * @return
     */
    @Override
    public ResponseVO uploadTestsetComp( String userId,
                                         Date uploadTime,
                                         String testsetId,
                                         String testsetName,
                                         Double size,
                                         String pathName,
                                         String description){
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);
        try {
            //增加一条Testset记录
            TTestsetEntity tTestsetEntity=new TTestsetEntity();
            tTestsetEntity.setCreateTime(uploadTime);
            tTestsetEntity.setTestsetId(testsetId);
            tTestsetEntity.setTestsetSize(size);
            tTestsetEntity.setTestsetTime(uploadTime);
            tTestsetEntity.setTestsetLocation(pathName);
            tTestsetEntity.setTestsetName(testsetName);
            tTestsetEntity.setDescription(description);
            tTestsetRepository.insert(tTestsetEntity);

            //增加tTestsetRecord记录
            TTestsetRecordEntity tTestsetRecordEntity =new TTestsetRecordEntity();
            tTestsetRecordEntity.setCreateTime(uploadTime);
            tTestsetRecordEntity.setUserId(userId);
            tTestsetRecordEntity.setTestsetId(testsetId);
            tTestsetRecordRepository.insert(tTestsetRecordEntity);

            TestsetOutputVO testsetOutputVO=new TestsetOutputVO();
            testsetOutputVO.setUserId(userId);
            testsetOutputVO.setTestsetId(testsetId);
            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData(testsetOutputVO);

        }catch (Exception e){
            log.error("uploadTestsetFile 异常", e);
            return responseVO;

        }
        return responseVO;

    }


    /**
     * 删除测试集
     * @param request
     * @return
     */

    @Override
    public ResponseVO deleteTestset(TestsetInputVO request){
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);
        try {
            String fileId=request.getTestsetId();
            int rows= tTestsetRepository.delete(Wrappers.<TTestsetEntity>lambdaQuery().eq(TTestsetEntity::getTestsetId,fileId));
            int rows_record = tTestsetRecordRepository.delete(Wrappers.<TTestsetRecordEntity>lambdaQuery().eq(TTestsetRecordEntity::getTestsetId,fileId));
            if(rows==1 && rows_record==1){
                responseVO.setCode(ResponseCode.OK.value());
                responseVO.setMsg(ResponseCode.OK.getDescription());
                responseVO.setData("测试集删除成功");
            }else{
                responseVO.setCode(ResponseCode.OPERATE_ERROR.value());
                responseVO.setMsg(ResponseCode.OPERATE_ERROR.getDescription());
                log.error("deleteTestset异常");
            }


        }catch (Exception e){
            log.error("deleteTestset异常");
        }
        return responseVO;
    }


}
