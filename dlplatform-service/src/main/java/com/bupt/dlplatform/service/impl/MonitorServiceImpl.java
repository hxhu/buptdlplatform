package com.bupt.dlplatform.service.impl;

import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.exception.ServiceException;
import com.bupt.dlplatform.mapper.MDisplayEntityRepository;
import com.bupt.dlplatform.mapper.MMonitorEntityRepository;
import com.bupt.dlplatform.model.MDisplayEntity;
import com.bupt.dlplatform.model.MMonitorEntity;
import com.bupt.dlplatform.service.MonitorService;
import com.bupt.dlplatform.util.IdGenerator;
import com.bupt.dlplatform.vo.MDisplayEntityOutputVO;
import com.bupt.dlplatform.vo.MMonitorEntityInputVO;
import com.bupt.dlplatform.vo.MMonitorEntityOutputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by huhx on 2020/10/3
 */
@Slf4j
@Service
public class MonitorServiceImpl implements MonitorService {
    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private MMonitorEntityRepository mMonitorEntityRepository;

    /**
     * 新建监控
     */
    @Override
    public ResponseVO createMMonitorEntity(MMonitorEntityInputVO request){
        long  nowTimestamp =  System.currentTimeMillis();
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            MMonitorEntity mMonitorEntity = new MMonitorEntity();
            mMonitorEntity.setId(idGenerator.nextId());
            mMonitorEntity.setName( request.getName() != null ? request.getName() : "" );
            mMonitorEntity.setDesc( request.getDesc() != null ? request.getDesc() : "" );
            if( request.getMapId() != null ){
                mMonitorEntity.setMapId( request.getMapId() );
            }else{
                throw new ServiceException("必须提供DataId");
            }
            mMonitorEntity.setCreateTimestamp(nowTimestamp);
            mMonitorEntity.setIsDeleted(false);
            mMonitorEntityRepository.save(mMonitorEntity);

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData("OK");
            return responseVO;

        }catch ( ServiceException e ){
            log.error("MMonitor新建异常", e);
            return responseVO;
        }catch (Exception e){
            log.error("MMonitor新建异常", e);
            return responseVO;
        }
    }

    /**
     * 更新监控
     */
    @Override
    public ResponseVO updateMMonitorEntity(MMonitorEntityInputVO request){
        ResponseVO responseVO =new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            Optional<MMonitorEntity> opt = mMonitorEntityRepository.findById(request.getId());
            MMonitorEntity mMonitorEntity;
            if( opt.isPresent() ){
                mMonitorEntity = opt.get();
            }else{
                throw new ServiceException("未找到该数据");
            }

            if( request.getName() != null ){
                mMonitorEntity.setName( request.getName() );
            }
            if( request.getDesc() != null ){
                mMonitorEntity.setDesc( request.getDesc() );
            }
            if( request.getMapId() != null ){
                mMonitorEntity.setMapId( request.getMapId() );
            }
            mMonitorEntityRepository.save(mMonitorEntity);

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData("OK");
            return responseVO;

        }catch (Exception e){
            log.error("MMonitor更新异常", e);
            return responseVO;
        }
    }

    /**
     * 读取一个监控（Id方式）
     */
    @Override
    public ResponseVO<MMonitorEntityOutputVO> getMMonitorEntityById(String id){
        ResponseVO responseVO =new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            MMonitorEntity mMonitorEntity;
            Optional<MMonitorEntity> opt = mMonitorEntityRepository.findById(id); //"N1310975139973697536"
            if( opt.isPresent() ){
                mMonitorEntity = opt.get();
                if( mMonitorEntity.getIsDeleted() ){
                    throw new ServiceException("数据已删除");
                }
            }else{
                throw new ServiceException("未找到该数据");
            }

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData(new MMonitorEntityOutputVO(mMonitorEntity));
            return responseVO;
        }catch( ServiceException e){
            log.error("MMonitor读取异常", e);
            return responseVO;
        }catch (Exception e){
            log.error("MMonitor读取异常", e);
            return responseVO;
        }
    }

    /**
     * 删除监控
     */
    @Override
    public ResponseVO deleteMMonitorEntity(String id){
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            MMonitorEntity mMonitorEntity;
            Optional<MMonitorEntity> opt = mMonitorEntityRepository.findById(id); //"N1310975139973697536"
            if( opt.isPresent() ){
                mMonitorEntity = opt.get();
                mMonitorEntity.setIsDeleted(true);
                mMonitorEntityRepository.save(mMonitorEntity);
            }else{
                throw new ServiceException("未找到该数据");
            }

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData("OK");
            return responseVO;

        }catch (Exception e){
            log.error("MMonitor删除异常", e);
            return responseVO;
        }
    }
}
