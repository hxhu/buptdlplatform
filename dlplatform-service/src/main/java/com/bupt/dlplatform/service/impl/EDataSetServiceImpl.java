package com.bupt.dlplatform.service.impl;

import com.baomidou.mybatisplus.extension.api.R;
import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.exception.ServiceException;
import com.bupt.dlplatform.mapper.EDataSetRepository;
import com.bupt.dlplatform.model.EDataSetEntity;
import com.bupt.dlplatform.model.EDeviceEntity;
import com.bupt.dlplatform.model.EFileEntity;
import com.bupt.dlplatform.model.EModelEntity;
import com.bupt.dlplatform.model.common.ConstantProperties;
import com.bupt.dlplatform.service.EDataSetService;
import com.bupt.dlplatform.util.IdGenerator;
import com.bupt.dlplatform.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by huhx on 2020/12/23
 */
@Service
@Slf4j
public class EDataSetServiceImpl implements EDataSetService {

    @Autowired
    private ConstantProperties constantProperties;

    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private EDataSetRepository eDataSetRepository;

    /**
     * 增加数据集
     *
     * @return
     */
    public ResponseVO addEDataSet(EDataSetInputVO eDataSetInputVO) {
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            EDataSetEntity eDataSetEntity = new EDataSetEntity();
            eDataSetEntity.setId(idGenerator.nextId());
            eDataSetEntity.setDataSetName(eDataSetInputVO.getDataSetName() != null ? eDataSetInputVO.getDataSetName() : "");
            eDataSetEntity.setDataSetDesc(eDataSetInputVO.getDataSetDesc() != null ? eDataSetInputVO.getDataSetDesc() : "");

            Long nowTime = System.currentTimeMillis();
            eDataSetEntity.setCreateTime(nowTime);
            eDataSetEntity.setUpdateTime(nowTime);
            if (eDataSetInputVO.getType() != null) {
                eDataSetEntity.setType(eDataSetInputVO.getType());
            } else {
                throw new ServiceException("必须提供type");
            }

            eDataSetEntity.setIsDeleted(0);

            /**
             *  创建目录
             */
            String rootPath = constantProperties.getRootPath() + eDataSetEntity.getId();
            String vocJPGPath = rootPath + constantProperties.getVocJPGPath();
            String vocXMLPath = rootPath + constantProperties.getVocXMLPath();
            String vocLabelPath = rootPath + constantProperties.getVocLabelPath();
            String vocTxtPath = vocLabelPath + constantProperties.getVocTxtPath();

            List<String> target = new ArrayList<String>();
            target.add(rootPath);
            target.add(vocJPGPath);
            target.add(vocXMLPath);
            target.add(vocLabelPath);
            target.add(vocTxtPath);

            for( String path : target ){
                File dir = new File(path);
                if (!dir.exists()) {
                    System.out.println("新建目录: " + path);
                    dir.mkdir();
                }
            }

            eDataSetRepository.save(eDataSetEntity);

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData("OK");
            return responseVO;

        } catch (Exception e) {
            log.error("EDataSet新建异常", e);
            return responseVO;
        }
    }

    /**
     * 修改数据集
     *
     * @return
     */
    public ResponseVO updateEDataSet(EDataSetInputVO eDataSetInputVO) {
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            EDataSetEntity eDataSetEntity;
            if (eDataSetInputVO.getId() != null) {
                Optional<EDataSetEntity> opt = eDataSetRepository.findById(eDataSetInputVO.getId());
                if (opt.isPresent() && opt.get().getIsDeleted() == 0) {
                    eDataSetEntity = opt.get();
                } else {
                    throw new ServiceException("未找到该数据");
                }
            } else {
                throw new ServiceException("必须提供dataSetId");
            }
            if (eDataSetInputVO.getDataSetName() != null && !eDataSetInputVO.getDataSetName().equals("")) {
                eDataSetEntity.setDataSetName(eDataSetInputVO.getDataSetName());
            }
            if (eDataSetInputVO.getDataSetDesc() != null && !eDataSetInputVO.getDataSetDesc().equals("")) {
                eDataSetEntity.setDataSetDesc(eDataSetInputVO.getDataSetDesc());
            }
            if (eDataSetInputVO.getType() != null && !eDataSetInputVO.getType().equals("")) {
                eDataSetEntity.setType(eDataSetInputVO.getType());
            }
            eDataSetEntity.setUpdateTime(System.currentTimeMillis());

            eDataSetRepository.save(eDataSetEntity);

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData("OK");
            return responseVO;

        } catch (ServiceException e) {
            log.error("EDataSet更新异常", e);
            return responseVO;
        } catch (Exception e) {
            log.error("EDataSet更新异常", e);
            return responseVO;
        }
    }

    /**
     * 查询数据集列表
     * @return
     */
    public ResponseVO<List<EDataSetOutputVO>> getEDataSetList(){
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            List<EDataSetEntity> tmp = eDataSetRepository.findAll();
            List<EDataSetEntity> list = new ArrayList<EDataSetEntity>();
            for (EDataSetEntity eDataSetEntity : tmp) {
                if (eDataSetEntity.getIsDeleted() == 0) {
                    list.add(eDataSetEntity);
                }
            }

            List<EDataSetOutputVO> result = new ArrayList<EDataSetOutputVO>();

            for (EDataSetEntity eDataSetEntity : list) {
                result.add(new EDataSetOutputVO(eDataSetEntity));
            }

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData(result);
            return responseVO;
        } catch (Exception e) {
            log.error("EDataSet查询异常", e);
            return responseVO;
        }
    }

    /**
     * 查询文件
     * Id方式
     *
     * @return
     */
    public ResponseVO<EDataSetOutputVO> getEDataSet(String dataSetId) {
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            EDataSetEntity eDataSetEntity;
            Optional<EDataSetEntity> opt = eDataSetRepository.findById(dataSetId);
            if (opt.isPresent() && opt.get().getIsDeleted() == 0) {
                eDataSetEntity = opt.get();
            } else {
                throw new ServiceException("未找到该数据");
            }

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData(new EDataSetOutputVO(eDataSetEntity));
            return responseVO;

        } catch (ServiceException e) {
            log.error("EDevice查询异常", e);
            return responseVO;
        } catch (Exception e) {
            log.error("EDevice查询异常", e);
            return responseVO;
        }
    }

    /**
     * 删除文件
     *
     * @return
     */
    public ResponseVO deleteEDataSet(String dataSetId) {
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            EDataSetEntity eDataSetEntity;
            Optional<EDataSetEntity> opt = eDataSetRepository.findById(dataSetId);
            if (opt.isPresent() && opt.get().getIsDeleted() == 0) {
                eDataSetEntity = opt.get();
            } else {
                throw new ServiceException("未找到该数据");
            }

            eDataSetEntity.setIsDeleted(1);

            eDataSetRepository.save(eDataSetEntity);

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData("OK");
            return responseVO;

        } catch (ServiceException e) {
            log.error("EDataSet删除异常", e);
            return responseVO;
        } catch (Exception e) {
            log.error("EDataSet删除异常", e);
            return responseVO;
        }
    }

    /**
     * 上传图片
     * @param avatar
     * @return
     */
    public R uploadImage(MultipartFile avatar, String dataSetId){
        Map<String,Object> map = new HashMap<>();
        String type = avatar.getContentType().split("/")[1];

        if (avatar.isEmpty()) {
            return R.failed("failed");
        } else if ( !type.equals("png") && !type.equals("jpg") && !type.equals("jpeg") ){
            return R.failed("图片类型错误 " + type);
        } else {
            String dirPath = constantProperties.getRootPath() + dataSetId + constantProperties.getVocJPGPath();
            String filePath = dirPath + avatar.getOriginalFilename();
            File newFile = new File(filePath);
            try {
                avatar.transferTo(newFile);
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }

            System.out.println("上传图片：" + filePath);
        }
        return R.ok(map).setCode(2000);
    }

    /**
     * 上传标注文件
     * @param avatar
     * @return
     */
    public R uploadAnnotation(MultipartFile avatar, String dataSetId){
        Map<String,Object> map = new HashMap<>();
        String type = avatar.getContentType().split("/")[1];

        if (avatar.isEmpty()) {
            return R.failed("failed");
        } else if ( !type.equals("xml") ){
            return R.failed("图片类型错误 " + type);
        } else {
            String dirPath = constantProperties.getRootPath() + dataSetId + constantProperties.getVocXMLPath();
            String filePath = dirPath + avatar.getOriginalFilename();
            File newFile = new File(filePath);
            try {
                avatar.transferTo(newFile);
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }

            System.out.println("上传标注文件：" + filePath);
        }
        return R.ok(map).setCode(2000);
    }

    /**
     * 匹配文件名上传
     */
    public ResponseVO uploadImageSet(UploadImageSetInputVO uploadImageSetInputVO){
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            String dirPath = constantProperties.getRootPath()
                    + uploadImageSetInputVO.getDataSetId()
                    + constantProperties.getVocLabelPath()
                    + constantProperties.getVocTxtPath();
            String filePath = dirPath + "train.txt";

            BufferedWriter out = new BufferedWriter(new FileWriter(filePath));
            for( String str : uploadImageSetInputVO.getNameList() ){
                out.write(str);
            }
            out.close();

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData("OK");
            return responseVO;

        } catch (IOException e) {
            log.error("ImageSet文件上传异常", e);
            return responseVO;
        } catch (Exception e) {
            log.error("EDataSet ImageSet上传异常", e);
            return responseVO;
        }

    }
}
