package com.bupt.dlplatform.service.impl;

import com.alipay.sofa.rpc.config.ConsumerConfig;
import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.exception.ServiceException;
import com.bupt.dlplatform.mapper.EModelRepository;
import com.bupt.dlplatform.model.EModelEntity;
import com.bupt.dlplatform.rpc.MQTTService;
import com.bupt.dlplatform.service.EModelService;
import com.bupt.dlplatform.util.IdGenerator;
import com.bupt.dlplatform.vo.EModelInputVO;
import com.bupt.dlplatform.vo.EModelOutputVO;
import com.bupt.dlplatform.vo.PushModelInputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by huhx on 2020/12/4
 */
@Service
@Slf4j
public class EModelServiceImpl implements EModelService {
    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private EModelRepository eModelRepository;

    private volatile ConsumerConfig<MQTTService> consumerConfig = null;
    private MQTTService mqttService = null;

    /**
     * 增加模型
     * @return
     */
    public ResponseVO addEModel(EModelInputVO eModelInputVO){
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            EModelEntity eModelEntity = new EModelEntity();
            eModelEntity.setId(idGenerator.nextId());
            eModelEntity.setModelName( eModelInputVO.getModelName() != null ? eModelInputVO.getModelName() : "" );
            eModelEntity.setModelDesc( eModelInputVO.getModelDesc() != null ? eModelInputVO.getModelDesc() : "" );
            eModelEntity.setCreateTime( eModelInputVO.getCreateTime() != null ? eModelInputVO.getCreateTime() : 0L );
            if( eModelInputVO.getModelLocation() != null ){
                eModelEntity.setModelLocation( eModelInputVO.getModelLocation() );
            }else{
                throw new ServiceException("必须提供modelLocation");
            }
            eModelEntity.setIsDeleted(0);

            eModelRepository.save(eModelEntity);

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData("OK");
            return responseVO;

        }catch ( ServiceException e ){
            log.error("EModel新建异常", e);
            return responseVO;
        }catch (Exception e){
            log.error("EModel新建异常", e);
            return responseVO;
        }
    }

    /**
     * 修改模型
     * @return
     */
    public ResponseVO updateEModel(EModelInputVO eModelInputVO){
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            EModelEntity eModelEntity;
            if( eModelInputVO.getId() != null ){
                Optional<EModelEntity> opt = eModelRepository.findById(eModelInputVO.getId());
                if( opt.isPresent() && opt.get().getIsDeleted() == 0 ){
                    eModelEntity = opt.get();
                }else{
                    throw new ServiceException("未找到该数据");
                }
            }else{
                throw new ServiceException("必须提供modelId");
            }
            if( eModelInputVO.getModelName() != null ){
                eModelEntity.setModelName( eModelInputVO.getModelName() );
            }
            if( eModelInputVO.getModelDesc() != null ){
                eModelEntity.setModelDesc( eModelInputVO.getModelDesc() );
            }
            if( eModelInputVO.getCreateTime() != null ){
                eModelEntity.setCreateTime( eModelInputVO.getCreateTime() );
            }
            if( eModelInputVO.getModelLocation() != null ){
                eModelEntity.setModelLocation( eModelInputVO.getModelLocation() );
            }

            eModelRepository.save(eModelEntity);

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData("OK");
            return responseVO;

        }catch ( ServiceException e ){
            log.error("EModel更新异常", e);
            return responseVO;
        }catch (Exception e){
            log.error("EModel更新异常", e);
            return responseVO;
        }
    }

    /**
     * 查询模型
     * Id方式
     * @return
     */
    public ResponseVO<EModelOutputVO> getEModel(String modelId){
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            EModelEntity eModelEntity;
            Optional<EModelEntity> opt = eModelRepository.findById(modelId);
            if( opt.isPresent() && opt.get().getIsDeleted() == 0 ){
                eModelEntity = opt.get();
            }else{
                throw new ServiceException("未找到该数据");
            }

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData( new EModelOutputVO(eModelEntity) );
            return responseVO;

        }catch ( ServiceException e ){
            log.error("EModel查询异常", e);
            return responseVO;
        }catch (Exception e){
            log.error("EModel查询异常", e);
            return responseVO;
        }
    }

    /**
     * 查询模型列表
     * @return
     */
    public ResponseVO<List<EModelOutputVO>> getEModelList(){
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            List<EModelEntity> list = eModelRepository.findAll();
            List<EModelOutputVO> result = new ArrayList<EModelOutputVO>();

            for( EModelEntity eModelEntity : list ){
                if( eModelEntity.getIsDeleted() == 0 ){
                    result.add( new EModelOutputVO(eModelEntity) );
                }
            }

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData( result );
            return responseVO;
        }catch (Exception e){
            log.error("EModel查询异常", e);
            return responseVO;
        }
    }

    /**
     * 删除模型
     * @return
     */
    public ResponseVO deleteEModel(String modelId){
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            EModelEntity eModelEntity;
            Optional<EModelEntity> opt = eModelRepository.findById(modelId);
            if( opt.isPresent() && opt.get().getIsDeleted() == 0 ){
                eModelEntity = opt.get();
            }else{
                throw new ServiceException("未找到该数据");
            }

            eModelEntity.setIsDeleted(1);

            eModelRepository.save(eModelEntity);

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData("OK");
            return responseVO;

        }catch ( ServiceException e ){
            log.error("EModel删除异常", e);
            return responseVO;
        }catch (Exception e){
            log.error("EModel删除异常", e);
            return responseVO;
        }
    }

    /**
     * 推送模型
     * @return
     */
    public ResponseVO pushModelWithDevices(PushModelInputVO pushModelInputVO){
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        System.out.println( pushModelInputVO.toString() );

        try {
            // 查询模型
            EModelEntity eModelEntity;
            Optional<EModelEntity> opt = eModelRepository.findById(pushModelInputVO.getModelId());
            if( opt.isPresent() && opt.get().getIsDeleted() == 0 ){
                eModelEntity = opt.get();
            }else{
                throw new ServiceException("未找到该数据");
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

            if( mqttService.pushModel(
                    pushModelInputVO.getDeviceIds(),
                    pushModelInputVO.getModelId(),
                    eModelEntity.getModelLocation(),
                    pushModelInputVO.getType()).equals("ERROR") ){
                new ServiceException("发送数据失败");
            }

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData("OK");
            return responseVO;

        }catch (Exception e){
            log.error("模型推送异常", e);
            return responseVO;
        }
    }
}
