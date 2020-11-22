package com.bupt.dlplatform.service.impl;

import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.mapper.MLogEntityRepository;
import com.bupt.dlplatform.model.MLogEntity;
import com.bupt.dlplatform.model.common.LogElement;
import com.bupt.dlplatform.service.LogService;
import com.bupt.dlplatform.util.IdGenerator;
import com.bupt.dlplatform.vo.MLogEntityInputVO;
import com.bupt.dlplatform.vo.MLogEntityOutputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by huhx on 2020/11/22
 */
@Slf4j
@Service
public class LogServiceimpl implements LogService{
    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private MLogEntityRepository mLogEntityRepository;

    /**
     * 更新日志
     */
    @Override
    public ResponseVO updateMLogEntity(MLogEntityInputVO request){
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            MLogEntity mLogEntity = mLogEntityRepository.findByType(request.getType());
            if( mLogEntity == null ){
                mLogEntity = new MLogEntity();
                mLogEntity.setId(idGenerator.nextId());
                mLogEntity.setType(request.getType());
                List<LogElement> tmp = new ArrayList<LogElement>();
                tmp.add(new LogElement(request));
                mLogEntity.setValue(tmp);
                mLogEntity.setIsDeleted(false);
            }else{
                List<LogElement> list = mLogEntity.getValue();
                if( list.size() >= 20 ){
                    for( int i = 0; i < list.size()-1; i++ ){
                        list.set(i, list.get(i+1));
                    }
                    list.set(list.size()-1, new LogElement(request));
                }else{
                    list.add(new LogElement(request));
                }
                mLogEntity.setValue(list);
            }


            mLogEntityRepository.save(mLogEntity);

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData("OK");
            return responseVO;

        }catch (Exception e){
            log.error("MLog更新异常", e);
            return responseVO;
        }
    }

    /**
     * 查询日志
     */
    @Override
    public ResponseVO<ArrayList<ArrayList<MLogEntityOutputVO>>> getLogEntities(){
        ResponseVO responseVO =new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            ArrayList<ArrayList<MLogEntityOutputVO>> result = new ArrayList<ArrayList<MLogEntityOutputVO>>();

            for( int i = 1; i <= 4; i++ ){
                MLogEntity data = mLogEntityRepository.findByType(i);
                ArrayList<MLogEntityOutputVO> arrayList = new ArrayList<MLogEntityOutputVO>();
                if( data != null ){
                   for( LogElement e : data.getValue() ){
                       arrayList.add(new MLogEntityOutputVO(e));
                   }
                }
                result.add(arrayList);
            }

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData(result);
            return responseVO;

        }catch (Exception e){
            log.error("MLog更新异常", e);
            return responseVO;
        }
    }
}
