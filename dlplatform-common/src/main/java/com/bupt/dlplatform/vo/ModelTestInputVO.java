package com.bupt.dlplatform.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.util.Date;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "模型测试相关")
public class ModelTestInputVO extends  BaseInputVO {

    @ApiModelProperty(value = "测试Id")
    private String testId;

    @ApiModelProperty(value = "检测网络")
    private String testNetwork;

    @ApiModelProperty(value = "模型Id")
    private String modelId;

    @ApiModelProperty(value = "测试名")
    private String testName;

    @ApiModelProperty(value = "测试集Id")
    private String testsetId;

    @ApiModelProperty(value = "阈值")
    private String threshold;

    @ApiModelProperty(value = "标签")
    private String label;

}
