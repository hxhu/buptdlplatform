package com.bupt.dlplatform.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.data.annotation.Id;

/**
 * Created by huhx on 2020/10/3
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MMonitorEntityInputVO {
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
}
