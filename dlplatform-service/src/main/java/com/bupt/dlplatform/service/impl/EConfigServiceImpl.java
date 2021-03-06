package com.bupt.dlplatform.service.impl;

import com.alipay.sofa.rpc.config.ConsumerConfig;
import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.exception.ServiceException;
import com.bupt.dlplatform.mapper.EConfigRepository;
import com.bupt.dlplatform.mapper.EDeviceRepository;
import com.bupt.dlplatform.model.EConfigEntity;
import com.bupt.dlplatform.model.EDataSetEntity;
import com.bupt.dlplatform.model.EDeviceEntity;
import com.bupt.dlplatform.rpc.MQTTService;
import com.bupt.dlplatform.service.EConfigService;
import com.bupt.dlplatform.service.ELogService;
import com.bupt.dlplatform.util.IdGenerator;
import com.bupt.dlplatform.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by huhx on 2021/2/5
 */

@Service
@Slf4j
public class EConfigServiceImpl implements EConfigService {
    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private EConfigRepository eConfigRepository;

    @Autowired
    private EDeviceRepository eDeviceRepository;

    @Autowired
    private ELogService eLogService;

    private volatile ConsumerConfig<MQTTService> consumerConfig = null;
    private MQTTService mqttService = null;

    /**
     * 增加配置
     *
     * @return
     */
    public ResponseVO addEConfig(EConfigInputVO eConfigInputVO) {
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            EConfigEntity eConfigEntity = new EConfigEntity();
            eConfigEntity.setId(idGenerator.nextId());
            eConfigEntity.setConfigName(eConfigInputVO.getConfigName() != null ? eConfigInputVO.getConfigName() : "");
            eConfigEntity.setConfigDesc(eConfigInputVO.getConfigDesc() != null ? eConfigInputVO.getConfigDesc() : "");
            eConfigEntity.setConfigs(eConfigInputVO.getConfigs() != null ? eConfigInputVO.getConfigs() : new HashMap<String, Object>());
            eConfigEntity.setUpdateTime(System.currentTimeMillis());

            eConfigEntity.setIsDeleted(0);

            eConfigRepository.save(eConfigEntity);

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData("OK");
            return responseVO;

        } catch (Exception e) {
            log.error("EConfig新建配置", e);
            return responseVO;
        }
    }

    /**
     * 修改配置
     *
     * @return
     */
    public ResponseVO updateEConfig(EConfigInputVO eConfigInputVO) {
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            EConfigEntity eConfigEntity;
            if (eConfigInputVO.getId() != null) {
                Optional<EConfigEntity> opt = eConfigRepository.findById(eConfigInputVO.getId());
                if (opt.isPresent() && opt.get().getIsDeleted() == 0) {
                    eConfigEntity = opt.get();
                } else {
                    throw new ServiceException("未找到该数据");
                }
            } else {
                throw new ServiceException("必须提供configId");
            }
            if (eConfigInputVO.getConfigName() != null && !eConfigInputVO.getConfigName().equals("")) {
                eConfigEntity.setConfigName(eConfigInputVO.getConfigName());
            }
            if (eConfigInputVO.getConfigDesc() != null && !eConfigInputVO.getConfigDesc().equals("")) {
                eConfigEntity.setConfigDesc(eConfigInputVO.getConfigDesc());
            }
            if (eConfigInputVO.getConfigs() != null) {
                eConfigEntity.setConfigs(eConfigInputVO.getConfigs());
            }
            eConfigEntity.setUpdateTime(System.currentTimeMillis());

            eConfigRepository.save(eConfigEntity);

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData("OK");
            return responseVO;

        } catch (ServiceException e) {
            log.error("EConfig更新异常", e);
            return responseVO;
        } catch (Exception e) {
            log.error("EConfig更新异常", e);
            return responseVO;
        }
    }

    /**
     * 查询配置列表
     *
     * @return
     */
    public ResponseVO<List<EConfigOutputVO>> getEConfigList() {
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            List<EConfigEntity> tmp = eConfigRepository.findAll();
            List<EConfigEntity> list = new ArrayList<EConfigEntity>();
            for (EConfigEntity eConfigEntity : tmp) {
                if (eConfigEntity.getIsDeleted() == 0) {
                    list.add(eConfigEntity);
                }
            }

            List<EConfigOutputVO> result = new ArrayList<EConfigOutputVO>();

            for (EConfigEntity eConfigEntity : list) {
                result.add(new EConfigOutputVO(eConfigEntity));
            }

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData(result);
            return responseVO;
        } catch (Exception e) {
            log.error("EConfig查询异常", e);
            return responseVO;
        }
    }

    /**
     * 查询配置
     * Id方式
     *
     * @return
     */
    public ResponseVO<EConfigOutputVO> getEConfig(String configId) {
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            EConfigEntity eConfigEntity;
            Optional<EConfigEntity> opt = eConfigRepository.findById(configId);
            if (opt.isPresent() && opt.get().getIsDeleted() == 0) {
                eConfigEntity = opt.get();
            } else {
                throw new ServiceException("未找到该数据");
            }

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData(new EConfigOutputVO(eConfigEntity));
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
     * 删除配置
     *
     * @return
     */
    public ResponseVO deleteEConfig(String configId) {
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            EConfigEntity eConfigEntity;
            Optional<EConfigEntity> opt = eConfigRepository.findById(configId);
            if (opt.isPresent() && opt.get().getIsDeleted() == 0) {
                eConfigEntity = opt.get();
            } else {
                throw new ServiceException("未找到该数据");
            }

            eConfigEntity.setIsDeleted(1);

            eConfigRepository.save(eConfigEntity);

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData("OK");
            return responseVO;

        } catch (ServiceException e) {
            log.error("EConfig删除异常", e);
            return responseVO;
        } catch (Exception e) {
            log.error("EConfig删除异常", e);
            return responseVO;
        }
    }

    /**
     * 推送配置到设备
     *
     * @return
     */
    public ResponseVO pushDownConfigs(EConfigDownInputVO eConfigDownInputVO) {
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            EConfigEntity eConfigEntity;
            HashMap<String, Object> resultConfigs;
            Optional<EConfigEntity> opt = eConfigRepository.findById(eConfigDownInputVO.getConfigId());
            if (opt.isPresent() && opt.get().getIsDeleted() == 0) {
                eConfigEntity = opt.get();
                resultConfigs = eConfigEntity.getConfigs();
            } else {
                throw new ServiceException("未找到该数据");
            }

            ArrayList<String> deviceIds = eConfigDownInputVO.getDeviceIds();
            ArrayList<String> resultDeviceIds = new ArrayList<String>();
            for (String id : deviceIds) {
                Optional<EDeviceEntity> optIds = eDeviceRepository.findById(id);
                if ( optIds.isPresent() && optIds.get().getIsDeleted() == 0 ) {
                    resultDeviceIds.add(id);
                } else {
                    throw new ServiceException("未找到该数据");
                }
            }

            // resultConfigs
            // resultDeviceIds
            // 双重检验锁
            if (mqttService == null) {
                synchronized (MQTTService.class) {
                    if (mqttService == null) {
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

            if (mqttService.pushConfig(
                    resultConfigs,
                    resultDeviceIds,
                    eConfigDownInputVO.getParttern()
            ).equals("ERROR")) {
                new ServiceException("发送数据失败");
            }


            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData("OK");
            return responseVO;

        } catch (ServiceException e) {
            log.error("EConfig推送异常", e);
            return responseVO;
        } catch (Exception e) {
            log.error("EConfig推送异常", e);
            return responseVO;
        }
    }
}
