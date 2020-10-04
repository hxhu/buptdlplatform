package com.bupt.dlplatform.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * Created by huhx on 2020/10/4
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class KafKaConsumerVO {
    @ApiModelProperty(value ="id")
    private String id;

    @ApiModelProperty(value ="设备ID")
    private String deviceId;

    @ApiModelProperty(value = "数据类型")
    private String type; //

    @ApiModelProperty(value ="值")
    private Object value;  //  这里是一个值
}
