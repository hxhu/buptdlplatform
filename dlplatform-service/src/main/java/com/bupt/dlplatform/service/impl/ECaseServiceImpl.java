package com.bupt.dlplatform.service.impl;

import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.exception.ServiceException;
import com.bupt.dlplatform.mapper.ECaseRepository;
import com.bupt.dlplatform.mapper.EDataSetRepository;
import com.bupt.dlplatform.model.ECaseEntity;
import com.bupt.dlplatform.model.EDataSetEntity;
import com.bupt.dlplatform.service.ECaseService;
import com.bupt.dlplatform.util.IdGenerator;
import com.bupt.dlplatform.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by huhx on 2020/12/23
 */
@Service
@Slf4j
public class ECaseServiceImpl implements ECaseService {
    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private ECaseRepository eCaseRepository;

    /**
     * 增加用例
     *
     * @return
     */
    public ResponseVO addECase(ECaseInputVO eCaseInputVO){
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            ECaseEntity eCaseEntity = new ECaseEntity();
            eCaseEntity.setId(idGenerator.nextId());
            eCaseEntity.setCaseName(eCaseInputVO.getCaseName() != null ? eCaseInputVO.getCaseName() : "");
            eCaseEntity.setCaseDesc(eCaseInputVO.getCaseDesc() != null ? eCaseInputVO.getCaseDesc() : "");
            eCaseEntity.setDataSetId(eCaseInputVO.getDataSetId() != null ? eCaseInputVO.getDataSetId() : "");
            eCaseEntity.setModelId(eCaseInputVO.getModelId() != null ? eCaseInputVO.getModelId() : "");

            eCaseEntity.setStatus("0");
            if (eCaseInputVO.getType() != null) {
                eCaseEntity.setType(eCaseInputVO.getType());
            } else {
                throw new ServiceException("必须提供type");
            }

            Long nowTime = System.currentTimeMillis();
            eCaseEntity.setCreateTime(nowTime);
            eCaseEntity.setUpdateTime(nowTime);

            eCaseEntity.setIsDeleted(0);

            eCaseRepository.save(eCaseEntity);

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData("OK");
            return responseVO;

        } catch (Exception e) {
            log.error("ECase新建异常", e);
            return responseVO;
        }
    }

    /**
     * 修改用例
     *
     * @return
     */
    public ResponseVO updateECase(ECaseInputVO eCaseInputVO){
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            ECaseEntity eCaseEntity;
            if (eCaseInputVO.getId() != null) {
                Optional<ECaseEntity> opt = eCaseRepository.findById(eCaseInputVO.getId());
                if (opt.isPresent() && opt.get().getIsDeleted() == 0) {
                    eCaseEntity = opt.get();
                } else {
                    throw new ServiceException("未找到该数据");
                }
            } else {
                throw new ServiceException("必须提供caseId");
            }

            if (eCaseInputVO.getCaseName() != null && !eCaseInputVO.getCaseName().equals("")) {
                eCaseEntity.setCaseName(eCaseInputVO.getCaseName());
            }
            if (eCaseInputVO.getCaseDesc() != null && !eCaseInputVO.getCaseDesc().equals("")) {
                eCaseEntity.setCaseDesc(eCaseInputVO.getCaseDesc());
            }
            if (eCaseInputVO.getStatus() != null && !eCaseInputVO.getStatus().equals("")) {
                eCaseEntity.setStatus(eCaseInputVO.getStatus());
            }
            if (eCaseInputVO.getType() != null && !eCaseInputVO.getType().equals("")) {
                eCaseEntity.setType(eCaseInputVO.getType());
            }
            if (eCaseInputVO.getModelId() != null && !eCaseInputVO.getModelId().equals("")) {
                eCaseEntity.setModelId(eCaseInputVO.getModelId());
            }
            if (eCaseInputVO.getDataSetId() != null && !eCaseInputVO.getDataSetId().equals("")) {
                eCaseEntity.setDataSetId(eCaseInputVO.getDataSetId());
            }
            eCaseEntity.setUpdateTime(System.currentTimeMillis());

            eCaseRepository.save(eCaseEntity);

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData("OK");
            return responseVO;

        } catch (ServiceException e) {
            log.error("ECase更新异常", e);
            return responseVO;
        } catch (Exception e) {
            log.error("ECase更新异常", e);
            return responseVO;
        }
    }

    /**
     * 查询用例
     * Id方式
     *
     * @return
     */
    public ResponseVO<ECaseOutputVO> getECase(String caseId){
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            ECaseEntity eCaseEntity;
            Optional<ECaseEntity> opt = eCaseRepository.findById(caseId);
            if (opt.isPresent() && opt.get().getIsDeleted() == 0) {
                eCaseEntity = opt.get();
            } else {
                throw new ServiceException("未找到该数据");
            }

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData(new ECaseOutputVO(eCaseEntity));
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
     * 删除用例
     *
     * @return
     */
    public ResponseVO deleteECase(String caseId){
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            ECaseEntity eCaseEntity;
            Optional<ECaseEntity> opt = eCaseRepository.findById(caseId);
            if (opt.isPresent() && opt.get().getIsDeleted() == 0) {
                eCaseEntity = opt.get();
            } else {
                throw new ServiceException("未找到该数据");
            }

            eCaseEntity.setIsDeleted(1);

            eCaseRepository.save(eCaseEntity);

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData("OK");
            return responseVO;

        } catch (ServiceException e) {
            log.error("ECase删除异常", e);
            return responseVO;
        } catch (Exception e) {
            log.error("ECase删除异常", e);
            return responseVO;
        }
    }
}
