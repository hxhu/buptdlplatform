package com.bupt.dlplatform.service.impl;

import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.exception.ServiceException;
import com.bupt.dlplatform.model.EModelEntity;
import com.bupt.dlplatform.model.ERHeartbeatEntity;
import com.bupt.dlplatform.service.ERHeartbeatService;
import com.bupt.dlplatform.vo.ERHeartbeatInputVO;
import com.bupt.dlplatform.vo.ERHeartbeatOutputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import com.bupt.dlplatform.vo.StatusMapOutputVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by huhx on 2020/12/9
 */
@Slf4j
@Service
public class ERHeartbeatServiceImpl implements ERHeartbeatService {
    @Autowired
    private RedisTemplate redisTemplate = null;

    @Autowired
    private StringRedisTemplate stringRedisTemplate = null;

    /**
     * 增加心跳
     * @return
     */
    public ResponseVO addERHeartbeat(ERHeartbeatInputVO erHeartbeatInputVO){
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            ERHeartbeatEntity erHeartbeatEntity = new ERHeartbeatEntity(erHeartbeatInputVO);
            Map<String, Object> map = erHeartbeatEntity.turn2Map();

            stringRedisTemplate.opsForHash().putAll(erHeartbeatEntity.getDeviceId(), map);

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData("OK");
            return responseVO;

        }catch (Exception e){
            log.error("ERHeartbeat增加异常", e);
            return responseVO;
        }
    }

    /**
     * 修改心跳
     * @return
     */
    public ResponseVO updateERHeartbeat(ERHeartbeatInputVO erHeartbeatInputVO){
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            return addERHeartbeat(erHeartbeatInputVO);
        }catch (Exception e){
            log.error("ERHeartbeat修改异常", e);
            return responseVO;
        }
    }

    /**
     * 查询心跳
     * Id方式
     * @return
     */
    public ResponseVO<ERHeartbeatOutputVO> getERHeartbeat(String deviceId){
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            BoundHashOperations hashOps = stringRedisTemplate.boundHashOps(deviceId);

            ERHeartbeatEntity erHeartbeatEntity = new ERHeartbeatEntity( hashOps.entries() );

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData(new ERHeartbeatOutputVO(erHeartbeatEntity));
            return responseVO;

        }catch (Exception e){
            log.error("ERHeartbeat查询异常", e);
            return responseVO;
        }
    }

    /**
     * 查询设备状态
     * Id列表方式
     * @return
     */
    public ResponseVO<Map<String,String>> getDeviceStatus(StatusMapOutputVO statusMapOutputVO){
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            Map<String,String> result = new HashMap<String,String>();
            for( String deviceId : statusMapOutputVO.getDeviceIds() ){
                String status = "0";
                try{
                    BoundHashOperations hashOps = stringRedisTemplate.boundHashOps(deviceId);
                    ERHeartbeatEntity erHeartbeatEntity = new ERHeartbeatEntity( hashOps.entries() );
                    status = erHeartbeatEntity.getStatus();
                }catch ( Exception e ){

                }finally {
                    result.put(deviceId, status);
                }
            }

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData(result);
            return responseVO;

        }catch (Exception e){
            log.error("ERHeartbeat查询异常", e);
            return responseVO;
        }
    }

    /**
     * 删除心跳
     * @return
     */
    public ResponseVO deleteERHeartbeat(String deviceId){
        ResponseVO responseVO = new ResponseVO(ResponseCode.SYSTEM_EXCEPTION);

        try {
            stringRedisTemplate.delete(deviceId);

            responseVO.setCode(ResponseCode.OK.value());
            responseVO.setMsg(ResponseCode.OK.getDescription());
            responseVO.setData("OK");
            return responseVO;

        }catch (Exception e){
            log.error("ERHeartbeat删除异常", e);
            return responseVO;
        }
    }
}
