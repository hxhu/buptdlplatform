package com.bupt.dlplatform.vo;

import com.bupt.dlplatform.model.EDeviceEntity;
import com.bupt.dlplatform.model.ELogEntity;
import com.bupt.dlplatform.model.EModelEntity;
import lombok.*;

/**
 * Created by huhx on 2020/12/15
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ELogOutputVO {
    private String id;

    private String modelId;

    private String deviceId;

    private String type; // 设备1 2 3  模型-1 -2 -3

    private String message; // 操作详情

    private Long timestamp;

    private EModelOutputVO eModelOutputVO;

    private EDeviceOutputVO eDeviceOutputVO;

    public ELogOutputVO(ELogEntity eLogEntity, EModelEntity eModelEntity, EDeviceEntity eDeviceEntity){
        this.id = eLogEntity.getId();
        this.modelId = eLogEntity.getModelId();
        this.deviceId = eLogEntity.getDeviceId();
        this.type = eLogEntity.getType();
        this.message = eLogEntity.getMessage();
        this.timestamp = eLogEntity.getTimestamp();

        this.eModelOutputVO = new EModelOutputVO(eModelEntity);
        this.eDeviceOutputVO = new EDeviceOutputVO(eDeviceEntity, eModelEntity,null);
    }
}
