package com.bupt.dlplatform.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.annotation.Id;

/**
 * Created by huhx on 2020/10/3
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MDisplayEntityInputVO {
    @ApiModelProperty(value ="id")
    private String id;

    @ApiModelProperty(value ="配置名")
    private String name;

    @ApiModelProperty(value ="描述")
    private String desc;

    @ApiModelProperty(value ="类型")
    private String type; // list,figure,picture,video,map,heartbeat

    @ApiModelProperty(value ="配置内容")
    private Object configs;

    @ApiModelProperty(value ="关联数据ID")
    private String dataId;
}
