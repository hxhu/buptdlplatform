package com.bupt.dlplatform.vo;

import com.bupt.dlplatform.model.ECaseEntity;
import lombok.*;

/**
 * Created by huhx on 2020/12/23
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ECaseOutputVO {
    private String id;

    private String caseName;

    private String caseDesc;

    private Long createTime;

    private Long updateTime;

    private String dataSetId;

    private String modelId;

    private String type;  // ssd yolo

    private String status;  // "0" —— "1" —— "6"

    public ECaseOutputVO(ECaseEntity eCaseEntity){
        this.id = eCaseEntity.getId();
        this.caseName = eCaseEntity.getCaseName();
        this.caseDesc = eCaseEntity.getCaseDesc();
        this.createTime = eCaseEntity.getCreateTime();
        this.updateTime = eCaseEntity.getUpdateTime();
        this.dataSetId = eCaseEntity.getDataSetId();
        this.modelId = eCaseEntity.getModelId();
        this.type = eCaseEntity.getType();
        this.status = eCaseEntity.getStatus();
    }
}
