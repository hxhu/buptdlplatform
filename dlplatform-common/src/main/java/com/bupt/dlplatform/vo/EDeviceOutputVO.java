package com.bupt.dlplatform.vo;

import com.bupt.dlplatform.model.EDeviceEntity;
import com.bupt.dlplatform.model.EModelEntity;
import lombok.*;

/**
 * Created by huhx on 2020/12/4
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EDeviceOutputVO {
    private String id;

//    private String deviceGroup;

    private String deviceName;

    private String deviceDesc;


    private String videoRtsp;

    private String videoMessage;

    private String currentModelId;

    private EModelOutputVO eModelOutputVO; // 当前模型详细信息

    public EDeviceOutputVO( EDeviceEntity eDeviceEntity, EModelEntity eModelEntity ){
        this.id = eDeviceEntity.getId();
        this.deviceName = eDeviceEntity.getDeviceName();
        this.deviceDesc = eDeviceEntity.getDeviceDesc();
        this.videoRtsp = eDeviceEntity.getVideoRtsp();
        this.videoMessage = eDeviceEntity.getVideoMessage();
        this.currentModelId = eDeviceEntity.getCurrentModelId();
        this.eModelOutputVO = new EModelOutputVO(eModelEntity);
    }
}
