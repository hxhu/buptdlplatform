package com.bupt.dlplatform.service.impl;

import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.exception.ServiceException;
import com.bupt.dlplatform.mapper.EDeviceRepository;
import com.bupt.dlplatform.mapper.ELogRepository;
import com.bupt.dlplatform.mapper.EModelRepository;
import com.bupt.dlplatform.model.EDeviceEntity;
import com.bupt.dlplatform.model.ELogEntity;
import com.bupt.dlplatform.model.EModelEntity;
import com.bupt.dlplatform.service.EDeviceService;
import com.bupt.dlplatform.service.ELogService;
import com.bupt.dlplatform.util.IdGenerator;
import com.bupt.dlplatform.vo.EDeviceOutputVO;
import com.bupt.dlplatform.vo.ELogInputVO;
import com.bupt.dlplatform.vo.ELogOutputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by huhx on 2020/12/15
 */
@Service
@Slf4j
public class ELogServiceImpl implements ELogService {
    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private EDeviceRepository eDeviceRepository;

    @Autowired
    private EModelRepository eModelRepository;

    @Autowired
    private ELogRepository eLogRepository;

    /**
     * 增加日志
     * @return
     */
    public ResponseVO addELog(ELogInputVO eLogInputVO){
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            ELogEntity eLogEntity = new ELogEntity();
            eLogEntity.setId(idGenerator.nextId());

            String modelId = eLogInputVO.getModelId();
            String deviceId = eLogInputVO.getDeviceId();
            if( modelId != null || deviceId != null ){
                modelId = (modelId != null) ? modelId : "";
                if( eModelRepository.existsById(modelId) || modelId.equals("") ){
                    eLogEntity.setModelId(modelId);
                }else{
                    throw new ServiceException("modelId不存在");
                }

                deviceId = (deviceId != null) ? deviceId : "";
                if( eDeviceRepository.existsById(deviceId) || deviceId.equals("") ){
                    eLogEntity.setDeviceId(deviceId);
                }else{
                    throw new ServiceException("deviceId不存在");
                }
            }else{
                throw new ServiceException("modelId和deviceId必须提供一个");
            }

            eLogEntity.setMessage( eLogInputVO.getMessage() != null ? eLogInputVO.getMessage() : "" );
            if( eLogInputVO.getType() != null ){
                eLogEntity.setType( eLogInputVO.getType() );
            }else{
                throw new ServiceException("必须提供type");
            }
            eLogEntity.setTimestamp( eLogInputVO.getMessage() != null ? eLogInputVO.getTimestamp() : System.currentTimeMillis() );

            eLogEntity.setIsDeleted(0);

            eLogRepository.save(eLogEntity);

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData("OK");
            return responseVO;

        }catch (Exception e){
            log.error("EDevice新建异常", e);
            return responseVO;
        }
    }

    /**
     * 查询日志
     * Id方式
     * @return
     */
    public ResponseVO<ELogOutputVO> getELog(String logId){
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            ELogEntity eLogEntity;
            Optional<ELogEntity> opt = eLogRepository.findById(logId);
            if( opt.isPresent() && opt.get().getIsDeleted() == 0 ){
                eLogEntity = opt.get();
            }else{
                throw new ServiceException("未找到该数据");
            }

            EModelEntity eModelEntity;
            Optional<EModelEntity> optModel = eModelRepository.findById(eLogEntity.getModelId());
            if( optModel.isPresent() && optModel.get().getIsDeleted() == 0 ){
                eModelEntity = optModel.get();
            }else{
                throw new ServiceException("未找到相应模型");
            }

            EDeviceEntity eDeviceEntity;
            Optional<EDeviceEntity> optDevice = eDeviceRepository.findById(eLogEntity.getDeviceId());
            if( optDevice.isPresent() && optDevice.get().getIsDeleted() == 0 ){
                eDeviceEntity = optDevice.get();
            }else{
                throw new ServiceException("未找到相应设备");
            }

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData( new ELogOutputVO(eLogEntity, eModelEntity, eDeviceEntity) );
            return responseVO;

        }catch ( ServiceException e ){
            log.error("EDevice查询异常", e);
            return responseVO;
        }catch (Exception e){
            log.error("EDevice查询异常", e);
            return responseVO;
        }
    }

    /**
     * 查询日志列表
     * modelId方式
     * @return
     */
    public ResponseVO<List<ELogOutputVO>> getELogListByModelId(String modelId){
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            List<ELogEntity> tmp = eLogRepository.findAllByModelId(modelId);
            List<ELogEntity> list = new ArrayList<ELogEntity>();
            for( ELogEntity eLogEntity : tmp ){
                if( eLogEntity.getIsDeleted() == 0 ){
                    list.add(eLogEntity);
                }
            }

            List<ELogOutputVO> result = new ArrayList<ELogOutputVO>();

            for( ELogEntity eLogEntity : list ){
                EModelEntity eModelEntity;
                Optional<EModelEntity> optModel = eModelRepository.findById(eLogEntity.getModelId());
                if( optModel.isPresent() && optModel.get().getIsDeleted() == 0 ){
                    eModelEntity = optModel.get();
                }else{
                    throw new ServiceException("未找到相应模型");
                }

                EDeviceEntity eDeviceEntity;
                Optional<EDeviceEntity> optDevice = eDeviceRepository.findById(eLogEntity.getDeviceId());
                if( optDevice.isPresent() && optDevice.get().getIsDeleted() == 0 ){
                    eDeviceEntity = optDevice.get();
                }else{
                    throw new ServiceException("未找到相应设备");
                }

                result.add( new ELogOutputVO(eLogEntity, eModelEntity, eDeviceEntity) );
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
     * 查询日志列表
     * deviceId方式
     * @return
     */
    public ResponseVO<List<ELogOutputVO>> getELogListByDeviceId(String deviceId){
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            List<ELogEntity> tmp = eLogRepository.findAllByDeviceId(deviceId);
            List<ELogEntity> list = new ArrayList<ELogEntity>();
            for( ELogEntity eLogEntity : tmp ){
                if( eLogEntity.getIsDeleted() == 0 ){
                    list.add(eLogEntity);
                }
            }

            List<ELogOutputVO> result = new ArrayList<ELogOutputVO>();

            for( ELogEntity eLogEntity : list ){
                EModelEntity eModelEntity;
                Optional<EModelEntity> optModel = eModelRepository.findById(eLogEntity.getModelId());
                if( optModel.isPresent() && optModel.get().getIsDeleted() == 0 ){
                    eModelEntity = optModel.get();
                }else{
                    throw new ServiceException("未找到相应模型");
                }

                EDeviceEntity eDeviceEntity;
                Optional<EDeviceEntity> optDevice = eDeviceRepository.findById(eLogEntity.getDeviceId());
                if( optDevice.isPresent() && optDevice.get().getIsDeleted() == 0 ){
                    eDeviceEntity = optDevice.get();
                }else{
                    throw new ServiceException("未找到相应设备");
                }

                result.add( new ELogOutputVO(eLogEntity, eModelEntity, eDeviceEntity) );
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
     * 查询日志列表
     * @return
     */
    public ResponseVO<List<ELogOutputVO>> getEDeviceList(){
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            List<ELogEntity> tmp = eLogRepository.findAll();
            List<ELogEntity> list = new ArrayList<ELogEntity>();
            for( ELogEntity eLogEntity : tmp ){
                if( eLogEntity.getIsDeleted() == 0 ){
                    list.add(eLogEntity);
                }
            }

            List<ELogOutputVO> result = new ArrayList<ELogOutputVO>();

            for( ELogEntity eLogEntity : list ){
                EModelEntity eModelEntity;
                Optional<EModelEntity> optModel = eModelRepository.findById(eLogEntity.getModelId());
                if( optModel.isPresent() && optModel.get().getIsDeleted() == 0 ){
                    eModelEntity = optModel.get();
                }else{
                    throw new ServiceException("未找到相应模型");
                }

                EDeviceEntity eDeviceEntity;
                Optional<EDeviceEntity> optDevice = eDeviceRepository.findById(eLogEntity.getDeviceId());
                if( optDevice.isPresent() && optDevice.get().getIsDeleted() == 0 ){
                    eDeviceEntity = optDevice.get();
                }else{
                    throw new ServiceException("未找到相应设备");
                }

                result.add( new ELogOutputVO(eLogEntity, eModelEntity, eDeviceEntity) );
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
}
