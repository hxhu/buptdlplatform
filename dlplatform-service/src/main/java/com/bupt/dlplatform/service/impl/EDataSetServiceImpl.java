package com.bupt.dlplatform.service.impl;

import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.exception.ServiceException;
import com.bupt.dlplatform.mapper.EDataSetRepository;
import com.bupt.dlplatform.model.EDataSetEntity;
import com.bupt.dlplatform.model.EDeviceEntity;
import com.bupt.dlplatform.model.EFileEntity;
import com.bupt.dlplatform.model.EModelEntity;
import com.bupt.dlplatform.service.EDataSetService;
import com.bupt.dlplatform.util.IdGenerator;
import com.bupt.dlplatform.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by huhx on 2020/12/23
 */
@Service
@Slf4j
public class EDataSetServiceImpl implements EDataSetService {
    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private EDataSetRepository eDataSetRepository;

    /**
     * 增加数据集
     *
     * @return
     */
    public ResponseVO addEDataSet(EDataSetInputVO eDataSetInputVO){
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            EDataSetEntity eDataSetEntity = new EDataSetEntity();
            eDataSetEntity.setId(idGenerator.nextId());
            eDataSetEntity.setDataSetName(eDataSetInputVO.getDataSetName() != null ? eDataSetInputVO.getDataSetName() : "");
            eDataSetEntity.setDataDesc(eDataSetInputVO.getDataDesc() != null ? eDataSetInputVO.getDataDesc() : "");

            Long nowTime = System.currentTimeMillis();
            eDataSetEntity.setCreateTime(nowTime);
            eDataSetEntity.setUpdateTime(nowTime);
            if (eDataSetInputVO.getType() != null) {
                eDataSetEntity.setType(eDataSetInputVO.getType());
            } else {
                throw new ServiceException("必须提供type");
            }

            eDataSetEntity.setIsDeleted(0);

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
    public ResponseVO updateEDataSet(EDataSetInputVO eDataSetInputVO){
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
            if (eDataSetInputVO.getDataDesc() != null && !eDataSetInputVO.getDataDesc().equals("")) {
                eDataSetEntity.setDataDesc(eDataSetInputVO.getDataDesc());
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
     * 查询文件
     * Id方式
     *
     * @return
     */
    public ResponseVO<EDataSetOutputVO> getEDataSet(String dataSetId){
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
    public ResponseVO deleteEDataSet(String dataSetId){
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
}
