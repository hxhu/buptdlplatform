package com.bupt.dlplatform.service.impl;

import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.exception.ServiceException;
import com.bupt.dlplatform.mapper.EDeviceRepository;
import com.bupt.dlplatform.mapper.EModelRepository;
import com.bupt.dlplatform.model.EDeviceEntity;
import com.bupt.dlplatform.model.EModelEntity;
import com.bupt.dlplatform.service.EDeviceService;
import com.bupt.dlplatform.util.IdGenerator;
import com.bupt.dlplatform.vo.EDeviceInputVO;
import com.bupt.dlplatform.vo.EDeviceOutputVO;
import com.bupt.dlplatform.vo.EModelOutputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    /**
     * 增加设备
     * @return
     */
    public ResponseVO addEDevice(EDeviceInputVO eDeviceInputVO){
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            EDeviceEntity eDeviceEntity = new EDeviceEntity();
            if( eDeviceInputVO.getId() != null ){
                eDeviceEntity.setId( eDeviceInputVO.getId() );
            }else{
                throw new ServiceException("必须提供deviceId");
            }
            eDeviceEntity.setDeviceName( eDeviceInputVO.getDeviceName() != null ? eDeviceInputVO.getDeviceName() : "" );
            eDeviceEntity.setDeviceDesc( eDeviceInputVO.getDeviceDesc() != null ? eDeviceInputVO.getDeviceDesc() : "" );

            eDeviceEntity.setVideoRtsp( eDeviceInputVO.getVideoRtsp() != null ? eDeviceInputVO.getVideoRtsp() : "" );
            eDeviceEntity.setVideoMessage( eDeviceInputVO.getVideoMessage() != null ? eDeviceInputVO.getVideoMessage() : "" );
            eDeviceEntity.setCurrentModelId( eDeviceInputVO.getCurrentModelId() != null ? eDeviceInputVO.getCurrentModelId() : "" );

            eDeviceEntity.setIsDeleted(0);

            eDeviceRepository.save(eDeviceEntity);

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
     * 修改设备
     * @return
     */
    public ResponseVO updateEDevice(EDeviceInputVO eDeviceInputVO){
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            EDeviceEntity eDeviceEntity;
            if( eDeviceInputVO.getId() != null ){
                Optional<EDeviceEntity> opt = eDeviceRepository.findById(eDeviceInputVO.getId());
                if( opt.isPresent() && opt.get().getIsDeleted() == 0 ){
                    eDeviceEntity = opt.get();
                }else{
                    throw new ServiceException("未找到该数据");
                }
            }else{
                throw new ServiceException("必须提供deviceId");
            }
            if( eDeviceInputVO.getDeviceName() != null ){
                eDeviceEntity.setDeviceName( eDeviceInputVO.getDeviceName() );
            }
            if( eDeviceInputVO.getDeviceDesc() != null ){
                eDeviceEntity.setDeviceDesc( eDeviceInputVO.getDeviceDesc() );
            }
            if( eDeviceInputVO.getVideoRtsp() != null ){
                eDeviceEntity.setVideoRtsp( eDeviceInputVO.getVideoRtsp() );
            }
            if( eDeviceInputVO.getVideoMessage() != null ){
                eDeviceEntity.setVideoMessage( eDeviceInputVO.getVideoMessage() );
            }
            if( eDeviceInputVO.getCurrentModelId() != null ){
                eDeviceEntity.setCurrentModelId( eDeviceInputVO.getCurrentModelId() );
            }

            eDeviceRepository.save(eDeviceEntity);

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData("OK");
            return responseVO;

        }catch ( ServiceException e ){
            log.error("EDevice更新异常", e);
            return responseVO;
        }catch (Exception e){
            log.error("EDevice更新异常", e);
            return responseVO;
        }
    }

    /**
     * 查询设备
     * Id方式
     * @return
     */
    public ResponseVO<EDeviceOutputVO> getEDevice(String deviceId){
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            EDeviceEntity eDeviceEntity;
            Optional<EDeviceEntity> opt = eDeviceRepository.findById(deviceId);
            if( opt.isPresent() && opt.get().getIsDeleted() == 0 ){
                eDeviceEntity = opt.get();
            }else{
                throw new ServiceException("未找到该数据");
            }

            EModelEntity eModelEntity;
            Optional<EModelEntity> optModel = eModelRepository.findById(eDeviceEntity.getCurrentModelId());
            if( optModel.isPresent() && optModel.get().getIsDeleted() == 0 ){
                eModelEntity = optModel.get();
            }else{
                throw new ServiceException("未找到响应模型");
            }

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData( new EDeviceOutputVO(eDeviceEntity, eModelEntity) );
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
     * 查询设备列表
     * @return
     */
    public ResponseVO<List<EDeviceOutputVO>> getEDeviceList(){
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            List<EDeviceEntity> tmp = eDeviceRepository.findAll();
            List<EDeviceEntity> list = new ArrayList<EDeviceEntity>();
            for( EDeviceEntity eDeviceEntity : tmp ){
                if( eDeviceEntity.getIsDeleted() == 0 ){
                    list.add(eDeviceEntity);
                }
            }

            List<EDeviceOutputVO> result = new ArrayList<EDeviceOutputVO>();

            for( EDeviceEntity eDeviceEntity : list ){
                EModelEntity eModelEntity;
                Optional<EModelEntity> optModel = eModelRepository.findById(eDeviceEntity.getCurrentModelId());
                if( optModel.isPresent() && optModel.get().getIsDeleted() == 0 ){
                    eModelEntity = optModel.get();
                }else{
                    throw new ServiceException("未找到响应模型");
                }
                result.add( new EDeviceOutputVO(eDeviceEntity, eModelEntity) );
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
     * 删除设备
     * @return
     */
    public ResponseVO deleteEDevice(String deviceId){
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            EDeviceEntity eDeviceEntity;
            Optional<EDeviceEntity> opt = eDeviceRepository.findById(deviceId);
            if( opt.isPresent() && opt.get().getIsDeleted() == 0 ){
                eDeviceEntity = opt.get();
            }else{
                throw new ServiceException("未找到该数据");
            }

            eDeviceEntity.setIsDeleted(1);

            eDeviceRepository.save(eDeviceEntity);

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData("OK");
            return responseVO;

        }catch ( ServiceException e ){
            log.error("EDevice删除异常", e);
            return responseVO;
        }catch (Exception e){
            log.error("EDevice删除异常", e);
            return responseVO;
        }
    }
}
