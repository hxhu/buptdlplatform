package com.bupt.dlplatform.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * Created by huhx on 2020/9/30
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MDataEntityInputVO {
    @ApiModelProperty(value ="id")
    private String id;

    @ApiModelProperty(value = "数据名")
    private String name;

    @ApiModelProperty(value = "创建时间")
    private Long createTimestamp;

    @ApiModelProperty(value = "更新时间")
    private Long lastTimestamp;

    @ApiModelProperty(value = "数据类型")
    private String type;

    @ApiModelProperty(value ="值")
    private Object value;  //  这里是一个值
}
