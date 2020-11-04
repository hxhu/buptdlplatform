package com.bupt.dlplatform.service.impl;

import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.exception.ServiceException;
import com.bupt.dlplatform.mapper.MDataEntityRepository;
import com.bupt.dlplatform.mapper.MDeviceEntityRepository;
import com.bupt.dlplatform.mapper.MDisplayEntityRepository;
import com.bupt.dlplatform.model.MDataEntity;
import com.bupt.dlplatform.model.MDeviceEntity;
import com.bupt.dlplatform.model.MDisplayEntity;
import com.bupt.dlplatform.service.DeviceService;
import com.bupt.dlplatform.util.IdGenerator;
import com.bupt.dlplatform.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.util.*;

/**
 * Created by huhx on 2020/10/3
 */
@Slf4j
@Service
public class DeviceServiceImpl implements DeviceService {
    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private MDeviceEntityRepository mDeviceEntityRepository;

    @Autowired
    private MDisplayEntityRepository mDisplayEntityRepository;

    @Autowired
    private MDataEntityRepository mDataEntityRepository;

    /**
     * 新建设备
     */
    @Override
    public ResponseVO createMDeviceEntity(MDeviceEntityInputVO request){
        long  nowTimestamp =  System.currentTimeMillis();
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            MDeviceEntity mDeviceEntity = new MDeviceEntity();
            // id
            mDeviceEntity.setId(idGenerator.nextId());

            // ip  拥有者 名字 描述 类型 注册时间
            if( request.getIp() != null ){
                mDeviceEntity.setIp( request.getIp() );
            }
            if( request.getUserId() != null ){
                mDeviceEntity.setUserId( request.getUserId() );
            }else{
                throw new ServiceException("必须提供UserId");
            }
            mDeviceEntity.setName( request.getName() != null ? request.getName() : "" );
            mDeviceEntity.setDesc( request.getDesc() != null ? request.getDesc() : "" );
            if( request.getType() != null ){
                mDeviceEntity.setType( request.getType() );
            }else{
                throw new ServiceException("必须提供type");
            }
            mDeviceEntity.setRegisterTime(nowTimestamp);

            // 状态 数据展示配置 监控配置
            mDeviceEntity.setStatus( request.getStatus() != null ? request.getStatus() : 0 );
            if( request.getDisplayIds() != null){
                mDeviceEntity.setDisplayIds(request.getDisplayIds());
            }
            if( request.getMonitorId() != null){
                mDeviceEntity.setMonitorId(request.getMonitorId());
            }

            // 是否收集数据 最新数据收集时间
            mDeviceEntity.setCollectFlag( request.getCollectFlag() != null ? request.getCollectFlag() : false );
            mDeviceEntity.setLastCollectTime(0L);
            mDeviceEntity.setIsDeleted(false);
            mDeviceEntityRepository.save(mDeviceEntity);

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData("OK");
            return responseVO;

        }catch ( ServiceException e ){
            log.error("MDevice创建异常", e);
            return responseVO;
        }catch (Exception e){
            log.error("MDevice创建异常", e);
            return responseVO;
        }
    }

    /**
     * 更新设备
     */
    @Override
    public ResponseVO updateMDeviceEntity(MDeviceEntityInputVO request){
        ResponseVO responseVO =new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            Optional<MDeviceEntity> opt = mDeviceEntityRepository.findById(request.getId());
            MDeviceEntity mDeviceEntity;
            if( opt.isPresent() ){
                mDeviceEntity = opt.get();
            }else{
                throw new ServiceException("未找到该数据");
            }

            if( request.getIp() != null ){
                mDeviceEntity.setIp( request.getIp() );
            }
            if( request.getName() != null ){
                mDeviceEntity.setName( request.getName() );
            }
            if( request.getDesc() != null ){
                mDeviceEntity.setDesc( request.getDesc() );
            }
            if( request.getStatus() != null ){
                mDeviceEntity.setStatus( request.getStatus() );
            }
            if( request.getDisplayIds() != null ){
                mDeviceEntity.setDisplayIds( request.getDisplayIds() );
            }
            if( request.getMonitorId() != null ){
                mDeviceEntity.setMonitorId( request.getMonitorId() );
            }
            if( request.getCollectFlag() != null ){
                mDeviceEntity.setCollectFlag( request.getCollectFlag() );
            }
            if( request.getLastCollectTime() != null ){
                mDeviceEntity.setLastCollectTime( request.getLastCollectTime() );
            }
            mDeviceEntityRepository.save(mDeviceEntity);

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData("OK");
            return responseVO;

        }catch (Exception e){
            log.error("MDevice更新异常", e);
            return responseVO;
        }
    }

    /**
     * 读取一个设备（Id方式）
     */
    @Override
    public ResponseVO<MDeviceEntityOutputVO> getMDeviceEntityById(String id){
        ResponseVO responseVO =new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            MDeviceEntity mDeviceEntity;
            Optional<MDeviceEntity> opt = mDeviceEntityRepository.findById(id); //"N1310975139973697536"
            if( opt.isPresent() ){
                mDeviceEntity = opt.get();
                if( mDeviceEntity.getIsDeleted() ){
                    throw new ServiceException("数据已删除");
                }
            }else{
                throw new ServiceException("未找到该数据");
            }

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData(new MDeviceEntityOutputVO(mDeviceEntity));
            return responseVO;
        }catch( ServiceException e){
            log.error("MDevice读取异常", e);
            return responseVO;
        }catch (Exception e){
            log.error("MDevice读取异常", e);
            return responseVO;
        }
    }

    /**
     * 根据UserID返回设备列表
     */
    public ResponseVO<ArrayList<MDeviceEntityOutputVO>> getMDeviceEntityByUserId(String userId){
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            ArrayList<MDeviceEntity> srcList = mDeviceEntityRepository.findByUserId(userId); //"N1310975139973697536"
            if( srcList == null ){
                throw new ServiceException("未找到该数据");
            }

            ArrayList<MDeviceEntityOutputVO> destList = new ArrayList<MDeviceEntityOutputVO>();
            for( MDeviceEntity i : srcList ){
                destList.add( new MDeviceEntityOutputVO(i) );
            }

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData(destList);
            return responseVO;
        }catch( ServiceException e){
            log.error("MDevice读取异常", e);
            return responseVO;
        }catch (Exception e){
            log.error("MDevice读取异常", e);
            return responseVO;
        }
    }

    /**
     * 根据设备ID找到数据列表
     */
    @Override
    public ResponseVO<ArrayList<MDataEntityOutputVO>> getMDataEntityByDeviceId(String deviceId){
        ResponseVO responseVO =new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            // 找到该设备
            MDeviceEntity mDeviceEntity;
            Optional<MDeviceEntity> opt = mDeviceEntityRepository.findById(deviceId); //"N1310975139973697536"
            if( opt.isPresent() ){
                mDeviceEntity = opt.get();
                if( mDeviceEntity.getIsDeleted() ){
                    throw new ServiceException("数据已删除");
                }
            }else{
                throw new ServiceException("未找到该数据");
            }

            // 获取DisplayIds列表
            Set<String> displaySet = new HashSet<String>();
            if( mDeviceEntity.getDisplayIds() != null ){
                for( String str : mDeviceEntity.getDisplayIds() ){
                    displaySet.add(str);
                }
            }

            // 获取DataIds列表
            Set<String> dataSet = new HashSet<String>();
            for( String str : displaySet ){
                Optional<MDisplayEntity> tmp = mDisplayEntityRepository.findById(str); //"N1310975139973697536"
                if( tmp.isPresent() ){
                    if( tmp.get().getIsDeleted() ){
                        throw new ServiceException("数据已删除");
                    }
                    dataSet.add( tmp.get().getDataId() );
                }else{
                    throw new ServiceException("未找到该数据");
                }
            }

            // 返回数据具体信息
            Set<MDataEntityOutputVO> result = new HashSet<MDataEntityOutputVO>();
            for( String str : dataSet ){
                Optional<MDataEntity> optTmp = mDataEntityRepository.findById(str);
                if( optTmp.isPresent() ){
                    if( optTmp.get().getIsDeleted() ){
                        throw new ServiceException("数据已删除");
                    }
                    result.add( new MDataEntityOutputVO( optTmp.get() ) ); // 添加数据元素
                }

            }


            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData(result);
            return responseVO;
        }catch( ServiceException e){
            log.error("MDevice读取异常", e);
            return responseVO;
        }catch (Exception e){
            log.error("MDevice读取异常", e);
            return responseVO;
        }
    }

    /**
     * 根据设备ID及类型，找到配置信息
     */
    public ResponseVO<MDisplayEntityOutputVO> getMDisplayEntityByDeviceId(String deviceId, String type){
        ResponseVO responseVO =new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            MDeviceEntity mDeviceEntity;
            Optional<MDeviceEntity> opt = mDeviceEntityRepository.findById(deviceId); //"N1310975139973697536"
            if( opt.isPresent() ){
                mDeviceEntity = opt.get();
                if( mDeviceEntity.getIsDeleted() ){
                    throw new ServiceException("数据已删除");
                }
            }else{
                throw new ServiceException("未找到该数据");
            }

            // 找到相应Type的配置信息
            ArrayList<String> displayIds = mDeviceEntity.getDisplayIds();
            MDisplayEntityOutputVO result = null;
            for( String str : displayIds ){
                MDisplayEntity mDisplayEntity = mDisplayEntityRepository.findByIdAndType(str, type);
                if( mDisplayEntity != null ){
                    result = new MDisplayEntityOutputVO(mDisplayEntity);
                }
            }
            if( result == null ){
                throw new ServiceException("查找数据配置失败");
            }

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData(result);
            return responseVO;
        }catch( ServiceException e){
            log.error("MDevice读取异常", e);
            return responseVO;
        }catch (Exception e){
            log.error("MDevice读取异常", e);
            return responseVO;
        }
    }

    /*
     * 获得某设备的全部配置（Id方式）
     */
    public ResponseVO<List<MDisplayEntityOutputVO>> getConfigsById(String deviceId){
        ResponseVO responseVO =new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            // 找到该设备
            MDeviceEntity mDeviceEntity;
            Optional<MDeviceEntity> opt = mDeviceEntityRepository.findById(deviceId); //"N1310975139973697536"
            if( opt.isPresent() ){
                mDeviceEntity = opt.get();
                if( mDeviceEntity.getIsDeleted() ){
                    throw new ServiceException("数据已删除");
                }
            }else{
                throw new ServiceException("未找到该数据");
            }

            // 获取DisplayIds列表
            Set<String> displaySet = new HashSet<String>();
            if( mDeviceEntity.getDisplayIds() != null ){
                for( String str : mDeviceEntity.getDisplayIds() ){
                    displaySet.add(str);
                }
            }

            // 获取Displays详细列表
            List<MDisplayEntityOutputVO> dataList = new ArrayList<MDisplayEntityOutputVO>();
            for( String str : displaySet ){
                Optional<MDisplayEntity> tmp = mDisplayEntityRepository.findById(str); //"N1310975139973697536"
                if( tmp.isPresent() ){
                    if( tmp.get().getIsDeleted() ){
                        throw new ServiceException("数据已删除");
                    }
                    dataList.add( new MDisplayEntityOutputVO( tmp.get() ) );
                }else{
                    throw new ServiceException("未找到该数据");
                }
            }

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData(dataList);
            return responseVO;
        }catch( ServiceException e){
            log.error("MDevice读取异常", e);
            return responseVO;
        }catch (Exception e){
            log.error("MDevice读取异常", e);
            return responseVO;
        }
    }

    /**
     * 删除设备
     */
    @Override
    public ResponseVO deleteMDeviceEntity(String id){
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            MDeviceEntity mDeviceEntity;
            Optional<MDeviceEntity> opt = mDeviceEntityRepository.findById(id); //"N1310975139973697536"
            if( opt.isPresent() ){
                mDeviceEntity = opt.get();
                mDeviceEntity.setIsDeleted(true);
                mDeviceEntityRepository.save(mDeviceEntity);
            }else{
                throw new ServiceException("未找到该数据");
            }

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData("OK");
            return responseVO;

        }catch (Exception e){
            log.error("MDevice删除异常", e);
            return responseVO;
        }
    }

}
