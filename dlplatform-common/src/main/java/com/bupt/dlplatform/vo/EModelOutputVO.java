package com.bupt.dlplatform.vo;

import com.bupt.dlplatform.model.EModelEntity;
import lombok.*;

import java.util.Date;

/**
 * Created by huhx on 2020/12/4
 */
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EModelOutputVO {
    private String id;

    private String modelName;

    private String modelDesc;

    private Long createTime;

    private String modelLocation;

    public EModelOutputVO( EModelEntity eModelEntity ){
        this.id = eModelEntity.getId();
        this.modelName = eModelEntity.getModelName();
        this.modelDesc = eModelEntity.getModelDesc();
        this.createTime = eModelEntity.getCreateTime();
        this.modelLocation = eModelEntity.getModelLocation();
    }
}
