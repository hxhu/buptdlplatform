package com.bupt.dlplatform.vo;

import com.bupt.dlplatform.model.EDataSetEntity;
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
public class EDataSetOutputVO {
    private String id;

    private String dataSetName;

    private String dataSetDesc;

    private Long createTime;

    private Long updateTime;

    private String type; // ssd yolo

    public EDataSetOutputVO(EDataSetEntity eDataSetEntity){
        this.id = eDataSetEntity.getId();
        this.dataSetName = eDataSetEntity.getDataSetName();
        this.dataSetDesc = eDataSetEntity.getDataSetDesc();
        this.createTime = eDataSetEntity.getCreateTime();
        this.updateTime = eDataSetEntity.getUpdateTime();
        this.type = eDataSetEntity.getType();
    }
}
