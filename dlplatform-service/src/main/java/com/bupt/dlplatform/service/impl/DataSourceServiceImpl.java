package com.bupt.dlplatform.service.impl;

import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.exception.ServiceException;
import com.bupt.dlplatform.mapper.MDataEntityRepository;
import com.bupt.dlplatform.model.MDataEntity;
import com.bupt.dlplatform.service.DataSourceService;
import com.bupt.dlplatform.util.IdGenerator;
import com.bupt.dlplatform.util.TypeUtil;
import com.bupt.dlplatform.vo.MDataEntityInputVO;
import com.bupt.dlplatform.vo.MDataEntityOutputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

/**
 * Created by huhx on 2020/9/29
 */
@Slf4j
@Service
public class DataSourceServiceImpl implements DataSourceService {
    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private MDataEntityRepository mDataEntityRepository;

    /**
     * 新建数据
     */
    @Override
    public ResponseVO createMDataEntity(MDataEntityInputVO request){
        long  nowTimestamp =  System.currentTimeMillis();
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            MDataEntity mDataEntity = new MDataEntity();
            mDataEntity.setId(idGenerator.nextId());
            mDataEntity.setName( request.getName() == null ? request.getName() : "" );
            mDataEntity.setCreateTimestamp(nowTimestamp);
            mDataEntity.setLastTimestamp(nowTimestamp);
            if( request.getType() != null ){
                mDataEntity.setType( request.getType() );
            }else{
                throw new ServiceException("必须提供type");
            }
            mDataEntity.setValue(TypeUtil.returnVoidValue(request.getType()));
            mDataEntity.setIsDeleted(false);
            mDataEntityRepository.save(mDataEntity);

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData("OK");
            return responseVO;

        }catch ( ServiceException e ){
            log.error("MData新建异常", e);
            return responseVO;
        }catch (Exception e){
            log.error("MData新建异常", e);
            return responseVO;
        }

    }

    /**
     * 更新数据
     */
    @Override
    public ResponseVO updateMDataEntity(MDataEntityInputVO request){
        long  nowTimestamp =  System.currentTimeMillis();
        ResponseVO responseVO =new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            Optional<MDataEntity> opt = mDataEntityRepository.findById(request.getId());
            MDataEntity mDataEntity;
            if( opt.isPresent() ){
                mDataEntity = opt.get();
            }else{
                throw new ServiceException("未找到该数据");
            }

            if( request.getName() != null ){
                mDataEntity.setName( request.getName() );
            }
            mDataEntity.setLastTimestamp(nowTimestamp);
            TypeUtil.insertValue((ArrayList)mDataEntity.getValue(), mDataEntity.getType(), request.getValue());

            mDataEntityRepository.save(mDataEntity);

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData("OK");
            return responseVO;

        }catch (Exception e){
            log.error("MData更新异常", e);
            return responseVO;
        }
    }

    /**
     * 读取一个数据（Id方式）
     */
    @Override
    public ResponseVO<MDataEntityOutputVO> getMDataEntityById(String id){
        ResponseVO responseVO =new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            MDataEntity mDataEntity;
            Optional<MDataEntity> opt = mDataEntityRepository.findById(id); //"N1310975139973697536"
            if( opt.isPresent() ){
                mDataEntity = opt.get();
                if( mDataEntity.getIsDeleted() ){
                    throw new ServiceException("数据已删除");
                }
            }else{
                throw new ServiceException("未找到该数据");
            }

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData(new MDataEntityOutputVO(mDataEntity));
            return responseVO;
        }catch( ServiceException e){
            log.error("MData读取异常", e);
            return responseVO;
        }catch (Exception e){
            log.error("MData读取异常", e);
            return responseVO;
        }
    }

    /**
     * 删除数据
     */
    @Override
    public ResponseVO deleteMDataEntity(String id){
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            MDataEntity mDataEntity;
            Optional<MDataEntity> opt = mDataEntityRepository.findById(id);
            if( opt.isPresent() ){
                mDataEntity = opt.get();
                mDataEntity.setIsDeleted(true);
                mDataEntityRepository.save(mDataEntity);
            }else{
                throw new ServiceException("未找到该数据");
            }

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData("OK");
            return responseVO;

        }catch (Exception e){
            log.error("MData删除异常", e);
            return responseVO;
        }
    }

}
