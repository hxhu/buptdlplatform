package com.bupt.dlplatform.service.impl;

import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.mapper.MDataEntityRepository;
import com.bupt.dlplatform.model.MDataEntity;
import com.bupt.dlplatform.service.DataDisplayService;
import com.bupt.dlplatform.util.IdGenerator;
import com.bupt.dlplatform.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Created by huhx on 2020/9/29
 */
@Slf4j
@Service
public class DataDisplayServiceImpl implements DataDisplayService {
    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private MDataEntityRepository mDataEntityRepository;

    /**
     * 新建数据
     */
    @Override
    public ResponseVO createMDataEntity(){
        ResponseVO responseVO =new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            MDataEntity mDataEntity = new MDataEntity();
            mDataEntity.setId(idGenerator.nextId());
            mDataEntity.setName("name");
            mDataEntity.setCreateTimestamp(123L);
            mDataEntity.setLastTimestamp(124L);
            mDataEntity.setType("list");
            mDataEntity.setValue("1234");
            mDataEntityRepository.save(mDataEntity);

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData("OK");
            return responseVO;

        }catch (Exception e){
            log.error("MData 保存异常", e);
            return responseVO;
        }

    }

    /**
     * 更新数据
     */
    public ResponseVO updateMDataEntity(){
        ResponseVO responseVO =new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            MDataEntity mDataEntity = new MDataEntity();
            mDataEntity.setId(idGenerator.nextId());
            mDataEntity.setName("name");
            mDataEntity.setCreateTimestamp(123L);
            mDataEntity.setLastTimestamp(124L);
            mDataEntity.setType("list");
            mDataEntity.setValue("1234");
            mDataEntityRepository.save(mDataEntity);

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData("OK");
            return responseVO;

        }catch (Exception e){
            log.error("MData 更新异常", e);
            return responseVO;
        }
    }

    /**
     * 读取一条数据（Id方式）
     */
    public ResponseVO<MDataEntity> getMDataEntityById(){
        ResponseVO responseVO =new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            MDataEntity mDataEntity = new MDataEntity();
//            mDataEntity.setId(idGenerator.nextId());
//            mDataEntity.setName("name");
//            mDataEntity.setCreateTimestamp(123L);
//            mDataEntity.setLastTimestamp(124L);
//            mDataEntity.setType("list");
//            mDataEntity.setValue("1234");
            Optional<MDataEntity> opt = mDataEntityRepository.findById("N1310975139973697536");
            opt.ifPresent( value -> {
                mDataEntity.setId(value.getId());
                mDataEntity.setName(value.getName());
                mDataEntity.setCreateTimestamp(value.getCreateTimestamp());
                mDataEntity.setLastTimestamp(value.getLastTimestamp());
                mDataEntity.setType(value.getType());
                mDataEntity.setValue(value.getValue());
            });

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData(mDataEntity);
            return responseVO;

        }catch (Exception e){
            log.error("MData 更新异常", e);
            return responseVO;
        }
    }

    /**
     * 删除数据
     */
    public ResponseVO daleteMDataEntity(){
        ResponseVO responseVO =new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData("OK");
            return responseVO;

        }catch (Exception e){
            log.error("MData 更新异常", e);
            return responseVO;
        }
    }

}
