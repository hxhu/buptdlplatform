package com.bupt.dlplatform.vo;

import com.bupt.dlplatform.model.MMonitorEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * Created by huhx on 2020/10/3
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MMonitorEntityOutputVO {
    @ApiModelProperty(value ="id")
    private String id;

    @ApiModelProperty(value ="监控名")
    private String name;

    @ApiModelProperty(value ="描述")
    private String desc;

    @ApiModelProperty(value ="创建时间")
    private Long createTimestamp;

    @ApiModelProperty(value ="地图ID")
    private String mapId;

    public MMonitorEntityOutputVO(MMonitorEntity mMonitorEntity){
        setId(mMonitorEntity.getId());
        setName(mMonitorEntity.getName());
        setDesc(mMonitorEntity.getDesc());
        setCreateTimestamp(mMonitorEntity.getCreateTimestamp());
        setMapId(mMonitorEntity.getMapId());
    }
}
