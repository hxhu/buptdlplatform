package com.bupt.dlplatform.service.impl;

import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.exception.ServiceException;
import com.bupt.dlplatform.mapper.EDeviceRepository;
import com.bupt.dlplatform.mapper.EFileRepository;
import com.bupt.dlplatform.mapper.ELogRepository;
import com.bupt.dlplatform.mapper.EModelRepository;
import com.bupt.dlplatform.model.EDeviceEntity;
import com.bupt.dlplatform.model.EFileEntity;
import com.bupt.dlplatform.model.EModelEntity;
import com.bupt.dlplatform.service.EDeviceService;
import com.bupt.dlplatform.service.ELogService;
import com.bupt.dlplatform.util.IdGenerator;
import com.bupt.dlplatform.vo.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by huhx on 2020/12/4
 */
@Service
@Slf4j
public class EDeviceServiceImpl implements EDeviceService {
    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private EDeviceRepository eDeviceRepository;

    @Autowired
    private EModelRepository eModelRepository;

    @Autowired
    private EFileRepository eFileRepository;

    @Autowired
    private ELogService eLogService;

    /**
     * 增加设备
     *
     * @return
     */
    public ResponseVO addEDevice(EDeviceInputVO eDeviceInputVO) {
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            EDeviceEntity eDeviceEntity = new EDeviceEntity();
            if (eDeviceInputVO.getId() != null) {
                eDeviceEntity.setId(eDeviceInputVO.getId());
            } else {
                throw new ServiceException("必须提供deviceId");
            }
            eDeviceEntity.setDeviceName(eDeviceInputVO.getDeviceName() != null ? eDeviceInputVO.getDeviceName() : "");
            eDeviceEntity.setDeviceDesc(eDeviceInputVO.getDeviceDesc() != null ? eDeviceInputVO.getDeviceDesc() : "");

            eDeviceEntity.setVideoRtsp(eDeviceInputVO.getVideoRtsp() != null ? eDeviceInputVO.getVideoRtsp() : "");
            eDeviceEntity.setVideoMessage(eDeviceInputVO.getVideoMessage() != null ? eDeviceInputVO.getVideoMessage() : "");
            eDeviceEntity.setCurrentModelId(eDeviceInputVO.getCurrentModelId() != null ? eDeviceInputVO.getCurrentModelId() : "");
            eDeviceEntity.setCurrentFileIdSet(new HashSet<String>());

            eDeviceEntity.setIsDeleted(0);

            eDeviceRepository.save(eDeviceEntity);
            eLogService.addELog(new ELogInputVO(
                    null,
                    "",
                    eDeviceInputVO.getId(),
                    "1",
                    "增加设备",
                    System.currentTimeMillis()));

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData("OK");
            return responseVO;

        } catch (Exception e) {
            log.error("EDevice新建异常", e);
            return responseVO;
        }
    }

    /**
     * 修改设备
     *
     * @return
     */
    public ResponseVO updateEDevice(EDeviceInputVO eDeviceInputVO) {
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            EDeviceEntity eDeviceEntity;
            if (eDeviceInputVO.getId() != null) {
                Optional<EDeviceEntity> opt = eDeviceRepository.findById(eDeviceInputVO.getId());
                if (opt.isPresent() && opt.get().getIsDeleted() == 0) {
                    eDeviceEntity = opt.get();
                } else {
                    throw new ServiceException("未找到该数据");
                }
            } else {
                throw new ServiceException("必须提供deviceId");
            }
            if (eDeviceInputVO.getDeviceName() != null && !eDeviceInputVO.getDeviceName().equals("")) {
                eDeviceEntity.setDeviceName(eDeviceInputVO.getDeviceName());
            }
            if (eDeviceInputVO.getDeviceDesc() != null && !eDeviceInputVO.getDeviceDesc().equals("")) {
                eDeviceEntity.setDeviceDesc(eDeviceInputVO.getDeviceDesc());
            }
            if (eDeviceInputVO.getVideoRtsp() != null && !eDeviceInputVO.getVideoRtsp().equals("")) {
                eDeviceEntity.setVideoRtsp(eDeviceInputVO.getVideoRtsp());
            }
            if (eDeviceInputVO.getVideoMessage() != null && !eDeviceInputVO.getVideoMessage().equals("")) {
                eDeviceEntity.setVideoMessage(eDeviceInputVO.getVideoMessage());
            }
            if (eDeviceInputVO.getCurrentModelId() != null && !eDeviceInputVO.getCurrentModelId().equals("")) {
                if (!eModelRepository.existsById(eDeviceInputVO.getCurrentModelId())) {
                    throw new ServiceException("模型不存在");
                }
                eDeviceEntity.setCurrentModelId(eDeviceInputVO.getCurrentModelId());
            }
            if (eDeviceInputVO.getCurrentFileIdSet() != null) {
                for (String i : eDeviceInputVO.getCurrentFileIdSet()) {
                    if (!eFileRepository.existsById(i)) {
                        throw new ServiceException("模型不存在");
                    }
                }
                eDeviceEntity.setCurrentFileIdSet(eDeviceInputVO.getCurrentFileIdSet());
            }

            eDeviceRepository.save(eDeviceEntity);

            eLogService.addELog(new ELogInputVO(
                    null,
                    "",
                    eDeviceInputVO.getId(),
                    "3",
                    "修改设备",
                    System.currentTimeMillis()));

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData("OK");
            return responseVO;

        } catch (ServiceException e) {
            log.error("EDevice更新异常", e);
            return responseVO;
        } catch (Exception e) {
            log.error("EDevice更新异常", e);
            return responseVO;
        }
    }

    /**
     * 更新设备的配置文件
     *
     * @return
     */
    public ResponseVO updateEFileIdSet(String deviceId, String fileId) {
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            EDeviceEntity eDeviceEntity;
            if (deviceId != null) {
                Optional<EDeviceEntity> opt = eDeviceRepository.findById(deviceId);
                if (opt.isPresent() && opt.get().getIsDeleted() == 0) {
                    eDeviceEntity = opt.get();
                } else {
                    throw new ServiceException("未找到该数据");
                }
            } else {
                throw new ServiceException("必须提供deviceId");
            }

            if (fileId != null) {
                Optional<EFileEntity> optFile = eFileRepository.findById(fileId);
                if (!optFile.isPresent() || optFile.get().getIsDeleted() != 0) {
                    throw new ServiceException("未找到该文件");
                }
            } else {
                throw new ServiceException("必须提供deviceId");
            }

            Set<String> set = eDeviceEntity.getCurrentFileIdSet();
            set.add(fileId);

            System.out.println(eDeviceEntity.toString());
            eDeviceRepository.save(eDeviceEntity);

            eLogService.addELog(new ELogInputVO(
                    null,
                    "",
                    deviceId,
                    "3",
                    "修改设备",
                    System.currentTimeMillis()));

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData("OK");
            return responseVO;

        } catch (Exception e) {
            log.error("EDevice的EFileIdSet更新异常", e);
            return responseVO;
        }
    }

    /**
     * 查询设备
     * Id方式
     *
     * @return
     */
    public ResponseVO<EDeviceOutputVO> getEDevice(String deviceId) {
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            EDeviceEntity eDeviceEntity;
            Optional<EDeviceEntity> opt = eDeviceRepository.findById(deviceId);
            if (opt.isPresent() && opt.get().getIsDeleted() == 0) {
                eDeviceEntity = opt.get();
            } else {
                throw new ServiceException("未找到该数据");
            }

            EModelEntity eModelEntity;
            Optional<EModelEntity> optModel = eModelRepository.findById(eDeviceEntity.getCurrentModelId());
            if (optModel.isPresent() && optModel.get().getIsDeleted() == 0) {
                eModelEntity = optModel.get();
            } else {
                throw new ServiceException("未找到响应模型");
            }

            Set<EFileEntity> fileSet = new HashSet<EFileEntity>();
            if (eDeviceEntity.getCurrentFileIdSet() != null) {
                for (String i : eDeviceEntity.getCurrentFileIdSet()) {
                    EFileEntity eFileEntity;
                    Optional<EFileEntity> optFile = eFileRepository.findById(i);
                    if (optFile.isPresent() && optFile.get().getIsDeleted() == 0) {
                        eFileEntity = optFile.get();
                        fileSet.add(eFileEntity);
                    } else {
                        throw new ServiceException("未找到文件");
                    }
                }
            }


            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData(new EDeviceOutputVO(eDeviceEntity, eModelEntity, fileSet));
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
     * 查询设备列表
     *
     * @return
     */
    public ResponseVO<List<EDeviceOutputVO>> getEDeviceList() {
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            List<EDeviceEntity> tmp = eDeviceRepository.findAll();
            List<EDeviceEntity> list = new ArrayList<EDeviceEntity>();
            for (EDeviceEntity eDeviceEntity : tmp) {
                if (eDeviceEntity.getIsDeleted() == 0) {
                    list.add(eDeviceEntity);
                }
            }

            List<EDeviceOutputVO> result = new ArrayList<EDeviceOutputVO>();

            for (EDeviceEntity eDeviceEntity : list) {
                EModelEntity eModelEntity;
                Optional<EModelEntity> optModel = eModelRepository.findById(eDeviceEntity.getCurrentModelId());
                if (optModel.isPresent() && optModel.get().getIsDeleted() == 0) {
                    eModelEntity = optModel.get();
                } else {
                    throw new ServiceException("未找到响应模型");
                }

                Set<EFileEntity> fileSet = new HashSet<EFileEntity>();
                if (eDeviceEntity.getCurrentFileIdSet() != null) {
                    for (String i : eDeviceEntity.getCurrentFileIdSet()) {
                        EFileEntity eFileEntity;
                        Optional<EFileEntity> optFile = eFileRepository.findById(i);
                        if (optFile.isPresent() && optFile.get().getIsDeleted() == 0) {
                            eFileEntity = optFile.get();
                            fileSet.add(eFileEntity);
                        } else {
                            throw new ServiceException("未找到文件");
                        }
                    }
                }


                result.add(new EDeviceOutputVO(eDeviceEntity, eModelEntity, fileSet));
            }

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData(result);
            return responseVO;
        } catch (Exception e) {
            log.error("EModel查询异常", e);
            return responseVO;
        }
    }

    /**
     * 删除设备
     *
     * @return
     */
    public ResponseVO deleteEDevice(String deviceId) {
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            EDeviceEntity eDeviceEntity;
            Optional<EDeviceEntity> opt = eDeviceRepository.findById(deviceId);
            if (opt.isPresent() && opt.get().getIsDeleted() == 0) {
                eDeviceEntity = opt.get();
            } else {
                throw new ServiceException("未找到该数据");
            }

            eDeviceEntity.setIsDeleted(1);

            eDeviceRepository.save(eDeviceEntity);
            eLogService.addELog(new ELogInputVO(
                    null,
                    "",
                    deviceId,
                    "2",
                    "删除设备",
                    System.currentTimeMillis()));

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData("OK");
            return responseVO;

        } catch (ServiceException e) {
            log.error("EDevice删除异常", e);
            return responseVO;
        } catch (Exception e) {
            log.error("EDevice删除异常", e);
            return responseVO;
        }
    }

    /**
     * 删除某个文件
     * @return
     */
    public ResponseVO deleteFile(String deviceId, List<String> fileIds){
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            EDeviceEntity eDeviceEntity;
            Optional<EDeviceEntity> opt = eDeviceRepository.findById(deviceId);
            if (opt.isPresent() && opt.get().getIsDeleted() == 0) {
                eDeviceEntity = opt.get();
            } else {
                throw new ServiceException("未找到该数据");
            }

            Set<String> fileIdSet;
            if (eDeviceEntity.getCurrentFileIdSet() != null) {
                fileIdSet = eDeviceEntity.getCurrentFileIdSet();
                for( String fileId : fileIds ){
                    if( fileIdSet.contains(fileId) ){
                        fileIdSet.remove(fileId);
                    }else{
                        throw new ServiceException("未找到该文件，删除失败");
                    }
                }
            }else{
                throw new ServiceException("未找到该文件，删除失败");
            }


            eDeviceEntity.setCurrentFileIdSet(fileIdSet);
            eDeviceRepository.save(eDeviceEntity);

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData("OK");
            return responseVO;

        } catch (ServiceException e) {
            log.error("EDevice中EFile删除异常", e);
            return responseVO;
        } catch (Exception e) {
            log.error("EDevice中EFile删除异常", e);
            return responseVO;
        }
    }
}
