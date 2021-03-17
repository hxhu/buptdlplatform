package com.bupt.dlplatform.service.impl;

import com.alipay.sofa.rpc.config.ConsumerConfig;
import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.exception.ServiceException;
import com.bupt.dlplatform.mapper.EDeviceRepository;
import com.bupt.dlplatform.mapper.EFileRepository;
import com.bupt.dlplatform.mapper.EModelRepository;
import com.bupt.dlplatform.model.EDeviceEntity;
import com.bupt.dlplatform.model.EFileEntity;
import com.bupt.dlplatform.model.EModelEntity;
import com.bupt.dlplatform.rpc.MQTTService;
import com.bupt.dlplatform.service.EFileService;
import com.bupt.dlplatform.service.ELogService;
import com.bupt.dlplatform.util.IdGenerator;
import com.bupt.dlplatform.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by huhx on 2020/12/16
 */
@Service
@Slf4j
public class EFileServiceImpl implements EFileService {
    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private EFileRepository eFileRepository;

    @Autowired
    private EDeviceRepository eDeviceRepository;

    @Autowired
    private ELogService eLogService;

    private volatile ConsumerConfig<MQTTService> consumerConfig = null;
    private MQTTService mqttService = null;

    /**
     * 增加文件
     *
     * @return
     */
    public ResponseVO addEFile(EFileInputVO eFileInputVO) {
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            EFileEntity eFileEntity = new EFileEntity();
            eFileEntity.setId(idGenerator.nextId());
            eFileEntity.setFileName(eFileInputVO.getFileName() != null ? eFileInputVO.getFileName() : "");
            eFileEntity.setFileDesc(eFileInputVO.getFileDesc() != null ? eFileInputVO.getFileDesc() : "");
            eFileEntity.setCreateTime(eFileInputVO.getCreateTime() != null ? eFileInputVO.getCreateTime() : 0L);
            if (eFileInputVO.getFileLocation() != null) {
                eFileEntity.setFileLocation(eFileInputVO.getFileLocation());
            } else {
                throw new ServiceException("必须提供modelLocation");
            }
            eFileEntity.setIsDeleted(0);

            eFileEntity.setType(eFileInputVO.getType() != null ? eFileInputVO.getType() : "");

            eFileRepository.save(eFileEntity);

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData("OK");
            return responseVO;

        } catch (ServiceException e) {
            log.error("EFile新建异常", e);
            return responseVO;
        } catch (Exception e) {
            log.error("EFile新建异常", e);
            return responseVO;
        }
    }

    /**
     * 修改文件
     *
     * @return
     */
    public ResponseVO updateEFile(EFileInputVO eFileInputVO) {
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            EFileEntity eFileEntity;
            if (eFileInputVO.getId() != null) {
                Optional<EFileEntity> opt = eFileRepository.findById(eFileInputVO.getId());
                if (opt.isPresent() && opt.get().getIsDeleted() == 0) {
                    eFileEntity = opt.get();
                } else {
                    throw new ServiceException("未找到该数据");
                }
            } else {
                throw new ServiceException("必须提供fileId");
            }
            if (eFileInputVO.getFileName() != null) {
                eFileEntity.setFileName(eFileInputVO.getFileName());
            }
            if (eFileInputVO.getFileDesc() != null) {
                eFileEntity.setFileDesc(eFileInputVO.getFileDesc());
            }
            if (eFileInputVO.getCreateTime() != null) {
                eFileEntity.setCreateTime(eFileInputVO.getCreateTime());
            }
            if (eFileInputVO.getFileLocation() != null) {
                eFileEntity.setFileLocation(eFileInputVO.getFileLocation());
            }

            if (eFileInputVO.getType() != null) {
                eFileEntity.setType(eFileInputVO.getType());
            }

            eFileRepository.save(eFileEntity);

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData("OK");
            return responseVO;

        } catch (ServiceException e) {
            log.error("EFile更新异常", e);
            return responseVO;
        } catch (Exception e) {
            log.error("EFile更新异常", e);
            return responseVO;
        }
    }

    /**
     * 查询文件
     * Id方式
     *
     * @return
     */
    public ResponseVO<EFileOutputVO> getEFile(String fileId) {
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            EFileEntity eFileEntity;
            Optional<EFileEntity> opt = eFileRepository.findById(fileId);
            if (opt.isPresent() && opt.get().getIsDeleted() == 0) {
                eFileEntity = opt.get();
            } else {
                throw new ServiceException("未找到该数据");
            }

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData(new EFileOutputVO(eFileEntity));
            return responseVO;

        } catch (ServiceException e) {
            log.error("EFile查询异常", e);
            return responseVO;
        } catch (Exception e) {
            log.error("EFile查询异常", e);
            return responseVO;
        }
    }

    /**
     * 查询文件列表
     * @return
     */
    public ResponseVO<List<EFileOutputVO>> getEFileList(){
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            List<EFileEntity> list = eFileRepository.findAll();
            List<EFileOutputVO> result = new ArrayList<EFileOutputVO>();

            for( EFileEntity eFileEntity : list ){
                if( eFileEntity.getIsDeleted() == 0 ){
                    result.add( new EFileOutputVO(eFileEntity) );
                }
            }

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData( result );
            return responseVO;
        }catch (Exception e){
            log.error("EFile查询异常", e);
            return responseVO;
        }
    }

    /**
     * 删除文件
     *
     * @return
     */
    public ResponseVO deleteEFile(String fileId) {
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            EFileEntity eFileEntity;
            Optional<EFileEntity> opt = eFileRepository.findById(fileId);
            if (opt.isPresent() && opt.get().getIsDeleted() == 0) {
                eFileEntity = opt.get();
            } else {
                throw new ServiceException("未找到该数据");
            }

            eFileEntity.setIsDeleted(1);

            eFileRepository.save(eFileEntity);

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData("OK");
            return responseVO;

        } catch (ServiceException e) {
            log.error("EFile删除异常", e);
            return responseVO;
        } catch (Exception e) {
            log.error("EFile删除异常", e);
            return responseVO;
        }
    }

    /**
     * 推送文件
     *
     * @return
     */
    public ResponseVO pushFileWithDevices(PushFileInputVO pushFileInputVO) {
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        System.out.println(pushFileInputVO.toString());

        try {
            // 查询文件
            EFileEntity eFileEntity;
            Optional<EFileEntity> opt = eFileRepository.findById(pushFileInputVO.getFileId());
            if (opt.isPresent() && opt.get().getIsDeleted() == 0) {
                eFileEntity = opt.get();
            } else {
                throw new ServiceException("未找到该数据");
            }

            // 检查设备
            for (String deviceId : pushFileInputVO.getDeviceIds()) {
                Optional<EDeviceEntity> tmp = eDeviceRepository.findById(deviceId);
                if (!tmp.isPresent()) {
                    throw new ServiceException("未找到该数据");
                }

                if (tmp.get().getIsDeleted() != 0) {
                    throw new ServiceException("该数据已经删除");
                }
            }


            // 双重检验锁
            if( mqttService == null ){
                synchronized (MQTTService.class){
                    if( mqttService == null ){
                        // PRC调用服务
                        consumerConfig = new ConsumerConfig<MQTTService>()
                                .setInterfaceId(MQTTService.class.getName()) // 指定接口
                                .setProtocol("bolt") // 指定协议
                                .setDirectUrl("bolt://127.0.0.1:12400"); // 指定直连地址
                        // 生成代理类
                        mqttService = consumerConfig.refer();
                    }
                }
            }

            if( mqttService.fileModel(
                    pushFileInputVO.getDeviceIds(),
                    pushFileInputVO.getFileId(),
                    eFileEntity.getFileLocation(),
                    pushFileInputVO.getType(),
                    pushFileInputVO.getParttern()).equals("ERROR") ){
                new ServiceException("发送数据失败");
            }

            for (String deviceId : pushFileInputVO.getDeviceIds()) {
                eLogService.addELog(new ELogInputVO(
                        null,
                        null,
                        deviceId,
                        "2",
                        "推送文件到设备",
                        System.currentTimeMillis()));
            }

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData("OK");
            return responseVO;

        } catch (Exception e) {
            log.error("文件推送异常", e);
            return responseVO;
        }
    }
}
