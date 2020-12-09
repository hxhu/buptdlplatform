package com.bupt.dlplatform.model;

import com.bupt.dlplatform.vo.ERHeartbeatInputVO;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huhx on 2020/12/9
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ERHeartbeatEntity {
    private String deviceId;

    private String type; // message

    private String status; //data 0, 1, 2

    private Long timestamp;

    public ERHeartbeatEntity(ERHeartbeatInputVO erHeartbeatInputVO){
        this.deviceId  = erHeartbeatInputVO.getDeviceId();
        this.type = erHeartbeatInputVO.getType();
        this.status = erHeartbeatInputVO.getStatus();
        this.timestamp = erHeartbeatInputVO.getTimestamp();
    }

    public ERHeartbeatEntity(Map<String, Object> map){
        this.deviceId  = (String)map.get("deviceId");
        this.type = (String)map.get("type");
        this.status = (String)map.get("status");
        this.timestamp = Long.parseLong( (String)map.get("timestamp") );
    }

    public Map<String, Object> turn2Map(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("deviceId", this.deviceId);
        map.put("type", this.type);
        map.put("status", this.status);
        map.put("timestamp", this.timestamp.toString());

        return map;
    }

}
