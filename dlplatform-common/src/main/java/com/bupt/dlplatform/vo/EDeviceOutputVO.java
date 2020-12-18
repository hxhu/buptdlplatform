package com.bupt.dlplatform.vo;

import com.bupt.dlplatform.model.EDeviceEntity;
import com.bupt.dlplatform.model.EFileEntity;
import com.bupt.dlplatform.model.EModelEntity;
import lombok.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

    private Set<EFileOutputVO> currentFileSet;

    public EDeviceOutputVO(EDeviceEntity eDeviceEntity, EModelEntity eModelEntity, Set<EFileEntity> fileSet){
        this.id = eDeviceEntity.getId();
        this.deviceName = eDeviceEntity.getDeviceName();
        this.deviceDesc = eDeviceEntity.getDeviceDesc();
        this.videoRtsp = eDeviceEntity.getVideoRtsp();
        this.videoMessage = eDeviceEntity.getVideoMessage();
        this.currentModelId = eDeviceEntity.getCurrentModelId();
        this.eModelOutputVO = new EModelOutputVO(eModelEntity);

        Set<EFileOutputVO> set = new HashSet<EFileOutputVO>();
        for( EFileEntity eFileEntity: fileSet ){
            set.add( new EFileOutputVO(eFileEntity) );
        }
        this.currentFileSet = set;
    }
}
