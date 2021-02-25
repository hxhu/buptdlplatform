package com.bupt.dlplatform.vo;

import com.bupt.dlplatform.model.ERHeartbeatEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by huhx on 2020/12/9
 */
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ERHeartbeatOutputVO {
    private String deviceId;

    private String type; // message

    private String status; //data 0, 1, 2

    private Long timestamp;

    private String targets;

    public ERHeartbeatOutputVO(ERHeartbeatEntity erHeartbeatEntity){
        this.deviceId = erHeartbeatEntity.getDeviceId();
        this.type = erHeartbeatEntity.getType();
        this.status = erHeartbeatEntity.getStatus();
        this.timestamp = erHeartbeatEntity.getTimestamp();
        this.targets = erHeartbeatEntity.getTargets();
    }
}
