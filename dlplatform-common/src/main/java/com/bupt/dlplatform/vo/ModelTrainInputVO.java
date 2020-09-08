package com.bupt.dlplatform.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "模型训练输入")
public class ModelTrainInputVO extends BaseInputVO {
    @ApiModelProperty(value = "训练Id")
    private String trainId;

    @ApiModelProperty(value = "训练网络")
    private String network;

    @ApiModelProperty(value = "训练名")
    private String trainName;

    @ApiModelProperty(value = "训练集Id")
    private String trainsetId;

    @ApiModelProperty(value = "gpu数量")
    private String gpus;

    @ApiModelProperty(value = "训练描述")
    private String description;
}
