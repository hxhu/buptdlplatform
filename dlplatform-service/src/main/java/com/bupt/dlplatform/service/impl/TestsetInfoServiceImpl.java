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
                    TTestsetEntity tTestsetEntity= tTestsetRepository.selectOne(Wrappers.<TTestsetEntity>lambdaQuery().eq(TTestsetEntity::getTestsetId,testsetId));
                    String testsetName = tTestsetEntity.getTestsetName();
                    Date uploadTime = tTestsetEntity.getTestsetTime();
                    String description =tTestsetEntity.getDescription();
                    TestsetOutputVO record= new TestsetOutputVO();
                    record.setTestsetId(testsetId);
                    record.setTestsetName(testsetName);
                    record.setUploadTime(uploadTime);
                    record.setDescription(description);
                    list_data.add(record);
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
     * 上传测试集
     * @param testsetTempVO
     * @return
     */
    @Override
    public ResponseVO uploadTestsetFile(TestsetTempVO testsetTempVO){
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);
        try {
            //增加一条记录，部分字段
            TTestsetEntity tTestsetEntity=new TTestsetEntity();
            tTestsetEntity.setCreateTime(testsetTempVO.getUploadTime());
            tTestsetEntity.setTestsetId(testsetTempVO.getTestsetId());
            tTestsetEntity.setTestsetSize(testsetTempVO.getSize());
            tTestsetEntity.setTestsetTime(testsetTempVO.getUploadTime());
            tTestsetEntity.setTestsetLocation(testsetTempVO.getPathname());
            tTestsetRepository.insert(tTestsetEntity);

            TestsetOutputVO testsetOutputVO=new TestsetOutputVO();
            testsetOutputVO.setUserId(testsetTempVO.getUserId());
            testsetOutputVO.setTestsetId(testsetTempVO.getTestsetId());
            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData(testsetOutputVO);

        }catch (Exception e){
            log.error("uploadTestsetFile 异常", e);
            return responseVO;

        }
        return responseVO;

    }

    //更新测试集记录和测试集表
    @Override
    public ResponseVO<TestsetOutputVO> addTestset(TestsetInputVO request){
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);
        try {
            String userId= request.getUserId();
            String testsetId = request.getTestsetId();
            String testsetName =request.getTestsetName();
            Long time =System.currentTimeMillis();
            Date createTime = new Date(time);
            String description = request.getDescription();

            //更新记录 testsetName, description 两个字段
            TTestsetEntity tTestsetEntity=new TTestsetEntity();
            tTestsetEntity.setUpdateTime(createTime);
            tTestsetEntity.setTestsetId(testsetId);
            tTestsetEntity.setTestsetName(testsetName);
            tTestsetEntity.setDescription(description);
            tTestsetRepository.update(tTestsetEntity,Wrappers.<TTestsetEntity>lambdaUpdate().eq(TTestsetEntity::getTestsetId,testsetId));


            //增加tTestsetRecord记录
            TTestsetRecordEntity tTestsetRecordEntity =new TTestsetRecordEntity();
            tTestsetRecordEntity.setCreateTime(createTime);
            tTestsetRecordEntity.setUserId(userId);
            tTestsetRecordEntity.setTestsetId(testsetId);
            tTestsetRecordRepository.insert(tTestsetRecordEntity);

            TestsetOutputVO testsetOutputVO = new TestsetOutputVO();
            testsetOutputVO.setTestsetId(testsetId);
            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData(testsetOutputVO);
            return responseVO;

        }catch (Exception e){
            log.error("uploadTestset 异常", e);
            return responseVO;
        }
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
